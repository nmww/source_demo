package com.bn.gjxq;

import static com.bn.gjxq.Constant.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainMenuView extends SurfaceView implements SurfaceHolder.Callback
{
	GJXQActivity activity;
	Paint paint;//����
	Bitmap bitmapStart;//��ʼ
	Bitmap bitmapHelp;//����
	Bitmap bitmapAbout;//����
	Bitmap bitmapExit;//�˳�
	Bitmap bitmapBack;//����
	public MainMenuView(GJXQActivity activity) 
	{
		super(activity);
		this.activity=activity;
		this.getHolder().addCallback(this);//�ص��ӿ�
		paint=new Paint();//�½�����
		paint.setAntiAlias(true);
		initBitmap();//��ʼ��ͼƬ
	}
	public void initBitmap()
	{
		bitmapStart=BitmapFactory.decodeResource(getResources(), R.drawable.start);//���ؿ�ʼͼƬ
		bitmapHelp=BitmapFactory.decodeResource(getResources(), R.drawable.help);//
		bitmapAbout=BitmapFactory.decodeResource(getResources(), R.drawable.about);
		bitmapExit=BitmapFactory.decodeResource(getResources(), R.drawable.exit);
		bitmapBack=BitmapFactory.decodeResource(getResources(), R.drawable.mainmenu);
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
    	//beijing
    	canvas.drawBitmap(bitmapBack, BEIJING_XOFFSET, BEIJING_YOFFSET, null);
    	//help��ť
    	canvas.drawBitmap(bitmapHelp, BUTTON_HELP_XOFFSET, BUTTON_HELP_YOFFSET, null);
    	//about��ť
    	canvas.drawBitmap(bitmapAbout, BUTTON_ABOUT_XOFFSET, BUTTON_ABOUT_YOFFSET, null);
    	//exit
    	canvas.drawBitmap(bitmapExit, BUTTON_EXIT_XOFFSET, BUTTON_EXIT_YOFFSET, null);
    	//start
    	canvas.drawBitmap(bitmapStart, BUTTON_START_XOFFSET, BUTTON_START_YOFFSET, null);
	}
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		int x=(int) (e.getX());
		int y=(int) (e.getY());
		
		switch(e.getAction())
		{
		    case MotionEvent.ACTION_DOWN:
		    	if(x>BUTTON_START_XOFFSET&&x<BUTTON_START_WIDTH+BUTTON_START_XOFFSET
		    	   &&y>BUTTON_START_YOFFSET&&y<BUTTON_START_YOFFSET+BUTTON_START_HEIGHT)
				{
		    		activity.gotoIpView();//��ʼ
				}
		        if(x>BUTTON_HELP_XOFFSET&&x<BUTTON_HELP_XOFFSET+BUTTON_HELP_WIDTH
		           &&y>BUTTON_HELP_YOFFSET&&y<BUTTON_HELP_YOFFSET+BUTTON_HELP_HEIGHT)
		        {
		        	activity.gotoHelpView();
				}
				if(x>BUTTON_ABOUT_XOFFSET&&x<BUTTON_ABOUT_XOFFSET+BUTTON_ABOUT_WIDTH
				   &&y>BUTTON_ABOUT_YOFFSET&&y<BUTTON_ABOUT_YOFFSET+BUTTON_ABOUT_HEIGHT)
				{
					activity.gotoAboutView();
				}	
				if(x>BUTTON_EXIT_XOFFSET&&x<BUTTON_EXIT_XOFFSET+BUTTON_EXIT_WIDTH
				   &&y>BUTTON_EXIT_YOFFSET&&y<BUTTON_EXIT_YOFFSET+BUTTON_EXIT_HEIGHT)
			    {
					System.exit(0);
				}	
		    break;
		}
		return true;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) 
	{	
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		this.repaint();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		
	}
	//���»��Ʒ���
	public void repaint()
	{
		SurfaceHolder holder=this.getHolder();//����holder����
		Canvas canvas=holder.lockCanvas();//��������
		try{
			synchronized(holder)
			{
				onDraw(canvas);
			}
		   }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}
}