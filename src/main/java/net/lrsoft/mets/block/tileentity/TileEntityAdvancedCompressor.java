package net.lrsoft.mets.block.tileentity;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityPump;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.util.LiquidUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
//Code from ic2
public class TileEntityAdvancedCompressor extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack>{
	protected boolean usingPumpRecipe;
	protected final Set<TileEntityPump> pumps;
	
	public TileEntityAdvancedCompressor() {
		super(5, 150, 1, 2);
		this.pumps = new HashSet<>(12, 0.5F);
		this.inputSlot = (InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack>) new InvSlotProcessableGeneric(
				this, "input", 1, (IMachineRecipeManager) Recipes.compressor);
	}
   
	protected void onLoaded() {
		super.onLoaded();
		findPumps();
	}

	protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
		super.onNeighborChange(neighbor, neighborPos);
		findPumps();
	}
  
	protected void findPumps() {
		World world = getWorld();
		this.pumps.clear();
		for (EnumFacing side : EnumFacing.VALUES) {
			TileEntity te = world.getTileEntity(this.pos.offset(side));
			if (te instanceof TileEntityPump)
				this.pumps.add((TileEntityPump) te);
		}
	}
	
	public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
		this.usingPumpRecipe = false;
		MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> output = super.getOutput();
		if (output != null)
			return output;
		if (!this.pumps.isEmpty() && this.inputSlot.isEmpty()
				&& this.outputSlot.canAdd(new ItemStack(Items.SNOWBALL))) {
			FluidStack fluid = new FluidStack(FluidRegistry.WATER, 1000);
			for (TileEntityPump pump : this.pumps) {
				FluidStack amount = LiquidUtil.drainTile((TileEntity) pump, EnumFacing.UP, FluidRegistry.WATER,
						fluid.amount, true);
				if (amount != null) {
					assert amount.getFluid() == FluidRegistry.WATER;
					fluid.amount -= amount.amount;
				}
				if (fluid.amount <= 0) {
					this.usingPumpRecipe = true;
					output = (new MachineRecipe(null, Collections.singletonList(new ItemStack(Items.SNOWBALL))))
							.getResult(null);
					break;
				}
			}
		}
		return output;
	}
	
	public void operateOnce(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> output,
			Collection<ItemStack> processResult) {
		if (this.usingPumpRecipe) {
			FluidStack fluid = new FluidStack(FluidRegistry.WATER, 1000);
			for (TileEntityPump pump : this.pumps) {
				FluidStack amount = LiquidUtil.drainTile((TileEntity) pump, EnumFacing.UP, FluidRegistry.WATER,
						fluid.amount, false);
				if (amount != null && amount.getFluid() == FluidRegistry.WATER)
					fluid.amount -= amount.amount;
				if (fluid.amount <= 0)
					break;
			}
			this.outputSlot.add(processResult);
		} else {
			super.operateOnce(output, processResult);
		}
	}

	public String getStartSoundFile() {
		return "Machines/CompressorOp.ogg";
	}

	public String getInterruptSoundFile() {
		return "Machines/InterruptOne.ogg";
	}

	public Set<UpgradableProperty> getUpgradableProperties() {
		return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer,
				UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
	}
}
