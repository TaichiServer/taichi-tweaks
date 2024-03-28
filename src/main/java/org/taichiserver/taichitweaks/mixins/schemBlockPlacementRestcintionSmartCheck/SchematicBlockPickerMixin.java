package org.taichiserver.taichitweaks.mixins.schemBlockPlacementRestcintionSmartCheck;

import me.fallenbreath.tweakermore.impl.features.schematicProPlace.ProPlaceUtils;
import me.fallenbreath.tweakermore.impl.features.schematicProPlace.SchematicBlockPicker;
import fi.dy.masa.litematica.world.SchematicWorldHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.taichiserver.taichitweaks.config.Configs;

@Mixin(SchematicBlockPicker.class)
public class SchematicBlockPickerMixin {
    @Inject(method = "doSchematicWorldPickBlock", at = @At("HEAD"), cancellable = true)
    private static void doSchematicWorldPickBlock(MinecraftClient mc, BlockPos pos, Hand hand, CallbackInfo ci){
        if (!Configs.Generic.SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_SMART_CHECK.getBooleanValue()) return;
        World schematicWorld = SchematicWorldHandler.getSchematicWorld();
        BlockState state = schematicWorld.getBlockState(pos);
        Item stack = ProPlaceUtils.getItemForState(state, schematicWorld, pos).getItem();
        if(mc.player.getOffHandStack().getItem().equals(stack)){
            ci.cancel();
        }
    }
}
