package net.lrsoft.mets.util;

public class VersionHelper {
	private static boolean isNewVersion = false, isFiuldNewVersion = false;
	public static boolean getIsItemSlotNewVersion()
	{
		return isNewVersion;
	}
	
	public static boolean getIsFiuldNewVersion()
	{
		return isFiuldNewVersion;
	}
	
	public static void checkItemSlotVersion()
	{
		try {
			Class IInventorySlotHolderClass = Class.forName("ic2.core.block.IInventorySlotHolder");
			System.out.println("[INFO]METS now running under a new version of ic2.");
			isNewVersion  = true;
		} catch (ClassNotFoundException e) {
			System.out.println("[INFO]METS now running under a old version of ic2.");
			isNewVersion = false;
		}
		
		try {
			Class IInventorySlotHolderClass = Class.forName("ic2.api.recipe.ISemiFluidFuelManager$FuelProperty");
			System.out.println("[INFO]METS now running under a ic2 version newer than ex112-216.");
			isFiuldNewVersion  = true;
		} catch (ClassNotFoundException e) {
			System.out.println("[INFO]METS now running under a old version of ic2.");
			isFiuldNewVersion = false;
		}
	}
	
	public static Class getTargetItemSlotClass()
	{
		if(isNewVersion)
		{
			try {
				Class IInventorySlotHolderClass = Class.forName("ic2.core.block.IInventorySlotHolder");
				
				return IInventorySlotHolderClass;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else {
			try {
				Class TileEntityInventoryClass = Class.forName("ic2.core.block.TileEntityInventory");
				return TileEntityInventoryClass;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
