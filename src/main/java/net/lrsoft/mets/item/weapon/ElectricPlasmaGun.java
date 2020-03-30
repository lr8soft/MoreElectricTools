package net.lrsoft.mets.item.weapon;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.entity.EntityGunBullet;
import net.lrsoft.mets.entity.EntityPlasmaBullet;
import net.lrsoft.mets.item.UniformElectricItem;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.SoundManager;
import net.lrsoft.mets.renderer.particle.EntityParticleSpray;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ElectricPlasmaGun extends UniformElectricItem {
	private final static double storageEnergy = 1000000, transferSpeed = 512;
	public ElectricPlasmaGun()
	{
		super("electric_plasma_gun", storageEnergy, transferSpeed, 3);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack currentGun = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastRightClick(currentGun);
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastRightClick > 500)
		{
			lastRightClick = currentTime;
			if(ElectricItem.manager.use(currentGun, ConfigManager.ElectricPlasmaGunCost, playerIn))
			{
				EntityPlasmaBullet entity = new EntityPlasmaBullet(worldIn, playerIn, 20f);
				entity.shoot(playerIn.rotationYaw, playerIn.rotationPitch, 1.5f);
				worldIn.spawnEntity(entity);	
				
				worldIn.playSound((EntityPlayer)null, playerIn.posX , playerIn.posY, playerIn.posZ, 
						SoundManager.laser_bullet_shoot, playerIn.getSoundCategory(), 0.1f, 0.55F);

				setLastRightClick(currentGun, lastRightClick);
			}
			
		}
		
		return new ActionResult(EnumActionResult.PASS, currentGun);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) 
	{
		return false;
	}
	
}
