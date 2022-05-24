package net.lrsoft.mets.item.blade;

import cn.mmf.lastsmith.recipe.RecipeAwakeBladeTLS;
import ic2.api.item.IC2Items;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.named.event.LoadEvent.PostInitEvent;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BladeFoxBasic {
	public static String strBladeWhite = "flammpfeil.slashblade.named.electric_fox_white";
	public static String strBladeBlack = "flammpfeil.slashblade.named.electric_fox_black";

	@SubscribeEvent
	public void init(InitEvent event) {
		//钢刃「白狐」
		{
			ItemStack customblade = new ItemStack(BladeManager.fox_white, 1, 0);  
			NBTTagCompound tag = new NBTTagCompound();// NBT
			customblade.setTagCompound(tag);
			ItemSlashBladeNamed.CurrentItemName.set(tag, strBladeWhite);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(160));
			ItemSlashBlade.setBaseAttackModifier(tag, 16f);
			ItemSlashBlade.TextureName.set(tag, "named/sange/white");
			ItemSlashBlade.ModelName.set(tag, "named/sange/sange");

			ItemSlashBlade.SpecialAttackType.set(tag, 0);
			ItemSlashBlade.StandbyRenderType.set(tag, Integer.valueOf(1));

			SlashBlade.registerCustomItemStack(strBladeWhite, customblade);
			ItemSlashBladeNamed.NamedBlades.add(strBladeWhite);
			customblade.addEnchantment(Enchantments.POWER, 1);
	     
			NamedBladeManager.registerBladeSoul(tag, "electric_fox_white");
		}
		//钢刃「黑狐」
		{
			ItemStack customblade = new ItemStack(BladeManager.fox_black, 1, 0);  
			NBTTagCompound tag = new NBTTagCompound();// NBT
			customblade.setTagCompound(tag);
			ItemSlashBladeNamed.CurrentItemName.set(tag, strBladeBlack);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(160));
			ItemSlashBlade.setBaseAttackModifier(tag, 16f);
			ItemSlashBlade.TextureName.set(tag, "named/sange/black");
			ItemSlashBlade.ModelName.set(tag, "named/sange/sange");

			ItemSlashBlade.SpecialAttackType.set(tag, 4);
			ItemSlashBlade.StandbyRenderType.set(tag, Integer.valueOf(1));

			SlashBlade.registerCustomItemStack(strBladeBlack, customblade);
			ItemSlashBladeNamed.NamedBlades.add(strBladeBlack);
			customblade.addEnchantment(Enchantments.POWER, 1);
	     
			NamedBladeManager.registerBladeSoul(tag, "electric_fox_black");
		}
	}
	

    @SubscribeEvent
    public void postInit(PostInitEvent event){
    	if(ConfigManager.EnableEUSlashBladeRecipe){
        	{
        		ItemStack custombladeReqired = new ItemStack(SlashBlade.weapon);
        		NBTTagCompound reqTag = ItemSlashBladeNamed.getItemTagCompound(custombladeReqired);
        		ItemSlashBlade.KillCount.set(reqTag, Integer.valueOf(70));
        		ItemStack foxex = SlashBlade.findItemStack("flammpfeil.slashblade", strBladeWhite, 1);

        		Object[] recipetable_white = new Object[] {
						"XAX", "XBX", "CAC",
						Character.valueOf('X'), IC2Items.getItem("casing", "gold"),
						Character.valueOf('A'), IC2Items.getItem("crafting", "circuit"),
						Character.valueOf('B'), custombladeReqired,
						Character.valueOf('C'), ItemManager.getAllTypeStack(ItemManager.lithiumBattery)
				};

        		if (Loader.isModLoaded("lastsmith")){
					SlashBlade.addRecipe(strBladeWhite, new RecipeAwakeBladeTLS(new ResourceLocation("flammpfeil.slashblade", strBladeWhite),
							"bewitched_blade", foxex, custombladeReqired, recipetable_white));
				} else {
					SlashBlade.addRecipe(strBladeWhite, new RecipeAwakeBlade(new ResourceLocation("flammpfeil.slashblade", strBladeWhite),
							foxex, custombladeReqired, recipetable_white));
				}
        	}
        	{
        		ItemStack custombladeReqired = new ItemStack(SlashBlade.weapon);
        		NBTTagCompound reqTag = ItemSlashBladeNamed.getItemTagCompound(custombladeReqired);
        		ItemSlashBlade.KillCount.set(reqTag, Integer.valueOf(70));
        		ItemStack foxex = SlashBlade.findItemStack("flammpfeil.slashblade", strBladeBlack, 1);

				Object[] recipetable_black =new Object[] {
						"XAX", "XBX", "CAC",
						Character.valueOf('X'), IC2Items.getItem("casing", "tin"),
						Character.valueOf('A'), IC2Items.getItem("crafting", "circuit"),
						Character.valueOf('B'), custombladeReqired,
						Character.valueOf('C'), ItemManager.getAllTypeStack(ItemManager.lithiumBattery)
				};

				if (Loader.isModLoaded("lastsmith")){
					SlashBlade.addRecipe(strBladeBlack, new RecipeAwakeBladeTLS(new ResourceLocation("flammpfeil.slashblade", strBladeBlack),
							"bewitched_blade", foxex, custombladeReqired, recipetable_black));
				} else {
					SlashBlade.addRecipe(strBladeBlack, new RecipeAwakeBlade(new ResourceLocation("flammpfeil.slashblade", strBladeBlack),
							foxex, custombladeReqired, recipetable_black));
				}
        	}
    	}
    }
}
