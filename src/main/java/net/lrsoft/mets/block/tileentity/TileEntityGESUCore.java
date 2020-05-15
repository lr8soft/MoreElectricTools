package net.lrsoft.mets.block.tileentity;

import org.apache.commons.logging.impl.Log4JLogger;

import ic2.core.block.TileEntityBlock;
import ic2.core.util.Log;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import io.netty.util.internal.logging.Log4JLoggerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TileEntityGESUCore extends TileEntityBlock {
	private double gesuFuel = 0.0d;
	private int currentTick = 0;
	private boolean isStructureComplete = false;
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		gesuFuel = compound.getDouble("gesuFuel");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound nbtTagCompound = super.writeToNBT(compound);
		nbtTagCompound.setDouble("gesuFuel", gesuFuel);
		return nbtTagCompound;
		
	}
	
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
			((IGesuPart)upPart).setCoreComplete(true);
			((IGesuPart)downPart).setCoreComplete(true);
			((IGesuPart)northPart).setCoreComplete(true);
			((IGesuPart)southPart).setCoreComplete(true);
			((IGesuPart)westPart).setCoreComplete(true);
			((IGesuPart)eastPart).setCoreComplete(true);
			isStructureComplete = true;
		}else 
		{
			isStructureComplete = false;
		}
	}
	
	public synchronized boolean addFuel(double fuel)
	{
		if(isStructureComplete)
		{
			if(this.gesuFuel + fuel <= Double.MAX_VALUE)
			{
				this.gesuFuel += fuel;
				return true;
			}else 
			{
				this.gesuFuel = Double.MAX_VALUE;
			}	
		}
		return false;
	}
	
	public synchronized boolean consumeFuel(double fuel)
	{
		if(isStructureComplete && gesuFuel - fuel >= 0)
		{
			gesuFuel -= fuel;
			return true;
		}else 
		{
			return false;
		}
	}
	
	public synchronized double getGESUFuel()
	{
		return gesuFuel;
	}
	
	private boolean checkPortAndUpdate(TileEntity te)
	{
		if(te != null && te instanceof IGesuPart)
		{
			IGesuPart port = (IGesuPart)te;
			port.setCore(new Vec3d(this.pos));
			port.setCoreComplete(false);
			return true;
		}
		return false;
	}

}
