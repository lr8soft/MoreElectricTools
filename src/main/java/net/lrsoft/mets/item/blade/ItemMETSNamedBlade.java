package net.lrsoft.mets.item.blade;

import java.util.LinkedList;
import java.util.List;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMETSNamedBlade extends ItemSlashBladeNamed implements IElectricItem, IItemHudInfo{
	private int maxEnergy, maxTransfer, tier;
	
	public ItemMETSNamedBlade(int maxEnergy, int transfer, int tier, ToolMaterial par2EnumToolMaterial, float baseAttackModifiers) {
		super(par2EnumToolMaterial, baseAttackModifiers);
		this.maxEnergy = maxEnergy;
		this.maxTransfer = transfer;
		this.tier = tier;
	}

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
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
        return tier;
    }
	
    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return (double) maxTransfer;
    }
    
    @Override
    public void onUpdate(ItemStack sitem, World par2World, Entity par3Entity, int indexOfMainSlot, boolean isCurrent) {
        super.onUpdate(sitem, par2World, par3Entity, indexOfMainSlot, isCurrent);
        if (!par2World.isRemote) {
            sitem.getTagCompound().setDouble("charge", 
            		(getMaxCharge(sitem) * (this.getMaxDamage(sitem) - this.getDamage(sitem))) / getMaxDamage(sitem));
        }
        
    }
    
	@Override
	public List<String> getHudInfo(ItemStack stack, boolean advanced) {
        LinkedList info = new LinkedList();
        info.add(ElectricItem.manager.getToolTip(stack));
        return info;
	}
}

