<%@ page contentType="text/html;charset=gbk"
    import="java.util.*,wyf.wyy.*"
%>
 <html>
  <head>
   <title>添加管理员</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
   <script language="javascript">
    function check()
    { 
      if(document.addAdmin.adname.value=="")
      {
         alert("请填写登录名！！！");
         addAdmin.adname.focus();
         return false;
      }
      if(document.addAdmin.adpwd.value=="")
      {
         alert("请填写登陆密码！！！");
         addAdmin.adpwd.focus();
         return false;
      }
      if(document.addAdmin.repwd.value=="")
      {
         alert("请重复密码！！！");
         addAdmin.repwd.focus();
         return false;
      }
      if(document.addAdmin.adpwd.value!=document.addAdmin.repwd.value)
       {
         alert("两次密码输入不一样，请检查输入！！！");
         addAdmin.adpwd.value="";
         addAdmin.repwd.value="";
         addAdmin.adpwd.focus();
         return false;
       }
       document.addAdmin.submit();
    }
   </script>
  </head>
 <body>
    <%@ include file="admintop.jsp" %>
    <hr width="100%"></hr><br>
    <table align="center" border="0" width="40%">
     <form name="addAdmin" action="ListServlet" method="post">
      <tr bgcolor="eeffee">
       <td align="right">登录名</td>
       <td><input type="text" name="adname" size="20"></td>
      </tr>
      <tr bgcolor="ffeeff">
       <td align="right">登陆密码</td>
       <td><input type="password" name="adpwd" size="20"></td>
      </tr>
      <tr bgcolor="eeffee">
       <td align="right">重复密码</td>
       <td><input type="password" name="repwd" size="20"></td>
      </tr>
      <tr bgcolor="ffeeff">
       <td></td>
       <td>
        <input type="hidden" name="action" value="addAdmin">
        <input type="button" value="添加" onclick="check()">
       </td>
      </tr>
     </form>
    </table>    
 </body>
</html>