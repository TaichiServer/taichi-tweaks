package org.taichiserver.taichitweaks.util;

import com.google.common.collect.ImmutableList;
import me.fallenbreath.tweakermore.impl.features.autoContainerProcess.AutoProcessableScreen;
import me.fallenbreath.tweakermore.impl.features.autoContainerProcess.processors.ProcessResult;
import me.fallenbreath.tweakermore.mixins.tweaks.features.autoContainerProcess.ItemScrollerInventoryUtilsAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.taichiserver.taichitweaks.features.AutoFillSchemInv;

import java.util.List;
import java.util.stream.Collectors;

public class ContainerProcessManager {
    private static final List<IContainerProcessor> CONTAINER_PROCESSORS = ImmutableList.of(
        new AutoFillSchemInv()
    );

    private static boolean hasTweakEnabled() {
        return CONTAINER_PROCESSORS.stream().anyMatch(IContainerProcessor::isEnabled);
    }

    public static List<IContainerProcessor> getProcessors() {
        return CONTAINER_PROCESSORS;
    }

    public static void process(ScreenHandler container) {
        if (!hasTweakEnabled()) return;
        Screen screen = MinecraftClient.getInstance().currentScreen;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && screen instanceof HandledScreen<?>) {
            if (player.isSpectator()) return;

            HandledScreen<?> containerScreen = (HandledScreen<?>)screen;
            if (containerScreen.getScreenHandler() != container || !((AutoProcessableScreen)screen).shouldProcess())
            {
                return;
            }

            ((AutoProcessableScreen)screen).setShouldProcess(false);
            List<Slot> allSlots = container.slots;
            List<Slot> playerInvSlots = allSlots.stream().filter(slot -> slot.inventory instanceof PlayerInventory).collect(Collectors.toList());
            if (allSlots.isEmpty() || playerInvSlots.isEmpty())
            {
                return;
            }
            List<Slot> containerInvSlots = allSlots.stream().filter(slot -> ItemScrollerInventoryUtilsAccessor.areSlotsInSameInventory(slot, allSlots.get(0))).collect(Collectors.toList());
            if (containerInvSlots.isEmpty())
            {
                return;
            }

            boolean closeGui = false;
            for (IContainerProcessor processor : CONTAINER_PROCESSORS)
            {
                if (processor.isEnabled())
                {
                    ProcessResult result = processor.process(player, containerScreen, allSlots, playerInvSlots, containerInvSlots);
                    closeGui |= result.closeGui;
                    if (result.cancelProcessing)
                    {
                        break;
                    }
                }
            }
            if (closeGui)
            {
                player.closeHandledScreen();
            }

        }
    }
}
