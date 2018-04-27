<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,wyf.wyy.MyConverter,wyf.wyy.DB,java.util.*,wyf.wyy.Order_DB"
 %>
<%
  String params1=request.getParameter("params1");
  //String params2=request.getParameter("params2");
  String param1=MyConverter.unescape(params1);
  //String param2=MyConverter.unescape(params2);
  //System.out.print(param1);
  //int rgroup=Integer.parseInt(param1);
  Vector<String[]> v = DB.getResource(param1);
  //System.out.print("+++++++++");
  String msg="";
  for(String[]s:v)
  {
      msg+=s[0]+"|"+s[1]+"|"+s[2]+"|"+s[3]+"|";
  }
  //System.out.println(msg);
  out.println(MyConverter.escape(msg));
  //System.out.print("111111");
%>