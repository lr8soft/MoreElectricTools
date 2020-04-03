package net.lrsoft.mets.block.tileentity;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.recipe.BasicMachineRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityAdvancedMacerator extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack>{
	public TileEntityAdvancedMacerator() 
	{
		 super(5, 150, 1, 2); 
		 this.inputSlot = (InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack>)
				 new InvSlotProcessableGeneric(this, "input", 1, (IMachineRecipeManager)Recipes.macerator);
	}
	 
	@SideOnly(Side.CLIENT)
	protected void updateEntityClient() 
	{
		super.updateEntityClient();
		World world = getWorld();
		if (getActive() && world.rand.nextInt(8) == 0) {
			for (int i = 0; i < 4; i++) {
				double x = this.pos.getX() + 0.5D + world.rand.nextFloat() * 0.6D - 0.3D;
				double y = (this.pos.getY() + 1) + world.rand.nextFloat() * 0.2D - 0.1D;
				double z = this.pos.getZ() + 0.5D + world.rand.nextFloat() * 0.6D - 0.3D;
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}

	public String getStartSoundFile() 
	{
		return "Machines/MaceratorOp.ogg";
	}

	public String getInterruptSoundFile() 
	{
		return "Machines/InterruptOne.ogg";
	}

	public Set<UpgradableProperty> getUpgradableProperties() 
	{
		return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer,
				UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
	}
}
