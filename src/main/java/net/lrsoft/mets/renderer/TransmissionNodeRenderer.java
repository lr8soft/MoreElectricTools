package net.lrsoft.mets.renderer;

import java.util.Vector;

import net.lrsoft.mets.block.tileentity.TileEntityLighterBlock;
import net.lrsoft.mets.block.tileentity.TileEntityWirelessPowerTransmissionNode;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.renderer.particle.XCustomizedParticle;
import net.lrsoft.mets.util.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TransmissionNodeRenderer extends TileEntitySpecialRenderer<TileEntityWirelessPowerTransmissionNode>{
	private Vector<XCustomizedParticle> particleVector = new Vector();
	@Override
	public void render(TileEntityWirelessPowerTransmissionNode te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		onRender(te, x, y, z, partialTicks);
	}

	@Override
	public void renderTileEntityFast(TileEntityWirelessPowerTransmissionNode te, double x, double y, double z, float partialTicks,
			int destroyStage, float partial, BufferBuilder buffer) {
		onRender(te, x, y, z, partialTicks);
		
	}
	
	private void onRender(TileEntityWirelessPowerTransmissionNode te, double x, double y, double z, float partialTicks)
	{
		for(int i=0; i < particleVector.size(); i++)
		{
			XCustomizedParticle particle = particleVector.get(i);
			particle.onRender(partialTicks);
			if(particle.getIsFinish())
			{
				particleVector.remove(i);
			}
		}

		if(System.currentTimeMillis() % 2 == 0)
		{
			Vec3d motionNow = getMotion(te.getTargetPosition().subtract(0.5d, 0.5d, 0.5d), 
					new Vec3d(x, y, z).addVector(0.5d, 0.5d, 0.5d), 1.0f);
			
			for(int count = 0; count < 2; count++)
			{
				XCustomizedParticle particle = new XCustomizedParticle(new Vec3d(0.8f, 1.0f, 1.0f),
						new Vec3d(x + 0.5f, y + 0.5f, z + 0.5f), 
						new Vec3d(motionNow.x/ 15.0f,motionNow.y / 15.0f, motionNow.z / 15.0f),
						new Vec3d(0.1f, 0.1f, 0.1f), new Vec3d(0.0f, 0.0f, 1.0f), 0.0f, MathUtils.getRandomFromRange(60, 20));
				particleVector.add(particle);
			}
		}
	}
	
	// this.render(tileentityIn, (double)blockpos.getX() - staticPlayerX, (double)blockpos.getY() - staticPlayerY, (double)blockpos.getZ() - staticPlayerZ, partialTicks, destroyStage, 1.0F);
	private Vec3d getMotion(Vec3d target, Vec3d pos, double velocity)
	{
		Vec3d newTarget = target.addVector(TileEntityRendererDispatcher.staticPlayerX, TileEntityRendererDispatcher.staticPlayerY, TileEntityRendererDispatcher.staticPlayerZ);
		double d1 = (newTarget.x - pos.x);
		double d2 = (newTarget.y - pos.y);
		double d3 = (newTarget.z - pos.z);
		double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
		Vec3d temp = new Vec3d(d1, d2 + d4 * 0.2D, d3);
		float f = MathHelper.sqrt(temp.x * temp.x + temp.y * temp.y + temp.z * temp.z);
		double x = temp.x / (double)f;
		double y = temp.y / (double)f;
		double z = temp.z / (double)f;
		x = temp.x * velocity;
		y = temp.y * velocity;
		z = temp.z * velocity;
		
        return new Vec3d(x, y, z);
	}
}
