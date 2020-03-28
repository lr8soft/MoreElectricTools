package net.lrsoft.mets.item.battery;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import ic2.core.util.Util;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.UniformElectricItem;
import net.lrsoft.mets.util.ItemStackUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AdvancedLithiumBattery extends UniformElectricItem {
	private final static double transferSpeed = 128D, storageEnergy = 200000;
	public AdvancedLithiumBattery()
	{
		super("advanced_lithium_battery", storageEnergy, transferSpeed, 2);
		setMaxStackSize(16);
		this.addPropertyOverride(new ResourceLocation(MoreElectricTools.MODID, "adv_lithium_battery_value"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) 
			{
				return ItemStackUtils.getCurrentTex(stack, 4) / 4.0f;
			}
		});
	}
	
	@Override
	public boolean canProvideEnergy(ItemStack stack) {return true;}
}
