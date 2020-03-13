package net.lrsoft.mets.item.weapon;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.Sys;

import com.google.common.base.Ticker;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.core.IC2;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.UniformElectricItem;
import net.lrsoft.mets.manager.ConfigManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
public class AdvancedIridiumSword  extends UniformElectricItem{
	private final static double maxStorageEU = 10000000, transferSpeed = 2048;
	private final static double sweepingDistance = 12d;

	public AdvancedIridiumSword() {
		super("advanced_iridium_sword", maxStorageEU, transferSpeed, 4);
		this.addPropertyOverride(new ResourceLocation(MoreElectricTools.MODID, "iridium_sword_state"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) 
			{
				boolean isHyperState = getHyperState(stack);
				float hyperState = getHyperValue(stack);
				if(isHyperState) 
				{
					if(hyperState <= 0.71f)
					{
						hyperState += 0.002f;
					}
					else
					{
						hyperState = 0.1f;
					}
					setHyperValue(stack, hyperState);
					return hyperState;
				}
				return 0.0f;
			}
		});
	}
	
    private float getAttackDamage(ItemStack stack)
    {
    	boolean isHyperState = getHyperState(stack);
    	float damage = ConfigManager.AdvancedIridiumSwordBaseAttackDamage;
		if(isHyperState) 
		{
			int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
			damage *= (level==0) ? 1.5f : (level + 1);
		}
        return damage;
    }
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		// won't work while attacker != player
		if(!(attacker instanceof EntityPlayer) || attacker.getEntityWorld().isRemote)
			return true;
		
		boolean isHyperState = getHyperState(stack);
		double attackCost = ConfigManager.AdvancedIridiumSwordBaseCost * (isHyperState ? 1.5d : 1.0d);
		if(ElectricItem.manager.canUse(stack, attackCost)) 
		{
			ElectricItem.manager.discharge(stack, attackCost, 4, true, false, false);
			float attackDamage =  getAttackDamage(stack);
			//sweeping edge
			if(isHyperState && attacker instanceof EntityPlayer) 
			{
				EntityPlayer player = (EntityPlayer)attacker;
				World currentWorld = player.getEntityWorld();
			    for (EntityLivingBase entitylivingbase : currentWorld.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(1.0D, 0.25D, 1.0D)))
	            {
	                if (entitylivingbase != player && entitylivingbase != target && player.getDistanceSq(entitylivingbase) < sweepingDistance)
	                {
	                    entitylivingbase.knockBack(player, 0.4F, (double)MathHelper.sin(player.rotationYaw * 0.017f), (double)(-MathHelper.cos(player.rotationYaw * 0.017f)));
	                    entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), attackDamage / 2.0f);
	                }
	            }	
			    player.getEntityWorld().playSound((EntityPlayer)null, 
			    			player.posX, player.posY, player.posZ, 
			    			SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
			}

			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)attacker), attackDamage);	
		}
		return true;
	}
	
/*	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		boolean isHyperState = getHyperState(stack);
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected && isHyperState);
		if(isHyperState && entityIn instanceof EntityPlayer) 
		{
			EntityPlayer player = (EntityPlayer)entityIn;
			if(!ElectricItem.manager.use(stack, 200, player))
			{
				isHyperState = false;
			}
		}
		setHyperState(stack, isHyperState);
	}
	*/
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack currentSword = playerIn.getHeldItem(handIn);
		
		long lastRightClick = getLastRightClick(currentSword);
		long currentTime = System.currentTimeMillis();
		boolean isHyperState = getHyperState(currentSword);
		
		if(currentTime - lastRightClick > 100)
		{
			isHyperState = !isHyperState;	
			lastRightClick = currentTime;
			
			setHyperState(currentSword, isHyperState);
			setLastRightClick(currentSword, lastRightClick);
		}
		
		return new ActionResult(EnumActionResult.SUCCESS, currentSword);
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		 Block block = state.getBlock();
	     if (block == Blocks.WEB)
	     {
	    	 return 18.0F;
	     }
	     else
	    	 return 1.0f;
	}
	
	private void setHyperState(ItemStack stack, boolean state) {stack.getItem().getNBTShareTag(stack).setBoolean("isHyperState", state);}

	private boolean getHyperState(ItemStack stack) 
	{
		boolean value = false;
		try {
			value = stack.getItem().getNBTShareTag(stack).getBoolean("isHyperState");
		} catch (Exception expt) {}
		return value;
	}

	private void setHyperValue(ItemStack stack, float value) {stack.getItem().getNBTShareTag(stack).setFloat("HyperValue", value);}
	private float getHyperValue(ItemStack stack) 
	{
		float value = 0.0f;
		try {
			value = stack.getItem().getNBTShareTag(stack).getFloat("HyperValue");
		} catch (Exception expt) {}
		return value;
	}
	
}
