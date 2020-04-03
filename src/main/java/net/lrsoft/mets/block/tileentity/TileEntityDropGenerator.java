package net.lrsoft.mets.block.tileentity;

import java.util.List;
import java.util.Random;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import net.lrsoft.mets.util.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityDropGenerator extends TileEntityBaseGenerator implements IMets{


	public TileEntityDropGenerator() {
		super(20.0D, 2, 10000);
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
		Vec3i offset = new Vec3i(3, 3, 3);
		BlockPos minPos = this.pos.subtract(offset);
		BlockPos maxPos = this.pos.add(offset);
		AxisAlignedBB bb = new AxisAlignedBB(minPos, maxPos);//new AxisAlignedBB(this.pos);
		List<Entity> list = world.getEntitiesWithinAABB(EntityItem.class, bb);
		for(Entity entity : list)
		{
			if (entity instanceof EntityItem) {
				EntityItem entityItem = (EntityItem) entity;
				ItemStack currentItem = entityItem.getItem();
				EnumRarity itemRarity = currentItem.getRarity();
				double dist = this.pos.distanceSq(entityItem.posX - 0.5f, entityItem.posY - 0.5f,
						entityItem.posZ - 0.5f);
				if (dist <= 1.0f) {
					int itemValue = 0;
					switch(itemRarity) 
					{
					case COMMON:
						itemValue = 400; break;
					case UNCOMMON:
						itemValue = 800; break;
					case EPIC:
						itemValue = 5000; break;
					case RARE:
						itemValue = 10000; break;
					}
					fuel += itemValue;
					currentItem.setCount(currentItem.getCount() - 1);
					entityItem.setItem(currentItem);
				} else {
					double d1 = (this.pos.getX() - entityItem.posX + 0.5f) / 8.0D;
					double d2 = (this.pos.getY() - entityItem.posY + 0.5f) / 8.0D;
					double d3 = (this.pos.getZ() - entityItem.posZ + 0.5f) / 8.0D;
					double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
					double d5 = 1.0D - d4;

					if (d5 > 0.0D) {
						d5 = d5 * d5;
						entityItem.motionX += d1 / d4 * d5 * 0.05D;
						entityItem.motionY += d2 / d4 * d5 * 0.05D;
						entityItem.motionZ += d3 / d4 * d5 * 0.05D;
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
