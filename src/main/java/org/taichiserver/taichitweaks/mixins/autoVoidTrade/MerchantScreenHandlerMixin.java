package org.taichiserver.taichitweaks.mixins.autoVoidTrade;

import net.minecraft.screen.MerchantScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.taichiserver.taichitweaks.features.AutoVoidTrade;

@Mixin(MerchantScreenHandler.class)
public abstract class MerchantScreenHandlerMixin {

    @Inject( method = "onClosed", at = @At("HEAD"))
    private void onClosed(CallbackInfo ci) {
        AutoVoidTrade.isOpeningScreen = false;
    }
}
