package net.lrsoft.mets.block.tileentity.GESU;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import ic2.api.network.NetworkHelper;
import ic2.core.block.comp.Energy;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;
import net.lrsoft.mets.block.tileentity.OilRig.TileEntityGUIMachine;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TileEntityGESUInputPort extends TileEntityGUIMachine implements IGESUPart{
	private Vec3d corePosition = null;
	private boolean isStructureCompleted = false;
	private static int transferSpeed = 81920;
	
	private int tick = 0;
	private double remainingEU =  0.0d;
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
		if (isStructureCompleted) {
			if (this.energy.canUseEnergy(transferSpeed)) {
				TileEntity te = this.world.getTileEntity(new BlockPos(corePosition));
				if (te != null && te instanceof TileEntityGESUCore) {
					TileEntityGESUCore core = (TileEntityGESUCore) te;
					if (core.addFuel(1.0)) {
						this.energy.useEnergy(transferSpeed);
						setActive(true);
					}
				} else {
					resume();
					setActive(false);
				}
			}
			
			if(tick % 15 == 0)
			{
				TileEntity te = this.world.getTileEntity(new BlockPos(corePosition));
				if (te != null && te instanceof TileEntityGESUCore) {
					TileEntityGESUCore core = (TileEntityGESUCore) te;
					remainingEU = core.getGESUFuel() * 81920.0d;
					NetworkHelper.updateTileEntityField(this, "remainingEU");
				}else 
				{
					resume();
				}
			}
			tick++;
		} else {
			setActive(false);
		}
	}
	
	private void resume()
	{
		isStructureCompleted = false;
		corePosition = null;
	}
	
	
	private static  DecimalFormat energyFormat = new DecimalFormat("#.#");
	public String getTotalEnergy() {
		return energyFormat.format(remainingEU);
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
