package net.lrsoft.mets.proxy;

import net.lrsoft.mets.item.ItemManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
		ItemManager.getInstance().onModelInit();
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }

}
