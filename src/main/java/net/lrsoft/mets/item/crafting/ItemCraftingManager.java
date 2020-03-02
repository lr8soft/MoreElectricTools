package net.lrsoft.mets.item.crafting;

import ic2.api.item.IC2Items;
import ic2.api.recipe.Recipes;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ItemCraftingManager {
	public static Item niobium_crushed;
	public static Item titanium_crushed;
	
	public static Item niobium_dust;
	public static Item titanium_dust;
	
	public static Item niobium_titanium_dust;
	public static Item niobium_titanium_ingot;
	public static Item niobium_titanium_plate;
	static 
	{
		niobium_crushed = new UniformCraftingItem("niobium_crushed", 64);
		niobium_dust = new UniformCraftingItem("niobium_dust", 64);
		
		titanium_crushed = new UniformCraftingItem("titanium_crushed", 64);
		titanium_dust = new UniformCraftingItem("titanium_dust", 64);
		
		niobium_titanium_dust = new UniformCraftingItem("niobium_titanium_dust", 64);	
		niobium_titanium_ingot = new UniformCraftingItem("niobium_titanium_ingot", 64);
		niobium_titanium_plate = new UniformCraftingItem("niobium_titanium_plate", 64);
	}
	
	public static void onCraftingItemInit(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(niobium_crushed);
		event.getRegistry().register(niobium_dust);
		
		event.getRegistry().register(titanium_crushed);
		event.getRegistry().register(titanium_dust);
		
		event.getRegistry().register(niobium_titanium_dust);
		event.getRegistry().register(niobium_titanium_ingot);
		event.getRegistry().register(niobium_titanium_plate);
	}
	
	public static void onCraftingItemModelInit()
	{
		ModelLoader.setCustomModelResourceLocation(niobium_crushed, 0,
				new ModelResourceLocation(niobium_crushed.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(niobium_dust, 0,
				new ModelResourceLocation(niobium_dust.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(titanium_crushed, 0,
				new ModelResourceLocation(titanium_crushed.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(titanium_dust, 0,
				new ModelResourceLocation(titanium_dust.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(niobium_titanium_dust, 0,
				new ModelResourceLocation(niobium_titanium_dust.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(niobium_titanium_ingot, 0,
				new ModelResourceLocation(niobium_titanium_ingot.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(niobium_titanium_plate, 0,
				new ModelResourceLocation(niobium_titanium_plate.getRegistryName(), "inventory"));
	}
	
	public static void onCraftingItemOreDictInit()
	{
		OreDictionary.registerOre("crushedNiobium", niobium_crushed);
		OreDictionary.registerOre("dustNiobium", niobium_dust);
		
		OreDictionary.registerOre("crushedTitanium", titanium_crushed);
		OreDictionary.registerOre("dustTitanium", titanium_dust);
		
		OreDictionary.registerOre("dustNiobiumTitanium", niobium_titanium_dust);
		OreDictionary.registerOre("ingotNiobiumTitanium", niobium_titanium_ingot);
		OreDictionary.registerOre("plateNiobiumTitanium", niobium_titanium_plate);
	}
	
	public static void onCraftingItemRecipeInit()
	{
		NBTTagCompound heat=new NBTTagCompound();
		heat.setInteger("minHeat", 3500);
		
		Recipes.macerator.addRecipe(Recipes.inputFactory.forOreDict("oreNiobium"), null, false, new ItemStack(niobium_crushed, 2));
		Recipes.centrifuge.addRecipe(Recipes.inputFactory.forOreDict("crushedNiobium"), heat, false,
				new ItemStack[] {new ItemStack(niobium_dust), IC2Items.getItem("dust", "stone")});
		
		Recipes.macerator.addRecipe(Recipes.inputFactory.forOreDict("oreTitanium"), null, false, new ItemStack(titanium_crushed, 2));
		Recipes.centrifuge.addRecipe(Recipes.inputFactory.forOreDict("crushedTitanium"), heat, false,
				new ItemStack[] {new ItemStack(titanium_dust), IC2Items.getItem("dust", "stone")});
		
		Recipes.advRecipes.addShapelessRecipe(new ItemStack(niobium_titanium_dust),
				niobium_dust, titanium_dust, titanium_dust, titanium_dust);
		
		NBTTagCompound metablastfurnace = new NBTTagCompound();
		metablastfurnace.setInteger("fluid", 4);
		metablastfurnace.setInteger("duration", 1000);
		
		Recipes.blastfurnace.addRecipe(Recipes.inputFactory.forOreDict("dustNiobiumTitanium"), metablastfurnace, false, 
				new ItemStack[] {new ItemStack(niobium_titanium_ingot),IC2Items.getItem("misc_resource", "ashes")});
		
		Recipes.metalformerRolling.addRecipe(Recipes.inputFactory.forOreDict("ingotNiobiumTitanium"),
				null, false, new ItemStack(niobium_titanium_plate));
	}
}
