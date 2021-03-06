package net.lrsoft.mets.block.tileentity;

import ic2.core.IC2;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.util.BiomeUtil;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

public class TileEntityVibrateSolarGenerator extends TileEntityBaseGenerator {
	private int ticker;
	private static final int tickRate = 128;
	private static final double energyMultiplier = 512d * ConfigUtil.getDouble(MainConfig.get(), "balance/energy/generator/solar");
	@GuiSynced
	public float skyLight;

	public TileEntityVibrateSolarGenerator() {
		super(2048D, 4, 40000000);
		this.ticker = IC2.random.nextInt(128);
	}
	
	public boolean gainEnergy() {
		if (++this.ticker % 128 == 0)
			updateSunVisibility();
		if (this.skyLight > 0.0F) {
			this.energy.addEnergy(energyMultiplier * this.skyLight);
			return true;
		}
		return false;
	}

	@Override
	public boolean gainFuel() {
		return false;
	}
	
	public void updateSunVisibility() {
		this.skyLight = getSkyLight(getWorld(), this.pos.up());
	}

	public static float getSkyLight(World world, BlockPos pos) {
		if (world.provider.isNether())
			return 0.1F;
		float sunBrightness = Util.limit((float) Math.cos(world.getCelestialAngleRadians(1.0F)) * 2.0F + 0.2F, 0.0F,
				1.0F);
		if (!BiomeDictionary.hasType(BiomeUtil.getBiome(world, pos), BiomeDictionary.Type.SANDY)) {
			sunBrightness *= 1.0F - world.getRainStrength(1.0F) * 5.0F / 16.0F;
			sunBrightness *= 1.0F - world.getThunderStrength(1.0F) * 5.0F / 16.0F;
			sunBrightness = Util.limit(sunBrightness, 0.0F, 1.0F);
		}
		if(sunBrightness == 0.0f)
		{
			sunBrightness = 0.1f;
		}
		return world.getLightFor(EnumSkyBlock.SKY, pos) / 15.0F * sunBrightness;
	}

	public boolean needsFuel() {
		return false;
	}

	public boolean getGuiState(String name) {
		if ("sunlight".equals(name))
			return (this.skyLight > 0.0F);
		return super.getGuiState(name);
	}

	protected boolean delayActiveUpdate() {
		return true;
	}

}

