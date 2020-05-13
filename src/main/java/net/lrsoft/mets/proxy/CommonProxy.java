package net.lrsoft.mets.proxy;
import java.util.Set;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.MetsBlockWithTileEntity;
import net.lrsoft.mets.crop.CropManager;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.lrsoft.mets.manager.OreDictManager;
import net.lrsoft.mets.manager.WorldGenManager;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ic2.core.block.wiring.CableType;
public class CommonProxy 
{
	public void preInit(FMLPreInitializationEvent event) 
	{

	}

	public void init(FMLInitializationEvent event) 
	{
		//ItemManager.onRecipeInit();
		MetsBlockWithTileEntity.buildDummies();
		BlockManager.onBlockRecipeInit();
		CropManager.onRecipeInit();
		OreDictManager.onOreDictInit();
		if(ConfigManager.EnableOreGenerate)
		{
			GameRegistry.registerWorldGenerator(new WorldGenManager(), 0);	
		}
	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}
}

