package net.lrsoft.mets.block.tileentity.OilRig;

import ic2.api.network.NetworkHelper;
import ic2.core.block.TileEntityBlock;
import ic2.core.init.Localization;
import net.lrsoft.mets.block.tileentity.GESU.TileEntityGESUCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TileEntityOilRigPanel extends TileEntityGUIMachine implements IOilRig {
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
		case -1:
			return Localization.translate("mets.info.rig.noenergy");
		case 2:
			return Localization.translate("mets.info.rig.finish");
		case 3:
			return Localization.translate("mets.info.rig.working");
		default:
			return Localization.translate("mets.info.rig.structure_incompleted");
		}
	}
}
