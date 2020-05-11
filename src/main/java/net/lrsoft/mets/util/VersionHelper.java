package net.lrsoft.mets.util;

public class VersionHelper {
	private static boolean isNewVersion = false;
	public static boolean getIsNewVersion()
	{
		return isNewVersion;
	}
	
	public static void checkVersion()
	{
		try {
			Class IInventorySlotHolderClass = Class.forName("ic2.core.block.IInventorySlotHolder");
			System.out.println("[INFO]METS now running under a new version of ic2.");
			isNewVersion  = true;
		} catch (ClassNotFoundException e) {
			System.out.println("[INFO]METS now running under a old version of ic2.");
			isNewVersion = false;
		}
	}
	
	public static Class getTargetSlotClass()
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
