<%@ page contentType="text/html;charset=gbk"
    import="java.util.*,wyf.wyy.*"
%>
 <html>
  <head>
   <title>管理员修改密码</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
   <script language="javascript">
    function check()
    { 
      if(document.changePwd.adname.value=="")
      {
         alert("请填写登录名！！！");
         changePwd.adname.focus();
         return false;
      }
      if(document.changePwd.adpwd.value=="")
      {
         alert("请填写登陆密码！！！");
         changePwd.adpwd.focus();
         return false;
      }
      if(document.changePwd.newPwd.value=="")
      {
         alert("请填写新密码！！！");
         changePwd.newPwd.focus();
         return false;         
      }
      if(document.changePwd.newPwd.value!=document.changePwd.reNewPwd.value)
       {
         alert("两次密码输入不一样，请检查输入！！！");
         changePwd.newPwd.value="";
         changePwd.reNewPwd.value="";
         changePwd.newPwd.focus();
         return false;
       }
       document.changePwd.submit();
    }
   </script>
  </head>
 <body>
    <%@ include file="admintop.jsp" %>
    <hr width="100%"></hr><br>
    <table align="center" border="0" width="40%">
     <form name="changePwd" action="ListServlet" method="post">
      <tr bgcolor="eeffee">
       <td align="right">登录名</td>
       <td><input type="text" name="adname" size="20"></td>
      </tr>
      <tr bgcolor="ffeeff">
       <td align="right">登陆密码</td>
       <td><input type="password" name="adpwd" size="20"></td>
      </tr>
      <tr bgcolor="eeffee">
       <td align="right">新密码</td>
       <td><input type="password" name="newPwd" size="20"></td>
      </tr>
      <tr bgcolor="ffeeff">
       <td align="right">再次输入新密码</td>
       <td><input type="password" name="reNewPwd" size="20"></td>
      </tr>
      <tr bgcolor="eeffee">
       <td></td>
       <td>
        <input type="hidden" name="action" value="changePwd">
        <input type="button" value="修改" onclick="check()">
       </td>
      </tr>
     </form>
    </table>    
 </body>
</html>