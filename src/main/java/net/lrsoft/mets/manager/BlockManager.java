package net.lrsoft.mets.manager;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.LESU;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class BlockManager {
	public static LESU lesuStorager;
	
	static
	{
		lesuStorager = new LESU();
	}
	
	@SubscribeEvent
	public static void onBlockInit(RegistryEvent.Register<Block> event)
	{

		event.getRegistry().register(lesuStorager);
		
		onItemBlockInit();
	}
	
	private static void onItemBlockInit()
	{
		ForgeRegistries.ITEMS.register(new ItemBlock(lesuStorager).setRegistryName(lesuStorager.getRegistryName()));
	}

}
