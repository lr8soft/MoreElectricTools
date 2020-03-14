package net.lrsoft.mets.proxy;
import java.util.Set;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.MetsBlockWithTileEntity;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.ItemManager;
import net.lrsoft.mets.manager.OreDictManager;
import net.lrsoft.mets.manager.WorldGenManager;
import net.lrsoft.mets.util.DynamicEnumUtils;
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
		/*DynamicEnumUtils.addEnum(CableType.class, "superconducting",
				new Class<?>[] { int.class, int.class, float.class, double.class, int.class },
				new Object[] { 0, 0, 0.25F, 0.0D, 16384});*/
	}

	public void init(FMLInitializationEvent event) 
	{
		//ItemManager.onRecipeInit();
		MetsBlockWithTileEntity.buildDummies();
		BlockManager.onBlockRecipeInit();
		OreDictManager.onOreDictInit();
		GameRegistry.registerWorldGenerator(new WorldGenManager(), 0);
	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}
}

