	package com.bn.tkqz;
	import java.util.ArrayList;
	public class TankSendBulletThread extends Thread
	{
		@Override
		public void run()
		{
			while(AliveWallPaperTank.tankSendBulletFlag)
			{
				try
				{
					ArrayList<Tank> alTank=new ArrayList<Tank>(AliveWallPaperTank.alTank);//��õ�̹���б�
					for(Tank t:alTank)
					{
						if
						(
								Math.random()<Constant.TANK_SEND_BULLET_POSSIBILITY&&
								AliveWallPaperTank.alBullet.size()<Constant.TANK_BULLET_MAX_NUM
						)
						{
							Bullet b=t.sendBullet();
							AliveWallPaperTank.alBullet.add(b);
						}
					}
					
					Thread.sleep(1000);//ÿ��һ���ӷ���һ��
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
