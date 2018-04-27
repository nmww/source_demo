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
	  private String[] items={"�����","ͼƬ","���","ʱ��","����","�г�","����","��Ϣ","��ͼ"};
	     
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.slidingdrawer);
	        gv = (GridView)findViewById(R.id.myContent); 
	        sd = (SlidingDrawer)findViewById(R.id.sd);
	        iv=(ImageView)findViewById(R.id.iv);
	        MyAdapter adapter=new MyAdapter(this,items,icons);//�Զ���MyAdapter��ʵ��ͼ���item����ʾЧ��
	        gv.setAdapter(adapter);
	        sd.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener()//������
	        {
	          public void onDrawerOpened()
	          {
	            iv.setImageResource(R.drawable.ic_launcher);//��Ӧ�������¼� ����ͼƬ��Ϊ���µ�
	          }
	        });
	        sd.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener()
	        {
	          public void onDrawerClosed()
	          {
	            iv.setImageResource(R.drawable.ic_launcher);//��Ӧ�س����¼�
	          }
	        });
	    }
	}