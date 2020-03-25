package net.lrsoft.mets.crop;

import ic2.api.item.IC2Items;
import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class IronRichCrop extends UniformCropTemplate {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
    public IronRichCrop()
    {
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), Integer.valueOf(0)));
        this.setTickRandomly(true);
        this.setCreativeTab(null);
        this.setRegistryName(MoreElectricTools.MODID, "iron_rich_crop");
        setUnlocalizedName("mets.crop.iron_rich_crop");
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
		return CropManager.ironRichSeed;
	}

	@Override
	protected Item getCrop() {
		return Items.IRON_NUGGET;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {AGE});
	}

}
