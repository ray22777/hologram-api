package net.ray.HologramAPI.platform.fabric;

//? fabric {

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//? if >=26.1{
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
//?}else if >=1.21.10{
/*import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
*///? }else{
/*import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
*///?}


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.ray.HologramAPI.HologramAPI;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		//? if >=26.1{
		LevelRenderEvents.AFTER_TRANSLUCENT_TERRAIN.register(context -> {
			float pt = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
			HologramAPI.render(context.poseStack(), context.bufferSource(),pt);
		});
		//?}else if >=1.21.10{
		/*WorldRenderEvents.AFTER_ENTITIES.register(context -> {
			float pt = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
			HologramAPI.render(context.matrices(), context.consumers(),pt);
		});
		*///?} else{
			/*WorldRenderEvents.END.register(context -> {
				HologramAPI.renderForce(context.matrixStack(), context.consumers(),context.tickCounter().getGameTimeDeltaPartialTick(false),true);
			});
			WorldRenderEvents.AFTER_ENTITIES.register(context -> {
				HologramAPI.render(context.matrixStack(), context.consumers(),context.tickCounter().getGameTimeDeltaPartialTick(false),true);
			});
		*///?}
//        WorldRenderEvents.END_MAIN.register((context) -> {
//            HologramAPI.renderForce(context.matrices(), context.consumers());
//        });
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
//?}
