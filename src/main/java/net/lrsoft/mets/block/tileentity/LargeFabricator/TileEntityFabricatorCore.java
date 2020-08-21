package net.lrsoft.mets.block.tileentity.LargeFabricator;

import java.util.Vector;

import ic2.core.block.TileEntityBlock;
import ic2.core.item.type.MiscResourceType;
import ic2.core.ref.ItemName;
import ic2.core.util.LiquidUtil;
import net.lrsoft.mets.block.tileentity.OilRig.IOilRig;
import net.lrsoft.mets.manager.FluidManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityFabricatorCore extends TileEntityBlock{
	private int currentTick = 0;
	private boolean isStructureComplete = false, shouldProcess = true;
	private int scrapFuel = 0;
	
	private Vector<IFabricatorPart> inputPart = new Vector<>();
	private IFabricatorPart outputPart, scrapPart;
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		if(currentTick % 15 == 0)
		{
			checkStructureComplete();
		}
		
		if(isStructureComplete)
		{
			updateTileEntity();
		}
		currentTick++;
	}
	
	private void updateTileEntity()
	{
		TileEntityFabricatorScrap scrapSlot = null;
		if(scrapPart != null)
		{
			scrapSlot = (TileEntityFabricatorScrap)scrapPart;
		}
		for(IFabricatorPart supply : inputPart)
		{
			TileEntityFabricatorInput inputTemp = (TileEntityFabricatorInput)supply;
			if(inputTemp.canConsume(inputTemp.getMaxEnergy()))
			{
				TileEntityFabricatorTank outputTemp = (TileEntityFabricatorTank) outputPart;
				if (outputTemp != null && outputTemp.addToOutputSlot(1)) {
					float discount = (scrapSlot == null) ? 0.0f : scrapSlot.consumeScrapValue((float)inputTemp.getMaxEnergy());
					inputTemp.consumeEnergy(inputTemp.getMaxEnergy() - discount);
				}
				break;
			}
		}
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
			inputPart.clear();
			outputPart = null;
			scrapPart = null;
		}
	}
	
	private boolean checkPortAndUpdate(TileEntity te)
	{
		if(te != null && te instanceof IFabricatorPart)
		{
			IFabricatorPart port = (IFabricatorPart)te;
			port.setCore(new Vec3d(this.pos));
			port.setCoreComplete(false);
			switch (port.getModuleType()) {
			case Input:
				inputPart.add(port);
				break;
			case Tank:
				outputPart = port;
				break;
			case Scrap:
				scrapPart = port;
				break;
			default:
				break;
			}
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
