<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,wyf.wyy.MyConverter,wyf.wyy.DB,java.util.*"
 %>
<%
   String param1=request.getParameter("param1").trim();
      //System.out.println(",用户数据已经传回客户端！");
   String uname=MyConverter.unescape(param1);
   Vector<String> userInfo=new Vector<String>(); 
   userInfo=DB.getUserInfo(uname);
   StringBuffer msg=new StringBuffer();
   for(String s:userInfo)
   {
 	msg.append(s);
 	msg.append("|");
   }
   String s=msg.toString();
   out.println(MyConverter.escape(s));
   //System.out.println(s+",用户数据已经传回客户端！");
%>