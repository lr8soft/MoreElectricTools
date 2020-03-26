package net.lrsoft.mets.renderer;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.tileentity.TileEntityLighterBlock;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class LighterRenderer extends TileEntitySpecialRenderer<TileEntityLighterBlock>{
	private static final ResourceLocation texture_lighter =
			new ResourceLocation(MoreElectricTools.MODID, "textures/entity/lighter.png");

	@Override
	public void render(TileEntityLighterBlock te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		  GlStateManager.pushMatrix();
          GlStateManager.translate((float)x, (float)y, (float)z);
          bindTexture(texture_lighter);
          RenderHelper.enableStandardItemLighting();
          int i = 1;
          float f = (float)(i % 4 * 16 + 0) / 64.0F;
          float f1 = (float)(i % 4 * 16 + 16) / 64.0F;
          float f2 = (float)(i / 4 * 16 + 0) / 64.0F;
          float f3 = (float)(i / 4 * 16 + 16) / 64.0F;
          float f4 = 1.0F;
          float f5 = 0.5F;
          float f6 = 0.25F;
         
          GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
          GlStateManager.translate(0.0F, 0.1F, 0.0F);
          GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
         
          float f7 = 0.3F;
          GlStateManager.scale(1.0F, 1.0F, 1.0F);
          Tessellator tessellator = Tessellator.getInstance();
          BufferBuilder bufferbuilder = tessellator.getBuffer();
          bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
          bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex((double)f, (double)f3).color(255, 255, 255, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
          bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex((double)f1, (double)f3).color(255, 255, 255, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
          bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex((double)f1, (double)f2).color(255, 255, 255, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
          bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex((double)f, (double)f2).color(255, 255, 255, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
          tessellator.draw();
          GlStateManager.disableBlend();
          GlStateManager.disableRescaleNormal();
          GlStateManager.popMatrix();
          
          
	}
}
