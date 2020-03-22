package net.lrsoft.mets.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import ic2.api.item.ElectricItem;
import ic2.core.util.StackUtil;
import net.lrsoft.mets.item.UniformElectricItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class EnergyCrystalBelt extends UniformElectricItem implements IBauble {

	private final static double transferSpeed = 512d, storageEnergy = 2000000;
	private final static int tier = 3;
	public EnergyCrystalBelt()
	{
		super("energy_crystal_belt", storageEnergy, transferSpeed, tier);
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.BELT;
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		if(entity instanceof EntityPlayer &&  entity.world.getTotalWorldTime() % 10L < getTier(itemstack))
		{
			EntityPlayer player = (EntityPlayer) entity;
			NonNullList<ItemStack> nonNullList = player.inventory.mainInventory;
			double limit = transferSpeed;
			for (int i = 0; i < 9 && limit > 0.0D; i++) {
				ItemStack toCharge = nonNullList.get(i);
				if (!StackUtil.isEmpty(toCharge) || i != player.inventory.currentItem) {
					double charge = ElectricItem.manager.charge(toCharge, limit, tier, false, true);
					charge = ElectricItem.manager.discharge(itemstack, charge, tier, true, false, false);
					ElectricItem.manager.charge(toCharge, charge, tier, true, false);
					limit -= charge;
				}
			}		
		}
	}
	
	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, .75F, 1.9f);
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, .75F, 2f);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(!world.isRemote) { 
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			for(int i = 0; i < baubles.getSlots(); i++) 
				if((baubles.getStackInSlot(i) == null || baubles.getStackInSlot(i).isEmpty()) && baubles.isItemValidForSlot(i, player.getHeldItem(hand), player)) {
					baubles.setStackInSlot(i, player.getHeldItem(hand).copy());
					if(!player.capabilities.isCreativeMode){
						player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
					}
					onEquipped(player.getHeldItem(hand), player);
					break;
				}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {return false;}
	
	@Override
	public boolean canProvideEnergy(ItemStack stack){return true;}

}

