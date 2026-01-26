package net.ray.HologramAPI;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;


import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import com.mojang.math.Axis;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class HologramRenderer {
    private static final Minecraft MC = Minecraft.getInstance();

    public static class HologramManager {
        private static final Map<Integer, Hologram> HOLOGRAMS = new HashMap<>();
        private static final List<Hologram> TO_ADD = new ArrayList<>();
        private static final List<Integer> TO_REMOVE = new ArrayList<>();
        private static boolean isUpdating = false;

        public static void addHologram(Hologram hologram) {
            if (isUpdating) {
                TO_ADD.add(hologram);
            } else {
                HOLOGRAMS.put(hologram.id, hologram);
            }
        }

        public static void removeHologram(int id) {
            if (isUpdating) {
                TO_REMOVE.add(id);
            } else {
                HOLOGRAMS.remove(id);
            }
        }

        public static void clearAll() {
            if (isUpdating) {
                TO_REMOVE.addAll(HOLOGRAMS.keySet());
            } else {
                HOLOGRAMS.clear();
            }
        }

        public static void updateAll() {
            isUpdating = true;

            try {
                processPendingRemovals();

                List<Hologram> hologramsToUpdate = new ArrayList<>(HOLOGRAMS.values());

                for (Hologram hologram : hologramsToUpdate) {
                    updateHologram(hologram);
                }

                processPendingRemovals();

                for (Hologram hologram : TO_ADD) {
                    HOLOGRAMS.put(hologram.id, hologram);
                }
                TO_ADD.clear();

            } finally {
                isUpdating = false;
            }
        }

        public static void renderAll(PoseStack poseStack, MultiBufferSource buffer) {
            if (MC.player == null || MC.level == null) return;

            List<Hologram> hologramsToRender = new ArrayList<>(HOLOGRAMS.values());

            for (Hologram hologram : hologramsToRender) {
                    renderHologram(hologram, poseStack, buffer);
            }
        }
        private static void updateHologram(Hologram hologram) {
            hologram.age++;

            if (hologram.lifetime > 0 && hologram.age >= hologram.lifetime) {
                removeHologram(hologram.id);
                return;
            }

            if (hologram.trackedEntityId != null && MC.level != null) {
                var entity = MC.level.getEntity(hologram.trackedEntityId);
                if (entity != null) {
                    hologram.x = entity.getX() + hologram.offsetFromEntity.x;
                    hologram.y = entity.getY() + hologram.offsetFromEntity.y;
                    hologram.z = entity.getZ() + hologram.offsetFromEntity.z;
                }
            }

            if (hologram.updateCallback != null) {
                hologram.updateCallback.accept(hologram);
            }
        }

        private static void processPendingRemovals() {
            for (int id : TO_REMOVE) {
                HOLOGRAMS.remove(id);
            }
            TO_REMOVE.clear();
        }
    }

    public static void renderHologram(Hologram hologram, PoseStack poseStack, MultiBufferSource buffer) {
        if (!hologram.visible || hologram.scale <= 0.001f) return;
        if (hologram.component == null) return;

        try {
            var camera = MC.gameRenderer.getMainCamera();
            if (camera == null) return;

            Vector3f cameraPos = camera.getPosition().toVector3f();

            float dx = (float)hologram.x - cameraPos.x();
            float dy = (float)hologram.y - cameraPos.y();
            float dz = (float)hologram.z - cameraPos.z();
            float distance = (float)Math.sqrt(dx*dx + dy*dy + dz*dz);
            Vector3f cameraForward = camera.getLookVector();
            Vector3f toHologram = new Vector3f(dx, dy, dz).normalize();
            float dot = cameraForward.dot(toHologram);
            if (dot < 0 && distance > 5.0f) {
                return;
            }
            float screenSize = (hologram.scale / 40f) / distance;
            if (screenSize < 0.001f) {
                return;
            }
            if (!hologram.alwaysRender && distance > hologram.renderDistance) {
                return;
            }



            poseStack.pushPose();

            poseStack.translate(
                    hologram.x - cameraPos.x(),
                    hologram.y - cameraPos.y(),
                    hologram.z - cameraPos.z()
            );

            if (hologram.renderCallback != null) {
                hologram.renderCallback.accept(hologram);
            }

            applyBillboard(poseStack, camera, hologram.billboardMode);

            float dynamicScale = hologram.scale / 40f;
            poseStack.scale(-dynamicScale, -dynamicScale, dynamicScale);

            renderComponent(hologram, poseStack, buffer, distance);

            poseStack.popPose();

        } catch (Exception e) {
            System.err.println("Error rendering hologram: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void applyBillboard(PoseStack poseStack, net.minecraft.client.Camera camera,
                                       Hologram.BillboardMode mode) {
        switch (mode) {
            case CENTER:
                poseStack.mulPose(Axis.YP.rotationDegrees(-camera.getYRot()));
                poseStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
                break;
            case VERTICAL:
                poseStack.mulPose(Axis.YP.rotationDegrees(-camera.getYRot()));
                break;
            case HORIZONTAL:
                poseStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
                break;
            case FIXED:
                break;
        }
    }

    private static void renderComponent(Hologram hologram, PoseStack poseStack,
                                        MultiBufferSource buffer, float distance) {
        Font font = MC.font;
        if (font == null) return;

        int lineWidth = font.width(hologram.component);
        float xOffset = getXOffset(lineWidth, hologram.alignment);
        int alphaByte = (int)(hologram.alpha * 255);
        int finalColor = (alphaByte << 24) |  0x00FFFFFF;
        poseStack.pushPose();
        Font.DisplayMode displayMode;
        if(hologram.renderOnTop){
            displayMode = Font.DisplayMode.SEE_THROUGH;
        }
        else{
            displayMode = Font.DisplayMode.NORMAL;
        }
        if (hologram.shadow) {
            poseStack.pushPose();
            poseStack.translate(0, 0, +0.1f);
            Component shadowComponent = ComponentUtils.darkenComponent(hologram.component, 0.70f);

            int shadowAlpha = (int)(hologram.alpha * 0.7f * 255);
            int shadowColor = (shadowAlpha << 24) | (0x000000 & 0x00FFFFFF);

            font.drawInBatch(
                    shadowComponent,
                    xOffset + 1, 1,
                    shadowColor,
                    false,
                    poseStack.last().pose(),
                    buffer,
                    displayMode,
                    0,
                    15728880
            );
            poseStack.popPose();
        }

        font.drawInBatch(
                hologram.component,
                xOffset, 0,
                finalColor,
                false,
                poseStack.last().pose(),
                buffer,
                displayMode,
                0,
                15728880
        );
        poseStack.popPose();
    }


    private static float getXOffset(int lineWidth, Hologram.TextAlignment alignment) {
        switch (alignment) {
            case CENTER: return -lineWidth / 2f;
            case LEFT: return -lineWidth;
            case RIGHT: return 0;
            default: return -lineWidth / 2f;
        }
    }
}