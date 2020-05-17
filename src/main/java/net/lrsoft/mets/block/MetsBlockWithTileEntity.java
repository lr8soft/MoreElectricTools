package net.lrsoft.mets.block;

import java.lang.instrument.ClassDefinition;
import java.lang.reflect.Constructor;
import java.util.Set;

import ic2.api.tile.IEnergyStorage;
import ic2.core.IC2;
import ic2.core.block.*;
import ic2.core.item.block.ItemBlockTileEntity;
import ic2.core.ref.TeBlock.*;
import ic2.core.ref.TeBlock.DefaultDrop;
import ic2.core.ref.TeBlock.HarvestTool;
import ic2.core.ref.IC2Material;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.profile.Version;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.tileentity.*;
import net.lrsoft.mets.block.tileentity.GESU.TileEntityGESUCore;
import net.lrsoft.mets.block.tileentity.GESU.TileEntityGESUInputPort;
import net.lrsoft.mets.block.tileentity.GESU.TileEntityGESUOutputPort;
import net.lrsoft.mets.block.tileentity.OilRig.*;

public enum MetsBlockWithTileEntity implements ITeBlock {
	lesu((Class)TileEntityLESU.class, 0, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	drop_generator((Class)TileEntityDropGenerator.class, 1, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	eesu((Class)TileEntityEESU.class, 2, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	advanced_kinetic_generator((Class)TileEntityAdvancedKineticGenerator.class, 3, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	super_kinetic_generator((Class)TileEntitySuperKineticGenerator.class, 4, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	advanced_blast_furnace((Class)TileEntityAdvancedBlastFurnace.class, 5, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	advanced_macerator((Class)TileEntityAdvancedMacerator.class, 6, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	advanced_compressor((Class)TileEntityAdvancedCompressor.class, 7, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	chargepad_eesu((Class)TileEntityChargepadEESU.class, 8, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	chargepad_lesu((Class)TileEntityChargepadLESU.class, 9, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	advanced_stirling_generator((Class)TileEntityAdvancedStirlingGenerator.class, 10, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	advanced_semifluid_generator((Class)TileEntityAdvancedSemifluidGenerator.class, 11, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	
	extruding_machine((Class)TileEntityExtrudingMachine.class, 12, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	bending_machine((Class)TileEntityBlendingMachine.class, 13, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	cutting_machine((Class)TileEntityCuttingMachine.class, 14, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	titanium_storage_box((Class)TileEntityTitaniumStorageBox.class, 15, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 3.0F, 100.0F, EnumRarity.EPIC, IC2Material.MACHINE, false),
	
	experience_generator((Class)TileEntityExperienceGenerator.class, 16, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	advanced_solar_generator((Class)TileEntityAdvancedSolarGenerator.class, 17, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	photon_resonance_solar_generator((Class)TileEntityVibrateSolarGenerator.class, 18, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	wireless_power_transmission_node((Class)TileEntityWirelessPowerTransmissionNode.class, 19, true, Util.noFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	ultimate_photon_resonance_solar_generator((Class)TileEntityUltimateSolarGenerator.class, 20, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	geomagnetic_generator((Class)TileEntityGeomagneticGenerator.class, 21, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	
	neutron_polymerizer((Class)TileEntityNeutronPolymerizer.class, 22, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	electric_blast_furnace((Class)TileEntityElectricBlastFurnace.class, 23, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	
	gesu_core((Class)TileEntityGESUCore.class, 24, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	gesu_input((Class)TileEntityGESUInputPort.class, 25, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	gesu_output((Class)TileEntityGESUOutputPort.class, 26, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	
	oil_rig_core((Class)TileEntityOilRigCore.class, 27, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	oil_rig_input((Class)TileEntityOilRigInput.class, 28, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	oil_rig_output((Class)TileEntityOilRigOutput.class, 29, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	oil_rig_base((Class)TileEntityOilRigBase.class, 30, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
	oil_rig_panel((Class)TileEntityOilRigPanel.class, 31, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false);
	public static final ResourceLocation loc = new ResourceLocation(MoreElectricTools.MODID, "te");
	private Class<? extends TileEntityBlock> teClass;
	private final int itemMeta;
	private final boolean hasActive;
	private final Set<EnumFacing> supportedFacings;
	private final boolean allowWrenchRotating;
	private final HarvestTool harvestTool;
	private final DefaultDrop defaultDrop;
	private final float hardness;
	
	private static final MetsBlockWithTileEntity[] VALUES;

	MetsBlockWithTileEntity(Class<? extends TileEntityBlock> teClass, int itemMeta, boolean hasActive,
			Set<EnumFacing> supportedFacings, boolean allowWrenchRotating, HarvestTool harvestTool,
			DefaultDrop defaultDrop, float hardness, float explosionResistance, EnumRarity rarity, Material material,
			boolean transparent) {
		this.teClass = teClass;
		this.itemMeta = itemMeta;
		this.hasActive = hasActive;
		this.supportedFacings = supportedFacings;
		this.allowWrenchRotating = allowWrenchRotating;
		this.harvestTool = harvestTool;
		this.defaultDrop = defaultDrop;
		this.hardness = hardness;
		this.explosionResistance = explosionResistance;
		this.rarity = rarity;
		this.material = material;
		this.transparent = transparent;
	}

	private final float explosionResistance;private final EnumRarity rarity;private final Material material;private final boolean transparent;private TileEntityBlock dummyTe;private ITePlaceHandler placeHandler;

	static {
		for (MetsBlockWithTileEntity block : values()) {
			TileEntity.register(loc.getResourceDomain() + ':' + block.getName(), block.getTeClass());
		}
		VALUES = values();
		
	}

	public static void buildDummies() {
	      ModContainer mc = Loader.instance().activeModContainer();
	      if (mc != null && MoreElectricTools.MODID.equals(mc.getModId())) {
	    	  MetsBlockWithTileEntity[] tileEntities = VALUES;

	         for(MetsBlockWithTileEntity block : tileEntities) {
	            if (block.teClass != null) {
	               try {
	            	  block.dummyTe = block.teClass.newInstance();
	               } catch (Exception expt) {
	            	  System.out.println("[METS]:Failed to init a tile entity.");
	            	  expt.printStackTrace();
	               }
	            }
	         }
	      } else {
	         throw new IllegalAccessError("Don't mess with this please.");
	      }
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.itemMeta;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name();
	}

	@Override
	public boolean allowWrenchRotating() {
		// TODO Auto-generated method stub
		return this.allowWrenchRotating;
	}

	@Override
	public DefaultDrop getDefaultDrop() {
		// TODO Auto-generated method stub
		return this.defaultDrop;
	}

	@Override
	public TileEntityBlock getDummyTe() {
		// TODO Auto-generated method stub
		return this.dummyTe;
	}

	@Override
	public float getExplosionResistance() {
		// TODO Auto-generated method stub
		return this.explosionResistance;
	}

	@Override
	public float getHardness() {
		// TODO Auto-generated method stub
		return this.hardness;
	}

	@Override
	public HarvestTool getHarvestTool() {
		// TODO Auto-generated method stub
		return this.harvestTool;
	}

	@Override
	public ResourceLocation getIdentifier() {
		// TODO Auto-generated method stub
		return loc;
	}

	@Override
	public EnumRarity getRarity() {
		// TODO Auto-generated method stub
		return this.rarity;
	}

	@Override
	public Set<EnumFacing> getSupportedFacings() {
		// TODO Auto-generated method stub
		return this.supportedFacings;
	}

	@Override
	public Class<? extends TileEntityBlock> getTeClass() {
		// TODO Auto-generated method stub
		return this.teClass;
	}

	@Override
	public boolean hasActive() {
		// TODO Auto-generated method stub
		return this.hasActive;
	}

	@Override
	public boolean hasItem() {
		// TODO Auto-generated method stub
		return (this.teClass != null && this.itemMeta != -1);
	}
	
}
