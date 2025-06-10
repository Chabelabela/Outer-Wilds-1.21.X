package net.chabelabela.outerwildsmod.registry;

import net.chabelabela.outerwildsmod.OuterWildsMod;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // Items du mod (outils, équipements spatiaux, etc.)

    public static void initialize() {
        OuterWildsMod.LOGGER.info("Enregistrement des items Outer Wilds");
        // Enregistrements futurs
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(OuterWildsMod.MOD_ID, name), item);
    }
}