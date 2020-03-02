package net.lrsoft.mets.block.tileentity;

import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.wiring.CableType;
import ic2.core.block.wiring.TileEntityCable;
import ic2.core.profile.NotClassic;

@NotClassic
public class TileEntitySuperconductingCable extends TileEntityCable{
	public static Class<? extends TileEntityCable> delegate()
	{
	   return (Class)TileEntitySuperconductingCable.class;
	}
	
	public TileEntitySuperconductingCable() {
		super(CableType.valueOf("superconducting"), 0);
	}
	
}
