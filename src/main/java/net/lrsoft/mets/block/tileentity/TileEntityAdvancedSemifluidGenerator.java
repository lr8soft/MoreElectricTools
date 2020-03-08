package net.lrsoft.mets.block.tileentity;

import ic2.api.recipe.ILiquidAcceptManager;
import ic2.api.recipe.ISemiFluidFuelManager;
import ic2.api.recipe.Recipes;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.network.GuiSynced;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityAdvancedSemifluidGenerator extends TileEntityBaseGenerator {
	public final InvSlotConsumableLiquid fluidSlot;
	public final InvSlotOutput outputSlot;
	
	@GuiSynced
	protected final FluidTank fluidTank;
	protected final Fluids fluids;
	public TileEntityAdvancedSemifluidGenerator() {
		super(64.0D, 2, 100000);
		this.fluids = (Fluids) addComponent((TileEntityComponent) new Fluids((TileEntityBlock) this));
		this.fluidTank = (FluidTank) this.fluids.addTankInsert("fluid", 30000,
				Fluids.fluidPredicate((ILiquidAcceptManager) Recipes.semiFluidGenerator));
		this.fluidSlot = (InvSlotConsumableLiquid) new InvSlotConsumableLiquidByManager(this, "fluidSlot", 1,
				(ILiquidAcceptManager) Recipes.semiFluidGenerator);
		this.outputSlot = new InvSlotOutput(this, "output", 1);
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
