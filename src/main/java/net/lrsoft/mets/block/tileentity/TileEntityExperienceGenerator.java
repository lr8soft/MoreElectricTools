package net.lrsoft.mets.block.tileentity;

import java.util.List;
import java.util.Random;

import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.network.GuiSynced;
import net.lrsoft.mets.gui.DropGeneratorSlot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityExperienceGenerator extends TileEntityBaseGenerator {
	public TileEntityExperienceGenerator() {
		super(160.0D, 3, 1000000);
	}
	
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		updateEntity();
	}
	
	@SideOnly(Side.CLIENT)
	protected void updateEntityClient() {
		super.updateEntityClient();
		updateEntity();
	}
	
	private void updateEntity()
	{
		Vec3i offset = new Vec3i(5, 5, 5);
		BlockPos minPos = this.pos.subtract(offset);
		BlockPos maxPos = this.pos.add(offset);
		AxisAlignedBB bb = new AxisAlignedBB(minPos, maxPos);//new AxisAlignedBB(this.pos);
		List<Entity> list = world.getEntitiesWithinAABB(EntityXPOrb.class, bb);
		for(Entity entity : list)
		{
			if (entity instanceof EntityXPOrb) {
				EntityXPOrb entityXPOrb = (EntityXPOrb) entity;
				double dist = this.pos.distanceSq(entityXPOrb.posX - 0.5f, entityXPOrb.posY - 0.5f, entityXPOrb.posZ - 0.5f);
				if (dist <= 1.0f) {
					fuel += entityXPOrb.getXpValue();
					entityXPOrb.setDead();
				} else {
		            double d1 = (this.pos.getX() - entityXPOrb.posX + 0.5f) / 8.0D;
		            double d2 = (this.pos.getY() - entityXPOrb.posY + 0.5f) / 8.0D;
		            double d3 = (this.pos.getZ() - entityXPOrb.posZ + 0.5f) / 8.0D;
		            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
		            double d5 = 1.0D - d4;

		            if (d5 > 0.0D)
		            {
		                d5 = d5 * d5;
		                entityXPOrb.motionX += d1 / d4 * d5 * 0.05D;
		                entityXPOrb.motionY += d2 / d4 * d5 * 0.05D;
		                entityXPOrb.motionZ += d3 / d4 * d5 * 0.05D;
		            }
				}
			}
		}
	}
	
	public String getOperationSoundFile() {
		return "Generators/GeneratorLoop.ogg";
	}
	
	public boolean isConverting() {
		return (this.fuel > 0);
	}
	
	@Override
	public boolean gainFuel() {
		if (fuel > 0)
		{
			fuel -= 1;
			return true;
		}else {
			return false;
		}
	}
	

}
