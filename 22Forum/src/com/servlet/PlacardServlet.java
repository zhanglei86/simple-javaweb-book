package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDao;
import com.dao.PlacardDao;
import com.dao.UserDao;
import com.toolsbean.StringHandler;
import com.valuebean.PlacardBean;
import com.valuebean.UserBean;

public class PlacardServlet extends SuperServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
		if(boardId==null){
			request.setAttribute("message","<li>版块ID值错误！</li>");
			RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
			rd.forward(request,response);	
		}
		else{
			request.getSession().setAttribute("visitboard",boardId);													//保存当前版块ID到session中，用来记忆当前访问的版块
			request.getSession().setAttribute("visitcategory",new BoardDao().getBelongToCategoryId(boardId));				//保存当前版块所属类别的ID到session中，用来记忆当前访问的类别
			request.getSession().setAttribute("visitboardstatus",new BoardDao().getBoardStatus(boardId));
			
			//以下代码用来实现：根据请求来调用相应的方法的功能
			String[] subPaths=(String[])request.getAttribute("subPaths");
			if(subPaths!=null&&subPaths.length==4){
				String operate=subPaths[3];													//获取请求的操作(方法名称)
				try {
					Method md=getClass().getDeclaredMethod(operate,new Class[]{HttpServletRequest.class,HttpServletResponse.class});		//根据opName变量值获取名称为opName变量值的方法。Class数组指定该方法中各参数的类型
					md.setAccessible(true);													//设为true后将能调用私有方法
					md.invoke(this,new Object[]{request,response});							//调用方法，Object数组指定方法需要的参数
				}catch (Exception e) {
					e.printStackTrace();
					invalidate(request,response);	
				}			
			}
			else
				invalidate(request,response);
		}		
	}	
	
	private void view(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer placardId=StringHandler.strToint(request.getParameter("placardId"));
		if(placardId==null){
			message="<li>公告ID值错误！</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				PlacardBean single = new PlacardDao().getPlacardSingle(placardId);
				if(single==null){
					message="<li>要查看的公告不存在或已经被删除！</li>";
					forward=RUNFAIL;
				}
				else{
					forward=INDEXTEMP;
					request.setAttribute("mainPage",getInitParameter("viewplacard"));
					request.setAttribute("placardsingle",single);
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	/** @功能：处理请求进入发布公告页面的方法 */
	private void postplacardview(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("postplacardview"));			
		
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
	/** @功能：处理请求发布公告的方法 */
	private void postplacardrun(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String forward="";
		String message="";
		request.setAttribute("mainPage",getInitParameter("postplacardview"));			
		
		int currentboardId=StringHandler.strToint(request.getParameter("boardId"));
		
		int categoryId=new BoardDao().getBelongToCategoryId(currentboardId);
		int boardId=currentboardId;
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String time=StringHandler.timeTostr(new Date());
		String author=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		String type=request.getParameter("type");
		
		if("2".equals(type)){
			categoryId=-1;
			boardId=-1;
		}
		else if("1".equals(type))
			boardId=-1;
		
		PlacardDao placardDao=new PlacardDao();
		Object[] params={categoryId,boardId,title,content,time,type,author};
		int i=placardDao.add(params);
		if(i<=0){
			message="<li>分布公告失败！</li>";
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			int placardId=placardDao.getId(time,author);			
			String autoforward=request.getContextPath()+"/visit/placard/a/view?placardId="+placardId+"&boardId="+currentboardId;
			request.setAttribute("autoforward",autoforward);
			message="√ 发布公告成功！3秒后将自动转发到发布的公告！";
		}		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
	private void deleteplacardview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer placardId=StringHandler.strToint(request.getParameter("placardId"));
		if(placardId==null){
			message="<li>公告ID值错误！</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				PlacardBean single=new PlacardDao().getPlacardSingle(placardId);
				if(single==null){
					message="<li>要删除的公告不存在或已经被删除！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else{
					forward=INDEXTEMP;
					request.setAttribute("mainPage",getInitParameter("deleteplacardview"));
					request.setAttribute("deletesingle",single);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void deleteplacardrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			Integer placardId=StringHandler.strToint(request.getParameter("placardId"));
			int i=new PlacardDao().deletePlacard(placardId);
			if(i<=0){
				message="<li>删除公告失败！</li><br>";
				message="<a href='javascript:window.history.go(-1)>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
				String autoforward=request.getContextPath()+"/visit/board/a/listspecialtopic?boardId="+boardId;	
				request.setAttribute("autoforward",autoforward);
				message="√ 删除公告成功！3秒后将自动转发到当前版块！";
			}			
		}		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
}
