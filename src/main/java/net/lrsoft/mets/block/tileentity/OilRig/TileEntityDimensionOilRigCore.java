package net.lrsoft.mets.block.tileentity.OilRig;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ic2.core.block.TileEntityBlock;
import ic2.core.util.LiquidUtil;
import net.lrsoft.mets.block.tileentity.OilRig.IOilRig.ModuleType;
import net.lrsoft.mets.manager.BlockManager;
import net.lrsoft.mets.manager.FluidManager;
import net.lrsoft.mets.util.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityDimensionOilRigCore extends TileEntityBlock implements IOilRigCore{
	private int currentTick = 0;
	private boolean isStructureComplete = false;
	private IOilRig inputPart = null, panelPart = null;
	private Vector<IOilRig> outputPart = new Vector<>();
	
	private int offsetX = 0, offsetY = 0, offsetZ = 0, offsetMode = 0, offsetValue = 1, offsetMoveTimes = 0;
	private boolean offsetFlag = false, offsetUseTwice = false;
	
	private boolean isRigFinish = false, isTankFull = false , haveEnergy = false, isFirstRun = true;
	private Vec3d tempCoord = new Vec3d(0, -2, 0);
	@Override
	protected void updateEntityServer() {
		super.updateEntityServer();
		if(currentTick % 30 == 0)
		{
			checkStructureComplete();
		}
		
		if(currentTick % 20 == 0 && isStructureComplete)
		{
			if(inputPart != null && outputPart.size() > 0)
			{
				TileEntityOilRigInput input = (TileEntityOilRigInput)inputPart;

				if(input.canUseEnergy(2000.0d))
				{
					if (tryDrill()) {
						boolean haveFillSuccess = false;
						for (IOilRig outputSlot : outputPart) {
							TileEntity output = (TileEntity) outputSlot;
							int amount = LiquidUtil.fillTile(output, getFacing(),
									new FluidStack(FluidManager.crudeOil, 100), false);
							if (amount > 0) {
								input.comsumeEnergy((amount / 100.0d) * 2000.0d);
								haveFillSuccess = true;
								break;
							}
						}
					} else {
						input.comsumeEnergy(1000.0d);
					}

					updatePanelInfo(true);
					haveEnergy = true;
				}else 
				{
					haveEnergy = false;
				}
			}else {
				isStructureComplete = false;
				updatePanelInfo(false);
			}
		}
		
		currentTick++;
	}
	
	protected boolean tryDrill()
	{
		Vec3d targetCoord = new Vec3d(0, -2, 0);
		if(offsetFlag)
		{
			offsetY = 0;//初始化y到两格以下
	
			switch(offsetMode)
			{
			case 0:
				offsetZ-=1;break;
			case 1:
				offsetX-=1;break;
			case 2:
				offsetZ+=1;break;
			case 3:
				offsetX+=1;break;
			default:
				break;
			}
			offsetFlag = false;
		}
		targetCoord = targetCoord.addVector(-offsetX, -offsetY, -offsetZ);
		targetCoord = targetCoord.add(new Vec3d(this.pos));//转移到当前方块
		tempCoord = new Vec3d(targetCoord.x, targetCoord.y, targetCoord.z);
		Block blockTemp = this.world.getBlockState(new BlockPos(targetCoord)).getBlock();
		if(blockTemp != Blocks.AIR)
		{
			offsetY++;
			if(blockTemp == Blocks.BEDROCK)//到底了，重新初始化变量
			{
				//System.out.println(isStructureComplete + " mode:" + offsetMode + " offsetValue:"+ offsetValue  +" twice:"+offsetUseTwice);
				if(!isFirstRun)
				{
					if(offsetMoveTimes + 1 < offsetValue)
					{
						offsetMoveTimes++;
					}else {
						offsetMoveTimes = 0;
						
						if(offsetMode + 1 < 4)
							offsetMode++;
						else {
							offsetMode = 0;
						}
						
						if(offsetUseTwice)//每个偏移量最多使用两次
						{
							offsetValue++;//偏移量变大;
							offsetUseTwice = false;
						}else 
						{
							offsetUseTwice = true;
						}
					}
				}else 
				{
					isFirstRun = false;
				}
				offsetFlag = true;
				return false;
			}
			else if(blockTemp.getUnlocalizedName().contains("ore") || blockTemp.getUnlocalizedName().contains("Ore")
					|| blockTemp ==  Blocks.LAVA || blockTemp == Blocks.FLOWING_LAVA)//矿物
			{
				this.world.setBlockState(new BlockPos(targetCoord), Blocks.AIR.getDefaultState(), 2);
				return MathUtils.getChance(80);
			}
			else if(blockTemp == Blocks.OBSIDIAN)
			{
				this.world.setBlockState(new BlockPos(targetCoord), Blocks.AIR.getDefaultState(), 2);
				return MathUtils.getChance(95);
			}
			else if(blockTemp == Blocks.GRAVEL || blockTemp == Blocks.SAND)
			{
				this.world.setBlockState(new BlockPos(targetCoord), Blocks.AIR.getDefaultState(), 2);
				return MathUtils.getChance(10);
			}
			else 
			{
				this.world.setBlockState(new BlockPos(targetCoord), Blocks.AIR.getDefaultState(), 2);
				return MathUtils.getChance(20);
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
		else if(!haveEnergy) {
			return -1;
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
		
		//侧翼
		Vector<TileEntity> frontFlank = new Vector<>();
		//前侧翼
		frontFlank.add(this.world.getTileEntity(pos.add(2, -1, 2)));
		frontFlank.add(this.world.getTileEntity(pos.add(2, -1, 1)));
		frontFlank.add(this.world.getTileEntity(pos.add(2, -1, 0)));
		frontFlank.add(this.world.getTileEntity(pos.add(2, -1, -1)));
		frontFlank.add(this.world.getTileEntity(pos.add(2, -1, -2)));
		//后侧翼
		frontFlank.add(this.world.getTileEntity(pos.add(-2, -1, 2)));
		frontFlank.add(this.world.getTileEntity(pos.add(-2, -1, 1)));
		frontFlank.add(this.world.getTileEntity(pos.add(-2, -1, 0)));
		frontFlank.add(this.world.getTileEntity(pos.add(-2, -1, -1)));
		frontFlank.add(this.world.getTileEntity(pos.add(-2, -1, -2)));
		//左侧翼
		frontFlank.add(this.world.getTileEntity(pos.add(2, -1, 2)));
		frontFlank.add(this.world.getTileEntity(pos.add(1, -1, 2)));
		frontFlank.add(this.world.getTileEntity(pos.add(0, -1, 2)));
		frontFlank.add(this.world.getTileEntity(pos.add(-1, -1, 2)));
		frontFlank.add(this.world.getTileEntity(pos.add(-2, -1, 2)));
		//右侧翼
		frontFlank.add(this.world.getTileEntity(pos.add(2, -1, -2)));
		frontFlank.add(this.world.getTileEntity(pos.add(1, -1, -2)));
		frontFlank.add(this.world.getTileEntity(pos.add(0, -1, -2)));
		frontFlank.add(this.world.getTileEntity(pos.add(-1, -1, -2)));
		frontFlank.add(this.world.getTileEntity(pos.add(-2, -1, -2)));
		
	
		if(checkPortAndUpdate(frontPart) && checkPortAndUpdate(backPart) &&
			checkPortAndUpdate(leftPart) && checkPortAndUpdate(rightPart) && 
			checkPortAndUpdate(frontLeftPart) && checkPortAndUpdate(frontRightPart) && 
			checkPortAndUpdate(backLeftart) && checkPortAndUpdate(backRightPart) &&
			checkPortAndUpdate(centerTop) && checkPortAndUpdate(centerTopest) && checkPortAndUpdate(frontFlank) ) 
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
			
			for(TileEntity flankTileEntity : frontFlank)
			{
				((IOilRig)flankTileEntity).setStructureComplete(true);
			}
			
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
		for(int offset = 0; offset < 4; offset++)//顶上的脚手架
			scaffoldList.add(this.world.getBlockState(this.pos.add(0, 3 + offset, 0)).getBlock());
		//侧天线
		scaffoldList.add(this.world.getBlockState(this.pos.add(1, 5, 0)).getBlock());
		scaffoldList.add(this.world.getBlockState(this.pos.add(-1, 5, 0)).getBlock());
		scaffoldList.add(this.world.getBlockState(this.pos.add(0, 5, 1)).getBlock());
		scaffoldList.add(this.world.getBlockState(this.pos.add(0, 5, -1)).getBlock());
		
		//四个支架
		scaffoldList.add(this.world.getBlockState(this.pos.add(3, 0, 0)).getBlock());
		scaffoldList.add(this.world.getBlockState(this.pos.add(-3, 0, 0)).getBlock());
		scaffoldList.add(this.world.getBlockState(this.pos.add(0, 0, 3)).getBlock());
		scaffoldList.add(this.world.getBlockState(this.pos.add(0, 0, -3)).getBlock());
		
		scaffoldList.add(this.world.getBlockState(this.pos.add(2, 1, 0)).getBlock());
		scaffoldList.add(this.world.getBlockState(this.pos.add(-2, 1, 0)).getBlock());
		scaffoldList.add(this.world.getBlockState(this.pos.add(0, 1, 2)).getBlock());
		scaffoldList.add(this.world.getBlockState(this.pos.add(0, 1, -2)).getBlock());
		
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
		nbtTagCompound.setInteger("offsetX", offsetX);
		nbtTagCompound.setInteger("offsetY", offsetY);
		nbtTagCompound.setInteger("offsetZ", offsetZ);
		nbtTagCompound.setInteger("offsetMode", offsetMode);
		nbtTagCompound.setInteger("offsetValue", offsetValue);
		nbtTagCompound.setBoolean("offsetUseTwice", offsetUseTwice);
		nbtTagCompound.setBoolean("offsetFlag", offsetFlag);
		nbtTagCompound.setInteger("offsetMoveTimes", offsetMoveTimes);
		nbtTagCompound.setBoolean("isFirstRun", isFirstRun);
		return nbtTagCompound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		offsetX = compound.getInteger("offsetX");
		offsetY = compound.getInteger("offsetY");
		offsetZ = compound.getInteger("offsetZ");
		offsetMode = compound.getInteger("offsetMode");
		offsetValue = compound.getInteger("offsetValue");
		offsetMoveTimes = compound.getInteger("offsetMoveTimes");
		offsetUseTwice = compound.getBoolean("offsetUseTwice");
		offsetFlag = compound.getBoolean("offsetFlag");
		isFirstRun = compound.getBoolean("isFirstRun");
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
	
	private boolean checkPortAndUpdate(Vector<TileEntity> te)
	{
		boolean result = true;
		for(TileEntity tileEntity : te)
		{
			if(!checkPortAndUpdate(tileEntity))
			{
				result = false;
			}
		}
		return result;
	}

}

