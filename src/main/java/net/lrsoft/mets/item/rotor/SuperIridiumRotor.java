package net.lrsoft.mets.item.rotor;

import java.util.List;

import ic2.api.item.IKineticRotor;
import ic2.core.init.Localization;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SuperIridiumRotor extends Item implements IKineticRotor {
	protected static final ResourceLocation texture = new ResourceLocation(MoreElectricTools.MODID,"textures/rotors/super_iridium_rotor.png");
	public SuperIridiumRotor() {
		setUnlocalizedName("mets.super_iridium_rotor");
		setRegistryName(MoreElectricTools.MODID, "super_iridium_rotor");
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxDamage(Integer.MAX_VALUE);
		setNoRepair();
		setMaxStackSize(1);
	}

	@Override
	public int getDiameter(ItemStack stack) {
		return 11;
	}

	@Override
	public ResourceLocation getRotorRenderTexture(ItemStack stack) {
		// TODO Auto-generated method stub
		return texture;
	}

	@Override
	public float getEfficiency(ItemStack stack) {
		// TODO Auto-generated method stub
		return 1.0f;
	}

	@Override
	public int getMinWindStrength(ItemStack stack) {
		// TODO Auto-generated method stub
		return 18;
	}

	@Override
	public int getMaxWindStrength(ItemStack stack) {
		// TODO Auto-generated method stub
		return 155;
	}
	
	@Override
	public boolean isAcceptedType(ItemStack stack, GearboxType type) {
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(Localization.translate("ic2.itemrotor.wind.info",
				new Object[] { Integer.valueOf(getMinWindStrength(stack)), Integer.valueOf(getMaxWindStrength(stack)) }));
		tooltip.add(Localization.translate("mets.info.rotor"));
	}

}
