package net.lrsoft.mets.item;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElectricFishingRod extends ItemFishingRod  implements IElectricItem, IItemHudInfo {
	private final static float rodCost = 100f; 
	public ElectricFishingRod() {
		setUnlocalizedName("mets.electric_fishing_rod");
		setRegistryName(MoreElectricTools.MODID, "electric_fishing_rod");
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxDamage(233);
		setMaxStackSize(1);
		setNoRepair();
		this.addPropertyOverride(new ResourceLocation("cast"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				if (entityIn == null) {
					return 0.0F;
				} else {
					boolean flag = entityIn.getHeldItemMainhand() == stack;
					boolean flag1 = entityIn.getHeldItemOffhand() == stack;

					if (entityIn.getHeldItemMainhand().getItem() instanceof ItemFishingRod) {
						flag1 = false;
					}
					return (flag || flag1) && entityIn instanceof EntityPlayer
							&& ((EntityPlayer) entityIn).fishEntity != null ? 1.0F : 0.0F;
				}
			}
		});
	}
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(!ElectricItem.manager.canUse(itemstack, rodCost)) return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        if (playerIn.fishEntity != null)
        {
            int i = playerIn.fishEntity.handleHookRetraction();
            ElectricItem.manager.use(itemstack, rodCost, playerIn);
            playerIn.swingArm(handIn);
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        }
        else
        {
			worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ,
					SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F,
					0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!worldIn.isRemote) {
				EntityFishHook entityfishhook = new EntityFishHook(worldIn, playerIn);
				int j = EnchantmentHelper.getFishingSpeedBonus(itemstack) + 2;

				if (j > 0) {
					entityfishhook.setLureSpeed(j);
				}

				int k = EnchantmentHelper.getFishingLuckBonus(itemstack) + 1;

				if (k > 0) {
					entityfishhook.setLuck(k);
				}

				worldIn.spawnEntity(entityfishhook);
			}
			playerIn.swingArm(handIn);
			playerIn.addStat(StatList.getObjectUseStats(this));
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }


	@Override
	public List<String> getHudInfo(ItemStack stack, boolean advanced) {
	    List<String> info = new LinkedList();
	    info.add(ElectricItem.manager.getToolTip(stack));
	    return info;
	}
	@Override
    public int getItemEnchantability() {return 3;}

	@Override
	public boolean canProvideEnergy(ItemStack stack) {return false;}

	@Override
	public double getMaxCharge(ItemStack stack) {return 12000;}

	@Override
	public int getTier(ItemStack stack) {return 1;}

	@Override
	public double getTransferLimit(ItemStack stack) {return 32;}

}
