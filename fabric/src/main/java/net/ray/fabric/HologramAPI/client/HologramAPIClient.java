package net.ray.fabric.HologramAPI.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.ray.HologramAPI.HologramAPI;
import net.ray.fabric.HologramAPI.HologramAPICommand;

public final class HologramAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            HologramAPI.render(context.matrices(), context.consumers(),context.gameRenderer().getMainCamera().getPartialTickTime());
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            HologramAPI.update();
//            Component comp = Component.literal("Test").withStyle(ChatFormatting.AQUA);
//            HologramAPI.create(comp, 0,70,0).renderOnTop(true); //testing
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            HologramAPICommand.register(dispatcher);
        });

    }
}
