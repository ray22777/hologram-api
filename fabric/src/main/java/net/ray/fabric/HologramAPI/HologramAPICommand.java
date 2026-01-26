package net.ray.fabric.HologramAPI;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.ray.HologramAPI.ComponentUtils;
import net.ray.HologramAPI.HologramAPI;

public class HologramAPICommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("hologram")
                .then(ClientCommandManager.literal("create")
                        .then(ClientCommandManager.argument("text", StringArgumentType.string())
                                .then(ClientCommandManager.argument("x", DoubleArgumentType.doubleArg())
                                        .then(ClientCommandManager.argument("y", DoubleArgumentType.doubleArg())
                                                .then(ClientCommandManager.argument("z", DoubleArgumentType.doubleArg())
                                                        .executes(context -> createHologram(
                                                                context,
                                                                StringArgumentType.getString(context, "text"),
                                                                DoubleArgumentType.getDouble(context, "x"),
                                                                DoubleArgumentType.getDouble(context, "y"),
                                                                DoubleArgumentType.getDouble(context, "z"),
                                                                false
                                                        ))
                                                        .then(ClientCommandManager.argument("renderOnTop", BoolArgumentType.bool())
                                                                .executes(context -> createHologram(
                                                                        context,
                                                                        StringArgumentType.getString(context, "text"),
                                                                        DoubleArgumentType.getDouble(context, "x"),
                                                                        DoubleArgumentType.getDouble(context, "y"),
                                                                        DoubleArgumentType.getDouble(context, "z"),
                                                                        BoolArgumentType.getBool(context, "renderOnTop")
                                                                ))
                                                        )
                                                )
                                        )
                                )
                        )
                )
                .then(ClientCommandManager.literal("clearAll")
                        .executes(HologramAPICommand::clearAll))
        );
    }
    private static int clearAll(CommandContext<FabricClientCommandSource> context) {
        HologramAPI.clearAll();
        return 1;
    }
    private static int createHologram(CommandContext<FabricClientCommandSource> context, String text,
                                      double x, double y, double z, boolean renderOnTop) {
        try {
            Component comp = ComponentUtils.parseColorCodes(text);
            HologramAPI.create(comp, x, y, z).renderOnTop(renderOnTop);

            context.getSource().sendFeedback(
                    Component.literal("Created hologram: '")
                            .append(Component.literal(text).withStyle(ChatFormatting.AQUA))
                            .append("' at " + String.format("%.1f, %.1f, %.1f", x, y, z))
                            .withStyle(ChatFormatting.GREEN)
            );

            return 1;

        } catch (Exception e) {
            context.getSource().sendError(
                    Component.literal("Error creating hologram").withStyle(ChatFormatting.RED)
            );
            e.printStackTrace();
            return 0;
        }
    }

    private int clearAllHolograms(CommandContext<FabricClientCommandSource> context) {
        try {
            HologramAPI.clearAll();

            context.getSource().sendFeedback(
                    Component.literal("Cleared all holograms").withStyle(ChatFormatting.GREEN)
            );

            return 1;

        } catch (Exception e) {
            context.getSource().sendError(
                    Component.literal("Error clearing holograms").withStyle(ChatFormatting.RED)
            );
            e.printStackTrace();
            return 0;
        }
    }
}