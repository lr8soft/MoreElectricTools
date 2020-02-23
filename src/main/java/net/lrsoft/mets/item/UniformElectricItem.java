package net.lrsoft.mets.item;

import java.util.LinkedList;
import java.util.List;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UniformElectricItem extends Item implements IElectricItem, IItemHudInfo{
	private double maxStorageEU = 0, transferSpeed = 0;
	private int powerLevel = 0;
	
    public UniformElectricItem(String itemName, double maxEU, double tSpeed, int tier) {
		maxStorageEU = maxEU;
		transferSpeed =tSpeed;
		powerLevel = tier;
		
		setUnlocalizedName("mets." + itemName);
		setRegistryName(MoreElectricTools.MODID, "itemName");
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxDamage(35);
		setMaxStackSize(1);
		setNoRepair();
	}

	
	@Override
	public List<String> getHudInfo(ItemStack stack, boolean advanced) {
	    List<String> info = new LinkedList();
	    info.add(ElectricItem.manager.getToolTip(stack));
	    return info;
	}

	@Override
	public boolean canProvideEnergy(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getMaxCharge(ItemStack stack) {
		// TODO Auto-generated method stub
		return maxStorageEU;
	}

	@Override
	public int getTier(ItemStack stack) {
		// TODO Auto-generated method stub
		return powerLevel;
	}

	@Override
	public double getTransferLimit(ItemStack stack) {
		// TODO Auto-generated method stub
		return transferSpeed;
	}
	
    public Item getChargedItem(ItemStack itemStack) {return this;}
    
    public Item getEmptyItem(ItemStack itemStack) {return this;}

}
