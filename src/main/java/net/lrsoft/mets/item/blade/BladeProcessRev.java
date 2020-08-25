package net.lrsoft.mets.item.blade;

import ic2.api.item.IC2Items;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.lrsoft.mets.blade.CraftRevSE;
import net.lrsoft.mets.blade.SACraftRev;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BladeProcessRev {
  public static ItemSlashBladeNamed bladeNamed;
  
  public static ISpecialEffect customSE = SpecialEffects.register((ISpecialEffect)new CraftRevSE());
  
  @SubscribeEvent
  public void init(LoadEvent.InitEvent event) {
	ItemStack customblade = new ItemStack(BladeManager.craftrev_extra, 1, 0);
    //ItemStack customblade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
    String name = "flammpfeil.slashblade.named.craftrev_extra";
    ItemSlashBlade.specialAttacks.put(Integer.valueOf(13248999), new SACraftRev());
    NBTTagCompound tag = new NBTTagCompound();
    customblade.setTagCompound(tag);
    ItemSlashBladeNamed.CurrentItemName.set(tag, name);
    ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(1280));
    ItemSlashBladeNamed.setBaseAttackModifier(tag, 1024.0F);
    ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(false));
    ItemSlashBladeNamed.TextureName.set(tag, "named/mrblade/texture_craft");
    ItemSlashBladeNamed.ModelName.set(tag, "named/mrblade/advanced");
    ItemSlashBladeNamed.AttackAmplifier.set(tag, Float.valueOf(10.0F));
    ItemSlashBladeNamed.SpecialAttackType.set(tag, Integer.valueOf(13248999));
    ItemSlashBladeNamed.StandbyRenderType.set(tag, Integer.valueOf(2));
    ItemSlashBladeNamed.SummonedSwordColor.set(tag, Integer.valueOf(10506995));
    SpecialEffects.addEffect(customblade, customSE);
    SlashBlade.registerCustomItemStack(name, customblade);
    ItemSlashBladeNamed.NamedBlades.add(name);
    customblade.addEnchantment(Enchantments.POWER, 12);
    customblade.addEnchantment(Enchantments.LOOTING, 3);
    customblade.addEnchantment(Enchantments.UNBREAKING, 12);
    customblade.addEnchantment(Enchantments.SHARPNESS, 12);
    ItemStack bladewecreate = SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);
    ItemStack custombladeReqired = SlashBlade.getCustomBlade("flammpfeil.slashblade", BladeKineticEnergy.name);
    NBTTagCompound reqTag = ItemSlashBladeNamed.getItemTagCompound(custombladeReqired);
    ItemSlashBlade.KillCount.set(reqTag, Integer.valueOf(1600));
    ItemSlashBlade.ProudSoul.set(reqTag, Integer.valueOf(5000));
    ItemSlashBlade.RepairCount.set(reqTag, Integer.valueOf(12));
    ItemStack mrblade_need = SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);
    SlashBlade.addRecipe(name, (IRecipe)new RecipeAwakeBlade(new ResourceLocation("flammpfeil.slashblade", name), bladewecreate, custombladeReqired, 
    		new Object[] { 
    		"XXC",
            "YBY",
            "CZZ",
			Character.valueOf('B'), custombladeReqired,
			Character.valueOf('X'), ItemCraftingManager.super_iridium_compress_plate, 
			Character.valueOf('Z'), ItemCraftingManager.nano_living_metal,//ItemManager.getAllTypeStack(ItemManager),
			Character.valueOf('Y'), ItemManager.getAllTypeStack(ItemManager.superLapotronCrystal),
			Character.valueOf('C'), IC2Items.getItem("iridium_reflector")
    }));
    NamedBladeManager.registerBladeSoul(tag, "bloodrev_extra");
  }
}