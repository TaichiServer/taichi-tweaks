package org.taichiserver.taichitweaks.mixins.enhancedToolSwitch;

import fi.dy.masa.tweakeroo.util.InventoryUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.taichiserver.taichitweaks.config.Configs;

@Mixin(InventoryUtils.class)
public class InventoryUtilsMixin {
    @Inject(method = "isBetterTool", at = @At("RETURN"), cancellable = true)
    private static void isBetterTool(ItemStack testedStack, ItemStack previousTool, BlockState state, CallbackInfoReturnable<Boolean> cir){
        if(!Configs.Generic.ENHANCED_TOOL_SWITCH.getBooleanValue()) return;
        if(state.getBlock() instanceof AbstractGlassBlock){
            if(!testedStack.hasEnchantments()){
                cir.setReturnValue(false);
                return;
            }
            NbtList enchantments = testedStack.getEnchantments();
            for (int i = 0; i < enchantments.size(); ++i) {
                Identifier enchantmentIdentifier = EnchantmentHelper.getIdFromNbt(enchantments.getCompound(i));
                Identifier dashEnchantment = EnchantmentHelper.getEnchantmentId(Enchantments.SILK_TOUCH);
                if (enchantmentIdentifier.equals(dashEnchantment)) {
                    cir.setReturnValue(true);
                    return;
                }
            }

            cir.setReturnValue(false);
        }
    }
}
