package net.lrsoft.mets.manager;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.weapon.PlasmaAirCannon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class SoundManager {
	public static SoundEvent plasma_charge_sound = new SoundEvent(new ResourceLocation(MoreElectricTools.MODID, "plasma_charge_sound"));
	public static SoundEvent laser_bullet_shoot = new SoundEvent(new ResourceLocation(MoreElectricTools.MODID, "laser_bullet_shoot"));
	public static SoundEvent lighter_place = new SoundEvent(new ResourceLocation(MoreElectricTools.MODID, "lighter_place"));
	public static SoundEvent plasma_launch = new SoundEvent(new ResourceLocation(MoreElectricTools.MODID, "plasma_launch"));
	//laser_bullet_shoot
	@SubscribeEvent
	public static void onSoundEvenrRegistration(RegistryEvent.Register<SoundEvent> event) 
	{
	    event.getRegistry().register(plasma_charge_sound.setRegistryName("plasma_charge_sound"));
	    event.getRegistry().register(laser_bullet_shoot.setRegistryName("laser_bullet_shoot"));
	    event.getRegistry().register(lighter_place.setRegistryName("lighter_place"));
	    event.getRegistry().register(plasma_launch.setRegistryName("plasma_launch"));
	}
	
}
