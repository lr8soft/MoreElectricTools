package net.lrsoft.mets.manager;

import java.util.LinkedList;
import java.util.List;

import ic2.api.item.ElectricItem;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.LESU;
import net.lrsoft.mets.block.tileentity.TileEntityLESU;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = MoreElectricTools.MODID)
public class BlockManager {
	public static LESU lesuStorager;
	
	static
	{
		lesuStorager = new LESU();
	}
	
	@SubscribeEvent
	public static void onBlockInit(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(lesuStorager);
		GameRegistry.registerTileEntity(TileEntityLESU.class, new ResourceLocation(MoreElectricTools.MODID,lesuStorager.lesu_registry_name));

		onItemBlockInit();
	}
	
	private static void onItemBlockInit()
	{
		ForgeRegistries.ITEMS.register(new ItemBlock(lesuStorager) {
			@Override
			public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
			    if(!stack.hasTagCompound()) return;
			    double currentEnergy =stack.getTagCompound().getDouble("energy");
			    tooltip.add(currentEnergy + "/" + TileEntityLESU.maxStorageEnergy + " EU");
			}
		}.setRegistryName(lesuStorager.getRegistryName()));
	}
	

}
