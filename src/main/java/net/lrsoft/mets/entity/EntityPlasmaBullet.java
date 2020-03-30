package net.lrsoft.mets.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.renderer.particle.EntityParticleSpray;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityPlasmaBullet extends Entity {
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
	protected Vec3d scaleSize = new Vec3d(0.1d, 0.1d, 0.1d);
	public EntityPlasmaBullet(World world, EntityPlayer owner, float power) {
		super(world);
		this.ticksInAir = 0;
		this.shooter = owner;
		this.maxExistTicks = 500;
		setSize(0.39F, 0.39F);
	
		setPosition(owner.posX, owner.posY + (double)shooter.getEyeHeight() - 0.1, owner.posZ);
		this.power = power;
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
        TileEntity te = world.getTileEntity(blockpos);
        if (iblockstate.getMaterial() != Material.AIR)
        {
            AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);

            if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ)))
            {
            	causeRangeDamage();
            	sprayEffect();
                setDead();
                return;
            }
        }
        ++ticksInAir;
		Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
		vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
		vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (raytraceresult != null) {
			vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
		}

		Entity entity = this.findEntityOnPath(vec3d1, vec3d);

		if (entity != null) {
			raytraceresult = new RayTraceResult(entity);
		}

		if (raytraceresult != null && !isDead) {
			Entity target = raytraceresult.entityHit;
			if (target != null) {
				causeRangeDamage();
				sprayEffect();
				setDead();
				return;
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;

		if (!this.hasNoGravity()) {
			this.motionY -= 0.05d;
		}

		this.setPosition(this.posX, this.posY, this.posZ);

		this.doBlockCollisions();  	

	}
	
	private void sprayEffect()
	{
		EntityParticleSpray particleSpray = new EntityParticleSpray(world, this, new Vec3d(0.8f, 1.0f, 1.0f), 500, 9, true);
		particleSpray.shoot(this.rotationYaw, this.rotationPitch, 3.0f);
		particleSpray.setScaleSize(new Vec3d(0.1d, 0.1d, 0.1d));
		world.spawnEntity(particleSpray);
	}
	
	protected void causeRangeDamage()
	{
		Vec3i offset = new Vec3i(5, 5, 5);
    	BlockPos currentPos  = new BlockPos(this.posX, this.posY, this.posZ);
		BlockPos minPos = currentPos.subtract(offset);
		BlockPos maxPos = currentPos.add(offset);
		AxisAlignedBB bb = new AxisAlignedBB(minPos, maxPos);
		List<Entity> list = world.getEntitiesInAABBexcluding(this, bb, GUN_TARGETS);
		for (Entity curEntity : list) {
			if(curEntity instanceof EntityLivingBase)
			{
				EntityLivingBase livingBase = (EntityLivingBase) curEntity;
				if(shooter != null) 
				{
					livingBase.attackEntityFrom(DamageSource.causePlayerDamage(shooter), power);	
					Explosion exp =  new Explosion(world, this,  this.posX, this.posY, this.posZ, 1.0f, false, false);
					exp.doExplosionA();
					exp.doExplosionB(true);
				}else 
				{
					livingBase.attackEntityFrom(DamageSource.GENERIC, power);
				}
			}
		}
		
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
	
	public void shoot(Vec3d pos,Vec3d target)
	{
		double d1 = (target.x - pos.x)/8f ;
		double d2 = (target.y - pos.y)/8f ;
		double d3 = (target.z - pos.z) /8f;
		double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
		shoot(d1, d2 + d4, d3, 1.0f);
		
	}
	
    public void shoot(double x, double y, double z, float velocity)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
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

    public float getBulletPower()
    {
    	return power;//this.pow
    }
   
	@Override
	protected void entityInit(){}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
		this.ticksInAir =  compound.getInteger("ticksInAir");
		this.velocity = compound.getFloat("velocity");
		this.maxExistTicks = compound.getInteger("maxExistTicks");
		this.power = compound.getFloat("power");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) 
	{
        compound.setInteger("ticksInAir", this.ticksInAir);
        compound.setFloat("velocity", this.velocity);
        compound.setInteger("maxExistTicks", this.maxExistTicks);
        compound.setFloat("compound", this.power);
	}
}

