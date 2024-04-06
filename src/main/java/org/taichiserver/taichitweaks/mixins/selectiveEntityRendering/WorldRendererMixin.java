package org.taichiserver.taichitweaks.mixins.selectiveEntityRendering;

import fi.dy.masa.malilib.util.restrictions.UsageRestriction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.taichiserver.taichitweaks.config.Configs;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(at = @At("HEAD"), method = "renderEntity", cancellable = true)
    private void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if(!Configs.Generic.SELECTIVE_ENTITY_RENDERING.getBooleanValue()) return;

        UsageRestriction.ListType type = (UsageRestriction.ListType) Configs.Generic.SELECTIVE_ENTITY_RENDERING_LIST_TYPE.getOptionListValue();
        if(type == UsageRestriction.ListType.NONE) return;

        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        assert player != null;

        String targetEntity = (entity.getType().toString()).replace("entity.minecraft.", "");

        if(type == UsageRestriction.ListType.BLACKLIST) {
            if(Configs.Generic.SELECTIVE_ENTITY_RENDERING_BLACKLIST.getStrings().contains(targetEntity)) {
                ci.cancel();
            }
        } else if (type == UsageRestriction.ListType.WHITELIST) {
            if(!Configs.Generic.SELECTIVE_ENTITY_RENDERING_WHITELIST.getStrings().contains(targetEntity)) {
                ci.cancel();
            }
        }
    }
}
