package com.bn.gjxq;
import javax.microedition.khronos.opengles.GL10;
import static com.bn.gjxq.Constant.*;

public class ChessForControl
{
	boolean isMoved=false;//是否移动过
	LoadedObjectVertexNormalTexture object;//加载的3D物体
	int chessType;  //0-11  0-5 表示黑棋,6-11 表示白棋
	int col;//当前棋子所在的列
	int row;//当前棋子所在的行
	float y=0;//当前棋子的高度值
	//构造器,传入参数为棋子模型引用,棋子角色,棋子的当前位置.
	public ChessForControl(LoadedObjectVertexNormalTexture object,int chessType,int row,int col)
	{
		this.object=object;//棋子模型的引用
		this.chessType=chessType;	//棋子的角色定位
		this.col=col;//棋子所在行
		this.row=row;//棋子所在列
	}
	public void drawSelf(GL10 gl,int texId)//画英雄的函数
	{
		gl.glPushMatrix();//保护现场	
		gl.glTranslatef((0.5f+col-4)*UNIT_SIZE,0,(0.5f+row-4)*UNIT_SIZE);//将棋子移动到对应的位置
		gl.glTranslatef(0, 0.05f, 0);//由于模型的原因,需要向上移动一点
    	gl.glRotatef(((chessType>=0&&chessType<=5)?180:0), 0, 1, 0);//如果是黑方需要把模型旋转180度
    	gl.glTranslatef(0, y, 0);//当棋子为选定时,把其画高一点.
		object.drawSelf(gl,texId);//画英雄
		gl.glPopMatrix();//恢复现场
	}
}
