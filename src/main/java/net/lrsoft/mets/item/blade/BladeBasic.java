package net.lrsoft.mets.item.blade;

import java.util.LinkedList;
import java.util.List;

import ic2.api.item.IC2Items;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BladeBasic {
	public static String name = "flammpfeil.slashblade.named.trblade_first";
	NBTTagCompound tag = new NBTTagCompound();
	ItemStack customblade = new ItemStack(BladeManager.trblade_first, 1, 0);
	public static ItemSlashBladeNamed bladeNamed;

	@SubscribeEvent
	public void init(InitEvent event) {
		customblade.setTagCompound(tag);
		ItemSlashBladeNamed.CurrentItemName.set(tag, name);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(141));// 512
		ItemSlashBladeNamed.setBaseAttackModifier(tag, 7);//
		ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(false));//
		ItemSlashBladeNamed.TextureName.set(tag, "named/mrblade/trfirst");
		ItemSlashBladeNamed.ModelName.set(tag, "named/mrblade/basic");
		ItemSlashBladeNamed.SpecialAttackType.set(tag, Integer.valueOf(3));// saex
		ItemSlashBladeNamed.StandbyRenderType.set(tag, Integer.valueOf(3));//
		ItemSlashBladeNamed.SummonedSwordColor.set(tag, 14276889);
		SlashBlade.registerCustomItemStack(name, customblade);
		ItemSlashBladeNamed.NamedBlades.add(name);
		customblade.addEnchantment(Enchantments.POWER, 1);//

		ItemStack custombladeReqired = new ItemStack(SlashBlade.weapon);
		NBTTagCompound reqTag = ItemSlashBladeNamed.getItemTagCompound(custombladeReqired);//
		ItemSlashBladeNamed.KillCount.set(reqTag, Integer.valueOf(70));
		ItemStack trfirst_need = SlashBlade.getCustomBlade(name);

		if (ConfigManager.EnableEUSlashBladeRecipe) {
			SlashBlade.addRecipe(name, new RecipeAwakeBlade(new ResourceLocation(SlashBlade.modid, name), trfirst_need,
					custombladeReqired,
					new Object[] { 
							"XAX", "XBX", "CAC", 
							Character.valueOf('X'), IC2Items.getItem("casing", "iron"),
							Character.valueOf('A'), IC2Items.getItem("crafting", "circuit"),
							Character.valueOf('B'), custombladeReqired, 
							Character.valueOf('C'), ItemManager.getAllTypeStack(ItemManager.advancedLithiumBattery) 
							}));
		}
		NamedBladeManager.registerBladeSoul(tag, "trfirst");
	}
}
