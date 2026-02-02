package net.chabelabela.outerwildsmod;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chabelabela.outerwildsmod.entities.SunEntityRenderer;
import net.chabelabela.outerwildsmod.registry.dimensionrender.SpaceDimensionEffects;
import net.chabelabela.outerwildsmod.registry.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Client entrypoint: registers dimension effects, sky renderer, and entity renderers.
 */
public class OuterWildsModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("[OuterWildsMod] Initializing client...");

        Identifier spaceId = Identifier.of(OuterWildsMod.MOD_ID, "space");
        RegistryKey<World> spaceWorldKey = RegistryKey.of(RegistryKeys.WORLD, spaceId);

        // Register custom DimensionEffects (controls fog, sky type, etc.)
        DimensionRenderingRegistry.registerDimensionEffects(spaceId, SpaceDimensionEffects.INSTANCE);

        // Register sky renderer: pure black void
        DimensionRenderingRegistry.registerSkyRenderer(spaceWorldKey, ctx -> {
            RenderSystem.clearColor(0f, 0f, 0f, 1f);
            RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT, false);
        });

        // Disable clouds in space
        DimensionRenderingRegistry.registerCloudRenderer(spaceWorldKey, ctx -> {
            // No clouds in space
        });

        // Disable weather in space
        DimensionRenderingRegistry.registerWeatherRenderer(spaceWorldKey, ctx -> {
            // No weather in space
        });

        // Register entity renderers
        EntityRendererRegistry.register(ModEntities.SUN_ENTITY, SunEntityRenderer::new);

        System.out.println("[OuterWildsMod] Client initialization complete!");
    }
}
