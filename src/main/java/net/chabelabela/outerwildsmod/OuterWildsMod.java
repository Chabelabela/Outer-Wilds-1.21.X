package net.chabelabela.outerwildsmod;

import net.chabelabela.outerwildsmod.registry.ModBlocks;
import net.chabelabela.outerwildsmod.registry.ModItems;
import net.chabelabela.outerwildsmod.registry.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OuterWildsMod implements ModInitializer {
	public static final String MOD_ID = "outerwilds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Clés de registre pour notre dimension
	public static final RegistryKey<World> SPACE_WORLD_KEY = RegistryKey.of(
			RegistryKeys.WORLD,
			Identifier.of(MOD_ID, "space")
	);

	public static final RegistryKey<DimensionType> SPACE_DIMENSION_TYPE_KEY = RegistryKey.of(
			RegistryKeys.DIMENSION_TYPE,
			Identifier.of(MOD_ID, "space_type")
	);

	@Override
	public void onInitialize() {
		LOGGER.info("Initialisation d'Outer Wilds Mod - Exploration de l'espace commence !");

		// Initialisation des registres - ORDRE IMPORTANT !
		ModWorldGeneration.initialize(); // D'abord les générateurs
		ModBlocks.initialize();
		ModItems.initialize();

		// Événements serveur
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			LOGGER.info("Serveur en cours de démarrage - Préparation de l'espace...");
		});
	}
}