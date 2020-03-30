package net.lrsoft.mets.renderer.particle;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.GLUtessellator;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.util.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class XCustomizedParticle {
	private static ResourceLocation resourceLocation = new ResourceLocation(MoreElectricTools.MODID, "textures/particles/particle.png");

	public XCustomizedParticle(Vec3d color, Vec3d worldLocation, Vec3d motion, Vec3d scale, Vec3d rotate, float angle, int maxTicks)
	{
		this.color = color; 
		this.worldLocation = worldLocation; 
		this.motion = motion;
		this.scale = scale; 
		this.rotate = rotate; 
		this.angle = angle;
		this.maxTicks = maxTicks;
		currentTicks = 0;
	}
	
	public void onRender(float partialTicks)
	{
		if(currentTicks < maxTicks)
		{
			onRenderInside(worldLocation.x, worldLocation.y, worldLocation.z ,partialTicks);
	        worldLocation = worldLocation.add(motion);
	        currentTicks++;
		}
	}
	
	private void onRenderInside(double x, double y, double z,float partialTicks) {
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayerSP viewer = minecraft.player;
		float[] info = MathUtils.getPlayerView(viewer, partialTicks);
		float alpha = (1.0f - (float) currentTicks / (float) maxTicks);

		int red = (int) color.x;
		int blue = (int) color.y;
		int green = (int) color.z;
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		
		GlStateManager.enableBlend();
	    GlStateManager.disableAlpha();
	    GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
	    GlStateManager.disableLighting();
	    GlStateManager.depthMask(false);
	    GlStateManager.enableLighting();
		GlStateManager.translate(x, y, z);
		//GlStateManager.enableBlend();

		//GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		
		
		//GlStateManager.depthMask(false);
		minecraft.getTextureManager().bindTexture(resourceLocation);
		GlStateManager.translate(0.0f, 0.0f, 0.0f);
		GlStateManager.rotate(angle, (float) rotate.x, (float) rotate.y, (float) rotate.z);
		GlStateManager.rotate(180.0F - info[1], 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((float) (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1) * -info[0],
				1.0F, 0.0F, 0.0F);
		GlStateManager.scale(scale.x, scale.y, scale.z);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(0.0d, 0.0d).color(red, green, blue, alpha)
				.endVertex();
		bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(1.0d, 0.0d).color(red, green, blue, alpha)
				.endVertex();
		bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(1.0d, 1.0d).color(red, green, blue, alpha)
				.endVertex();
		bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(0.0d, 1.0d).color(red, green, blue, alpha)
				.endVertex();
		tessellator.draw();
		
		//GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		//GlStateManager.enableLighting();
		//GlStateManager.disableBlend();
		//GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		//GlStateManager.depthMask(true);
		
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
	}
	
	public boolean getIsFinish()
	{
		return (currentTicks >= maxTicks);
	}
	
	private Vec3d color;
	private Vec3d worldLocation;
	private Vec3d motion;
	private Vec3d scale;
	private Vec3d rotate;
	private float angle;
	private int maxTicks, currentTicks;
}
