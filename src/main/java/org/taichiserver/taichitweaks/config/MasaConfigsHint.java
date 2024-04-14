package org.taichiserver.taichitweaks.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBase;

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
        return ImmutableList.copyOf(optionsList);
    }
}
