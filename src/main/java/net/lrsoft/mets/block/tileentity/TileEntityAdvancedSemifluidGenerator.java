package net.lrsoft.mets.block.tileentity;

import java.util.Collection;

import ic2.api.recipe.ILiquidAcceptManager;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.ISemiFluidFuelManager;
import ic2.api.recipe.Recipes;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.network.GuiSynced;
import net.lrsoft.mets.util.VersionHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityAdvancedSemifluidGenerator extends TileEntityBaseGenerator {
	public InvSlotConsumableLiquid fluidSlot;
	public InvSlotOutput outputSlot;
	
	@GuiSynced
	protected final FluidTank fluidTank;
	protected final Fluids fluids;
	public TileEntityAdvancedSemifluidGenerator() {
		super(64.0D, 2, 100000);
		this.fluids = (Fluids) addComponent((TileEntityComponent) new Fluids((TileEntityBlock) this));
		this.fluidTank = (FluidTank) this.fluids.addTankInsert("fluid", 30000,
				Fluids.fluidPredicate((ILiquidAcceptManager) Recipes.semiFluidGenerator));
		
		try {
			Class<?> slotClass = VersionHelper.getTargetSlotClass();
			this.fluidSlot = 
					InvSlotConsumableLiquidByManager.class.getConstructor(slotClass, String.class, int.class, ILiquidAcceptManager.class)
					 .newInstance(slotClass.cast(this), "fluidSlot", 1, (ILiquidAcceptManager)Recipes.semiFluidGenerator);
			this.outputSlot =
					InvSlotOutput.class.getConstructor(slotClass, String.class, int.class)
					.newInstance(slotClass.cast(this), "output", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateEntityServer() {
		super.updateEntityServer();
		if (this.fluidSlot.processIntoTank((IFluidTank) this.fluidTank, this.outputSlot))
		{
			markDirty();
		}
	}

	public boolean gainFuel() {
		boolean dirty = false;

		FluidStack ret = this.fluidTank.drain(Integer.MAX_VALUE, false);
		if (ret != null) {
			ISemiFluidFuelManager.BurnProperty property = Recipes.semiFluidGenerator.getBurnProperty(ret.getFluid());
			if (property != null) {
				int rate = 0;
				if(ret.amount >= property.amount * 3)
				{
					rate = 3;
				}
				else if(ret.amount < property.amount * 3 && ret.amount >= property.amount * 2)
				{
					rate = 2;
				}
				else if(ret.amount < property.amount * 2 && ret.amount>= property.amount)
				{
					rate = 1;
				}
				this.fluidTank.drainInternal(property.amount * rate, true);
				this.production = property.power * 1.5d * rate;
				this.fuel += property.amount * rate;
				dirty = (rate > 0 ) ? true : false;
			}
		}
		return dirty;
	}

	public String getOperationSoundFile() {
		return "Generators/GeothermalLoop.ogg";
	}
}
