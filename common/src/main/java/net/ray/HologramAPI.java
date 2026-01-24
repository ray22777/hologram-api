package net.ray;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;

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

    public static void render(PoseStack poseStack, MultiBufferSource buffer) {
        HologramRenderer.HologramManager.renderAll(poseStack, buffer);
    }
}