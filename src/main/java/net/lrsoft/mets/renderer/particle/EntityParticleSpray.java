package net.lrsoft.mets.renderer.particle;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityParticleSpray extends Entity {

	protected Entity shooter;
	protected int ticksInAir;
	protected int maxExistTicks;
	protected int spawnParticles;
	protected float velocity;
	protected boolean haveInit = false;
	protected Vec3d color;
	protected Vec3d scaleSize = new Vec3d(0.1d, 0.1d, 0.1d);
	
	public EntityParticleSpray(World world)
	{
		super(world);
		this.ticksInAir = 0;
		setSize(0.39F, 0.39F);
		this.maxExistTicks = 0;
		this.spawnParticles = 0;
		this.velocity = 0;
	}
	
	public EntityParticleSpray(World world, Entity owner, Vec3d color, int maxTick, int spawnParticles, boolean useLastPos) {
		super(world);
		this.ticksInAir = 0;
		this.shooter = owner;
		setSize(0.39F, 0.39F);
	
		if(!useLastPos)
			setPosition(owner.posX, owner.posY + (double)shooter.getEyeHeight() - 0.1, owner.posZ);
		else
			setPosition(owner.lastTickPosX, owner.lastTickPosY + (double)shooter.getEyeHeight() - 0.1, owner.lastTickPosZ);
		this.maxExistTicks = maxTick;
		this.spawnParticles = spawnParticles;
		this.color = color;
	}
	
	public EntityParticleSpray(World world, Entity owner, Vec3d color, int maxTick, int spawnParticles) 
	{
		this(world, owner, color, maxTick, spawnParticles, false);
	}
	
	public EntityParticleSpray(World world,  Vec3d postion, Vec3d color, int maxTick, int spawnParticles) 
	{
		super(world);
		this.ticksInAir = 0;
		setSize(0.39F, 0.39F);
		setPosition(postion.x, postion.y, postion.z);
		this.maxExistTicks = maxTick;
		this.spawnParticles = spawnParticles;
		this.color = color;
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
                setDead();
                return;
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
        
        if (!this.hasNoGravity())
        {
            this.motionY -= 0.08d;
        }

        this.setPosition(this.posX, this.posY, this.posZ);

        //this.doBlockCollisions();
	}
	
	public void shoot(float yaw, float pitch, float velocity)
	{
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float f1 = -MathHelper.sin(pitch * 0.017453292F);
        float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.shoot((double)f, (double)f1, (double)f2, velocity);
        
        if(shooter != null)
        {
	        this.motionX += shooter.motionX;
	        this.motionY += shooter.motionY;
	        this.motionZ += shooter.motionZ;        	
        }

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
        x = x + this.rand.nextGaussian() * 0.1;
        y = y + this.rand.nextGaussian() * 0.1;
        z = z + this.rand.nextGaussian() * 0.1;
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
    
    public boolean getIsInit()
    {
    	return haveInit;
    }
    
    public void setIsInit(boolean value)
    {
    	haveInit = value;
    }
    
    public Vec3d getColor()
    {
    	return color;
    }
    
    
    public void setScaleSize(Vec3d size)
    {
    	this.scaleSize = size;
    }
    
    public Vec3d getScaleSize()
    {
    	return scaleSize;
    }
    
   
	@Override
	protected void entityInit(){}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
		this.ticksInAir =  compound.getInteger("ticksInAir");
		this.spawnParticles = compound.getInteger("spawnParticles");
		this.velocity = compound.getFloat("velocity");
		this.maxExistTicks = compound.getInteger("maxExistTicks");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) 
	{
        compound.setInteger("ticksInAir", this.ticksInAir);
        compound.setInteger("spawnParticles", this.spawnParticles);
        compound.setFloat("velocity", this.velocity);
        compound.setInteger("maxExistTicks", this.maxExistTicks);
	}
}


