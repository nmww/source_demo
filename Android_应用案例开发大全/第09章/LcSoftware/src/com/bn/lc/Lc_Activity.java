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

enum WhichView{    //����ö��
				LC_VIEW,CATEGORY_VIEW,INCOME_VIEW,SPEND_VIEW,TJ_VIEW,TJSP_VIEW,
				JSQ_VIEW,JSQH_VIEW,INCOMESELECT_VIEW,INSELECT_VIEW,SPENDSELECT_VIEW,
				PERSONDATA_VIEW,SPSELECT_VIEW,INSPSELECT_VIEW,HELP_VIEW,ABOUT_VIEW,
				INLIST_VIEW,SPLIST_VIEW
	          }
public class Lc_Activity extends Activity 
{
   //���������Ի���
   Dialog nameInputdialog;
   Dialog dateInputdialog;
   
   static String text01;//��ȡ��Ϣ
   static String text02;
   static String text03;
   
   Lc_View lcview;  // ������
   WhichView curr;  //ö��
   static int EditTextId;   //ID
     
   static TextView dateEdit01; //���ڿ�
   static TextView dateEdit02; 
   static EditText moneyedit01; //��������
   static EditText moneyedit02; 
   
   static TextView tv0;
   static TextView tv1;
   static TextView tv2;
   static ListView lv;
   
   static String sexDate;
   static String bloodDate;
   static String priovinceDate;
   static String cityDate;
   
   static Spinner sexspinner; //�Ա�
   static Spinner bloodspinner; //Ѫ��������
   static Spinner provincespinner; //ʡ��
   static Spinner cityspinner; //����
   static Spinner yearspinner;//��
      
   static String Idate01;   //����
   static String Idate02;
   static String Isource;   //��Դ
   static String Imoney01;  //���
   static String Imoney02;
   static String Imemo;     //��ע
   static String icategory; //���
   static String Iage;      //����
   static String Iemail;    //����
   static String Ioldpwd="123456";   //������
   static String Inewpwd;   //������
   static String Iokpwd;    //ȷ������
   //static int c=0;  //���������ı�־
   boolean flag=true;
   List<String> data;
   int dbif;
   
    Handler hd=new Handler() //������Ϣ������ת
    {
		  @Override
		  public void handleMessage(Message msg)//��д����  
		  {
			 switch(msg.what)
			 {
			     case 0:
			    	 goToCategoryView();  //���ά������
				    break;
			     case 1:
			    	 goToIncomeView();   //�ճ�����
				    break;
			     case 2:
			    	 goToSpendView();   //�ճ�֧��
				    break;
			     case 3:
			    	 goToIncometjView();  //ͳ��
				    break;
			     case 4:
			    	 goToJsqView();   //������
			        break;
			     case 5:
			    	 goToIncomeSelected(); //�����ѯ
				   break;
			     case 6:
			    	 goToSpendSelectView(); //֧����ѯ
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
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //����ȫ����ʾ
        getWindow().setFlags
        (
        		WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ǿ������
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
 			{//����������
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
			{//���������ѯ����
    			DBUtil.closeDatabase();
				Lc_Activity.this.goToIncomeSelected();
			}
			if(curr==WhichView.SPSELECT_VIEW)
			{//����֧����ѯ����
				DBUtil.closeDatabase();
				Lc_Activity.this.goToSpendSelectView();
			}
			if(curr==WhichView.INSPSELECT_VIEW)
			{
				DBUtil.closeDatabase();
				goToIncometjView();
			}
		    if(curr==WhichView.INLIST_VIEW)   //�����ѯ��ϸ����
		    {
		    	DBUtil.createOrOpenDatabase();
		    	data=DBUtil.queryIncome( "Income",dbif);  //�鿴���ڵı�������
		    	DBUtil.closeDatabase();
		    	
		    	goToselectView(data,1,dbif);  //ȥ�����ѯ����ķ���
		    }
		    if(curr==WhichView.SPLIST_VIEW)  //֧����ѯ��ϸ����
		    {
		    	DBUtil.createOrOpenDatabase();
		    	data=DBUtil.queryIncome( "Spend",dbif);  //�鿴���ڵı�������
		    	DBUtil.closeDatabase();
		    	
		    	goToselectView(data,2,dbif);  //ȥ֧����ѯ����ĵķ���
		    }    
    	}
    	return false;
    }
     
    //ѡ��˵�
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
    
    //�����Ի���
    @Override
    public Dialog onCreateDialog(int id)   
    {    	
        Dialog result=null;
    	switch(id)
    	{
	    	case NAME_INPUT_DIALOG_ID://��������Ի���
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
    
    //ÿ�ε����Ի���ʱ���ص��Զ�̬���¶Ի������ݵķ���
    @Override    
    public void onPrepareDialog(int id, final Dialog dialog)
    {
    	//�����ǵȴ��Ի����򷵻�
    	switch(id)
    	{
 	       //��һ������
    	   case NAME_INPUT_DIALOG_ID://��������Ի���
    		   //ȷ����ť
    		   Button bjhmcok=(Button)nameInputdialog.findViewById(R.id.bjhmcOk);
    		   //ȡ����ť
       		   Button bjhmccancel=(Button)nameInputdialog.findViewById(R.id.bjhmcCancle);
       		   //��ȷ����ť��Ӽ�����
       		   bjhmcok.setOnClickListener
               ( 
    	          new OnClickListener()
    	          {
    	        	@Override
      				public void onClick(View v) 
      				{
      					//��ȡ�Ի���������ݲ���Toast��ʾ
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
  	    						//�رնԻ���
  	        					nameInputdialog.cancel();
  	    					}
  	    					else
  	    					{
  	    						Toast.makeText
  	        					(
  	        						Lc_Activity.this,
  	        						"��������벻��ȷ�����������룡", 
  	        						Toast.LENGTH_SHORT
  	        					).show();   
  	    					}
      					}
      					else
      					{
      						Toast.makeText
          					(
          						Lc_Activity.this,
          						"��������򲻿���Ϊ�գ�����", 
          						Toast.LENGTH_SHORT
          					).show();   
      					} 
      					DBUtil.closeDatabase();
      				}        	  
    	          }
    	        );   
       		    //��ȡ����ť��Ӽ�����
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
    	   //�ڶ�������
    	   case DATE_INPUT_DIALOG_ID:
    		 //�ϵ�һ����ť
    		   ImageButton button1=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton01);
    		   final EditText et1=(EditText)dateInputdialog.findViewById(R.id.EditText01);
    		   this.UpOrDownDateTime(button1, et1, UP_TIME);
    		   //�µ�һ����ť
    		   ImageButton button4=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton04);
    		   this.UpOrDownDateTime(button4, et1, DOWN_TIME);
    		   //�ϵڶ�����ť
    		   ImageButton button2=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton02);
    		   final EditText et2=(EditText)dateInputdialog.findViewById(R.id.EditText02);
    		   this.UpOrDownDateTime(button2, et2, UP_TIME);
    		   //�µڶ�����ť
    		   ImageButton button5=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton05);
    		   this.UpOrDownDateTime(button5, et2, DOWN_TIME);
    		   //�ϵ�������ť
    		   ImageButton button3=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton03);
    		   final EditText et3=(EditText)dateInputdialog.findViewById(R.id.EditText03);
    		   this.UpOrDownDateTime(button3, et3, UP_TIME);
    		   //�µ�������ť
    		   ImageButton button6=(ImageButton)dateInputdialog.findViewById(R.id.ImageButton06); 
    		   this.UpOrDownDateTime(button6, et3, DOWN_TIME);
    		   
    		   //ȷ����ť
    		   Button bsure=(Button)dateInputdialog.findViewById(R.id.Button01);
    		   bsure.setOnClickListener
    		   (
    				new OnClickListener()
    				{
						@Override
						public void onClick(View v) 
						{
							//��
							EditText eyear=(EditText)dateInputdialog.findViewById(R.id.EditText01);
							String syear=eyear.getText().toString().trim();
							//��
							EditText emonth=(EditText)dateInputdialog.findViewById(R.id.EditText02);
							String smonth=emonth.getText().toString().trim();
							//��
							EditText edate=(EditText)dateInputdialog.findViewById(R.id.EditText03);
							String sdate=edate.getText().toString().trim();
					       
							dateverify (syear,smonth,sdate);  //��֤���ڵĺϷ���
						}
    				}
    		   );
    		   Button bcancel=(Button)dateInputdialog.findViewById(R.id.Button02);
    		   this.cancelDialog(bcancel, dateInputdialog);
    	   break;
    	}
    }

    //��ӭ����
    public void goToWelcomeView()
    {
    	MySurfaceView mView=new MySurfaceView(this);
    	setContentView(mView);
    }
    
    //ȥ�����淽�� 
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
    		DBUtil.insertCategory("����", "Scy","˵����Ϣ");
    	}
    	slist=DBUtil.queryCategory("Zcy");
    	if(slist.size()==0)
    	{
    		DBUtil.insertCategory("����", "Zcy","˵����Ϣ");	
    	}
    	DBUtil.closeDatabase();
    	
    	curr=WhichView.LC_VIEW;
    } 
    
    //���ά��
    @SuppressWarnings("unused")
	public void goToCategoryView()   
    {
    	setContentView(R.layout.category); //���ý���
    	
    	DBUtil.createOrOpenDatabase();  //�����ݿ�
    	
    	Button addbutton=(Button)this.findViewById(R.id.Button01);  //�������ť
    	Button delbutton=(Button)this.findViewById(R.id.Button02); //ɾ�����ť
    	Button returnbutton=(Button)this.findViewById(R.id.Button03); //������һ���水ť
		
		final ListView lv=(ListView)this.findViewById(R.id.ListView01);  // �б��
		List<String> result=DBUtil.queryCategory("Scy");
		this.getDataToListView(lv,"Scy");  //����������ʱ����ֵ�б������
		
		final RadioButton rb1=(RadioButton)findViewById(R.id.RadioButton01);  //��ѡ��ť
		final RadioButton rb2=(RadioButton)findViewById(R.id.RadioButton02);
		//��ѡ��ť��ļ���     ListView ��ʾ�������
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
		(//��ѡ��ť��ļ��� 
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
		
		//����������
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
							 //����ͨ����10���ĵ�Constant������===========================================
							 System.out.println(icategory.length());
							 System.out.println(saytext.length());
							 if((icategory.length()!=0)&&(saytext.length()<textlength)&&(saytext.length()>0))
							 {
								 if(flag) 
								 {
									 DBUtil.insertCategory(icategory,"Scy",saytext);    //�������
									 ListView lv=(ListView)findViewById(R.id.ListView01);
									 Lc_Activity.this.getDataToListView(lv,"Scy");
								 }
								 else 
								 {
									 Toast.makeText(Lc_Activity.this, "�������ظ����룡����", Toast.LENGTH_SHORT).show();
								 }
							 }
							 else if(icategory.length()==0)
							 {
								 Toast.makeText(Lc_Activity.this, "����򲻿�Ϊ�գ�����", Toast.LENGTH_SHORT).show();
							 }
							 else if(saytext.length()>textlength)
							 {
								 Toast.makeText(Lc_Activity.this, "˵�������Գ���"+textlength+"���֣�", Toast.LENGTH_SHORT).show();
							 }
							 else
							 {
								 Toast.makeText(Lc_Activity.this, "˵���򲻿���Ϊ�գ�", Toast.LENGTH_SHORT).show();
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
									 DBUtil.insertCategory(icategory,"Zcy",saytext);    //�������
									 ListView lv=(ListView)findViewById(R.id.ListView01);
									 Lc_Activity.this.getDataToListView(lv,"Zcy");
								 }
								 else
								 {
									 Toast.makeText(Lc_Activity.this, "�������ظ����룡����", Toast.LENGTH_SHORT).show();
								 }
							 }
							 else if(icategory.length()==0)
							 {
								 Toast.makeText(Lc_Activity.this, "�������ظ����룡����", Toast.LENGTH_SHORT).show();
							 }
							 else if(saytext.length()>textlength)
							 {
								 Toast.makeText(Lc_Activity.this, "˵�������Գ���"+textlength+"���֣�", Toast.LENGTH_SHORT).show();
							 }
							 else
							 {
								 Toast.makeText(Lc_Activity.this, "˵���򲻿���Ϊ�գ�", Toast.LENGTH_SHORT).show();
							 }			 
						 }
					 }
				 }
		  );
    	
    	 delbutton.setOnClickListener  //ɾ����ť����
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
									 Toast.makeText(Lc_Activity.this, "������ɾ���������������Դ�л����ڸ��", Toast.LENGTH_SHORT).show();
								 }
								 else if(flag0)
								 {
									 Toast.makeText(Lc_Activity.this, "�޷�ɾ������������в����ڸ��", Toast.LENGTH_SHORT).show();
								 }
							 }
							 else
							 {
								 Toast.makeText(Lc_Activity.this, "�������򲻿�Ϊ�գ�����", Toast.LENGTH_SHORT).show();
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
									 Toast.makeText(Lc_Activity.this, "������ɾ���������������Դ�л����ڸ��", Toast.LENGTH_SHORT).show();
								 }
								 else if(flag0)
								 {
									 Toast.makeText(Lc_Activity.this, "�޷�ɾ������������в����ڸ��", Toast.LENGTH_SHORT).show();
								 }
							 }
							 else
							 {
								 Toast.makeText(Lc_Activity.this, "�������򲻿�Ϊ�գ�����", Toast.LENGTH_SHORT).show();
							 }							 
						 }
					 }
				 }
		  );
    	
    	returnButtonClicked(returnbutton);
   	 	curr=WhichView.CATEGORY_VIEW;
    }
   
    //�ճ��������
    public void goToIncomeView() 
    { 
		 setContentView(R.layout.income); //���ò���
		 DBUtil.createOrOpenDatabase();  //�����ݿ�
		 Button addbutton=(Button)this.findViewById(R.id.Button01); //���Ӱ�ť
		 Button returnbutton=(Button)this.findViewById(R.id.Button03); //������һ����
		 
		 dateEdit01=(TextView)findViewById(R.id.EditText01); //���������
		 setEditTextClick(dateEdit01);   //����������������
		 
		 SpinnerListener("Scy"); //���������������
		 addbutton.setOnClickListener  //���Ӱ�ť����
		 (
				 new OnClickListener()
				 {
					 @Override
					 public void onClick(View v)
					 {
						 EditText moneyedit01=(EditText)findViewById(R.id.EditText02); //���
						 EditText memoedit=(EditText)findViewById(R.id.EditText03); //��ע
						 Idate01=dateEdit01.getText().toString().trim(); //��ȡ����
						 Imoney01=moneyedit01.getText().toString().trim(); //��ȡ���
						 Imemo=memoedit.getText().toString().trim(); //��ȡ��ע
						 List<String> slist=DBUtil.queryTable("Income");
						 String smatch="\\d{1,}";
						 boolean flag=Imoney01.matches(smatch);
						 boolean flag1=Lc_Activity.this.sameOrDifferent(Idate01, Isource, Imoney01, slist);
						 if(flag&&flag1&&(Idate01.length()==10))
						 {
							 DBUtil.insert("Income");  //���ò������ݷ��� 
							 Toast.makeText(Lc_Activity.this, 
									        "�������������:����:"+Idate01+"���:"+Imoney01+"��ע:"+Imemo+"!!!", 
									        Toast.LENGTH_LONG
									        ).show();
						 }
						 else if(!flag)
						 {
							 Toast.makeText(Lc_Activity.this, "������Ľ��Ϸ������������룡�磺10000", Toast.LENGTH_SHORT).show();
						 }
						 else if(Idate01.length()!=10)
						 {
							Toast.makeText(Lc_Activity.this, "����ѡ�����ڣ�", Toast.LENGTH_SHORT).show(); 
						 } 
						 else
						 {
							 Toast.makeText(Lc_Activity.this, "�������ظ��Ĳ����¼��", Toast.LENGTH_SHORT).show();
						 }
					 }
				 }
		 );
		 returnButtonClicked(returnbutton);
		 curr=WhichView.INCOME_VIEW;
    }
    
    //�ճ�֧������
    public void goToSpendView() 
    {
    	 setContentView(R.layout.spend); //���ò���
    	 
    	 DBUtil.createOrOpenDatabase();  //�����ݿ�
		 
    	 Button addbutton=(Button)this.findViewById(R.id.Button01); //���Ӱ�ť
    	 Button returnbutton=(Button)this.findViewById(R.id.Button03);
		  
    	 dateEdit01=(TextView)findViewById(R.id.EditText01); //���������
		  
		 setEditTextClick(dateEdit01);   //����������������
		 SpinnerListener("Zcy"); //���������������
		 	 		 
		 addbutton.setOnClickListener  //���Ӱ�ť����
		 (
				 new OnClickListener()
				 {
					 @Override
					 public void onClick(View v)
					 {
						 EditText moneyedit01=(EditText)findViewById(R.id.EditText02);
						 EditText memoedit=(EditText)findViewById(R.id.EditText03);
						 
						 Idate01=dateEdit01.getText().toString().trim(); //��ȡ����
						 Imoney01=moneyedit01.getText().toString().trim(); //��ȡ���
						 Imemo=memoedit.getText().toString().trim(); //��ȡ��ע
						 List<String> slist=DBUtil.queryTable("Spend");
						 String smatch="\\d{1,}";
						 boolean flag=Imoney01.matches(smatch);
						 boolean flag1=Lc_Activity.this.sameOrDifferent(Idate01, Isource, Imoney01, slist);
						 if(flag&&flag1&&(Idate01.length()==10))
						 {
							 DBUtil.insert("Spend");  //���ò������ݷ��� 
							 Toast.makeText(Lc_Activity.this, 
									        "�������������:����:"+Idate01+"���:"+Imoney01+"��ע:"+Imemo+"!!!", 
									        Toast.LENGTH_LONG
									        ).show();
						 }
						 else if(Idate01.length()!=10)
						 {
							Toast.makeText(Lc_Activity.this, "����ѡ�����ڣ�", Toast.LENGTH_SHORT).show(); 
						 } 
						 else if(!flag)
						 {
							 Toast.makeText(Lc_Activity.this, "������Ľ��Ϸ������������룡�磺10000", Toast.LENGTH_SHORT).show();
						 }
						 else
						 {
							 Toast.makeText(Lc_Activity.this, "�������ظ��Ĳ����¼��", Toast.LENGTH_SHORT).show();
						 }
					 }
				 }
		 );
		 returnButtonClicked(returnbutton);
		 curr=WhichView.SPEND_VIEW;
    }
    
    //����ͳ�ƽ���
    public void goToIncometjView()            
    {
    	 setContentView(R.layout.tj);
    	 
    	 DBUtil.createOrOpenDatabase();  //�����ݿ�
    	 
       	 dateEdit01=(TextView)findViewById(R.id.EditText01);  
       	 this.setEditTextClick(dateEdit01);  //�������ڶԻ���
       	 
       	 dateEdit02=(TextView)findViewById(R.id.EditText02);
       	 this.setEditTextClick(dateEdit02);  //�������ڶԻ���
    	 
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
						    	Toast.makeText(Lc_Activity.this, "�����������ڣ�", Toast.LENGTH_SHORT).show();
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
						    	Toast.makeText(Lc_Activity.this, "�����������ڣ�", Toast.LENGTH_SHORT).show();
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
    
    //֧��ͳ�ƽ���
    public void goToTjSpView()  
    {
    	setContentView(R.layout.tjsp);
    	DBUtil.createOrOpenDatabase();  //�����ݿ�
    	SpinnerListener("Zcy");
    	addListener(1);
    	//===============���ڵ�����======================
   	 dateEdit01=(TextView)findViewById(R.id.EditText01);  
   	 this.setEditTextClick(dateEdit01);  //�������ڶԻ���
   	 
   	 dateEdit02=(TextView)findViewById(R.id.EditText02);
   	 this.setEditTextClick(dateEdit02);  //�������ڶԻ���
   	//===============���ڵ�����======================
    	
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
						    	Toast.makeText(Lc_Activity.this, "�����������ڣ�", Toast.LENGTH_SHORT).show();
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
						    	Toast.makeText(Lc_Activity.this, "�����������ڣ�", Toast.LENGTH_SHORT).show();
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
    
    //���ڼ���������
    public void goToJsqView()
    {
    	 setContentView(R.layout.jsq);
		 yearspinner=(Spinner)findViewById(R.id.Spinner01);
	     Lc_Activity.this.PersonSpinner(yearspinner,Constant.years.length,Constant.years);
    	 addListener(0);
		 curr=WhichView.JSQH_VIEW;
    }
    
    //���ڼ���������
    public void goToOthJsqView()
    {
    	setContentView(R.layout.jsqh);
    	addListener(0);
    	curr=WhichView.JSQ_VIEW;
    }
    
    //�����ѯ����
    public void goToIncomeSelected()
    {
    	 setContentView(R.layout.incomeselect_view);  //��������
    	 
    	 DBUtil.createOrOpenDatabase();  //�����ݿ�
    	 
    	//===============���ڵ�����======================
    	 dateEdit01=(TextView)findViewById(R.id.EditText01);  
    	 this.setEditTextClick(dateEdit01);  //�������ڶԻ���
    	 
    	 dateEdit02=(TextView)findViewById(R.id.EditText02);
    	 this.setEditTextClick(dateEdit02);  //�������ڶԻ���
    	//===============���ڵ�����======================
    	 Button returnbutton=(Button)this.findViewById(R.id.Button02); //���ذ�ť
    		 
    	 moneyedit01=(EditText)findViewById(R.id.EditText03);  //��������
    	 moneyedit02=(EditText)findViewById(R.id.EditText04);
    	 
		 SpinnerListener("Scy"); //���������������
		 
		 final Button selectBut=(Button)findViewById(R.id.Button01);  //��ѯ���ݰ�ť����
		 selectBut.setOnClickListener    
	     (
	        	new OnClickListener()
	        	{
					@Override
				    public void onClick(View v) 
					{	
						selected(selectBut,"Income",1);  //  1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
					}
	        	}               
	      );  

	     returnButtonClicked(returnbutton);
		 curr=WhichView.INCOMESELECT_VIEW;
    }
    
    //֧����ѯ����
    public void goToSpendSelectView()  
    {
    	 setContentView(R.layout.spendselect_view);
    	 
    	 DBUtil.createOrOpenDatabase();  //�����ݿ�
    	 
     	//===============���ڵ�����======================
    	 dateEdit01=(TextView)findViewById(R.id.EditText01);  
     	 this.setEditTextClick(dateEdit01);
     	 
     	 dateEdit02=(TextView)findViewById(R.id.EditText02);
     	 this.setEditTextClick(dateEdit02);
     	//===============���ڵ�����======================
     	Button returnbutton=(Button)this.findViewById(R.id.Button02); //���ذ�ť
     		 
     	moneyedit01=(EditText)findViewById(R.id.EditText03);  //��������
     	moneyedit02=(EditText)findViewById(R.id.EditText04);
     	 
 		 SpinnerListener("Zcy"); //���������������
 		
 		final Button selectBut=(Button)findViewById(R.id.Button01);  //��ѯ���ݰ�ť����
 		selectBut.setOnClickListener    
 	     (
 	        	new OnClickListener()
 	        	{
 					@Override  
 				    public void onClick(View v)    
 					{ 
 						selected(selectBut,"Spend",2);  //  1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
 					}
 	        	}
 	      );

 	     returnButtonClicked(returnbutton);
		 curr=WhichView.SPENDSELECT_VIEW;
    }
    
    //������Ϣ����
    public void goToPersonDataView()
    {
    	setContentView(R.layout.persondata);
    	DBUtil.createOrOpenDatabase();
    	sexspinner=(Spinner)findViewById(R.id.Spinner01);    //�Ա������б�
    	PersonSpinner(sexspinner,sexIds.length,sexIds);
    	
    	bloodspinner=(Spinner)findViewById(R.id.Spinner02);  //Ѫ��������
    	PersonSpinner(bloodspinner,bloodIds.length,bloodIds);
    
    	provincespinner=(Spinner)findViewById(R.id.Spinner03);  //ʡ��������
    	PersonSpinner(provincespinner,provinceIds.length,provinceIds);
    	
    	cityspinner=(Spinner)findViewById(R.id.Spinner04); //����������
    	PersonSpinner(cityspinner,cityIds.length,cityIds);
    	
    	dateEdit01=(TextView)findViewById(R.id.EditText01);
		Lc_Activity.this.setEditTextClick(dateEdit01); 

    	Button addbutton=(Button)findViewById(R.id.Button01); //���¸�����Ϣ��ť
    	addbutton.setOnClickListener
    	(
    			new OnClickListener()
    			{
					@Override
					public void onClick(View v) 
					{
						//=================�ı���======================
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
				    	//=================�ı���======================
				    	String sage="[1-9][0-9]";
				    	String semail="[a-zA-Z0-9]{8,15}@[a-zA-Z0-9]{3}.com";
				    	String spwd="[a-zA-Z0-9]{6,13}";
				    	if((Iage.matches(sage))&&(Iemail.matches(semail))
				    		&&(Inewpwd.matches(spwd))&&(Ioldpwd.matches(spwd))
				    		&&(Iokpwd.matches(spwd))&&(Inewpwd.length()==Iokpwd.length()))
				    	{
				    		//���ĸ�������
				    		String result=DBUtil.getPassword();
				    		if(result.equals(Ioldpwd)) 
				    		{
				    			DBUtil.UpdatePersonDate();
				    			Toast.makeText(Lc_Activity.this, "��������ɹ���", Toast.LENGTH_SHORT).show();
				    		}
				    		else
				    		{
				    			Toast.makeText(Lc_Activity.this, "�������ʧ�ܣ��������ԭ���벻�ԣ�", Toast.LENGTH_SHORT).show();
				    		}
				    	}
				    	else if(!Iage.matches(sage))
				    	{
				    		Toast.makeText(Lc_Activity.this, "����ķ�Χ��10-99֮��", Toast.LENGTH_SHORT).show();
				    	}
				    	else if(!Iemail.matches(semail))
				    	{
				    		Toast.makeText(Lc_Activity.this, "�����@ǰ��ı��������ֻ���ĸ�ҳ�����8-15֮��", Toast.LENGTH_SHORT).show();
				    	}
				    	else 
				    	{
				    		Toast.makeText(Lc_Activity.this, "���������䳤����6-13֮��", Toast.LENGTH_SHORT).show();
				    	}
				    	
					}
    			}
        );
    	
    	Button returnbutton=(Button)findViewById(R.id.Button02);  //���� ��ť����
    	returnButtonClicked(returnbutton);
    	curr=WhichView.PERSONDATA_VIEW;
    }

    //�����ѯ��ϸ��Ϣ����
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
    			    	data=DBUtil.queryIncome( "Income",dbif);  //�鿴���ڵı�������
    			    	DBUtil.closeDatabase();
    					
    					goToselectView(data,1,dbif);
    					
    				}
    			}
    	);
    	
    	curr=WhichView.INLIST_VIEW;
    }
    
    //֧�����ݲ�ѯ��ϸ����
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
    			    	data=DBUtil.queryIncome( "Spend",dbif);  //�鿴���ڵı�������
    			    	DBUtil.closeDatabase();
    					
    					goToselectView(data,2,dbif);
    				}
    			}
    	);
    	
    	curr=WhichView.SPLIST_VIEW;
    }
    
    //��������
    public void goToHelpView()
    {
    	setContentView(R.layout.helpview);
    	curr=WhichView.HELP_VIEW;
    }
    
    //���ڽ���
    public void goToAboutView()
    {
    	setContentView(R.layout.aboutview);
    	curr=WhichView.ABOUT_VIEW;
    }
        
    //TextView�ļ���
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
					//=================�������ڵ�����Ϊϵͳʱ��==========================
					EditText tempyear=(EditText)dateInputdialog.findViewById(R.id.EditText01);  //��
					EditText tempmonth=(EditText)dateInputdialog.findViewById(R.id.EditText02); //��
					EditText tempdate=(EditText)dateInputdialog.findViewById(R.id.EditText03); //��
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
    
    //���ڶԻ��������ϻ����°�ť
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
    
    //���ڵ���֤����
    public  void dateverify (String syear,String smonth,String sdate)  
    {
		//�ܵ�
		String str=DateUtil.getdate(syear, smonth, sdate, YEAR_INTERVAL);
		//����Toast
		if(str==null)
		{
			switch(DateUtil.ERROR_MSG_INT)
			{
			     case 0:
			    	 Toast.makeText
			    	 (
			    			 Lc_Activity.this, 
			    			 "���̫��Զ", 
			    			 Toast.LENGTH_LONG
			    	  ).show(); 
			     break;
			     case 1:
			    	 Toast.makeText
			    	 (
			    			 Lc_Activity.this, 
			    			 "���µ���������", 
			    			 Toast.LENGTH_LONG
			    	  ).show();
			     break;
			     case 2:
			    	 Toast.makeText
			    	 (
			    			 Lc_Activity.this, 
			    			 "����", 
			    			 Toast.LENGTH_LONG
			    	 ).show();
			     break;
			     case 3:
			    	 Toast.makeText
			    	 (
			    			 Lc_Activity.this, 
			    			 "��ݻ��·ݻ����ڸ�ʽ���ԣ�������������", 
			    			 Toast.LENGTH_LONG
			    	  ).show();
			     break;
			}
		}
		//�˴��Ķ�
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

    //�˳��Ի���
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
    
    //������Ϣ�������б�������
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
				ll.setOrientation(LinearLayout.VERTICAL);		//���ó���	
				
				TextView tv=new TextView(Lc_Activity.this);
				tv.setText(msgIds[arg0]);   //��������
				tv.setTextSize(15);   //���������С
				tv.setTextColor(R.color.black);//����������ɫ
				ll.addView(tv);   //��ӵ�LinearLayout��
				
				return ll;
			}  
        };
        setspinner.setAdapter(ba);   //ΪSpinner��������������
        setspinner.setOnItemSelectedListener   //����ѡ��ѡ�еļ�����
        (
           new OnItemSelectedListener()
           {
        	    @Override
   				public void onItemSelected(AdapterView<?> arg0, View arg1,
   					int arg2, long arg3) 
        	    {		
	   				LinearLayout ll=(LinearLayout)arg1;//��ȡ��ǰѡ��ѡ���Ӧ��LinearLayout
	   				TextView tvn=(TextView)ll.getChildAt(0);//��ȡ���е�TextView 

	   				if(setspinner==sexspinner)      //�ж��Ƿ��������Ա�
	   				{
	   					sexDate=tvn.getText().toString();
	   				}
	   				if(setspinner==bloodspinner)    //�ж��Ƿ�������Ѫ��
	   				{
	   					bloodDate=tvn.getText().toString();
	   				}
	   				if(setspinner==provincespinner) //�ж��Ƿ�������ʡ��
	   				{
	   					priovinceDate=tvn.getText().toString();
	   				}
	   				if(setspinner==cityspinner)     //�ж��Ƿ������ǳ���
	   				{
	   					cityDate=tvn.getText().toString();
	   				}
	   				if(setspinner==yearspinner)     //�ж��Ƿ����������ʵ����
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
    
    //�������������
    public void SpinnerListener(final String tableName) 
    {
    	DBUtil.queryCategory(tableName);    //��ѯ�������
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
				ll.setOrientation(LinearLayout.VERTICAL);		//���ó���	
				
				TextView tv=new TextView(Lc_Activity.this);
				tv.setText(result.get(2*arg0));
				tv.setTextSize(18);//���������С
				tv.setTextColor(R.color.black);//����������ɫ
				ll.addView(tv);//��ӵ�LinearLayout��
				 
				return ll;
			}        	
        };
        
        setspinner.setAdapter(ba);//ΪSpinner��������������
        
        setspinner.setOnItemSelectedListener   //����ѡ��ѡ�еļ�����
        (
           new OnItemSelectedListener()
           {
        	    @Override
   				public void onItemSelected(AdapterView<?> arg0, View arg1,
   					int arg2, long arg3) 
        	    {
   				
	   				LinearLayout ll=(LinearLayout)arg1;//��ȡ��ǰѡ��ѡ���Ӧ��LinearLayout
	   				TextView tvn=(TextView)ll.getChildAt(0);//��ȡ���е�TextView 
	   		       
	   				Isource=(String) tvn.getText();
        	    }
        	    
        	    @Override
        	    public void onNothingSelected(AdapterView<?> arg0) { }        	   
           }
        );
    }
   
  //���� ֧����ѯ��ť�ļ��������������Բ�ѯ����������Ӧ���ж�
    //���� ֧�� �������ݿ��ѯ�ķ���  
    public void selected(Button selectBut,final String tableName,final int state)
    {                                  // state 1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
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
						{//�ж� checkBox1 checkBox2 checkBox3  �Ƿ�ѡ�е����
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
									if(state==1)  //  1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
									{             // 1 ����Ҫȥ���ݿ��ѯ�ĵڼ�������
										goToselectView(IncomeDate,1,1); 
									}
									if(state==2)
									{
										goToselectView(IncomeDate,2,1);
									}
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "��һ������Ľ�����ȵڶ�������Ľ��С��", Toast.LENGTH_SHORT).show();
								}
							}
							else if((Idate01.length()!=10)||(Idate02.length()!=10))
							{
								Toast.makeText(Lc_Activity.this, "�����������ڣ�", Toast.LENGTH_SHORT).show();
							}
							else
							{
								Toast.makeText(Lc_Activity.this, "������Ľ��Ϸ������������룡�磺10000", Toast.LENGTH_SHORT).show();
							}
						}
						else if(dataCheck01.isChecked()&&dataCheck02.isChecked())
						{//�ж� checkBox1 checkBox2 �Ƿ�ѡ�е����
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
										if(state==1)  //  1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
										{             // 2 ����Ҫȥ���ݿ��ѯ�ĵڼ�������
											goToselectView(IncomeDate,1,2); 
										}
										if(state==2)
										{
											goToselectView(IncomeDate,2,2);
										}
									}
									else
									{
										Toast.makeText(Lc_Activity.this, "��һ������Ľ�����ȵڶ�������Ľ��С��", Toast.LENGTH_SHORT).show();
									}
								}
								else if((Idate01.length()!=10)||(Idate02.length()!=10))
								{
									Toast.makeText(Lc_Activity.this, "�����������ڣ�", Toast.LENGTH_SHORT).show();
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "������Ľ��Ϸ������������룡�磺10000", Toast.LENGTH_SHORT).show();
								}
						}
						else if(dataCheck01.isChecked()&&dataCheck03.isChecked())
						{//�ж� checkBox1 checkBox3  �Ƿ�ѡ�е����
								Idate01=dateEdit01.getText().toString();
								Idate02=dateEdit02.getText().toString();
								IncomeDate=DBUtil.queryIncome(tableName,3);
								if((Idate01.length()==10)&&(Idate02.length()==10))
								{
									if(state==1)   //  1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
									{        // 3����Ҫȥ���ݿ��ѯ�ĵڼ�������
										goToselectView(IncomeDate,1,3); 
									}
									if(state==2)
									{
										goToselectView(IncomeDate,2,3);
									}
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "�����������ڣ�", Toast.LENGTH_SHORT).show();
								}
						}
						else if(dataCheck02.isChecked()&&dataCheck03.isChecked())
						{//�ж� checkBox2 checkBox3  �Ƿ�ѡ�е����
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
										if(state==1)  //  1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
										{// 4����Ҫȥ���ݿ��ѯ�ĵڼ�������
											goToselectView(IncomeDate,1,4); 
										}
										if(state==2)
										{
											goToselectView(IncomeDate,2,4);
										}
									}
									else
									{
										Toast.makeText(Lc_Activity.this, "��һ������Ľ�����ȵڶ�������Ľ��С��", Toast.LENGTH_SHORT).show();
									}
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "������Ľ��Ϸ������������룡�磺10000", Toast.LENGTH_SHORT).show();
								}
						}
						else if(dataCheck01.isChecked())
						{// checkBox1 �Ƿ�ѡ�е���� 
								Idate01=dateEdit01.getText().toString();
								Idate02=dateEdit02.getText().toString();
								IncomeDate=DBUtil.queryIncome(tableName,5);
								if((Idate01.length()==10)&&(Idate02.length()==10))
								{
									if(state==1)   //  1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
									{// 5 ����Ҫȥ���ݿ��ѯ�ĵڼ�������
										goToselectView(IncomeDate,1,5); 
									}
									if(state==2)
									{
										goToselectView(IncomeDate,2,5);
									}
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "�����������ڣ�", Toast.LENGTH_SHORT).show();
								}
						}
						else if(dataCheck02.isChecked())
						{	// checkBox2  �Ƿ�ѡ�е���� 
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
										if(state==1)  //  1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
										{// 6 ����Ҫȥ���ݿ��ѯ�ĵڼ�������
											goToselectView(IncomeDate,1,6); 
										}
										if(state==2)
										{
											goToselectView(IncomeDate,2,6);
										}
									}
									else
									{
										Toast.makeText(Lc_Activity.this, "��һ������Ľ�����ȵڶ�������Ľ��С��", Toast.LENGTH_SHORT).show();
									}
								}
								else
								{
									Toast.makeText(Lc_Activity.this, "������Ľ��Ϸ������������룡�磺10000", Toast.LENGTH_SHORT).show();
								}
						}
						else if(dataCheck03.isChecked())
						{// checkBox3  �Ƿ�ѡ�е���� 
								IncomeDate=DBUtil.queryIncome(tableName,7);
								if(state==1)  //  1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
								{// 7 ����Ҫȥ���ݿ��ѯ�ĵڼ�������
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
    
    //���� ֧����ѯ����ʾ���ݵĽ���
    public void goToselectView(List<String> IncomeDate,int state,int dbif)
    {
    	
    		if(state==1)  //  1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
        	{
        		setContentView(R.layout.inselect_view);
            	
            	ListView lv=(ListView)findViewById(R.id.ListView01);
            	//Ϊ�����ѯ���ݽ����������ݣ���gridView ������
            	SelectBaseAdapter(lv,IncomeDate,1,dbif);
            	//���ذ�ť������
            	Button returnbutton=(Button)findViewById(R.id.Button02);
            	returnbutton.setOnClickListener
            	(
            			new OnClickListener()
            			{
        					@Override
        					public void onClick(View v) 
        					{
        						DBUtil.closeDatabase();
        	    				goToIncomeSelected();  //���������ѯ������
        					}
            			}
            	);
            	
            	curr=WhichView.INSELECT_VIEW;
        	}
        	if(state==2)  //  1 ȥ��ʾ�������ݵĽ���   2ȥ��ʾ֧�����ݵĽ���
        	{
        		setContentView(R.layout.spselect_view);
            	
            	ListView lv=(ListView)findViewById(R.id.ListView01);
            	//Ϊ֧����ѯ���ݽ����������ݣ���gridView ������
            	SelectBaseAdapter(lv,IncomeDate,2,dbif);
            	//���ذ�ť������
            	Button returnbutton=(Button)findViewById(R.id.Button02);
            	returnbutton.setOnClickListener
            	(
            			new OnClickListener()
            			{
        					@Override
        					public void onClick(View v) 
        					{
        						DBUtil.closeDatabase();
        	    				goToSpendSelectView();   //����֧����ѯ������
        	    				
        					}
            			}
            	);
            	
            	curr=WhichView.SPSELECT_VIEW;
        	}
    }
    
    //����������ʱ����ֵ�б������ ListView �������÷���
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
				ll.setOrientation(LinearLayout.HORIZONTAL);		//���ó���	
				ll.setPadding(5,5,5,5);//������������	
				TextView tv=new TextView(Lc_Activity.this);
				tv.setText(result.get(2*arg0));
				tv.setTextSize(17);//���������С
				tv.setTextColor(Lc_Activity.this.getResources().getColor(R.color.blue));//����������ɫ
				tv.setPadding(5,5,5,5);//������������
			    tv.setGravity(Gravity.LEFT);
				ll.addView(tv);//��ӵ�LinearLayout��				
				//Textview============================================================================
				LinearLayout llt=new LinearLayout(Lc_Activity.this);
				llt.setOrientation(LinearLayout.VERTICAL);
				TextView tv1=new TextView(Lc_Activity.this);
				tv1.setText(result.get((2*arg0)+1));
				String str=result.get((2*arg0)+1);
				final int c=80-str.length();
				tv1.setTextSize(17);//���������С
				tv1.setTextColor(Lc_Activity.this.getResources().getColor(R.color.blue));//����������ɫ
				tv1.setPadding(c,5,5,5);//������������
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
    			{//��дѡ������¼��Ĵ�����
    				EditText et2=(EditText)findViewById(R.id.EditText01);
    				LinearLayout ll=(LinearLayout)arg1;
    				TextView tvn=(TextView)ll.getChildAt(0);
    				et2.setText(tvn.getText().toString());
    			}        	   
    	    }	
    	);
    }  
      
    //GridView������
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
        gv.setAdapter(sca);//ΪGridView��������������
        gv.setOnItemClickListener
        (
        	new OnItemClickListener()
        	{
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						                int arg2, long arg3) 
				{
					LinearLayout ll=(LinearLayout)arg1;
					TextView tvn1=(TextView)ll.getChildAt(0);//��ȡ���е�TextView 
					TextView tvn2=(TextView)ll.getChildAt(1);//��ȡ���е�TextView 
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
    
    //GridView�洢����
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
    
    //����������  ͳ�ƽ��� �����¼���ʵ�ַ���
    public void addListener(final int state)
    {
       //��ѡ��ť
       RadioButton rb1=(RadioButton)findViewById(R.id.RadioButton01);
  	   RadioButton rb2=(RadioButton)findViewById(R.id.RadioButton02);
  	   //��ť
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
  						Toast.makeText(Lc_Activity.this, "ͳ�ƽ���", Toast.LENGTH_LONG).show();
  					}
  				}
  	   	   }
  	   	);
  	  returnView(button2);
     } 
    
    //������ý�Ǯ�ļ���
    public void getMoney()
    {
     	float[] result=new float[2];
      	//�õ�ÿ��EditText������
      	EditText et1=(EditText)findViewById(R.id.EditText01);
      	EditText et2=(EditText)findViewById(R.id.EditText02);
      	EditText et3=(EditText)findViewById(R.id.EditText03);
      	EditText et4=(EditText)findViewById(R.id.EditText04);
      	EditText et5=(EditText)findViewById(R.id.EditText05);
      	String str1=et1.getText().toString().trim();//��һ���ı���
  	   	String str2=et2.getText().toString().trim();//�ڶ����ı���
  	   	String str3=et3.getText().toString().trim();//�������ı���
  	   	//�ַ���ƥ��
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
      		Toast.makeText(this, "����������ֲ��Ϸ��������������룡��10000", Toast.LENGTH_LONG).show();
      	}
    }
    
    //�ж�����Ƿ��Ѿ�����
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
    
    //�жϲ����¼������ͬ��
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
    
    //���ذ�ť��ʵ��  ���ر����ݿ� 
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
    
    //�����ݿ�ķ��ذ�ť�ļ���
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

    //Ϊ �����ѯ����֧����ѯ ����ϸ��Ϣ����   ������ϸ��Ϣ
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
    	tv0.setText("ʱ��Ϊ:"+str0[0]);
    	tv1.setText("��ԴΪ:"+str0[1]);
    	tv2.setText("���Ϊ:"+str0[2]);
    	
    	String str=slist.get(4);    
    	SetListViewData(str,lv); 
    	
    	//ɾ����¼
    	str=slist.get(0);
    	final int id=Integer.parseInt(str);
    	Button button01=(Button)findViewById(R.id.Button01);
    	setListenerToButton(button01,id,tableName,dbif);
    }
    
    //Ϊ ���� ֧����ѯ���ݵ���ϸ��Ϣ����� ListView��������     ��ע����ϸ����
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
				ll.setOrientation(LinearLayout.HORIZONTAL);		//���ó���	
				ll.setPadding(5,5,5,5);//������������	
				TextView tv=new TextView(Lc_Activity.this);
				tv.setText("��ע:"+str);
				tv.setTextSize(17);//���������С
				tv.setTextColor(Lc_Activity.this.getResources().getColor(R.color.blue));//����������ɫ
				tv.setPadding(5,5,5,5);//������������
			    tv.setGravity(Gravity.LEFT);
				ll.addView(tv);//��ӵ�LinearLayout��				
				return ll;
			}
  	};
  	lv.setAdapter(baseadapter);
  }
    //֧��  ������ϸ�����ɾ����ť
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
					tv0.setText("ʱ��Ϊ:"+"                     ");
					tv1.setText("��ԴΪ:"+"        ");
					tv2.setText("���Ϊ:"+"        ");  
					Lc_Activity.this.SetListViewData("", lv);
					Toast.makeText(Lc_Activity.this, "ɾ�����ݳɹ�", Toast.LENGTH_SHORT).show();
					DBUtil.closeDatabase();
				}
    		}
    	);
    	
    
    }
}