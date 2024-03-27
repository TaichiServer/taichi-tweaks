package org.taichiserver.taichitweaks.mixins.schemBlockPlacementRestcintionSmartCheck;

import me.fallenbreath.tweakermore.impl.features.schematicProPlace.restrict.BlockInteractionRestrictor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.taichiserver.taichitweaks.config.Configs;
import org.taichiserver.taichitweaks.util.items.ItemTypeEquals;

@Mixin(BlockInteractionRestrictor.class)
public class BlockInteractionRestrictorMixin {
    @Inject(method = "checkInteract", at = @At("TAIL"), cancellable = true)
    private static void checkInteract(PlayerEntity player, BlockState worldState, BlockState schematicState, CallbackInfoReturnable<BlockInteractionRestrictor.Result> cir){
        if (!Configs.Generic.SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_SMART_CHECK.getBooleanValue()) return;
        if (cir.getReturnValue().equals(BlockInteractionRestrictor.Result.good())) return;

        Item mainHandItem = player.getMainHandStack().getItem();

        if( Blocks.DIRT.equals(worldState.getBlock()) && Blocks.FARMLAND.equals(schematicState.getBlock()) && ItemTypeEquals.isHoe(mainHandItem)) {
            cir.setReturnValue(BlockInteractionRestrictor.Result.good());
        }
    }
}
