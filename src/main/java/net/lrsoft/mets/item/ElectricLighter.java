package net.lrsoft.mets.item;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.entity.EntityHyperGunBullet;
import net.lrsoft.mets.manager.SoundManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ElectricLighter extends UniformElectricItem {
	private final static double storageEnergy = 10000, transferSpeed = 32;
	public ElectricLighter() {
		super("electric_lighter", storageEnergy, transferSpeed, 1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack currentItem = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastRightClick(currentItem);
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastRightClick > 100)
		{
			lastRightClick = currentTime;
			if(ElectricItem.manager.use(currentItem, 50, playerIn))
			{
				//EntityLighter entityLighter = new EntityLighter(worldIn, playerIn, 2, 300);
				//worldIn.spawnEntity(entityLighter);
				
				return new ActionResult(EnumActionResult.SUCCESS, currentItem);
			}
			setLastRightClick(currentItem, lastRightClick);
		}
		
		return new ActionResult(EnumActionResult.PASS, currentItem);
	}

}
