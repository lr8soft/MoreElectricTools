package net.lrsoft.mets.crop;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.Item;

public class LeadRichCrop extends UniformCropTemplate {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
    public LeadRichCrop()
    {
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), Integer.valueOf(0)));
        this.setTickRandomly(true);
        this.setCreativeTab(null);
        this.setRegistryName(MoreElectricTools.MODID, "lead_rich_crop");
        setUnlocalizedName("mets.crop.lead_rich_crop");
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
		return CropManager.leadRichSeed;
	}

	@Override
	protected Item getCrop() {
		return ItemCraftingManager.lead_nugget;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {AGE});
	}

}

