package net.lrsoft.mets.item.blade;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.lrsoft.mets.blade.BloodRevSE;
import net.lrsoft.mets.blade.SABloodRev;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BladeBloodRev {
	public static ItemSlashBladeNamed bladeNamed;
	public static ISpecialEffect customSE=SpecialEffects.register(new BloodRevSE());
	public static String name = "flammpfeil.slashblade.named.bloodrev_extra";//
	   @SubscribeEvent
	   public void init(InitEvent event) {
	      ItemStack customblade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
	    
///////////////////////////////////////////////////////////////////////////////////////////	  
	      ItemSlashBlade.specialAttacks.put(Integer.valueOf(13248569), new SABloodRev());
	      NBTTagCompound tag = new NBTTagCompound();//NBT
	      customblade.setTagCompound(tag);
	      ItemSlashBladeNamed.CurrentItemName.set(tag, name);
	      ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(1280));//640
	      ItemSlashBladeNamed.setBaseAttackModifier(tag, 1024);//
	      ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(true));//
	      ItemSlashBladeNamed.TextureName.set(tag, "named/mrblade/texture_blood");
	      ItemSlashBladeNamed.ModelName.set(tag, "named/mrblade/model");
	      ItemSlashBladeNamed.SpecialAttackType.set(tag, Integer.valueOf(13248569));//saex
	      ItemSlashBladeNamed.StandbyRenderType.set(tag, Integer.valueOf(2));//
	      ItemSlashBladeNamed.SummonedSwordColor.set(tag,12326679);
	      SpecialEffects.addEffect(customblade, customSE);
	      SlashBlade.registerCustomItemStack(name, customblade);
	      ItemSlashBladeNamed.NamedBlades.add(name);
	      customblade.addEnchantment(Enchantments.POWER, 12);//
	      customblade.addEnchantment(Enchantments.UNBREAKING, 12);
	      customblade.addEnchantment(Enchantments.SHARPNESS, 12);
	      customblade.addEnchantment(Enchantments.LOOTING, 3);
/////////////////////////////////////////////////////////////////////////////////////////////
	      ItemStack bladewecreate =SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);
	      ItemStack custombladeReqired = SlashBlade.getCustomBlade("flammpfeil.slashblade", BladeTechRevTheme.name); 
	      NBTTagCompound reqTag = ItemSlashBladeNamed.getItemTagCompound(custombladeReqired);
	      ItemSlashBlade.KillCount.set(reqTag, Integer.valueOf(1500));
	      ItemSlashBlade.ProudSoul.set(reqTag, Integer.valueOf(5000));
          ItemSlashBlade.RepairCount.set(reqTag, Integer.valueOf(10));
          //custombladeReqired.removeSubCompound("Enchantment");
          ItemStack mrblade_need = SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);  
          SlashBlade.addRecipe(name, 
				new RecipeAwakeBlade(new ResourceLocation(SlashBlade.modid,name),bladewecreate, custombladeReqired,
	    		  new Object[]{"CCC",
	    		               "YBY",
	    		               "CCC",
	    		  Character.valueOf('B'), custombladeReqired,
	    		  Character.valueOf('Y'), Items.NETHER_STAR, 
	    		  Character.valueOf('C'), Blocks.DIAMOND_BLOCK
	    		  }));

	      
////////////////////////////////////////////////////////////////////////////////////////////////////	      
	      NamedBladeManager.registerBladeSoul(tag, "bloodrev_extra");
	   }

}