package net.chabelabela.outerwildsmod;

import net.fabricmc.api.ModInitializer;
import net.chabelabela.outerwildsmod.registry.commands.SpaceCommand;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class OuterWildsMod implements ModInitializer {

	public static final RegistryKey<World> SPACE_WORLD_KEY =
			RegistryKey.of(RegistryKeys.WORLD, Identifier.tryParse("outerwildsmod:space"));

	@Override
	public void onInitialize() {
		System.out.println("[OuterWildsMod] Mod initialized");
		SpaceCommand.register();

		// Server tick: update player gravity manually
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				handleGravity(player);
			}
		});
	}

	private void handleGravity(ServerPlayerEntity player) {
		boolean inSpace = player.getWorld().getRegistryKey().equals(SPACE_WORLD_KEY);

		if (inSpace) {
			player.setVelocity(player.getVelocity().x, 0.0, player.getVelocity().z);
			player.velocityModified = true;
			player.fallDistance = 0f;
		}
	}
}
