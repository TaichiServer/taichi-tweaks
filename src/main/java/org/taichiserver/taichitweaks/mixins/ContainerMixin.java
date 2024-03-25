package org.taichiserver.taichitweaks.mixins;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.util.ModIds;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.taichiserver.taichitweaks.util.container.ContainerProcessManager;

@Restriction(require = @Condition(ModIds.itemscroller))
@Mixin(ScreenHandler.class)
public abstract class ContainerMixin {
    @Inject(method = "updateSlotStacks", at = @At("TAIL"))
    private void autoContainerProcess(CallbackInfo ci) {
        ContainerProcessManager.process((ScreenHandler)(Object)this);
    }
}
