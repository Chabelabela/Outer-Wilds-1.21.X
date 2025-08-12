package net.chabelabela.outerwildsmod;

import net.fabricmc.api.ModInitializer;
import net.chabelabela.outerwildsmod.command.SpaceCommand;

public class OuterWildsMod implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("[OuterWildsMod] Mod initialized");
		SpaceCommand.register();
	}
}
