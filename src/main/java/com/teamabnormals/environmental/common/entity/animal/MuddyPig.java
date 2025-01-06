package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.other.EnvironmentalDataProcessors;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent.FinalizeSpawn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class MuddyPig {
	public static final int MAX_DRYING_TIME = 36000;

	public static boolean enabled() {
		return EnvironmentalConfig.COMMON.muddyPigs.get();
	}

	public static boolean isMuddy(Pig pig) {
		IDataManager data = (IDataManager) pig;
		return data.getValue(EnvironmentalDataProcessors.IS_MUDDY);
	}

	public static int getDryingTime(Pig pig) {
		IDataManager data = (IDataManager) pig;
		return data.getValue(EnvironmentalDataProcessors.MUD_DRYING_TIME);
	}

	public static void setMuddy(Pig pig, boolean muddy) {
		IDataManager data = (IDataManager) pig;
		data.setValue(EnvironmentalDataProcessors.IS_MUDDY, muddy);
	}

	public static void setDryingTime(Pig pig, int amount) {
		IDataManager data = (IDataManager) pig;
		data.setValue(EnvironmentalDataProcessors.MUD_DRYING_TIME, amount);
	}

	public static void updateDryingTime(Pig pig, int amount) {
		setDryingTime(pig, Mth.clamp(0, getDryingTime(pig) + amount, MAX_DRYING_TIME));
	}

	public static boolean canGetWet(Pig pig) {
		return (pig.isInWaterOrBubble() && EnvironmentalConfig.COMMON.muddyPigsGetWetInWater.get()) || ((pig.isInWaterOrRain() && !pig.isInWater()) && EnvironmentalConfig.COMMON.muddyPigsGetWetInRain.get());
	}

	public static void removeDecoration(Pig pig) {
		IDataManager data = (IDataManager) pig;
		data.setValue(EnvironmentalDataProcessors.MUDDY_PIG_DECORATION, new ResourceLocation("empty"));
	}

	public static boolean isDry(Pig pig) {
		return getDryingTime(pig) < 60;
	}

	public static Optional<Item> getDecoration(Pig pig) {
		IDataManager data = (IDataManager) pig;
		if (isMuddy(pig)) {
			ResourceLocation decoration = data.getValue(EnvironmentalDataProcessors.MUDDY_PIG_DECORATION);
			Item item = ForgeRegistries.ITEMS.getValue(decoration);
			if (item instanceof BlockItem) {
				return Optional.of(item);
			}
		}

		return Optional.empty();
	}

	@SubscribeEvent
	public static void onLivingSpawn(FinalizeSpawn event) {
		Mob entity = event.getEntity();
		ServerLevelAccessor level = event.getLevel();
		if (event.getResult() != Result.DENY) {
			RandomSource random = level.getRandom();
			if (entity instanceof Pig pig && enabled() && EnvironmentalConfig.COMMON.naturalMuddyPigs.get() && level.getBiome(entity.blockPosition()).is(EnvironmentalBiomeTags.HAS_MUDDY_PIG)) {
				IDataManager data = (IDataManager) entity;
				setMuddy(pig, true);
				setDryingTime(pig, MAX_DRYING_TIME);
				if (random.nextFloat() < EnvironmentalConfig.COMMON.muddyPigDecorationChance.get()) {
					Optional<Item> item = ForgeRegistries.ITEMS.tags().getTag(EnvironmentalItemTags.SPAWNS_ON_MUDDY_PIG).getRandomElement(random);
					item.ifPresent(value -> data.setValue(EnvironmentalDataProcessors.MUDDY_PIG_DECORATION, ForgeRegistries.ITEMS.getKey(value)));
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingTick(LivingTickEvent event) {
		Entity entity = event.getEntity();
		Level level = entity.getCommandSenderWorld();

		if (entity instanceof Pig pig && entity.isAlive() && isMuddy(pig)) {
			int mudDryingTime = getDryingTime(pig);
			if (canGetWet(pig)) {
				updateDryingTime(pig, 1);
			} else if (EnvironmentalConfig.COMMON.muddyPigsDryOverTime.get() && (!EnvironmentalConfig.COMMON.muddyPigsOnlyDryInTheNether.get() || level.dimensionType().ultraWarm())) {
				updateDryingTime(pig, -1);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityInteract(EntityInteract event) {
		Player player = event.getEntity();
		InteractionHand hand = event.getHand();
		ItemStack stack = event.getItemStack();
		Entity target = event.getTarget();
		Level level = event.getLevel();
		RandomSource random = level.getRandom();

		if (target instanceof Pig pig && target.isAlive()) {
			IDataManager data = ((IDataManager) target);
			if (enabled() && isMuddy(pig)) {
				boolean dried = getDryingTime(pig) <= 0;

				Optional<Item> decoration = getDecoration(pig);
				if (decoration.isEmpty()) {
					removeDecoration(pig);
				}

				boolean allowDecorating = EnvironmentalConfig.COMMON.decoratableMuddyPigs.get();

				if (allowDecorating) {
					if (decoration.isEmpty()) {
						if (stack.is(EnvironmentalItemTags.MUDDY_PIG_DECORATIONS)) {
							data.setValue(EnvironmentalDataProcessors.MUDDY_PIG_DECORATION, ForgeRegistries.ITEMS.getKey(stack.getItem()));
							level.playSound(null, target, dried ? SoundEvents.PACKED_MUD_PLACE : SoundEvents.MUD_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
							if (!event.getEntity().isCreative()) stack.shrink(1);
							event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
							event.setCanceled(true);
						}
					} else if (stack.canPerformAction(ToolActions.SHEARS_CARVE)) {
						level.playSound(null, target, SoundEvents.SNOW_GOLEM_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
						target.gameEvent(GameEvent.SHEAR, player);
						if (!level.isClientSide()) {
							ItemEntity item = target.spawnAtLocation(new ItemStack(decoration.get()), 1.0F);
							item.setDeltaMovement(item.getDeltaMovement().add((random.nextFloat() - random.nextFloat()) * 0.1F, random.nextFloat() * 0.05F, (random.nextFloat() - random.nextFloat()) * 0.1F));
							stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));
						}
						removeDecoration(pig);
						event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
						event.setCanceled(true);
					}
				}

				if (stack.is(EnvironmentalItemTags.MUDDY_PIG_DRYING_ITEMS) && !dried && !canGetWet(pig) && !pig.getBlockStateOn().is(Blocks.MUD)) {
					level.playSound(null, target, SoundEvents.PACKED_MUD_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
					if (!event.getEntity().isCreative()) stack.shrink(1);
					setDryingTime(pig, 0);
					event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
					event.setCanceled(true);
				} else if (stack.is(BlueprintItemTags.BUCKETS_WATER)) {
					level.playSound(null, target, SoundEvents.GENERIC_SPLASH, SoundSource.PLAYERS, 1.0F, 1.0F);
					player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, stack.getCraftingRemainingItem()));
					player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
					if (!level.isClientSide) {
						ServerLevel serverlevel = (ServerLevel) level;
						for (int i = 0; i < 5; ++i) {
							serverlevel.sendParticles(ParticleTypes.SPLASH, target.getX() + level.random.nextDouble(), target.getY() + 1, target.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
						}
					}

					setMuddy(pig, false);
					setDryingTime(pig, 0);

					if (decoration.isPresent()) {
						if (!level.isClientSide()) {
							ItemEntity item = target.spawnAtLocation(new ItemStack(decoration.get()), 1.0F);
							item.setDeltaMovement(item.getDeltaMovement().add((random.nextFloat() - random.nextFloat()) * 0.1F, random.nextFloat() * 0.05F, (random.nextFloat() - random.nextFloat()) * 0.1F));
							stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));
						}
						removeDecoration(pig);
					}

					level.playSound(null, target, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
					event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity instanceof Pig pig && getDecoration(pig).isPresent()) {
			pig.spawnAtLocation(getDecoration(pig).get());
		}
	}
}
