package net.lrsoft.mets.block.tileentity;

import ic2.api.energy.tile.IKineticSource;
import ic2.core.block.generator.tileentity.TileEntityKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityElectricKineticGenerator;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySuperKineticGenerator extends TileEntityKineticGenerator {
	private final double euPerKu = 1.0D * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Kinetic");
	private double effectivePercent = 1.0d;
	@Override
	protected double getMultiplier() 
	{
		 return this.euPerKu * effectivePercent;
	}
	
	@Override
	protected void updateSource() {
		if (this.source == null || ((TileEntity) this.source).isInvalid()) {
			TileEntity te = this.world.getTileEntity(this.pos.offset(getFacing()));
			if (te instanceof IKineticSource) {
				this.source = (IKineticSource) te;
				if(te instanceof TileEntityElectricKineticGenerator)
				{
					effectivePercent = 0.1d;
				}else {
					effectivePercent = 1.0d;
				}
			} else {
				this.source = null;
			}
		}
	}
}
