package net.lrsoft.mets.block.tileentity.OilRig;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ic2.core.block.TileEntityBlock;
import ic2.core.init.Localization;
import ic2.core.util.LiquidUtil;
import net.lrsoft.mets.block.tileentity.OilRig.IOilRig.ModuleType;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.FluidManager;
import net.lrsoft.mets.util.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityOilRigCore extends TileEntityBlock{
	private int currentTick = 0;
	private boolean isStructureComplete = false;
	private IOilRig inputPart = null, panelPart = null;
	private Vector<IOilRig> outputPart = new Vector<>();
	
	private int offsetY = 0;
	private int offsetFlag = 0;
	private boolean isRigFinish = false;
	private Vec3d tempCoord = new Vec3d(0, -2, 0);
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		if(currentTick % 25 == 0)
		{
			checkStructureComplete();
		}
		
		if(currentTick % 10 == 0 && isStructureComplete)
		{
			if(inputPart != null && outputPart != null)
			{
				TileEntityOilRigInput input = (TileEntityOilRigInput)inputPart;

				if(input.canUseEnergy(200.0d))
				{
					try {
						if (tryDrill()) {
							boolean haveFillSuccess = false;
							for (IOilRig outputSlot : outputPart) {
								TileEntity output = (TileEntity) outputSlot;
								int amount = LiquidUtil.fillTile(output, getFacing(),
										new FluidStack(FluidManager.crudeOil, 100), false);
								if (amount > 0) {
									input.comsumeEnergy((amount / 100.0d) * 200.0d);
									haveFillSuccess = true;
									break;// 给一个加了就够了
								}
							}
							if(!haveFillSuccess)
							{
								isRigFinish = true;
							}
						} else {
							input.comsumeEnergy(50.0d);
						}
					} catch (Exception e) {
						isRigFinish = true;
					}
					updatePanelInfo(true);
				}
			}else {
				isStructureComplete = false;
				updatePanelInfo(false);
			}
		}
		
		currentTick++;
	}
	
	protected boolean tryDrill() throws Exception
	{
		Vec3d targetCoord;
		if(offsetFlag < 9)
		{
			targetCoord = coordGroup[offsetFlag];
			
		}else if(offsetFlag >=9 && offsetFlag < 18){
			targetCoord = coordGroup[offsetFlag - 9].scale(2.0d);
			targetCoord = new Vec3d(targetCoord.x, -2, targetCoord.z);
		}else if(offsetFlag >=18 && offsetFlag < 27) {
			targetCoord = coordGroup[offsetFlag - 18].scale(3.0d);
			targetCoord = new Vec3d(targetCoord.x, -2, targetCoord.z);
		}
		else {
			throw new Exception("OilRig Finish.");
		}
		
		targetCoord = targetCoord.addVector(0.0d, -offsetY, 0.0d);
		targetCoord = targetCoord.add(new Vec3d(this.pos));//转移到当前方块
		tempCoord = new Vec3d(targetCoord.x, targetCoord.y, targetCoord.z);
		Block blockTemp = this.world.getBlockState(new BlockPos(targetCoord)).getBlock();
		if(blockTemp != Blocks.AIR)
		{
			offsetY++;
			if(blockTemp == Blocks.BEDROCK)//到底了
			{
				offsetY = 0;
				offsetFlag++;
				return false;
			}
			else if(blockTemp.getUnlocalizedName().contains("ore") || blockTemp.getUnlocalizedName().contains("Ore")
					|| blockTemp ==  Blocks.LAVA || blockTemp == Blocks.FLOWING_LAVA)//矿物
			{
				this.world.setBlockState(new BlockPos(targetCoord), Blocks.AIR.getDefaultState(), 2);
				return MathUtils.getChance(60);
			}
			else if(blockTemp == Blocks.OBSIDIAN)
			{
				this.world.setBlockState(new BlockPos(targetCoord), Blocks.AIR.getDefaultState(), 2);
				return MathUtils.getChance(70);
			}
			else if(blockTemp == Blocks.GRAVEL || blockTemp == Blocks.SAND)
			{
				this.world.setBlockState(new BlockPos(targetCoord), Blocks.AIR.getDefaultState(), 2);
				return MathUtils.getChance(5);
			}
			else 
			{
				this.world.setBlockState(new BlockPos(targetCoord), Blocks.AIR.getDefaultState(), 2);
				return MathUtils.getChance(10);
			}
		}
		offsetY++;
		return false;
	}
	
	private void updatePanelInfo(boolean isWorking)
	{
		if(panelPart != null)
		{
			TileEntityBlock te = (TileEntityBlock)panelPart;
			te.setActive(isWorking);
		}
	}
	
	public Vec3d getRigCoordinate()
	{
		return tempCoord;
	}
	
	public int getCoreState()
	{
		if(!isStructureComplete)
		{
			return 1;
		}
		else if(isRigFinish)
		{
			return 2;
		}else 
		{
			return 3;
		}
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
		}else 
		{
			isStructureComplete = false;
			updatePanelInfo(false);
			outputPart.clear();
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
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound nbtTagCompound = super.writeToNBT(compound);
		nbtTagCompound.setInteger("offsetY", offsetY);
		nbtTagCompound.setInteger("offsetFlag", offsetFlag);
		return nbtTagCompound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		offsetY = compound.getInteger("offsetY");
		offsetFlag = compound.getInteger("offsetFlag");
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
				outputPart.add(port);
			}else if(port.getModuleType() == ModuleType.Panel)
			{
				panelPart = port;
			}
			
			return true;
		}
		return false;
	}
	
	private final static Vec3d[] coordGroup = new Vec3d[] {
			new Vec3d(0, -2, 0), new Vec3d(1, -2, 0), new Vec3d(-1, -2, 0),
			new Vec3d(0, -2, 1), new Vec3d(0, -2, -1), new Vec3d(1, -2, 1), 
			new Vec3d(1, -2, -1), new Vec3d(-1, -2, 1), new Vec3d(-1, -2, -1)
	};

}
