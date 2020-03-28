package net.lrsoft.mets.util;

public class VersionHelper {
	public static boolean getShouldUseIInventorySlotHolder()
	{
		try 
		{
			Class cIInventorySlotHolder = Class.forName("ic2.core.block.IInventorySlotHolder");
			System.out.println("[METS]:IInventorySlotHolder is avaliable.");
			return true;
		}catch(Exception expt) {
			System.out.println("[METS]:IInventorySlotHolder is unavailable.");
			return false;
		}
	}
}
