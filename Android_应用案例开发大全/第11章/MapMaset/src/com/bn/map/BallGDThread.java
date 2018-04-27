package com.bn.map;
import static com.bn.map.GameSurfaceView.*;
import static  com.bn.map.Constant.*;
public class BallGDThread extends Thread
{
	GameSurfaceView gameSurface;//surfaceView������
	public Boolean flag=true;//�̱߳�־λ
	public static float t=0.1f;//ÿ���ߵ�ʱ��
	public static int ZJS_Time;//�ܼ�ʱ�䣬ÿһ�صģ��ӿ�ʼ����ɿ�ʼ��ʱ
	public static float ballRD;
	public float ballGX;
	public float ballGZ;//ÿ�ο������ٶ�
	
	
	int ballXx=0;//�˸����ڵ�����
	int ballZz=0;
	public static Boolean flagSY=true;//�ж��Ƿ��������־      
	public BallGDThread(GameSurfaceView gameSurface)
	{
		this.gameSurface=gameSurface;
		ballRD=ballR/2;
	}
	@Override   
	public void run()
	{
		while(flag)
		{
			ballGX=GameSurfaceView.ballGX;//�������ٶ�
			ballGZ=GameSurfaceView.ballGZ;
			try{
			PZJC(ballX,ballZ,ballVX*t+ballGX*t*t/2,ballVZ*t+ballGZ*t*t/2);//�ж���ײ�������
			}catch (Exception tt) {
				tt.printStackTrace();
			}
			ballVX+=ballGX*t;
			ballVZ+=ballGZ*t;//�����ٶ�
			
			ballX=ballX+ballVX*t+ballGX*t*t/2;//VT+1/2A*T*T
			ballZ=ballZ+ballVZ*t+ballGZ*t*t/2;//����λ��		
			gameSurface.ball.mAngleX+=(float)Math.toDegrees(((ballVZ*t+ballGZ*t*t/2))/ballR);
			gameSurface.ball.mAngleZ-=(float)Math.toDegrees((ballVX*t+ballGX*t*t/2)/ballR);//��ת�ĽǶ�
			if(Math.abs((ballVZ*t+ballGZ*t*t/2))<0.005f)//�����ǰǰ��ֵС�ڵ���ֵ������Ӧ��ת������ǹ���
			{
				gameSurface.ball.mAngleX=0;
			}
			if(Math.abs(ballVX*t+ballGX*t*t/2)<0.005f)
			{
				gameSurface.ball.mAngleZ=0;
			}
			//*********************�ж��Ƿ�ײ��*****************//
			pdZJ();//�жϽ�������������Ӧ�Ĳ���
			
			if(!flagSY)//�������������
			{
				flagSY=true;//��ʼ����ʼ
				if(ballXx==ballMbX&&ballZz==ballMbZ)//�����Ӯ��
				{
					try 
					{
						Thread.sleep(1000);
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					flag=false;
					gameSurface.father.curr_grade=GD_TIME[guankaID]-STIME;
					gameSurface.father.hd.sendEmptyMessage(1);//����Ӯ�Ľ���
				}
				else//�����ǽ�����
				{
					try
			        {
						Thread.sleep(1000);//ͣ��1��
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				ballX=ballCsX*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;
				ballZ=ballCsZ*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
				ballY=ballR;
//				ZJS_Time=0;
				}
				ballVX=0;
				ballVZ=0;//�����ٶȶ���Ϊ��
//				ballGX=0;
//				ballGZ=0;
			}		
			ballVX*=V_TENUATION;
			ballVZ*=V_TENUATION;//˥��
			if(Math.abs(ballVX)<0.04)//���ٶ�С��ĳ������ֵʱ
			{
				ballVX=0;//�ٶȹ���
				gameSurface.ball.mAngleZ=0;//������ѡ���ֵ��Ϊ��
			}
			if(Math.abs(ballVZ)<0.04)
			{
				ballVZ=0;
				gameSurface.ball.mAngleX=0;
			}
			
			
			ZJS_Time+=50;//ÿ�ֵ���ʱ���
			STIME=(ZJS_Time/1000);//�߹���ʱ������
			if(GD_TIME[guankaID]-STIME<=0)//���ʱ������㣬˵��û��ͨ��
			{
				flag=false;
				gameSurface.father.hd.sendEmptyMessage(2);
			}
			try //��Ϣ50���룬������һ��ѭ��
			{
				Thread.sleep(50);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	public static void initDiTu()//��ʼ����ͼ����
	{
		guankaID%=MAPP.length;//��ֹ����Խ��
        ballY=0;
        MAP=MAPP[guankaID];//��ͼ����
        
        Wall walll=new Wall();//�½���ͼ
        try
        {
			Thread.sleep(1000);//ͣ��1��
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}		
		wall=walll;//���½���ͼ������ǰ�������Ƶ�ͼ
		ballCsX=CAMERA_COL_ROW[guankaID][0];//��ʼ�����ڵĳ�ʼ����
        ballCsZ=CAMERA_COL_ROW[guankaID][1];
        
        ballMbX=CAMERA_COL_ROW[guankaID][2];//����ȨĿ������
        ballMbZ=CAMERA_COL_ROW[guankaID][3];
		MAP_OBJECT=MAP_OBJECTT[guankaID];//�õ�ͼ�Ķ�����
        ballX=ballCsX*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;//���򻭵���ʼλ��
		ballZ=ballCsZ*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
		STIME=GD_TIME[guankaID];//����ʱ�䣬��ԭ
		ZJS_Time=0;//��ʱ�����
	}
	public Boolean PZJC(float ballX,float ballZ,float BX,float BZ)
	{
		Boolean flag=false;
		ballX=MAP[0].length*UNIT_SIZE/2+ballX;//����ͼ�Ƶ�XZ�������������
		ballZ=MAP.length*UNIT_SIZE/2+ballZ;
		if(BZ>0)//�����Z���������˶�
		{
			for(int i=(int)((ballZ+ballR)/UNIT_SIZE);i<=(int)((ballZ+ballR+BZ)/UNIT_SIZE);i++)
				//ѭ����������һ�´����������ӣ���ô�ӵ�һ�����ӿ�ʼ�ж�
			{
				if(MAP[i][(int)(ballX/UNIT_SIZE)]==BKTG&&MAP[i-1][(int)(ballX/UNIT_SIZE)]==KTG){//�ж��Ƿ���ǽ����
					ballVZ=-ballVZ*VZ_TENUATION;//���ٶ��÷���������
					if((GameSurfaceView.ballZ+ballVZ*t+ballGZ*t*t/2)>=(i*UNIT_SIZE-ballR-MAP.length*UNIT_SIZE/2))//����ٶȵ������ǻᴩǽ����ô�����ٶȹ��㣬�������ں�ǽ�ڽ����ŵĵط�
					{
						GameSurfaceView.ballZ=(i*UNIT_SIZE-ballR-MAP.length*UNIT_SIZE/2);
						ballVZ=0;
						ballGZ=0;
					}
					else{					
					gameSurface.father.playSound(1, 0);//�����ƶ�����
					gameSurface.father.shake();//��
					}
					flag=true;//��־λ��Ϊtrue
				}
				else if(BX<=0&&((int)((ballX-ballR)/UNIT_SIZE)>=0)&&(MAP[i][(int)((ballX-ballR)/UNIT_SIZE)]==BKTG)
						&&MAP[i-1][(int)((ballX-ballR)/UNIT_SIZE)]==KTG)//��������Z����������ǣ����ܻ�������
				{
					float sina=(ballX-((int)((ballX-ballR)/UNIT_SIZE)+1)*UNIT_SIZE)/ballR;//�õ�����ʱ�ĽǶ����ֵ
					float cosa=(float)Math.sqrt(1-sina*sina);
					ballVX=jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//�õ����Ǻ���ٶ�
					ballVZ=-jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					ballGX=0;
					ballGZ=0;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ){//�����ײ�ܴ��򲥷�����
					gameSurface.father.playSound(1, 0);//�����ƶ�����
					gameSurface.father.shake();
					}else if(Math.abs(ballVZ)<SD_TZZ)//����ٶ�С�ڵ���ֵ���򲻵��𣬶����ٶ�ֵΪ��
					{
						GameSurfaceView.ballZ=i*UNIT_SIZE-ballR-MAP.length*UNIT_SIZE/2;
						ballVZ=0;
						ballGZ=0;
					}else if(Math.abs(ballVX)<SD_TZZ)//����ٶ�С�ڵ���ֵ���򲻵��𣬶����ٶ�ֵΪ��
					{
						GameSurfaceView.ballX=(1+i)*UNIT_SIZE-ballR-MAP.length*UNIT_SIZE/2;
						ballVZ=0;
						ballGZ=0;
					}
					flag=true;
				}
				else if(BX>=0&&((int)((ballX+ballR)/UNIT_SIZE)>=0)&&MAP[i][(int)((ballX+ballR)/UNIT_SIZE)]==BKTG
						&&MAP[i-1][(int)((ballX+ballR)/UNIT_SIZE)]==KTG){//�����Z�������˶�ʱ������
					float sina=(ballX-((int)((ballX+ballR)/UNIT_SIZE)*UNIT_SIZE))/ballR;
					float cosa=(float)Math.sqrt(1-sina*sina);
					ballVX=-jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					ballVZ=-jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ){
						gameSurface.father.playSound(1, 0);//�����ƶ�����
						gameSurface.father.shake();
						}else{
							ballGX=0;
							ballGZ=0;
						}
					flag=true;
				}
			}	
		}
		
		if(BX>0)//�����X����������
		{
			for(int i=(int)((ballX+ballR)/UNIT_SIZE);i<=(int)((ballX+ballR+BX)/UNIT_SIZE);i++)
			{//ѭ����������һ�´����������ӣ���ô�ӵ�һ�����ӿ�ʼ�ж�
				if(MAP[(int)(ballZ/UNIT_SIZE)][i]==BKTG&&MAP[(int)(ballZ/UNIT_SIZE)][i-1]==KTG){//���������			
					
					ballVX=-ballVX*VZ_TENUATION;//�ٶ��÷���������
					if((GameSurfaceView.ballX+ballVX*t+ballGX*t*t/2)>
					((i)*UNIT_SIZE-ballR-MAP[0].length*UNIT_SIZE/2))//����Ѿ�����ǽ���ˣ���ô�ٶ�Ϊ��
					{
						GameSurfaceView.ballX=(i)*UNIT_SIZE-ballR-MAP[0].length*UNIT_SIZE/2;
						ballGX=0;//���ٶȺ��ٶ�����Ϊ��
						ballVX=0;
					}
					else
					{						
					gameSurface.father.playSound(1, 0);//�����ƶ�����
					gameSurface.father.shake();
					}
					if(ballGX>0)//�ٶ�С�ڵ���ֵ�����
					{
						ballGX=0;
					}
					 flag=true;
					 
				}
				else if(BZ<=0&&((int)((ballZ-ballR)/UNIT_SIZE)>=0)&&(MAP[(int)((ballZ-ballR)/UNIT_SIZE)][i]==BKTG)
						&&(MAP[(int)((ballZ-ballR)/UNIT_SIZE)][i-1]==KTG))//������ײ��
				{
					float sina=(ballZ-((int)((ballZ-ballR)/UNIT_SIZE)+1)*UNIT_SIZE)/ballR;//�õ���ؽǶȵ�����ֵ������ֵ
					float cosa=(float)Math.sqrt(1-sina*sina);
					ballVX=-jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					ballVZ=jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//�õ����Ǻ���ٶ�
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ){//�ٶȴﵽһ�����Ų�����������
						gameSurface.father.playSound(1, 0);//�����ƶ�����
						gameSurface.father.shake();//��
						}else{
							ballGX=0;//�ٶȹ���
							ballGZ=0;
						}
					flag=true;
				}
				else if(BZ>=0&&((int)((ballZ+ballR)/UNIT_SIZE)>=0)&&MAP[(int)((ballZ+ballR)/UNIT_SIZE)][i]==BKTG
						&&(MAP[(int)((ballZ+ballR)/UNIT_SIZE)][i-1]==KTG)){//����ұ�����
					float sina=-(ballZ-((int)((ballZ+ballR)/UNIT_SIZE))*UNIT_SIZE)/ballR;//�õ����ֵ
					float cosa=(float)Math.sqrt(1-sina*sina);
					ballVX=-jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//�õ����Ǻ���ٶ�
					ballVZ=-jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ)
					{
						gameSurface.father.playSound(1, 0);//�����ƶ�����
						gameSurface.father.shake();//��
						}else{
							ballGX=0;
							ballGZ=0;//�����ٶȹ���
						}
					flag=true;
				}
			}		
		}
		 if(BX<0)
		{
			for(int i=(int)((ballX-ballR)/UNIT_SIZE);i>=(int)((ballX-ballR+BX)/UNIT_SIZE);i--)
			{//ѭ���ж��Ƿ�����
				if(MAP[(int)(ballZ/UNIT_SIZE)][i]==BKTG&&MAP[(int)(ballZ/UNIT_SIZE)][i+1]==KTG)
				{//�������
					
						ballVX=-ballVX*VZ_TENUATION;//�ٶ��÷���������
						if((GameSurfaceView.ballX+ballVX*t+ballGX*t*t/2)<
						((1+i)*UNIT_SIZE+ballR-MAP[0].length*UNIT_SIZE/2))//����Ѿ�����ǽ���ˣ���ô�ٶȹ���
						{
							GameSurfaceView.ballX=(1+i)*UNIT_SIZE+ballR-MAP[0].length*UNIT_SIZE/2;
							ballGX=0;//���ٶȺ��ٶ�����Ϊ��
							ballVX=0;
						}
						else
						{						
						gameSurface.father.playSound(1, 0);//�����ƶ�����
						gameSurface.father.shake();
						}
					
					if(ballVX<0)
					{
						ballVX=-ballVX;
					}
					flag=true;
//					return true;
				}
				else if(BZ<=0&&((int)((ballZ-ballR)/UNIT_SIZE)>=0)&&(MAP[(int)((ballZ-ballR)/UNIT_SIZE)][i]==BKTG)
						&&MAP[(int)((ballZ-ballR)/UNIT_SIZE)][i+1]==KTG)//�����ײ��
				{
					float sina=(ballZ-((int)((ballZ-ballR)/UNIT_SIZE)+1)*UNIT_SIZE)/ballR;
					float cosa=(float)Math.sqrt(1-sina*sina);//�õ���ص�ֵ
					ballVX=jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//�õ����Ǻ���ٶ�
					ballVZ=jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ)//���Ƿ�Ҫ��������
					{
						gameSurface.father.playSound(1, 0);//�����ƶ�����
						gameSurface.father.shake();//��
						}else{
							ballGX=0;
							ballGZ=0;
						}
					flag=true;
				}
				else if(BZ>=0&&((int)((ballZ+ballR)/UNIT_SIZE)>=0)&&MAP[(int)((ballZ+ballR)/UNIT_SIZE)][i]==BKTG
						&&MAP[(int)((ballZ+ballR)/UNIT_SIZE)][i+1]==KTG){//����ұ�����
					float sina=-(ballZ-((int)((ballZ+ballR)/UNIT_SIZE))*UNIT_SIZE)/ballR;
					float cosa=(float)Math.sqrt(1-sina*sina);//�õ����ֵ
					ballVX=jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//�õ����Ǻ���ٶ�
					ballVZ=-jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ)//�ж��Ƿ�Ҫ��������
					{
						gameSurface.father.playSound(1, 0);//�����ƶ�����
						gameSurface.father.shake();//��
						}else{
							ballGX=0;
							ballGZ=0;
						}
					flag=true;
				}
			}
		}
		
		if(BZ<0)//��Z�Ḻ�������˶�ʱ
		{
			for(int i=(int)((ballZ-ballR)/UNIT_SIZE);i>=(int)((ballZ-ballR+BZ)/UNIT_SIZE);i--)
			{//ѭ�����Ƿ�������
				if(MAP[i][(int)(ballX/UNIT_SIZE)]==BKTG&&MAP[i+1][(int)(ballX/UNIT_SIZE)]==KTG){
					ballVZ=-ballVZ*VZ_TENUATION;//���ٶ��÷���������
					if((GameSurfaceView.ballZ+ballVZ*t+ballGZ*t*t/2)<=((1+i)*UNIT_SIZE+ballR-MAP.length*UNIT_SIZE/2))
					{//����������ٶ��£����Ƿ�ᴩǽ
						GameSurfaceView.ballZ=(1+i)*UNIT_SIZE+ballR-MAP.length*UNIT_SIZE/2;
						ballVZ=0;
						ballGZ=0;
					}
					else
					{					
					gameSurface.father.playSound(1, 0);//�����ƶ�����
					gameSurface.father.shake();//��
					}					
					if(ballVZ<0)//����ٶȻ�С���㣬���÷�
					{
						ballVZ=-ballVZ;
					}					
					flag=true;
				}
				else if(BX<=0&&((int)((ballX-ballR)/UNIT_SIZE)>=0)&&(MAP[i][(int)((ballX-ballR)/UNIT_SIZE)]==BKTG)
						&&MAP[i+1][(int)((ballX-ballR)/UNIT_SIZE)]==KTG)//�������
				{
					float sina=(ballX-((int)((ballX-ballR)/UNIT_SIZE)+1)*UNIT_SIZE)/ballR;//�õ��Ƕ����ֵ
					float cosa=(float)Math.sqrt(1-sina*sina);					
					ballVX=jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//�õ����Ǻ���ٶ�
					ballVZ=jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ)//�ж��Ƿ�Ҫ��������
					{
						gameSurface.father.playSound(1, 0);//�����ƶ�����
						gameSurface.father.shake();//��
					}
					else
					{
							ballGX=0;
							ballGZ=0;
					}
					flag=true;
				}
				else if(BX>=0&&((int)((ballX+ballR)/UNIT_SIZE)<MAP[0].length)&&MAP[i][(int)((ballX+ballR)/UNIT_SIZE)]==BKTG
						&&MAP[i+1][(int)((ballX+ballR)/UNIT_SIZE)]==KTG){//�ұ�����
					float sina=-(ballX-((int)((ballX+ballR)/UNIT_SIZE))*UNIT_SIZE)/ballR;
					float cosa=(float)Math.sqrt(1-sina*sina);//�õ����ֵ
					ballVX=-jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//�õ����Ǻ���ٶ�
					ballVZ=jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ)//���Ƿ�Ҫ��������
					{
						gameSurface.father.playSound(1, 0);//�����ƶ�����
						gameSurface.father.shake();//��
					}
					else
					{
							ballGX=0;
							ballGZ=0;
					}
					flag=true;
				}
				
			}	
		}
		return flag;
	}
	public void pdZJ()//�ж��Ƿ��������
	{
		ballXx=(int)((MAP[0].length*UNIT_SIZE/2+ballX)/UNIT_SIZE);//�˸����ڵĶ�Ӧ����������
		ballZz=(int)((MAP.length*UNIT_SIZE/2+ballZ)/UNIT_SIZE);
		if(MAP_OBJECT[ballZz][ballXx]==1){//���ϵĸ����Ƕ�
				if((float)Math.sqrt(
				(ballX-ballXx*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)*(ballX-ballXx*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)
				+(ballZ-ballZz*UNIT_SIZE+MAP.length*UNIT_SIZE/2)*(ballZ-ballZz*UNIT_SIZE+MAP.length*UNIT_SIZE/2))
				<ballR+ballRD//���ж������Ƿ��ڶ���
				){//����������
					flagSY=false;//��־Ϊ
					ballX=ballXx*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;//���򻭵�����
					ballZ=ballZz*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
					ballY=0;
					
		}}
		else if(MAP_OBJECT[ballZz][ballXx+1]==1)//���µĸ����Ƕ�
		{
			if((float)Math.sqrt(
					(ballX-(1+ballXx)*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)*(ballX-(1+ballXx)*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)
					+(ballZ-ballZz*UNIT_SIZE+MAP.length*UNIT_SIZE/2)*(ballZ-ballZz*UNIT_SIZE+MAP.length*UNIT_SIZE/2))
					<ballR+ballRD//���ж������Ƿ��ڶ���
					){
				flagSY=false;//����������
				ballX=(1+ballXx)*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;
				ballZ=ballZz*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
				ballY=0;
				ballXx=ballXx+1;
			}
		}
		else if(MAP_OBJECT[ballZz+1][ballXx+1]==1){//���µĸ����Ƕ�
			if((float)Math.sqrt(
					(ballX-(1+ballXx)*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)*(ballX-(1+ballXx)*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)
					+(ballZ-(1+ballZz)*UNIT_SIZE+MAP.length*UNIT_SIZE/2)*(ballZ-(1+ballZz)*UNIT_SIZE+MAP.length*UNIT_SIZE/2))
					<ballR+ballRD//���ж������Ƿ��ڶ���
					){
				flagSY=false;//����������
				ballX=(1+ballXx)*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;
				ballZ=(ballZz+1)*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
				ballY=0;
				ballXx=ballXx+1;
				ballZz=ballZz+1;
			}
		}
		else if(MAP_OBJECT[ballZz+1][ballXx]==1){//���ϵĸ����Ƕ�
			if((float)Math.sqrt(
					(ballX-ballXx*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)*(ballX-ballXx*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)
					+(ballZ-(1+ballZz)*UNIT_SIZE+MAP.length*UNIT_SIZE/2)*(ballZ-(1+ballZz)*UNIT_SIZE+MAP.length*UNIT_SIZE/2))
					<ballR+ballRD//���ж������Ƿ��ڶ���
					){
						flagSY=false;//����������
						ballX=ballXx*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;
						ballZ=(ballZz+1)*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
						ballY=0;
						ballZz=ballZz+1;
			}
		}
		
	}
	public float jsSDX(float vx,float vz,float cosa,float sina)//������ʱ����X������ٶȣ�����������Ϊ���Ḻ����ǰ��ʱ
	{
	float vvx;
	vvx=-2*vz*sina*cosa+vx*cosa*cosa-vx*sina*sina;//�����ʱX�᷽����ٶ�
		return Math.abs(vvx);//����ֵ
	}
	public float jsSDZ(float vx,float vz,float cosa,float sina)//������ʱ����Z������ٶȡ�����������Ϊ���Ḻ����ǰ��ʱ
	{
	float vvz;	
	vvz=vz*sina*sina-vz*cosa*cosa+2*vx*cosa*sina;//�����ʱ��Z�����ϵ��ٶ�
		return Math.abs(vvz);
	}
}
