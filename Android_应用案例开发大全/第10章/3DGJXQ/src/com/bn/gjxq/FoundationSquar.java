package com.bn.gjxq;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import static com.bn.gjxq.Constant.UNIT_SIZE;
public class FoundationSquar 
{
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   mTextureBuffer;//顶点纹理数据缓冲 
    int vCount;//记录定点数
    public FoundationSquar(float whith_1,float whith_2,float height)
    {
    	vCount=6;
    	float []vertices=new float[]
    	 {
    		-whith_1/2*UNIT_SIZE,0,0,
    		-whith_2/2*UNIT_SIZE,-height*UNIT_SIZE,(whith_2/2-whith_1/2)*UNIT_SIZE,
    		whith_2/2*UNIT_SIZE,-height*UNIT_SIZE,(whith_2/2-whith_1/2)*UNIT_SIZE,
    		
    		whith_2/2*UNIT_SIZE,-height*UNIT_SIZE,(whith_2/2-whith_1/2)*UNIT_SIZE,
    		whith_1/2*UNIT_SIZE,0,0,
    		-whith_1/2*UNIT_SIZE,0,0,
    	};
    	
    	ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        
        float textures[]=new float[]
        {
        		1.5f/11,0,
        		0,1f,
        		1f,1f,
        		
        		1f,1f,
        		9.5f/11,0,
        		1.5f/11
        };
        
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTextureBuffer= tbb.asFloatBuffer();//转换为Float型缓冲
        mTextureBuffer.put(textures);//向缓冲区中放入顶点着色数据
        mTextureBuffer.position(0);//设置缓冲区起始位置
    }
    
    public void drawSelf(GL10 gl,int texId)
    {
    	//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
        
      //开启纹理
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //允许使用纹理ST坐标缓冲
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //为画笔指定纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //绑定当前纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0,
        		vCount 
        );
    }
}
