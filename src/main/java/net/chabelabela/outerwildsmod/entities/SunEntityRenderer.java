package net.chabelabela.outerwildsmod.entities;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.Frustum;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

/**
 * Renders the Sun as a billboard (always faces the camera) with a core and bloom layer.
 * Configured to render from very far distances (celestial body).
 */
public class SunEntityRenderer extends EntityRenderer<SunEntity> {

    private static final Identifier CORE_TEX = Identifier.of("outerwildsmod", "textures/entity/sun_core.png");
    private static final Identifier BLOOM_TEX = Identifier.of("outerwildsmod", "textures/entity/sun_bloom.png");

    public SunEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0f;
    }

    /**
     * Override to always render the sun regardless of frustum culling.
     * This is critical for celestial bodies that should be visible from anywhere.
     */
    @Override
    public boolean shouldRender(SunEntity entity, Frustum frustum, double x, double y, double z) {
        // ALWAYS render the sun - ignore all culling
        return true;
    }

    @Override
    public void render(SunEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {

        // Debug: uncomment to verify render is being called
        // System.out.println("[OuterWildsMod] Rendering sun at distance: " +
        //     Math.sqrt(x*x + y*y + z*z) + " from camera");

        matrices.push();

        // Billboard: make the quad always face the camera
        matrices.multiply(this.dispatcher.getRotation());

        // Scale based on entity's radius
        float scale = entity.getRadius();
        matrices.scale(scale, scale, scale);

        // Full brightness - the sun is a light source!
        int fullBright = 15728880;

        // === BLOOM LAYER (render first, larger, behind core) ===
        matrices.push();
        matrices.scale(2.0f, 2.0f, 2.0f);
        renderQuad(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(BLOOM_TEX)),
                255, 200, 100, 120, fullBright);
        matrices.pop();

        // === CORE LAYER (on top) ===
        renderQuad(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(CORE_TEX)),
                255, 255, 255, 255, fullBright);

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    /**
     * Renders a simple quad facing the camera.
     */
    private void renderQuad(MatrixStack matrices, VertexConsumer consumer,
                            int r, int g, int b, int a, int light) {
        Matrix4f posMatrix = matrices.peek().getPositionMatrix();

        consumer.vertex(posMatrix, -0.5f, -0.5f, 0f)
                .color(r, g, b, a)
                .texture(0f, 1f)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(0f, 0f, 1f);

        consumer.vertex(posMatrix, 0.5f, -0.5f, 0f)
                .color(r, g, b, a)
                .texture(1f, 1f)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(0f, 0f, 1f);

        consumer.vertex(posMatrix, 0.5f, 0.5f, 0f)
                .color(r, g, b, a)
                .texture(1f, 0f)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(0f, 0f, 1f);

        consumer.vertex(posMatrix, -0.5f, 0.5f, 0f)
                .color(r, g, b, a)
                .texture(0f, 0f)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(0f, 0f, 1f);
    }

    @Override
    public Identifier getTexture(SunEntity entity) {
        return CORE_TEX;
    }
}