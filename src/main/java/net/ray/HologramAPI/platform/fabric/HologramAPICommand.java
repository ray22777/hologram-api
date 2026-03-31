package net.ray.HologramAPI.platform.fabric;
//? fabric {
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.ray.HologramAPI.ComponentUtils;
import net.ray.HologramAPI.Hologram;
import net.ray.HologramAPI.HologramAPI;

public class HologramAPICommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommands.literal("hologram")
                .then(ClientCommands.literal("create")
                        .then(ClientCommands.argument("text", StringArgumentType.string())
                                .then(ClientCommands.argument("x", DoubleArgumentType.doubleArg())
                                        .then(ClientCommands.argument("y", DoubleArgumentType.doubleArg())
                                                .then(ClientCommands.argument("z", DoubleArgumentType.doubleArg())
                                                        .executes(context -> createHologram(
                                                                context,
                                                                StringArgumentType.getString(context, "text"),
                                                                DoubleArgumentType.getDouble(context, "x"),
                                                                DoubleArgumentType.getDouble(context, "y"),
                                                                DoubleArgumentType.getDouble(context, "z"),
                                                                false
                                                        ))
                                                        .then(ClientCommands.argument("renderOnTop", BoolArgumentType.bool())
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
                .then(ClientCommands.literal("clearAll")
                        .executes(HologramAPICommand::clearAll))
                .then(ClientCommands.literal("test")
                        .executes(HologramAPICommand::test))
        );
    }
    private static int clearAll(CommandContext<FabricClientCommandSource> context) {
//        HologramAPI.clearAll();
        HologramAPI.clearTagged("command");
        return 1;
    }

    private static int test(CommandContext<FabricClientCommandSource> context) {
        Component component = Component.literal("Hello World!").withStyle(ChatFormatting.GREEN); //create a minecraft component
        Hologram hologram = HologramAPI.create(component, 0,110,0).shadow(true).scale(2).renderDistance(20).billboardMode(Hologram.BillboardMode.VERTICAL).renderOnTop(true); //creating the hologram
        return 1;
    }
    private static int createHologram(CommandContext<FabricClientCommandSource> context, String text,
                                      double x, double y, double z, boolean renderOnTop) {
        try {
            Component comp = ComponentUtils.parseColorCodes(text);
            HologramAPI.create(comp, x, y, z).renderOnTop(renderOnTop).shadow(true).tag("command");

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

}//?}
