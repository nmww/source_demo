	package com.bn.tkqz;
	import java.util.ArrayList;
	public class TankChangeDirectionThread extends Thread 
	{		
		public void run()
		{
			while(AliveWallPaperTank.TankChangeDirectionFlag)
			{
				try
				{
					ArrayList<Tank> alTank=new ArrayList<Tank>(AliveWallPaperTank.alTank);//��ȡ�Ѵ���̹�˴���б�
					
					for(Tank tank:alTank)//ѭ��ÿһ��̹��
					{
						tank.changeDirection();
					}
					Thread.sleep(1000);//ÿ��һ���Ӽ��һ��
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
