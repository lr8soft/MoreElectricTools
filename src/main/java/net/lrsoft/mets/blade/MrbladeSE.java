package net.lrsoft.mets.blade;

import java.util.List;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialeffect.IRemovable;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import mods.flammpfeil.slashblade.util.SlashBladeHooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MrbladeSE implements ISpecialEffect, IRemovable{
    private static final String EffectKey = "MrbladeSE";
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
                return;
            case Effective:
            	if(event.target.getRNG().nextInt(2) != 0) return;
            	ItemSlashBlade.damageItem(event.blade, 1, player);
            	 if(event.target!=null) {
            		 try {
            			 event.target.setHealth(event.target.getHealth()*2/3);
            		 }catch(Exception e) {
            			 event.target.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE,100,3));
            		 }
            	 }
                break;
            case NonEffective:
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
	public void AOEAttack(EntityPlayer player,World world) {
		  for(int dist = 1; dist < 10; dist++){
	            AxisAlignedBB a = player.getCollisionBoundingBox();
	            Vec3d v = player.getLookVec();
	            v = v.normalize();
	            a = a.expand(2.0f, 0.25f, 2.0f);
	            a = a.offset(v.x*(float)dist,v.y*(float)dist,v.z*(float)dist);

	            List<Entity> list = world.getEntitiesInAABBexcluding(player, a, EntitySelectorAttackable.getInstance());
	            for(Entity target : list){
	                float targetdistance = target.getDistance(player);
	              	if(target instanceof EntityLivingBase) {
	              		((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 2));
	              	}
	            }
	        }
	}
}