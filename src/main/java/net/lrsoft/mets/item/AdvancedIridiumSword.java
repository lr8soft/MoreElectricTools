package net.lrsoft.mets.item;
import java.util.LinkedList;
import java.util.List;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.core.IC2;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.model.ModelLoader;
public class AdvancedIridiumSword  extends UniformElectricItem{
	private final static double maxStorageEU = 2000000, transferSpeed = 2048;
	
	private boolean isHyperState = false;
	public AdvancedIridiumSword() {
		super("advanced_iridium_sword", maxStorageEU, transferSpeed, 4);

	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		// won't work while attacker != player
		if(!(attacker instanceof EntityPlayer))
			return true;
		
		if(ElectricItem.manager.canUse(stack, 120)) 
		{
			double attackCost = 2000;
			ElectricItem.manager.discharge(stack, 200, 4, true, false, false);
			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)attacker), 30);	
		}
		return true;
	}
	
	

}
