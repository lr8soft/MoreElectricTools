package net.lrsoft.mets.block.tileentity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

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
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.network.GuiSynced;
import net.lrsoft.mets.util.VersionHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
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
			Class<?> slotClass = VersionHelper.getTargetItemSlotClass();
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
			if(VersionHelper.getIsFiuldNewVersion())
			{
				try {
					dirty = gainFuelNew(ret);
				}catch(Exception expt)
				{
					System.out.println("[METS]:Incompatible IC2 version.\n" );
					expt.printStackTrace();
				}
			}
			else {
				try {
					dirty = gainFuelOld(ret);
				}catch(Exception expt)
				{
					System.out.println("[METS]:Incompatible IC2 version.\n" );
					expt.printStackTrace();
				}
			}
		}
		return dirty;
	}
	
	private boolean gainFuelOld(FluidStack stack) throws Exception
	{
		//get Inner class
		Class burnPropertyClass = Class.forName("ic2.api.recipe.ISemiFluidFuelManager$BurnProperty");
		//get Method
		Method getBurnPropertyMethod = ISemiFluidFuelManager.class.getMethod("getBurnProperty", Fluid.class);
		Object burnPropertyObject =  getBurnPropertyMethod.invoke(Recipes.semiFluidGenerator, stack.getFluid());
		if(burnPropertyObject != null)
		{
			Object castBurnPropertyObject = burnPropertyClass.cast(burnPropertyObject);
			
			Field amountField = burnPropertyClass.getDeclaredField("amount");
			Field powerField = burnPropertyClass.getDeclaredField("power");		
			
			int amount = amountField.getInt(castBurnPropertyObject);
			double power = powerField.getDouble(castBurnPropertyObject);
			int rate = 0;
			
			if(stack.amount >= amount * 3)
			{
				rate = 3;
			}
			else if(stack.amount < amount * 3 && stack.amount >= amount * 2)
			{
				rate = 2;
			}
			else if(stack.amount < amount * 2 && stack.amount>= amount)
			{
				rate = 1;
			}
			this.fluidTank.drainInternal(amount * rate, true);
			this.production = power * 1.5d * rate;
			this.fuel += amount * rate;
			return (rate > 0 ) ;
		}
		return false;
	}
	
	private boolean gainFuelNew(FluidStack stack) throws Exception {
		// get Inner class
		Class fuelPropertyClass = Class.forName("ic2.api.recipe.ISemiFluidFuelManager$FuelProperty");
		Method getFuelPropertyMethod = ISemiFluidFuelManager.class.getMethod("getFuelProperty", Fluid.class);
		Object fuelPropertyObject = getFuelPropertyMethod.invoke(Recipes.semiFluidGenerator, stack.getFluid());
		if (fuelPropertyObject != null) {
			Object castFuelPropertyObject = fuelPropertyClass.cast(fuelPropertyObject);
			// ISemiFluidFuelManager.FuelProperty property =
			// Recipes.semiFluidGenerator.getFuelProperty(stack.getFluid());

			Field energyPerMbField = fuelPropertyClass.getDeclaredField("energyPerMb");
			Field energyPerTickField = fuelPropertyClass.getDeclaredField("energyPerTick");

			long energyPerMb = energyPerMbField.getLong(castFuelPropertyObject);
			long energyPerTick = energyPerTickField.getLong(castFuelPropertyObject);

			int toBeConsumed = (energyPerMb >= energyPerTick) ? 1 : (int) Math.ceil(energyPerTick / energyPerMb);
			toBeConsumed = Math.min(toBeConsumed, stack.amount);

			if (stack.amount >= toBeConsumed) {
				this.fluidTank.drainInternal(toBeConsumed, true);

				this.production = energyPerTick * 1.5;
				this.fuel = (int) (this.fuel + toBeConsumed * energyPerMb);
				return true;
			}
		}
		return false;
	}
	

	public String getOperationSoundFile() {
		return "Generators/GeothermalLoop.ogg";
	}
}
