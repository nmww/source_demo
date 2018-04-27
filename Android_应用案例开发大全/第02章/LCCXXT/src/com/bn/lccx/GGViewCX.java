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
	int COMPONENT_WIDTH;							//�ÿؼ����
	int COMPONENT_HEIGHT;							//�ÿؼ��߶�
	boolean initflag=false;								//�Ƿ�Ҫ��ȡ�ؼ��ĸ߶ȺͿ�ȱ�־
	Bitmap[] bma;										//��Ҫ���ŵ�ͼƬ������
	Paint paint;										//����
	static int[] drawablesId;									//ͼƬID����
	int currIndex=0;										//ͼƬID�����±꣬���ݴ˱�����ͼƬ
	boolean workFlag=true;								//����ͼƬ�̱߳�־λ
	public GGViewCX(Context father,AttributeSet as) { 			//������
		super(father,as);								
		drawablesId=new int[]{						//��ʼ��ͼƬID����
		
			R.drawable.adv7,	
			R.drawable.adv8,	
			R.drawable.adv9
				
		};
		bma=new Bitmap[drawablesId.length];				//�������ͼƬ������
		initBitmaps();									//���ó�ʼ��ͼƬ��������ʼ��ͼƬ����
		paint=new Paint();								//��������
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);				//�������	
		new Thread(){									//��������ͼƬ�߳�
			public void run(){
				while(workFlag){
					currIndex=(currIndex+1)%drawablesId.length;//�ı�ID�����±�ֵ
					GGViewCX.this.postInvalidate();			//����
					try {
						Thread.sleep(3000);				//��Ϣ����
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}}}}.start();							//�����߳�
	}	
	public void initBitmaps(){								//��ʼ��ͼƬ����
		Resources res=this.getResources();					//��ȡResources����
		for(int i=0;i<drawablesId.length;i++){					
			bma[i]=BitmapFactory.decodeResource(res, drawablesId[i]);
		}}	
	public void onDraw(Canvas canvas){						//���ƺ���
		if(!initflag) {									//��һ�λ���ʱ��Ҫ��ȡ��Ⱥ͸߶�
			COMPONENT_WIDTH=this.getWidth();			//��ȡview�Ŀ��
			COMPONENT_HEIGHT=this.getHeight();			//��ȡview�ĸ߶�
			initflag=true;
		}
		int picWidth=bma[currIndex].getWidth();				//��ȡ��ǰ����ͼƬ�Ŀ��
		int picHeight=bma[currIndex].getHeight();				//��ȡ��ǰ����ͼƬ�ĸ߶�
		int startX=(COMPONENT_WIDTH-picWidth)/2;			//�õ�����ͼƬ�����Ͻ�X����
		int startY=(COMPONENT_HEIGHT-picHeight)/2; 		//�õ�����ͼƬ�����Ͻ�Y����
		canvas.drawARGB(255, 200, 128, 128);				//���ñ���ɫ
		canvas.drawBitmap(bma[currIndex], startX,startY, paint);	//����ͼƬ
	}}


