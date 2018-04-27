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
	WelcomeView wv;//进入欢迎界面
	WhichView curr;//当前枚举值	
	static int flag;//设置页面的标志位　　　０　站站查询　１　车次查询　２　车站查询	
	
	
	String[][]msgg=new String[][]{{""}};//声明引用
	
	
	String s1[];
	String s2[];
	
	
	Handler hd=new Handler()//声明消息处理器
	{
			@Override
			public void handleMessage(Message msg)//重写方法
        	{
        		switch(msg.what)
        		{
	        		case 0://进入欢迎界面
	        			goToWelcomeView();
	        			
	        		break;
	        		case 1://进入菜单界面
	        			goToMainMenu();       			
	        		break;
	        		case 2://进入关于界面
	        			setContentView(R.layout.about);
	        	    	curr=WhichView.ABOUT_VIEW;
	        			break;
	        		case 3://进入帮助界面
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
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//设置横屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		
		CreatTable.creattable();//建表
		iniTLisit();//初始化数组
		this.hd.sendEmptyMessage(0);						//发送消息进入欢迎界面
    }
	
	 public void goToWelcomeView()
	    {
	    	if(wv==null)//如果该对象没创建则创建
	    	{
	    		wv=new WelcomeView(this);
	    	}
	    	setContentView(wv);
	    	curr=WhichView.WELCOME_VIEW;//标识当前所在界面
	    }
	public void goToMainMenu()//去主菜单
	 {	
	      	setContentView(R.layout.main);	
	      	curr=WhichView.MAIN_MENU;
			//拿到主菜单中个按钮的引用
			ImageButton ibzzcx=(ImageButton)findViewById(R.id.ibzzcx);
			ImageButton ibcccx=(ImageButton)findViewById(R.id.ibcccx);
			ImageButton ibczcccx=(ImageButton)findViewById(R.id.ibczcccx);
			ImageButton ibfjgn=(ImageButton)findViewById(R.id.ibfjgn);
			ImageButton ibabout=(ImageButton)findViewById(R.id.about_button);
			ImageButton ibhelp=(ImageButton)findViewById(R.id.help_button);
			ibabout.setOnClickListener//关于按钮的监听
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					
					hd.sendEmptyMessage(3);//发消息进入关于界面					
				}
			   }
			);
			ibhelp.setOnClickListener//帮助查询的监听
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{

					hd.sendEmptyMessage(2);	//发消息进入帮助界面
				}
			   }
			);
			ibzzcx.setOnClickListener//站站查询按钮的监听
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					goTozzcxView();//进入站站查询模块
				}
			   }
			);
			ibcccx.setOnClickListener//车次查询按钮的监听
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					goTocccxView();//进入车次查询模块
				}   
			   }
			);
			ibczcccx.setOnClickListener//车站所有车次查询
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					goToczcccxView();//进入车站查询模块
				}   
			   }
			); 
			ibfjgn.setOnClickListener//附加功能按钮的监听
			(
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
                    goTofjgnView();//进入附加功能模块
				}
			   }
			);
	 }
	 public void goTozzcxView()//去站站查询
	 {
		 setContentView(R.layout.zzcx);
		 curr=WhichView.ZZCX_VIEW;
		 flag=0;//标志位
		
		 Button bcx=(Button) findViewById(R.id.zzcxbt);//查询按钮
		 Button bfh=(Button) findViewById(R.id.zzcxfhbt);//返回按钮
		
		 iniTLisitarray(R.id.EditText01);//为各个车站输入文本框添加适配器
		 iniTLisitarray(R.id.zzcxzzz);
		 iniTLisitarray(R.id.zzcxzdz); 

		 final CheckBox zzzcx=(CheckBox)findViewById(R.id.zzcxzzzbt);//中转站复选框的引用
		 		 
		 bcx.setOnClickListener//为查询按钮添加监听
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

					AutoCompleteTextView zzcx_cfz = (AutoCompleteTextView) findViewById(R.id.EditText01);//出发站
					AutoCompleteTextView zzcx_zzz = (AutoCompleteTextView) findViewById(R.id.zzcxzzz);//中转站
					AutoCompleteTextView zzcx_zdz= (AutoCompleteTextView) findViewById(R.id.zzcxzdz);//终点站
					
					String start=zzcx_cfz.getText().toString().trim();//得到相应的文本
					String end =zzcx_zdz.getText().toString().trim();
					String between=zzcx_zzz.getText().toString().trim();
					
					
					Vector<Vector<String>> temp;
					if(zzzcx.isChecked()==true)//如果中转站查询按钮被选中，则进行中转站查询
					{
						 temp= LoadUtil.Zjzquery(start, between, end);//进行中转站查询
						 if(temp.size()==0)//如果查询结果向量长度为0，则无查询结果
							{
								Toast.makeText(LCCXActivity.this, "没有你所查找的中转站路线!!!", Toast.LENGTH_SHORT).show();
								zzcx_cfz.setText("");zzcx_zzz.setText("");zzcx_zdz.setText("");
								return;
							}
						 
					}else //否则进行站站查询
					{
						temp= LoadUtil.getSameVector(start, end);
						if(temp.size()==0)
						{
							Toast.makeText(LCCXActivity.this, "对不起，没有相关的列车信息!!!", Toast.LENGTH_SHORT).show();
							zzcx_cfz.setText("");zzcx_zzz.setText("");zzcx_zdz.setText("");
							return;
						}
					}

					zzcx_cfz=null;//将个输入框的引用置为空
					zzcx_zdz=null;
					zzcx_zzz=null;
					
					String[][] msgInfo=new String[temp.elementAt(0).size()][temp.size()];//新建和结果向量对应的数组
					for(int i=0;i<temp.size();i++)
					{//for循环将结果向量中的数据导入数组
						for(int j=0;j<temp.elementAt(0).size();j++)
						{
							msgInfo[j][i]=(String)temp.get(i).get(j);
						}
					}
					goToListView(msgInfo);//切换到查询结果显示界面ListView界面
				}	
			}
		 );
		 bfh.setOnClickListener//为返回按钮添加监听
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					goToMainMenu();	//返回到主菜单界面
				}	
			}
		 );		
		 //建立适配器
			
	 }
	 public void goTocccxView()//去车次查询界面
	 {
		 setContentView(R.layout.cccx);//切换到车次查询界面
		 curr=WhichView.CCCX_VIEW;//标识界面
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
					if(!isLegal())//如果各个输入框不满足规则则返回
					{						
						return;
					}
					AutoCompleteTextView cccx_cc= (AutoCompleteTextView) findViewById(R.id.cccxcc);//得到车次输入框的引用
					 String cccxcc=cccx_cc.getText().toString().trim();//得到其中的文本
					 Vector<Vector<String>> temp= LoadUtil.trainSearch(cccxcc);//调用工具函数查询得到结果集
					 cccx_cc=null;
					 if(temp.size()==0)//如果结果向量长度为0，说明没有查询结果，即无此车次相关信息
						{
							Toast.makeText(LCCXActivity.this, "没有相关信息!!!", Toast.LENGTH_SHORT).show();
							return;
						}
					 String[][] msgInfo=new String[temp.elementAt(0).size()][temp.size()];//新建对应于向量的数组					 
						for(int i=0;i<temp.size();i++)//否则将向量中的数据导入对应的数组
						{
							for(int j=0;j<temp.elementAt(i).size();j++)
							{
								msgInfo[j][i]=(String)temp.get(i).get(j);
							}
						}						
						goToListView(msgInfo);//切换到结果显示界面ListView界面
				}	
			}
		 );
		 bfh.setOnClickListener//为返回按钮添加监听
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					goToMainMenu();	//返回到菜单界面
				}	
			}
		 );
		 
	 }
	 public void goToczcccxView()//去车站所有车次查询
	 {
		 setContentView(R.layout.czcx);//切换到车站查询界面
		 curr=WhichView.CZCCCX_VIEW;//标识界面
		 flag=2;//标识所在界面为车次查询界面
		 Button bcx=(Button) findViewById(R.id.czcx_cx);//拿到查询按钮的引用
		 Button bfh=(Button) findViewById(R.id.czcx_fh);//拿到返回按钮的引用
		 
		 iniTLisitarray(R.id.czcxwb);//为车站文本框添加适配器来完成文本输入的提示功能
		 bcx.setOnClickListener//为查询按钮添加监听
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					if(!isLegal())//如果某个文本框不合规则，则返回
					{
						return;
					}
					AutoCompleteTextView czcx_czzm= (AutoCompleteTextView) findViewById(R.id.czcxwb);//拿到车站输入框的引用
					String czcxczzm=czcx_czzm.getText().toString().trim();//得到对应文本框中的文本
					 Vector<Vector<String>> temp= stationSearch(czcxczzm);//调用工具函数查询得到结果向量
					 czcx_czzm=null;
					 if(temp.size()==0)//如果结果向量的长度为0，说明没有相关信息
						{
							Toast.makeText(LCCXActivity.this, "没有相关信息!!!", Toast.LENGTH_SHORT).show();
							return;
						}
					 String[][] msgInfo=new String[temp.elementAt(0).size()][temp.size()];//否则创建对应于结果向量的数组
						for(int i=0;i<temp.size();i++)//将结果向量中的数据导入数组
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
		 bfh.setOnClickListener//为返回按钮添加监听
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					goToMainMenu();	//切换到主菜单界面
				}	
			}
		 );
		 
	 }
	 public void goTofjgnView()//去附加功能界面
	 {
		 setContentView(R.layout.fjgnmenu);//切换到附加功能界面
		 curr=WhichView.FJGN_VIEW;//标识当前所在界面为附加功能界面
		 ImageButton ibcctj=(ImageButton)findViewById(R.id.ibcctj);//拿到车次添加按钮引用
	     ImageButton ibcztj=(ImageButton)findViewById(R.id.ibcztj);//拿到车站添加按钮引用
		 ImageButton ibgxtj=(ImageButton)findViewById(R.id.ibgxtj);//拿到关系添加按钮的引用
		 ibcctj.setOnClickListener//车次添加按钮的监听
		 (
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					goTocctjView();//去车次添加界面
				}
			   }
		 );
		 ibcztj.setOnClickListener//车站添加按钮的监听
		 (
			   new OnClickListener()
			   {
				public void onClick(View v) 
				{
					goTocztjView();//切换到车站添加界面
				}
			   }
		 );
		 ibgxtj.setOnClickListener//关系添加按钮的监听
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
	 public void goTocctjView()//去车次添加界面
	 {
		 setContentView(R.layout.cctj);//切换界面
		 curr=WhichView.CCTJ_VIEW;//标识界面
		 Button bcctjtj=(Button)findViewById(R.id.cctj_tj);//拿到添加按钮的一引用
		 Button bcctjfh=(Button)findViewById(R.id.cctj_fh);//拿到返回按钮的引用
		 iniTLisitarray(R.id.cctj_sfz);//为始发站和终点站文本框添加适配器
		 iniTLisitarray(R.id.cctj_zdz);		 
		 final int tid=LoadUtil.getInsertId("train","Tid")+1;//拿到此时车站表中TID列的最大ID，然后加1得出要插入此车次的ID。
		 bcctjtj.setOnClickListener//为添加按钮添加监听
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					if(!isLegal())//判断输入框是否符合规则
					{
						return;
					}
					AutoCompleteTextView cctjcnm=(AutoCompleteTextView)findViewById(R.id.cctj_cm);//拿到个输入框的引用
					 AutoCompleteTextView cctjclx=(AutoCompleteTextView)findViewById(R.id.cctj_lclx);
					 AutoCompleteTextView cctjcsf=(AutoCompleteTextView)findViewById(R.id.cctj_sfz);
					 AutoCompleteTextView cctjczd=(AutoCompleteTextView)findViewById(R.id.cctj_zdz);
					 String cnm=cctjcnm.getText().toString().trim();
					 String clx=cctjclx.getText().toString().trim();
					 String csf=cctjcsf.getText().toString().trim();
					 String czd=cctjczd.getText().toString().trim();
					 String sql="select * from train where Tname='" +cnm+//查看是否有该车次
					"'";
					Vector<Vector<String>> ss=query(sql);					
					if(ss.size()>0)
					{
						Toast.makeText(LCCXActivity.this, "对不起，已经有了此车次!!!", Toast.LENGTH_SHORT).show();
						return;
					}
					 sql ="select Sid from station where Sname='"+csf+"'";
					if(query(sql).size()==0)//查看是否有该车站
					{
						Toast.makeText(LCCXActivity.this, "对不起，该始发站不存在!!!", Toast.LENGTH_SHORT).show();
						return;
					}
					sql="select Sid from station where Sname='"+czd+"'";//查看是否有该车站
					if(query(sql).size()==0)
					{
						Toast.makeText(LCCXActivity.this, "对不起，该终点站不存在!!!", Toast.LENGTH_SHORT).show();
						return;
					}
					
					
					
					sql="insert into train values(" +
					tid +",'" +cnm+"','" +csf +"'" +",'" +czd +"','" +clx +"')";//添加关系
					if(!insert(sql))//如果失败
					{
					Toast.makeText(LCCXActivity.this, "对不起，添加失败!!!", Toast.LENGTH_SHORT).show();
						
					}else{//否则为添加成功
						Toast.makeText(LCCXActivity.this, "恭喜你，添加成功!!!", Toast.LENGTH_SHORT).show();
					}
					
				}	
			}
		 );
		 bcctjfh.setOnClickListener//为返回按钮添加监听
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
				  goTofjgnView();//返回到附加功能界面
				}	
			}
		 );
		 
	 }
	 public void goTocztjView()//去车站添加界面
	 {
		 setContentView(R.layout.cztj);//切换界面
		 curr=WhichView.CZTJ_VIEW;//标识界面
		 Button bcztjtj=(Button)findViewById(R.id.cztj_tj);//拿到添加按钮的引用
		 Button bcztjfh=(Button)findViewById(R.id.cztj_fh);//拿到返回按钮的引用
		
		 
		 
		 final int sid=LoadUtil.getInsertId("station","Sid")+1;//查出SId列中最大的ID，加1得到此时需要插入的车站的ID
		 bcztjtj.setOnClickListener//为添加按钮添加监听
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
					 EditText cztjmc=(EditText)findViewById(R.id.et_cztj_czmc);//得到个输入框中的引用
					 EditText cztjjc=(EditText)findViewById(R.id.et_cztj_czjc);
					String cnm=cztjmc.getText().toString().trim();//得到对应的文本
					String clx=cztjjc.getText().toString().trim();
					 if(!clx.matches("[a-zA-Z]+"))//正则式匹配，查看简称输入框中的文本是否符合都是字母的规则
					{
						//发不匹配消息
						 Toast.makeText(LCCXActivity.this, "对不起，简称只能为字母!!!", Toast.LENGTH_SHORT).show();
							return;
					}
					
					String sql="select * from station where Sname='" +cnm+
					"'";
					Vector<Vector<String>> ss=query(sql);//查看该车站是否已经存在					
					if(ss.size()>0)//如果结果向量的长度大于0，说明已经有了该车了
					{
						Toast.makeText(LCCXActivity.this, "对不起，已经有了此车站!!!", Toast.LENGTH_SHORT).show();
						return;
					}
					sql="insert into station values(" +sid +	",'" +cnm +	"','" +	clx +"')";
					if(!insert(sql))//进行插入操作，如果是添加失败
					{
					Toast.makeText(LCCXActivity.this, "对不起，添加失败!!!", Toast.LENGTH_SHORT).show();
						return;
					}else{//否则为添加成功
						iniTLisit();
						Toast.makeText(LCCXActivity.this, "恭喜你，添加成功!!!", Toast.LENGTH_SHORT).show();
					}
				}	
			}
		 );
		 bcztjfh.setOnClickListener//为返回按钮添加监听
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
				  goTofjgnView();//返回到附加功能界面
				}	
			}
		 );
	 }
	 public void goTogxtjView()//去关系添加界面
	 {
		 setContentView(R.layout.gxtj);//切换界面
		 curr=WhichView.GXTJ_VIEW;//标识界面
		 Button bgxtjtj=(Button)findViewById(R.id.gxtj_tj);//拿到添加按钮的引用
		 Button bgxtjfh=(Button)findViewById(R.id.gxtj_fh);//拿到返回按钮的引用
		
		 iniTLisitarray(R.id.et_gxtj_zm);//为车站名字添加适配器
		 
		 bgxtjtj.setOnClickListener//为添加按钮添加监听
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					EditText gxtjcnm=(EditText)findViewById(R.id.et_gxtj_cm);//拿到车名输入框的引用
					AutoCompleteTextView gxtjclx=(AutoCompleteTextView)findViewById(R.id.et_gxtj_zm);//拿到站名输入框的引用
					EditText gxtjcsf=(EditText)findViewById(R.id.et_gxtj_dzsj);//拿到到站时间输入框的引用
					EditText gxtjczd=(EditText)findViewById(R.id.et_gxtj_kcsj);//拿到发车时间输入框的引用
					
					String cnm=gxtjcnm.getText().toString().trim();//得到对应的文本信息
					String znm=gxtjclx.getText().toString().trim();
					String dct=gxtjcsf.getText().toString().trim();
					String fct=gxtjczd.getText().toString().trim();
					 
					int Rid=LoadUtil.getInsertId("relation","Rid")+1;//查出relation表中最大的ID加1得到当前插入的关系的ID
					 
					int cnmm=0;//车次对应的ID
					int cznm=0;//车站对应的ID
					
					if(!isLegal())
					{
						return;
					}					
					
					String sql = "select Tid "+
					"from train where Tname='"+cnm+"'";
					Vector<Vector<String>> ss=query(sql);					
					if(ss.size()>0)//得到车次对应的ID
					{
						cnmm=Integer.parseInt((String)ss.get(0).get(0));						
					}else if(ss.size()==0){
						Toast.makeText(LCCXActivity.this, "对不起，没有该车!!!", Toast.LENGTH_SHORT).show();
					return;
					}
					sql="select Sid from station where Sname='"+znm+"'";				
					ss=query(sql);
					if(ss.size()>0)//得到车站对应的ID
					{
						cznm=Integer.parseInt((String)ss.get(0).get(0));						
					}
					else if(ss.size()==0){
						Toast.makeText(LCCXActivity.this, "对不起，没有该站!!!", Toast.LENGTH_SHORT).show();
						return;
					}
					
					sql="select Rid from relation where Sid="+cznm+" and Tid="+cnmm;//进行查看该关系是否已经存在
				
					if(query(sql).size()>0)//如果已经存在
					{
					Toast.makeText(LCCXActivity.this, "对不起，该关系已经有了!!!", Toast.LENGTH_SHORT).show();
					return;
					}//否则进行插入操作
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
					
					if(!insert(sql))//如果插入失败
					{
					Toast.makeText(LCCXActivity.this, "对不起，添加失败!!!", Toast.LENGTH_SHORT).show();
					return;
					}else{
						Toast.makeText(LCCXActivity.this, "恭喜你，添加成功!!!", Toast.LENGTH_SHORT).show();
					}
				}	
			}
		 );
		 bgxtjfh.setOnClickListener//为返回按钮添加监听
		 (
			new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
				  goTofjgnView();//返回到附加功能界面
				}	
			}
		 );
	 }
	 public void goToListView(String[][]mssg)//去ListView界面
	 {
		 	msgg=mssg;//赋值引用给全局数组，用来实现返回按钮功能
	        setContentView(R.layout.listview);//切换界面
	        curr=WhichView.LIST_VIEW;//标识界面
	        final String[][]msg=mssg;//新建数组，并赋值
	        ListView lv_detail=(ListView)this.findViewById(R.id.ListView_detail);//拿到ListView的引用
	        BaseAdapter ba_detail=new BaseAdapter()//新建适配器
	        {
				@Override
				public int getCount() 
				{
					return msg[0].length;//得到列表的长度
				}
				@Override
				public Object getItem(int arg0){return null;}
				@Override
				public long getItemId(int arg0){return 0;}
				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2)//为每一项添加内容
				{
					LinearLayout ll_detail=new LinearLayout(LCCXActivity.this);
					ll_detail.setOrientation(LinearLayout.HORIZONTAL);		//设置朝向	
					ll_detail.setPadding(5,5,5,5);//四周留白

					for(int i=0;i<msg.length;i++)//为每一行设置显示的数据
					{					    
						TextView s= new TextView(LCCXActivity.this);
						s.setText(msg[i][arg0]);//TextView中显示的文字
						s.setTextSize(14);//字体大小
						s.setTextColor(getResources().getColor(R.color.black));//字体颜色
						s.setPadding(1,2,2,1);//四周留白
						s.setWidth(60);//宽度
					    s.setGravity(Gravity.CENTER);
					    ll_detail.addView(s);//放入LinearLayout
					}
					return ll_detail;//将此LinearLayout返回
				}        	
	        };    
	        lv_detail.setAdapter(ba_detail);//将适配器添加进ListView
	        
	        lv_detail.setOnItemClickListener//为列表添加监听
	        (
	           new OnItemClickListener()
	           {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,//当点击列表中的某一项时调用此函数
						long arg3) //arg2为点击的第几项
				{
					String cccx=msg[0][arg2];//取出对应项中对应的车次信息
					
					 Vector<Vector<String>> temp= LoadUtil.getInfo(cccx);//查询该车次经过的所有车站
					 if(temp.size()==0)//判断是否有查询结果
						{
							Toast.makeText(LCCXActivity.this, "没有相关信息!!!", Toast.LENGTH_SHORT).show();
							return;
						}
					 String[][] msgInfo=new String[temp.elementAt(0).size()][temp.size()];//如果有则将结果放入对应的数组
						for(int i=0;i<temp.size();i++)
						{
							for(int j=0;j<temp.elementAt(0).size();j++)
							{
								msgInfo[j][i]=(String)temp.get(i).get(j);								
							}
						}
						msgg=msg;
						goToPassStationView(msgInfo);//切换到车次具体情况显示界面

				}        	   
	           }
	        );
	 }
	//某列车经过的所有车站。去经过车站界面
	 public void goToPassStationView(String[][]mssg)
	 {
		 setContentView(R.layout.passstation);//切换界面
		 curr=WhichView.PASSSTATION_VIEW;//标识界面
	        ListView lv_detail=(ListView)this.findViewById(R.id.ListView_passstation);//得到ListView的引用
	        final String[][]msg=mssg;
	       
	        BaseAdapter ba_detail=new BaseAdapter()//新建适配器
	        {
				@Override
				public int getCount() 
				{
					return msg[0].length;//得到列表的长度
				}
				@Override
				public Object getItem(int arg0){return null;}
				@Override
				public long getItemId(int arg0){return 0;}
				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2)
				{
					LinearLayout ll_detail=new LinearLayout(LCCXActivity.this);
					ll_detail.setOrientation(LinearLayout.HORIZONTAL);		//设置朝向	
					ll_detail.setPadding(5,2,2,4);//设置四周留白
					TextView []tv=
					{
					   new TextView(LCCXActivity.this),new TextView(LCCXActivity.this),new TextView(LCCXActivity.this)
					};
					for(int i=0;i<msg.length;i++)//设置每一行中显示的数据
					{
						tv[i].setText(msg[i][arg0]);//每个TextView中的文本
						tv[i].setTextSize(13);//字体大小
						tv[i].setTextColor(getResources().getColor(R.color.black));//字体颜色
						tv[i].setPadding(5,2,3,2);//四周留白
						tv[i].setWidth(150);//宽度
					    tv[i].setGravity(Gravity.CENTER);
					    ll_detail.addView(tv[i]);//添加进LinearLayout

					}
					return ll_detail;//将此LinearLayout返回
				}        	
	        };    
	        lv_detail.setAdapter(ba_detail);//将适配器添加进列表
	        
	 }
	 //查看在某个界面中点击查询按钮时，判断输入框是否为空
	 public boolean isLegal()
	 {
		 if(curr==WhichView.ZZCX_VIEW)//如果当前为站站查询界面，对相应的文本框进行合法验证
		 {
			EditText etcfz=(EditText)findViewById(R.id.EditText01);//出发站
			EditText etzzz=(EditText)findViewById(R.id.zzcxzzz);//中转站
			EditText etzdz=(EditText)findViewById(R.id.zzcxzdz);//终点站
			CheckBox cbzzz=(CheckBox)findViewById(R.id.zzcxzzzbt);//中转站复选框
			if(etcfz.getText().toString().trim().equals(""))//出发站为空
			{
				Toast.makeText(this, "出发站不能为空！！！",Toast.LENGTH_LONG).show();
				return false;
			}
			if(etzzz.getText().toString().trim().equals("")&&cbzzz.isChecked())//中转站为空
			{
				Toast.makeText(this, "中转站不能为空！！！",Toast.LENGTH_LONG).show();
				return false;
			}
			if(etzdz.getText().toString().trim().equals(""))//终点站为空
			{
				Toast.makeText(this, "终点站不能为空！！！",Toast.LENGTH_LONG).show();
				return false;
			}
			if(etcfz.getText().toString().trim().contentEquals(etzdz.getText().toString().trim()))//出发站和终点站相同
			{
				Toast.makeText(this, "出发站和终点站不能相同！！！",Toast.LENGTH_LONG).show();
				return false;
			}
			if(cbzzz.isChecked()&&etcfz.getText().toString().trim().contentEquals(etzzz.getText().toString().trim()))//出发站和中转站相同
			{
				Toast.makeText(this, "出发站和中转站不能相同！！！",Toast.LENGTH_LONG).show();
				return false;
		    }
			if(cbzzz.isChecked()&&etzdz.getText().toString().trim().contentEquals(etzzz.getText().toString().trim()))//终点站和中转站
			{
				Toast.makeText(this, "终点站和中转站不能相同！！！",Toast.LENGTH_LONG).show();
				return false;
			}
		 }
		 if(curr==WhichView.CCCX_VIEW)//如果当前为车次查询界面
		 {
			 EditText etcccx=(EditText)findViewById(R.id.cccxcc);
			 if(etcccx.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "车次不能为空！！！",Toast.LENGTH_LONG).show();
					return false;
			 }
		 }
		 if(curr==WhichView.CZCCCX_VIEW)//如果当前为车站车次查询界面
		 {
			 EditText etczcccx=(EditText)findViewById(R.id.czcxwb);
			 if(etczcccx.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "车站不能为空！！！",Toast.LENGTH_LONG).show();
					return false;
			 }
		 }
		 if(curr==WhichView.CCTJ_VIEW)//如果当前在车次添加
		 {
			 EditText et_cm=(EditText)findViewById(R.id.cctj_cm);//车名
			 EditText et_lclx=(EditText)findViewById(R.id.cctj_lclx);//列车类型
			 EditText et_sfz=(EditText)findViewById(R.id.cctj_sfz);//始发站
			 EditText et_zdz=(EditText)findViewById(R.id.cctj_zdz);//终点站
			 if(et_cm.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "车名不能为空！！！",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 if(et_lclx.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "列车类型不能为空！！！",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 if(et_sfz.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "始发站不能为空！！！",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 if(et_zdz.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "终点站不能为空！！！",Toast.LENGTH_SHORT).show();
					return false;
			 }	 
		 }
		 if(curr==WhichView.CZTJ_VIEW)//如果当前在车站添加界面
		 {
			 EditText et_czmc=(EditText)findViewById(R.id.et_cztj_czmc);//车站名称
			 EditText et_czjc=(EditText)findViewById(R.id.et_cztj_czjc);//车站简称
			 if(et_czmc.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "车站名称不能为空！！！",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 if(et_czjc.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "车站简称不能为空！！！",Toast.LENGTH_SHORT).show();
					return false;
			 }
		 }
		 if(curr==WhichView.GXTJ_VIEW)//如果当前在关系添加界面
		 {
			 EditText et_cm=(EditText)findViewById(R.id.et_gxtj_cm);//车名
			 EditText et_zm=(EditText)findViewById(R.id.et_gxtj_zm);//站名
			 
			 if(et_cm.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "车名不能为空！！！",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 if(et_zm.getText().toString().trim().contentEquals(""))
			 {
				 Toast.makeText(this, "站名不能为空！！！",Toast.LENGTH_SHORT).show();
					return false;
			 }
			 
		 }
		 return true;
	 } 
	 @Override
	  public boolean onKeyDown(int keyCode, KeyEvent e)//键盘监听
	  { 
	    	if(keyCode!=4)//如果不是按下的返回按钮时不做任何处理，直接返回
	    	{
	    		return false;
	    	}
	    	if(curr==WhichView.ZZCX_VIEW|| curr==WhichView.CCCX_VIEW||curr==WhichView.CZCCCX_VIEW||
	    			curr==WhichView.FJGN_VIEW)//站站查询//车次查询//车站查询//附加功能
	    	{
	    		goToMainMenu();//返回到主菜单界面
	    		return true;
	    	}
	    	if(curr==WhichView.CCTJ_VIEW|| curr==WhichView.CZTJ_VIEW||curr==WhichView.GXTJ_VIEW)
	    	{//如果是车次添加、车站添加和关系添加界面
	    		goTofjgnView();//返回到附加界面
	    		return true;
	    	}	    
	    	
	    	if(curr==WhichView.MAIN_MENU)
	    	{
	    		System.exit(0);//如果是在主菜单中按下返回按钮，则直接退出
	    		return true;
	    	}
	    	if(curr==WhichView.PASSSTATION_VIEW)//如果是在车次详细情况显示界面
	    	{
	    		goToListView(msgg);//返回到ListView界面
	    		return true;
	    	}
	    	
	    	if(curr==WhichView.LIST_VIEW)//如果是在ListView界面，则根据当前情况返回
	    	{
	    		if(flag==0)//如果为站站查询界面
	    		{
	    			goTozzcxView();
		    		return true;
	    		}
	    		else if(flag==1)//如果是车次查询界面
	    		{
	    			goTocccxView();
		    		return true;
	    		}
	    		else//否则为车站查询界面
	    		{
	    			goToczcccxView();
		    		return true;
	    		}
	    	}
	    	if(curr==WhichView.ABOUT_VIEW||curr==WhichView.HELP_VIEW)//如果是关于和帮助界面
	    	{
	    		
	    		goToMainMenu();//返回到主菜单界面
	    		return true;
	    	}
	    	return false;
	 }
	 
	 public void iniTLisit()//初始化适配器中需要的数据的函数
	    {
		 
		 String sql = "select Sname from station";//查出所有车站名字
		 
		 Vector<Vector<String>> temp= LoadUtil.query(sql);
			String[][] msgInfo=new String[temp.get(0).size()][temp.size()];
			for(int i=0;i<temp.size();i++)
			{
				for(int j=0;j<temp.elementAt(0).size();j++)
				{
					msgInfo[j][i]=(String)temp.get(i).get(j);
				}
			}
			this.s1=msgInfo[0];//得到该数组
		 sql="select Spy from station";//查出所有车站名字的简称
		 
		 temp= LoadUtil.query(sql);

			msgInfo=new String[temp.elementAt(0).size()][temp.size()];
			for(int i=0;i<temp.size();i++)
			{
				for(int j=0;j<temp.elementAt(0).size();j++)
				{
					msgInfo[j][i]=(String)temp.get(i).get(j);
				}
			}
			this.s2=msgInfo[0];//得到该数组

	    }
	 
	 public void iniTLisitarray(int id)//为对应ID的输入框添加适配器
	 {
		 CityAdapter<String> cAdapter = new CityAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,this.s1,this.s2); //设置提示框中的所有内容  
	        AutoCompleteTextView autoView=(AutoCompleteTextView)findViewById(id);//设置要添加提示信息的输入框
	        autoView.setAdapter(cAdapter);   //添加适配器
	        autoView.setThreshold(1);
	        autoView.setDropDownHeight(100) ;
	        autoView.setDropDownBackgroundResource(R.color.gray);
	 }

	
}