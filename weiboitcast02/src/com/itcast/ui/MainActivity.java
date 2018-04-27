package com.itcast.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends TabActivity{
    public TabHost th;
    public static MainActivity mainUI;
    public RadioButton bt02;
    public MainActivity()
    {
    	mainUI=this;
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.maintabs);
		bt02=(RadioButton)this.findViewById(R.id.radio_button2);
		th=this.getTabHost();
		//添加第一个子页 Home
		th.addTab(th.newTabSpec("TS_HOME")
				    .setIndicator("TS_HOME")
				    .setContent(new Intent(this,HomeActivity.class))
		          );
		//添加第二个子页MSG
		th.addTab(th.newTabSpec("TS_MSG")
			    .setIndicator("TS_MSG")
			    .setContent(new Intent(this,MSGActivity.class))
	          );
	   //添加第三个子页
		th.addTab(th.newTabSpec("TS_USER")
			    .setIndicator("TS_USER")
			    .setContent(new Intent(this,UserInfoActivity.class))
	          );
		//添加第四个子页
		th.addTab(th.newTabSpec("TS_SEARCH")
			    .setIndicator("TS_SEARCH")
			    .setContent(new Intent(this,SearchActivity.class))
	          );
		
		//添加第五个子页
		th.addTab(th.newTabSpec("TS_MORE")
			    .setIndicator("TS_MORE")
			    .setContent(new Intent(this,MoreActivity.class))
	          );
		RadioGroup rg=(RadioGroup)this.findViewById(R.id.main_radio);
	    rg.setOnCheckedChangeListener(new OnCheckedChangeListener()
	    {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
              // Toast.makeText(MainActivity.this,"您选中了"+checkedId, 1000).show();
				switch(checkedId)
				{case R.id.radio_button0://首页
					th.setCurrentTabByTag("TS_HOME");
					break;
				case R.id.radio_button1://信息
					th.setCurrentTabByTag("TS_MSG");
					break;
				case R.id.radio_button2://用户资料
					th.setCurrentTabByTag("TS_USER");
					break;
				case R.id.radio_button3://搜索
					
					th.setCurrentTabByTag("TS_SEARCH");
					break;
				case R.id.radio_button4://更多
					th.setCurrentTabByTag("TS_MORE");
					break;
				}
			}
	    	
	    }
	    );
		
	}

	
}
