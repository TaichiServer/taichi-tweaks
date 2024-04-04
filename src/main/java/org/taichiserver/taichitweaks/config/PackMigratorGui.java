package org.taichiserver.taichitweaks.config;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ITextFieldListener;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Objects;


public class PackMigratorGui extends GuiBase {
    public PackMigratorGui() {
        this.title = "PackMigrator";
    }
    @Override
    public void initGui() {
        super.initGui();

        int x = 12;
        int y = 50;

        this.addLabel(x, y, 100, 20, 0xFFFFFFFF, "");

        y += 20;

        MinecraftClient mc = MinecraftClient.getInstance();
        GuiTextFieldGeneric textField = new GuiTextFieldGeneric(x, y, 400, 20, mc.textRenderer);
        this.addTextField(textField, new TextFieldListener());

        y += 40;

        ButtonListenerChangeMenu.ButtonType buttonType = ButtonListenerChangeMenu.ButtonType.LOAD_PACK;
        ButtonGeneric button = new ButtonGeneric(x, y, 100, 20, buttonType.getDisplayName());
        this.addButton(button, new ButtonListenerChangeMenu(buttonType, this));
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
            //LOGGER.info("actionPerformedWithButton(): {}, {}", this.type.getDisplayName(), i);
            GuiBase gui = null;

            if (Objects.requireNonNull(this.type) == ButtonType.LOAD_PACK) {
//                PackManager modpack = new PackManager(TextFieldListener.URL);
//                PackManager.packManagerThread thread = new PackManager.packManagerThread(modpack);
//                thread.start();
                return;
            }

            if (gui != null) {
                gui.setParent(this.parent);
                GuiBase.openGui(gui);
            }
        }

        public enum ButtonType {
            LOAD_PACK ("Load");
            private final String label;

            private ButtonType(String labelKey) {
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
        public static String URL = "";
        @Override
        public boolean onTextChange(GuiTextFieldGeneric textField) {
            URL = textField.getText();
            return false;
        }
    }
}
