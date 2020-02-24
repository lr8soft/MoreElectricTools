package net.lrsoft.mets.item;
import java.util.LinkedList;
import java.util.List;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
public class AdvancedIridiumSword  extends UniformElectricItem{
	private final static double maxStorageEU = 2000000, transferSpeed = 2048;
	private boolean isHyperState = false;
	public AdvancedIridiumSword() {
		super("AdvancedIridiumSword", maxStorageEU, transferSpeed, 4);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		// won't work while attacker != player
		if( (!(attacker instanceof EntityPlayer)) || (!(attacker instanceof EntityPlayerMP)))
			return true;
		
		double attackCost = 2000;
		if(isHyperState) 
		{
			
		}
		else
		{
			
			
		}
		return true;
	}

}
