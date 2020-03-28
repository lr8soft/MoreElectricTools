package net.lrsoft.mets.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.util.StackUtil;
import net.lrsoft.mets.item.UniformElectricItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class EnergyCrystalBelt extends UniformBaubleTemplate {

	private final static double transferSpeed = 512d, storageEnergy = 2000000;
	private final static int tier = 3;
	public EnergyCrystalBelt()
	{
		super("energy_crystal_belt", storageEnergy, transferSpeed, tier);
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.BELT;
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		if(entity instanceof EntityPlayer &&  entity.world.getTotalWorldTime() % 10L < getTier(itemstack) )
		{
			EntityPlayer player = (EntityPlayer) entity;
			NonNullList<ItemStack> nonNullList = player.inventory.mainInventory;
			double limit = transferSpeed;
			for (int i = 0; i < 9 && limit > 0.0D; i++) {
				ItemStack toCharge = nonNullList.get(i);
				if (!StackUtil.isEmpty(toCharge) || i != player.inventory.currentItem) {
					double charge = ElectricItem.manager.charge(toCharge, limit, tier, false, true);
					charge = ElectricItem.manager.discharge(itemstack, charge, tier, true, false, false);
					ElectricItem.manager.charge(toCharge, charge, tier, true, false);
					limit -= charge;
				}
			}		
		}
	}
	
	@Override
	public boolean canProvideEnergy(ItemStack stack){return true;}

}

