package net.ray.HologramAPI.forge;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ray.HologramAPI.HologramAPI;


@Mod.EventBusSubscriber(modid = "hologram_api", value = Dist.CLIENT)
public final class HologramAPIMod {

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            PoseStack poseStack = event.getPoseStack();
            var minecraft = Minecraft.getInstance();
            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
            HologramAPI.render(poseStack, bufferSource);

        }
//        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
//            PoseStack poseStack = event.getPoseStack();
//            var minecraft = Minecraft.getInstance();
//            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
//            HologramAPI.renderForce(poseStack, bufferSource);
//        } //use mixins
    }



    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        HologramAPI.update();
//            Component comp = Component.literal("Test").withStyle(ChatFormatting.AQUA);
//            HologramAPI.create(comp, 0,70,0).renderOnTop(true); //testing
    }
}