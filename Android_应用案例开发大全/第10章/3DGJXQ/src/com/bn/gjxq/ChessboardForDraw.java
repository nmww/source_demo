package com.bn.gjxq;
import static com.bn.gjxq.Constant.*;

import javax.microedition.khronos.opengles.GL10;
//绘制棋盘类
public class ChessboardForDraw
{
	//颜色矩形数组
    ColorRect[] cr;
	public ChessboardForDraw()//构造器
	{
		ColorRect cr1=new ColorRect(COLORARR[0]);//白色矩形
		ColorRect cr2=new ColorRect(COLORARR[1]);//黑色矩形
		ColorRect cr3=new ColorRect(COLORARR[2]);//红色矩形
		//创建三个对象
		cr=new ColorRect[]
		        {
				 cr1,
				 cr2,
				 cr3
		        };
	}
	public void drawself(GL10 gl)
	{
		//colorRect移动
		for(int j=-4;j<4;j++)//循环绘制棋盘
		{
			for(int i=-4;i<4;i++)
			{ 
				if(MySurfaceView.road[j+4][i+4]!=1)//如果当前没有光标
				{
			      gl.glPushMatrix();
	              gl.glTranslatef(i*UNIT_SIZE, 0, j*UNIT_SIZE);//将颜色矩形移动到指定位置
	              cr[Math.abs((i+j)%2)].drawSelf(gl);//绘制棋盘
	              gl.glPopMatrix();
				}
				else//如果当前有光标,棋盘颜色变红
				{
					 gl.glPushMatrix();
		             gl.glTranslatef(i*UNIT_SIZE, 0, j*UNIT_SIZE);//将颜色矩形移动到指定位置
		             cr[2].drawSelf(gl);//绘制路径
		             gl.glPopMatrix();
				}
			}
		}
	}
}