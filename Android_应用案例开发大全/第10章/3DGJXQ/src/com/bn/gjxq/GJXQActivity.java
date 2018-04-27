package com.bn.gjxq;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

enum WhichView {WELCOME_VIEW,MAIN_MENU,IP_VIEW,GL_VIEW,LOAD_VIEW,
	   WAIT_VIEW,WIN_VIEW,EXIT_VIEW,FULL_VIEW,ABOUT_VIEW,HELP_VIEW}

public class GJXQActivity extends Activity 
{
	WelcomeView wv;//进入欢迎界面
	MainMenuView mmv;//进入主菜单
	MySurfaceView msv;//进入游戏界面
	WhichView curr;//当前枚举值
	ClientAgent ca;//客户端代理线程
	SoundPool soundPool;//声音池
	HashMap<Integer, Integer> soundPoolMap; //声音池中声音ID与自定义声音ID的Map
	Handler hd=new Handler()//声明消息处理器
	{
			@Override
			public void handleMessage(Message msg)//重写方法
        	{
        		switch(msg.what)
        		{
	        		case 0://切换等待界面
	        			setContentView(R.layout.wait);
	        			curr=WhichView.WAIT_VIEW;
	        		break;
	        		case 1://切换进入3D游戏界面
	        			gotoGameView();	        			
	        		break;
	        		case 2://切换赢的界面
	        			setContentView(R.layout.win);
	        			curr=WhichView.WIN_VIEW;
	        		break;
	        		case 3://切换输的界面
	        			setContentView(R.layout.lost);
	        			curr=WhichView.WIN_VIEW;
	        		break;
	        		case 4://切换有人退出界面
	        			setContentView(R.layout.exit);
	        			curr=WhichView.EXIT_VIEW;
	        		break;
	        		case 5://进入加载界面
	        			gotoLoadView();
	        		break;
	        		case 6://进入菜单界面
	        			goToMainMenu();
	        		break;
	        		case 7://进入人数已满界面
	        			setContentView(R.layout.full);
	        			curr=WhichView.FULL_VIEW;
	        		break;
	        		case 8://初始化棋子模型
	        			initChess();
	        		break;
        		}
        	}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);        
        //设置全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags
        (
        		WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        //强制为横屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        goToWelcomeView();//进入欢迎界面
    }
    //启动线程加载棋子模型
    public void initChess()
    {
    	 new Thread()
         {
         	public void run()
         	{
         		hd.sendEmptyMessage(5);//进入加载界面
         		MySurfaceView.initChessForDraw(GJXQActivity.this.getResources());//初始化棋子
         		hd.sendEmptyMessage(6);	//进入主菜单
         	}
         }.start();
    }
    //进入欢迎界面
    public void goToWelcomeView()
    {
    	if(wv==null)
    	{
    		wv=new WelcomeView(this);
    	}
    	setContentView(wv);
    	curr=WhichView.WELCOME_VIEW;
    }
    //进入主菜单
    public void goToMainMenu()
    {
    	if(mmv==null)
    	{
    		mmv=new MainMenuView(this);
    	}
    	setContentView(mmv);	
    	curr=WhichView.MAIN_MENU;
    }
    //进入ip地址和port界面
    public void gotoIpView()
    {
    	setContentView(R.layout.main);   
    	final Button blj=(Button)this.findViewById(R.id.Button01);
    	final Button bfh=(Button)this.findViewById(R.id.Button02);
    	
        blj.setOnClickListener
        (
    		new  OnClickListener()
    		{
				@Override
				public void onClick(View v) 
				{
					final EditText eta=(EditText)findViewById(R.id.EditText01);
			    	final EditText etb=(EditText)findViewById(R.id.EditText02);
					String ipStr=eta.getText().toString();					
					String portStr=etb.getText().toString();
					//ip地址本地验证
					String[] ipA=ipStr.split("\\.");
					if(ipA.length!=4)
					{
						Toast.makeText
						(
								GJXQActivity.this,
								"服务器IP地址不合法", 
								Toast.LENGTH_SHORT
						).show();
						
						return;
					}
					
					for(String s:ipA)
					{
						try
						{
							int ipf=Integer.parseInt(s);
							if(ipf>255||ipf<0)
							{
								Toast.makeText
								(
										GJXQActivity.this,
										"服务器IP地址不合法", 
										Toast.LENGTH_SHORT
								).show();							
								return;
							}
						}
						catch(Exception e)
						{
							Toast.makeText
							(
									GJXQActivity.this,
									"服务器IP地址不合法!", 
									Toast.LENGTH_SHORT
							).show();							
							return;
						}
					}
					//端口号本地验证
					try
					{
						int port=Integer.parseInt(portStr);
						if(port>65535||port<0)
						{
							Toast.makeText
							(
									GJXQActivity.this,
									"服务器端口号不合法!", 
									Toast.LENGTH_SHORT
							).show();							
							return;
						}						
					}
					catch(Exception e)
					{
						Toast.makeText
						(
								GJXQActivity.this,
								"服务器端口号不合法!", 
								Toast.LENGTH_SHORT
						).show();							
						return;
					}	
					
					//验证过关
					int port=Integer.parseInt(portStr);
					try
					{
						Socket sc=new Socket(ipStr,port);
						DataInputStream din=new DataInputStream(sc.getInputStream());
						DataOutputStream dout=new DataOutputStream(sc.getOutputStream());
						ca=new ClientAgent
						(
								GJXQActivity.this,
								sc,
								din,
								dout
						);
						ca.start();
					}
					catch(Exception e)
					{
						Toast.makeText
						(
								GJXQActivity.this,
								"网络连接失败,请稍后重试!", 
								Toast.LENGTH_SHORT
						).show();	
						return;	
					}	
				}    			
    		} 
        );
        bfh.setOnClickListener
        (
    		new  OnClickListener()
    		{
				@Override
				public void onClick(View v) 
				{
					goToMainMenu();//进入主菜单
				}    			
    		}
        );
    	
    	curr=WhichView.IP_VIEW;
    }
    //进入游戏界面
    public void gotoGameView()
    {
    	 msv=new MySurfaceView(this); 
         msv.requestFocus();//获取焦点
         msv.setFocusableInTouchMode(true);//设置为可触控
         initSound();//初始化声音池
         setContentView(msv);    	
    	 curr=WhichView.GL_VIEW;    	
    }
    //进入加载界面
    public void gotoLoadView()
    {
    	setContentView(R.layout.load);
    	curr=WhichView.LOAD_VIEW;
    }
    
    //进入帮助界面
    public void gotoHelpView()
    {
    	setContentView(R.layout.help);
    	curr=WhichView.HELP_VIEW;
    }
    //进入关于界面
    public void gotoAboutView()
    {
    	setContentView(R.layout.about);
    	curr=WhichView.ABOUT_VIEW;
    }
    //返回主菜单
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent e)
    { 
    	if(keyCode!=4)
    	{
    		return false;
    	}	
    	if(curr==WhichView.WIN_VIEW||curr==WhichView.IP_VIEW||
    	   curr==WhichView.EXIT_VIEW||curr==WhichView.FULL_VIEW||
    	   curr==WhichView.ABOUT_VIEW||curr==WhichView.HELP_VIEW)
    	{
    		goToMainMenu();
    		return true;
    	}
    	if(curr==WhichView.MAIN_MENU)
    	{
    		System.exit(0);
    	}
    	if(curr==WhichView.GL_VIEW)
    	{
    		try 
    		{
				ca.dout.writeUTF("<#EXIT#>");
			}
    		catch (IOException e1) 
			{
				e1.printStackTrace();
			}
    		return true;
    	}
    	if(curr==WhichView.LOAD_VIEW||curr==WhichView.WAIT_VIEW)
    	{
    		return true;
    	}
    	return false;
    }
    public void initSound()
    {
		//声音池
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	    soundPoolMap = new HashMap<Integer, Integer>();   
	    //吃东西音乐
	    soundPoolMap.put(1, soundPool.load(this, R.raw.dong, 1)); 
    }
    //播放声音
    public void playSound(int sound, int loop) 
    {
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}
}








