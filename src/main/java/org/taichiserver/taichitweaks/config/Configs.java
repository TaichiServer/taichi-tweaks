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

import fi.dy.masa.malilib.util.restrictions.UsageRestriction;
import org.taichiserver.taichitweaks.TaichiTweaks;

import java.io.File;

public class Configs implements IConfigHandler {
    private static final String CONFIG_FILE_NAME = TaichiTweaks.MOD_ID + ".json";
    private static final int CONFIG_VERSION = 1;

    public static class Generic {
        public static final ConfigHotkey OPEN_CONFIG_GUI = new ConfigHotkey("openConfigGui", "T,C", "open config gui");
        public static final ConfigBooleanHotkeyed AUTOCOLLECT_SELECTIVE = new ConfigBooleanHotkeyed("autoCollectSelective", false, "", "autoCollectMaterialListItem[Tweakermore] selective");
        public static final ConfigString AUTOCOLLECT_SELECTIVE_LIST = new ConfigString("autoCollectSelectiveList", "", "autoCollectMaterialListItem[Tweakermore] selective");
        public static final ConfigBooleanHotkeyed AUTOFILL_SCHEMATIC_INVENTORY = new ConfigBooleanHotkeyed("autoFillSchematicInventory", false, "", "autoFillSchematicInventory");
        public static final ConfigBooleanHotkeyed AUTO_VOID_TRADE = new ConfigBooleanHotkeyed("autoVoidTrade", false, "", "autoVoidTrade");
        public static final ConfigInteger AUTO_VOID_TRADE_WAIT_TICK = new ConfigInteger("autoVoidTradeWaitTick", 10, 0, 100, "autoVoidTradeWaitTick");
        public static final ConfigBooleanHotkeyed SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_SMART_CHECK = new ConfigBooleanHotkeyed("schematicBlockPlacementRestrictionSmartCheck", false, "", "schematicBlockPlacementRestrictionSmartCheck");
        public static final ConfigStringList SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_BLOCKS_MAP = new ConfigStringList("schematicBlockPlacementRestrictionBlocksMap", ImmutableList.of("minecraft:farmland, minecraft:dirt", "minecraft:water, minecraft:ice"), "schematicBlockPlacementRestrictionBlocksMap");
        public static final ConfigBooleanHotkeyed SELECTIVE_ENTITY_RENDERING = new ConfigBooleanHotkeyed("selectiveEntityRendering", false, "", "selectiveEntityRendering");
        public static final ConfigOptionList SELECTIVE_ENTITY_RENDERING_LIST_TYPE = new ConfigOptionList("selectiveEntityRenderingListType", UsageRestriction.ListType.NONE, "selectiveEntityRenderingListType");
        public static final ConfigStringList SELECTIVE_ENTITY_RENDERING_WHITELIST = new ConfigStringList("selectiveEntityRenderingWhiteList", ImmutableList.of(), "selectiveEntityRenderingWhiteList");
        public static final ConfigStringList SELECTIVE_ENTITY_RENDERING_BLACKLIST = new ConfigStringList("selectiveEntityRenderingBlackList", ImmutableList.of(), "selectiveEntityRenderingBlackList");
        public static final ConfigString SNAPAIM_ANGLE1 = new ConfigString("snapAim_Angle1", "0.0, 0.0", "Snap Aim Angle (1)");
        public static final ConfigString SNAPAIM_ANGLE2 = new ConfigString("snapAim_Angle2", "0.0, 0.0", "Snap Aim Angle (2)");
        public static final ConfigString SNAPAIM_ANGLE3 = new ConfigString("snapAim_Angle3", "0.0, 0.0", "Snap Aim Angle (3)");
        public static final ConfigString SNAPAIM_ANGLE4 = new ConfigString("snapAim_Angle4", "0.0, 0.0", "Snap Aim Angle (4)");
        public static final ConfigHotkey SNAPAIM_ANGLE1_KEYBIND = new ConfigHotkey("snapAim_AngleKeyBind1", "", "snapAim_AngleKeyBind1");
        public static final ConfigHotkey SNAPAIM_ANGLE2_KEYBIND = new ConfigHotkey("snapAim_AngleKeyBind2", "", "snapAim_AngleKeyBind2");
        public static final ConfigHotkey SNAPAIM_ANGLE3_KEYBIND = new ConfigHotkey("snapAim_AngleKeyBind3", "", "snapAim_AngleKeyBind3");
        public static final ConfigHotkey SNAPAIM_ANGLE4_KEYBIND = new ConfigHotkey("snapAim_AngleKeyBind4", "", "snapAim_AngleKeyBind4");

        public static final ConfigBooleanHotkeyed OVERLAY_LIGHTNING_ROD_RANGE = new ConfigBooleanHotkeyed("overlayLightningRodRange", false, "", "overlayLightningRodRange");
        public static final ConfigColor OVERLAY_LIGHTNING_ROD_COLOR = new ConfigColor("overlayLightningRodColor", "#302050D0", "overlayLightningRodColor");

        public static ConfigBooleanHotkeyed GAMMA_OVERRIDE_FIX = new ConfigBooleanHotkeyed("gammaOverrideFix", false, "", "Fixes gamma override not applying when starting the game");

        public static final ConfigBooleanHotkeyed DISABLE_MASSCRAFT_PLAYER_INVENTORY = new ConfigBooleanHotkeyed("disableMassCraftPlayerInventory", false, "", "disableMassCraftPlayerInventory");
        public static final ConfigBooleanHotkeyed DISABLE_PLACED_ON_PORTAL_SIDES = new ConfigBooleanHotkeyed("disablePlacedOnNetherPortalSides+", false, "", "disableMassCraftPlayerInventory");


        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                OPEN_CONFIG_GUI,
//                AUTOCOLLECT_SELECTIVE,
//                AUTOCOLLECT_SELECTIVE_LIST,
                AUTOFILL_SCHEMATIC_INVENTORY,
                AUTO_VOID_TRADE,
                AUTO_VOID_TRADE_WAIT_TICK,
                SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_SMART_CHECK,
                SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_BLOCKS_MAP,
                SELECTIVE_ENTITY_RENDERING,
                SELECTIVE_ENTITY_RENDERING_LIST_TYPE,
                SELECTIVE_ENTITY_RENDERING_WHITELIST,
                SELECTIVE_ENTITY_RENDERING_BLACKLIST,
                SNAPAIM_ANGLE1,
                SNAPAIM_ANGLE2,
                SNAPAIM_ANGLE3,
                SNAPAIM_ANGLE4,
                SNAPAIM_ANGLE1_KEYBIND,
                SNAPAIM_ANGLE2_KEYBIND,
                SNAPAIM_ANGLE3_KEYBIND,
                SNAPAIM_ANGLE4_KEYBIND,
                //OVERLAY_LIGHTNING_ROD_RANGE,
                //OVERLAY_LIGHTNING_ROD_COLOR,
                GAMMA_OVERRIDE_FIX,
                DISABLE_MASSCRAFT_PLAYER_INVENTORY,
                DISABLE_PLACED_ON_PORTAL_SIDES
        );
    }

    public static void loadFromFile() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "Generic", Configs.Generic.OPTIONS);
            }
        }
    }

    public static void saveToFile() {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "Generic", Configs.Generic.OPTIONS);


            root.add("config_version", new JsonPrimitive(CONFIG_VERSION));

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load() {
        loadFromFile();
    }

    @Override
    public void save() {
        saveToFile();
    }
}