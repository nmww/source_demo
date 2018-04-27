package com.bn.lccx;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class WelcomeView extends SurfaceView 
implements SurfaceHolder.Callback   //ʵ���������ڻص��ӿ�
{
	LCCXActivity activity;//activity������
	Paint paint;      //����
	int currentAlpha=0;  //��ǰ�Ĳ�͸��ֵ
	int screenWidth=480;   //��Ļ���
	int screenHeight=320;  //��Ļ�߶�
	int sleepSpan=50;      //������ʱ��ms
	Bitmap[] logos=new Bitmap[2];//logoͼƬ����
	Bitmap currentLogo;  //��ǰlogoͼƬ����
	int currentX;      //ͼƬλ��
	int currentY;
	public WelcomeView(LCCXActivity activity)
	{
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);  //�����������ڻص��ӿڵ�ʵ����
		paint = new Paint();  //��������
		paint.setAntiAlias(true);  //�򿪿����
		//����ͼƬ
		logos[0]=BitmapFactory.decodeResource(activity.getResources(), R.drawable.baina);
		logos[1]=BitmapFactory.decodeResource(activity.getResources(), R.drawable.bnkjs);		
	}
	public void onDraw(Canvas canvas)
	{	
		//���ƺ��������屳��
		paint.setColor(Color.BLACK);//���û�����ɫ
		paint.setAlpha(255);//���ò�͸����Ϊ255
		canvas.drawRect(0, 0, screenWidth, screenHeight, paint);
		//����ƽ����ͼ
		if(currentLogo==null)return;
		paint.setAlpha(currentAlpha);		
		canvas.drawBitmap(currentLogo, currentX, currentY, paint);	
	}
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	{
	}
	public void surfaceCreated(SurfaceHolder holder) //����ʱ������	
	{	
		new Thread()
		{
			public void run()
			{
				for(Bitmap bm:logos)
				{
					currentLogo=bm;//��ǰͼƬ������
					currentX=screenWidth/2-bm.getWidth()/2;//ͼƬλ��
					currentY=screenHeight/2-bm.getHeight()/2;
					for(int i=255;i>-10;i=i-10)
					{//��̬����ͼƬ��͸����ֵ�������ػ�	
						currentAlpha=i;
						if(currentAlpha<0)//�����ǰ��͸����С����
						{
							currentAlpha=0;//����͸������Ϊ��
						}
						SurfaceHolder myholder=WelcomeView.this.getHolder();//��ȡ�ص��ӿ�
						Canvas canvas = myholder.lockCanvas();//��ȡ����
						try{
							synchronized(myholder)//ͬ��
							{
								onDraw(canvas);//���л��ƻ���
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							if(canvas!= null)//�����ǰ������Ϊ��
							{
								myholder.unlockCanvasAndPost(canvas);//��������
							}
						}
						try
						{
							if(i==255)//������ͼƬ����ȴ�һ��
							{
								Thread.sleep(1000);
							}
							Thread.sleep(sleepSpan);
						}
						catch(Exception e)//�׳��쳣
						{
							e.printStackTrace();
						}
					}
				}
				activity.hd.sendEmptyMessage(1);//������Ϣ����ʼ��������ģ��
			}
		}.start();
	}
	public void surfaceDestroyed(SurfaceHolder arg0)
	{//����ʱ������
	}
}