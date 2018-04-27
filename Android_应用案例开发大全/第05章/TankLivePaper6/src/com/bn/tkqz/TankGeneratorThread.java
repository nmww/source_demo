	package com.bn.tkqz;
	
	import static com.bn.tkqz.Constant.*;
	public class TankGeneratorThread extends Thread
	{		 
		public void run()
		{
			while(AliveWallPaperTank.TankGeneratorFlag)
			{
				if
				(this.canGenerateATank())
				{
					try
					{
						int direction=(int)(Math.random()*4);//����̹�˸����������
						//����̹�˸������λ��
						int x=Constant.GAME_VIEW_X;
						int y=Constant.GAME_VIEW_Y;
						int i=(int)(Math.random()*3);
						switch(i)//������ɵ�λ�ò���̫���ߣ����������ײ��������
						{
						case 0:
							x+=1;
							y+=1;
							break;
						case 1:
							x+=GAME_VIEW_WIDTH-Constant.TANK_SIZE-1;
							y+=1;
							break;
						case 2:
							x+=(GAME_VIEW_WIDTH-Constant.TANK_SIZE)/2;
							y+=1;
							break;
						}
//						System.out.println("~~~~~~~~x,y = ("+x+","+y+")~~~~~~~~~~");
						Tank tank=null;
						i=(int)(Math.random()*6);
						switch(i)
						{
						case 0:
							tank=new TankNormal(direction,x,y);//����̹�˶���1
							break;
						case 1:
							tank=new TankFast(direction,x,y);//����̹�˶���2
							break;
						case 2:
							tank=new TankStrong(direction,x,y);//����̹�˶���3
							break;
						case 3:
							tank=new RedTankNormal(direction,x,y);//������̹�˶���1
							break;
						case 4:
							tank=new RedTankFast(direction,x,y);//������̹�˶���2
							break;
						case 5:
							tank=new RedTankStrong(direction,x,y);//������̹�˶���3
							break;
						}
						if(tank.canGo(x, y))//����̹�˵�λ�ú����ټ���̹���б�
						{
							AliveWallPaperTank.alTank.add(tank);//������̹����ӵ�̹���б�
						}
						
						Thread.sleep(5000);//ÿ������������һ���µ�̹��				
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}				
			}
		}
		private boolean canGenerateATank()
		{
			if
			(
					!AliveWallPaperTank.isCurrentMissionCompleted()&&//��ǰ����û�����
					AliveWallPaperTank.countTankDestoryed+AliveWallPaperTank.alTank.size()<Constant.TANK_TOTAL_NUM&&
					AliveWallPaperTank.alTank.size()<Constant.TANK_MAX_NUM_IN_GAME_VIEW
			)
			{
				return true;
			}
			return false;
			
		}
	}
