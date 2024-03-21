package org.taichiserver.taichitweaks.config;

import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import org.taichiserver.taichitweaks.TaichiTweaks;

import java.util.Collections;
import java.util.List;

public class ConfigGui extends GuiConfigsBase {

    public static ConfigGuiTab tab = ConfigGuiTab.GENERIC;

    public ConfigGui()
    {
        super(10, 50, TaichiTweaks.MOD_ID, null, "TaichTweakroo", TaichiTweaks.VERSION);
    }

    @Override
    public void initGui()
    {

        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;
        int rows = 1;

        for (ConfigGuiTab tab : ConfigGuiTab.values())
        {
            int width = this.getStringWidth(tab.getDisplayName()) + 10;

            if (x >= this.width - width - 10)
            {
                x = 10;
                y += 22;
                rows++;
            }

            x += this.createButton(x, y, width, tab);
        }

        if (rows > 1)
        {
            int scrollbarPosition = this.getListWidget().getScrollbar().getValue();
            this.setListPosition(this.getListX(), 50 + (rows - 1) * 22);
            this.reCreateListWidget();
            this.getListWidget().getScrollbar().setValue(scrollbarPosition);
            this.getListWidget().refreshEntries();
        }
    }


    private int createButton(int x, int y, int width, ConfigGuiTab tab)
    {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(ConfigGui.tab != tab);
        this.addButton(button, new ButtonListenerConfigTabs(tab, this));

        return button.getWidth() + 2;
    }


    @Override
    public List<ConfigOptionWrapper> getConfigs()
    {
        ConfigGuiTab tab = ConfigGui.tab;

        if (tab == ConfigGuiTab.GENERIC)
        {
            return ConfigOptionWrapper.createFor(Configs.Generic.OPTIONS);
        }
        else if (tab == ConfigGuiTab.HOTKEY)
        {
            return ConfigOptionWrapper.createFor(Configs.Hotkeys.OPTIONS);
        }
        else if (tab == ConfigGuiTab.FIXES)
        {
            return ConfigOptionWrapper.createFor(Configs.Fixes.OPTIONS);
        }
        else if (tab == ConfigGuiTab.DISABLES)
        {
            return ConfigOptionWrapper.createFor(Configs.Disables.OPTIONS);
        }

        return Collections.emptyList();
    }


    private static class ButtonListenerConfigTabs implements IButtonActionListener
    {
        private final ConfigGui parent;
        private final ConfigGuiTab tab;

        public ButtonListenerConfigTabs(ConfigGuiTab tab, ConfigGui parent)
        {
            this.tab = tab;
            this.parent = parent;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton)
        {
            ConfigGui.tab = this.tab;
            this.parent.reCreateListWidget(); // apply the new config width
            this.parent.getListWidget().resetScrollbarPosition();
            this.parent.initGui();
        }
    }

    public enum ConfigGuiTab
    {
        GENERIC ("Generic"),
        HOTKEY  ("HotKey"),
        FIXES   ("Fix"),
        DISABLES("Disable");

        private final String translationKey;

        ConfigGuiTab(String translationKey)
        {
            this.translationKey = translationKey;
        }

        public String getDisplayName()
        {
            return StringUtils.translate(this.translationKey);
        }
    }
}
