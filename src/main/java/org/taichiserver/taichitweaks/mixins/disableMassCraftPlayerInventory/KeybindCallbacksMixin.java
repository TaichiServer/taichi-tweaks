package org.taichiserver.taichitweaks.mixins.disableMassCraftPlayerInventory;

import fi.dy.masa.itemscroller.event.KeybindCallbacks;
import fi.dy.masa.malilib.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.taichiserver.taichitweaks.config.Configs;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

@Mixin(KeybindCallbacks.class)
public class KeybindCallbacksMixin {
    @Inject(method = "onClientTick", at = @At("HEAD"), cancellable = true)
    private void onClientTick(MinecraftClient mc, CallbackInfo ci) {
        if(!Configs.Generic.DISABLE_MASSCRAFT_PLAYER_INVENTORY.getBooleanValue()) return;
        Screen screen = GuiUtils.getCurrentScreen();
        if(screen instanceof InventoryScreen) {
            ci.cancel();
        }
    }
}
