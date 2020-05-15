package net.lrsoft.mets.block.tileentity;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import ic2.api.network.NetworkHelper;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;
import ic2.core.network.NetworkManager;
import net.minecraft.block.Block;
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
	private double remainingEU =  0.0d;
	private int tick = 0;
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
		if(isStructureCompleted)
		{
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
			setActive(true);
		}else {
			remainingEU = 0.0d;
			NetworkHelper.updateTileEntityField(this, "remainingEU");
			setActive(false);
		}
	}
	
	private void resume()
	{
		isStructureCompleted = false;
		corePosition = null;
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
	
	private static  DecimalFormat energyFormat = new DecimalFormat("#.#");
	public String getTotalEnergy() {
		return energyFormat.format(remainingEU);
	}
	
	@Override
	public boolean gainFuel() {
		if(isStructureCompleted && this.energy.getEnergy() <= 0.0d) 
		{
			TileEntity te = this.world.getTileEntity(new BlockPos(corePosition));
			if (te instanceof TileEntityGESUCore) {
				TileEntityGESUCore core = (TileEntityGESUCore) te;
				remainingEU = core.getGESUFuel() * 81920.0d;
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
