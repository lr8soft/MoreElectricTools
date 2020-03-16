package net.lrsoft.mets.manager;

import ic2.api.event.TeBlockFinalCallEvent;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.enchantment.EfficientEnergyCost;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class EnchantmentManager {
	public static EfficientEnergyCost efficientEu = new EfficientEnergyCost();
	@SubscribeEvent
	public static void onEnchantmentInit(RegistryEvent.Register<Enchantment> event)
	{
		event.getRegistry().register(efficientEu);
	}
}
