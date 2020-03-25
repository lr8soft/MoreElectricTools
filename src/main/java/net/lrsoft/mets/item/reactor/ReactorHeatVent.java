package net.lrsoft.mets.item.reactor;

import java.util.List;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.init.Localization;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ReactorHeatVent extends Item implements IReactorComponent {
	private int reactorVent = 36, selfVent = 20, maxHeat = 1000;
	public ReactorHeatVent(String itemName, int newReactorVent, int newSelfVent, int newMaxHeat)
	{
		this.reactorVent = newReactorVent;
		this.selfVent = newSelfVent; 
		this.maxHeat = newMaxHeat;
		
		setMaxStackSize(64);
		setMaxDamage(maxHeat);
		setUnlocalizedName("mets." + itemName);
		setRegistryName(MoreElectricTools.MODID, itemName);
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
	}
	
	@Override
	public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
		return true;
	}

	@Override
	public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
		if (heatrun) {
			if (this.reactorVent > 0) {
				int rheat = reactor.getHeat();
				int reactorDrain = rheat;
				if (reactorDrain > this.reactorVent)
					reactorDrain = this.reactorVent;
				rheat -= reactorDrain;
				if ((reactorDrain = alterHeat(stack, reactor, x, y, reactorDrain)) > 0)
					return;
				reactor.setHeat(rheat);
			}

			int self = alterHeat(stack, reactor, x, y, -this.selfVent);
			if (self <= 0)
				reactor.addEmitHeat(self + this.selfVent);
		}
	}

	private void checkNearByItem(IReactor reactor, int x, int y) {
		ItemStack targetStack = reactor.getItemAt(x, y);
		if(targetStack != null && targetStack.getItem() instanceof IReactorComponent)
		{
			IReactorComponent comp = (IReactorComponent)targetStack.getItem();
		}
	}

	@Override
	public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY,
			int pulseX, int pulseY, boolean heatrun) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
		// TODO Auto-generated method stub
		 return getMaxDamage(stack);
	}

	@Override
	public int getCurrentHeat(ItemStack stack, IReactor reactor, int x, int y) {
		// TODO Auto-generated method stub
		return getDamage(stack);
	}

	@Override
	public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		int myHeat = getCurrentHeat(stack, reactor, x, y);

		myHeat += heat;

		int max = getMaxHeat(stack, reactor, x, y);
		if (myHeat > max) {

			reactor.setItemAt(x, y, null);
			heat = max - myHeat + 1;
		} else {
			if (myHeat < 0) {
				heat = myHeat;
				myHeat = 0;
			} else {
				heat = 0;
			}

			setDamage(stack, myHeat);
		}

		return heat;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, world, tooltip, advanced);
		tooltip.add(Localization.translate("ic2.reactoritem.durability") + " " + (
				      getMaxDamage(stack) - getDamage(stack)) + "/" + getMaxDamage(stack));
		if (getDamage(stack) > 0) {
			tooltip.add(Localization.translate("ic2.reactoritem.heatwarning.line1"));
			tooltip.add(Localization.translate("ic2.reactoritem.heatwarning.line2"));
		}
	}

	@Override
	public float influenceExplosion(ItemStack stack, IReactor reactor) {
		// TODO Auto-generated method stub
		return 0;
	}

}
