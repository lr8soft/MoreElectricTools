package net.lrsoft.mets.item.blade;

import cn.mmf.lastsmith.recipe.RecipeAwakeBladeTLS;
import cn.mmf.lastsmith.util.BladeUtil;
import ic2.api.item.IC2Items;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.named.event.LoadEvent.PostInitEvent;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.blade.SAEnergyLevelTransition;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//钢刃「纳米科技」
public class BladeAdvanced {
	public static String name = "flammpfeil.slashblade.named.hyperblade_second";//

	@SubscribeEvent
	public void init(InitEvent event) {
		ItemStack customblade = new ItemStack(BladeManager.hyperblade_second, 1, 0);
		// ItemStack customblade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
///////////////////////////////////////////////////////////////////////////////////////////	  
		ItemSlashBlade.specialAttacks.put(Integer.valueOf(750955), new SAEnergyLevelTransition());
		NBTTagCompound tag = new NBTTagCompound();// NBT
		customblade.setTagCompound(tag);
		ItemSlashBladeNamed.CurrentItemName.set(tag, name);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(512));// 512
		ItemSlashBladeNamed.setBaseAttackModifier(tag, 20);//
		ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(true));//
		if (Loader.isModLoaded("lastsmith")) {
			BladeUtil.getInstance().IsBewitchedActived.set(tag, true);
		}
		ItemSlashBladeNamed.TextureName.set(tag, "named/mrblade/hypersecond");
		ItemSlashBladeNamed.ModelName.set(tag, "named/mrblade/basic");
		ItemSlashBladeNamed.SpecialAttackType.set(tag, Integer.valueOf(750955));// saex
		ItemSlashBladeNamed.StandbyRenderType.set(tag, Integer.valueOf(3));//
		ItemSlashBladeNamed.SummonedSwordColor.set(tag, 16744192);
		customblade.addEnchantment(Enchantments.POWER, 2);

		SlashBlade.registerCustomItemStack(name, customblade);
		ItemSlashBladeNamed.NamedBlades.add(name);
		NamedBladeManager.registerBladeSoul(tag, "hyperblade_second");
	}

	@SubscribeEvent
	public void postInit(PostInitEvent event){
		ItemStack bladewecreate = SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);
		ItemStack custombladeReqired = SlashBlade.findItemStack("flammpfeil.slashblade", BladeBasic.name, 1);
		NBTTagCompound reqTag = ItemSlashBladeNamed.getItemTagCompound(custombladeReqired);//
		ItemSlashBlade.KillCount.set(reqTag, 300);

		if (ConfigManager.EnableEUSlashBladeRecipe) {
			Object[] recipetable =new Object[]{
					"CYC",
					"XBX",
					"CLC",
					Character.valueOf('X'), IC2Items.getItem("crafting", "alloy"),
					Character.valueOf('Y'), IC2Items.getItem("crafting", "advanced_circuit"),
					Character.valueOf('L'), ItemManager.getAllTypeStack(IC2Items.getItem("energy_crystal")),
					Character.valueOf('B'), custombladeReqired,
					Character.valueOf('C'), ItemCraftingManager.niobium_titanium_plate
			};

			if (Loader.isModLoaded("lastsmith")) {
				SlashBlade.addRecipe(name, new RecipeAwakeBladeTLS(new ResourceLocation(MoreElectricTools.MODID, name),
						"bewitched_blade", bladewecreate, custombladeReqired, recipetable));
			} else {
				SlashBlade.addRecipe(name, new RecipeAwakeBlade(new ResourceLocation(MoreElectricTools.MODID, name),
						bladewecreate, custombladeReqired, recipetable));
			}
		}
	}
}