package net.chabelabela.outerwildsmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class OuterWildsModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Black void dimension effects
        DimensionRenderingRegistry.registerDimensionEffects(
                Objects.requireNonNull(Identifier.tryParse("outerwildsmod:space")),
                new DimensionEffects(Float.NaN, false, DimensionEffects.SkyType.NONE, false, false) {
                    @Override
                    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
                        return new Vec3d(0, 0, 0); // completely black sky/fog
                    }

                    @Override
                    public boolean useThickFog(int camX, int camY) {
                        return false; // no thick fog
                    }
                }
        );
    }
}

