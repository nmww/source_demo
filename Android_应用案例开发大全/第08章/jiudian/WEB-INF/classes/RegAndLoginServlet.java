package wyf.wyy;
import java.io.*; 
import javax.servlet.*; 
import javax.servlet.http.*;
import java.util.*;
public class RegAndLoginServlet extends HttpServlet
{
	public void init(ServletConfig conf) throws ServletException 
	{ 
		super.init(conf);
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException 
	{
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException
	{
		req.setCharacterEncoding("gbk");
		res.setCharacterEncoding("gbk");
		//得到请求的响应action		
		String action = req.getParameter("action");
		//得到session对象
		HttpSession session=req.getSession(true);
		PrintWriter pw=res.getWriter();
		String msg="";
	/*if(action.equals("reg")){			
		//得到注册用户填写的资料
		String uname = req.getParameter("uname").trim();			
		String pwd = req.getParameter("pwd").trim();
		String telNum = req.getParameter("telNum").trim();
		String realName = req.getParameter("realName").trim();
		String gender = req.getParameter("gender");
		String email = req.getParameter("email").trim();
		//拼装查看用户是否存在的SQL
		String sqla = "select * from user where uname='"+uname+"'";
		if(DB.isExist(sqla)){
			msg=uname+":用户名已存在，请试试另一个！！！<br/><a href=reg.jsp>重新注册</a>";
		}
		else{//插入新用户的SQL				
			String sql = "insert into user values ('"+uname+"','"+pwd+"'"+
					",'"+telNum+"','"+realName+"','"+gender+"','"+email+"')";							
			DB.update(sql);//执行插入动作
			msg=uname+":注册成功！！！<br/><a href=login.jsp>现在登陆？？？</a>";
		}
		req.setAttribute("msg",msg);//转发消息
		req.getRequestDispatcher("info.jsp").forward(req,res);
	}

		else if(action.equals("login")){			
			session.removeAttribute("uname");//从session中移除当前登陆用户
			String uname = req.getParameter("uname").trim();//得到登陆用户名
			String pwd =  req.getParameter("pwd").trim();//得到登陆密码
			//拼装查看用户是否存在的SQL
			String sqla = "select * from user where uname='"+uname+"'";
			if(DB.isExist(sqla)){
				String sql = "select pwd from user where uname='"+uname+"'";
				String password=DB.getInfo(sql).trim();//从数据库得到密码
				if(pwd.equals(password)){				
					session.setAttribute("uname",uname);//登陆成功
					res.sendRedirect("main.jsp");//跳转到主页	
				}
				else{
					msg=uname+"密码不正确！！！<br><br>"+
					       "<a href=login.jsp>重新登陆</a>";					
					req.setAttribute("msg",msg);//将信息发送到信息显示页面
					req.getRequestDispatcher("info.jsp").forward(req,res);				
				}
			}
			else{
				msg=uname+"此用户名不存在！！！<br><br>"+
				     "<a href=login.jsp>重新登陆</a>";				
				req.setAttribute("msg",msg);//将信息发送到信息显示页面
				req.getRequestDispatcher("info.jsp").forward(req,res);
			}	
		}						
		else if(action.equals("logout")){
			session.removeAttribute("uname");//从session中移除登陆用户
			msg = "欢迎再次光临！！！";//提示消息
			req.setAttribute("msg",msg);//设置并转发提示消息
			req.getRequestDispatcher("info.jsp").forward(req,res);
		}		
		else if(action.equals("changeMyInfo")){
			String uname=(String)session.getAttribute("uname");
			//得到修改后的信息
			String telNum = req.getParameter("telNum").trim();
			String realName = req.getParameter("realName").trim();
			String gender = req.getParameter("gender");
			String email = req.getParameter("email").trim();
			//生成更新SQL
			String sql="update user set telNum='"+telNum+"',realName='"+realName+
			   "',gender='"+gender+"',email='"+email+"'where uname='"+uname+"'";
			if(DB.update(sql)==1){
				msg = "修改资料成功！！！<br>";				
				req.setAttribute("msg",msg);//将信息发送到信息显示页面
				req.getRequestDispatcher("info.jsp").forward(req,res);
			}
		}
		else if(action.equals("changePwd")){
			//得到提交的信息
			String uname=(String)session.getAttribute("uname");
			String currentPwd = req.getParameter("currentPwd").trim();
			String newPwd = req.getParameter("newPwd").trim();
			//从数据库拿到当前用户的密码
			String sqla = "select pwd from user where uname='"+uname+"'";
			String pwdFromDB =  DB.getInfo(sqla);			
			if(currentPwd.equals(pwdFromDB)){//比较密码
				String sqlb = "update user set pwd='"+newPwd+"'where uname='"+
								uname+"'";//更新此用户的密码
				DB.update(sqlb);//执行更新
				msg = "密码修改成功！！！下一次请用新密码登陆。<br>";
			}
			else{
				msg = "您输入的密码不正确，修改失败！！！<br>"+
				       "<a href=changeMyInfo.jsp>返回继续修改？？？";
			}			
			req.setAttribute("msg",msg);//将信息发送到信息显示页面
			req.getRequestDispatcher("info.jsp").forward(req,res);
		}*/

		if(action.equals("adlogin")){
			session.removeAttribute("adname");
			String adname = req.getParameter("adname").trim();//得到登陆名
			String pwd =  req.getParameter("pwd").trim();//得到登陆密码
			//拼装从数据库得到登陆管理员密码的SQL语句
			String sqla = "select adpwd from adinfo where adname='"+adname+"'";
			String pwdFromDB = DB.getInfo(sqla);//执行查询得到正确密码
			if(pwdFromDB!=null&&pwd.equals(pwdFromDB)){//登陆成功			
				session.setAttribute("adname",adname);//将登陆管理员保存进session
				msg = "登陆成功。";//提示登陆成功
			}
			else{//登陆失败的提示
				msg = "错误的用户名和密码，请重新登陆<br><br>"+
				 		"<a href=adindex.jsp>重新登陆";				
			}
			//将信息发送到信息显示页面
			req.setAttribute("msg",msg);
			req.getRequestDispatcher("adinfo.jsp").forward(req,res);
		}
		else if(action.equals("adlogout")){//注销
			session.removeAttribute("adname");
			msg = "退出成功。";//提示注销成功
			req.setAttribute("msg",msg);
			req.getRequestDispatcher("adinfo.jsp").forward(req,res);
		}
	}
}