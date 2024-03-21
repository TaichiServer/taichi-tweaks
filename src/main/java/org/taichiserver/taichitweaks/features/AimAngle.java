package org.taichiserver.taichitweaks.features;

import fi.dy.masa.tweakeroo.util.MiscUtils;
import net.minecraft.entity.Entity;

public class AimAngle {
    public static void init(Entity player, String angles_raw) {
        var angles = angles_raw.split("\s*,\s*");
        float yaw = Float.parseFloat(angles[0]);
        float pitch = Float.parseFloat(angles[1]);
        MiscUtils.setEntityRotations(player, yaw, pitch);
    }
}