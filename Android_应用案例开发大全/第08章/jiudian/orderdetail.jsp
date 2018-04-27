<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,wyf.wyy.MyConverter,wyf.wyy.DB,wyf.wyy.Order_DB,java.util.*"
 %>
 <% 
     String param1=request.getParameter("param1").trim();
     String oid=MyConverter.unescape(param1);
     Vector<String[]> v=new Vector<String[]>();
     v=Order_DB.getOrderDetail(oid);
     StringBuffer msg=new StringBuffer();
     for(String[] s:v)
   {
 	msg.append(s[0]);
 	msg.append("|");
 		msg.append(s[1]);
 	msg.append("|");
 		msg.append(s[2]);
 	msg.append("|");
 		msg.append(s[3]);
 	msg.append("|");
 		msg.append(s[4]);
 	msg.append("|");
   }
   String s=msg.toString();
   out.println(MyConverter.escape(s));
  // System.out.println(s+",用户数据已经传回客户端！");
  %>