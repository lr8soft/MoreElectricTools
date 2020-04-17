package net.lrsoft.mets.item.bauble;

import java.util.Random;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import net.lrsoft.mets.item.UniformElectricItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ElectricFlightRing extends UniformBaubleTemplate {
	private final static double transferSpeed = 2048, storageEnergy = 10000000;
	public ElectricFlightRing()
	{
		super("electric_flight_ring", storageEnergy, transferSpeed, 4);
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			if(!player.isSpectator() && !player.capabilities.isCreativeMode)
			{
				float ratio = getElectricItemAttenuationRatio(itemstack);
				if(ElectricItem.manager.canUse(itemstack, 10 * ratio))
				{
					if(!player.capabilities.allowFlying)
					{
						enableFlyingAbility(player);
					}
					else 
					{
						if(player.capabilities.isFlying)
						{
							if(!ElectricItem.manager.use(itemstack, 10 * ratio, entity))
							{
								disableFlyingAbility(player);
							}else
							{
								if(IC2.keyboard.isBoostKeyDown(player) && ElectricItem.manager.use(itemstack, 10 * ratio, entity))
								{
							         Vec3d vec3d = player.getLookVec();
							         player.motionX += vec3d.x * 0.4D + (vec3d.x * 1.5D - player.motionX) * 0.5D;
							         player.motionY += vec3d.y * 0.4D + (vec3d.y * 1.5D - player.motionY) * 0.5D;
							         player.motionZ += vec3d.z * 0.4D + (vec3d.z * 1.5D - player.motionZ) * 0.5D;

									float pitch = player.rotationPitch, yaw = player.rotationYaw;
									for (int p = 0; p < 3; p++) {
										float newYaw = yaw + getRandomFromRange(40, -40);
										float newPitch = pitch + getRandomFromRange(40, -40);
										Vec3d shootPosition = new Vec3d(
												-MathHelper.sin(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F),
												-MathHelper.sin(newPitch * 0.0174F),
												MathHelper.cos(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F));
										player.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, player.posX,
												player.posY + player.getEyeHeight() - 0.2f, player.posZ, shootPosition.x, shootPosition.y , shootPosition.z , 1);
									}
								}
							}
						}
					}
				}else
				{
					disableFlyingAbility(player);
				}
			}
		}
	}
	
	private int getRandomFromRange(int max, int min)
	{
		return new Random().nextInt(max-min)+min;
	}
	
	
	private void enableFlyingAbility(EntityPlayer player)
	{
		player.capabilities.allowFlying = true;
		player.capabilities.isFlying = false;
		player.sendPlayerAbilities();
	}
	
	private void disableFlyingAbility(EntityPlayer player)
	{
		player.capabilities.allowFlying = false;
		player.capabilities.isFlying = false;
		player.sendPlayerAbilities();
	}
	
	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		super.onUnequipped(itemstack, player);
		if(player instanceof EntityPlayer)
		{
			disableFlyingAbility((EntityPlayer)player);
		}
	}
}
