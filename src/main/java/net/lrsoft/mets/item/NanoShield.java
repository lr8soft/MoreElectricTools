package net.lrsoft.mets.item;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NanoShield extends ItemShield implements IElectricItem, IItemHudInfo {
	
	public NanoShield()
	{
		setUnlocalizedName("mets.nano_shield");
		setRegistryName(MoreElectricTools.MODID, "nano_shield");
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxDamage(233);
		setMaxStackSize(1);
		setNoRepair();
        this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });

	    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);

	}
	
	@Override
    public String getItemStackDisplayName(ItemStack stack)
    {
		return I18n.translateToLocal("mets.nano_shield.name");
    }


	@Override
	public List<String> getHudInfo(ItemStack stack, boolean advanced) {
		 List<String> info = new LinkedList();
		 info.add(ElectricItem.manager.getToolTip(stack));
		 return info;
	}

	@Override
	public boolean canProvideEnergy(ItemStack stack) {return false;}

	@Override
	public double getMaxCharge(ItemStack stack) {return 10000;}

	@Override
	public int getTier(ItemStack stack) {return 1;}

	@Override
	public double getTransferLimit(ItemStack stack) {return 32;}
	
    public Item getChargedItem(ItemStack itemStack) {return this;}
    
    public Item getEmptyItem(ItemStack itemStack) {return this;}
}
