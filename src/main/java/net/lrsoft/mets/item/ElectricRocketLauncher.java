package net.lrsoft.mets.item;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.entity.EntityGunBullet;
import net.lrsoft.mets.entity.EntityRocket;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.SoundManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ElectricRocketLauncher extends UniformElectricItem {
	private final static double storageEnergy = 1000000, transferSpeed = 512;
	public ElectricRocketLauncher()
	{
		super("electric_rocket_launcher", storageEnergy, transferSpeed, 3);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack currentLauncher = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastRightClick(currentLauncher);
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastRightClick > ConfigManager.ElectricRocketLauncherInterval)
		{
			lastRightClick = currentTime;
			boolean isCreativeMode = playerIn.capabilities.isCreativeMode;
			if(ElectricItem.manager.canUse(currentLauncher, ConfigManager.ElectricRocketLauncherCost) || isCreativeMode)
			{
				boolean shouldLaunch = false;
				if(isCreativeMode)
				{
					shouldLaunch = true;
				}else
				{
					ItemStack ammo = findAmmo(playerIn);
					
					if(ammo != ItemStack.EMPTY)
					{
						ElectricItem.manager.use(currentLauncher, ConfigManager.ElectricRocketLauncherCost, playerIn);
						ammo.setCount(ammo.getCount()-1);
						shouldLaunch = true;
					}
				}
				
				if(shouldLaunch)
				{
					EntityRocket entity = new EntityRocket(worldIn, 12f);
					entity.shoot(playerIn, playerIn.rotationYaw, playerIn.rotationPitch, 2.5f);
					worldIn.spawnEntity(entity);
					setLastRightClick(currentLauncher, lastRightClick);
					return new ActionResult(EnumActionResult.SUCCESS, currentLauncher);
				}
			}
		}
		
		return new ActionResult(EnumActionResult.PASS, currentLauncher);
	}
	
	protected boolean isRocket(ItemStack stack) {
		return stack.getItem() == ItemCraftingManager.rocket;
	}
	
	private ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isRocket(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isRocket(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isRocket(itemstack))
                {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

}
