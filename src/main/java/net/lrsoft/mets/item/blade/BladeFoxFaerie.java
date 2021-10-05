package net.lrsoft.mets.item.blade;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.Fox;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.lrsoft.mets.blade.FoxFaerieSE;
import net.lrsoft.mets.blade.SASlashDimension;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
public class BladeFoxFaerie {
	public String name = "flammpfeil.slashblade.named.foxfaerie";
	public static ItemSlashBladeNamed bladeNamed;
	public static ISpecialEffect BladeSpeicalEffect = SpecialEffects.register(new FoxFaerieSE());
	   @SubscribeEvent
	   public void init(InitEvent event) {
	      //
	      ItemStack customblade = new ItemStack(BladeManager.fox_faerie, 1, 0);
///////////////////////////////////////////////////////////////////////////////////////////	  
	      ItemSlashBlade.specialAttacks.put(Integer.valueOf(12079), new SASlashDimension());
	      NBTTagCompound tag = new NBTTagCompound();//NBT
	      customblade.setTagCompound(tag);
	      ItemSlashBladeNamed.CurrentItemName.set(tag, name);
	      ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(540));
	      ItemSlashBlade.setBaseAttackModifier(tag, 23f);
	      ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(true));
	      ItemSlashBlade.TextureName.set(tag, "named/foxextra/bladeFaerie");
	      ItemSlashBlade.ModelName.set(tag, "named/foxextra/model");
	      ItemSlashBlade.SpecialAttackType.set(tag, Integer.valueOf(12079));
	      ItemSlashBlade.StandbyRenderType.set(tag, Integer.valueOf(1));
	      ItemSlashBlade.SummonedSwordColor.set(tag, 16711935);
	      
	      SpecialEffects.addEffect(customblade, BladeSpeicalEffect.getEffectKey(), BladeSpeicalEffect.getDefaultRequiredLevel());
	      
	      SlashBlade.registerCustomItemStack(name, customblade);
	      ItemSlashBladeNamed.NamedBlades.add(name);
	      customblade.addEnchantment(Enchantments.POWER, 3);//
	      customblade.addEnchantment(Enchantments.UNBREAKING, 3);
	      customblade.addEnchantment(Enchantments.LOOTING, 3);
	      customblade.addEnchantment(Enchantments.SHARPNESS, 6);
 /////////////////////////////////////////////////////////////////////////////////////////////
	      /*
          ItemStack custombladeReqired2 = SlashBlade.findItemStack(SlashBlade.modid,Fox.nameWhite,1); 
	      NBTTagCompound reqTag2 = ItemSlashBlade.getItemTagCompound(custombladeReqired2);//
	      ItemSlashBlade.KillCount.set(reqTag2, Integer.valueOf(1000));
	      ItemSlashBlade.ProudSoul.set(reqTag2, Integer.valueOf(12000));
          ItemSlashBlade.RepairCount.set(reqTag2, Integer.valueOf(6)); 
	      
	      
          ItemSlashBladeNamed.IsDefaultBewitched.set(reqTag2, Boolean.valueOf(true));
          ItemStack foxex = SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);  
	      SlashBlade.addRecipe(name, new RecipeAwakeBlade(new ResourceLocation("flammpfeil.slashblade", name), foxex, custombladeReqired2, 
	    		  new Object[]{"CAC",
	    		               "XBX",
	    		               "CXC",
	    		  Character.valueOf('A'), new ItemStack(Items.NETHER_STAR),
	    		  Character.valueOf('B'), custombladeReqired2,
	    		  Character.valueOf('C'), new ItemStack(Blocks.GLOWSTONE), 
	    		  Character.valueOf('X'),new ItemStack(Blocks.DIAMOND_BLOCK)
	    		  }));
	      
		  */
////////////////////////////////////////////////////////////////////////////////////////////////////	      
	      NamedBladeManager.registerBladeSoul(tag, "foxfaerie");
	   }

}
