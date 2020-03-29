package net.lrsoft.mets.block.tileentity;

import java.lang.reflect.Field;

import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.Energy;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;
import net.lrsoft.mets.entity.EntityParticleGroup;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TileEntityWirelessPowerTransmissionNode extends TileEntityElectricMachine {
	private Vec3d targetPosition = new Vec3d(0, 10, 0);
	public TileEntityWirelessPowerTransmissionNode() {
		super(8192, 4);
	}
	
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		updateTileEntity();
	}
	
	private void updateTileEntity()
	{
		if (this.energy.canUseEnergy(8192)) 
		{
			setActive(true);
			if(targetPosition != null)
			{
				TileEntity targetTE = getWorld().getTileEntity(new BlockPos(targetPosition));
				if(targetTE != null) 
				{
					if(targetTE instanceof TileEntityElectricMachine)
					{
						TileEntityElectricMachine machine = (TileEntityElectricMachine)targetTE;
						try
						{
							Energy targetEnergy = getEnegryCompFromTE(machine);
							NBTTagCompound compound = targetEnergy.writeToNbt();
							
							double currentEnergy = compound.getDouble("storage");
							double maxEnergy = targetEnergy.getCapacity();
							if(currentEnergy < maxEnergy)
							{
								double useage = 8192;
								if(currentEnergy + 8192 <= maxEnergy)
								{
									compound.setDouble("storage", currentEnergy + 8192);
								}else 
								{
									compound.setDouble("storage", maxEnergy);
									useage = maxEnergy - currentEnergy;
								}
								energy.useEnergy(useage);
								targetEnergy.readFromNbt(compound);
							}
						}catch(Exception expt){
							System.out.println(expt.getMessage());
						}
					}else if(targetTE instanceof TileEntityElectricBlock)
					{
						TileEntityElectricBlock electricBlock = (TileEntityElectricBlock)targetTE;
						
						NBTTagCompound compound = electricBlock.energy.writeToNbt();
						double currentEnergy = compound.getDouble("storage");
						double maxEnergy = electricBlock.energy.getCapacity();
						if(currentEnergy < maxEnergy)
						{
							double useage = 8192;
							if(currentEnergy + 8192 <= maxEnergy)
							{
								compound.setDouble("storage", currentEnergy + 8192);
							}else 
							{
								compound.setDouble("storage", maxEnergy);
								useage = maxEnergy - currentEnergy;
							}
							energy.useEnergy(useage);
							electricBlock.energy.readFromNbt(compound);
						}
					}
				}
			}
			
		}
	}
	
	public Vec3d getTargetPosition()
	{
		return targetPosition;
	}
	
	public void setTargetPosition(Vec3d target)
	{
		this.targetPosition = target;
	}
	
	private Energy getEnegryCompFromTE(TileEntityElectricMachine te) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		Field energyField =  TileEntityElectricMachine.class.getDeclaredField("energy");
		energyField.setAccessible(true);
		return (Energy) energyField.get(te);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		double x = tag.getDouble("targetX");
		double y = tag.getDouble("targetY");
		double z = tag.getDouble("targetZ");
		targetPosition = new Vec3d(x, y, z);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		if(targetPosition != null) 
		{
			tag.setDouble("targetX", targetPosition.x);
			tag.setDouble("targetY", targetPosition.y);
			tag.setDouble("targetZ", targetPosition.z);
		}
		return tag;
	}

	public double getEnergy()
	{
		return this.energy.getEnergy();
	}
	
	public boolean useEnergy(double amount)
	{
		    return this.energy.useEnergy(amount);
	}
	
}
