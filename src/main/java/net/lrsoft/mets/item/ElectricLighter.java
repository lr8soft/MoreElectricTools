package net.lrsoft.mets.item;

import java.util.Random;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.block.LighterBlock;
import net.lrsoft.mets.entity.EntityHyperGunBullet;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.SoundManager;
import net.lrsoft.mets.util.MathUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElectricLighter extends UniformElectricItem {
	private final static double storageEnergy = 200000, transferSpeed = 128;
	public ElectricLighter() {
		super("electric_lighter", storageEnergy, transferSpeed, 2);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
	        IBlockState iblockstate = worldIn.getBlockState(pos);
	        Block block = iblockstate.getBlock();
	        
	        if (!block.isReplaceable(worldIn, pos))
	        {
	            pos = pos.offset(facing);
	        }

	        ItemStack itemstack = player.getHeldItem(hand);

	        if (!itemstack.isEmpty() 
	        		&& ElectricItem.manager.canUse(itemstack, 500)
	        		&& player.canPlayerEdit(pos, facing, itemstack)
	        		&&worldIn.mayPlace(BlockManager.lighterBlock, pos, false, facing, (Entity)null))
	        {
	            int i = this.getMetadata(itemstack.getMetadata());
	            IBlockState iblockstate1 = BlockManager.lighterBlock.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

	            if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
	            {
	                iblockstate1 = worldIn.getBlockState(pos);
	                worldIn.playSound(player, pos, SoundManager.lighter_place, SoundCategory.BLOCKS, 1.0f, 0.5f);
	                ElectricItem.manager.use(itemstack, 500, player);
	            }

	            return EnumActionResult.SUCCESS;
	        }
	        else
	        {
	            return EnumActionResult.FAIL;
	        }
	}
	
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if (!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == BlockManager.lighterBlock)
        {
            ItemBlock.setTileEntityNBT(world, player, pos, stack);
            BlockManager.lighterBlock.onBlockPlacedBy(world, pos, state, player, stack);

            if (player instanceof EntityPlayerMP)
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
        }

        return true;
    }
}
