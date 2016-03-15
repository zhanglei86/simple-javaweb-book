package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDao;
import com.dao.CategoryDao;
import com.dao.GradeDao;
import com.dao.GroupBoardDao;
import com.dao.PlacardDao;
import com.dao.TopicDao;
import com.dao.UserDao;
import com.toolsbean.StringHandler;
import com.valuebean.BoardBean;
import com.valuebean.UserBean;

public class BoardServlet extends SuperServlet {

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
			request.getSession().setAttribute("visitcategory",new BoardDao().getBelongToCategoryId(boardId));					//保存当前版块所属类别的ID到session中，用来记忆当前访问的类别
			request.setAttribute("visitboardstatus",new BoardDao().getBoardStatus(boardId));
			
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

	/** @功能：获取公告及置顶帖子 */
	private void listspecialtopic(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("listmain"));			
		
		try {
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));								//获取当前版块ID
			BoardDao boardDao=new BoardDao();				
			
			BoardBean boardsingle=boardDao.getBoardSingle(boardId);													//获取版块信息
			List placardlist=new PlacardDao().getPlacardList((Integer)boardsingle.getCategoryId(),boardId);			//获取公告
			List topTopicList=new TopicDao().getTopTopicList(boardId);												//获取置顶帖子
			
			request.setAttribute("boardsingle",boardsingle);
			request.setAttribute("placardlist",placardlist);
			request.setAttribute("topTopicList",topTopicList);				
		} catch (SQLException e) {			
			System.out.println("获取公告及置顶帖子失败！");
			e.printStackTrace();
		}

		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
	/** @功能：获取非置顶帖子 */
	private void listcommontopic(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		try {
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));						//获取当前版块ID
			String currentP=request.getParameter("currentP");
			String currentG=request.getParameter("currentG");
			String goWhich=request.getContextPath()+"/visit/board/a/listcommontopic?boardId="+boardId;
			
			TopicDao topicDao=new TopicDao();			
			List topicList=topicDao.getTopicList(boardId,currentP,currentG,goWhich);
			
			request.setAttribute("topicList",topicList);
			request.setAttribute("pageBar",topicDao.getDaoPage());
		} catch (SQLException e) {
			System.out.println("获取非置顶帖子失败！");
			e.printStackTrace();
		}
		
		RequestDispatcher rd=request.getRequestDispatcher(getInitParameter("listcommontopic"));
		rd.forward(request,response);
	}
	/** @功能：进入版块的发表帖子页面 */
	private void postview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		try {
			BoardDao boardDao=new BoardDao();
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			BoardBean boardsingle = boardDao.getBoardSingle(boardId);
			if(boardsingle==null){
				message="<li>版块不存在或已经被删除！</li>";
				message+="<a href='javascript:window.history.go(-1)>返回</a>";
				forward=RUNFAIL;
			}
			else{
				if(!"2".equals(boardsingle.getBoardStatus())){								//锁定或关闭版块
					message="<li>当前版块被锁定或关闭，不能发表帖子！</li>";
					message+="<a href='javascript:window.history.go(-1)>返回</a>";
					forward=RUNFAIL;
				}
				else{																		//开放版块
					forward=INDEXTEMP;
					request.setAttribute("mainPage",getInitParameter("postview"));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	/** @功能：发表帖子 */
	private void postrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String author=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		String emotion=request.getParameter("emotion");
		String hits="0";
		String type="0";
		String status="2";
		String replyNum="0";
		String attachment=request.getParameter("attachment");
		String posttime=StringHandler.timeTostr(new Date());
		String operatetime=posttime;
		Object[] params={boardId,title,content,author,emotion,hits,type,status,replyNum,"0",posttime,operatetime};
		
		TopicDao topicDao=new TopicDao();
		int i=topicDao.postTopic(params);
		if(i<=0){					//发表失败
			message="<li>发表帖子失败！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</>";
			forward=RUNFAIL;
		}						
		else{																	//发表成功
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			new UserDao().updatePostNum(author);								//累加会员发帖数量			
			new BoardDao().addTopicNum(boardId);								//累加当前版块的主题数
			new GradeDao().experPost(((UserBean)request.getSession().getAttribute("loginer")).getId());		//增加发帖人的经验值
	
			int topicId=topicDao.getJustTopicId(author, operatetime);
			if("1".equals(attachment)){											//添加附件
				String autoforward=request.getContextPath()+"/visit/attachment/c/uploadview?topicId="+topicId;			//自动转发到添加附件页面的路径
				request.setAttribute("autoforward",autoforward);
				request.setAttribute("message","√ 发表成功！下面将转发到添加附件页面！");
			}
			else{																//没有附件
				String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//自动转发到查看主题信息请求的路径
				request.setAttribute("autoforward",autoforward);
				request.setAttribute("message","√ 发表成功！3秒后将自动转发到您发表的帖子！");
			}
		}
		
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void updateorder(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
		if(boardId==null){
			String message="<li>版块ID值错误！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>";
			request.setAttribute("message",message);
			RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
			rd.forward(request,response);
		}
		else{
			BoardDao boardDao=new BoardDao();
			try {
				BoardBean single = boardDao.getBoardSingle(boardId);
				if(single==null){
					String message="要重新排序的版块不存在后已经被删除！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					request.setAttribute("message",message);
					RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
					rd.forward(request,response);
				}
				else{
					int maxorder=Integer.parseInt(getServletContext().getInitParameter("maxorder"));
					int order=single.getBoardOrder();
					
					String direction=request.getParameter("direction");
					if("up".equals(direction)){
						if(order>0)
							boardDao.updateOrderToUp(boardId);
					}
					else if("down".equals(direction)){
						if(order<maxorder)
							boardDao.updateOrderToDown(boardId);
					}	
					response.sendRedirect(request.getContextPath()+"/admin/category/c/forummanager");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private void addboardmasterview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			BoardBean single = new BoardDao().getBoardSingle(boardId);
			if(single==null){
				message="<li>要添加新版主的版块不存在后已经被删除！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
				forward=RUNFAIL;
			}
			else{
				request.setAttribute("boardsingle",single);
				request.setAttribute("mainPage",getInitParameter("addboardmasterview"));
				forward=ADMINTEMP;
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addboardmasterrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			String newmaster=request.getParameter("newmaster");
			
			try {
				UserDao userDao=new UserDao();
				UserBean single = userDao.getUserSingle(newmaster);
				if(single==null){									//如果用户名称不存在论坛中
					message+="用户 <font color='red'>"+newmaster+"</font> 不存在！<br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else if(single.getGroupId()!=1){
					message+="用户 <font color='red'>"+newmaster+"</font> 不属于版块管理组成员，不能为其分配版块！<br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else if(StringHandler.isexist(boardId,single.getAssignBoardId())){
					message="<li>已经为用户 "+newmaster+" 分配了当前版块！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{
					int i=new GroupBoardDao().addnewmaster(single.getId(), boardId);
					if(i<=0){
						message="<li>添加新版主失败！</li>";
						message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
						forward=RUNFAIL;
					}
					else{
						request.setAttribute("mainPage",RUNSUCCESS);
						forward=ADMINTEMP;
						
						message="<li>添加新版主成功！3秒后将自动跳转到 <b>论坛管理</b> 模块主页！</li>";
						String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
						request.setAttribute("autoforward",autoforward);
					}					
				}						
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void modifyboardview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			BoardBean single = new BoardDao().getBoardSingle(boardId);
			if(single==null){
				message="<li>要修改信息的版块不存在后已经被删除！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
				forward=RUNFAIL;
			}
			else{
				request.setAttribute("mainPage",getInitParameter("modifyboardview"));
				forward=ADMINTEMP;
				
				//获取所有类型的ID和名称
				Map categorys=new CategoryDao().getCategoryMap();
				request.setAttribute("categorys",categorys);
				request.setAttribute("boardsingle",single);
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void modifyboardrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
		int categoryId=StringHandler.strToint(request.getParameter("categoryid"));
		String boardname=request.getParameter("boardname");
		String boardinfo=request.getParameter("boardinfo");
		String boardstatus=request.getParameter("boardstatus");
		int boardorder=StringHandler.strToint(request.getParameter("boardorder"));
		Object[] params={categoryId,boardname,boardinfo,boardstatus,boardorder,boardId};
		
		int i=new BoardDao().update(params);
		if(i<=0){
			message="<li>修改版块信息失败！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			request.setAttribute("mainPage",RUNSUCCESS);
			forward=ADMINTEMP;	
			
			message="<li>修改版块信息成功！3秒后将自动跳转到 <b>论坛管理</b> 模块主页！</li>";
			String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
			request.setAttribute("autoforward",autoforward);
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deleteboardview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			BoardDao boardDao=new BoardDao();
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));

			BoardBean single =boardDao.getBoardSingle(boardId);
			if(single==null){
				message="<li>要删除的版块不存在或已经被删除！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
				forward=RUNFAIL;
			}
			else{				
				if(single.getBoardAllTopicNum()>0){
					message="<li>该版块下具有帖子，不能进行删除！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("boardsingle",single);
					request.setAttribute("mainPage",getInitParameter("deleteboardview"));
					forward=ADMINTEMP;
				}
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deleteboardrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			int i=new BoardDao().delete(boardId);
			if(i<=0){
				message="<li>删除版块失败！</li>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{				
				request.setAttribute("mainPage",RUNSUCCESS);
				forward=ADMINTEMP;
				
				message="<li>删除版块成功！3秒后将自动跳转到 <b>论坛管理</b> 模块主页！</li>";
				String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
				request.setAttribute("autoforward",autoforward);
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
}
