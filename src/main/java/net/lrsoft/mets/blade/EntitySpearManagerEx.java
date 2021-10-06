package net.lrsoft.mets.blade;

import java.util.List;
import java.util.Random;

import ic2.core.IC2Potion;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntitySpearManager;
import mods.flammpfeil.slashblade.util.ReflectionAccessHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySpearManagerEx extends EntitySpearManager {
	private double dRustDist = 3.0d;
	public EntitySpearManagerEx(World par1World) {
		super(par1World);
	}
	
	public EntitySpearManagerEx(World par1World, EntityLivingBase entityLiving) {
		super(par1World, entityLiving);
	}

	public EntitySpearManagerEx(World par1World, EntityLivingBase entityLiving, boolean multiHit) {
		super(par1World, entityLiving, multiHit);
	}

	public EntitySpearManagerEx(World par1World, EntityLivingBase entityLiving, boolean multiHit, double rushDist) {
		this(par1World, entityLiving, multiHit);
		dRustDist = rushDist;
	}

	@Override
	public void onUpdate() {
		if (!world.isRemote) {
			Entity thrower = this.getThrower();
			if (thrower != null) {

				AxisAlignedBB bb = new AxisAlignedBB(thrower.posX - dRustDist, thrower.posY - dRustDist,
						thrower.posZ - dRustDist, thrower.posX + dRustDist, thrower.posY + dRustDist,
						thrower.posZ + dRustDist);
				if (thrower instanceof EntityLivingBase) {
					EntityLivingBase entityLiving = (EntityLivingBase) thrower;
					List<Entity> list = this.world.getEntitiesInAABBexcluding(this.getThrower(), bb,
							HyperEntitySelector.getInstance());
					list.removeAll(alreadyHitEntity);
					StylishRankManager.setNextAttackType(this.thrower, StylishRankManager.AttackTypes.DestructObject);

					for (Entity curEntity : list) {
						if (blade.isEmpty())
							break;
						ReflectionAccessHelper.setVelocity(curEntity, 0, 0, 0);
						if (curEntity instanceof EntityLivingBase) {
							EntityLivingBase curLivingEntity = (EntityLivingBase) curEntity;
							curLivingEntity.setHealth(curLivingEntity.getHealth() / 4.0f);
							curLivingEntity.addPotionEffect(new PotionEffect(IC2Potion.radiation, 100, 3));
						}
					}
					StylishRankManager.doAttack(this.thrower);
				}

			}
		}
		
		if(ticksExisted >= getLifeTime()) {
            alreadyHitEntity.clear();
            alreadyHitEntity = null;
            setDead();
        }
	}
}
