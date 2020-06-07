package net.lrsoft.mets.block.tileentity;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.profile.NotClassic;
import net.lrsoft.mets.gui.ContainerUniformSimpleMachine;
import net.lrsoft.mets.gui.GuiUniformSimpleMachine;
import net.lrsoft.mets.util.VersionHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityExtrudingMachine extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> {
	public TileEntityExtrudingMachine() {
		super(50, 20, 1, 2);
		try {
			Class<?> slotClass = VersionHelper.getTargetItemSlotClass();
			InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack> newInputSlot = 
					 InvSlotProcessableGeneric.class.getConstructor(slotClass, String.class, int.class, IMachineRecipeManager.class)
					 .newInstance(slotClass.cast(this), "input", 1, (IMachineRecipeManager)Recipes.metalformerExtruding);
			this.inputSlot = newInputSlot;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//this.inputSlot = 
		//		(InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack>)new InvSlotProcessableGeneric(
		//				this, "input", 1, (IMachineRecipeManager)Recipes.metalformerExtruding);
	}
	
	public ContainerBase<TileEntityExtrudingMachine> getGuiContainer(EntityPlayer player) {
		return (ContainerBase<TileEntityExtrudingMachine>) new ContainerUniformSimpleMachine<TileEntityExtrudingMachine>(player, this);
	}

	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
		return (GuiScreen) new GuiUniformSimpleMachine(new ContainerUniformSimpleMachine(player, this));
	}

	@Override
	public Set<UpgradableProperty> getUpgradableProperties() {
		 return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
	}
	
}
