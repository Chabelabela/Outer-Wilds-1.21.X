package net.chabelabela.outerwildsmod.registry.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.chabelabela.outerwildsmod.OuterWildsMod;
import net.chabelabela.outerwildsmod.entities.SunEntity;
import net.chabelabela.outerwildsmod.registry.entity.ModEntities;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * Commands for the Outer Wilds mod.
 * - /space : Teleport to space dimension
 * - /space sun : Spawn the sun entity
 * - /space sun <radius> : Spawn sun with custom radius
 */
public final class SpaceCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    literal("space")
                            // /space - teleport to space
                            .executes(ctx -> teleportToSpace(ctx.getSource()))
                            
                            // /space sun - spawn sun at default position
                            .then(literal("sun")
                                    .executes(ctx -> spawnSun(ctx.getSource(), 10))
                                    
                                    // /space sun <radius> - spawn sun with custom radius
                                    .then(argument("radius", IntegerArgumentType.integer(1, 100))
                                            .executes(ctx -> spawnSun(
                                                    ctx.getSource(),
                                                    IntegerArgumentType.getInteger(ctx, "radius")
                                            ))
                                    )
                            )
            );
        });
    }

    private static int teleportToSpace(ServerCommandSource source) {
        ServerPlayerEntity player = source.getPlayer();
        if (player == null) {
            source.sendError(Text.of("This command can only be run by a player!"));
            return 0;
        }

        ServerWorld spaceWorld = source.getServer().getWorld(OuterWildsMod.SPACE_WORLD_KEY);
        if (spaceWorld == null) {
            source.sendError(Text.of("Space dimension not found! Check your datapack."));
            return 0;
        }

        // Teleport player to space at Y=100 (middle of the void)
        player.teleport(spaceWorld, 0, 100, 0, player.getYaw(), player.getPitch());
        source.sendFeedback(() -> Text.of("§9Teleported to space!"), false);
        
        return 1;
    }

    private static int spawnSun(ServerCommandSource source, int radius) {
        ServerWorld spaceWorld = source.getServer().getWorld(OuterWildsMod.SPACE_WORLD_KEY);
        if (spaceWorld == null) {
            source.sendError(Text.of("Space dimension not found!"));
            return 0;
        }

        // Spawn sun at a fixed position in space
        Vec3d sunPos = new Vec3d(0, 100, -200); // Far from spawn point
        
        SunEntity sun = new SunEntity(ModEntities.SUN_ENTITY, spaceWorld);
        sun.setPosition(sunPos);
        sun.setRadius(radius);
        
        spaceWorld.spawnEntity(sun);
        
        source.sendFeedback(() -> Text.of("§6☀ Spawned the Sun with radius " + radius), true);
        
        return 1;
    }

    // Private constructor - utility class
    private SpaceCommand() {}
}
