package net.lrsoft.mets.block.tileentity.LargeFabricator;

import java.util.Collection;


import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.storage.tank.TileEntityTank;
import ic2.core.ref.FluidName;
import ic2.core.util.LiquidUtil;
import net.lrsoft.mets.block.tileentity.OilRig.IOilRig.ModuleType;
import net.lrsoft.mets.manager.FluidManager;
import net.lrsoft.mets.util.VersionHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityFabricatorTank extends TileEntityTank implements IFabricatorPart{

	private Vec3d coreCoord = null;
	private boolean isStructureCompleled = false;
	
	public TileEntityFabricatorTank() {
		super(128);
	}
	
	public boolean addToOutputSlot(int amount)
	{
		int realAdd = LiquidUtil.fillTile(this, getFacing(),
				new FluidStack(FluidName.uu_matter.getInstance(), 1), false);
		return (realAdd > 0);
	}

	@Override
	public void setCore(Vec3d coord) {
		coreCoord = coord;
	}

	@Override
	public void setCoreComplete(boolean isComplete) {
		isStructureCompleled = isComplete;
	}

	@Override
	public FabricatorType getModuleType() {
		return FabricatorType.Tank;
	}
	
}
