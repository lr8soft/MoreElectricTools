package net.lrsoft.mets.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.lrsoft.mets.renderer.particle.XCustomizedParticle;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityParticleGroup extends Entity {

	protected EntityPlayer shooter;
	protected int ticksInAir;
	protected int maxExistTicks;
	protected int spawnParticles;
	protected int spawnParticlesPerTick;
	protected int count;
	protected float velocity;
	
	public EntityParticleGroup(World world, EntityPlayer owner, int count, int maxTick, int spawnParticles,int spawnParticlesPerTick) {
		super(world);
		this.ticksInAir = 0;
		this.shooter = owner;
		setSize(0.39F, 0.39F);
	
		setPosition(owner.posX, owner.posY + (double)shooter.getEyeHeight() - 0.1, owner.posZ);
		this.count = count;
		this.maxExistTicks = maxTick;
		this.spawnParticles = spawnParticles;
		this.spawnParticlesPerTick = spawnParticlesPerTick;
	}
	
	public EntityParticleGroup(World world,  int count, int maxTick, int spawnParticles,int spawnParticlesPerTick) {
		super(world);
		this.ticksInAir = 0;
		setSize(0.39F, 0.39F);
		this.count = count;
		this.maxExistTicks = maxTick;
		this.spawnParticles = spawnParticles;
		this.spawnParticlesPerTick = spawnParticlesPerTick;
	}
	
	
	@Override
	public void onUpdate() 
	{
		super.onUpdate();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f) * (180D / Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }
        BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        
        if (iblockstate.getMaterial() != Material.AIR)
        {
            AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);

            if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ)))
            {
            //    setDead();
            //    return;
            }
        }

        ++this.ticksInAir;
 
        if(this.ticksInAir > maxExistTicks)
        {
        	setDead();
        	return;
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;

        this.setPosition(this.posX, this.posY, this.posZ);

        this.doBlockCollisions();
	}
	
	public void shoot(float yaw, float pitch, float velocity)
	{
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float f1 = -MathHelper.sin(pitch * 0.017453292F);
        float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.shoot((double)f, (double)f1, (double)f2, velocity);
        
        this.motionX += shooter.motionX;
        this.motionY += shooter.motionY;
        this.motionZ += shooter.motionZ;
        this.velocity = velocity;

	}
	
	public void shoot(Vec3d nowPosition, Vec3d targetPosition)
	{
		setPosition(nowPosition.x, nowPosition.y, nowPosition.z);
		
		/* double d1 = (targetPosition.x - nowPosition.x) /8.0d;
         double d2 = (targetPosition.y - nowPosition.y)/8.0d ;
         double d3 = (targetPosition.z - nowPosition.z)/8.0d;
         double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
         double d5 = 1.0D - d4;

		d5 = d5 * d5;
		this.motionX = d1 / d4 * d5 * 0.05D;
		this.motionY = d2 / d4 * d5 * 0.05D;
		this.motionZ = d3 / d4 * d5 * 0.05D;

		double v = Math.pow(motionX * motionX + motionY * motionY + motionZ * motionZ, 0.5d);
		double s = Math.pow(d1 * d1 + d2 * d2 + d3 * d3, 0.5d);
		this.maxExistTicks = (int) (s / v) * 20;
             
		this.rotationYaw = 0.0f;
        this.rotationPitch = 0.0f;
        this.prevRotationYaw = 0.0f;
        this.prevRotationPitch = 0.0f;   */
		
		shoot(targetPosition.x, targetPosition.y, targetPosition.z, 0.5f);
	}

	
    protected void shoot(double x, double y, double z, float velocity)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + this.rand.nextGaussian() * 0.007;
        y = y + this.rand.nextGaussian() * 0.007;
        z = z + this.rand.nextGaussian() * 0.007;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }
  
    
    public int getParticleSpawnCount()
    {
    	return spawnParticles;
    }
    
    public int getParticleSpawnPerTick()
    {
    	return spawnParticlesPerTick;
    }
   
	@Override
	protected void entityInit(){}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
		this.ticksInAir =  compound.getInteger("ticksInAir");
		this.count = compound.getInteger("count");
		this.velocity = compound.getFloat("velocity");
		this.maxExistTicks = compound.getInteger("maxExistTicks");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) 
	{
        compound.setInteger("ticksInAir", this.ticksInAir);
        compound.setInteger("count", this.count);
        compound.setFloat("velocity", this.velocity);
        compound.setInteger("maxExistTicks", this.maxExistTicks);
	}
}

