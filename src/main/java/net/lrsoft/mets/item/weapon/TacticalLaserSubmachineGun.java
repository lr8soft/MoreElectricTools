package net.lrsoft.mets.item.weapon;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.entity.EntityGunBullet;
import net.lrsoft.mets.item.UniformElectricItem;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.SoundManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class TacticalLaserSubmachineGun extends UniformElectricItem {
	private final static double storageEnergy = 100000000, transferSpeed = 8192;
	public TacticalLaserSubmachineGun()
	{
		super("tactical_laser_submachine_gun", storageEnergy, transferSpeed, 5);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack currentGun = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastRightClick(currentGun);
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastRightClick > 10)
		{
			lastRightClick = currentTime;
			if(ElectricItem.manager.use(currentGun, 10000, playerIn))
			{
				EntityGunBullet entity = new EntityGunBullet(worldIn, playerIn, 50f, 360);
				entity.shoot(playerIn.rotationYaw, playerIn.rotationPitch, 3.0f);
				worldIn.spawnEntity(entity);				
				
				worldIn.playSound((EntityPlayer)null, playerIn.posX , playerIn.posY, playerIn.posZ, 
						SoundManager.laser_bullet_shoot, playerIn.getSoundCategory(), 0.2f, 1.0F);
				return new ActionResult(EnumActionResult.SUCCESS, currentGun);
			}
			setLastRightClick(currentGun, lastRightClick);
		}
		
		return new ActionResult(EnumActionResult.PASS, currentGun);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) 
	{
		return false;
	}
}
