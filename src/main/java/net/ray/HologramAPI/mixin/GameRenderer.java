package net.ray.HologramAPI.mixin;
//?if forge && 1.20.1{

import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.ray.HologramAPI.HologramAPI;
//?}
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import org.spongepowered.asm.mixin.Mixin;


@IfModLoaded("forge")
@Mixin(net.minecraft.client.renderer.GameRenderer.class)
public class GameRenderer {
	//?if forge && 1.20.1{
	
	@Inject(
			method = "renderLevel",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lorg/joml/Matrix4f;)V",
					shift = At.Shift.AFTER
			)
	)
	private void hologramAPI_renderEnd(float partialTick, long nanoTime,
									   PoseStack poseStack, CallbackInfo ci) {

		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null || mc.player == null || mc.isPaused()) return;
		MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
		HologramAPI.renderForce(poseStack, bufferSource,partialTick);
	}//?}
}
