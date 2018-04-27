package com.bn.gjxq;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import static com.bn.gjxq.Constant.*;
//���Ϊ���������е�ÿһ����ɫ����
public class ColorRect 
{
	private FloatBuffer mVertexBuffer;//�����������ݻ���
	private FloatBuffer mColorBuffer;//����������ɫ���ݻ���
	int vCount;	//�������������	
	public ColorRect(float[] colorArr)
	{
		 vCount=6;//���ö�������Ϊ6
		 //��������
		 float vertices[]=new float[]//���ڴ洢���������
		 {
				 0,0,0,
				 0,0,UNIT_SIZE,
				 UNIT_SIZE,0,0,         //��һ����ʱ����Ƶ�������
				 
				 UNIT_SIZE,0,0,
				 0,0,UNIT_SIZE,
				 UNIT_SIZE,0,UNIT_SIZE   //�ڶ�����ʱ����Ƶ�������
		 };
		 	ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//�ֽڻ���
	        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
	        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
	        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
	        mVertexBuffer.position(0);//���û�������ʼλ��
	        
	        //������ɫ ����
	        float colors[]=new float[vCount*4];
	        int c=0;//����һ����־
	        for(int i=0;i<vCount;i++)
	        {
	        	colors[c++]=colorArr[0];//��ɫ���� R
	        	colors[c++]=colorArr[1];//��ɫ���� G
	        	colors[c++]=colorArr[2];//��ɫ����B
	        	colors[c++]=colorArr[3];//��ɫ����A
	        }
	        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
	        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
	        mColorBuffer= cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
	        mColorBuffer.put(colors);//�򻺳����з��붥����ɫ����
	        mColorBuffer.position(0);//���û�������ʼλ��	        
	}
	public void drawSelf(GL10 gl)
	{
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//���ض�������ָ��
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);//������ɫ����ָ��
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//�Զ��������η�ʽ���л���
	}
}
