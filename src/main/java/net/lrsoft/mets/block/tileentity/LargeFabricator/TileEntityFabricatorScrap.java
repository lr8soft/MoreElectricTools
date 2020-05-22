package net.lrsoft.mets.block.tileentity.LargeFabricator;

import java.util.Collection;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import net.lrsoft.mets.block.tileentity.OilRig.TileEntityGUIMachine;
import net.lrsoft.mets.util.VersionHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;

public class TileEntityFabricatorScrap extends TileEntityGUIMachine implements IFabricatorPart {
	
	public InvSlotProcessable<IRecipeInput, Integer, ItemStack> scrapSlot;
	private float scrapValue = 0.f;
	public TileEntityFabricatorScrap()
	{
		super(0, 14);
		 try {
			Class<?> slotClass = VersionHelper.getTargetSlotClass();
			InvSlotProcessable<?, ?, ?> newInputSlot = 
					InvSlotProcessableGeneric.class.getConstructor(slotClass, String.class, int.class,IMachineRecipeManager.class)
					.newInstance(slotClass.cast(this), "scrap", 1, (IMachineRecipeManager<?, ?, ?>)Recipes.matterAmplifier);
			this.scrapSlot = (InvSlotProcessable<IRecipeInput, Integer, ItemStack>)newInputSlot;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		if(scrapValue < 100000)
		{
			MachineRecipeResult<IRecipeInput, Integer, ItemStack> recipe = this.scrapSlot.process();
			if(recipe != null)
			{
				this.scrapSlot.consume(recipe);
				this.scrapValue += (recipe.getOutput()).intValue() * 10;
				
			}
		}
	}
	
	
	public float consumeScrapValue(float value)
	{
		if(this.scrapValue - value >= 0)
		{
			this.scrapValue-=value;
			return value;
		}else if(this.scrapValue > 0){
			this.scrapValue = 0;
			return scrapValue;
		}
		return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		super.readFromNBT(compound);
		scrapValue  = compound.getFloat("scrapValue");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound nbtCompound = super.writeToNBT(compound);
		nbtCompound.setFloat("scrapValue", scrapValue);
		return nbtCompound;
	}
	
	@Override
	public void setCore(Vec3d coord) {

	}

	@Override
	public void setCoreComplete(boolean isComplete) {

	}

	@Override
	public FabricatorType getModuleType() {
		return FabricatorType.Scrap;
	}

}
