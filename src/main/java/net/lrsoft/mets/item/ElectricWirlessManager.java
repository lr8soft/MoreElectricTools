package net.lrsoft.mets.item;

import javax.annotation.Nullable;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.init.Localization;
import net.lrsoft.mets.MoreElectricTools;
import net.lrsoft.mets.block.tileentity.TileEntityWirelessPowerTransmissionNode;
import net.lrsoft.mets.util.ItemStackUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElectricWirlessManager extends UniformElectricItem {
	private final static double storageEnergy = 50000, transferSpeed = 128;
	public ElectricWirlessManager() {
		super("electric_wirless_manager", storageEnergy, transferSpeed, 2);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack currentItem = player.getHeldItem(hand);
		TileEntity te = worldIn.getTileEntity(pos);
		if(ElectricItem.manager.canUse(currentItem, 10))
		{
			if (IC2.keyboard.isSneakKeyDown(player)
					&& (te == null || !(te instanceof TileEntityWirelessPowerTransmissionNode))) {
				currentItem.getTagCompound().setInteger("blockX", pos.getX());
				currentItem.getTagCompound().setInteger("blockY", pos.getY());
				currentItem.getTagCompound().setInteger("blockZ", pos.getZ());
				try {
					player.sendMessage(
							new TextComponentString(Localization.translate("mets.info.wirless.selecttarget")));
				} catch (Exception expt) {}
				ElectricItem.manager.use(currentItem, 10, player);
			} else if (te != null && te instanceof TileEntityWirelessPowerTransmissionNode) {
				TileEntityWirelessPowerTransmissionNode wTE = (TileEntityWirelessPowerTransmissionNode) te;
				int x = currentItem.getTagCompound().getInteger("blockX");
				int y = currentItem.getTagCompound().getInteger("blockY");
				int z = currentItem.getTagCompound().getInteger("blockZ");
				wTE.setTargetPosition(new Vec3d(x, y, z));

				try {
					player.sendMessage(new TextComponentString(Localization.translate("mets.info.wirless.settarget")));
				} catch (Exception expt) {
				}
				ElectricItem.manager.use(currentItem, 10, player);
			}		
		}
	
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
