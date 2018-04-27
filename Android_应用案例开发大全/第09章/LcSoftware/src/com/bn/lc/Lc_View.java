package com.bn.lc;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.bn.lc.Constant.*;

public class Lc_View extends SurfaceView implements SurfaceHolder.Callback
{
	Lc_Activity activity;
	Paint paint; 
	 
	Bitmap iback;      //����
	Bitmap icategory;  //���
	Bitmap iproceeds;  //����
	Bitmap iremove;    //֧�� 
	Bitmap isum;       //����ͳ��
	Bitmap izhi;       //֧��ͳ��
	Bitmap isselect;   //�����ѯ
	Bitmap izselect;   //֧����ѯ
	Bitmap isystem;    //ϵͳ����
	Bitmap iout;       //�˳�
	Bitmap bnlc;       //����
  
	public Lc_View(Lc_Activity activity)
	{
        super(activity);
        this.activity=activity;
        this.getHolder().addCallback(this);
        
        paint=new Paint();
        paint.setAntiAlias(true); //�����
        
        initBitmap(activity.getResources());  //����ͼƬ
	}

	public void initBitmap(Resources r)  //����ͼƬ
	{
		iback=BitmapFactory.decodeResource(r, R.drawable.back);     //����
		icategory=BitmapFactory.decodeResource(r,R.drawable.category);   //��֧���
		iproceeds=BitmapFactory.decodeResource(r, R.drawable.income); //�ճ�����
		iremove=BitmapFactory.decodeResource(r, R.drawable.spend);    //�ճ�֧��
		isum=BitmapFactory.decodeResource(r, R.drawable.sum);  //����ͳ��
		izhi=BitmapFactory.decodeResource(r, R.drawable.jsq);       //֧��ͳ��
		isselect=BitmapFactory.decodeResource(r, R.drawable.sselect);//�����ѯ
		izselect=BitmapFactory.decodeResource(r, R.drawable.zselect); //֧����ѯ
		isystem=BitmapFactory.decodeResource(r, R.drawable.person);   //ϵͳ����
		iout=BitmapFactory.decodeResource(r, R.drawable.out);        //�˳�ϵͳ
		bnlc=BitmapFactory.decodeResource(r, R.drawable.bnlc);
	}
	   
	@Override   
	public void onDraw(Canvas canvas) //���� 
	{
		canvas.drawBitmap(iback, BACK_XOFFSET,BACK_YOFFSET, paint);   //���Ʊ���
		canvas.drawBitmap(bnlc, BNLC_XOFFSET,BNLC_YOFFSET, paint);   //���Ʊ���
		canvas.drawBitmap(icategory, LEI_XOFFSET,LEI_YOFFSET, paint); //��֧���
		canvas.drawBitmap(iproceeds,SHOU_XOFFSET,SHOU_YOFFSET,paint); //�ճ�����
		canvas.drawBitmap(iremove, ZHI_XOFFSET,ZHI_YOFFSET, paint);   //�ճ�֧��
		canvas.drawBitmap(isum, STONG_XOFFSET,STONG_YOFFSET, paint);  //����ͳ��
		canvas.drawBitmap(izhi, ZTONG_XOFFSET,ZTONG_YOFFSET, paint);  //֧��ͳ��
		canvas.drawBitmap(isselect, SCHA_XOFFSET,SCHA_YOFFSET, paint);//�����ѯ
		canvas.drawBitmap(izselect,ZCHA_XOFFSET ,ZCHA_YOFFSET, paint);//֧����ѯ
		canvas.drawBitmap(isystem, XI_XOFFSET,XI_YOFFSET, paint);     //ϵͳ����
		canvas.drawBitmap(iout, OUT_XOFFSET,OUT_YOFFSET, paint);      //�˳�ϵͳ
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)  //����
	{
		int x=(int)(e.getX());
		int y=(int)(e.getY());
		
		switch(e.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				if(x>LEI_XOFFSET&&x<LEI_XOFFSET+PIC_WIDTH&&y>LEI_YOFFSET&&y<LEI_YOFFSET+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(0);
				}				
				if(x>SHOU_XOFFSET&&x<SHOU_XOFFSET+PIC_WIDTH&&y>SHOU_YOFFSET&&y<SHOU_YOFFSET+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(1);
				}				
				if(x>ZHI_XOFFSET&&x<ZHI_XOFFSET+PIC_WIDTH&&y>ZHI_YOFFSET&&y<ZHI_YOFFSET+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(2);
				}				
				if(x>STONG_XOFFSET&&x<STONG_XOFFSET+PIC_WIDTH&&y>STONG_YOFFSET&&y<STONG_YOFFSET+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(3);
				}				
				if(x>ZTONG_XOFFSET&&x<ZTONG_XOFFSET+PIC_WIDTH&&y>ZTONG_YOFFSET&&y<ZTONG_YOFFSET+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(4);
				}				
				if(x>SCHA_XOFFSET&&x<SCHA_XOFFSET+PIC_WIDTH&&y>SCHA_YOFFSET&&y<SCHA_YOFFSET+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(5);
				}				
				if(x>ZCHA_XOFFSET&&x<ZCHA_XOFFSET+PIC_WIDTH&&y>ZCHA_YOFFSET&&y<ZCHA_YOFFSET+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(6);
				}				
				if(x>XI_XOFFSET&&x<XI_XOFFSET+PIC_WIDTH&&y>XI_YOFFSET&&y<XI_YOFFSET+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(7);
				}				
				if(x>OUT_XOFFSET&&x<OUT_XOFFSET+PIC_WIDTH&&y>OUT_YOFFSET&&y<OUT_YOFFSET+PIC_HEIGHT)
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
	
	public void repaint()  //�ػ淽��
	{
		SurfaceHolder surfaceholder=this.getHolder();
		Canvas canvas=surfaceholder.lockCanvas();
		try
		{
			synchronized(surfaceholder)
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
				surfaceholder.unlockCanvasAndPost(canvas);
			}
		}
     }
}
