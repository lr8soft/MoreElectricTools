package net.lrsoft.mets.block;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LighterBlock extends Block {
	 public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
	    {
	        public boolean apply(@Nullable EnumFacing p_apply_1_)
	        {
	            return p_apply_1_ != EnumFacing.DOWN;
	        }
	    });
	
	public LighterBlock() {
		super(Material.CIRCUITS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
		setRegistryName(MoreElectricTools.MODID, "lighter_block");
		setLightLevel(1.0f);
		setHardness(0.0f);
		this.setCreativeTab(null);
		
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return  new AxisAlignedBB(0.2D, 0.2D, 0.2D, 0.8D, 0.8D, 0.8D);
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
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState();
		return iblockstate.withProperty(FACING, EnumFacing.UP);
	}
	
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
