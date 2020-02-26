package net.lrsoft.mets.item;

import ic2.api.item.IC2Items;
import ic2.api.recipe.Recipes;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.renderer.NanoShieldRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemManager {
	public static AdvancedIridiumSword advancedIridiumSword;
	public static LithiumBattery lithiumBattery;
	public static ElectricNutritionSupply electricNutritionSupply;
	//public static NanoShield nanoShield;
	public static NanoBow nanoBow;
	public static PlasmaAirCannon plasmaAirCannon;
	
	private static ItemManager rInstance;
	private ItemManager()
	{
		advancedIridiumSword = new AdvancedIridiumSword();
		lithiumBattery = new LithiumBattery();
		electricNutritionSupply = new ElectricNutritionSupply();
		//nanoShield = new NanoShield();
		nanoBow = new NanoBow();
		plasmaAirCannon = new PlasmaAirCannon();

	}
	
	public void onItemInit()
	{
		
		ForgeRegistries.ITEMS.register(advancedIridiumSword);
		ForgeRegistries.ITEMS.register(lithiumBattery);
		ForgeRegistries.ITEMS.register(electricNutritionSupply);
		//ForgeRegistries.ITEMS.register(nanoShield);
		ForgeRegistries.ITEMS.register(nanoBow);
		ForgeRegistries.ITEMS.register(plasmaAirCannon);
	}
	
	public void onModelInit() 
	{
		ModelLoader.setCustomModelResourceLocation(advancedIridiumSword, 0,
				new ModelResourceLocation(advancedIridiumSword.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(lithiumBattery, 0,
				new ModelResourceLocation(lithiumBattery.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(electricNutritionSupply, 0,
				new ModelResourceLocation(electricNutritionSupply.getRegistryName(), "inventory"));
		//ModelLoader.setCustomModelResourceLocation(nanoShield, 0,
		//		new ModelResourceLocation(nanoShield.getRegistryName(), "inventory"));
		//nanoShield.setTileEntityItemStackRenderer(new NanoShieldRenderer());
		ModelLoader.setCustomModelResourceLocation(nanoBow, 0,
				new ModelResourceLocation(nanoBow.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(plasmaAirCannon, 0,
				new ModelResourceLocation(plasmaAirCannon.getRegistryName(), "inventory"));
	}	
	
	public void onRecipeInit() 
	{
		Recipes.advRecipes.addRecipe(new ItemStack(advancedIridiumSword), 
				new Object[] {
						"SIS",
						"SIS",
						"CDC",
						'S', IC2Items.getItem("casing", "steel"),
						'I', IC2Items.getItem("crafting", "iridium"),
						'C', IC2Items.getItem("crafting", "carbon_plate"),
						'D', IC2Items.getItem("lapotron_crystal")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(lithiumBattery), 
				new Object[] {
						" C ",
						"SLS",
						"SLS",
						'C', IC2Items.getItem("cable", "type:copper,insulation:1"),
						'S', IC2Items.getItem("casing", "copper"),
						'L', IC2Items.getItem("dust", "lithium")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(electricNutritionSupply), 
				new Object[] {
						"SPS",
						"HCH",
						"SBS",
						'P', IC2Items.getItem("treetap"),
						'S', IC2Items.getItem("casing", "gold"),
						'C', IC2Items.getItem("crafting", "circuit"),
						'H', IC2Items.getItem("heat_exchanger"),
						'B', IC2Items.getItem("re_battery")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(plasmaAirCannon), 
				new Object[] {
						"CCB",
						"LPA",
						"LLB",
						'C', IC2Items.getItem("crafting", "coil"),
						'B', lithiumBattery,
						'L', IC2Items.getItem("crafting", "alloy"),
						'P', IC2Items.getItem("fluid_cell"),
						'A', IC2Items.getItem("crafting", "advanced_circuit")
				});
	}


	public static ItemManager getInstance()
	{
		if(rInstance == null) 
		{
			rInstance = new ItemManager();
		}
		return rInstance;
	}

}
