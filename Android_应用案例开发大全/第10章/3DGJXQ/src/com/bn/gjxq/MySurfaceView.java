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
	private final float TOUCH_SCALE_FACTOR = 180.0f/320*3;//角度缩放比例
    private SceneRenderer mRenderer;//场景渲染器
	
	private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置Y坐标
    
    static float cx;//摄像机x坐标
    static float cy;//摄像机y坐标
    static float cz;//摄像机z坐标
    
    static float tx;//观察目标点x坐标  
    static float ty;//观察目标点y坐标
    static float tz;//观察目标点z坐标
 
    static float yAngle=0;//方位角
    static float xAngle=30;//仰角
    static boolean OKMove=false;//是否需要移动棋子标志
    
    static int herosquareZ=7;//主正方形格子的初始行数
    static int herosquareX=0;//主正方形格子的初始列数
    
    static int herosquarez;//当确定了要走某个英雄时，记录这个英雄的原来所在坐标。
    static int herosquarex;
    
    static LoadedObjectVertexNormalTexture[] qizi;//模型数组
    ChessboardForDraw cb;//棋盘
    
    ChessForControl[][] currBoard;//棋子数组
    static int[][] road;
    GJXQActivity father;//activity引用
  
	public MySurfaceView(Context context)
	{ 
        super(context);
        father=(GJXQActivity)context;//Activity对象
        mRenderer = new SceneRenderer();	
        setRenderer(mRenderer);				 	
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置为连续渲染模式
        tx=0;//观察目标点x坐标  
	    ty=0;//平视观察目标点y坐标
	    tz=0;//观察目标点z坐标
	    road=new int[8][8];

	    //初始化走棋路径
	    if(father.ca.num==1)
	    {
	    	herosquareZ=1;//如果是黑方,把红格子定位在1,0格子里面
	    	herosquareX=0;
	        //如果当前为黑方,则光标处于黑方位置
	    }
	    else
	    {
	    	herosquareZ=6;//如果是白方,就把红格子放入6,0格子里面
	    	herosquareX=0;
	    }
	    road[herosquareZ][herosquareX]=1;//把初始位置的红方格定位于此  
	    yAngle=((father.ca.num%2)==1)?180:0;//如果当前为黑方,则摄像机方向角旋转180.
		cx=(float)(tx+Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*DISTANCE);//观察者x坐标 
        cz=(float)(tz+Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*DISTANCE);//观察者z坐标 
        cy=(float)(ty+Math.sin(Math.toRadians(xAngle))*DISTANCE);  
       //初始化棋盘
        cb=new ChessboardForDraw();
        initBoard();//初始化棋子
    }
	
	public void initBoard()//初始化棋子数组的函数
	{
		currBoard=new ChessForControl[8][8];
		
		currBoard[0][0]=new ChessForControl(qizi[0] ,0,0,0);//黑车
		currBoard[0][1]=new ChessForControl(qizi[1] ,1,0,1);//黑马
		currBoard[0][2]=new ChessForControl(qizi[2] ,2,0,2);//黑象
		currBoard[0][3]=new ChessForControl(qizi[3] ,3,0,3);//黑后
		currBoard[0][4]=new ChessForControl(qizi[4] ,4,0,4);//黑王
		currBoard[0][5]=new ChessForControl(qizi[2] ,2,0,5);//黑象
		currBoard[0][6]=new ChessForControl(qizi[1] ,1,0,6);//黑马
		currBoard[0][7]=new ChessForControl(qizi[0] ,0,0,7);//黑车
		
		currBoard[1][0]=new ChessForControl(qizi[5] ,5,1,0);//黑兵
		currBoard[1][1]=new ChessForControl(qizi[5] ,5,1,1);//黑兵
		currBoard[1][2]=new ChessForControl(qizi[5] ,5,1,2);//黑兵
		currBoard[1][3]=new ChessForControl(qizi[5] ,5,1,3);//黑兵
		currBoard[1][4]=new ChessForControl(qizi[5] ,5,1,4);//黑兵
		currBoard[1][5]=new ChessForControl(qizi[5] ,5,1,5);//黑兵
		currBoard[1][6]=new ChessForControl(qizi[5] ,5,1,6);//黑兵
		currBoard[1][7]=new ChessForControl(qizi[5] ,5,1,7);//黑兵
		
		currBoard[6][0]=new ChessForControl(qizi[5] ,11,6,0);//白兵
		currBoard[6][1]=new ChessForControl(qizi[5] ,11,6,1);//白兵
		currBoard[6][2]=new ChessForControl(qizi[5] ,11,6,2);//白兵
		currBoard[6][3]=new ChessForControl(qizi[5] ,11,6,3);//白兵
		currBoard[6][4]=new ChessForControl(qizi[5] ,11,6,4);//白兵
		currBoard[6][5]=new ChessForControl(qizi[5] ,11,6,5);//白兵
		currBoard[6][6]=new ChessForControl(qizi[5] ,11,6,6);//白兵
		currBoard[6][7]=new ChessForControl(qizi[5] ,11,6,7);//白兵
		
		currBoard[7][0]=new ChessForControl(qizi[0] ,6,7,0);//白车
		currBoard[7][1]=new ChessForControl(qizi[1] ,7,7,1);//白马
		currBoard[7][2]=new ChessForControl(qizi[2] ,8,7,2);//白象
		currBoard[7][3]=new ChessForControl(qizi[3] ,9,7,3);//白后
		currBoard[7][4]=new ChessForControl(qizi[4] ,10,7,4);//白王
		currBoard[7][5]=new ChessForControl(qizi[2] ,8,7,5);//白象
		currBoard[7][6]=new ChessForControl(qizi[1] ,7,7,6);//白马
		currBoard[7][7]=new ChessForControl(qizi[0] ,6,7,7);//白车	
	}

	@Override 
    public boolean onTouchEvent(MotionEvent e) 
	{
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction())
        {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//计算触控笔Y位移
            float dx = x - mPreviousX;//计算触控笔X位移
            yAngle += dx * TOUCH_SCALE_FACTOR;//仰角改变
            xAngle += dy * TOUCH_SCALE_FACTOR;//方位角改变    
            if(xAngle+dy * TOUCH_SCALE_FACTOR<10)//如果当前摄像机仰角小于15,将其仰角强制为15
            {
            	xAngle=10;
            }
            if(xAngle+dy * TOUCH_SCALE_FACTOR>85)//当摄像机仰角大于85,将其强制为85;
            {
            	xAngle=85;
            }
            requestRender();//重绘画面
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置    
        cx=(float)(tx+Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*DISTANCE);//摄像机x坐标 
        cz=(float)(tz+Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*DISTANCE);//摄像机z坐标 
        cy=(float)(ty+Math.sin(Math.toRadians(xAngle))*DISTANCE);//摄像机y坐标 
        return true;
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e)
    { 
		if(!father.ca.perFlag)//当前不是该玩家下棋,那么直接返回,玩家无任何操控权利
		{
			return false;
		}
		if(keyCode==19||keyCode==20||keyCode==21||keyCode==22)//如果是按下前后左右建进行移动的情况.
		{
			road[herosquareZ][herosquareX]=0;//将此处的格子去掉
			if(keyCode==19)//分别代表前移，后移，左移，右移
    		{
				herosquareZ++;
    		}
    		else if(keyCode==20)//向后移动
    		{
    			herosquareZ--;
    		}
    		else if(keyCode==21)//向左移动
    		{
    			herosquareX++;
    		}
    		else if(keyCode==22)//向右移动
    		{
    			herosquareX--;
    		}
    	
    		if(herosquareX<0)//如果移动在左边界了,那么不可移动,
    		{
    			herosquareX=0;
			}
			if(herosquareX>7)//移动到了右边界了
			{
				herosquareX=7;
			}
			if(herosquareZ<0)//移动到了上边界了
			{
				herosquareZ=0;
			}
			if(herosquareZ>7)//移动到了下边界了
			{
				herosquareZ=7;
			}
			
			road[herosquareZ][herosquareX]=1;//把格子移动到当前位置
			
		}
		else if(keyCode==62)//如果是空格键,这里代表玩家操控棋子的建.
		{
			if(!OKMove&&currBoard[herosquareZ][herosquareX]!=null)//第一次按下空格
			{
				if(currBoard[herosquareZ][herosquareX].chessType>=(father.ca.num%2)*6
				   &&currBoard[herosquareZ][herosquareX].chessType<(father.ca.num%2+1)*6)
					//第一次按下空格,此格子不为空,并且该棋子是对方的,那么不可操控
						{
							Toast.makeText
							(
									father, 
									"不能动别人的棋子,请重新操作!", 
									Toast.LENGTH_SHORT
							).show();
						
						}
				else//如果是自己的棋子,而且是第一次标志,那么记录当前位置
					{OKMove=true;//至此标志位为true
					herosquarez=herosquareZ;//并且记录标记的英雄
					herosquarex=herosquareX;
					currBoard[herosquarez][herosquarex].y=0.4f;
					}
			}
			else if(OKMove&&currBoard[herosquarez][herosquarex]!=null
					&&GuiZe.canMove(currBoard[herosquarez][herosquarex], currBoard, herosquareZ, herosquareX)
					)//加可走接口，库
    			//第二次按下空格，而且可走.
    		{				
      			OKMove=false;//同时恢复标记，进行下一轮      			
      			int srcRow=herosquarez;
      			int srcCol=herosquarex;
      			int dstRow=herosquareZ;
      			int dstCol=herosquareX;     
      			String msg="<#MOVE#>"+srcRow+","+srcCol+","+dstRow+","+dstCol;//向服务器发送下棋操作,并且携带下棋信息
      			father.ca.sendMessage(msg);
      			father.ca.perFlag=false;
    		}
    		else if(OKMove&&currBoard[herosquarez][herosquarex]!=null&&!GuiZe.canMove(currBoard[herosquarez][herosquarex], currBoard, herosquareZ, herosquareX))//第二次按下空格，该地方不可走，表示玩家不下这颗棋
    		{//第二次按下棋子,而且不可下,那么恢复标记,将第一次抬起的棋子恢复原状
    			currBoard[herosquarez][herosquarex].y=0f;
    			OKMove=false;
    			Toast.makeText//同时发Toast.提示玩家
    			(
    				father, 
    				"不符合规则,请重新操作!", 
    				Toast.LENGTH_SHORT
    			).show();
    		}
		}
    	return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer 
    { 		
		ChessFoundation foundation;//底座
		TriangleS sanjiao1;//上面的三角形
		TriangleX sanjiao2;//下面的三角形
		RectWall heifang;//黑方条形板
		RectWall baifang;//白方条形板
		RectWall wall;//墙的正方形
		
		public int foundationTexId;//底座纹理ID
		public int qipantexId;
		public int walltexId;//墙面
		public int floortexId;//地面
		public int rooftexId;//屋顶纹理
		public int heitexId;//黑方纹理
		public int baitexId;//白方纹理
		public int triangletexIds;//三角形上面纹理
		public int triangletexIdx;//三角形下面纹理
		
		public int whitechesstexId;//棋子纹理
		public int blackchesstexId;

		public SceneRenderer(){}
      
        public void onDrawFrame(GL10 gl) 
        {                  	
        	//清除颜色缓存于深度缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity(); 
            //设置摄像机位置
            GLU.gluLookAt(gl, cx, cy, cz, tx, ty, tz, 0, 1, 0);
            
            gl.glDisable(GL10.GL_LIGHTING);//不允许光照
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点数组
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//启用颜色数组
			cb.drawself(gl);//画棋盘
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);//关闭颜色数组缓冲		
			//开启纹理
	        gl.glEnable(GL10.GL_TEXTURE_2D);   
	        //允许使用纹理ST坐标缓冲
	        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			foundation.drawSelf(gl, foundationTexId,qipantexId);//画底座    
            drawWall( gl);//画房间 
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);//关闭颜色数组缓冲
	        gl.glEnable(GL10.GL_LIGHTING);//允许光照
	        gl.glEnable(GL10.GL_LIGHT0);//开0号灯        
	        gl.glEnable(GL10.GL_LIGHT1);//开一号灯
	        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//启用顶点法向量数组     
	        for(int i=0;i<currBoard.length;i++)//画黑色棋子
	        {
	        	for(int j=0;j<currBoard[0].length;j++)
	        	{
	        		if(currBoard[i][j]!=null&&currBoard[i][j].chessType<=5&&currBoard[i][j].chessType>=0)
	        		{
	        			currBoard[i][j].drawSelf(gl,blackchesstexId);
	        		}
	        	}
	        }
	        for(int i=0;i<currBoard.length;i++)//循环棋子数组画白色棋子,传不同的纹理ID
	        {
	        	for(int j=0;j<currBoard[0].length;j++)
	        	{
	        		if(currBoard[i][j]!=null&&currBoard[i][j].chessType<=11&&currBoard[i][j].chessType>=6)
	        		{
	        			currBoard[i][j].drawSelf(gl,whitechesstexId);
	        		}
	        	}
	        }        
          gl.glDisable(GL10.GL_LIGHTING);//关闭光照
          gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//关闭发向量数组
          gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//禁用顶点法向量数组
          gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//禁用使用纹理ST坐标缓冲
          gl.glDisable(GL10.GL_TEXTURE_2D);  
          drawPlayerNum(gl);//画条幅版    
        }

        public void onSurfaceChanged(GL10 gl, int width, int height)
        {
            //设置视窗大小及位置 
        	gl.glViewport(0, 0, width, height);
        	//设置当前矩阵为投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();
            //计算透视投影的比例
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            gl.glFrustumf(-ratio, ratio, -1f, 1f, 2.0f, 400);    
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config)
        {
            //关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);     
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);
            //打开背面剪裁
            gl.glEnable(GL10.GL_CULL_FACE);
            //打开平滑着色
            gl.glShadeModel(GL10.GL_SMOOTH);            
            
            foundation=new ChessFoundation();//建立底盘
            wall=new RectWall((HOUSE_SIZE+0.5f)*UNIT_SIZE,(HOUSE_SIZE+0.5f)*UNIT_SIZE);
            walltexId=initTexture(gl,R.drawable.qiangbi);//墙壁纹理
            floortexId=initTexture(gl,R.drawable.diban);//地板纹理
            rooftexId=initTexture(gl,R.drawable.wuding);//屋顶的纹理
            
            foundationTexId=initTexture(gl,R.drawable.dizuo);//加载底盘纹理
            qipantexId=initTexture(gl,R.drawable.qipan);
            triangletexIds=initTexture(gl,R.drawable.sjx);//上面的朝向下的三角形纹理
            triangletexIdx=initTexture(gl,R.drawable.sjxs);//下方的朝向上的三角形
            
            heitexId=initTexture(gl,R.drawable.heifang);//黑方纹理
            baitexId=initTexture(gl,R.drawable.baifang);//白方纹理
            
            whitechesstexId=initTexture(gl,R.drawable.whitechess);
            blackchesstexId=initTexture(gl,R.drawable.blackchess);
            
            heifang=new RectWall(0.7f,0.35f);//造黑方图片
            heifang.x=BLACK_FLAG_X;
            heifang.y=BLACK_FLAG_Y;
            heifang.z=-4;
            
            
            baifang=new RectWall(0.7f,0.35f);//造白方图片
            baifang.x=WHITE_FLAG_X;
            baifang.y=WHITE_FLAG_Y;
            baifang.z=-4;

            sanjiao1=new TriangleS(0.25f);//造两个指示黑白方三角形
            sanjiao1.y=PLAYER_TYPE_Y;
            sanjiao1.z=-4;
            
            sanjiao2=new TriangleX(0.255f);//指示谁下棋的三角形
            sanjiao2.y=CURR_MOVE_PLAYER_Y;
            sanjiao2.z=-4;
            
            initLight(gl);//初始化灯光
            float[] positionParamsGreen={0,16*UNIT_SIZE,16.5f*UNIT_SIZE,1};//最后的1表示是定位光
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
            
            initLight2(gl);
            float[] positionParamsGreen2={0,16*UNIT_SIZE,-16.5f*UNIT_SIZE,1};//最后的1表示是定位光
            gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, positionParamsGreen2,0);
            
        }
        //绘制三角标志板
        public void drawPlayerNum(GL10 gl)
        {
        	if(father.ca.num==1)//如果是黑方
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
            //设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity(); 
            gl.glDisable(GL10.GL_LIGHTING);//关灯
            
            //开启纹理
	        gl.glEnable(GL10.GL_TEXTURE_2D);   
	        //允许使用纹理ST坐标缓冲
	        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	        
            sanjiao1.drawSelf(gl, triangletexIds);
            sanjiao2.drawSelf(gl, triangletexIdx);
            gl.glEnable(GL10.GL_BLEND);//开启混合
            gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_COLOR);
            heifang.drawSelf(gl, heitexId);
            baifang.drawSelf(gl, baitexId);
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理
            gl.glDisable(GL10.GL_TEXTURE_2D);
            gl.glDisable(GL10.GL_BLEND);//关闭混合
        	
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
		//白色灯光
        gl.glEnable(GL10.GL_LIGHT0);//打开0号灯  
        
        //环境光设置
        float[] ambientParams={1f,1f,1f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            

        //散射光设置
        float[] diffuseParams={1f,1f,1f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
        
        //反射光设置
        float[] specularParams={1f,1f,1f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);     
	}
	
	private void initLight2(GL10 gl)
	{
		//白色灯光
        gl.glEnable(GL10.GL_LIGHT1);//打开0号灯  
        
        //环境光设置
        float[] ambientParams={1f,1f,1f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, ambientParams,0);            

        //散射光设置
        float[] diffuseParams={1f,1f,1f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseParams,0); 
        
        //反射光设置
        float[] specularParams={1f,1f,1f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, specularParams,0);     
	}
	//加载棋子模型
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
	//初始化纹理
	public int initTexture(GL10 gl,int drawableId)//textureId
	{
		//生成纹理ID
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
