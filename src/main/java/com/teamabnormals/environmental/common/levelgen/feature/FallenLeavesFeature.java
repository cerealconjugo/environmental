package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class FallenLeavesFeature extends Feature<NoneFeatureConfiguration> {
	public FallenLeavesFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel worldIn = context.level();
		Random rand = context.random();
		BlockPos pos = context.origin();
		NoneFeatureConfiguration config = context.config();

		int i = 0;
		BlockState blockstate = EnvironmentalBlocks.CHERRY_LEAF_CARPET.get().defaultBlockState();

		for (int x = -3; x <= 3; ++x) {
			for (int z = -3; z <= 3; ++z) {
				if (Math.abs(x) < 2 || Math.abs(z) < 2) {
					for (int y = -3; y <= 3; ++y) {
						BlockPos blockpos = pos.offset(x, y, z);
						if (rand.nextInt(3) > 0 && worldIn.isEmptyBlock(blockpos) && blockpos.getY() < worldIn.getMaxBuildHeight() && worldIn.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK) {
							worldIn.setBlock(blockpos, blockstate, 2);
							++i;
						}
					}
				}
			}
		}

		return i > 0;
	}
}