package net.lrsoft.mets.block.tileentity.LargeFabricator;

import net.minecraft.util.math.Vec3d;

public interface IFabricatorPart {
	void setCore(Vec3d coord);
	void setCoreComplete(boolean isComplete);
}
