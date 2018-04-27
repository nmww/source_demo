package com.bn.tkqz;//声明包语句
import static com.bn.tkqz.Constant.SCREEN_HEIGHT;//引入相关类
import static com.bn.tkqz.Constant.SCREEN_WIDTH;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.SoundPool;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class AliveWallPaperTank extends WallpaperService
{	
	static Handler hd = new Handler();//创建静态Handler对象
	EngineCrazyTank tankEngine;//EngineCrazyTank对象的引用
    
    static Bitmap bullet;//子弹位图
    static Bitmap heroBullet;//英雄子弹位图
    //敌方白坦克
    static Bitmap[] tanki1;//坦克位图数组1
    static Bitmap[] tanki2;//坦克位图数组2
    static Bitmap[] tanki3;//坦克位图数组3
    //敌方红坦克
    static Bitmap[] tankRedi1;//红坦克位图数组1
    static Bitmap[] tankRedi2;//红坦克位图数组2
    static Bitmap[] tankRedi3;//红坦克位图数组3
    //英雄坦克
    static Bitmap[] heroTanki1;//英雄坦克位图数组1
    static Bitmap[] heroTanki2;//英雄坦克位图数组2
    static Bitmap[] heroTanki3;//英雄坦克位图数组3
    static Bitmap[] heroTanki4;//英雄坦克位图数组4
    
    //障碍物
    static Bitmap brickBitmap;
    static Bitmap stoneBitmap;
    static Bitmap seaBitmap;
    static Bitmap iceBitmap;
    static Bitmap grassBitmap;
    //老窝
    static Bitmap homeBitmap;
    static Bitmap homediedBitmap;
    //奖励物
    static Bitmap starBitmap;
    static Bitmap bombBitmap;
    static Bitmap lifeBitmap;
    static Bitmap shovelBitmap;
    static Bitmap protectorBitmap;
    static Bitmap timerBitmap;
    //英雄坦克保护器外壳
    static Bitmap coveringBitmap;
    //虚拟按钮
    static Bitmap controlBitmap;
    static Bitmap redDotBitmap;
    static Bitmap fireBtnUpBitmap;
    static Bitmap fireBtnDownBitmap;
    //其它
    static Bitmap[] numbers;
    static Bitmap gameOver;
    static Bitmap restartBitmap;
    static SoundPool soundPool;
    static Map<Integer,Integer> soundPoolMap;
    //================================== 非资源 变量 begin ==========================================
    static BattleMap map;//地图引用
 	static Hero hero;
 	static int keyState=0;//按键状态,1——up,2——down,4——left,8——right
 	
 	static boolean heroGoFlag=true;//刷新界面线程标志位
 	
 	static ArrayList<Tank> alTank;//敌方坦克列表
 	private TankGeneratorThread  generator;//随机产生敌方坦克线程的引用
 	static TankGoThread go;//敌方坦克行进线程的引用
 	HeroGoThread heroGo;//英雄坦克行进线程的引用
 	static HeroSendBulletThread heroSendBullet;//英雄坦克发射子弹线程的引用
 	static boolean heroSendBulletFlag=true;//英雄坦克发射子弹的标志位
 	static TankChangeDirectionThread changeDirection;//敌方坦克随机改变方向线程的引用
 	static boolean TankGeneratorFlag=true;//随机产生坦克的标志位
 	static boolean TankGoFlag=true;//英雄坦克行进的标志
 	static boolean TankChangeDirectionFlag=true;//坦克随机改变方向的标志位
 	
 	static Vector<HeroBullet> alHeroBullet;//英雄子弹列表
 	private HeroBulletGoThread heroBulletGo;
 	static boolean heroBulletGoFlag=true;
 	
 	static ArrayList<Bullet> alBullet;//敌方子弹列表
 	static TankSendBulletThread tankSendBullet;
 	private TankBulletGoThread tankBulletGo;
 	static boolean tankSendBulletFlag=true;
 	static boolean tankBulletGoFlag=true;
 	
 	static boolean gameOverFlag=false;			//游戏结束标志位
 	static int countTankDestoryed=0;//记录击中坦克数量
 	static long gameStartTime;//游戏开始时间
 	static int time=0;//游戏进行时间
 	static int fullTime=0;//记录游戏界面现在总时间，从开始游戏 到退出游戏
 	static int score=0;//游戏得分
 	private boolean fireButtonDownFlag=false;//发射按钮是否被按下的标志
 	static ScreenScaleResult ssr;
 	 //================================== 非资源 变量 end ==========================================
	@Override
    public Engine onCreateEngine() 
    {
		initBitmap();//初始化位图资源 
	    tankEngine=new EngineCrazyTank(); 
        return tankEngine;
    }
	private void initBitmap()
    {//初始化位图资源
    	bullet=BitmapFactory.decodeResource(this.getResources(), R.drawable.b);//创建子弹位图
    	heroBullet=BitmapFactory.decodeResource(this.getResources(), R.drawable.hb);//创建子弹位图
    	tanki1=new Bitmap[]
    	{//创建坦克位图1
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.up1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.right1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.down1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.left1)    			
    	};
    	tanki2=new Bitmap[]
    	{//创建坦克位图1
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.up2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.right2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.down2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.left2)    			
    	};
    	tanki3=new Bitmap[]
    	{//创建坦克位图2
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.up3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.right3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.down3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.left3)    			
    	};
    	//英雄坦克
    	heroTanki1=new Bitmap[]
    	                      {//创建英雄坦克位图1
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroup1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroright1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.herodown1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroleft1)    			
    	};
    	heroTanki2=new Bitmap[]
    	                      {//创建英雄坦克位图2
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroup2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroright2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.herodown2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroleft2)    			
    	};
    	heroTanki3=new Bitmap[]
    	                      {//创建英雄坦克位图3
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroup3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroright3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.herodown3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroleft3)    			
    	};
    	heroTanki4=new Bitmap[]
    	                      {//创建英雄坦克位图3
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroup4),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroright4),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.herodown4),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroleft4)    			
    	};
    	//红坦克
    	tankRedi1=new Bitmap[]
					    	{//创建红坦克位图1
					    			BitmapFactory.decodeResource(this.getResources(), R.drawable.upred1),
					    			BitmapFactory.decodeResource(this.getResources(), R.drawable.rightred1),
					    			BitmapFactory.decodeResource(this.getResources(), R.drawable.downred1),
					    			BitmapFactory.decodeResource(this.getResources(), R.drawable.leftred1)    			
					    	};
    	tankRedi2=new Bitmap[]
    	                 	{//创建红坦克位图2
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.upred2),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.rightred2),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.downred2),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.leftred2)    			
    	                 	};
    	tankRedi3=new Bitmap[]
    	                 	{//创建红坦克位图3
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.upred3),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.rightred3),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.downred3),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.leftred3)    			
    	                 	};
    	//障碍物
    	brickBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.brick);//创建砖墙位图
    	stoneBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.stone);//创建石墙位图
    	seaBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.sea);//创建海洋位图
    	iceBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.ice);//创建冰位图
    	grassBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);//创建草地位图
    	//老窝
    	homeBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.home);//创建老窝位图
    	homediedBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.homedied);//创建老窝死后位图
    	//奖励物
    	starBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.star);//星星
    	bombBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.bomb);//炸弹
    	lifeBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.life);//命
    	shovelBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.shovel);//铁锹
    	protectorBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.protector);//保护器
    	timerBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.timer);//定时
    	//外壳
    	coveringBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.covering);
    	//数字
		numbers=new Bitmap[]{//数组位图
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number0),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number1),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number2),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number3),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number4),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number5),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number6),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number7),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number8),
				BitmapFactory.decodeResource(this.getResources(), R.drawable.number9)
			};
		gameOver =BitmapFactory.decodeResource(this.getResources(), R.drawable.gameover);//游戏结束位图
		restartBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.restart);//提示重新开始的位图
		controlBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.control);//虚拟按钮位图
		redDotBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.reddot);//红点位图
		fireBtnUpBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.fireup);//发射位图
		fireBtnDownBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.firedown);//发射位图
    }  
	//===========================================非资源方法== begin ====================
	//游戏结束的方法
 	public static void overGame()
 	{
 		gameOverFlag=true;
 		TankGeneratorFlag=false;
 		heroGoFlag=true;
 		TankGoFlag=true;
 		TankChangeDirectionFlag=true;
 		heroBulletGoFlag=false;
 		tankSendBulletFlag=false;
 		tankBulletGoFlag=false;
 		AliveWallPaperTank.keyState=0;//按键状态置空
 	}
 	//检测当前任务是否完成的方法
 	public static boolean isCurrentMissionCompleted()
 	{
 		return AliveWallPaperTank.countTankDestoryed>=Constant.TANK_TOTAL_NUM;	
 	}
 	//判断是否为竖屏的方法
 	public static boolean isShuPing()
 	{
 		return (ssr.so==ScreenOrien.SP);
 	}
 	//===========================================非资源方法== end ====================
 	
class EngineCrazyTank extends Engine 
 {
     private final Paint paint = new Paint();
     boolean ifDraw;

     private final Runnable drawTask = new Runnable() {
         public void run() {
             repaint();
         }
     };
     
     EngineCrazyTank() 
     {
    	 
     }

     @Override
     public void onCreate(SurfaceHolder surfaceHolder) 
     {
         super.onCreate(surfaceHolder);            
         setTouchEventsEnabled(true); 
     } 

     @Override
     public void onDestroy() 
     {
         super.onDestroy();       
     }

     @Override
     public void onVisibilityChanged(boolean visible) 
     {
    	 ifDraw=visible;  	 
    	 if(ifDraw)
         {
    		 //如果可见，重新初始化所有数据
    		 this.initAllData();
        	 hd.postDelayed(drawTask, 1000 / 25);
         }
    	 else
    	 {
    		 //如果不可见，停止所有线程
    		 this.stopAllThreads();
    	 }
     }

     @Override
     public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) 
     {System.out.println(width+","+height);
        super.onSurfaceChanged(holder, format, width, height);
        //自动判断横屏竖屏
 		ssr=ScreenScaleUtil.calScale(width, height);
 		Constant.LEFT_TOP_X=ssr.lucX;
 		Constant.LEFT_TOP_Y=ssr.lucY;
 		this.initAllData();//初始化所有数据
 	}

     @Override
     public void onSurfaceCreated(SurfaceHolder holder) 
     {
         super.onSurfaceCreated(holder);
     }

     @Override
     public void onSurfaceDestroyed(SurfaceHolder holder) 
     {
        super.onSurfaceDestroyed(holder);
        this.stopAllThreads();// 停止所有线程
     }

     //桌面换子屏幕时的回调方法
     @Override
     public void onOffsetsChanged(float xOffset, float yOffset,
             float xStep, float yStep, int xPixels, int yPixels) 
     {
    	 Constant.LEFT_TOP_X=(int) (ssr.lucX+xOffset);
  		 Constant.LEFT_TOP_Y=(int) (ssr.lucY+yOffset);
     }

     @Override
     public void onTouchEvent(MotionEvent event) 
     {
         float y = event.getY();
         float x = event.getX();
         switch (event.getAction()) {
 	        case MotionEvent.ACTION_DOWN://===========按下虚拟键盘==========
 	            if(//===== up ===
 	            		Constant.pointIsInRect
 	            		(
 	            				x, y, 
 	            				Constant.UP_X, Constant.UP_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
 	            		)
 	            )
 	            {
 	            	keyState=(keyState==0x1)?0:0x1;
 	            }
 	            else if(//===== down ===
 		            		Constant.pointIsInRect
 		            		(
 		            				x, y, 
 		            				Constant.DOWN_X, Constant.DOWN_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
 		            		)
 	            		)
 	            {
 	            	keyState=(keyState==0x2)?0:0x2;
 	            }
 	            else if(//===== left ===
 	            		Constant.pointIsInRect
 	            		(
 	            				x, y, 
 	            				Constant.LEFT_X, Constant.LEFT_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
 	            		)
             		)
 		            {
 	            		keyState=(keyState==0x4)?0:0x4;
 		            }
 	            else if(//===== right ===
 	            		Constant.pointIsInRect
 	            		(
 	            				x, y, 
 	            				Constant.RIGHT_X, Constant.RIGHT_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
 	            		)
             		)
 	            {
 	            	keyState=(keyState==0x8)?0:0x8;
 	            }
 	            else if(//===== fire area ===
 	            		Constant.pointIsInRect
 	            		(
 	            				x, y, 
 	            				Constant.FIRE_BTN_X, Constant.FIRE_BTN_Y, Constant.FIRE_BTN_WIDTH, Constant.FIRE_BTN_HEIGHT
 	            		)
             		)
 	            {
 	            	fireButtonDownFlag=((fireButtonDownFlag==true)?false:true);
 	            	if(fireButtonDownFlag)
 	            	{
 	            		AliveWallPaperTank.heroSendBulletFlag=true;
 	            		AliveWallPaperTank.heroSendBullet=new HeroSendBulletThread();
 	            		heroSendBullet.start();
 	            	}
 	            	else
 	            	{
 	            		AliveWallPaperTank.heroSendBulletFlag=false;
 	            	}
 	            	
 	            }
 	            //如果游戏结束，点击重玩的触控区域，重新开始游戏
 	            if(gameOverFlag)
 	            {
 	            	if(
 	            			Constant.pointIsInRect
	 	            		(
	 	            				x, y, 
	 	            				Constant.FIRE_BTN_X, Constant.FIRE_BTN_Y, Constant.FIRE_BTN_WIDTH, Constant.FIRE_BTN_HEIGHT
	 	            		)
 	            	)
 	            	{
 	            		stopAllThreads();//停止以前所有线程
 	            		initAllData();//重新初始化所有数据和线程
 	            	}
 	            }
 		        break;
 		        //===================分界===============================================
         }
     }


     void repaint()
     {
         final SurfaceHolder holder = getSurfaceHolder();
         Canvas c = null;
         try 
         {
             c = holder.lockCanvas();
             if (c != null) 
             {
            	 onDraw(c);
             }
         } 
         finally 
         {
             if (c != null) holder.unlockCanvasAndPost(c);
         }
         if(ifDraw)
         {
        	 hd.postDelayed(drawTask, 1000 / 20);
         }
     }
     public void onDraw(Canvas canvas)
 	{	
 		canvas.drawColor(Color.argb(255, 0, 0, 0));//擦空界面
 		//绘制下层地图
 		map.drawSelfBelow(canvas, paint);
 		hero.drawSelf(canvas, paint);//绘制英雄坦克
 		//绘制敌方坦克
 		ArrayList<Tank> alTank=new ArrayList<Tank>(AliveWallPaperTank.alTank);//获得当前已存在敌坦克存放列表
 		for(Tank t:alTank)
 		{
 			t.drawSelf(canvas, paint);
 		}
 		//绘制英雄子弹
 		ArrayList<HeroBullet> alHeroBullet=new ArrayList<HeroBullet>(AliveWallPaperTank.alHeroBullet);//复制敌子弹列表
 		for(HeroBullet hb:alHeroBullet)
 		{
 			hb.drawSelf(canvas, paint);
 		}
 		//绘制敌方子弹
 		ArrayList<Bullet> alBullet=new ArrayList<Bullet>(AliveWallPaperTank.alBullet);//复制敌子弹列表
 		for(Bullet b:alBullet)
 		{
 			b.drawSelf(canvas, paint);
 		}
 		//绘制上层地图
 		map.drawSelfAbove(canvas, paint);
 		if(AliveWallPaperTank.isShuPing())
 		{
 			//绘制屏幕右侧数据信息
 			drawAllDataMessageSP(canvas,paint);
 			//绘制虚拟按键
 			drawVirtualButtonSP(canvas,paint);
 		}
 		else
 		{
 			//绘制屏幕上侧数据信息
 			drawAllDataMessageHP(canvas,paint);
 			//绘制虚拟按键
 			drawVirtualButtonHP(canvas,paint);
 			
 		}
 		//当游戏结束时，绘制Game Over提示信息
 		long currentTime=System.currentTimeMillis();//记录当前时间
 		fullTime=(int) ((currentTime-gameStartTime)/1000);//记录游戏总时间
 		
 		if(gameOverFlag)
 		{
 			if(fullTime%2==0)
 			{//绘制游戏结束界面
 				canvas.drawBitmap(gameOver, SCREEN_WIDTH/2-100, SCREEN_HEIGHT/2-26, paint);
 				canvas.drawBitmap(restartBitmap, SCREEN_WIDTH/2-100, SCREEN_HEIGHT-120, paint);
 			}
 		}

 	}
 	//=============================================== SP =========================== begin =======
 	//绘制屏幕右侧数据信息的方法
 	void drawAllDataMessageSP(Canvas canvas,Paint paint)
 	{
 		paint.setColor(Color.RED);	//将画笔颜色设置为红色
 		//绘制上侧灰色条
 		canvas.drawRect
 		(
 				Constant.GAME_VIEW_X,						//left, 
 				Constant.GAME_VIEW_Y-Constant.UP_BAR-2,			//top, 
 				Constant.GAME_VIEW_X+Constant.GAME_VIEW_WIDTH,	//right, 
 				Constant.GAME_VIEW_Y-2,	//bottom, 
 				paint
 		);
 		paint.setColor(Color.YELLOW);	//将画笔颜色设置为黄色
 		paint.setTextSize(13);		//设置字体大小
 		
 		drawOneDataMessageSP(5,"得分",score,canvas,paint);
 		drawOneDataMessageSP(3,"击毁",countTankDestoryed,canvas,paint);
 		drawOneDataMessageSP(4,"英雄",Hero.HEROLIFE,canvas,paint);
 		drawOneDataMessageSP(1,"关卡",map.getMissionNum(),canvas,paint);
 		drawOneDataMessageSP(2,"敌坦克",Constant.TANK_TOTAL_NUM,canvas,paint);
 	}
 	void drawOneDataMessageSP(int order,String msg,int number,Canvas canvas,Paint paint)
 	{		
 		canvas.drawText
 		(
 				msg, 
 				Constant.GAME_VIEW_X+Constant.FIRST_HANZI_WIDTH+(order-1)*Constant.HANZI_WIDTH, 
 				Constant.GAME_VIEW_Y-Constant.HANZI_HEIGHT-Constant.NUMBER_HEIGHT, 
 				paint
 		);
 		String numberStr=number+"";
 		for(int i=0;i<numberStr.length();i++)
 		{
 			char c=numberStr.charAt(i);
 			canvas.drawBitmap
 			(
 					numbers[c-'0'], 
 					Constant.GAME_VIEW_X+Constant.FIRST_NUMBER_WIDTH+order*Constant.NUMBER_TOTAL_WIDTH-Constant.NUMBER_WIDTH*(numberStr.length()-i), 
 					Constant.GAME_VIEW_Y-Constant.NUMBER_HEIGHT, 
 					paint
 			);
 		}
 	}
 	//画虚拟按钮的方法
 	void drawVirtualButtonSP(Canvas canvas,Paint paint)
 	{
 		canvas.drawBitmap
 		(
 				AliveWallPaperTank.controlBitmap, 
 				Constant.BUTTON_X,
 				Constant.BUTTON_Y, 
 				paint
 		);
// 		//用有色矩形标记可触控区域
// 		paint.setColor(Color.BLUE);
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.UP_X, Constant.UP_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
// 		);
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.DOWN_X, Constant.DOWN_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
// 		);
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.LEFT_X, Constant.LEFT_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
// 		);
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.RIGHT_X, Constant.RIGHT_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
// 		);
// 		//标记发射子弹的区域
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.FIRE_BTN_X, Constant.FIRE_BTN_Y, Constant.FIRE_BTN_WIDTH, Constant.FIRE_BTN_HEIGHT
// 		);
 		//画虚拟方向键图片
 		canvas.drawBitmap
 		(
 				AliveWallPaperTank.controlBitmap, 
 				Constant.BUTTON_X,
 				Constant.BUTTON_Y, 
 				paint
 		);
 		switch(keyState)
 		{
 			case 0x1:	//上
 			{
 				//画红点
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X,
 						Constant.RED_DOT_CENTER_Y-Constant.BUTTON_HEIGHT, 
 						paint
 				);
 			}
 			break;
 			case 0x2:	//下
 			{
 				//画红点
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X,
 						Constant.RED_DOT_CENTER_Y+Constant.BUTTON_HEIGHT, 
 						paint
 				);
 			}
 			break;
 			case 0x4:	//左
 			{
 				//画红点
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X-Constant.BUTTON_WIDTH,
 						Constant.RED_DOT_CENTER_Y, 
 						paint
 				);
 			}
 			break;
 			case 0x8:	//右
 			{
 				//画红点
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X+Constant.BUTTON_WIDTH,
 						Constant.RED_DOT_CENTER_Y, 
 						paint
 				);
 			}
 			break;
 			case 0:
 			{
 				//画红点
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X,
 						Constant.RED_DOT_CENTER_Y, 
 						paint
 				);
 			}
 			break;
 		}//switch
 		//画发射图片
 		if(fireButtonDownFlag)
 		{//System.out.println("+++++fireButtonDownFlag++++++ true ++++++");
 			canvas.drawBitmap
 			(
 					AliveWallPaperTank.fireBtnDownBitmap, 
 					Constant.FIR_MAP_X,
 					Constant.FIR_MAP_Y, 
 					paint
 			);
 		}
 		else
 		{//System.out.println("+++++fireButtonDownFlag+++++++ false        +++++");
 			canvas.drawBitmap
 			(
 					AliveWallPaperTank.fireBtnUpBitmap, 
 					Constant.FIR_MAP_X,
 					Constant.FIR_MAP_Y, 
 					paint
 			);
 		}
 	}
 	//=============================================== SP =========================== end =======
 	//=============================================== HP ====== begin =======
 	//绘制屏幕右侧数据信息的方法
 	void drawAllDataMessageHP(Canvas canvas,Paint paint)
 	{
 		paint.setColor(Color.RED);	//将画笔颜色设置为灰色
 		//绘制右侧灰色条
 		canvas.drawRect
 		(
 				Constant.GAME_VIEW_X+Constant.GAME_VIEW_WIDTH+1,						//left, 
 				Constant.GAME_VIEW_Y,												//top, 
 				Constant.GAME_VIEW_X+Constant.GAME_VIEW_WIDTH+Constant.RIGHT_BAR,	//right, 
 				Constant.GAME_VIEW_Y+Constant.GAME_VIEW_HEIGHT,						//bottom, 
 				paint
 		);
 		paint.setColor(Color.YELLOW);	//将画笔颜色设置为黄色
 		paint.setTextSize(13);		//设置字体大小
 		
 		drawOneDataMessageHP(5,"得分",score,canvas,paint);
 		drawOneDataMessageHP(3,"击毁",countTankDestoryed,canvas,paint);
 		drawOneDataMessageHP(4,"英雄",Hero.HEROLIFE,canvas,paint);
 		drawOneDataMessageHP(1,"关卡",map.getMissionNum(),canvas,paint);
 		drawOneDataMessageHP(2,"敌坦克",Constant.TANK_TOTAL_NUM,canvas,paint);
 	}
 	void drawOneDataMessageHP(int order,String msg,int number,Canvas canvas,Paint paint)
 	{
 		//绘制英雄数量		
 		canvas.drawText
 		(
 				msg, 
 				Constant.GAME_VIEW_X+Constant.GAME_VIEW_WIDTH+Constant.RIGHT_BAR+2, 
 				Constant.GAME_VIEW_Y+Constant.FIRST_MESSAGE_HEIGHT+(order-1)*Constant.HANZI_HEIGHT+(order-1)*Constant.NUMBER_HEIGHT, 
 				paint
 		);
 		String numberStr=number+"";
 		for(int i=0;i<numberStr.length();i++)
 		{
 			char c=numberStr.charAt(i);
 			canvas.drawBitmap
 			(
 					numbers[c-'0'], 
 					Constant.GAME_VIEW_X+SCREEN_WIDTH-Constant.NUMBER_WIDTH*(numberStr.length()-i), 
 					Constant.GAME_VIEW_Y+Constant.FIRST_MESSAGE_HEIGHT+order*Constant.HANZI_HEIGHT+(order-1)*Constant.NUMBER_HEIGHT, 
 					paint
 			);
 		}
 	}
 	//画虚拟按钮的方法
 	void drawVirtualButtonHP(Canvas canvas,Paint paint)
 	{
 		//画虚拟方向键图片
 		canvas.drawBitmap
 		(
 				AliveWallPaperTank.controlBitmap, 
 				Constant.BUTTON_X,
 				Constant.BUTTON_Y, 
 				paint
 		);
// 		//用有色矩形标记可触控区域
// 		paint.setColor(Color.BLUE);
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.UP_X, Constant.UP_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
// 		);
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.DOWN_X, Constant.DOWN_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
// 		);
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.LEFT_X, Constant.LEFT_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
// 		);
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.RIGHT_X, Constant.RIGHT_Y,Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT
// 		);
// 		//标记发射子弹的区域
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.FIRE_BTN_X, Constant.FIRE_BTN_Y, Constant.FIRE_BTN_WIDTH, Constant.FIRE_BTN_HEIGHT
// 		);
 		//画虚拟方向键图片
 		canvas.drawBitmap
 		(
 				AliveWallPaperTank.controlBitmap, 
 				Constant.BUTTON_X,
 				Constant.BUTTON_Y, 
 				paint
 		);
 		switch(keyState)
 		{
 			case 0x1:	//上
 			{
 				//画红点
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X,
 						Constant.RED_DOT_CENTER_Y-Constant.BUTTON_HEIGHT, 
 						paint
 				);
 			}
 			break;
 			case 0x2:	//下
 			{
 				//画红点
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X,
 						Constant.RED_DOT_CENTER_Y+Constant.BUTTON_HEIGHT, 
 						paint
 				);
 			}
 			break;
 			case 0x4:	//左
 			{
 				//画红点
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X-Constant.BUTTON_WIDTH,
 						Constant.RED_DOT_CENTER_Y, 
 						paint
 				);
 			}
 			break;
 			case 0x8:	//右
 			{
 				//画红点
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X+Constant.BUTTON_WIDTH,
 						Constant.RED_DOT_CENTER_Y, 
 						paint
 				);
 			}
 			break;
 			case 0:
 			{
 				//画红点
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X,
 						Constant.RED_DOT_CENTER_Y, 
 						paint
 				);
 			}
 			break;
 		}//switch
 		//画发射图片
 		if(fireButtonDownFlag)
 		{
 			canvas.drawBitmap
 			(
 					AliveWallPaperTank.fireBtnDownBitmap, 
 					Constant.FIR_MAP_X,
 					Constant.FIR_MAP_Y, 
 					paint
 			);
 		}
 		else
 		{
 			canvas.drawBitmap
 			(
 					AliveWallPaperTank.fireBtnUpBitmap, 
 					Constant.FIR_MAP_X,
 					Constant.FIR_MAP_Y, 
 					paint
 			);
 		}
 	}
 	//=============================================== HP ====== end =======
 	//按比例画矩形的方法
 	void drawColorRect
 	(
 			Canvas canvas,Paint paint,
 			float xLeftTop,float yLeftTop,float length,float width		//坐标值在0到1之间
 	)
 	{
 		canvas.drawRect
 		(
 				xLeftTop,
 				yLeftTop,
 				(xLeftTop+length),
 				(yLeftTop+width),
 				paint
 		);
 	}
 	//初始化所有数据的方法
 	void initAllData()
 	{
 		//要先初始化常量，再初始化地图数据！
 		Constant.initConst();//初始化常量
 		map=new BattleMap();//创建地图对象
 		map.intiMapData();//初始化地图数据		
 		hero=new Hero(AliveWallPaperTank.heroTanki1);//创建英雄坦克对象(位置与常量有关，所以要放在初始化常量和数据后)
 		hero.backHome();//英雄回家
 		//初始化管理列表
 		alTank=new ArrayList<Tank>();		
 		alHeroBullet=new Vector<HeroBullet>();	
 		alBullet=new ArrayList<Bullet>();
 		//恢复初值
 		score=0;
 		countTankDestoryed=0;
 		Hero.HEROLIFE=Constant.HERO_LIFE;
 		map.setMissionNum(1);
 		AliveWallPaperTank.map.reward=null;//清空奖励物
 		//恢复线程标志
 		AliveWallPaperTank.gameOverFlag=false;
 		AliveWallPaperTank.heroGoFlag=true;
 		AliveWallPaperTank.TankGeneratorFlag=true;
 		AliveWallPaperTank.TankGoFlag=true;
 		AliveWallPaperTank.TankChangeDirectionFlag=true;
 		AliveWallPaperTank.heroBulletGoFlag=true;
 		AliveWallPaperTank.tankSendBulletFlag=true;
 		AliveWallPaperTank.tankBulletGoFlag=true;
 		//创建线程
 		generator=new TankGeneratorThread();
 		go=new TankGoThread();
 		heroGo=new HeroGoThread();
 		changeDirection=new TankChangeDirectionThread();
 		heroBulletGo=new HeroBulletGoThread();		
 		tankSendBullet=new TankSendBulletThread();
 		tankBulletGo=new TankBulletGoThread();
 		//启动线程
 		generator.start();
 		go.start();
 		heroGo.start();
 		changeDirection.start();
 		heroBulletGo.start();
 		tankSendBullet.start();
 		tankBulletGo.start();
 	}
 	//停止所有线程的方法
 	void stopAllThreads()
 	{
 		//将所有控制线程的标志设为false
  		AliveWallPaperTank.heroGoFlag=false;
  		AliveWallPaperTank.TankGeneratorFlag=false;
  		AliveWallPaperTank.TankGoFlag=false;
  		AliveWallPaperTank.TankChangeDirectionFlag=false;
  		AliveWallPaperTank.heroBulletGoFlag=false;
  		AliveWallPaperTank.tankSendBulletFlag=false;
  		AliveWallPaperTank.tankBulletGoFlag=false;
 	}
  }
}