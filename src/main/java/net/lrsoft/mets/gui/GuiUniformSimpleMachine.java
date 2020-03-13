package net.lrsoft.mets.gui;

import com.google.common.base.Supplier;
import ic2.core.ContainerBase;
import ic2.core.GuiIC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.gui.CustomGauge;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.RecipeButton;
import ic2.core.init.Localization;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ic2.core.gui.Gauge;
import ic2.core.gui.VanillaButton;
import net.lrsoft.mets.block.tileentity.TileEntityExtrudingMachine;
public class GuiUniformSimpleMachine<T extends TileEntityStandardMachine<?, ?, ?>> extends GuiIC2<ContainerUniformSimpleMachine<T>>{
	 public GuiUniformSimpleMachine(final ContainerUniformSimpleMachine container)
	 {
		super(container);
		addElement((GuiElement) EnergyGauge.asBolt(this, 57, 37, (TileEntityBlock) container.base));
		addElement((GuiElement) CustomGauge.create(this, 72, 38, new CustomGauge.IGaugeRatioProvider() {
			public double getRatio() {
				return ((T) container.base).getProgress();
			}
		}, Gauge.GaugeStyle.ProgressLongArrow));
	 }
	@Override
	protected ResourceLocation getTexture()
	{
		return new ResourceLocation("mets", "textures/guis/standard.png");
	}
}
