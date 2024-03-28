package org.taichiserver.taichitweaks;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import fi.dy.masa.malilib.hotkeys.IKeyboardInputHandler;
import org.taichiserver.taichitweaks.config.Configs;

public class InputHandler implements IKeybindProvider, IKeyboardInputHandler {
    private static final InputHandler INSTANCE = new InputHandler();

    private InputHandler() {
        super();
    }

    public static InputHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (IHotkey hotkey : Configs.Hotkeys.OPTIONS) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
        for (IConfigBase config : Configs.Generic.OPTIONS) {
            if(config instanceof ConfigBooleanHotkeyed hotkey) {
                manager.addKeybindToMap(hotkey.getKeybind());
            }
        }
        for (IConfigBase config : Configs.Fixes.OPTIONS) {
            if(config instanceof ConfigBooleanHotkeyed hotkey) {
                manager.addKeybindToMap(hotkey.getKeybind());
            }
        }
        for (IConfigBase config : Configs.Disables.OPTIONS) {
            if(config instanceof ConfigBooleanHotkeyed hotkey) {
                manager.addKeybindToMap(hotkey.getKeybind());
            }
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(TaichiTweaks.MOD_ID, "taichtweakroo.hotkeys.category.hotkeys", Configs.Hotkeys.OPTIONS);
    }
}