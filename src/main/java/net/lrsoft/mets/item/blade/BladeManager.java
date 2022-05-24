package net.lrsoft.mets.item.blade;

import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BladeManager {
	public static Item mrblade_final;
	public static Item trblade_first;
	public static Item hyperblade_second;
	public static Item kineticenergyblade_final;
	public static Item bloodrev_extra;
	public static Item craftrev_extra;
	
	public static Item fox_white;
	public static Item fox_black;
	public static Item fox_faerie;
	public static Item fox_elf;
	
	public static void onBladeInit() {
		if (Loader.isModLoaded("lastsmith")) {
			trblade_first = (Item)(new ItemMETSNamedBladeTLS(30000, 128, 2, ToolMaterial.IRON, 7.0f)).setRegistryName("trblade_first");
			hyperblade_second = (Item)(new ItemMETSNamedBladeTLS(1000000, 512, 3, ToolMaterial.IRON, 7.0f)).setRegistryName("hyperblade_second");
			mrblade_final = (Item)(new ItemMETSNamedBladeTLS(5000000, 8192, 4, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("mrblade_final");
			kineticenergyblade_final = (Item)(new ItemMETSNamedBladeTLS(5000000, 8192, 4, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("kineticenergyblade_final");
			bloodrev_extra = (Item)(new ItemMETSNamedBladeTLS(100000000, 32768, 5, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("bloodrev_extra");
			craftrev_extra = (Item)(new ItemMETSNamedBladeTLS(100000000, 32768, 5, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("craftrev_extra");
			fox_white = (Item)(new ItemMETSNamedBladeTLS(50000, 256, 2, ToolMaterial.IRON, 12.0f)).setRegistryName("fox_white");
			fox_black = (Item)(new ItemMETSNamedBladeTLS(50000, 256, 2, ToolMaterial.IRON, 12.0f)).setRegistryName("fox_black");
			fox_faerie = (Item)(new ItemMETSNamedBladeTLS(2000000, 2048, 3, ToolMaterial.DIAMOND, 15.0f)).setRegistryName("fox_faerie");
			fox_elf = (Item)(new ItemMETSNamedBladeTLS(2000000, 2048, 3, ToolMaterial.DIAMOND, 15.0f)).setRegistryName("fox_elf");
		} else {
			trblade_first = (Item)(new ItemMETSNamedBlade(30000, 128, 2, ToolMaterial.IRON, 7.0f)).setRegistryName("trblade_first");
			hyperblade_second = (Item)(new ItemMETSNamedBlade(1000000, 512, 3, ToolMaterial.IRON, 7.0f)).setRegistryName("hyperblade_second");
			mrblade_final = (Item)(new ItemMETSNamedBlade(5000000, 8192, 4, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("mrblade_final");
			kineticenergyblade_final = (Item)(new ItemMETSNamedBlade(5000000, 8192, 4, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("kineticenergyblade_final");
			bloodrev_extra = (Item)(new ItemMETSNamedBlade(100000000, 32768, 5, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("bloodrev_extra");
			craftrev_extra = (Item)(new ItemMETSNamedBlade(100000000, 32768, 5, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("craftrev_extra");
			fox_white = (Item)(new ItemMETSNamedBlade(50000, 256, 2, ToolMaterial.IRON, 12.0f)).setRegistryName("fox_white");
			fox_black = (Item)(new ItemMETSNamedBlade(50000, 256, 2, ToolMaterial.IRON, 12.0f)).setRegistryName("fox_black");
			fox_faerie = (Item)(new ItemMETSNamedBlade(2000000, 2048, 3, ToolMaterial.DIAMOND, 15.0f)).setRegistryName("fox_faerie");
			fox_elf = (Item)(new ItemMETSNamedBlade(2000000, 2048, 3, ToolMaterial.DIAMOND, 15.0f)).setRegistryName("fox_elf");
		}

		ForgeRegistries.ITEMS.register(trblade_first);
		ForgeRegistries.ITEMS.register(hyperblade_second);
		ForgeRegistries.ITEMS.register(mrblade_final);
		ForgeRegistries.ITEMS.register(kineticenergyblade_final);
		ForgeRegistries.ITEMS.register(bloodrev_extra);
		ForgeRegistries.ITEMS.register(craftrev_extra);
		ForgeRegistries.ITEMS.register(fox_white);
		ForgeRegistries.ITEMS.register(fox_black);
		ForgeRegistries.ITEMS.register(fox_faerie);
		ForgeRegistries.ITEMS.register(fox_elf);

		SlashBlade.InitEventBus.register(new BladeBasic());
		SlashBlade.InitEventBus.register(new BladeAdvanced());
		SlashBlade.InitEventBus.register(new BladeTechRevTheme());
		SlashBlade.InitEventBus.register(new BladeKineticEnergy());
		SlashBlade.InitEventBus.register(new BladeBloodRev());
		SlashBlade.InitEventBus.register(new BladeProcessRev());
		SlashBlade.InitEventBus.register(new BladeFoxBasic());
		SlashBlade.InitEventBus.register(new BladeFoxElf());
		SlashBlade.InitEventBus.register(new BladeFoxFaerie());
	}
}
