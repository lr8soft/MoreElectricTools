package net.lrsoft.mets.block.tileentity;

import java.util.List;
import java.util.Random;

import ic2.api.network.NetworkHelper;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.util.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeHell;
import net.minecraft.world.biome.BiomeHills;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityGeomagneticGenerator extends TileEntityBaseGenerator{
	private int currentTick = 0;
	private float lastSourceValue = 0.0f;
	private boolean shouldMachineWork = false;
	public TileEntityGeomagneticGenerator() {
		super(20480D, 6, 400000000);
	}

	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		updateEntity();
	}
	
	private void updateEntity()
	{	
		shouldMachineWork = checkStructureCompleted();
		if(shouldMachineWork)
		{
			Biome biome = this.world.getBiome(this.pos);
			float ratio = getMagneticSource();
			if(biome instanceof BiomeHell || biome instanceof BiomeHills)
			{
				ratio *= 1.2f;
			}	
			
			int newFuel = (int)ratio * 6;
			if(fuel + newFuel <= Integer.MAX_VALUE)
				fuel += newFuel;
			else {
				fuel = Integer.MAX_VALUE;
			}
			setActive(true);
		}else {
			setActive(false);
		}

		
		if(currentTick + 1 < Integer.MAX_VALUE)
			currentTick++;
		else {
			currentTick = 0;
		}
	}
	
	private float getMagneticSource()
	{
		if(lastSourceValue !=0 && currentTick % 15 != 0)
		{
			return lastSourceValue;
		}
		float source = 1.0f;
		int seaLevelDelta = this.pos.getY() - this.world.getSeaLevel();
		if(seaLevelDelta < 0)
		{
			source = (float)this.pos.getY() / (float)(this.world.getSeaLevel()+1);
		}
		
		int baseDelta = (this.pos.getY() >= 20) ? 20 : this.pos.getY() - 0;
		int airBlockCount = 0;
		for(int i=0; i< baseDelta; i++)
		{
			Block tempBlock = this.world.getBlockState(this.pos.add(0, -i, 0)).getBlock();
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
		lastSourceValue = source;
		return source;
	}
	
	private boolean checkStructureCompleted()
	{
		IBlockState topestAntenna = this.world.getBlockState(this.pos.add(0.0d, 2.0d, 0.0d));
		IBlockState midAntenna = this.world.getBlockState(this.pos.add(0.0d, 1.0d, 0.0d));
		IBlockState centerBase = this.world.getBlockState(this.pos.add(0.0d, -1.0d, 0.0d));
		IBlockState ABase = this.world.getBlockState(this.pos.add(1.0d, -1.0d, 0.0d));
		IBlockState BBase = this.world.getBlockState(this.pos.add(-1.0d, -1.0d, 0.0d));
		IBlockState CBase = this.world.getBlockState(this.pos.add(0.0d, -1.0d, 1.0d));
		IBlockState DBase = this.world.getBlockState(this.pos.add(0.0d, -1.0d, -1.0d));
		
		if(topestAntenna.getBlock() == BlockManager.geomagneticAntenna && midAntenna.getBlock() == BlockManager.geomagneticAntenna)
		{
			if(ABase.getBlock() == BlockManager.geomagneticPedestal && BBase.getBlock() == BlockManager.geomagneticPedestal &&
					CBase.getBlock() == BlockManager.geomagneticPedestal && DBase.getBlock() == BlockManager.geomagneticPedestal)
			{
				return true;
			}
		}
		return false;
	}
	
	public String getOperationSoundFile() {
		return "Generators/GeneratorLoop.ogg";
	}
	
	public boolean isConverting() {
		return (shouldMachineWork && this.fuel > 0);
	}
	
	@Override
	public boolean gainFuel() {
		if (shouldMachineWork && fuel > 0)
		{
			fuel -= 1;
			return true;
		}else {
			return false;
		}
	}
}
