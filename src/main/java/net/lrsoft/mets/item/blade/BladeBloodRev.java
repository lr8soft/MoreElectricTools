package net.lrsoft.mets.item.blade;

import cn.mmf.lastsmith.recipe.RecipeAwakeBladeTLS;
import cn.mmf.lastsmith.util.BladeUtil;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.named.event.LoadEvent.PostInitEvent;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.lrsoft.mets.blade.BloodRevSE;
import net.lrsoft.mets.blade.SABloodRev;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//神钢刃「血腥革新」
public class BladeBloodRev {
	public static ItemSlashBladeNamed bladeNamed;
	public static ISpecialEffect customSE=SpecialEffects.register(new BloodRevSE());
	public static String name = "flammpfeil.slashblade.named.bloodrev_extra";//

	@SubscribeEvent
	public void init(InitEvent event) {
		ItemStack customblade = new ItemStack(BladeManager.bloodrev_extra, 1, 0);
		//ItemStack customblade = new ItemStack(SlashBlade.bladeNamed, 1, 0);

///////////////////////////////////////////////////////////////////////////////////////////
		ItemSlashBlade.specialAttacks.put(Integer.valueOf(13248569), new SABloodRev());
		NBTTagCompound tag = new NBTTagCompound();//NBT
		customblade.setTagCompound(tag);
		ItemSlashBladeNamed.CurrentItemName.set(tag, name);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(1280));//640
		ItemSlashBladeNamed.setBaseAttackModifier(tag, 1024);//
		ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(true));//
		if (Loader.isModLoaded("lastsmith")) {
			BladeUtil.getInstance().IsBewitchedActived.set(tag, true);
		}
		ItemSlashBladeNamed.TextureName.set(tag, "named/mrblade/texture_blood");
		ItemSlashBladeNamed.ModelName.set(tag, "named/mrblade/advanced");
		ItemSlashBladeNamed.SpecialAttackType.set(tag, Integer.valueOf(13248569));//saex
		ItemSlashBladeNamed.StandbyRenderType.set(tag, Integer.valueOf(2));//
		ItemSlashBladeNamed.SummonedSwordColor.set(tag, 12326679);
		SpecialEffects.addEffect(customblade, customSE);
		customblade.addEnchantment(Enchantments.POWER, 12);//
		customblade.addEnchantment(Enchantments.UNBREAKING, 12);
		customblade.addEnchantment(Enchantments.SHARPNESS, 12);
		customblade.addEnchantment(Enchantments.LOOTING, 3);

		SlashBlade.registerCustomItemStack(name, customblade);
		ItemSlashBladeNamed.NamedBlades.add(name);
		NamedBladeManager.registerBladeSoul(tag, "bloodrev_extra");
/////////////////////////////////////////////////////////////////////////////////////////////
		}

	@SubscribeEvent
	public void postInit(PostInitEvent event) {
		ItemStack bladewecreate = SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);
		ItemStack custombladeReqired = SlashBlade.findItemStack("flammpfeil.slashblade", BladeTechRevTheme.name, 1);
		NBTTagCompound reqTag = ItemSlashBladeNamed.getItemTagCompound(custombladeReqired);
		ItemSlashBlade.KillCount.set(reqTag, Integer.valueOf(1500));
		ItemSlashBlade.ProudSoul.set(reqTag, Integer.valueOf(5000));
		ItemSlashBlade.RepairCount.set(reqTag, Integer.valueOf(10));
		//custombladeReqired.removeSubCompound("Enchantment");
		ItemStack mrblade_need = SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);

		if (ConfigManager.EnableEUSlashBladeRecipe) {
			Object[] recipetable = new Object[] {
					"XXC",
					"YBY",
					"CZZ",
					Character.valueOf('B'), custombladeReqired,
					Character.valueOf('X'), ItemCraftingManager.super_iridium_compress_plate,
					Character.valueOf('Z'), ItemCraftingManager.nano_living_metal,//ItemManager.getAllTypeStack(ItemManager),
					Character.valueOf('Y'), ItemManager.getAllTypeStack(ItemManager.superLapotronCrystal),
					Character.valueOf('C'), Items.NETHER_STAR
			};

			if (Loader.isModLoaded("lastsmith")) {

				SlashBlade.addRecipe(name, new RecipeAwakeBladeTLS(new ResourceLocation(SlashBlade.modid,name),
						"final_blade", bladewecreate, custombladeReqired, recipetable));
			} else {
				SlashBlade.addRecipe(name, new RecipeAwakeBlade(new ResourceLocation(SlashBlade.modid,name),
						bladewecreate, custombladeReqired, recipetable));
			}
////////////////////////////////////////////////////////////////////////////////////////////////////	      
		}
	}
}