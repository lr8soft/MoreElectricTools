package net.lrsoft.mets.item.blade;

import cn.mmf.lastsmith.item.ItemSlashBladeNamedTLS;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.lrsoft.mets.blade.HyperEntitySelector;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public class ItemMETSNamedBladeTLS extends ItemSlashBladeNamedTLS implements IElectricItem, IItemHudInfo{
	private int maxEnergy, maxTransfer, tier;
	private Method methodUpdateStyleAttackType = null;
	public ItemMETSNamedBladeTLS(int maxEnergy, int transfer, int tier, ToolMaterial par2EnumToolMaterial, float baseAttackModifiers) {
		super(par2EnumToolMaterial, baseAttackModifiers);
		this.maxEnergy = maxEnergy;
		this.maxTransfer = transfer;
		this.tier = tier;

		try {
			methodUpdateStyleAttackType = ItemSlashBlade.class.getDeclaredMethod("updateStyleAttackType", ItemStack.class, EntityLivingBase.class);
	    	methodUpdateStyleAttackType.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
	
	@Override
	public void doAttack(ItemStack stack, ComboSequence comboSeq, EntityPlayer player){
        World world = player.getEntityWorld();
        NBTTagCompound tag = getItemTagCompound(stack);
        EnumSet<SwordType> swordType = getSwordType(stack);

        long currentTime = world.getTotalWorldTime();
        LastActionTime.set(tag, currentTime);

        OnClick.set(tag,true);
        setPlayerEffect(stack, comboSeq, player);
        setComboSequence(tag, comboSeq);

        doSwingItem(stack, player);

        if(methodUpdateStyleAttackType != null)
        {
            try {
                methodUpdateStyleAttackType.invoke(this, stack, player);
    		} catch (Exception e) {
    			super.doAttack(stack, comboSeq, player);
    			return;
    		}
        	
        }else {
        	super.doAttack(stack, comboSeq, player);
        	return;
        }


        AxisAlignedBB bb = getBBofCombo(stack, comboSeq, player);

        int rank = StylishRankManager.getStylishRank(player);

        List<Entity> list = world.getEntitiesInAABBexcluding(player, bb, HyperEntitySelector.getInstance());

        StylishRankManager.Whiffs(player, list.isEmpty());

        for(Entity curEntity : list){
            if(stack.isEmpty()) break;

            switch (comboSeq) {
                case Saya1:
                case Saya2:
                case Force3:
                case Force4:
                    float attack = 4.0f;
                    if(rank < 3 || swordType.contains(SwordType.Broken)){
                        attack = 2.0f;
                    }else{
                        attack += Item.ToolMaterial.STONE.getAttackDamage(); //stone like
                        if(swordType.contains(SwordType.FiercerEdge) && player instanceof EntityPlayer){
                            attack += AttackAmplifier.get(tag) * 0.5f;
                        }
                    }

                    if (curEntity instanceof EntityLivingBase)
                    {
                        float var4 = 0;
                        var4 = EnchantmentHelper.getModifierForCreature(stack, ((EntityLivingBase)curEntity).getCreatureAttribute());
                        if(var4 > 0)
                            attack += var4;
                    }


                    if (curEntity instanceof EntityLivingBase){
                        attack = Math.min(attack,((EntityLivingBase)curEntity).getHealth()-1);
                    }


                    curEntity.hurtResistantTime = 0;
                    curEntity.attackEntityFrom(DamageSource.causeMobDamage(player), attack);


                    if (curEntity instanceof EntityLivingBase){
                        this.hitEntity(stack, (EntityLivingBase)curEntity, player);
                    }

                    break;

                case None:
                    break;

                default:
                    attackTargetEntity(stack, curEntity, player, true);
                    player.onCriticalHit(curEntity);
                    break;
            }
        }
        OnClick.set(tag, false);


        if (swordType.containsAll(SwordType.BewitchedPerfect) && comboSeq.equals(ComboSequence.Battou)) {
            ItemSlashBlade.damageItem(stack, 10, player);
        }
    }
}

