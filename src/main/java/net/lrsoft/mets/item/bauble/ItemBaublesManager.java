package net.lrsoft.mets.item.bauble;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class ItemBaublesManager {
	public static ElectricFireProofNecklace electricFireProofNecklace = new ElectricFireProofNecklace();
	
	@SubscribeEvent
	public static void onBaublesInit(RegistryEvent.Register<Item> event) 
	{
		event.getRegistry().register(electricFireProofNecklace);
		
		onRecipeInit();
	}
	
	
	private static void onRecipeInit() 
	{
		
	}
	
	public static void onBaublesModelInit()
	{
		ModelLoader.setCustomModelResourceLocation(electricFireProofNecklace, 0,
				new ModelResourceLocation(electricFireProofNecklace.getRegistryName(), "inventory"));
	}
	
	
}
