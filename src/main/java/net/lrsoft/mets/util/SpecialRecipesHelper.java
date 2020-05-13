package net.lrsoft.mets.util;

import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.recipe.BasicMachineRecipeManager;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.minecraft.item.ItemStack;

public class SpecialRecipesHelper {
	public static IBasicMachineRecipeManager neutronPolymerizerRecipes = new BasicMachineRecipeManager();;
	
	static 
	{
		neutronPolymerizerRecipes.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemCraftingManager.nano_living_metal)),
				null, false,new ItemStack[] {new ItemStack(ItemCraftingManager.neutron_plate)});
	}
}
