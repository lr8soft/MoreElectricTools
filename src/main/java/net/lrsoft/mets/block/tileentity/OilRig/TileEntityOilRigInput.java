package net.lrsoft.mets.block.tileentity.OilRig;

import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import net.minecraft.util.math.BlockPos;

public class TileEntityOilRigInput extends TileEntityElectricMachine implements IOilRig {
	private BlockPos coreCoord = null;
	private boolean isStructureCompleled = false;
	public TileEntityOilRigInput() {
		super(100000, 3);
	}

	public boolean canUseEnergy(double amount) {
		return this.energy.canUseEnergy(amount);
	}
	
	public boolean comsumeEnergy(double amount) {
		return this.energy.useEnergy(amount);
	}
	
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
		return ModuleType.Input;
	}


}
