package net.lrsoft.mets.renderer;

import java.util.Random;
import java.util.Vector;

import net.lrsoft.mets.block.tileentity.TileEntityWirelessPowerTransmissionNode;
import net.lrsoft.mets.entity.EntityGunBullet;
import net.lrsoft.mets.entity.EntityPlasmaBullet;
import net.lrsoft.mets.renderer.particle.XCustomizedParticle;
import net.lrsoft.mets.util.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PlasmaBulletRenderer extends Render<EntityPlasmaBullet>{
	private Vector<XCustomizedParticle> particleVector = new Vector();
	public PlasmaBulletRenderer(RenderManager manager)
	{
		super(manager);
	}

	public void doRender(EntityPlasmaBullet entity, double x, double y, double z, float entityYaw, float partialTicks) {
		for(int i=0; i < particleVector.size(); i++)
		{
			XCustomizedParticle particle = particleVector.get(i);
			particle.onRender(partialTicks);
			if(particle.getIsFinish())
			{
				particleVector.remove(i);
			}
		}

		
		for (int count = 0; count < 9; count++) {
			XCustomizedParticle particle = new XCustomizedParticle(new Vec3d(0.8f, 1.0f, 1.0f), new Vec3d(x, y, z),
					new Vec3d(getNewMotion(entity.motionX),
							getNewMotion(entity.motionY),
							getNewMotion(entity.motionZ)),
					new Vec3d(0.1f, 0.1f, 0.1f), new Vec3d(0.0f, 0.0f, 1.0f), 0.0f,
					MathUtils.getRandomFromRange(20, 5));
			particleVector.add(particle);
		}
	}
	
	private double getNewMotion(double motion)
	{
		double value = motion;
		if(new Random().nextInt() % 2 == 0)
		{
			value *= MathUtils.getRandomFromRange(0.1, 0.01);
			value /= 12.0f;
		}else 
		{
			value *= MathUtils.getRandomFromRange(-0.01, -0.1);
			value /= 12.0f;
		}
		return value;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityPlasmaBullet entity)
	{
		return null;
	}

}
