package net.lrsoft.mets.manager;

import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class LootManager {
	public static ResourceLocation lootLocation = new ResourceLocation(MoreElectricTools.MODID, "mets_loot");
	@SubscribeEvent
	public static void onLootManagerInit(LootTableLoadEvent event)
	{
		
		if(LootTableList.CHESTS_SIMPLE_DUNGEON.equals(event.getName()))
		{
			LootEntry simpleDungeonEntry = new LootEntryTable(lootLocation, 1, 0,
					new LootCondition[0], MoreElectricTools.MODID+":mets_loot");
			LootPool simpleDungeonPool = new LootPool(new LootEntry[] {simpleDungeonEntry}, new LootCondition[0],
					new RandomValueRange(1), new RandomValueRange(0, 1), MoreElectricTools.MODID+":mets_loot");
			event.getTable().addPool(simpleDungeonPool);
		}
		
	}
}
