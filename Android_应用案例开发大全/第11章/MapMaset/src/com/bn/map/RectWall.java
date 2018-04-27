package com.bn.map;

import static com.bn.map.Constant.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class RectWall
{
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTextureBuffer;//�����������ݻ���
    int vCount;//������
    float x;//�����ƶ����ĵط�
	float y;
	float z;
	public RectWall(float width,float height)
	{
		vCount=6;   	
    	float []vertices=new float[]
    	{
    			-width*UNIT_SIZE/2,height*UNIT_SIZE/2,0,
    			-width*UNIT_SIZE/2,-height*UNIT_SIZE/2,0,
    			width*UNIT_SIZE/2,-height*UNIT_SIZE/2,0,
    		
    			width*UNIT_SIZE/2,-height*UNIT_SIZE/2,0,
    			width*UNIT_SIZE/2,height*UNIT_SIZE/2,0,
    			-width*UNIT_SIZE/2,height*UNIT_SIZE/2,0
    	};    	
    	ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��       
        float textures[]=new float[]
        {
        		0,0,
        		0,1f,
        		1f,1f,
        		
        		1f,1f,
        		1,0,
        		0,0
        };
        
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTextureBuffer= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTextureBuffer.put(textures);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
	}
	
	 public void drawSelf(GL10 gl,int texId)
	    {
	    	
	        gl.glPushMatrix();
	        gl.glTranslatef(x, y, z);
	      //Ϊ����ָ��������������
	        gl.glVertexPointer
	        (
	        		3,				//ÿ���������������Ϊ3  xyz 
	        		GL10.GL_FLOAT,	//��������ֵ������Ϊ GL_FIXED
	        		0, 				//����������������֮��ļ��
	        		mVertexBuffer	//������������
	        );
	        

	        //Ϊ����ָ������ST���껺��
	        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
	        //�󶨵�ǰ����
	        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
			
	        //����ͼ��
	        gl.glDrawArrays
	        (
	        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
	        		0,
	        		vCount 
	        );
	        gl.glPopMatrix();
	    }
}
