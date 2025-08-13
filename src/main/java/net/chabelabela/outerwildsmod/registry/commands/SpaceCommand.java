package net.chabelabela.outerwildsmod.registry.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public final class SpaceCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    literal("space").executes(ctx -> {
                        ServerCommandSource src = ctx.getSource();

                        // Run teleport as the server console
                        src.getServer().getCommandManager().executeWithPrefix(
                                src.getServer().getCommandSource(),
                                "execute in outerwildsmod:space run tp " + src.getName() + " 0 60 0"
                        );

                        src.sendFeedback(() -> Text.of("Teleporting to space..."), false);
                        return 1;
                    })
            );
        });
    }
}