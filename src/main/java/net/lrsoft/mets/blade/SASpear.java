package net.lrsoft.mets.blade;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import ic2.api.item.ElectricItem;
import mods.flammpfeil.slashblade.ability.UntouchableTime;
import mods.flammpfeil.slashblade.entity.EntitySpearManager;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import mods.flammpfeil.slashblade.util.ReflectionAccessHelper;

public class SASpear extends SpecialAttackBase {
	private static float cost = 10000f;

	@Override
	public String toString() {
		return "SA_SpearEX";
	}

	@Override
	public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
		World world = player.world;

		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);

		double playerDist = 7d;
		float attackDist = (float) (playerDist / 3.0);

		if (!player.onGround) {
			playerDist *= 1.2f;
		}

		ReflectionAccessHelper.setVelocity(player, -Math.sin(Math.toRadians(player.rotationYaw)) * playerDist,
				player.motionY, Math.cos(Math.toRadians(player.rotationYaw)) * playerDist);

		if (!world.isRemote) {
			if (!ElectricItem.manager.use(stack, cost, player))
				return;

			ItemSlashBlade blade = (ItemSlashBlade) stack.getItem();

			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 2, true, false));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 300, 2, true, false));
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 250, 2, true, false));
			
			EntitySpearManagerEx entityDA = new EntitySpearManagerEx(world, player, false, 3d);
			entityDA.setLifeTime(10);
			if (entityDA != null) {
				world.spawnEntity(entityDA);
			}
			
			for(int i = 0; i < 24;i++){
            	EntityDriveEx entityDrive = new EntityDriveEx(world, player, 7.0f, false, 0);
            	
                float rotationYaw = player.rotationYaw + 30 * i + (entityDrive.getRand().nextFloat() - 0.5f) * 30;
                float rotationPitch = player.rotationPitch + 30 * i + (entityDrive.getRand().nextFloat() - 0.5f) * 30;

                float fYawDtoR = (  rotationYaw / 180F) * (float)Math.PI;
                float fPitDtoR = (rotationPitch / 180F) * (float)Math.PI;
                float fYVecOfst = 0.5f;

                float motionX = -MathHelper.sin(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst * 2;
                float motionY = -MathHelper.sin(fPitDtoR) * fYVecOfst;
                float motionZ =  MathHelper.cos(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst * 2;
                entityDrive.setLocationAndAngles(player.posX - motionX,
                		player.posY + (double) player.getEyeHeight() / 2D - motionY,
                		player.posZ - motionZ,
                        rotationYaw,
                        rotationPitch);
                entityDrive.setDriveVector(fYVecOfst);
                entityDrive.setLifeTime(25);
                entityDrive.setIsMultiHit(false);

                entityDrive.setRoll(90.0f + 120 * (entityDrive.getRand().nextFloat() - 0.5f));
                if (entityDrive != null) {
                    world.spawnEntity(entityDrive);
                }
            }
		}

		UntouchableTime.setUntouchableTime(player, 5);

		player.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.2F, 1.2F);
		ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.HiraTuki);
	}
}