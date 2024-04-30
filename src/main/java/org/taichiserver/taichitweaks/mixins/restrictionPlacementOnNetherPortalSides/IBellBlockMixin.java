package org.taichiserver.taichitweaks.mixins.restrictionPlacementOnNetherPortalSides;

import net.minecraft.block.BellBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BellBlock.class)
public interface IBellBlockMixin {
    @Invoker("isPointOnBell")
    boolean ModIsPointOnBell(BlockState state, Direction side, double y);
}
