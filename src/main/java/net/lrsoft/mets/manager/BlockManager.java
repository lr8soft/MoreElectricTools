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
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
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
	static 
	{
		niobiumOre = new UniformResourceBlock("niobium_ore", 2.5f, 2);
		titaniumOre = new UniformResourceBlock("titanium_ore", 2.5f, 2);
	}
	
	@SubscribeEvent
	public static void onTeBlockInit(TeBlockFinalCallEvent event)
	{
		TeBlockRegistry.addAll(MetsTeBlock.class, MetsTeBlock.loc);
		TeBlockRegistry.addCreativeRegisterer((list, block, itemblock, tab) -> {
			if (tab == IC2.tabIC2 || tab == CreativeTabs.SEARCH || tab == MoreElectricTools.CREATIVE_TAB) {
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
	
	private static ItemStack getAllTypeStack(ItemStack itemstack)
	{
		return new ItemStack(itemstack.getItem(), 1, OreDictionary.WILDCARD_VALUE);
	}
	
	private static ItemStack getAllTypeStack(Item item)
	{
		return new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);
	}
	
}
