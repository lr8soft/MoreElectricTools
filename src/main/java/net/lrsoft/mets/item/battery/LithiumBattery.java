package net.lrsoft.mets.item.battery;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.UniformElectricItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LithiumBattery extends UniformElectricItem
{
	private final static double transferSpeed = 128D, storageEnergy = 50000;
	public LithiumBattery()
	{
		super("lithium_battery", storageEnergy, transferSpeed, 2);
		setMaxStackSize(16);
		this.addPropertyOverride(new ResourceLocation(MoreElectricTools.MODID, "lithium_battery_value"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) 
			{
				float remainingEnergy = 0.0f;
				try {
					remainingEnergy = (float) (ElectricItem.manager.getCharge(stack) / ElectricItem.manager.getMaxCharge(stack));
				}catch(Exception expt) {remainingEnergy = 0.0f;}

				if(remainingEnergy >= 1.0f) {
					return 1.0f;
				}else if(remainingEnergy >= 0.25f && remainingEnergy < 0.5f) {
					return 0.25f;
				}else if(remainingEnergy >= 0.5f && remainingEnergy < 0.75f) {
					return 0.5f;
				}else if(remainingEnergy >= 0.75f && remainingEnergy < 1.0f) {
					return 0.75f;
				}else{
					return 0.0f;
				}
			}
		});
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		ItemStack currentBattery = playerIn.getHeldItem(handIn);
		if(!worldIn.isRemote && currentBattery != null && currentBattery.getCount() == 1)
		{
			for(int i=0; i < 9; i++)
			{
				ItemStack chargeTemp =playerIn.inventory.mainInventory.get(i);
				if(chargeTemp != null && (chargeTemp.getItem() instanceof IElectricItem) && chargeTemp != currentBattery) 
				{
					if(ElectricItem.manager.getCharge(currentBattery) > 0D) 
					{
						double chargeElectric = ElectricItem.manager.discharge(currentBattery, transferSpeed, 2, true, true, false);
						if(chargeElectric > 0D)
						{
							double actuallyCharge = ElectricItem.manager.charge(chargeTemp, chargeElectric, 2, true, false);
							if(actuallyCharge > 0D)
							{
								ElectricItem.manager.use(currentBattery, actuallyCharge, playerIn);
								playerIn.inventoryContainer.detectAndSendChanges();
							}
						}
					}
				}
			}	
			return new ActionResult(EnumActionResult.SUCCESS, currentBattery);			
		}
		return new ActionResult(EnumActionResult.FAIL, currentBattery);
	}

	
	@Override
	public boolean canProvideEnergy(ItemStack stack) {return true;}

}
