package com.bn.gjxq;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import static com.bn.gjxq.Constant.*;
//这个为绘制棋盘中的每一个颜色矩形
public class ColorRect 
{
	private FloatBuffer mVertexBuffer;//声明顶点数据缓冲
	private FloatBuffer mColorBuffer;//声明顶点颜色数据缓冲
	int vCount;	//声明顶点的数量	
	public ColorRect(float[] colorArr)
	{
		 vCount=6;//设置顶点总数为6
		 //顶点数据
		 float vertices[]=new float[]//用于存储顶点的数组
		 {
				 0,0,0,
				 0,0,UNIT_SIZE,
				 UNIT_SIZE,0,0,         //第一个逆时针卷绕的三角形
				 
				 UNIT_SIZE,0,0,
				 0,0,UNIT_SIZE,
				 UNIT_SIZE,0,UNIT_SIZE   //第二个逆时针卷绕的三角形
		 };
		 	ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//字节缓冲
	        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        mVertexBuffer = vbb.asFloatBuffer();//转换为float型缓冲
	        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
	        mVertexBuffer.position(0);//设置缓冲区起始位置
	        
	        //顶点颜色 数组
	        float colors[]=new float[vCount*4];
	        int c=0;//声明一个标志
	        for(int i=0;i<vCount;i++)
	        {
	        	colors[c++]=colorArr[0];//颜色分量 R
	        	colors[c++]=colorArr[1];//颜色分量 G
	        	colors[c++]=colorArr[2];//颜色分量B
	        	colors[c++]=colorArr[3];//颜色分量A
	        }
	        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
	        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        mColorBuffer= cbb.asFloatBuffer();//转换为Float型缓冲
	        mColorBuffer.put(colors);//向缓冲区中放入顶点着色数据
	        mColorBuffer.position(0);//设置缓冲区起始位置	        
	}
	public void drawSelf(GL10 gl)
	{
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//加载顶点数组指针
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);//加载颜色数组指针
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//以顶点三角形方式进行绘制
	}
}
