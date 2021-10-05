package net.lrsoft.mets.blade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ic2.api.item.ElectricItem;
import mods.flammpfeil.slashblade.ability.UntouchableTime;
import mods.flammpfeil.slashblade.entity.EntitySpearManager;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import mods.flammpfeil.slashblade.util.ReflectionAccessHelper;

public class SASpear extends SpecialAttackBase {
	private static float cost = 10000f;
	@Override
	public String toString() {
		return "SA_SpearEX";
	}

	@Override
	public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
		World world = player.world;

		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);

		double playerDist = 10;
		float attackDist = (float) (playerDist / 3.0);

		if (!player.onGround)
		{
			playerDist *= 1.2f;
		}

		ReflectionAccessHelper.setVelocity(player, -Math.sin(Math.toRadians(player.rotationYaw)) * playerDist, 
				player.motionY, Math.cos(Math.toRadians(player.rotationYaw)) * playerDist);

		if (!world.isRemote) {
	        if(!ElectricItem.manager.use(stack, cost, player))
	        	return;

			ItemSlashBlade blade = (ItemSlashBlade) stack.getItem();

			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 2, true, false));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 300, 2, true, false));
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 250, 2, true, false));

			EntitySpearManager entityDA = new EntitySpearManager(world, player, false);
			entityDA.setLifeTime(14);
			if (entityDA != null) {
				world.spawnEntity(entityDA);
			}
		}

		UntouchableTime.setUntouchableTime(player, 5);

		player.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
		ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.HiraTuki);
	}
}