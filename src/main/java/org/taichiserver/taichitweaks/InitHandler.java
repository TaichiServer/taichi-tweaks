package org.taichiserver.taichitweaks;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import org.taichiserver.taichitweaks.config.ConfigCallbacks;
import org.taichiserver.taichitweaks.config.Configs;

public class InitHandler implements IInitializationHandler
{
    @Override
    public void registerModHandlers()
    {
        ConfigManager.getInstance().registerConfigHandler(TaichiTweaks.MOD_ID, new Configs());
        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());

        ConfigCallbacks.init();
    }
}