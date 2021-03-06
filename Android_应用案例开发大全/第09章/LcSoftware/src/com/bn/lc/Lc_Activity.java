package com.bn.lc;

import static com.bn.lc.Constant.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

enum WhichView{    //界面枚举
				LC_VIEW,CATEGORY_VIEW,INCOME_VIEW,SPEND_VIEW,TJ_VIEW,TJSP_VIEW,
				JSQ_VIEW,JSQH_VIEW,INCOMESELECT_VIEW,INSELECT_VIEW,SPENDSELECT_VIEW,
				PERSONDATA_VIEW,SPSELECT_VIEW,INSPSELECT_VIEW,HELP_VIEW,ABOUT_VIEW,
				INLIST_VIEW,SPLIST_VIEW
	          }
public class Lc_Activity extends Activity 
{
   //声明弹出对话框
   Dialog nameInputdialog;
   Dialog dateInputdialog;
   
   static String text01;//收取信息
   static String text02;
   static String text03;
   
   Lc_View lcview;  // 主界面
   WhichView curr;  //枚举
   static int EditTextId;   //ID
     
   static TextView dateEdit01; //日期框
   static TextView dateEdit02; 
   static EditText moneyedit01; //金额输入框
   static EditText moneyedit02; 
   
   static TextView tv0;
   static TextView tv1;
   static TextView tv2;
   static ListView lv;
   
   static String sexDate;
   static String bloodDate;
   static String priovinceDate;
   static String cityDate;
   
   static Spinner sexspinner; //性别
   static Spinner bloodspinner; //血型下拉表
   static Spinner provincespinner; //省份
   static Spinner cityspinner; //城市
   static Spinner yearspinner;//年
      
   static String Idate01;   //日期
   static String Idate02;
   static String Isource;   //来源
   static String Imoney01;  //金额
   static String Imoney02;
   static String Imemo;     //备注
   static String icategory; //类别
   static String Iage;      //年龄
   static String Iemail;    //邮箱
   static String Ioldpwd="123456";   //旧密码
   static String Inewpwd;   //新密码
   static String Iokpwd;    //确认密码
   //static int c=0;  //弹出密码框的标志
   boolean flag=true;
   List<String> data;
   int dbif;
   
    Handler hd=new Handler() //接受信息界面跳转
    {
		  @Override
		  public void handleMessage(Message msg)//重写方法  
		  {
			 switch(msg.what)
			 {
			     case 0:
			    	 goToCategoryView();  //类别维护界面
				    break;
			     case 1:
			    	 goToIncomeView();   //日常收入
				    break;
			     case 2:
			    	 goToSpendView();   //日出支出
				    break;
			     case 3:
			    	 goToIncometjView();  //统计
				    break;
			     case 4:
			    	 goToJsqView();   //计算器
			        break;
			     case 5:
			    	 goToIncomeSelected(); //收入查询
				   break;
			     case 6:
			    	 goToSpendSelectView(); //支出查询
				   break;
			     case 7:
			    	 goToPersonDataView();
				   break;	
			     case 8:
			    	 goToLc_View();
			       break;
			     case 9:
			    	 goToLc_View();
			       break;
			 }
		 }
   };	
   
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //设置全屏显示
        getWindow().setFlags
        (
        		WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        goToWelcomeView(); 
    }
    
    //
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent e)
    {
    	if(keyCode==4)
    	{
    		if((curr==WhichView.CATEGORY_VIEW)||(curr==WhichView.PERSONDATA_VIEW)||
 			   (curr==WhichView.INCOME_VIEW)||(curr==WhichView.SPEND_VIEW)||
 			   (curr==WhichView.INCOMESELECT_VIEW)||(curr==WhichView.SPENDSELECT_VIEW)||
 			   (curr==WhichView.JSQ_VIEW))
 			{//跳到主界面
    			DBUtil.closeDatabase();
 				goToLc_View();
 				return true;
 			}
    		if((curr==WhichView.JSQ_VIEW)||(curr==WhichView.JSQH_VIEW)||
    		   (curr==WhichView.TJ_VIEW)||(curr==WhichView.TJSP_VIEW)||
    		   (curr==WhichView.HELP_VIEW)||(curr==WhichView.ABOUT_VIEW))
    		{
    			goToLc_View();
 				return true;
    		}
    		if(curr==WhichView.INSELECT_VIEW)
			{//跳到收入查询界面
    			DBUtil.closeDatabase();
				Lc_Activity.this.goToIncomeSelected();
			}
			if(curr==WhichView.SPSELECT_VIEW)
			{//跳到支出查询界面
				DBUtil.closeDatabase();
				Lc_Activity.this.goToSpendSelectView();
			}
			if(curr==WhichView.INSPSELECT_VIEW)
			{
				DBUtil.closeDatabase();
				goToIncometjView();
			}
		    if(curr==WhichView.INLIST_VIEW)   //收入查询详细界面
		    {
		    	DBUtil.createOrOpenDatabase();
		    	data=DBUtil.queryIncome( "Income",dbif);  //查看现在的表中数据
		    	DBUtil.closeDatabase();
		    	
		    	goToselectView(data,1,dbif);  //去收入查询界面的方法
		    }
		    if(curr==WhichView.SPLIST_VIEW)  //支出查询详细界面
		    {
		    	DBUtil.createOrOpenDatabase();
		    	data=DBUtil.queryIncome( "Spend",dbif);  //查看现在的表中数据
		    	DBUtil.closeDatabase();
		    	
		    	goToselectView(data,2,dbif);  //去支出查询界面的的方法
		    }    
    	}
    	return false;
    }
     
    //选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	MenuItem help=menu.add(MAIN_GROUP,MENU_GENDER_HELP,0,R.string.help); 
    	help.setIcon(R.drawable.help); 
    	help.setOnMenuItemClickListener
    	(
    		new OnMenuItemClickListener()
    		{
				@Override
				public boolean onMenuItemClick(MenuItem item) 
				{
					Lc_Activity.this.goToHelpView();
					return false;
				} 
    		}
    	);
    	MenuItem about=menu.add(MAIN_GROUP,MENU_GENDER_ABOUT,0,R.string.about); 
    	about.setIcon(R.drawable.about);
    	about.setOnMenuItemClickListener
    	(
    		new OnMenuItemClickListener() 
    		{
				@Override
				public boolean onMenuItemClick(MenuItem item) 
				{
					Lc_Activity.this.goToAboutView();
					return false;
				} 
    		}
    	);
    	return true;
    }  
    
    //创建对话框
    @Override
    public Dialog onCreateDialog(int id)   
    {    	
        Dialog result=null;
    	switch(id)
    	{
	    	case NAME_INPUT_DIALOG_ID://姓名输入对话框
		    	nameInputdialog=new MyDialog(this,R.layout.dialog_name_input); 	    
				result=nameInputdialog;				
			break;	
	    	case DATE_INPUT_DIALOG_ID:
	    		dateInputdialog=new MyDialog(this,R.layout.dialog_date_input);
	    		result=dateInputdialog;
	    	break;
    	}   
		return result;
    }
    
    //每次弹出对话框时被回调以动态更新对话框内容的方法
    @Override    
    public void onPrepareDialog(int id, final Dialog dialog)
    {
    	//若不是等待对话框则返回
    	switch(id)
    	{
 	       //第一个窗口
    	   case NAME_INPUT_DIALOG_ID://姓名输入对话框
    		   //确定按钮
    		   Button bjhmcok=(Button)nameInputdialog.findViewById(R.id.bjhmcOk);
    		   //取消按钮
       		   Button bjhmccancel=(Button)nameInputdialog.findViewById(R.id.bjhmcCancle);
       		   //给确定按钮添加监听器
       		   bjhmcok.setOnClickListener
               ( 
    	          new OnClickListener()
    	          {
    	        	@Override
      				public void onClick(View v) 
      				{
      					//获取对话框里的内容并用Toast显示
      					EditText et=(EditText)nameInputdialog.findViewById(R.id.etname);
      					String name=et.getText().toString().trim();
      		       		DBUtil.createOrOpenDatabase();  
      					String pwd=DBUtil.getPassword();
      					if(pwd==null)
      			        {
      			        	DBUtil.InsertPersonDate();
      			        	pwd="123456";
      			        }
      					if(name.length()!=0)
      					{
      						if(name.equals(pwd)) 
  	    					{
  	    						Lc_Activity.this.hd.sendEmptyMessage(8);
  	    						//关闭对话框
  	        					nameInputdialog.cancel();
  	    					}
  	    					else
  	    					{
  	    						Toast.makeText
  	        					(
  	        						Lc_Activity.this,
  	        						"输入的密码不正确，请重新输入！", 
  	        						Toast.LENGTH_SHORT
  	        					).show();   
  	    					}
      					}
      					else
      					{
      						Toast.makeText
          					(
          						Lc_Activity.this,
          						"密码输入框不可以为空！！！", 
          						Toast.LENGTH_SHORT
          					).show();   
      					} 
      					DBUtil.closeDatabase();
      				}        	  
    	          }
    	        );   
       		    //给取消按钮添加监听器
       		    bjhmccancel.setOnClickListener
       		    (
       		    	new OnClickListener() 
       		    	{
						@Override
						public void onClick(View v) 
						{
							System.exit(0);
						}
       		    	}
       		    );
    	   break;
    	   //第二个窗口
    	   case DATE_INPUT_DIALOG_ID:
    		 //上第一个按钮
    		   ImageButton button1=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton01);
    		   final EditText et1=(EditText)dateInputdialog.findViewById(R.id.EditText01);
    		   this.UpOrDownDateTime(button1, et1, UP_TIME);
    		   //下第一个按钮
    		   ImageButton button4=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton04);
    		   this.UpOrDownDateTime(button4, et1, DOWN_TIME);
    		   //上第二个按钮
    		   ImageButton button2=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton02);
    		   final EditText et2=(EditText)dateInputdialog.findViewById(R.id.EditText02);
    		   this.UpOrDownDateTime(button2, et2, UP_TIME);
    		   //下第二个按钮
    		   ImageButton button5=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton05);
    		   this.UpOrDownDateTime(button5, et2, DOWN_TIME);
    		   //上第三个按钮
    		   ImageButton button3=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton03);
    		   final EditText et3=(EditText)dateInputdialog.findViewById(R.id.EditText03);
    		   this.UpOrDownDateTime(button3, et3, UP_TIME);
    		   //下第三个按钮
    		   ImageButton button6=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton06); 
    		   this.UpOrDownDateTime(button6, et3, DOWN_TIME);
    		   
    		   //确定按钮
    		   Button bsure=(Button)dateInputdialog.findViewById(R.id.Button01);
    		   bsure.setOnClickListener
    		   (
    				new OnClickListener()
    				{
						@Override
						public void onClick(View v) 
						{
							//年
							EditText eyear=(EditText)dateInputdialog.findViewById(R.id.EditText01);
							String syear=eyear.getText().toString().trim();
							//月
							EditText emonth=(EditText)dateInputdialog.findViewById(R.id.EditText02);
							String smonth=emonth.getText().toString().trim();
							//日
							EditText edate=(EditText)dateInputdialog.findViewById(R.id.EditText03);
							String sdate=edate.getText().toString().trim();
					       
							dateverify (syear,smonth,sdate);  //验证日期的合法性
						}
    				}
    		   );
    		   Button bcancel=(Button)dateInputdialog.findViewById(R.id.Button02);
    		   this.cancelDialog(bcancel, dateInputdialog);
    	   break;
    	}
    }

    //欢迎界面
    public void goToWelcomeView()
    {
    	MySurfaceView mView=new MySurfaceView(this);
    	setContentView(mView);
    }
    
    //去主界面方法 
    public void goToLc_View()   
    {
    	if(lcview==null)
    	{ 
    		lcview=new Lc_View(this);
    	}
    	setContentView(lcview);
    	if(flag)
    	{
    		showDialog(NAME_INPUT_DIALOG_ID);
    		flag=false;
    	}
    	
    	DBUtil.createOrOpenDatabase();
    	List<String> slist=DBUtil.queryCategory("Scy");
    	if(slist.size()==0)
    	{
    		DBUtil.insertCategory("工资", "Scy","说明信息");
    	}
    	slist=DBUtil.queryCategory("Zcy");
    	if(slist.size()==0)
    	{
    		DBUtil.insertCategory("消费", "Zcy","说明信息");	
    	}
    	DBUtil.closeDatabase();
    	
    	curr=WhichView.LC_VIEW;
    } 
    
    //类别维护
    @SuppressWarnings("unused")
	public void goToCategoryView()   
    {
    	setContentView(R.layout.category); //设置界面
    	
    	DBUtil.createOrOpenDatabase();  //打开数据库
    	
    	Button addbutton=(Button)this.findViewById(R.id.Button01);  //增加类别按钮
    	Button delbutton=(Button)this.findViewById(R.id.Button02); //删除类别按钮
    	Button returnbutton=(Button)this.findViewById(R.id.Button03); //返回上一界面按钮
		
		final ListView lv=(ListView)this.findViewById(R.id.ListView01);  // 列表框
		List<String> result=DBUtil.queryCategory("Scy");
		this.getDataToListView(lv,"Scy");  //进入类别界面时，赋值列表框数据
		
		final RadioButton rb1=(RadioButton)findViewById(R.id.RadioButton01);  //单选按钮
		final RadioButton rb2=(RadioButton)findViewById(R.id.RadioButton02);
		//单选按钮组的监听     ListView 显示类别数据
		rb1.setOnCheckedChangeListener
		(
			new OnCheckedChangeListener()
            {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
     			{
     				ListView lv=(ListView)Lc_Activity.this.findViewById(R.id.ListView01);
     	    		Lc_Activity.this.getDataToListView(lv,"Zcy");  
     			}        	   
            }      
		);
		rb2.setOnCheckedChangeListener
		(//单选按钮组的监听 
			new OnCheckedChangeListener()
            {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
     			{
     				ListView lv=(ListView)Lc_Activity.this.findViewById(R.id.ListView01);
     	    		Lc_Activity.this.getDataToListView(lv,"Scy");
     			}        	   
            }      
		);
		
		//增加类别监听
    	addbutton.setOnClickListener  
		 (
				 new OnClickListener()
				 {
					 @Override
					 public void onClick(View v)
					 {
						 EditText addedit=(EditText)findViewById(R.id.EditText01);
						 EditText sayedit=(EditText)findViewById(R.id.EditText02);
						 String icategory=addedit.getText().toString().trim();
						 String saytext=sayedit.getText().toString().trim();
						 List<String> slist = null;
						 if(rb1.isChecked())
						 {
							 slist=DBUtil.queryCategory("Scy");
							 boolean flag=Lc_Activity.this.InOrNot(icategory, slist);
							 //测试通过后将10更改到Constant的类中===========================================
							 System.out.println(icategory.length());
							 System.out.println(saytext.length());
							 if((icategory.length()!=0)&&(saytext.length()<textlength)&&(saytext.length()>0))
							 {
								 if(flag) 
								 {
									 DBUtil.insertCategory(icategory,"Scy",saytext);    //插入类别
									 ListView lv=(ListView)findViewById(R.id.ListView01);
									 Lc_Activity.this.getDataToListView(lv,"Scy");
								 }
								 else 
								 {
									 Toast.makeText(Lc_Activity.this, "不可以重复插入！！！", Toast.LENGTH_SHORT).show();
								 }
							 }
							 else if(icategory.length()==0)
							 {
								 Toast.makeText(Lc_Activity.this, "输入框不可为空！！！", Toast.LENGTH_SHORT).show();
							 }
							 else if(saytext.length()>textlength)
							 {
								 Toast.makeText(Lc_Activity.this, "说明不可以超过"+textlength+"个字！", Toast.LENGTH_SHORT).show();
							 }
							 else
							 {
								 Toast.makeText(Lc_Activity.this, "说明框不可以为空！", Toast.LENGTH_SHORT).show();
							 }
						 }
						 if(rb2.isChecked())
						 {
							 slist=DBUtil.queryCategory("Zcy");
							 boolean flag=Lc_Activity.this.InOrNot(icategory, slist);
							 if((icategory.length()!=0)&&(saytext.length()<textlength)&&(saytext.length()>0))
							 {
								 if(flag)
								 {
									 DBUtil.insertCategory(icategory,"Zcy",saytext);    //插入类别
									 ListView lv=(ListView)findViewById(R.id.ListView01);
									 Lc_Activity.this.getDataToListView(lv,"Zcy");
								 }
								 else
								 {
									 Toast.makeText(Lc_Activity.this, "不可以重复插入！！！", Toast.LENGTH_SHORT).show();
								 }
							 }
							 else if(icategory.length()==0)
							 {
								 Toast.makeText(Lc_Activity.this, "不可以重复插入！！！", Toast.LENGTH_SHORT).show();
							 }
							 else if(saytext.length()>textlength)
							 {
								 Toast.makeText(Lc_Activity.this, "说明不可以超过"+textlength+"个字！", Toast.LENGTH_SHORT).show();
							 }
							 else
							 {
								 Toast.makeText(Lc_Activity.this, "说明框不可以为空！", Toast.LENGTH_SHORT).show();
							 }			 
						 }
					 }
				 }
		  );
    	
    	 delbutton.setOnClickListener  //删除按钮监听
		 (
				 new OnClickListener()
				 {
					 @Override
					 public void onClick(View v)
					 {
						 EditText et0=(EditText)findViewById(R.id.EditText01);
						 String str=et0.getText().toString().trim();
						 List<String> slist = null;
						 if(rb1.isChecked())
						 {
							 slist=DBUtil.getIsource("Income");
							 List<String> alist=DBUtil.queryCategory("Scy");
							 boolean flag=Lc_Activity.this.InOrNot(str, slist);
							 boolean flag0=Lc_Activity.this.InOrNot(str, alist);
							 if(str.length()!=0)
							 {
								 if(flag&&(!flag0))
								 {
									 DBUtil.deleteValuesFromTable("Scy", "icategory", str);
									 ListView lv=(ListView)findViewById(R.id.ListView01);
									 Lc_Activity.this.getDataToListView(lv,"Scy"); 
								 }
								 else if(!flag)
								 {
									 Toast.makeText(Lc_Activity.this, "不可以删除，由于收入的来源中还存在该项！", Toast.LENGTH_SHORT).show();
								 }
								 else if(flag0)
								 {
									 Toast.makeText(Lc_Activity.this, "无法删除，由于类别中不存在该项！", Toast.LENGTH_SHORT).show();
								 }
							 }
							 else
							 {
								 Toast.makeText(Lc_Activity.this, "类别输入框不可为空！！！", Toast.LENGTH_SHORT).show();
							 }							 
						 }
						 if(rb2.isChecked())
						 {
							 slist=DBUtil.getIsource("Spend");
							 List<String> alist=DBUtil.queryCategory("Zcy");
							 boolean flag=Lc_Activity.this.InOrNot(str, slist);
							 boolean flag0=Lc_Activity.this.InOrNot(str, alist);
							 System.out.println(flag0);
							 if(str.length()!=0)
							 {
								 if(flag&&(!flag0))
								 {
									 DBUtil.deleteValuesFromTable("Zcy", "icategory", str);
									 ListView lv=(ListView)findViewById(R.id.ListView01);
									 Lc_Activity.this.getDataToListView(lv,"Zcy");
								 }
								 else if(!flag)
								 {
									 Toast.makeText(Lc_Activity.this, "不可以删除，由于收入的来源中还存在该项！", Toast.LENGTH_SHORT).show();
								 }
								 else if(flag0)
								 {
									 Toast.makeText(Lc_Activity.this, "无法删除，由于类别中不存在该项！", Toast.LENGTH_SHORT).show();
								 }
							 }
							 else
							 {
								 Toast.makeText(Lc_Activity.this, "类别输入框不可为空！！！", Toast.LENGTH_SHORT).show();
							 }							 
						 }
					 }
				 }
		  );
    	
    	returnButtonClicked(returnbutton);
   	 	curr=WhichView.CATEGORY_VIEW;
    }
   
    //日常收入界面
    public void goToIncomeView() 
    { 
		 setContentView(R.layout.income); //设置布局
		 DBUtil.createOrOpenDatabase();  //打开数据库
		 Button addbutton=(Button)this.findViewById(R.id.Button01); //增加按钮
		 Button returnbutton=(Button)this.findViewById(R.id.Button03); //返回上一界面
		 
		 dateEdit01=(TextView)findViewById(R.id.EditText01); //日期输入框
		 setEditTextClick(dateEdit01);   //日期输入框监听方法
		 
		 SpinnerListener("Scy"); //下拉表监听器方法
		 addbutton.setOnClickListener  //增加按钮监听
		 (
				 new OnClickListener()
				 {
					 @Override
					 public void onClick(View v)
					 {
						 EditText moneyedit01=(EditText)findViewById(R.id.EditText02); //金额
						 EditText memoedit=(EditText)findViewById(R.id.EditText03); //备注
						 Idate01=dateEdit01.getText().toString().trim(); //获取日期
						 Imoney01=moneyedit01.getText().toString().trim(); //获取金额
						 Imemo=memoedit.getText().toString().trim(); //获取备注
						 List<String> slist=DBUtil.queryTable("Income");
						 String smatch="\\d{1,}";
						 boolean flag=Imoney01.matches(smatch);
						 boolean flag1=Lc_Activity.this.sameOrDifferent(Idate01, Isource, Imoney01, slist);
						 if(flag&&flag1&&(Idate01.length()==10))
						 {
							 DBUtil.insert("Income");  //调用插入数据方法 
							 Toast.makeText(Lc_Activity.this, 
									        "您插入的数据是:日期:"+Idate01+"金额:"+Imoney01+"备注:"+Imemo+"!!!", 
									        Toast.LENGTH_LONG
									        ).show();
						 }
						 else if(!flag)
						 {
							 Toast.makeText(Lc_Activity.this, "您输入的金额不合法，请重新输入！如：10000", Toast.LENGTH_SHORT).show();
						 }
						 else if(Idate01.length()!=10)
						 {
							Toast.makeText(Lc_Activity.this, "请您选择日期！", Toast.LENGTH_SHORT).show(); 
						 } 
						 else
						 {
							 Toast.makeText(Lc_Activity.this, "不可以重复的插入记录！", Toast.LENGTH_SHORT).show();
						 }
					 }
				 }
		 );
		 returnButtonClicked(returnbutton);
		 curr=WhichView.INCOME_VIEW;
    }
    
    //日常支出界面
    public void goToSpendView() 
    {
    	 setContentView(R.layout.spend); //设置布局
    	 
    	 DBUtil.createOrOpenDatabase();  //打开数据库
		 
    	 Button addbutton=(Button)this.findViewById(R.id.Button01); //增加按钮
    	 Button returnbutton=(Button)this.findViewById(R.id.Button03);
		  
    	 dateEdit01=(TextView)findViewById(R.id.EditText01); //日期输入框
		  
		 setEditTextClick(dateEdit01);   //日期输入框监听方法
		 SpinnerListener("Zcy"); //下拉表监听器方法
		 	 		 
		 addbutton.setOnClickListener  //增加按钮监听
		 (
				 new OnClickListener()
				 {
					 @Override
					 public void onClick(View v)
					 {
						 EditText moneyedit01=(EditText)findViewById(R.id.EditText02);
						 EditText memoedit=(EditText)findViewById(R.id.EditText03);
						 
						 Idate01=dateEdit01.getText().toString().trim(); //获取日期
						 Imoney01=moneyedit01.getText().toString().trim(); //获取金额
						 Imemo=memoedit.getText().toString().trim(); //获取备注
						 List<String> slist=DBUtil.queryTable("Spend");
						 String smatch="\\d{1,}";
						 boolean flag=Imoney01.matches(smatch);
						 boolean flag1=Lc_Activity.this.sameOrDifferent(Idate01, Isource, Imoney01, slist);
						 if(flag&&flag1&&(Idate01.length()==10))
						 {
							 DBUtil.insert("Spend");  //调用插入数据方法 
							 Toast.makeText(Lc_Activity.this, 
									        "您插入的数据是:日期:"+Idate01+"金额:"+Imoney01+"备注:"+Imemo+"!!!", 
									        Toast.LENGTH_LONG
									        ).show();
						 }
						 else if(Idate01.length()!=10)
						 {
							Toast.makeText(Lc_Activity.this, "请您选择日期！", Toast.LENGTH_SHORT).show(); 
						 } 
						 else if(!flag)
						 {
							 Toast.makeText(Lc_Activity.this, "您输入的金额不合法，请重新输入！如：10000", Toast.LENGTH_SHORT).show();
						 }
						 else
						 {
							 Toast.makeText(Lc_Activity.this, "不可以重复的插入记录！", Toast.LENGTH_SHORT).show();
						 }
					 }
				 }
		 );
		 returnButtonClicked(returnbutton);
		 curr=WhichView.SPEND_VIEW;
    }
    
    //收入统计界面
    public void goToIncometjView()            
    {
    	 setContentView(R.layout.tj);
    	 
    	 DBUtil.createOrOpenDatabase();  //打开数据库
    	 
       	 dateEdit01=(TextView)findViewById(R.id.EditText01);  
       	 this.setEditTextClick(dateEdit01);  //弹出日期对话框
       	 
       	 dateEdit02=(TextView)findViewById(R.id.EditText02);
       	 this.setEditTextClick(dateEdit02);  //弹出日期对话框
    	 
    	 SpinnerListener("Scy");
    	 addListener(1);
    	 Button Incomebutton=(Button)findViewById(R.id.Button01);
    	 Incomebutton.setOnClickListener
    	 (
    			 new OnClickListener()
    			 {
					@Override
					public void onClick(View v) 
					{
						CheckBox check01=(CheckBox)findViewById(R.id.CheckBox01);
						CheckBox check02=(CheckBox)findViewById(R.id.CheckBox03);
						if(check01.isChecked()&&check02.isChecked())
						{
							Idate01=dateEdit01.getText().toString().trim();
							Idate02=dateEdit02.getText().toString().trim();
							if((Idate01.length()==10)&&(Idate02.length()==10))
							{
								List<String> Insum=DBUtil.getSum("Income",3);
								Toast.makeText(Lc_Activity.this, Insum.get(0),Toast.LENGTH_SHORT).show();
							}
							else
						    {
						    	Toast.makeText(Lc_Activity.this, "请您插入日期！", Toast.LENGTH_SHORT).show();
						    }
						}
						else if(check01.isChecked())
						{
							Idate01=dateEdit01.getText().toString().trim();
							Idate02=dateEdit02.getText().toString().trim();
							if((Idate01.length()==10)&&(Idate02.length()==10))
							{
								List<String> Insum=DBUtil.getSum("Income",1);
							    Toast.makeText(Lc_Activity.this, Insum.get(0),Toast.LENGTH_SHORT).show();
							}
							else
						    {
						    	Toast.makeText(Lc_Activity.this, "请您插入日期！", Toast.LENGTH_SHORT).show();
						    }
						}
						else if(check02.isChecked())
						{
							List<String> Insum=DBUtil.getSum("Income",2);
							Toast.makeText(Lc_Activity.this, Insum.get(0),Toast.LENGTH_SHORT).show();
						}
					} 
    			 }
    	 );
    	 
    	 Button returnbutton=(Button)findViewById(R.id.Button02);
    	 returnbutton.setOnClickListener
    	 (
    			 new OnClickListener()
    			 {
					@Override
					public void onClick(View v) 
					{
						DBUtil.closeDatabase();
		 				goToLc_View();
					} 
    			 }
    	 );
    	 
		 curr=WhichView.TJ_VIEW;
    }
    
    //支出统计界面
    public void goToTjSpView()  
    {
    	setContentView(R.layout.tjsp);
    	DBUtil.createOrOpenDatabase();  //打开数据库
    	SpinnerListener("Zcy");
    	addListener(1);
    	//===============日期弹出框======================
   	 dateEdit01=(TextView)findViewById(R.id.EditText01);  
   	 this.setEditTextClick(dateEdit01);  //弹出日期对话框
   	 
   	 dateEdit02=(TextView)findViewById(R.id.EditText02);
   	 this.setEditTextClick(dateEdit02);  //弹出日期对话框
   	//===============日期弹出框======================
    	
     Button Incomebutton=(Button)findViewById(R.id.Button01);
   	 Incomebutton.setOnClickListener
   	 (
   			 new OnClickListener()
   			 {
					@Override
					public void onClick(View v) 
					{
						CheckBox check01=(CheckBox)findViewById(R.id.CheckBox01);
						CheckBox check02=(CheckBox)findViewById(R.id.CheckBox03);
						if(check01.isChecked()&&check02.isChecked())
						{
							Idate01=dateEdit01.getText().toString().trim();
						    Idate02=dateEdit02.getText().toString().trim();
						    if((Idate01.length()==10)&&(Idate02.length()==10))
						    {
						    	List<String> Insum=DBUtil.getSum("Spend",3);
						    	Toast.makeText(Lc_Activity.this, Insum.get(0),Toast.LENGTH_SHORT).show();
						    }
						    else
						    {
						    	Toast.makeText(Lc_Activity.this, "请您插入日期！", Toast.LENGTH_SHORT).show();
						    }
						}
						else if(check01.isChecked())
						{
							Idate01=dateEdit01.getText().toString().trim();
							Idate02=dateEdit02.getText().toString().trim();
							if((Idate01.length()==10)&&(Idate02.length()==10))
						    {
								List<String> Insum=DBUtil.getSum("Spend",1);
								Toast.makeText(Lc_Activity.this, Insum.get(0),Toast.LENGTH_SHORT).show();
						    }
						    else
						    {
						    	Toast.makeText(Lc_Activity.this, "请您插入日期！", Toast.LENGTH_SHORT).show();
						    }
						}
						else if(check02.isChecked())
						{
							List<String> Insum=DBUtil.getSum("Spend",2);
							Toast.makeText(Lc_Activity.this, Insum.get(0),Toast.LENGTH_SHORT).show();
						}
					} 
   			 }
   	 );
   	 
   	 Button returnbutton=(Button)findViewById(R.id.Button02);
	 returnbutton.setOnClickListener
	 (
			 new OnClickListener()
			 {
				@Override
				public void onClick(View v) 
				{
					DBUtil.closeDatabase();
	 				goToLc_View();
				} 
			 }
	 );
    	
    	curr=WhichView.TJSP_VIEW;
    }
    
    //活期计算器界面
    public void goToJsqView()
    {
    	 setContentView(R.layout.jsq);
		 yearspinner=(Spinner)findViewById(R.id.Spinner01);
	     Lc_Activity.this.PersonSpinner(yearspinner,Constant.years.length,Constant.years);
    	 addListener(0);
		 curr=WhichView.JSQH_VIEW;
    }
    
    //定期计算器界面
    public void goToOthJsqView()
    {
    	setContentView(R.layout.jsqh);
    	addListener(0);
    	curr=WhichView.JSQ_VIEW;
    }
    
    //收入查询界面
    public void goToIncomeSelected()
    {
    	 setContentView(R.layout.incomeselect_view);  //布局设置
    	 
    	 DBUtil.createOrOpenDatabase();  //打开数据库
    	 
    	//===============日期弹出框======================
    	 dateEdit01=(TextView)findViewById(R.id.EditText01);  
    	 this.setEditTextClick(dateEdit01);  //弹出日期对话框
    	 
    	 dateEdit02=(TextView)findViewById(R.id.EditText02);
    	 this.setEditTextClick(dateEdit02);  //弹出日期对话框
    	//===============日期弹出框======================
    	 Button returnbutton=(Button)this.findViewById(R.id.Button02); //返回按钮
    		 
    	 moneyedit01=(EditText)findViewById(R.id.EditText03);  //金额输入框
    	 moneyedit02=(EditText)findViewById(R.id.EditText04);
    	 
		 SpinnerListener("Scy"); //下拉表监听器方法
		 
		 final Button selectBut=(Button)findViewById(R.id.Button01);  //查询数据按钮监听
		 selectBut.setOnClickListener    
	     (
	        	new OnClickListener()
	        	{
					@Override
				    public void onClick(View v) 
					{	
						selected(selectBut,"Income",1);  //  1 去显示收入数据的界面   2去显示支出数据的界面
					}
	        	}               
	      );  

	     returnButtonClicked(returnbutton);
		 curr=WhichView.INCOMESELECT_VIEW;
    }
    
    //支出查询界面
    public void goToSpendSelectView()  
    {
    	 setContentView(R.layout.spendselect_view);
    	 
    	 DBUtil.createOrOpenDatabase();  //打开数据库
    	 
     	//===============日期弹出框======================
    	 dateEdit01=(TextView)findViewById(R.id.EditText01);  
     	 this.setEditTextClick(dateEdit01);
     	 
     	 dateEdit02=(TextView)findViewById(R.id.EditText02);
     	 this.setEditTextClick(dateEdit02);
     	//===============日期弹出框======================
     	Button returnbutton=(Button)this.findViewById(R.id.Button02); //返回按钮
     		 
     	moneyedit01=(EditText)findViewById(R.id.EditText03);  //金额输入框
     	moneyedit02=(EditText)findViewById(R.id.EditText04);
     	 
 		 SpinnerListener("Zcy"); //下拉表监听器方法
 		
 		final Button selectBut=(Button)findViewById(R.id.Button01);  //查询数据按钮监听
 		selectBut.setOnClickListener    
 	     (
 	        	new OnClickListener()
 	        	{
 					@Override  
 				    public void onClick(View v)    
 					{ 
 						selected(selectBut,"Spend",2);  //  1 去显示收入数据的界面   2去显示支出数据的界面
 					}
 	        	}
 	      );

 	     returnButtonClicked(returnbutton);
		 curr=WhichView.SPENDSELECT_VIEW;
    }
    
    //个人信息界面
    public void goToPersonDataView()
    {
    	setContentView(R.layout.persondata);
    	DBUtil.createOrOpenDatabase();
    	sexspinner=(Spinner)findViewById(R.id.Spinner01);    //性别下拉列表
    	PersonSpinner(sexspinner,sexIds.length,sexIds);
    	
    	bloodspinner=(Spinner)findViewById(R.id.Spinner02);  //血型下拉表
    	PersonSpinner(bloodspinner,bloodIds.length,bloodIds);
    
    	provincespinner=(Spinner)findViewById(R.id.Spinner03);  //省份下拉表
    	PersonSpinner(provincespinner,provinceIds.length,provinceIds);
    	
    	cityspinner=(Spinner)findViewById(R.id.Spinner04); //城市下拉表
    	PersonSpinner(cityspinner,cityIds.length,cityIds);
    	
    	dateEdit01=(TextView)findViewById(R.id.EditText01);
		Lc_Activity.this.setEditTextClick(dateEdit01); 

    	Button addbutton=(Button)findViewById(R.id.Button01); //更新个人信息按钮
    	addbutton.setOnClickListener
    	(
    			new OnClickListener()
    			{
					@Override
					public void onClick(View v) 
					{
						//=================文本框======================
						TextView dateEdit01=(TextView)findViewById(R.id.EditText01);
						Idate01=dateEdit01.getText().toString().trim();
						
						EditText ageEdit=(EditText)findViewById(R.id.EditText02);
				    	Iage=ageEdit.getText().toString().trim();
				    	
				    	EditText emailEdit=(EditText)findViewById(R.id.EditText03);
				    	Iemail=emailEdit.getText().toString().trim();
				    	
				    	EditText oldpwdEdit=(EditText)findViewById(R.id.EditText04);
				    	Ioldpwd=oldpwdEdit.getText().toString().trim();
				    	
				    	EditText newpwdEdit=(EditText)findViewById(R.id.EditText05);
				    	Inewpwd=newpwdEdit.getText().toString().trim();
				    	
				    	EditText okpwdEdit=(EditText)findViewById(R.id.EditText06);
				    	Iokpwd=okpwdEdit.getText().toString().trim();
				    	//=================文本框======================
				    	String sage="[1-9][0-9]";
				    	String semail="[a-zA-Z0-9]{8,15}@[a-zA-Z0-9]{3}.com";
				    	String spwd="[a-zA-Z0-9]{6,13}";
				    	if((Iage.matches(sage))&&(Iemail.matches(semail))
				    		&&(Inewpwd.matches(spwd))&&(Ioldpwd.matches(spwd))
				    		&&(Iokpwd.matches(spwd))&&(Inewpwd.length()==Iokpwd.length()))
				    	{
				    		//更改个人数据
				    		String result=DBUtil.getPassword();
				    		if(result.equals(Ioldpwd)) 
				    		{
				    			DBUtil.UpdatePersonDate();
				    			Toast.makeText(Lc_Activity.this, "更改密码成功！", Toast.LENGTH_SHORT).show();
				    		}
				    		else
				    		{
				    			Toast.makeText(Lc_Activity.this, "密码更改失败！您输入的原密码不对！", Toast.LENGTH_SHORT).show();
				    		}
				    	}
				    	else if(!Iage.matches(sage))
				    	{
				    		Toast.makeText(Lc_Activity.this, "年龄的范围在10-99之间", Toast.LENGTH_SHORT).show();
				    	}
				    	else if(!Iemail.matches(semail))
				    	{
				    		Toast.makeText(Lc_Activity.this, "邮箱的@前面的必须是数字或字母且长度在8-15之间", Toast.LENGTH_SHORT).show();
				    	}
				    	else 
				    	{
				    		Toast.makeText(Lc_Activity.this, "密码有误，其长度在6-13之间", Toast.LENGTH_SHORT).show();
				    	}
				    	
					}
    			}
        );
    	
    	Button returnbutton=(Button)findViewById(R.id.Button02);  //返回 按钮监听
    	returnButtonClicked(returnbutton);
    	curr=WhichView.PERSONDATA_VIEW;
    }

    //收入查询详细信息界面
    List<String> slist=null;
    public void goToInxxView(final int dbif)
    {
    	setContentView(R.layout.inlistview);
    	goToSpecificView("Income",dbif);
        
    	Button returnbutton=(Button)findViewById(R.id.Button02);
    	returnbutton.setOnClickListener
    	(
    			new OnClickListener()
    			{
    				@Override
    				public void onClick(View v)
    				{
    					
    			    	DBUtil.createOrOpenDatabase();
    			    	data=DBUtil.queryIncome( "Income",dbif);  //查看现在的表中数据
    			    	DBUtil.closeDatabase();
    					
    					goToselectView(data,1,dbif);
    					
    				}
    			}
    	);
    	
    	curr=WhichView.INLIST_VIEW;
    }
    
    //支出数据查询详细界面
    public void goToSpxxView(final int dbif)          
    {
    	setContentView(R.layout.splistview);
    	goToSpecificView("Spend",dbif);
    	
    	Button returnbutton=(Button)findViewById(R.id.Button02);
    	returnbutton.setOnClickListener
    	(
    			new OnClickListener()
    			{
    				@Override
    				public void onClick(View v)
    				{
    					DBUtil.createOrOpenDatabase();
    			    	data=DBUtil.queryIncome( "Spend",dbif);  //查看现在的表中数据
    			    	DBUtil.closeDatabase();
    					
    					goToselectView(data,2,dbif);
    				}
    			}
    	);
    	
    	curr=WhichView.SPLIST_VIEW;
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
    	setContentView(R.layout.aboutview);
    	curr=WhichView.ABOUT_VIEW;
    }
        
    //TextView的监听
    public int setEditTextClick(final TextView edittext)
    {
    	edittext.setOnClickListener
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					showDialog(DATE_INPUT_DIALOG_ID);
					//=================设置日期弹出框为系统时间==========================
					EditText tempyear=(EditText)dateInputdialog.findViewById(R.id.EditText01);  //年
					EditText tempmonth=(EditText)dateInputdialog.findViewById(R.id.EditText02); //月
					EditText tempdate=(EditText)dateInputdialog.findViewById(R.id.EditText03); //日
					String[] str=DateUtil.getSystemDateTime().split("\\-");
					tempyear.setText(str[0]);   
					tempmonth.setText(str[1]);
					tempdate.setText(str[2]);
					//===================================================================
					if(edittext==Lc_Activity.dateEdit01)
					{
						Lc_Activity.EditTextId=R.id.EditText01;
					}
					else if(edittext==Lc_Activity.dateEdit02)
					{
						Lc_Activity.EditTextId=R.id.EditText02;
					}
				}
			}
		 );
    	return Lc_Activity.EditTextId;
    }
    
    //日期对话框点击向上或向下按钮
    public void UpOrDownDateTime(ImageButton button,final EditText et,final int uptime)
    {
    	button.setOnClickListener
    	(
    		new OnClickListener()
    		{
    			@Override
    			public void onClick(View v)
    			{
    				String etStr=et.getText().toString().trim();
    				int etInt=Integer.parseInt(etStr)+uptime;
    				String str=etInt+"";
    				et.setText(str);
    			}
    		}
    	);
    }
    
    //日期的验证方法
    public  void dateverify (String syear,String smonth,String sdate)  
    {
		//总的
		String str=DateUtil.getdate(syear, smonth, sdate, YEAR_INTERVAL);
		//弹出Toast
		if(str==null)
		{
			switch(DateUtil.ERROR_MSG_INT)
			{
			     case 0:
			    	 Toast.makeText
			    	 (
			    			 Lc_Activity.this, 
			    			 "年份太久远", 
			    			 Toast.LENGTH_LONG
			    	  ).show(); 
			     break;
			     case 1:
			    	 Toast.makeText
			    	 (
			    			 Lc_Activity.this, 
			    			 "该月的天数不对", 
			    			 Toast.LENGTH_LONG
			    	  ).show();
			     break;
			     case 2:
			    	 Toast.makeText
			    	 (
			    			 Lc_Activity.this, 
			    			 "出错", 
			    			 Toast.LENGTH_LONG
			    	 ).show();
			     break;
			     case 3:
			    	 Toast.makeText
			    	 (
			    			 Lc_Activity.this, 
			    			 "年份或月份或日期格式不对！请您检查清楚！", 
			    			 Toast.LENGTH_LONG
			    	  ).show();
			     break;
			}
		}
		//此处改动
		else
		{		
			if(Lc_Activity.EditTextId==R.id.EditText01)
			{
				Lc_Activity.dateEdit01.setText(str);
				
			}
			if(Lc_Activity.EditTextId==R.id.EditText02)
			{
				Lc_Activity.dateEdit02.setText(str);
				
			}
			dateInputdialog.cancel();							
		}
    }

    //退出对话框
    public void cancelDialog(Button button,final Dialog dialog)
    {
    	button.setOnClickListener
    	(
    		new OnClickListener()
    		{
    			@Override
    			public void onClick(View v)
    			{
    				dateInputdialog.cancel();
    			}
    		}
    	);
    }
    
    //个人信息的下拉列表适配器
    public void PersonSpinner(final Spinner setspinner,final int size,final String msgIds[])
    {
    	BaseAdapter ba=new BaseAdapter()
        {
			@Override
			public int getCount() 
			{                           
				return  size;
			}
			@Override
			public Object getItem(int arg0) 
			{ 
				return null;
			}
			@Override
			public long getItemId(int arg0) 
			{ 
				return 0; 
			}
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) 
			{
				LinearLayout ll=new LinearLayout(Lc_Activity.this);
				ll.setOrientation(LinearLayout.VERTICAL);		//设置朝向	
				
				TextView tv=new TextView(Lc_Activity.this);
				tv.setText(msgIds[arg0]);   //设置内容
				tv.setTextSize(15);   //设置字体大小
				tv.setTextColor(R.color.black);//设置字体颜色
				ll.addView(tv);   //添加到LinearLayout中
				
				return ll;
			}  
        };
        setspinner.setAdapter(ba);   //为Spinner设置内容适配器
        setspinner.setOnItemSelectedListener   //设置选项选中的监听器
        (
           new OnItemSelectedListener()
           {
        	    @Override
   				public void onItemSelected(AdapterView<?> arg0, View arg1,
   					int arg2, long arg3) 
        	    {		
	   				LinearLayout ll=(LinearLayout)arg1;//获取当前选中选项对应的LinearLayout
	   				TextView tvn=(TextView)ll.getChildAt(0);//获取其中的TextView 

	   				if(setspinner==sexspinner)      //判断是否点击的是性别
	   				{
	   					sexDate=tvn.getText().toString();
	   				}
	   				if(setspinner==bloodspinner)    //判断是否点击的是血型
	   				{
	   					bloodDate=tvn.getText().toString();
	   				}
	   				if(setspinner==provincespinner) //判断是否点击的是省份
	   				{
	   					priovinceDate=tvn.getText().toString();
	   				}
	   				if(setspinner==cityspinner)     //判断是否点击的是城市
	   				{
	   					cityDate=tvn.getText().toString();
	   				}
	   				if(setspinner==yearspinner)     //判断是否点击的是利率的年份
	   				{
	   					String str=tvn.getText().toString().trim();
	   					EditText et=(EditText)findViewById(R.id.EditText02);
	   					outher:for(int i=0;i<Constant.years.length;i++)
	   					{
	   						if(Constant.years[i]==str)
	   						{
	   							et.setText(Constant.rates[i]);
	   							break outher;
	   						}
	   					}
	   				}
        	    }
        	    @Override
        	    public void onNothingSelected(AdapterView<?> arg0) { }        	   
           }
        );
    }
    
    //下拉表监听方法
    public void SpinnerListener(final String tableName) 
    {
    	DBUtil.queryCategory(tableName);    //查询收入类别
    	Spinner setspinner=(Spinner)this.findViewById(R.id.Spinner01);
    	BaseAdapter ba=new BaseAdapter()
        {
    		List<String> result=DBUtil.queryCategory(tableName);
			@Override
			public int getCount() 
			{
				return result.size()/2;
			}
			@Override
			public Object getItem(int arg0) 
			{ 
				return null;
			}
			@Override
			public long getItemId(int arg0) 
			{ 
				return 0; 
			}
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) 
			{
				LinearLayout ll=new LinearLayout(Lc_Activity.this);
				ll.setOrientation(LinearLayout.VERTICAL);		//设置朝向	
				
				TextView tv=new TextView(Lc_Activity.this);
				tv.setText(result.get(2*arg0));
				tv.setTextSize(18);//设置字体大小
				tv.setTextColor(R.color.black);//设置字体颜色
				ll.addView(tv);//添加到LinearLayout中
				 
				return ll;
			}        	
        };
        
        setspinner.setAdapter(ba);//为Spinner设置内容适配器
        
        setspinner.setOnItemSelectedListener   //设置选项选中的监听器
        (
           new OnItemSelectedListener()
           {
        	    @Override
   				public void onItemSelected(AdapterView<?> arg0, View arg1,
   					int arg2, long arg3) 
        	    {
   				
	   				LinearLayout ll=(LinearLayout)arg1;//获取当前选中选项对应的LinearLayout
	   				TextView tvn=(TextView)ll.getChildAt(0);//获取其中的TextView 
	   		       
	   				Isource=(String) tvn.getText();
        	    }
        	    
        	    @Override
        	    public void onNothingSelected(AdapterView<?> arg0) { }        	   
           }
        );
    }
   
  //收入 支出查询按钮的监听方法，，并对查询条件进行相应的判断
    //收入 支出 连接数据库查询的方法  
    public void selected(Button selectBut,final String tableName,final int state)
    {                                  // state 1 去显示收入数据的界面   2去显示支出数据的界面
    	selectBut.setOnClickListener
	     (
	        	new OnClickListener()
	        	{
					@Override
				    public void onClick(View v) 
					{
					   CheckBox	dataCheck01=(CheckBox)findViewById(R.id.CheckBox01); 
					   CheckBox dataCheck02=(CheckBox)findViewById(R.id.CheckBox02);
					   CheckBox dataCheck03=(CheckBox)findViewById(R.id.CheckBox03);
					   
					    List<String> IncomeDate;						
						if(dataCheck01.isChecked()&&dataCheck02.isChecked()&&dataCheck03.isChecked())
						{//判断 checkBox1 checkBox2 checkBox3  是否选中的情况
							Idate01=dateEdit01.getText().toString();
							Idate02=dateEdit02.getText().toString();
							
							String smatch="\\d{1,}";
							String str0=moneyedit01.getText().toString().trim();
							String str1=moneyedit02.getText().toString().trim();
							boolean flag=((str0.matches(smatch))&&(str1.matches(smatch)));
							if(flag&&(Idate01.length()==10)&&(Idate02.length()==10))
							{
								int int0=Integer.parseInt(str0);
								int int1=Integer.parseInt(str1);
								if(int0<int1)
								{
									Imoney01=moneyedit01.getText().toString();
									Imoney02=moneyedit02.getText().toString();
									IncomeDate=DBUtil.queryIncome(tableName,1);
									if(state==1)  //  1 去显示收入数据的界面   2去显示支出数据的界面
									{             // 1 代表要去数据库查询的第几个条件
										goToselectView(IncomeDate,1,1); 
									}
									if(state==2)
									{
										goToselectView(IncomeDate,2,1);
									}
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "第一个输入的金额必须比第二个输入的金额小！", Toast.LENGTH_SHORT).show();
								}
							}
							else if((Idate01.length()!=10)||(Idate02.length()!=10))
							{
								Toast.makeText(Lc_Activity.this, "请您输入日期！", Toast.LENGTH_SHORT).show();
							}
							else
							{
								Toast.makeText(Lc_Activity.this, "您输入的金额不合法，请重新输入！如：10000", Toast.LENGTH_SHORT).show();
							}
						}
						else if(dataCheck01.isChecked()&&dataCheck02.isChecked())
						{//判断 checkBox1 checkBox2 是否选中的情况
								Idate01=dateEdit01.getText().toString();
								Idate02=dateEdit02.getText().toString();
								
								String smatch="\\d{1,}";
								String str0=moneyedit01.getText().toString().trim();
								String str1=moneyedit02.getText().toString().trim();
								boolean flag=((str0.matches(smatch))&&(str1.matches(smatch)));
								if(flag&&(Idate01.length()==10)&&(Idate02.length()==10))
								{
									int int0=Integer.parseInt(str0);
									int int1=Integer.parseInt(str1);
									if(int0<int1)
									{
										Imoney01=moneyedit01.getText().toString();
										Imoney02=moneyedit02.getText().toString();
										IncomeDate=DBUtil.queryIncome(tableName,2);
										if(state==1)  //  1 去显示收入数据的界面   2去显示支出数据的界面
										{             // 2 代表要去数据库查询的第几个条件
											goToselectView(IncomeDate,1,2); 
										}
										if(state==2)
										{
											goToselectView(IncomeDate,2,2);
										}
									}
									else
									{
										Toast.makeText(Lc_Activity.this, "第一个输入的金额必须比第二个输入的金额小！", Toast.LENGTH_SHORT).show();
									}
								}
								else if((Idate01.length()!=10)||(Idate02.length()!=10))
								{
									Toast.makeText(Lc_Activity.this, "请您输入日期！", Toast.LENGTH_SHORT).show();
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "您输入的金额不合法，请重新输入！如：10000", Toast.LENGTH_SHORT).show();
								}
						}
						else if(dataCheck01.isChecked()&&dataCheck03.isChecked())
						{//判断 checkBox1 checkBox3  是否选中的情况
								Idate01=dateEdit01.getText().toString();
								Idate02=dateEdit02.getText().toString();
								IncomeDate=DBUtil.queryIncome(tableName,3);
								if((Idate01.length()==10)&&(Idate02.length()==10))
								{
									if(state==1)   //  1 去显示收入数据的界面   2去显示支出数据的界面
									{        // 3代表要去数据库查询的第几个条件
										goToselectView(IncomeDate,1,3); 
									}
									if(state==2)
									{
										goToselectView(IncomeDate,2,3);
									}
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "请您输入日期！", Toast.LENGTH_SHORT).show();
								}
						}
						else if(dataCheck02.isChecked()&&dataCheck03.isChecked())
						{//判断 checkBox2 checkBox3  是否选中的情况
								String smatch="\\d{1,}";
								String str0=moneyedit01.getText().toString().trim();
								String str1=moneyedit02.getText().toString().trim();
								boolean flag=((str0.matches(smatch))&&(str1.matches(smatch)));
								if(flag)
								{
									int int0=Integer.parseInt(str0);
									int int1=Integer.parseInt(str1);
									if(int0<int1)
									{
										Imoney01=moneyedit01.getText().toString();
										Imoney02=moneyedit02.getText().toString();
										IncomeDate=DBUtil.queryIncome(tableName,4);
										if(state==1)  //  1 去显示收入数据的界面   2去显示支出数据的界面
										{// 4代表要去数据库查询的第几个条件
											goToselectView(IncomeDate,1,4); 
										}
										if(state==2)
										{
											goToselectView(IncomeDate,2,4);
										}
									}
									else
									{
										Toast.makeText(Lc_Activity.this, "第一个输入的金额必须比第二个输入的金额小！", Toast.LENGTH_SHORT).show();
									}
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "您输入的金额不合法，请重新输入！如：10000", Toast.LENGTH_SHORT).show();
								}
						}
						else if(dataCheck01.isChecked())
						{// checkBox1 是否被选中的情况 
								Idate01=dateEdit01.getText().toString();
								Idate02=dateEdit02.getText().toString();
								IncomeDate=DBUtil.queryIncome(tableName,5);
								if((Idate01.length()==10)&&(Idate02.length()==10))
								{
									if(state==1)   //  1 去显示收入数据的界面   2去显示支出数据的界面
									{// 5 代表要去数据库查询的第几个条件
										goToselectView(IncomeDate,1,5); 
									}
									if(state==2)
									{
										goToselectView(IncomeDate,2,5);
									}
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "请您输入日期！", Toast.LENGTH_SHORT).show();
								}
						}
						else if(dataCheck02.isChecked())
						{	// checkBox2  是否被选中的情况 
								String smatch="\\d{1,}";
								String str0=moneyedit01.getText().toString().trim();
								String str1=moneyedit02.getText().toString().trim();
								boolean flag=((str0.matches(smatch))&&(str1.matches(smatch)));
								if(flag)
								{
									int int0=Integer.parseInt(str0);
									int int1=Integer.parseInt(str1);
									if(int0<int1)
									{
										Imoney01=moneyedit01.getText().toString();
										Imoney02=moneyedit02.getText().toString();
										IncomeDate=DBUtil.queryIncome(tableName,6);
										if(state==1)  //  1 去显示收入数据的界面   2去显示支出数据的界面
										{// 6 代表要去数据库查询的第几个条件
											goToselectView(IncomeDate,1,6); 
										}
										if(state==2)
										{
											goToselectView(IncomeDate,2,6);
										}
									}
									else
									{
										Toast.makeText(Lc_Activity.this, "第一个输入的金额必须比第二个输入的金额小！", Toast.LENGTH_SHORT).show();
									}
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "您输入的金额不合法，请重新输入！如：10000", Toast.LENGTH_SHORT).show();
								}
						}
						else if(dataCheck03.isChecked())
						{// checkBox3  是否被选中的情况 
								IncomeDate=DBUtil.queryIncome(tableName,7);
								if(state==1)  //  1 去显示收入数据的界面   2去显示支出数据的界面
								{// 7 代表要去数据库查询的第几个条件
									goToselectView(IncomeDate,1,7); 
								}
								if(state==2)
								{
									goToselectView(IncomeDate,2,7);
								}
							}
						}
	        	}
	      );
    }
    
    //收入 支出查询后显示数据的界面
    public void goToselectView(List<String> IncomeDate,int state,int dbif)
    {
    	
    		if(state==1)  //  1 去显示收入数据的界面   2去显示支出数据的界面
        	{
        		setContentView(R.layout.inselect_view);
            	
            	ListView lv=(ListView)findViewById(R.id.ListView01);
            	//为收入查询数据界面配置数据，，gridView 适配器
            	SelectBaseAdapter(lv,IncomeDate,1,dbif);
            	//返回按钮监听器
            	Button returnbutton=(Button)findViewById(R.id.Button02);
            	returnbutton.setOnClickListener
            	(
            			new OnClickListener()
            			{
        					@Override
        					public void onClick(View v) 
        					{
        						DBUtil.closeDatabase();
        	    				goToIncomeSelected();  //返回收入查询主界面
        					}
            			}
            	);
            	
            	curr=WhichView.INSELECT_VIEW;
        	}
        	if(state==2)  //  1 去显示收入数据的界面   2去显示支出数据的界面
        	{
        		setContentView(R.layout.spselect_view);
            	
            	ListView lv=(ListView)findViewById(R.id.ListView01);
            	//为支出查询数据界面配置数据，，gridView 适配器
            	SelectBaseAdapter(lv,IncomeDate,2,dbif);
            	//返回按钮监听器
            	Button returnbutton=(Button)findViewById(R.id.Button02);
            	returnbutton.setOnClickListener
            	(
            			new OnClickListener()
            			{
        					@Override
        					public void onClick(View v) 
        					{
        						DBUtil.closeDatabase();
        	    				goToSpendSelectView();   //返回支出查询主界面
        	    				
        					}
            			}
            	);
            	
            	curr=WhichView.SPSELECT_VIEW;
        	}
    }
    
    //进入类别界面时，赋值列表框数据 ListView 数据配置方法
    public void getDataToListView(ListView lv,final String str)
    {
    	BaseAdapter baseadapter=new BaseAdapter()
    	{
    		List<String> result=DBUtil.queryCategory(str);
			@Override
			public int getCount() 
			{
				return result.size()/2;
			}
			@Override
			public Object getItem(int position) 
			{
				return null;
			}
			@Override
			public long getItemId(int position) 
			{
				return 0;
			}
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) 
			{
				LinearLayout ll=new LinearLayout(Lc_Activity.this);
				ll.setOrientation(LinearLayout.HORIZONTAL);		//设置朝向	
				ll.setPadding(5,5,5,5);//设置四周留白	
				TextView tv=new TextView(Lc_Activity.this);
				tv.setText(result.get(2*arg0));
				tv.setTextSize(17);//设置字体大小
				tv.setTextColor(Lc_Activity.this.getResources().getColor(R.color.blue));//设置字体颜色
				tv.setPadding(5,5,5,5);//设置四周留白
			    tv.setGravity(Gravity.LEFT);
				ll.addView(tv);//添加到LinearLayout中				
				//Textview============================================================================
				LinearLayout llt=new LinearLayout(Lc_Activity.this);
				llt.setOrientation(LinearLayout.VERTICAL);
				TextView tv1=new TextView(Lc_Activity.this);
				tv1.setText(result.get((2*arg0)+1));
				String str=result.get((2*arg0)+1);
				final int c=80-str.length();
				tv1.setTextSize(17);//设置字体大小
				tv1.setTextColor(Lc_Activity.this.getResources().getColor(R.color.blue));//设置字体颜色
				tv1.setPadding(c,5,5,5);//设置四周留白
			    tv1.setGravity(Gravity.LEFT);
			    ll.addView(tv1);   
				return ll;
			}
    	};
    	lv.setAdapter(baseadapter);
    	lv.setOnItemClickListener
    	(
    		new OnItemClickListener()
    	    {
    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
    			{//重写选项被单击事件的处理方法
    				EditText et2=(EditText)findViewById(R.id.EditText01);
    				LinearLayout ll=(LinearLayout)arg1;
    				TextView tvn=(TextView)ll.getChildAt(0);
    				et2.setText(tvn.getText().toString());
    			}        	   
    	    }	
    	);
    }  
      
    //GridView适配器
    public void SelectBaseAdapter(ListView lv,final List<String> result,final int id,final int dbif)
    {
    	GridView gv=(GridView)this.findViewById(R.id.GridView01);        
        SimpleAdapter sca=new SimpleAdapter
        (
          this,
          generateDataList(result),           
          R.layout.gridview,  
          new String[]{"col1","col2","col3"}, 
          new int[]{R.id.TextView01,R.id.TextView02,R.id.TextView03}
        );
        gv.setAdapter(sca);//为GridView设置数据适配器
        gv.setOnItemClickListener
        (
        	new OnItemClickListener()
        	{
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						                int arg2, long arg3) 
				{
					LinearLayout ll=(LinearLayout)arg1;
					TextView tvn1=(TextView)ll.getChildAt(0);//获取其中的TextView 
					TextView tvn2=(TextView)ll.getChildAt(1);//获取其中的TextView 
					TextView tvn3=(TextView)ll.getChildAt(2);
					Lc_Activity.text01=tvn1.getText().toString().trim();
					Lc_Activity.text02=tvn2.getText().toString().trim();
					Lc_Activity.text03=tvn3.getText().toString().trim();
					if(id==1)
					{
						goToInxxView(dbif);
					}
					else if(id==2)
					{
						goToSpxxView(dbif);
					}
				} 
        	}
        );
    }
    
    //GridView存储数据
    public List<? extends Map<String, ?>> generateDataList(List<String> result)
    {
    	 ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();;
    	 int rowCounter=result.size()/4;   
    	 for(int i=0;i<rowCounter;i++)
    	 {
    	    HashMap<String,Object> hmap=new HashMap<String,Object>();
    	    hmap.put("col1",result.get(i*4));   	
    	    hmap.put("col2",result.get(i*4+1));
    	    hmap.put("col3",result.get(i*4+2));
    	    hmap.put("col4",result.get(i*4+3));
    	    list.add(hmap);
    	  }    	
    	  return list;
    }
    
    //计算器界面  统计界面 监听事件的实现方法
    public void addListener(final int state)
    {
       //单选按钮
       RadioButton rb1=(RadioButton)findViewById(R.id.RadioButton01);
  	   RadioButton rb2=(RadioButton)findViewById(R.id.RadioButton02);
  	   //按钮
  	   Button button1=(Button)findViewById(R.id.Button01);
  	   Button button2=(Button)findViewById(R.id.Button02);
  	   rb1.setOnCheckedChangeListener
  	   (
  	   	   new OnCheckedChangeListener()
  	   	   {
  				@Override
  				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
  				{
  					if(state==0)
  					{
  						Lc_Activity.this.goToJsqView();
  					}
  					if(state==1)
  					{
  						DBUtil.closeDatabase();
  						Lc_Activity.this.goToTjSpView();
  						
  					}
  				}
  	   	    }
  	   	);
  	   	rb2.setOnCheckedChangeListener
  	   	(
  	   	   new OnCheckedChangeListener()
  	   	   {
  				@Override
  				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
  				{
  					if(state==0)
  					{
  						Lc_Activity.this.goToOthJsqView();
  					}
  					if(state==1)
  					{
  						DBUtil.closeDatabase();
  						Lc_Activity.this.goToIncometjView();
  					}
  				}
  	   	   }
  	   	);
  	   	button1.setOnClickListener
  	   	(
  	   	   new OnClickListener()
  	   	   {
  				@Override
  				public void onClick(View v) 
  				{
  					if(state==0)
  					{
  						Lc_Activity.this.getMoney();
  					}
  					if(state==1)
  					{
  						Toast.makeText(Lc_Activity.this, "统计界面", Toast.LENGTH_LONG).show();
  					}
  				}
  	   	   }
  	   	);
  	  returnView(button2);
     } 
    
    //存款所得金钱的计算
    public void getMoney()
    {
     	float[] result=new float[2];
      	//得到每个EditText的引用
      	EditText et1=(EditText)findViewById(R.id.EditText01);
      	EditText et2=(EditText)findViewById(R.id.EditText02);
      	EditText et3=(EditText)findViewById(R.id.EditText03);
      	EditText et4=(EditText)findViewById(R.id.EditText04);
      	EditText et5=(EditText)findViewById(R.id.EditText05);
      	String str1=et1.getText().toString().trim();//第一个文本框
  	   	String str2=et2.getText().toString().trim();//第二个文本框
  	   	String str3=et3.getText().toString().trim();//第三个文本框
  	   	//字符串匹配
  	   	String sm1="\\d{1,}";
  	   	String sm2="\\d{1,}";
      	if((str1.matches(sm1))&&(str3.matches(sm2)))
      	{
      		float fl1=Float.parseFloat(str1);
      	   	float fl2=Float.parseFloat(str2);
      	   	float fl3=Float.parseFloat(str3);
      	   	float fmoney=fl1*((100+fl2)/100)*fl3;
      	   	result[0]=fmoney;
      	  	result[1]=fmoney-fl1;
      	   	String str=FDSDUtil.formatData(result[0]);
      	   	et4.setText(str);
      	   	str=FDSDUtil.formatData(result[1]); 
      	   	et5.setText(str);
      	}
      	else
      	{
      		Toast.makeText(this, "您输入的数字不合法，请您重新输入！如10000", Toast.LENGTH_LONG).show();
      	}
    }
    
    //判断类别是否已经存在
    public boolean InOrNot(String str,List<String> slist)
    {
    	boolean result=true;
    	outher:for(int i=0;i<slist.size();i++)
    	{
    		if(slist.get(i).equals(str))
    		{
    			result=false;
    			break outher;
    		}
    	}
    	return result;
    }
    
    //判断插入记录否有相同的
    public boolean sameOrDifferent(String date,String category,String money,List<String> slist)
    {
  	  boolean result=true;
  	  outher:for(int i=0;i<slist.size()/4;i++)
  	  {
  		  if((date.equals(slist.get(i*4)))&&
  		     (category.equals(slist.get(i*4+1)))&&(money.equals(slist.get(i*4+2))))
  		  {
  			  result=false;
  			  break outher;
  		  }
  	  }
  	  return result;
    }
    
    //返回按钮的实现  不关闭数据库 
    public void returnView(Button button)
    {
    	button.setOnClickListener
     	(
     		new OnClickListener()
     		{
     			@Override
     			public void onClick(View v)
     			{
     				DBUtil.closeDatabase();
     				goToLc_View();
     			}
     		}
     	);
    }
    
    //闭数据库的返回按钮的监听
    public void returnButtonClicked(Button button)
    {
    	button.setOnClickListener
    	(
    		new OnClickListener()
    		{
    			@Override
    			public void onClick(View v)
    			{
    				DBUtil.closeDatabase();
    				goToLc_View();
    			}
    		}
    	);
    }

    //为 收入查询，，支出查询 的详细信息界面   配置详细信息
    public void goToSpecificView(final String tableName,int dbif)
    {
    	tv0=(TextView)findViewById(R.id.TextView02);
    	tv1=(TextView)findViewById(R.id.TextView03);
    	tv2=(TextView)findViewById(R.id.TextView04);
    	lv=(ListView)this.findViewById(R.id.ListView01);
    	
    	DBUtil.createOrOpenDatabase();
    	slist=DBUtil.getAllInformation(text01, text02, text03, tableName);
    	
    	String[] str0=new String[3];
    	for(int i=1;i<4;i++)
    	{
    		str0[i-1]=slist.get(i);
    	}
    	tv0.setText("时间为:"+str0[0]);
    	tv1.setText("来源为:"+str0[1]);
    	tv2.setText("金额为:"+str0[2]);
    	
    	String str=slist.get(4);    
    	SetListViewData(str,lv); 
    	
    	//删除记录
    	str=slist.get(0);
    	final int id=Integer.parseInt(str);
    	Button button01=(Button)findViewById(R.id.Button01);
    	setListenerToButton(button01,id,tableName,dbif);
    }
    
    //为 收入 支出查询数据的详细信息界面的 ListView配置数据     备注的详细内容
    public void SetListViewData(final String str,ListView lv)
  {
  	BaseAdapter baseadapter=new BaseAdapter()
  	{
			@Override
			public int getCount() 
			{
				return 1;
			}
			@Override
			public Object getItem(int position) 
			{
				return null;
			}
			@Override
			public long getItemId(int position) 
			{
				return 0;
			}
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) 
			{
				LinearLayout ll=new LinearLayout(Lc_Activity.this);
				ll.setOrientation(LinearLayout.HORIZONTAL);		//设置朝向	
				ll.setPadding(5,5,5,5);//设置四周留白	
				TextView tv=new TextView(Lc_Activity.this);
				tv.setText("备注:"+str);
				tv.setTextSize(17);//设置字体大小
				tv.setTextColor(Lc_Activity.this.getResources().getColor(R.color.blue));//设置字体颜色
				tv.setPadding(5,5,5,5);//设置四周留白
			    tv.setGravity(Gravity.LEFT);
				ll.addView(tv);//添加到LinearLayout中				
				return ll;
			}
  	};
  	lv.setAdapter(baseadapter);
  }
    //支出  收入详细界面的删除按钮
    public void setListenerToButton(Button button,final int id,final String str,int DBcode)
    {
    	dbif=DBcode;
    	button.setOnClickListener
    	(
    		new OnClickListener()
    		{
				@Override
				public void onClick(View v) 
				{
					DBUtil.deleteFromTable(id,str);
					tv0.setText("时间为:"+"                     ");
					tv1.setText("来源为:"+"        ");
					tv2.setText("金额为:"+"        ");  
					Lc_Activity.this.SetListViewData("", lv);
					Toast.makeText(Lc_Activity.this, "删除数据成功", Toast.LENGTH_SHORT).show();
					DBUtil.closeDatabase();
				}
    		}
    	);
    	
    
    }
}