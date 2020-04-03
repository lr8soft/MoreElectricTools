package net.lrsoft.mets.renderer;

import java.util.Vector;

import org.lwjgl.opengl.GL11;

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
			particle.onSpecialRender(x, y, z, partialTicks);
			if(particle.getIsFinish())
			{
				particleVector.remove(i);
			}
		}
		Vec3d target = te.getTargetPosition();
		Vec3d currentPos = new Vec3d(te.getPos());
		if(target != null)
		{
			
			Vec3d motionNow = getMotion(target.subtract(x, y, z), currentPos.addVector(0.5d, 0.5d, 0.5d), 0.05f);
			
			for(int count = 0; count < 6; count++)
			{
				XCustomizedParticle particle = new XCustomizedParticle(new Vec3d(0.8f, 1.0f, 1.0f),
						currentPos.addVector(0.5d, 0.5d, 0.5d), 
						new Vec3d(motionNow.x/ 15.0f,motionNow.y / 15.0f, motionNow.z / 15.0f),
						new Vec3d(0.1f, 0.1f, 0.1f), new Vec3d(0.0f, 0.0f, 1.0f), 0.0f, MathUtils.getRandomFromRange(60, 20));
				particleVector.add(particle);
			}
		}
	}
	
	private Vec3d getMotion(Vec3d target, Vec3d pos, double velocity)
	{
		Vec3d newTarget = pos.scale(velocity);
        return pos.subtract(newTarget);
	}
}
