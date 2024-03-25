package org.taichiserver.taichitweaks.mixins.autoVoidTrade;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.util.ModIds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import org.taichiserver.taichitweaks.features.AutoVoidTrade;

@Restriction(require = @Condition(ModIds.itemscroller))
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow private MinecraftClient client;
    @Inject(
            method = "onSetTradeOffers",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/MerchantScreenHandler;setCanRefreshTrades(Z)V",
                    shift = At.Shift.AFTER
            )
    )
    private void onSetTradeOffers(CallbackInfo ci) {
        Screen screen = client.currentScreen;
        if (screen instanceof MerchantScreen) {
            AutoVoidTrade.isOpeningScreen = true;
            AutoVoidTrade.latestChunkPos = this.client.player.getChunkPos();;
        }
    }
}
