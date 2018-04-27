package com.bn.lb.client;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import android.R.color;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import com.bn.lb.client.CItem;
import static com.bn.lb.client.Constant.*;




enum WhichView {MAIN_MENU,IP_VIEW,LOSE_VIEW,YUYUE_VIEW,QUERY_VIEW,GIRD_VIEW,DETIALSVIEW,YUYUEDETAILS,YUYUE_MANAGE,LOSE_INFO_VIEW,
	            LOSE_DETAILS_VIEW,MANAGE_DETAILS_VIEW,QUERYMAIN_VIEW,SELF_VIEW_INFO,HELP_VIEW,ABOUT_VIEW}

public class RootActivity extends Activity 
{
	MainMenuView mmv;
	WhichView curr;
	private String sname;//保存登录时的用户名
	private Vector<String> queryTOgird =new Vector<String>();	
	private Vector<String> detailsSelect=new Vector<String>();	
	private Vector<Integer> resultnumdetails =new Vector<Integer>();
	private String numberdetails;
	private Vector<String> yuyuedetails1=new Vector<String>();//预约主界面到详细界面的数据承载集合
	private Vector<String> yuyuedetails2=new Vector<String>();//预约主页界面到详细界面的数据集合
	private Vector<String> yuyueManage1=new Vector<String>();//预约管理表的数据集合	
	private String bookno;
	private Vector<String> loseInfo=new Vector<String>();//挂失图书基本信息的数据集合
	private Vector<String> loseInfo1=new Vector<String>();//挂失图书基本信息的数据集合
	private Vector<String> manageInfo=new Vector<String>();//查看预约图书的基本信息
	private String SnameID;
	private Vector<String> selfQuery=new Vector<String>();//个人图书查询的基本信息
    private Vector<String> strmanage=new Vector<String>();//文本中的书号
    
    
    Handler hd=new Handler() //接受信息界面跳转
    {
	   @Override
	  public void handleMessage(Message msg)//重写方法  
	  {
		 switch(msg.what)
		 {
		     case 0:
		    	 gotoIpView();  //登录界面
			    break;
		     case 1:
		    	 gotoQueryMainView();   //查询界面
			    break;
		     case 2:
		    	 gotoloseView();   //挂失界面
			    break;
		     case 3:
		    	 gotoyuyueView();  //预约界面
			    break;
		     case 4:
		    	 goToHelpView();   //注销界面及ipview()
		        break;
		     case 5:
		    	 goToAboutView();
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
        //强制为竖屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
      
        goToWelcomeView();
    }
       
    //帮助界面
    public void goToHelpView()
    {
    	setContentView(R.layout.helpview);   	
    	curr=WhichView.HELP_VIEW;
    }
    
    //关于界面
    public void goToAboutView()
    {
    	setContentView(R.layout.about);
    	curr=WhichView.ABOUT_VIEW;
    }
    
    public void goToWelcomeView()
    {
    	MySurfaceView mView=new MySurfaceView(this);
    	setContentView(mView);
    }
    
    public void goToMainMenu()
    {
    	if(mmv==null)
    	{
    		mmv=new MainMenuView(this);
    	}
    	
    	setContentView(mmv);
    	
    	curr=WhichView.MAIN_MENU;
    }
    
    public void gotoIpView()
    {
    	
    	setContentView(R.layout.main);   
    	final Button dlu=(Button)this.findViewById(R.id.button01);
    	final Button chz=(Button)this.findViewById(R.id.button02);
    	final EditText yhm=(EditText)findViewById(R.id.yhm);
    	final EditText pwd=(EditText)findViewById(R.id.pwd);
    	
    	//登陆的按钮设置的监听
    	dlu.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    sname=yhm.getText().toString().trim();
					String spwd=pwd.getText().toString().trim();
					String ppwd=DBUtil.selectPwd(sname);
					System.out.println("==========================AAAAAAAAA=========================");
					System.out.print(ppwd);
					if(spwd.equals(ppwd))
					{						
						goToMainMenu();
						
					}
					else 
					{
						Toast.makeText
						(
								RootActivity.this,
								"登陆失败", 
								Toast.LENGTH_SHORT
						).show();
					}
				}
    			
    		}
    	);
      
        chz.setOnClickListener
        (
    		new  OnClickListener()
    		{
				@Override
				public void onClick(View v) 
				{
					//goToMainMenu();					
					yhm.setText("");
					pwd.setText("");
				}    			
    		}
        );
    	
        curr=WhichView.IP_VIEW;
    	
    }
   
    
    
    public void gotoloseView()
    {
    	setContentView(R.layout.lose); 
    	final EditText tvXH=(EditText)RootActivity.this.findViewById(R.id.loseXH);
    	final EditText tvMM=(EditText)RootActivity.this.findViewById(R.id.loseMM);
    	final Button loseButtonOk=(Button)RootActivity.this.findViewById(R.id.losebok);
    	final Button loseButtonRe=(Button)RootActivity.this.findViewById(R.id.loseresert);
    	//为后退按钮添加监听
    	ImageButton imagelose=(ImageButton)RootActivity.this.findViewById(R.id.ImageButtonlose);
    	imagelose.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goback();
				}
    			
    		}
    	);
    	//重置按钮的设置监听
    	loseButtonRe.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					tvXH.setText("");
					tvMM.setText("");
				}
    			
    		}
    	);
    	loseInfo.clear();
    	//挂失按钮的设置监听
    	loseButtonOk.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SnameID=tvXH.getText().toString().trim();
					String mm=tvMM.getText().toString().trim();
					String ppwd=DBUtil.selectPwd(sname);					
					if(mm.equals(ppwd))
					{
						loseInfo=DBUtil.getSomeInfo(SnameID);
						gotoloseinfoView();
					}
					else 
					{
						Toast.makeText
						(
								RootActivity.this,
								"密码和学号不匹配！", 
								Toast.LENGTH_SHORT
						).show();
					}
				}
    			
    		}
    	);
    	   	
    	curr=WhichView.LOSE_VIEW;
    			
		 
    }
    
    public void gotoloseinfoView()
    {
    	setContentView(R.layout.loseinfo);
    	
    	GridView gvlose=(GridView)RootActivity.this.findViewById(R.id.GridViewlose01);
    	SimpleAdapter sca=new SimpleAdapter
        (
          this,
          generateDataList(loseInfo), //数据List
          R.layout.lose_grid_row, //行对应layout id
          new String[]{"col1","col2","col3","col4","col5"}, //列名列表
          new int[]{R.id.loseTextView02,R.id.loseTextView04,R.id.loseTextView06,R.id.loseTextView08,R.id.loseTextView10}//列对应控件id列表
        );
       
    	gvlose.setAdapter(sca);//为GridView设置数据适配器        
        //设置选项选中的监听器
    	gvlose.setOnItemSelectedListener(
           new OnItemSelectedListener()
           {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {//重写选项被选中事件的处理方法
					
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { }
    	   
           }
        );  
        
        //设置选项被单击的监听器
    	gvlose.setOnItemClickListener(
           new OnItemClickListener()
           {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {//重写选项被单击事件的处理方法				
				LinearLayout l1=(LinearLayout)arg1;//获取当前选中选项对应的LinearLayout
				LinearLayout l2=(LinearLayout)l1.getChildAt(0);
				TextView tvn=(TextView)l2.getChildAt(1);//获取其中的TextView 书号
				String booknum=tvn.getText().toString().trim();
				loseInfo1=DBUtil.getBNSomeInfo(booknum);
				gotolosedetailsView();
			}        	   
           }
        );        
    	//为后退的按钮添加监听
    	ImageButton imageibt=(ImageButton)RootActivity.this.findViewById(R.id.ImageButton_loseinfo1);
    	imageibt.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goback();
				}
    			
    		}
    	);
    	curr=WhichView.LOSE_INFO_VIEW;
    }
    //挂失界面的详细界面
    public void gotolosedetailsView()
    {
    	setContentView(R.layout.lose_details_info);
    	
    	
    	final TextView tv1=(TextView)RootActivity.this.findViewById(R.id.lose_detailTextView02);
    	final TextView tv2=(TextView)RootActivity.this.findViewById(R.id.lose_detailTextView04);
    	final TextView tv3=(TextView)RootActivity.this.findViewById(R.id.lose_detailTextView06);
    	TextView tv4=(TextView)RootActivity.this.findViewById(R.id.lose_detailTextView08);
    	TextView tv5=(TextView)RootActivity.this.findViewById(R.id.lose_detailTextView10);
    	TextView tv6=(TextView)RootActivity.this.findViewById(R.id.lose_detailTextView12);
    	TextView tv7=(TextView)RootActivity.this.findViewById(R.id.lose_detailTextView14);
    	tv1.setText(loseInfo1.get(0));    	
    	tv2.setText(loseInfo1.get(3));
    	tv3.setText(loseInfo1.get(4));
    	tv4.setText(loseInfo1.get(5));
    	tv5.setText(loseInfo1.get(6));
    	tv6.setText(loseInfo1.get(1));
    	tv7.setText(loseInfo1.get(2));
    	
        
    	
    	//确定挂失按钮的监听
    	Button bt=(Button)RootActivity.this.findViewById(R.id.lose_details_button);
    	bt.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					String[] losebook=new String[2];					
					losebook[0]=tv1.getText().toString().trim();
			    	losebook[1]=tv3.getText().toString().trim();
			    	int num=DBUtil.getMaxLBNO()+1;
			    	String sql="insert into losebook values ("+num+",'"+losebook[0]+"','"+losebook[1]+"','"+SnameID+"')";
			    	DBUtil.update(sql);
			    	Toast.makeText
					(
							RootActivity.this,
							"挂失成功！", 
							Toast.LENGTH_SHORT
					).show();
				}
    			
    		}
    	);
      //后退按钮的设置监听
    	ImageButton imageb=(ImageButton)RootActivity.this.findViewById(R.id.details_ImageButtonlose);
    	imageb.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goback();
				}
    			
    		}
    	);
    	
    	curr=WhichView.LOSE_DETAILS_VIEW;
    	
    }
     
    public void gotoyuyueView()
    {
    	setContentView(R.layout.yuyue);   	
    	final EditText yuyueEditSH=(EditText)RootActivity.this.findViewById(R.id.yuyueEditSH);
    	Button orderbook=(Button)RootActivity.this.findViewById(R.id.yuyuequery01);//预约图书的按钮
    	Button managebook=(Button)RootActivity.this.findViewById(R.id.yuyuequery02);//管理预约的按钮
    	//imagebutton后退按钮的监听
        ImageButton ibyuyue=(ImageButton)findViewById(R.id.ImageButtonyuyue);
        ibyuyue.setOnClickListener(
        		new OnClickListener()
        		{

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						goback();
					}
        			
        		}
        );       
    	
    	
        orderbook.setOnClickListener(
        		new OnClickListener()
        		{

    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					 bookno=yuyueEditSH.getText().toString().trim();
    					 if(bookno.equals(""))
    					 {
    						 Toast.makeText
    							(
    								RootActivity.this,
    								"书号为空，请输入书号！", 
    								Toast.LENGTH_SHORT
    							).show();	
    					 }
    					 else
    					 {
    						 yuyuedetails1.clear();
    						 yuyuedetails2.clear();
    						 yuyuedetails1=DBUtil.getISinfromdetails(bookno);
    						 if(yuyuedetails1.size()==0)
    						 {
    							 Toast.makeText
      							(
      								RootActivity.this,
      								"对不起，你要预约的资料不存在！", 
      								Toast.LENGTH_SHORT
      							).show();
    						 }
    						 else if(yuyuedetails1.get(2).toString().equals("是"))
    					     {
    					    	 Toast.makeText
     							(
     								RootActivity.this,
     								"该书已被预约，请选择其他书号！", 
     								Toast.LENGTH_SHORT
     							).show();	
    					     }    					     
    					     else
    					     {
    					          yuyuedetails2=DBUtil.getISfrombook(yuyuedetails1.get(0).toString().trim());
    					          gotoyuyuedetails();
    					     }
    					 }
    				}
        			
        		}
        	);
        
        yuyueManage1.clear();
    	
    	//管理预约的按钮的设置监听
    	managebook.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					yuyueManage1=DBUtil.getBNofromOrder(sname);
					if(yuyueManage1.size()==0)
					{
						 Toast.makeText
							(
								RootActivity.this,
								"你没有预约过图书，请先预约图书再进行管理！", 
								Toast.LENGTH_SHORT
							).show();
					}
					else
					{						
						gotoyuyueManage();
					}
				}
    			
    		}
    	);    	
    	curr=WhichView.YUYUE_VIEW;    	    	   	    	   	
    }
    
    public void gotoyuyueManage()
    {
    	setContentView(R.layout.yuyuemanage);
    	
    	TextView tvnum=(TextView)RootActivity.this.findViewById(R.id.yuyuemanageNum);
    	tvnum.setText(yuyueManage1.size()/4+"");
    	GridView gridManage01=(GridView)RootActivity.this.findViewById(R.id.GridViewyuyue01);
    	generateDataList1(yuyueManage1,yuyueManage1);
    	gridManage01.setAdapter(gridView1(yuyueManage1,yuyueManage1));
    	//设置选项选中的监听器
    	gridManage01.setOnItemSelectedListener(
           new OnItemSelectedListener()
           {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {//重写选项被选中事件的处理方法
					
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { }
    	   
           }
        ); 
        
      
    	strmanage.clear();
        //设置选项被单击的监听器
    	gridManage01.setOnItemClickListener(
           new OnItemClickListener()
           {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {//重写选项被单击事件的处理方法
				//TextView tv=(TextView)findViewById(R.id.girdTextView);//获取主界面TextView
				LinearLayout ll=(LinearLayout)arg1;//获取当前选中选项对应的LinearLayout				
				TextView l3=(TextView)ll.getChildAt(0);
			    String str1=l3.getText().toString().trim();
				manageInfo=DBUtil.getBNSomeINFO(str1);
				//strmanage=DBUtil.getifBorrow(str1);
				gotomanagedetailsView();																		
			}        	   
           }
        );
    	//后退按钮设置后退的监听事件
    	ImageButton imageManage=(ImageButton)RootActivity.this.findViewById(R.id.ImageButtonyuyue_manage01);
    	imageManage.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goback();
				}
    			
    		}
    	);
    	curr=WhichView.YUYUE_MANAGE;
    }
 
    public void gotomanagedetailsView()
    {
    	
    	if(manageInfo.get(5).toString().equals("是"))
   	    {
    		setContentView(R.layout.yuyuemanagedetails1);
    		String bytime=DBUtil.getifBorrow(manageInfo.get(0).toString().trim()).toString();
    		TextView tvma=(TextView)RootActivity.this.findViewById(R.id.ordermanageTextV);
    		tvma.setText(bytime);
    	}
    	else 
   	   {
    		setContentView(R.layout.yuyuemanagedetails);
     	}
    	//String ss=DBUtil.getifBorrow(str).toString().trim();	   	
    	TextView tt1=(TextView)RootActivity.this.findViewById(R.id.manage_detailTextView02);
    	TextView tt2=(TextView)RootActivity.this.findViewById(R.id.manage_detailTextView04);
    	TextView tt3=(TextView)RootActivity.this.findViewById(R.id.manage_detailTextView06);
    	TextView tt4=(TextView)RootActivity.this.findViewById(R.id.manage_detailTextView08);
    	TextView tt5=(TextView)RootActivity.this.findViewById(R.id.manage_detailTextView10);
    	tt1.setText(manageInfo.get(0).toString());
    	tt2.setText(manageInfo.get(1).toString());
    	tt3.setText(manageInfo.get(2).toString());
    	tt4.setText(manageInfo.get(3).toString());
    	tt5.setText(manageInfo.get(4).toString());
    	
    	//返回按钮的监听事件
    	Button btd=(Button)RootActivity.this.findViewById(R.id.manage_details_button);
    	btd.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					gotoyuyueManage();
				}
    			
    		}
    	);
    	//imageButton返回的监听事件
    	ImageButton ibd=(ImageButton)RootActivity.this.findViewById(R.id.details_ImageButtonmanage);
    	ibd.setOnClickListener(
        		new OnClickListener()
        		{

    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					gotoyuyueManage();
    				}
        			
        		}
        	);
    	
    	curr=WhichView.MANAGE_DETAILS_VIEW;
    }
    public void gotoyuyuedetails()
    {   	
    	if(yuyuedetails1.get(1).toString().trim().equals("是"))
    	{
    		setContentView(R.layout.yuyuedetails1);
    		LinearLayout lo=(LinearLayout)RootActivity.this.findViewById(R.id.yuyuedetailsLinear);
        	TextView tv07=(TextView)RootActivity.this.findViewById(R.id.yuyuedetailsBrrow);
    		lo.setVisibility(1);    		
    		String str=DBUtil.gettimefromrecord(bookno);
    		tv07.setText(str);
        	TextView tv01=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails01);
        	TextView tv02=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails02);
        	TextView tv03=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails03);
        	TextView tv04=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails04);
        	TextView tv05=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails05);
        	TextView tv06=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails06);   	
        	tv01.setText(bookno);
        	tv02.setText(yuyuedetails2.get(0));
        	tv03.setText(yuyuedetails2.get(1));
        	tv04.setText(yuyuedetails2.get(2));
        	tv05.setText(yuyuedetails2.get(3)); 
        	StringBuilder sb=new StringBuilder();		
    		sb.append("                 ");
    		sb.append(yuyuedetails1.get(3).toString());//获取描述信息	
        	tv06.setText(sb);
        
        	//确定预约按钮的设置监听
        	Button yuyueOK=(Button)RootActivity.this.findViewById(R.id.yuyueOK);
        	yuyueOK.setOnClickListener(
        		new OnClickListener()
        		{

    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					//按下此按钮显示姓名输入对话框
    				    String[] yuyueInfo=new String[3];
    					yuyueInfo=DBUtil.getIDClNO(sname);//通过登录的学生的ID得到这个学生的基本信息
    					String sql="insert into orderbook values('"+bookno+"','"+yuyueInfo[2]+"','"+yuyueInfo[1]+"','"+yuyuedetails2.get(1).toString().trim()+"','"+yuyueInfo[0]+"','"+yuyuedetails2.get(2).toString().trim()+"')";
    					String sql1="update bdetailedinformation set Ordered='是' where B_Num='"+bookno+"'";
    					DBUtil.update(sql);
    					DBUtil.update(sql1);
    					Toast.makeText 
    					(
    							RootActivity.this,
    							"预约成功！", 
    							Toast.LENGTH_SHORT
    					).show();
    				}   			
        		}
        	);
    	}
    	else 
    	{
    	    setContentView(R.layout.yuyuedetails);
    	    TextView tv01=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails01);
        	TextView tv02=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails02);
        	TextView tv03=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails03);
        	TextView tv04=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails04);
        	TextView tv05=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails05);
        	TextView tv06=(TextView)RootActivity.this.findViewById(R.id.TextVieworderdetails06);   	
        	tv01.setText(bookno);
        	tv02.setText(yuyuedetails2.get(0));
        	tv03.setText(yuyuedetails2.get(1));
        	tv04.setText(yuyuedetails2.get(2));
        	tv05.setText(yuyuedetails2.get(3)); 
        	StringBuilder sb=new StringBuilder();		
    		sb.append("                 ");
    		sb.append(yuyuedetails1.get(3).toString());//获取描述信息	
        	tv06.setText(sb);
        
        	//返回按钮按钮的设置监听
        	Button breturn=(Button)RootActivity.this.findViewById(R.id.yuyueReturn);
        	breturn.setOnClickListener(
        		new OnClickListener()
        		{

    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					gotoyuyueView();
    				}   			
        		}
        	);
    	}
    	
    	
    	//后退按钮的设置监听
    	ImageButton imagebt=(ImageButton)RootActivity.this.findViewById(R.id.ImageButtonyuyue_details01);
    	imagebt.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goback();
				}
    			
    		}
    	);
    	

    	curr=WhichView.YUYUEDETAILS;
    }
    
    //图书查询主界面
    public void gotoQueryMainView()
    {
    	setContentView(R.layout.self_or_query);
    	//后退按钮的设置监听
    	ImageButton selfib=(ImageButton)RootActivity.this.findViewById(R.id.ImageButtonself_query);
    	selfib.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goback();
				}
    			
    		}
    	);
    	
    	//查询图书的界面
    	Button selfb=(Button)RootActivity.this.findViewById(R.id.self_or_bok);
    	selfb.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					gotoQueryView();
				}
    			
    		}
    	);
    	
    	//查询个人图书界面
    	selfQuery.clear();
    	Button selfb1=(Button)RootActivity.this.findViewById(R.id.self_1_orbutton);
    	selfb1.setOnClickListener(
    		new OnClickListener()
    		{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					selfQuery=DBUtil.getSomeInfo(sname);
					gotoSelfView();
				}
    			
    		}
    	);
    	
    	curr=WhichView.QUERYMAIN_VIEW;
    }
    
    //个人图书的查询
    public void gotoSelfView()
    {
    	setContentView(R.layout.self_query);    	
    	//后退按钮的设置监听
    	ImageButton selfib1=(ImageButton)RootActivity.this.findViewById(R.id.ImageButtonself_details01);
    	selfib1.setOnClickListener(
    		new OnClickListener()
    		{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goback();
				}  			
    		}
    	);
       	//girdView
    	GridView gr=(GridView)RootActivity.this.findViewById(R.id.selfGridViewdetails01);
    	selfgenerateDataList(selfQuery);
		gr.setAdapter(selfgridView(selfQuery));   	
    	curr=WhichView.SELF_VIEW_INFO;
    }
    public void gotoQueryView()
    {
    	setContentView(R.layout.query);
    	final Spinner sp=(Spinner)findViewById(R.id.Spinner01);
    	List<CItem > lst = new ArrayList<CItem>();
        CItem  ct = new CItem ("1","书名");
        CItem  ct1 = new CItem ("2","作者");
        CItem  ct2 = new CItem ("3","出版社");
        lst.add(ct);
        lst.add(ct1);
        lst.add(ct2);
        ArrayAdapter<CItem > Adapter = new ArrayAdapter<CItem>(RootActivity.this,
            android.R.layout.simple_spinner_item, lst);
        sp.setAdapter(Adapter);
        
        Button sbmit=(Button)findViewById(R.id.querybok);
        final RadioButton simpleq=(RadioButton)findViewById(R.id.simpleQuery);
        final RadioButton highq=(RadioButton)findViewById(R.id.highQuery);
        
        final EditText simpleEdit=(EditText)findViewById(R.id.simpleQueryEdit);
        final EditText highEditSM=(EditText)findViewById(R.id.highEditSM);
        final EditText highEditZZ=(EditText)findViewById(R.id.highEditZZ);
        final EditText highEditCBS=(EditText)findViewById(R.id.highEditCBS);
        
        final LinearLayout simple=(LinearLayout)findViewById(R.id.linearsimple);
        final LinearLayout high=(LinearLayout)findViewById(R.id.linearhigh);
                        
        //imagebutton后退按钮的监听
        ImageButton ibquery=(ImageButton)findViewById(R.id.ImageButtonquery);
        ibquery.setOnClickListener(
        	new OnClickListener()
        	{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goback();
				}
        		
        	}
        );
          
       //两个简单和高级单选按钮的单击的监听的方法 
        simpleq.setOnClickListener(
        	new OnClickListener()
        	{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					   simple.setVisibility(1);					   
					   high.setVisibility(-1);
				}
        		
        	}
        );
        //高级单选按钮的单击监听方法       
        highq.setOnClickListener(
            	new OnClickListener()
            	{

    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub    					    						
    					   simple.setVisibility(-1);
 					       high.setVisibility(1);    					
    				}
            		
            	}
            );
                      
        sbmit.setOnClickListener(
        	new OnClickListener()
        	{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Vector<String> vvvv =new Vector<String>();
					//简单查询的事件监听
					if(simpleq.isChecked())
					{						
						String result=simpleEdit.getText().toString().trim();
						String id=((CItem)sp.getSelectedItem()).GetID().toString().trim();
						
						if(result.equals(""))
						{
							Toast.makeText
							(
									RootActivity.this,
									"输入不能为空，请输入要查询的内容!", 
									Toast.LENGTH_SHORT
							).show();	
						}
					  
						else 
						{	
							resultnumdetails.clear();
						    if(id.equals("1"))//通过输入书名进行查询
						    {
						    	queryTOgird=DBUtil.selectAllfrombook(result);
						    	toast(queryTOgird,resultnumdetails);															
						     }
						    else if(id.equals("2"))//通过作者进行查询
						    {
						    	queryTOgird=DBUtil.getAuthorAllfromBook(result);
						    	toast(queryTOgird,resultnumdetails);
						    }
						   else if(id.equals("3"))//通过出版社进行查询
						    {
							    queryTOgird=DBUtil.getPubAllfrombook(result);
							    toast(queryTOgird,resultnumdetails);			
						    }
						}
					}
					else if(highq.isChecked())
					{
						String highSM=highEditSM.getText().toString().trim();
						String highZZ=highEditZZ.getText().toString().trim();
						String highCBS=highEditCBS.getText().toString().trim();
						resultnumdetails.clear();
						if(highSM.equals("")&&highZZ.equals("")&&highCBS.equals(""))
						{
							Toast.makeText
							(
									RootActivity.this,
									"输入不能全为空，请输入要查询的内容!", 
									Toast.LENGTH_SHORT
							).show();	
						}
						//书名和作者的组合查询
						else if((!highSM.equals(""))&&(!highZZ.equals(""))&&(highCBS.equals("")))							
						{
							queryTOgird=DBUtil.getBnAuAllfrombook(highSM, highZZ);
							toast(queryTOgird,resultnumdetails);
						}
						//书名和出版社的组合查询
						else if((!highSM.equals(""))&&(highZZ.equals(""))&&(!highCBS.equals("")))
						{
							queryTOgird=DBUtil.getBnCbAllfrombook(highSM, highCBS);
							toast(queryTOgird,resultnumdetails);
						}
						//作者和出版社的组合查询
						else if((highSM.equals(""))&&(!highZZ.equals(""))&&(!highCBS.equals("")))
						{
							queryTOgird=DBUtil.getAuCbAllfrombook(highZZ, highCBS);
							toast(queryTOgird,resultnumdetails);
						}
						//书名作者出版社三者的组合查询
						else if((!highSM.equals(""))&&(!highZZ.equals(""))&&(!highCBS.equals("")))
						{
							queryTOgird=DBUtil.getBnAuCbAllfrombook(highSM, highZZ, highCBS);
							toast(queryTOgird,resultnumdetails);
						}
						else 
						{
							Toast.makeText
							(
									RootActivity.this,
									"输入最多一个为空，请输入要查询的内容!", 
									Toast.LENGTH_SHORT
							).show();
						}
					}										
				}
        		
        	}
        );
        
        
        
        curr=WhichView.QUERY_VIEW;
    }
    
    public void gotogirdView()
    {
    	setContentView(R.layout.grid_main);      	    	
		GridView gv=(GridView)findViewById(R.id.GridView01);
		generateDataList(queryTOgird,resultnumdetails);
		gv.setAdapter(gridView(queryTOgird,resultnumdetails));
		//resultnumdetails.clear();
		
		//设置选项选中的监听器
        gv.setOnItemSelectedListener(
           new OnItemSelectedListener()
           {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {//重写选项被选中事件的处理方法
					
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { }
    	   
           }
        ); 
        
      
        
        //设置选项被单击的监听器
        gv.setOnItemClickListener(
           new OnItemClickListener()
           {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {//重写选项被单击事件的处理方法
				//TextView tv=(TextView)findViewById(R.id.girdTextView);//获取主界面TextView
				LinearLayout ll=(LinearLayout)arg1;//获取当前选中选项对应的LinearLayout
				LinearLayout l2=(LinearLayout)ll.getChildAt(0);				
				TextView tvn=(TextView)l2.getChildAt(1);
				TextView tvn1=(TextView)l2.getChildAt(3);
				LinearLayout l3=(LinearLayout)ll.getChildAt(1);	
				TextView tvn2=(TextView)l3.getChildAt(1);
				LinearLayout l4=(LinearLayout)ll.getChildAt(2);	
				TextView tvn3=(TextView)l4.getChildAt(1);
				LinearLayout l5=(LinearLayout)ll.getChildAt(3);	
				TextView tvn4=(TextView)l5.getChildAt(1);							
				detailsSelect.add(tvn.getText().toString().trim());
				detailsSelect.add(tvn1.getText().toString().trim());
				detailsSelect.add(tvn2.getText().toString().trim());
				detailsSelect.add(tvn3.getText().toString().trim());
				detailsSelect.add(tvn4.getText().toString().trim());				
				gotodetaislView();								
			}        	   
           }
        );
        
        ImageButton gridButtonback=(ImageButton)RootActivity.this.findViewById(R.id.ImageButtonquery_qrid01);
        gridButtonback.setOnClickListener(
        	new OnClickListener()
        	{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goback();
				}
        		
        	}
        );
        
        curr=WhichView.GIRD_VIEW;
    }
    
    public void gotodetaislView()
    {
    	setContentView(R.layout.detials);   	
    	TextView t1=(TextView)RootActivity.this.findViewById(R.id.TextViewdetails01);
    	TextView t2=(TextView)RootActivity.this.findViewById(R.id.TextViewdetails02);
    	TextView t3=(TextView)RootActivity.this.findViewById(R.id.TextViewdetails03);
    	TextView t4=(TextView)RootActivity.this.findViewById(R.id.TextViewdetails04);
    	TextView t5=(TextView)RootActivity.this.findViewById(R.id.TextViewdetails05);
    	TextView t6=(TextView)RootActivity.this.findViewById(R.id.TextViewdetails06);
    	t1.setText(detailsSelect.get(0).toString());
    	t2.setText(detailsSelect.get(1).toString());
    	t3.setText(detailsSelect.get(2).toString());
    	t4.setText(detailsSelect.get(3).toString());
    	t5.setText(detailsSelect.get(4).toString());
    	detailsSelect.clear();
    	String BookISBNgirdTOdetails=t1.getText().toString().trim();			    
    	Vector<String> bookdetails=DBUtil.selectISBNALlfromdetailInfo(BookISBNgirdTOdetails);
    	StringBuilder sb=new StringBuilder();		
		sb.append("                 ");
		sb.append(bookdetails.get(3).toString());//获取描述信息	
    	t6.setText(sb);
    	
    	GridView gvv=(GridView)findViewById(R.id.GridViewdetails01);
		generateDataList1(bookdetails,queryTOgird);
		gvv.setAdapter(gridView1(bookdetails,queryTOgird));
    	
		//设置后退按钮的监听
		ImageButton detailsButtonback=(ImageButton)RootActivity.this.findViewById(R.id.ImageButtonquery_details01);
		detailsButtonback.setOnClickListener(
			new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goback();
				}
				
			}
		);
		//tvdetails.setText(girddetail.get(2));
		curr=WhichView.DETIALSVIEW;
    }
    
    //个人借书的要调用的方法
    public List<? extends Map<String, ?>> selfgenerateDataList(Vector<String> v)
     		{
     	    	ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();;
     	    	int rowCounter=v.size()/7;//得到表格的行数
     	    	for(int i=0;i<rowCounter;i++)
     	    	{//循环生成每行的包含对应各个列数据的Map；col1、col2、col3为列名
     	    		HashMap<String,Object> hmap=new HashMap<String,Object>();
     	    		hmap.put("col1", v.get(i*7+0));   //第一列为书号		
     	    		hmap.put("col2", v.get(i*7+3));//第二例为ISBN
     	    		hmap.put("col3", v.get(i*7+4));//第三列为书名
     	    		hmap.put("col4", v.get(i*7+5));//第四列为作者
     	    		hmap.put("col5", v.get(i*7+6));//第五列为出版社
     	    		hmap.put("col6", v.get(i*7+1));
     	    		hmap.put("col7", v.get(i*7+2));
     	    		list.add(hmap);
     	    	}    	
     	    	return list;
     		}
    public BaseAdapter selfgridView(Vector<String> v)
    {
    	SimpleAdapter sca=new SimpleAdapter
        (
          this,
          selfgenerateDataList(v), //数据List
          R.layout.self_query_info, //行对应layout id
          new String[]{"col1","col2","col3","col4","col5","col6","col7"}, //列名列表
          new int[]{R.id.self_detailTextView02,R.id.self_detailTextView04,R.id.self_detailTextView06,R.id.self_detailTextView08,R.id.self_detailTextView10,R.id.self_detailTextView12,R.id.self_detailTextView14}//列对应控件id列表
        );
    	return sca;
       // gv.setAdapter(sca);//为GridView设置数据适配器    	    	
    }
    
    //等到表格的行数等得到的表格的基本信息
     public List<? extends Map<String, ?>> generateDataList(Vector<String> v,Vector<Integer> num)
    		{
    	    	ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();;
    	    	int rowCounter=v.size()/4;//得到表格的行数
    	    	for(int i=0;i<rowCounter;i++)
    	    	{//循环生成每行的包含对应各个列数据的Map；col1、col2、col3为列名
    	    		HashMap<String,Object> hmap=new HashMap<String,Object>();
    	    		hmap.put("col1", v.get(i*4+0));   //第一列为ISBN		
    	    		hmap.put("col2", num.get(i));//第二例为一种ISBN书的数量
    	    		hmap.put("col3", v.get(i*4+1));//第三列为书名
    	    		hmap.put("col4", v.get(i*4+2));//第四列为作者
    	    		hmap.put("col5", v.get(i*4+3));//第五列为出版社
    	    		//hmap.put("col6", v.get(i*6+5));//第六列列为是否预览
    	    		list.add(hmap);
    	    	}    	
    	    	return list;
    		}
     public List<? extends Map<String, ?>> generateDataList(Vector<String> v)
     		{
     	    	ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();;
     	    	int rowCounter=v.size()/7;//得到表格的行数
     	    	for(int i=0;i<rowCounter;i++)
     	    	{//循环生成每行的包含对应各个列数据的Map；col1、col2、col3为列名
     	    		HashMap<String,Object> hmap=new HashMap<String,Object>();
     	    		hmap.put("col1", v.get(i*7+0));   //第一列为书号		
     	    		hmap.put("col2", v.get(i*7+3));//第二例为ISBN
     	    		hmap.put("col3", v.get(i*7+4));//第三列为书名
     	    		hmap.put("col4", v.get(i*7+5));//第四列为作者
     	    		hmap.put("col5", v.get(i*7+6));//第五列为出版社     	    		
     	    		list.add(hmap);
     	    	}    	
     	    	return list;
     		}
     
     public List<? extends Map<String, ?>> generateDataList1(Vector<String> v,Vector<String> vv)
    		{
    	    	ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();;
    	    	int rowCounter=v.size()/4;//得到表格的行数
    	    	for(int i=0;i<rowCounter;i++)
    	    	{//循环生成每行的包含对应各个列数据的Map；col1、col2、col3为列名
    	    		HashMap<String,Object> hmap=new HashMap<String,Object>();
    	    		hmap.put("col1", v.get(i*4+0));   //第一列为书号	
    	    		hmap.put("col2", v.get(i*4+1));//第二例书名   	    		
    	    		hmap.put("col3", v.get(i*4+2));//第四列是否借阅    	    		  	    		
    	    		list.add(hmap);
    	    	}    	
    	    	return list;
    		}
    
    public BaseAdapter gridView(Vector<String> v,Vector<Integer> result)
    {
    	SimpleAdapter sca=new SimpleAdapter
        (
          this,
          generateDataList(v,result), //数据List
          R.layout.grid_row, //行对应layout id
          new String[]{"col1","col2","col3","col4","col5"}, //列名列表
          new int[]{R.id.TextView02,R.id.TextView04,R.id.TextView06,R.id.TextView08,R.id.TextView10}//列对应控件id列表
        );
    	return sca;
       // gv.setAdapter(sca);//为GridView设置数据适配器    	    	
    }
    
    public BaseAdapter gridView1(Vector<String> v,Vector<String> vv)
    {
    	SimpleAdapter sca=new SimpleAdapter
        (
          this,
          generateDataList1(v,vv), //数据List
          R.layout.details_grid_row, //行对应layout id
          new String[]{"col1","col2","col3"}, //列名列表
          new int[]{R.id.TextViewdetail01,R.id.TextViewdetail02,R.id.TextViewdetail03}//列对应控件id列表
        );
    	return sca;
       // gv.setAdapter(sca);//为GridView设置数据适配器    	    	
    }
          
    
    //后退按钮的事件方法
       
    public boolean goback()
    {
    	if(curr==WhichView.QUERY_VIEW)
		{
    		gotoQueryMainView();	
			return true;
		}
		if(curr==WhichView.GIRD_VIEW)
		{
			gotoQueryView();
			return true;
		}
		if(curr==WhichView.DETIALSVIEW)
		{
			gotogirdView();
			return true;
		}
		if(curr==WhichView.YUYUE_VIEW||curr==WhichView.ABOUT_VIEW||curr==WhichView.HELP_VIEW)
		{
			goToMainMenu();	
			return true;
		}
		if(curr==WhichView.YUYUEDETAILS)
		{
			gotoyuyueView();
			return true;
		}
		if(curr==WhichView.YUYUE_MANAGE)
		{
			gotoyuyueView();
			return true;
		}
		if(curr==WhichView.LOSE_VIEW)
		{
			goToMainMenu();	
			return true;
		}
		if(curr==WhichView.LOSE_INFO_VIEW)
		{
			gotoloseView();
			return true;
		}
		if(curr==WhichView.LOSE_DETAILS_VIEW)
		{
			gotoloseinfoView();
			return true;
		}
		if(curr==WhichView.MANAGE_DETAILS_VIEW)
		{
			gotoyuyueManage();
			return true;
		}
		if(curr==WhichView.QUERYMAIN_VIEW)
		{
			goToMainMenu();	
			return true;
		}
		if(curr==WhichView.SELF_VIEW_INFO)
		{
			gotoQueryMainView();	
			return true;
		}				
    	return false;
    	
    }
    
    //键盘上的返回键的监听事件
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent e)
    {
    	if(keyCode==4)//调制上一个界面的键
    	{//根据记录的当前是哪个界面信息的curr可以知道要跳转到的是哪个界面
    		if(curr==WhichView.QUERY_VIEW)
    		{
        		gotoQueryMainView();	
    			return true;
    		}    		
    		if(curr==WhichView.GIRD_VIEW)
    		{
    			gotoQueryView();
    			return true;
    		}
    		if(curr==WhichView.DETIALSVIEW)
    		{
    			gotogirdView();
    			return true;
    		}
    		if(curr==WhichView.YUYUE_VIEW||curr==WhichView.ABOUT_VIEW||curr==WhichView.HELP_VIEW)
    		{
    			goToMainMenu();	
    			return true;
    		}
    		if(curr==WhichView.YUYUEDETAILS)
    		{
    			gotoyuyueView();
    			return true;
    		}
    		if(curr==WhichView.YUYUE_MANAGE)
    		{
    			gotoyuyueView();
    			return true;
    		}
    		if(curr==WhichView.LOSE_VIEW)
    		{
    			goToMainMenu();	
    			return true;
    		}
    		if(curr==WhichView.LOSE_INFO_VIEW)
    		{
    			gotoloseView();
    			return true;
    		}
    		if(curr==WhichView.LOSE_DETAILS_VIEW)
    		{
    			gotoloseinfoView();
    			return true;
    		}
    		if(curr==WhichView.MANAGE_DETAILS_VIEW)
    		{
    			gotoyuyueManage();
    			return true;
    		}
    		if(curr==WhichView.QUERYMAIN_VIEW)
    		{
    			goToMainMenu();	
    			return true;
    		}
    		if(curr==WhichView.SELF_VIEW_INFO)
    		{
    			gotoQueryMainView();	
    			return true;
    		}
    		
    	}
    	return false;
    	
    }
    
    public void toast()
    {    	
		Toast.makeText
		(
			RootActivity.this,
			"没有查到与你输入相关的资料！", 
			Toast.LENGTH_SHORT
		).show();										 
    }
    
    public void toast(Vector<String> v,Vector<Integer> vv)
    {
    	if(v.size()==0)
		{
			toast();								
		 }
		else 
		{
			for(int i=0;i<v.size()/4;i++)
	        {
		         vv.add(DBUtil.getNumfrombdetailedInfo(v.get(i*4).toString().trim()));
	        }							    
	        gotogirdView();
		}
    	
    }
      
}








