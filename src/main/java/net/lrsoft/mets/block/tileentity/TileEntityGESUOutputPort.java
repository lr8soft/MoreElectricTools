package net.lrsoft.mets.block.tileentity;

import java.lang.reflect.Field;

import ic2.core.block.comp.Energy;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityGESUOutputPort extends TileEntityBaseGenerator implements IGesuPart{
	private Vec3d corePosition = null;
	private boolean isStructureCompleted = false;
	private static int transferSpeed = 81920;
	public TileEntityGESUOutputPort() {
		super(transferSpeed, 5, transferSpeed);

	}
	
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		updateTileEntity();
	}
	
	
	private void updateTileEntity()
	{
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
	

	@Override
	public boolean gainFuel() {
		if(isStructureCompleted && this.energy.getEnergy() <= 0.0d) 
		{
			TileEntity te = this.world.getTileEntity(new BlockPos(corePosition));
			if (te instanceof TileEntityGESUCore) {
				TileEntityGESUCore core = (TileEntityGESUCore) te;
				if(core.consumeFuel(1.0))
				{
					this.energy.addEnergy(transferSpeed);
					return true;
				}
			}
		}
		return false;
	}
	
}
