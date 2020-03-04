package net.lrsoft.mets.block.tileentity;

import java.util.Collection;

import ic2.api.energy.tile.IHeatSource;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.core.block.machine.tileentity.TileEntityBlastFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
//Code from ic2
public class TileEntityAdvancedBlastFurnace extends TileEntityBlastFurnace {
	public static int blastProcessSpeed = 3;
	public void updateEntityServer() {
		super.updateEntityServer();
		boolean needsInvUpdate = false;
		heatup();
		MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result = getOutput();
		if (result != null && isHot()) {
			setActive(true);

			if (result.getRecipe().getMetaData().getInteger("fluid") <= this.fluidTank.getFluidAmount()) {
				this.progress += blastProcessSpeed;
				this.fluidTank.drainInternal(result.getRecipe().getMetaData().getInteger("fluid"), true);
			}
			this.progressNeeded = result.getRecipe().getMetaData().getInteger("duration");
			if (this.progress >= result.getRecipe().getMetaData().getInteger("duration")) {
				operateOnce(result, (Collection<ItemStack>) result.getOutput());
				needsInvUpdate = true;
				this.progress = 0;
			}
		} else {

			if (result == null) {
				this.progress = 0;
			}

			setActive(false);
		}

		if (this.fluidTank.getFluidAmount() < this.fluidTank.getCapacity()) {
			gainFluid();
		}

		needsInvUpdate |= this.upgradeSlot.tickNoMark();

		this.guiProgress =  (float)this.progress /  (float)this.progressNeeded;
		this.guiHeat = (float)this.heat /  (float)maxHeat;

		if (needsInvUpdate) {
			markDirty();
		}
	}
	
	private void heatup() {
		int coolingPerTick = 1;
		int heatRequested = 0;
		int gainhU = 0;

		if ((!this.inputSlot.isEmpty() || this.progress >= 1) && this.heat <= maxHeat) {
			heatRequested = maxHeat - this.heat + 100;
		} else if (this.redstone.hasRedstoneInput() && this.heat <= maxHeat) {
			heatRequested = maxHeat - this.heat + 100;
		}

		if (heatRequested > 0) {
			EnumFacing dir = getFacing();
			TileEntity te = getWorld().getTileEntity(this.pos.offset(dir));

			if (te instanceof IHeatSource) {
				gainhU = ((IHeatSource) te).drawHeat(dir.getOpposite(), heatRequested, false);
				this.heat += gainhU;
			}

			if (gainhU == 0) {
				this.heat -= Math.min(this.heat, 1);
			}
		} else {
			this.heat -= Math.min(this.heat, 1);
		}
	}
}
