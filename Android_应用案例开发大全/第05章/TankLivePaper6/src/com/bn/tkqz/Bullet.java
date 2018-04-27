package com.bn.tkqz;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
	int direction;//  0-up 1-right 2-down 3-left
	int x;
	int y;
	int span=Constant.TANK_BULLET_SPAN;	
	Bitmap bitmap;
	public Bullet(Bitmap bitmap,int direction,int x,int y)
	{
		this.bitmap=bitmap;
		this.direction=direction;
		this.x=x;
		this.y=y;
	}
	boolean canGo(int xTemp,int yTemp)//�ж��ܲ����ƶ����µ�λ��
	{
		  boolean canGoFlag=true;
		  if(!Constant.isBulletInGameView(xTemp, yTemp))
		  {
			  canGoFlag=false;
		  }
		  Hero hero=AliveWallPaperTank.hero;
			//�ж��ӵ��Ƿ����Ӣ��
			if
			(
				Constant.oneIsInAnother
				(
						x, y,Constant.BULLET_SIZE, Constant.BULLET_SIZE,
						hero.x, hero.y, Constant.TANK_SIZE, Constant.TANK_SIZE
				)
			)
			{
				if(!hero.isProtected())
				{
					hero.explode();
				}				
				canGoFlag=false;
			}
			//���̹���Ƿ������ϰ���
			if(AliveWallPaperTank.map.isBulletMetWithBarrier(xTemp,yTemp))
			{
				canGoFlag=false;
			}
			return canGoFlag;
	}
	void go()
	{
		int xTemp=x;
		int yTemp=y;
		
		
		//�ӵ��н�
		switch(direction)
		{
			case Tank.DERECTION_UP:
				yTemp=y-span;
			break;
			case Tank.DERECTION_DOWN:
				yTemp=y+span;
			break;	
			case Tank.DERECTION_RIGHT:
				xTemp=x+span;
			break;
			case Tank.DERECTION_LEFT:
				xTemp=x-span;
			break;											
		}
		if(canGo(xTemp,yTemp))//�ж��ܲ����ƶ����µ�λ��
		{
			x=xTemp;
			y=yTemp;
			//�ӵ��ߵ���λ�ú��ж��Ƿ��������
			if(AliveWallPaperTank.map.isBulletMetWithHome(x, y))
			{
				this.explode();
				AliveWallPaperTank.map.home.explode();
			}
		}
		else
		{
			explode();
		}
	}
	void drawSelf(Canvas canvas, Paint paint) 
	{
		canvas.drawBitmap(bitmap, x,y, paint);
	}
	void explode() 
	{
		AliveWallPaperTank.alBullet.remove(this);
	}
}
