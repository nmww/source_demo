	package com.bn.tkqz;
	import java.util.ArrayList;
	public class HeroBulletGoThread extends Thread
	{
		public void run()
		{
			while(AliveWallPaperTank.heroBulletGoFlag)
			{
				try
				{
					ArrayList<HeroBullet> alHeroBullet=new ArrayList<HeroBullet>(AliveWallPaperTank.alHeroBullet);//���Ƶ��ӵ��б�
					
					for(HeroBullet hb:alHeroBullet)//ѭ��ɨ��Ӣ���ӵ��б�
					{
						hb.go();					
						Thread.sleep(10);//ÿ10������һ��
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}		
			}
		}
	}
