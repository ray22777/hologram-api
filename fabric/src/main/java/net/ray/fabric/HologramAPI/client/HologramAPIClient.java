package net.ray.fabric.HologramAPI.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.ray.HologramAPI.Hologram;
import net.ray.HologramAPI.HologramAPI;
import net.ray.fabric.HologramAPI.HologramAPICommand;

public final class HologramAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            HologramAPI.render(context.matrixStack(), context.consumers(), context.tickDelta());
        });
        WorldRenderEvents.END.register(context -> {
            HologramAPI.renderForce(context.matrixStack(), context.consumers(), context.tickDelta());
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            HologramAPI.update();

//            Component comp = Component.literal("Test").withStyle(ChatFormatting.AQUA);
//            Hologram hologram = HologramAPI.create(comp, 0,80,0).lifetime(40).shadow(true).scale(2).renderDistance(20);
//            hologram.onUpdate(h -> {
//                h.y += 0.05f;
//            });
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            HologramAPICommand.register(dispatcher);
        });

    }
}
