package io.github.asnmodding.faithmod.world.gen;

import io.github.asnmodding.faithmod.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;

import java.util.Random;

public class MysticalFlowerFeature extends DefaultFlowersFeature
{
//    public static final BlockClusterFeatureConfig DEFAULT_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).addWeightedBlockstate(POPPY, 2).addWeightedBlockstate(DANDELION, 1), new SimpleBlockPlacer())).tries(64).build();

//    public static final FlowersFeature<BlockClusterFeatureConfig> FLOWER = register("flower", new DefaultFlowersFeature(BlockClusterFeatureConfig::deserialize));

//          biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(DEFAULT_FLOWER_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(2))));

    public static final BlockClusterFeatureConfig DEFAULT_MYSTICAL_FLOWER_CONFIG = new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider().addWeightedBlockstate(ModBlocks.MYSTICAL_FLOWER.getDefaultState(), 2), new SimpleBlockPlacer()).tries(64).build();

    public MysticalFlowerFeature()
    {
        super(dynamic -> DEFAULT_MYSTICAL_FLOWER_CONFIG);
    }

    @Override
    public BlockState getFlowerToPlace(Random random, BlockPos pos, BlockClusterFeatureConfig blockClusterFeatureConfig)
    {
        return blockClusterFeatureConfig.stateProvider.getBlockState(random, pos);
    }
}
