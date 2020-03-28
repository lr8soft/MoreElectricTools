package net.lrsoft.mets.util;

import java.util.Random;

public class MathUtils{
	public static int getRandomFromRange(int max, int min)
	{
		return new Random().nextInt(max-min)+min;
	}
}
