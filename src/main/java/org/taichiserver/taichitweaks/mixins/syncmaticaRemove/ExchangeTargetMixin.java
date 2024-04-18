package org.taichiserver.taichitweaks.mixins.syncmaticaRemove;

import ch.endte.syncmatica.Context;
import ch.endte.syncmatica.communication.ExchangeTarget;
import ch.endte.syncmatica.communication.PacketType;
import fi.dy.masa.malilib.gui.GuiBase;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.taichiserver.taichitweaks.config.Configs;

@Mixin(ExchangeTarget.class)
public class ExchangeTargetMixin {
    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void onRemoveAction(Identifier id, PacketByteBuf packetBuf, Context context, CallbackInfo ci){
        if(!id.equals(PacketType.REMOVE_SYNCMATIC.identifier)) return;
        if(Configs.Generic.SYNCMATICA_REMOVE_DISABLED.getBooleanValue()){
            ci.cancel();
        } else if (Configs.Generic.SYNCMATICA_REMOVE_NEED_SHIFT.getBooleanValue()) {
            if(!GuiBase.isShiftDown()) ci.cancel();
        }
    }
}
