package net.lrsoft.mets.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.event.TeBlockFinalCallEvent;
import ic2.api.item.ElectricItem;
import ic2.api.item.IC2Items;
import ic2.api.recipe.Recipes;
import ic2.api.tile.IEnergyStorage;
import ic2.core.IC2;
import ic2.core.block.BlockTileEntity;
import ic2.core.block.TeBlockRegistry;
import ic2.core.ref.TeBlock;
import ic2.core.util.StackUtil;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.MetsTeBlock;
import net.lrsoft.mets.block.UniformResourceBlock;
import net.lrsoft.mets.block.tileentity.IMets;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.item.reactor.ReactorItemManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import ic2.core.block.ITeBlock;
@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class BlockManager {
	public static Block niobiumOre;
	public static Block titaniumOre;
	public static Block titaniumBlock;
	static 
	{
		niobiumOre = new UniformResourceBlock("niobium_ore", 2.5f, 2);
		titaniumOre = new UniformResourceBlock("titanium_ore", 2.5f, 2);
		titaniumBlock = new UniformResourceBlock("titanium_block", Material.IRON, 5.0f, 1);
	}
	
	@SubscribeEvent
	public static void onTeBlockInit(TeBlockFinalCallEvent event)
	{
		TeBlockRegistry.addAll(MetsTeBlock.class, MetsTeBlock.loc);
		TeBlockRegistry.addCreativeRegisterer((list, block, itemblock, tab) -> {
			if (tab == CreativeTabs.SEARCH || tab == MoreElectricTools.CREATIVE_TAB) {
				block.getAllTypes().forEach(type -> {
					if (type.hasItem()) {
						list.add(block.getItemStack(type));
						if (type.getDummyTe() instanceof IEnergyStorage) {
							ItemStack filled = block.getItemStack(type);
							StackUtil.getOrCreateNbtData(filled).setDouble("energy",
									((IEnergyStorage) type.getDummyTe()).getCapacity());
							list.add(filled);
						}
					}
				});
			}
		}, MetsTeBlock.loc);
	}
	
	public static void onBlockRecipeInit()
	{
		BlockTileEntity teBlock = TeBlockRegistry.get(MetsTeBlock.loc);
		
		ItemStack lesuStack = teBlock.getItemStack(MetsTeBlock.lesu);
		Recipes.advRecipes.addRecipe(lesuStack, 
				new Object[] {
						"ALA",
						"BBB",
						"AAA",
						'A', IC2Items.getItem("plate", "steel"),
						'B', getAllTypeStack(ItemManager.advancedLithiumBattery),
						'L', IC2Items.getItem("cable", "type:gold,insulation:2")
				});
		
		ItemStack lootGenerator = teBlock.getItemStack(MetsTeBlock.drop_generator);
		Recipes.advRecipes.addRecipe(lootGenerator, 
				new Object[] {
						"BTB",
						"PAP",
						"PGP",
						'A', IC2Items.getItem("crafting", "advanced_circuit"),
						'T', getAllTypeStack(ItemManager.lithiumBattery),
						'P', IC2Items.getItem("casing", "steel"),
						'B', Items.BLAZE_ROD,
						'G', IC2Items.getItem("te", "generator")
				});
		
		ItemStack eesuStorage = teBlock.getItemStack(MetsTeBlock.eesu);
		Recipes.advRecipes.addRecipe(eesuStorage, 
				new Object[] {
						"BCB",
						"BAB",
						"BCB",
						'A', IC2Items.getItem("te", "mfsu"),
						'B', getAllTypeStack(ItemManager.superLapotronCrystal),
						'C', ItemCraftingManager.super_circuit
				});
		
		ItemStack advancedKineticGenerator = teBlock.getItemStack(MetsTeBlock.advanced_kinetic_generator);
		Recipes.advRecipes.addRecipe(advancedKineticGenerator, 
				new Object[] {
						"SSS",
						"GMX",
						"SSS",
						'S', ItemCraftingManager.titanium_plate,
						'G', IC2Items.getItem("te","kinetic_generator"),
						'M', ItemManager.titaniumIronAlloyRotor,
						'X', ItemCraftingManager.super_circuit
				});
		
		ItemStack superKineticGenerator = teBlock.getItemStack(MetsTeBlock.super_kinetic_generator);
		Recipes.advRecipes.addRecipe(superKineticGenerator, 
				new Object[] {
						"SES",
						"GMG",
						"SES",
						'S', IC2Items.getItem("crafting", "iridium"),
						'G', advancedKineticGenerator,
						'M', ItemManager.superIridiumRotor,
						'E', ReactorItemManager.advOCHeatVent
				});
		
		ItemStack advancedBlastFurnace = teBlock.getItemStack(MetsTeBlock.advanced_blast_furnace);
		Recipes.advRecipes.addRecipe(advancedBlastFurnace, 
				new Object[] {
						"SSS",
						"SMS",
						"SCS",
						'M', IC2Items.getItem("te", "blast_furnace"),
						'S', ItemCraftingManager.titanium_casing,
						'C', IC2Items.getItem("advanced_heat_exchanger")
				});
		
		
		ItemStack advancedMacerator = teBlock.getItemStack(MetsTeBlock.advanced_macerator);
		Recipes.advRecipes.addRecipe(advancedMacerator, 
				new Object[] {
						"DDD",
						"BMB",
						" C ",
						'M', IC2Items.getItem("te", "macerator"),
						'B', ItemCraftingManager.titanium_plate,
						'D', Items.DIAMOND,
						'C', ItemCraftingManager.super_circuit
				});
		
		ItemStack advancedCompressor = teBlock.getItemStack(MetsTeBlock.advanced_compressor);
		Recipes.advRecipes.addRecipe(advancedCompressor, 
				new Object[] {
						"B B",
						"BMB",
						"BCB",
						'M', IC2Items.getItem("te", "compressor"),
						'B', ItemCraftingManager.titanium_plate,
						'C', ItemCraftingManager.super_circuit
				});
		
		ItemStack charpad_eesu = teBlock.getItemStack(MetsTeBlock.chargepad_eesu);
		Recipes.advRecipes.addRecipe(charpad_eesu, 
				new Object[] {
						"CPC",
						"SMS",
						"   ",
						'S', IC2Items.getItem("crafting", "rubber"),
						'P', Item.getItemFromBlock(Blocks.STONE_PRESSURE_PLATE),
						'M', eesuStorage,
						'C', ItemCraftingManager.super_circuit
				});
		
		ItemStack charpad_lesu = teBlock.getItemStack(MetsTeBlock.chargepad_lesu);
		Recipes.advRecipes.addRecipe(charpad_lesu, 
				new Object[] {
						"CPC",
						"SMS",
						"   ",
						'S', IC2Items.getItem("crafting", "rubber"),
						'P', Item.getItemFromBlock(Blocks.STONE_PRESSURE_PLATE),
						'M', lesuStack,
						'C', IC2Items.getItem("crafting", "circuit")
				});
		
		ItemStack advanced_stirling_generator = teBlock.getItemStack(MetsTeBlock.advanced_stirling_generator);
		Recipes.advRecipes.addRecipe(advanced_stirling_generator, 
				new Object[] {
						"SPS",
						"SMS",
						"SSS",
						'S', ItemCraftingManager.titanium_casing,
						'P', IC2Items.getItem("advanced_heat_exchanger"),
						'M', IC2Items.getItem("te", "stirling_generator")
				});
		
		ItemStack advanced_semifluid_generator = teBlock.getItemStack(MetsTeBlock.advanced_semifluid_generator);
		Recipes.advRecipes.addRecipe(advanced_semifluid_generator, 
				new Object[] {
						"SPS",
						"MEM",
						"SCS",
						'S', ItemCraftingManager.titanium_casing,
						'C', ItemCraftingManager.super_circuit,
						'E', IC2Items.getItem("advanced_heat_exchanger"),
						'M', IC2Items.getItem("te", "semifluid_generator"),
						'P', IC2Items.getItem("upgrade", "fluid_pulling")
				});
		
		Recipes.compressor.addRecipe(
				Recipes.inputFactory.forOreDict("ingotTitanium", 9), null, false, new ItemStack(titaniumBlock));
		
		
		ItemStack extruding_machine = teBlock.getItemStack(MetsTeBlock.extruding_machine);
		Recipes.advRecipes.addRecipe(extruding_machine, 
				new Object[] {
						"SKS",
						"GMG",
						"SCS",
						'S', ItemCraftingManager.titanium_casing,
						'C', ItemCraftingManager.super_circuit,
						'M', IC2Items.getItem("te", "metal_former"),
						'K', IC2Items.getItem("cutter"),
						'G', IC2Items.getItem("crafting", "electric_motor")
				});
		ItemStack bending_machine = teBlock.getItemStack(MetsTeBlock.bending_machine);
		Recipes.advRecipes.addRecipe(bending_machine, 
				new Object[] {
						"SKS",
						"GMG",
						"SCS",
						'S', ItemCraftingManager.titanium_casing,
						'C', ItemCraftingManager.super_circuit,
						'M', IC2Items.getItem("te", "metal_former"),
						'K', IC2Items.getItem("forge_hammer"),
						'G', IC2Items.getItem("crafting", "electric_motor")
				});
		
		ItemStack cutting_machine = teBlock.getItemStack(MetsTeBlock.cutting_machine);
		Recipes.advRecipes.addRecipe(cutting_machine, 
				new Object[] {
						"SKS",
						"GMG",
						"SCS",
						'S', ItemCraftingManager.titanium_casing,
						'C', ItemCraftingManager.super_circuit,
						'M', IC2Items.getItem("te", "metal_former"),
						'K', IC2Items.getItem("block_cutting_blade", "diamond"),
						'G', IC2Items.getItem("crafting", "electric_motor")
				});
	}
	
	@SubscribeEvent
	public static void onCommonBlockInit(RegistryEvent.Register<Block> event) {
	    event.getRegistry().register(niobiumOre);
	    event.getRegistry().register(titaniumOre);
	    event.getRegistry().register(titaniumBlock);
	    //
	    //onCommonBlockItemInit();
	    //
	}
	
	@SubscribeEvent
	public static void onCommonBlockItemInit(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemBlock(niobiumOre).setRegistryName(niobiumOre.getRegistryName()));
		event.getRegistry().register(new ItemBlock(titaniumOre).setRegistryName(titaniumOre.getRegistryName()));
		event.getRegistry().register(new ItemBlock(titaniumBlock).setRegistryName(titaniumBlock.getRegistryName()));
	}
	
	private static ItemStack getAllTypeStack(ItemStack itemstack)
	{
		return new ItemStack(itemstack.getItem(), 1, OreDictionary.WILDCARD_VALUE);
	}
	
	private static ItemStack getAllTypeStack(Item item)
	{
		return new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);
	}
	
}
