package net.lrsoft.mets.manager;

import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictManager {
	public static void onOreDictInit()
	{
		OreDictionary.registerOre("oreNiobium", BlockManager.niobiumOre);
		OreDictionary.registerOre("oreTitanium", BlockManager.titaniumOre);
		
		ItemCraftingManager.onCraftingItemOreDictInit();
	}

}
