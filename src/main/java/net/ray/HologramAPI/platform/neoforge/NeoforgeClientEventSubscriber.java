package net.ray.HologramAPI.platform.neoforge;

//? neoforge {

/*import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.ray.HologramAPI.HologramAPI;
import net.ray.HologramAPI.HologramMod;

@EventBusSubscriber(modid = HologramMod.MOD_ID, value = Dist.CLIENT)
public class NeoforgeClientEventSubscriber {
	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		HologramMod.onInitializeClient();
	}

	@SubscribeEvent
	public static void onRenderLevelStage(RenderLevelStageEvent event) {
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
			PoseStack poseStack = event.getPoseStack();
			var minecraft = Minecraft.getInstance();
			MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
			HologramAPI.render(poseStack, bufferSource ,event.getPartialTick().getGameTimeDeltaPartialTick(false),true);

		}
		if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL){
			PoseStack poseStack = event.getPoseStack();
			var minecraft = Minecraft.getInstance();
			MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
			HologramAPI.renderForce(poseStack, bufferSource,event.getPartialTick().getGameTimeDeltaPartialTick(false));
		}
	}


	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Post event) {
		HologramAPI.update();
//            Component comp = Component.literal("Test").withStyle(ChatFormatting.AQUA);
//            HologramAPI.create(comp, 0,70,0).renderOnTop(true); //testing
	}

	}

*///?}
