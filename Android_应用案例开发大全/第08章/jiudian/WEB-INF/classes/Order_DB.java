package wyf.wyy;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import java.text.*;
public class Order_DB{
	private static Connection con=null;//声明Connection引用
	private static Statement stat=null;//声明Statement引用
	private static ResultSet rs=null;//声明ResultSet引用
	public static boolean isOrdered(String rgid1){//判断资源是否被预定中
		boolean b = false;//初始为false，即没有被预订
		try{
			String ostatus = new String("预定中".getBytes(),"iso8859-1");
			String rstatus = new String("占用".getBytes(),"iso8859-1");
			String rgid = new String(rgid1.getBytes(),"iso8859-1");
			con = DB.getCon();
			stat = con.createStatement();
			//查看当前rgid对应的资源有没有正在预订中
			rs = stat.executeQuery("select rgid from oinfo where ostatus='"+
								ostatus+"' and rgid='"+rgid+"'");
			if(rs.next()) {b = true;}
			//查看当前资源是否处于空闲状态
			rs = stat.executeQuery("select rgid from resource where rstatus='"+
								rstatus+"' and rgid='"+rgid+"'");
			if(rs.next()) {b = true;}
		}
		catch(Exception e){e.printStackTrace();}
		finally{DB.closeCon();}//关闭数据库连接
		return b;//返回结果
	}
	public static Vector<String []> getOrderedDay(String rgid1){
		Vector<String []> v = new Vector<String []>();
		try{
			String ostatus = new String("预订成功".getBytes(),"iso8859-1");
			String rgid = new String(rgid1.getBytes(),"iso8859-1");
			con = DB.getCon();//得到数据库连接
			stat = con.createStatement();//创建语句对象
			//查看当前rgid对应的资源已经被预订的列表（未来七天之内）
			rs = stat.executeQuery("select ftime,etime from oinfo where ostatus='"+
								ostatus+"' and rgid='"+rgid+"'");
			while(rs.next()){				
				String []s =new String[2];
				//得到开始和结束时间
				s[0] = new String(rs.getString(1).getBytes("iso8859-1"),"gbk");
				s[1] = new String(rs.getString(2).getBytes("iso8859-1"),"gbk");
				java.util.Date etime = Order_DB.chageStringToDate(s[1]);
				java.util.Date now = new java.util.Date();
				//如果结束时间在当前时间之后，则未来七天之内有已经被订的日期
				if(etime.after(now)) {v.add(s);}
			}
		}
		catch(Exception e)	{e.printStackTrace();}
		finally {DB.closeCon();}//关闭数据库连接
		return v;//返回结果
	}
	public static java.util.Date chageStringToDate(String s){
		java.util.Date da = null;//声明日期对象引用
		if(s!=null){
			String p = "-|:| "; //用于拆分的正则式
			String[] d = s.split(p);//得到拆分后的字符串数组			
			int[] date = new int[d.length];//将字符穿数组转换为int型数组
			for(int i=0;i<d.length;i++)
			{date[i]=Integer.parseInt(d[i]);}
			//调用java.util.Date的构造器构造一个日期对象
			da = new java.util.Date(date[0]-1900,date[1]-1,date[2],date[3],date[4]);
		}	
		return da;//返回日期对象
	}
/*	public static int addOrder(String user,Vector<String[]> OrderList)
	{
		int i = 0;
		int orid = DB.getId("oinfo","orid");//得到订单明细的主键ID+1值
		int oid = DB.getId("olist","oid");//得到订单表的主键ID+1值
		try{			
			con = DB.getCon();
			stat = con.createStatement();
			//得到相关信息组成订单			
			java.util.Date d = new java.util.Date();
			String otime = d.toLocaleString();
			con.setAutoCommit(false);//禁用自动提交，开始一个事务
			String sqla = "insert into olist(oid,oname,otime) values"+
					"("+oid+",'"+user+"','"+otime+"')";
			String sql = new String(sqla.getBytes(),"iso8859-1");			
			stat.executeUpdate(sql);
			//得到订单明细信息
			Vector<String> sqlb = new Vector<String>();			
			for(String []s:OrderList){												
				String rgid = s[0];//得到所订资源号
				//得到开始和结束时间
				String ftime = s[2]; String etime = s[3];
				String sqlc = "insert into oinfo(orid,oid,rgid,ftime,etime) values"+
						"("+orid+","+oid+",'"+rgid+"','"+ftime+"','"+etime+"')";
				String sqld = new String(sqlc.getBytes(),"iso8859-1");
				stat.executeUpdate(sqld);//执行更新
				orid++;//主键自加，作为下一条记录的主键
			}
			con.commit();//将事务提交
			con.setAutoCommit(true);//恢复自动提交模式
		}
		catch(Exception e){
			e.printStackTrace();
			i = -1;
			try{con.rollback();}//出现错误，发起回滚操作
			catch(Exception ea)	{e.printStackTrace();}
		}
		finally	{DB.closeCon();}
		return i;//返回执行结果,-1代表失败
	}*/
		public static int addOrder(String user,Vector<String[]> OrderList)
	{
		int i = 0;
		int orid = DB.getId("oinfo","orid");//得到订单明细的主键ID+1值
		int oid = DB.getId("olist","oid");//得到订单表的主键ID+1值
		try{			
			con = DB.getCon();
			stat = con.createStatement();
			//得到相关信息组成订单			
			//java.util.Date d = new java.util.Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String otime = df.format(new java.util.Date());
			//String otime = d.toLocaleString();
			con.setAutoCommit(false);//禁用自动提交，开始一个事务
			String sqla = "insert into olist(oid,oname,otime) values"+  
					"("+oid+",'"+user+"','"+otime+"')";//订单编号 用户名 下定时间
			String sql = new String(sqla.getBytes(),"iso8859-1");			
			stat.executeUpdate(sql);
			//得到订单明细信息
			Vector<String> sqlb = new Vector<String>();			
			for(String []s:OrderList){												
				String rgid = s[0];//得到所订资源号
				//得到开始和结束时间
				String ftime = s[1]; String etime = s[2];
				String sqlc = "insert into oinfo(orid,oid,rgid,ftime,etime) values"+
						"("+orid+","+oid+",'"+rgid+"','"+ftime+"','"+etime+"')";
				String sqld = new String(sqlc.getBytes(),"iso8859-1");
				stat.executeUpdate(sqld);//执行更新
				orid++;//主键自加，作为下一条记录的主键
			}
			con.commit();//将事务提交
			con.setAutoCommit(true);//恢复自动提交模式
		}
		catch(Exception e){
			e.printStackTrace();
			i = -1;
			try{con.rollback();}//出现错误，发起回滚操作
			catch(Exception ea)	{e.printStackTrace();}
		}
		finally	{DB.closeCon();}
		return i;//返回执行结果,-1代表失败
	}
	public static Vector<String []> getOrderList(String sqla){//得到用户已提交订单
		Vector<String []> v = new Vector<String[]>();//创建返回向量
		try{
			con = DB.getCon();//得到数据库连接
			stat = con.createStatement();//创建语句对象		
			String sql = new String(sqla.getBytes(),"iso8859-1");//转码
			rs = stat.executeQuery(sql);//执行查询
			while(rs.next()){//遍历结果集
				String s[] = new String[6];
				for(int i=0;i<s.length;i++){//对信息进行转
					s[i] = new String(rs.getString(i+1).getBytes("iso8859-1"),"gbk");
				}				
				v.add(s);//将订单信息添加进返回向量
			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally	{DB.closeCon();}
		return v;
	}
	public static Vector<String []> getOrderDetail(String oid){//得到某一订单详情
		Vector<String []> v = new Vector<String[]>();
		try{
			con = DB.getCon();//得到数据库连接
			stat = con.createStatement();//创建语句对象
			rs = stat.executeQuery("select rgid,ftime,etime,ostatus from oinfo"+
						" where oid='"+oid+"'");//执行查询得到结果集			
			while(rs.next()){//遍历结果集
				String s[] = new String[5];
				for(int i=0;i<s.length-1;i++){//转码
					s[i] = new String(rs.getString(i+1).getBytes("iso8859-1"),"gbk");
				}										
				v.add(s);//将信息添加到返回向量
			}
			for(String[] s:v){//得到分组名
				String rgid = new String(s[0].getBytes(),"iso8859-1");
				rs = stat.executeQuery("select gName from rgroup where gId=("+
							"select rgroup from resource where rgid='"+rgid+"')");
				rs.next();
				s[4] = new String(rs.getString(1).getBytes("iso8859-1"),"gbk");
			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally	{DB.closeCon();}
		return v;
	}
		public static Vector<String []> getOrderListThree(String sqla){//得到用户已提交订单其中的三项
		Vector<String []> v = new Vector<String[]>();//创建返回向量
		try{
			con = DB.getCon();//得到数据库连接
			stat = con.createStatement();//创建语句对象		
			String sql = new String(sqla.getBytes(),"iso8859-1");//转码
			rs = stat.executeQuery(sql);//执行查询
			while(rs.next()){//遍历结果集
				String s[] = new String[3];
				for(int i=0;i<s.length;i++){//对信息进行转
					s[i] = new String(rs.getString(i+1).getBytes("iso8859-1"),"gbk");
				}				
				v.add(s);//将订单信息添加进返回向量
			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally	{DB.closeCon();}
		return v;
	}
}