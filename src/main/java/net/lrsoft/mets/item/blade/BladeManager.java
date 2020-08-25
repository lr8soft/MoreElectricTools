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
	public static EUadvbasic hyperblade_second;
	public static Item kineticenergyblade_final;
	public static Item bloodrev_extra;
	public static Item craftrev_extra;
	
	public static void onBladeInit() {
		trblade_first = (Item)(new EUbasic(ToolMaterial.IRON, 7.0F)).setRegistryName("trblade_first");
		ForgeRegistries.ITEMS.register(trblade_first);

		/*hyperblade_second = new EUadvbasic(ToolMaterial.IRON, 15.0F);
		ForgeRegistries.ITEMS.register(hyperblade_second.setRegistryName("hyperblade_second"));

		mrblade_final = new EUcontroler(ToolMaterial.IRON, 25.0F);
		ForgeRegistries.ITEMS.register(mrblade_final.setRegistryName("mrblade_final"));

		kineticenergyblade_final = new EUkinetic_final(ToolMaterial.IRON, 20.0F);
		ForgeRegistries.ITEMS.register(kineticenergyblade_final.setRegistryName("kineticenergyblade_final"));

		bloodrev_extra = new EUrev_final(ToolMaterial.IRON, 75.0F);
		ForgeRegistries.ITEMS.register(bloodrev_extra.setRegistryName("bloodrev_extra"));

		craftrev_extra = new EUcraft_final(ToolMaterial.IRON, 75.0F);
		ForgeRegistries.ITEMS.register(craftrev_extra.setRegistryName("craftrev_extra"));*/

		SlashBlade.InitEventBus.register(new BladeBasic());
		SlashBlade.InitEventBus.register(new BladeAdvanced());
		SlashBlade.InitEventBus.register(new BladeTechRevTheme());
		SlashBlade.InitEventBus.register(new BladeKineticEnergy());
		SlashBlade.InitEventBus.register(new BladeBloodRev());
		SlashBlade.InitEventBus.register(new BladeProcessRev());
	}
}
