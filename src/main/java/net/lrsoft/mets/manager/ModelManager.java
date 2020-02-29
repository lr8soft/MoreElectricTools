package net.lrsoft.mets.manager;

import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MoreElectricTools.MODID)
public class ModelManager {
	@SubscribeEvent
	public static void onItemModelInit(ModelRegistryEvent event) 
	{
		ModelLoader.setCustomModelResourceLocation(ItemManager.advancedIridiumSword, 0,
				new ModelResourceLocation(ItemManager.advancedIridiumSword.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.lithiumBattery, 0,
				new ModelResourceLocation(ItemManager.lithiumBattery.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricNutritionSupply, 0,
				new ModelResourceLocation(ItemManager.electricNutritionSupply.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricFishingRod, 0,
				new ModelResourceLocation(ItemManager.electricFishingRod.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricShield, 0,
				new ModelResourceLocation(ItemManager.electricShield.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.nanoBow, 0,
				new ModelResourceLocation(ItemManager.nanoBow.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.plasmaAirCannon, 0,
				new ModelResourceLocation(ItemManager.plasmaAirCannon.getRegistryName(), "inventory"));
		
		
		ModelLoader.setCustomModelResourceLocation(ItemManager.divingMask, 0,
				new ModelResourceLocation(ItemManager.divingMask.getRegistryName(), "inventory"));
		
		
		ItemManager.electricShield.setTileEntityItemStackRenderer(new net.lrsoft.mets.renderer.NanoShieldRenderer());
	}	
	
	@SubscribeEvent
	public static void onBlockModelInit(ModelRegistryEvent event) 
	{
		//ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockManager.lesuStorager), 0, new ModelResourceLocation(BlockManager.lesuStorager.getRegistryName(),"normal"));
	}
}
