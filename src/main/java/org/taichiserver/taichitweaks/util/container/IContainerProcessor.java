package org.taichiserver.taichitweaks.util.container;

import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import me.fallenbreath.tweakermore.impl.features.autoContainerProcess.processors.ProcessResult;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.screen.slot.Slot;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

import java.util.List;

public interface IContainerProcessor {
    default boolean isEnabled()
    {
        ConfigBooleanHotkeyed config = this.getConfig();
        return config.getBooleanValue();
    }

    ConfigBooleanHotkeyed getConfig();

    ProcessResult process(ClientPlayerEntity player, HandledScreen<?> containerScreen, List<Slot> allSlots, List<Slot> playerInvSlots, List<Slot> containerInvSlots);
}
