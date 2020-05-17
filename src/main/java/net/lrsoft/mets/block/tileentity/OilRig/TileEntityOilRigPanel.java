package net.lrsoft.mets.block.tileentity.OilRig;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;

public class TileEntityOilRigPanel extends TileEntityElectricMachine implements IOilRig, IHasGui {
	private BlockPos coreCoord = null;
	private boolean isStructureCompleled = false;
	public TileEntityOilRigPanel() {
		super(0, 3);
	}
	
	@Override
	public void setCore(BlockPos coord) {
		coreCoord = coord;
	}

	@Override
	public void setStructureComplete(boolean isCompleted) {
		isStructureCompleled = isCompleted;
	}

	@Override
	public ModuleType getModuleType() {
		// TODO Auto-generated method stub
		return ModuleType.Base;
	}
	
	public String getGuiExtraInfo()
	{
		return "";
	}
	

	@Override
	public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
		return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
	}

	@Override
	public ContainerBase<?> getGuiContainer(EntityPlayer player) {
		  return (ContainerBase)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
	}

	@Override
	public void onGuiClosed(EntityPlayer arg0) {

	}

}
