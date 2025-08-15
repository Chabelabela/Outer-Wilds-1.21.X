package net.chabelabela.outerwildsmod.registry.dimensionrender;

import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;

public class SpaceDimensionEffects extends DimensionEffects {
    public SpaceDimensionEffects() {
        // Distance: NaN disables fog distance calc, no clouds, no alternate sky
        super(Float.NaN, false, SkyType.NONE, false, false);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return new Vec3d(0.0, 0.0, 0.0); // pure black
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return false;
    }
}
