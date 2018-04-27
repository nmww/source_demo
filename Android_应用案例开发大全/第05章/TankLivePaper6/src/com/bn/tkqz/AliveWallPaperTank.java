package com.bn.tkqz;//���������
import static com.bn.tkqz.Constant.SCREEN_HEIGHT;//���������
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
	static Handler hd = new Handler();//������̬Handler����
	EngineCrazyTank tankEngine;//EngineCrazyTank���������
    
    static Bitmap bullet;//�ӵ�λͼ
    static Bitmap heroBullet;//Ӣ���ӵ�λͼ
    //�з���̹��
    static Bitmap[] tanki1;//̹��λͼ����1
    static Bitmap[] tanki2;//̹��λͼ����2
    static Bitmap[] tanki3;//̹��λͼ����3
    //�з���̹��
    static Bitmap[] tankRedi1;//��̹��λͼ����1
    static Bitmap[] tankRedi2;//��̹��λͼ����2
    static Bitmap[] tankRedi3;//��̹��λͼ����3
    //Ӣ��̹��
    static Bitmap[] heroTanki1;//Ӣ��̹��λͼ����1
    static Bitmap[] heroTanki2;//Ӣ��̹��λͼ����2
    static Bitmap[] heroTanki3;//Ӣ��̹��λͼ����3
    static Bitmap[] heroTanki4;//Ӣ��̹��λͼ����4
    
    //�ϰ���
    static Bitmap brickBitmap;
    static Bitmap stoneBitmap;
    static Bitmap seaBitmap;
    static Bitmap iceBitmap;
    static Bitmap grassBitmap;
    //����
    static Bitmap homeBitmap;
    static Bitmap homediedBitmap;
    //������
    static Bitmap starBitmap;
    static Bitmap bombBitmap;
    static Bitmap lifeBitmap;
    static Bitmap shovelBitmap;
    static Bitmap protectorBitmap;
    static Bitmap timerBitmap;
    //Ӣ��̹�˱��������
    static Bitmap coveringBitmap;
    //���ⰴť
    static Bitmap controlBitmap;
    static Bitmap redDotBitmap;
    static Bitmap fireBtnUpBitmap;
    static Bitmap fireBtnDownBitmap;
    //����
    static Bitmap[] numbers;
    static Bitmap gameOver;
    static Bitmap restartBitmap;
    static SoundPool soundPool;
    static Map<Integer,Integer> soundPoolMap;
    //================================== ����Դ ���� begin ==========================================
    static BattleMap map;//��ͼ����
 	static Hero hero;
 	static int keyState=0;//����״̬,1����up,2����down,4����left,8����right
 	
 	static boolean heroGoFlag=true;//ˢ�½����̱߳�־λ
 	
 	static ArrayList<Tank> alTank;//�з�̹���б�
 	private TankGeneratorThread  generator;//��������з�̹���̵߳�����
 	static TankGoThread go;//�з�̹���н��̵߳�����
 	HeroGoThread heroGo;//Ӣ��̹���н��̵߳�����
 	static HeroSendBulletThread heroSendBullet;//Ӣ��̹�˷����ӵ��̵߳�����
 	static boolean heroSendBulletFlag=true;//Ӣ��̹�˷����ӵ��ı�־λ
 	static TankChangeDirectionThread changeDirection;//�з�̹������ı䷽���̵߳�����
 	static boolean TankGeneratorFlag=true;//�������̹�˵ı�־λ
 	static boolean TankGoFlag=true;//Ӣ��̹���н��ı�־
 	static boolean TankChangeDirectionFlag=true;//̹������ı䷽��ı�־λ
 	
 	static Vector<HeroBullet> alHeroBullet;//Ӣ���ӵ��б�
 	private HeroBulletGoThread heroBulletGo;
 	static boolean heroBulletGoFlag=true;
 	
 	static ArrayList<Bullet> alBullet;//�з��ӵ��б�
 	static TankSendBulletThread tankSendBullet;
 	private TankBulletGoThread tankBulletGo;
 	static boolean tankSendBulletFlag=true;
 	static boolean tankBulletGoFlag=true;
 	
 	static boolean gameOverFlag=false;			//��Ϸ������־λ
 	static int countTankDestoryed=0;//��¼����̹������
 	static long gameStartTime;//��Ϸ��ʼʱ��
 	static int time=0;//��Ϸ����ʱ��
 	static int fullTime=0;//��¼��Ϸ����������ʱ�䣬�ӿ�ʼ��Ϸ ���˳���Ϸ
 	static int score=0;//��Ϸ�÷�
 	private boolean fireButtonDownFlag=false;//���䰴ť�Ƿ񱻰��µı�־
 	static ScreenScaleResult ssr;
 	 //================================== ����Դ ���� end ==========================================
	@Override
    public Engine onCreateEngine() 
    {
		initBitmap();//��ʼ��λͼ��Դ 
	    tankEngine=new EngineCrazyTank(); 
        return tankEngine;
    }
	private void initBitmap()
    {//��ʼ��λͼ��Դ
    	bullet=BitmapFactory.decodeResource(this.getResources(), R.drawable.b);//�����ӵ�λͼ
    	heroBullet=BitmapFactory.decodeResource(this.getResources(), R.drawable.hb);//�����ӵ�λͼ
    	tanki1=new Bitmap[]
    	{//����̹��λͼ1
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.up1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.right1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.down1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.left1)    			
    	};
    	tanki2=new Bitmap[]
    	{//����̹��λͼ1
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.up2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.right2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.down2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.left2)    			
    	};
    	tanki3=new Bitmap[]
    	{//����̹��λͼ2
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.up3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.right3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.down3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.left3)    			
    	};
    	//Ӣ��̹��
    	heroTanki1=new Bitmap[]
    	                      {//����Ӣ��̹��λͼ1
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroup1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroright1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.herodown1),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroleft1)    			
    	};
    	heroTanki2=new Bitmap[]
    	                      {//����Ӣ��̹��λͼ2
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroup2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroright2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.herodown2),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroleft2)    			
    	};
    	heroTanki3=new Bitmap[]
    	                      {//����Ӣ��̹��λͼ3
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroup3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroright3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.herodown3),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroleft3)    			
    	};
    	heroTanki4=new Bitmap[]
    	                      {//����Ӣ��̹��λͼ3
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroup4),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroright4),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.herodown4),
    			BitmapFactory.decodeResource(this.getResources(), R.drawable.heroleft4)    			
    	};
    	//��̹��
    	tankRedi1=new Bitmap[]
					    	{//������̹��λͼ1
					    			BitmapFactory.decodeResource(this.getResources(), R.drawable.upred1),
					    			BitmapFactory.decodeResource(this.getResources(), R.drawable.rightred1),
					    			BitmapFactory.decodeResource(this.getResources(), R.drawable.downred1),
					    			BitmapFactory.decodeResource(this.getResources(), R.drawable.leftred1)    			
					    	};
    	tankRedi2=new Bitmap[]
    	                 	{//������̹��λͼ2
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.upred2),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.rightred2),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.downred2),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.leftred2)    			
    	                 	};
    	tankRedi3=new Bitmap[]
    	                 	{//������̹��λͼ3
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.upred3),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.rightred3),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.downred3),
    	                 			BitmapFactory.decodeResource(this.getResources(), R.drawable.leftred3)    			
    	                 	};
    	//�ϰ���
    	brickBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.brick);//����שǽλͼ
    	stoneBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.stone);//����ʯǽλͼ
    	seaBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.sea);//��������λͼ
    	iceBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.ice);//������λͼ
    	grassBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);//�����ݵ�λͼ
    	//����
    	homeBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.home);//��������λͼ
    	homediedBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.homedied);//������������λͼ
    	//������
    	starBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.star);//����
    	bombBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.bomb);//ը��
    	lifeBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.life);//��
    	shovelBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.shovel);//����
    	protectorBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.protector);//������
    	timerBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.timer);//��ʱ
    	//���
    	coveringBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.covering);
    	//����
		numbers=new Bitmap[]{//����λͼ
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
		gameOver =BitmapFactory.decodeResource(this.getResources(), R.drawable.gameover);//��Ϸ����λͼ
		restartBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.restart);//��ʾ���¿�ʼ��λͼ
		controlBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.control);//���ⰴťλͼ
		redDotBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.reddot);//���λͼ
		fireBtnUpBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.fireup);//����λͼ
		fireBtnDownBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.firedown);//����λͼ
    }  
	//===========================================����Դ����== begin ====================
	//��Ϸ�����ķ���
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
 		AliveWallPaperTank.keyState=0;//����״̬�ÿ�
 	}
 	//��⵱ǰ�����Ƿ���ɵķ���
 	public static boolean isCurrentMissionCompleted()
 	{
 		return AliveWallPaperTank.countTankDestoryed>=Constant.TANK_TOTAL_NUM;	
 	}
 	//�ж��Ƿ�Ϊ�����ķ���
 	public static boolean isShuPing()
 	{
 		return (ssr.so==ScreenOrien.SP);
 	}
 	//===========================================����Դ����== end ====================
 	
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
    		 //����ɼ������³�ʼ����������
    		 this.initAllData();
        	 hd.postDelayed(drawTask, 1000 / 25);
         }
    	 else
    	 {
    		 //������ɼ���ֹͣ�����߳�
    		 this.stopAllThreads();
    	 }
     }

     @Override
     public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) 
     {System.out.println(width+","+height);
        super.onSurfaceChanged(holder, format, width, height);
        //�Զ��жϺ�������
 		ssr=ScreenScaleUtil.calScale(width, height);
 		Constant.LEFT_TOP_X=ssr.lucX;
 		Constant.LEFT_TOP_Y=ssr.lucY;
 		this.initAllData();//��ʼ����������
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
        this.stopAllThreads();// ֹͣ�����߳�
     }

     //���滻����Ļʱ�Ļص�����
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
 	        case MotionEvent.ACTION_DOWN://===========�����������==========
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
 	            //�����Ϸ�������������Ĵ����������¿�ʼ��Ϸ
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
 	            		stopAllThreads();//ֹͣ��ǰ�����߳�
 	            		initAllData();//���³�ʼ���������ݺ��߳�
 	            	}
 	            }
 		        break;
 		        //===================�ֽ�===============================================
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
 		canvas.drawColor(Color.argb(255, 0, 0, 0));//���ս���
 		//�����²��ͼ
 		map.drawSelfBelow(canvas, paint);
 		hero.drawSelf(canvas, paint);//����Ӣ��̹��
 		//���Ƶз�̹��
 		ArrayList<Tank> alTank=new ArrayList<Tank>(AliveWallPaperTank.alTank);//��õ�ǰ�Ѵ��ڵ�̹�˴���б�
 		for(Tank t:alTank)
 		{
 			t.drawSelf(canvas, paint);
 		}
 		//����Ӣ���ӵ�
 		ArrayList<HeroBullet> alHeroBullet=new ArrayList<HeroBullet>(AliveWallPaperTank.alHeroBullet);//���Ƶ��ӵ��б�
 		for(HeroBullet hb:alHeroBullet)
 		{
 			hb.drawSelf(canvas, paint);
 		}
 		//���Ƶз��ӵ�
 		ArrayList<Bullet> alBullet=new ArrayList<Bullet>(AliveWallPaperTank.alBullet);//���Ƶ��ӵ��б�
 		for(Bullet b:alBullet)
 		{
 			b.drawSelf(canvas, paint);
 		}
 		//�����ϲ��ͼ
 		map.drawSelfAbove(canvas, paint);
 		if(AliveWallPaperTank.isShuPing())
 		{
 			//������Ļ�Ҳ�������Ϣ
 			drawAllDataMessageSP(canvas,paint);
 			//�������ⰴ��
 			drawVirtualButtonSP(canvas,paint);
 		}
 		else
 		{
 			//������Ļ�ϲ�������Ϣ
 			drawAllDataMessageHP(canvas,paint);
 			//�������ⰴ��
 			drawVirtualButtonHP(canvas,paint);
 			
 		}
 		//����Ϸ����ʱ������Game Over��ʾ��Ϣ
 		long currentTime=System.currentTimeMillis();//��¼��ǰʱ��
 		fullTime=(int) ((currentTime-gameStartTime)/1000);//��¼��Ϸ��ʱ��
 		
 		if(gameOverFlag)
 		{
 			if(fullTime%2==0)
 			{//������Ϸ��������
 				canvas.drawBitmap(gameOver, SCREEN_WIDTH/2-100, SCREEN_HEIGHT/2-26, paint);
 				canvas.drawBitmap(restartBitmap, SCREEN_WIDTH/2-100, SCREEN_HEIGHT-120, paint);
 			}
 		}

 	}
 	//=============================================== SP =========================== begin =======
 	//������Ļ�Ҳ�������Ϣ�ķ���
 	void drawAllDataMessageSP(Canvas canvas,Paint paint)
 	{
 		paint.setColor(Color.RED);	//��������ɫ����Ϊ��ɫ
 		//�����ϲ��ɫ��
 		canvas.drawRect
 		(
 				Constant.GAME_VIEW_X,						//left, 
 				Constant.GAME_VIEW_Y-Constant.UP_BAR-2,			//top, 
 				Constant.GAME_VIEW_X+Constant.GAME_VIEW_WIDTH,	//right, 
 				Constant.GAME_VIEW_Y-2,	//bottom, 
 				paint
 		);
 		paint.setColor(Color.YELLOW);	//��������ɫ����Ϊ��ɫ
 		paint.setTextSize(13);		//���������С
 		
 		drawOneDataMessageSP(5,"�÷�",score,canvas,paint);
 		drawOneDataMessageSP(3,"����",countTankDestoryed,canvas,paint);
 		drawOneDataMessageSP(4,"Ӣ��",Hero.HEROLIFE,canvas,paint);
 		drawOneDataMessageSP(1,"�ؿ�",map.getMissionNum(),canvas,paint);
 		drawOneDataMessageSP(2,"��̹��",Constant.TANK_TOTAL_NUM,canvas,paint);
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
 	//�����ⰴť�ķ���
 	void drawVirtualButtonSP(Canvas canvas,Paint paint)
 	{
 		canvas.drawBitmap
 		(
 				AliveWallPaperTank.controlBitmap, 
 				Constant.BUTTON_X,
 				Constant.BUTTON_Y, 
 				paint
 		);
// 		//����ɫ���α�ǿɴ�������
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
// 		//��Ƿ����ӵ�������
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.FIRE_BTN_X, Constant.FIRE_BTN_Y, Constant.FIRE_BTN_WIDTH, Constant.FIRE_BTN_HEIGHT
// 		);
 		//�����ⷽ���ͼƬ
 		canvas.drawBitmap
 		(
 				AliveWallPaperTank.controlBitmap, 
 				Constant.BUTTON_X,
 				Constant.BUTTON_Y, 
 				paint
 		);
 		switch(keyState)
 		{
 			case 0x1:	//��
 			{
 				//�����
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X,
 						Constant.RED_DOT_CENTER_Y-Constant.BUTTON_HEIGHT, 
 						paint
 				);
 			}
 			break;
 			case 0x2:	//��
 			{
 				//�����
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X,
 						Constant.RED_DOT_CENTER_Y+Constant.BUTTON_HEIGHT, 
 						paint
 				);
 			}
 			break;
 			case 0x4:	//��
 			{
 				//�����
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X-Constant.BUTTON_WIDTH,
 						Constant.RED_DOT_CENTER_Y, 
 						paint
 				);
 			}
 			break;
 			case 0x8:	//��
 			{
 				//�����
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
 				//�����
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
 		//������ͼƬ
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
 	//������Ļ�Ҳ�������Ϣ�ķ���
 	void drawAllDataMessageHP(Canvas canvas,Paint paint)
 	{
 		paint.setColor(Color.RED);	//��������ɫ����Ϊ��ɫ
 		//�����Ҳ��ɫ��
 		canvas.drawRect
 		(
 				Constant.GAME_VIEW_X+Constant.GAME_VIEW_WIDTH+1,						//left, 
 				Constant.GAME_VIEW_Y,												//top, 
 				Constant.GAME_VIEW_X+Constant.GAME_VIEW_WIDTH+Constant.RIGHT_BAR,	//right, 
 				Constant.GAME_VIEW_Y+Constant.GAME_VIEW_HEIGHT,						//bottom, 
 				paint
 		);
 		paint.setColor(Color.YELLOW);	//��������ɫ����Ϊ��ɫ
 		paint.setTextSize(13);		//���������С
 		
 		drawOneDataMessageHP(5,"�÷�",score,canvas,paint);
 		drawOneDataMessageHP(3,"����",countTankDestoryed,canvas,paint);
 		drawOneDataMessageHP(4,"Ӣ��",Hero.HEROLIFE,canvas,paint);
 		drawOneDataMessageHP(1,"�ؿ�",map.getMissionNum(),canvas,paint);
 		drawOneDataMessageHP(2,"��̹��",Constant.TANK_TOTAL_NUM,canvas,paint);
 	}
 	void drawOneDataMessageHP(int order,String msg,int number,Canvas canvas,Paint paint)
 	{
 		//����Ӣ������		
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
 	//�����ⰴť�ķ���
 	void drawVirtualButtonHP(Canvas canvas,Paint paint)
 	{
 		//�����ⷽ���ͼƬ
 		canvas.drawBitmap
 		(
 				AliveWallPaperTank.controlBitmap, 
 				Constant.BUTTON_X,
 				Constant.BUTTON_Y, 
 				paint
 		);
// 		//����ɫ���α�ǿɴ�������
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
// 		//��Ƿ����ӵ�������
// 		drawColorRect
// 		(
// 				canvas,paint,
// 				Constant.FIRE_BTN_X, Constant.FIRE_BTN_Y, Constant.FIRE_BTN_WIDTH, Constant.FIRE_BTN_HEIGHT
// 		);
 		//�����ⷽ���ͼƬ
 		canvas.drawBitmap
 		(
 				AliveWallPaperTank.controlBitmap, 
 				Constant.BUTTON_X,
 				Constant.BUTTON_Y, 
 				paint
 		);
 		switch(keyState)
 		{
 			case 0x1:	//��
 			{
 				//�����
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X,
 						Constant.RED_DOT_CENTER_Y-Constant.BUTTON_HEIGHT, 
 						paint
 				);
 			}
 			break;
 			case 0x2:	//��
 			{
 				//�����
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X,
 						Constant.RED_DOT_CENTER_Y+Constant.BUTTON_HEIGHT, 
 						paint
 				);
 			}
 			break;
 			case 0x4:	//��
 			{
 				//�����
 				canvas.drawBitmap
 				(
 						AliveWallPaperTank.redDotBitmap, 
 						Constant.RED_DOT_CENTER_X-Constant.BUTTON_WIDTH,
 						Constant.RED_DOT_CENTER_Y, 
 						paint
 				);
 			}
 			break;
 			case 0x8:	//��
 			{
 				//�����
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
 				//�����
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
 		//������ͼƬ
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
 	//�����������εķ���
 	void drawColorRect
 	(
 			Canvas canvas,Paint paint,
 			float xLeftTop,float yLeftTop,float length,float width		//����ֵ��0��1֮��
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
 	//��ʼ���������ݵķ���
 	void initAllData()
 	{
 		//Ҫ�ȳ�ʼ���������ٳ�ʼ����ͼ���ݣ�
 		Constant.initConst();//��ʼ������
 		map=new BattleMap();//������ͼ����
 		map.intiMapData();//��ʼ����ͼ����		
 		hero=new Hero(AliveWallPaperTank.heroTanki1);//����Ӣ��̹�˶���(λ���볣���йأ�����Ҫ���ڳ�ʼ�����������ݺ�)
 		hero.backHome();//Ӣ�ۻؼ�
 		//��ʼ�������б�
 		alTank=new ArrayList<Tank>();		
 		alHeroBullet=new Vector<HeroBullet>();	
 		alBullet=new ArrayList<Bullet>();
 		//�ָ���ֵ
 		score=0;
 		countTankDestoryed=0;
 		Hero.HEROLIFE=Constant.HERO_LIFE;
 		map.setMissionNum(1);
 		AliveWallPaperTank.map.reward=null;//��ս�����
 		//�ָ��̱߳�־
 		AliveWallPaperTank.gameOverFlag=false;
 		AliveWallPaperTank.heroGoFlag=true;
 		AliveWallPaperTank.TankGeneratorFlag=true;
 		AliveWallPaperTank.TankGoFlag=true;
 		AliveWallPaperTank.TankChangeDirectionFlag=true;
 		AliveWallPaperTank.heroBulletGoFlag=true;
 		AliveWallPaperTank.tankSendBulletFlag=true;
 		AliveWallPaperTank.tankBulletGoFlag=true;
 		//�����߳�
 		generator=new TankGeneratorThread();
 		go=new TankGoThread();
 		heroGo=new HeroGoThread();
 		changeDirection=new TankChangeDirectionThread();
 		heroBulletGo=new HeroBulletGoThread();		
 		tankSendBullet=new TankSendBulletThread();
 		tankBulletGo=new TankBulletGoThread();
 		//�����߳�
 		generator.start();
 		go.start();
 		heroGo.start();
 		changeDirection.start();
 		heroBulletGo.start();
 		tankSendBullet.start();
 		tankBulletGo.start();
 	}
 	//ֹͣ�����̵߳ķ���
 	void stopAllThreads()
 	{
 		//�����п����̵߳ı�־��Ϊfalse
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