	package com.bn.tkqz;
	import java.util.ArrayList;
	public class TankBulletGoThread extends Thread
	{
		public void run()
		{
			while(AliveWallPaperTank.tankBulletGoFlag)
			{
				try
				{
					ArrayList<Bullet> alBullet=new ArrayList<Bullet>(AliveWallPaperTank.alBullet);//���Ƶ��ӵ��б�
					for(Bullet b:alBullet)
					{
						b.go();
					}
					
					Thread.sleep(50);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
