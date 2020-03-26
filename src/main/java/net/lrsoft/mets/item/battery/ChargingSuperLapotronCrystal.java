package net.lrsoft.mets.item.battery;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import ic2.core.init.Localization;
import ic2.core.util.StackUtil;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.UniformElectricItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChargingSuperLapotronCrystal extends UniformElectricItem {
	public final static double maxStorageEnergy = 400000000, transferSpeed = 8192;
	public final static int tier = 5;
	public ChargingSuperLapotronCrystal()
	{
		super("charging_super_lapotron_crystal", maxStorageEnergy, transferSpeed, tier);
		this.addPropertyOverride(new ResourceLocation(MoreElectricTools.MODID, "super_lapotron_crystal_value"), new IItemPropertyGetter() {
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
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		boolean autoCharge = getAutoCharge(stack);

		if (entity instanceof EntityPlayerMP && world.getTotalWorldTime() % 10L < getTier(stack) && autoCharge) {
			EntityPlayer thePlayer = (EntityPlayer) entity;
			NonNullList<ItemStack> nonNullList = thePlayer.inventory.mainInventory;
			double limit = transferSpeed;
			for (int i = 0; i < 9 && limit > 0.0D; i++) {
				ItemStack toCharge = nonNullList.get(i);
				if (!StackUtil.isEmpty(toCharge) || i != thePlayer.inventory.currentItem) {
					double charge = ElectricItem.manager.charge(toCharge, limit, tier, false, true);
					charge = ElectricItem.manager.discharge(stack, charge, tier, true, false, false);
					ElectricItem.manager.charge(toCharge, charge, tier, true, false);
					limit -= charge;
				}
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastRightClick(stack);
		long currentTime = System.currentTimeMillis();
		boolean autoCharge = getAutoCharge(stack);

		if (currentTime - lastRightClick > 100) {
			autoCharge = !autoCharge;
			lastRightClick = currentTime;
			try {
				if (autoCharge) {
					playerIn.sendMessage(new TextComponentString(Localization.translate("mets.info.ens.auto")));
				} else {
					playerIn.sendMessage(new TextComponentString(Localization.translate("mets.info.ens.disable")));
				}
			} catch (Exception expt) {}

			setLastRightClick(stack, lastRightClick);
			setAutoCharge(stack, autoCharge);
		}

		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}
	
	
	private void setAutoCharge(ItemStack stack, boolean value) 
	{
		stack.getItem().getNBTShareTag(stack).setBoolean("AutoCharge", value);
	}
	
	private boolean getAutoCharge(ItemStack stack) 
	{
		boolean value = false;
		try {
			value = stack.getItem().getNBTShareTag(stack).getBoolean("AutoCharge");
		} catch (Exception expt) {}
		return value;
	}
	
	
	@Override
	public boolean canProvideEnergy(ItemStack stack) {return true;}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {return false;}
}
