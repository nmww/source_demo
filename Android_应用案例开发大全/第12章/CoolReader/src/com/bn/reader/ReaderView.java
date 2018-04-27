package com.bn.reader;

import java.util.HashMap;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.Toast;
import static com.bn.reader.Constant.*;

enum TurnDir{
	noTurning,left,right,//����ҳ����ǰ�������
}

public class ReaderView extends SurfaceView implements SurfaceHolder.Callback
{
	ReaderActivity readerActivity;//ReaderActivity������
	Paint paint;//���ʵ�����
	//��Ҫ���Ƶ���������ͼ������
	Bitmap bmLeft;//��ߵ�
	Bitmap bmRight;//�ұߵ�
	
	ReadRecord currRR;//��ǰҳ����
	
	//��ҳ���õ�����ʱ����
	Bitmap bmLeft_temp;//���ͼƬ��ʱ����
	Bitmap bmRight_temp;//�ұ�ͼƬ��ʱ����
	
	ReadRecord currRR_temp;//��¼ReadRecord��һ����ʱ����
	Bitmap bmBack;// ����ͼƬ
	Bitmap title;// ��ͷͼƬ

	AdThread at;//�������ˢ���߳�
	//���ͼƬ����
	int ad[]={R.drawable.ad_a,R.drawable.ad_b,R.drawable.ad_c,R.drawable.ad_d,
			R.drawable.ad_e,R.drawable.ad_f,R.drawable.ad_g,R.drawable.ad_h};
	//���صĹ��ͼƬ����
	Bitmap adb[]=new Bitmap[ad.length];
	
	//��ǰ����ı��ļ����˱��飩���Ķ�����
	HashMap<Integer,ReadRecord> currBook=new HashMap<Integer,ReadRecord>();
	
	//��ǰ��ҳ���ص�����
	float ax=-1;
	float ay=-1;	
	//���½�����
	int bx;
	int by;
	
	int[] cd;//c��d������������,����c��d����ֱ�Ϊ��������ҳ���͸ߵĽ���
	TurnDir turnDir=TurnDir.noTurning;//��ҳ����ö������
	boolean repaintAdFlag=true;//���ƹ��ı�־
	//ReaderView�Ĺ��췽��
    public ReaderView(ReaderActivity readerActivity) {
		super(readerActivity);		
		this.readerActivity=readerActivity;
		
		this.getHolder().addCallback((Callback) this);
		//��������
		paint=new Paint();		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		at=new AdThread(this);//�������ˢ���߳�
		bmBack=PicLoadUtil.LoadBitmap(this.getResources(), BITMAP);//����Ӧ��Ļ�ı���ͼƬ
		bmBack=PicLoadUtil.scaleToFit(bmBack, PAGE_WIDTH, PAGE_HEIGHT);
		
		title=PicLoadUtil.LoadBitmap(this.getResources(), R.drawable.bt);//����Ӧ��Ļ�ı�ͷͼƬ
		title=PicLoadUtil.scaleToFit(title, SCREEN_WIDTH, BLANK);
		
		for(int i=0;i<ad.length;i++)//����Ӧ��Ļ�Ĺ��ͼƬ
		{
			adb[i]=PicLoadUtil.LoadBitmap(this.getResources(), ad[i]);
			adb[i]=PicLoadUtil.scaleToFit(adb[i], AD_WIDTH, BLANK);
		}
		//��ʼ������ǰ�ļ���Xҳ
		currRR=new ReadRecord(CURRENT_LEFT_START,0,CURRENT_PAGE);
		
		if(CURRENT_PAGE==0)//����ǵ�һ�δ�ĳһ����
		{
			currBook.put(currRR.pageNo, currRR);//��һҳ����Ϣ����hashMap��			
		}
		
		//������������ͼƬ
		bmLeft=this.drawPage(currRR);
		bmRight=this.drawPage(currRR);
		repaint();
		at.start();//�������ˢ���߳�
	}
    @Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
    }

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		at.stopCurrentThread();//��ֹ����ˢ��
	}
	public void onDraw(Canvas canvas)
	{
		synchronized(paint)
		{	
			canvas.drawColor(Color.BLACK);//���ս���
			canvas.drawBitmap(title, 0, 0, paint);//���Ʊ�ͷͼƬ
			canvas.drawBitmap(adb[NUM],Constant.SCREEN_WIDTH-AD_WIDTH, 0, paint);//���ƹ����
			drawcut_line(canvas);//���Ʒָ���
			drawTitle(canvas);//������Ŀ			
			if(turnDir==TurnDir.right)
			{	
				canvas.drawBitmap(bmLeft, LEFT_OR_RIGHT_X, BLANK, paint);//��������Զ����ͼƬ
	
				//����a���λ�û����ұ���������ҳ====================begin===========================
				//���滭��״̬
				canvas.save();
				//�½�һ��·��
				Path path2=new Path();
				//��·��Ȧס�ķ�ΧΪe��f��c��d��g
				path2.moveTo(RIGHT_OR_LEFT_x,0);
				path2.lineTo(RIGHT_OR_LEFT_x, PAGE_HEIGHT+BLANK);			
				path2.lineTo(cd[0], cd[1]);
				path2.lineTo(cd[2], cd[3]);
				path2.lineTo(RIGHT_OR_LEFT_x+PAGE_WIDTH, BLANK);
				path2.lineTo(RIGHT_OR_LEFT_x,0);
				//Ϊ�������û��Ƽ���
				canvas.clipPath(path2);
				
				canvas.drawBitmap(bmRight, RIGHT_OR_LEFT_x,BLANK, paint);//�����Ҳ�ͼƬ
				//�ָ�����״̬
				canvas.restore();
				//����a���λ�û����ұ���������ҳ=====================end============================
				
				
				//����a���λ�û��Ʊ��������ķ���ҳ����===============begin==========================
				//���㷴����ҳ�����ת������
				float angle=(float)Math.toDegrees(Math.atan((ay-cd[3])/(ax-cd[2])));
				//������������ת����
				Matrix m1=new Matrix();
				m1.setRotate(90+angle, LEFT_OR_RIGHT_X,PAGE_HEIGHT);
				//����������תҳ�����½ǶԵ����ص���
				Matrix m2=new Matrix();
				m2.setTranslate
				(
					ax,	
					ay-PAGE_HEIGHT
				);
				//�����ܾ���
				Matrix mz=new Matrix();
				mz.setConcat(m2, m1);
				//���滭��״̬
				canvas.save();
				//��·��Ȧס�ķ�ΧΪa��c��d
				Path path3=new Path();
				path3.moveTo(ax,ay);		
				path3.lineTo(cd[0], cd[1]);
				path3.lineTo(cd[2], cd[3]);		
				path3.lineTo(ax,ay);
				canvas.clipPath(path3);
							
	
				canvas.drawBitmap(bmLeft_temp, mz, paint);//��������Զ����ͼƬ
	
				//�ָ�����״̬
				canvas.restore();
				//����a���λ�û��Ʊ��������ķ���ҳ����================end===========================
				
				
				//����a���λ�û���������Ҫ����������ҳ�Ļ���================begin====================
				//���滭��״̬
				canvas.save();
				//�½�һ��·��
				Path path1=new Path();
				//��·��Ȧס�ķ�ΧΪc��b��d
				path1.moveTo(cd[0], cd[1]);
				path1.lineTo(bx, by);
				path1.lineTo(cd[2], cd[3]);
				path1.lineTo(cd[0], cd[1]);
				//Ϊ�������û��Ƽ���
				canvas.clipPath(path1);
				paint.setAlpha(220);
				
				canvas.drawBitmap(bmRight_temp, RIGHT_OR_LEFT_x,BLANK, paint);//�����Ҳ�ͼƬ
				paint.setAlpha(255);
				//�ָ�����״̬
				canvas.restore();	
				//����a���λ�û���������Ҫ����������ҳ�Ļ���=================end=====================			
	
			}//�������
			else if(turnDir==TurnDir.left)			
			{
				//����a���λ�û��������������ҳ====================begin===========================
				//���滭��״̬
				canvas.save();
				//�½�һ��·��
				Path path2=new Path();
				//��·��Ȧס�ķ�ΧΪe��f��c��d��g
				path2.moveTo(PAGE_WIDTH,0);
				path2.lineTo(PAGE_WIDTH, PAGE_HEIGHT+BLANK);			
				path2.lineTo(cd[0], cd[1]);
				path2.lineTo(cd[2], cd[3]);
				path2.lineTo(0, BLANK);	
				path2.lineTo(PAGE_WIDTH,0);	
				//Ϊ�������û��Ƽ���
				canvas.clipPath(path2);
				canvas.drawBitmap(bmLeft, LEFT_OR_RIGHT_X, BLANK, paint);//��������Զ����ͼƬ
				//�ָ�����״̬
				canvas.restore();
				//����a���λ�û��������������ҳ=====================end============================
				
				//�����ұ���������ҳ
				canvas.drawBitmap(bmRight, RIGHT_OR_LEFT_x,BLANK, paint);
				
				//����a���λ�û���������Ҫ����������ҳ�Ļ���================begin====================
				//���滭��״̬
				canvas.save();
				//�½�һ��·��
				Path path1=new Path();
				//��·��Ȧס�ķ�ΧΪc��b��d
				path1.moveTo(cd[0], cd[1]);
				path1.lineTo(bx, by);
				path1.lineTo(cd[2], cd[3]);
				path1.lineTo(cd[0], cd[1]);
				//Ϊ�������û��Ƽ���
				canvas.clipPath(path1);
				paint.setAlpha(220);//�����Ľ�Ϊ��͸��
				
				canvas.drawBitmap(bmLeft_temp, LEFT_OR_RIGHT_X, BLANK, paint);//��������Զ����ͼƬ
			
				paint.setAlpha(255);
				//�ָ�����״̬
				canvas.restore();
				//����a���λ�û���������Ҫ����������ҳ�Ļ���=================end=====================
				
				
				//����a���λ�û��Ʊ��������ķ���ҳ����===============begin==========================
				//���㷴����ҳ�����ת������
				float angle=(float)Math.toDegrees(Math.atan((ax-cd[0])/(ay-cd[1])));//��ǰ��ҳʱ������Ƕ��õ�c������
				//������������ת����
				Matrix m1=new Matrix();
				m1.setRotate(-90-angle, PAGE_WIDTH ,PAGE_HEIGHT );//��ͼƬ���½�Ϊ��ת���ĵ㣬��ʱ����ת90+angle
				//����������תҳ�����½ǶԵ����ص���
				Matrix m2=new Matrix();
				m2.setTranslate
				(
					ax-PAGE_WIDTH ,	
					ay-PAGE_HEIGHT
				);
				//�����ܾ���
				Matrix mz=new Matrix();
				mz.setConcat(m2, m1);
				//���滭��״̬
				canvas.save();
				//��·��Ȧס�ķ�ΧΪa��c��d
				Path path3=new Path();
				path3.moveTo(ax,ay);		
				path3.lineTo(cd[0], cd[1]);
				path3.lineTo(cd[2], cd[3]);		
				path3.lineTo(ax,ay);
				canvas.clipPath(path3);
				
				canvas.drawBitmap(bmRight_temp, mz, paint);
				//�ָ�����״̬
				canvas.restore();
				//����a���λ�û��Ʊ��������ķ���ҳ����================end===========================
			}
			else
			{
				canvas.drawBitmap(bmLeft, LEFT_OR_RIGHT_X, BLANK, paint);//��������Զ����ͼƬ
				canvas.drawBitmap(bmRight, RIGHT_OR_LEFT_x,BLANK, paint);//�����Ҳ��Զ����ͼƬ
			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)
	{
		
		 switch(keyCode)
		 {
		    case 4:
		    	readerActivity.showDialog(readerActivity.EXIT_READER);//�˳��Ի���
				break;
		    case 22:
		    	repaintAdFlag=false;//���ƹ��ı�־��Ϊfalse
				//��ʼ������һҳ����
		    	currRR=new ReadRecord(Constant.nextPageStart,0,Constant.nextPageNo);
	
		    	Constant.CURRENT_LEFT_START=currRR.leftStart;//��¼��ǰ������leftstart��ֵ
		    	Constant.CURRENT_PAGE=currRR.pageNo;//��¼��ǰ��������page��ֵ
	    	
		    	currBook.put(currRR.pageNo, currRR);//��ǰҳ����Ϣ����hashMap
		    	
		    	
		    	if(currRR.leftStart>Constant.CONTENTCOUNT){
				   Toast.makeText
				   (
						this.getContext(), 
						"�Ѿ������һҳ�ˣ��������������ˣ�", 
						Toast.LENGTH_SHORT
					).show();
				}else
				{
					//������������ͼƬ
					bmLeft=drawPage(currRR);
					bmRight=drawPage(currRR);
					repaint();
				}
		    	repaintAdFlag=true;//����ͼƬ���ػ�
				break;
			    case 21:
			    	repaintAdFlag=false;//���ƹ��ı�־��Ϊfalse
				   if(currRR.pageNo==0){
						Toast.makeText
						(
							this.getContext(), 
							"�Ѿ�����һҳ������������ǰ���ˣ�", 
							Toast.LENGTH_SHORT
						).show();				
					}
					else
					{
						currRR=currBook.get(currRR.pageNo-1);
						
						Constant.CURRENT_LEFT_START=currRR.leftStart;//��¼��ǰ������leftstart��ֵ
						Constant.CURRENT_PAGE=currRR.pageNo;//��¼��ǰ��������page��ֵ

						currRR.isLeft=true;
						bmLeft=drawPage(currRR);
						bmRight=drawPage(currRR);
						repaint();
					}
				   repaintAdFlag=true;//����ͼƬ���ػ�
				   break;		    	
		    case 82:
		    	readerActivity.openOptionsMenu();
				   break; 
		 }
		   return true;
	}
	public boolean onTouchEvent(MotionEvent e) 
	{  
		repaintAdFlag=false;
    	float x = e.getX();//��ȡ���ص�X����
        float y = e.getY();//��ȡ���ص�Y����    	
        
        switch (e.getAction()) 
        {
            case MotionEvent.ACTION_DOWN:
            	/*
            	 * ������ʱ�ж���Ҫ��󷭻���Ҫ��ǰ����
            	 * �ٳ�ʼ����Ӧ��b�������ֵ
            	 */
      	
            	if(x>RIGHT_OR_LEFT_x )//��������ұߣ�ȷ��ΪҪ���ҳ
            	{
            		//��ʼ��Ϊ���½�����
            		bx=SCREEN_WIDTH;
            		by=SCREEN_HEIGHT;
            	}
            	else//���������ߣ�ȷ��ΪҪ��ǰ��ҳ
            	{
            		//��ʼ��Ϊ���½�����
            		bx=0;
            		by=PAGE_HEIGHT+BLANK;
            	}
            	//����c��d��������
            	cd=CalUtil.calCD(x, y, bx, by);

            	//�����ΰ��µ�λ�������½�ָ����Χ����������Ʒ�ҳЧ��
            	if(x>PAGE_WIDTH*1.7f&&x<SCREEN_WIDTH&&cd[0]>RIGHT_OR_LEFT_x)   
                {
            		if(Constant.nextPageStart>Constant.CONTENTCOUNT){
        				Toast.makeText
        				(
        					this.getContext(), 
        					"�Ѿ������һҳ�ˣ��������������ˣ�", 
        					Toast.LENGTH_SHORT
        				).show();
        				repaintAdFlag=true;//����ͼƬ���ػ�
        				return true;
        			}

            	turnDir=TurnDir.right;             	   
            	 //������ʱ��һҳ��ReadRecord��Ķ���
       			currRR_temp=new ReadRecord(Constant.nextPageStart,0,Constant.nextPageNo);
       			//����Constant.nextPageNo��Constant.nextPageNo����ֵ
           		int t1=Constant.nextPageNo;
           		int t2=Constant.nextPageStart;
           		//������һҳ������ͼƬ
       			bmLeft_temp=drawPage(currRR_temp);        			
       			bmRight_temp=drawPage(currRR_temp);        			
				Constant.nextPageNo=t1;
           		Constant.nextPageStart=t2;        	
            	   
                }//�����ΰ��µ�λ�������½�ָ����Χ�������������ǰ�ķ�ҳЧ��
            	else 
            		if(x<PAGE_WIDTH*0.3&&cd[0]<PAGE_WIDTH)
                {
        			if(currRR.pageNo<=0){
						Toast.makeText
						(
							this.getContext(), 
							"�Ѿ�����һҳ������������ǰ���ˣ�", 
							Toast.LENGTH_SHORT
						).show();	
						repaintAdFlag=true;//����ͼƬ���ػ�
						return true;
					}
        			
            		turnDir=TurnDir.left;
            		
            		currRR_temp=currBook.get(currRR.pageNo-1);

            		int t1=Constant.nextPageNo;
            		int t2=Constant.nextPageStart;
            		currRR_temp.isLeft=true;    		
					bmLeft_temp=drawPage(currRR_temp);
					bmRight_temp=drawPage(currRR_temp);
					Constant.nextPageNo=t1;
            		Constant.nextPageStart=t2;	
                }
            	ax=x;
          	   	ay=y;          	 
            break;        
            case MotionEvent.ACTION_MOVE: 
            	//��ҳʱ��̬����c��d��������
            	cd=CalUtil.calCD(x, y, bx, by);
            	
            	//���ƶ�������û��˺ֽ��������Ʒ�ҳЧ��
                if(x>0&&x<SCREEN_WIDTH&&
             		   (turnDir==TurnDir.right&&cd[0]>PAGE_WIDTH)||//���ҳʱû��˺ֽ
             		   turnDir==TurnDir.left&&cd[0]<PAGE_WIDTH)//��ǰ��ҳʱû��˺ֽ 
            	
                {
             	   ax=x;
             	   ay=y;            	   
                }
                else
                {
             	   turnDir=TurnDir.noTurning;
                }
              //��̧���λ�������ָ����Χ��ʵʩ���ҳ������һҳ������ֵС������������
                if(turnDir==TurnDir.right && ax<PAGE_WIDTH*0.1f)	
                { 
                	currRR=new ReadRecord(Constant.nextPageStart,0,Constant.nextPageNo);
  				   	Constant.CURRENT_LEFT_START=currRR.leftStart;//��¼��ǰ������leftstart��ֵ
  				   	Constant.CURRENT_PAGE=currRR.pageNo;//��¼��ǰ��������page��ֵ
  				   	
  				   	currBook.put(currRR.pageNo, currRR);//��ǰҳ����Ϣ����hashMap
			   
					//������������ͼƬ
					bmLeft=drawPage(currRR);
					bmRight=drawPage(currRR);  
					
					turnDir=TurnDir.noTurning;
                }
                //��̧���λ�����ұ�ָ����Χ��ʵʩ��ǰ��ҳ
                else if(turnDir==TurnDir.left && ax>PAGE_WIDTH*1.9f)	
                {
					currRR=currBook.get(currRR.pageNo-1);
					
					Constant.CURRENT_LEFT_START=currRR.leftStart;//��¼��ǰ������leftstart��ֵ
					Constant.CURRENT_PAGE=currRR.pageNo;//��¼��ǰ��������page��ֵ
					
					currRR.isLeft=true;
					bmLeft=drawPage(currRR);
					bmRight=drawPage(currRR);
					
					turnDir=TurnDir.noTurning;
                }
            break;
            case MotionEvent.ACTION_UP:            	
                turnDir=TurnDir.noTurning;                 
              break;              
        }  
        this.repaint();
        repaintAdFlag=true;//����ͼƬ���ػ�
        return true;
    }
   //����Bitmap�ķ���
	public Bitmap drawPage(ReadRecord rr)
	{
		int start=0;
		if(rr.isLeft)
		{
			start=rr.leftStart;
		}
		else
		{
			start=rr.rightStart;
		}
		
		Bitmap bm=Bitmap.createBitmap(PAGE_WIDTH, PAGE_HEIGHT,Bitmap.Config.ARGB_8888);
		Canvas canvas=new Canvas(bm);
		
		canvas.drawBitmap(bmBack,0,0, paint);
		canvas.drawBitmap(bmBack,0,0, paint);
		
		try
		{
			synchronized(paint)
			{
				String str=null;
				paint.setColor(COLOR);
				paint.setTextSize(TEXT_SIZE);//�����ֵĴ�С
				if(Constant.FILE_PATH==null)
				{
					str=TextLoadUtil.loadFromSDFile(this,start,PAGE_LENGTH,Constant.DIRECTIONSNAME);//��ȡ˵��
					CONTENTCOUNT=TextLoadUtil.getCharacterCountApk(this, Constant.DIRECTIONSNAME);
				}else//���������
				{
					str=TextLoadUtil.readFragment(start, PAGE_LENGTH, FILE_PATH);//��ȡ����
				}
				int index=0;
				int index2=0;//��\n'ռ�����ַ�
				char c=str.charAt(index);
				boolean finishFlag=false;		
				int currRow=0;
				int currX=0;
				while(!finishFlag)
				{
					if(c=='\n')  
					{//����ǻ��� 
						currRow++;
						currX=0;
						index2++;
					}
					else if((c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0'))
					{//Ӣ�Ĵ�Сд������
						canvas.drawText(c+"", currX+TEXT_SIZE/2, currRow*TEXT_SIZE+TEXT_SIZE, paint);
						currX=currX+TEXT_SPACE_BETWEEN_EN;
					}
					else
					{//����
						canvas.drawText(c+"", currX+TEXT_SIZE/2, currRow*TEXT_SIZE+TEXT_SIZE, paint);
						currX=currX+TEXT_SPACE_BETWEEN_CN;
					}
					index++;
					c=str.charAt(index);
					
					if(currX>=Constant.PAGE_WIDTH-TEXT_SIZE)
					{
						currRow=currRow+1;
						currX=0;
					}
					if(currRow==ROWS)
					{
						finishFlag=true;
						if(rr.isLeft)
						{
							rr.isLeft=false;
							rr.rightStart=index+index2+rr.leftStart;
						}
						else
						{
							nextPageStart=rr.rightStart+index+index2;
							nextPageNo=rr.pageNo+1;
						}
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return bm;
	}
	
	   //��������ҳBitmap�ķ���
	public void drawVirtualPage(ReadRecord rr)
	{
		int start=0;
		if(rr.isLeft)
		{
			start=rr.leftStart;
		}
		else
		{
			start=rr.rightStart;
		}
		
		try
		{
			synchronized(paint)
			{
				String str=null;
				paint.setColor(COLOR);
				paint.setTextSize(TEXT_SIZE);//�����ֵĴ�С
				
				
				str=TextLoadUtil.readFragment(start, PAGE_LENGTH, FILE_PATH);//��ȡ����
				
				int index=0;
				int index2=0;//��\n'ռ�����ַ�
				char c=str.charAt(index);
				boolean finishFlag=false;		
				int currRow=0;
				int currX=0;
				while(!finishFlag)
				{
					if(c=='\n')  
					{//����ǻ��� 
						currRow++;
						currX=0;
						index2++;
					}
					else if((c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0'))
					{//Ӣ�Ĵ�Сд������
						currX=currX+TEXT_SPACE_BETWEEN_EN;
					}
					else
					{//����
						currX=currX+TEXT_SPACE_BETWEEN_CN;
					}
					index++;
					c=str.charAt(index);
					
					if(currX>=Constant.PAGE_WIDTH-TEXT_SIZE)
					{
						currRow=currRow+1;
						currX=0;
					}
					if(currRow==ROWS)
					{
						finishFlag=true;
						if(rr.isLeft)
						{
							rr.isLeft=false;
							rr.rightStart=index+index2+rr.leftStart;
						}
						else
						{
							nextPageStart=rr.rightStart+index+index2;
							nextPageNo=rr.pageNo+1;
						}
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	public void drawTitle(Canvas canvas)
	{
		try
		{
			synchronized(paint)
			{
				paint.setColor(Color.BLACK);
				paint.setTextSize(TITLE_SIZE);
				canvas.drawText("����Ķ���", 0, TITLE_SIZE, paint);
				if(Constant.FILE_PATH==null)
				{
					canvas.drawText("˵��", Constant.SCREEN_WIDTH/2-TITLE_SIZE, TITLE_SIZE, paint);//���ơ�˵����
					
				}else//������д����txt������
				{
					//�������ִ�Լ�����м�λ��
					canvas.drawText(Constant.TEXTNAME,Constant.SCREEN_WIDTH/2-3*TITLE_SIZE,TITLE_SIZE, paint);//������Ҫ��
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void drawcut_line(Canvas canvas)
	{
		try
		{
			synchronized(paint)
			{
				paint.setColor(Color.YELLOW);//���Ʒָ���
				canvas.drawRect(CENTER_LEFT_X, CENTER_LEFT_Y, CENTER_RIGHT_X, CENTER_RIGHT_Y, paint);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//���»��Ƶķ���
    public void repaint()
	{
		Canvas canvas=this.getHolder().lockCanvas();
		try
		{
			synchronized(canvas)
			{
				onDraw(canvas);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				this.getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}
    
}
