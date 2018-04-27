package com.bn.tkqz;

import static com.bn.tkqz.Constant.*;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Hero {
	//��ʾӢ��״̬�ĳ���
	public static final int WITH_NOTHING=0;
	public static final int WITH_ONE_STAR=1;
	public static final int WITH_TWO_STARS=2;
	public static final int WITH_MORE_STARS=3;
	
	static int HEROLIFE=Constant.HERO_LIFE;//Ӣ�۸���
	int direction=0;//  0-up 1-right 2-down 3-left
	int initX;
	int initY;
	int x;
	int y;
	int span=Constant.HERO_SPAN;//Ӣ��̹�˲�������
	int heroState=Hero.WITH_NOTHING;
	int starCount=0;//�����ǵ�����
	int heroBulletMaxNum=Constant.HERO_BULLET_INIT_MAX_NUM;//Ӣ���ӵ��������
	Bitmap []tanki;
	Bitmap coveringBitmap=AliveWallPaperTank.coveringBitmap;
	StopTankForAMomentThread stopThread=null;//��ʱ�̵߳�����
	BuildHomeThread buildThread=null;//��ʯǽ����������һ��ʱ����߳�����
	ProtectHeroThread protectThread=null;//����̹�˵��߳�����
	private boolean isprotectedFlag=false;
	public Hero(Bitmap[] tanki)
	{
		  this.tanki=tanki;
		  if(AliveWallPaperTank.isShuPing())
		  {
			  initX=GAME_VIEW_WIDTH/2-4*Constant.BARRIER_SIZE-15+Constant.GAME_VIEW_X;
			  initY=GAME_VIEW_HEIGHT-Constant.TANK_SIZE+Constant.GAME_VIEW_Y;
		  }
		  else
		  {
			  initX=GAME_VIEW_WIDTH/2-4*Constant.BARRIER_SIZE-8+Constant.GAME_VIEW_X;
			  initY=GAME_VIEW_HEIGHT-Constant.TANK_SIZE+Constant.GAME_VIEW_Y;
		  }
		  x=initX;
		  y=initY;
	}
	boolean canGo(int xTemp,int yTemp)//�ж��ܲ����ƶ����µ�λ��
	  {
		  boolean canGoFlag=true;
		  //����Ƿ�����Ļ��
		  if(!Constant.isHeroInGameView(xTemp, yTemp))
		  {
			  canGoFlag=false;
		  }
		  //����Ƿ���з�̹����ײ
		  ArrayList<Tank> alTank=new ArrayList<Tank>(AliveWallPaperTank.alTank);
		  for(Tank t:alTank)
		  {
			  if
			  (
				  Constant.oneIsInAnother
				  (
						  xTemp+Constant.TANK_SIZE_REVISE, yTemp+Constant.TANK_SIZE_REVISE, Constant.TANK_SIZE-2*Constant.TANK_SIZE_REVISE, Constant.TANK_SIZE-2*Constant.TANK_SIZE_REVISE, 
						  t.x+Constant.TANK_SIZE_REVISE, t.y+Constant.TANK_SIZE_REVISE, Constant.TANK_SIZE-2*Constant.TANK_SIZE_REVISE, Constant.TANK_SIZE-2*Constant.TANK_SIZE_REVISE
				  )
			  )
			  {
				  canGoFlag=false;
			  }
		  }
		  //���̹���Ƿ������ϰ���
		  if(AliveWallPaperTank.map.isTankMetWithBarrier(xTemp,yTemp))
		  {
			  canGoFlag=false;
		  }
		  return canGoFlag;
	  }
	void go()
	{
		int xTemp=x;
		int yTemp=y;
		  if((AliveWallPaperTank.keyState&0x1)!=0)//up
			{
				direction=Tank.DERECTION_UP;
				yTemp=y-span;
			}
			else if((AliveWallPaperTank.keyState&0x2)!=0)//down
			{
				direction=Tank.DERECTION_DOWN;
				yTemp=y+span;
			}
			else if((AliveWallPaperTank.keyState&0x4)!=0)//left
			{
				direction=Tank.DERECTION_LEFT;
				xTemp=x-span;
			}
			else if((AliveWallPaperTank.keyState&0x8)!=0)//right
			{
				direction=Tank.DERECTION_RIGHT;
				xTemp=x+span;
			}
		  
		  if(canGo(xTemp,yTemp))//�ж��ܲ����ƶ����µ�λ��
			{
			  	//���̹���Ƿ��ڱ��ϴ�
				if(AliveWallPaperTank.map.isHeroMetWithIce(this))
				{//��ǰ�������һ�ξ���
					if(y==yTemp)
					{
						int vx=xTemp-x;
						xTemp+=vx;
					}
					else
					{
						int vy=yTemp-y;
						yTemp+=vy;
					}
				}
				x=xTemp;
				y=yTemp;				
			}
		  if(AliveWallPaperTank.map.isHeroMetWithReward(this))
		  {
			  this.getTheReward(AliveWallPaperTank.map.reward);
			  //���������
			  AliveWallPaperTank.map.reward=null;
			  //�Ե��������һ��
			  AliveWallPaperTank.score+=1;
		  }
	}
	HeroBullet sendBullet()
	{//System.out.println("herolife: "+Hero.HEROLIFE);
		HeroBullet result=null;
		int direction=this.direction;//��õ�ǰ��̹�˵��˶������λ��
		int x=this.x;
		int y=this.y;
				
		switch(direction)//��ʼ���ӵ��ĳ�ʼλ��
		{
			case Tank.DERECTION_UP://up
			 x=x+Constant.TANK_SIZE/2-Constant.BULLET_SIZE/2-1;
			 y=y-Constant.BULLET_SIZE/2;
			break;
			case Tank.DERECTION_DOWN://down
			 x=x+Constant.TANK_SIZE/2-Constant.BULLET_SIZE/2-1;
			 y=y+Constant.TANK_SIZE-Constant.BULLET_SIZE/2;
			break;
			case Tank.DERECTION_RIGHT://right
			 x=x+Constant.TANK_SIZE-Constant.BULLET_SIZE/2;
			 y=y+Constant.TANK_SIZE/2-Constant.BULLET_SIZE/2-1;
			break;
			case Tank.DERECTION_LEFT://left
			 x=x-Constant.BULLET_SIZE/2;
			 y=y+Constant.TANK_SIZE/2-Constant.BULLET_SIZE/2-1;
			break;
		}
		switch(this.heroState)
		{
		case Hero.WITH_NOTHING:
			result=new HeroBulletNormal(direction,x,y);
			break;
		case Hero.WITH_ONE_STAR:
		case Hero.WITH_TWO_STARS:
			result=new HeroBulletFast(direction,x,y);
			break;
		case Hero.WITH_MORE_STARS:
			result=new HeroBulletFastAndStrong(direction,x,y);
			break;
		}
		return result;//�����µĵ��ӵ�
	}
	void explode() 
	{
		Hero.HEROLIFE--;
		if(Hero.HEROLIFE==0)
		{
			AliveWallPaperTank.overGame();
		}
		else
		{
			try
			{
				Thread.sleep(100);//һ��ʱ�������
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			this.backHome();
			AliveWallPaperTank.hero=new Hero(AliveWallPaperTank.heroTanki1); 
		}
	}
	void getTheReward(Reward reward)
	{
		//�Ե�����
		if(reward.getClass()==Star.class)
		{
			if(this.starCount==0)
			{
				this.starCount++;
				this.heroState=Hero.WITH_ONE_STAR;
				this.tanki=AliveWallPaperTank.heroTanki2;				
			}
			else if(this.starCount==1)
			{
				this.starCount++;
				this.heroState=Hero.WITH_TWO_STARS;	
				this.heroBulletMaxNum=Constant.HERO_BULLET_MAX_NUM_MORE;
				this.tanki=AliveWallPaperTank.heroTanki3;
			}
			else if(this.starCount>=2)
			{
				this.heroState=Hero.WITH_MORE_STARS;	
				this.heroBulletMaxNum=Constant.HERO_BULLET_MAX_NUM_MORE;
				this.tanki=AliveWallPaperTank.heroTanki4;
			}
		}
		else if(reward.getClass()==Bomb.class)
		{//�Ե�ը��
			ArrayList<Tank> alTank=new ArrayList<Tank>(AliveWallPaperTank.alTank);//��õ�ǰ�Ѵ��ڵ�̹�˴���б�
			for(Tank t:alTank)
			{
				t.explode();
			}
			
		}
		else if(reward instanceof Life)
		{//�Ե���
			Hero.HEROLIFE++;
		}
		else if(reward instanceof Shovel)
		{//�Ե�����
			//������ʯǽ����������һ��ʱ����߳�
			if(buildThread==null || !buildThread.isAlive())
			{
				buildThread=new BuildHomeThread();
				buildThread.start();
			}
		}
		else if(reward instanceof Protector)
		{//�Ե�������
			//��������Ӣ��̹���߳�
			if(protectThread==null || !protectThread.isAlive())
			{
				protectThread=new ProtectHeroThread(this);
				protectThread.start();
			}
		}
		else if(reward instanceof Timer)
		{//�Ե���ʱ��
			//������ʱ�߳�
			if(stopThread==null || !stopThread.isAlive())
			{
				stopThread=new StopTankForAMomentThread();
				stopThread.start();
			}			
		}
	}
	void drawSelf(Canvas canvas,Paint paint)
	{
		  canvas.drawBitmap(tanki[direction], x,y, paint);
		  if(this.isProtected())
		  {
			  canvas.drawBitmap(coveringBitmap, x,y, paint);
		  }
	}
	//���ϱ������ķ���
	void wearProtector()
	{
		isprotectedFlag=true;
	}
	//ȥ���������ķ���
	void removeProtector()
	{
		isprotectedFlag=false;
	}
	//���̹���Ƿ񱻱����ķ���
	boolean isProtected()
	{
		return isprotectedFlag;		
	}
	//̹�˻ص���ʼλ�õķ���
	void backHome()
	{
		this.x=initX;
		this.y=initY;
		this.direction=Tank.DERECTION_UP;
		AliveWallPaperTank.keyState=0;
	}
}
