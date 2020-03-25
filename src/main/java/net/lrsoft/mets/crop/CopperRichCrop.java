package net.lrsoft.mets.crop;

import ic2.api.item.IC2Items;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CopperRichCrop extends UniformCropTemplate {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
    public CopperRichCrop()
    {
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), Integer.valueOf(0)));
        this.setTickRandomly(true);
        this.setCreativeTab(null);
        this.setRegistryName(MoreElectricTools.MODID, "copper_rich_crop");
        setUnlocalizedName("mets.crop.copper_rich_crop");
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.disableStats();
    }
	
	@Override
	protected PropertyInteger getAgeProperty() {
		return AGE;
	}

	@Override
	public int getMaxAge() {
		return 7;
	}

	@Override
	protected Item getSeed() {
		return CropManager.copperRichSeed;
	}

	@Override
	protected Item getCrop() {
		return ItemCraftingManager.copper_nugget;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {AGE});
	}

}

