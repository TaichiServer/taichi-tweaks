package org.taichiserver.taichitweaks.util.items;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ItemTypeEquals {
    public static boolean isShulkerBox(Item item) {
        return item.equals(Items.SHULKER_BOX) ||
                item.equals(Items.BLACK_SHULKER_BOX) ||
                item.equals(Items.BLUE_SHULKER_BOX) ||
                item.equals(Items.BROWN_SHULKER_BOX) ||
                item.equals(Items.CYAN_SHULKER_BOX) ||
                item.equals(Items.GRAY_SHULKER_BOX) ||
                item.equals(Items.GREEN_SHULKER_BOX) ||
                item.equals(Items.LIGHT_BLUE_SHULKER_BOX) ||
                item.equals(Items.LIGHT_GRAY_SHULKER_BOX) ||
                item.equals(Items.LIME_SHULKER_BOX) ||
                item.equals(Items.MAGENTA_SHULKER_BOX) ||
                item.equals(Items.ORANGE_SHULKER_BOX) ||
                item.equals(Items.PINK_SHULKER_BOX) ||
                item.equals(Items.PURPLE_SHULKER_BOX) ||
                item.equals(Items.RED_SHULKER_BOX) ||
                item.equals(Items.WHITE_SHULKER_BOX) ||
                item.equals(Items.YELLOW_SHULKER_BOX);
    }
}
