package org.taichiserver.taichitweaks.config;

import fi.dy.masa.litematica.gui.Icons;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.interfaces.ITextFieldListener;
import fi.dy.masa.malilib.gui.widgets.WidgetCheckBox;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.taichiserver.taichitweaks.PackMigrator.PackMigrator;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PackMigratorGui extends GuiBase {
    public PackMigratorGui() {
        this.title = "PackMigrator";
    }
    @Override
    public void initGui() {
        super.initGui();

        int x = 12;
        int y = 50;

        this.addLabel(x, y, 100, 20, 0xFFFFFFFF, "folder");

        y += 20;

        MinecraftClient mc = MinecraftClient.getInstance();
        GuiTextFieldGeneric textField = new GuiTextFieldGeneric(x, y, 400, 20, mc.textRenderer);
        this.addTextField(textField, new TextFieldListener());

        y += 40;

        ButtonListenerChangeMenu.ButtonType buttonType = ButtonListenerChangeMenu.ButtonType.SUBMIT;
        ButtonGeneric button = new ButtonGeneric(x+50, y, 300, 20, buttonType.getDisplayName());
        this.addButton(button, new ButtonListenerChangeMenu(button, buttonType, this));

        y += 20;

        List<String> copyOptions = new ArrayList<>();
        copyOptions.add("options.txt");
        copyOptions.add("hotbar.nbt");
        copyOptions.add("config");
        copyOptions.add("schematics");
        copyOptions.add("screenshot");
        copyOptions.add("shaderpacks");
        copyOptions.add("saves");
        copyOptions.add("resourcepacks");
        copyOptions.add("replay_recordings");
        copyOptions.add("itemscroller");
        copyOptions.add("XaeroWorldMap");
        copyOptions.add("XaeroWaypoints");
        for(String copyOption : copyOptions) {
            y += 20;
            WidgetCheckBox cb = new WidgetCheckBox(x, y, Icons.CHECKBOX_UNSELECTED, Icons.CHECKBOX_SELECTED, copyOption);
            cb.setChecked(false, false);
            cb.setListener(new CheckBoxListener(copyOption));
            this.addWidget(cb);
        }
    }

    public static class ButtonListenerChangeMenu implements IButtonActionListener {
        private final ButtonGeneric button;
        private final ButtonType type;
        @Nullable
        private final Screen parent;
        public ButtonListenerChangeMenu(ButtonGeneric button, ButtonType type, @Nullable Screen parent) {
            this.button = button;
            this.type = type;
            this.parent = parent;
        }
        @Override
        public void actionPerformedWithButton(ButtonBase buttonBase, int i) {
            if (this.type == ButtonType.SUBMIT) {
                button.setEnabled(false);
                PackMigrator thread = new PackMigrator(button, Paths.get(TextFieldListener.INSTANCE_PATH));
                thread.start();
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

    public static class CheckBoxListener implements ISelectionListener<WidgetCheckBox> {
        public static Map<String,Boolean> options = new HashMap<>();
        private String option = "";
        public CheckBoxListener(String opt){
            this.option = opt;
        }
        @Override
        public void onSelectionChange(WidgetCheckBox widgetCheckBox) {
            options.put(this.option, widgetCheckBox.isChecked());
        }
    }
}
