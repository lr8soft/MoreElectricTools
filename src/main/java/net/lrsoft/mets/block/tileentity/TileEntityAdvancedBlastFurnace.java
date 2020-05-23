package net.lrsoft.mets.block.tileentity;

import java.util.Collection;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.core.block.machine.tileentity.TileEntityBlastFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityAdvancedBlastFurnace extends TileEntityBlastFurnace {
	public static int blastProcessSpeed = 3;
	public void updateEntityServer() {
		super.updateEntityServer();
		if(getActive())
		{
			MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result = getOutput();
			if (result != null && (result.getRecipe().getMetaData().getInteger("fluid") <= this.fluidTank.getFluidAmount())) {
				this.progress+=blastProcessSpeed;
			}
		}
	}
}
