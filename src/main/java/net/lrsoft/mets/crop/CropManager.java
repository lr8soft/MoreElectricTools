package net.lrsoft.mets.crop;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;

public class CropManager {
	public static Block ironRichCrop = new IronRichCrop();
	public static Block copperRichCrop = new CopperRichCrop();
	public static Block tinRichCrop = new TinRichCrop();
	public static Block titaniumRichCrop = new TitaniumRichCrop();
	
	public static Item ironRichSeed;
	public static Item copperRichSeed;
	public static Item tinRichSeed;
	public static Item titaniumRichSeed;
	static 
	{
		ironRichSeed = getCropSeed(ironRichCrop, Blocks.FARMLAND, "iron_rich_seed");
		copperRichSeed = getCropSeed(copperRichCrop, Blocks.FARMLAND, "copper_rich_seed");
		tinRichSeed = getCropSeed(tinRichCrop, Blocks.FARMLAND, "tin_rich_seed");
		titaniumRichSeed = getCropSeed(titaniumRichCrop, Blocks.FARMLAND, "titanium_rich_seed");
	}
	public static void onItemInit(Register<Item> event)
	{
		event.getRegistry().register(ironRichSeed);
		event.getRegistry().register(copperRichSeed);
		event.getRegistry().register(tinRichSeed);
		event.getRegistry().register(titaniumRichSeed);
	}
	
	public static void onBlockInit(Register<Block> event)
	{
		event.getRegistry().register(ironRichCrop);
		event.getRegistry().register(copperRichCrop);
		event.getRegistry().register(tinRichCrop);
		event.getRegistry().register(titaniumRichCrop);
	}
	
	public static void onRecipeInit()
	{
		
	}
	
	public static void onModelInit()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ironRichCrop), 0, new ModelResourceLocation(ironRichCrop.getRegistryName(),"normal"));
		ModelLoader.setCustomModelResourceLocation(ironRichSeed, 0,
				new ModelResourceLocation(ironRichSeed.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(copperRichCrop), 0, new ModelResourceLocation(copperRichCrop.getRegistryName(),"normal"));
		ModelLoader.setCustomModelResourceLocation(copperRichSeed, 0,
				new ModelResourceLocation(copperRichSeed.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(tinRichCrop), 0, new ModelResourceLocation(tinRichCrop.getRegistryName(),"normal"));
		ModelLoader.setCustomModelResourceLocation(tinRichSeed, 0,
				new ModelResourceLocation(tinRichSeed.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(titaniumRichCrop), 0, new ModelResourceLocation(titaniumRichCrop.getRegistryName(),"normal"));
		ModelLoader.setCustomModelResourceLocation(titaniumRichSeed, 0,
				new ModelResourceLocation(titaniumRichSeed.getRegistryName(), "inventory"));
	}
	
	private static Item getCropSeed(Block crop, Block place, String itemName) 
	{
		return new ItemSeeds(crop, place).setRegistryName(MoreElectricTools.MODID, itemName).setUnlocalizedName("mets." + itemName)
				.setCreativeTab(MoreElectricTools.CREATIVE_TAB);
	}

}
