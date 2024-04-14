package org.taichiserver.taichitweaks.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBase;
import me.fallenbreath.tweakermore.config.TweakerMoreOption;
import me.fallenbreath.tweakermore.config.options.TweakerMoreIConfigBase;

import java.util.ArrayList;
import java.util.List;

public class MasaConfigsHint {

    public static ImmutableList<IConfigBase> getOptions(){
        List<IConfigBase> optionsList = new ArrayList<IConfigBase>();

        for(IConfigBase config : fi.dy.masa.tweakeroo.config.Configs.Generic.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.tweakeroo.config.Configs.Fixes.OPTIONS) {
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.tweakeroo.config.Configs.Lists.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.tweakeroo.config.Configs.Disable.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(TweakerMoreOption option : me.fallenbreath.tweakermore.config.TweakerMoreConfigs.getAllOptions()){
            TweakerMoreIConfigBase config = option.getConfig();
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.minihud.config.Configs.Generic.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.minihud.config.Configs.Colors.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.litematica.config.Configs.Generic.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.litematica.config.Configs.Visuals.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.litematica.config.Configs.InfoOverlays.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.litematica.config.Configs.Colors.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.itemscroller.config.Configs.Generic.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.itemscroller.config.Configs.Generic.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        for(IConfigBase config : fi.dy.masa.itemscroller.config.Configs.Toggles.OPTIONS){
            if(((ConfigBase) config).isModified()) {
                optionsList.add(config);
            }
        }
        return ImmutableList.copyOf(optionsList);
    }
}
