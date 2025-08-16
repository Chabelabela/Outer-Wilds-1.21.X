package net.chabelabela.outerwildsmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.chabelabela.outerwildsmod.registry.dimensionrender.SpaceDimensionEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

/**
 * Client entrypoint: registers the DimensionEffects + a no-op sky renderer for a true black void.
 */
public class OuterWildsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Use Identifier.of(...) per project preference
        Identifier effectsId = Identifier.of("outerwildsmod", "space");
        RegistryKey<World> worldKey = RegistryKey.of(RegistryKeys.WORLD, Identifier.of("outerwildsmod", "space"));

        // Register DimensionEffects (controls fog color / sky type)
        DimensionRenderingRegistry.registerDimensionEffects(effectsId, SpaceDimensionEffects.INSTANCE);

        // Register SkyRenderer: clear to black and do nothing else (prevents vanilla sky rendering)
        DimensionRenderingRegistry.registerSkyRenderer(worldKey, (WorldRenderContext ctx) -> {
            RenderSystem.clearColor(0f, 0f, 0f, 1f);
            RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT, false);
        });

        // Defensive: block clouds and weather from rendering
        DimensionRenderingRegistry.registerCloudRenderer(worldKey, (WorldRenderContext ctx) -> { /* no clouds */ });
        DimensionRenderingRegistry.registerWeatherRenderer(worldKey, (WorldRenderContext ctx) -> { /* no weather */ });
    }
}
