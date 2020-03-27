package net.lrsoft.mets;

import ic2.core.block.TeBlockRegistry;
import net.lrsoft.mets.block.MetsBlockWithTileEntity;
import net.lrsoft.mets.manager.ItemManager;
import net.lrsoft.mets.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
@Mod(modid = MoreElectricTools.MODID, name=MoreElectricTools.NAME,  version = MoreElectricTools.VERSION, dependencies = "required-after:ic2")
public class MoreElectricTools
{
    public static final String MODID = "mets", NAME = "More electric tools", VERSION = "1.322";

	@SidedProxy(clientSide="net.lrsoft.mets.proxy.ClientProxy",
			serverSide="net.lrsoft.mets.proxy.CommonProxy")
    public static CommonProxy proxy; 
	
	public static final CreativeTabs CREATIVE_TAB = new CreativeTabs("metsTabs") 
	{
		@Override
		public ItemStack getTabIconItem() 
		{
			return new ItemStack(ItemManager.advancedIridiumSword);
		}
	};

   @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

}

