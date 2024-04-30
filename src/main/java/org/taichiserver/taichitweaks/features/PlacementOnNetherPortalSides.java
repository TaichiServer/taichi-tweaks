package org.taichiserver.taichitweaks.features;

import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.taichiserver.taichitweaks.config.Configs;
import org.taichiserver.taichitweaks.mixins.restrictionPlacementOnNetherPortalSides.IBellBlockMixin;
import org.taichiserver.taichitweaks.util.blocks.BlockTypeEquals;

import static net.minecraft.block.NetherPortalBlock.AXIS;

public class PlacementOnNetherPortalSides {
    public static boolean restriction(World world, ItemPlacementContext ctx, BlockHitResult hitResult) {
        if(!Configs.Generic.DISABLE_PLACED_ON_PORTAL_SIDES.getBooleanValue()) return false;

        if (ctx == null) {
            return false;
        }

        ItemStack itemStack = ctx.getStack();
        if (itemStack.isEmpty()) {
            return false;
        }

        BlockState blockState = world.getBlockState(hitResult.getBlockPos());
        if (blockState.isOf(Blocks.SCAFFOLDING) && itemStack.getItem() instanceof ScaffoldingItem scaffolding) {
            ctx = scaffolding.getPlacementContext(ctx);
            if (ctx == null) {
                return false;
            }
        }
        BlockPos blockPos2 = ctx.getBlockPos();

        while (true) {
            if (checkNeighbors(world, blockPos2.north(), Direction.Axis.Z, ctx, hitResult, hitResult.getBlockPos()) ||
                    checkNeighbors(world, blockPos2.south(), Direction.Axis.Z, ctx, hitResult, hitResult.getBlockPos()) ||
                    checkNeighbors(world, blockPos2.east(), Direction.Axis.X, ctx, hitResult, hitResult.getBlockPos()) ||
                    checkNeighbors(world, blockPos2.west(), Direction.Axis.X, ctx, hitResult, hitResult.getBlockPos()) ||
                    checkNeighbors(world, blockPos2.up(), Direction.Axis.Y, ctx, hitResult, hitResult.getBlockPos()) ||
                    checkNeighbors(world, blockPos2.down(), Direction.Axis.Y, ctx, hitResult, hitResult.getBlockPos())
            ) {
                return true;
            }

            if (itemStack.getItem() instanceof TallBlockItem || itemStack.isOf(Items.PITCHER_PLANT)) {
                if (blockPos2.equals(hitResult.getBlockPos().offset(hitResult.getSide()))) {
                    blockPos2 = blockPos2.up();
                } else {
                    break;
                }
            } else if (itemStack.getItem() instanceof BedItem) {
                if (blockPos2.equals(hitResult.getBlockPos().offset(hitResult.getSide()))) {
                    Direction direction = ctx.getHorizontalPlayerFacing();
                    blockPos2 = blockPos2.offset(direction);
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return false;
    }


    public static boolean checkNeighbors(World world, BlockPos blockPos, Direction.Axis axis, ItemPlacementContext ctx, BlockHitResult hitResult, BlockPos origin) {
        BlockState blockState = world.getBlockState(blockPos);
        ItemStack itemStack = ctx.getStack();
        if (blockState.isOf(Blocks.NETHER_PORTAL)) {
            if (Direction.Axis.Y == axis || blockState.get(AXIS) == axis) {
                if (!itemStack.isOf(Items.SCAFFOLDING) && ctx.canReplaceExisting()) {
                    return false;
                }
                if (!ctx.canPlace()) {
                    return false;
                }
                if (!canUse(ctx, origin, hitResult)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean canUse(ItemPlacementContext context, BlockPos origin, BlockHitResult hitResult) {
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(origin);
        Block block = blockState.getBlock();

        if (BlockTypeEquals.isSneakingInteractionCancel(blockState)) {
            if (context.shouldCancelInteraction()) {
                return true;
            } else {
                return false;
            }
        } else if (blockState.isOf(Blocks.BELL)) {
            if ((!canRing((BellBlock) block, blockState, hitResult, origin))) {
                return true;
            } else if (context.shouldCancelInteraction()){
                return true;
            }
            return false;
        }

        return true;
    }


    public static boolean canRing(BellBlock bell, BlockState blockState, BlockHitResult hitResult, BlockPos blockPos) {
        return ((IBellBlockMixin) bell).ModIsPointOnBell(blockState, hitResult.getSide(), hitResult.getPos().y - (double)blockPos.getY());
    }
}
