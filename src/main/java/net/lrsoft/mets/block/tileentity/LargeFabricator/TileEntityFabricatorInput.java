package net.lrsoft.mets.block.tileentity.LargeFabricator;

import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.lrsoft.mets.block.tileentity.OilRig.TileEntityGUIMachine;
import net.minecraft.util.math.Vec3d;

public class TileEntityFabricatorInput extends TileEntityGUIMachine implements IFabricatorPart {
	private Vec3d coreCoord = null;
	private boolean isStructureCompleled = false;
	public TileEntityFabricatorInput() {
		super((int) (1000000 * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor")), 5);
	}
	
	public boolean canConsume(double amount)
	{
		return this.energy.canUseEnergy(amount);
	}
	
	public void consumeEnergy(double amount)
	{
		this.energy.useEnergy(amount);
	}
	
	public double getMaxEnergy()
	{
		return this.energy.getCapacity();
	}

	@Override
	public void setCore(Vec3d coord) {
		coreCoord = coord;
	}

	@Override
	public void setCoreComplete(boolean isComplete) {
		isStructureCompleled = isComplete;
	}

	@Override
	public FabricatorType getModuleType() {
		return FabricatorType.Input;
	}

}
