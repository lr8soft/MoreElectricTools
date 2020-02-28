package net.lrsoft.mets.block;

import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LESU extends Block {
	public LESU() 
	{
		super(Material.IRON);
		setUnlocalizedName("mets.machine.lesu");
		setRegistryName(MoreElectricTools.MODID, "lithium_energy_storage_unit");
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
	}

}
