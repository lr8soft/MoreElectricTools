package net.lrsoft.mets.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityLighter extends Entity{
	protected int ticksInAir;
	protected int maxExistTicks;
	protected int lightStengthen;
	public EntityLighter(World world, EntityPlayer owner, int lightStengthen, int maxTick) {
		super(world);
		this.ticksInAir = 0;
		setSize(0.1F, 0.1F);
		setPosition(owner.posX, owner.posY, owner.posZ);
		this.lightStengthen = lightStengthen;
		this.maxExistTicks = maxTick;
	}
	
	@Override
	public void onUpdate() 
	{
		super.onUpdate();
		if(ticksInAir < maxExistTicks)
		{
			BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
			int currentLightStrength = world.getLight(pos);
			this.world.setLightFor(EnumSkyBlock.BLOCK, pos, currentLightStrength + lightStengthen);
			ticksInAir++;
		}
		else 
		{
			setDead();
		}

	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(amount > 0.0d)
			setDead();
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	protected void entityInit(){}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
		this.ticksInAir =  compound.getInteger("ticksInAir");
		this.lightStengthen = compound.getInteger("lightStengthen");
		this.maxExistTicks = compound.getInteger("maxExistTicks");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) 
	{
        compound.setInteger("ticksInAir", this.ticksInAir);
        compound.setInteger("lightStengthen", this.lightStengthen);
        compound.setInteger("maxExistTicks", this.maxExistTicks);
	}
}
