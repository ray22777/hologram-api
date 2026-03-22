package net.ray.neoforge.HologramAPI;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.ray.HologramAPI.HologramAPI;

@EventBusSubscriber(modid = "hologram_api", value = Dist.CLIENT)
public final class HologramAPIMod {

    @SubscribeEvent
    public static void onRenderAfterEntities(RenderLevelStageEvent.AfterEntities event) {
        Minecraft mc = Minecraft.getInstance();
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
        float tickDelta = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
        HologramAPI.render(new PoseStack(), bufferSource, tickDelta);
    }


    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        HologramAPI.update();
    }
}