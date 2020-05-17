package net.lrsoft.mets.manager;

import baubles.common.Baubles;
import ic2.core.item.tool.RenderCrossed;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.tileentity.TileEntityLighterBlock;
import net.lrsoft.mets.block.tileentity.TileEntityWirelessPowerTransmissionNode;
import net.lrsoft.mets.crop.CropManager;
import net.lrsoft.mets.entity.EntityGunBullet;
import net.lrsoft.mets.entity.EntityHyperGunBullet;
import net.lrsoft.mets.entity.EntityPlasmaBullet;
import net.lrsoft.mets.entity.EntityRocket;
import net.lrsoft.mets.entity.EntityTachyonBullet;
import net.lrsoft.mets.item.bauble.ItemBaublesManager;
import net.lrsoft.mets.item.crafting.ItemCraftingManager;
import net.lrsoft.mets.item.reactor.ReactorItemManager;
import net.lrsoft.mets.renderer.BulletRenderer;
import net.lrsoft.mets.renderer.LighterRenderer;
import net.lrsoft.mets.renderer.PlasmaBulletRenderer;
import net.lrsoft.mets.renderer.RocketRender;
import net.lrsoft.mets.renderer.TachyonRenderer;
import net.lrsoft.mets.renderer.TransmissionNodeRenderer;
import net.lrsoft.mets.renderer.particle.EntityParticleGroup;
import net.lrsoft.mets.renderer.particle.EntityParticleSpray;
import net.lrsoft.mets.renderer.particle.InstantParticleRender;
import net.lrsoft.mets.renderer.particle.ParticleRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MoreElectricTools.MODID)
public class ModelManager {
	@SubscribeEvent
	public static void onItemModelInit(ModelRegistryEvent event) 
	{
		ModelLoader.setCustomModelResourceLocation(ItemManager.superLapotronCrystal, 0,
				new ModelResourceLocation(ItemManager.superLapotronCrystal.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.chargingSuperLapotronCrystal, 0,
				new ModelResourceLocation(ItemManager.chargingSuperLapotronCrystal.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(ItemManager.advancedIridiumSword, 0,
				new ModelResourceLocation(ItemManager.advancedIridiumSword.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(ItemManager.advancedLithiumBattery, 0,
				new ModelResourceLocation(ItemManager.advancedLithiumBattery.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.lithiumBattery, 0,
				new ModelResourceLocation(ItemManager.lithiumBattery.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.thoriumBattery, 0,
				new ModelResourceLocation(ItemManager.thoriumBattery.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricFirstAidLifeSupport, 0,
				new ModelResourceLocation(ItemManager.electricFirstAidLifeSupport.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricNutritionSupply, 0,
				new ModelResourceLocation(ItemManager.electricNutritionSupply.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricFishingRod, 0,
				new ModelResourceLocation(ItemManager.electricFishingRod.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricShield, 0,
				new ModelResourceLocation(ItemManager.electricShield.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricForceFieldGenerator, 0,
				new ModelResourceLocation(ItemManager.electricForceFieldGenerator.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricLighter, 0,
				new ModelResourceLocation(ItemManager.electricLighter.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricWirlessManager, 0,
				new ModelResourceLocation(ItemManager.electricWirlessManager.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(ItemManager.nanoBow, 0,
				new ModelResourceLocation(ItemManager.nanoBow.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.plasmaAirCannon, 0,
				new ModelResourceLocation(ItemManager.plasmaAirCannon.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricSubmachineGun, 0,
				new ModelResourceLocation(ItemManager.electricSubmachineGun.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.advancedElectricSubmachineGun, 0,
				new ModelResourceLocation(ItemManager.advancedElectricSubmachineGun.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricRocketLauncher, 0,
				new ModelResourceLocation(ItemManager.electricRocketLauncher.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.electricPlasmaGun, 0,
				new ModelResourceLocation(ItemManager.electricPlasmaGun.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.tachyonDisruptor, 0,
				new ModelResourceLocation(ItemManager.tachyonDisruptor.getRegistryName(), "inventory"));
		
		
		ModelLoader.setCustomModelResourceLocation(ItemManager.divingMask, 0,
				new ModelResourceLocation(ItemManager.divingMask.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.advancedQuantumChest, 0,
				new ModelResourceLocation(ItemManager.advancedQuantumChest.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.advancedJetPack, 0,
				new ModelResourceLocation(ItemManager.advancedJetPack.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.heavyQuantumChest, 0,
				new ModelResourceLocation(ItemManager.heavyQuantumChest.getRegistryName(), "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(ItemManager.titaniumIronAlloyRotor, 0,
				new ModelResourceLocation(ItemManager.titaniumIronAlloyRotor.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.superIridiumRotor, 0,
				new ModelResourceLocation(ItemManager.superIridiumRotor.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ItemManager.tacticalLaserSubmachineGun, 0,
				new ModelResourceLocation(ItemManager.tacticalLaserSubmachineGun.getRegistryName(), "inventory"));
		
		ItemManager.electricShield.setTileEntityItemStackRenderer(new net.lrsoft.mets.renderer.NanoShieldRenderer());
		
		ItemCraftingManager.onCraftingItemModelInit();
		ReactorItemManager.onItemModelInit();
		CropManager.onModelInit();
		
		if (Loader.isModLoaded(Baubles.MODID))
		{
			ItemBaublesManager.onBaublesModelInit();
		}
	}	
	
	@SubscribeEvent
	public static void onBlockModelInit(ModelRegistryEvent event) 
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockManager.niobiumOre), 0, new ModelResourceLocation(BlockManager.niobiumOre.getRegistryName(),"normal"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockManager.titaniumOre), 0, new ModelResourceLocation(BlockManager.titaniumOre.getRegistryName(),"normal"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockManager.titaniumBlock), 0, new ModelResourceLocation(BlockManager.titaniumBlock.getRegistryName(),"normal"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockManager.titaniumScaffold), 0, new ModelResourceLocation(BlockManager.titaniumScaffold.getRegistryName(),"normal"));
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockManager.geomagneticPedestal), 0, new ModelResourceLocation(BlockManager.geomagneticPedestal.getRegistryName(),"normal"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockManager.geomagneticAntenna), 0, new ModelResourceLocation(BlockManager.geomagneticAntenna.getRegistryName(),"normal"));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLighterBlock.class, new LighterRenderer());
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWirelessPowerTransmissionNode.class, new TransmissionNodeRenderer());
	}
	
	@SubscribeEvent
	public static void onEntityModelInit(ModelRegistryEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityGunBullet.class, new IRenderFactory<EntityGunBullet>() {
			public Render<EntityGunBullet> createRenderFor(RenderManager manager) {
				return (Render<EntityGunBullet>) new BulletRenderer(manager,
						new ResourceLocation("mets", "textures/entity/bullet.png"));
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityHyperGunBullet.class, new IRenderFactory<EntityHyperGunBullet>() {
			public Render<EntityHyperGunBullet> createRenderFor(RenderManager manager) {
				return (Render<EntityHyperGunBullet>) new BulletRenderer(manager,
						new ResourceLocation("mets", "textures/entity/hyper_bullet.png"));
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new IRenderFactory<EntityRocket>() {
			public Render<EntityRocket> createRenderFor(RenderManager manager) {
				return (Render<EntityRocket>) new RocketRender(manager,
						new ResourceLocation("mets", "textures/entity/rocket.png"));
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityParticleGroup.class, new IRenderFactory<EntityParticleGroup>() {
			public Render<EntityParticleGroup> createRenderFor(RenderManager manager) {
				return (Render<EntityParticleGroup>) new ParticleRenderer(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityParticleSpray.class, new IRenderFactory<EntityParticleSpray>() {
			public Render<EntityParticleSpray> createRenderFor(RenderManager manager) {
				return (Render<EntityParticleSpray>) new InstantParticleRender(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityPlasmaBullet.class, new IRenderFactory<EntityPlasmaBullet>() {
			public Render<EntityPlasmaBullet> createRenderFor(RenderManager manager) {
				return (Render<EntityPlasmaBullet>) new PlasmaBulletRenderer(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTachyonBullet.class, new IRenderFactory<EntityTachyonBullet>() {
			public Render<EntityTachyonBullet> createRenderFor(RenderManager manager) {
				return (Render<EntityTachyonBullet>) new TachyonRenderer(manager,
						new ResourceLocation("mets", "textures/entity/tachyon.png"));
			}
		});
	}
	
	@SubscribeEvent
	public static void onFluidModelInit(ModelRegistryEvent event)
	{
		FluidManager.onModelInit();
	}
}
