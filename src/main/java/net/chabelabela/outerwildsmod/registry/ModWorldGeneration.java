package net.chabelabela.outerwildsmod.registry;

import net.chabelabela.outerwildsmod.OuterWildsMod;
import net.chabelabela.outerwildsmod.world.dimension.SpaceBiomeSource;
import net.chabelabela.outerwildsmod.world.dimension.SpaceChunkGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModWorldGeneration {

    public static void initialize() {
        OuterWildsMod.LOGGER.info("Enregistrement des générateurs de monde Outer Wilds");

        // Enregistrement du BiomeSource
        Registry.register(
                Registries.BIOME_SOURCE,
                Identifier.of(OuterWildsMod.MOD_ID, "space"),
                SpaceBiomeSource.CODEC
        );

        // Enregistrement du ChunkGenerator
        Registry.register(
                Registries.CHUNK_GENERATOR,
                Identifier.of(OuterWildsMod.MOD_ID, "space"),
                SpaceChunkGenerator.CODEC
        );

        OuterWildsMod.LOGGER.info("Générateurs de monde enregistrés avec succès !");
    }
}
