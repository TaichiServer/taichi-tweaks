package org.taichiserver.taichitweaks.features;

import net.minecraft.util.math.BlockPos;
import org.taichiserver.taichitweaks.config.Configs;

import java.util.ArrayList;
import java.util.List;

public class SelectiveBlockRenderingArea {
    public static Boolean Enabled = false;
    public static BlockPos Pos_1;
    public static BlockPos Pos_2;
    public static void onValueChanged(){
        String AreaSelector = Configs.Generic.SELECTIVE_BLOCKS_RENDERING_AREA_SELECTOR.getStringValue();
        List<BlockPos> poss = new ArrayList<BlockPos>();

        for (String x: AreaSelector.split(" ")){
            List<Integer> nums = new ArrayList<Integer>();
            for (String y: x.split(",")){
                nums.add(Integer.parseInt(y));
            }
            poss.add(new BlockPos(nums.get(0), nums.get(1), nums.get(2)));
        }
        Pos_1 = poss.get(0);
        Pos_2 = poss.get(1);

        System.out.println("#");
        System.out.println(Pos_1);
        System.out.println(Pos_2);
        Enabled = !AreaSelector.isEmpty() && Pos_1 != null && Pos_2 != null;
    }
    public static boolean isValidPos(BlockPos pos){
        return Enabled && (
                isInner(pos.getY(), Pos_1.getY(), Pos_2.getY()) &&
                isInner(pos.getX(), Pos_1.getX(), Pos_2.getX()) &&
                isInner(pos.getZ(), Pos_1.getZ(), Pos_2.getZ())
        );
    }
    public static boolean isInner(int v1, int v2, int v3){
        int startValue;
        int endValue;

        if (v2 <= v3) { startValue = v2; endValue = v3; } else { startValue = v3; endValue = v2; }

        return v1 >= startValue && v1 <= endValue;
    }
}
