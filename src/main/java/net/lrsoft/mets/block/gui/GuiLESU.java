package net.lrsoft.mets.block.gui;

import com.google.common.base.Supplier;
import ic2.core.gui.VanillaButton;
import ic2.core.GuiIC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.wiring.ContainerElectricBlock;
import ic2.core.block.wiring.TileEntityElectricBlock;
import ic2.core.gui.EnergyGauge;
import ic2.core.init.Localization;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

//This class all code come from industrialcraft2-exp
public class GuiLESU extends GuiIC2<ContainerElectricBlock> {
	public GuiLESU(final ContainerElectricBlock container) {
		super(container, 196);

		addElement(EnergyGauge.asBar(this, 79, 38, (TileEntityBlock) container.base));

		addElement(((VanillaButton) new VanillaButton(this, 152, 4, 20, 20, createEventSender(0)).withIcon(new Supplier() {
				public ItemStack get() 
				{
					return new ItemStack(Items.REDSTONE);
				}
			})).withTooltip(new Supplier() {
				public String get() 
				{
					return ((TileEntityElectricBlock)container.base).getRedstoneMode();
				}})
				);

	}

	protected void drawForegroundLayer(int mouseX, int mouseY) {
		super.drawForegroundLayer(mouseX, mouseY);

		this.fontRenderer.drawString(Localization.translate("ic2.EUStorage.gui.info.armor"), 8, this.ySize - 126 + 3,
				4210752);

		this.fontRenderer.drawString(Localization.translate("ic2.EUStorage.gui.info.level"), 79, 25, 4210752);
		int e = (int) Math.min(
				((TileEntityElectricBlock) ((ContainerElectricBlock) this.container).base).energy.getEnergy(),
				((TileEntityElectricBlock) ((ContainerElectricBlock) this.container).base).energy.getCapacity());
		this.fontRenderer.drawString(" " + e, 110, 35, 4210752);
		this.fontRenderer.drawString("/"
				+ (int) ((TileEntityElectricBlock) ((ContainerElectricBlock) this.container).base).energy.getCapacity(),
				110, 45, 4210752);

		String output = Localization.translate("ic2.EUStorage.gui.info.output", new Object[] {
				Double.valueOf(((TileEntityElectricBlock) ((ContainerElectricBlock) this.container).base).getOutput()) });
		this.fontRenderer.drawString(output, 85, 60, 4210752);
	}

	protected ResourceLocation getTexture() {
		return background;
	}
  
   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIElectricBlock.png");
}
