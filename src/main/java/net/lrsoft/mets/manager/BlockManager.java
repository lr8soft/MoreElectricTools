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
import ic2.core.util.StackUtil;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.MetsTeBlock;
import net.lrsoft.mets.block.UniformResourceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class BlockManager {
	public static Block niobiumOre;
	public static Block titaniumOre;
	static 
	{
		niobiumOre = new UniformResourceBlock("niobium_ore", 2.5f, 2);
		titaniumOre = new UniformResourceBlock("titanium_ore", 2.5f, 2);
	}
	
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
						'L', IC2Items.getItem("cable", "type:gold,insulation:2")
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
	
	@SubscribeEvent
	public static void onCommonBlockInit(RegistryEvent.Register<Block> event) {
	    event.getRegistry().register(niobiumOre);
	    event.getRegistry().register(titaniumOre);
	    //
	    //onCommonBlockItemInit();
	    //
	}
	
	@SubscribeEvent
	public static void onCommonBlockItemInit(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemBlock(niobiumOre).setRegistryName(niobiumOre.getRegistryName()));
		event.getRegistry().register(new ItemBlock(titaniumOre).setRegistryName(titaniumOre.getRegistryName()));
	}
}
