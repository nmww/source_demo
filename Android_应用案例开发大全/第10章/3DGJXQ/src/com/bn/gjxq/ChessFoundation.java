package com.bn.gjxq;
import static com.bn.gjxq.Constant.UNIT_SIZE;

import javax.microedition.khronos.opengles.GL10;
//���̵ĵ���
public class ChessFoundation 
{
	FoundationSquar squar;//FoundationSquar������
	RectWall cc;//RectWall������
	float whith_1=9.5f;//���1
	float whith_2=10.5f;//���2
	float height=1;//�߶�
    public ChessFoundation()
    {
    	squar=new FoundationSquar(whith_1,whith_2,height);//��������ÿһ��ľ���
    	cc=new RectWall(whith_1+0.01f,whith_1+0.01f);
    	cc.z=0;
    }
    public void  drawSelf(GL10 gl,int texId,int texIdd)
    {
    	gl.glPushMatrix();//��������
    	gl.glTranslatef(0, -0.05f,0 );//�������ƶ�
    	
    	gl.glPushMatrix();
    	gl.glRotatef(-90, 1, 0, 0);//��X�ᷴ����ת90
    	
    	cc.drawSelf(gl, texIdd);//���л���
    	gl.glPopMatrix();
    	
    	gl.glPushMatrix();//������ǰ����
		gl.glTranslatef(0, 0, whith_1/2*UNIT_SIZE);//�����ƶ�������λ��
		squar.drawSelf(gl, texId);//����ǰ����һ��
		gl.glTranslatef(0, 0, -whith_1*UNIT_SIZE);
		gl.glRotatef(180f, 0, 1, 0);
		squar.drawSelf(gl, texId);//����󷽵�һ��
		gl.glPopMatrix();//����ǰ�������ָ�����
		
		gl.glPushMatrix();	//������ǰ����		
		gl.glTranslatef(whith_1/2*UNIT_SIZE, 0, 0);//�ƶ�������λ��
		gl.glRotatef(90f, 0, 1, 0);
		squar.drawSelf(gl, texId);
		gl.glTranslatef(0,0, -whith_1*UNIT_SIZE);
		gl.glRotatef(180f, 0, 1, 0);
		squar.drawSelf(gl, texId);
		gl.glPopMatrix();//�ָ�����
		gl.glPopMatrix();
    }
}
