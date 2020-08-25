package net.lrsoft.mets.item.blade;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import net.lrsoft.mets.blade.SAEnergyLevelTransition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
public class BladeAdvanced {
	public static ItemSlashBladeNamed bladeNamed;
	public static String name = "flammpfeil.slashblade.named.hyperblade_second";//
	   @SubscribeEvent
	   public void preinit(InitEvent event) {
	      ItemStack customblade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
///////////////////////////////////////////////////////////////////////////////////////////	  
	      ItemSlashBlade.specialAttacks.put(Integer.valueOf(750955), new SAEnergyLevelTransition());
	      NBTTagCompound tag = new NBTTagCompound();//NBT
	      customblade.setTagCompound(tag);
	      ItemSlashBladeNamed.CurrentItemName.set(tag, name);
	      ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(512));//512
	      ItemSlashBladeNamed.setBaseAttackModifier(tag, 20);//
	      ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(false));//
	      ItemSlashBladeNamed.TextureName.set(tag, "named/mrblade/hypersecond");
	      ItemSlashBladeNamed.ModelName.set(tag, "named/mrblade/model0");
	      ItemSlashBladeNamed.SpecialAttackType.set(tag, Integer.valueOf(750955));//saex
	      ItemSlashBladeNamed.StandbyRenderType.set(tag, Integer.valueOf(3));//
	      ItemSlashBladeNamed.SummonedSwordColor.set(tag,16744192);
	      customblade.addEnchantment(Enchantments.POWER, 2);
	      SlashBlade.registerCustomItemStack(name, customblade);
	      ItemSlashBladeNamed.NamedBlades.add(name);
	      NamedBladeManager.registerBladeSoul(tag, "hyperblade_second");
	      
	      
		  ItemStack bladewecreate =SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);
	      ItemStack bladesoul_need= SlashBlade.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1);
	      ItemStack custombladeReqired = SlashBlade.findItemStack("flammpfeil.slashblade", BladeBasic.name, 1);
		  NBTTagCompound reqTag = ItemSlashBladeNamed.getItemTagCompound(custombladeReqired);//
		  ItemSlashBlade.KillCount.set(reqTag, 300);

        SlashBlade.addRecipe(name, 
				new RecipeAwakeBlade(new ResourceLocation(SlashBlade.modid,name),bladewecreate, custombladeReqired, 
		    	new Object[]{
		    			"CAC",
		    			"XBX",
		    			"CAC",
		        Character.valueOf('X'), Blocks.IRON_BLOCK,
		    	Character.valueOf('A'), Items.DIAMOND,//
		    	Character.valueOf('B'), custombladeReqired,
		    	Character.valueOf('C'), bladesoul_need 
		    	}));
	   }

}