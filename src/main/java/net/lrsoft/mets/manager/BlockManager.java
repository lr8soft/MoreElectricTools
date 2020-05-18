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
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
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
import scala.reflect.api.Trees.NewApi;
import ic2.core.block.ITeBlock;
@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class BlockManager {
	public static Block niobiumOre;
	public static Block titaniumOre;
	public static Block titaniumBlock;
	public static Block lighterBlock;
	
	public static Block geomagneticPedestal;
	public static Block geomagneticAntenna;
	
	public static Block titaniumScaffold;
	static 
	{
		niobiumOre = new UniformResourceBlock("niobium_ore", 2.5f, 2);
		titaniumOre = new UniformResourceBlock("titanium_ore", 2.5f, 2);
		titaniumBlock = new UniformResourceBlock("titanium_block", Material.IRON, 5.0f, 1);
		lighterBlock = new LighterBlock();
		
		geomagneticPedestal = new UniformResourceBlock("geomagnetic_pedestal", 2.5f, -1);
		geomagneticAntenna = new UniformResourceBlock("geomagnetic_antenna", 2.5f, -1);
		
		titaniumScaffold = new UniformResourceBlock("titanium_scaffold",  Material.GLASS, SoundType.METAL ,5.0f, 1) {
			@Override
			public boolean isOpaqueCube(IBlockState state) {
				return false;
			}
			
			@Override
		    public BlockRenderLayer getBlockLayer()
		    {
		        return BlockRenderLayer.CUTOUT;
		    }
			
			@Override
		    public boolean isFullCube(IBlockState state)
		    {
		        return false;
		    }
		};
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
		
		//if(ConfigManager.EnableMoreKineticGenerator)
		//{
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
		//}

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
						"STS",
						"BCB",
						"STS",
						'T', IC2Items.getItem("te", "hv_transformer"),
						'C', IC2Items.getItem("te", "tesla_coil"),
						'B', getAllTypeStack(ItemManager.superLapotronCrystal),
						'S', ItemCraftingManager.super_circuit
				});
		
		ItemStack ultimate_solar_generator = teBlock.getItemStack(MetsBlockWithTileEntity.ultimate_photon_resonance_solar_generator);
		Recipes.advRecipes.addRecipe(ultimate_solar_generator, 
				new Object[] {
						"PGP",
						"GBG",
						"PGP",
						'P', ItemCraftingManager.super_iridium_compress_plate,
						'G', photon_resonance_solar_generator,
						'B', getAllTypeStack(ItemManager.chargingSuperLapotronCrystal)
				});
		
		
		
		//
		ItemStack geomagnetic_antenna = new ItemStack(Item.getItemFromBlock(geomagneticAntenna));
		Recipes.advRecipes.addRecipe(geomagnetic_antenna, 
				new Object[] {
						"SMS",
						"SCS",
						"SMS",
						'S', ItemCraftingManager.superconducting_cable,
						'M', ItemCraftingManager.nano_living_metal,
						'C', IC2Items.getItem("te", "tesla_coil")
				});
		
		ItemStack geomagnetic_pedestal = new ItemStack(Item.getItemFromBlock(geomagneticPedestal));
		Recipes.advRecipes.addRecipe(geomagnetic_pedestal, 
				new Object[] {
						"GGG",
						"SCS",
						"TTT",
						'T', titaniumBlock,
						'S', IC2Items.getItem("te", "geo_generator"),
						'C', ItemCraftingManager.living_circuit,
						'G', ItemCraftingManager.field_generator
				});
		
		ItemStack geomagnetic_generator = teBlock.getItemStack(MetsBlockWithTileEntity.geomagnetic_generator);
		Recipes.advRecipes.addRecipe(geomagnetic_generator, 
				new Object[] {
						"ABA",
						"SGS",
						"ACA",
						'A', ItemCraftingManager.nano_living_metal,
						'S', ItemCraftingManager.living_circuit,
						'G', superKineticGenerator,
						'B', eesuStorage,
						'C', geomagnetic_antenna
				});
		
		ItemStack neutron_polymerizer = teBlock.getItemStack(MetsBlockWithTileEntity.neutron_polymerizer);
		Recipes.advRecipes.addRecipe(neutron_polymerizer, 
				new Object[] {
						"FSF",
						"RBM",
						"FSF",
						'F', ItemCraftingManager.field_generator,
						'S', ItemCraftingManager.living_circuit,
						'R', IC2Items.getItem("te", "replicator"),
						'M', IC2Items.getItem("te", "matter_generator"),
						'B', eesuStorage
				});
		
		ItemStack electric_blast_furnace = teBlock.getItemStack(MetsBlockWithTileEntity.electric_blast_furnace);
		Recipes.advRecipes.addRecipe(electric_blast_furnace, 
				new Object[] {
						"CFC",
						"SBR",
						"CFC",
						'F', ItemCraftingManager.field_generator,
						'C', ItemCraftingManager.neutron_plate, 
						'S', ItemCraftingManager.living_circuit,
						'B', advancedBlastFurnace,
						'R', IC2Items.getItem("te", "electric_heat_generator")
				});
		
		///gesu
		ItemStack gesu_input = teBlock.getItemStack(MetsBlockWithTileEntity.gesu_input);
		Recipes.advRecipes.addRecipe(gesu_input, 
				new Object[] {
						"CFC",
						"SBS",
						"PIP",
						'C', ItemCraftingManager.field_generator,
						'F', ItemCraftingManager.neutron_plate, 
						'S', ItemCraftingManager.living_circuit,
						'B', eesuStorage,
						'P', ItemCraftingManager.super_iridium_compress_plate,
						'I', IC2Items.getItem("te", "ev_transformer")
				});
		
		ItemStack gesu_output = teBlock.getItemStack(MetsBlockWithTileEntity.gesu_output);
		Recipes.advRecipes.addRecipe(gesu_output, 
				new Object[] {
						"PIP",
						"SBS",
						"CFC",
						'C', ItemCraftingManager.field_generator,
						'F', ItemCraftingManager.neutron_plate, 
						'S', ItemCraftingManager.living_circuit,
						'B', eesuStorage,
						'P', ItemCraftingManager.super_iridium_compress_plate,
						'I', IC2Items.getItem("te", "ev_transformer")
				});
		
	
		
		ItemStack gesu_core = teBlock.getItemStack(MetsBlockWithTileEntity.gesu_core);
		Recipes.advRecipes.addRecipe(gesu_core, 
				new Object[] {
						"CFC",
						"IBO",
						"CFC",
						'F', ItemCraftingManager.field_generator,
						'C', ItemCraftingManager.neutron_plate, 
						'I', gesu_input,
						'O', gesu_output,
						'B', eesuStorage
				});
		
		//transformer
		ItemStack transformer_iv = teBlock.getItemStack(MetsBlockWithTileEntity.transformer_iv);
		Recipes.advRecipes.addRecipe(transformer_iv, 
				new Object[] {
						" C ",
						"STB",
						" C ",
						'C', IC2Items.getItem("cable", "type:glass,insulation:0"),
						'S', ItemCraftingManager.super_circuit,
						'T', IC2Items.getItem("te", "ev_transformer"),
						'B', getAllTypeStack(ItemManager.superLapotronCrystal)
				});
		ItemStack transformer_luv = teBlock.getItemStack(MetsBlockWithTileEntity.transformer_luv);
		Recipes.advRecipes.addRecipe(transformer_luv, 
				new Object[] {
						" C ",
						"STB",
						" C ",
						'C', ItemCraftingManager.superconducting_cable,
						'S', ItemCraftingManager.living_circuit,
						'T', transformer_iv,
						'B', eesuStorage
				});
		
		ItemStack gesu_output_luv = teBlock.getItemStack(MetsBlockWithTileEntity.gesu_output_luv);
		Recipes.advRecipes.addRecipe(gesu_output_luv, 
				new Object[] {
						" B ",
						"IMT",
						" B ",
						'B', eesuStorage,
						'T', transformer_luv,
						'M', gesu_output,
						'I', ItemCraftingManager.living_circuit
				});
		
		//oil rig 
		ItemStack titanium_scaffold = new ItemStack(titaniumScaffold, 3);
		Recipes.advRecipes.addRecipe(titanium_scaffold, 
				new Object[] {
						"PPP",
						"TTT",
						"PPP",
						'P', ItemCraftingManager.titanium_plate,
						'T', titaniumBlock
				});
	}
	
	@SubscribeEvent
	public static void onCommonBlockInit(RegistryEvent.Register<Block> event) {
	    event.getRegistry().register(niobiumOre);
	    event.getRegistry().register(titaniumOre);
	    event.getRegistry().register(titaniumBlock);
	    
	    event.getRegistry().register(lighterBlock);
	    
	    event.getRegistry().register(geomagneticPedestal);
	    event.getRegistry().register(geomagneticAntenna);
	    
	    event.getRegistry().register(titaniumScaffold);
	    GameRegistry.registerTileEntity(TileEntityLighterBlock.class,
	    		new ResourceLocation(MoreElectricTools.MODID, "lighter_block"));
	    CropManager.onBlockInit(event);
	    FluidManager.onFluidBlockInit(event);
	    //
	    //onCommonBlockItemInit();
	    //
	}
	
	@SubscribeEvent
	public static void onCommonBlockItemInit(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemBlock(niobiumOre).setRegistryName(niobiumOre.getRegistryName()));
		event.getRegistry().register(new ItemBlock(titaniumOre).setRegistryName(titaniumOre.getRegistryName()));
		event.getRegistry().register(new ItemBlock(titaniumBlock).setRegistryName(titaniumBlock.getRegistryName()));
		event.getRegistry().register(new ItemBlock(titaniumScaffold).setRegistryName(titaniumScaffold.getRegistryName()));
		
		event.getRegistry().register(new ItemBlock(geomagneticPedestal).setRegistryName(geomagneticPedestal.getRegistryName()));
		event.getRegistry().register(new ItemBlock(geomagneticAntenna).setRegistryName(geomagneticAntenna.getRegistryName()));
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
