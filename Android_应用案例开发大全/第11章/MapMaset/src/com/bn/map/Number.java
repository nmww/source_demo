package com.bn.map;
import javax.microedition.khronos.opengles.GL10;
import static com.bn.map.Constant.*;
//��ʾ��þ�����������
public class Number 
{
	GameSurfaceView mv;
	TextureRect[] numbers=new TextureRect[10];
	String NumberStr;
	public float y;
	public Number(GameSurfaceView mv)
	{
		this.mv=mv;
		
		//����0-9ʮ�����ֵ��������
		for(int i=0;i<10;i++)
		{
			numbers[i]=new TextureRect
            (
            	 ICON_WIDTH*0.7f/2,
            	 ICON_HEIGHT*0.7f/2,
           		 new float[]
		             {
		           	  0.1f*i,0, 0.1f*i,1, 0.1f*(i+1),0,
		           	  0.1f*i,1, 0.1f*(i+1),1,  0.1f*(i+1),0
		             }
             ); 
		}
	}
	
	public void drawSelf(GL10 gl,int flag,int texId)
	{	
		for(int i=0;i<NumberStr.length();i++)
		{//���÷��е�ÿ�������ַ�����
			char c=NumberStr.charAt(flag==1?i:NumberStr.length()-i-1);
			gl.glPushMatrix();
			gl.glTranslatef(flag==1?i*ICON_WIDTH*0.7f:-i*ICON_WIDTH*0.7f,y, 0);
			numbers[c-'0'].drawSelf(gl,texId);
			gl.glPopMatrix();
		}
	}
}
