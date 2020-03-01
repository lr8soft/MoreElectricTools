package net.lrsoft.mets.manager;

import java.util.LinkedList;
import java.util.List;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.event.TeBlockFinalCallEvent;
import ic2.api.item.ElectricItem;
import ic2.api.item.IC2Items;
import ic2.api.recipe.Recipes;
import ic2.core.block.BlockTileEntity;
import ic2.core.block.TeBlockRegistry;
import ic2.core.ref.TeBlock;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.MetsTeBlock;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class BlockManager {
	public static Block lesuStorager;
	
	@SubscribeEvent
	public static void onTeBlockInit(TeBlockFinalCallEvent event)
	{
		  TeBlockRegistry.addAll(MetsTeBlock.class, MetsTeBlock.loc);
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
						'B', ItemManager.advancedLithiumBattery,
						'L', IC2Items.getItem("cable", "type:copper,insulation:1")
				});
		
		ItemStack lootGenerator = teBlock.getItemStack(MetsTeBlock.drop_generator);
		Recipes.advRecipes.addRecipe(lootGenerator, 
				new Object[] {
						"BTB",
						"PAP",
						"PGP",
						'A', IC2Items.getItem("crafting", "advanced_circuit"),
						'T', ItemManager.lithiumBattery,
						'P', IC2Items.getItem("casing", "steel"),
						'B', Items.BLAZE_ROD,
						'G', IC2Items.getItem("te", "generator")
				});
	}
	


	/*private static void onItemBlockInit()
	{
		ForgeRegistries.ITEMS.register(new ItemBlock(lesuStorager) {
			@Override
			public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
			    if(!stack.hasTagCompound()) return;
			    double currentEnergy =stack.getTagCompound().getDouble("energy");
			    tooltip.add(currentEnergy + "/" + TileEntityLESU.maxStorageEnergy + " EU");
			}
		}.setRegistryName(lesuStorager.getRegistryName()));
	}*/
	
}
