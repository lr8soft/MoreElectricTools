package net.lrsoft.mets.block.tileentity.OilRig;

import java.util.ArrayList;
import java.util.List;

import ic2.core.block.BlockScaffold;
import ic2.core.block.TileEntityBlock;
import ic2.core.util.LiquidUtil;
import net.lrsoft.mets.block.tileentity.GESU.IGESUPart;
import net.lrsoft.mets.block.tileentity.OilRig.IOilRig.ModuleType;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.FluidManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityOilRigCore extends TileEntityBlock{
	private int currentTick = 0;
	private boolean isStructureComplete = false;
	private IOilRig inputPart = null, outputPart = null;
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		if(currentTick % 25 == 0)
		{
			checkStructureComplete();
		}
		
		if(currentTick % 120 == 0 && isStructureComplete)
		{
			if(inputPart != null && outputPart != null)
			{
				TileEntityOilRigInput input = (TileEntityOilRigInput)inputPart;
				TileEntity output = (TileEntity)outputPart;
				if(input.canUseEnergy(500.0d))
				{
					int amount = LiquidUtil.fillTile(output, getFacing(), new FluidStack(FluidManager.crudeOil, 500), false);
					if(amount > 0)
					{
						input.comsumeEnergy((amount / 500.0d) * 500.0d);
					}
				}
			}else {
				isStructureComplete = false;
			}
		}
		
		currentTick++;
	}
	
	private void checkStructureComplete() {
		//前后左右
		TileEntity frontPart = this.world.getTileEntity(pos.add(1, 0, 0));
		TileEntity backPart = this.world.getTileEntity(pos.add(-1, 0, 0));
		TileEntity leftPart = this.world.getTileEntity(pos.add(0, 0, 1));
		TileEntity rightPart = this.world.getTileEntity(pos.add(0, 0, -1));
		//侧边
		TileEntity frontLeftPart = this.world.getTileEntity(pos.add(1, 0, 1));
		TileEntity frontRightPart = this.world.getTileEntity(pos.add(1, 0, -1));
		TileEntity backLeftart = this.world.getTileEntity(pos.add(-1, 0, 1));
		TileEntity backRightPart = this.world.getTileEntity(pos.add(-1, 0, -1));
		//顶上的
		TileEntity centerTop = this.world.getTileEntity(pos.add(0, 1, 0));
		TileEntity centerTopest = this.world.getTileEntity(pos.add(0, 2, 0));
		if(checkPortAndUpdate(frontPart) && checkPortAndUpdate(backPart) &&
			checkPortAndUpdate(leftPart) && checkPortAndUpdate(rightPart) && 
			checkPortAndUpdate(frontLeftPart) && checkPortAndUpdate(frontRightPart) && 
			checkPortAndUpdate(backLeftart) && checkPortAndUpdate(backRightPart) &&
			checkPortAndUpdate(centerTop) && checkPortAndUpdate(centerTopest))
		{
			((IOilRig)frontPart).setStructureComplete(true);
			((IOilRig)backPart).setStructureComplete(true);
			((IOilRig)leftPart).setStructureComplete(true);
			((IOilRig)rightPart).setStructureComplete(true);
			
			((IOilRig)frontLeftPart).setStructureComplete(true);
			((IOilRig)frontRightPart).setStructureComplete(true);
			((IOilRig)backLeftart).setStructureComplete(true);
			((IOilRig)backRightPart).setStructureComplete(true);
			
			((IOilRig)centerTop).setStructureComplete(true);
			((IOilRig)centerTopest).setStructureComplete(true);
			isStructureComplete = checkExtraPart();
			System.out.println(isStructureComplete);
		}else 
		{
			isStructureComplete = false;
		}
	}
	
	private boolean checkExtraPart()
	{
		boolean result = true;
		List<Block> scaffoldList = new ArrayList<Block>();
		for(int offset = 0; offset < 3; offset++)//顶上的脚手架
			scaffoldList.add(this.world.getBlockState(this.pos.add(0, 3 + offset, 0)).getBlock());
		//接下来是侧边上空
		for(int offset = 0; offset < 2; offset++)
			scaffoldList.add(this.world.getBlockState(this.pos.add(1, 1 + offset, 0)).getBlock());
		for(int offset = 0; offset < 2; offset++)
			scaffoldList.add(this.world.getBlockState(this.pos.add(-1,1 + offset, 0)).getBlock());
		for(int offset = 0; offset < 2; offset++)
			scaffoldList.add(this.world.getBlockState(this.pos.add(0, 1 + offset, 1)).getBlock());
		for(int offset = 0; offset < 2; offset++)
			scaffoldList.add(this.world.getBlockState(this.pos.add(0, 1 + offset, -1)).getBlock());
		
		for(Block block : scaffoldList)
		{
			if(block != Blocks.AIR && block == BlockManager.titaniumScaffold)
			{
				continue;
			}else {
				result = false;
				break;
			}
		}
		return result;
	}
	
	private boolean checkPortAndUpdate(TileEntity te)
	{
		if(te != null && te instanceof IOilRig)
		{
			IOilRig port = (IOilRig)te;
			port.setCore(this.pos);
			port.setStructureComplete(false);
			if(port.getModuleType() == ModuleType.Input)
			{
				inputPart = port;
			}else if(port.getModuleType() == ModuleType.Output)
			{
				outputPart = port;
			}
			
			return true;
		}
		return false;
	}

}
