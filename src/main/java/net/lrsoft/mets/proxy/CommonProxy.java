package net.lrsoft.mets.proxy;
import net.lrsoft.mets.item.AdvancedIridiumSword;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		ForgeRegistries.ITEMS.register(new AdvancedIridiumSword());
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}
}

