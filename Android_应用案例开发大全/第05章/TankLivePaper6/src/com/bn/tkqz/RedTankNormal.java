package com.bn.tkqz;

public class RedTankNormal extends TankNormal
{
	public RedTankNormal(int direction, int x,int y) 
	{
		super(direction, x, y);
		this.tanki=AliveWallPaperTank.tankRedi1;
	}
	@Override
	void explode()
	{
		super.explode();
		//��ը�����������
		AliveWallPaperTank.map.reward=Reward.generateAReward();
	}
}