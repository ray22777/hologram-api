package net.ray.HologramAPI;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;

import java.util.List;

public class HologramAPI {
    private HologramAPI() {}

    public static Hologram create(Component component, double x, double y, double z) {
        Hologram hologram = new Hologram(component, x, y, z);
        HologramRenderer.HologramManager.addHologram(hologram);
        return hologram;
    }

    public static void remove(int id) {
        HologramRenderer.HologramManager.removeHologram(id);
    }

    public static void clearAll() {
        HologramRenderer.HologramManager.clearAll();
    }

    public static void update() {
        HologramRenderer.HologramManager.updateAll();
    }

    public static List<Hologram> getList() {
        return HologramRenderer.HologramManager.getHologramList();
    }


    public static void render(PoseStack poseStack, MultiBufferSource buffer, float tickDelta) {
        HologramRenderer.HologramManager.renderAll(poseStack, buffer, tickDelta);
    }
    public static void renderForce(PoseStack poseStack, MultiBufferSource buffer, float tickDelta) {
        Minecraft mc = Minecraft.getInstance();
//        poseStack.last().pose().mul(mc.gameRenderer.getProjectionMatrix(tickDelta)); //weird bug when using fov effects > 0, TODO: fix it
        HologramRenderer.HologramManager.renderAllForce(poseStack, buffer,tickDelta);
    }
}