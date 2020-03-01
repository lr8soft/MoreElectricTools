package net.lrsoft.mets.proxy;
import ic2.core.block.BlockTileEntity;
import ic2.core.block.TeBlockRegistry;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.MetsTeBlock;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.ItemManager;
import net.lrsoft.mets.manager.OreDictManager;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class CommonProxy 
{
	
	public void preInit(FMLPreInitializationEvent event) 
	{
		
	}

	public void init(FMLInitializationEvent event) 
	{
		//ItemManager.onRecipeInit();
		MetsTeBlock.buildDummies();
		BlockManager.onBlockRecipeInit();
		OreDictManager.onOreDictInit();
	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}
}

