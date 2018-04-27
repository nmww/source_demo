package wyf.wyy;
import java.io.*; 
import java.util.*;
import javax.servlet.*; 
import javax.servlet.http.*;
public class OrderServlet extends HttpServlet{
	public void init(ServletConfig conf) throws ServletException 
	{ //Servlet的inti初始化方法
		super.init(conf);
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException{//doGet方法
		doPost(req,res);//调用doPost方法
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException{
		req.setCharacterEncoding("gbk");
		res.setCharacterEncoding("gbk");		
		String action = req.getParameter("action");//得到请求的响应action		
		HttpSession session=req.getSession(true);//得到session对象
		PrintWriter pw=res.getWriter();//得到输出流对象
		String msg="";//声明提示消息		
		String uname = (String)session.getAttribute("uname");//得到登陆用户名
		//资源的几种状态
		String ostatus1 = "预订中";
		String ostatus2 = "预订成功";
		String ostatus3 = "预订失败";
		Vector<String[]> OrderList = //得到订单列表
					(Vector<String[]>)session.getAttribute("OrderList");
		if(OrderList==null)//如果为空则创建一个订单列表对象
		{OrderList = new Vector<String[]>();}		
		/*if(action.equals("ADD")){	
			String orderNum = (String)req.getParameter("orderNum");//得到资源名		
			if(orderNum!=null){//得到预订开始时间
				String fyear = (String)req.getParameter("fyear");
				String fmonth = (String)req.getParameter("fmonth");
				String fday = (String)req.getParameter("fday");
				String fhour = (String)req.getParameter("fhour");
				//得到预订结束时间
				String eyear = (String)req.getParameter("eyear");
				String emonth = (String)req.getParameter("emonth");
				String eday = (String)req.getParameter("eday");
				String ehour = (String)req.getParameter("ehour");
				//得到所预订的资源分组
				String group = (String)req.getParameter("group");
				String ftime = fyear+"-"+fmonth+"-"+fday+"-"+fhour+":"+"00";
				String etime = eyear+"-"+emonth+"-"+eday+"-"+ehour+":"+"00";				
				String[] s = new String[4];
				s[0] = orderNum; s[1] = group;
				s[2] = ftime;    s[3] = etime;				
				OrderList.add(s);//将订单加入到订单列表中				
				session.setAttribute("OrderList",OrderList);//将订单列表放到session								
			}
			res.sendRedirect("groupList.jsp");//发回到groupList页面
		}
		else if(action.equals("DELETE")){			
			String id = req.getParameter("index");//得到要删除订单的下标			
			int index = Integer.parseInt(id);//转换为int值			
			OrderList.remove(index);//删除此选中的订单
			res.sendRedirect("myOrder.jsp");//发回订单页面
		}
		else if(action.equals("REMOVE")){		
			OrderList.removeAllElements();//移除所有订单			
			session.setAttribute("OrderList",OrderList);//将订单列表放进session			
			res.sendRedirect("myOrder.jsp");//发回订单页面			
		}
		else if(action.equals("SUBMIT")){			
			if(uname!=null){//判断用户是否登陆
				if(Order_DB.addOrder(uname,OrderList)!=-1){					
					OrderList.removeAllElements();//提交完成，清空订单列表
					msg = "订单提交成功，本酒店欢迎您的光临<br><br>"+
					       "<a href=main.jsp>返回主页";//给出成功消息
				}
				else{
					msg = "对不起，系统错误，提交失败！<br><br>"+
					       "<a href=main.jsp>返回主页";//给出失败消息
				}			
			}
			else{//用户没有登陆的情况
				msg = "您还没有登陆，请先登陆。<br><br>"+
				     "<a href=login.jsp>现在登陆>>";		
			}
			req.setAttribute("msg",msg);//将消息发送到消息显示页面
			req.getRequestDispatcher("info.jsp").forward(req,res);	
		}
		else if(action.equals("order")){
			if(uname==null){//用户没有登陆
				msg = "您还没有登陆，请先登陆。<br><br>"+
				     "<a href=login.jsp>现在登陆>>";
				req.setAttribute("msg",msg);
				req.getRequestDispatcher("info.jsp").forward(req,res);
			}
			else{//查询该用户的所有订单信息
				String sql = "select * from olist where oname='"+uname+"'";
				Vector<String []> list = Order_DB.getOrderList(sql);//执行查询				
				req.setAttribute("list",list);
				req.getRequestDispatcher("list.jsp").forward(req,res);
			}
		}*/
		if(action.equals("ListDetail")){
			String oid = req.getParameter("oid");//得到订单编号
			Vector<String []> ListDetail = Order_DB.getOrderDetail(oid);//执行查询				
			req.setAttribute("ListDetail",ListDetail);
			req.setAttribute("oid",oid);
			req.getRequestDispatcher("detail.jsp").forward(req,res);
		}
		
		else if(action.equals("allOrders")){//按条件查询订单
			if(session.getAttribute("adname")!=null){//管理员是否登陆
				String sql = "";//声明SQL引用
				int conditon = Integer.parseInt(req.getParameter("condition"));
				switch(conditon){
					case 1://1表示所有订单
					sql = "select * from olist";
					break;
					case 2://2表示已经处理的订单
					sql = "select * from olist where ostatus='"+ostatus2+"' or ostatus='"+ostatus3+"'";
					break;
					case 3://3表示未处理的订单
					sql = "select * from olist where ostatus='"+ostatus1+"'";
					break;
				}
				Vector<String []> list = Order_DB.getOrderList(sql);				
				req.setAttribute("list",list);//将订单列表返回				
				req.getRequestDispatcher("adminOrders.jsp").forward(req,res);
			}
			else{
				msg = "请先登陆";//没有登陆的提示消息
				req.setAttribute("msg",msg);
				req.getRequestDispatcher("adinfo.jsp").forward(req,res);
			}
		}
		else if(action.equals("query")){//按编号查询订单
		    Vector<String []> list = null;
		    try{
		    	int oid = Integer.parseInt(req.getParameter("oid"));
				String sql = "select * from olist where oid="+oid;
				list = Order_DB.getOrderList(sql);
		    }
		    catch(NumberFormatException nfe)//输入订单号格式不正确
		    {list = new Vector<String []>();}//返回一个空的向量							
			req.setAttribute("list",list);
			req.getRequestDispatcher("adminOrders.jsp").forward(req,res);
		}
		else if(action.equals("dealOrder")){//处理订单
			String adname = (String)session.getAttribute("adname");
			String reason = req.getParameter("reason");
			String ostatus = req.getParameter("ostatus");
			int oid = Integer.parseInt(req.getParameter("oid"));
			//拼装SQL
			String sqla = "update olist set ostatus='"+ostatus+"',oreason='"+
							reason+"',odeal='"+adname+"'where oid="+oid;
			String sqlb = "update oinfo set ostatus='"+ostatus+"' where oid="+oid;			
			boolean b = DB.update(sqla,sqlb);//执行更新		
			if(b==true){
				msg = "订单处理成功<br><br>"
					+"<a href=OrderServlet?action=allOrders&&condition=1>返回";
			}
			else{msg = "订单处理发生错误，处理失败";}
			req.setAttribute("msg",msg);//返回处理消息
			req.getRequestDispatcher("adinfo.jsp").forward(req,res);
		}
	}
}