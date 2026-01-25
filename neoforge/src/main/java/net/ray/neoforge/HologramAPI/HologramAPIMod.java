package net.ray.neoforge.HologramAPI;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.common.EventBusSubscriber;
import net.ray.HologramAPI.HologramAPI;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;


@EventBusSubscriber(modid = "hologram_api", value = Dist.CLIENT)
public final class HologramAPIMod {

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            PoseStack poseStack = event.getPoseStack();
            var minecraft = Minecraft.getInstance();
            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
            HologramAPI.render(poseStack, bufferSource);

        }
        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL){
            PoseStack poseStack = event.getPoseStack();
            var minecraft = Minecraft.getInstance();
            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
            HologramAPI.renderForce(poseStack, bufferSource);
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        HologramAPI.update();
//            Component comp = Component.literal("Test").withStyle(ChatFormatting.AQUA);
//            HologramAPI.create(comp, 0,70,0).renderOnTop(true); //testing
    }
}