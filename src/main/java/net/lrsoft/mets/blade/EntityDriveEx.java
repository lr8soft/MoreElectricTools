package net.lrsoft.mets.blade;

import mods.flammpfeil.slashblade.entity.EntityDrive;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityDriveEx extends EntityDrive {
	private boolean hasUpdate = false;
	private Vec3d color = new Vec3d(0.2f, 0.2f, 1.0f);

	public EntityDriveEx(World par1World) {
		super(par1World);
	}

	public EntityDriveEx(World par1World, EntityLivingBase entityLiving, float AttackLevel) {
		super(par1World, entityLiving, AttackLevel);
	}

	public EntityDriveEx(World par1World, EntityLivingBase entityLiving, float AttackLevel, boolean multiHit) {
		super(par1World, entityLiving, AttackLevel, multiHit);
	}

	public EntityDriveEx(World par1World, EntityLivingBase entityLiving, float AttackLevel, boolean multiHit,
			float roll) {
		super(par1World, entityLiving, AttackLevel, multiHit, roll);
	}

	public EntityDriveEx(World par1World, EntityLivingBase entityLiving, float AttackLevel, boolean multiHit,
			float roll, Vec3d rgbColor) {
		super(par1World, entityLiving, AttackLevel, multiHit, roll);
		setColor(rgbColor);
	}

	private static final DataParameter<Float> COLOR_RED = EntityDataManager.<Float>createKey(EntityDriveEx.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Float> COLOR_GREEN = EntityDataManager.<Float>createKey(EntityDriveEx.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Float> COLOR_BLUE = EntityDataManager.<Float>createKey(EntityDriveEx.class,
			DataSerializers.FLOAT);

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(COLOR_RED, 0.2f);
		dataManager.register(COLOR_GREEN, 0.2f);
		dataManager.register(COLOR_BLUE, 1.0f);
	}

	public void setColor(Vec3d rbg) {
		color = rbg;

		dataManager.set(COLOR_RED, (float)color.x);
		dataManager.set(COLOR_GREEN, (float)color.y);
		dataManager.set(COLOR_BLUE, (float)color.z);
		hasUpdate = true;
	}

	public Vec3d getColor() {
		if (!hasUpdate) {
			color = new Vec3d(dataManager.get(COLOR_RED), dataManager.get(COLOR_GREEN), this.getDataManager().get(COLOR_BLUE));
			hasUpdate = true;
		}
		return color;
	}
}
