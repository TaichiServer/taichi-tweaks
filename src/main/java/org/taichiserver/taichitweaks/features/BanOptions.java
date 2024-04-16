package org.taichiserver.taichitweaks.features;

public class BanOptions {
    public static String checker(){
        if(fi.dy.masa.tweakeroo.config.Configs.Generic.FAST_LEFT_CLICK_COUNT.getIntegerValue() > 2) return "[Tweakeroo]: tweakFastLeftClick";
        if(fi.dy.masa.tweakeroo.config.Configs.Generic.FAST_RIGHT_CLICK_COUNT.getIntegerValue() > 2) return "[Tweakeroo]: tweakFastRightClick";
        if(fi.dy.masa.tweakeroo.config.FeatureToggle.TWEAK_FLEXIBLE_BLOCK_PLACEMENT.getBooleanValue()) return "[Tweakeroo]: tweakFlexibleBlockPlacement";
        if(fi.dy.masa.tweakeroo.config.FeatureToggle.TWEAK_ACCURATE_BLOCK_PLACEMENT.getBooleanValue()) return "[Tweakeroo]: tweakAccurateBlockPlacement";
        if(fi.dy.masa.tweakeroo.config.FeatureToggle.TWEAK_BLOCK_REACH_OVERRIDE.getBooleanValue()) return "[Tweakeroo]: tweakBlockReachOverride";
        if(fi.dy.masa.tweakeroo.config.FeatureToggle.TWEAK_NO_SNEAK_SLOWDOWN.getBooleanValue()) return "[Tweakeroo]: tweakNoSneakSlowdown";
        if(fi.dy.masa.tweakeroo.config.Configs.Disable.DISABLE_SLIME_BLOCK_SLOWDOWN.getBooleanValue()) return "[Tweakeroo]: disableSlimeBlockSlowdown";

        if(fi.dy.masa.litematica.config.Configs.Generic.EASY_PLACE_MODE.getBooleanValue()) return "[Litematica]: easyPlaceMode";

        if(me.fallenbreath.tweakermore.config.TweakerMoreConfigs.DISABLE_SLIME_BLOCK_BOUNCING.getBooleanValue()) return "[TweakerMore]: disableSlimeBlockBouncing";

        return null;
    }
}
