package net.lrsoft.mets.manager;

import baubles.common.Baubles;
import ic2.api.item.IC2Items;
import ic2.api.recipe.Recipes;
import ic2.core.item.armor.jetpack.JetpackAttachmentRecipe;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.armor.AdvancedJetPack;
import net.lrsoft.mets.armor.AdvancedQuantumSuit;
import net.lrsoft.mets.armor.DivingMask;
import net.lrsoft.mets.armor.HeavyQuantumSuit;
import net.lrsoft.mets.crop.CropManager;
import net.lrsoft.mets.item.ElectricFirstAidLifeSupport;
import net.lrsoft.mets.item.ElectricFishingRod;
import net.lrsoft.mets.item.ElectricForceFieldGenerator;
import net.lrsoft.mets.item.ElectricLighter;
import net.lrsoft.mets.item.ElectricNutritionSupply;
import net.lrsoft.mets.item.ElectricWirlessManager;
import net.lrsoft.mets.item.GeomagneticDetector;
import net.lrsoft.mets.item.battery.AdvancedLithiumBattery;
import net.lrsoft.mets.item.battery.ChargingSuperLapotronCrystal;
import net.lrsoft.mets.item.battery.LithiumBattery;
import net.lrsoft.mets.item.battery.SuperLapotronCrystal;
import net.lrsoft.mets.item.battery.ThoriumBattery;
import net.lrsoft.mets.item.bauble.ItemBaublesManager;
import net.lrsoft.mets.item.blade.BladeManager;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.item.reactor.ReactorItemManager;
import net.lrsoft.mets.item.rotor.SuperIridiumRotor;
import net.lrsoft.mets.item.rotor.TitaniumIronAlloyRotor;
import net.lrsoft.mets.item.weapon.AdvancedElectricSubmachineGun;
import net.lrsoft.mets.item.weapon.AdvancedIridiumSword;
import net.lrsoft.mets.item.weapon.ElectricPlasmaGun;
import net.lrsoft.mets.item.weapon.ElectricRocketLauncher;
import net.lrsoft.mets.item.weapon.ElectricShield;
import net.lrsoft.mets.item.weapon.ElectricSubmachineGun;
import net.lrsoft.mets.item.weapon.NanoBow;
import net.lrsoft.mets.item.weapon.PlasmaAirCannon;
import net.lrsoft.mets.item.weapon.TachyonDisruptor;
import net.lrsoft.mets.item.weapon.TacticalLaserSubmachineGun;
import net.lrsoft.mets.util.SpecialRecipesHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class ItemManager {
	
	public static SuperLapotronCrystal superLapotronCrystal;
	public static ChargingSuperLapotronCrystal chargingSuperLapotronCrystal;
	public static AdvancedIridiumSword advancedIridiumSword;
	public static AdvancedLithiumBattery advancedLithiumBattery;
	public static LithiumBattery lithiumBattery;
	public static ThoriumBattery thoriumBattery;
	
	public static ElectricFirstAidLifeSupport electricFirstAidLifeSupport;
	public static ElectricNutritionSupply electricNutritionSupply;
	public static ElectricFishingRod electricFishingRod;
	public static ElectricShield electricShield;
	public static ElectricForceFieldGenerator electricForceFieldGenerator;
	public static ElectricLighter electricLighter;
	
	public static ElectricWirlessManager electricWirlessManager;
	public static GeomagneticDetector geomagneticDetector;
	
	public static NanoBow nanoBow;
	public static PlasmaAirCannon plasmaAirCannon;
	public static ElectricSubmachineGun electricSubmachineGun;
	public static AdvancedElectricSubmachineGun advancedElectricSubmachineGun;
	public static TacticalLaserSubmachineGun tacticalLaserSubmachineGun;
	public static ElectricRocketLauncher electricRocketLauncher;
	public static ElectricPlasmaGun electricPlasmaGun;
	public static TachyonDisruptor tachyonDisruptor;
	
	public static DivingMask divingMask;
	public static AdvancedQuantumSuit advancedQuantumChest;
	public static AdvancedJetPack advancedJetPack;
	public static HeavyQuantumSuit heavyQuantumChest;
	
	public static TitaniumIronAlloyRotor titaniumIronAlloyRotor;
	public static SuperIridiumRotor superIridiumRotor;
	
	static 
	{
		superLapotronCrystal = new SuperLapotronCrystal();
		chargingSuperLapotronCrystal = new ChargingSuperLapotronCrystal();
		advancedIridiumSword = new AdvancedIridiumSword();
		
		advancedLithiumBattery = new AdvancedLithiumBattery();
		lithiumBattery = new LithiumBattery();
		thoriumBattery = new ThoriumBattery();
		
		electricFirstAidLifeSupport = new ElectricFirstAidLifeSupport();
		electricNutritionSupply = new ElectricNutritionSupply();
		electricFishingRod = new ElectricFishingRod();
		electricShield = new ElectricShield();
		electricForceFieldGenerator = new ElectricForceFieldGenerator();
		electricWirlessManager = new ElectricWirlessManager();
		nanoBow = new NanoBow();
		plasmaAirCannon = new PlasmaAirCannon();	
		electricLighter = new ElectricLighter();
		geomagneticDetector = new GeomagneticDetector();
		
		electricSubmachineGun = new ElectricSubmachineGun();
		advancedElectricSubmachineGun = new AdvancedElectricSubmachineGun();
		tacticalLaserSubmachineGun = new TacticalLaserSubmachineGun();
		electricRocketLauncher = new ElectricRocketLauncher();
		electricPlasmaGun = new ElectricPlasmaGun();
		tachyonDisruptor = new TachyonDisruptor();
		
		divingMask = new DivingMask();	
		advancedQuantumChest = new AdvancedQuantumSuit("advanced_quantum_chest", EntityEquipmentSlot.CHEST);
		advancedJetPack = new AdvancedJetPack();
		heavyQuantumChest = new HeavyQuantumSuit("heavy_quantum_chest");
		
		titaniumIronAlloyRotor = new TitaniumIronAlloyRotor();
		superIridiumRotor = new SuperIridiumRotor();
	}
	
	@SubscribeEvent
	public static void onItemInit(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(superLapotronCrystal);
		event.getRegistry().register(chargingSuperLapotronCrystal);
		event.getRegistry().register(advancedIridiumSword);
		event.getRegistry().register(advancedLithiumBattery);
		event.getRegistry().register(lithiumBattery);
		event.getRegistry().register(thoriumBattery);
		event.getRegistry().register(electricFirstAidLifeSupport);
		event.getRegistry().register(electricNutritionSupply);
		event.getRegistry().register(electricFishingRod);
		event.getRegistry().register(electricShield);
		event.getRegistry().register(nanoBow);
		event.getRegistry().register(plasmaAirCannon);
		event.getRegistry().register(electricSubmachineGun);
		event.getRegistry().register(advancedElectricSubmachineGun);
		event.getRegistry().register(tacticalLaserSubmachineGun);
		event.getRegistry().register(electricRocketLauncher);
		event.getRegistry().register(electricForceFieldGenerator);
		event.getRegistry().register(electricLighter);
		event.getRegistry().register(geomagneticDetector);
		
		event.getRegistry().register(electricPlasmaGun);
		event.getRegistry().register(electricWirlessManager);
		event.getRegistry().register(tachyonDisruptor);
		
		event.getRegistry().register(divingMask);
		event.getRegistry().register(advancedQuantumChest);
		event.getRegistry().register(advancedJetPack);
		event.getRegistry().register(heavyQuantumChest);
		
		event.getRegistry().register(titaniumIronAlloyRotor);
		event.getRegistry().register(superIridiumRotor);
		
		OreDictionary.registerOre("superLapotronCrystal", superLapotronCrystal);
		OreDictionary.registerOre("advancedLithiumBattery", advancedLithiumBattery);
		OreDictionary.registerOre("lithiumBattery", lithiumBattery);
		
		ItemCraftingManager.onCraftingItemInit(event);
		ReactorItemManager.onItemInit(event);
		CropManager.onItemInit(event);
		
		onRecipeInit();
		ItemCraftingManager.onCraftingItemRecipeInit();
		ReactorItemManager.onItemRecipeInit();
		
		if (Loader.isModLoaded(Baubles.MODID)) 
		{
			ItemBaublesManager.onBaublesInit(event);
		}
		
		if (Loader.isModLoaded("flammpfeil.slashblade")) 
		{
			BladeManager.onBladeInit();
		}
	}
	// new ItemStack(IC2Items.getItem("lapotron_crystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),//IC2Items.getItem("energy_crystal"),
	private static void onRecipeInit() 
	{
		Recipes.advRecipes.addRecipe(new ItemStack(superLapotronCrystal), 
				new Object[] {
						"SCS",
						"SDS",
						"SCS",
						'S', ItemCraftingManager.niobium_titanium_plate,
						'C', Recipes.inputFactory.forOreDict("circuitElite"),//ItemCraftingManager.super_circuit,
						'D', getAllTypeStack(IC2Items.getItem("lapotron_crystal"))
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(advancedIridiumSword), 
				new Object[] {
						"SIS",
						"SIS",
						"CDC",
						'S', IC2Items.getItem("casing", "steel"),
						'I', IC2Items.getItem("crafting", "iridium"),
						'C', ItemCraftingManager.niobium_titanium_plate,
						'D', getAllTypeStack(IC2Items.getItem("lapotron_crystal"))
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(advancedIridiumSword), 
				new Object[] {
						"SIS",
						"SBS",
						"CDC",
						'S', IC2Items.getItem("casing", "steel"),
						'I', IC2Items.getItem("crafting", "iridium"),
						'B', getAllTypeStack(IC2Items.getItem("nano_saber")),
						'C', ItemCraftingManager.niobium_titanium_plate,
						'D', getAllTypeStack(IC2Items.getItem("lapotron_crystal"))
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(advancedLithiumBattery), 
				new Object[] {
						"CSC",
						"SLS",
						"SPS",
						'C', IC2Items.getItem("cable", "type:copper,insulation:1"),
						'S', IC2Items.getItem("casing", "steel"),
						'L', IC2Items.getItem("dust", "lithium"),
						'P', IC2Items.getItem("dust", "lead")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(lithiumBattery), 
				new Object[] {
						" C ",
						"SLS",
						"SLS",
						'C', IC2Items.getItem("cable", "type:copper,insulation:1"),
						'S', IC2Items.getItem("casing", "copper"),
						'L', IC2Items.getItem("dust", "lithium")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(thoriumBattery), 
				new Object[] {
						" C ",
						"SLS",
						"SLS",
						'C', IC2Items.getItem("cable", "type:tin,insulation:1"),
						'S', IC2Items.getItem("casing", "lead"),
						'L', Recipes.inputFactory.forOreDict("dustThorium")//ItemCraftingManager.thorium_dust
				});
		
		if(ConfigManager.EnableElectricNutritionSupplyCost)
		{
			Recipes.advRecipes.addRecipe(new ItemStack(electricNutritionSupply), 
					new Object[] {
							"SPS",
							"HCH",
							"SBS",
							'P', IC2Items.getItem("treetap"),
							'S', IC2Items.getItem("casing", "gold"),
							'C', IC2Items.getItem("crafting", "circuit"),
							'H', IC2Items.getItem("heat_exchanger"),
							'B', getAllTypeStack(lithiumBattery)//IC2Items.getItem("re_battery")
					});			
		}

		
		Recipes.advRecipes.addRecipe(new ItemStack(electricFishingRod), 
				new Object[] {
						" SL",
						"SDL",
						" SL",
						'S', IC2Items.getItem("casing", "iron"),
						'L', Items.STRING,
						'D', IC2Items.getItem("crafting", "small_power_unit")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(nanoBow), 
				new Object[] {
						"CMS",
						"CBA",
						"CMS",
						'C', IC2Items.getItem("crafting", "carbon_plate"),
						'B', getAllTypeStack(IC2Items.getItem("energy_crystal")),//IC2Items.getItem("energy_crystal"),
						'M', IC2Items.getItem("crafting", "electric_motor"),
						'S', Items.LEAD,
						'A', IC2Items.getItem("crafting", "advanced_circuit")
				});		
		
		Recipes.advRecipes.addRecipe(new ItemStack(plasmaAirCannon), 
				new Object[] {
						"CCB",
						"LPA",
						"LLB",
						'C', IC2Items.getItem("crafting", "coil"),
						'B', getAllTypeStack(lithiumBattery),
						'L', IC2Items.getItem("crafting", "alloy"),
						'P', IC2Items.getItem("fluid_cell"),
						'A', IC2Items.getItem("crafting", "advanced_circuit")
				});
		
		
		Recipes.advRecipes.addRecipe(new ItemStack(electricShield), 
				new Object[] {
						"SAS",
						"SBS",
						"SDS",
						'S', IC2Items.getItem("plate", "steel"),
						'B', getAllTypeStack(lithiumBattery),
						'D', IC2Items.getItem("crafting", "power_unit"),
						'A', IC2Items.getItem("crafting", "advanced_circuit")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(divingMask), 
				new Object[] {
						"EBE",
						"FGF",
						"SCS",
						'E', IC2Items.getItem("component_heat_exchanger"),
						'B', getAllTypeStack(lithiumBattery),
						'G', IC2Items.getItem("glass", "reinforced"),
						'F', IC2Items.getItem("fluid_cell"),
						'C', IC2Items.getItem("crafting", "advanced_circuit"),
						'S', IC2Items.getItem("crafting", "rubber")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(titaniumIronAlloyRotor), 
				new Object[] {
						" B ",
						"BHB",
						" B ",
						'H', Recipes.inputFactory.forOreDict("shaftTitanium"),//ItemCraftingManager.titanium_shaft,
						'B', getAllTypeStack(ItemCraftingManager.titanium_iron_rotor_blade)
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(superIridiumRotor), 
				new Object[] {
						" B ",
						"BHB",
						" B ",
						'H', Recipes.inputFactory.forOreDict("shaftTitanium"),//ItemCraftingManager.titanium_shaft,
						'B', getAllTypeStack(ItemCraftingManager.super_iridium_blade)
				});
		
		if(ConfigManager.EnableElectricFirstAidLifeSupportRecipe)
		{
			Recipes.advRecipes.addRecipe(new ItemStack(electricFirstAidLifeSupport), 
					new Object[] {
							"ITI",
							"MBM",
							"ITI",
							'M', getAllTypeStack(electricForceFieldGenerator),
							'B', getAllTypeStack(IC2Items.getItem("lapotron_crystal")),
							'I', IC2Items.getItem("crafting", "iridium"),
							'T', Recipes.inputFactory.forOreDict("circuitElite")//ItemCraftingManager.super_circuit
					});
		}
		
		Recipes.advRecipes.addRecipe(new ItemStack(advancedQuantumChest), new Object[] {
				"SBS",
				"CAC",
				"SRS",
				'A', getAllTypeStack(IC2Items.getItem("quantum_chestplate")),
				'B', getAllTypeStack(superLapotronCrystal),
				'R', getAllTypeStack(electricFirstAidLifeSupport),
				'S', ItemCraftingManager.super_iridium_compress_plate,
				'C', Recipes.inputFactory.forOreDict("circuitElite")//ItemCraftingManager.super_circuit
		});
		
		Recipes.advRecipes.addRecipe(new ItemStack(advancedQuantumChest), new Object[] {
				"SBS",
				"RCD",
				"SAS",
				'A', getAllTypeStack(heavyQuantumChest),
				'B', getAllTypeStack(superLapotronCrystal),
				'R', getAllTypeStack(electricFirstAidLifeSupport),
				'S', ItemCraftingManager.super_iridium_compress_plate,
				'C', Recipes.inputFactory.forOreDict("circuitElite"),//ItemCraftingManager.super_circuit,
				'D', getAllTypeStack(IC2Items.getItem("jetpack_electric"))
		});
		
		Recipes.advRecipes.addRecipe(new ItemStack(heavyQuantumChest), new Object[] {
				"SAS",
				"IBI",
				"ICI",
				'S', IC2Items.getItem("crafting", "alloy"),
				'A', getAllTypeStack(IC2Items.getItem("nano_chestplate")),
				'I', IC2Items.getItem("crafting", "iridium"),
				'B', getAllTypeStack(IC2Items.getItem("lapotron_crystal")),
				'C', Recipes.inputFactory.forOreDict("circuitElite")//ItemCraftingManager.super_circuit
		});

		Recipes.advRecipes.addRecipe(new ItemStack(advancedJetPack), new Object[]
				{
				"SBS",
				"SJS",
				"P P",
				'S', Recipes.inputFactory.forOreDict("plateTitanium"),//ItemCraftingManager.titanium_plate,
				'B', getAllTypeStack(IC2Items.getItem("energy_crystal")),
				'J', getAllTypeStack(IC2Items.getItem("jetpack_electric")),
				'P', IC2Items.getItem("upgrade", "fluid_ejector")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(electricSubmachineGun), new Object[]
				{
				"SSB",
				"LJC",
				"SSD",
				'S', IC2Items.getItem("plate", "iron"),
				'B', getAllTypeStack(lithiumBattery),
				'L', ItemCraftingManager.lens,
				'J', IC2Items.getItem("neutron_reflector"),
				'C', IC2Items.getItem("crafting", "advanced_circuit"),
				'D', IC2Items.getItem("crafting", "power_unit")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(advancedElectricSubmachineGun), new Object[]
				{
				"SSB",
				"LJC",
				"SSD",
				'S', ItemCraftingManager.niobium_titanium_plate,
				'B', getAllTypeStack(IC2Items.getItem("lapotron_crystal")),
				'L', ItemCraftingManager.diamond_lens,
				'J', IC2Items.getItem("iridium_reflector"),
				'C', Recipes.inputFactory.forOreDict("circuitElite"),//ItemCraftingManager.super_circuit,
				'D', getAllTypeStack(electricSubmachineGun)
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(tacticalLaserSubmachineGun), new Object[]
				{
				"SSB",
				"LJC",
				"SSB",
				'S', ItemCraftingManager.super_iridium_compress_plate,
				'B', getAllTypeStack(superLapotronCrystal),
				'L', getAllTypeStack(advancedIridiumSword),
				'J', getAllTypeStack(advancedElectricSubmachineGun),
				'C', Recipes.inputFactory.forOreDict("circuitElite")//ItemCraftingManager.super_circuit
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(electricRocketLauncher), new Object[]
				{
				"SSB",
				"XLC",//XLC
				"SFD",
				'S', Recipes.inputFactory.forOreDict("plateTitanium"),//ItemCraftingManager.titanium_plate,
				'B', getAllTypeStack(IC2Items.getItem("energy_crystal")),
				'X', IC2Items.getItem("crafting", "coil"),
				'L', IC2Items.getItem("upgrade", "ejector"),
				'C', IC2Items.getItem("crafting", "advanced_circuit"),
				'F', IC2Items.getItem("frequency_transmitter"),
				'D', IC2Items.getItem("crafting", "power_unit")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(chargingSuperLapotronCrystal), new Object[]
				{
				"EBS",
				"BOB",//XLC
				"SBE",
				'B', getAllTypeStack(ItemManager.superLapotronCrystal),
				'E', ReactorItemManager.advHeatVent,
				'S', IC2Items.getItem("advanced_heat_exchanger"),
				'O', getAllTypeStack(IC2Items.getItem("charging_lapotron_crystal"))
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(electricForceFieldGenerator), new Object[]
				{
				"SCS",
				"MBM",
				"SCS",
				'S', Recipes.inputFactory.forOreDict("casingTitanium"),//ItemCraftingManager.titanium_casing,
				'B', getAllTypeStack(IC2Items.getItem("energy_crystal")),
				'M', getAllTypeStack(electricNutritionSupply),
				'C', IC2Items.getItem("crafting", "advanced_circuit")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(electricLighter), new Object[]
			   {"NSB",
				"LJC",
				"NSD",
				'S', IC2Items.getItem("plate", "iron"),
				'B', getAllTypeStack(advancedLithiumBattery),
				'L', ItemCraftingManager.lens,
				'J', Items.GLOWSTONE_DUST,
				'N', IC2Items.getItem("neutron_reflector"),
				'C', IC2Items.getItem("crafting", "advanced_circuit"),
				'D', IC2Items.getItem("crafting", "power_unit")
				});
		
		Recipes.advRecipes.addRecipe(new ItemStack(electricWirlessManager), new Object[]
				   {"SCS",
					"CTC",
					"SBS",
					'S', Recipes.inputFactory.forOreDict("plateTitanium"),//ItemCraftingManager.titanium_plate,
					'B', getAllTypeStack(lithiumBattery),
					'T', IC2Items.getItem("frequency_transmitter"),
					'C', IC2Items.getItem("crafting", "advanced_circuit"),
					});
		
		Recipes.advRecipes.addRecipe(new ItemStack(geomagneticDetector), new Object[]
				   {"SCS",
					"CTC",
					"SBS",
					'S', Recipes.inputFactory.forOreDict("plateTitanium"),//ItemCraftingManager.titanium_plate,
					'B', getAllTypeStack(lithiumBattery),
					'T', IC2Items.getItem("te", "tesla_coil"),
					'C', IC2Items.getItem("crafting", "advanced_circuit"),
					});
		
		Recipes.advRecipes.addRecipe(new ItemStack(electricPlasmaGun), new Object[]
				{
					"TSB",
					"OOC",
					"TSD",
					'S', ItemCraftingManager.niobium_titanium_plate,
					'B', getAllTypeStack(IC2Items.getItem("energy_crystal")),
					'O', IC2Items.getItem("crafting", "coil"),
					'J', IC2Items.getItem("iridium_reflector"),
					'T', IC2Items.getItem("upgrade", "ejector"),
					'C', Recipes.inputFactory.forOreDict("circuitElite"),//ItemCraftingManager.super_circuit,
					'D', getAllTypeStack(electricSubmachineGun)
					});
		
		Recipes.advRecipes.addRecipe(new ItemStack(tachyonDisruptor), new Object[]
				{
					"OSB",
					"TTC",
					"OSD",
					'S', ItemCraftingManager.nano_living_metal,
					'B', getAllTypeStack(superLapotronCrystal),
					'O', ItemCraftingManager.neutron_plate,
					'T', ItemCraftingManager.field_generator,
					'C', Recipes.inputFactory.forOreDict("circuitUltimate"),//ItemCraftingManager.living_circuit,
					'D', getAllTypeStack(tacticalLaserSubmachineGun)
					});
		
		JetpackAttachmentRecipe.blacklistedItems.add(advancedQuantumChest);// N O P E
		JetpackAttachmentRecipe.blacklistedItems.add(advancedJetPack);
		
		//Other
		//Recipes.semiFluidGenerator.addFluid("ic2creosote", 10L, 8L);
		try 
		{
			SpecialRecipesHelper.onInitLiquidRecipe();
		}catch(Exception expt)
		{
			System.out.println("[METS]:Fail to init liquid recipe.");
			expt.printStackTrace();
		}
	}	
	
	public static ItemStack getAllTypeStack(ItemStack itemstack) {
		return new ItemStack(itemstack.getItem(), 1, OreDictionary.WILDCARD_VALUE);
	}

	public static ItemStack getAllTypeStack(Item item) {
		return new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);
	}
}
