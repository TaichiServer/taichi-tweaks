package org.taichiserver.taichitweaks.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.taichiserver.taichitweaks.config.Configs;

public class schematicBlockPlacementRestrictionBlocksMap {
    public static boolean checker(Block schematicBlock, Block blockToPlace){
        return schematicBlock == blockToPlace ||
                Configs.Generic.SCHEMATIC_BLOCK_PLACEMENT_RESTRICTION_BLOCKS_MAP.getStrings()
                .stream()
                .map(x -> x.split("\s*,\s*"))
                .map(x -> ImmutableList.of(
                        Registries.BLOCK.get(new Identifier(x[0])),
                        Registries.BLOCK.get(new Identifier(x[1]))
                ))
                .anyMatch(x -> x.get(0) == schematicBlock && x.get(1) == blockToPlace);
    }
}
