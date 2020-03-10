package net.lrsoft.mets.item;

import net.lrsoft.mets.entity.EntityGunBullet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ElectricSubmachineGun extends UniformElectricItem {
	private final static double storageEnergy = 10000, transferSpeed = 32;
	public ElectricSubmachineGun()
	{
		super("electric_submachine_gun", storageEnergy, transferSpeed, 1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack currentSword = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastRightClick(currentSword);
		long currentTime = System.currentTimeMillis();
		
		if(currentTime - lastRightClick > 90)
		{
			lastRightClick = currentTime;
			
			EntityGunBullet entity = new EntityGunBullet(worldIn, playerIn, 8f);
			entity.shoot(playerIn.rotationYaw, playerIn.rotationPitch, 3.0f);
			worldIn.spawnEntity(entity);
			
			setLastRightClick(currentSword, lastRightClick);
		}
		
		return new ActionResult(EnumActionResult.SUCCESS, currentSword);
	}
}
