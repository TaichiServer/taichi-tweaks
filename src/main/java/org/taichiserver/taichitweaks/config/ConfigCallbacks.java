package org.taichiserver.taichitweaks.config;

import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.util.InfoUtils;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.minihud.util.DataStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import org.taichiserver.taichitweaks.features.AimAngle;
import org.taichiserver.taichitweaks.features.OverlayRendererBeaconRange;

public class ConfigCallbacks {
    public static void init() {
        Callbacks callback = new Callbacks();

        Configs.Hotkeys.OPEN_CONFIG_GUI.getKeybind().setCallback(callback);
        Configs.Hotkeys.SNAPAIM_ANGLE1_KEYBIND.getKeybind().setCallback(callback);
        Configs.Hotkeys.SNAPAIM_ANGLE2_KEYBIND.getKeybind().setCallback(callback);
        Configs.Hotkeys.SNAPAIM_ANGLE3_KEYBIND.getKeybind().setCallback(callback);
        Configs.Hotkeys.SNAPAIM_ANGLE4_KEYBIND.getKeybind().setCallback(callback);

        Configs.Renders.OVERLAY_LIGHTNING_ROD_COLOR.setValueChangeCallback((config) -> OverlayRendererBeaconRange.INSTANCE.setNeedsUpdate());
        Configs.Renders.OVERLAY_LIGHTNING_ROD_RANGE.setValueChangeCallback(RendererCallbacks::onBeaconRangeToggled);
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

    public static class RendererCallbacks {
        public static void onSpawnChunksRealToggled(IConfigBoolean config) {
            if (config.getBooleanValue()) {

                BlockPos spawn = DataStorage.getInstance().getWorldSpawn();
                String green = GuiBase.TXT_GREEN;
                String rst = GuiBase.TXT_RST;
                String strStatus = green + StringUtils.translate("malilib.message.value.on") + rst;
                String strPos = String.format("x: %d, y: %d, z: %d", spawn.getX(), spawn.getY(), spawn.getZ());
                String message = StringUtils.translate("minihud.message.toggled_using_world_spawn", config.getPrettyName(), strStatus, strPos);

                InfoUtils.printActionbarMessage(message);
            }
        }

        public static void onBeaconRangeToggled(IConfigBoolean config) {
            if (config.getBooleanValue()) {
                OverlayRendererBeaconRange.INSTANCE.setNeedsUpdate();
            }
        }
    }
}
