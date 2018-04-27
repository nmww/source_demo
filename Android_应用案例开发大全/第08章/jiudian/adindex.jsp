<%@ page contentType="text/html;charset=gbk"
    import="java.util.*"
%>
 <html>
  <head>
   <title>管理首页</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
   <script language="JavaScript">
    function Check()
    {
       if(document.adlogin.adname.value=="")
       {
         alert("请填写用户名！！！");
         adlogin.adname.focus();
         return false;
       }
       if(document.adlogin.pwd.value=="")
       {
         alert("请填写密码！！！");
         adlogin.pwd.focus();
         return false;
       }
       document.adlogin.submit();
    }
   </script>
  </head>
 <body>
   <%@ include file="admintop.jsp" %>
	<br><br><br>
	<table align="center" border="0" width="40%" bgcolor="aabbcc">
	 <form  name="adlogin" action="RegAndLoginServlet" method="post">
	     <tr height=20></tr>
		 <tr>
		  <td align="right" width="40%">用户名:</td>
		  <td colspan="2" width="60%">
		  	 <input type="text" name="adname" size="20">		    
		  </td>
		 </tr>
		  <tr>
		  <td align="right" width="40%">密码:</td>
		  <td colspan="2" width="60%">
		  	 <input type="password" name="pwd" size="20">		     
		  </td>
		 </tr>
		 <tr>
		  <td align="right">
		   <input type="hidden" name="action" value="adlogin">	
		   <input type="button" value="登录" onclick="Check()">
		  </td>		 
		  <td align="left">
		   &nbsp;&nbsp;&nbsp;&nbsp;
		   <input type="reset" value="重置">		   
		  </td>
		 </tr>
	  </form>
	 </table>
 </body>
</html>