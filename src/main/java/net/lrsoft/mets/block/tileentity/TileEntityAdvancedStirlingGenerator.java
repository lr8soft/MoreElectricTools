package net.lrsoft.mets.block.tileentity;

import ic2.api.energy.tile.IHeatSource;
import ic2.core.block.generator.tileentity.TileEntityStirlingGenerator;
import ic2.core.block.heatgenerator.tileentity.TileEntityElectricHeatGenerator;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAdvancedStirlingGenerator extends TileEntityStirlingGenerator {
	 private final double productionPeerHeat = (0.85F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Stirling"));
	 private double effectivePercent = 1.0d;
	 @Override
	 protected double getMultiplier() {
		   return productionPeerHeat * effectivePercent;
	 }

	@Override
	protected void updateSource() {
		if (this.source == null || ((TileEntity) this.source).isInvalid()) {
			TileEntity te = this.world.getTileEntity(this.pos.offset(getFacing()));

			if (te instanceof IHeatSource) {
				this.source = (IHeatSource) te;
				if (te instanceof TileEntityElectricHeatGenerator) {
					effectivePercent = 0.1d;
				} else {
					effectivePercent = 1.0d;
				}
			} else {
				this.source = null;
			}
		}
	}
}
