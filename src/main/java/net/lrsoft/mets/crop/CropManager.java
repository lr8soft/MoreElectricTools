package net.lrsoft.mets.crop;

import ic2.api.item.IC2Items;
import ic2.api.recipe.Recipes;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.ItemManager;
import net.lrsoft.mets.util.ItemStackUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;

public class CropManager {
	public static Block ironRichCrop = new IronRichCrop();
	public static Block copperRichCrop = new CopperRichCrop();
	public static Block tinRichCrop = new TinRichCrop();
	public static Block titaniumRichCrop = new TitaniumRichCrop();
	public static Block uraniumRichCrop = new UraniumRichCrop();
	
	public static Item ironRichSeed;
	public static Item copperRichSeed;
	public static Item tinRichSeed;
	public static Item titaniumRichSeed;
	public static Item uraniumRichSeed;
	static 
	{
		ironRichSeed = getCropSeed(ironRichCrop, Blocks.FARMLAND, "iron_rich_seed");
		copperRichSeed = getCropSeed(copperRichCrop, Blocks.FARMLAND, "copper_rich_seed");
		tinRichSeed = getCropSeed(tinRichCrop, Blocks.FARMLAND, "tin_rich_seed");
		titaniumRichSeed = getCropSeed(titaniumRichCrop, Blocks.FARMLAND, "titanium_rich_seed");
		uraniumRichSeed = getCropSeed(uraniumRichCrop, Blocks.FARMLAND, "uranium_rich_seed");
	}
	public static void onItemInit(Register<Item> event)
	{
		event.getRegistry().register(ironRichSeed);
		event.getRegistry().register(copperRichSeed);
		event.getRegistry().register(tinRichSeed);
		event.getRegistry().register(titaniumRichSeed);
		event.getRegistry().register(uraniumRichSeed);
	}
	
	public static void onBlockInit(Register<Block> event)
	{
		event.getRegistry().register(ironRichCrop);
		event.getRegistry().register(copperRichCrop);
		event.getRegistry().register(tinRichCrop);
		event.getRegistry().register(titaniumRichCrop);
		event.getRegistry().register(uraniumRichCrop);
	}
	
	public static void onRecipeInit()
	{
		Recipes.advRecipes.addRecipe(new ItemStack(ironRichSeed),
				new Object[] {
						"EOE",
						"OSO",
						"EOE",
						'E', IC2Items.getItem("dust", "energium"),
						'O', IC2Items.getItem("crushed", "iron"),
						'S', Items.WHEAT_SEEDS
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(copperRichSeed),
				new Object[] {
						"EOE",
						"OSO",
						"EOE",
						'E', IC2Items.getItem("dust", "energium"),
						'O', IC2Items.getItem("crushed", "copper"),
						'S', Items.WHEAT_SEEDS
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(tinRichSeed),
				new Object[] {
						"EOE",
						"OSO",
						"EOE",
						'E', IC2Items.getItem("dust", "energium"),
						'O', IC2Items.getItem("crushed", "tin"),
						'S', Items.WHEAT_SEEDS
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(titaniumRichSeed),
				new Object[] {
						"EOE",
						"OSO",
						"EOE",
						'E', IC2Items.getItem("dust", "energium"),
						'O', ItemCraftingManager.titanium_crushed,
						'S', Items.WHEAT_SEEDS
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(uraniumRichSeed),
				new Object[] {
						"AOA",
						"OSO",
						"AOA",
						'A', ItemStackUtils.getAllTypeStack(IC2Items.getItem("energy_crystal")),
						'O', IC2Items.getItem("nuclear", "mox"),
						'S', Items.WHEAT_SEEDS
				});
	}
	
	public static void onModelInit()
	{
		ModelLoader.setCustomModelResourceLocation(ironRichSeed, 0,
				new ModelResourceLocation(ironRichSeed.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(copperRichSeed, 0,
				new ModelResourceLocation(copperRichSeed.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(tinRichSeed, 0,
				new ModelResourceLocation(tinRichSeed.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(titaniumRichSeed, 0,
				new ModelResourceLocation(titaniumRichSeed.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(uraniumRichSeed, 0,
				new ModelResourceLocation(uraniumRichSeed.getRegistryName(), "inventory"));
	}
	
	private static Item getCropSeed(Block crop, Block place, String itemName) 
	{
		return new ItemSeeds(crop, place).setRegistryName(MoreElectricTools.MODID, itemName).setUnlocalizedName("mets." + itemName)
				.setCreativeTab(MoreElectricTools.CREATIVE_TAB);
	}

}
