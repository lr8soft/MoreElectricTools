package net.lrsoft.mets.item.blade;

import mods.flammpfeil.slashblade.client.model.BladeSpecialRender;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class BladeModelManager {
	public static final ModelResourceLocation modelLoc = new ModelResourceLocation("flammpfeil.slashblade:model/named/blade.obj");

    public static void onModelInit() {
    	registBladeModel(BladeManager.trblade_first);
    	registBladeModel(BladeManager.hyperblade_second);
    	registBladeModel(BladeManager.mrblade_final);
    	registBladeModel(BladeManager.kineticenergyblade_final);
    	registBladeModel(BladeManager.bloodrev_extra);
    	registBladeModel(BladeManager.craftrev_extra);
    	
    	registBladeModel(BladeManager.fox_white);
    	registBladeModel(BladeManager.fox_black);
    	registBladeModel(BladeManager.fox_elf);
    	registBladeModel(BladeManager.fox_faerie);
    }
    
    private static void registBladeModel(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, modelLoc);
        item.setTileEntityItemStackRenderer((TileEntityItemStackRenderer)new BladeSpecialRender());
    }
}
