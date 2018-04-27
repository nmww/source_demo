<%@ page contentType="text/html;charset=gbk"
    import="java.util.*,wyf.wyy.*"
%>
 <html>
  <head>
   <title>资源管理</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
   <script language="JavaScript">
    function check2()
    {
       if(document.resInfo.rgidAfter.value=="")
       {
         alert("编号为空！！！");
         resInfo.rgidAfter.focus();
         return false;
       }
       if(document.resInfo.rlevel.value=="")
       {
         alert("规格为空");
         resInfo.rlevel.focus();
         return false;
       }
       if(document.resInfo.rmoney.value=="")
       {
         alert("价格为空！！！");
         resInfo.rmoney.focus();
         return false;
       }
       if(document.resInfo.rdetail.value=="")
       {
         alert("描述为空！！！");
         resInfo.rdetail.focus();
         return false;
       }
       document.resInfo.action.value="changeRes";
       document.resInfo.submit();
    }
   </script>
  </head>
 <body>
   <%@ include file="adminRestop.jsp" %>	
    <%
      Vector<String[]> rinfo = 
      	 (Vector<String[]>)request.getAttribute("rinfo");
 	  String []s = rinfo.get(0);
 	%>
   <table align="center" border="0" width="60%">
    <form name="resInfo" action="ListServlet" method="post">
     <tr bgcolor="ffeeee">
      <td align="right" width="20%">编号:</td>
      <td><input type="text" name="rgidAfter" value=<%= s[0] %>></td>
     </tr>
     <tr>
      <td align="right">分组:</td>
      <td>
       <select name="rgroup">
        <%
         for(String ss[]:vgroup)
         {
           if(ss[0].equals(s[7]))
           {
         %>           
             <option selected value=<%= ss[3] %>><%= ss[0] %></option>
         <%
           }
           else
           {
           %>
             <option value=<%= ss[3] %>><%= ss[0] %></option>
           <%
           }
          }
          %>
       </select>          
      </td>
     </tr>
     
     <tr bgcolor="ffeeee">
      <td align="right">规格:</td>
      <td><input type="text" name="rlevel" value=<%= s[1] %>></td>
     </tr>
     <tr>
      <td align="right">价格/时段:</td>
      <td><input type="text" name="rmoney" value=<%= s[2] %>></td>
     </tr>
     <tr bgcolor="ffeeee">
       <td align="right">描述:</td>
       <td>
         <textarea rows=4 cols=40 name="rdetail"><%= s[3] %></textarea>
       </td>
     </tr>
     <tr>
      <td align="right">状态:</td>
      <td>
       <select name="rstatus">
        <option>空闲</option>
        <%
        if(s[4].equals("占用"))
        {
        %>
        <option selected>占用</option>
        <%
        }
        else
        {
        %>
        <option>占用</option>
        <% 
        }
         %>                
       </select>
      </td>
     </tr>
     <tr bgcolor="ffeeee"><td></td>
      <td align="left">
       <input type="hidden" name="action" value="deleteRes">
       <input type="hidden" name="rid" value=<%= s[6] %>>
       <input type="hidden" name="rgidBefor" value=<%= s[0] %>>       
       <input type="button" value="保存" onclick="check2()">
       &nbsp&nbsp&nbsp&nbsp
       <input type="submit" value="删除">
       <font color="red" size="2">*删除资源之前请先确认该资源没有订单且处于空闲状态
      </td>
     </tr>
    </form>
   </table><br>
 </body>
</html>