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
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import mods.flammpfeil.slashblade.util.ReflectionAccessHelper;

public class SAHighEnergyParticleFlowAlpha extends SpecialAttackBase {
    @Override
    public String toString() {
        return "SA_HighEnergyParticleFlow_alpha";
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
                    if(tmp.getDistance(player) < 30.0f)
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
                //ItemSlashBlade.damageItem(stack, 50, player);
                StylishRankManager.setNextAttackType(player, StylishRankManager.AttackTypes.PhantomSword);
                blade.attackTargetEntity(stack, target, player, true);
                player.onCriticalHit(target);

                ReflectionAccessHelper.setVelocity(target, 0, 0, 0);
                //target.addVelocity(0.0, 0.55D, 0.0);

                if(target instanceof EntityLivingBase){
                    blade.setDaunting((EntityLivingBase)target);
                    ((EntityLivingBase) target).hurtTime = 0;
                    ((EntityLivingBase) target).hurtResistantTime = 0;
                }

                int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                float magicDamage = 15.0f + ItemSlashBlade.AttackAmplifier.get(tag) * (level / 5.0f);
//////////////////////////////////////////////////////////////////////////////////
                int  count = 10;
                if(blade.RepairCount.get(tag)>=11){
                	count=count+ 33;
                }else{
                	count=count+blade.RepairCount.get(tag)*3;
                }
                for(int i = 0; i < count;i++){

                    if(!world.isRemote){
                        boolean isBurst = (i % 2 == 0);

                        EntityWitherSword entityDrive = new EntityWitherSword(world, player, magicDamage,90.0f);
                        if (entityDrive != null) {
                            entityDrive.setInterval((int) (1+i*0.5));;
                            entityDrive.setLifeTime(45);
                            int color = isBurst ? -0x00CED1 : -0x00CED1;
                            entityDrive.setColor(color);
                            entityDrive.setBurst(isBurst);
                            entityDrive.setTargetEntityId(target.getEntityId());
                            
                            world.spawnEntity(entityDrive);

                        }
                    }
                }
            }
        }
        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Kiriorosi);
    }

    private Entity getEntityToWatch(EntityPlayer player){
        World world = player.world;
        Entity target = null;
        for(int dist = 2; dist < 20; dist+=2){
            AxisAlignedBB bb = player.getEntityBoundingBox();
            Vec3d vec = player.getLookVec();
            vec = vec.normalize();
            bb = bb.expand(2.0f, 0.25f, 2.0f);
            bb = bb.offset(vec.x*(float)dist,vec.y*(float)dist,vec.z*(float)dist);

            List<Entity> list = world.getEntitiesInAABBexcluding(player, bb, EntitySelectorAttackable.getInstance());
            float distance = 30.0f;
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

