package net.lrsoft.mets.manager;

import ic2.api.recipe.IElectrolyzerRecipeManager.ElectrolyzerOutput;
import ic2.api.recipe.Recipes;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidManager {
	public static Fluid crudeOil;
	public static Fluid dieselOil;
	
	public static BlockFluidClassic crudeOilBlock;
	public static BlockFluidClassic dieselOilBlock;
	static 
	{
		crudeOil = new Fluid("crude_oil", new ResourceLocation(MoreElectricTools.MODID, "fluids/crude_oil_still"),
				new ResourceLocation(MoreElectricTools.MODID, "fluids/crude_oil_flow")).setGaseous(false);
		dieselOil = new Fluid("diesel_oil", new ResourceLocation(MoreElectricTools.MODID, "fluids/diesel_oil_still"),
				new ResourceLocation(MoreElectricTools.MODID, "fluids/diesel_oil_flow")).setGaseous(false);
	}
	public static void onFluidInit()
	{
		crudeOil = registerFluid(crudeOil);
		dieselOil = registerFluid(dieselOil);
	}
	
	public static void onFluidBlockInit(RegistryEvent.Register<Block> event)
	{
		crudeOilBlock = getFluidBlock(crudeOil);
		dieselOilBlock = getFluidBlock(dieselOil);
		
		event.getRegistry().register(crudeOilBlock);
		event.getRegistry().register(dieselOilBlock);
	}
	
	public static void onRecipeInit() {
		Recipes.electrolyzer.addRecipe(crudeOil.getName(), 300, 128, 50, 
				new ElectrolyzerOutput(dieselOil.getName(), 200, EnumFacing.NORTH));
	}
	
	private static Fluid registerFluid(Fluid fluid)
	{
		if(!FluidRegistry.registerFluid(fluid))
		{
			return FluidRegistry.getFluid(fluid.getName());
		}
		return fluid;
	}
	
	public static BlockFluidClassic getFluidBlock(Fluid fluid)
	{
		BlockFluidClassic blockFluidClassic = new BlockFluidClassic(fluid, Material.WATER);
		blockFluidClassic.setRegistryName(fluid.getName());
		blockFluidClassic.setUnlocalizedName(fluid.getName());
		blockFluidClassic.setCreativeTab(null);
		return blockFluidClassic;
	}

}
