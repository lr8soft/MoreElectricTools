package net.lrsoft.mets.item;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NanoBow extends ItemBow implements IElectricItem, IItemHudInfo {
	public NanoBow()
	{
		setUnlocalizedName("mets.nano_bow");
		setRegistryName(MoreElectricTools.MODID, "nano_bow");
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxDamage(233);
		setMaxStackSize(1);
		setNoRepair();
		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				if (entityIn == null) {
					return 0.0F;
				} else {
					return entityIn.getActiveItemStack().getItem() != ItemManager.nanoBow ? 0.0F
							: (float) (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
				}
			}
		});
		this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F
						: 0.0F;
			}
		});
	}
	
	@Override
	public List<String> getHudInfo(ItemStack stack, boolean advanced) {
	    List<String> info = new LinkedList();
	    info.add(ElectricItem.manager.getToolTip(stack));
	    return info;
	}
	
	  public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	    {
	        if (entityLiving instanceof EntityPlayer)
	        {
	            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
	            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
	            ItemStack itemstack = this.findAmmo(entityplayer);

	            int i = this.getMaxItemUseDuration(stack) - timeLeft;
	            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty() || flag);
	            if (i < 0) return;

	            if (!itemstack.isEmpty() || flag)
	            {
	                if (itemstack.isEmpty())
	                {
	                    itemstack = new ItemStack(Items.ARROW);
	                }

	                float arrowVelocity = getNanoArrowVelocity(i);

	                if ((double)arrowVelocity >= 0.1D && ElectricItem.manager.canUse(stack, ConfigManager.NanoBowBaseCost))
	                {
	                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

	                    if (!worldIn.isRemote)
	                    {
	                        ItemArrow itemarrow = (ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW);
	                        EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
	                        entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, arrowVelocity * 3.0F, 1.0F);
	                        entityarrow.setIsCritical(true);

	                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

	                        if (j > 0)
	                        {
	                            entityarrow.setDamage(entityarrow.getDamage() + (double)j + 1);
	                        }

	                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

	                        if (k > 0)
	                        {
	                            entityarrow.setKnockbackStrength(k);
	                        }

	                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
	                        {
	                            entityarrow.setFire(100);
	                        }
	                        
	                        ElectricItem.manager.use(stack, ConfigManager.NanoBowBaseCost, entityplayer);

	                        if (flag1 || entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
	                        {
	                            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
	                        }
	                        worldIn.spawnEntity(entityarrow);
	                    }

	                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + arrowVelocity * 0.5F);

	                    if (!flag1 && !entityplayer.capabilities.isCreativeMode)
	                    {
	                        itemstack.shrink(1);

	                        if (itemstack.isEmpty())
	                        {
	                            entityplayer.inventory.deleteStack(itemstack);
	                        }
	                    }

	                    entityplayer.addStat(StatList.getObjectUseStats(this));
	                }
	            }
	        }
	    }
	
    private ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isArrow(itemstack))
                {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }
    
    public static float getNanoArrowVelocity(int charge)
    {
        float v = (float)charge;
        v = (v * v + v * 2.0F) / 5.0F;
        if(v > ConfigManager.NanoBowMaxVelocity) 
        {
        	v = ConfigManager.NanoBowMaxVelocity;
        }
        return v;
    }
    
    public int getMaxItemUseDuration(ItemStack stack) {return 72000;}
	
	@Override
    public int getItemEnchantability() {return 3;}

	@Override
	public boolean canProvideEnergy(ItemStack stack) {return false;}

	@Override
	public double getMaxCharge(ItemStack stack) {return 500000;}

	@Override
	public int getTier(ItemStack stack) {return 3;}

	@Override
	public double getTransferLimit(ItemStack stack) {return 512;}

}
