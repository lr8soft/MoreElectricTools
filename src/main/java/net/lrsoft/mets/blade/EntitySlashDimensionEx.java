package net.lrsoft.mets.blade;

import java.util.List;

import mods.flammpfeil.slashblade.entity.EntitySlashDimension;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorDestructable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;

public class EntitySlashDimensionEx extends EntitySlashDimension {
	private EntityPlayer shooter;
	private int updateTick = 0;
	public EntitySlashDimensionEx(World par1World) {
		super(par1World);
		setSize(2.0F, 2.0F);
	}

	public EntitySlashDimensionEx(World par1World, EntityPlayer entityLiving, float AttackLevel) {
		super(par1World, entityLiving, AttackLevel);
		setSize(2.0F, 2.0F);
		shooter = entityLiving;
	}

    @Override
    public void onUpdate()
    {
    	super.onUpdate();
    	
    	updateTick++;
    	
    	if(updateTick % 3 == 0) {
    		Vec3i offset = new Vec3i(8, 8, 8);
        	BlockPos currentPos  = new BlockPos(this.posX, this.posY, this.posZ);
    		BlockPos minPos = currentPos.subtract(offset);
    		BlockPos maxPos = currentPos.add(offset);
    		AxisAlignedBB bb = new AxisAlignedBB(minPos, maxPos);
    		
        	List<Entity> list = this.world.getEntitiesInAABBexcluding(shooter, bb, HyperEntitySelector.getInstance());
        	for(Entity curEntity : list){
        		if(curEntity instanceof EntityMob) {
		            double d1 = (this.posX - curEntity.posX + 0.5d) / 8.0D;
		            double d2 = (this.posY - curEntity.posY + 0.5d) / 8.0D;
		            double d3 = (this.posZ - curEntity.posZ + 0.5d) / 8.0D;
		            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
		            double d5 = 1.0D - d4;

		            if (d5 > 0.0D) {
		                d5 = d5 * d5;
		                curEntity.motionX += d1 / d4 * d5 * 5D;
		                curEntity.motionY += d2 / d4 * d5 * 5D;
		                curEntity.motionZ += d3 / d4 * d5 * 5D;
		            }else {
		                curEntity.motionX -= d1 / d4 * d5 * 5D;
		                curEntity.motionY -= d2 / d4 * d5 * 5D;
		                curEntity.motionZ -= d3 / d4 * d5 * 5D;
		            }
		            
	            	if(shooter != null && curEntity != shooter) {
			            curEntity.hurtResistantTime = 0;
	            		curEntity.attackEntityFrom(DamageSource.causePlayerDamage(shooter), 4.0f);
	            	}
        		}
        	}
        	updateTick = 0;
    	}

    }
}
