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
import net.lrsoft.mets.blade.FoxBladeSE;
import net.lrsoft.mets.blade.SASlashDimension;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//灵钢刃「仙狐」
public class BladeFoxFaerie {
	public String name = "flammpfeil.slashblade.named.fox_faerie";
	public static ISpecialEffect BladeSpeicalEffect = SpecialEffects.register(new FoxBladeSE());

	@SubscribeEvent
	public void init(InitEvent event) {
	  ItemStack customblade = new ItemStack(BladeManager.fox_faerie, 1, 0);
	  ItemSlashBlade.specialAttacks.put(Integer.valueOf(12079), new SASlashDimension());
	  NBTTagCompound tag = new NBTTagCompound();//NBT
	  customblade.setTagCompound(tag);
	  ItemSlashBladeNamed.CurrentItemName.set(tag, name);
	  ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(540));
	  ItemSlashBlade.setBaseAttackModifier(tag, 23f);
	  ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(true));
	   if (Loader.isModLoaded("lastsmith")) {
		   BladeUtil.getInstance().IsBewitchedActived.set(tag, true);
	   }
	  ItemSlashBlade.TextureName.set(tag, "named/foxextra/bladeFaerie");
	  ItemSlashBlade.ModelName.set(tag, "named/foxextra/model");
	  ItemSlashBlade.SpecialAttackType.set(tag, Integer.valueOf(12079));
	  ItemSlashBlade.StandbyRenderType.set(tag, Integer.valueOf(1));
	  ItemSlashBlade.SummonedSwordColor.set(tag, 16711935);

	  SpecialEffects.addEffect(customblade, BladeSpeicalEffect.getEffectKey(), BladeSpeicalEffect.getDefaultRequiredLevel());

	  SlashBlade.registerCustomItemStack(name, customblade);
	  ItemSlashBladeNamed.NamedBlades.add(name);
	  customblade.addEnchantment(Enchantments.POWER, 3);
	  customblade.addEnchantment(Enchantments.UNBREAKING, 3);
	  customblade.addEnchantment(Enchantments.LOOTING, 3);
	  customblade.addEnchantment(Enchantments.SHARPNESS, 6);
	  NamedBladeManager.registerBladeSoul(tag, "foxfaerie");
	}
	   
	@SubscribeEvent
	public void postInit(PostInitEvent event) {
		if(ConfigManager.EnableEUSlashBladeRecipe)
		{
			ItemStack custombladeReqired = SlashBlade.findItemStack(SlashBlade.modid, BladeFoxBasic.strBladeWhite, 1);
			NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(custombladeReqired);
			ItemSlashBlade.KillCount.set(reqTag, Integer.valueOf(600));
			ItemSlashBlade.ProudSoul.set(reqTag, Integer.valueOf(6000));
			ItemSlashBlade.RepairCount.set(reqTag, Integer.valueOf(6));
			ItemSlashBladeNamed.IsDefaultBewitched.set(reqTag, Boolean.valueOf(true));
			ItemStack foxex = SlashBlade.findItemStack("flammpfeil.slashblade", name, 1);

			Object[] recipetable = new Object[] {
					"CYC",
					"XBX",
					"CLC",
					Character.valueOf('X'), Blocks.DIAMOND_BLOCK,
					Character.valueOf('Y'), IC2Items.getItem("crafting", "advanced_circuit"),
					Character.valueOf('L'), ItemManager.getAllTypeStack(IC2Items.getItem("energy_crystal")),
					Character.valueOf('B'), custombladeReqired,
					Character.valueOf('C'), ItemCraftingManager.niobium_titanium_plate
			};

			if (Loader.isModLoaded("lastsmith")){
			  SlashBlade.addRecipe(name, new RecipeAwakeBladeTLS(new ResourceLocation(SlashBlade.modid, name),
					  "sharpness", foxex, custombladeReqired, recipetable));
			} else {
			  SlashBlade.addRecipe(name, new RecipeAwakeBlade(new ResourceLocation(SlashBlade.modid, name),
					  foxex, custombladeReqired, recipetable));
			}
		}
	}
}
