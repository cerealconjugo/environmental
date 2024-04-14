package com.teamabnormals.environmental.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.loot.modification.LootModifierProvider;
import com.teamabnormals.blueprint.common.loot.modification.modifiers.LootPoolEntriesModifier;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class EnvironmentalLootModifierProvider extends LootModifierProvider {

	public EnvironmentalLootModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Environmental.MOD_ID, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		LootItemCondition.Builder inBlossomWoods = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(EnvironmentalBiomes.BLOSSOM_WOODS));
		LootItemCondition.Builder inBlossomValleys = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(EnvironmentalBiomes.BLOSSOM_VALLEYS));
		this.entry(BuiltInLootTables.FISHING_FISH.getPath()).selects(BuiltInLootTables.FISHING_FISH).addModifier(new LootPoolEntriesModifier(false, 0, LootItem.lootTableItem(EnvironmentalItems.KOI.get()).setWeight(70).when(inBlossomWoods.or(inBlossomValleys)).build()));
		this.entry(BuiltInLootTables.SIMPLE_DUNGEON.getPath()).selects(BuiltInLootTables.SIMPLE_DUNGEON).addModifier(new LootPoolEntriesModifier(false, 0, Collections.singletonList(LootItem.lootTableItem(EnvironmentalItems.THIEF_HOOD.get()).setWeight(10).build())));
	}
}