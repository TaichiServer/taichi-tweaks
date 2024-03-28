package org.taichiserver.taichitweaks;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import org.taichiserver.taichitweaks.config.ConfigGui;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            ConfigGui gui = new ConfigGui();
            gui.setParent(screen);
            return gui;
        };
    }
}