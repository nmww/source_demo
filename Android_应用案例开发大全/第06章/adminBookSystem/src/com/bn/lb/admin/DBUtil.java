package com.bn.lb.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

public class DBUtil 
{
   static boolean feeflag=false;
    public static Connection getConnection()
	{
		Connection con=null; 
		try
		{
            Class.forName("org.gjt.mm.mysql.Driver");
			con=DriverManager.getConnection("jdbc:mysql://192.168.0.106:3306/test?useUnicode=true&characterEncoding=UTF-8","root","initial");  
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
    
    public static void delete(String sql)
    {
    	try
		{
			Connection con=getConnection();			
			Statement st=con.createStatement();			
			st.executeUpdate(sql);						
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
     //登录验证
    public static String selectADPwd(String mgNo)
    {
		String result=null;
		try
		{
			Connection con=getConnection();
			Statement st=con.createStatement();
			String sql="select M_pwd from manager where M_Num='"+mgNo+"'";
			ResultSet rs=st.executeQuery(sql);
			if(rs.next())
			{
				result=rs.getString(1);
			}
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return result;
    	
    }
    //权限验证
    public static int CheckPermitted(String mgNO)
    {
    	int permitted=0;
    	String result=null; 
    	try
    	{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="select M_Permitted from student where M_Num='"+mgNO+"'";
        	ResultSet rs=st.executeQuery(sql);
        	if(rs.next())
        	{
        		result=rs.getString(1).trim();
        		if(result.equals("普通"))
            	{
        			permitted=1;
            	}else if(result.equals("高级"))
            	{
            		permitted=0;
            	}
        	}
       
        	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	
    	return permitted;
    }
    //查询管理员 
    public static String[] SelectAdmin(String mgNO)
    {
    	String sa[]=new String[3];
    	try{
    		
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="select M_Permitted,M_Pwd from manager where M_Num='"+mgNO+"'";
        	ResultSet rs=st.executeQuery(sql);
        	if(rs.next())
        	{
        		sa[0]=rs.getString(1);
        		sa[1]=rs.getString(2);
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	return sa;
    }
    //添加管理员
    public static Boolean insertManager(String mgNO,String permitted,String password)
    {
    	Boolean falg=false;
    	try{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
            String sql="insert into manager(M_Num,M_Permitted,M_Pwd) value('"+mgNO+"','"+permitted+"','"+password+"')";
        	st.executeUpdate(sql);
        	falg=true;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
		return falg;
    	
    }
    //删除管理员
    public static void deleteManager(String mgNO)
    {
    	try{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="delete from manager where M_Num='"+mgNO+"'";
        	st.execute(sql);
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    //查询管理员密码 
    public static String selectAdminPassword(String mgNo)
    {
    	String pwd=null;
       try{
    		
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="select M_Pwd from manager where M_Num='"+mgNo+"'";
        	ResultSet rs=st.executeQuery(sql);
        	if(rs.next())
        	{
        		pwd=rs.getString(1);
        	}
        	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
		return pwd;
    	
    }
    //修改管理员密码
    public static void updateManager(String mgNo,String password)
    {
    	try{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="update manager set M_Pwd='"+password+"' where M_Num='"+mgNo+"'";
        	st.executeUpdate(sql);
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    //图书入库
    public static void insertBook(String isbn,String BookNo,String BookName,String Author,String Publishment,String BuyTime,String Borrowed,String Ordered,String instroduction)
    {
    	try{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql1="insert into book(ISBN,B_Name,B_Author,B_Publishment,B_BuyTime) values('"+isbn+"'," +
        			"'"+BookName+"','"+Author+"','"+Publishment+"','"+BuyTime+"')";
        	String sql2="insert into bdetailedinformation(B_Num,ISBN,Borrowed,Ordered,Introduction) values('"+BookNo+"'," +
			"'"+isbn+"','"+Borrowed+"','"+Ordered+"','"+instroduction+"')";
        	st.executeUpdate(sql1);
        	st.executeUpdate(sql2);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    //删除图书信息
    public static void deleteBook(String bookNO)
    {
    	try{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="delete from bdetailedinformation where B_Num='"+bookNO+"'";
        	String sql1="delete from book where B_Num='"+bookNO+"'";
        	st.execute(sql1);
        	st.execute(sql);
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
   //修改图书信息
    public static void updateBook(String BookNo,String BookName,String Author,String Publishment,String BuyTime,String Borrowed,String Ordered,String Introduction)
    {
    	try{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="update bdetailedinformation set B_Num='"+BookNo+"',B_Name='"+BookName+"',B_Author='"+Author+"',B_Publishment='"+Publishment+"',B_BuyTime='"+BuyTime+"',Borrowed='"+Borrowed+"',Ordered='"+Ordered+"',Introduction='"+Introduction+"')";
        	String sql2="update book set B_Num='"+BookNo+"',B_Name='"+BookName+"',B_Author='"+Author+"',B_Publishment='"+Publishment+"',B_BuyTime='"+BuyTime+"'";
        	st.executeUpdate(sql2);
        	st.executeUpdate(sql);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    //添加学生
    public static void addStu(String StuNO,String StuName,String StuAge,String StuSex,String Class,String Department,String Tel,String Permitted,String Password)
    {
    	try{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="insert into student(S_Num,S_Name,S_Age,S_Sex,S_Class,S_Department,S_Phone,S_Permitted,S_Pwd) values('"+StuNO+"','"+StuName+"','"+StuAge+"','"+StuSex+"','"+Class+"','"+Department+"','"+Tel+"','"+Permitted+"','"+Password+"')";
        	st.executeUpdate(sql);
        	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    //查询学生信息
    public static String[] selectStu(String StuNO)
    {
    	String ss[]=new String[8];
    	try{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="select S_Name,S_Age,S_Sex,S_Class,S_Department,S_Phone,S_Permitted,S_Pwd from student where S_Num='"+StuNO+"'";
        	ResultSet rs=st.executeQuery(sql);
        	if(rs.next())
        	{
        		ss[0]=rs.getString(1);
        		ss[1]=rs.getString(2);
        		ss[2]=rs.getString(3);
        		ss[3]=rs.getString(4);
        		ss[4]=rs.getString(5);
        		ss[5]=rs.getString(6);
        		ss[6]=rs.getString(7);
        		ss[7]=rs.getString(8);
        		
        	}
        	       	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return ss;
    	
    }
    //删除学生信息
    public static void delectStu(String Sno)
    {
    	try{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="delete from student where S_Num='"+Sno+"'";
        	st.execute(sql);
        	       	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    //修改学生信息
    public static void updateStu(String StuNO,String StuName,String StuAge,String StuSex,String Class,String Department,String Tel,String Permitted,String Password)
    {
    	try{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="update student set S_Num='"+StuNO+"',S_Name='"+StuName+"',S_Age='"+StuAge+"',S_Sex='"+StuSex+"',S_Class='"+Class+"',S_Department='"+Department+"',S_Phone='"+Tel+"',S_Permitted='"+Permitted+"',S_Pwd='"+Password+"')";
        	st.executeUpdate(sql);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    //检查是否超期
    public static int checktime(String sno,String bno)
    {//-1代表超期没交罚款  0代表当天借的书   1代表正常还的书  -2表示超期交罚款
    	int day=0;
    	int flag=0;
    	String returntime=null;   		
   		try{
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="select ReturnTime from record where S_Num='"+sno+"'and B_Num='"+bno+"'";
        	ResultSet rs=st.executeQuery(sql);
        	if(rs.next())
        	{
        		returntime=rs.getString(1);//获取应该归还的时间
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	String[] strday=returntime.split("\\-");//这里使用了简单的正则式，规定了时间的格式
		int ryear=Integer.parseInt(strday[0].trim());
		int rmonth=Integer.parseInt(strday[1].trim());
		int rday=Integer.parseInt(strday[2].trim());
	
		Calendar c=Calendar.getInstance();//获得系统当前时间
		day=(c.get(Calendar.YEAR)+1900-ryear)*365+(c.get(Calendar.MONTH)+1-rmonth)*30+(c.get(Calendar.DAY_OF_MONTH)-rday);
		System.out.println(day);
        if(day==-30)
        {
        	//表示当天借的书
        	flag=0;
        }
        else if(day>0)
        {//代表超期了
        	if(feeflag)
        	{//超期交费
        		flag=-2;
        	}
        	else
        	{//超期没交费
        		try{
        			String bname;
        			Connection con1=getConnection();
                	Statement st1=con1.createStatement();
        			String sql1="update overtime set overtime='"+day+"' where S_Num='"+sno+"',B_Num='"+bno+"'";
                    st1.executeUpdate(sql1);

            	}
            	catch(Exception e)
            	{
            		e.printStackTrace();
            	}
        		flag=-1;
        	}
        }
        else
        {
        	//可以正常归还的书
        	flag=1;
        }
		return flag;
    	
    }
   //查看超期天数信息
    public static int selectfee(String StuNO)
    {
    	int day=0;
    	try{
    		int flag=0;
    		Connection con=getConnection();
        	Statement st=con.createStatement();
        	String sql="select Overtime from overtime where S_Num='"+StuNO+"'";
        	ResultSet rs=st.executeQuery(sql);
        	while(rs.next())
        	{
        		flag++;
        		day+=rs.getInt(1);
        	}
        
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return day;
    }
    
    //交费
    public static void fee(String StuNo,String fee)
    {
    	try{
    		Connection con=getConnection();
    		Statement st=con.createStatement();
    		String sql="update Overtime set overtime='0' where S_Num='"+StuNo+"'";
    		st.execute(sql);
    		feeflag=true;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    //查询借阅或预约图书
    public static String borrowororderbook(String bookNo)
    {
    	String s=null;
    	try{
    		Connection con=getConnection();
    		Statement st=con.createStatement();
    		String sql="select Borrowed from record where B_Num='"+bookNo+"'";
    		ResultSet rs=st.executeQuery(sql);
    		if(rs.next())
			{
				s=rs.getString(1);
				
			}

    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return s;
    }
    //预约图书
    public static void orderbook(String bookNo,String StuNo)
    {
    	try{
    		Connection con=getConnection();
    		Statement st=con.createStatement();
    		String sql="update record set ordered=‘是’,S_Num='"+StuNo+"' where B_Num='"+bookNo+"'";
            ResultSet rs=st.executeQuery(sql);
    	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    //借阅图书
    public static void borrowbook(String bookNo,String StuNo)
    {
    	Calendar c=Calendar.getInstance();//获得系统当前时间
    	String day=c.toString().trim();    	
    	try{
    		Connection con=getConnection();
    		Statement st=con.createStatement();
    		String sql="update record set Borrowed=‘是’,S_Num='"+StuNo+"',borrowtime='"+day+"'where B_Num='"+bookNo+"'";
            st.executeUpdate(sql);
    	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
 
    public static Vector<String> selectbookfromISBN(String ISBN)
    {
    	
    	Vector<String> v =new Vector<String>();
    	
    	try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			
			String sql="select book.B_Name,book.B_Author,book.B_Publishment,book.B_Buytime," +
					"bdetailedinformation.B_Num,bdetailedinformation.Borrowed," +
					"bdetailedinformation.Ordered,bdetailedinformation.Introduction from " +
					"book,bdetailedinformation where bdetailedinformation.ISBN=book.ISBN " +
					"And book.ISBN='"+ISBN+"'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中								
								
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));
				v.add(rs.getString(5));
				v.add(rs.getString(6));
				v.add(rs.getString(7));
				v.add(rs.getString(8));
				
			}			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return v; 
    } 
    
    public static Vector<String> selectfeeinformation(String StuNO)
    {
    	Vector<String> v =new Vector<String>();
    	
    	try
		{
    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select B_Num,B_Name,Overtime from overtime where S_Num='"+StuNO+"'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中								
								
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3)); 
				
			}			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    	
    	
    	
		return v;
    	
    }
    
    //得到挂失图书的信息表中的记录的数量
    public static int getMaxGSBH()
    {
		int result=0;
		try
		{
			Connection con=getConnection();			
			Statement st=con.createStatement();
			String sql="select MAX(GSBH) from losebook";
			ResultSet rs=st.executeQuery(sql);
			if(rs.next())
			{
				result=rs.getInt(1);
			}
			rs.close();
			st.close();
			con.close();
		}		
		catch(Exception e)
		{
			e.printStackTrace();
		}		
    	return result;
    	
    }
    //执行没有返回值的插入语句的方法
    public static void update(String sql)
    {
    	try
		{
			Connection con=getConnection();			
			Statement st=con.createStatement();			
			st.executeUpdate(sql);						
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
   //已知书名，得到这个书籍的基本信息
    public static Vector<String> selectAllfrombook(String BookName)
    {
    	Vector<String> v =new Vector<String>();
//    	int lenght=0;
    	try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select ISBN,B_Name,B_Author,B_Publishment from book where B_Name like '%"+BookName+"%'";
			ResultSet rs=st.executeQuery(sql);				
			while(rs.next()){//将结果集信息添加到返回向量中										
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));				
			}			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return v; 
    } 
    //通过书号得到书的基本信息
    public static String[] selectbookinformationfrombookno(String bookno)
    {
    	String info[]=new String[6];
    	try
		{
    	Connection con=getConnection();				
		Statement st=con.createStatement();
		String sql="select book.B_Name,book.B_Author,book.B_Publishment,bdetailedinformation.Borrowed,bdetailedinformation.Ordered from book,bdetailedinformation where bdetailedinformation.B_Num='"+bookno+"'";
		ResultSet rs=st.executeQuery(sql);	
		if(rs.next())
		{
		 info[1]=rs.getString(1);
		 info[2]=rs.getString(2);
		 info[3]=rs.getString(3);
		 info[4]=rs.getString(4);
		 info[5]=rs.getString(5);
		}
		}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return info;
     }
    //通过学号查询借阅数量
    public static int selectcount(String StuNO)
    {
    	int a=0;
    	try
    	{
    		Connection con=getConnection();				
    		Statement st=con.createStatement();
    		String sql="select count(B_Num) from record where S_Num='"+StuNO+"'";
    		ResultSet rs=st.executeQuery(sql);	
    		if(rs.next())
    		{
    			a=rs.getInt(1);
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	return a;
    }
    
   //得到同种ISBN的书籍的数量
   public static int getNumfrombdetailedInfo(String ISBN)
    {
		int num=0;
		try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select count(B_Num) from bdetailedinformation where ISBN='"+ISBN+"'";
			ResultSet rs=st.executeQuery(sql);						
			if(rs.next()){//将结果集信息添加到返回向量中								
				//String[] middle=new String[6];
				num=rs.getInt(1);
			}		
			rs.close();
			st.close();
			con.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}		
    	return num;
    	
    }
  
  //一个ISBN号得到同种号下的这样的书的基本信息
    public static Vector<String> selectISBNALlfromdetailInfo(String ISBN)
    {
    	Vector<String> v =new Vector<String>();
    	int lenght=0;
    	try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select B_Num,Borrowed,Ordered,Introduction from bdetailedinformation where ISBN='"+ISBN+"'";
			ResultSet rs=st.executeQuery(sql);				
			while(rs.next()){//将结果集信息添加到返回向量中								
				//String[] middle=new String[6];				
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));				
			}			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return v;    	    	
    }
    
    
    //根据书号得到作者名
    public static String getAuthor(String BookNO)
    {
		String result=null;
		try
		{
			Connection con=getConnection();			
			Statement st=con.createStatement();
			String sql="select B_Author from book where B_Num='"+BookNO+"'";
			ResultSet rs=st.executeQuery(sql);
			if(rs.next())
			{
				result=rs.getString(1);
			}
			System.out.println(result);
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    	return result;
    	
    }
    
    //根据学生ID得到学生的班级和姓名
    public static Vector<String> getClassAndsname(String StuNO)
    {
    	Vector<String> result =new Vector<String>();    	
    	try
		{   	
			Connection con=getConnection();	
			Statement st=con.createStatement();
			String sql="select S_Name,S_Class,S_Num from student where S_Num='"+StuNO+"'";
			ResultSet rs=st.executeQuery(sql);
			if(rs.next()){//将结果集信息添加到返回向量中
				
				
				String[] middle=new String[3];
				middle[0]=rs.getString(1);
				middle[1]=rs.getString(2);
				middle[2]=rs.getString(3);							
				String[] str=new String[1]; 
				str[0]=middle[0]+"/"+middle[1]+"/"+middle[2];
				result.add(str[0]);
			}
			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return result; 
    	
    	
    }
    
    //通过输入图书的作者得到图书的基本信息
    public static Vector<String> getAuthorAllfromBook(String Author)
    {
    	Vector<String> v =new Vector<String>();    	
    	try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select ISBN,B_Name,B_Author,B_Publishment from book where B_Author like '%"+Author+"%'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));				
			}
			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return v;     	    	
    }
    
    
    //通过出版社得到图书的基本信息
    public static Vector<String> getPubAllfrombook(String Publishment)
    {
    	Vector<String> v =new Vector<String>();    	
    	try
		{
    	//测试在后台打印   		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select ISBN,B_Name,B_Author,B_Publishment from book where B_Publishment like '%"+Publishment+"%'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中				
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));							
			}
			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return v;     	    	

    }
    
    
    //通过书名和作者得到图书的基本信息
    public static Vector<String> getBnAuAllfrombook(String BookName,String Author)
    {
    	Vector<String> v =new Vector<String>();    	
    	try
		{
    	//测试在后台打印   		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select ISBN,B_Name,B_Author,B_Publishment from book where B_Name like '%"+BookName+"%' and B_Author like '%"+Author+"%'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));								
			}
			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return v;     	    	
    }
    
    //通过书名和出版社得到图书的基本信息
    public static Vector<String> getBnCbAllfrombook(String BookName,String Publishment)
    {
    	Vector<String> v =new Vector<String>();    	
    	try
		{
    	//测试在后台打印   		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select ISBN,B_Name,B_Author,B_Publishment from book where B_Name like '%"+BookName+"%' and B_Publishment like '%"+Publishment+"%'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));								
			}			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return v;     	    	
    }
    
    //通过作者和出版社
    public static Vector<String> getAuCbAllfrombook(String Author,String Publishment)
    {
    	Vector<String> v =new Vector<String>();    	
    	try
		{
    	//测试在后台打印   		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select ISBN,B_Name,B_Author,B_Publishment from book where B_Author like '%"+Author+"%' and B_Publishment like '%"+Publishment+"%'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));								
			}			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return v;     	    	
    }
    
    //通过作者 书名和出版社进行查询
    public static Vector<String> getBnAuCbAllfrombook(String BookName,String Author,String Publishment)
    {
    	Vector<String> v =new Vector<String>();    	
    	try
		{
    	//测试在后台打印   		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select ISBN,B_Name,B_Author,B_Publishment from book where B_Name like '%"+BookName+"%' and B_Author like '%"+Author+"%' and B_Publishment like '%"+Publishment+"%'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));								
			}			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return v;     	    	
    }
    
    //通过书号对ISBN和图书简介的查询
    public static Vector<String> getISinfromdetails(String BookNo)
    {
    	Vector<String> v =new Vector<String>();    	
    	try
		{
    	//测试在后台打印   		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select ISBN,Borrowed,Ordered,Introduction from bdetailedinformation where B_Num='"+BookNo+"'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));
			}			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return v;     	    	
    }
    
   //通过ISBN对同一种ISBN号下的基本信息
    public static Vector<String> getISfrombook(String isbn)
    {
    	Vector<String> v =new Vector<String>();   	
    	try
		{
    	//测试在后台打印   		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select ISBN,B_Name,B_Author,B_Publishment from book where ISBN ='"+isbn+"'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));
			}			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return v;      	    	
    }
    
    //根据学生的ID得到他预约图书的基本信息
    public static Vector<String> getBNofromOrder(String stuNo)
    {
    	Vector<String> v =new Vector<String>();   	
    	try
		{
    	//测试在后台打印   		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select B_Num,S_Name,S_Num,B_Author from orderbook where S_Num ='"+stuNo+"'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中
				v.add(rs.getString(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));
			}			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return v;      	    	
    }
    //根据预约图书信息表得到某同学的预约图书信息
    public static int getNumfromborderreport(String stuno)
    {
		int num=0;
		try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select count(B_Num) from orderbook where B_Num='"+stuno+"'";
			ResultSet rs=st.executeQuery(sql);						
			if(rs.next()){//将结果集信息添加到返回向量中								
				//String[] middle=new String[6];
				num=rs.getInt(1);
			}		
			rs.close();
			st.close();
			con.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}		
    	return num;
    	
    }
    
    //根据学生的学号得到图书的ISBN，BookNO,BookName,Author,Publishment,借阅时间，归还时间
    public static Vector<String> getSomeInfo(String stuno)
    {
    	Vector<String> result=new Vector<String>();
		try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select record.B_Num,record.BorrowTime,record.ReturnTime,book.ISBN,book.B_Name,book.B_Author,book.B_Publishment from record,book,bdetailedinformation where record.B_Num=bdetailedinformation.B_Num AND bdetailedinformation.ISBN=book.ISBN And record.S_Num='"+stuno+"'";
			ResultSet rs=st.executeQuery(sql);
			int num=0;
			while(rs.next()){//将结果集信息添加到返回向量中								
				//String[] middle=new String[6];
				result.add(rs.getString(1));
				result.add(rs.getString(2));
				result.add(rs.getString(3));
				result.add(rs.getString(4));
				result.add(rs.getString(5));
				result.add(rs.getString(6));
				result.add(rs.getString(7));
				num++;
				System.out.println(num);
			}		
			rs.close();
			st.close();
			con.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}		
    	return result;
    	
    }
    
    
    
    //根据图书的书号得到图书的基本信息
    public static Vector<String> getBNSomeInfo(String BookNO)
    {
    	Vector<String> result=new Vector<String>();
		try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select record.B_Num,record.BorrowTime,record.ReturnTime,book.ISBN,book.B_Name,book.B_Author,book.B_Publishment from record,book,bdetailedinformation where record.B_Num=bdetailedinformation.B_Num AND bdetailedinformation.ISBN=book.ISBN And record.B_Num='"+BookNO+"'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中								
				//String[] middle=new String[6];
				result.add(rs.getString(1));
				result.add(rs.getString(2));
				result.add(rs.getString(3));
				result.add(rs.getString(4));
				result.add(rs.getString(5));
				result.add(rs.getString(6));
				result.add(rs.getString(7));				
			}		
			rs.close();
			st.close();
			con.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}		
    	return result;
    	
    }
    
    //根据预约图书书号得到图书基本信息
    public static Vector<String> getBNSomeINFO(String BookNO)
    {
    	Vector<String> result=new Vector<String>();
		try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select orderbook.B_Num,book.ISBN,book.B_Name,book.B_Author,book.B_Publishment,bdetailedinformation.Borrowed from orderreport,book,bdetailedinformation where orderbook.B_Num=bdetailedinformation.B_Num AND bdetailedinformation.ISBN=book.ISBN And orderbook.B_Num='"+BookNO+"'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中								
				//String[] middle=new String[6];
				result.add(rs.getString(1));
				result.add(rs.getString(2));
				result.add(rs.getString(3));
				result.add(rs.getString(4));
				result.add(rs.getString(5));
				result.add(rs.getString(6));
			}		
			rs.close();
			st.close();
			con.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}		
    	return result;
    	
    }
    
    //通过学生的ID得到学生的班级，姓名，学号
    public static String[] getIDClNO(String stuno)
    {
		String[] result=new String[3];
		try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select S_Num,S_Class,S_Name from student where S_Num='"+stuno+"'";
			ResultSet rs=st.executeQuery(sql);						
			if(rs.next()){//将结果集信息添加到返回向量中								
				//String[] middle=new String[6];
				result[0]=rs.getString(1);
				result[1]=rs.getString(2);
				result[2]=rs.getString(3);
			}		
			rs.close();
			st.close();
			con.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}		
    	return result;
    	
    }
    
    //通过书号得到归还时间
    public static String gettimefromrecord(String BookNo)
    {
		String result=null;
		try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select ReturnTime from record where B_Num='"+BookNo+"'";
			ResultSet rs=st.executeQuery(sql);						
			if(rs.next()){//将结果集信息添加到返回向量中								
				result=rs.getString(1);
			}		
			rs.close();
			st.close();
			con.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}		
    	return result;
    	
    }
    
    
    //通过书号判断时候是再借状态
    public static String getifBorrow(String BookNO)
    {
    	String result=null;
		try
		{
    	//测试在后台打印    		
			Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select ReturnTime from record where B_Num='"+BookNO+"'";
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next()){//将结果集信息添加到返回向量中								
				result=rs.getString(1);				
			}		
			rs.close();
			st.close();
			con.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}		
    	return result;
    	
    }
   
    //通过书号查询预约人
    public static String getstu(String BookNO)
    {
    	String stu=null;
    	try
    	{ 
    		Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select S_Num from orderbook where B_Num='"+BookNO+"'";
			ResultSet rs=st.executeQuery(sql);	
			if(rs.next())
			{
				stu=rs.getString(1);
			}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return stu;
    }
    //通过isbn获得书号
    public static String getBookNumber()
    {
    	String bookno=null;
    	int num=0;
    	String a = null;
    	try
    	{
    		Connection con=getConnection();				
			Statement st=con.createStatement();
			String sql="select count(B_Num) from bdetailedinformation";
			ResultSet rs=st.executeQuery(sql);	
			if(rs.next())
			{
				a=rs.getString(1);
			}
			num=Integer.parseInt(a)+1+10000;
			bookno=num+"";
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return bookno;
    }
}

