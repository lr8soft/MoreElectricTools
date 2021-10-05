package net.lrsoft.mets.item.blade;

import ic2.api.item.IC2Items;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.named.event.LoadEvent.PostInitEvent;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.lrsoft.mets.blade.SASpear;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BladeFoxBasic {
	public static String strBladeWhite = "flammpfeil.slashblade.named.electric_fox_white";
	public static String strBladeBlack = "flammpfeil.slashblade.named.electric_fox_black";
	@SubscribeEvent
	public void init(InitEvent event) {
		{
			ItemStack customblade = new ItemStack(BladeManager.fox_white, 1, 0);  
			NBTTagCompound tag = new NBTTagCompound();// NBT
			customblade.setTagCompound(tag);
			ItemSlashBladeNamed.CurrentItemName.set(tag, strBladeWhite);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(160));
			ItemSlashBlade.setBaseAttackModifier(tag, 16f);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
			ItemSlashBlade.TextureName.set(tag, "named/sange/white");
			ItemSlashBlade.ModelName.set(tag, "named/sange/sange");

			ItemSlashBlade.SpecialAttackType.set(tag, 0);
			ItemSlashBlade.StandbyRenderType.set(tag, Integer.valueOf(1));

			SlashBlade.registerCustomItemStack(strBladeWhite, customblade);
			ItemSlashBladeNamed.NamedBlades.add(strBladeWhite);
			customblade.addEnchantment(Enchantments.POWER, 1);
	     
			NamedBladeManager.registerBladeSoul(tag, "electric_fox_white");
		}
		{
			ItemStack customblade = new ItemStack(BladeManager.fox_black, 1, 0);  
			NBTTagCompound tag = new NBTTagCompound();// NBT
			customblade.setTagCompound(tag);
			ItemSlashBladeNamed.CurrentItemName.set(tag, strBladeBlack);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(160));
			ItemSlashBlade.setBaseAttackModifier(tag, 16f);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
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

        		SlashBlade.addRecipe(strBladeWhite, new RecipeAwakeBlade(new ResourceLocation("flammpfeil.slashblade", strBladeWhite), foxex,
        				custombladeReqired,
        				new Object[] { 
        						"XAX", "XBX", "CAC", 
        						Character.valueOf('X'), IC2Items.getItem("casing", "gold"),
        						Character.valueOf('A'), IC2Items.getItem("crafting", "circuit"),
        						Character.valueOf('B'), custombladeReqired, 
        						Character.valueOf('C'), ItemManager.getAllTypeStack(ItemManager.lithiumBattery) 
        				}));
        	}
        	{
        		ItemStack custombladeReqired = new ItemStack(SlashBlade.weapon);
        		NBTTagCompound reqTag = ItemSlashBladeNamed.getItemTagCompound(custombladeReqired);
        		ItemSlashBlade.KillCount.set(reqTag, Integer.valueOf(70));
        		ItemStack foxex = SlashBlade.findItemStack("flammpfeil.slashblade", strBladeBlack, 1);

        		SlashBlade.addRecipe(strBladeBlack, new RecipeAwakeBlade(new ResourceLocation("flammpfeil.slashblade", strBladeBlack), foxex,
        				custombladeReqired,
        				new Object[] { 
        						"XAX", "XBX", "CAC", 
        						Character.valueOf('X'), IC2Items.getItem("casing", "tin"),
        						Character.valueOf('A'), IC2Items.getItem("crafting", "circuit"),
        						Character.valueOf('B'), custombladeReqired, 
        						Character.valueOf('C'), ItemManager.getAllTypeStack(ItemManager.lithiumBattery) 
        				}));
        	}
    	}
    }
}
