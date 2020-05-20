package net.lrsoft.mets.item;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.init.Localization;
import net.lrsoft.mets.block.tileentity.TileEntityWirelessPowerTransmissionNode;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeHell;
import net.minecraft.world.biome.BiomeHills;

public class GeomagneticDetector extends UniformElectricItem {
	private final static double storageEnergy = 50000, transferSpeed = 128;
	public GeomagneticDetector() {
		super("geomagnetic_detector", storageEnergy, transferSpeed, 2);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack currentItem = player.getHeldItem(hand);
		TileEntity te = worldIn.getTileEntity(pos);
		if(ElectricItem.manager.canUse(currentItem, 100))
		{
			Biome biome = worldIn.getBiome(pos);
			float ratio = getMagneticSource(worldIn, pos);
			if(biome instanceof BiomeHell || biome instanceof BiomeHills)
			{
				ratio *= 1.2f;
			}	
			try {
				player.sendMessage(new TextComponentString(Localization.translate("mets.info.geomagnetic_detector", ratio)));
			} catch (Exception expt) {}
		}
	
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	private float getMagneticSource(World worldIn, BlockPos pos)
	{

		float source = 1.0f;
		int seaLevelDelta = pos.getY() - worldIn.getSeaLevel();
		if(seaLevelDelta < 0)
		{
			source = (float)pos.getY() / (float)(worldIn.getSeaLevel()+1);
		}
		
		int baseDelta = (pos.getY() >= 20) ? 20 : pos.getY() - 0;
		int airBlockCount = 0;
		for(int i=0; i< baseDelta; i++)
		{
			Block tempBlock = worldIn.getBlockState(pos.add(0, -i, 0)).getBlock();
			if(tempBlock == Blocks.AIR || tempBlock == Blocks.WATER)
			{
				airBlockCount++;
			}
		}
		if(baseDelta!=0)
			source *= (baseDelta - airBlockCount) / baseDelta;
		else {
			source = 0;
		}
		return source;
	}
}

