<%@ page contentType="text/html;charset=gbk"
    import="java.util.*"%>
<script language="JavaScript">
    function check()
    {
       if(document.searchRes.rgid.value=="")
       {
         alert("请输入编号查询！！！");
         searchRes.rgid.focus();
         return false;
       }
       document.searchRes.submit();
    }
</script>
<%@ include file="admintop.jsp" %>
  <hr width="100%"></hr><br>
   <table align="left" border="0" width="98.5%">
   <tr>    
      <td align="center">
        <a target="balnk" href="addRes.jsp">添加资源</a>
      </td>
      <form name="searchRes" action="ListServlet" method="post">     
      <td align="right">编号:	   
	    <input type="text" name="rgid">
	    <input type="button" value="查询" onclick="check()">
	    <input type="hidden" name="action" value="queryRes">
	  </td>
    </form>
    </tr>
   </table><br><br><br>
   <table align="center" bgcolor="C4DCDF" width="80%">
    <th>分组</th>
     <td align="center">
      <a href=ListServlet?action=adminList&&gId=0>所有分组</a>
     </td>
    <%
      Vector<String[]> vgroup = DB.getGroup();//得到分组信息
      for(String[] s:vgroup){%><td align="center">
      <a href=ListServlet?action=adminList&&gId=<%=s[3]%>><%= s[0] %></a> 
    </td>
     <%}%>
</table>