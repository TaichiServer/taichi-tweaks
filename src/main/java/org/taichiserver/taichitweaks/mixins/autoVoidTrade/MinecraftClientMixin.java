package org.taichiserver.taichitweaks.mixins.autoVoidTrade;


import fi.dy.masa.itemscroller.util.InventoryUtils;
import fi.dy.masa.itemscroller.villager.VillagerDataStorage;
import fi.dy.masa.malilib.util.InfoUtils;
import me.fallenbreath.tweakermore.impl.features.autoVillagerTradeFavorites.MerchantAutoFavoritesTrader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.screen.MerchantScreenHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.taichiserver.taichitweaks.config.Configs;
import org.taichiserver.taichitweaks.features.AutoVoidTrade;

import java.util.Objects;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Nullable public ClientPlayerEntity player;
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        if (AutoVoidTrade.isOpeningScreen && this.player != null){
            int distance = AutoVoidTrade.latestChunkPos.getChebyshevDistance(this.player.getChunkPos());
            if (distance >= 32) {
                MinecraftClient mc = (MinecraftClient)(Object)this;
                MerchantAutoFavoritesTrader.doAutoTrade(mc);
                Screen screen = mc.currentScreen;
                if (screen instanceof MerchantScreen){
                    AutoVoidTrade.preTick++;
                    if(Configs.Generic.AUTO_VOID_TRADE_WAIT_TICK.getIntegerValue() >= AutoVoidTrade.preTick) return;
                    MerchantScreenHandler screenHandler = ((MerchantScreen) screen).getScreenHandler();
                    if (!VillagerDataStorage.getInstance().getFavoritesForCurrentVillager(screenHandler).favorites.isEmpty()) {
                    	InventoryUtils.villagerTradeEverythingPossibleWithAllFavoritedTrades();
                    	InfoUtils.printActionbarMessage("tweakermore.impl.autoVillagerTradeFavorites.triggered");
                    } else {
                    	InfoUtils.printActionbarMessage("tweakermore.impl.autoVillagerTradeFavorites.no_favorite");
                    }
                    mc.player.closeHandledScreen();
                }
            } else {
                return;
            }
        } else {
            return;
        }
        AutoVoidTrade.preTick = 0;
    }
}