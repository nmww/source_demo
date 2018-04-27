<%@ page contentType="text/html;charset=gbk" 
    import="java.util.*"%>
<html>
 <head>
   <title>订单明细</title>
    <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
 </head>
 <body>
   <%Vector<String[]> ListDetail =
     (Vector<String[]>)request.getAttribute("ListDetail");
    String oid =(String)request.getAttribute("oid");%>
    <br><center>订单<%= oid %>明细</center><br>
   <table align="center" width="70%" cellspacing="1" bgcolor="black">
	 <tr width="60%" height="30" bgcolor="white">
	   <th>编号</th>      <th>类别</th> <th>开始时间</th>  
	   <th>结束时间</th>  <th>状态</th>
	  </tr>
	   <%int color = 0;//控制每行颜色
	  	 for(int i=0;i<ListDetail.size();i++){
		   String[] s = ListDetail.get(i);%>
     <tr bgcolor=<%= color%2==0?"eeffee":"ffeeee" %> height="40">
	   <td align="center"><%= s[0] %></td>
	   <td align="center"><%= s[4] %></td>
	   <td align="center"><%= s[1] %></td>
	   <td align="center"><%= s[2] %></td>
	   <td align="center"><%= s[3] %></td>
	</tr><%color++;}%>
   </table>
 </body>
</html>