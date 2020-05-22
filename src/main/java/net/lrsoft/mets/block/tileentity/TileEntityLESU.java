package net.lrsoft.mets.block.tileentity;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyTile;
import ic2.core.block.wiring.TileEntityElectricBlock;
import ic2.core.profile.NotClassic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityLESU extends TileEntityElectricBlock implements IMets{
	public final static int maxStorageEnergy = 1000000; 
	public TileEntityLESU() 
	{
		super(3, 512, maxStorageEnergy);
	}
}
