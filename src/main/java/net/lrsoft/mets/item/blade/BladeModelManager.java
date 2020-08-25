package net.lrsoft.mets.item.blade;

import mods.flammpfeil.slashblade.client.model.BladeModel;
import mods.flammpfeil.slashblade.client.model.BladeSpecialRender;
import mods.flammpfeil.slashblade.tileentity.DummyTileEntity;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BladeModelManager {
	public static final ModelResourceLocation modelLoc = new ModelResourceLocation("flammpfeil.slashblade:model/named/blade.obj");

    public static void onModelInit() {
        
    	registBladeModel(BladeManager.trblade_first);
        //ModelLoader.setCustomModelResourceLocation(BladeManager.trblade_first, 0, modelLoc);
        //BladeManager.trblade_first.setTileEntityItemStackRenderer((TileEntityItemStackRenderer)new BladeSpecialRender());
        //ForgeHooksClient.registerTESRItemStack(BladeManager.trblade_first, 0, DummyTileEntity.class);
        
        /*ModelLoader.setCustomModelResourceLocation(BladeManager.hyperblade_second, 0, modelLoc);
        ForgeHooksClient.registerTESRItemStack(BladeManager.hyperblade_second, 0, DummyTileEntity.class); 
        
        ModelLoader.setCustomModelResourceLocation(BladeManager.mrblade_final, 0, modelLoc);
        ForgeHooksClient.registerTESRItemStack(BladeManager.mrblade_final, 0, DummyTileEntity.class);
        
        ModelLoader.setCustomModelResourceLocation(BladeManager.kineticenergyblade_final, 0, modelLoc);
        ForgeHooksClient.registerTESRItemStack(BladeManager.kineticenergyblade_final, 0, DummyTileEntity.class);
        
        ModelLoader.setCustomModelResourceLocation(BladeManager.bloodrev_extra, 0, modelLoc);
        ForgeHooksClient.registerTESRItemStack(BladeManager.bloodrev_extra, 0, DummyTileEntity.class);
        
        ModelLoader.setCustomModelResourceLocation(BladeManager.craftrev_extra, 0, modelLoc);
        ForgeHooksClient.registerTESRItemStack(BladeManager.craftrev_extra, 0, DummyTileEntity.class);*/
    }
    
    private static void registBladeModel(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, modelLoc);
        item.setTileEntityItemStackRenderer((TileEntityItemStackRenderer)new BladeSpecialRender());
    }
}
