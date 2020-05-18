package net.lrsoft.mets.block.tileentity.OilRig;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityGUIMachine extends TileEntityElectricMachine implements IHasGui{

	public TileEntityGUIMachine(int maxEnergy, int tier) {
		super(maxEnergy, tier);
	}


	@SideOnly(Side.CLIENT)
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
