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
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.blade.MrbladeSE;
import net.lrsoft.mets.blade.SAHighEnergyParticleFlowAlpha;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//灵钢刃「技术革新」
public class BladeTechRevTheme {
	public static ItemSlashBladeNamed bladeNamed;
	public static ISpecialEffect customSE = SpecialEffects.register(new MrbladeSE());
	public static String name = "flammpfeil.slashblade.named.mrblade_final";//

	@SubscribeEvent
	public void init(InitEvent event) {
		ItemStack customblade = new ItemStack(BladeManager.mrblade_final, 1, 0);
///////////////////////////////////////////////////////////////////////////////////////////	  
		ItemSlashBlade.specialAttacks.put(Integer.valueOf(750954), new SAHighEnergyParticleFlowAlpha());
		NBTTagCompound tag = new NBTTagCompound();// NBT
		customblade.setTagCompound(tag);
		ItemSlashBladeNamed.CurrentItemName.set(tag, name);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(640));// 640
		ItemSlashBladeNamed.setBaseAttackModifier(tag, 25);//
		ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(true));
		if (Loader.isModLoaded("lastsmith")) {
			BladeUtil.getInstance().IsBewitchedActived.set(tag, true);
		}
		ItemSlashBladeNamed.TextureName.set(tag, "named/mrblade/texture");
		ItemSlashBladeNamed.ModelName.set(tag, "named/mrblade/advanced");
		ItemSlashBladeNamed.SpecialAttackType.set(tag, Integer.valueOf(750954));
		ItemSlashBladeNamed.StandbyRenderType.set(tag, Integer.valueOf(2));
		ItemSlashBladeNamed.SummonedSwordColor.set(tag, 255);
		SpecialEffects.addEffect(customblade, customSE);
		ItemSlashBladeNamed.IsCharged.set(tag, false);
		ItemSlashBladeNamed.IsBroken.set(tag, false);
		customblade.addEnchantment(Enchantments.POWER, 3);//
		customblade.addEnchantment(Enchantments.UNBREAKING, 3);
		customblade.addEnchantment(Enchantments.SHARPNESS, 3);

		SlashBlade.registerCustomItemStack(name, customblade);
		ItemSlashBladeNamed.NamedBlades.add(name);
		NamedBladeManager.registerBladeSoul(tag, "mrblade_final");
		/////////////////////////////////////////////////////////////////////////////////////////////
	}

	@SubscribeEvent
	public void postInit(PostInitEvent event){
		ItemStack custombladeReqired = SlashBlade.findItemStack("flammpfeil.slashblade", BladeAdvanced.name, 1);
		NBTTagCompound reqTag = ItemSlashBladeNamed.getItemTagCompound(custombladeReqired);//
		ItemSlashBladeNamed.KillCount.set(reqTag, Integer.valueOf(1000));
		ItemSlashBladeNamed.RepairCount.set(reqTag, Integer.valueOf(6));
		ItemStack mrblade_need = SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);

		if (ConfigManager.EnableEUSlashBladeRecipe) {
			Object[] recipetable = new Object[] {
					"CAC",
					"YBY",
					"CLC",
					Character.valueOf('A'), ItemCraftingManager.super_circuit,
					Character.valueOf('B'), custombladeReqired,
					Character.valueOf('C'), IC2Items.getItem("crafting", "iridium"),
					Character.valueOf('Y'), ItemCraftingManager.niobium_titanium_plate,
					Character.valueOf('L'), ItemManager.getAllTypeStack(IC2Items.getItem("lapotron_crystal"))
			};

			if (Loader.isModLoaded("lastsmith")) {
				SlashBlade.addRecipe(name, new RecipeAwakeBladeTLS(new ResourceLocation(MoreElectricTools.MODID, name),
						"sharpness", mrblade_need, custombladeReqired, recipetable));
			} else {
				SlashBlade.addRecipe(name, new RecipeAwakeBlade(new ResourceLocation(MoreElectricTools.MODID, name),
						mrblade_need, custombladeReqired, recipetable));
			}

		}
////////////////////////////////////////////////////////////////////////////////////////////////////	      
	}
}
