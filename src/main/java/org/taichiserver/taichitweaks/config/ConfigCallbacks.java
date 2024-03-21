package org.taichiserver.taichitweaks.config;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.minecraft.client.MinecraftClient;
import org.taichiserver.taichitweaks.features.AimAngle;

public class ConfigCallbacks {
    public static void init() {
        Callbacks callback = new Callbacks();

        Configs.Hotkeys.OPEN_CONFIG_GUI.getKeybind().setCallback(callback);
        Configs.Hotkeys.SNAPAIM_ANGLE1_KEYBIND.getKeybind().setCallback(callback);
        Configs.Hotkeys.SNAPAIM_ANGLE2_KEYBIND.getKeybind().setCallback(callback);
        Configs.Hotkeys.SNAPAIM_ANGLE3_KEYBIND.getKeybind().setCallback(callback);
        Configs.Hotkeys.SNAPAIM_ANGLE4_KEYBIND.getKeybind().setCallback(callback);
    }

    public static class Callbacks implements IHotkeyCallback {
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
            }
            return false;
        }
    }
}
