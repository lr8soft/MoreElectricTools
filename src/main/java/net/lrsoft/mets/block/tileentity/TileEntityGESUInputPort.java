package net.lrsoft.mets.block.tileentity;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import ic2.core.block.comp.Energy;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TileEntityGESUInputPort extends TileEntityElectricMachine implements IGesuPart{
	private Vec3d corePosition = null;
	private boolean isStructureCompleted = false;
	private static int transferSpeed = 81920;
	public TileEntityGESUInputPort() {
		super(transferSpeed, Integer.MAX_VALUE);

	}
	
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		updateTileEntity();
	}
	
	private void updateTileEntity()
	{
		setActive(false);
		if (isStructureCompleted && this.energy.canUseEnergy(transferSpeed)) 
		{
			TileEntity te = this.world.getTileEntity(new BlockPos(corePosition));
			if(te instanceof TileEntityGESUCore)
			{
				TileEntityGESUCore core = (TileEntityGESUCore)te;
				if(core.addFuel(1.0))
				{
					this.energy.useEnergy(transferSpeed);
					setActive(true);
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		double x = tag.getDouble("coreX");
		double y = tag.getDouble("coreY");
		double z = tag.getDouble("coreZ");
		isStructureCompleted = tag.getBoolean("isStructureCompleted");
		corePosition = new Vec3d(x, y, z);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		if(corePosition != null) 
		{
			tag.setDouble("coreX", corePosition.x);
			tag.setDouble("coreY", corePosition.y);
			tag.setDouble("coreZ", corePosition.z);
			tag.setBoolean("isStructureCompleted", isStructureCompleted);
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
	
	@Override
	public synchronized void setCore(Vec3d coord)
	{
		this.corePosition = coord;
	}
	@Override
	public synchronized void setCoreComplete(boolean isComplete)
	{
		this.isStructureCompleted = isComplete;
	}
	
}
