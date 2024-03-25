package org.taichiserver.taichitweaks.features;

import fi.dy.masa.itemscroller.util.InventoryUtils;
import fi.dy.masa.litematica.world.SchematicWorldHandler;
import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.util.LayerRange;
import me.fallenbreath.tweakermore.impl.features.autoContainerProcess.processors.ProcessResult;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.taichiserver.taichitweaks.config.Configs;
import org.taichiserver.taichitweaks.util.container.IContainerProcessor;

import java.util.List;


public class AutoFillSchemInv implements IContainerProcessor {
    @Override
    public ConfigBooleanHotkeyed getConfig() {
        return Configs.Generic.AUTOFILL_SCHEMATIC_INVENTORY;
    }

    @Override
    public ProcessResult process(ClientPlayerEntity player, HandledScreen<?> containerScreen, List<Slot> allSlots, List<Slot> playerInvSlots, List<Slot> containerInvSlots) {
        boolean result = false;
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;
        if (hit == null) return new ProcessResult(false, false);
        if (hit.getType() != HitResult.Type.BLOCK) return new ProcessResult(false, false);

        BlockHitResult hitBlock = (BlockHitResult) hit;
        BlockPos hitBlockPos = hitBlock.getBlockPos();

        World schematicWorld = SchematicWorldHandler.getSchematicWorld();
        World clientWorld = client.world;

        if(schematicWorld != null && client.player != null && clientWorld != null) {
            LayerRange layerRange = DataManager.getRenderLayerRange();
            if (!layerRange.isPositionWithinRange(hitBlockPos)) return new ProcessResult(false, false);

            BlockState schemBlockState = schematicWorld.getBlockState(hitBlockPos);
            BlockState clientBlockState = clientWorld.getBlockState(hitBlockPos);
            if(!schemBlockState.equals(clientBlockState)) return new ProcessResult(false, false);

            BlockEntity schemBlockEntity = schematicWorld.getBlockEntity(hitBlockPos);
            if(schemBlockEntity == null) return new ProcessResult(false, false);

            if (schemBlockEntity instanceof Inventory) {
                Inventory schemInventory = (Inventory) schemBlockEntity;
                int schemInventorySize = schemInventory.size();
                for (int i = 0; i < schemInventorySize; i++) {
                    ItemStack schemContainerStack = schemInventory.getStack(i);
                    int schemContainerStackCount = schemContainerStack.getCount();
                    Slot clientContainerSlot = containerInvSlots.get(i);
                    ItemStack clientContainerStack = containerInvSlots.get(i).getStack();
                    if (InventoryUtils.areStacksEqual(schemContainerStack, clientContainerStack)) {
                        if (schemContainerStackCount == clientContainerStack.getCount()) {
                            continue;
                        }
                    }
                    for (Slot clientPlayerSlot : playerInvSlots) {
                        ItemStack clientPlayerStack = clientPlayerSlot.getStack();
                        int clientPlayerSlotInedex = clientPlayerSlot.getIndex();
                        if (clientPlayerSlotInedex < 9) clientPlayerSlotInedex += 36;
                        int slotIndex = clientPlayerSlotInedex - 9 + schemInventorySize;
                        if (InventoryUtils.areStacksEqual(schemContainerStack, clientPlayerStack)) {

                            if (schemContainerStackCount == clientPlayerStack.getCount()) {
                                result = true;
                                InventoryUtils.clickSlot(containerScreen, slotIndex, 0, SlotActionType.PICKUP);
                                InventoryUtils.clickSlot(containerScreen, clientContainerSlot.getIndex(), 0, SlotActionType.PICKUP);
                            }
                        }
                    }
                }

            }
        }

        return new ProcessResult(true, result);
    }
}
