package net.lrsoft.mets.item.blade;

import cn.mmf.lastsmith.recipe.RecipeAwakeBladeTLS;
import cn.mmf.lastsmith.util.BladeUtil;
import ic2.api.item.IC2Items;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.named.event.LoadEvent.PostInitEvent;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.lrsoft.mets.blade.KineticSE;
import net.lrsoft.mets.blade.SAKineticImpact;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//灵钢刃「能量守恒」
public class BladeKineticEnergy {
	public static ItemSlashBladeNamed bladeNamed;
	public static ISpecialEffect customSE = SpecialEffects.register(new KineticSE());
	public static String name = "flammpfeil.slashblade.named.kineticenergyblade_final";

	@SubscribeEvent
	public void init(InitEvent event) {
		ItemStack customblade = new ItemStack(BladeManager.kineticenergyblade_final, 1, 0);
		// ItemStack customblade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
///////////////////////////////////////////////////////////////////////////////////////////	  
		ItemSlashBladeNamed.specialAttacks.put(Integer.valueOf(760959), new SAKineticImpact());
		NBTTagCompound tag = new NBTTagCompound();// NBT
		customblade.setTagCompound(tag);
		ItemSlashBladeNamed.CurrentItemName.set(tag, name);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(451));// 640
		ItemSlashBladeNamed.setBaseAttackModifier(tag, 20);//
		ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(true));//
		if (Loader.isModLoaded("lastsmith")) {
			BladeUtil.getInstance().IsBewitchedActived.set(tag, true);
		}
		ItemSlashBladeNamed.TextureName.set(tag, "named/mrblade/texture2");
		ItemSlashBladeNamed.ModelName.set(tag, "named/mrblade/advanced");
		ItemSlashBladeNamed.SpecialAttackType.set(tag, Integer.valueOf(760959));// saex
		ItemSlashBladeNamed.StandbyRenderType.set(tag, Integer.valueOf(3));//
		ItemSlashBladeNamed.SummonedSwordColor.set(tag, 14276889);
		SpecialEffects.addEffect(customblade, customSE);
		customblade.addEnchantment(Enchantments.POWER, 3);//
		customblade.addEnchantment(Enchantments.UNBREAKING, 3);
		customblade.addEnchantment(Enchantments.SHARPNESS, 3);

		SlashBlade.registerCustomItemStack(name, customblade);
		ItemSlashBladeNamed.NamedBlades.add(name);
		NamedBladeManager.registerBladeSoul(tag, "kineticenergyblade_final");
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
					"CYC",
					"XBX",
					"CLC",
					Character.valueOf('B'), custombladeReqired,
					Character.valueOf('C'), IC2Items.getItem("crafting", "iridium"),
					Character.valueOf('X'), IC2Items.getItem("te", "manual_kinetic_generator"),
					Character.valueOf('Y'), ItemCraftingManager.super_circuit,
					Character.valueOf('L'), ItemManager.getAllTypeStack(IC2Items.getItem("lapotron_crystal"))
			};

			if (Loader.isModLoaded("lastsmith")) {
				SlashBlade.addRecipe(name, new RecipeAwakeBladeTLS(new ResourceLocation(SlashBlade.modid, name),
						"sharpness", mrblade_need, custombladeReqired, recipetable));
			} else {
				SlashBlade.addRecipe(name, new RecipeAwakeBlade(new ResourceLocation(SlashBlade.modid, name),
						mrblade_need, custombladeReqired, recipetable));
			}
		}
////////////////////////////////////////////////////////////////////////////////////////////////////
	}
}