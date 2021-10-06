package net.lrsoft.mets.manager;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.blade.EntityDriveEx;
import net.lrsoft.mets.blade.EntitySlashDimensionEx;
import net.lrsoft.mets.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class EntityManager {
	 private static int currentEntityId = 0;
	@SubscribeEvent
	public static void onEntityInit(RegistryEvent.Register<EntityEntry> event)
	{
		registerEntity(event, EntityGunBullet.class, "GunBullet", 200, 5);
		registerEntity(event, EntityHyperGunBullet.class, "HyperGunBullet", 250, 5);
		registerEntity(event, EntityRocket.class, "RocketBullet", 200, 3);
		registerEntity(event, EntityPlasmaBullet.class, "PlasmaBullet", 200, 5);
		registerEntity(event, EntityTachyonBullet.class, "TachyonBullet", 300, 5);
		
		if (Loader.isModLoaded("flammpfeil.slashblade")) 
		{
			registerEntity(event, EntitySlashDimensionEx.class, "EntitySlashDimensionEx", 400, 1);
			registerEntity(event, EntityDriveEx.class, "EntityDriveEx", 400, 1);
		}
	}
	
	private static void registerEntity(RegistryEvent.Register<EntityEntry> event, 
			Class<? extends Entity> entityClass, String entityName, int updateRange, int updateFrequency)
	{
	    event.getRegistry().register(EntityEntryBuilder.create()
	            .entity(entityClass)
	            .id(new ResourceLocation(MoreElectricTools.MODID, entityName), currentEntityId++)
	            .name(entityName)
	            .tracker(updateRange, updateFrequency, true)
	            .build()
	    );
	}
}
