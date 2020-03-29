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
import net.lrsoft.mets.block.LighterBlock;
import net.lrsoft.mets.block.MetsBlockWithTileEntity;
import net.lrsoft.mets.block.UniformResourceBlock;
import net.lrsoft.mets.block.tileentity.IMets;
import net.lrsoft.mets.block.tileentity.TileEntityLighterBlock;
import net.lrsoft.mets.crop.CropManager;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import ic2.core.block.ITeBlock;
@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class BlockManager {
	public static Block niobiumOre;
	public static Block titaniumOre;
	public static Block titaniumBlock;
	public static Block lighterBlock;
	static 
	{
		niobiumOre = new UniformResourceBlock("niobium_ore", 2.5f, 2);
		titaniumOre = new UniformResourceBlock("titanium_ore", 2.5f, 2);
		titaniumBlock = new UniformResourceBlock("titanium_block", Material.IRON, 5.0f, 1);
		lighterBlock = new LighterBlock();
	}
	
	@SubscribeEvent
	public static void onTeBlockInit(TeBlockFinalCallEvent event)
	{
		TeBlockRegistry.addAll(MetsBlockWithTileEntity.class, MetsBlockWithTileEntity.loc);
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
		}, MetsBlockWithTileEntity.loc);
	}
	
	public static void onBlockRecipeInit()
	{
		BlockTileEntity teBlock = TeBlockRegistry.get(MetsBlockWithTileEntity.loc);
		
		ItemStack lesuStack = teBlock.getItemStack(MetsBlockWithTileEntity.lesu);
		Recipes.advRecipes.addRecipe(lesuStack, 
				new Object[] {
						"ALA",
						"BBB",
						"AAA",
						'A', IC2Items.getItem("plate", "steel"),
						'B', getAllTypeStack(ItemManager.advancedLithiumBattery),
						'L', IC2Items.getItem("cable", "type:gold,insulation:2")
				});
		
		ItemStack lootGenerator = teBlock.getItemStack(MetsBlockWithTileEntity.drop_generator);
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
		
		ItemStack eesuStorage = teBlock.getItemStack(MetsBlockWithTileEntity.eesu);
		Recipes.advRecipes.addRecipe(eesuStorage, 
				new Object[] {
						"BCB",
						"BAB",
						"BCB",
						'A', IC2Items.getItem("te", "mfsu"),
						'B', getAllTypeStack(ItemManager.superLapotronCrystal),
						'C', ItemCraftingManager.super_circuit
				});
		
		if(ConfigManager.EnableMoreKineticGenerator)
		{
			ItemStack advancedKineticGenerator = teBlock.getItemStack(MetsBlockWithTileEntity.advanced_kinetic_generator);
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
			
			ItemStack superKineticGenerator = teBlock.getItemStack(MetsBlockWithTileEntity.super_kinetic_generator);
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
		}

		ItemStack advancedBlastFurnace = teBlock.getItemStack(MetsBlockWithTileEntity.advanced_blast_furnace);
		Recipes.advRecipes.addRecipe(advancedBlastFurnace, 
				new Object[] {
						"SSS",
						"SMS",
						"SCS",
						'M', IC2Items.getItem("te", "blast_furnace"),
						'S', ItemCraftingManager.titanium_casing,
						'C', IC2Items.getItem("advanced_heat_exchanger")
				});
		
		
		ItemStack advancedMacerator = teBlock.getItemStack(MetsBlockWithTileEntity.advanced_macerator);
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
		
		ItemStack advancedCompressor = teBlock.getItemStack(MetsBlockWithTileEntity.advanced_compressor);
		Recipes.advRecipes.addRecipe(advancedCompressor, 
				new Object[] {
						"B B",
						"BMB",
						"BCB",
						'M', IC2Items.getItem("te", "compressor"),
						'B', ItemCraftingManager.titanium_plate,
						'C', ItemCraftingManager.super_circuit
				});
		
		ItemStack charpad_eesu = teBlock.getItemStack(MetsBlockWithTileEntity.chargepad_eesu);
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
		
		ItemStack charpad_lesu = teBlock.getItemStack(MetsBlockWithTileEntity.chargepad_lesu);
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
		
		if(ConfigManager.EnableMoreStirlingGenerator)
		{
			ItemStack advanced_stirling_generator = teBlock.getItemStack(MetsBlockWithTileEntity.advanced_stirling_generator);
			Recipes.advRecipes.addRecipe(advanced_stirling_generator, 
					new Object[] {
							"SPS",
							"SMS",
							"SSS",
							'S', ItemCraftingManager.titanium_casing,
							'P', IC2Items.getItem("advanced_heat_exchanger"),
							'M', IC2Items.getItem("te", "stirling_generator")
					});			
		}
		
		ItemStack advanced_semifluid_generator = teBlock.getItemStack(MetsBlockWithTileEntity.advanced_semifluid_generator);
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
		
		
		ItemStack extruding_machine = teBlock.getItemStack(MetsBlockWithTileEntity.extruding_machine);
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
		ItemStack bending_machine = teBlock.getItemStack(MetsBlockWithTileEntity.bending_machine);
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
		
		ItemStack cutting_machine = teBlock.getItemStack(MetsBlockWithTileEntity.cutting_machine);
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
		
		ItemStack titanium_storage_box = teBlock.getItemStack(MetsBlockWithTileEntity.titanium_storage_box);
		Recipes.advRecipes.addRecipe(titanium_storage_box, 
				new Object[] {
						"PSP",
						"S S",
						"PSP",
						'S', ItemCraftingManager.titanium_casing,
						'P', ItemCraftingManager.titanium_plate
				});
		
		
		ItemStack experience_generator = teBlock.getItemStack(MetsBlockWithTileEntity.experience_generator);
		Recipes.advRecipes.addRecipe(experience_generator, 
				new Object[] {
						"SMS",
						"BCB",
						"SMS",
						'S', ItemCraftingManager.titanium_casing,
						'M', lootGenerator,
						'C', ItemCraftingManager.super_circuit,
						'B', getAllTypeStack(IC2Items.getItem("energy_crystal"))
				});
		
		ItemStack advanced_solar_generator = teBlock.getItemStack(MetsBlockWithTileEntity.advanced_solar_generator);
		Recipes.advRecipes.addRecipe(advanced_solar_generator, 
				new Object[] {
						"PPP",
						"GGG",
						"CBC",
						'P', IC2Items.getItem("crafting", "carbon_plate"),
						'G', IC2Items.getItem("te", "solar_generator"),
						'C', ItemCraftingManager.super_circuit,
						'B', getAllTypeStack(ItemManager.advancedLithiumBattery)
				});
		
		ItemStack photon_resonance_solar_generator = teBlock.getItemStack(MetsBlockWithTileEntity.photon_resonance_solar_generator);
		Recipes.advRecipes.addRecipe(photon_resonance_solar_generator, 
				new Object[] {
						"GGG",
						"PBP",
						"PCP",
						'P', ItemCraftingManager.super_iridium_compress_plate,
						'G', advanced_solar_generator,
						'C', ItemCraftingManager.super_circuit,
						'B', getAllTypeStack(ItemManager.superLapotronCrystal)
				});
		
		ItemStack wireless_power_transmission_node = teBlock.getItemStack(MetsBlockWithTileEntity.wireless_power_transmission_node);
		Recipes.advRecipes.addRecipe(wireless_power_transmission_node, 
				new Object[] {
						"PCP",
						"CTC",
						"SCS",
						'P', ItemCraftingManager.niobium_titanium_plate,
						'T', IC2Items.getItem("te", "hv_transformer"),
						'C', IC2Items.getItem("te", "tesla_coil"),
						'S', ItemCraftingManager.super_circuit
				});
	}
	
	@SubscribeEvent
	public static void onCommonBlockInit(RegistryEvent.Register<Block> event) {
	    event.getRegistry().register(niobiumOre);
	    event.getRegistry().register(titaniumOre);
	    event.getRegistry().register(titaniumBlock);
	    
	    event.getRegistry().register(lighterBlock);
	    GameRegistry.registerTileEntity(TileEntityLighterBlock.class,
	    		new ResourceLocation(MoreElectricTools.MODID, "lighter_block"));
	    CropManager.onBlockInit(event);
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
