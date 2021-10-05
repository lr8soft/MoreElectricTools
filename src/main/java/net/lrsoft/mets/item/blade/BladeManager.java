package net.lrsoft.mets.item.blade;

import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class BladeManager {
	public static Item mrblade_final;
	public static Item trblade_first;
	public static Item hyperblade_second;
	public static Item kineticenergyblade_final;
	public static Item bloodrev_extra;
	public static Item craftrev_extra;
	
	public static Item fox_faerie;
	public static Item fox_elf;
	
	public static void onBladeInit() {
		trblade_first = (Item)(new ItemMETSNamedBlade(30000, 128, 2, ToolMaterial.IRON, 7.0f)).setRegistryName("trblade_first");
		ForgeRegistries.ITEMS.register(trblade_first);
		
		hyperblade_second = (Item)(new ItemMETSNamedBlade(1000000, 512, 3, ToolMaterial.IRON, 7.0f)).setRegistryName("hyperblade_second");
		ForgeRegistries.ITEMS.register(hyperblade_second);

		mrblade_final = (Item)(new ItemMETSNamedBlade(5000000, 8192, 4, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("mrblade_final");
		ForgeRegistries.ITEMS.register(mrblade_final);
		
		kineticenergyblade_final = (Item)(new ItemMETSNamedBlade(5000000, 8192, 4, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("kineticenergyblade_final");
		ForgeRegistries.ITEMS.register(kineticenergyblade_final);
		
		bloodrev_extra = (Item)(new ItemMETSNamedBlade(100000000, 32768, 5, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("bloodrev_extra");
		ForgeRegistries.ITEMS.register(bloodrev_extra);
		
		craftrev_extra = (Item)(new ItemMETSNamedBlade(100000000, 32768, 5, ToolMaterial.DIAMOND, 20.0f)).setRegistryName("craftrev_extra");
		ForgeRegistries.ITEMS.register(craftrev_extra);
		
		fox_faerie = (Item)(new ItemMETSNamedBlade(2000000, 2048, 3, ToolMaterial.DIAMOND, 15.0f)).setRegistryName("fox_faerie");
		ForgeRegistries.ITEMS.register(fox_faerie);
		
		fox_elf = (Item)(new ItemMETSNamedBlade(2000000, 2048, 3, ToolMaterial.DIAMOND, 15.0f)).setRegistryName("fox_elf");
		ForgeRegistries.ITEMS.register(fox_elf);

		SlashBlade.InitEventBus.register(new BladeBasic());
		SlashBlade.InitEventBus.register(new BladeAdvanced());
		SlashBlade.InitEventBus.register(new BladeTechRevTheme());
		SlashBlade.InitEventBus.register(new BladeKineticEnergy());
		SlashBlade.InitEventBus.register(new BladeBloodRev());
		SlashBlade.InitEventBus.register(new BladeProcessRev());
		
		SlashBlade.InitEventBus.register(new BladeFoxElf());
		SlashBlade.InitEventBus.register(new BladeFoxFaerie());
	}
}
