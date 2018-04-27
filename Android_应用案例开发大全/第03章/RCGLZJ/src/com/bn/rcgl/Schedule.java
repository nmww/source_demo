package com.bn.rcgl;

import static com.bn.rcgl.Constant.getNowDateString;
import static com.bn.rcgl.Constant.getNowTimeString;
import static com.bn.rcgl.DBUtil.getSNFromPrefs;
import android.util.Log;

public class Schedule 
{
	private int sn;//ÿһ���ճ̶�Ӧһ����һ�޶���sn�룬�����ݿ���Ϊ����
	private String date1;//�ճ�����
	private String time1;//�ճ�ʱ��
	private String date2;//��������
	private String time2;//����ʱ��
	private String type;//�ճ�����
	private String title;//�ճ̱���
	private String note;//�ճ̱�ע
	private boolean timeSet;//�ճ��Ƿ����þ���ʱ��
	private boolean alarmSet;//�ճ��Ƿ���������
	
	
	//�������ճ�ʱ����ʱ���ݣ�ֻ��Ҫ�������������ݣ������ڸոս����½��ճ̽����հ�������Ĭ�����óɵ�ǰ����
	public Schedule(int y,int m,int d)
	{
		sn=0;
		date1=toDateString(y,m,d);
		time1=toTimeString(8,0);//ʱ��Ĭ��8��
		
		date2=null;
		time2=null;
		
		title="";
		note="";
		type="";
		
		timeSet=true;
		alarmSet=false;
				
	}
	
	//�˹�����Ϊ�����ݿ��ȡ�ճ̶���ʱ��
	public Schedule(int sn,String date1,String time1,String date2,String time2,String title,String note,String type,String timeSet,String alarmSet)
	{
		this.sn=sn;
		this.date1=date1;
		this.time1=time1;
		this.date2=date2;
		this.time2=time2;
		this.title=title;
		this.note=note;
		this.type=type;
		this.timeSet=Boolean.parseBoolean(timeSet);
		this.alarmSet=Boolean.parseBoolean(alarmSet);
	}
	
	public int getYear()//�����
	{
		String[] date=date1.split("/");
		int tmp=Integer.valueOf(date[0]);
		return tmp;
	}
	
	public int getMonth()//�����
	{
		String[] date=date1.split("/");
		int tmp=Integer.valueOf(date[1]);
		return tmp;
	}
	
	public int getDay()//�����
	{
		String[] date=date1.split("/");
		int tmp=Integer.valueOf(date[2]);
		return tmp;
	}
	
	public int getHour()//���ʱ
	{
		String[] time=time1.split(":");
		int tmp=Integer.valueOf(time[0]);
		return tmp;
	}
	
	public int getMinute()//��÷�
	{
		String[] time=time1.split(":");
		int tmp=Integer.valueOf(time[1]);
		return tmp;
	}
	
	public int getAYear()//������ӵ���
	{
		String[] date=date2.split("/");
		int tmp=Integer.valueOf(date[0]);
		return tmp;
	}
	
	public int getAMonth()//���������
	{
		String[] date=date2.split("/");
		int tmp=Integer.valueOf(date[1]);
		return tmp;
	}
	
	public int getADay()//���������
	{
		String[] date=date2.split("/");
		int tmp=Integer.valueOf(date[2]);
		return tmp;
	}
	
	public int getAHour()//�������ʱ
	{
		String[] time=time2.split(":");
		int tmp=Integer.valueOf(time[0]);
		return tmp;
	}
	
	public int getAMin()//������ӷ�
	{
		String[] time=time2.split(":");
		int tmp=Integer.valueOf(time[1]);
		return tmp;
	}
	
	public void setType(String s)//��������
	{
		this.type=s;
	}
	
	public String getType()//�������
	{
		return type;
	}
	
	public void setTitle(String s)//���ñ���
	{
		this.title=s;
	}
	
	public String getTitle()//��ñ���
	{
		return title;
	}
	
	public void setNote(String s)//���ñ�ע
	{
		this.note=s;
	}
	
	public String getNote()//��ñ�ע
	{
		return note;
	}
	
	public void setTimeSet(boolean b)//�����Ƿ����þ���ʱ��Ĳ���ֵ
	{
		this.timeSet=b;
		if(!timeSet)//���Ϊfalse˵��û�����þ���ʱ�䣬�����ʱ��Ĭ��Ϊ�������һ����
		{
			time1="23:59";
		}
	}
	
	public boolean getTimeSet()//�õ��Ƿ�����ʱ��
	{
		return timeSet;
	}
	
	public void setAlarmSet(boolean b)//�����Ƿ��������ӵĲ���ֵ
	{
		this.alarmSet=b;
		if(!timeSet)//���Ϊfalse˵��û���������ӣ���������null
		{
			date2=null;
			time2=null;
		}
	}
	
	public boolean getAlarmSet()//�õ��Ƿ�����������
	{
		return alarmSet;
	}
	
	public void setDate1(String y,String m,String d)//�����ճ����ڣ�ת����YYYY/MM/DD
	{
		StringBuffer sb=new StringBuffer();
		sb.append(y);
		sb.append("/");
		sb.append(m);
		sb.append("/");
		sb.append(d);
		date1=sb.toString();
	}
	
	public String getDate1()//�õ��ճ�����
	{
		return date1;
	}
	
	public void setTime1(String h,String m)//�����ճ�ʱ�䣬ת����HH:MM
	{
		StringBuffer sb=new StringBuffer();
		sb.append(h);
		sb.append(":");
		sb.append(m);
		time1=sb.toString();
	}
	
	public String getTime1()//����ճ�ʱ��
	{
		return time1;
	}
	
	public void setDate2(String y,String m,String d)//������������
	{
		StringBuffer sb=new StringBuffer();
		sb.append(y);
		sb.append("/");
		sb.append(m);
		sb.append("/");
		sb.append(d);
		date2=sb.toString();
	}
	
	public String getDate2()//�õ���������
	{
		return date2;
	}
	
	public void setTime2(String h,String m)//��������ʱ��
	{
		StringBuffer sb=new StringBuffer();
		sb.append(h);
		sb.append(":");
		sb.append(m);
		time2=sb.toString();
	}
	
	public String getTime2()//�õ�����ʱ��
	{
		return time2;
	}	
	
	public void setSn(int sn)//����sn�� 
	{
		this.sn = sn;
	}

	public int getSn() //�õ�sn��
	{
		return sn;
	}

	public static String toDateString(int y,int m,int d)//��̬��������int�͵�������ת����YYYY/MM/DD
	{
		StringBuffer sb = new StringBuffer();
		sb.append(y);
		sb.append("/");
		sb.append(m<10?"0"+m:""+m);
		sb.append("/");
		sb.append(d<10?"0"+d:""+d);
		return sb.toString();
	}
	
	public String toTimeString(int h,int m)//��int�͵�ʱ��ת����HH:MM
	{
		StringBuffer sb = new StringBuffer();
		sb.append(h<10?"0"+h:""+h);
		sb.append(":");
		sb.append(m<10?"0"+m:""+m);
		return sb.toString();
	}
		
	public String typeForListView()//�����õ����������ListView����ʾ�����͸�ʽ
	{
		StringBuffer sbTmp=new StringBuffer();
		sbTmp.append("[");
		sbTmp.append(type);
		sbTmp.append("]");
		return sbTmp.toString();
	}
	
	public String dateForListView()//�����õ����������ListView����ʾ�����ڸ�ʽ
	{
		StringBuffer sbTmp=new StringBuffer();
		sbTmp.append(date1);
		sbTmp.append("   ");
		return sbTmp.toString();
	}
	
	public String timeForListView()//�����õ����������ListView����ʾ��ʱ���ʽ
	{
		if(!timeSet)
		{
			return "- -:- -   ";
		}
		StringBuffer sbTmp=new StringBuffer();
		sbTmp.append(time1);
		sbTmp.append("   ");
		return sbTmp.toString();
	}
	
	public boolean isPassed()//���ճ�����ʱ���뵱ǰʱ����ȣ��ж��ճ��Ƿ��ѹ���
	{
		String nowDate=getNowDateString();
		String nowTime=getNowTimeString();
		String schDate=date1;
		String schTime=timeSet?time1:"23:59";//����ճ�û������ʱ�䣬����Ϊ���˵���23:59��Ҳ���ǵ��˵ڶ���Ź�ʱ
							
		if(nowDate.compareTo(schDate)>0||(nowDate.compareTo(schDate)==0&&nowTime.compareTo(schTime)>0))
		{
			return true;
		}
		return false;
	}
	
	public String toInsertSql(RcActivity father)//��ȡschedule����������ݿ�ʱ��sql���
	{
		StringBuffer sb = new StringBuffer();
		sb.append("insert into schedule values(");
		sn=getSNFromPrefs(father);
		sb.append(sn);
		sb.append(",'");
		sb.append(date1);
		sb.append("','");
		sb.append(time1);
		sb.append("','");
		sb.append(date2);
		sb.append("','");
		sb.append(time2);
		sb.append("','");
		sb.append(title);
		sb.append("','");
		sb.append(note);
		sb.append("','");
		sb.append(type);
		sb.append("','");
		sb.append(timeSet);
		sb.append("','");
		sb.append(alarmSet);
		sb.append("')");	
		Log.d("toInsertSql",sb.toString());
		return sb.toString();
	}
	
	public String toUpdateSql(RcActivity father)//��ȡschedule�������ʱ��sql���
	{
		int preSn=sn;//��¼֮ǰ��sn
		StringBuffer sb = new StringBuffer();
		sb.append("update schedule set sn=");
		sn=getSNFromPrefs(father);//�����µ�sn
		sb.append(sn);
		sb.append(",date1='");
		sb.append(date1);
		sb.append("',time1='");
		sb.append(time1);
		sb.append("',date2='");
		sb.append(date2);
		sb.append("',time2='");
		sb.append(time2);
		sb.append("',title='");
		sb.append(title);
		sb.append("',note='");
		sb.append(note);
		sb.append("',type='");
		sb.append(type);
		sb.append("',timeset='");
		sb.append(timeSet);
		sb.append("',alarmset='");
		sb.append(alarmSet);
		sb.append("' where sn=");
		sb.append(preSn);
		Log.d("toUpdateSql",sb.toString());
		return sb.toString();
	}
}