package net.lrsoft.mets.block;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.tileentity.TileEntityLighterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LighterBlock extends Block {
	public LighterBlock() {
		super(Material.CIRCUITS);
		setRegistryName(MoreElectricTools.MODID, "lighter_block");
		setLightLevel(1.0f);
		setHardness(0.0f);
		this.setCreativeTab(null);
		
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return  new AxisAlignedBB(0.4D, 0.4D, 0.4D, 0.6D, 0.6D, 0.6D);
	}
	
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
	
    
    @Override
    public boolean hasTileEntity() {
    	return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {

    	return new TileEntityLighterBlock();
    }
    
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
}
