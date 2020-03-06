package net.lrsoft.mets.block;

import java.util.Random;

import javax.annotation.Nonnull;

import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;

public class UniformResourceBlock extends Block{
	
	public UniformResourceBlock(String blockName,float hardness, int harvestLevel) {
		this( blockName,Material.ROCK, hardness, harvestLevel);
	}
	
	public UniformResourceBlock(String blockName, Material type, float hardness, int harvestLevel) {
		super(type);
		setUnlocalizedName("mets." + blockName);
		setRegistryName(MoreElectricTools.MODID, blockName);
		setHardness(hardness);
		setHarvestLevel("pickaxe", harvestLevel);
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
	}
	
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemBlock.getItemFromBlock(this);
    }

}
