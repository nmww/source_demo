package com.bn.tkqz;
public class RedTankFast extends TankFast
{
	public RedTankFast(int direction, int x,int y) 
	{
		super(direction, x, y);
		this.tanki=AliveWallPaperTank.tankRedi2;
	}
	@Override
	void explode()
	{
		super.explode();
		//��ը�����������
		AliveWallPaperTank.map.reward=Reward.generateAReward();		
	}
}
