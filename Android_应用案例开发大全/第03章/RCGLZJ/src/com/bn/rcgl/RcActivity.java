package com.bn.rcgl;

import java.util.ArrayList;
import java.util.Calendar;
import com.bn.rcgl.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import static com.bn.rcgl.Constant.*;
import static com.bn.rcgl.DBUtil.*;

public class RcActivity extends Activity 
{
	String[] defultType=new String[]{"����","����","����"};//�������������ɾ����Ĭ������
	Dialog dialogSetRange;//�ճ̲���ʱ����������ʼ��Χ�ĶԻ���
	Dialog dialogSetDatetime;//�½����޸��ճ�ʱ�������ں�ʱ��ĶԻ���
	Dialog dialogSchDelConfirm;//ɾ���ճ�ʱ��ȷ�϶Ի���
	Dialog dialogCheck;//�������в鿴�ճ���ϸ���ݵĶԻ���
	Dialog dialogAllDelConfirm;//ɾ��ȫ�������ճ�ʱ��ȷ�϶Ի���
	Dialog dialogAbout;//���ڶԻ���
	static ArrayList<String> alType=new ArrayList<String>();//�洢�����ճ����͵�arraylist
	static ArrayList<Schedule> alSch=new ArrayList<Schedule>();//�洢����schedule�����ArrayList
	Schedule schTemp;//��ʱ��schedule
	ArrayList<Boolean> alSelectedType=new ArrayList<Boolean>();//��¼���ҽ���������ǰ��checkbox״̬��
	String rangeFrom=getNowDateString();//�����ճ�ʱ���õ���ʼ���ڣ�Ĭ�ϵ�ǰ����
	String rangeTo=rangeFrom;//�����ճ�ʱ���õ���ֹ���ڣ�Ĭ�ϵ�ǰ����
	Layout curr=null;//��¼��ǰ�����ö������
	WhoCall wcSetTimeOrAlarm;//�����жϵ���ʱ�����ڶԻ���İ�ť������ʱ�仹����������,�Ա���ĶԻ����е�һЩ�ؼ�������Ϊvisible����gone
	WhoCall wcNewOrEdit;//�����жϵ����ճ̱༭��������½��ճ̰�ť�������޸��ճ̰�ť���Ա����ö�Ӧ�Ľ������
	int sel=0;
	/*��ʱ��¼�½��ճ̽����������spinner��position����Ϊ����ʱ��ĶԻ���cancel��
	     �ص��½��ճ̽���ʱ��ˢ�����пؼ���spinner����ѡ�е���ĿҲ��ص�Ĭ��*/ 
	Handler hd=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case 0:
					gotoMain();
				break;
			}
		}
	};
	@Override    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//�ޱ���
        goToWelcomeView();
    }
    //��ӭ����
    public void goToWelcomeView()
    {
    	MySurfaceView mview=new MySurfaceView(this);
    	getWindow().setFlags//ȫ��
    	(
    			WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FULLSCREEN
    	);
    	setContentView(mview);
    	curr=Layout.WELCOME_VIEW;
    }
    //===================================������start===========================================
    public void gotoMain()//��ʼ��������
    {
    	getWindow().setFlags
    	(//��ȫ��
    			WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
    	);	
    	setContentView(R.layout.main);
    	curr=Layout.MAIN;
    	sel=0;
    	
    	final ArrayList<Boolean> alIsSelected=new ArrayList<Boolean>();//��¼ListView������ѡ���˵ı�־λ
    	
    	final ImageButton bEdit=(ImageButton)findViewById(R.id.ibmainEdit);//�޸��ճ̰�ť
    	final ImageButton bCheck=(ImageButton)findViewById(R.id.ibmainCheck);//�鿴�ճ���ϸ���ݵİ�ť
    	final ImageButton bDel=(ImageButton)findViewById(R.id.ibmainDel);//ɾ����ǰѡ���ճ̵İ�ť
    	ImageButton bNew=(ImageButton)findViewById(R.id.ibmainNew);//�½��ճ̰�ť
    	ImageButton bDelAll=(ImageButton)findViewById(R.id.ibmainDelAll);//ɾ�����й����ճ̰�ť
    	ImageButton bSearch=(ImageButton)findViewById(R.id.ibmainSearch);//�����ճ̰�ť
    	final ListView lv=(ListView)findViewById(R.id.lvmainSchedule);//�ճ��б�
        
        bCheck.setEnabled(false);//��������ť�ֱ�Ϊ��������ճ̲鿴���ճ��޸ġ��ճ�ɾ��,
    	bEdit.setEnabled(false);//Ĭ����Ϊ������״̬
    	bDel.setEnabled(false);
        
    	alSch.clear();//�����ݿ��ȡ֮ǰ��մ洢�ճ̵�arraylist
		loadSchedule(this);//�����ݿ��ж�ȡ�ճ�
		loadType(this);//�����ݿ��ж�ȡ����
		
        if(alSch.size()==0)//���û���κ��ճ̣���ɾ��ȫ�������ճ̰�ť��Ϊ����
        {
        	bDelAll.setEnabled(false);
        }
        else
        {
        	bDelAll.setEnabled(true);
        }
        
        alIsSelected.clear();
    	
        for(int i=0;i<alSch.size();i++)//ȫ������Ϊfalse����û��һ��ѡ��
    	{
    		alIsSelected.add(false);
    	}
        
        //������ListView����
        lv.setAdapter
        (
        		new BaseAdapter()
        		{
					@Override
					public int getCount() 
					{
						return alSch.size();
					}
					@Override
					public Object getItem(int position) 
					{
						return alSch.get(position);
					}
					@Override
					public long getItemId(int position) 
					{
						return 0;
					}
					@Override
					public View getView(int position, View convertView, ViewGroup parent) 
					{
						LinearLayout ll=new LinearLayout(RcActivity.this);
						ll.setOrientation(LinearLayout.VERTICAL);
						ll.setPadding(5, 5, 5, 5);
						
						LinearLayout llUp=new LinearLayout(RcActivity.this);
						llUp.setOrientation(LinearLayout.HORIZONTAL);
						LinearLayout llDown=new LinearLayout(RcActivity.this);
						llDown.setOrientation(LinearLayout.HORIZONTAL);
						
						//ListView������TextView
						TextView tvDate=new TextView(RcActivity.this);
						tvDate.setText(alSch.get(position).getDate1()+"   ");
						tvDate.setTextSize(17);
						tvDate.setTextColor(Color.parseColor("#129666"));
						llUp.addView(tvDate);
						
						//ListViewʱ��TextView
						TextView tvTime=new TextView(RcActivity.this);
						tvTime.setText(alSch.get(position).timeForListView());
						tvTime.setTextSize(17);
						tvTime.setTextColor(Color.parseColor("#925301"));
						llUp.addView(tvTime);
						
						//���ճ��ѹ��ڣ������ں�ʱ����ɫ������ɫ����Ϊ���ڵ���ɫ
						if(alSch.get(position).isPassed())
						{
							tvDate.setTextColor(getResources().getColor(R.color.passedschtext));
							tvTime.setTextColor(getResources().getColor(R.color.passedschtext));
							ll.setBackgroundColor(getResources().getColor(R.color.passedschbg));
						}
						//������ѡ���ˣ�����ɫ����Ϊѡ�еı���ɫ
						if(alIsSelected.get(position))
						{
							ll.setBackgroundColor(getResources().getColor(R.color.selectedsch));
						}
						//��������ӣ���������ӵ�ͼ��
						if(alSch.get(position).getAlarmSet())
						{
							ImageView iv=new ImageView(RcActivity.this);
							iv.setImageDrawable(getResources().getDrawable(R.drawable.alarm));
							iv.setLayoutParams(new LayoutParams(20, 20));
							llUp.addView(iv);
						}
						//�ճ�����TextView
						TextView tvType=new TextView(RcActivity.this);
						tvType.setText(alSch.get(position).typeForListView());
						tvType.setTextSize(17);
						tvType.setTextColor(Color.parseColor("#b20000"));
						llDown.addView(tvType);
						//�ճ̱���TextView
						TextView tvTitle=new TextView(RcActivity.this);
						tvTitle.setText(alSch.get(position).getTitle());
						tvTitle.setTextSize(17);
						tvTitle.setTextColor(Color.parseColor("#000000"));
						llDown.addView(tvTitle);
						ll.addView(llUp);
						ll.addView(llDown);
						return ll;
					}
		        }
        );
        lv.setOnItemClickListener
        (
        		new OnItemClickListener()
        		{
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
					{
						bCheck.setEnabled(true);//��������ť�ֱ�Ϊ��������ճ̲鿴���ճ��޸ġ��ճ�ɾ��,
				    	bEdit.setEnabled(true);//����û����ճ��б���ѡ����ĳ���ճ�ʱ����Ϊ����״̬
				    	bDel.setEnabled(true);
				    	
						schTemp=alSch.get(arg2);//ѡ�и���Ŀʱ���Ѹ���Ŀ���󸳸�schTemp
						
						//�ѱ�־λȫ����Ϊfalse���ٰѵ�ǰѡ����Ķ�Ӧ�ı�־λ��Ϊtrue
						for(int i=0;i<alIsSelected.size();i++)
						{
							alIsSelected.set(i,false);
						}
						alIsSelected.set(arg2,true);
					}
		        }
        );
        
        //bNew����
        bNew.setOnClickListener
        (
        		new OnClickListener()
		        {
		
					@Override
					public void onClick(View v) {
						Calendar c=Calendar.getInstance();
						int t1=c.get(Calendar.YEAR);
						int t2=c.get(Calendar.MONTH)+1;
						int t3=c.get(Calendar.DAY_OF_MONTH);
						schTemp=new Schedule(t1,t2,t3);//��ʱ�½�һ���ճ̶�����������Ϊ��ǰ����
						wcNewOrEdit=WhoCall.NEW;//�����ճ̱༭��������½���ť
						gotoSetting();//ȥ�ճ̱༭����
					}        	
		        }
        );       
        //bEdit����
        bEdit.setOnClickListener
        (
        		new OnClickListener()
		        {
					@Override
					public void onClick(View v) {
						wcNewOrEdit=WhoCall.EDIT;//�����ճ̱༭��������޸İ�ť
						gotoSetting();//ȥ�ճ̱༭����
					}        	
		        }
        ); 
        
        //ɾ��ѡ�е��ճ̰�ť
        bDel.setOnClickListener
        (
        		new OnClickListener()
		        {
					@Override
					public void onClick(View v) {
						showDialog(DIALOG_SCH_DEL_CONFIRM);
					}
		        }
        );
        
        //ɾ�����й����ճ̰�ť
        bDelAll.setOnClickListener
        (
        		new OnClickListener()
		        {
					@Override
					public void onClick(View v) {
						showDialog(DIALOG_ALL_DEL_CONFIRM);
					}
		        }
        );
        
        //�ճ̲��Ұ�ť
        bSearch.setOnClickListener
        (
        		new OnClickListener()
		        {
					@Override
					public void onClick(View v) {
						gotoSearch();
					}
		        }
        );
        
      //�ճ̲鿴��ť
        bCheck.setOnClickListener
        (
        		new OnClickListener()
		        {
					@Override
					public void onClick(View v) {
						showDialog(DIALOG_CHECK);
					}
		        }
        ); 
    }
    //===================================�ճ̱༭����start=====================================
    public void gotoSetting()//��ʼ���½��ճ̽���
    {
    	setContentView(R.layout.newschedule);
    	curr=Layout.SETTING;
    	
    	TextView tvTitle=(TextView)findViewById(R.id.tvnewscheduleTitle);
    	if(wcNewOrEdit==WhoCall.NEW)
    	{
    		tvTitle.setText("�½��ճ�");
    	}
    	else if(wcNewOrEdit==WhoCall.EDIT)
    	{
    		tvTitle.setText("�޸��ճ�");
    	}
    	final Spinner spType=(Spinner)findViewById(R.id.spxjrcType);
    	Button bNewType=(Button)findViewById(R.id.bxjrcNewType);
    	final EditText etTitle=(EditText)findViewById(R.id.etxjrcTitle);
    	final EditText etNote=(EditText)findViewById(R.id.etxjrcNote);
    	TextView tvDate=(TextView)findViewById(R.id.tvnewscheduleDate);
    	Button bSetDate=(Button)findViewById(R.id.bxjrcSetDate);
    	TextView tvTime=(TextView)findViewById(R.id.tvnewscheduleTime);
    	TextView tvAlarm=(TextView)findViewById(R.id.tvnewscheduleAlarm);
    	final Button bSetAlarm=(Button)findViewById(R.id.bxjrcSetAlarm);
    	Button bDone=(Button)findViewById(R.id.bxjrcDone);
    	Button bCancel=(Button)findViewById(R.id.bxjrcCancel);
		
		etTitle.setText(schTemp.getTitle());
		etNote.setText(schTemp.getNote());
		tvDate.setText(schTemp.getDate1());
		tvTime.setText(schTemp.getTimeSet()?schTemp.getTime1():"�޾���ʱ��");
		tvAlarm.setText(schTemp.getAlarmSet()?schTemp.getDate2()+"  "+schTemp.getTime2():"������");
		
		//����spinner����
		spType.setAdapter
		(
				new BaseAdapter()
				{
					@Override
					public int getCount() 
					{
						return alType.size();
					}
		
					@Override
					public Object getItem(int position) 
					{
						return alType.get(position);
					}
					@Override
					public long getItemId(int position) {return 0;}
		
					@Override
					public View getView(int position, View convertView, ViewGroup parent) 
					{
						LinearLayout ll=new LinearLayout(RcActivity.this);
						ll.setOrientation(LinearLayout.HORIZONTAL);	
						TextView tv=new TextView(RcActivity.this);
						tv.setText(alType.get(position));
						tv.setTextSize(17);
						tv.setTextColor(R.color.black);
						return tv;
					}			
				}
		);
		spType.setSelection(sel);

		//�½��ճ����Ͱ�ť
		bNewType.setOnClickListener
		(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						schTemp.setTitle(etTitle.getText().toString());//���Ѿ������title��note����schTemp���Է�����ʱ�����
						schTemp.setNote(etNote.getText().toString());
						sel=spType.getSelectedItemPosition();//�洢spType�ĵ�ǰѡ��
						gotoTypeManager();//�����ճ����͹������
					}
				}
		);
		
		//
		bSetDate.setOnClickListener
		(
				new OnClickListener()
				{

					@Override
					public void onClick(View v) {
						schTemp.setTitle(etTitle.getText().toString());//���Ѿ����������ͱ�ע����schTemp���Է�������ʱ������ӷ���ʱ�����
						schTemp.setNote(etNote.getText().toString());
						sel=spType.getSelectedItemPosition();
						wcSetTimeOrAlarm=WhoCall.SETTING_DATE;//������������ʱ��Ի�����������ճ����ڰ�ť
						showDialog(DIALOG_SET_DATETIME);
					}
				}
		);
		bSetAlarm.setOnClickListener
		(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						schTemp.setTitle(etTitle.getText().toString());//���Ѿ����������ͱ�ע����schTemp���Է�������ʱ������ӷ���ʱ�����
						schTemp.setNote(etNote.getText().toString());
						sel=spType.getSelectedItemPosition();
						wcSetTimeOrAlarm=WhoCall.SETTING_ALARM;//������������ʱ��Ի�������������Ӱ�ť
						showDialog(DIALOG_SET_DATETIME);
					}
				}
		);
		
		//��ɰ�ť����
		bDone.setOnClickListener(
			new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					//���½����ճ�ʱ��͵�ǰʱ��ȽϿ��Ƿ����
					if(schTemp.isPassed())
					{
						Toast.makeText(RcActivity.this, "���ܴ��������ճ�", Toast.LENGTH_SHORT).show();
						return;
					}
					
					if(schTemp.getAlarmSet())//������������ӣ���������ʱ���Ƿ����
					{
						//����ճ���������������֮ǰ,
						//�������ճ�ʱ�������õ�ǰ���£��ճ����ں�����������ͬ�������ճ�ʱ��������ʱ��֮ǰ��
						//������ʾ
						if(schTemp.getDate1().compareTo(schTemp.getDate2())<0||
								schTemp.getTimeSet()&&
								schTemp.getDate1().compareTo(schTemp.getDate2())==0&&
								schTemp.getTime1().compareTo(schTemp.getTime2())<0)
						{
							Toast.makeText(RcActivity.this,"����ʱ�䲻�����ճ�ʱ��֮��", Toast.LENGTH_SHORT).show();
							return;
						}
					}
					
					String title=etTitle.getText().toString().trim();
					if(title.equals(""))//����ճ̱���û�����룬Ĭ��Ϊδ����
					{
						title="δ����";
					}
					schTemp.setTitle(title);
					String note=etNote.getText().toString();
					schTemp.setNote(note);
					String type=(String) spType.getSelectedItem();
					schTemp.setType(type);
					
			    	if(wcNewOrEdit==WhoCall.NEW)//�����ǰ�������½��ճ̣����ò����ճ̷���
			    	{
			    		insertSchedule(RcActivity.this);
			    	}
			    	else if(wcNewOrEdit==WhoCall.EDIT)//�����ǰ�������޸��ճ̣����ø����ճ̷���
			    	{
			    		updateSchedule(RcActivity.this);
			    	}
					
					gotoMain();
				}
				
			}
		);
		//ȡ����ť����
		bCancel.setOnClickListener
		(
			new OnClickListener()
			{
				@Override
				public void onClick(View v) {					
					gotoMain();
				}
				
			}
		);
    }
    //===================================���͹������start=====================================
	public void gotoTypeManager()
	{
		setContentView(R.layout.typemanager);
		curr=Layout.TYPE_MANAGER;
		final ListView lvType=(ListView)findViewById(R.id.lvtypemanagerType);//�б��г�������������
		final EditText etNew=(EditText)findViewById(R.id.ettypemanagerNewType);//�������������Ƶ�TextView
		final Button bNew=(Button)findViewById(R.id.btypemanagerNewType);//�½����Ͱ�ť
		final Button bBack=(Button)findViewById(R.id.btypemanagerBack);//������һҳ��ť
		
		bBack.setOnClickListener
		(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						gotoSetting();
					}
				}
		);
		
		lvType.setAdapter
		(
				new BaseAdapter()
				{
					@Override
					public int getCount() 
					{
						return alType.size();
					}
					@Override
					public Object getItem(int position) 
					{
						return alType.get(position);
					}
					@Override
					public long getItemId(int position) 
					{
						return 0;
					}
					@Override
					public View getView(final int position, View convertView, ViewGroup parent) 
					{
						LinearLayout ll=new LinearLayout(RcActivity.this);
						ll.setOrientation(LinearLayout.HORIZONTAL);
						ll.setGravity(Gravity.CENTER_VERTICAL);
						TextView tv=new TextView(RcActivity.this);
						tv.setText(alType.get(position));
						tv.setTextSize(17);
						tv.setTextColor(Color.BLACK);
						tv.setPadding(20, 0, 0, 0);
						ll.addView(tv);
						
						//����Դ������Ͳ���ɾ���������Խ����ͺ������һ���������ɾ���Խ�����
						if(position>=defultType.length)
						{
							ImageButton ib=new ImageButton(RcActivity.this);
							ib.setBackgroundDrawable(RcActivity.this.getResources().getDrawable(R.drawable.cross));
							ib.setLayoutParams(new LayoutParams(24, 24));
							ib.setPadding(20, 0, 0, 0);
							
							ib.setOnClickListener
							(
									new OnClickListener()
									{
										@Override
										public void onClick(View v) 
										{
											deleteType(RcActivity.this,lvType.getItemAtPosition(position).toString());
											loadType(RcActivity.this);
											gotoTypeManager();
										}
									}
							);
							ll.addView(ib);
						}
						return ll;
					}
				}
		);

		bNew.setOnClickListener
		(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						String newType=etNew.getText().toString().trim();
						if(newType.equals(""))
						{
							Toast.makeText(RcActivity.this, "�������Ʋ���Ϊ�ա�", Toast.LENGTH_SHORT).show();
							return;
						}
						insertType(RcActivity.this,newType);
						gotoTypeManager();
					}
				}
		);
	}
    //===================================���ҽ���start=========================================
    public void gotoSearch()
    {
		setContentView(R.layout.search);
    	curr=Layout.SEARCH;
    	final Button bChange=(Button)findViewById(R.id.bsearchChange);//�ı���ҷ�Χ��ť
		final Button bSearch=(Button)findViewById(R.id.bsearchGo);//��ʼ����
		final Button bCancel=(Button)findViewById(R.id.bsearchCancel);//ȡ��
		final CheckBox cbDateRange=(CheckBox)findViewById(R.id.cbsearchDateRange);//�����Ƿ����Ʒ�Χ��CheckBox
		final CheckBox cbAllType=(CheckBox)findViewById(R.id.cbsearchType);//�Ƿ��������������в��ҵ�CheckBox
		final ListView lv=(ListView)findViewById(R.id.lvSearchType);//������������lv��
		final TextView tvFrom=(TextView)findViewById(R.id.tvsearchFrom);//������ʼʱ�ڵ�tv
		final TextView tvTo=(TextView)findViewById(R.id.tvsearchTo);////������ֹʱ�ڵ�tv
		tvFrom.setText(rangeFrom);
		tvTo.setText(rangeTo);
		
		final ArrayList<String> type=getAllType(RcActivity.this);//��ȡ�Ѵ��ճ��е��������ͺ��û��Խ�����������
		
		alSelectedType.clear();
		for(int i=0;i<type.size();i++)//Ĭ��Ϊ������������״̬λfalse
		{
			alSelectedType.add(false);
		}
		
		cbDateRange.setOnCheckedChangeListener
		(
				new OnCheckedChangeListener()
				{//�����Ƿ��������ڷ�Χ��CheckBox�����������ڷ�Χ�İ�ť�Ƿ����
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						bChange.setEnabled(isChecked);
					}
				}
		);
		
		//���á���ȫ����������������CheckBox�ı�״̬ʱ����Ϊ
		cbAllType.setOnCheckedChangeListener
		(
				new OnCheckedChangeListener()
				{
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						for(int i=0;i<type.size();i++)//ѡ�С�ȫ��ѡ�С����listview����������ͺ����checkbox���ѡ��״̬
						{
							alSelectedType.set(i, isChecked);
						}
						lv.invalidateViews();//ˢ��ListView??
					}
				}
		);
		
		bChange.setOnClickListener
		(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						showDialog(DIALOG_SET_SEARCH_RANGE);
					}
				}
		);
		
		lv.setAdapter
		(
				new BaseAdapter()
				{
					@Override
					public int getCount() 
					{
						return type.size();
					}
		
					@Override
					public Object getItem(int position) 
					{
						return type.get(position);
					}
					@Override
					public long getItemId(int position) 
					{
						return 0;
					}
		
					@Override
					public View getView(final int position, View convertView, ViewGroup parent) {
						LinearLayout ll=new LinearLayout(RcActivity.this);
						ll.setOrientation(LinearLayout.HORIZONTAL);	
						ll.setGravity(Gravity.CENTER_VERTICAL);
						LinearLayout llin=new LinearLayout(RcActivity.this);
						llin.setPadding(20, 0, 0, 0);
						ll.addView(llin);
						CheckBox cb=new CheckBox(RcActivity.this);
						cb.setButtonDrawable(R.drawable.checkbox);
						llin.addView(cb);
						cb.setChecked(alSelectedType.get(position));//��ArrayList����洢��״̬����CheckBox״̬
						
						cb.setOnCheckedChangeListener
						(
								new OnCheckedChangeListener()
								{
									@Override
									public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
									{
										alSelectedType.set(position, isChecked);//�ı�ArrayList�����Ӧλ��booleanֵ
									}
								}
						);
						
						TextView tv=new TextView(RcActivity.this);
						tv.setText(type.get(position));
						tv.setTextSize(18);
						tv.setTextColor(R.color.black);
						ll.addView(tv);
						return ll;
					}			
				}	
		);
		
		bSearch.setOnClickListener
		(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{ 
						//���û��һ�����ͱ�ѡ������ʾ
						boolean tmp=false;
						for(boolean b:alSelectedType)
						{
							tmp=tmp|b;
						}
						if(tmp==false)
						{
							Toast.makeText(RcActivity.this, "������ѡ��һ������", Toast.LENGTH_SHORT).show();
							return;
						}
						
						searchSchedule(RcActivity.this,type);
						gotoSearchResult();
					}
				}
		);
		
		bCancel.setOnClickListener
		(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						gotoMain();
					}
				}
		);
    }
    //===================================���ҽ������start=====================================
    public void gotoSearchResult()//�ý����������������˼�����ť������ȫһ��
    {
    	setContentView(R.layout.searchresult);
    	curr=Layout.SEARCH_RESULT;

    	sel=0;
    	
    	final ImageButton bCheck=(ImageButton)findViewById(R.id.ibsearchresultCheck);
    	final ImageButton bEdit=(ImageButton)findViewById(R.id.ibsearchresultEdit);
    	final ImageButton bDel=(ImageButton)findViewById(R.id.ibsearchresultDel);
    	ImageButton bBack=(ImageButton)findViewById(R.id.ibsearchresultBack);
    	ListView lv=(ListView)findViewById(R.id.lvsearchresultSchedule);
        
        bCheck.setEnabled(false);
    	bEdit.setEnabled(false);
    	bDel.setEnabled(false);
        
        
        //�����ǲ��ҽ����ListView����
        lv.setAdapter
        (
        		new BaseAdapter()
		        {
					@Override
					public int getCount() 
					{
						return alSch.size();
					}
		
					@Override
					public Object getItem(int position) 
					{
						return alSch.get(position);
					}
		
					@Override
					public long getItemId(int position) 
					{
						return 0;
					}
		
					@Override
					public View getView(int position, View convertView, ViewGroup parent) 
					{
						LinearLayout ll=new LinearLayout(RcActivity.this);
						ll.setOrientation(LinearLayout.VERTICAL);
						ll.setPadding(5, 5, 5, 5);
						
						LinearLayout llUp=new LinearLayout(RcActivity.this);
						llUp.setOrientation(LinearLayout.HORIZONTAL);
						LinearLayout llDown=new LinearLayout(RcActivity.this);
						llDown.setOrientation(LinearLayout.HORIZONTAL);
								
						TextView tvDate=new TextView(RcActivity.this);
						tvDate.setText(alSch.get(position).getDate1()+"   ");
						tvDate.setTextSize(17);
						tvDate.setTextColor(Color.parseColor("#129666"));
						llUp.addView(tvDate);
						
						TextView tvTime=new TextView(RcActivity.this);
						tvTime.setText(alSch.get(position).timeForListView());
						tvTime.setTextSize(17);
						tvTime.setTextColor(Color.parseColor("#925301"));
						llUp.addView(tvTime);
						
						if(alSch.get(position).isPassed())//���ճ��ѹ��ڣ������ں�ʱ����ɫ������ɫ���
						{
							tvDate.setTextColor(Color.parseColor("#292929"));
							tvTime.setTextColor(Color.parseColor("#292929"));
							ll.setBackgroundColor(Color.parseColor("#818175"));
						}
						
						if(alSch.get(position).getAlarmSet())
						{
							ImageView iv=new ImageView(RcActivity.this);
							iv.setImageDrawable(getResources().getDrawable(R.drawable.alarm));
							iv.setLayoutParams(new LayoutParams(20, 20));
							llUp.addView(iv);
						}
						
						TextView tvType=new TextView(RcActivity.this);
						tvType.setText(alSch.get(position).typeForListView());
						tvType.setTextSize(17);
						tvType.setTextColor(Color.parseColor("#b20000"));
						llDown.addView(tvType);
						
						TextView tvTitle=new TextView(RcActivity.this);
						tvTitle.setText(alSch.get(position).getTitle());
						tvTitle.setTextSize(17);
						tvTitle.setTextColor(Color.parseColor("#000000"));
						llDown.addView(tvTitle);
		
						
						ll.addView(llUp);
						ll.addView(llDown);
						return ll;
					}
		        }
        );
        
        lv.setOnItemClickListener
        (
        		new OnItemClickListener()
        		{
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
					{
				        bCheck.setEnabled(true);
				    	bEdit.setEnabled(true);
				    	bDel.setEnabled(true);
						schTemp=alSch.get(arg2);//ѡ��ĳ����Ŀʱ���Ѹ���Ŀ���󸳸�schTemp
					}
		        }
        );
        
        //�޸��ճ̰�ť����
        bEdit.setOnClickListener
        (
        		new OnClickListener()
		        {
		
					@Override
					public void onClick(View v) {
						wcSetTimeOrAlarm=WhoCall.EDIT;
						gotoSetting();
					}        	
		        }
        ); 
        //ɾ��ѡ���ճ̰�ť����
        bDel.setOnClickListener
        (
        		new OnClickListener()
		        {
					@Override
					public void onClick(View v) {
						showDialog(DIALOG_SCH_DEL_CONFIRM);
					}
		        }
        );
        
        //�����ճ̰�ť����
        bBack.setOnClickListener
        (
        		new OnClickListener()
		        {
					@Override
					public void onClick(View v) 
					{
						gotoSearch();
					}
		        	
		        }
        );
        
      //�鿴�ճ̰�ť����
        bCheck.setOnClickListener
        (
        		new OnClickListener()
		        {
					@Override
					public void onClick(View v) {
						showDialog(DIALOG_CHECK);
					}
		        }
        );
    }
	//=========================��������start==================================
	public void gotoHelp()
	{ 
		getWindow().setFlags//ȫ��
    	(
    			WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FULLSCREEN
    	);
		setContentView(R.layout.help);
		curr=Layout.HELP;
		Button bBack=(Button)this.findViewById(R.id.bhelpback);
		bBack.setOnClickListener
		(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						gotoMain();
					}
					
				}
		);
	}
	//�����Ի���
    @Override
	public Dialog onCreateDialog(int id) 
    {
    	Dialog dialog=null;
    	switch(id)
    	{
    		case DIALOG_SET_SEARCH_RANGE:
    			AlertDialog.Builder b=new AlertDialog.Builder(this); 
  			  	b.setItems(null,null);
  			  	dialogSetRange=b.create();
  			  	dialog=dialogSetRange;	
    		break;
    		
    		case DIALOG_SET_DATETIME:
    			AlertDialog.Builder abSetDatetime=new AlertDialog.Builder(this); 
    			abSetDatetime.setItems(null,null);
    			dialogSetDatetime=abSetDatetime.create();
  			  	dialog=dialogSetDatetime;	
    		break;
    		
    		case DIALOG_SCH_DEL_CONFIRM:
    			AlertDialog.Builder abSchDelConfirm=new AlertDialog.Builder(this); 
    			abSchDelConfirm.setItems(null,null);
    			dialogSchDelConfirm=abSchDelConfirm.create();
  			  	dialog=dialogSchDelConfirm;	
    		break;
    		
    		case DIALOG_CHECK:
    			AlertDialog.Builder abCheck=new AlertDialog.Builder(this); 
    			abCheck.setItems(null,null);
    			dialogCheck=abCheck.create();
  			  	dialog=dialogCheck;	
    		break;
    		
    		case DIALOG_ALL_DEL_CONFIRM:
    			AlertDialog.Builder abAllDelConfirm=new AlertDialog.Builder(this); 
    			abAllDelConfirm.setItems(null,null);
    			dialogAllDelConfirm=abAllDelConfirm.create();
  			  	dialog=dialogAllDelConfirm;	
    		break;
    		
    		case DIALOG_ABOUT:
    			AlertDialog.Builder abAbout=new AlertDialog.Builder(this); 
    			abAbout.setItems(null,null);
    			dialogAbout=abAbout.create();
  			  	dialog=dialogAbout;	
    		break;
    	}
		return dialog;
	}
    //ÿ�ε���Dialog�Ի���ʱ���¶Ի��������
	@Override
	public void onPrepareDialog(int id,Dialog dialog) 
	{
		super.onPrepareDialog(id, dialog);
    	switch(id)
    	{
			case DIALOG_SET_SEARCH_RANGE://����������Χ�Ի���		
				dialog.setContentView(R.layout.dialogsetrange);
				//year month day������1�ı�ʾ������ʼʱ�����ã�2��ʾ������ֹʱ�����ã�P��ʾplus�Ӻţ�M��ʾminus����
				final ImageButton bYear1P=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeYear1P);
				final ImageButton bYear1M=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeYear1M);
				final ImageButton bMonth1P=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeMonth1P);
				final ImageButton bMonth1M=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeMonth1M);
				final ImageButton bDay1P=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeDay1P);
				final ImageButton bDay1M=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeDay1M);
				final EditText etYear1=(EditText)dialog.findViewById(R.id.etdialogsetrangeYear1);
				final EditText etMonth1=(EditText)dialog.findViewById(R.id.etdialogsetrangeMonth1);
				final EditText etDay1=(EditText)dialog.findViewById(R.id.etdialogsetrangeDay1);
				
				final ImageButton bYear2P=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeYear2P);
				final ImageButton bYear2M=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeYear2M);
				final ImageButton bMonth2P=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeMonth2P);
				final ImageButton bMonth2M=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeMonth2M);
				final ImageButton bDay2P=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeDay2P);
				final ImageButton bDay2M=(ImageButton)dialog.findViewById(R.id.bdialogsetrangeDay2M);
				final EditText etYear2=(EditText)dialog.findViewById(R.id.etdialogsetrangeYear2);
				final EditText etMonth2=(EditText)dialog.findViewById(R.id.etdialogsetrangeMonth2);
				final EditText etDay2=(EditText)dialog.findViewById(R.id.etdialogsetrangeDay2);
				
				Button bSetRangeOk=(Button)dialog.findViewById(R.id.bdialogsetrangeOk);
				Button bSetRangeCancel=(Button)dialog.findViewById(R.id.bdialogsetrangeCancel);
				
				//��YYYY/MM/DD��ʽ�������շ������,�������ʾ�����յ�TextView��
				String[] from=splitYMD(rangeFrom);
				String[] to=splitYMD(rangeTo);
				
				etYear1.setText(from[0]);
				etMonth1.setText(from[1]);
				etDay1.setText(from[2]);
				etYear2.setText(to[0]);
				etMonth2.setText(to[1]);
				etDay2.setText(to[2]);
				
				
				bYear1P.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear1.getText().toString().trim());
								year++;
								etYear1.setText(""+year);
							}
						}
				);
				bYear1M.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear1.getText().toString().trim());
								year--;
								etYear1.setText(""+year);
							}
						}
				);
				bMonth1P.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int month=Integer.parseInt(etMonth1.getText().toString().trim());
								if(++month>12)
								{
									month=1;
								}
								etMonth1.setText(month<10?"0"+month:""+month);
							}
						}
				);
				bMonth1M.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int month=Integer.parseInt(etMonth1.getText().toString().trim());
								if(--month<1)
								{
									month=12;
								}
								etMonth1.setText(month<10?"0"+month:""+month);
							}
						}
				);
				
				bDay1P.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear1.getText().toString().trim());
								int month=Integer.parseInt(etMonth1.getText().toString().trim());
								int day=Integer.parseInt(etDay1.getText().toString().trim());
								if(++day>getMaxDayOfMonth(year,month))
								{
									day=1;
								}
								etDay1.setText(day<10?"0"+day:""+day);
							}
						}
				);
				bDay1M.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear1.getText().toString().trim());
								int month=Integer.parseInt(etMonth1.getText().toString().trim());
								int day=Integer.parseInt(etDay1.getText().toString().trim());
								if(--day<1)
								{
									day=getMaxDayOfMonth(year,month);
								}
								etDay1.setText(day<10?"0"+day:""+day);
							}
						}
				);
				//================�ָ��ߣ�����Ϊ������ʼʱ��İ�ť������һ��Ϊ������ֹʱ��İ�ť����==================
				bYear2P.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear2.getText().toString().trim());
								year++;
								etYear2.setText(""+year);
							}
						}	
				);
				bYear2M.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear2.getText().toString().trim());
								year--;
								etYear2.setText(""+year);
							}
						}
				);
				bMonth2P.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int month=Integer.parseInt(etMonth2.getText().toString().trim());
								if(++month>12)
								{
									month=1;
								}
								etMonth2.setText(month<10?"0"+month:""+month);
							}
						}
				);
				bMonth2M.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int month=Integer.parseInt(etMonth2.getText().toString().trim());
								if(--month<1)
								{
									month=12;
								}
								etMonth2.setText(month<10?"0"+month:""+month);
							}
						}
				);
				
				bDay2P.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear2.getText().toString().trim());
								int month=Integer.parseInt(etMonth2.getText().toString().trim());
								int day=Integer.parseInt(etDay2.getText().toString().trim());
								if(++day>getMaxDayOfMonth(year,month))
								{
									day=1;
								}
								etDay2.setText(day<10?"0"+day:""+day);
							}
						}
				);
				bDay2M.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear2.getText().toString().trim());
								int month=Integer.parseInt(etMonth2.getText().toString().trim());
								int day=Integer.parseInt(etDay2.getText().toString().trim());
								if(--day<1)
								{
									day=getMaxDayOfMonth(year,month);
								}
								etDay2.setText(day<10?"0"+day:""+day);
							}
						}
				);
				
				bSetRangeOk.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year1=Integer.parseInt(etYear1.getText().toString().trim());
								int month1=Integer.parseInt(etMonth1.getText().toString().trim());
								int day1=Integer.parseInt(etDay1.getText().toString().trim());
								int year2=Integer.parseInt(etYear2.getText().toString().trim());
								int month2=Integer.parseInt(etMonth2.getText().toString().trim());
								int day2=Integer.parseInt(etDay2.getText().toString().trim());
								
								if(day1>getMaxDayOfMonth(year1,month1)||day2>getMaxDayOfMonth(year2,month2))
								{
									Toast.makeText(RcActivity.this, "�������ô���", Toast.LENGTH_SHORT).show();
									return;
								}
								rangeFrom=Schedule.toDateString(year1, month1, day1);
								rangeTo=Schedule.toDateString(year2, month2, day2);
								if(rangeFrom.compareTo(rangeTo)>0)
								{
									Toast.makeText(RcActivity.this, "��ʼ���ڲ��ܴ�����ֹ����", Toast.LENGTH_SHORT).show();
									return;
								}
								dialogSetRange.cancel();
								gotoSearch();
							}
						}
				);
				
				//��ȡ����Ի���ر�
				bSetRangeCancel.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								dialogSetRange.cancel();
							}
						}
				);

			break;
			
			case DIALOG_SET_DATETIME://����ʱ�����ڶԻ���
				dialog.setContentView(R.layout.dialogdatetime);
				final ImageButton bYearP=(ImageButton)dialog.findViewById(R.id.bdialogdatetimeYearP);
				final ImageButton bYearM=(ImageButton)dialog.findViewById(R.id.bdialogdatetimeYearM);
				final ImageButton bMonthP=(ImageButton)dialog.findViewById(R.id.bdialogdatetimeMonthP);
				final ImageButton bMonthM=(ImageButton)dialog.findViewById(R.id.bdialogdatetimeMonthM);
				final ImageButton bDayP=(ImageButton)dialog.findViewById(R.id.bdialogdatetimeDayP);
				final ImageButton bDayM=(ImageButton)dialog.findViewById(R.id.bdialogdatetimeDayM);
				final ImageButton bHourP=(ImageButton)dialog.findViewById(R.id.bdialogdatetimeHourP);
				final ImageButton bHourM=(ImageButton)dialog.findViewById(R.id.bdialogdatetimeHourM);
				final ImageButton bMinP=(ImageButton)dialog.findViewById(R.id.bdialogdatetimeMinP);
				final ImageButton bMinM=(ImageButton)dialog.findViewById(R.id.bdialogdatetimeMinM);
				final EditText etYear=(EditText)dialog.findViewById(R.id.etdialogdatetimeYear);
				final EditText etMonth=(EditText)dialog.findViewById(R.id.etdialogdatetimeMonth);
				final EditText etDay=(EditText)dialog.findViewById(R.id.etdialogdatetimeDay);
				final EditText etHour=(EditText)dialog.findViewById(R.id.etdialogdatetimeHour);
				final EditText etMin=(EditText)dialog.findViewById(R.id.etdialogdatetimeMin);
				final CheckBox cbSetTime=(CheckBox)dialog.findViewById(R.id.cbdialogdatetimeSettime);
				final CheckBox cbSetAlarm=(CheckBox)dialog.findViewById(R.id.cbdialogdatetimeSetAlarm);
				Button bSetDateOk=(Button)dialog.findViewById(R.id.bdialogdatetimeOk);
				Button bSetDateCancel=(Button)dialog.findViewById(R.id.bdialogdatetimeCancel);
				
				LinearLayout llSetTime=(LinearLayout)dialog.findViewById(R.id.lldialogdatetimeSetTime);
				LinearLayout llCheckBox=(LinearLayout)dialog.findViewById(R.id.lldialogdatetimeCheckBox);
				LinearLayout llAlarmCheckBox=(LinearLayout)dialog.findViewById(R.id.lldialogdatetimeAlarmCheckBox);
				
				if(wcSetTimeOrAlarm==WhoCall.SETTING_DATE)//������������ڰ�ť���õı��Ի���
				{
					llSetTime.setVisibility(LinearLayout.VISIBLE);//���þ���ʱ���LinearLayout��ʾ����
					llCheckBox.setVisibility(LinearLayout.VISIBLE);//�Ƿ����þ���ʱ���CheckBox��ʾ����
					llAlarmCheckBox.setVisibility(LinearLayout.GONE);//�Ƿ��������ӵ�CheckBox����ʾ
					
					//��schTemp�е�year month day��ʾ��EditText��
					etYear.setText(""+schTemp.getYear());
					etMonth.setText(schTemp.getMonth()<10?"0"+schTemp.getMonth():""+schTemp.getMonth());
					etDay.setText(schTemp.getDay()<10?"0"+schTemp.getDay():""+schTemp.getDay());
					
					//���schTemp�б�ʾ�Ƿ����þ���ʱ��Ĳ���ֵtimeSetΪtrue���������˾���ʱ�䣬��������õ�ʱ����ʾ��EditText��
					if(schTemp.getTimeSet())
					{
						etHour.setText(schTemp.getHour()<10?"0"+schTemp.getHour():""+schTemp.getHour());
						etMin.setText(schTemp.getMinute()<10?"0"+schTemp.getMinute():""+schTemp.getMinute());
					}
					else//����Ĭ����ʾ�˵�
					{
						etHour.setText("08");
						etMin.setText("00");
					}
					
					//�Ƿ����þ���ʱ���CheckBox�����й�����ʱ��Ŀؼ��ɲ�����
					cbSetTime.setOnCheckedChangeListener
					(
							new OnCheckedChangeListener()
							{
								@Override
								public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
								{
									etHour.setEnabled(isChecked);
									etMin.setEnabled(isChecked);
									bHourP.setEnabled(isChecked);
									bHourM.setEnabled(isChecked);
									bMinP.setEnabled(isChecked);
									bMinM.setEnabled(isChecked);
								}
							}
					);
					
					//���������ȷ������cbSetTime��OnCheckedChangeListener
					cbSetTime.setChecked(schTemp.getTimeSet());
					cbSetTime.setChecked(!schTemp.getTimeSet());
					cbSetTime.setChecked(schTemp.getTimeSet());
				}
				
				//������øý�������������Ӱ�ť
				if(wcSetTimeOrAlarm==WhoCall.SETTING_ALARM)
				{
					llSetTime.setVisibility(LinearLayout.VISIBLE);//���þ���ʱ���LinearLayout��ʾ
					llCheckBox.setVisibility(LinearLayout.GONE);//�Ƿ����þ���ʱ���CheckBox����ʾ
					llAlarmCheckBox.setVisibility(LinearLayout.VISIBLE);//�Ƿ��������ӵ�CheckBox��ʾ
					
					//�Ƿ���������CheckBox�����й��������ӵĿؼ��ɲ�����
					cbSetAlarm.setOnCheckedChangeListener
					(
							new OnCheckedChangeListener()
							{
								@Override
								public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
								{
									bYearP.setEnabled(isChecked);
									bYearM.setEnabled(isChecked);
									bMonthP.setEnabled(isChecked);
									bMonthM.setEnabled(isChecked);
									bDayP.setEnabled(isChecked);
									bDayM.setEnabled(isChecked);
									bHourP.setEnabled(isChecked);
									bHourM.setEnabled(isChecked);
									bMinP.setEnabled(isChecked);
									bMinM.setEnabled(isChecked);
									etYear.setEnabled(isChecked);
									etMonth.setEnabled(isChecked);
									etDay.setEnabled(isChecked);
									etHour.setEnabled(isChecked);
									etMin.setEnabled(isChecked);
								}
							}
					);
					
					//ȷ��OnCheckedChangeListener������
					cbSetAlarm.setChecked(schTemp.getAlarmSet());
					cbSetAlarm.setChecked(!schTemp.getAlarmSet());
					cbSetAlarm.setChecked(schTemp.getAlarmSet());
					
					if(cbSetAlarm.isChecked())//�����ʾ�Ƿ��������ӵ�Checkbox��ѡ�У�˵�����������ã����ȡ������������EditText
					{
						etYear.setText(""+schTemp.getAYear());
						etMonth.setText(schTemp.getAMonth()<10?"0"+schTemp.getAMonth():""+schTemp.getAMonth());
						etDay.setText(schTemp.getADay()<10?"0"+schTemp.getADay():""+schTemp.getADay());
						etHour.setText(schTemp.getAHour()<10?"0"+schTemp.getAHour():""+schTemp.getAHour());
						etMin.setText(schTemp.getAMin()<10?"0"+schTemp.getAMin():""+schTemp.getAMin());
					}
					else//���ûѡ�У�˵��û���������ã�Ĭ�϶�ȡ�ճ�ʱ�����õ����ӵ�EditText
					{
						etYear.setText(""+schTemp.getYear());
						etMonth.setText(schTemp.getMonth()<10?"0"+schTemp.getMonth():""+schTemp.getMonth());
						etDay.setText(schTemp.getDay()<10?"0"+schTemp.getDay():""+schTemp.getDay());
						if(schTemp.getTimeSet())//����ճ������˾���ʱ�䣬���ӵ�Сʱ��������Ϊ����ʱ���Сʱ����
						{
							etHour.setText(schTemp.getHour()<10?"0"+schTemp.getHour():""+schTemp.getHour());
							etMin.setText(schTemp.getMinute()<10?"0"+schTemp.getMinute():""+schTemp.getMinute());
						}
						else//����ճ�û�����ʱ�䣬�����ӵ�Сʱ����Ĭ������8��
						{
							etHour.setText("08");
							etMin.setText("00");
						}
					}
				}				
				
				
				bYearP.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear.getText().toString().trim());
								year++;
								etYear.setText(""+year);
							}
						}
				);
				bYearM.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear.getText().toString().trim());
								year--;
								etYear.setText(""+year);
							}
						}
				);
				bMonthP.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int month=Integer.parseInt(etMonth.getText().toString().trim());
								if(++month>12)
								{
									month=1;
								}
								etMonth.setText(month<10?"0"+month:""+month);
							}
						}
				);
				bMonthM.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int month=Integer.parseInt(etMonth.getText().toString().trim());
								if(--month<1)
								{
									month=12;
								}
								etMonth.setText(month<10?"0"+month:""+month);
							}
						}
				);
				
				bDayP.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear.getText().toString().trim());
								int month=Integer.parseInt(etMonth.getText().toString().trim());
								int day=Integer.parseInt(etDay.getText().toString().trim());
								if(++day>getMaxDayOfMonth(year,month))
								{
									day=1;
								}
								etDay.setText(day<10?"0"+day:""+day);
							}
						}
				);
				bDayM.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int year=Integer.parseInt(etYear.getText().toString().trim());
								int month=Integer.parseInt(etMonth.getText().toString().trim());
								int day=Integer.parseInt(etDay.getText().toString().trim());
								if(--day<1)
								{
									day=getMaxDayOfMonth(year,month);
								}
								etDay.setText(day<10?"0"+day:""+day);
							}
						}
				);
				
				bHourP.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int hour=Integer.parseInt(etHour.getText().toString().trim());
								if(++hour>23)
								{
									hour=0;
								}
								etHour.setText(hour<10?"0"+hour:""+hour);
							}
						}
				);
				bHourM.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int hour=Integer.parseInt(etHour.getText().toString().trim());
								if(--hour<0)
								{
									hour=23;
								}
								etHour.setText(hour<10?"0"+hour:""+hour);
							}
						}
				);
				bMinP.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int min=Integer.parseInt(etMin.getText().toString().trim());
								if(++min>59)
								{
									min=0;
								}
								etMin.setText(min<10?"0"+min:""+min);
							}
						}
				);
				bMinM.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								int min=Integer.parseInt(etMin.getText().toString().trim());
								if(--min<0)
								{
									min=59;
								}
								etMin.setText(min<10?"0"+min:""+min);
							}
						}
				);
				
				bSetDateOk.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								String year=etYear.getText().toString().trim();
								String month=etMonth.getText().toString().trim();
								String day=etDay.getText().toString().trim();
								//����ټ��һ���Ƿ������������ô��󣬱���2��30�ŵȵ�
								if(Integer.parseInt(day)>getMaxDayOfMonth(Integer.parseInt(year),Integer.parseInt(month)))
								{
									Toast.makeText(RcActivity.this, "�������ô���", Toast.LENGTH_SHORT).show();
									return;
								}
								
								//����˶Ի����Ǳ��������ڰ�ť���õģ��������ո���Schedule�е�Date1�����ճ�����
								if(wcSetTimeOrAlarm==WhoCall.SETTING_DATE)
								{
									schTemp.setDate1(year, month, day);
									schTemp.setTimeSet(cbSetTime.isChecked());
									if(cbSetTime.isChecked())//��������˾���ʱ�䣬��ʱ�ָ���Schedule�е�Time1�����ճ�ʱ��
									{							
										String hour=etHour.getText().toString().trim();
										String min=etMin.getText().toString().trim();
										schTemp.setTime1(hour, min);
									}
									
								}
								//����˶Ի����Ǳ��������Ӱ�ť���õģ��������ո���Schedule�е�Date2�����������ڣ�ʱ�ָ���Time2��������ʱ��
								else if(wcSetTimeOrAlarm==WhoCall.SETTING_ALARM)
								{
									schTemp.setAlarmSet(cbSetAlarm.isChecked());
									if(cbSetAlarm.isChecked())
									{
										schTemp.setDate2(year, month, day);
										String hour=etHour.getText().toString().trim();
										String min=etMin.getText().toString().trim();
										schTemp.setTime2(hour, min);
									}
								}
								dialogSetDatetime.cancel();
								gotoSetting();		
							}
						}
				);
				bSetDateCancel.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								dialogSetDatetime.cancel();
							}
						}
				);
				break;
			case DIALOG_SCH_DEL_CONFIRM://ɾ���ճ̶Ի���
				dialog.setContentView(R.layout.dialogschdelconfirm);
				Button bDelOk=(Button)dialog.findViewById(R.id.bdialogschdelconfirmOk);
				Button bDelCancel=(Button)dialog.findViewById(R.id.bdialogschdelconfirmCancel);
				
				bDelOk.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								deleteSchedule(RcActivity.this);
								gotoMain();
								dialogSchDelConfirm.cancel();
							}
						}
				);
				
				bDelCancel.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								dialogSchDelConfirm.cancel();
							}
						}
				);
				break;
				
			case DIALOG_CHECK://�鿴�ճ̶Ի���
				dialog.setContentView(R.layout.dialogcheck);
				TextView tvType=(TextView)dialog.findViewById(R.id.tvdialogcheckType);//��ʾ���͵�TextView
				TextView tvTitle=(TextView)dialog.findViewById(R.id.tvdialogcheckTitle);//��ʾ�����TextView
				TextView tvNote=(TextView)dialog.findViewById(R.id.tvdialogcheckNote);//��ʾ��ע��TextView
				TextView tvDatetime1=(TextView)dialog.findViewById(R.id.tvdialogcheckDate1);//��ʾ�ճ����ں�ʱ���TextView
				TextView tvDatetime2=(TextView)dialog.findViewById(R.id.tvdialogcheckDate2);//��ʾ�������ں�ʱ���TextView
				Button bEdit=(Button)dialog.findViewById(R.id.bdialogcheckEdit);//�༭��ť
				Button bDel=(Button)dialog.findViewById(R.id.bdialogcheckDel);//ɾ����ť
				Button bBack=(Button)dialog.findViewById(R.id.bdialogcheckBack);//���ذ�ť
				
				tvType.setText(schTemp.typeForListView());
				tvTitle.setText(schTemp.getTitle());
				tvNote.setText(schTemp.getNote());
				
				//�����עΪ�գ���ʾ�ޱ�ע
				if(schTemp.getNote().equals(""))
				{
					tvNote.setText("(�ޱ�ע)");
				}
				String time1=schTemp.getTime1();
				
				//�������ʱ��Ϊ�գ�ʱ����ʾ��--:--
				if(time1.equals("null"))
				{
					time1="- -:- -";
				}
				tvDatetime1.setText(schTemp.getDate1()+"  "+time1);
				
				String date2=schTemp.getDate2();
				String time2=schTemp.getTime2();
				
				//��������Ϊ�յĻ�˵��û������
				if(date2.equals("null"))
				{
					date2="(������)";
					time2="";
				}
				tvDatetime2.setText(date2+"  "+time2);
				
		        bEdit.setOnClickListener
		        (
		        		new OnClickListener()
				        {
							@Override
							public void onClick(View v) {
								dialogCheck.cancel();
								gotoSetting();
							}        	
				        }
		        ); 
		        
		        bDel.setOnClickListener
		        (
		        		new OnClickListener()
				        {
							@Override
							public void onClick(View v) {
								dialogCheck.cancel();
								showDialog(DIALOG_SCH_DEL_CONFIRM);
							}
				        }
		        );
		        
		        bBack.setOnClickListener
		        (
		        		new OnClickListener()
				        {
							@Override
							public void onClick(View v) {
								dialogCheck.cancel();
							}
				        }
		        );
				break;
			case DIALOG_ALL_DEL_CONFIRM://ɾ�����й����ճ̶Ի���
				dialog.setContentView(R.layout.dialogdelpassedconfirm);
				Button bAllDelOk=(Button)dialog.findViewById(R.id.bdialogdelpassedconfirmOk);
				Button bAllDelCancel=(Button)dialog.findViewById(R.id.bdialogdelpassedconfirmCancel);
				bAllDelOk.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								deletePassedSchedule(RcActivity.this);
								gotoMain();
								dialogAllDelConfirm.cancel();
							}
						}
				);
				
				bAllDelCancel.setOnClickListener
				(
						new OnClickListener()
						{
							@Override
							public void onClick(View v) 
							{
								dialogAllDelConfirm.cancel();
							}
						}
			    );
				break;
    	}
	}
	//onKeyDown����
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	//�����ֻ����ذ�ťʱ
    	if(keyCode==4){
        	switch(curr)
        	{
        		case MAIN://��������Ļ��˳�����
        			System.exit(0);
        		break;
        		case SETTING://���ճ̱༭����Ļ�����������
        			gotoMain();
        		break;
        		case TYPE_MANAGER:////�����͹������Ļ������ճ̱༭����
        			gotoSetting();
        		break;
        		case SEARCH://���ճ̲��ҽ���Ļ�����������
        			gotoMain();
        		break;
        		case SEARCH_RESULT://���ճ̲��ҽ������Ļ������ճ̲��ҽ���
        			gotoSearch();
        		break;
        		case HELP://�ڰ�������Ļ�����������
        			gotoMain();
        		break;
        		case ABOUT:
        			gotoMain();
        		break;
        	}
        	return true;
    	}
    	return false;
	}
    //����Menu
    @Override
	public boolean onCreateOptionsMenu(Menu menu) 
    {
    	if(curr!=Layout.MAIN)//ֻ��������������ò˵�???????????????????????
    	{
    		return false;  
    	}
    	MenuItem miHelp=menu.add(1, MENU_HELP, 0, "����");
    	miHelp.setIcon(R.drawable.help);
		MenuItem miAbout=menu.add(1, MENU_ABOUT, 0, "����");
		miAbout.setIcon(R.drawable.about);
		
		miAbout.setOnMenuItemClickListener
		(
				new OnMenuItemClickListener()
				{
					@Override
					public boolean onMenuItemClick(MenuItem item) 
					{	
						setContentView(R.layout.rcabout);
						curr=Layout.HELP;
						return true;
					}
					
				}
		);
		
		miHelp.setOnMenuItemClickListener
		(
				new OnMenuItemClickListener()
				{
					@Override
					public boolean onMenuItemClick(MenuItem item) 
					{		
						setContentView(R.layout.rchelp);
						curr=Layout.ABOUT;
						return true;
					}
				}
		);
		return true;
	}
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) 
	{
		return super.onMenuItemSelected(featureId, item);
	}
    //�����õ�year��month�µ��������
	public int getMaxDayOfMonth(int year,int month)
    {
    	int day=0;
    	boolean run=false;
    	if(year%400==0||year%4==0&&year%100!=0)
    	{
    		run=true;
    	}
    	if(month==4||month==6||month==9||month==11)
    	{
    		day=30;
    	}
    	else if(month==2)
    	{
    		if(run)
    		{
    			day=29;
    		}
    		else
    		{
    			day=28;
    		}
    	}
    	else
    	{
    		day=31;
    	}
    	return day;
    }
    //���ذ�YYYY/MM/DD�ָ�����������ַ�������
	public String[] splitYMD(String ss)
    {
    	String[] s=ss.split("/");
    	return s;
    }
}