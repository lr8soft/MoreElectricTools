package net.lrsoft.mets.item.blade;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EUcraft_final extends ItemSlashBladeNamed implements IElectricItem, IItemHudInfo {
	   private static int maxEnergy = 10000000;//10,000,000
	   private static int maxTransfer = 16384;
	   NBTTagCompound tag = new NBTTagCompound();
	    public EUcraft_final(ToolMaterial par2EnumToolMaterial, float baseAttackModifiers) {
	        super(par2EnumToolMaterial, baseAttackModifiers);
	    }
	    @Override
	    public boolean canProvideEnergy(ItemStack itemStack) {
	        return true;
	    }
	    public Item getChargedItem(ItemStack itemStack) {
	        return this;
	    }
	    public Item getEmptyItem(ItemStack itemStack) {
	        return this;
	    }
	    @Override
	    public double getMaxCharge(ItemStack itemStack) {
	        return (double) maxEnergy;
	    }
		@Override
	    public int getTier(ItemStack itemStack) {
	        return 3;
	    }
	    @Override
	    public double getTransferLimit(ItemStack itemStack) {
	        return (double) maxTransfer;
	    }
	    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
	        super.addInformationEnergy(par1ItemStack, par2EntityPlayer, par3List, par4);
	        par3List.add("Stored EU" + (int)ElectricItem.manager.getCharge(par1ItemStack)+"/"+this.maxEnergy);
	        par3List.add("PowerTier" + " " + 3);
	        par3List.add("TechREV Final Series");
	    }

	    @Override
	    public void onUpdate(ItemStack sitem, World par2World, Entity par3Entity, int indexOfMainSlot, boolean isCurrent) {
	        super.onUpdate(sitem, par2World, par3Entity, indexOfMainSlot, isCurrent);
	        if (!par2World.isRemote) {
	            sitem.getTagCompound().setDouble("charge", (getMaxCharge(sitem) * (this.getMaxDamage(sitem) - this.getDamage(sitem))) / getMaxDamage(sitem));
	        	if((int)ElectricItem.manager.getCharge(sitem)>=7500000){
	                NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(sitem);
	             	this.IsBroken.set(reqTag, false);
	        	}
	        }
	        
	    }

		@Override
		public List<String> getHudInfo(ItemStack stack, boolean advanced) {
			 LinkedList info = new LinkedList();
		     info.add(ElectricItem.manager.getToolTip(stack));
		     return info;
		}
}
