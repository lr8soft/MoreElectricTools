package net.lrsoft.mets.item;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElectricNutritionSupply extends UniformElectricItem {
	private final static double storageEnergy = 10000, transferSpeed = 32;
	public ElectricNutritionSupply() {
		super("electric_nutrition_supply", storageEnergy, transferSpeed, 1);
		this.addPropertyOverride(new ResourceLocation(MoreElectricTools.MODID, "storage_energy_value"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) 
			{
				float remainingEnergy = 0.0f;
				try {
					remainingEnergy = (float) (ElectricItem.manager.getCharge(stack) / ElectricItem.manager.getMaxCharge(stack));
				}catch(Exception expt) {}

				if(remainingEnergy < 0.25f) {
					return 0.0f;
				}else if(remainingEnergy >= 0.25f && remainingEnergy < 0.5f) {
					return 0.25f;
				}else if(remainingEnergy >= 0.5f && remainingEnergy < 0.75f) {
					return 0.5f;
				}else if(remainingEnergy >= 0.75f && remainingEnergy < 1.0f) {
					return 0.75f;
				}else {
					return 1.0f;
				}
			}
		});
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		boolean shouldWork = getAutoSupplement(stack);
		if(!worldIn.isRemote && shouldWork && entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;
			FoodStats foodStats = player.getFoodStats();
			if(foodStats.needFood())
			{
				if (ElectricItem.manager.canUse(stack, 200)) {
					ElectricItem.manager.use(stack, 200, player);
					foodStats.addStats(1, 0.2f);
				}
				player.inventoryContainer.detectAndSendChanges();
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItemMainhand();
		
		long lastRightClick = getLastRightClick(stack);
		long currentTime = System.currentTimeMillis();
		boolean autoSupplement = getAutoSupplement(stack);
		
		if(currentTime - lastRightClick > 100)
		{
			autoSupplement = !autoSupplement;	
			lastRightClick = currentTime;
			try {
				if (autoSupplement) {
					playerIn.sendMessage(new TextComponentString(I18n.format("mets.info.ens.auto")));
				} else {
					playerIn.sendMessage(new TextComponentString(I18n.format("mets.info.ens.disable")));
				}
			} catch (Exception expt) {
			}

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

}
