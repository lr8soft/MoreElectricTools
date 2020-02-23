package net.lrsoft.mets.item;
import java.util.LinkedList;
import java.util.List;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
public class AdvancedIridiumSword  extends UniformElectricItem{
	private final static double maxStorageEU = 5000000, transferSpeed = 2048;
	public AdvancedIridiumSword() {
		super("AdvancedIridiumSword", maxStorageEU, transferSpeed, 4);
	}

}
