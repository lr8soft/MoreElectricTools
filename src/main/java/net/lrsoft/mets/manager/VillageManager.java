package net.lrsoft.mets.manager;

import java.util.List;
import java.util.Random;

import ic2.api.item.IC2Items;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.structure.EngineerLodge;
import net.lrsoft.mets.structure.EngineerLodgeCreationHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class VillageManager {
	public static VillagerProfession engineer;
	static 
	{
		engineer = new VillagerRegistry.VillagerProfession(MoreElectricTools.MODID + ":engineer", 
				MoreElectricTools.MODID + ":textures/entity/villagers/engineer.png", 
				MoreElectricTools.MODID + ":textures/entity/villagers/zombie_engineer.png");
	}
	
	@SubscribeEvent
	public static void onVillagerProfessionRegistration(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
	    VillagerRegistry.VillagerCareer smelter = new VillagerRegistry.VillagerCareer(engineer, "smelter");
	    smelter.addTrade(1, new EntityVillager.ListItemForEmeralds(new ItemStack(ItemCraftingManager.titanium_ingot), 
	    		new PriceInfo(5, 6)));
	    smelter.addTrade(1, new EntityVillager.ListItemForEmeralds(IC2Items.getItem("ingot", "bronze"), 
	    		new PriceInfo(3, 6)));
	    smelter.addTrade(2, new EntityVillager.ListItemForEmeralds(new ItemStack(ItemCraftingManager.niobium_titanium_ingot), 
	    		new PriceInfo(24, 36)));

	    
	    VillagerRegistry.VillagerCareer toolTrader = new VillagerRegistry.VillagerCareer(engineer, "toolTrader");
	    toolTrader.addTrade(1, new EntityVillager.ListItemForEmeralds(IC2Items.getItem("re_battery"), 
	    		new PriceInfo(12, 24)));
	    toolTrader.addTrade(1, new EntityVillager.ListItemForEmeralds(IC2Items.getItem("crafting", "circuit"), 
	    		new PriceInfo(6, 16)));
	    
	    toolTrader.addTrade(2, new EntityVillager.ListItemForEmeralds(new ItemStack(ItemManager.electricSubmachineGun), 
	    		new PriceInfo(42, 64)));
	    toolTrader.addTrade(2, new EntityVillager.ListItemForEmeralds(new ItemStack(ItemManager.electricLighter), 
	    		new PriceInfo(42, 64)));
	    
	    event.getRegistry().register(engineer);
	    VillagerRegistry.instance().registerVillageCreationHandler(new EngineerLodgeCreationHandler());
	    MapGenStructureIO.registerStructureComponent(EngineerLodge.class, "MetsEL");
	}
}
