package net.lrsoft.mets.item;

import java.util.LinkedList;
import java.util.List;

import ic2.api.item.ElectricItem;
import ic2.api.item.IBoxable;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.core.item.ElectricItemManager;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.tileentity.IMets;
import net.lrsoft.mets.enchantment.EfficientEnergyCost;
import net.lrsoft.mets.manager.EnchantmentManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UniformElectricItem extends Item implements IElectricItem, IItemHudInfo, IBoxable
{
	private double maxStorageEU = 0, transferSpeed = 0;
	private int powerLevel = 0;
	
    public UniformElectricItem(String itemName, double maxEU, double tSpeed, int tier) 
    {
		maxStorageEU = maxEU;
		transferSpeed =tSpeed;
		powerLevel = tier;
		
		setUnlocalizedName("mets." + itemName);
		setRegistryName(MoreElectricTools.MODID, itemName);
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxDamage(54321);
		setMaxStackSize(1);
		setNoRepair();
	}
	
	@Override
	public List<String> getHudInfo(ItemStack stack, boolean advanced)
	{
	    List<String> info = new LinkedList();
	    info.add(ElectricItem.manager.getToolTip(stack));
	    return info;
	}

	@Override
	public boolean canProvideEnergy(ItemStack stack)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getMaxCharge(ItemStack stack) 
	{
		// TODO Auto-generated method stub
		return maxStorageEU;
	}

	@Override
	public int getTier(ItemStack stack)
	{
		// TODO Auto-generated method stub
		return powerLevel;
	}

	@Override
	public double getTransferLimit(ItemStack stack)
	{
		// TODO Auto-generated method stub
		return transferSpeed;
	}
	
    public Item getChargedItem(ItemStack itemStack) {return this;}
    
    public Item getEmptyItem(ItemStack itemStack) {return this;}
    
	protected void setLastRightClick(ItemStack stack, long value) 
	{
		stack.getItem().getNBTShareTag(stack).setLong("LastRightClick", value);
		}
	protected long getLastRightClick(ItemStack stack) 
	{
		long value = 0;
		try {
			value = stack.getItem().getNBTShareTag(stack).getLong("LastRightClick");
		} catch (Exception expt) {}
		return value;
	}
	
	@Override
	public boolean canBeStoredInToolbox(ItemStack stack) {return true;}
	
	
	public float getElectricItemAttenuationRatio(ItemStack stack)
	{
		return EfficientEnergyCost.getAttenuationRatio(EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.efficientEu, stack));
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		if (enchantment == EnchantmentManager.efficientEu) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab)) {
			return;
		}
		ElectricItemManager.addChargeVariants((Item)this, (List)items);
	}

}
