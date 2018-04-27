package com.bn.gjxq;
import static com.bn.gjxq.Constant.*;

import javax.microedition.khronos.opengles.GL10;
//����������
public class ChessboardForDraw
{
	//��ɫ��������
    ColorRect[] cr;
	public ChessboardForDraw()//������
	{
		ColorRect cr1=new ColorRect(COLORARR[0]);//��ɫ����
		ColorRect cr2=new ColorRect(COLORARR[1]);//��ɫ����
		ColorRect cr3=new ColorRect(COLORARR[2]);//��ɫ����
		//������������
		cr=new ColorRect[]
		        {
				 cr1,
				 cr2,
				 cr3
		        };
	}
	public void drawself(GL10 gl)
	{
		//colorRect�ƶ�
		for(int j=-4;j<4;j++)//ѭ����������
		{
			for(int i=-4;i<4;i++)
			{ 
				if(MySurfaceView.road[j+4][i+4]!=1)//�����ǰû�й��
				{
			      gl.glPushMatrix();
	              gl.glTranslatef(i*UNIT_SIZE, 0, j*UNIT_SIZE);//����ɫ�����ƶ���ָ��λ��
	              cr[Math.abs((i+j)%2)].drawSelf(gl);//��������
	              gl.glPopMatrix();
				}
				else//�����ǰ�й��,������ɫ���
				{
					 gl.glPushMatrix();
		             gl.glTranslatef(i*UNIT_SIZE, 0, j*UNIT_SIZE);//����ɫ�����ƶ���ָ��λ��
		             cr[2].drawSelf(gl);//����·��
		             gl.glPopMatrix();
				}
			}
		}
	}
}