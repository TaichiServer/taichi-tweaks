package org.taichiserver.taichitweaks.mixins.schemBlockPlacementRestcintionSmartCheck;

import me.fallenbreath.tweakermore.impl.features.schematicProPlace.restrict.BlockInteractionRestrictor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.HoeItem;
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
        Item offHandItem = player.getOffHandStack().getItem();

        Block worldBlock = worldState.getBlock();
        Block schemBlock = schematicState.getBlock();

        if ( Blocks.DIRT.equals(worldBlock) && Blocks.FARMLAND.equals(schemBlock) && mainHandItem instanceof HoeItem) {
            cir.setReturnValue(BlockInteractionRestrictor.Result.good());
        } else if ( Blocks.FARMLAND.equals(worldBlock) && Blocks.FARMLAND.equals(schemBlock) && offHandItem instanceof AliasedBlockItem) {
            cir.setReturnValue(BlockInteractionRestrictor.Result.good());
        }
    }
}
