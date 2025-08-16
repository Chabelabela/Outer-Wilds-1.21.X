package net.chabelabela.outerwildsmod.registry.dimensionrender;

import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;

/**
 * DimensionEffects that make the sky/fog pitch black and disable built-in sky.
 */
public final class SpaceDimensionEffects extends DimensionEffects {
    public static final SpaceDimensionEffects INSTANCE = new SpaceDimensionEffects();

    private SpaceDimensionEffects() {
        // Float.NaN cloud height disables cloud logic; SkyType.NONE avoids built-in sky rendering
        super(Float.NaN, false, SkyType.NONE, false, false);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        // Pure black fog/sky color
        return new Vec3d(0.0, 0.0, 0.0);
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return false;
    }
}
