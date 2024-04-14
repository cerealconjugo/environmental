package com.teamabnormals.environmental.core.registry;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.item.BlueprintRecordItem;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import com.teamabnormals.environmental.common.item.*;
import com.teamabnormals.environmental.common.item.explorer.ArchitectBeltItem;
import com.teamabnormals.environmental.common.item.explorer.HealerPouchItem;
import com.teamabnormals.environmental.common.item.explorer.ThiefHoodItem;
import com.teamabnormals.environmental.common.item.explorer.WandererBootsItem;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalTiers.EnvironmentalArmorMaterials;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBannerPatternTags;
import com.teamabnormals.environmental.integration.boatload.EnvironmentalBoatTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalItems {
	public static final ItemSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getItemSubHelper();

	public static final RegistryObject<Item> THIEF_HOOD = HELPER.createItem("thief_hood", () -> new ThiefHoodItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> HEALER_POUCH = HELPER.createItem("healer_pouch", () -> new HealerPouchItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ARCHITECT_BELT = HELPER.createItem("architect_belt", () -> new ArchitectBeltItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> WANDERER_BOOTS = HELPER.createItem("wanderer_boots", () -> new WandererBootsItem(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> CHERRIES = HELPER.createItem("cherries", () -> new Item(new Item.Properties().food(EnvironmentalFoods.CHERRIES)));
	public static final RegistryObject<Item> CHERRY_PIE = HELPER.createItem("cherry_pie", () -> new Item(new Item.Properties().food(EnvironmentalFoods.CHERRY_PIE)));
	public static final RegistryObject<Item> VENISON = HELPER.createItem("venison", () -> new Item(new Item.Properties().food(EnvironmentalFoods.VENISON)));
	public static final RegistryObject<Item> COOKED_VENISON = HELPER.createItem("cooked_venison", () -> new Item(new Item.Properties().food(EnvironmentalFoods.COOKED_VENISON)));

	public static final RegistryObject<Item> KOI = HELPER.createItem("koi", () -> new Item(new Item.Properties().food(EnvironmentalFoods.KOI)));

	public static final RegistryObject<Item> DUCK = HELPER.createItem("duck", () -> new Item(new Item.Properties().food(EnvironmentalFoods.DUCK)));
	public static final RegistryObject<Item> COOKED_DUCK = HELPER.createItem("cooked_duck", () -> new Item(new Item.Properties().food(EnvironmentalFoods.COOKED_DUCK)));
	public static final RegistryObject<Item> DUCK_EGG = HELPER.createItem("duck_egg", () -> new DuckEggItem(new Item.Properties().stacksTo(16)));

	public static final RegistryObject<Item> TRUFFLE = HELPER.createItem("truffle", () -> new Item(new Item.Properties().food(EnvironmentalFoods.TRUFFLE)));

	public static final RegistryObject<Item> MUD_BALL = HELPER.createItem("mud_ball", () -> new MudBallItem(new Item.Properties()));

	public static final RegistryObject<Item> YAK_HAIR = HELPER.createItem("yak_hair", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> YAK_PANTS = HELPER.createItem("yak_pants", () -> new YakPantsItem(EnvironmentalArmorMaterials.YAK, ArmorItem.Type.LEGGINGS, new Item.Properties()));

	public static final Pair<RegistryObject<Item>, RegistryObject<Item>> WILLOW_BOAT = HELPER.createBoatAndChestBoatItem("willow", EnvironmentalBlocks.WILLOW_PLANKS);
	public static final RegistryObject<Item> WILLOW_FURNACE_BOAT = HELPER.createItem("willow_furnace_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.WILLOW_FURNACE_BOAT : () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LARGE_WILLOW_BOAT = HELPER.createItem("large_willow_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.LARGE_WILLOW_BOAT : () -> new Item(new Item.Properties()));

	public static final Pair<RegistryObject<Item>, RegistryObject<Item>> PINE_BOAT = HELPER.createBoatAndChestBoatItem("pine", EnvironmentalBlocks.PINE_PLANKS);
	public static final RegistryObject<Item> PINE_FURNACE_BOAT = HELPER.createItem("pine_furnace_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.PINE_FURNACE_BOAT : () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LARGE_PINE_BOAT = HELPER.createItem("large_pine_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.LARGE_PINE_BOAT : () -> new Item(new Item.Properties()));

	public static final Pair<RegistryObject<Item>, RegistryObject<Item>> WISTERIA_BOAT = HELPER.createBoatAndChestBoatItem("wisteria", EnvironmentalBlocks.WISTERIA_PLANKS);
	public static final RegistryObject<Item> WISTERIA_FURNACE_BOAT = HELPER.createItem("wisteria_furnace_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.WISTERIA_FURNACE_BOAT : () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LARGE_WISTERIA_BOAT = HELPER.createItem("large_wisteria_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.LARGE_WISTERIA_BOAT : () -> new Item(new Item.Properties()));

	public static final Pair<RegistryObject<Item>, RegistryObject<Item>> CHERRY_BOAT = HELPER.createBoatAndChestBoatItem("cherry", EnvironmentalBlocks.CHERRY_PLANKS);
	public static final RegistryObject<Item> CHERRY_FURNACE_BOAT = HELPER.createItem("cherry_furnace_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.CHERRY_FURNACE_BOAT : () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LARGE_CHERRY_BOAT = HELPER.createItem("large_cherry_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.LARGE_CHERRY_BOAT : () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> CATTAIL_FLUFF = HELPER.createItem("cattail_fluff", () -> new ItemNameBlockItem(EnvironmentalBlocks.CATTAIL_SPROUT.get(), new Item.Properties()));
	public static final RegistryObject<Item> DUCKWEED = HELPER.createItem("duckweed", () -> new PlaceOnWaterBlockItem(EnvironmentalBlocks.DUCKWEED.get(), new Item.Properties()));

	public static final RegistryObject<Item> MUSIC_DISC_LEAVING_HOME = HELPER.createItem("music_disc_leaving_home", () -> new BlueprintRecordItem(6, EnvironmentalSoundEvents.MUSIC_DISC_LEAVING_HOME, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 144));
	public static final RegistryObject<Item> MUSIC_DISC_SLABRAVE = HELPER.createItem("music_disc_slabrave", () -> new BlueprintRecordItem(13, EnvironmentalSoundEvents.MUSIC_DISC_SLABRAVE, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 111));
	public static final RegistryObject<Item> LUMBERER_BANNER_PATTERN = HELPER.createItem("lumberer_banner_pattern", () -> new BannerPatternItem(EnvironmentalBannerPatternTags.PATTERN_ITEM_LUMBERER, new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> LARGE_LILY_PAD = HELPER.createItem("large_lily_pad", () -> new LargeLilyPadItem(new Item.Properties()));
	public static final RegistryObject<Item> GIANT_LILY_PAD = HELPER.createItem("giant_lily_pad", () -> new GiantLilyPadItem(new Item.Properties()));

	public static final RegistryObject<Item> SLABFISH_BUCKET = HELPER.createItem("slabfish_bucket", () -> new SlabfishBucketItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> KOI_BUCKET = HELPER.createItem("koi_bucket", () -> new KoiBucketItem(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> YELLOW_HIBISCUS = HELPER.createItem("yellow_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.YELLOW_HIBISCUS.get(), EnvironmentalBlocks.YELLOW_WALL_HIBISCUS.get(), new Item.Properties()));
	public static final RegistryObject<Item> ORANGE_HIBISCUS = HELPER.createItem("orange_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.ORANGE_HIBISCUS.get(), EnvironmentalBlocks.ORANGE_WALL_HIBISCUS.get(), new Item.Properties()));
	public static final RegistryObject<Item> RED_HIBISCUS = HELPER.createItem("red_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.RED_HIBISCUS.get(), EnvironmentalBlocks.RED_WALL_HIBISCUS.get(), new Item.Properties()));
	public static final RegistryObject<Item> PINK_HIBISCUS = HELPER.createItem("pink_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.PINK_HIBISCUS.get(), EnvironmentalBlocks.PINK_WALL_HIBISCUS.get(), new Item.Properties()));
	public static final RegistryObject<Item> MAGENTA_HIBISCUS = HELPER.createItem("magenta_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.MAGENTA_HIBISCUS.get(), EnvironmentalBlocks.MAGENTA_WALL_HIBISCUS.get(), new Item.Properties()));
	public static final RegistryObject<Item> PURPLE_HIBISCUS = HELPER.createItem("purple_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.PURPLE_HIBISCUS.get(), EnvironmentalBlocks.PURPLE_WALL_HIBISCUS.get(), new Item.Properties()));

	public static final RegistryObject<ForgeSpawnEggItem> SLABFISH_SPAWN_EGG = HELPER.createSpawnEggItem("slabfish", EnvironmentalEntityTypes.SLABFISH::get, 6263617, 13940616);
	public static final RegistryObject<ForgeSpawnEggItem> DUCK_SPAWN_EGG = HELPER.createSpawnEggItem("duck", EnvironmentalEntityTypes.DUCK::get, 1138489, 16754947);
	public static final RegistryObject<ForgeSpawnEggItem> DEER_SPAWN_EGG = HELPER.createSpawnEggItem("deer", EnvironmentalEntityTypes.DEER::get, 10057035, 15190442);
	public static final RegistryObject<ForgeSpawnEggItem> REINDEER_SPAWN_EGG = HELPER.createSpawnEggItem("reindeer", EnvironmentalEntityTypes.REINDEER::get, 7360069, 15388873);
	public static final RegistryObject<ForgeSpawnEggItem> YAK_SPAWN_EGG = HELPER.createSpawnEggItem("yak", EnvironmentalEntityTypes.YAK::get, 5392966, 8607802);
	public static final RegistryObject<ForgeSpawnEggItem> KOI_SPAWN_EGG = HELPER.createSpawnEggItem("koi", EnvironmentalEntityTypes.KOI::get, 5392966, 16754947);
	public static final RegistryObject<ForgeSpawnEggItem> TAPIR_SPAWN_EGG = HELPER.createSpawnEggItem("tapir", EnvironmentalEntityTypes.TAPIR::get, 0x38373D, 0xC6CACE);
	public static final RegistryObject<ForgeSpawnEggItem> ZEBRA_SPAWN_EGG = HELPER.createSpawnEggItem("zebra", EnvironmentalEntityTypes.ZEBRA::get, 0xD4CBC6, 0x342E2B);
	// public static final RegistryObject<ForgeSpawnEggItem> FENNEC_FOX_SPAWN_EGG = HELPER.createSpawnEggItem("fennec_fox", EnvironmentalEntityTypes.FENNEC_FOX::get, 0xFBDB9E, 0xFFFFFF);

	public static final class EnvironmentalFoods {
		public static final FoodProperties CHERRIES = new FoodProperties.Builder().nutrition(1).saturationMod(0.1F).fast().build();
		public static final FoodProperties CHERRY_PIE = new FoodProperties.Builder().nutrition(6).saturationMod(0.3F).build();

		public static final FoodProperties VENISON = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).meat().build();
		public static final FoodProperties COOKED_VENISON = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.8F).meat().build();

		public static final FoodProperties DUCK = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build();
		public static final FoodProperties COOKED_DUCK = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.3F).meat().build();

		public static final FoodProperties TRUFFLE = new FoodProperties.Builder().nutrition(16).saturationMod(1.2F).build();

		public static final FoodProperties KOI = new FoodProperties.Builder().nutrition(1).saturationMod(0.1F).build();
	}
}