package com.bn.map;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import static com.bn.map.GameSurfaceView.*;
import static com.bn.map.Constant.*;
enum WhichView {WELCOME_VIEW,MAIN_MENU,SETTING_VIEW,
	MAPLEVEL_VIEW,GAME_VIEW,RANKING_VIEW,WIN_VIEW,FAIL_VIEW}
public class MapMasetActivity extends Activity{
	WhichView curr;//��ǰö��ֵ
	WelcomeView wv;//���뻶ӭ����
	GameSurfaceView msv;//������Ϸ����
	GameView gameView;	//���а����
	boolean collision_soundflag=true;//�Ƿ�����ײ����
	Vibrator mVibrator;//��������
	boolean shakeflag=true;//�Ƿ���
	int level;//��ǰ��ѡ�ؿ�
	int map_level_index=1;//���а�����ѡ����
	int curr_grade;//��ǰ��Ϸ�ĵ÷�
	SensorManager mySensorManager;	//SensorManager��������
//	SensorManagerSimulator mySensorManager;	//ʹ��SensorSimulatorģ��ʱ����SensorSensorManager�������õķ���
	SoundPool soundPool;//������
	HashMap<Integer, Integer> soundPoolMap; //������������ID���Զ�������ID��Map
    Handler hd=new Handler(){
			@Override
			public void handleMessage(Message msg){
        		switch(msg.what){
	        		case 0://�л����˵�����
	        			goToMainView();
	        		break;
	        		case 1://�л���Ӯ�Ľ���
	        			goToWinView();
	                    break;
	        		case 2://�л�����Ľ���
	        			goToFailView();
	        			break;
	        		case 3://�л�����Ϸ�Ľ���
	        			goToGameView();
	        			break;
	        		case 4://�л���ѡ�ؽ���
	        			goToMapLevelView();
	        			break;
	        		case 5://�л������ý���
	        			goToSettingView();
	        			break;
	        		case 6://�л������а����
	        			goToRankView();
	        			break;
        		}}};
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);        
        //����ȫ����ʾ
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        //ǿ��Ϊ����
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        SCREEN_HEIGHT=dm.heightPixels;
        SCREEN_WIDTH=dm.widthPixels;        
		//���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	           
        //ʹ��SensorSimulatorģ��ʱ����SensorSensorManager�������õķ���
//        mySensorManager = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
//        mySensorManager.connectSimulator();
        initSound();
        collisionShake();
        initDatabase();  
        goToWelcomeView();//���뻶ӭ����
       // goToWinView();
    }
    //�������ݿ�
    public  void initDatabase(){
    	//������
    	String sql="create table if not exists rank(id int(2) primary key not" +
    			" null,level int(2),grade int(4),time char(20));";
    	SQLiteUtil.createTable(sql);
    }
    //����ʱ��ķ���
    public  void insertTime(int level,int grade)
    {
    	Date d=new Date();
    	int curr_Id;
        String curr_time=(d.getYear()+1900)+"-"+(d.getMonth()+1<10?"0"+
        		(d.getMonth()+1):(d.getMonth()+1))+"-"+d.getDate();
    	String sql_maxId="select max(id) from rank";
    	Vector<Vector<String>> vector=SQLiteUtil.query(sql_maxId);
    	if(vector.get(0).get(0)==null)
    	{
    		curr_Id=0;
    	}
    	else
    	{
    		curr_Id=Integer.parseInt(vector.get(0).get(0))+1;
    	}
    	String sql_insert="insert into rank values("+curr_Id+","+level+
    								","+grade+","+"'"+curr_time+"');";
    	SQLiteUtil.insert(sql_insert);
    }
  //���뻶ӭ����
    public void goToWelcomeView(){
    	if(wv==null){
    		wv=new WelcomeView(this);
    	}
    	setContentView(wv);
    	curr=WhichView.WELCOME_VIEW;
    }
    //�������˵�
    public void goToMainView(){
    	setContentView(R.layout.main);
    	curr=WhichView.MAIN_MENU;
    	ImageButton ib_start=(ImageButton)findViewById(R.id.ImageButton_Start);
    	ImageButton ib_rank=(ImageButton)findViewById(R.id.ImageButton_Rank);
    	ImageButton ib_set=(ImageButton)findViewById(R.id.ImageButton_Set);
    	ib_start.setOnClickListener(//���뵽ѡ�ؽ���
              new OnClickListener(){
				@Override
				public void onClick(View v){
					shake();
					hd.sendEmptyMessage(4);
				}});
    	ib_rank.setOnClickListener(//�л������а����
              new OnClickListener(){
				@Override
				public void onClick(View v){
					shake();
					hd.sendEmptyMessage(6);
				}});
    	ib_set.setOnClickListener(//�л������ý���
              new OnClickListener(){
				@Override
				public void onClick(View v){
					shake();
					hd.sendEmptyMessage(5);
				}});}
    //�������ý���
    public void goToSettingView()
    {
    	setContentView(R.layout.setting);
    	curr=WhichView.SETTING_VIEW;
    	final CheckBox cb_collision=(CheckBox)findViewById(R.id.CheckBox_collision);
    	cb_collision.setChecked(collision_soundflag);
    	final CheckBox cb_shake=(CheckBox)findViewById(R.id.CheckBox_shake);
    	cb_shake.setChecked(shakeflag);
    	ImageButton ib_ok=(ImageButton)findViewById(R.id.ImageButton_ok);
    	ib_ok.setOnClickListener
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					collision_soundflag=cb_collision.isChecked();
					shakeflag=cb_shake.isChecked();
					shake();
					//ǰ�����˵�
					hd.sendEmptyMessage(0);
				}
			}
    	);
    }
    //���뿪ʼ��Ϸѡ�ؽ���
    public void goToMapLevelView()
    {
    	setContentView(R.layout.level_map);
    	curr=WhichView.MAPLEVEL_VIEW;
    	final ImageButton ib_map[]=
    	{
    			(ImageButton)findViewById(R.id.ImageButton_map01),
    			(ImageButton)findViewById(R.id.ImageButton_map02),
    			(ImageButton)findViewById(R.id.ImageButton_map03),
    			(ImageButton)findViewById(R.id.ImageButton_map04),
    			(ImageButton)findViewById(R.id.ImageButton_map05),
    			(ImageButton)findViewById(R.id.ImageButton_map06)
    	};
    		ib_map[0].setOnClickListener//������Ϸ
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//��ʼ����ͼ����
    					shake();
    					guankaID=level=0;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		ib_map[1].setOnClickListener//������Ϸ
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//��ʼ����ͼ����
    					shake();
    					guankaID=level=1;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		ib_map[2].setOnClickListener//������Ϸ
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//��ʼ����ͼ����
    					shake();
    					guankaID=level=2;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		ib_map[3].setOnClickListener//������Ϸ
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//��ʼ����ͼ����
    					shake();
    					guankaID=level=3;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		ib_map[4].setOnClickListener//������Ϸ
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//��ʼ����ͼ����
    					shake();
    					guankaID=level=4;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		ib_map[5].setOnClickListener//������Ϸ
        	( 
                  new OnClickListener() 
                  {
    				@Override
    				public void onClick(View v) 
    				{
    					//��ʼ����ͼ����
    					shake();
    					guankaID=level=5;
    					BallGDThread.initDiTu(); 
    					hd.sendEmptyMessage(3);
    				}
    			}
        	);
    		
    }
    //������Ϸ����
    public void goToGameView()
    {
    	 msv=new GameSurfaceView(this); 
    	 msv.requestFocus();//��ȡ����
         msv.setFocusableInTouchMode(true);//����Ϊ�ɴ���
         curr=WhichView.GAME_VIEW;
    	 setContentView(msv);
    }
    //�������а�
    public void goToRankView()
    {
    	if(gameView==null)
    	{
    		 gameView = new GameView(this);
    	}    	   	
         setContentView(gameView);         
    	curr=WhichView.RANKING_VIEW;
    }
    //������سɹ�
    public void goToWinView()
    {
    	setContentView(R.layout.win);
    	curr=WhichView.WIN_VIEW;
        TextView tv_score=(TextView)findViewById(R.id.TextView_score);//��ǰ�÷�
        TextView tv_flag=(TextView)findViewById(R.id.TextView_flag);//�Ƿ�ˢ�¼�¼
        ImageButton ib_replay=(ImageButton)findViewById(R.id.ImageButton_Replay);//���水ť
        ImageButton ib_next=(ImageButton)findViewById(R.id.ImageButton_Next);//��һ�ذ�ť
        ImageButton ib_back=(ImageButton)findViewById(R.id.ImageButton_Back);//���ذ�ť
        tv_score.setText("���ص÷�Ϊ:"+curr_grade);
        //��ѯ�������ķ�����¼
        String sql_maxScore="select max(grade) from rank where level="+(level+1);
        System.out.println(sql_maxScore);
    	Vector<Vector<String>> vector=SQLiteUtil.query(sql_maxScore);
    	//�����ǰ����������ʷ��¼,��ˢ�¼�¼
    	
    	if(vector.get(0).get(0)==null||curr_grade>Integer.parseInt(vector.get(0).get(0)))
    	{
    		tv_flag.setText("ˢ�¼�¼!");
    	}
    	else
    	{
    		tv_flag.setText("û��ˢ�¼�¼!");
    	}
    	insertTime(level+1,curr_grade);
    	//�����ǰ�ѵ���ص� ����һ�ذ�ť������
    	if(level==5)
    	{
    		ib_next.setEnabled(false);
    		ib_next.setVisibility(INVISIBLE);
    	}
        ib_replay.setOnClickListener//���水ť����   
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					shake();
					BallGDThread.initDiTu();
					hd.sendEmptyMessage(3);
				}
			}
    	);
        ib_next.setOnClickListener//��һ�ذ�ť����
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					shake();
					if(level<5)
					{
						level++;
					}
					guankaID=level;//������һ��
					BallGDThread.initDiTu();
					hd.sendEmptyMessage(3);
				}
			}
    	);
        ib_back.setOnClickListener//���ذ�ť����   ���ص�ѡ�ؽ���
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					shake();
					hd.sendEmptyMessage(4);
				}
			}
    	);
    }
    //�������ʧ��
    public void goToFailView()
    {
    	setContentView(R.layout.fail);
    	curr=WhichView.FAIL_VIEW;
        ImageButton ib_replay=(ImageButton)findViewById(R.id.Fail_ImageButton_Replay);
        ImageButton ib_back=(ImageButton)findViewById(R.id.Fail_ImageButton_Back);
        ib_replay.setOnClickListener//���水ť����
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					shake();
					BallGDThread.initDiTu();
					hd.sendEmptyMessage(3);
				}
			}
    	);
        ib_back.setOnClickListener//���ذ�ť����   ���ص�ѡ�ؽ���
    	( 
              new OnClickListener() 
              {
				@Override
				public void onClick(View v) 
				{
					shake();
					hd.sendEmptyMessage(4);
				}
			}
    	);
    }
    @SuppressWarnings("deprecation")
	private SensorListener mySensorListener = new SensorListener(){
		@Override
		public void onAccuracyChanged(int sensor, int accuracy) 
		{	
		}
		@Override
		public void onSensorChanged(int sensor, float[] values) 
		{
			if(sensor == SensorManager.SENSOR_ORIENTATION)
			{//�ж��Ƿ�Ϊ���ٶȴ������仯����������	
				int directionDotXY[]=RotateUtil.getDirectionDot
				(
						new double[]{values[0],values[1],values[2]} 
			    );
				
				ballGX=-directionDotXY[0]*3.2f;//�õ�X��Z�����ϵļ��ٶ�
				ballGZ=directionDotXY[1]*3.2f;
			}	
		}		
	};
    @Override
	protected void onResume() //��дonResume����
    {		
    	super.onResume();
		mySensorManager.registerListener
		(			//ע�������
				mySensorListener, 					//����������
				SensorManager.SENSOR_ORIENTATION,	//����������
				SensorManager.SENSOR_DELAY_UI		//�������¼����ݵ�Ƶ��
		);
	}
	@Override
	protected void onPause() //��дonPause����
	{		
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);	//ȡ��ע�������
	}
	public void initSound()
    {
			//������
			soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		    soundPoolMap = new HashMap<Integer, Integer>();   
		    //�Զ�������
		    soundPoolMap.put(1, soundPool.load(this, R.raw.dong, 1)); 
    }
    //��������
    public void playSound(int sound, int loop) 
    {
	   if(collision_soundflag)
	   {
		   AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
		    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
		    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
		    float volume = streamVolumeCurrent / streamVolumeMax; 
		    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	   }
	}
    //�ֻ���
    public void collisionShake()
    {
    		mVibrator=(Vibrator)getApplication().getSystemService
            (Service.VIBRATOR_SERVICE);	
    }
    //��
    public void shake()
    {
    	if(shakeflag)
    	{
    		mVibrator.vibrate( new long[]{0,50},-1);
    	}
    }
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent e)
    { 
    	if(keyCode==4&&(curr==WhichView.MAPLEVEL_VIEW||curr==WhichView.SETTING_VIEW||
    			curr==WhichView.RANKING_VIEW))//����ѡ�ؽ���
    	{
    		goToMainView();
    		return true;
    	}
    	if(keyCode==4&&(curr==WhichView.WIN_VIEW||curr==WhichView.FAIL_VIEW))//�����ǰ��Ӯ�����
    	{
    		goToMapLevelView();
    		return true;
    	}
    	if(keyCode==4&&curr==WhichView.MAIN_MENU)//�����ǰ�����˵�����
    	{
    		System.exit(0);
    		return true;
    	}
    	if(keyCode==4&&curr==WhichView.GAME_VIEW)//�����ǰ����Ϸ����
    	{
    		msv.ballgdT.flag=false;
    		goToMapLevelView();
    		return true;
    	}
    	return false;
    }
}