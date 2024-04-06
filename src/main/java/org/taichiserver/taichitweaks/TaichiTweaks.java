package org.taichiserver.taichitweaks;

import fi.dy.masa.tweakeroo.config.FeatureToggle;
import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taichiserver.taichitweaks.config.Configs;

public class TaichiTweaks implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("TaichiTweaks");
    public static final String MOD_ID = "taichi-tweaks";
    public static final String VERSION = "1.0.0-SNAPSHOT";


    @Override
    public void onInitializeClient() {
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (Configs.Generic.GAMMA_OVERRIDE_FIX.getBooleanValue()) {
                FeatureToggle.TWEAK_GAMMA_OVERRIDE.onValueChanged();
            }
        });
    }
}