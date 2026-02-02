package net.chabelabela.outerwildsmod.registry.dimensionrender;

import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;

/**
 * Custom dimension effects for the space dimension.
 * Creates a pitch-black void with no fog, clouds, or vanilla sky.
 */
public final class SpaceDimensionEffects extends DimensionEffects {
    
    public static final SpaceDimensionEffects INSTANCE = new SpaceDimensionEffects();

    private SpaceDimensionEffects() {
        // Parameters:
        // - cloudHeight: Float.NaN disables clouds
        // - alternateSkyColor: false (no alternate sky)
        // - skyType: NONE (no vanilla sky rendering)
        // - brightenLighting: false
        // - darkened: false
        super(Float.NaN, false, SkyType.NONE, false, false);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        // Pure black - no fog color
        return Vec3d.ZERO;
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        // No fog in space
        return false;
    }

    // Private constructor pattern enforced by INSTANCE
}
