package net.lrsoft.mets.item;

import java.util.List;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import ic2.core.init.Localization;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.util.ItemStackUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElectricForceFieldGenerator extends UniformElectricItem {
	private final static double storageEnergy = 1000000, transferSpeed = 512;
	public ElectricForceFieldGenerator() {
		super("electric_force_field_generator", storageEnergy, transferSpeed, 3);
		this.addPropertyOverride(new ResourceLocation(MoreElectricTools.MODID, "storage_energy_value"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) 
			{
				return ItemStackUtils.getCurrentTex(stack, 4) / 4.0f;
			}
		});
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!(entityIn instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer)entityIn;
		boolean shouldWork = getAutoSupplement(stack);
		float currentAbsAmount = player.getAbsorptionAmount();
		if(!worldIn.isRemote && shouldWork && currentAbsAmount < 10.0f)
		{
			if(ElectricItem.manager.use(stack, ConfigManager.ForceFieldCost, player))
			{
				player.setAbsorptionAmount(currentAbsAmount + 0.5f);
			}
		}		
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastRightClick(stack);
		long currentTime = System.currentTimeMillis();
		boolean autoSupplement = getAutoSupplement(stack);

		if (currentTime - lastRightClick > 100) {
			autoSupplement = !autoSupplement;
			lastRightClick = currentTime;
			try {
				if (autoSupplement) {
					playerIn.sendMessage(new TextComponentString(Localization.translate("mets.info.ens.auto")));
				} else {
					playerIn.sendMessage(new TextComponentString(Localization.translate("mets.info.ens.disable")));
				}
			} catch (Exception expt) {}

			setLastRightClick(stack, lastRightClick);
			setAutoSupplement(stack, autoSupplement);
		}

		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return false;
	}
	
	private void setAutoSupplement(ItemStack stack, boolean value) 
	{
		stack.getItem().getNBTShareTag(stack).setBoolean("AutoSupplement", value);
	}
	
	private boolean getAutoSupplement(ItemStack stack) 
	{
		boolean value = false;
		try {
			value = stack.getItem().getNBTShareTag(stack).getBoolean("AutoSupplement");
		} catch (Exception expt) {}
		return value;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(Localization.translate("mets.info.extralife"));
	}
}
