package net.lrsoft.mets.manager;

import ic2.api.recipe.IElectrolyzerRecipeManager.ElectrolyzerOutput;
import ic2.api.recipe.Recipes;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

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
	
	public static void onModelInit() {
		registerFluidRender(crudeOilBlock, crudeOil);
		registerFluidRender(dieselOilBlock, dieselOil);
	}
	
	public static void onRecipeInit() {
		Recipes.electrolyzer.addRecipe(crudeOil.getName(), 3000, 128, 500, 
				new ElectrolyzerOutput(dieselOil.getName(), 2000, EnumFacing.NORTH));
		
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

	private static final ResourceLocation fluidLocation = new ResourceLocation(MoreElectricTools.MODID, "fluid");
	
    public static void registerFluidRender(BlockFluidBase blockFluid, Fluid fluid)
    {
        final Item itemFluid = Item.getItemFromBlock(blockFluid);
        ModelLoader.setCustomMeshDefinition(itemFluid, new ItemMeshDefinition()
        {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                return new ModelResourceLocation(fluidLocation, "type=" + fluid.getName());
            }
        });
        ModelLoader.setCustomStateMapper(blockFluid, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(fluidLocation, "type=" + fluid.getName());
            }
        });
    }

}
