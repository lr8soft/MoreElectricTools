package net.lrsoft.mets.util;

import ic2.core.util.Util;
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
	
	public static int getCurrentTex(ItemStack stack, int texLevel)
	{
		int level = 0, damage = stack.getItemDamage();
		int maxDamage = stack.getMaxDamage() - 1;
		if (maxDamage > 0) {
			level = Util.limit((damage * texLevel + maxDamage / 2) / maxDamage, 0, texLevel);
		}
		return texLevel - level;
	}
}
