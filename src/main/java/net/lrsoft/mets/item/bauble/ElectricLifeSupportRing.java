package net.lrsoft.mets.item.bauble;

import javax.annotation.Nullable;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import ic2.api.item.ElectricItem;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.UniformElectricItem;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.util.ItemStackUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElectricLifeSupportRing extends UniformBaubleTemplate {
	private final static double transferSpeed = 8192d, storageEnergy = 100000000;
	public ElectricLifeSupportRing()
	{
		super("electric_life_support_ring", storageEnergy, transferSpeed, 5);
		this.addPropertyOverride(new ResourceLocation(MoreElectricTools.MODID, "ring_energy_value"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) 
			{
				return ItemStackUtils.getCurrentTex(stack, 4) / 4.0f;
			}
		});
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		if(!entity.world.isRemote && entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			boolean haveInfoUpdated = false;
			
			float ratio = getElectricItemAttenuationRatio(itemstack);
			
			float currentHealth = player.getHealth();
			if(currentHealth < player.getMaxHealth())
			{
				if (ElectricItem.manager.canUse(itemstack, ConfigManager.ElectricFirstAidLifeSupport * ratio)) {
					ElectricItem.manager.use(itemstack, ConfigManager.ElectricFirstAidLifeSupport * ratio, player);
					player.setHealth(currentHealth+1);
					haveInfoUpdated = true;
				}
			}
		
			FoodStats foodStats = player.getFoodStats();
			if(foodStats.needFood())
			{
				if (ElectricItem.manager.canUse(itemstack, ConfigManager.ElectricNutritionSupplyCost * ratio)) {
					ElectricItem.manager.use(itemstack, ConfigManager.ElectricNutritionSupplyCost * ratio, player);
					foodStats.addStats(1, 0.2f);
					haveInfoUpdated = true;
				}
			}
			
			float currentAbsAmount = player.getAbsorptionAmount();
			if(currentAbsAmount < 20.0f)
			{
				if(ElectricItem.manager.canUse(itemstack, ConfigManager.ForceFieldCost * ratio))
				{
					ElectricItem.manager.use(itemstack, ConfigManager.ForceFieldCost * ratio, player);
					player.setAbsorptionAmount(currentAbsAmount + 0.5f);
					haveInfoUpdated = true;
				}
			}
			
			if(haveInfoUpdated)
			{
				player.inventoryContainer.detectAndSendChanges();
			}
			
		}
	}
}
