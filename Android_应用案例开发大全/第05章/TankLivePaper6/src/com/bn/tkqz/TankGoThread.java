	package com.bn.tkqz;
	import java.util.ArrayList;
	public class TankGoThread extends Thread
	{		
		public void run()
		{
			while(AliveWallPaperTank.TankGoFlag)
			{
				try
				{
					ArrayList<Tank> alTank=new ArrayList<Tank>(AliveWallPaperTank.alTank);//�������̹�˴���б�
					for(Tank tank:alTank)//ѭ�����Ƹ���̹�˵��˶�
					{
						tank.go();
					}
					Thread.sleep(100);//ÿ��100����̹���˶�һ��
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
