package net.lrsoft.mets.manager;

import ic2.core.block.wiring.ContainerElectricBlock;
import net.lrsoft.mets.block.gui.GuiLESU;
import net.lrsoft.mets.block.tileentity.TileEntityLESU;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiManager implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity currentEntity = world.getTileEntity(new BlockPos(x, y, z));
		if(currentEntity instanceof TileEntityLESU)
		{
			return new ContainerElectricBlock(player, (TileEntityLESU) currentEntity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity currentEntity = world.getTileEntity(new BlockPos(x, y, z));
		if(currentEntity instanceof TileEntityLESU)
		{
			return new GuiLESU(new ContainerElectricBlock(player, (TileEntityLESU) currentEntity));
		}
		return null;
	}

}
