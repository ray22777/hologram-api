package net.ray.neoforge.HologramAPI;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.ray.HologramAPI.ComponentUtils;
import net.ray.HologramAPI.HologramAPI;
@EventBusSubscriber(modid = "hologram_api", bus = EventBusSubscriber.Bus.GAME)
public class HologramAPICommand {

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("hologram")
                .then(Commands.literal("create")
                        .then(Commands.argument("text", StringArgumentType.string())
                                .then(Commands.argument("x", DoubleArgumentType.doubleArg())
                                        .then(Commands.argument("y", DoubleArgumentType.doubleArg())
                                                .then(Commands.argument("z", DoubleArgumentType.doubleArg())
                                                        .executes(context -> createHologram(
                                                                context,
                                                                StringArgumentType.getString(context, "text"),
                                                                DoubleArgumentType.getDouble(context, "x"),
                                                                DoubleArgumentType.getDouble(context, "y"),
                                                                DoubleArgumentType.getDouble(context, "z"),
                                                                false
                                                        ))
                                                        .then(Commands.argument("renderOnTop", BoolArgumentType.bool())
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
                .then(Commands.literal("clearAll")
                        .executes(HologramAPICommand::clearAll))
        );
    }

    private static int clearAll(CommandContext<CommandSourceStack> context) {
        try {
            HologramAPI.clearAll();

            context.getSource().sendSuccess(
                    () -> Component.literal("Cleared all holograms").withStyle(ChatFormatting.GREEN),
                    false
            );

            return 1;

        } catch (Exception e) {
            context.getSource().sendFailure(
                    Component.literal("Error clearing holograms").withStyle(ChatFormatting.RED)
            );
            e.printStackTrace();
            return 0;
        }
    }

    private static int createHologram(CommandContext<CommandSourceStack> context, String text,
                                      double x, double y, double z, boolean renderOnTop) {
        try {
            Component comp = ComponentUtils.parseColorCodes(text);
            HologramAPI.create(comp, x, y, z).renderOnTop(renderOnTop);

            context.getSource().sendSuccess(
                    () -> Component.literal("Created hologram: '")
                            .append(Component.literal(text).withStyle(ChatFormatting.AQUA))
                            .append("' at " + String.format("%.1f, %.1f, %.1f", x, y, z))
                            .withStyle(ChatFormatting.GREEN),
                    false
            );

            return 1;

        } catch (Exception e) {
            context.getSource().sendFailure(
                    Component.literal("Error creating hologram").withStyle(ChatFormatting.RED)
            );
            e.printStackTrace();
            return 0;
        }
    }
}