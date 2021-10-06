package net.lrsoft.mets.blade;

import java.util.List;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialeffect.IRemovable;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import mods.flammpfeil.slashblade.util.SlashBladeHooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FoxBladeSE implements ISpecialEffect, IRemovable {
    private static final String EffectKey = "FoxBladeSE";
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
        		Vec3i offset = new Vec3i(3, 3, 3);
            	BlockPos currentPos  = new BlockPos(player.posX, player.posY, player.posZ);
        		BlockPos minPos = currentPos.subtract(offset);
        		BlockPos maxPos = currentPos.add(offset);
        		AxisAlignedBB bb = new AxisAlignedBB(minPos, maxPos);
        		
            	List<Entity> list = player.world.getEntitiesInAABBexcluding(player, bb, HyperEntitySelector.getInstance());
            	for(Entity curEntity : list){
            		if(curEntity instanceof EntityLivingBase) {
            			if(player == curEntity)
            				continue;
            			EntityLivingBase entity = (EntityLivingBase)curEntity;
            			entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30, 2));
            			entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 30, 2));
               			entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 30, 2));
            		}
            	}

            	int rank = StylishRankManager.getStylishRank(player);
            	if(rank >= 4) {
            		if(event.blade.isItemDamaged()) {
            			int currentDamage = event.blade.getItemDamage();
            			int fixDamage = rank - 3;
            			currentDamage = (currentDamage - fixDamage > 0 ? currentDamage - fixDamage : 0);
            			event.blade.setItemDamage(currentDamage);
            		}
            	}
            	
            	if(rank >= 2) {
            		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10, 1));
            		player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 10, 1));
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
}
