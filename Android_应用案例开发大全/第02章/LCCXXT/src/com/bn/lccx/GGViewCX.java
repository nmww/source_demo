package com.bn.lccx;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GGViewCX extends View 
{
	int COMPONENT_WIDTH;							//该控件宽度
	int COMPONENT_HEIGHT;							//该控件高度
	boolean initflag=false;								//是否要获取控件的高度和宽度标志
	Bitmap[] bma;										//需要播放的图片的数组
	Paint paint;										//画笔
	static int[] drawablesId;									//图片ID数组
	int currIndex=0;										//图片ID数组下标，根据此变量画图片
	boolean workFlag=true;								//播放图片线程标志位
	public GGViewCX(Context father,AttributeSet as) { 			//构造器
		super(father,as);								
		drawablesId=new int[]{						//初始化图片ID数组
		
			R.drawable.adv7,	
			R.drawable.adv8,	
			R.drawable.adv9
				
		};
		bma=new Bitmap[drawablesId.length];				//创建存放图片的数组
		initBitmaps();									//调用初始化图片函数，初始化图片数组
		paint=new Paint();								//创建画笔
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);				//消除锯齿	
		new Thread(){									//创建播放图片线程
			public void run(){
				while(workFlag){
					currIndex=(currIndex+1)%drawablesId.length;//改变ID数组下标值
					GGViewCX.this.postInvalidate();			//绘制
					try {
						Thread.sleep(3000);				//休息三秒
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}}}}.start();							//启动线程
	}	
	public void initBitmaps(){								//初始化图片函数
		Resources res=this.getResources();					//获取Resources对象
		for(int i=0;i<drawablesId.length;i++){					
			bma[i]=BitmapFactory.decodeResource(res, drawablesId[i]);
		}}	
	public void onDraw(Canvas canvas){						//绘制函数
		if(!initflag) {									//第一次绘制时需要获取宽度和高度
			COMPONENT_WIDTH=this.getWidth();			//获取view的宽度
			COMPONENT_HEIGHT=this.getHeight();			//获取view的高度
			initflag=true;
		}
		int picWidth=bma[currIndex].getWidth();				//获取当前绘制图片的宽度
		int picHeight=bma[currIndex].getHeight();				//获取当前绘制图片的高度
		int startX=(COMPONENT_WIDTH-picWidth)/2;			//得到绘制图片的左上角X坐标
		int startY=(COMPONENT_HEIGHT-picHeight)/2; 		//得到绘制图片的左上角Y坐标
		canvas.drawARGB(255, 200, 128, 128);				//设置背景色
		canvas.drawBitmap(bma[currIndex], startX,startY, paint);	//绘制图片
	}}


