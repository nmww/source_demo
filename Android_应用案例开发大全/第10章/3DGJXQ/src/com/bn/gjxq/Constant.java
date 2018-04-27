package com.bn.gjxq;
public class Constant
{
	public static final float MAX_S_QHC=1.0f;//S纹理坐标
	public static final float MAX_T_QHC=0.746f;//T纹理坐标	
	//START按钮
	public static int BUTTON_START_XOFFSET=75;
	public static int BUTTON_START_YOFFSET=116;
	//HELP按钮
	public static int BUTTON_HELP_XOFFSET=277;
	public static int BUTTON_HELP_YOFFSET=116;
	//ABOUT按钮
	public static int BUTTON_ABOUT_XOFFSET=75;
	public static int BUTTON_ABOUT_YOFFSET=201;
	//EXIT按钮
	public static int BUTTON_EXIT_XOFFSET=277;
	public static int BUTTON_EXIT_YOFFSET=201;
	
	//START按钮的尺寸
	public static int BUTTON_START_WIDTH=128;
	public static int BUTTON_START_HEIGHT=64;
	//START按钮的尺寸
	public static int BUTTON_HELP_WIDTH=128;
	public static int BUTTON_HELP_HEIGHT=64;
	//ABOUT按钮的尺寸
	public static int BUTTON_ABOUT_WIDTH=128;
	public static int BUTTON_ABOUT_HEIGHT=64;
	//EXIT按钮的尺寸
	public static int BUTTON_EXIT_WIDTH=128;
	public static int BUTTON_EXIT_HEIGHT=64;	
	//加载界面中图片的相关参数
	public static final int BEIJING_XOFFSET=0;
	public static final int BEIJING_YOFFSET=0;
	//黑白方标志位位置相关参数
	public static final float BLACK_FLAG_X=-1.6f;
	public static final float BLACK_FLAG_Y=1.5f;
	
	public static final float WHITE_FLAG_X=1.6f;
	public static final float WHITE_FLAG_Y=1.5f;
	
	//箭头标志板相关位置参数
	public static final float PLAYER_TYPE_X1=-1.62f;
	public static final float PLAYER_TYPE_X2=1.62f;
	public static final float PLAYER_TYPE_Y=1.8f;
	
	public static final float CURR_MOVE_PLAYER_X1=-1.62f;
	public static final float CURR_MOVE_PLAYER_X2=1.62f;
	public static final float CURR_MOVE_PLAYER_Y=1.2f;

	 //设置颜色元素参数
	 public static float ONE=65535F;
	  //棋盘颜色数组
	 public static final float[][] COLORARR=new float[][]
	  {
		  new float[]{ONE,ONE,ONE,0},//白
		  new float[]{0,0,0,0},//黑
		  new float[]{ONE,0,0,0}//红
	  };
	  //棋盘单位格子大小
	  public static float UNIT_SIZE=1f;//每格的单位长度
	  //摄像机位置距离观察目标点的距离  
	  public static final float DISTANCE=12f;
	  //房间大小
	  public static final float HOUSE_SIZE=34f;
}