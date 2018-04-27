package com.bn.gjxq;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
//加载后的物体——携带顶点、法向量、纹理信息
public class LoadedObjectVertexNormalTexture 
{
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	private FloatBuffer   mNormalBuffer;//顶点法向量数据缓冲
	private FloatBuffer   mTexBuffer;//顶点纹理数据缓冲
    int vCount=0;  
    public LoadedObjectVertexNormalTexture(float[] vertices,float[] normals,float texCoors[]) 
    {	
    	//顶点坐标数据的初始化================begin============================
        vCount=vertices.length/3;    
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为Float型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化================end============================
        
        //法向量信息初始化
        ByteBuffer vbn = ByteBuffer.allocateDirect(normals.length*4);
        vbn.order(ByteOrder.nativeOrder());//设置字节顺序
        mNormalBuffer = vbn.asFloatBuffer();//转换为Float型缓冲
        mNormalBuffer.put(normals);//向缓冲区中放入顶点坐标数据
        mNormalBuffer.position(0);//设置缓冲区起始位置 
        
        //纹理坐标缓冲初始化
        ByteBuffer vbt = ByteBuffer.allocateDirect(texCoors.length*4);
        vbt.order(ByteOrder.nativeOrder());//设置字节顺序
        mTexBuffer = vbt.asFloatBuffer();//转换为Float型缓冲
        mTexBuffer.put(texCoors);//向缓冲区中放入顶点坐标数据
        mTexBuffer.position(0);//设置缓冲区起始位置 
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
        
        //为画笔指定顶点法向量数据
        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
        
        //为画笔指定纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexBuffer);
        //绑定当前纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0, 			 			//开始点编号
        		vCount					//顶点的数量
        );        
        
       
    }
}
