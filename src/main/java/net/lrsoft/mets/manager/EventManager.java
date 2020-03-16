package net.lrsoft.mets.manager;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class EventManager {
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	@Mod.EventHandler
	public static void onPlayerHurtEvent(LivingDamageEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.world.isRemote) return;
		if(!(entity instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer)entity; 
		ItemStack target = new ItemStack(ItemManager.electricForceFieldGenerator);
		if(player.inventory.hasItemStack(target))
		{
			int itemId = player.inventory.getSlotFor(target);
			ItemStack currentItem = player.inventory.mainInventory.get(itemId);
			ElectricItem.manager.use(currentItem, 10000, player);
		}
	}

}
