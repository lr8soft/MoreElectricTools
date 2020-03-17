package net.lrsoft.mets.item.battery;

import java.util.LinkedList;
import java.util.List;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.core.init.Localization;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.UniformElectricItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ThoriumBattery extends Item implements IElectricItem, IItemHudInfo{
	private final static double transferSpeed = 2D, maxStorageEU = Integer.MAX_VALUE;
	public ThoriumBattery()
	{
		setUnlocalizedName("mets.thorium_battery");
		setRegistryName(MoreElectricTools.MODID, "thorium_battery");
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxDamage(35);
		setMaxStackSize(1);
		setNoRepair();
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		// TODO Auto-generated method stub
		if(!(entityIn instanceof EntityPlayer)) return;
		double currentEnergy = ElectricItem.manager.getCharge(stack);
		EntityPlayer player = (EntityPlayer)entityIn;
		if(currentEnergy < Integer.MAX_VALUE)
		{
			ElectricItem.manager.charge(stack, Integer.MAX_VALUE - currentEnergy, 1, true, false);
			player.inventoryContainer.detectAndSendChanges();
		}
	}
	
	@Override
	public List<String> getHudInfo(ItemStack stack, boolean advanced)
	{
	    List<String> info = new LinkedList();
	    return info;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(Localization.translate("mets.info.th_battery"));
	}
	
	@Override
	public boolean canProvideEnergy(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public double getTransferLimit(ItemStack stack)
	{
		// TODO Auto-generated method stub
		return transferSpeed;
	}
	
    public Item getChargedItem(ItemStack itemStack) {return this;}
    
    public Item getEmptyItem(ItemStack itemStack) {return this;}
    
	@Override
	public double getMaxCharge(ItemStack stack) 
	{
		return maxStorageEU;
	}

	@Override
	public int getTier(ItemStack stack)
	{
		return 1;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}
}
