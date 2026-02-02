package net.chabelabela.outerwildsmod;

import net.chabelabela.outerwildsmod.registry.commands.SpaceCommand;
import net.chabelabela.outerwildsmod.registry.entity.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class OuterWildsMod implements ModInitializer {

    public static final String MOD_ID = "outerwildsmod";

    // Registry key for the space dimension
    public static final RegistryKey<World> SPACE_WORLD_KEY =
            RegistryKey.of(RegistryKeys.WORLD, Identifier.of(MOD_ID, "space"));

    @Override
    public void onInitialize() {
        System.out.println("[OuterWildsMod] Initializing...");

        // Register entities
        ModEntities.register();

        // Register commands
        SpaceCommand.register();

        // Server tick: handle zero-G for players in space
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                handleZeroGravity(player);
            }
        });

        System.out.println("[OuterWildsMod] Initialization complete!");
    }

    /**
     * Handles zero-gravity for players in the space dimension.
     * Currently just neutralizes Y velocity—we'll add planetary gravity later.
     */
    private void handleZeroGravity(ServerPlayerEntity player) {
        boolean inSpace = player.getWorld().getRegistryKey().equals(SPACE_WORLD_KEY);

        if (inSpace) {
            // Neutralize gravity by preserving horizontal velocity but zeroing vertical acceleration
            // Note: This is a simple approach. For true zero-G, we'll need mixins later.
            var velocity = player.getVelocity();
            
            // Only counteract gravity if not actively moving up/down (jump/sneak)
            if (!player.isOnGround() && !player.isSneaking() && !player.getAbilities().flying) {
                // Counteract default gravity (0.08 blocks/tick downward)
                player.setVelocity(velocity.x, velocity.y + 0.08, velocity.z);
                player.velocityModified = true;
            }
            
            // Prevent fall damage in space
            player.fallDistance = 0f;
        }
    }
}
