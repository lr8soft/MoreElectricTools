package net.lrsoft.mets.blade;


import java.util.List;

import ic2.api.item.ElectricItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.capability.MobEffect.MobEffect;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import mods.flammpfeil.slashblade.util.ReflectionAccessHelper;

public class SABloodRev extends SpecialAttackBase {

    @Override
    public String toString() {
        return "SA_BloodRev";
    }

    @Override
    public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
        World world = player.world;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);

        if(!world.isRemote){

            ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();

            Entity target = null;

            int entityId = ItemSlashBlade.TargetEntityId.get(tag);

            if(entityId != 0){
                Entity tmp = world.getEntityByID(entityId);
                if(tmp != null){
                    if(tmp.getDistance(player)  < 50.0f)
                        target = tmp;
                }
            }

            if(target == null){
                target = getEntityToWatch(player);
            }
            if(target != null){
                ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashDim);
                
                if(!ElectricItem.manager.use(stack, 1000000, player))
                	return;
                //ItemSlashBlade.damageItem(stack, 80, player);
                StylishRankManager.setNextAttackType(player, StylishRankManager.AttackTypes.PhantomSword);
                blade.attackTargetEntity(stack, target, player, true);
                player.onCriticalHit(target);

                ReflectionAccessHelper.setVelocity(target, 0, 0, 0);
                //target.addVelocity(0.0, 0.55D, 0.0);

                if(target instanceof EntityLivingBase){
                    blade.setDaunting((EntityLivingBase)target);
                    ((EntityLivingBase) target).hurtTime = 5;
                    ((EntityLivingBase) target).hurtResistantTime = 5;
                    if(target!=null) {
                    	((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.POISON, 20 * 5, 4));
                    	if(!target.isDead) 
                    		((EntityLivingBase) target).setHealth(((EntityLivingBase) target).getHealth()/2);
                    }  	
                }
                int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                float magicDamage = 500.0f  + ItemSlashBlade.AttackAmplifier.get(tag) *level+ItemSlashBlade.RepairCount.get(tag)*5;
//////////////////////////////////////////////////////////////////////////////////
               int  count = 35,tickcount=0;
       		  	
                for(int i = 0; i < count;i++){
                	if(target.isDead) break;
                    if(!world.isRemote){
                        boolean select = (i % 2 == 0);
                        EntityWitherSword entityDrive = new EntityWitherSword(world, player, magicDamage,90.0f);
                        
                        if (entityDrive != null) {
                            entityDrive.setInterval(1+i/2);;
                            entityDrive.setLifeTime(50);
                            int color = select ? -0xFFA07A : -0xFF6347;
                            entityDrive.setColor(color);
                            entityDrive.setBurst(false);
							entityDrive.motionX = 19;
							entityDrive.motionY = 19;
							entityDrive.motionZ = 19;
                            
                            entityDrive.setTargetEntityId(target.getEntityId());
                            entityDrive.setPosition(player.posX+Math.cos(3*i)+3*Math.cos(i*Math.PI)+0.5, player.posY+Math.tan(3*i)+0.5, player.posZ+Math.cos(3*i)+3*Math.cos(i*Math.PI)+0.5);
                            world.spawnEntity(entityDrive);
                            
                            
                        }else {
                        	break;
                        }
                    }else {
                    	break;
                    }
                }
                ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.AerialRave);
            }
        }
        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Kiriorosi);
    }

    private Entity getEntityToWatch(EntityPlayer player){
        World world = player.world;
        Entity target = null;
        for(int dist = 2; dist < 30; dist+=2){
            AxisAlignedBB bb = player.getEntityBoundingBox();
            Vec3d vec = player.getLookVec();
            vec = vec.normalize();
            bb = bb.expand(2.0f, 0.25f, 2.0f);
            bb = bb.offset(vec.x*(float)dist,vec.y*(float)dist,vec.z*(float)dist);


            List<Entity> list = world.getEntitiesInAABBexcluding(player, bb, HyperEntitySelector.getInstance());
            float distance = 35.0f;
            for(Entity curEntity : list){
                float curDist = curEntity.getDistance(player);
                if(curDist < distance)
                {
                    target = curEntity;
                    distance = curDist;
                }
            }
            if(target != null)
                break;
        }
        return target;
    }

}
