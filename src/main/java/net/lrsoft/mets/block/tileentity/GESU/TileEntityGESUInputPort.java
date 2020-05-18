package net.lrsoft.mets.block.tileentity.GESU;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import ic2.core.block.comp.Energy;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TileEntityGESUInputPort extends TileEntityElectricMachine implements IGESUPart{
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
		if (isStructureCompleted && this.energy.canUseEnergy(transferSpeed)) 
		{
			TileEntity te = this.world.getTileEntity(new BlockPos(corePosition));
			if(te != null && te instanceof TileEntityGESUCore)
			{
				TileEntityGESUCore core = (TileEntityGESUCore)te;
				if(core.addFuel(1.0))
				{
					this.energy.useEnergy(transferSpeed);
					setActive(true);
				}
			}else {
				resume();
			}
		}else 
		{
			setActive(false);
		}
	}
	
	private void resume()
	{
		isStructureCompleted = false;
		corePosition = null;
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
