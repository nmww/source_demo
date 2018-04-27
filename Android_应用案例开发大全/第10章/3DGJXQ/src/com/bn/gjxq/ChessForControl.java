package com.bn.gjxq;
import javax.microedition.khronos.opengles.GL10;
import static com.bn.gjxq.Constant.*;

public class ChessForControl
{
	boolean isMoved=false;//�Ƿ��ƶ���
	LoadedObjectVertexNormalTexture object;//���ص�3D����
	int chessType;  //0-11  0-5 ��ʾ����,6-11 ��ʾ����
	int col;//��ǰ�������ڵ���
	int row;//��ǰ�������ڵ���
	float y=0;//��ǰ���ӵĸ߶�ֵ
	//������,�������Ϊ����ģ������,���ӽ�ɫ,���ӵĵ�ǰλ��.
	public ChessForControl(LoadedObjectVertexNormalTexture object,int chessType,int row,int col)
	{
		this.object=object;//����ģ�͵�����
		this.chessType=chessType;	//���ӵĽ�ɫ��λ
		this.col=col;//����������
		this.row=row;//����������
	}
	public void drawSelf(GL10 gl,int texId)//��Ӣ�۵ĺ���
	{
		gl.glPushMatrix();//�����ֳ�	
		gl.glTranslatef((0.5f+col-4)*UNIT_SIZE,0,(0.5f+row-4)*UNIT_SIZE);//�������ƶ�����Ӧ��λ��
		gl.glTranslatef(0, 0.05f, 0);//����ģ�͵�ԭ��,��Ҫ�����ƶ�һ��
    	gl.glRotatef(((chessType>=0&&chessType<=5)?180:0), 0, 1, 0);//����Ǻڷ���Ҫ��ģ����ת180��
    	gl.glTranslatef(0, y, 0);//������Ϊѡ��ʱ,���仭��һ��.
		object.drawSelf(gl,texId);//��Ӣ��
		gl.glPopMatrix();//�ָ��ֳ�
	}
}
