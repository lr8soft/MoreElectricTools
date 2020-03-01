package net.lrsoft.mets.block.compenent;

import ic2.api.info.Info;
import ic2.api.energy.tile.IChargingSlot;
import ic2.api.item.ElectricItem;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.util.StackUtil;
import net.lrsoft.mets.util.DropGeneratorInfo;
import net.minecraft.item.ItemStack;

public class DropGeneratorSlot extends InvSlotConsumable {
	public DropGeneratorSlot(TileEntityInventory base1, String name1, int count) {
		 super(base1, name1, InvSlot.Access.I, count, InvSlot.InvSide.SIDE);
	}
	
	public boolean accepts(ItemStack stack) {
		return DropGeneratorInfo.dropItemList.containsKey(stack.getItem());//(Info.itemInfo.getFuelValue(stack, false) > 0);
	}

	public int consumeFuel() {
		ItemStack fuel = consume(1);
		if (fuel == null)
			return 0;

		return DropGeneratorInfo.dropItemList.get(fuel.getItem());//Info.itemInfo.getFuelValue(fuel, false);
	}
}
