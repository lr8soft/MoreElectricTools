package net.lrsoft.mets.blade;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityDrive;
import mods.flammpfeil.slashblade.entity.EntitySakuraEndManager;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade.ComboSequence;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;

public class SAKineticImpact extends SpecialAttackBase {
    @Override
    public String toString() {
        return "SA_KineticImpact";
    }
    @Override
    public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
        World world = player.world;
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
        if(!world.isRemote){
            ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();
            float baseModif = blade.getBaseAttackModifiers(tag);
        	String combolevel_player=String.valueOf(StylishRankManager.getStylishRank(player));
   	//	    player.addChatMessage(new ChatComponentTranslation(combolevel_player));
        	if(StylishRankManager.getStylishRank(player)>=4){	 
        		 ItemSlashBlade.damageItem(stack, -StylishRankManager.getStylishRank(player)*5, player);
                 EntitySakuraEndManager entityDA = new EntitySakuraEndManager(world, player);
        	//	 entityDA.setColor(0xFFFF00);
                 if (entityDA != null) {
                     world.spawnEntity(entityDA);
                 }
                 StylishRankManager.setRankPoint(player, StylishRankManager.getStylishRank(player)-1);
               //  ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashEdge);
        	}else{
        		ItemSlashBlade.damageItem(stack, 60, player);
                int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                float magicDamage = baseModif;

                int rank = StylishRankManager.getStylishRank(player);
                if(5 <= rank)
                    magicDamage += ItemSlashBlade.AttackAmplifier.get(tag) * (0.5f + (level / 5.0f));
                EntityDrive entityDrive = new EntityDrive(world, player, magicDamage,true,90.0f - ComboSequence.SlashEdge.comboResetTicks);
                if (entityDrive != null) {
                    entityDrive.setInitialSpeed(2.0f);
                    entityDrive.setLifeTime(45);
                    world.spawnEntity(entityDrive);
                }
                ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Kiriorosi); 
        	}


        }
 
    }

    
}
