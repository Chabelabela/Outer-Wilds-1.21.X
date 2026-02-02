package net.chabelabela.outerwildsmod.registry.entity;

import net.chabelabela.outerwildsmod.OuterWildsMod;
import net.chabelabela.outerwildsmod.entities.SunEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * Registers all custom entities for the Outer Wilds mod.
 */
public final class ModEntities {

    // Entity type instances
    public static EntityType<SunEntity> SUN_ENTITY;

    // Registry keys
    private static final RegistryKey<EntityType<?>> SUN_KEY = RegistryKey.of(
            RegistryKeys.ENTITY_TYPE,
            Identifier.of(OuterWildsMod.MOD_ID, "sun")
    );

    public static void register() {
        System.out.println("[OuterWildsMod] Registering entities...");

        // Build the Sun entity type
        SUN_ENTITY = Registry.register(
                Registries.ENTITY_TYPE,
                SUN_KEY,
                EntityType.Builder.create(SunEntity::new, SpawnGroup.MISC)
                        .dimensions(4.0f, 4.0f)      // Larger hitbox
                        .maxTrackingRange(1024)      // HUGE tracking range for celestial bodies
                        .trackingTickInterval(1)     // Update every tick
                        .build(String.valueOf(SUN_KEY))
        );

        System.out.println("[OuterWildsMod] Entities registered!");
    }

    private ModEntities() {}
}