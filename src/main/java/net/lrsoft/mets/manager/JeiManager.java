package net.lrsoft.mets.manager;

import ic2.core.block.ITeBlock;
import ic2.jeiIntegration.recipe.machine.DynamicCategory;
import ic2.jeiIntegration.recipe.machine.IORecipeCategory;
import ic2.jeiIntegration.recipe.machine.IRecipeWrapperGenerator;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategory;
import net.lrsoft.mets.block.MetsBlockWithTileEntity;
import net.lrsoft.mets.util.SpecialRecipesHelper;

@JEIPlugin
public class JeiManager implements IModPlugin{
	@Override
	public void register(IModRegistry registry) {
		IModPlugin.super.register(registry);
		
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		addMachineRecipes(registry, (IORecipeCategory)new DynamicCategory((ITeBlock)MetsBlockWithTileEntity.neutron_polymerizer, 
				SpecialRecipesHelper.neutronPolymerizerRecipes, guiHelper), IRecipeWrapperGenerator.basicMachine);
		
	}
	
	private <T> void addMachineRecipes(IModRegistry registry, IORecipeCategory<T> category,
			IRecipeWrapperGenerator<T> wrappergen) {
		registry.addRecipeCategories(new IRecipeCategory[] { (IRecipeCategory) category });
		registry.addRecipes(wrappergen.getRecipeList(category));
		registry.addRecipeCategoryCraftingItem(category.getBlockStack(), new String[] { category.getUid() });
	}
}
