package net.lrsoft.mets.item.crafting;

import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.item.Item;

public class UniformCraftingItem extends Item {
	public UniformCraftingItem(String itemName, int maxStackSize)
	{
		setUnlocalizedName("mets.crafting." + itemName);
		setRegistryName(MoreElectricTools.MODID, itemName);
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxStackSize(maxStackSize);
		setNoRepair();
	}
}
