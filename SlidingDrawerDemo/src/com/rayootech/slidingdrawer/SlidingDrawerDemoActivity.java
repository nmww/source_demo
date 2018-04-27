package com.rayootech.slidingdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;

public class SlidingDrawerDemoActivity extends Activity {
	private GridView gv;
	  private SlidingDrawer sd;
	  private ImageView iv;
	  private int[] icons={R.drawable.ic_launcher,R.drawable.ic_launcher,
	                        R.drawable.ic_launcher,R.drawable.ic_launcher,
	                        R.drawable.ic_launcher,R.drawable.ic_launcher,
	                        R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
	  private String[] items={"浏览器","图片","相机","时钟","音乐","市场","拨号","信息","地图"};
	     
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.slidingdrawer);
	        gv = (GridView)findViewById(R.id.myContent); 
	        sd = (SlidingDrawer)findViewById(R.id.sd);
	        iv=(ImageView)findViewById(R.id.iv);
	        MyAdapter adapter=new MyAdapter(this,items,icons);//自定义MyAdapter来实现图标加item的显示效果
	        gv.setAdapter(adapter);
	        sd.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener()//开抽屉
	        {
	          public void onDrawerOpened()
	          {
	            iv.setImageResource(R.drawable.ic_launcher);//响应开抽屉事件 ，把图片设为向下的
	          }
	        });
	        sd.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener()
	        {
	          public void onDrawerClosed()
	          {
	            iv.setImageResource(R.drawable.ic_launcher);//响应关抽屉事件
	          }
	        });
	    }
	}