package com.bn.lccx;

import java.util.Vector;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import static com.bn.lccx.LoadUtil.*;
enum WhichView {MAIN_MENU,ZZCX_VIEW,CCCX_VIEW,CZCCCX_VIEW,LIST_VIEW,PASSSTATION_VIEW,
	             CCTJ_VIEW,CZTJ_VIEW,GXTJ_VIEW,FJGN_VIEW,WELCOME_VIEW,ABOUT_VIEW,HELP_VIEW}
public class LCCXActivity extends Activity 
{
	WelcomeView wv;//���뻶ӭ����
	WhichView curr;//��ǰö��ֵ	
	static int flag;//����ҳ��ı�־λ����������վվ��ѯ���������β�ѯ��������վ��ѯ	
	
	
	String[][]msgg=new String[][]{{""}};//��������
	
	
	String s1[];
	String s2[];
	
	
	Handler hd=new Handler()//������Ϣ������
	{
			@Override
			public void handleMessage(Message msg)//��д����
        	{
        		switch(msg.what)
        		{
	        		case 0://���뻶ӭ����
	        			goToWelcomeView();
	        			
	        		break;
	        		case 1://����˵�����
	        			goToMainMenu();       			
	        		break;
	        		case 2://������ڽ���
	        			setContentView(R.layout.about);
	        	    	curr=WhichView.ABOUT_VIEW;
	        			break;
	        		case 3://�����������
	        			setContentView(R.layout.help);
	        	    	curr=WhichView.HELP_VIEW;
	        			break;
	        		
        		}
        	}
	};
	
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        //����Ϊȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//���ú���ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		
		CreatTable.creattable();//����
		iniTLisit();//��ʼ������
		this.hd.sendEmptyMessage(0);						//������Ϣ���뻶ӭ����
    }
	
	 public void goToWelcomeView()
	    {
	    	if(wv==null)//����ö���û�����򴴽�
	    	{
	    		wv=new WelcomeView(this);
	    	}
	    	setContentView(wv);
	    	curr=WhichView.WELCOME_VIEW;//��ʶ��ǰ���ڽ���
	    }
	public void goToMainMenu()//ȥ���˵�
	 {	
	      	setContentView(R.layout.main);	
	      	curr=WhichView.MAIN_MENU;
			//�õ����˵��и���ť������
			ImageButton ibzzcx=(ImageButton)findViewById(R.id.ibzzcx);
			ImageButton ibcccx=(ImageButton)findViewById(R.id.ibcccx);
			ImageButton ibczcccx=(ImageButton)findViewById(R.id.ibczcccx);
			ImageButton ibfjgn=(ImageButton)findViewById(R.id.ibfjgn);
			ImageButton ibabout=(ImageButton)findViewById(R.id.about_button);
			ImageButton ibhelp=(ImageButton)findViewById(R.id.help_button);
			ibabout.setOnClickListener//���ڰ�ť�ļ���
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					
					hd.sendEmptyMessage(3);//����Ϣ������ڽ���					
				}
			   }
			);
			ibhelp.setOnClickListener//������ѯ�ļ���
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{

					hd.sendEmptyMessage(2);	//����Ϣ�����������
				}
			   }
			);
			ibzzcx.setOnClickListener//վվ��ѯ��ť�ļ���
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					goTozzcxView();//����վվ��ѯģ��
				}
			   }
			);
			ibcccx.setOnClickListener//���β�ѯ��ť�ļ���
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					goTocccxView();//���복�β�ѯģ��
				}   
			   }
			);
			ibczcccx.setOnClickListener//��վ���г��β�ѯ
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					goToczcccxView();//���복վ��ѯģ��
				}   
			   }
			); 
			ibfjgn.setOnClickListener//���ӹ��ܰ�ť�ļ���
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
                    goTofjgnView();//���븽�ӹ���ģ��
				}
			   }
			);
	 }
	 public void goTozzcxView()//ȥվվ��ѯ
	 {
		 setContentView(R.layout.zzcx);
		 curr=WhichView.ZZCX_VIEW;
		 flag=0;//��־λ
		
		 Button bcx=(Button) findViewById(R.id.zzcxbt);//��ѯ��ť
		 Button bfh=(Button) findViewById(R.id.zzcxfhbt);//���ذ�ť
		
		 iniTLisitarray(R.id.EditText01);//Ϊ������վ�����ı������������
		 iniTLisitarray(R.id.zzcxzzz);
		 iniTLisitarray(R.id.zzcxzdz); 

		 final CheckBox zzzcx=(CheckBox)findViewById(R.id.zzcxzzzbt);//��תվ��ѡ�������
		 		 
		 bcx.setOnClickListener//Ϊ��ѯ��ť��Ӽ���
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					if(!isLegal())
					{
						return;
					}

					AutoCompleteTextView zzcx_cfz = (AutoCompleteTextView) findViewById(R.id.EditText01);//����վ
					AutoCompleteTextView zzcx_zzz = (AutoCompleteTextView) findViewById(R.id.zzcxzzz);//��תվ
					AutoCompleteTextView zzcx_zdz= (AutoCompleteTextView) findViewById(R.id.zzcxzdz);//�յ�վ
					
					String start=zzcx_cfz.getText().toString().trim();//�õ���Ӧ���ı�
					String end =zzcx_zdz.getText().toString().trim();
					String between=zzcx_zzz.getText().toString().trim();
					
					
					Vector<Vector<String>> temp;
					if(zzzcx.isChecked()==true)//�����תվ��ѯ��ť��ѡ�У��������תվ��ѯ
					{
						 temp= LoadUtil.Zjzquery(start, between, end);//������תվ��ѯ
						 if(temp.size()==0)//�����ѯ�����������Ϊ0�����޲�ѯ���
							{
								Toast.makeText(LCCXActivity.this, "û���������ҵ���תվ·��!!!", Toast.LENGTH_SHORT).show();
								zzcx_cfz.setText("");zzcx_zzz.setText("");zzcx_zdz.setText("");
								return;
							}
						 
					}else //�������վվ��ѯ
					{
						temp= LoadUtil.getSameVector(start, end);
						if(temp.size()==0)
						{
							Toast.makeText(LCCXActivity.this, "�Բ���û����ص��г���Ϣ!!!", Toast.LENGTH_SHORT).show();
							zzcx_cfz.setText("");zzcx_zzz.setText("");zzcx_zdz.setText("");
							return;
						}
					}

					zzcx_cfz=null;//����������������Ϊ��
					zzcx_zdz=null;
					zzcx_zzz=null;
					
					String[][] msgInfo=new String[temp.elementAt(0).size()][temp.size()];//�½��ͽ��������Ӧ������
					for(int i=0;i<temp.size();i++)
					{//forѭ������������е����ݵ�������
						for(int j=0;j<temp.elementAt(0).size();j++)
						{
							msgInfo[j][i]=(String)temp.get(i).get(j);
						}
					}
					goToListView(msgInfo);//�л�����ѯ�����ʾ����ListView����
				}	
			}
		 );
		 bfh.setOnClickListener//Ϊ���ذ�ť��Ӽ���
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					goToMainMenu();	//���ص����˵�����
				}	
			}
		 );		
		 //����������
			
	 }
	 public void goTocccxView()//ȥ���β�ѯ����
	 {
		 setContentView(R.layout.cccx);//�л������β�ѯ����
		 curr=WhichView.CCCX_VIEW;//��ʶ����
		 flag=1;
		 Button bcx=(Button) findViewById(R.id.cccx_cx);
		 Button bfh=(Button) findViewById(R.id.cccx_fh);
		 bcx.setOnClickListener
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					if(!isLegal())//��������������������򷵻�
					{						
						return;
					}
					AutoCompleteTextView cccx_cc= (AutoCompleteTextView) findViewById(R.id.cccxcc);//�õ���������������
					 String cccxcc=cccx_cc.getText().toString().trim();//�õ����е��ı�
					 Vector<Vector<String>> temp= LoadUtil.trainSearch(cccxcc);//���ù��ߺ�����ѯ�õ������
					 cccx_cc=null;
					 if(temp.size()==0)//��������������Ϊ0��˵��û�в�ѯ��������޴˳��������Ϣ
						{
							Toast.makeText(LCCXActivity.this, "û�������Ϣ!!!", Toast.LENGTH_SHORT).show();
							return;
						}
					 String[][] msgInfo=new String[temp.elementAt(0).size()][temp.size()];//�½���Ӧ������������					 
						for(int i=0;i<temp.size();i++)//���������е����ݵ����Ӧ������
						{
							for(int j=0;j<temp.elementAt(i).size();j++)
							{
								msgInfo[j][i]=(String)temp.get(i).get(j);
							}
						}						
						goToListView(msgInfo);//�л��������ʾ����ListView����
				}	
			}
		 );
		 bfh.setOnClickListener//Ϊ���ذ�ť��Ӽ���
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					goToMainMenu();	//���ص��˵�����
				}	
			}
		 );
		 
	 }
	 public void goToczcccxView()//ȥ��վ���г��β�ѯ
	 {
		 setContentView(R.layout.czcx);//�л�����վ��ѯ����
		 curr=WhichView.CZCCCX_VIEW;//��ʶ����
		 flag=2;//��ʶ���ڽ���Ϊ���β�ѯ����
		 Button bcx=(Button) findViewById(R.id.czcx_cx);//�õ���ѯ��ť������
		 Button bfh=(Button) findViewById(R.id.czcx_fh);//�õ����ذ�ť������
		 
		 iniTLisitarray(R.id.czcxwb);//Ϊ��վ�ı������������������ı��������ʾ����
		 bcx.setOnClickListener//Ϊ��ѯ��ť��Ӽ���
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					if(!isLegal())//���ĳ���ı��򲻺Ϲ����򷵻�
					{
						return;
					}
					AutoCompleteTextView czcx_czzm= (AutoCompleteTextView) findViewById(R.id.czcxwb);//�õ���վ����������
					String czcxczzm=czcx_czzm.getText().toString().trim();//�õ���Ӧ�ı����е��ı�
					 Vector<Vector<String>> temp= stationSearch(czcxczzm);//���ù��ߺ�����ѯ�õ��������
					 czcx_czzm=null;
					 if(temp.size()==0)//�����������ĳ���Ϊ0��˵��û�������Ϣ
						{
							Toast.makeText(LCCXActivity.this, "û�������Ϣ!!!", Toast.LENGTH_SHORT).show();
							return;
						}
					 String[][] msgInfo=new String[temp.elementAt(0).size()][temp.size()];//���򴴽���Ӧ�ڽ������������
						for(int i=0;i<temp.size();i++)//����������е����ݵ�������
						{
							for(int j=0;j<temp.elementAt(0).size();j++)
							{
								msgInfo[j][i]=(String)temp.get(i).get(j);
								
							}
						}
						
					goToListView(msgInfo);
				}	
			}
		 );
		 bfh.setOnClickListener//Ϊ���ذ�ť��Ӽ���
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					goToMainMenu();	//�л������˵�����
				}	
			}
		 );
		 
	 }
	 public void goTofjgnView()//ȥ���ӹ��ܽ���
	 {
		 setContentView(R.layout.fjgnmenu);//�л������ӹ��ܽ���
		 curr=WhichView.FJGN_VIEW;//��ʶ��ǰ���ڽ���Ϊ���ӹ��ܽ���
		 ImageButton ibcctj=(ImageButton)findViewById(R.id.ibcctj);//�õ�������Ӱ�ť����
	     ImageButton ibcztj=(ImageButton)findViewById(R.id.ibcztj);//�õ���վ��Ӱ�ť����
		 ImageButton ibgxtj=(ImageButton)findViewById(R.id.ibgxtj);//�õ���ϵ��Ӱ�ť������
		 ibcctj.setOnClickListener//������Ӱ�ť�ļ���
		 (
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					goTocctjView();//ȥ������ӽ���
				}
			   }
		 );
		 ibcztj.setOnClickListener//��վ��Ӱ�ť�ļ���
		 (
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					goTocztjView();//�л�����վ��ӽ���
				}
			   }
		 );
		 ibgxtj.setOnClickListener//��ϵ��Ӱ�ť�ļ���
		 (
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					goTogxtjView();
				}
			   }
		 );
	 }
	 public void goTocctjView()//ȥ������ӽ���
	 {
		 setContentView(R.layout.cctj);//�л�����
		 curr=WhichView.CCTJ_VIEW;//��ʶ����
		 Button bcctjtj=(Button)findViewById(R.id.cctj_tj);//�õ���Ӱ�ť��һ����
		 Button bcctjfh=(Button)findViewById(R.id.cctj_fh);//�õ����ذ�ť������
		 iniTLisitarray(R.id.cctj_sfz);//Ϊʼ��վ���յ�վ�ı������������
		 iniTLisitarray(R.id.cctj_zdz);		 
		 final int tid=LoadUtil.getInsertId("train","Tid")+1;//�õ���ʱ��վ����TID�е����ID��Ȼ���1�ó�Ҫ����˳��ε�ID��
		 bcctjtj.setOnClickListener//Ϊ��Ӱ�ť��Ӽ���
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					if(!isLegal())//�ж�������Ƿ���Ϲ���
					{
						return;
					}
					AutoCompleteTextView cctjcnm=(AutoCompleteTextView)findViewById(R.id.cctj_cm);//�õ�������������
					 AutoCompleteTextView cctjclx=(AutoCompleteTextView)findViewById(R.id.cctj_lclx);
					 AutoCompleteTextView cctjcsf=(AutoCompleteTextView)findViewById(R.id.cctj_sfz);
					 AutoCompleteTextView cctjczd=(AutoCompleteTextView)findViewById(R.id.cctj_zdz);
					 String cnm=cctjcnm.getText().toString().trim();
					 String clx=cctjclx.getText().toString().trim();
					 String csf=cctjcsf.getText().toString().trim();
					 String czd=cctjczd.getText().toString().trim();
					 String sql="select * from train where Tname='" +cnm+//�鿴�Ƿ��иó���
					"'";
					Vector<Vector<String>> ss=query(sql);					
					if(ss.size()>0)
					{
						Toast.makeText(LCCXActivity.this, "�Բ����Ѿ����˴˳���!!!", Toast.LENGTH_SHORT).show();
						return;
					}
					 sql ="select Sid from station where Sname='"+csf+"'";
					if(query(sql).size()==0)//�鿴�Ƿ��иó�վ
					{
						Toast.makeText(LCCXActivity.this, "�Բ��𣬸�ʼ��վ������!!!", Toast.LENGTH_SHORT).show();
						return;
					}
					sql="select Sid from station where Sname='"+czd+"'";//�鿴�Ƿ��иó�վ
					if(query(sql).size()==0)
					{
						Toast.makeText(LCCXActivity.this, "�Բ��𣬸��յ�վ������!!!", Toast.LENGTH_SHORT).show();
						return;
					}
					
					
					
					sql="insert into train values(" +
					tid +",'" +cnm+"','" +csf +"'" +",'" +czd +"','" +clx +"')";//��ӹ�ϵ
					if(!insert(sql))//���ʧ��
					{
					Toast.makeText(LCCXActivity.this, "�Բ������ʧ��!!!", Toast.LENGTH_SHORT).show();
						
					}else{//����Ϊ��ӳɹ�
						Toast.makeText(LCCXActivity.this, "��ϲ�㣬��ӳɹ�!!!", Toast.LENGTH_SHORT).show();
					}
					
				}	
			}
		 );
		 bcctjfh.setOnClickListener//Ϊ���ذ�ť��Ӽ���
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
				  goTofjgnView();//���ص����ӹ��ܽ���
				}	
			}
		 );
		 
	 }
	 public void goTocztjView()//ȥ��վ��ӽ���
	 {
		 setContentView(R.layout.cztj);//�л�����
		 curr=WhichView.CZTJ_VIEW;//��ʶ����
		 Button bcztjtj=(Button)findViewById(R.id.cztj_tj);//�õ���Ӱ�ť������
		 Button bcztjfh=(Button)findViewById(R.id.cztj_fh);//�õ����ذ�ť������
		
		 
		 
		 final int sid=LoadUtil.getInsertId("station","Sid")+1;//���SId��������ID����1�õ���ʱ��Ҫ����ĳ�վ��ID
		 bcztjtj.setOnClickListener//Ϊ��Ӱ�ť��Ӽ���
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					if(!isLegal())
					{
						return;
					}
					 EditText cztjmc=(EditText)findViewById(R.id.et_cztj_czmc);//�õ���������е�����
					 EditText cztjjc=(EditText)findViewById(R.id.et_cztj_czjc);
					String cnm=cztjmc.getText().toString().trim();//�õ���Ӧ���ı�
					String clx=cztjjc.getText().toString().trim();
					 if(!clx.matches("[a-zA-Z]+"))//����ʽƥ�䣬�鿴���������е��ı��Ƿ���϶�����ĸ�Ĺ���
					{
						//����ƥ����Ϣ
						 Toast.makeText(LCCXActivity.this, "�Բ��𣬼��ֻ��Ϊ��ĸ!!!", Toast.LENGTH_SHORT).show();
							return;
					}
					
					String sql="select * from station where Sname='" +cnm+
					"'";
					Vector<Vector<String>> ss=query(sql);//�鿴�ó�վ�Ƿ��Ѿ�����					
					if(ss.size()>0)//�����������ĳ��ȴ���0��˵���Ѿ����˸ó���
					{
						Toast.makeText(LCCXActivity.this, "�Բ����Ѿ����˴˳�վ!!!", Toast.LENGTH_SHORT).show();
						return;
					}
					sql="insert into station values(" +sid +	",'" +cnm +	"','" +	clx +"')";
					if(!insert(sql))//���в����������������ʧ��
					{
					Toast.makeText(LCCXActivity.this, "�Բ������ʧ��!!!", Toast.LENGTH_SHORT).show();
						return;
					}else{//����Ϊ��ӳɹ�
						iniTLisit();
						Toast.makeText(LCCXActivity.this, "��ϲ�㣬��ӳɹ�!!!", Toast.LENGTH_SHORT).show();
					}
				}	
			}
		 );
		 bcztjfh.setOnClickListener//Ϊ���ذ�ť��Ӽ���
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
				  goTofjgnView();//���ص����ӹ��ܽ���
				}	
			}
		 );
	 }
	 public void goTogxtjView()//ȥ��ϵ��ӽ���
	 {
		 setContentView(R.layout.gxtj);//�л�����
		 curr=WhichView.GXTJ_VIEW;//��ʶ����
		 Button bgxtjtj=(Button)findViewById(R.id.gxtj_tj);//�õ���Ӱ�ť������
		 Button bgxtjfh=(Button)findViewById(R.id.gxtj_fh);//�õ����ذ�ť������
		
		 iniTLisitarray(R.id.et_gxtj_zm);//Ϊ��վ�������������
		 
		 bgxtjtj.setOnClickListener//Ϊ��Ӱ�ť��Ӽ���
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					EditText gxtjcnm=(EditText)findViewById(R.id.et_gxtj_cm);//�õ���������������
					AutoCompleteTextView gxtjclx=(AutoCompleteTextView)findViewById(R.id.et_gxtj_zm);//�õ�վ������������
					EditText gxtjcsf=(EditText)findViewById(R.id.et_gxtj_dzsj);//�õ���վʱ������������
					EditText gxtjczd=(EditText)findViewById(R.id.et_gxtj_kcsj);//�õ�����ʱ������������
					
					String cnm=gxtjcnm.getText().toString().trim();//�õ���Ӧ���ı���Ϣ
					String znm=gxtjclx.getText().toString().trim();
					String dct=gxtjcsf.getText().toString().trim();
					String fct=gxtjczd.getText().toString().trim();
					 
					int Rid=LoadUtil.getInsertId("relation","Rid")+1;//���relation��������ID��1�õ���ǰ����Ĺ�ϵ��ID
					 
					int cnmm=0;//���ζ�Ӧ��ID
					int cznm=0;//��վ��Ӧ��ID
					
					if(!isLegal())
					{
						return;
					}					
					
					String sql = "select Tid "+
					"from train where Tname='"+cnm+"'";
					Vector<Vector<String>> ss=query(sql);					
					if(ss.size()>0)//�õ����ζ�Ӧ��ID
					{
						cnmm=Integer.parseInt((String)ss.get(0).get(0));						
					}else if(ss.size()==0){
						Toast.makeText(LCCXActivity.this, "�Բ���û�иó�!!!", Toast.LENGTH_SHORT).show();
					return;
					}
					sql="select Sid from station where Sname='"+znm+"'";				
					ss=query(sql);
					if(ss.size()>0)//�õ���վ��Ӧ��ID
					{
						cznm=Integer.parseInt((String)ss.get(0).get(0));						
					}
					else if(ss.size()==0){
						Toast.makeText(LCCXActivity.this, "�Բ���û�и�վ!!!", Toast.LENGTH_SHORT).show();
						return;
					}
					
					sql="select Rid from relation where Sid="+cznm+" and Tid="+cnmm;//���в鿴�ù�ϵ�Ƿ��Ѿ�����
				
					if(query(sql).size()>0)//����Ѿ�����
					{
					Toast.makeText(LCCXActivity.this, "�Բ��𣬸ù�ϵ�Ѿ�����!!!", Toast.LENGTH_SHORT).show();
					return;
					}//������в������
					sql="insert into relation values(" +
					Rid +
							"," +
							cnmm +
							"," +
							cznm +
							",'" +
							dct +
							"','" +
							fct +
							"')";
					
					if(!insert(sql))//�������ʧ��
					{
					Toast.makeText(LCCXActivity.this, "�Բ������ʧ��!!!", Toast.LENGTH_SHORT).show();
					return;
					}else{
						Toast.makeText(LCCXActivity.this, "��ϲ�㣬��ӳɹ�!!!", Toast.LENGTH_SHORT).show();
					}
				}	
			}
		 );
		 bgxtjfh.setOnClickListener//Ϊ���ذ�ť��Ӽ���
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
				  goTofjgnView();//���ص����ӹ��ܽ���
				}	
			}
		 );
	 }
	 public void goToListView(String[][]mssg)//ȥListView����
	 {
		 	msgg=mssg;//��ֵ���ø�ȫ�����飬����ʵ�ַ��ذ�ť����
	        setContentView(R.layout.listview);//�л�����
	        curr=WhichView.LIST_VIEW;//��ʶ����
	        final String[][]msg=mssg;//�½����飬����ֵ
	        ListView lv_detail=(ListView)this.findViewById(R.id.ListView_detail);//�õ�ListView������
	        BaseAdapter ba_detail=new BaseAdapter()//�½�������
	        {
				@Override
				public int getCount() 
				{
					return msg[0].length;//�õ��б�ĳ���
				}
				@Override
				public Object getItem(int arg0){return null;}
				@Override
				public long getItemId(int arg0){return 0;}
				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2)//Ϊÿһ���������
				{
					LinearLayout ll_detail=new LinearLayout(LCCXActivity.this);
					ll_detail.setOrientation(LinearLayout.HORIZONTAL);		//���ó���	
					ll_detail.setPadding(5,5,5,5);//��������

					for(int i=0;i<msg.length;i++)//Ϊÿһ��������ʾ������
					{					    
						TextView s= new TextView(LCCXActivity.this);
						s.setText(msg[i][arg0]);//TextView����ʾ������
						s.setTextSize(14);//�����С
						s.setTextColor(getResources().getColor(R.color.black));//������ɫ
						s.setPadding(1,2,2,1);//��������
						s.setWidth(60);//���
					    s.setGravity(Gravity.CENTER);
					    ll_detail.addView(s);//����LinearLayout
					}
					return ll_detail;//����LinearLayout����
				}        	
	        };    
	        lv_detail.setAdapter(ba_detail);//����������ӽ�ListView
	        
	        lv_detail.setOnItemClickListener//Ϊ�б���Ӽ���
	        (
	           new OnItemClickListener()
	           {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,//������б��е�ĳһ��ʱ���ô˺���
						long arg3) //arg2Ϊ����ĵڼ���
				{
					String cccx=msg[0][arg2];//ȡ����Ӧ���ж�Ӧ�ĳ�����Ϣ
					
					 Vector<Vector<String>> temp= LoadUtil.getInfo(cccx);//��ѯ�ó��ξ��������г�վ
					 if(temp.size()==0)//�ж��Ƿ��в�ѯ���
						{
							Toast.makeText(LCCXActivity.this, "û�������Ϣ!!!", Toast.LENGTH_SHORT).show();
							return;
						}
					 String[][] msgInfo=new String[temp.elementAt(0).size()][temp.size()];//������򽫽�������Ӧ������
						for(int i=0;i<temp.size();i++)
						{
							for(int j=0;j<temp.elementAt(0).size();j++)
							{
								msgInfo[j][i]=(String)temp.get(i).get(j);								
							}
						}
						msgg=msg;
						goToPassStationView(msgInfo);//�л������ξ��������ʾ����

				}        	   
	           }
	        );
	 }
	//ĳ�г����������г�վ��ȥ������վ����
	 public void goToPassStationView(String[][]mssg)
	 {
		 setContentView(R.layout.passstation);//�л�����
		 curr=WhichView.PASSSTATION_VIEW;//��ʶ����
	        ListView lv_detail=(ListView)this.findViewById(R.id.ListView_passstation);//�õ�ListView������
	        final String[][]msg=mssg;
	       
	        BaseAdapter ba_detail=new BaseAdapter()//�½�������
	        {
				@Override
				public int getCount() 
				{
					return msg[0].length;//�õ��б�ĳ���
				}
				@Override
				public Object getItem(int arg0){return null;}
				@Override
				public long getItemId(int arg0){return 0;}
				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2)
				{
					LinearLayout ll_detail=new LinearLayout(LCCXActivity.this);
					ll_detail.setOrientation(LinearLayout.HORIZONTAL);		//���ó���	
					ll_detail.setPadding(5,2,2,4);//������������
					TextView []tv=
					{
					   new TextView(LCCXActivity.this),new TextView(LCCXActivity.this),new TextView(LCCXActivity.this)
					};
					for(int i=0;i<msg.length;i++)//����ÿһ������ʾ������
					{
						tv[i].setText(msg[i][arg0]);//ÿ��TextView�е��ı�
						tv[i].setTextSize(13);//�����С
						tv[i].setTextColor(getResources().getColor(R.color.black));//������ɫ
						tv[i].setPadding(5,2,3,2);//��������
						tv[i].setWidth(150);//���
					    tv[i].setGravity(Gravity.CENTER);
					    ll_detail.addView(tv[i]);//��ӽ�LinearLayout

					}
					return ll_detail;//����LinearLayout����
				}        	
	        };    
	        lv_detail.setAdapter(ba_detail);//����������ӽ��б�
	        
	 }
	 //�鿴��ĳ�������е����ѯ��ťʱ���ж�������Ƿ�Ϊ��
	 public boolean isLegal()
	 {
		 if(curr==WhichView.ZZCX_VIEW)//�����ǰΪվվ��ѯ���棬����Ӧ���ı�����кϷ���֤
		 {
			EditText etcfz=(EditText)findViewById(R.id.EditText01);//����վ
			EditText etzzz=(EditText)findViewById(R.id.zzcxzzz);//��תվ
			EditText etzdz=(EditText)findViewById(R.id.zzcxzdz);//�յ�վ
			CheckBox cbzzz=(CheckBox)findViewById(R.id.zzcxzzzbt);//��תվ��ѡ��
			if(etcfz.getText().toString().trim().equals(""))//����վΪ��
			{
				Toast.makeText(this, "����վ����Ϊ�գ�����",Toast.LENGTH_LONG).show();
				return false;
			}
			if(etzzz.getText().toString().trim().equals("")&&cbzzz.isChecked())//��תվΪ��
			{
				Toast.makeText(this, "��תվ����Ϊ�գ�����",Toast.LENGTH_LONG).show();
				return false;
			}
			if(etzdz.getText().toString().trim().equals(""))//�յ�վΪ��
			{
				Toast.makeText(this, "�յ�վ����Ϊ�գ�����",Toast.LENGTH_LONG).show();
				return false;
			}
			if(etcfz.getText().toString().trim().contentEquals(etzdz.getText().toString().trim()))//����վ���յ�վ��ͬ
			{
				Toast.makeText(this, "����վ���յ�վ������ͬ������",Toast.LENGTH_LONG).show();
				return false;
			}
			if(cbzzz.isChecked()&&etcfz.getText().toString().trim().contentEquals(etzzz.getText().toString().trim()))//����վ����תվ��ͬ
			{
				Toast.makeText(this, "����վ����תվ������ͬ������",Toast.LENGTH_LONG).show();
				return false;
		    }
			if(cbzzz.isChecked()&&etzdz.getText().toString().trim().contentEquals(etzzz.getText().toString().trim()))//�յ�վ����תվ
			{
				Toast.makeText(this, "�յ�վ����תվ������ͬ������",Toast.LENGTH_LONG).show();
				return false;
			}
		 }
		 if(curr==WhichView.CCCX_VIEW)//�����ǰΪ���β�ѯ����
		 {
			 EditText etcccx=(EditText)findViewById(R.id.cccxcc);
			 if(etcccx.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "���β���Ϊ�գ�����",Toast.LENGTH_LONG).show();
					return false;
			 }
		 }
		 if(curr==WhichView.CZCCCX_VIEW)//�����ǰΪ��վ���β�ѯ����
		 {
			 EditText etczcccx=(EditText)findViewById(R.id.czcxwb);
			 if(etczcccx.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "��վ����Ϊ�գ�����",Toast.LENGTH_LONG).show();
					return false;
			 }
		 }
		 if(curr==WhichView.CCTJ_VIEW)//�����ǰ�ڳ������
		 {
			 EditText et_cm=(EditText)findViewById(R.id.cctj_cm);//����
			 EditText et_lclx=(EditText)findViewById(R.id.cctj_lclx);//�г�����
			 EditText et_sfz=(EditText)findViewById(R.id.cctj_sfz);//ʼ��վ
			 EditText et_zdz=(EditText)findViewById(R.id.cctj_zdz);//�յ�վ
			 if(et_cm.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "��������Ϊ�գ�����",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 if(et_lclx.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "�г����Ͳ���Ϊ�գ�����",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 if(et_sfz.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "ʼ��վ����Ϊ�գ�����",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 if(et_zdz.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "�յ�վ����Ϊ�գ�����",Toast.LENGTH_SHORT).show();
					return false;
			 }	 
		 }
		 if(curr==WhichView.CZTJ_VIEW)//�����ǰ�ڳ�վ��ӽ���
		 {
			 EditText et_czmc=(EditText)findViewById(R.id.et_cztj_czmc);//��վ����
			 EditText et_czjc=(EditText)findViewById(R.id.et_cztj_czjc);//��վ���
			 if(et_czmc.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "��վ���Ʋ���Ϊ�գ�����",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 if(et_czjc.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "��վ��Ʋ���Ϊ�գ�����",Toast.LENGTH_SHORT).show();
					return false;
			 }
		 }
		 if(curr==WhichView.GXTJ_VIEW)//�����ǰ�ڹ�ϵ��ӽ���
		 {
			 EditText et_cm=(EditText)findViewById(R.id.et_gxtj_cm);//����
			 EditText et_zm=(EditText)findViewById(R.id.et_gxtj_zm);//վ��
			 
			 if(et_cm.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "��������Ϊ�գ�����",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 if(et_zm.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "վ������Ϊ�գ�����",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 
		 }
		 return true;
	 } 
	 @Override
	  public boolean onKeyDown(int keyCode, KeyEvent e)//���̼���
	  { 
	    	if(keyCode!=4)//������ǰ��µķ��ذ�ťʱ�����κδ���ֱ�ӷ���
	    	{
	    		return false;
	    	}
	    	if(curr==WhichView.ZZCX_VIEW|| curr==WhichView.CCCX_VIEW||curr==WhichView.CZCCCX_VIEW||
	    			curr==WhichView.FJGN_VIEW)//վվ��ѯ//���β�ѯ//��վ��ѯ//���ӹ���
	    	{
	    		goToMainMenu();//���ص����˵�����
	    		return true;
	    	}
	    	if(curr==WhichView.CCTJ_VIEW|| curr==WhichView.CZTJ_VIEW||curr==WhichView.GXTJ_VIEW)
	    	{//����ǳ�����ӡ���վ��Ӻ͹�ϵ��ӽ���
	    		goTofjgnView();//���ص����ӽ���
	    		return true;
	    	}	    
	    	
	    	if(curr==WhichView.MAIN_MENU)
	    	{
	    		System.exit(0);//����������˵��а��·��ذ�ť����ֱ���˳�
	    		return true;
	    	}
	    	if(curr==WhichView.PASSSTATION_VIEW)//������ڳ�����ϸ�����ʾ����
	    	{
	    		goToListView(msgg);//���ص�ListView����
	    		return true;
	    	}
	    	
	    	if(curr==WhichView.LIST_VIEW)//�������ListView���棬����ݵ�ǰ�������
	    	{
	    		if(flag==0)//���Ϊվվ��ѯ����
	    		{
	    			goTozzcxView();
		    		return true;
	    		}
	    		else if(flag==1)//����ǳ��β�ѯ����
	    		{
	    			goTocccxView();
		    		return true;
	    		}
	    		else//����Ϊ��վ��ѯ����
	    		{
	    			goToczcccxView();
		    		return true;
	    		}
	    	}
	    	if(curr==WhichView.ABOUT_VIEW||curr==WhichView.HELP_VIEW)//����ǹ��ںͰ�������
	    	{
	    		
	    		goToMainMenu();//���ص����˵�����
	    		return true;
	    	}
	    	return false;
	 }
	 
	 public void iniTLisit()//��ʼ������������Ҫ�����ݵĺ���
	    {
		 
		 String sql = "select Sname from station";//������г�վ����
		 
		 Vector<Vector<String>> temp= LoadUtil.query(sql);
			String[][] msgInfo=new String[temp.get(0).size()][temp.size()];
			for(int i=0;i<temp.size();i++)
			{
				for(int j=0;j<temp.elementAt(0).size();j++)
				{
					msgInfo[j][i]=(String)temp.get(i).get(j);
				}
			}
			this.s1=msgInfo[0];//�õ�������
		 sql="select Spy from station";//������г�վ���ֵļ��
		 
		 temp= LoadUtil.query(sql);

			msgInfo=new String[temp.elementAt(0).size()][temp.size()];
			for(int i=0;i<temp.size();i++)
			{
				for(int j=0;j<temp.elementAt(0).size();j++)
				{
					msgInfo[j][i]=(String)temp.get(i).get(j);
				}
			}
			this.s2=msgInfo[0];//�õ�������

	    }
	 
	 public void iniTLisitarray(int id)//Ϊ��ӦID����������������
	 {
		 CityAdapter<String> cAdapter = new CityAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,this.s1,this.s2); //������ʾ���е���������  
	        AutoCompleteTextView autoView=(AutoCompleteTextView)findViewById(id);//����Ҫ�����ʾ��Ϣ�������
	        autoView.setAdapter(cAdapter);   //���������
	        autoView.setThreshold(1);
	        autoView.setDropDownHeight(100) ;
	        autoView.setDropDownBackgroundResource(R.color.gray);
	 }

	
}