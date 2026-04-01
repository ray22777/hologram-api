package net.ray.HologramAPI;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;


import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import net.minecraft.world.level.LightLayer;
import org.joml.Matrix4f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.client.gui.Font.DisplayMode.*;
//? if >=26.1 {

/*import net.minecraft.util.LightCoordsUtil;
import net.minecraft.client.renderer.Lightmap;
*///?}else{
import net.minecraft.client.renderer.LightTexture;
//?}
//? if >=1.21.11 {
/*import net.minecraft.client.renderer.rendertype.RenderTypes;
*///?}else {
import net.minecraft.client.renderer.RenderType;
//?}
public class HologramRenderer {
    private static final Minecraft MC = Minecraft.getInstance();

    public static class HologramManager {
        public static final Map<Integer, Hologram> HOLOGRAMS = new HashMap<>();
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
        public static void clearTagged(String tag) {
            if (isUpdating) {
                HOLOGRAMS.values().stream()
                        .filter(h -> tag.equals(h.tag))
                        .map(h -> h.id)
                        .forEach(TO_REMOVE::add);
            } else {
                HOLOGRAMS.values().removeIf(h -> tag.equals(h.tag));
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

        public static void handleTrackEntities(Hologram hologram, float tickDelta) {
            if (hologram.trackedEntityId != null && MC.level != null) {
                var entity = MC.level.getEntity(hologram.trackedEntityId);
                if (entity != null) {
                    hologram.x = entity.getPosition(tickDelta).x + hologram.offsetFromEntity.x;
                    hologram.y = entity.getPosition(tickDelta).y + hologram.offsetFromEntity.y;
                    hologram.z = entity.getPosition(tickDelta).z + hologram.offsetFromEntity.z;
                }
            }
        }

        public static List<Hologram> getHologramList() {
            List<Hologram> hologramList = new ArrayList<>(HOLOGRAMS.values());
            return hologramList;
        }

        public static void renderAll(PoseStack poseStack, MultiBufferSource buffer, float tickDelta,boolean legacySeethrough,boolean seethroughRenderer) {
            if (MC.player == null || MC.level == null) return;
            ClientLevel world = MC.level;
            List<Hologram> hologramsToRender = new ArrayList<>(HOLOGRAMS.values());
            Camera camera = MC.gameRenderer.getMainCamera();
            if (camera == null) return;


            for (Hologram hologram : hologramsToRender) {
                if (hologram.world != world) continue;
                handleTrackEntities(hologram, tickDelta);
                hologram.tickDelta = tickDelta;
				if(legacySeethrough){
					if(seethroughRenderer && hologram.renderOnTop){
						renderHologram(hologram, poseStack, buffer, tickDelta,true);
					}
					else if(!seethroughRenderer && !hologram.renderOnTop){
						renderHologram(hologram, poseStack, buffer, tickDelta,false);
					}
				}
				else{
					renderHologram(hologram, poseStack, buffer, tickDelta,false);
				}

            }
        }

        private static void updateHologram(Hologram hologram) {
            hologram.age++;

            if (hologram.lifetime > 0 && hologram.age >= hologram.lifetime) {
                removeHologram(hologram.id);
                return;
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

    public static void renderHologram(Hologram hologram, PoseStack poseStack, MultiBufferSource buffer, float tickDelta,boolean seethrough) {

        if (!hologram.visible) return;
        if (hologram.component == null) return;

        try {
            var camera = MC.gameRenderer.getMainCamera();
            if (camera == null) return;
			//? if >=1.21.11 {
            /*Vector3f cameraPos = camera.position().toVector3f();
			*///?} else {
			Vector3f cameraPos = camera.getPosition().toVector3f();
			//?}
            float dx = (float)hologram.x - cameraPos.x();
            float dy = (float)hologram.y - cameraPos.y();
            float dz = (float)hologram.z - cameraPos.z();
            float distance = (float)Math.sqrt(dx*dx + dy*dy + dz*dz);
			//? if >=1.21.11 {
			/*Vector3f cameraForward = new Vector3f();
            camera.forwardVector().get(cameraForward);
			*///?} else {
			Vector3f cameraForward = camera.getLookVector();
			//?}

            Vector3f toHologram = new Vector3f(dx, dy, dz).normalize();
            float dot = cameraForward.dot(toHologram);
            if (dot < 0 && distance > 5.0f) {
                return;
            }
            float screenSize = (hologram.scale / 40f) / distance;
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
            if (hologram.renderCallbackPartialTick != null) {
                hologram.renderCallbackPartialTick.accept(hologram, tickDelta);
            }
			float holoScale = hologram.scale * 0.025f;
			poseStack.scale(-holoScale, -holoScale, -holoScale);
            applyBillboard(poseStack, camera, hologram.billboardMode);
			int lightLevel;
			if(hologram.lightLevel == null){
				//? if >=26.1 {
					/*lightLevel = LightCoordsUtil.pack(
							MC.level.getLightEngine().getRawBrightness(
									new BlockPos((int)hologram.x, (int)hologram.y, (int)hologram.z), 0
							) << 4, 7
					);
			*///?}else if 1.21.11 {
				/*lightLevel = LightTexture.lightCoordsWithEmission(
						MC.level.getLightEngine().getRawBrightness(
								new BlockPos((int)hologram.x, (int)hologram.y, (int)hologram.z), 0
						) << 4, 7 //minimum light level is 7
				);
			*///?} else {
				lightLevel = LightTexture.pack(
						MC.level.getBrightness(LightLayer.BLOCK, new BlockPos((int)hologram.x, (int)hologram.y, (int)hologram.z)),
						Math.max(7, MC.level.getBrightness(LightLayer.SKY, new BlockPos((int)hologram.x, (int)hologram.y, (int)hologram.z)))
				);
				//?}

			}
			else{
				lightLevel = hologram.lightLevel;
			}
            renderComponent(hologram, poseStack, buffer,lightLevel,seethrough,tickDelta);
            poseStack.popPose();

        } catch (Exception e) {
            HologramModInitializer.getLogger().warn("Error rendering hologram: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void renderComponent(Hologram hologram, PoseStack poseStack,
                                        MultiBufferSource buffer,int lightLevel,boolean seethrough,float tickDelta) {
        Font font = hologram.font;
        if (font == null) return;

        int alphaByte = (int)(hologram.alpha * 255);
        int finalColor = (alphaByte << 24) | 0x00FFFFFF;
        Font.DisplayMode displayMode = hologram.renderOnTop
                ? Font.DisplayMode.SEE_THROUGH
                : Font.DisplayMode.POLYGON_OFFSET;

        List<FormattedCharSequence> rawLines = font.split(hologram.component, Integer.MAX_VALUE);
        List<FormattedCharSequence> lines = new ArrayList<>();
        int maxWidth = 0;
        for (FormattedCharSequence line : rawLines) {
            int w = font.width(line);
            maxWidth = Math.max(maxWidth, w);
            lines.add(line);
        }

        int lineHeight = 9 + 1;
        int totalHeight = lines.size() * lineHeight;

        Matrix4f pose = poseStack.last().pose();
//		pose.rotate((float)Math.PI, 0.0F, 1.0F, 0.0F);
		pose.translate(1.0F - maxWidth / 2.0F, -totalHeight / 2.0F, 0.0F);
        if (hologram.background) {
            int bgAlpha = (int)(hologram.alpha * ((hologram.backgroundColor >> 24) & 0xFF));
            int bgColor = (bgAlpha << 24) | (hologram.backgroundColor & 0x00FFFFFF);
			//? if >=1.21.11 {
			/*VertexConsumer vertexConsumer = buffer.getBuffer(hologram.renderOnTop ? RenderTypes.textBackgroundSeeThrough() : RenderTypes.textBackground());
			*///?} else {
			VertexConsumer vertexConsumer = buffer.getBuffer(hologram.renderOnTop ? RenderType.textBackgroundSeeThrough() : RenderType.textBackground());
			//?}
			//? if <=1.20.1 {
			vertexConsumer.vertex(pose, -1.0F, -1.0F, 0.0F).color(bgColor).uv2(15728880).endVertex();
			vertexConsumer.vertex(pose, -1.0F, totalHeight, 0.0F).color(bgColor).uv2(15728880).endVertex();
			vertexConsumer.vertex(pose, maxWidth, totalHeight, 0.0F).color(bgColor).uv2(15728880).endVertex();
			vertexConsumer.vertex(pose, maxWidth, -1.0F, 0.0F).color(bgColor).uv2(15728880).endVertex();
			//?}else{
			
			/*vertexConsumer.addVertex(pose, -1.0F, -1.0F, 0.0F).setColor(bgColor).setLight(15728880);
            vertexConsumer.addVertex(pose, -1.0F, totalHeight, 0.0F).setColor(bgColor).setLight(15728880);
            vertexConsumer.addVertex(pose, maxWidth, totalHeight, 0.0F).setColor(bgColor).setLight(15728880);
            vertexConsumer.addVertex(pose, maxWidth, -1.0F, 0.0F).setColor(bgColor).setLight(15728880);
			 *///?}
        }

        float y = 0;
        for (FormattedCharSequence line : lines) {
            int lineWidth = font.width(line);


            float x = switch (hologram.alignment) {
                case LEFT -> 0;
                case RIGHT -> maxWidth - lineWidth;
                case CENTER -> maxWidth / 2f - lineWidth / 2f;
            };
			if(seethrough){
				renderComponentSeeThrough(font,poseStack,hologram,buffer,lightLevel,x,y,tickDelta);
			}
			else{
				font.drawInBatch(line, x, y, finalColor, hologram .shadow, pose, buffer, displayMode, 0, lightLevel); //unfortunately shadow rendering with see through rendering is kinda broken in versions <1.21.10, resulting in z fighting.

			}
			y += lineHeight;
        }
    }
	private static void renderComponentSeeThrough(Font font,PoseStack poseStack,Hologram hologram,MultiBufferSource buffer,int lightLevel,float x, float y,float tickDelta) {
		//TODO:fix posestack and text location when using dynamic fov
		//     fix shadow color as well to match vanilla mc

		if (font == null) return;
		int alphaByte = (int)(hologram.alpha * 255);
		int finalColor = (alphaByte << 24) |  0x00FFFFFF;
		poseStack.pushPose();
		if (hologram.shadow) {
			poseStack.pushPose();
			poseStack.translate(0, 0, -0.1f);
			Component shadowComponent = ComponentUtils.darkenComponent(hologram.component, 0.70f);

			int shadowAlpha = (int)(hologram.alpha * 0.7f * 255);
			int shadowColor = (shadowAlpha << 24) | (0x000000 & 0x00FFFFFF);

			font.drawInBatch(
					shadowComponent,
					x + 1, y+1,
					shadowColor,
					false,
					poseStack.last().pose(),
					buffer,
					POLYGON_OFFSET,
					0,
					lightLevel
			);
			poseStack.popPose();
		}

		font.drawInBatch(
				hologram.component,
				x, y,
				finalColor,
				false,
				poseStack.last().pose(),
				buffer,
				POLYGON_OFFSET,
				0,
				lightLevel
		);
		poseStack.popPose();
	}
    private static void applyBillboard(PoseStack poseStack, net.minecraft.client.Camera camera,
                                       Hologram.BillboardMode mode) {
        switch (mode) {
			//? if >=1.21.11 {
            /*case CENTER:
                poseStack.mulPose(Axis.YP.rotationDegrees(-camera.yRot()));
                poseStack.mulPose(Axis.XP.rotationDegrees(camera.xRot()));
                break;
            case VERTICAL:
                poseStack.mulPose(Axis.YP.rotationDegrees(-camera.yRot()));
                break;
            case HORIZONTAL:
                poseStack.mulPose(Axis.XP.rotationDegrees(camera.xRot()));
                break;
            case FIXED:
                break;
			*///?} else {
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
			//?}
        }
    }

}
