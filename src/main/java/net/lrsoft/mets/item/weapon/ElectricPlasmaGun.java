package net.lrsoft.mets.item.weapon;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.entity.EntityGunBullet;
import net.lrsoft.mets.entity.EntityPlasmaBullet;
import net.lrsoft.mets.item.UniformElectricItem;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.SoundManager;
import net.lrsoft.mets.renderer.particle.EntityParticleSpray;
import net.lrsoft.mets.util.ItemStackUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElectricPlasmaGun extends UniformElectricItem {
	private final static double storageEnergy = 1000000, transferSpeed = 512;
	public ElectricPlasmaGun()
	{
		super("electric_plasma_gun", storageEnergy, transferSpeed, 3);
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
		if(currentTime - lastRightClick > 500)
		{
			lastRightClick = currentTime;
			if(ElectricItem.manager.use(currentGun, ConfigManager.ElectricPlasmaGunCost, playerIn))
			{
				EntityPlasmaBullet entity = new EntityPlasmaBullet(worldIn, playerIn, 20f);
				entity.shoot(playerIn.rotationYaw, playerIn.rotationPitch, 1.5f);
				worldIn.spawnEntity(entity);	
				
				worldIn.playSound((EntityPlayer)null, playerIn.posX , playerIn.posY, playerIn.posZ, 
						SoundManager.plasma_launch, playerIn.getSoundCategory(), 0.5f, 0.8F);

				setLastRightClick(currentGun, lastRightClick);
			}
			
		}
		
		return new ActionResult(EnumActionResult.PASS, currentGun);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) 
	{
		return false;
	}
	
}
