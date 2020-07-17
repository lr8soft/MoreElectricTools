package net.lrsoft.mets.block.tileentity;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import net.lrsoft.mets.gui.ContainerUniformSimpleMachine;
import net.lrsoft.mets.gui.GuiUniformSimpleMachine;
import net.lrsoft.mets.util.SpecialRecipesHelper;
import net.lrsoft.mets.util.VersionHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityElectricBlastFurnace extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack>{
	public TileEntityElectricBlastFurnace() {
		super(8000, 40, 1, 5);

		try {
			Class<?> slotClass = VersionHelper.getTargetItemSlotClass();
			InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack> newInputSlot = 
					 InvSlotProcessableGeneric.class.getConstructor(slotClass, String.class, int.class, IMachineRecipeManager.class)
					 .newInstance(slotClass.cast(this), "input", 1, (IMachineRecipeManager)SpecialRecipesHelper.electricBlastFurnaceRecipes);
			this.inputSlot = newInputSlot;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ContainerBase<TileEntityElectricBlastFurnace> getGuiContainer(EntityPlayer player) {
		return (ContainerBase<TileEntityElectricBlastFurnace>)
				new ContainerUniformSimpleMachine<TileEntityElectricBlastFurnace>(player, this);
	}
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
		return (GuiScreen) new GuiUniformSimpleMachine(new ContainerUniformSimpleMachine(player, this));
	}


	@Override
	public Set<UpgradableProperty> getUpgradableProperties() {
		 return EnumSet.of(UpgradableProperty.Processing,  UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
	}
}
