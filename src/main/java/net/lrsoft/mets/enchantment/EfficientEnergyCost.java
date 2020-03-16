package net.lrsoft.mets.enchantment;

import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EfficientEnergyCost extends Enchantment {
	private static float[] Ratio = new float[] { 0.95F, 0.9F, 0.85F, 0.80F, 0.75F};
	public EfficientEnergyCost() 
	{
		super(Enchantment.Rarity.RARE, EnumEnchantmentType.ALL,  new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
		setRegistryName(MoreElectricTools.MODID, "efficient_energy_cost");
		setName("efficient_energy_cost");
	}

    public int getMinEnchantability(int enchantmentLevel)
    {
        return 6 + 6 * (enchantmentLevel - 1);
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 30;
    }
    
    public int getMaxLevel()
    {
        return 5;
    }
    
    public static float getAttenuationRatio(int level) {
    	try {
    		return Ratio[level - 1];
    	}catch(Exception expt) {
    		return 1.0f;
    	}
        
    }
}
