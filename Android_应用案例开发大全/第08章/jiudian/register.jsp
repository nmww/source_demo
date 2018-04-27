<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,wyf.wyy.MyConverter,wyf.wyy.DB"
 %>
 <% 
   String param1=request.getParameter("yonghuming").trim();
   String param2=request.getParameter("mm").trim();
   String param3=request.getParameter("telnum").trim();
   String param4=request.getParameter("realname").trim();
   String param6=request.getParameter("sex").trim();
   String param5=request.getParameter("email").trim();
   
    String yonghuming=MyConverter.unescape(param1);
    String mm=MyConverter.unescape(param2);
    String telnum=MyConverter.unescape(param3);
    String realname=MyConverter.unescape(param4);
    String email=MyConverter.unescape(param5);
    String sex1=MyConverter.unescape(param6);
    System.out.print(sex1);
    
    String sqla="select pwd from user where uname='"+yonghuming+"'";
    if(DB.isExist(sqla)){
	 out.println(MyConverter.escape("用户名已存在，请试试另一个！！！"));	
	}
	else
	{
	 String sql="insert into user values ('"+yonghuming+"','"+mm+"'"+
					",'"+telnum+"','"+realname+"','"+sex1+"','"+email+"')";
	 DB.update(sql);
	 out.println(MyConverter.escape("注册成功,请输入用户名和密码以登录！"));
	}
  %>