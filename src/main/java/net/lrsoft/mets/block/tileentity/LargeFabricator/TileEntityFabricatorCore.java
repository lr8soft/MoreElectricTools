package net.lrsoft.mets.block.tileentity.LargeFabricator;

import ic2.core.block.TileEntityBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;

public class TileEntityFabricatorCore extends TileEntityBlock{
	private int currentTick = 0;
	private boolean isStructureComplete = false;
	private int scrapFuel = 0;
	
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		if(currentTick % 15 == 0)
		{
			checkStructureComplete();
		}
		currentTick++;
	}
	
	private void checkStructureComplete()
	{
		TileEntity upPart = this.world.getTileEntity(pos.add(0, 1, 0));
		TileEntity downPart = this.world.getTileEntity(pos.add(0, -1, 0));
		TileEntity northPart = this.world.getTileEntity(pos.add(1, 0, 0));
		TileEntity southPart = this.world.getTileEntity(pos.add(-1, 0, 0));
		TileEntity westPart = this.world.getTileEntity(pos.add(0, 0, 1));
		TileEntity eastPart = this.world.getTileEntity(pos.add(0, 0, -1));
		if(checkPortAndUpdate(upPart) && checkPortAndUpdate(downPart) && 
				checkPortAndUpdate(northPart) && checkPortAndUpdate(southPart) && 
				checkPortAndUpdate(westPart) && checkPortAndUpdate(eastPart))
		{
			((IFabricatorPart)upPart).setCoreComplete(true);
			((IFabricatorPart)downPart).setCoreComplete(true);
			((IFabricatorPart)northPart).setCoreComplete(true);
			((IFabricatorPart)southPart).setCoreComplete(true);
			((IFabricatorPart)westPart).setCoreComplete(true);
			((IFabricatorPart)eastPart).setCoreComplete(true);
			isStructureComplete = true;
		}else 
		{
			isStructureComplete = false;
		}
	}
	
	private boolean checkPortAndUpdate(TileEntity te)
	{
		if(te != null && te instanceof IFabricatorPart)
		{
			IFabricatorPart port = (IFabricatorPart)te;
			port.setCore(new Vec3d(this.pos));
			port.setCoreComplete(false);
			return true;
		}
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		scrapFuel = compound.getInteger("scrapFuel");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound nbtTagCompound = super.writeToNBT(compound);
		nbtTagCompound.setInteger("scrapFuel", scrapFuel);
		return nbtTagCompound;
	}
}
