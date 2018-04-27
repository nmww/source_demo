package com.bn.tkqz;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BattleMap
{
	
	//所有关卡的地图
	private final byte[][][] MAP_SP=
	{
			
			{//crazy tank 关卡代码：  行数30， 列数：23
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,5,5,0,0,5,0,0,5,5,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,5,5,0,0,5,0,0,5,5,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0},
				{0,0,0,0,0,5,5,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0,0},
				{0,0,0,0,5,5,0,0,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0},
				{0,0,0,5,5,0,0,2,2,0,0,0,0,0,2,2,0,0,5,5,0,0,0},
				{0,0,3,0,0,0,0,2,2,0,0,0,0,0,2,2,0,0,0,0,3,0,0},
				{0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,0},
				{0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0},
				{0,0,0,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,0,0,0},
				{0,0,0,0,5,5,0,0,0,2,2,2,2,2,0,0,0,5,5,0,0,0,0},
				{0,0,0,0,0,5,5,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0,0},
				{0,0,0,0,0,0,5,0,3,0,3,0,3,0,3,0,5,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,3,0,3,0,3,0,3,0,3,0,0,0,0,0,0,0},
				{0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0},
				{0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0},
				{0,0,1,0,0,0,1,1,1,1,1,0,0,0,0,1,1,1,1,1,0,0,0},
				{0,0,1,0,0,1,0,0,1,0,1,1,0,0,0,1,1,0,0,0,0,0,0},
				{0,0,1,0,1,0,0,0,1,0,1,0,1,0,0,1,1,0,0,0,0,0,0},
				{0,0,1,1,0,0,0,0,1,0,1,0,0,1,0,1,1,0,0,0,0,0,0},
				{0,0,1,0,1,0,0,0,1,0,1,0,0,0,1,1,1,0,1,1,1,0,0},
				{0,0,1,0,0,1,0,0,1,0,1,0,0,0,0,1,1,0,0,1,1,0,0},
				{0,0,1,0,0,0,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,0,0},
				{0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0}
			}
	};
	//所有关卡的地图
	private final byte[][][] MAP_HP=
	{
		{//crazy tank 关卡代码  （行数：23， 列数：30）
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,1,1,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0,0,0,1,1,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,1,1,5,5,1,1,0,0,0,0,1,1,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,2,2,5,5,5,5,5,5,2,2,0,0,1,1,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,1,5,5,5,5,1,1,5,5,5,5,1,1,2,2,1,0,0,0,0,0},
			{0,0,0,0,0,0,2,2,5,5,5,5,1,1,1,1,1,1,5,5,5,5,2,2,1,0,0,0,0,0},
			{0,0,0,0,2,2,5,5,5,5,1,1,1,1,1,1,1,1,1,1,5,5,5,5,2,2,0,0,0,0},
			{0,0,1,1,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,1,1,0,0},
			{0,0,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,0,0},
			{2,2,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,2,2},
			{5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5},
			{5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5},
			{0,0,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,0,0},
			{0,0,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,0,0},
			{0,0,5,5,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,5,5,0,0},
			{0,0,5,5,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,5,5,0,0},
			{0,0,5,5,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,5,5,0,0},
			{0,0,5,5,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,5,5,0,0},
			{0,0,5,5,1,1,1,1,0,0,0,0,1,1,0,0,1,1,0,0,0,0,1,1,1,1,5,5,0,0},
			{0,0,5,5,1,1,1,1,0,0,0,0,1,1,0,0,1,1,0,0,0,0,1,1,1,1,5,5,0,0}
		}
	};
	//当前关卡数
	private int missionNum=1;
	//当前关卡的地图
	private byte[][] mapData; //注意，地图数组为byte类型
	private Bitmap brickBitmap=AliveWallPaperTank.brickBitmap;
	private Bitmap stoneBitmap=AliveWallPaperTank.stoneBitmap;
	private Bitmap seaBitmap=AliveWallPaperTank.seaBitmap;
	private Bitmap iceBitmap=AliveWallPaperTank.iceBitmap;
	private Bitmap grassBitmap=AliveWallPaperTank.grassBitmap;
	private Bitmap homeBitmap=AliveWallPaperTank.homeBitmap;
	private Barrier []barrier=
	{
			new Rode(),//0代表路
			new Brick(brickBitmap),//1代表砖墙
			new Stone(stoneBitmap),//2代表石墙
			new Sea(seaBitmap),//3代表海洋
			new Ice(iceBitmap),//4代表冰
			new Grass(grassBitmap),//5代表草地，画在上层
	};
	//创建老窝对象
	int xHome;//老窝的x,y坐标
	int yHome;
	Home home;
	//奖励物
	Reward reward=null;
	void intiMapData()
	{
		if(AliveWallPaperTank.isShuPing())
		{
			mapData=copyMapData(MAP_SP[missionNum-1]);
		}
		else
		{
			mapData=copyMapData(MAP_HP[missionNum-1]);
		}
		//创建老窝对象
		xHome=(mapData[0].length/2-1)*Constant.BARRIER_SIZE+Constant.GAME_VIEW_X;//老窝的x,y坐标
		yHome=(mapData.length-2)*Constant.BARRIER_SIZE+Constant.GAME_VIEW_Y;	
		home=new Home(homeBitmap,xHome,yHome);
		
	}
	//拷贝二维地图数组的方法
	byte[][] copyMapData(byte[][] mapData)
	{
		byte[][] result=null;
		result=new byte[mapData.length][mapData[0].length];
		for(int i=0;i<mapData.length;i++)
		{
			for(int j=0;j<mapData[0].length;j++)
			{
				result[i][j]=mapData[i][j];
			}
		}
		return result;
	}
	void drawSelfBelow(Canvas canvas,Paint paint)//要求画在下层的
	{
		for(int i=0;i<mapData.length;i++)//画障碍物
		{
			for(int j=0;j<mapData[i].length;j++)
			{
				if(mapData[i][j]!=Barrier.GRASS)//画在下层的代号
				{
					barrier[mapData[i][j]].drawSelf(canvas, paint, j*Constant.BARRIER_SIZE+Constant.GAME_VIEW_X, i*Constant.BARRIER_SIZE+Constant.GAME_VIEW_Y);
				}
			}
		}
		home.drawSelf(canvas, paint);//画老窝
	}
	void drawSelfAbove(Canvas canvas,Paint paint)//要求画在上层的
	{
		for(int i=0;i<mapData.length;i++)
		{
			for(int j=0;j<mapData[i].length;j++)
			{
				if(mapData[i][j]==Barrier.GRASS)//画在上层的代号
				{
					barrier[mapData[i][j]].drawSelf(canvas, paint, j*Constant.BARRIER_SIZE+Constant.GAME_VIEW_X, i*Constant.BARRIER_SIZE+Constant.GAME_VIEW_Y);
				}
			}
		}
		if(reward!=null)
		{
			reward.drawSelf(canvas, paint);
		}
	}
	//检测坦克（包括英雄坦克）是否遇到障碍物的方法
	boolean isTankMetWithBarrier(int xTemp,int yTemp)
	{
		for(int i=0;i<mapData.length;i++)
		{
			for(int j=0;j<mapData[i].length;j++)
			{
				if(mapData[i][j]==Barrier.BRICK||mapData[i][j]==Barrier.STONE||mapData[i][j]==Barrier.SEA)
				{//如果遇到障碍物，
					if
					  (
						  Constant.oneIsInAnother//这里的修正值非常重要！！！
						  (
								  xTemp+Constant.TANK_SIZE_REVISE-1, yTemp+Constant.TANK_SIZE_REVISE-1, Constant.TANK_SIZE-2*Constant.TANK_SIZE_REVISE-3, Constant.TANK_SIZE-2*Constant.TANK_SIZE_REVISE-3, 
								  j*Constant.BARRIER_SIZE+Constant.GAME_VIEW_X, i*Constant.BARRIER_SIZE+Constant.GAME_VIEW_Y, Constant.BARRIER_SIZE, Constant.BARRIER_SIZE
						  )
					  )
					  {
						  return true;
					  }
				}
			}
		}
		return false;
	}
	//英雄坦克是否在冰上打滑
	boolean isHeroMetWithIce(Hero hero)//只有英雄坦克才在冰上打滑，敌方坦克不打滑
	{
		for(int i=0;i<mapData.length;i++)
		{
			for(int j=0;j<mapData[i].length;j++)
			{
				if(mapData[i][j]==Barrier.ICE)
				{//如果遇到障碍物，
					if
					  (
						  Constant.oneIsInAnother
						  (
								  hero.x, hero.y, Constant.TANK_SIZE, Constant.TANK_SIZE, 
								  j*Constant.BARRIER_SIZE+Constant.GAME_VIEW_X, i*Constant.BARRIER_SIZE+Constant.GAME_VIEW_Y, Constant.BARRIER_SIZE, Constant.BARRIER_SIZE
						  )
					  )
					  {
						  return true;
					  }
				}
			}
		}
		return false;
	}
	//子弹是否击中障碍物
	boolean isBulletMetWithBarrier(int xTemp,int yTemp)
	{
		for(int i=0;i<mapData.length;i++)
		{
			for(int j=0;j<mapData[i].length;j++)
			{
				if(mapData[i][j]==Barrier.BRICK||mapData[i][j]==Barrier.STONE)
				{//如果遇到障碍物，
					if
					  (
						  Constant.oneIsInAnother
						  (
								  xTemp, yTemp, Constant.BULLET_SIZE, Constant.BULLET_SIZE, 
								  j*Constant.BARRIER_SIZE+Constant.GAME_VIEW_X, i*Constant.BARRIER_SIZE+Constant.GAME_VIEW_Y, Constant.BARRIER_SIZE, Constant.BARRIER_SIZE
						  )
					  )
					  {
							if(mapData[i][j]==Barrier.BRICK)//将砖打碎
							{
								mapData[i][j]=Barrier.RODE;
							}
							return true;
					  }
				}
			}
		}
		return false;
	}
	//加强子弹是否击中障碍物
	boolean isStrongBulletMetWithBarrier(int xTemp,int yTemp)
	{
		for(int i=0;i<mapData.length;i++)
		{
			for(int j=0;j<mapData[i].length;j++)
			{
				if(mapData[i][j]==Barrier.BRICK||mapData[i][j]==Barrier.STONE)
				{//如果遇到障碍物，
					if
					  (
						  Constant.oneIsInAnother
						  (
								  xTemp, yTemp, Constant.BULLET_SIZE, Constant.BULLET_SIZE, 
								  j*Constant.BARRIER_SIZE+Constant.GAME_VIEW_X, i*Constant.BARRIER_SIZE+Constant.GAME_VIEW_Y, Constant.BARRIER_SIZE, Constant.BARRIER_SIZE
						  )
					  )
					  {
							if(mapData[i][j]==Barrier.BRICK)//将砖打碎
							{
								mapData[i][j]=Barrier.RODE;
							}
							if(mapData[i][j]==Barrier.STONE)//将砖打碎
							{
								mapData[i][j]=Barrier.RODE;
							}
							return true;
					  }
				}
			}
		}
		return false;
	}
	//子弹是否击中老窝
	boolean isBulletMetWithHome(int x,int y)
	{
		if
		(
				Constant.oneIsInAnother
				(
						x, y, Constant.BULLET_SIZE, Constant.BULLET_SIZE, 
						home.x,home.y,Constant.HOME_SIZE,Constant.HOME_SIZE
				)	
		)
		{
			return true;
		}
		
		return false;
	}
	//英雄坦克是否吃到奖励物
	boolean isHeroMetWithReward(Hero hero)
	{
		if
		(
				reward!=null&&
				Constant.oneIsInAnother
				(
						hero.x, hero.y, Constant.TANK_SIZE, Constant.TANK_SIZE,
						reward.x,reward.y,Constant.REWARD_SIZE,Constant.REWARD_SIZE
				)	
		)
		{
			return true;
		}
		return false;		
	}
	//用石墙将老窝保护起来的方法
	void buildHomeWithStone()
	{
		int row=mapData.length;
		int col=mapData[0].length;
		//初始化砖墙，保护老窝
		mapData[row-1][col/2-2]=Barrier.STONE;//左
		mapData[row-1][col/2-3]=Barrier.STONE;
		mapData[row-2][col/2-2]=Barrier.STONE;
		mapData[row-2][col/2-3]=Barrier.STONE;
		
		mapData[row-1][col/2+1]=Barrier.STONE;//右
		mapData[row-1][col/2+2]=Barrier.STONE;
		mapData[row-2][col/2+1]=Barrier.STONE;
		mapData[row-2][col/2+2]=Barrier.STONE;
		
		mapData[row-3][col/2-2]=Barrier.STONE;//左上
		mapData[row-3][col/2-3]=Barrier.STONE;
		mapData[row-4][col/2-2]=Barrier.STONE;
		mapData[row-4][col/2-3]=Barrier.STONE;
		
		mapData[row-3][col/2+1]=Barrier.STONE;//右上
		mapData[row-3][col/2+2]=Barrier.STONE;
		mapData[row-4][col/2+1]=Barrier.STONE;
		mapData[row-4][col/2+2]=Barrier.STONE;
		
		mapData[row-3][col/2-1]=Barrier.STONE;//上
		mapData[row-3][col/2]=Barrier.STONE;
		mapData[row-4][col/2-1]=Barrier.STONE;
		mapData[row-4][col/2]=Barrier.STONE;
	}
	//用石墙将老窝保护起来的方法
	void buildHomeWithBrick()
	{
		int row=mapData.length;
		int col=mapData[0].length;
		//初始化砖墙，保护老窝
		mapData[row-1][col/2-2]=Barrier.BRICK;//左
		mapData[row-1][col/2-3]=Barrier.BRICK;
		mapData[row-2][col/2-2]=Barrier.BRICK;
		mapData[row-2][col/2-3]=Barrier.BRICK;
		
		mapData[row-1][col/2+1]=Barrier.BRICK;//右
		mapData[row-1][col/2+2]=Barrier.BRICK;
		mapData[row-2][col/2+1]=Barrier.BRICK;
		mapData[row-2][col/2+2]=Barrier.BRICK;
		
		mapData[row-3][col/2-2]=Barrier.BRICK;//左上
		mapData[row-3][col/2-3]=Barrier.BRICK;
		mapData[row-4][col/2-2]=Barrier.BRICK;
		mapData[row-4][col/2-3]=Barrier.BRICK;
		
		mapData[row-3][col/2+1]=Barrier.BRICK;//右上
		mapData[row-3][col/2+2]=Barrier.BRICK;
		mapData[row-4][col/2+1]=Barrier.BRICK;
		mapData[row-4][col/2+2]=Barrier.BRICK;
		
		mapData[row-3][col/2-1]=Barrier.BRICK;//上
		mapData[row-3][col/2]=Barrier.BRICK;
		mapData[row-4][col/2-1]=Barrier.BRICK;
		mapData[row-4][col/2]=Barrier.BRICK;
	}
	//进入下一关的方法
	public void goToNextMission()
	{
		if(missionNum>=this.MAP_SP.length)
		{
			//通关的代码
			System.out.println("congartulations! All missions are completed!!!");
			return;
		}
		missionNum++;
		mapData=copyMapData(this.MAP_SP[missionNum-1]);
		//计数器清零
		AliveWallPaperTank.countTankDestoryed=0;
		//英雄坦克回到原来的位置
		AliveWallPaperTank.hero.backHome();
		//清除奖励物
		AliveWallPaperTank.map.reward=null;
	}
	public int getMissionNum()
	{
		return missionNum;
	}
	public void setMissionNum(int missionNum)
	{
		this.missionNum=missionNum;
	}

}
