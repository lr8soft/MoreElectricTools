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
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import net.lrsoft.mets.util.VersionHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityAdvancedCompressor extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack>{
	public TileEntityAdvancedCompressor() 
	{
		super(25, 70, 1, 2);
		Class<?> slotClass = VersionHelper.getTargetItemSlotClass();
		try {
			InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack> newInputSlot = 
					 InvSlotProcessableGeneric.class.getConstructor(slotClass, String.class, int.class, IMachineRecipeManager.class)
					 .newInstance(slotClass.cast(this), "input", 1, (IMachineRecipeManager)Recipes.compressor);
			this.inputSlot = newInputSlot;
		} catch (Exception e) {
			e.printStackTrace();
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
