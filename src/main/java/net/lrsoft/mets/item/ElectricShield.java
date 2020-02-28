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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElectricShield extends ItemShield implements IElectricItem, IItemHudInfo {
	private final static double heldCost = 100d;
	public ElectricShield()
	{
		setUnlocalizedName("mets.electric_shield");
		setRegistryName(MoreElectricTools.MODID, "electric_shield");
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxDamage(1000);
		setNoRepair();
		setMaxStackSize(1);
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
		return I18n.translateToLocal("mets.electric_shield.name");
    }

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		ElectricItem.manager.use(stack, heldCost, entityLiving);
		if(entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityLiving;
			player.inventoryContainer.detectAndSendChanges();
		}
	}
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(ElectricItem.manager.canUse(itemstack, heldCost)) 
		{
			playerIn.setActiveHand(handIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);			
    }


	@Override
	public List<String> getHudInfo(ItemStack stack, boolean advanced) {
		 List<String> info = new LinkedList();
		 info.add(ElectricItem.manager.getToolTip(stack));
		 return info;
	}
	
    public int getMaxItemUseDuration(ItemStack stack) {return 72000;}

	@Override
	public boolean canProvideEnergy(ItemStack stack) {return false;}

	@Override
	public double getMaxCharge(ItemStack stack) {return 50000;}

	@Override
	public int getTier(ItemStack stack) {return 2;}

	@Override
	public double getTransferLimit(ItemStack stack) {return 128;}
	
    public Item getChargedItem(ItemStack itemStack) {return this;}
    
    public Item getEmptyItem(ItemStack itemStack) {return this;}
    
    @Override
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {return true;}
}
