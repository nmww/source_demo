package com.bn.map;
import static com.bn.map.Constant.*;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.MotionEvent;
class GameSurfaceView extends GLSurfaceView 
{		
	MapMasetActivity father;//����Activity
	private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��Y����
    
    public static int guankaID;//�ܿ�ID
    public static int[][]MAP;//��Ӧ�ؿ��ĵ�ͼ����
    public static int[][] MAP_OBJECT;//��Ӧ�ؿ��Ķ�����
    public static int STIME;//ÿһ�ض�Ӧ��ʱ������
    
    public static float yAngle=0f;//��λ��
    public static float xAngle=90f;//���� 
    public static float cx;//�����x����
    public static float cy;//�����y����
    public static float cz;//�����z����
    public static float tx=0;//�۲�Ŀ���x����  
    public static float ty=0;//�۲�Ŀ���y����
    public static float tz=0f;//�۲�Ŀ���z����      
    public static float upX=0;
    public static float upY=1;
    public static float upZ=0;//up��
    
    public static float ballX;//��ĸ�������
    public static float ballY;
    public static float ballZ;
    public static float ballGX=0f;//x�����ϵļ��ٶ�
    public static float ballGZ=0f;//y�����ϵļ��ٶ�
    
    public static int ballCsX;//��ʼ����
    public static int ballCsZ;
    public static int ballMbX;//Ŀ�����
    public static int ballMbZ;
    
    public static float ballVX=0;//XZ�����ϵ��ٶ�
    public static float ballVZ=0;
   
    private SceneRenderer mRenderer;//������Ⱦ��	
    
    public static int floorId;//�ذ�����ID
    public static int wallId;//ǽ����
    public static int yuankonId;//Բ������Id
    public static int ballId;//������ID
    public static int ballYZId;//���Ӱ������ID
    public static int numberId;//����ID
    public static int time_DH_Id;//�ٺ�ID
    public static int mbyuankonId;
    
	public RectWall yuankon;//Բ�׾���
	public Floor floor;//�ذ�
	public static  Wall wall;//ǽ
	public BallTextureByVertex ball;//��
	public RectWall ballYZ;//���Ӱ�Ӿ���
	public Number number;//����
	public TextureRect time_DH;//�ٺţ�����ʱ��
	
	BallGDThread ballgdT;//���˶��߳�
	
	public GameSurfaceView(Context context)
	{
        super(context);
        this.father=(MapMasetActivity)context;
        ballCsX=CAMERA_COL_ROW[guankaID][0];//��ʼ����
        ballCsZ=CAMERA_COL_ROW[guankaID][1];
        
        ballMbX=CAMERA_COL_ROW[guankaID][2];//Ŀ������
        ballMbZ=CAMERA_COL_ROW[guankaID][3];
        
        MAP=MAPP[guankaID];//��ͼ����
        MAP_OBJECT=MAP_OBJECTT[guankaID];//������
        STIME=GD_TIME[guankaID];//����ʱ��
        
        ballX=ballCsX*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;//��ʼ����λ��
		ballZ=ballCsZ*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
		ballY=ballR;
       
        tx=0;//�����Ŀ��λ��
        ty=0;
        tz=0;
        ballgdT=new BallGDThread(this);
        //�����������λ��
        cx=(float)(tx+Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*DISTANCE);//�����x���� 
        cz=(float)(tz+Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*DISTANCE);//�����z���� 
        cy=(float)(ty+Math.sin(Math.toRadians(xAngle))*DISTANCE);//�����y���� 
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ      
       
    }	
	@Override 
    public boolean onTouchEvent(MotionEvent e) 
	{
        float y = e.getY();//�õ����µ�XY����
        float x = e.getX();
        switch (e.getAction())
        {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Xλ��
            yAngle += dx * TOUCH_SCALE_FACTOR;//���Ǹı�
            xAngle += dy * TOUCH_SCALE_FACTOR;//��λ�Ǹı�    
            if(xAngle+dy * TOUCH_SCALE_FACTOR<10)//�����ǰ���������С��15,��������ǿ��Ϊ15
            {
            	xAngle=10;
            }
            if(xAngle+dy * TOUCH_SCALE_FACTOR>90)//����������Ǵ���85,����ǿ��Ϊ85;
            {
            	xAngle=90;
            }
            requestRender();//�ػ滭��
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��    
        cx=(float)(tx+Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*DISTANCE);//�����x���� 
        cz=(float)(tz+Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*DISTANCE);//�����z���� 
        cy=(float)(ty+Math.sin(Math.toRadians(xAngle))*DISTANCE);//�����y���� 
        return true;
    }
	
	private class SceneRenderer implements GLSurfaceView.Renderer 
	{
		
        public void onDrawFrame(GL10 gl) 
        {  
        	//����ƽ����ɫ
            gl.glShadeModel(GL10.GL_SMOOTH);            
        	//�����ɫ��������Ȼ���
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);        	
        	//���õ�ǰ����Ϊģʽ����
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();    
            //����cameraλ��
            GLU.gluLookAt
            (gl, cx,cy,cz, tx,ty, tz,0,1, 0);   //���������
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�������
            gl.glEnable(GL10.GL_TEXTURE_2D);//��������
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY); 
            
            gl.glEnable(GL10.GL_LIGHTING);//�������
	        gl.glEnable(GL10.GL_LIGHT0);//��0�ŵ�  	        
	        //����ʹ�÷���������
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY); 
            
            floor.drawSelf(gl, floorId);//���Ƶذ�
            
            gl.glPushMatrix();//��������
            gl.glTranslatef(-MAP[0].length/2*UNIT_SIZE, 0, (-MAP.length/2)*UNIT_SIZE);
            wall.drawSelf(gl, wallId);//����ǽ           
            gl.glPopMatrix();//�ָ�����
            
            gl.glDisable(GL10.GL_LIGHTING);//�رչ���
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//�رշ���������
            
            gl.glEnable(GL10.GL_BLEND);//�������

            gl.glBlendFunc(GL10.GL_SRC_ALPHA ,GL10.GL_ONE_MINUS_SRC_ALPHA);//���û������
            gl.glPushMatrix();//������ǰ����
            gl.glTranslatef(ballMbX*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2,
            		0.015f,
            		ballMbZ*UNIT_SIZE- MAP.length*UNIT_SIZE/2);
            gl.glRotatef(-90, 1, 0, 0);
			yuankon.drawSelf(gl, mbyuankonId);//����Ŀ��Բ��
			gl.glPopMatrix();
            drawYuanKong(gl);//����Բ��           
            gl.glPushMatrix();
        	gl.glTranslatef(ballX+ballR-0.2f, 0.01f, ballZ-ballR+0.2f);
        	gl.glRotatef(-90, 1, 0, 0);
        	gl.glRotatef(45, 0, 0, 1);
        	ballYZ.drawSelf(gl, ballYZId);//����Ӱ��
        	gl.glPopMatrix();            
            gl.glDisable(GL10.GL_BLEND);//�رջ��                    
            drawBall(gl);//������            
            drawNumber(gl);//���Ƶ�ǰʣ��ʱ������            
            
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);   //�رն�������         
            gl.glDisable(GL10.GL_TEXTURE_2D);//�ر�����
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);        
         }        
        
        public  void drawNumber(GL10 gl)//����ʣ��ʱ�䷽��
        {
        	gl.glMatrixMode(GL10.GL_MODELVIEW);//�ָ�����   
	        gl.glLoadIdentity();   	     //���õ�ǰ����Ϊ��λ����
	      
	        gl.glEnable(GL10.GL_BLEND);//�������
	        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);//���û������
	        //���������Ǳ���    �߶�
	        gl.glPushMatrix();
	        gl.glTranslatef(2.0f,1.6f,-6);//�����Ǳ���λ��.�����ٵ���
	        number.y=0;//���ֵ�Y����
	        number.NumberStr=Math.abs(GD_TIME[guankaID]-STIME)/60+"";//ʣ�µķ�����
	        number.drawSelf(gl,0,numberId);//���Ʒ���
	        gl.glTranslatef(ICON_WIDTH*0.7f,0f,0);
	        time_DH.drawSelf(gl, time_DH_Id);//���ٺ�
	        gl.glTranslatef(ICON_WIDTH*0.7f,0f,0);
	        number.NumberStr=Math.abs(GD_TIME[guankaID]-STIME)%60+"";
	        number.drawSelf(gl,1,numberId);//������
	        gl.glPopMatrix();//�ָ�����
	        gl.glDisableClientState(GL10.GL_BLEND);//�رջ��       
        }
        public void onSurfaceChanged(GL10 gl, int width, int height) 
        {
            //�����Ӵ���С��λ�� 
        	gl.glViewport(0, 0, width, height);
        	//���õ�ǰ����ΪͶӰ����
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();
            //����͸��ͶӰ�ı���
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            gl.glFrustumf(-ratio, ratio, -1, 1, 3, 1000);   
        }
        public void onSurfaceCreated(GL10 gl, EGLConfig config) 
        {
            //�رտ����� 
        	gl.glDisable(GL10.GL_DITHER);
        	//�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //����Ϊ�򿪱������
    		gl.glEnable(GL10.GL_CULL_FACE);
    		 //������ɫģ��Ϊƽ����ɫ   
            gl.glShadeModel(GL10.GL_SMOOTH);
    		//�������   
            //������Ļ����ɫ��ɫRGBA
            gl.glClearColor(0,0,0,0);           
            //������Ȳ���
            gl.glEnable(GL10.GL_DEPTH_TEST);             
            floorId=initTexture(gl,R.drawable.floor);//����ID
            wallId=initTexture(gl,R.drawable.wall);    //ǽID
            
            yuankonId=initTexture2(gl,R.drawable.yuankon);//Բ��ID
            ballId=initTexture2(gl,R.drawable.ball);//��ID
            ballYZId=initTexture2(gl,R.drawable.ballyingzi);//���Ӱ��ID
            numberId=initTexture2(gl,R.drawable.number);//����ID
            time_DH_Id=initTexture2(gl,R.drawable.dunhao);//�ٺ�����
            mbyuankonId=initTexture2(gl,R.drawable.mbyuankon);//Ŀ��Բ��Id
            
            
            floor=new Floor(MAP[0].length,MAP.length);//�ذ�
            wall=new Wall();//ǽ     
            yuankon=new RectWall(2f*ballR,2f*ballR);//Բ��
            ball=new BallTextureByVertex(ballR,15);//��
            ballYZ=new RectWall(3.6f*ballR,2.6f*ballR);//Ӱ��
            number=new Number(GameSurfaceView.this);//���ֶ���
            time_DH=new TextureRect(ICON_WIDTH*0.5f/2,//����
            	 ICON_HEIGHT*0.5f/2,
           		 new float[]
		             {
		           	  0,0, 0,1, 1,0,
		           	  0,1, 1,1,  1,0
		             });//�ٺ�
            ballgdT.start();
            
            initLight(gl);//��ʼ���ƹ�
            float[] positionParamsGreen={-4,4,4,0};//����0��ʾ�Ƕ����
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
     
        }
        public void drawBall(GL10 gl)//��������
        {	
        	gl.glPushMatrix();
        	gl.glTranslatef(ballX, ballY, ballZ);     //�ƶ���Ӧ��λ�� 	
        	ball.drawSelf(gl, ballId);   //����
        	gl.glPopMatrix();       	
        }
        public void drawYuanKong(GL10 gl)//����Բ��
        {
        	gl.glPushMatrix();
        	gl.glTranslatef(-MAP[0].length*UNIT_SIZE/2, 0.01f,- MAP.length*UNIT_SIZE/2);
        	for(int i=0;i<MAP_OBJECT.length;i++)
        	{
        		for(int j=0;j<MAP_OBJECT[0].length;j++)
        		{
        			if(MAP_OBJECT[i][j]==1)
        			{
        				if(i==ballMbX&&j==ballMbZ)//�������Ŀ�궴�����
        				{
        				   continue;
        				}
        				gl.glPushMatrix();
        				gl.glTranslatef((j)*UNIT_SIZE, 0.001f, (i)*UNIT_SIZE);
        				gl.glRotatef(-90, 1, 0, 0);
        				yuankon.drawSelf(gl, yuankonId);//����
        				gl.glPopMatrix();
        			}
        		}
        	}
        	gl.glPopMatrix();
        }
        private void initLight(GL10 gl)
    	{
    		//��ɫ�ƹ�
            gl.glEnable(GL10.GL_LIGHT0);//��0�ŵ�  
            //����������
            float[] ambientParams={1f,1f,1f,1.0f};//����� RGBA
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
            //ɢ�������
            float[] diffuseParams={1f,1f,1f,1.0f};//����� RGBA
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
            //���������
            float[] specularParams={1f,1f,1f,1.0f};//����� RGBA
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);     
    	}
	 }
	//��ʼ������
	public int initTexture2(GL10 gl,int drawableId)//textureId
	{
		//��������ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
        
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp; 
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle();  
        return currTextureId;
	}

	//��ʼ������
	public int initTexture(GL10 gl,int drawableId)//textureId
	{
		//��������ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		
		//��MIN_FILTER MAG_FILTER��ʹ��MIPMAP����
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR_MIPMAP_LINEAR);
		// ����Mipmap����
		((GL11)gl).glTexParameterf(GL10.GL_TEXTURE_2D,GL11.GL_GENERATE_MIPMAP,GL10.GL_TRUE);
        //�������췽ʽ
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
        
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp; 
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle(); 
        return currTextureId;
	}
}
