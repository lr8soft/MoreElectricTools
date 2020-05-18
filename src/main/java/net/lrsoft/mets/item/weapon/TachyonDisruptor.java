package net.lrsoft.mets.item.weapon;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.entity.EntityHyperGunBullet;
import net.lrsoft.mets.entity.EntityTachyonBullet;
import net.lrsoft.mets.item.UniformElectricItem;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.SoundManager;
import net.lrsoft.mets.util.ItemStackUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TachyonDisruptor extends UniformElectricItem {
	private final static double storageEnergy = 400000000d, transferSpeed = 8192;
	public TachyonDisruptor()
	{
		super("tachyon_disruptor", storageEnergy, transferSpeed, 5);
		this.addPropertyOverride(new ResourceLocation(MoreElectricTools.MODID, "energy_value"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) 
			{
				return ItemStackUtils.getCurrentTex(stack, 4) / 4.0f;
			}
		});
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack currentGun = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastRightClick(currentGun);
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastRightClick > 250)
		{
			float ratio = getElectricItemAttenuationRatio(currentGun);
			if(ElectricItem.manager.use(currentGun, ConfigManager.TachyonDisruptorCost * ratio, playerIn))
			{
				EntityTachyonBullet entity = new EntityTachyonBullet(worldIn, playerIn, 120f, 600);
				entity.shoot(playerIn.rotationYaw, playerIn.rotationPitch, 4.0f);
				worldIn.spawnEntity(entity);		
				
				worldIn.playSound((EntityPlayer)null, playerIn.posX , playerIn.posY, playerIn.posZ, 
						SoundManager.laser_bullet_shoot, playerIn.getSoundCategory(), 0.2f, 0.4F);
				setLastRightClick(currentGun, currentTime);
			}
		}
		
		return new ActionResult(EnumActionResult.PASS, currentGun);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase targetEntity, EntityLivingBase attacker) {
		 if (targetEntity instanceof EntityLivingBase && attacker instanceof EntityPlayer)
         {
			EntityPlayer player = (EntityPlayer) attacker;
			EntityLivingBase enemyEntity = (EntityLivingBase) targetEntity;
			float ratio = getElectricItemAttenuationRatio(stack);
			if (ElectricItem.manager.use(stack, ConfigManager.TachyonDisruptorCost * ratio, player)) {
				enemyEntity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 30, 3));
				enemyEntity.knockBack(attacker, 1.0f, (double) MathHelper.sin(player.rotationYaw * 0.017453292F),
						(double) (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
				enemyEntity.attackEntityFrom(DamageSource.causePlayerDamage(player), getAttackDamage(30.0f, stack));	
			}
         }
		return true;
	}
	
    private float getAttackDamage(float damage, ItemStack stack)
    {
		int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
		damage *= (level == 0) ? 1.0f : level * 1.25f;
        return damage;
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
    	boolean value = super.canApplyAtEnchantingTable(stack, enchantment);
    	if(enchantment == Enchantments.SHARPNESS)
    	{
    		value = true;
    	}
    	return value;
    }
}
