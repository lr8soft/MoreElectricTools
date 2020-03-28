package net.lrsoft.mets.renderer;

import org.lwjgl.opengl.GL11;

import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.tileentity.TileEntityLighterBlock;
import net.lrsoft.mets.manager.ConfigManager;
import net.lrsoft.mets.util.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.animation.FastTESR;

public class LighterRenderer extends TileEntitySpecialRenderer<TileEntityLighterBlock>{
	private static final ResourceLocation texture_lighter =
			new ResourceLocation(MoreElectricTools.MODID, "textures/entity/lighter.png");
	private static float[] defaultColor = new float[] {1.0f, 1.0f, 1.0f};
	@Override
	public void render(TileEntityLighterBlock te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		onRender(ConfigManager.EnableLighterDynamicSource ? te.getColor() : defaultColor, x, y, z, partialTicks);
	}

	@Override
	public void renderTileEntityFast(TileEntityLighterBlock te, double x, double y, double z, float partialTicks,
			int destroyStage, float partial, BufferBuilder buffer) {
		onRender(ConfigManager.EnableLighterDynamicSource ? te.getColor() : defaultColor, x, y, z, partialTicks);
		
	}
	
	private void onRender(float[] color, double x, double y, double z, float partialTicks)
	{
		EntityPlayerSP viewer = (Minecraft.getMinecraft()).player;
		float[] info = MathUtils.getPlayerView(viewer, partialTicks);
		
		int red = (int)color[0];
		int blue = (int)color[2];
		int green = (int)color[1];
		
		bindTexture(texture_lighter);
		GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
        //bindTexture(texture_lighter);
        RenderHelper.enableStandardItemLighting();
        GL11.glDepthMask(false);
       
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F - info[1], 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(
        		(float)(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1) * -info[0], 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.35F, 0.35F, 0.35F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(0.0d, 0.0d).color(red, green, blue, 0.3f).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(1.0d, 0.0d).color(red, green, blue, 0.3f).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(1.0d, 1.0d).color(red, green, blue, 0.3f).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(0.0d, 1.0d).color(red, green, blue, 0.3f).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        
        GL11.glDepthMask(true);
	}
	
}
