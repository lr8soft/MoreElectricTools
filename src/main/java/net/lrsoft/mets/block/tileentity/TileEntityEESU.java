package net.lrsoft.mets.block.tileentity;

import ic2.core.block.wiring.TileEntityElectricBlock;
import ic2.core.profile.NotClassic;

public class TileEntityEESU extends TileEntityElectricBlock implements IMets {
	public final static int maxStorageEnergy = 400000000; 
	public TileEntityEESU() 
	{
		super(5, 8192, maxStorageEnergy);
	}
}
