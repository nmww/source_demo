package wyf.wyy;
import java.io.*; 
import java.util.*;
import javax.servlet.*; 
import javax.servlet.http.*;
public class ListServlet extends HttpServlet
{
	public void init(ServletConfig conf) throws ServletException
	{//Servlet的inti初始化方法
		super.init(conf);
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException{//doGet方法
		doPost(req,res);//调用doPost方法
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException{
		req.setCharacterEncoding("gbk");//设置req编码为gbk
		res.setCharacterEncoding("gbk");//设置res编码为gbk		
		String action = req.getParameter("action");//得到请求的响应action		
		HttpSession session=req.getSession(true);//得到session对象
		//得到登陆的管理员ID		
		String adnameSes = (String)session.getAttribute("adname");
		String msg = "";//声明消息字符串
		/*if(action.equals("list")){//得到分组的预订规则			
			int gId = Integer.parseInt(req.getParameter("gId"));
			Vector<String> list = DB.getGroupInfo(gId);
			session.setAttribute("list",list);
			res.sendRedirect("groupList.jsp");
		}
		else if(action.equals("status")){
			Vector<String []> v = null;//发送到显示资源状态页面的信息
			String isOrdered = "NO";//是否被预定，初始为"NO"，即没有被预订
			String rgid = req.getParameter("rgid");//得到要查看状态的资源编号
			//资源正在被预定中，未处理之前不再接受订单
			if(Order_DB.isOrdered(rgid)) {isOrdered = "YES";}
			//可以预订时，从数据库得到已经被预订列表
			else {v = Order_DB.getOrderedDay(rgid);}
			//设置资源状态的一些信息并转发到显示页面
			req.setAttribute("v",v);
			req.setAttribute("rgid",rgid);
			req.setAttribute("isOrdered",isOrdered);	
			req.getRequestDispatcher("lookRes.jsp").forward(req,res);			
		}*/
//************************************资源***********************************************
		if(action.equals("adminList")){
			Vector<String> list = new Vector<String>();
			if(session.getAttribute("adname")!=null){//判断管理员是否登陆
			    int gId = Integer.parseInt(req.getParameter("gId"));
			    if(gId==0){//0代表所有分组
			    	list.add("0");
			    	list.add("所有分组");
			    }
			    else{//某一特定分组
			    	list = DB.getGroupInfo(gId);
			    }								
				session.setAttribute("list",list);
				res.sendRedirect("adminResource.jsp");
			}
			else{//管理员没有登陆
				msg = "请先登陆";
				this.forward(req,res,msg,"adinfo.jsp");		 
			}
		}
		else if(action.equals("queryRes")){//查询
			String rgid = req.getParameter("rgid");//得到资源ID
			String sql = "select rgid,rlevel,rmoney,rdetail,rstatus,rgroup,rid "+
										"from resource where rgid='"+rgid+"'";			
			Vector<String[]> v = DB.getResInfo(sql);//执行查询
			req.setAttribute("list",v);
			req.getRequestDispatcher("ResQuery.jsp").forward(req,res);	
		}
		
		else if(action.equals("editRes")){//编辑资源
			int rid = Integer.parseInt(req.getParameter("rid"));
			String sql = "select rgid,rlevel,rmoney,rdetail,rstatus,rgroup,rid from resource where rid='"+rid+"'";
			Vector<String []> rinfo = DB.getResInfo(sql);
			req.setAttribute("rinfo",rinfo);
			req.getRequestDispatcher("ResInfo.jsp").forward(req,res);
		}
		else if(action.equals("changeRes")){			
			String rgidBefor = req.getParameter("rgidBefor");//编辑前的编号
			String rgidAfter = req.getParameter("rgidAfter");//编辑后的编号			
			int rid = Integer.parseInt(req.getParameter("rid"));//得到资源ID主键
			//得到修改后的信息
			String rgroup = req.getParameter("rgroup");
			String rlevel = req.getParameter("rlevel").trim();
			double rmoney = Double.parseDouble(req.getParameter("rmoney").trim());
			String rdetail = req.getParameter("rdetail").trim();
			String rstatus = req.getParameter("rstatus");			
			String sql = "update resource set rgid='"+rgidAfter+"',rgroup='"+rgroup+"',rlevel='"+rlevel+
			             "',rmoney="+rmoney+",rdetail='"+rdetail+"',rstatus='"+rstatus+"' where rid="+rid;			
			if(rgidBefor.equals(rgidAfter)){//组内编号没改变
				if(DB.update(sql)>0){
					msg = "修改保存成功<br><br><a href=ListServlet?action=adminList&&gId=0>返回";
				}
			}
			else{//组内编号改变了
			    String sqla = "select * from resource where rgid='"+rgidAfter+"'";
				if(DB.isExist(sqla)){
					msg = "已经有此编号的资源，请核对输入。<br><br>"+
				      	  "<a href=ListServlet?action=editRes&&rid="+rid+">返回继续修改";
				}
			 	else{
			 		if(DB.update(sql)>0){
			 			msg = "修改保存成功<br><br><a href=ListServlet?action=adminList&&gId=0>返回";
			 		}
			 	}
			}
			this.forward(req,res,msg,"adinfo.jsp");		 	
		}
		else if(action.equals("deleteRes")){
			String rid = req.getParameter("rid");//得到要删除的ID号
			String sql = "delete from resource where rid='"+rid+"'";
			if(DB.update(sql)>0){
				msg = "删除成功<br><br><a href=ListServlet?action=adminList&&gId=0>返回";						 
			}
			else{
				msg = "未知错误，删除失败";
			}
			this.forward(req,res,msg,"adinfo.jsp");
		}
		else if(action.equals("addRes")){
			//得到要添加资源的详细信息
			String rgid = req.getParameter("rgid").trim();
			String rgroup = req.getParameter("rgroup");
			String rlevel = req.getParameter("rlevel").trim();
			Double rmoney = Double.parseDouble(req.getParameter("rmoney").trim());
			String rdetail = req.getParameter("rdetail").trim();
			String rstatus = req.getParameter("rstatus");			
			String sql = "select * from resource where rgid='"+rgid+"'";
			if(DB.isExist(sql))	{
				msg="此编号对应的资源已经存在，请核对编号输入。<br><a href=addRes.jsp>返回";
			}
			else{
				int rid = DB.getId("resource","rid");//得到资源表中主键最大值加1
				sql = "insert into resource(rid,rgroup,rgid,rlevel,rmoney,rdetail,rstatus)"+
				      "values("+rid+",'"+rgroup+"','"+rgid+"','"+rlevel+"',"+rmoney+",'"+
				       rdetail+"','"+rstatus+"')";
				if(DB.update(sql)>0){
					msg = "增加资源成功！！！<br><a href=addRes.jsp>返回";
				}
			}
			this.forward(req,res,msg,"adinfo.jsp");		 
		}
//***************************分组*******************************************
		else if(action.equals("adminGroup")){
			if(session.getAttribute("adname")!=null){//判断管理员是否登陆			   
				res.sendRedirect("adminGroup.jsp");
			}
			else{//没有登陆
				msg = "请先登陆";
				this.forward(req,res,msg,"adinfo.jsp");//转发到消息 页面	 
			}
		}
		else if(action.equals("editGroup")){//编辑分组
			int gId = Integer.parseInt(req.getParameter("gId"));
			Vector<String> ginfo = DB.getGroupInfo(gId);
			req.setAttribute("ginfo",ginfo);
			req.getRequestDispatcher("GroupInfo.jsp").forward(req,res);
		}
		else if(action.equals("changeGroup")){//修改分组信息后提交		    
		    int gId = Integer.parseInt(req.getParameter("gId"));//得到分组ID
		    String gNameBefor = req.getParameter("gNameBefor");//得到修改前的名字
		    //得到修改后的信息
		    String gNameAfter = req.getParameter("gNameAfter");
		    String gImg = req.getParameter("gImg");
		    String gDetail = req.getParameter("gDetail");
		    String gOrderDet = req.getParameter("gOrderDet");		    
		    String sql = "update rgroup set gName='"+gNameAfter+"',gImg='"+gImg+"',gDetail='"+gDetail+
		    			"',gOrderDet='"+gOrderDet+"' where gId="+gId;//拼装SQL		    
		    if(gNameAfter.equals(gNameBefor)){//判断分组名字是否改变
		    	if(DB.update(sql)>0){
					msg = "修改保存成功<br><br><a href=ListServlet?action=adminGroup>返回";
				}
		    }
		    else{//分组名字改变
		    	String sqla = "select * from rgroup where gName='"+gNameAfter+"'";
				if(DB.isExist(sqla)){//名字已经存在
					msg = "已经有此名字的分组，请核对输入。<br>"+
							"<a href=ListServlet?action=editGroup&&gId="+gId+">返回";
				}
			 	else{//分组名不存在
			 		if(DB.update(sql)>0){
			 			msg = "修改保存成功<br><br><a href=ListServlet?action=adminGroup>返回";
			 		}
			 	}
		    }
		   this.forward(req,res,msg,"adinfo.jsp");		 			
		}
		else if(action.equals("deleteGroup")){
			int gId = Integer.parseInt(req.getParameter("gId"));//得到要删除的ID
			String sqla = "delete from resource where rgroup="+gId;//删除分组记录的SQL
			String sqlb = "delete from rgroup where gId="+gId;//删除分组下资源的SQL
			if(DB.update(sqla,sqlb)==true){
				msg = "删除分组成功<br><br><a href=ListServlet?action=adminGroup>返回";
			}
			else{
				msg = "未知错误，删除失败";
			}
			this.forward(req,res,msg,"adinfo.jsp");		 
		}
		else if(action.equals("addGroup")){
			int gId = DB.getId("rgroup","gId");//得到添加分组的ID
			//得到填写的信息
			String gName = req.getParameter("gName");
			String gImg = req.getParameter("gImg");
		    String gDetail = req.getParameter("gDetail");
		    String gOrderDet = req.getParameter("gOrderDet"); 
		    String sql = "insert into rgroup(gId,gName,gImg,gDetail,gOrderDet)values('"+gId+"','"+
		    				gName+"','"+gImg+"','"+gDetail+"','"+gOrderDet+"')";//拼装SQL   
		    if(DB.update(sql)>0){//执行更新
		    	msg = "添加分组成功<br><br><a href=ListServlet?action=adminGroup>返回";
		    }
		    else{
		    	msg = "未知错误，添加失败";
		    }
		    this.forward(req,res,msg,"adinfo.jsp");	
		}
//********************************管理员*****************************************
		else if(action.equals("admanage")){			
			if(adnameSes==null){//判断管理员是否登陆
			    msg = "请先登陆";
			    this.forward(req,res,msg,"adinfo.jsp");		 				
			}
			else{
				String sql = "select adlevel from adinfo where adname='"+adnameSes+"'";				
				int adlevel = Integer.parseInt(DB.getInfo(sql));//得到管理员级别
				if(adlevel!=1){
					msg = "对不起，权限不够";
					this.forward(req,res,msg,"adinfo.jsp");		 
				}
				else{
					res.sendRedirect("adminManage.jsp");
				}
			}
		}
		else if(action.equals("addAdmin")){//添加管理员
			String adnameAdd = req.getParameter("adname");
			String adpwd = req.getParameter("adpwd");
			String sql = "insert into adinfo(adname,adpwd,adlevel)values"+
			  			 "('"+adnameAdd+"','"+adpwd+"',"+0+")";
			String sqla = "select * from adinfo where adname='"+adnameAdd+"'";
			if(DB.isExist(sqla)){
				msg = "此管理员ID已经存在请核对输入";
			}
			else{
				if(DB.update(sql)>0)
				msg = "添加管理员成功";
			}		
			this.forward(req,res,msg,"adinfo.jsp");		 
		}
		else if(action.equals("deleteAdmin")){
			//得到要删除的管理员ID
			String adnameDel = req.getParameter("adname");
			if(adnameDel.equals(adnameSes)){
				msg = "不能删除自己";
			}
			else{
				String sql = "delete from adinfo where adname='"+adnameDel+"'";
				DB.update(sql);
				msg = "删除成功";
			}
			this.forward(req,res,msg,"adinfo.jsp");
		}
		else if(action.equals("resetPwd")){
			String adname = req.getParameter("adname").trim();
			String adpwd = req.getParameter("adpwd").trim();
			String sql = "update adinfo set adpwd='"+adpwd+"' where adname='"+adname+"'";
			if(DB.update(sql)>0){
				msg = "设置密码成功";
			}
			else{
				msg = "设置失败，请重新检查设置";
			}
			this.forward(req,res,msg,"adinfo.jsp");
		}
		else if(action.equals("changePwd")){//管理员修改密码
			String adname = req.getParameter("adname");
			String adpwd = req.getParameter("adpwd");
			String newPwd = req.getParameter("newPwd");
			String sql = "select adpwd from adinfo where adname='"+adname+"'";
			String pwdFromDB = DB.getInfo(sql);
			if(pwdFromDB==null){
				msg = "该管理员用户不存在，请重新核对输入";
			}
			else if(!pwdFromDB.equals(adpwd)){
				msg = "密码输入不正确，请重新输入";
			}
			else{
				sql = "update adinfo set adpwd='"+newPwd+"' where adname='"+adname+"'";				
				if(DB.update(sql)>0){
					msg = "修改成功";
				}
				else{
					msg = "未知错误，修改失败！！！";
				}				
			}
			this.forward(req,res,msg,"adinfo.jsp");			
		}			
}
	public void forward(HttpServletRequest req,HttpServletResponse res,
	String msg,String url)throws ServletException,IOException
	{
		req.setAttribute("msg",msg);//设置消息
		req.getRequestDispatcher("adinfo.jsp").forward(req,res);//转发到消息页面
	}
}