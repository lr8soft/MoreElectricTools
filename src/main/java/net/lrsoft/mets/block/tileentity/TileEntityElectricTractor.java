package net.lrsoft.mets.block.tileentity;

import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.ComparatorEmitter;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Redstone;
import ic2.core.block.comp.TileEntityComponent;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityElectricTractor extends TileEntityBlock {
	private static int tier = 1, maxEnergy = 5000;
	private final Energy energy;
	private final Redstone redstone;
	private final ComparatorEmitter comparator;
	private boolean invertRedstone;
	public TileEntityElectricTractor() {
		this.energy = (Energy)addComponent((TileEntityComponent)Energy.asBasicSink(this, 5.0D));
		this.redstone = (Redstone)addComponent((TileEntityComponent)new Redstone(this));
		this.comparator = (ComparatorEmitter)addComponent((TileEntityComponent)new ComparatorEmitter(this));
		this.comparator.setUpdate(this.energy::getComparatorValue);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.invertRedstone = nbt.getBoolean("invert");
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("invert", this.invertRedstone);
		return nbt;
	}
	
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
	}
	
}
