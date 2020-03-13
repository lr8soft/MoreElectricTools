package net.lrsoft.mets.item.crafting;

import java.util.List;

import ic2.api.item.IC2Items;
import ic2.api.recipe.Recipes;
import ic2.core.init.Localization;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.manager.ItemManager;
import net.lrsoft.mets.util.ItemStackUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ItemCraftingManager {
	public static Item niobium_crushed;
	public static Item titanium_crushed;
	
	public static Item niobium_dust;
	public static Item titanium_dust;
	public static Item titanium_ingot;
	public static Item titanium_plate;
	public static Item titanium_casing;
	public static Item titanium_shaft;
	
	public static Item niobium_titanium_dust;
	public static Item niobium_titanium_ingot;
	public static Item niobium_titanium_plate;
	
	public static Item thorium_pile;
	public static Item thorium_dust;
	
	public static Item lens;
	public static Item diamond_lens;
	public static Item rocket;
	
	public static Item superconducting_cable;
	public static Item super_circuit;
	
	public static Item titanium_iron_rotor_blade;
	public static Item super_iridium_blade;
	public static Item super_iridium_compress_plate;
	static 
	{
		niobium_crushed = new UniformCraftingItem("niobium_crushed", 64);
		niobium_dust = new UniformCraftingItem("niobium_dust", 64);
		
		titanium_crushed = new UniformCraftingItem("titanium_crushed", 64);
		titanium_dust = new UniformCraftingItem("titanium_dust", 64);
		titanium_ingot = new UniformCraftingItem("titanium_ingot", 64);
		titanium_plate = new UniformCraftingItem("titanium_plate", 64);
		titanium_casing = new UniformCraftingItem("titanium_casing", 64);
		titanium_shaft = new UniformCraftingItem("titanium_shaft", 64);
		
		thorium_pile = new UniformCraftingItem("thorium_pile", 64);
		thorium_dust = new UniformCraftingItem("thorium_dust", 64);
		
		lens = new UniformCraftingItem("lens", 64);
		diamond_lens = new UniformCraftingItem("diamond_lens", 64);
		rocket = new UniformCraftingItem("rocket", 64);
		
		niobium_titanium_dust = new UniformCraftingItem("niobium_titanium_dust", 64);	
		niobium_titanium_ingot = new UniformCraftingItem("niobium_titanium_ingot", 64);
		niobium_titanium_plate = new UniformCraftingItem("niobium_titanium_plate", 64);
		
		superconducting_cable = new UniformCraftingItem("superconducting_cable", 64) {
			@Override
			public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
				String info = Localization.translate("mets.info.crafting_only");
				tooltip.add(info);
			}
		};
		
		super_circuit = new UniformCraftingItem("super_circuit", 64);
		titanium_iron_rotor_blade = new UniformCraftingItem("titanium_iron_rotor_blade", 64);
		super_iridium_blade = new UniformCraftingItem("super_iridium_blade", 64);
		super_iridium_compress_plate = new UniformCraftingItem("super_iridium_compress_plate", 64);
	}
	
	public static void onCraftingItemInit(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(niobium_crushed);
		event.getRegistry().register(niobium_dust);
		
		event.getRegistry().register(titanium_crushed);
		event.getRegistry().register(titanium_dust);
		event.getRegistry().register(titanium_ingot);
		event.getRegistry().register(titanium_plate);
		event.getRegistry().register(titanium_casing);
		event.getRegistry().register(titanium_shaft);
		
		event.getRegistry().register(thorium_pile);
		event.getRegistry().register(thorium_dust);
		
		event.getRegistry().register(niobium_titanium_dust);
		event.getRegistry().register(niobium_titanium_ingot);
		event.getRegistry().register(niobium_titanium_plate);
		
		event.getRegistry().register(lens);
		event.getRegistry().register(diamond_lens);
		event.getRegistry().register(rocket);
		
		event.getRegistry().register(superconducting_cable);
		event.getRegistry().register(super_circuit);
		event.getRegistry().register(titanium_iron_rotor_blade);
		event.getRegistry().register(super_iridium_blade);
		event.getRegistry().register(super_iridium_compress_plate);
	}
	
	public static void onCraftingItemModelInit()
	{
		ModelLoader.setCustomModelResourceLocation(niobium_crushed, 0,
				new ModelResourceLocation(niobium_crushed.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(niobium_dust, 0,
				new ModelResourceLocation(niobium_dust.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(titanium_crushed, 0,
				new ModelResourceLocation(titanium_crushed.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(titanium_dust, 0,
				new ModelResourceLocation(titanium_dust.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(titanium_ingot, 0,
				new ModelResourceLocation(titanium_ingot.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(titanium_plate, 0,
				new ModelResourceLocation(titanium_plate.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(titanium_casing, 0,
				new ModelResourceLocation(titanium_casing.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(titanium_shaft, 0,
				new ModelResourceLocation(titanium_shaft.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(thorium_pile, 0,
				new ModelResourceLocation(thorium_pile.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(thorium_dust, 0,
				new ModelResourceLocation(thorium_dust.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(niobium_titanium_dust, 0,
				new ModelResourceLocation(niobium_titanium_dust.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(niobium_titanium_ingot, 0,
				new ModelResourceLocation(niobium_titanium_ingot.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(niobium_titanium_plate, 0,
				new ModelResourceLocation(niobium_titanium_plate.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(lens, 0,
				new ModelResourceLocation(lens.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(diamond_lens, 0,
				new ModelResourceLocation(diamond_lens.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(rocket, 0,
				new ModelResourceLocation(rocket.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(superconducting_cable, 0,
				new ModelResourceLocation(superconducting_cable.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(super_circuit, 0,
				new ModelResourceLocation(super_circuit.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(titanium_iron_rotor_blade, 0,
				new ModelResourceLocation(titanium_iron_rotor_blade.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(super_iridium_blade, 0,
				new ModelResourceLocation(super_iridium_blade.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(super_iridium_compress_plate, 0,
				new ModelResourceLocation(super_iridium_compress_plate.getRegistryName(), "inventory"));
	}
	
	public static void onCraftingItemOreDictInit()
	{
		OreDictionary.registerOre("crushedNiobium", niobium_crushed);
		OreDictionary.registerOre("dustNiobium", niobium_dust);
		
		OreDictionary.registerOre("crushedTitanium", titanium_crushed);
		OreDictionary.registerOre("dustTitanium", titanium_dust);
		OreDictionary.registerOre("ingotTitanium", titanium_ingot);
		OreDictionary.registerOre("plateTitanium", titanium_plate);
		OreDictionary.registerOre("casingTitanium", titanium_casing);
		OreDictionary.registerOre("shaftTitanium", titanium_shaft);
		
		OreDictionary.registerOre("pileThorium", thorium_pile);
		OreDictionary.registerOre("dustThorium", thorium_dust);
		
		OreDictionary.registerOre("dustNiobiumTitanium", niobium_titanium_dust);
		OreDictionary.registerOre("ingotNiobiumTitanium", niobium_titanium_ingot);
		OreDictionary.registerOre("plateNiobiumTitanium", niobium_titanium_plate);
		
		OreDictionary.registerOre("cableSuperconducting", superconducting_cable);
		OreDictionary.registerOre("superCircuit", super_circuit);
	}
	
	public static void onCraftingItemRecipeInit()
	{
		NBTTagCompound heat=new NBTTagCompound();
		heat.setInteger("minHeat", 3500);
		
		NBTTagCompound tiblastfurnace = new NBTTagCompound();
		tiblastfurnace.setInteger("fluid", 1);
		tiblastfurnace.setInteger("duration", 750);
		//Nb(Th source)
		Recipes.macerator.addRecipe(Recipes.inputFactory.forOreDict("oreNiobium"), null, false, new ItemStack(niobium_crushed, 2));
		Recipes.centrifuge.addRecipe(Recipes.inputFactory.forOreDict("crushedNiobium"), heat, false,
				new ItemStack[] {new ItemStack(niobium_dust), new ItemStack(thorium_pile), IC2Items.getItem("dust", "stone")});
		//Ti
		Recipes.macerator.addRecipe(Recipes.inputFactory.forOreDict("oreTitanium"), null, false, new ItemStack(titanium_crushed, 2));
		Recipes.centrifuge.addRecipe(Recipes.inputFactory.forOreDict("crushedTitanium"), heat, false,
				new ItemStack[] {new ItemStack(titanium_dust), IC2Items.getItem("dust", "stone")});
		Recipes.metalformerRolling.addRecipe(Recipes.inputFactory.forOreDict("ingotTitanium"),
				null, false, new ItemStack(titanium_plate));
		Recipes.blastfurnace.addRecipe(Recipes.inputFactory.forOreDict("dustTitanium"), tiblastfurnace, false, 
				new ItemStack[] {new ItemStack(titanium_ingot),IC2Items.getItem("misc_resource", "slag")});
		
		Recipes.metalformerRolling.addRecipe(Recipes.inputFactory.forOreDict("plateTitanium"),
				null, false,new ItemStack[] {new ItemStack(titanium_casing, 2)});
		Recipes.metalformerExtruding.addRecipe(Recipes.inputFactory.forOreDict("blockTitanium"),
				null, false, new ItemStack(titanium_shaft));
		//Nb_Ti
		Recipes.advRecipes.addShapelessRecipe(new ItemStack(niobium_titanium_dust),
				niobium_dust, titanium_dust, titanium_dust, titanium_dust);
		
		NBTTagCompound metablastfurnace = new NBTTagCompound();
		metablastfurnace.setInteger("fluid", 4);
		metablastfurnace.setInteger("duration", 1000);
		
		Recipes.blastfurnace.addRecipe(Recipes.inputFactory.forOreDict("dustNiobiumTitanium"), metablastfurnace, false, 
				new ItemStack[] {new ItemStack(niobium_titanium_ingot),IC2Items.getItem("misc_resource", "slag")});
		
		Recipes.metalformerRolling.addRecipe(Recipes.inputFactory.forOreDict("ingotNiobiumTitanium"),
				null, false, new ItemStack(niobium_titanium_plate));
		
		
		Recipes.advRecipes.addRecipe(new ItemStack(superconducting_cable, 2),
				new Object[] {
						"XXX",
						"STS",
						"XXX",
						'S', IC2Items.getItem("dust", "energium"),
						'T', IC2Items.getItem("cable", "type:glass,insulation:0"),
						'X', niobium_titanium_plate
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(super_circuit),
				new Object[] {
						"XXX",
						"STS",
						"XXX",
						'S', IC2Items.getItem("crafting", "advanced_circuit"),
						'T', niobium_titanium_plate,
						'X', superconducting_cable
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(titanium_iron_rotor_blade), 
				new Object[] {
						"ASA",
						"ASA",
						"ASA",
						'A', IC2Items.getItem("plate", "steel"),
						'S', titanium_plate
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(super_iridium_blade), 
				new Object[] {
						"ASA",
						"ASA",
						"ASA",
						'S', IC2Items.getItem("crafting", "iridium"),
						'A', niobium_titanium_plate
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(lens), 
				new Object[] {
						"GFG",
						"FRF",
						"GFG",
						'F', IC2Items.getItem("plate", "iron"),
						'G', Item.getItemFromBlock(Blocks.GLASS),
						'R', IC2Items.getItem("glass", "reinforced")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(diamond_lens), 
				new Object[] {
						"GFG",
						"FRF",
						"GFG",
						'F', niobium_titanium_plate,
						'G', IC2Items.getItem("glass", "reinforced"),
						'R', Items.DIAMOND
				});
		

		Recipes.advRecipes.addRecipe(new ItemStack(rocket, 3),
				new Object[] {
						"TST",
						"SBS", 
						"YYY", 
						'T', Item.getItemFromBlock(Blocks.STONE_PRESSURE_PLATE), 
						'S', titanium_casing,
						'B', IC2Items.getItem("te", "itnt"),
						'Y', Items.FIREWORKS });	

		Recipes.compressor.addRecipe(Recipes.inputFactory.forStack(new ItemStack(super_iridium_blade, 3)), null, false, new ItemStack(super_iridium_compress_plate));
		
		
		//Th
		Recipes.advRecipes.addShapelessRecipe(new ItemStack(thorium_dust),
				thorium_pile, thorium_pile, thorium_pile, thorium_pile);
		
	}
}
