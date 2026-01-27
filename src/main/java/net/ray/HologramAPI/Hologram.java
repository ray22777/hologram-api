package net.ray.HologramAPI;

import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import java.util.function.Consumer;

public class Hologram {
    private static int NEXT_ID = 1;

    public final int id;
    public Component component;
    public double x, y, z;
    public float scale = 1.0f;
//    public int color = 0xFFFFFF; //use textcomponents instead
    public boolean shadow = true;
    public boolean visible = true;
    public boolean alwaysRender = false;
    public float renderDistance = 50.0f;
    public int lifetime = 0; // 0 = infinite
    public int age = 0;
    public Integer trackedEntityId = null;
    public Vec3 offsetFromEntity = Vec3.ZERO;
    public BillboardMode billboardMode = BillboardMode.CENTER;
    public TextAlignment alignment = TextAlignment.CENTER;
    public Consumer<Hologram> updateCallback = null;
    public Consumer<Hologram> renderCallback = null;
    public boolean renderOnTop = false;

    public enum BillboardMode {
        CENTER, VERTICAL, HORIZONTAL, FIXED
    }

    public enum TextAlignment {
        LEFT, CENTER, RIGHT
    }

    public Hologram(Component component, double x, double y, double z) {
        this.id = NEXT_ID++;
        this.component = component;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Hologram(String text, double x, double y, double z) {
        this(Component.literal(text), x, y, z);
    }

    public Hologram component(Component component) {
        this.component = component;
        return this;
    }

    public Hologram text(String text) {
        this.component = Component.literal(text);
        return this;
    }

    public Hologram scale(float scale) {
        this.scale = scale;
        return this;
    }

//    public Hologram color(int color) {
//        this.color = color;
//        return this;
//    }

    public Hologram shadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }
    public Hologram renderOnTop(boolean renderOnTop) {
        this.renderOnTop = renderOnTop;
        return this;
    }

    public Hologram visible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public Hologram alwaysRender(boolean alwaysRender) {
        this.alwaysRender = alwaysRender;
        return this;
    }

    public Hologram renderDistance(float distance) {
        this.renderDistance = distance;
        return this;
    }

    public Hologram lifetime(int ticks) {
        this.lifetime = ticks;
        return this;
    }

    public Hologram trackEntity(int entityId, Vec3 offset) {
        this.trackedEntityId = entityId;
        this.offsetFromEntity = offset;
        return this;
    }

    public Hologram billboardMode(BillboardMode mode) {
        this.billboardMode = mode;
        return this;
    }

    public Hologram alignment(TextAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public Hologram onUpdate(Consumer<Hologram> callback) {
        this.updateCallback = callback;
        return this;
    }

    public Hologram onRender(Consumer<Hologram> callback) {
        this.renderCallback = callback;
        return this;
    }
    public float alpha = 1.0f;

    public Hologram setAlpha(float alpha) {
        this.alpha = Math.max(0, Math.min(1, alpha));
        return this;
    }

    public Hologram setAlpha(int alpha) {
        this.alpha = Math.max(0, Math.min(255, alpha)) / 255f;
        return this;
    }

    public Hologram fade(float progress) {
        this.alpha = Math.max(0, Math.min(1, this.alpha * progress));
        return this;
    }
    public void remove() {
        HologramRenderer.HologramManager.removeHologram(this.id);
    }

    public void update() {
        HologramRenderer.HologramManager.updateAll();
    }
}