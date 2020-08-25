package net.lrsoft.mets.blade;

import java.util.List;

import ic2.api.item.ElectricItem;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import mods.flammpfeil.slashblade.util.ReflectionAccessHelper;
import net.lrsoft.mets.entity.EntityCraftRev;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SACraftRev extends SpecialAttackBase {
  private int[] color = new int[] { 16711680, 16776960, 255, 65280, 65535, 41215, 9072383 };
  
  public String toString() {
    return "SA_CraftRev";
  }
  
  public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
    World world = player.world;
    NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
    if (!world.isRemote) {
      ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();
      Entity target = null;
      int entityId = ItemSlashBlade.TargetEntityId.get(tag).intValue();
      if (entityId != 0) {
        Entity tmp = world.getEntityByID(entityId);
        if (tmp != null && 
          tmp.getDistance((Entity)player) < 50.0F)
          target = tmp; 
      } 
      if (target == null)
        target = getEntityToWatch(player); 
      if (target != null) {
        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashDim);
        if(!ElectricItem.manager.use(stack, 1000000, player))
        	return;
        //ItemSlashBlade.damageItem(stack, 80, (EntityLivingBase)player);
        StylishRankManager.setNextAttackType((Entity)player, StylishRankManager.AttackTypes.PhantomSword);
        blade.attackTargetEntity(stack, target, player, Boolean.valueOf(true));
        player.onCriticalHit(target);
        ReflectionAccessHelper.setVelocity(target, 0.0D, 0.0D, 0.0D);
        int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
        float magicDamage = 400.0F + ItemSlashBlade.AttackAmplifier.get(tag).floatValue() * (level * ItemSlashBlade.RepairCount.get(tag).intValue() + 1) / 2.0F;
        if (target instanceof EntityLivingBase) {
          blade.setDaunting((EntityLivingBase)target);
          ((EntityLivingBase)target).hurtTime = 10;
          ((EntityLivingBase)target).hurtResistantTime = 10;
          magicDamage = ((EntityLivingBase)target).getMaxHealth() * (float)(Math.random() * 20.0D + 1.0D);
          if (target != null) {
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 4));
            if (!target.isDead)
              ((EntityLivingBase)target).setHealth(((EntityLivingBase)target).getHealth() / 2.0F); 
          } 
        } 
        for (int i = 0; i < 7 && !target.isDead && !world.isRemote; ) {
          EntityWitherSword entityDrive = new EntityWitherSword(world, (EntityLivingBase)player, magicDamage, 90.0F);
          if (entityDrive != null) {
            entityDrive.setInterval(1 + i);
            entityDrive.setLifeTime(45);
            entityDrive.setColor(this.color[i]);
            entityDrive.setBurst(false);
            try {
              entityDrive.setVelocity(18.0D, 18.0D, 18.0D);
            } catch (NoSuchMethodError error) {
              entityDrive.motionX = 18.0D;
              entityDrive.motionY = 18.0D;
              entityDrive.motionZ = 18.0D;
            } 
            entityDrive.setTargetEntityId(target.getEntityId());
            world.spawnEntity((Entity)entityDrive);
            i++;
          } 
        } 
        if (!target.isDead) {
          EntityCraftRev sword = new EntityCraftRev(world, (EntityLivingBase)player, magicDamage);
          world.spawnEntity((Entity)sword);
        } 
        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.AerialRave);
      } 
    } 
    ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Kiriorosi);
  }
  
  private Entity getEntityToWatch(EntityPlayer player) {
    World world = player.world;
    Entity target = null;
    for (int dist = 2; dist < 30; dist += 2) {
      AxisAlignedBB bb = player.getEntityBoundingBox();
      Vec3d vec = player.getLookVec();
      vec = vec.normalize();
      bb = bb.expand(2.0D, 0.25D, 2.0D);
      bb = bb.offset(vec.x * dist, vec.y * dist, vec.z * dist);
      List<Entity> list = world.getEntitiesInAABBexcluding((Entity)player, bb, HyperEntitySelector.getInstance());
      float distance = 35.0F;
      for (Entity curEntity : list) {
        float curDist = curEntity.getDistance((Entity)player);
        if (curDist < distance) {
          target = curEntity;
          distance = curDist;
        } 
      } 
      if (target != null)
        break; 
    } 
    return target;
  }
}