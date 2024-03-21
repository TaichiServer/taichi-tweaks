package org.taichiserver.taichitweaks.config;

import com.google.common.collect.ImmutableList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;

import org.taichiserver.taichitweaks.TaichiTweaks;

import java.io.File;

public class Configs implements IConfigHandler {
    private static final String CONFIG_FILE_NAME = TaichiTweaks.MOD_ID + ".json";
    private static final int CONFIG_VERSION = 1;

    public static class Generic {
        public static final ConfigBooleanHotkeyed AUTOCOLLECT_SELECTIVE = new ConfigBooleanHotkeyed("autoCollectSelective", false, "", "autoCollectMaterialListItem[Tweakermore] selective");
        public static final ConfigString AUTOCOLLECT_SELECTIVE_LIST = new ConfigString("autoCollectSelectiveList", "", "autoCollectMaterialListItem[Tweakermore] selective");
        public static final ConfigBooleanHotkeyed AUTOFILL_SCHEMATIC_INVENTORY = new ConfigBooleanHotkeyed("autoFillSchematicInventory", false, "", "");
        public static final ConfigBooleanHotkeyed AUTO_VOID_TRADE = new ConfigBooleanHotkeyed("autoVoidTrade", false, "", "");
        public static final ConfigString SNAPAIM_ANGLE1 = new ConfigString("snapAim_Angle1", "0.0, 0.0", "Snap Aim Angle (1)");
        public static final ConfigString SNAPAIM_ANGLE2 = new ConfigString("snapAim_Angle2", "0.0, 0.0", "Snap Aim Angle (2)");
        public static final ConfigString SNAPAIM_ANGLE3 = new ConfigString("snapAim_Angle3", "0.0, 0.0", "Snap Aim Angle (3)");
        public static final ConfigString SNAPAIM_ANGLE4 = new ConfigString("snapAim_Angle4", "0.0, 0.0", "Snap Aim Angle (4)");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                AUTOCOLLECT_SELECTIVE,
                AUTOCOLLECT_SELECTIVE_LIST,
                AUTOFILL_SCHEMATIC_INVENTORY,
                AUTO_VOID_TRADE,
                SNAPAIM_ANGLE1,
                SNAPAIM_ANGLE2,
                SNAPAIM_ANGLE3,
                SNAPAIM_ANGLE4
        );
    }

    public static class Hotkeys {
        public static final ConfigHotkey OPEN_CONFIG_GUI = new ConfigHotkey("openConfigGui", "T,C", "open config gui");
        public static final ConfigHotkey SNAPAIM_ANGLE1_KEYBIND = new ConfigHotkey("snapAim_AngleKeyBind1", "", "snapAim_AngleKeyBind1");
        public static final ConfigHotkey SNAPAIM_ANGLE2_KEYBIND = new ConfigHotkey("snapAim_AngleKeyBind2", "", "snapAim_AngleKeyBind2");
        public static final ConfigHotkey SNAPAIM_ANGLE3_KEYBIND = new ConfigHotkey("snapAim_AngleKeyBind3", "", "snapAim_AngleKeyBind3");
        public static final ConfigHotkey SNAPAIM_ANGLE4_KEYBIND = new ConfigHotkey("snapAim_AngleKeyBind4", "", "snapAim_AngleKeyBind4");

        public static final ImmutableList<ConfigHotkey> OPTIONS = ImmutableList.of(
                OPEN_CONFIG_GUI,
                SNAPAIM_ANGLE1_KEYBIND,
                SNAPAIM_ANGLE2_KEYBIND,
                SNAPAIM_ANGLE3_KEYBIND,
                SNAPAIM_ANGLE4_KEYBIND
        );
    }


    public static class Fixes {
        public static ConfigBoolean GAMMA_OVERRIDE_FIX = new ConfigBoolean("gammaOverrideFix", true, "Fixes gamma override not applying when starting the game");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                GAMMA_OVERRIDE_FIX
        );
    }

    public static class Disables {

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                //DISABLE_ENTITY_COLLISIONS
        );
    }


    public static void loadFromFile()
    {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead())
        {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject())
            {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "Generic", Configs.Generic.OPTIONS);
                ConfigUtils.readConfigBase(root, "Hotkey", Configs.Hotkeys.OPTIONS);
                ConfigUtils.readConfigBase(root, "Fixes", Configs.Fixes.OPTIONS);
                ConfigUtils.readConfigBase(root, "Disables", Configs.Disables.OPTIONS);
            }
        }
    }

    public static void saveToFile()
    {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs())
        {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "Generic", Configs.Generic.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Hotkey", Configs.Hotkeys.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Fixes", Configs.Fixes.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Disables", Configs.Disables.OPTIONS);


            root.add("config_version", new JsonPrimitive(CONFIG_VERSION));

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load()
    {
        loadFromFile();
    }

    @Override
    public void save()
    {
        saveToFile();
    }
}