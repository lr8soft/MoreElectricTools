package net.lrsoft.mets.block.tileentity;

import ic2.core.block.generator.tileentity.TileEntityStirlingGenerator;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;

public class TileEntityAdvancedStirlingGenerator extends TileEntityStirlingGenerator {
	 private final double productionPeerHeat = (0.85F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Stirling"));
	 
	 @Override
	 protected double getMultiplier() {
		   return productionPeerHeat;
	 }
}
