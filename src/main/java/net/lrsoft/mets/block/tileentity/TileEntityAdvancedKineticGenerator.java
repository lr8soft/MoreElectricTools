package net.lrsoft.mets.block.tileentity;
import ic2.core.block.generator.tileentity.TileEntityKineticGenerator;
import ic2.core.init.MainConfig;
import ic2.core.profile.NotClassic;
import ic2.core.util.ConfigUtil;

@NotClassic
public class TileEntityAdvancedKineticGenerator extends TileEntityKineticGenerator {
	private final double euPerKu = 1.0D * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Kinetic");
	@Override
	protected double getMultiplier() 
	{
		 return this.euPerKu;
	}

}
