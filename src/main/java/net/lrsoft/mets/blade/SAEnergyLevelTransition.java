package net.lrsoft.mets.blade;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityDrive;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade.ComboSequence;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;

public class SAEnergyLevelTransition extends SpecialAttackBase {


    @Override
    public String toString() {
        return "SA_EnergyLevelTransition";
    }

    @Override
    public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
        World world = player.world;
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
        if(!world.isRemote){
            ItemSlashBlade.damageItem(stack, 20, player);
            ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();
            float baseModif = blade.getBaseAttackModifiers(tag)+blade.RepairCount.get(tag);
            int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
            float magicDamage = baseModif;

            int rank = StylishRankManager.getStylishRank(player);
            if(5 <= rank)
                magicDamage += ItemSlashBlade.AttackAmplifier.get(tag) * (0.5f + (level / 5.0f));
            EntityDrive entityDrive = new  EntityDrive(world, player, magicDamage,true,90.0f - ComboSequence.SlashEdge.swingDirection);
            if (entityDrive != null) {
      //          entityDrive.setColor(16744192);
                entityDrive.setInitialSpeed(2.0f);
                entityDrive.setLifeTime(45);
                world.spawnEntity(entityDrive);
            }
        }

        ItemSlashBlade.setComboSequence(tag, ComboSequence.SlashEdge);
    }
}

