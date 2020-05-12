package net.lrsoft.mets.block.tileentity;

import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;

public class TileEntityZeroEnergyGenerator extends TileEntityBaseGenerator {

	public TileEntityZeroEnergyGenerator(double production, int tier, int maxStorage) {
		super(production, tier, maxStorage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean gainFuel() {
		// TODO Auto-generated method stub
		return false;
	}

}
