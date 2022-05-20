package net.lrsoft.mets.item.blade;

import cn.mmf.lastsmith.recipe.RecipeAwakeBladeTLS;
import ic2.api.item.IC2Items;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.named.event.LoadEvent.PostInitEvent;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//钢刃「源初」
public class BladeBasic {
	public static String name = "flammpfeil.slashblade.named.trblade_first";
	public static ItemSlashBladeNamed bladeNamed;

	@SubscribeEvent
	public void init(InitEvent event) {
		NBTTagCompound tag = new NBTTagCompound();
		ItemStack customblade = new ItemStack(BladeManager.trblade_first, 1, 0);
		customblade.setTagCompound(tag);

		ItemSlashBladeNamed.CurrentItemName.set(tag, name);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(141));// 512
		ItemSlashBladeNamed.setBaseAttackModifier(tag, 7);//
		ItemSlashBladeNamed.TextureName.set(tag, "named/mrblade/trfirst");
		ItemSlashBladeNamed.ModelName.set(tag, "named/mrblade/basic");
		ItemSlashBladeNamed.SpecialAttackType.set(tag, Integer.valueOf(3));// saex
		ItemSlashBladeNamed.StandbyRenderType.set(tag, Integer.valueOf(3));//
		ItemSlashBladeNamed.SummonedSwordColor.set(tag, 14276889);
		customblade.addEnchantment(Enchantments.POWER, 1);//

		SlashBlade.registerCustomItemStack(name, customblade);
		ItemSlashBladeNamed.NamedBlades.add(name);
		NamedBladeManager.registerBladeSoul(tag, "trfirst");

	}
	@SubscribeEvent
	public void postInit(PostInitEvent event){
		ItemStack custombladeReqired = new ItemStack(SlashBlade.weapon);
		NBTTagCompound reqTag = ItemSlashBladeNamed.getItemTagCompound(custombladeReqired);//
		ItemSlashBladeNamed.KillCount.set(reqTag, Integer.valueOf(70));
		ItemStack trfirst_need = SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);

		if (ConfigManager.EnableEUSlashBladeRecipe) {
			Object[] recipetable = new Object[]{
					"XAX",
					"XBX",
					"CAC",
					Character.valueOf('X'), IC2Items.getItem("casing", "iron"),
					Character.valueOf('A'), IC2Items.getItem("crafting", "circuit"),
					Character.valueOf('B'), custombladeReqired,
					Character.valueOf('C'), ItemManager.getAllTypeStack(ItemManager.advancedLithiumBattery)
			};

			if (Loader.isModLoaded("lastsmith")){
				SlashBlade.addRecipe(name, new RecipeAwakeBladeTLS(new ResourceLocation(MoreElectricTools.MODID, name),
						"slashblade_white", trfirst_need, custombladeReqired, recipetable));
			} else {
				SlashBlade.addRecipe(name, new RecipeAwakeBlade(new ResourceLocation("flammpfeil.slashblade", name),
						trfirst_need, custombladeReqired, recipetable));
			}
		}
	}
}
