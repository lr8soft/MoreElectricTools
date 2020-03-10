package net.lrsoft.mets.armor;

import java.util.LinkedList;
import java.util.List;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.core.item.armor.jetpack.IBoostingJetpack;
import ic2.core.item.armor.jetpack.IJetpack;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.common.util.EnumHelper;

public class AdvancedJetPack extends ItemArmor implements IElectricItem, IItemHudInfo, IBoostingJetpack {
	private static ArmorMaterial jetPackMaterial = EnumHelper.addArmorMaterial(
			"advanced_jetpack", MoreElectricTools.MODID + ":advanced_jetpack", 33, new int[]{3, 12, 8, 4}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2);
	
	private static double maxStorageEnergy = 1000000, transferSpeed = 512;
	public AdvancedJetPack()
	{
		super(jetPackMaterial, 0, EntityEquipmentSlot.CHEST);
		setUnlocalizedName("mets.advanced_jetpack");
		setRegistryName(MoreElectricTools.MODID, "advanced_jetpack");
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxDamage(233);
		setMaxStackSize(1);
		setNoRepair();
	}
	
	@Override
	public boolean drainEnergy(ItemStack pack, int amount) 
	{
		return (ElectricItem.manager.discharge(pack, (amount + 3), Integer.MAX_VALUE, true, false, false) > 0.0D);
	}

	@Override
	public double getChargeLevel(ItemStack arg0) {return 3;}

	@Override
	public float getDropPercentage(ItemStack arg0) {return 0.01f;}

	@Override
	public float getHoverMultiplier(ItemStack arg0, boolean arg1) {return 1.0f;}

	@Override
	public float getPower(ItemStack arg0) {return 1.0f;}

	@Override
	public float getWorldHeightDivisor(ItemStack arg0) {return 0.1f;}

	@Override
	public boolean isJetpackActive(ItemStack arg0) {return true;}

	@Override
	public List<String> getHudInfo(ItemStack stack, boolean advanced) {
	    List<String> info = new LinkedList();
	    info.add(ElectricItem.manager.getToolTip(stack));
	    return info;
	}

	@Override
	public boolean canProvideEnergy(ItemStack stack) {return false;}

	@Override
	public double getMaxCharge(ItemStack stack) {return maxStorageEnergy;}

	@Override
	public int getTier(ItemStack stack) {return 3;}

	@Override
	public double getTransferLimit(ItemStack stack) {return transferSpeed;}

	@Override
	public float getBaseThrust(ItemStack arg0, boolean arg1) {return 0.3f;}

	@Override
	public float getBoostThrust(EntityPlayer arg0, ItemStack arg1, boolean arg2) {return 0.2f;}

	@Override
	public float getHoverBoost(EntityPlayer arg0, ItemStack arg1, boolean arg2) {return 0.2f;}

	@Override
	public boolean useBoostPower(ItemStack pack, float amount) 
	{
		return (ElectricItem.manager.discharge(pack, (amount + 3), Integer.MAX_VALUE, true, false, false) > 0.0D);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {return false;}

}
