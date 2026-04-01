package net.ray.HologramAPI.platform.forge;

//? forge {

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.ray.HologramAPI.HologramAPI;
import net.ray.HologramAPI.HologramMod;

@Mod.EventBusSubscriber(modid = HologramMod.MOD_ID, value = Dist.CLIENT)
public class ForgeClientEventSubscriber {

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
			HologramAPI.render(poseStack, bufferSource ,event.getPartialTick(),true);


		}
//        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
//            PoseStack poseStack = event.getPoseStack();
//            var minecraft = Minecraft.getInstance();
//            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
//			HologramAPI.renderForce(poseStack, bufferSource,event.getPartialTick());
//        } //use mixins
	}



	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		HologramAPI.update();
//            Component comp = Component.literal("Test").withStyle(ChatFormatting.AQUA);
//            HologramAPI.create(comp, 0,70,0).renderOnTop(true); //testing
	}
	}

//?}
