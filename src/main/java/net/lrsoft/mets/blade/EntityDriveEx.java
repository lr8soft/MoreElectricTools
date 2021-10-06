package net.lrsoft.mets.blade;

import org.lwjgl.util.vector.Vector3f;

import mods.flammpfeil.slashblade.entity.EntityDrive;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityDriveEx extends EntityDrive {
	private boolean hasUpdate = false;
	private Vector3f color = new Vector3f(0.2f, 0.2f, 1.0f);

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
			float roll, Vector3f rgbColor) {
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

	public void setColor(Vector3f rbg) {
		color = rbg;

		dataManager.set(COLOR_RED, color.x);
		dataManager.set(COLOR_GREEN, color.y);
		dataManager.set(COLOR_BLUE, color.z);
		hasUpdate = true;
	}

	public Vector3f getColor() {
		if (!hasUpdate) {
			color.x = dataManager.get(COLOR_RED);
			color.y = dataManager.get(COLOR_GREEN);
			color.z = this.getDataManager().get(COLOR_BLUE);
			hasUpdate = true;
		}
		return color;
	}
}
