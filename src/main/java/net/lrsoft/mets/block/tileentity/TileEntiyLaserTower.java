package net.lrsoft.mets.block.tileentity;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import mods.flammpfeil.slashblade.SlashBlade;
import net.lrsoft.mets.entity.EntityGunBullet;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.SoundManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TileEntiyLaserTower extends TileEntityElectricMachine {
	private static Vec3d shootOffset = new Vec3d(0.5d, 1.0d, 0.5d);
	private static int scanRange = 15;
	private static int maxEnergy = 50000, tier = 2;
	private static double attackConsume = ConfigManager.LaserTowerCost;
	private EntityPlayer owner;
	
	private int updateInterval = 45, currentInterval = 0;
	public TileEntiyLaserTower() {
		super(maxEnergy, tier);
	}
	
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		updateTileEntity();
	}

	private void updateTileEntity() {
		boolean shouldActive = false;
		if (this.energy.canUseEnergy(attackConsume)) {
			shouldActive = true;
			
			currentInterval++;
			if(currentInterval >= updateInterval) {
				Vec3d currentPos = new Vec3d(this.pos.getX(), pos.getY(), pos.getZ());
				currentPos = currentPos.add(shootOffset);

				AxisAlignedBB bb = new AxisAlignedBB(
						currentPos.x - scanRange, currentPos.y - scanRange, currentPos.z - scanRange, 
						currentPos.x + scanRange, currentPos.y + scanRange, currentPos.z + scanRange);
				List<Entity> list = this.world.getEntitiesInAABBexcluding(null, bb, EntityMob.MOB_SELECTOR);
				if (!list.isEmpty() && this.energy.useEnergy(attackConsume)) {
					Entity mob = list.get(0);

					double yOffset = mob.getEyeHeight();
					Vec3d mobPos = new Vec3d(mob.posX, mob.posY + yOffset, mob.posZ);
					mobPos = mobPos.subtract(currentPos);
					
					double vecLength = mobPos.lengthVector();
					double tVecLength = 1.0D - vecLength;
					tVecLength = tVecLength * tVecLength;
					
					Vec3d motion = new Vec3d(mobPos.x / vecLength * tVecLength, mobPos.y / vecLength * tVecLength, mobPos.z / vecLength * tVecLength);
					
					EntityGunBullet bullet = new EntityGunBullet(world, currentPos, 12f, 360, true);
					bullet.shoot(motion.x, motion.y, motion.z, 3f, 0.0f);
					
					world.spawnEntity(bullet);	
					
					world.playSound((EntityPlayer)null, currentPos.x , currentPos.y, currentPos.z, 
							SoundManager.laser_bullet_shoot, SoundCategory.AMBIENT, 0.1f, 0.55F);
				}

				currentInterval = 0;
			}
		}else {
			currentInterval = 0;
		}
		setActive(shouldActive);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("shootInterval", currentInterval);
		return tag;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		currentInterval = tag.getInteger("shootInterval");
	}

}
