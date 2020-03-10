package net.lrsoft.mets.block.compenent;

import ic2.core.block.machine.container.ContainerStandardMachine;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import net.lrsoft.mets.block.tileentity.TileEntityExtrudingMachine;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerUniformSimpleMachine<T extends TileEntityStandardMachine<?, ?, ?>> extends  ContainerStandardMachine<T>{
	public ContainerUniformSimpleMachine(EntityPlayer player, T tileEntity1)
	{
		super(player, tileEntity1, 166, 55, 53, 55, 17, 116, 35, 152, 8);
	}
}
