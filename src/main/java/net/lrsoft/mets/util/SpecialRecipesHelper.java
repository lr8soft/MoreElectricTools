package net.lrsoft.mets.util;

import ic2.api.item.IC2Items;
import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.ILiquidAcceptManager;
import ic2.api.recipe.ISemiFluidFuelManager;
import ic2.api.recipe.Recipes;
import ic2.core.SemiFluidFuelManager;
import ic2.core.recipe.BasicMachineRecipeManager;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.manager.FluidManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SpecialRecipesHelper {
	public static IBasicMachineRecipeManager neutronPolymerizerRecipes = new BasicMachineRecipeManager();;
	public static ISemiFluidFuelManager dieselGeneratorAcceptManager = new SemiFluidFuelManager();
	static 
	{
		neutronPolymerizerRecipes.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemCraftingManager.nano_living_metal)),
				null, false,new ItemStack[] {new ItemStack(ItemCraftingManager.neutron_plate)});
		
		neutronPolymerizerRecipes.addRecipe(Recipes.inputFactory.forStack(new ItemStack(Items.IRON_INGOT)),
				null, false,new ItemStack[] {new ItemStack(ItemCraftingManager.niobium_titanium_ingot)});
		
		neutronPolymerizerRecipes.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemCraftingManager.niobium_titanium_ingot)),
				null, false,new ItemStack[] {new ItemStack(ItemCraftingManager.nano_living_metal)});
		
		
		neutronPolymerizerRecipes.addRecipe(Recipes.inputFactory.forStack(IC2Items.getItem("crafting", "advanced_circuit")),
				null, false,new ItemStack[] {new ItemStack(ItemCraftingManager.super_circuit)});
		
		neutronPolymerizerRecipes.addRecipe(Recipes.inputFactory.forStack(IC2Items.getItem("cable","type:glass,insulation:0")),
				null, false,new ItemStack[] {new ItemStack(ItemCraftingManager.superconducting_cable)});
		
		neutronPolymerizerRecipes.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemCraftingManager.titanium_ingot)),
				null, false,new ItemStack[] {IC2Items.getItem("misc_resource", "iridium_ore")});
		
		neutronPolymerizerRecipes.addRecipe(Recipes.inputFactory.forStack(new ItemStack(Blocks.GOLD_ORE)),
				null, false,new ItemStack[] {IC2Items.getItem("resource", "uranium_ore")});
		
		neutronPolymerizerRecipes.addRecipe(Recipes.inputFactory.forStack(new ItemStack(Items.COAL)),
				null, false,new ItemStack[] {new ItemStack(Items.DIAMOND)});
		
		dieselGeneratorAcceptManager.addFluid(FluidManager.dieselOil.getName(), 5, 80.0d);
	}
}
