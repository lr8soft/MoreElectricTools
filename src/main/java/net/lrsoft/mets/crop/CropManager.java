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
	public static Block ironRichRrop = new IronRichCrop();
	public static Item  ironRichSeed;
	static 
	{
		ironRichSeed = getCropSeed(ironRichRrop, Blocks.FARMLAND, "iron_rich_seed");//new ItemSeeds(ironRichRrop, Blocks.FARMLAND);
	}
	public static void onItemInit(Register<Item> event)
	{
		//event.getRegistry().register(new ItemSeeds(ironRichRrop, Blocks.FARMLAND).
		//		setRegistryName(MoreElectricTools.MODID, "iron_rich_seed").setUnlocalizedName("iron_rich_seed"));
		event.getRegistry().register(ironRichSeed);
	}
	
	public static void onBlockInit(Register<Block> event)
	{
		event.getRegistry().register(ironRichRrop);

	
	}
	
	public static void onRecipeInit()
	{
		
	}
	
	public static void onModelInit()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ironRichRrop), 0, new ModelResourceLocation(ironRichRrop.getRegistryName(),"normal"));
		//	ModelLoader.setCustomModelResourceLocation(ItemManager.tacticalLaserSubmachineGun, 0,
		//new ModelResourceLocation(ItemManager.tacticalLaserSubmachineGun.getRegistryName(), "inventory"));
	}
	
	private static Item getCropSeed(Block crop, Block place, String itemName) 
	{
		return new ItemSeeds(crop, place).setRegistryName(MoreElectricTools.MODID, itemName).setUnlocalizedName(itemName);
	}

}
