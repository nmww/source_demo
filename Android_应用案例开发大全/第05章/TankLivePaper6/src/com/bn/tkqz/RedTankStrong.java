package com.bn.tkqz;

public class RedTankStrong extends TankStrong
{
	public RedTankStrong(int direction, int x,int y) 
	{
		super(direction, x, y);
		this.tanki=AliveWallPaperTank.tankRedi3;
	}
	@Override
	void explode()
	{
		super.explode();
		//��ը�����������
		AliveWallPaperTank.map.reward=Reward.generateAReward();
	}
}
