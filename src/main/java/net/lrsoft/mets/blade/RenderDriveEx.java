package net.lrsoft.mets.blade;

import mods.flammpfeil.slashblade.client.renderer.entity.RenderDrive;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import mods.flammpfeil.slashblade.util.ResourceLocationRaw;
import org.lwjgl.opengl.GL11;

//Modify from https://github.com/flammpfeil/SlashBlade/blob/1.12.2/src/main/java/mods/flammpfeil/slashblade/client/renderer/entity/RenderDrive.java
//Original author flammpfeil
public class RenderDriveEx extends Render {
	private static double[][] dVec = { { 0, 1, -0.5 },
			{ 0, 0.75, 0 },
			{ 0.1, 0.6, -0.15 },
			{ 0, 0.5, -0.25 },
			{ -0.1, 0.6, -0.15 },
			{ 0, 0, 0.25 }, 
			{ 0.25, 0, 0 },
			{ 0, 0, -0.25 },
			{ -0.25, 0, 0 },
			{ 0, -0.75, 0 },
			{ 0.1, -0.6, -0.15 },
			{ 0, -0.5, -0.25 },
			{ -0.1, -0.6, -0.15 },
			{ 0, -1, -0.5 } }; 

	private static int[][] nVecPos = { { 0, 1, 2, 3 },
			{ 0, 3, 4, 1 },
			{ 1, 5, 6, 2 },
			{ 3, 2, 6, 7 },
			{ 3, 7, 8, 4 },
			{ 1, 4, 8, 5 },
			{ 6, 5, 9, 10 },
			{ 6, 10, 11, 7 },
			{ 8, 7, 11, 12 },
			{ 8, 12, 9, 5 },
			{ 10, 9, 13, 11 },
			{ 12, 11, 13, 9 } };

	public RenderDriveEx(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
		if (entity instanceof EntityDriveEx) {
			doDriveRender((EntityDriveEx) entity, d0, d1, d2, f, f1);
		}
	}

	@Override
	protected ResourceLocationRaw getEntityTexture(Entity var1) {
		return null;
	}

	private void doDriveRender(EntityDriveEx entityDrive, double dX, double dY, double dZ, float f, float f1) {
		Tessellator tessellator = Tessellator.getInstance();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		GL11.glPushMatrix();

		GL11.glTranslatef((float) dX, (float) dY + 0.5f, (float) dZ);
		GL11.glRotatef(entityDrive.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-entityDrive.rotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(entityDrive.getRoll(), 0, 0, 1);

		GL11.glScalef(0.25f, 1, 1);

		float lifetime = entityDrive.getLifeTime();
		float ticks = entityDrive.ticksExisted;
		BufferBuilder wr = tessellator.getBuffer();
		wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

		float alpha = (float) Math.pow((lifetime - Math.min(lifetime, ticks)) / lifetime, 2.0f);

		double dScale = 1.0;
		Vec3d color  = entityDrive.getColor();
		for (int idx = 0; idx < nVecPos.length; idx++) {
			float r = (float)color.x;
			float g = (float)color.y;
			float b = (float)color.z;
			wr.pos(dVec[nVecPos[idx][0]][0] * dScale, dVec[nVecPos[idx][0]][1] * dScale,
					dVec[nVecPos[idx][0]][2] * dScale).color(r, g, b, alpha).endVertex();
			wr.pos(dVec[nVecPos[idx][1]][0] * dScale, dVec[nVecPos[idx][1]][1] * dScale,
					dVec[nVecPos[idx][1]][2] * dScale).color(r, g, b, alpha).endVertex();
			wr.pos(dVec[nVecPos[idx][2]][0] * dScale, dVec[nVecPos[idx][2]][1] * dScale,
					dVec[nVecPos[idx][2]][2] * dScale).color(r, g, b, alpha).endVertex();
			wr.pos(dVec[nVecPos[idx][3]][0] * dScale, dVec[nVecPos[idx][3]][1] * dScale,
					dVec[nVecPos[idx][3]][2] * dScale).color(r, g, b, alpha).endVertex();
		}
		tessellator.draw();

		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}


}
