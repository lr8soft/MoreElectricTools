package net.lrsoft.mets.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MathUtils{
	public static int getRandomFromRange(int max, int min)
	{
		return new Random().nextInt(max-min)+min;
	}
	
	public static double getRandomFromRange(double max, double min)
	{
		return (Math.random() * (max - min) + min);
	}
	
	public static float[] getPlayerView(EntityPlayer player, float partialTicks)
	{
		float[] playerView = new float[2];
		playerView[0] = playerView[1] = 0f;
		World worldIn = player.world;
		if (player.isPlayerSleeping())
        {
            IBlockState iblockstate = worldIn.getBlockState(new BlockPos(player));
            Block block = iblockstate.getBlock();

            if (block.isBed(iblockstate, worldIn, new BlockPos(player), player))
            {
                int i = block.getBedDirection(iblockstate, worldIn, new BlockPos(player)).getHorizontalIndex();
                playerView[1] = (float)(i * 90 + 180);
                playerView[0] = 0.0F;
            }
        }
        else
        {
        	playerView[1] = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks;
        	playerView[0] = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        }

        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2)
        {
        	playerView[1] += 180.0F;
        }
        return playerView;
	}
}
