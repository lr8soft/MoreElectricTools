package net.lrsoft.mets.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import ic2.core.util.Vector3;
import net.lrsoft.mets.renderer.particle.EntityParticleSpray;
import net.lrsoft.mets.util.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityGunBullet extends Entity {
	public static final Predicate<Entity> GUN_TARGETS = Predicates.and(EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity entity)
        {
            return entity.canBeCollidedWith();
        }
    });
	
	protected EntityPlayer shooter;
	protected int ticksInAir;
	protected int maxExistTicks;
	protected float power;
	protected float velocity;
	
	public EntityGunBullet(World world, EntityPlayer owner, float power, int maxTick) {
		super(world);
		this.ticksInAir = 0;
		this.shooter = owner;
		setSize(0.39F, 0.39F);
	
		setPosition(owner.posX, owner.posY + (double)shooter.getEyeHeight() - 0.1, owner.posZ);
		this.power = power;
		this.maxExistTicks = maxTick;
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
            	sprayEffect();
                setDead();
                return;
            }
        }
        
        ++this.ticksInAir;
        Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
        vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
        vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        
        if (raytraceresult != null)
        {
            vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
        }
        
        Entity entity = this.findEntityOnPath(vec3d1, vec3d);

        if (entity != null)
        {
            raytraceresult = new RayTraceResult(entity);
        }
        
        if (raytraceresult != null && !isDead)
        {
            Entity target = raytraceresult.entityHit;
            if (target != null)
            {
            	target.hurtResistantTime = 0;
            	
            	if(shooter != null)
            	{
            		target.attackEntityFrom(DamageSource.causePlayerDamage(shooter), power);
            	}else {
            		target.attackEntityFrom(DamageSource.GENERIC, power);
            	}
            	sprayEffect();
            	setDead();
            	return;
            }
        }
        
        if(this.ticksInAir > maxExistTicks)
        {
        	setDead();
        	return;
        }

        //from arrow
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;

        this.setPosition(this.posX, this.posY, this.posZ);

        this.doBlockCollisions();
	}
	
	protected Entity findEntityOnPath(Vec3d start, Vec3d end)
    {
		Entity entity = null;
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this,
				this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D), GUN_TARGETS);
		double d0 = 0.0D;

		for (int i = 0; i < list.size(); ++i) {
			Entity entity1 = list.get(i);

			if (shooter != null && shooter == entity1) {
				continue;
			}
			AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.3D);
			RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);

			if (raytraceresult != null) {
				double d1 = start.squareDistanceTo(raytraceresult.hitVec);

				if (d1 < d0 || d0 == 0.0D) {
					entity = entity1;
					d0 = d1;
				}
			}
		}

        return entity;
    }
	
	protected void sprayEffect()
	{
		float initYaw =  this.rotationYaw;
		float initPitch = this.rotationPitch;
		for(int i=0; i < 2; i++)
		{
			initYaw += MathUtils.getRandomFromRange(360, 0);
			initPitch += MathUtils.getRandomFromRange(360, 0);
			EntityParticleSpray particleSpray = new EntityParticleSpray(world, this, new Vec3d(0.8f, 1.0f, 1.0f), 800, 6, true);
			particleSpray.shoot(initYaw, initPitch, 0.8f);
			particleSpray.setScaleSize(new Vec3d(0.08d, 0.08d, 0.08d));
			world.spawnEntity(particleSpray);			
		}
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

	@Override
	protected void entityInit(){}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
		this.ticksInAir =  compound.getInteger("ticksInAir");
		this.power = compound.getFloat("power");
		this.velocity = compound.getFloat("velocity");
		this.maxExistTicks = compound.getInteger("maxExistTicks");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) 
	{
        compound.setInteger("ticksInAir", this.ticksInAir);
        compound.setFloat("power", this.power);
        compound.setFloat("velocity", this.velocity);
        compound.setInteger("maxExistTicks", this.maxExistTicks);
	}
}
