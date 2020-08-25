package net.lrsoft.mets.blade;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialeffect.IRemovable;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import mods.flammpfeil.slashblade.util.SlashBladeHooks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KineticSE implements ISpecialEffect, IRemovable{
    private static final String EffectKey = "KineticSE";
    private boolean burst=false;
    private int interval=0;
    private boolean useBlade(ItemSlashBlade.ComboSequence sequence){
        if(sequence.useScabbard) return false;
        if(sequence == ItemSlashBlade.ComboSequence.None) return false;
        if(sequence == ItemSlashBlade.ComboSequence.Noutou) return false;
        return true;
    }

    @SubscribeEvent
    public void onImpactEffectEvent(SlashBladeEvent.ImpactEffectEvent event){

        if(!useBlade(event.sequence)) return;

        if(!SpecialEffects.isPlayer(event.user)) return;
        EntityPlayer player = (EntityPlayer) event.user;
        
        switch (SpecialEffects.isEffective(player, event.blade, this)){
            case None:
            	if(interval>0) interval--;
                return;
            case Effective:
            	float damage;
            	try {
            		damage=event.target.getHealth() / 4;
            	}catch(Exception e) {
            		damage=20f+EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, event.blade);
            	}
            	if(interval<=0) {
                    if(event.target!=null) {
                    	EntityWitherSword entityDrive = new EntityWitherSword(event.target.world, player, damage,90);
                        if (entityDrive != null) {
                            entityDrive.setInterval(1);
                            entityDrive.setLifeTime(40);
                            int color =burst ?-0xFFFFB1:-0xFFFF7B;
                            entityDrive.setColor(color);
                            entityDrive.setBurst(burst);
                            entityDrive.setTargetEntityId(event.target.getEntityId());
                            event.target.world.spawnEntity(entityDrive);
                            burst=!burst;
                        }
                    }
                    interval+=5;
            	}else {
            		interval--;
            	}
                break;
            case NonEffective:
            	if(interval>0) interval--;
                break;
        }
        player.onEnchantmentCritical(event.target);

    }

    @SubscribeEvent
    public void onUpdateItemSlashBlade(SlashBladeEvent.OnUpdateEvent event){

        if(!SpecialEffects.isPlayer(event.entity)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;

        switch (SpecialEffects.isEffective(player,event.blade,this)){
            case None:
                return;
            case NonEffective:
                if(player.getRNG().nextInt(4) != 0) return;
                break;
            case Effective:
                return;
        }
    }

    @Override
    public void register() {
        SlashBladeHooks.EventBus.register(this);
    }

    @Override
    public int getDefaultRequiredLevel() {
        return 25;
    }

    @Override
    public String getEffectKey() {
        return EffectKey;
    }

	@Override
	public boolean canCopy(ItemStack arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canRemoval(ItemStack arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
