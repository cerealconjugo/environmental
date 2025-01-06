package com.teamabnormals.environmental.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class HibiscusBlock extends AbstractHibiscusBlock {
	private final Block wallHibiscus;

	public HibiscusBlock(Supplier<MobEffect> stewEffect, int stewEffectDuration, Block wallHibiscus, Properties properties) {
		super(stewEffect, stewEffectDuration, properties);
		this.wallHibiscus = wallHibiscus;
	}

	@Override
	protected Block getWallHibiscus() {
		return this.wallHibiscus;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(BlockTags.LEAVES) || super.mayPlaceOn(state, level, pos);
	}
}