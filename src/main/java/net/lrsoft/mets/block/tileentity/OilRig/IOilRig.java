package net.lrsoft.mets.block.tileentity.OilRig;

import net.minecraft.util.math.BlockPos;

public interface IOilRig {
	enum ModuleType{ Input, Output, Base, Panel}
	
	void setCore(BlockPos coord);
	void setStructureComplete(boolean isCompleted);
	
	ModuleType getModuleType();
}
