package net.lrsoft.mets.block.tileentity.GESU;

import net.minecraft.util.math.Vec3d;

public interface IGESUPart {
	void setCore(Vec3d coord);
	void setCoreComplete(boolean isComplete);
}
