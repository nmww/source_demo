package com.bn.tkqz;

public class ConstantHP {
	public static int SCREEN_HEIGHT=320;//��Ļ�߶�
	public static int SCREEN_WIDTH=480;//��Ļ���
	public static final int RIGHT_MARGIN=60;//��Ļ�ұ����׿��
	public static final int RIGHT_BAR=5;//��Ļ�ұ����Ŀ��
	//��Ϸ����xy����
	public static int GAME_VIEW_X=Constant.LEFT_TOP_X;
	public static int GAME_VIEW_Y=Constant.LEFT_TOP_Y;
	public static int GAME_VIEW_WIDTH=SCREEN_WIDTH-RIGHT_MARGIN;;//��Ϸ������
	public static int GAME_VIEW_HEIGHT=SCREEN_HEIGHT;//��Ϸ����߶�
	
	public static final int NUMBER_WIDTH=12;//ÿһ������ռ��Ļ�Ŀ��
	public static final int FIRST_MESSAGE_HEIGHT=25;//��һ����Ϣ�ĸ߶�
	public static final int HANZI_HEIGHT=6;//����ռ��Ļ�ĸ߶�
	public static final int NUMBER_HEIGHT=28;//����ռ��Ļ�ĸ߶�
	
	//======================================�������ֵ============================begin======
	//��������ܳߴ�
	public static final float BUTTON_TOTAL_WIDTH=70;
	public static final float BUTTON_TOTAL_HEIGHT=70;
	//����������������xy����
	public static final float BUTTON_AREA_X=GAME_VIEW_X+GAME_VIEW_WIDTH-BUTTON_TOTAL_WIDTH+46;
	public static final float BUTTON_AREA_Y=GAME_VIEW_Y+GAME_VIEW_HEIGHT-BUTTON_TOTAL_HEIGHT-8;	
	//������̳ߴ�
	public static final float BUTTON_WIDTH=BUTTON_TOTAL_WIDTH/3;
	public static final float BUTTON_HEIGHT=BUTTON_TOTAL_HEIGHT/3;
	//ʣ��ߴ��һ��
	public static final float OTHER_WIDTH=(BUTTON_TOTAL_WIDTH-BUTTON_WIDTH)/2;
	public static final float OTHER_HEIGHT=(BUTTON_TOTAL_HEIGHT-BUTTON_HEIGHT)/2;
	//�ĸ���ť�����ϵ�����
	public static final float UP_X=BUTTON_AREA_X+OTHER_WIDTH;
	public static final float UP_Y=BUTTON_AREA_Y;
	
	public static final float DOWN_X=BUTTON_AREA_X+OTHER_WIDTH;
	public static final float DOWN_Y=BUTTON_AREA_Y+BUTTON_HEIGHT+OTHER_HEIGHT;
	
	public static final float LEFT_X=BUTTON_AREA_X;
	public static final float LEFT_Y=BUTTON_AREA_Y+OTHER_HEIGHT;
	
	public static final float RIGHT_X=BUTTON_AREA_X+BUTTON_WIDTH+OTHER_WIDTH;
	public static final float RIGHT_Y=BUTTON_AREA_Y+OTHER_HEIGHT;
	//�����ӵ�����ĳߴ�
	public static final float FIRE_BTN_WIDTH=50;
	public static final float FIRE_BTN_HEIGHT=50;
	//�����ӵ���������ϵ�����
	public static final float FIRE_BTN_X=GAME_VIEW_X+2;
	public static final float FIRE_BTN_Y=GAME_VIEW_Y+GAME_VIEW_HEIGHT-FIRE_BTN_HEIGHT-2;	
	//======================================�������ֵ============================end======
	
	//======================================ͼƬֵ===================begin======
	//������̵�����xy����
	public static final float BUTTON_X=BUTTON_AREA_X-7;
	public static final float BUTTON_Y=BUTTON_AREA_Y-7;
	//�������xy����
	public static final float RED_DOT_CENTER_X=BUTTON_AREA_X+BUTTON_WIDTH-3;
	public static final float RED_DOT_CENTER_Y=BUTTON_AREA_Y+BUTTON_HEIGHT-3;
	//����ͼƬ��xy����
	public static final float FIR_MAP_X=FIRE_BTN_X-2;
	public static final float FIR_MAP_Y=FIRE_BTN_Y+3;
	//======================================ͼƬֵ=================== end ======
}
