package net.lrsoft.mets.item.bauble;

import ic2.api.item.IC2Items;
import ic2.api.recipe.Recipes;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.manager.ItemManager;
import net.lrsoft.mets.util.ItemStackUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class ItemBaublesManager {
	public static ElectricFireProofNecklace electricFireProofNecklace = new ElectricFireProofNecklace();
	public static EnergyCrystalBelt energyCrystalBelt = new EnergyCrystalBelt();
	@SubscribeEvent
	public static void onBaublesInit(RegistryEvent.Register<Item> event) 
	{
		event.getRegistry().register(electricFireProofNecklace);
		event.getRegistry().register(energyCrystalBelt);
		onRecipeInit();
	}
	
	
	private static void onRecipeInit() 
	{
		Recipes.advRecipes.addRecipe(new ItemStack(electricFireProofNecklace), 
				new Object[] {
						"SSS",
						"BCB",
						"LSL",
						'S', ItemCraftingManager.titanium_casing,
						'C', IC2Items.getItem("crafting", "advanced_circuit"),
						'L', Items.BLAZE_ROD,
						'B', ItemStackUtils.getAllTypeStack(ItemManager.lithiumBattery)
				});	
		
		Recipes.advRecipes.addRecipe(new ItemStack(energyCrystalBelt), 
				new Object[] {
						"PSP",
						"BCB",
						"PSP",
						'P', ItemCraftingManager.titanium_casing,
						'S', IC2Items.getItem("crafting", "advanced_circuit"),
						'C', ItemCraftingManager.titanium_plate,
						'B', ItemStackUtils.getAllTypeStack(IC2Items.getItem("energy_crystal"))
				});	
	}
	
	public static void onBaublesModelInit()
	{
		ModelLoader.setCustomModelResourceLocation(electricFireProofNecklace, 0,
				new ModelResourceLocation(electricFireProofNecklace.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(energyCrystalBelt, 0,
				new ModelResourceLocation(energyCrystalBelt.getRegistryName(), "inventory"));
	}
	
	
}
