package net.lrsoft.mets.proxy;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.PlasmaAirCannon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class SoundManager {
	public static SoundEvent plasma_charge_sound = new SoundEvent(new ResourceLocation(MoreElectricTools.MODID, "plasma_charge_sound"));
	@SubscribeEvent
	public static void onSoundEvenrRegistration(RegistryEvent.Register<SoundEvent> event) 
	{
	    event.getRegistry().register(plasma_charge_sound.setRegistryName("plasma_charge_sound"));
	}
	
}
