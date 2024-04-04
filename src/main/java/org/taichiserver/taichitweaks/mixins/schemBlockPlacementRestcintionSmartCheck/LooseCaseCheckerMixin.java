package org.taichiserver.taichitweaks.mixins.schemBlockPlacementRestcintionSmartCheck;

import me.fallenbreath.tweakermore.impl.features.schematicProPlace.restrict.LooseCaseChecker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.taichiserver.taichitweaks.config.Configs;
import org.taichiserver.taichitweaks.features.schematicBlockPlacementRestrictionBlocksMap;

@Mixin(LooseCaseChecker.class)
public class LooseCaseCheckerMixin {
    @Inject(method = "Lme/fallenbreath/tweakermore/impl/features/schematicProPlace/restrict/LooseCaseChecker;isLooseCheckSpecialCase(Lnet/minecraft/block/BlockState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
    private static void isLooseCheckSpecialCase(BlockState schematicState, ItemStack schematicStack, ItemStack stackToUse, CallbackInfoReturnable<Boolean> cir){
        if (!Configs.Generic.SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_SMART_CHECK.getBooleanValue()) return;
        if (cir.getReturnValue()) return;

        Item stackToUseItem = stackToUse.getItem();

        if (stackToUseItem instanceof BlockItem blockItem) {

            Block schematicBlock = schematicState.getBlock();
            Block blockToPlace = blockItem.getBlock();

            cir.setReturnValue( schematicBlockPlacementRestrictionBlocksMap.checker(schematicBlock, blockToPlace) );
        }
    }
}
