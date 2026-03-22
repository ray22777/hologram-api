package net.ray.HologramAPI;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;

import java.util.List;

public class HologramAPI {
    private HologramAPI() {}

    public static Hologram create(Component component, double x, double y, double z) {
        Hologram hologram = new Hologram(component, x, y, z);
        hologram.world = Minecraft.getInstance().level;
        HologramRenderer.HologramManager.addHologram(hologram);
        return hologram;
    }

    public static void remove(int id) {
        HologramRenderer.HologramManager.removeHologram(id);
    }
    public static void remove(Hologram hologram) {
        HologramRenderer.HologramManager.removeHologram(hologram.id);
    }
    /**
     * Do NOT use as this can break other mods as it removes their holograms too.
     * Use clearTagged() and set your own hologram tags instead.
     */
    @Deprecated()
    public static void clearAll() {
        HologramRenderer.HologramManager.clearAll();
    }

    public static void clearTagged(String tag) {
        HologramRenderer.HologramManager.clearTagged(tag);
    }

    public static void update() {
        HologramRenderer.HologramManager.updateAll();
    }

    public static List<Hologram> getList() {
        return HologramRenderer.HologramManager.getHologramList();
    }

    public static Hologram get(int id) {
        return HologramRenderer.HologramManager.HOLOGRAMS.get(id);
    }

    public static void render(PoseStack poseStack, MultiBufferSource buffer, float tickDelta) {
        HologramRenderer.HologramManager.renderAll(poseStack, buffer, tickDelta);
    }
//    public static void renderForce(PoseStack poseStack, MultiBufferSource buffer) {
//        HologramRenderer.HologramManager.renderAllForce(poseStack, buffer);
//    }
}