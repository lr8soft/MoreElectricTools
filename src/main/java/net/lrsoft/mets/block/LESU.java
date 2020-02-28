package net.lrsoft.mets.block;

import java.util.List;

import javax.annotation.Nullable;

import ic2.api.item.IC2Items;
import ic2.api.tile.IWrenchable;
import ic2.core.block.BlockBase;
import ic2.core.ref.BlockName;
import ic2.core.ref.IBlockModelProvider;
import ic2.core.ref.IC2Material;
import ic2.core.util.StackUtil;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.tileentity.TileEntityLESU;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LESU extends Block implements ITileEntityProvider, IWrenchable, IBlockModelProvider{
	public final static String lesu_registry_name = "lithium_energy_storage_unit";
	public LESU() 
	{
		super(IC2Material.MACHINE);
		setUnlocalizedName("mets.machine.lesu");
		setRegistryName(MoreElectricTools.MODID, lesu_registry_name);
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
	}
	
	

	@Override
	public void registerModels(BlockName arg0) {
		// TODO Auto-generated method stub
		BlockBase.registerDefaultItemModel(this);
	}

	@Override
	public EnumFacing getFacing(World world, BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wrenchCanRemove(World world, BlockPos pos, EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ItemStack> getWrenchDrops(World world, BlockPos pos, IBlockState state, TileEntity te,
			EntityPlayer player, int fortune) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new TileEntityLESU();
	}
	
	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.inventory.getCurrentItem() == IC2Items.getItem("wrench") || player.inventory.getCurrentItem() == IC2Items.getItem("electricWrench"))
			return true;

		if(!player.isSneaking()){
			player.openGui(MoreElectricTools.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}

		return false;
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		// TODO Auto-generated method stub
		ItemStack zeroStack = new ItemStack(this, 1, 0);
		StackUtil.getOrCreateNbtData(zeroStack).setInteger("energy", 0);
		items.add(zeroStack);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		if(world.isRemote)
			return;

		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof IInventory){
			IInventory inventory = (IInventory) tile;
			for(int j1 = 0; j1 < inventory.getSizeInventory(); ++j1){
				ItemStack itemstack = inventory.getStackInSlot(j1);
				if(itemstack != null){
					float f = world.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
					while(itemstack.getCount() > 0){
						int k1 = world.rand.nextInt(21) + 10;
						if(k1 > itemstack.getCount())
							k1 = itemstack.getCount();
						itemstack.setCount(itemstack.getCount()-k1);
						EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
						if(itemstack.hasTagCompound()){
							entityitem.getItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
						entityitem.motionX = world.rand.nextGaussian() * 0.05F;
						entityitem.motionY = world.rand.nextGaussian() * 0.05F + 0.2F;
						entityitem.motionZ = world.rand.nextGaussian() * 0.05F;
						world.spawnEntity(entityitem);
					}
				}
			}

		}
		super.breakBlock(world, pos, state);
	}

}
