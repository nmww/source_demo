package com.bn.map;			//���������
import java.util.Vector;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.bn.map.Constant.*;
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	MapMasetActivity activity;		//Activity����
	Canvas c;
	SurfaceHolder holder;
    int scoreWidth = 10;
    int guanshuX;//��������X����
    int guanshuY;//��������Y����
    int guanshu=1;
    Bitmap iback;//����ͼ
    Bitmap[] iscore=new Bitmap[10];//�÷�ͼ
    Bitmap JianHaotupian;//����ͼ
    Bitmap JiaHaotupian;//�Ӻ�ͼ
    Bitmap[] guanShu=new Bitmap[10];//����ͼ
    Bitmap time_wz;//ʱ������ͼ
    Bitmap gread_wz;//�ɼ�����ͼ
    Bitmap hengXian;//����
	public GameView(MapMasetActivity activity) {
		super(activity);
		getHolder().addCallback(this);//ע��ص��ӿ�
		this.activity = activity;
		initBitmap();
	}
	//��ͼƬ����
	public void initBitmap(){
		iback = BitmapFactory.decodeResource(getResources(), R.drawable.main);
		iscore[0] = BitmapFactory.decodeResource(getResources(), R.drawable.d0);//����ͼ
		iscore[1] = BitmapFactory.decodeResource(getResources(), R.drawable.d1);
		iscore[2] = BitmapFactory.decodeResource(getResources(), R.drawable.d2);
		iscore[3] = BitmapFactory.decodeResource(getResources(), R.drawable.d3);
		iscore[4] = BitmapFactory.decodeResource(getResources(), R.drawable.d4);
		iscore[5] = BitmapFactory.decodeResource(getResources(), R.drawable.d5);
		iscore[6] = BitmapFactory.decodeResource(getResources(), R.drawable.d6);
		iscore[7] = BitmapFactory.decodeResource(getResources(), R.drawable.d7);
		iscore[8] = BitmapFactory.decodeResource(getResources(), R.drawable.d8);
		iscore[9] = BitmapFactory.decodeResource(getResources(), R.drawable.d9);
		
		guanShu[0] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka);//�ؿ�ͼ
		guanShu[1] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka1);
		guanShu[2] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka2);
		guanShu[3] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka3);
		guanShu[4] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka4);
		guanShu[5] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka5);
		JiaHaotupian = BitmapFactory.decodeResource(getResources(), R.drawable.right);
		JianHaotupian = BitmapFactory.decodeResource(getResources(), R.drawable.left);
		gread_wz = BitmapFactory.decodeResource(getResources(), R.drawable.grade);//�ɼ�����
		time_wz= BitmapFactory.decodeResource(getResources(), R.drawable.time);//ʱ������
		hengXian=BitmapFactory.decodeResource(getResources(), R.drawable.hengxian);//����
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas); 
		canvas.drawColor(Color.argb(255, 0, 0, 0));
		canvas.drawBitmap(iback,30,0, null);//������
		//���Ƽ��źͼӺ�ͼƬ
		canvas.drawBitmap(JianHaotupian,SCREEN_WIDTH/6,SCREEN_HEIGHT/6+40, null);	
		//���ƹؿ�����
		canvas.drawBitmap(guanShu[guanshu-1],SCREEN_WIDTH/2-60,SCREEN_HEIGHT/6+40, null);
		//�����ұ߼Ӻ�
		canvas.drawBitmap(JiaHaotupian,SCREEN_WIDTH/2+80,SCREEN_HEIGHT/6+40, null);
		//���Ƴɼ�����gread_wz
		canvas.drawBitmap(gread_wz,SCREEN_WIDTH/6,SCREEN_HEIGHT/6+63, null);
		//������Ϸʱ������
		canvas.drawBitmap(time_wz,SCREEN_WIDTH/2+80,SCREEN_HEIGHT/6+63, null);
		String sql_select="select grade,time from rank where level="+guanshu+" order by grade desc limit 0,5;";
    	Vector<Vector<String>> vector=SQLiteUtil.query(sql_select);//�����ݿ���ȡ����Ӧ������
    	for(int i=0;i<vector.size();i++)//ѭ���������а�ķ����Ͷ�Ӧʱ��
    	{
    		drawScoreStr(canvas,vector.get(i).get(0).toString(),SCREEN_WIDTH/6,SCREEN_HEIGHT/6+40+60+i*30);//�ɼ�������
    		drawRiQi(canvas,vector.get(i).get(1).toString(),SCREEN_WIDTH/2+65,SCREEN_HEIGHT/6+40+60+i*30);
    	}
	}
	public void drawScoreStr(Canvas canvas,String s,int width,int height)//�����ַ�������
	{
    	//���Ƶ÷�
    	String scoreStr=s; 
    	for(int i=0;i<scoreStr.length();i++){//ѭ�����Ƶ÷�
    		int tempScore=scoreStr.charAt(i)-'0';
    		canvas.drawBitmap(iscore[tempScore], width+i*scoreWidth,height, null);
    		}
	}
	public void drawRiQi(Canvas canvas,String s,int width,int height)//������
	{
		String ss[]=s.split("-");//�и�õ�������
		drawScoreStr(canvas,ss[0],width,height);//����������
		canvas.drawBitmap(hengXian,width+scoreWidth*4,height, null);//������
		drawScoreStr(canvas,ss[1],width+scoreWidth*5,height);//����������
		canvas.drawBitmap(hengXian,width+scoreWidth*7,height, null);//������
		drawScoreStr(canvas,ss[2],width+scoreWidth*8,height);//��������
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		int x = (int) event.getX();
		int y = (int) event.getY();
		if(x>SCREEN_WIDTH/6&&x<SCREEN_WIDTH/6+60&&
				y>SCREEN_HEIGHT/6+40&&y<SCREEN_HEIGHT/6+40+40)
		{			
			if(guanshu>1)
			{
				guanshu--;
				c = null;
	            try {
	            	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
	                c = holder.lockCanvas(null);
	                synchronized (holder) {
	                	onDraw(c);//����
	                }
	            } finally {
	                if (c != null) {
	                	//���ͷ���
	                	holder.unlockCanvasAndPost(c);
	                }
	            }
			}
		}
		if(x>SCREEN_WIDTH/2+80&&x<SCREEN_WIDTH/2+140
				&&y>SCREEN_HEIGHT/6+40&&y<SCREEN_HEIGHT/6+80){			
			if(guanshu<MAPP.length)
			{
				guanshu++;
				c = null;
	            try {
	            	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
	                c = holder.lockCanvas(null);
	                synchronized (holder) {
	                	onDraw(c);//����
	                }
	            } finally {
	                if (c != null) {
	                	//���ͷ���
	                	holder.unlockCanvasAndPost(c);
	                }
	            }
			}
		}		
		return super.onTouchEvent(event);
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
	public void surfaceCreated(SurfaceHolder holder) {//����ʱ������Ӧ����		
		this.holder=holder;        
            c = null;
            try {
            	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
                c = holder.lockCanvas(null);
                synchronized (holder) {
                	onDraw(c);//����
                }
            } finally {
                if (c != null) {
                	//���ͷ���
                	holder.unlockCanvasAndPost(c);
                }
            }
	}
	public void surfaceDestroyed(SurfaceHolder holder) {//�ݻ�ʱ�ͷ���Ӧ����
	}
}
