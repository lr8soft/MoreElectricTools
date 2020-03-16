package net.lrsoft.mets.item.weapon;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.entity.EntityGunBullet;
import net.lrsoft.mets.item.UniformElectricItem;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.SoundManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AdvancedElectricSubmachineGun extends UniformElectricItem {
	private final static double storageEnergy = 5000000, transferSpeed = 2048;
	public AdvancedElectricSubmachineGun()
	{
		super("advanced_electric_submachine_gun", storageEnergy, transferSpeed, 4);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack currentGun = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastRightClick(currentGun);
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastRightClick > 100)
		{
			lastRightClick = currentTime;
			if(ElectricItem.manager.use(currentGun, ConfigManager.AdvancedElectricSubmachineGunCost, playerIn))
			{
				EntityGunBullet entity = new EntityGunBullet(worldIn, playerIn, 25f, 360);
				entity.shoot(playerIn.rotationYaw, playerIn.rotationPitch, 3.0f);
				worldIn.spawnEntity(entity);				
				
				worldIn.playSound((EntityPlayer)null, playerIn.posX , playerIn.posY, playerIn.posZ, 
						SoundManager.laser_bullet_shoot, playerIn.getSoundCategory(), 0.1f, 0.65F);
				setLastRightClick(currentGun, lastRightClick);
			}
			
		}
		
		return new ActionResult(EnumActionResult.PASS, currentGun);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase targetEntity, EntityLivingBase attacker) {
		 if (targetEntity instanceof EntityLivingBase && attacker instanceof EntityPlayer)
         {
			EntityPlayer player = (EntityPlayer) attacker;
			EntityLivingBase enemyEntity = (EntityLivingBase) targetEntity;
			if (ElectricItem.manager.use(stack, ConfigManager.AdvancedElectricSubmachineGunCost / 5.0, player)) {
				enemyEntity.knockBack(attacker, 0.75f, (double) MathHelper.sin(player.rotationYaw * 0.017453292F),
						(double) (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
			}
         }
		return true;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) 
	{
		return false;
	}
}
