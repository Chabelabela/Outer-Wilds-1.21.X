package net.chabelabela.outerwildsmod.registry;

import net.chabelabela.outerwildsmod.OuterWildsMod;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    // Ici on ajoutera nos blocs custom plus tard

    public static void initialize() {
        OuterWildsMod.LOGGER.info("Enregistrement des blocs Outer Wilds");
        // Enregistrements futurs
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(OuterWildsMod.MOD_ID, name), block);
    }
}