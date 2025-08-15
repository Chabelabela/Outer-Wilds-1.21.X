package net.chabelabela.outerwildsmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.util.Identifier;
import net.chabelabela.outerwildsmod.registry.dimensionrender.SpaceDimensionEffects;

import java.util.Objects;

public class OuterWildsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DimensionRenderingRegistry.registerDimensionEffects(
                Objects.requireNonNull(Identifier.tryParse("outerwildsmod:space")),
                new SpaceDimensionEffects()
        );
    }
}
