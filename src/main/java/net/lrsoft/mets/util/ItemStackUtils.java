package net.lrsoft.mets.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemStackUtils {
	public static ItemStack getAllTypeStack(ItemStack itemstack)
	{
		return new ItemStack(itemstack.getItem(), 1, OreDictionary.WILDCARD_VALUE);
	}
	
	public static ItemStack getAllTypeStack(Item item)
	{
		return new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);
	}
}
