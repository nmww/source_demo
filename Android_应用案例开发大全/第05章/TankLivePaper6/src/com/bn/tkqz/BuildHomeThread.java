package com.bn.tkqz;
public class BuildHomeThread extends Thread
{
	@Override
	public void run()
	{
		AliveWallPaperTank.map.buildHomeWithStone();
		try
		{
			Thread.sleep(Constant.TIME_BACK_TO_BRICK_FROM_STONE);//һ��ʱ���ָ�שǽ
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		AliveWallPaperTank.map.buildHomeWithBrick();
	}
}
