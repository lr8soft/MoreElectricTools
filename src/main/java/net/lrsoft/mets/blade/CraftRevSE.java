package net.lrsoft.mets.blade;

import java.util.List;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialeffect.IRemovable;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import mods.flammpfeil.slashblade.util.SlashBladeHooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CraftRevSE implements ISpecialEffect, IRemovable {
	private static final String EffectKey = "CraftRevSE";
	private boolean burst = false;

	private boolean useBlade(ItemSlashBlade.ComboSequence sequence) {
		if (sequence.useScabbard)
			return false;
		if (sequence == ItemSlashBlade.ComboSequence.None)
			return false;
		if (sequence == ItemSlashBlade.ComboSequence.Noutou)
			return false;
		return true;
	}

	@SubscribeEvent
	public void onImpactEffectEvent(SlashBladeEvent.ImpactEffectEvent event) {

		if (!useBlade(event.sequence))
			return;

		if (!SpecialEffects.isPlayer(event.user))
			return;
		EntityPlayer player = (EntityPlayer) event.user;

		switch (SpecialEffects.isEffective(player, event.blade, this)) {
		case None:
			return;
		case Effective:
			ItemSlashBlade.damageItem(event.blade, 3, player);
			player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 80, 3));

			EntityWitherSword entityDrive = new EntityWitherSword(event.target.world, player, 40, 90);
			if (entityDrive != null) {
				entityDrive.setInterval(1);
				entityDrive.setLifeTime(40);
				int color = burst ? 7396315 : 7377883;
				entityDrive.setColor(color);
				entityDrive.setTargetEntityId(event.target.getEntityId());
				event.target.world.spawnEntity(entityDrive);
				burst = !burst;
			}
			aoeAttack(player, 10086);
			break;
		case NonEffective:
			if (player.getRNG().nextInt(4) != 0) {
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 80, 2));
				return;
			}
			break;
		}
		player.onEnchantmentCritical(event.target);

	}

	@SubscribeEvent
	public void onUpdateItemSlashBlade(SlashBladeEvent.OnUpdateEvent event) {

		if (!SpecialEffects.isPlayer(event.entity))
			return;
		EntityPlayer player = (EntityPlayer) event.entity;

		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
		if (!useBlade(ItemSlashBlade.getComboSequence(tag)))
			return;

		switch (SpecialEffects.isEffective(player, event.blade, this)) {
		case None:
			return;
		case NonEffective:
			if (player.getRNG().nextInt(4) != 0)
				return;
			break;
		case Effective:
			return;
		}
	}

	@Override
	public void register() {
		SlashBladeHooks.EventBus.register(this);
	}

	@Override
	public int getDefaultRequiredLevel() {
		return 35;
	}

	@Override
	public String getEffectKey() {
		return EffectKey;
	}

	@Override
	public boolean canCopy(ItemStack arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canRemoval(ItemStack arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void aoeAttack(EntityPlayer player, int damage) {
		World world = player.world;
		for (int dist = 2; dist < 14; dist += 2) {
			AxisAlignedBB bb = player.getEntityBoundingBox();
			Vec3d vec = player.getLookVec();
			vec = vec.normalize();
			bb = bb.expand(2.0f, 0.25f, 2.0f);
			bb = bb.offset(vec.x * (float) dist, vec.y * (float) dist, vec.z * (float) dist);
			List<Entity> list = world.getEntitiesInAABBexcluding(player, bb, EntitySelectorAttackable.getInstance());
			for (Entity curEntity : list) {
				if (curEntity instanceof EntityLivingBase) {
					((EntityLivingBase) curEntity).setHealth(((EntityLivingBase) curEntity).getHealth() / 2);
					((EntityLivingBase) curEntity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 250, 4));
				}
				curEntity.setFire(damage);
				;
			}
		}
	}
}
