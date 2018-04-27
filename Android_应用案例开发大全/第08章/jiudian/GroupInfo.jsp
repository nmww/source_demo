<%@ page contentType="text/html;charset=gbk"
    import="java.util.*,wyf.wyy.*"
%>
<html>
  <head>
   <title>分组管理</title>
   <script language="JavaScript">
    function check()
    {
      if(document.groInfo.gNameAfter.value=="")
      {
         alert("请填写分组名！！！");
         groInfo.gNameAfter.focus();
         return false;
      }
      if(document.groInfo.gImg.value=="")
      {
         alert("请填写图片URL！！！");
         groInfo.gImg.focus();
         return false;
      }
      if(document.groInfo.gDetail.value=="")
      {
         alert("请填写描述信息！！！");
         groInfo.gDetail.focus();
         return false;
      }
      if(document.groInfo.gOrderDet.value=="")
      {
         alert("请填写预订的一些规则！！！");
         groInfo.gOrderDet.focus();
         return false;
      }
      document.groInfo.action.value="changeGroup";
      document.groInfo.submit();
    }    
   </script>
  </head>
 <body>
   <%@ include file="admintop.jsp" %><br>   
    <%
      Vector<String> ginfo = 
      	 (Vector<String>)request.getAttribute("ginfo");
 	%>
   <center>
    <font color="black" size="5.5"><%= ginfo.get(1) %>信息
   </center>
   <table align="center" border="0" width="60%">
    <form name="groInfo" action="ListServlet" method="post">
     <tr bgcolor="ffeeee">
      <td align="right" width="20%">组名:</td>
      <td><input type="text" name="gNameAfter" value=<%= ginfo.get(1) %>></td>
     </tr>
     
     <tr>
      <td align="right">图片URL:</td>
      <td><input type="text" name="gImg" value=<%= ginfo.get(3) %>></td>
     </tr>
     
     <tr bgcolor="ffeeee">
      <td align="right">描述:</td>
      <td>
        <textarea rows=6 cols=50 name="gDetail"><%= ginfo.get(4) %></textarea>
      </td>
     </tr>
     <tr>
      <td align="right">规则:</td>
      <td>
        <textarea rows=6 cols=50 name="gOrderDet"><%= ginfo.get(2) %></textarea>
      </td>
     </tr>
     <tr bgcolor="ffeeee"><td></td>
      <td align="left">
       <input type="hidden" name="action" value="deleteGroup">
       <input type="hidden" name="gId" value=<%= ginfo.get(0) %> >
       <input type="hidden" name="gNameBefor" value=<%= ginfo.get(1) %>>       
       <input type="button" value="保存" onclick="check()">
       &nbsp&nbsp&nbsp&nbsp
       <input type="submit" value="删除">
       <font color="red" size="2">*删除分组，将会删除分组下所有的资源
      </td>
     </tr>
    </form>
   </table><br>
 </body>
</html>