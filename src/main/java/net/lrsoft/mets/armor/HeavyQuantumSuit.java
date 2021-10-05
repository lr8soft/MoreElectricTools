package net.lrsoft.mets.armor;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IHazmatLike;
import ic2.api.item.IItemHudInfo;
import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.item.IPseudoDamageItem;
import ic2.core.item.armor.jetpack.IBoostingJetpack;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.manager.ConfigManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.common.util.EnumHelper;

public class HeavyQuantumSuit extends ItemArmor
		implements ISpecialArmor, IPseudoDamageItem, IElectricItem, IItemHudInfo, IHazmatLike {
	private static ArmorMaterial defaultMaterial = EnumHelper.addArmorMaterial("heavy_quantum_suit",
			MoreElectricTools.MODID + ":heavy_quantum_suit", 50, new int[] { 7, 15, 9, 6 }, 40,
			SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2);
	public final static UUID KNOWBACK_MODIFER = UUID.fromString("191a4c46-856a-4a51-a3d9-a466f1a1ca5b");
	private static double maxStorageEnergy = 10000000d, transferSpeed = 4096d;
	private static int suitTier = 4;

	public HeavyQuantumSuit(String itemName) {
		super(defaultMaterial, 0, EntityEquipmentSlot.CHEST);
		setUnlocalizedName("mets." + itemName);// advanced_quantum_chest
		setRegistryName(MoreElectricTools.MODID, itemName);
		setCreativeTab(MoreElectricTools.CREATIVE_TAB);
		setMaxDamage(2333);
		setMaxStackSize(1);
		setNoRepair();
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
			IC2.platform.profilerStartSection("QuantumBodyarmor");
			player.extinguish();
			float currentHealth = player.getHealth();
			if (currentHealth < player.getMaxHealth() * 0.2f) {
				if (ElectricItem.manager.use(itemStack, 5000, player)) {
					player.setHealth(currentHealth + 1);
				}
			}
			IC2.platform.profilerEndSection();
	}


	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage,
			int slot) {
		int energyPerDamage = (int) ConfigManager.HeavyQuantumSuitDamageEnergyCost;
		int damageLimit = Integer.MAX_VALUE;
		if (energyPerDamage > 0)
			damageLimit = (int) Math.min(damageLimit, 25.0D * ElectricItem.manager.getCharge(armor) / energyPerDamage);
		return new ISpecialArmor.ArmorProperties(8, 0.5, damageLimit);
	}

	@Override
	public boolean addsProtection(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack) {
		return (ElectricItem.manager.getCharge(stack) > 0.0D);
	}


	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		ElectricItem.manager.discharge(stack, (damage * ConfigManager.HeavyQuantumSuitDamageEnergyCost), Integer.MAX_VALUE,
				true, false, false);
	}

	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		if (equipmentSlot == EntityEquipmentSlot.CHEST) {
			multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
					new AttributeModifier(KNOWBACK_MODIFER, "Weapon modifier", 1.0f, 0));
		}
		return multimap;
	}


	@Override
	public void setStackDamage(ItemStack stack, int damage) {
		setDamage(stack, damage);
	}

	@Override
	public List<String> getHudInfo(ItemStack stack, boolean advanced) {
		List<String> info = new LinkedList<>();
		info.add(ElectricItem.manager.getToolTip(stack));
		info.add(Localization.translate("ic2.item.tooltip.PowerTier", new Object[] { Integer.valueOf(this.suitTier) }));
		return info;
	}

	@Override
	public boolean canProvideEnergy(ItemStack stack) {
		return false;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@Override
	public double getMaxCharge(ItemStack stack) {
		return maxStorageEnergy;
	}

	@Override
	public int getTier(ItemStack stack) {
		return suitTier;
	}

	@Override
	public double getTransferLimit(ItemStack stack) {
		return transferSpeed;
	}

	public Item getChargedItem(ItemStack itemStack) {
		return this;
	}

	public Item getEmptyItem(ItemStack itemStack) {
		return this;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
}
