package net.chabelabela.outerwildsmod.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.util.Identifier;

import java.util.stream.Stream;

public class SpaceBiomeSource extends BiomeSource {
    public static final Codec<SpaceBiomeSource> CODEC = Codec.unit(SpaceBiomeSource::new);

    private final RegistryEntry<Biome> spaceBiome;

    public SpaceBiomeSource() {
        // Utilise temporairement le biome "the_void" en attendant notre biome custom
        this.spaceBiome = BuiltinRegistries.createWrapperLookup()
                .getWrapperOrThrow(RegistryKeys.BIOME)
                .getClass(Identifier.of("minecraft", "the_void"));
    }

    @Override
    protected MapCodec<? extends BiomeSource> getCodec() {
        return (MapCodec<? extends BiomeSource>) CODEC;
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        return this.spaceBiome;
    }

    @Override
    protected Stream<RegistryEntry<Biome>> biomeStream() {
        return Stream.of(this.spaceBiome);
    }
}