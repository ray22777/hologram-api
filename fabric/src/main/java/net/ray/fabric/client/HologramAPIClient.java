package net.ray.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.ray.Hologram;
import net.ray.HologramAPI;
import net.ray.animation.FloatingFade;

public final class HologramAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            HologramAPI.render(context.matrixStack(), context.consumers());
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            HologramAPI.update();
        });
    }
}
