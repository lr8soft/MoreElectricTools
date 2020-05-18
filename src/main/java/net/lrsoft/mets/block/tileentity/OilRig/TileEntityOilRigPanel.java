package net.lrsoft.mets.block.tileentity.OilRig;

import ic2.api.network.NetworkHelper;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.init.Localization;
import net.lrsoft.mets.block.tileentity.GESU.TileEntityGESUCore;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TileEntityOilRigPanel extends TileEntityElectricMachine implements IOilRig, IHasGui {
	private BlockPos coreCoord = null;
	private boolean isStructureCompleled = false, haveInfoUpdate = false;
	private int tick = 0;
	
	private int  tipState = 1;
	private int digX = -1, digY= -1, digZ= -1;
	public TileEntityOilRigPanel() {
		super(0, 3);
	}
	
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		updateTileEntity();
	}
	
	private void updateTileEntity()
	{
		if(isStructureCompleled)
		{
			if(tick % 15 == 0)
			{
				TileEntity te = this.world.getTileEntity(coreCoord);
				if (te != null && te instanceof TileEntityOilRigCore) {
					TileEntityOilRigCore core = (TileEntityOilRigCore) te;
					Vec3d coord = core.getRigCoordinate();
					digX = (int)coord.x;
					digY = (int)coord.y;
					digZ = (int)coord.z;
					tipState = core.getCoreState();
					
				}else {
					digX = digY = digZ = -1;
					tipState = 1;
				}
				updateNetworkInfo();
			}
		}else {
			digX = digY = digZ = -1;
			tipState = 1;
			if(!haveInfoUpdate)
			{
				updateNetworkInfo();
				haveInfoUpdate = true;
			}
		}
		tick++;
	}
	
	private void updateNetworkInfo()
	{
		NetworkHelper.updateTileEntityField(this, "digX");
		NetworkHelper.updateTileEntityField(this, "digY");
		NetworkHelper.updateTileEntityField(this, "digZ");
		NetworkHelper.updateTileEntityField(this, "tipState");
	}
	
	@Override
	public void setCore(BlockPos coord) {
		coreCoord = coord;
	}

	@Override
	public void setStructureComplete(boolean isCompleted) {
		isStructureCompleled = isCompleted;
		if(isCompleted)
		{
			haveInfoUpdate = false;
		}
	}

	@Override
	public ModuleType getModuleType() {
		return ModuleType.Panel;
	}
	
	public String getCoordInfo()
	{
		return  String.format("X:%d Y:%d Z:%d", digX, digY, digZ);
	}
	
	public String getCoreInfo()
	{
		switch(tipState)
		{
		case 2:
			return Localization.translate("mets.info.rig.finish");
		case 3:
			return Localization.translate("mets.info.rig.working");
		default:
			return Localization.translate("mets.info.rig.structure_incompleted");
		}
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
