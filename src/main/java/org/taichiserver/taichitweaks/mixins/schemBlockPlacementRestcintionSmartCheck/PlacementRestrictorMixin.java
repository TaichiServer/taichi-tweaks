package org.taichiserver.taichitweaks.mixins.schemBlockPlacementRestcintionSmartCheck;

import me.fallenbreath.tweakermore.impl.features.schematicProPlace.restrict.PlacementRestrictor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.taichiserver.taichitweaks.config.Configs;

@Mixin(PlacementRestrictor.class)
public abstract class PlacementRestrictorMixin {
    @Inject(method = "isBlockToPlaceCorrect", at = @At("TAIL"), cancellable = true)
    private static void isBlockToPlaceCorrect(BlockState schematicState, BlockState stateToPlace, CallbackInfoReturnable<Boolean> cir){
        if (!Configs.Generic.SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_SMART_CHECK.getBooleanValue()) return;

        Block schematicBlock = schematicState.getBlock();
        Block blockToPlace = stateToPlace.getBlock();
        System.out.println(schematicBlock);
        System.out.println(blockToPlace);
        if (schematicBlock == Blocks.FARMLAND && blockToPlace == Blocks.DIRT) {
            cir.setReturnValue(true);
        }
    }
}
