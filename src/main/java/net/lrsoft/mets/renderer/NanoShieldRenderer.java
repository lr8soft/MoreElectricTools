package net.lrsoft.mets.renderer;

import net.lrsoft.mets.MoreElectricTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class NanoShieldRenderer extends TileEntityItemStackRenderer {
	private NanoShieldModel model;
	private  ResourceLocation modelTexture;
	public NanoShieldRenderer()
	{
		modelTexture = new ResourceLocation(MoreElectricTools.MODID, "textures/entity/nano_shield.png");
		model = new NanoShieldModel();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		TextureManager texManager = Minecraft.getMinecraft().getTextureManager();
		texManager.bindTexture(modelTexture);
		model.render();
		GlStateManager.bindTexture(0);
	}
	
}
