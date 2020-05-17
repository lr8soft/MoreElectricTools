package net.lrsoft.mets.block.tileentity.OilRig;

import ic2.core.block.TileEntityBlock;
import net.minecraft.util.math.BlockPos;

public class TileEntityOilRigBase extends TileEntityBlock implements IOilRig {

	@Override
	public void setCore(BlockPos coord) {

	}

	@Override
	public void setStructureComplete(boolean isCompleted) {

	}

	@Override
	public ModuleType getModuleType() {
		return ModuleType.Base;
	}

}
