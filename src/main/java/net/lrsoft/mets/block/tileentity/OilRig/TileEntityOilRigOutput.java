package net.lrsoft.mets.block.tileentity.OilRig;

import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.Fluids.InternalFluidTank;
import ic2.core.block.storage.tank.TileEntityTank;
import net.lrsoft.mets.manager.FluidManager;
import net.minecraft.util.math.BlockPos;

public class TileEntityOilRigOutput extends TileEntityTank implements IOilRig {
	public TileEntityOilRigOutput() {
		super(24);
	}

	private BlockPos coreCoord = null;
	private boolean isStructureCompleled = false;

	
	@Override
	public void setCore(BlockPos coord) {
		coreCoord = coord;
	}

	@Override
	public void setStructureComplete(boolean isCompleted) {
		isStructureCompleled = isCompleted;
	}

	@Override
	public ModuleType getModuleType() {
		return ModuleType.Output;
	}

}
