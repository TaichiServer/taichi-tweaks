package org.taichiserver.taichitweaks.config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ITextFieldListener;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;

public class MasaConfigsHintGui extends GuiBase {
    public MasaConfigsHintGui() {
        this.title = "Configs Hint";
    }
    @Override
    public void initGui() {
        super.initGui();

        int x = 20;
        int y = 20;

        this.addLabel(x, y, 100, 20, 0xFFFFFFFF, "Tweakroo");
        for(IConfigBase config : fi.dy.masa.tweakeroo.config.Configs.Generic.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                y += 15;
                this.addLabel(x + 10, y, 100, 20, 0xFFFFFFFF, config.getName());
            }
        }
        for(IConfigBase config : fi.dy.masa.tweakeroo.config.Configs.Fixes.OPTIONS) {
            if(((ConfigBase) config).isModified()) {
                y += 15;
                this.addLabel(x + 10, y, 100, 20, 0xFFFFFFFF, config.getName());
            }
        }
        for(IConfigBase config : fi.dy.masa.tweakeroo.config.Configs.Lists.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                y += 15;
                this.addLabel(x + 10, y, 100, 20, 0xFFFFFFFF, config.getName());
            }
        }
        for(IConfigBase config : fi.dy.masa.tweakeroo.config.Configs.Disable.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                y += 15;
                this.addLabel(x + 10, y, 100, 20, 0xFFFFFFFF, config.getName());
            }
        }

        y += 20;

        this.addLabel(x, y, 100, 20, 0xFFFFFFFF, "MiniHud");
        for(IConfigBase config : fi.dy.masa.minihud.config.Configs.Generic.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                y += 15;
                this.addLabel(x + 10, y, 100, 20, 0xFFFFFFFF, config.getName());
            }
        }
        for(IConfigBase config : fi.dy.masa.minihud.config.Configs.Colors.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                y += 15;
                this.addLabel(x + 10, y, 100, 20, 0xFFFFFFFF, config.getName());
            }
        }

        y += 20;

        this.addLabel(x, y, 100, 20, 0xFFFFFFFF, "Litematica");
        for(IConfigBase config : fi.dy.masa.litematica.config.Configs.Generic.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                y += 15;
                this.addLabel(x + 10, y, 100, 20, 0xFFFFFFFF, config.getName());
            }
        }
        for(IConfigBase config : fi.dy.masa.litematica.config.Configs.Colors.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                y += 15;
                this.addLabel(x + 10, y, 100, 20, 0xFFFFFFFF, config.getName());
            }
        }
        for(IConfigBase config : fi.dy.masa.litematica.config.Configs.Visuals.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                y += 15;
                this.addLabel(x + 10, y, 100, 20, 0xFFFFFFFF, config.getName());
            }
        }
        this.height = y;
    }

    public static class ButtonListenerChangeMenu implements IButtonActionListener {
        private final ButtonType type;
        @Nullable
        private final Screen parent;
        public ButtonListenerChangeMenu(ButtonType type, @Nullable Screen parent) {
            this.type = type;
            this.parent = parent;
        }
        @Override
        public void actionPerformedWithButton(ButtonBase buttonBase, int i) {
            if (this.type == ButtonType.SUBMIT) {
                System.out.println(TextFieldListener.INSTANCE_PATH);

//                PackManager modpack = new PackManager(TextFieldListener.URL);
//                PackManager.packManagerThread thread = new PackManager.packManagerThread(modpack);
//                thread.start();
                return;
            }
        }

        public enum ButtonType {
            SUBMIT ("Submit");
            private final String label;

            ButtonType(String labelKey) {
                this.label = labelKey;
            }

            public String getLabelKey() {
                return this.label;
            }

            public String getDisplayName() {
                return this.getLabelKey();
            }
        }
    }


    public static class TextFieldListener implements ITextFieldListener<GuiTextFieldGeneric> {
        public static String INSTANCE_PATH = "";
        @Override
        public boolean onTextChange(GuiTextFieldGeneric textField) {
            INSTANCE_PATH = textField.getText();
            return false;
        }
    }
}
