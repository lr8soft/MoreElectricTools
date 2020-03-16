package net.lrsoft.mets.item.weapon;

import net.lrsoft.mets.item.UniformElectricItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ElectricForceFieldGenerator extends UniformElectricItem {
	private final static double storageEnergy = 1000000, transferSpeed = 512;
	public ElectricForceFieldGenerator() {
		super("electric_force_field_generator", storageEnergy, transferSpeed, 3);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!(entityIn instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer)entityIn;
		float currentAbsAmount = player.getAbsorptionAmount();
		if(currentAbsAmount < 20.0f)
		{
			player.setAbsorptionAmount(currentAbsAmount+ 1);			
		}
		player.setEntityInvulnerable(true);
	}
}
