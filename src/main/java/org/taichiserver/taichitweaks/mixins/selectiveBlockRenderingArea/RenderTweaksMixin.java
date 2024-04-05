package org.taichiserver.taichitweaks.mixins.selectiveBlockRenderingArea;

import fi.dy.masa.tweakeroo.tweaks.RenderTweaks;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.taichiserver.taichitweaks.config.Configs;
import org.taichiserver.taichitweaks.features.SelectiveBlockRenderingArea;

@Mixin(RenderTweaks.class)
public class RenderTweaksMixin {
    @Inject(method = "Lfi/dy/masa/tweakeroo/tweaks/RenderTweaks;isPositionValidForRendering(Lnet/minecraft/util/math/BlockPos;)Z", at = @At("RETURN"), cancellable = true)
    private static void isPositionValidForRendering(BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        if (!Configs.Generic.SELECTIVE_BLOCKS_RENDERING_AREA.getBooleanValue()) return;
        //if (!cir.getReturnValue()) return;
        cir.setReturnValue(!SelectiveBlockRenderingArea.isValidPos(pos));
        //cir.setReturnValue(false);
    }
}
