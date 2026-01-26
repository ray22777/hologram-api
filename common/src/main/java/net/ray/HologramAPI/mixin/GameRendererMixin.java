//package net.ray.HologramAPI.mixin;
//
//import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
//import com.llamalad7.mixinextras.sugar.Local;
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraft.client.Camera;
//import net.minecraft.client.DeltaTracker;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.GameRenderer;
//import net.minecraft.client.renderer.LevelRenderer;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.world.phys.Vec3;
//import net.ray.HologramAPI.HologramAPI;
//import org.joml.Matrix3dStack;
//import org.joml.Matrix4f;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Coerce;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(LevelRenderer.class)
//public abstract class GameRendererMixin {
//
//    @Inject(
//            method = "renderLevel",
//            at = @At("TAIL")
//    )
//    private void afterWorldRender(CallbackInfo ci) {
//        Minecraft minecraft = Minecraft.getInstance();
//        PoseStack poseStack = new PoseStack();
//        Matrix4f modelViewMatrix = RenderSystem.getModelViewMatrix();
//        poseStack.last().pose().set(modelViewMatrix);
//        MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
//
//        HologramAPI.renderForce(poseStack, bufferSource);
//    }
//}