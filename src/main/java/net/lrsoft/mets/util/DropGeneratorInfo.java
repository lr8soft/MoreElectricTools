package net.lrsoft.mets.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;

public class DropGeneratorInfo {
	public static Map<Item, Integer> dropItemList = new HashMap<>();
	static
	{
		dropItemList.put(Items.BONE, 100);
		dropItemList.put(Items.BOWL, 100);
		dropItemList.put(Items.EGG, 200);
		dropItemList.put(Items.ROTTEN_FLESH, 200);
		dropItemList.put(Items.SPIDER_EYE, 200);
		dropItemList.put(Items.GUNPOWDER, 400);
		dropItemList.put(Items.STRING, 100);
		dropItemList.put(Items.SLIME_BALL, 200);
		dropItemList.put(Items.MAGMA_CREAM, 200);
		
		dropItemList.put(Items.BLAZE_ROD, 1000);
		dropItemList.put(Items.ENDER_EYE, 1000);
		dropItemList.put(Items.GHAST_TEAR, 1000);
		dropItemList.put(Items.PRISMARINE_SHARD , 1000);
		
		dropItemList.put(Items.NETHER_STAR, 100000);
		dropItemList.put(Item.getItemFromBlock(Blocks.DRAGON_EGG), 1000000);
		dropItemList.put(Items.SKULL, 8000);
		dropItemList.put(Items.TOTEM_OF_UNDYING, 8000);
		
		dropItemList.put(Items.APPLE, 100);
		dropItemList.put(Items.MUTTON, 100);
		dropItemList.put(Items.BEEF, 100);
		dropItemList.put(Items.CHICKEN, 100);
		dropItemList.put(Items.BEETROOT, 100);
		dropItemList.put(Items.ARROW, 200);
		dropItemList.put(Items.BAKED_POTATO, 200);
		dropItemList.put(Items.BEETROOT_SEEDS, 50);
		dropItemList.put(Items.MELON_SEEDS, 50);
		dropItemList.put(Items.PUMPKIN_SEEDS, 50);
		dropItemList.put(Items.WHEAT_SEEDS, 50);
		dropItemList.put(Items.FISH, 100);
		dropItemList.put(Items.RABBIT, 100);
		dropItemList.put(Items.RABBIT_FOOT, 100);
		dropItemList.put(Items.LEATHER, 100);
		dropItemList.put(Items.POTATO, 100);
		
		dropItemList.put(Items.COOKED_MUTTON, 200);
		dropItemList.put(Items.COOKED_BEEF, 200);
		dropItemList.put(Items.COOKED_FISH, 200);
		dropItemList.put(Items.COOKED_PORKCHOP, 100);
		dropItemList.put(Items.COOKED_RABBIT, 100);
		dropItemList.put(Items.BAKED_POTATO, 200);
		
		dropItemList.put(Items.GLASS_BOTTLE, 50);
		dropItemList.put(Items.GLOWSTONE_DUST, 100);
		dropItemList.put(Items.REDSTONE, 100);
	}
}
