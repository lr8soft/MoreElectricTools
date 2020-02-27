package net.lrsoft.mets.armor;

import java.util.LinkedList;
import java.util.List;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;

public class DivingMask extends ItemArmor implements ISpecialArmor, IElectricItem, IItemHudInfo {
	private static ArmorMaterial divingMaskMaterial = EnumHelper.addArmorMaterial(
			"divingMask", MoreElectricTools.MODID + ":diving_mask", 33, new int[]{2, 7, 5, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 9);
	public DivingMask() {
		super(divingMaskMaterial, 0, EntityEquipmentSlot.HEAD);
		setUnlocalizedName("mets.diving_mask");
		setRegistryName(MoreElectricTools.MODID, "diving_mask");
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxDamage(233);
		setMaxStackSize(1);
		setNoRepair();
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage,
			int slot) {
		return null;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
	}

	@Override
	public List<String> getHudInfo(ItemStack stack, boolean advanced)
	{
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
		return 100000;
	}

	@Override
	public int getTier(ItemStack stack) {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public double getTransferLimit(ItemStack stack) {
		// TODO Auto-generated method stub
		return 128;
	}
	
	public Item getChargedItem(ItemStack itemStack) {
		return this;
	}

	public Item getEmptyItem(ItemStack itemStack) {
		return this;
	}

}
