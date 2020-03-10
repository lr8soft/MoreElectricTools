package net.lrsoft.mets.item;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.entity.EntityGunBullet;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.SoundManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class AdvancedElectricSubmachineGun extends UniformElectricItem {
	private final static double storageEnergy = 5000000, transferSpeed = 2048;
	public AdvancedElectricSubmachineGun()
	{
		super("advanced_electric_submachine_gun", storageEnergy, transferSpeed, 4);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack currentSword = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastRightClick(currentSword);
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastRightClick > ConfigManager.AdvancedElectricSubmachineGunInterval)
		{
			lastRightClick = currentTime;
			if(ElectricItem.manager.use(currentSword, ConfigManager.AdvancedElectricSubmachineGunCost, playerIn))
			{
				EntityGunBullet entity = new EntityGunBullet(worldIn, playerIn, 25f, 360);
				entity.shoot(playerIn.rotationYaw, playerIn.rotationPitch, 3.0f);
				worldIn.spawnEntity(entity);				
				
				worldIn.playSound((EntityPlayer)null, playerIn.posX , playerIn.posY, playerIn.posZ, 
						SoundManager.laser_bullet_shoot, playerIn.getSoundCategory(), 0.1f, 0.65F);
			}
			setLastRightClick(currentSword, lastRightClick);
		}
		
		return new ActionResult(EnumActionResult.SUCCESS, currentSword);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) 
	{
		return false;
	}
}
