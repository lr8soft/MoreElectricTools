package net.lrsoft.mets.block.tileentity;

import java.util.Random;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.invslot.InvSlot;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GuiSynced;
import net.lrsoft.mets.gui.DropGeneratorNewSlot;
import net.lrsoft.mets.gui.DropGeneratorSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
public class TileEntityDropGeneratorNew  extends TileEntityBaseGenerator implements IMets, IGuiValueProvider{
	public final DropGeneratorNewSlot fuelSlot;
	@GuiSynced
	public int totalFuel;

	public TileEntityDropGeneratorNew() {
		super(20.0D, 2, 10000);
		this.totalFuel = 0;
		this.fuelSlot = new DropGeneratorNewSlot((IInventorySlotHolder)this, "fuel", 1);
	}
	
	@SideOnly(Side.CLIENT)
	protected void updateEntityClient() {
		super.updateEntityClient();
		if (getActive()) 
		{
			Random random = new Random();
			int pX = getRandomFromRange(2, -2), pY = getRandomFromRange(2, -2), pZ =  getRandomFromRange(2, -2);
			getWorld().spawnParticle(EnumParticleTypes.PORTAL,
					this.pos.getX() + 0.5f, this.pos.getY() + 0.5f, this.pos.getZ() + 0.5f,
					random.nextDouble() * pX, random.nextDouble()* pY, random.nextDouble()* pZ, 0);
		}
		
	}

	public double getFuelRatio() {
		if (this.fuel <= 0)
			return 0.0D;
		return (double)this.fuel / (double)this.totalFuel;
	}

	@Override
	public boolean gainFuel() {
		int fuelValue = this.fuelSlot.consumeFuel() / 2;
		if (fuelValue == 0)
			return false;
		this.fuel += fuelValue;
		this.totalFuel = fuelValue;
		return true;
	}
	
	public boolean isConverting() {
		return (this.fuel > 0);
	}

	public String getOperationSoundFile() {
		return "Generators/GeneratorLoop.ogg";
	}

	public double getGuiValue(String name) {
		if ("fuel".equals(name))
			return getFuelRatio();
		throw new IllegalArgumentException();
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.totalFuel = nbt.getInteger("totalFuel");
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("totalFuel", this.totalFuel);
		return nbt;
	}
	
	private int getRandomFromRange(int max, int min)
	{
		return new Random().nextInt(max-min)+min;
	}

}

