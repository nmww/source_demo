package com.bn.tkqz;

public class ProtectHeroThread extends Thread
{
	Hero hero;
	public ProtectHeroThread(Hero hero)
	{
		this.hero=hero;
	}
	@Override
	public void run()
	{
		hero.wearProtector();
		try
		{
			Thread.sleep(Constant.TIME_WEARING_PROTECTOR);//һ��ʱ���ȥ��������
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		hero.removeProtector();
	}
}
