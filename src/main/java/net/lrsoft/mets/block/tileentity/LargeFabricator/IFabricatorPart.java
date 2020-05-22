package net.lrsoft.mets.block.tileentity.LargeFabricator;

import net.lrsoft.mets.block.tileentity.OilRig.IOilRig.ModuleType;
import net.minecraft.util.math.Vec3d;

public interface IFabricatorPart {
	enum FabricatorType{ Input, Tank, Scrap}
	
	void setCore(Vec3d coord);
	void setCoreComplete(boolean isComplete);
	
	FabricatorType getModuleType();
}
