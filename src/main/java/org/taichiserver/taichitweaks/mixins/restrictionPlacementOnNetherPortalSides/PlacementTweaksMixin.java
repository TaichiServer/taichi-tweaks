package org.taichiserver.taichitweaks.mixins.restrictionPlacementOnNetherPortalSides;

import fi.dy.masa.malilib.util.PositionUtils;
import fi.dy.masa.tweakeroo.tweaks.PlacementTweaks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.taichiserver.taichitweaks.config.Configs;

@Mixin(PlacementTweaks.class)
public class PlacementTweaksMixin {
    @Inject(method = "tryPlaceBlock", at = @At("HEAD"), cancellable = true)
    private static void tryPlaceBlock(ClientPlayerInteractionManager controller, ClientPlayerEntity player, ClientWorld world, BlockPos posIn, Direction sideIn, Direction sideRotatedIn, float playerYaw, Vec3d hitVec, Hand hand, PositionUtils.HitPart hitPart, boolean isFirstClick, CallbackInfoReturnable<ActionResult> cir){
        if(!Configs.Generic.DISABLE_PLACED_ON_PORTAL_SIDES.getBooleanValue()) return;

        // if (!Configs.disablePlacedOnNetherPortalSides.getBool()) {
        //     return;
        // }
        if (checkNeighbors(world, posIn.north(), Direction.Axis.Z, cir)) {
            return;
        }
        if (checkNeighbors(world, posIn.south(), Direction.Axis.Z, cir)) {
            return;
        }
        if (checkNeighbors(world, posIn.east(), Direction.Axis.X, cir)) {
            return;
        }
        if (checkNeighbors(world, posIn.west(), Direction.Axis.X, cir)) {
            return;
        }
        if (checkNeighbors(world, posIn.up(), Direction.Axis.Y, cir)) {
            return;
        }
        if (checkNeighbors(world, posIn.down(), Direction.Axis.Y, cir)) {
            return;
        }
    }
    private static boolean checkNeighbors(World world, BlockPos blockPos, Direction.Axis axis, CallbackInfoReturnable<ActionResult> cir) {
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.NETHER_PORTAL)) {
            if (Direction.Axis.Y == axis || blockState.get(NetherPortalBlock.AXIS) == axis) {
                cir.setReturnValue(ActionResult.CONSUME);
                cir.cancel();
                //sendRestrictionMessage("placed on Nether Portal sides");
                System.out.println("placed on Nether Portal sides");
                return true;
            }
        }
        return false;
    }
}
