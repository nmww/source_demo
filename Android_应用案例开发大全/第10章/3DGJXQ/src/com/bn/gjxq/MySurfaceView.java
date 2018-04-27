package com.bn.gjxq;

import java.io.IOException;
import java.io.InputStream;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;
import static com.bn.gjxq.Constant.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MySurfaceView extends GLSurfaceView
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320*3;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
	
	private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��Y����
    
    static float cx;//�����x����
    static float cy;//�����y����
    static float cz;//�����z����
    
    static float tx;//�۲�Ŀ���x����  
    static float ty;//�۲�Ŀ���y����
    static float tz;//�۲�Ŀ���z����
 
    static float yAngle=0;//��λ��
    static float xAngle=30;//����
    static boolean OKMove=false;//�Ƿ���Ҫ�ƶ����ӱ�־
    
    static int herosquareZ=7;//�������θ��ӵĳ�ʼ����
    static int herosquareX=0;//�������θ��ӵĳ�ʼ����
    
    static int herosquarez;//��ȷ����Ҫ��ĳ��Ӣ��ʱ����¼���Ӣ�۵�ԭ���������ꡣ
    static int herosquarex;
    
    static LoadedObjectVertexNormalTexture[] qizi;//ģ������
    ChessboardForDraw cb;//����
    
    ChessForControl[][] currBoard;//��������
    static int[][] road;
    GJXQActivity father;//activity����
  
	public MySurfaceView(Context context)
	{ 
        super(context);
        father=(GJXQActivity)context;//Activity����
        mRenderer = new SceneRenderer();	
        setRenderer(mRenderer);				 	
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//����Ϊ������Ⱦģʽ
        tx=0;//�۲�Ŀ���x����  
	    ty=0;//ƽ�ӹ۲�Ŀ���y����
	    tz=0;//�۲�Ŀ���z����
	    road=new int[8][8];

	    //��ʼ������·��
	    if(father.ca.num==1)
	    {
	    	herosquareZ=1;//����Ǻڷ�,�Ѻ���Ӷ�λ��1,0��������
	    	herosquareX=0;
	        //�����ǰΪ�ڷ�,���괦�ںڷ�λ��
	    }
	    else
	    {
	    	herosquareZ=6;//����ǰ׷�,�ͰѺ���ӷ���6,0��������
	    	herosquareX=0;
	    }
	    road[herosquareZ][herosquareX]=1;//�ѳ�ʼλ�õĺ췽��λ�ڴ�  
	    yAngle=((father.ca.num%2)==1)?180:0;//�����ǰΪ�ڷ�,��������������ת180.
		cx=(float)(tx+Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*DISTANCE);//�۲���x���� 
        cz=(float)(tz+Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*DISTANCE);//�۲���z���� 
        cy=(float)(ty+Math.sin(Math.toRadians(xAngle))*DISTANCE);  
       //��ʼ������
        cb=new ChessboardForDraw();
        initBoard();//��ʼ������
    }
	
	public void initBoard()//��ʼ����������ĺ���
	{
		currBoard=new ChessForControl[8][8];
		
		currBoard[0][0]=new ChessForControl(qizi[0] ,0,0,0);//�ڳ�
		currBoard[0][1]=new ChessForControl(qizi[1] ,1,0,1);//����
		currBoard[0][2]=new ChessForControl(qizi[2] ,2,0,2);//����
		currBoard[0][3]=new ChessForControl(qizi[3] ,3,0,3);//�ں�
		currBoard[0][4]=new ChessForControl(qizi[4] ,4,0,4);//����
		currBoard[0][5]=new ChessForControl(qizi[2] ,2,0,5);//����
		currBoard[0][6]=new ChessForControl(qizi[1] ,1,0,6);//����
		currBoard[0][7]=new ChessForControl(qizi[0] ,0,0,7);//�ڳ�
		
		currBoard[1][0]=new ChessForControl(qizi[5] ,5,1,0);//�ڱ�
		currBoard[1][1]=new ChessForControl(qizi[5] ,5,1,1);//�ڱ�
		currBoard[1][2]=new ChessForControl(qizi[5] ,5,1,2);//�ڱ�
		currBoard[1][3]=new ChessForControl(qizi[5] ,5,1,3);//�ڱ�
		currBoard[1][4]=new ChessForControl(qizi[5] ,5,1,4);//�ڱ�
		currBoard[1][5]=new ChessForControl(qizi[5] ,5,1,5);//�ڱ�
		currBoard[1][6]=new ChessForControl(qizi[5] ,5,1,6);//�ڱ�
		currBoard[1][7]=new ChessForControl(qizi[5] ,5,1,7);//�ڱ�
		
		currBoard[6][0]=new ChessForControl(qizi[5] ,11,6,0);//�ױ�
		currBoard[6][1]=new ChessForControl(qizi[5] ,11,6,1);//�ױ�
		currBoard[6][2]=new ChessForControl(qizi[5] ,11,6,2);//�ױ�
		currBoard[6][3]=new ChessForControl(qizi[5] ,11,6,3);//�ױ�
		currBoard[6][4]=new ChessForControl(qizi[5] ,11,6,4);//�ױ�
		currBoard[6][5]=new ChessForControl(qizi[5] ,11,6,5);//�ױ�
		currBoard[6][6]=new ChessForControl(qizi[5] ,11,6,6);//�ױ�
		currBoard[6][7]=new ChessForControl(qizi[5] ,11,6,7);//�ױ�
		
		currBoard[7][0]=new ChessForControl(qizi[0] ,6,7,0);//�׳�
		currBoard[7][1]=new ChessForControl(qizi[1] ,7,7,1);//����
		currBoard[7][2]=new ChessForControl(qizi[2] ,8,7,2);//����
		currBoard[7][3]=new ChessForControl(qizi[3] ,9,7,3);//�׺�
		currBoard[7][4]=new ChessForControl(qizi[4] ,10,7,4);//����
		currBoard[7][5]=new ChessForControl(qizi[2] ,8,7,5);//����
		currBoard[7][6]=new ChessForControl(qizi[1] ,7,7,6);//����
		currBoard[7][7]=new ChessForControl(qizi[0] ,6,7,7);//�׳�	
	}

	@Override 
    public boolean onTouchEvent(MotionEvent e) 
	{
        float y = e.getY();
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
            if(xAngle+dy * TOUCH_SCALE_FACTOR>85)//����������Ǵ���85,����ǿ��Ϊ85;
            {
            	xAngle=85;
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e)
    { 
		if(!father.ca.perFlag)//��ǰ���Ǹ��������,��ôֱ�ӷ���,������κβٿ�Ȩ��
		{
			return false;
		}
		if(keyCode==19||keyCode==20||keyCode==21||keyCode==22)//����ǰ���ǰ�����ҽ������ƶ������.
		{
			road[herosquareZ][herosquareX]=0;//���˴��ĸ���ȥ��
			if(keyCode==19)//�ֱ����ǰ�ƣ����ƣ����ƣ�����
    		{
				herosquareZ++;
    		}
    		else if(keyCode==20)//����ƶ�
    		{
    			herosquareZ--;
    		}
    		else if(keyCode==21)//�����ƶ�
    		{
    			herosquareX++;
    		}
    		else if(keyCode==22)//�����ƶ�
    		{
    			herosquareX--;
    		}
    	
    		if(herosquareX<0)//����ƶ�����߽���,��ô�����ƶ�,
    		{
    			herosquareX=0;
			}
			if(herosquareX>7)//�ƶ������ұ߽���
			{
				herosquareX=7;
			}
			if(herosquareZ<0)//�ƶ������ϱ߽���
			{
				herosquareZ=0;
			}
			if(herosquareZ>7)//�ƶ������±߽���
			{
				herosquareZ=7;
			}
			
			road[herosquareZ][herosquareX]=1;//�Ѹ����ƶ�����ǰλ��
			
		}
		else if(keyCode==62)//����ǿո��,���������Ҳٿ����ӵĽ�.
		{
			if(!OKMove&&currBoard[herosquareZ][herosquareX]!=null)//��һ�ΰ��¿ո�
			{
				if(currBoard[herosquareZ][herosquareX].chessType>=(father.ca.num%2)*6
				   &&currBoard[herosquareZ][herosquareX].chessType<(father.ca.num%2+1)*6)
					//��һ�ΰ��¿ո�,�˸��Ӳ�Ϊ��,���Ҹ������ǶԷ���,��ô���ɲٿ�
						{
							Toast.makeText
							(
									father, 
									"���ܶ����˵�����,�����²���!", 
									Toast.LENGTH_SHORT
							).show();
						
						}
				else//������Լ�������,�����ǵ�һ�α�־,��ô��¼��ǰλ��
					{OKMove=true;//���˱�־λΪtrue
					herosquarez=herosquareZ;//���Ҽ�¼��ǵ�Ӣ��
					herosquarex=herosquareX;
					currBoard[herosquarez][herosquarex].y=0.4f;
					}
			}
			else if(OKMove&&currBoard[herosquarez][herosquarex]!=null
					&&GuiZe.canMove(currBoard[herosquarez][herosquarex], currBoard, herosquareZ, herosquareX)
					)//�ӿ��߽ӿڣ���
    			//�ڶ��ΰ��¿ո񣬶��ҿ���.
    		{				
      			OKMove=false;//ͬʱ�ָ���ǣ�������һ��      			
      			int srcRow=herosquarez;
      			int srcCol=herosquarex;
      			int dstRow=herosquareZ;
      			int dstCol=herosquareX;     
      			String msg="<#MOVE#>"+srcRow+","+srcCol+","+dstRow+","+dstCol;//������������������,����Я��������Ϣ
      			father.ca.sendMessage(msg);
      			father.ca.perFlag=false;
    		}
    		else if(OKMove&&currBoard[herosquarez][herosquarex]!=null&&!GuiZe.canMove(currBoard[herosquarez][herosquarex], currBoard, herosquareZ, herosquareX))//�ڶ��ΰ��¿ո񣬸õط������ߣ���ʾ��Ҳ��������
    		{//�ڶ��ΰ�������,���Ҳ�����,��ô�ָ����,����һ��̧������ӻָ�ԭ״
    			currBoard[herosquarez][herosquarex].y=0f;
    			OKMove=false;
    			Toast.makeText//ͬʱ��Toast.��ʾ���
    			(
    				father, 
    				"�����Ϲ���,�����²���!", 
    				Toast.LENGTH_SHORT
    			).show();
    		}
		}
    	return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer 
    { 		
		ChessFoundation foundation;//����
		TriangleS sanjiao1;//�����������
		TriangleX sanjiao2;//�����������
		RectWall heifang;//�ڷ����ΰ�
		RectWall baifang;//�׷����ΰ�
		RectWall wall;//ǽ��������
		
		public int foundationTexId;//��������ID
		public int qipantexId;
		public int walltexId;//ǽ��
		public int floortexId;//����
		public int rooftexId;//�ݶ�����
		public int heitexId;//�ڷ�����
		public int baitexId;//�׷�����
		public int triangletexIds;//��������������
		public int triangletexIdx;//��������������
		
		public int whitechesstexId;//��������
		public int blackchesstexId;

		public SceneRenderer(){}
      
        public void onDrawFrame(GL10 gl) 
        {                  	
        	//�����ɫ��������Ȼ���
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//���õ�ǰ����Ϊģʽ����
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity(); 
            //���������λ��
            GLU.gluLookAt(gl, cx, cy, cz, tx, ty, tz, 0, 1, 0);
            
            gl.glDisable(GL10.GL_LIGHTING);//���������
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�������
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//������ɫ����
			cb.drawself(gl);//������
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);//�ر���ɫ���黺��		
			//��������
	        gl.glEnable(GL10.GL_TEXTURE_2D);   
	        //����ʹ������ST���껺��
	        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			foundation.drawSelf(gl, foundationTexId,qipantexId);//������    
            drawWall( gl);//������ 
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);//�ر���ɫ���黺��
	        gl.glEnable(GL10.GL_LIGHTING);//�������
	        gl.glEnable(GL10.GL_LIGHT0);//��0�ŵ�        
	        gl.glEnable(GL10.GL_LIGHT1);//��һ�ŵ�
	        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//���ö��㷨��������     
	        for(int i=0;i<currBoard.length;i++)//����ɫ����
	        {
	        	for(int j=0;j<currBoard[0].length;j++)
	        	{
	        		if(currBoard[i][j]!=null&&currBoard[i][j].chessType<=5&&currBoard[i][j].chessType>=0)
	        		{
	        			currBoard[i][j].drawSelf(gl,blackchesstexId);
	        		}
	        	}
	        }
	        for(int i=0;i<currBoard.length;i++)//ѭ���������黭��ɫ����,����ͬ������ID
	        {
	        	for(int j=0;j<currBoard[0].length;j++)
	        	{
	        		if(currBoard[i][j]!=null&&currBoard[i][j].chessType<=11&&currBoard[i][j].chessType>=6)
	        		{
	        			currBoard[i][j].drawSelf(gl,whitechesstexId);
	        		}
	        	}
	        }        
          gl.glDisable(GL10.GL_LIGHTING);//�رչ���
          gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//�رշ���������
          gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//���ö��㷨��������
          gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//����ʹ������ST���껺��
          gl.glDisable(GL10.GL_TEXTURE_2D);  
          drawPlayerNum(gl);//��������    
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
            gl.glFrustumf(-ratio, ratio, -1f, 1f, 2.0f, 400);    
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config)
        {
            //�رտ����� 
        	gl.glDisable(GL10.GL_DITHER);
        	//�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //������Ļ����ɫ��ɫRGBA
            gl.glClearColor(0,0,0,0);     
            //������Ȳ���
            gl.glEnable(GL10.GL_DEPTH_TEST);
            //�򿪱������
            gl.glEnable(GL10.GL_CULL_FACE);
            //��ƽ����ɫ
            gl.glShadeModel(GL10.GL_SMOOTH);            
            
            foundation=new ChessFoundation();//��������
            wall=new RectWall((HOUSE_SIZE+0.5f)*UNIT_SIZE,(HOUSE_SIZE+0.5f)*UNIT_SIZE);
            walltexId=initTexture(gl,R.drawable.qiangbi);//ǽ������
            floortexId=initTexture(gl,R.drawable.diban);//�ذ�����
            rooftexId=initTexture(gl,R.drawable.wuding);//�ݶ�������
            
            foundationTexId=initTexture(gl,R.drawable.dizuo);//���ص�������
            qipantexId=initTexture(gl,R.drawable.qipan);
            triangletexIds=initTexture(gl,R.drawable.sjx);//����ĳ����µ�����������
            triangletexIdx=initTexture(gl,R.drawable.sjxs);//�·��ĳ����ϵ�������
            
            heitexId=initTexture(gl,R.drawable.heifang);//�ڷ�����
            baitexId=initTexture(gl,R.drawable.baifang);//�׷�����
            
            whitechesstexId=initTexture(gl,R.drawable.whitechess);
            blackchesstexId=initTexture(gl,R.drawable.blackchess);
            
            heifang=new RectWall(0.7f,0.35f);//��ڷ�ͼƬ
            heifang.x=BLACK_FLAG_X;
            heifang.y=BLACK_FLAG_Y;
            heifang.z=-4;
            
            
            baifang=new RectWall(0.7f,0.35f);//��׷�ͼƬ
            baifang.x=WHITE_FLAG_X;
            baifang.y=WHITE_FLAG_Y;
            baifang.z=-4;

            sanjiao1=new TriangleS(0.25f);//������ָʾ�ڰ׷�������
            sanjiao1.y=PLAYER_TYPE_Y;
            sanjiao1.z=-4;
            
            sanjiao2=new TriangleX(0.255f);//ָʾ˭�����������
            sanjiao2.y=CURR_MOVE_PLAYER_Y;
            sanjiao2.z=-4;
            
            initLight(gl);//��ʼ���ƹ�
            float[] positionParamsGreen={0,16*UNIT_SIZE,16.5f*UNIT_SIZE,1};//����1��ʾ�Ƕ�λ��
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
            
            initLight2(gl);
            float[] positionParamsGreen2={0,16*UNIT_SIZE,-16.5f*UNIT_SIZE,1};//����1��ʾ�Ƕ�λ��
            gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, positionParamsGreen2,0);
            
        }
        //�������Ǳ�־��
        public void drawPlayerNum(GL10 gl)
        {
        	if(father.ca.num==1)//����Ǻڷ�
        	{
        		sanjiao1.x=PLAYER_TYPE_X1;
        	}
        	else
        	{
        		sanjiao1.x=PLAYER_TYPE_X2;
        	}
        	
        	
        	if((father.ca.perFlag&&father.ca.num==1)||((!father.ca.perFlag)&&father.ca.num==2))
        	{
        		sanjiao2.x=CURR_MOVE_PLAYER_X1;
        	}
        	else
        	{
        		sanjiao2.x=CURR_MOVE_PLAYER_X2;
        	}        	
            //���õ�ǰ����Ϊģʽ����
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity(); 
            gl.glDisable(GL10.GL_LIGHTING);//�ص�
            
            //��������
	        gl.glEnable(GL10.GL_TEXTURE_2D);   
	        //����ʹ������ST���껺��
	        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	        
            sanjiao1.drawSelf(gl, triangletexIds);
            sanjiao2.drawSelf(gl, triangletexIdx);
            gl.glEnable(GL10.GL_BLEND);//�������
            gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_COLOR);
            heifang.drawSelf(gl, heitexId);
            baifang.drawSelf(gl, baitexId);
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//�ر�����
            gl.glDisable(GL10.GL_TEXTURE_2D);
            gl.glDisable(GL10.GL_BLEND);//�رջ��
        	
        }
         public void drawWall(GL10 gl)
        {
        	gl.glPushMatrix();
	        gl.glTranslatef(0,(HOUSE_SIZE/2-1)*UNIT_SIZE,-HOUSE_SIZE/2*UNIT_SIZE);
        	wall.drawSelf(gl, walltexId);
        	gl.glTranslatef(0, 0, HOUSE_SIZE*UNIT_SIZE);
        	gl.glRotatef(180, 0, 1, 0);
        	wall.drawSelf(gl,walltexId );
        	gl.glPopMatrix();
        	
        	gl.glPushMatrix();
        	gl.glTranslatef(-HOUSE_SIZE/2*UNIT_SIZE, (HOUSE_SIZE/2-1)*UNIT_SIZE, 0);
        	gl.glRotatef(90, 0, 1, 0);
        	wall.drawSelf(gl, walltexId);
        	gl.glTranslatef(0, 0, HOUSE_SIZE*UNIT_SIZE);
        	gl.glRotatef(180, 0, 1, 0);
        	wall.drawSelf(gl, walltexId);
        	gl.glPopMatrix();
        	
        	gl.glPushMatrix();
        	gl.glRotatef(-90, 1, 0, 0);
        	gl.glTranslatef(0,0,-UNIT_SIZE);
        	wall.drawSelf(gl,floortexId);
        	gl.glTranslatef(0, 0,HOUSE_SIZE*UNIT_SIZE);
        	gl.glRotatef(180, 0, 1, 0);
        	wall.drawSelf(gl,rooftexId );
        	gl.glPopMatrix();
        }
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
	
	private void initLight2(GL10 gl)
	{
		//��ɫ�ƹ�
        gl.glEnable(GL10.GL_LIGHT1);//��0�ŵ�  
        
        //����������
        float[] ambientParams={1f,1f,1f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, ambientParams,0);            

        //ɢ�������
        float[] diffuseParams={1f,1f,1f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseParams,0); 
        
        //���������
        float[] specularParams={1f,1f,1f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, specularParams,0);     
	}
	//��������ģ��
	public static void initChessForDraw(Resources r)
	{
		qizi=new LoadedObjectVertexNormalTexture[]
		    {
		        LoadUtil.loadFromFileVertexOnly("che.obj", r),
		        LoadUtil.loadFromFileVertexOnly("ma.obj", r),
		        LoadUtil.loadFromFileVertexOnly("xiang.obj", r),
	            LoadUtil.loadFromFileVertexOnly("hou.obj", r),
		        LoadUtil.loadFromFileVertexOnly("wang.obj", r),
		        LoadUtil.loadFromFileVertexOnly("bing.obj", r)
		     };
	}
	//��ʼ������
	public int initTexture(GL10 gl,int drawableId)//textureId
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
}
