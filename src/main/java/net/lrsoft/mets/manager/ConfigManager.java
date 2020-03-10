package net.lrsoft.mets.manager;

import net.lrsoft.mets.MoreElectricTools;
import net.minecraftforge.common.config.Config;

@Config(modid = MoreElectricTools.MODID) 
public class ConfigManager {
	 @Config.RequiresMcRestart
	 public static double AdvancedIridiumSwordBaseCost = 800d;
	 @Config.RequiresMcRestart
	 public static float AdvancedIridiumSwordBaseAttackDamage = 25f;
	 
	 @Config.RequiresMcRestart
	 public static double NanoBowBaseCost = 300d;
	 @Config.RequiresMcRestart
	 public static float NanoBowMaxVelocity = 5.0f;
	 
	 @Config.RequiresMcRestart
	 public static boolean EnableElectricNutritionSupplyCost = true;
	 @Config.RequiresMcRestart
	 public static double ElectricNutritionSupplyCost = 200d;
	 
	 @Config.RequiresMcRestart
	 public static boolean EnableElectricFirstAidLifeSupportRecipe = true;
	 @Config.RequiresMcRestart
	 public static double ElectricFirstAidLifeSupport = 10000d;
	 
	 @Config.RequiresMcRestart
	 public static double PlasmaAirCannonBaseCost = 1000d;
	 @Config.RequiresMcRestart
	 public static double PlasmaAirCannonBaseDamage = 10d;
	 
	 @Config.RequiresMcRestart
	 public static double AdvancedQuantumSuitDamageEnergyCost = 10000d;
	 @Config.RequiresMcRestart
	 public static double AdvancedQuantumSuitCureCost = 30000d;
	 
	 @Config.RequiresMcRestart
	 public static double ElectricSubmachineGunCost = 100d;
	 @Config.RequiresMcRestart
	 public static int ElectricSubmachineGunInterval = 90;
	 
	 @Config.RequiresMcRestart
	 public static double AdvancedElectricSubmachineGunCost = 5000d;
	 @Config.RequiresMcRestart
	 public static int AdvancedElectricSubmachineGunInterval = 80;
}
