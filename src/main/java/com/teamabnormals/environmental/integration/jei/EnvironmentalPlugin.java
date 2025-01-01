package com.teamabnormals.environmental.integration.jei;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalTiers.EnvironmentalArmorMaterials;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.stream.Stream;

@JeiPlugin
public class EnvironmentalPlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(Environmental.MOD_ID, Environmental.MOD_ID);
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, EnvironmentalItems.SLABFISH_BUCKET.get(), (stack, context) -> stack.getTag() != null && stack.getTag().contains("SlabfishType", Tag.TAG_STRING) ? stack.getTag().getString("SlabfishType") : IIngredientSubtypeInterpreter.NONE);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(RecipeTypes.ANVIL, getRepairRecipes(registration.getVanillaRecipeFactory()).toList());
	}

	private static Stream<RepairData> getRepairData() {
		return Stream.of(
				new RepairData(EnvironmentalArmorMaterials.YAK.getRepairIngredient(),
						new ItemStack(EnvironmentalItems.YAK_PANTS.get())
				)
		);
	}

	private static Stream<IJeiAnvilRecipe> getRepairRecipes(IVanillaRecipeFactory vanillaRecipeFactory) {
		return getRepairData().flatMap(repairData -> getRepairRecipes(repairData, vanillaRecipeFactory));
	}

	private static Stream<IJeiAnvilRecipe> getRepairRecipes(RepairData repairData, IVanillaRecipeFactory vanillaRecipeFactory) {
		Ingredient repairIngredient = repairData.getRepairIngredient();
		List<ItemStack> repairables = repairData.getRepairables();

		List<ItemStack> repairMaterials = List.of(repairIngredient.getItems());

		return repairables.stream().mapMulti((itemStack, consumer) -> {
			ItemStack damagedThreeQuarters = itemStack.copy();
			damagedThreeQuarters.setDamageValue(damagedThreeQuarters.getMaxDamage() * 3 / 4);
			ItemStack damagedHalf = itemStack.copy();
			damagedHalf.setDamageValue(damagedHalf.getMaxDamage() / 2);

			IJeiAnvilRecipe repairWithSame = vanillaRecipeFactory.createAnvilRecipe(List.of(damagedThreeQuarters), List.of(damagedThreeQuarters), List.of(damagedHalf));
			consumer.accept(repairWithSame);

			if (!repairMaterials.isEmpty()) {
				ItemStack damagedFully = itemStack.copy();
				damagedFully.setDamageValue(damagedFully.getMaxDamage());
				IJeiAnvilRecipe repairWithMaterial = vanillaRecipeFactory.createAnvilRecipe(List.of(damagedFully), repairMaterials, List.of(damagedThreeQuarters));
				consumer.accept(repairWithMaterial);
			}
		});
	}

	private static class RepairData {
		private final Ingredient repairIngredient;
		private final List<ItemStack> repairables;

		public RepairData(Ingredient repairIngredient, ItemStack... repairables) {
			this.repairIngredient = repairIngredient;
			this.repairables = List.of(repairables);
		}

		public Ingredient getRepairIngredient() {
			return repairIngredient;
		}

		public List<ItemStack> getRepairables() {
			return repairables;
		}
	}
}