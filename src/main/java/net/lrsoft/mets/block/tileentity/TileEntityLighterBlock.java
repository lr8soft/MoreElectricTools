package net.lrsoft.mets.block.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityLighterBlock extends TileEntity implements ITickable{
	private float red = 170.0f, green = 85.0f ,blue = 255.0f;
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		green = compound.getFloat("greenParameter");
		red = (green + 85.0f >= 255) ? ((green + 85.0f) - 255) : (green + 85.0f) ;
		blue = (red + 85.0f >= 255) ? ((red + 85.0f) - 255) : (red + 85.0f) ;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound nbtTagCompound = super.writeToNBT(compound);
		nbtTagCompound.setFloat("greenParameter", green);
		return nbtTagCompound;
		
	}

	@Override
	public void update() {
		if(blue < 255.0f)
		{
			blue +=3.0f;
		}else 
		{
			blue = 0.0f;
		}
		
		if(green < 255.0f)
		{
			green +=3.0f;
		}else 
		{
			green = 0.0f;
		}
		
		if(red < 255.0f)
		{
			red +=3.0f;
		}else 
		{
			red = 0.0f;
		}
	}
	
	public float[] getColor()
	{
		return new float[]{ red,green, blue };
	}
	
	@Override
	public boolean hasFastRenderer() {
		return true;
	}
}
