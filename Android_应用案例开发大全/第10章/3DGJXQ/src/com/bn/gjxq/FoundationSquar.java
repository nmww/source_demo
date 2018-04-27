package com.bn.gjxq;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import static com.bn.gjxq.Constant.UNIT_SIZE;
public class FoundationSquar 
{
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTextureBuffer;//�����������ݻ��� 
    int vCount;//��¼������
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
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        
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
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTextureBuffer= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTextureBuffer.put(textures);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
    }
    
    public void drawSelf(GL10 gl,int texId)
    {
    	//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FLOAT,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );
        
      //��������
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //����ʹ������ST���껺��
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
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
    }
}
