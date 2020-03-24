package net.lrsoft.mets.crop;

import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;

public class CropManager {
	public static Block ironRichRrop = new  IronRichCrop();
	public static void onItemInit(Register<Item> event)
	{
		event.getRegistry().register(new ItemBlock(ironRichRrop).setRegistryName(ironRichRrop.getRegistryName()));
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
		//ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockManager.niobiumOre), 0, new ModelResourceLocation(BlockManager.niobiumOre.getRegistryName(),"normal"));
		//	ModelLoader.setCustomModelResourceLocation(ItemManager.tacticalLaserSubmachineGun, 0,
		//new ModelResourceLocation(ItemManager.tacticalLaserSubmachineGun.getRegistryName(), "inventory"));
	}

}
