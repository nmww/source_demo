package com.bn.gjxq;
import static com.bn.gjxq.Constant.UNIT_SIZE;

import javax.microedition.khronos.opengles.GL10;
//棋盘的底盘
public class ChessFoundation 
{
	FoundationSquar squar;//FoundationSquar的引用
	RectWall cc;//RectWall的引用
	float whith_1=9.5f;//宽度1
	float whith_2=10.5f;//宽度2
	float height=1;//高度
    public ChessFoundation()
    {
    	squar=new FoundationSquar(whith_1,whith_2,height);//创建底盘每一面的矩形
    	cc=new RectWall(whith_1+0.01f,whith_1+0.01f);
    	cc.z=0;
    }
    public void  drawSelf(GL10 gl,int texId,int texIdd)
    {
    	gl.glPushMatrix();//保护矩阵
    	gl.glTranslatef(0, -0.05f,0 );//向外轴移动
    	
    	gl.glPushMatrix();
    	gl.glRotatef(-90, 1, 0, 0);//绕X轴反向旋转90
    	
    	cc.drawSelf(gl, texIdd);//进行绘制
    	gl.glPopMatrix();
    	
    	gl.glPushMatrix();//保护当前矩阵
		gl.glTranslatef(0, 0, whith_1/2*UNIT_SIZE);//将其移动到合适位置
		squar.drawSelf(gl, texId);//画正前方的一个
		gl.glTranslatef(0, 0, -whith_1*UNIT_SIZE);
		gl.glRotatef(180f, 0, 1, 0);
		squar.drawSelf(gl, texId);//画最后方的一个
		gl.glPopMatrix();//画完前后两面后恢复矩阵
		
		gl.glPushMatrix();	//保护当前矩阵		
		gl.glTranslatef(whith_1/2*UNIT_SIZE, 0, 0);//移动到合适位置
		gl.glRotatef(90f, 0, 1, 0);
		squar.drawSelf(gl, texId);
		gl.glTranslatef(0,0, -whith_1*UNIT_SIZE);
		gl.glRotatef(180f, 0, 1, 0);
		squar.drawSelf(gl, texId);
		gl.glPopMatrix();//恢复矩阵
		gl.glPopMatrix();
    }
}
