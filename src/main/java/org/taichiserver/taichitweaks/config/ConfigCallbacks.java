package org.taichiserver.taichitweaks.config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import net.minecraft.client.MinecraftClient;
import org.taichiserver.taichitweaks.features.AimAngle;
import org.taichiserver.taichitweaks.features.SelectiveBlockRenderingArea;

public class ConfigCallbacks {
    public static void init() {
        HotkeyCallbacks hotkeyCallback = new HotkeyCallbacks();
        ValueChangeCallbacks valueChangeCallback = new ValueChangeCallbacks();

        Configs.Generic.AUTOFILL_SCHEMATIC_INVENTORY.getKeybind().setCallback(hotkeyCallback);
        Configs.Generic.AUTO_VOID_TRADE.getKeybind().setCallback(hotkeyCallback);
        Configs.Generic.SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_SMART_CHECK.getKeybind().setCallback(hotkeyCallback);
        Configs.Generic.SELECTIVE_BLOCKS_RENDERING_AREA.getKeybind().setCallback(hotkeyCallback);
        Configs.Generic.SELECTIVE_BLOCKS_RENDERING_AREA_SELECTOR.setValueChangeCallback(valueChangeCallback);

        Configs.Hotkeys.OPEN_CONFIG_GUI.getKeybind().setCallback(hotkeyCallback);
        Configs.Hotkeys.SNAPAIM_ANGLE1_KEYBIND.getKeybind().setCallback(hotkeyCallback);
        Configs.Hotkeys.SNAPAIM_ANGLE2_KEYBIND.getKeybind().setCallback(hotkeyCallback);
        Configs.Hotkeys.SNAPAIM_ANGLE3_KEYBIND.getKeybind().setCallback(hotkeyCallback);
        Configs.Hotkeys.SNAPAIM_ANGLE4_KEYBIND.getKeybind().setCallback(hotkeyCallback);

        Configs.Fixes.GAMMA_OVERRIDE_FIX.getKeybind().setCallback(hotkeyCallback);

        Configs.Disables.DISABLE_MASSCRAFT_PLAYER_INVENTORY.getKeybind().setCallback(hotkeyCallback);

//        Configs.Renders.OVERLAY_LIGHTNING_ROD_COLOR.setValueChangeCallback((config) -> OverlayRendererBeaconRange.INSTANCE.setNeedsUpdate());
//        Configs.Renders.OVERLAY_LIGHTNING_ROD_RANGE.setValueChangeCallback(RendererCallbacks::onBeaconRangeToggled);
    }

    public static class HotkeyCallbacks implements IHotkeyCallback {
        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null) {
                return false;
            } else if (key == Configs.Hotkeys.OPEN_CONFIG_GUI.getKeybind()) {
                GuiBase.openGui(new ConfigGui());
                return true;
            } else if (key == Configs.Hotkeys.SNAPAIM_ANGLE1_KEYBIND.getKeybind()) {
                AimAngle.init(mc.player, Configs.Generic.SNAPAIM_ANGLE1.getStringValue());
                return true;
            } else if (key == Configs.Hotkeys.SNAPAIM_ANGLE2_KEYBIND.getKeybind()) {
                AimAngle.init(mc.player, Configs.Generic.SNAPAIM_ANGLE2.getStringValue());
                return true;
            } else if (key == Configs.Hotkeys.SNAPAIM_ANGLE3_KEYBIND.getKeybind()) {
                AimAngle.init(mc.player, Configs.Generic.SNAPAIM_ANGLE3.getStringValue());
                return true;
            } else if (key == Configs.Hotkeys.SNAPAIM_ANGLE4_KEYBIND.getKeybind()) {
                AimAngle.init(mc.player, Configs.Generic.SNAPAIM_ANGLE4.getStringValue());
                return true;
            } else if (key == Configs.Generic.AUTOFILL_SCHEMATIC_INVENTORY.getKeybind()) {
                valueChange(Configs.Generic.AUTOFILL_SCHEMATIC_INVENTORY);
                return true;
            } else if (key == Configs.Generic.AUTO_VOID_TRADE.getKeybind()) {
                valueChange(Configs.Generic.AUTO_VOID_TRADE);
                return true;
            } else if (key == Configs.Generic.SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_SMART_CHECK.getKeybind()) {
                valueChange(Configs.Generic.SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_SMART_CHECK);
                return true;
            } else if (key == Configs.Generic.SELECTIVE_BLOCKS_RENDERING_AREA.getKeybind()) {
                valueChange(Configs.Generic.SELECTIVE_BLOCKS_RENDERING_AREA);
                SelectiveBlockRenderingArea.onValueChanged();
                return true;
            } else if (key == Configs.Fixes.GAMMA_OVERRIDE_FIX.getKeybind()) {
                valueChange(Configs.Fixes.GAMMA_OVERRIDE_FIX);
                return true;
            } else if (key == Configs.Disables.DISABLE_MASSCRAFT_PLAYER_INVENTORY.getKeybind()) {
                valueChange(Configs.Disables.DISABLE_MASSCRAFT_PLAYER_INVENTORY);
                return true;
            }
            return false;
        }
    }

    public static class ValueChangeCallbacks implements IValueChangeCallback {
        @Override
        public void onValueChanged(IConfigBase iConfigBase) {
            if(iConfigBase == Configs.Generic.SELECTIVE_BLOCKS_RENDERING_AREA_SELECTOR) {
                System.out.println( "onValueChanged() : SELECTIVE_BLOCKS_RENDERING_AREA_SELECTOR" );
                SelectiveBlockRenderingArea.onValueChanged();
            }
        }
    }

    private static void valueChange(ConfigBoolean config) {
        config.setBooleanValue(!config.getBooleanValue());
    }
}