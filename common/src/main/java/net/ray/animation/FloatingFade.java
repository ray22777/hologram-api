package net.ray.animation;

import net.ray.Hologram;

public class FloatingFade {
//    public static void setFloatingFade(Hologram holo) {
//        double y = holo.y;
//        holo.onUpdate(hologram -> {
//            float progress = (float)hologram.age / hologram.lifetime;
//            float floatHeight = 1.0f;
//            float floatProgress = easeOutCubic(progress);
//            hologram.y = y + floatHeight * floatProgress;
//            float fadeStart = 0.5f;
//            float fadeProgress = Math.max(0, (progress - fadeStart) / (1.0f - fadeStart));
//            float alpha = 1.0f - fadeProgress;
//            int alphaByte = (int)(alpha * 255);
//            hologram.color = (alphaByte << 24) | (hologram.color & 0xFFFFFF);
//            float pulse = 1.0f + (float)Math.sin(hologram.age * 0.2f) * 0.05f;
//            hologram.scale = holo.scale * pulse;
//        });
//    }
//    private static float easeOutCubic(float x) {
//        return 1.0f - (float)Math.pow(1.0f - x, 3.0f);
//    }
}
