package net.lrsoft.mets.renderer;

import net.lrsoft.mets.block.tileentity.TileEntityLighterBlock;
import net.lrsoft.mets.block.tileentity.TileEntityWirelessPowerTransmissionNode;
import net.lrsoft.mets.manager.ConfigManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class BlockParticleRenderer extends TileEntitySpecialRenderer<TileEntityWirelessPowerTransmissionNode>{
	@Override
	public void render(TileEntityWirelessPowerTransmissionNode te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		
	}

	@Override
	public void renderTileEntityFast(TileEntityWirelessPowerTransmissionNode te, double x, double y, double z, float partialTicks,
			int destroyStage, float partial, BufferBuilder buffer) {
		
		
	}
	
	private void onRender()
	{
		
	}
}
