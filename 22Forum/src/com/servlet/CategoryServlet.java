package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDao;
import com.dao.CategoryDao;
import com.dao.GroupCategoryDao;
import com.dao.UserDao;
import com.toolsbean.StringHandler;
import com.valuebean.CategoryBean;
import com.valuebean.UserBean;

public class CategoryServlet extends SuperServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String[] subPaths=(String[])request.getAttribute("subPaths");
		if(subPaths!=null&&subPaths.length==4){
			String operate=subPaths[3];
			try {
				Method md=getClass().getDeclaredMethod(operate,new Class[]{HttpServletRequest.class,HttpServletResponse.class});		//根据opName变量值获取名称为opName变量值的方法。Class数组指定该方法中各参数的类型
				md.setAccessible(true);													//设为true后将能调用私有方法
				md.invoke(this,new Object[]{request,response});							//调用方法，Object数组指定方法需要的参数
			}catch (Exception e) {
				e.printStackTrace();
				invalidate(request,response);	
			}			
		}
		else{
			invalidate(request,response);
		}
	}
	private void alllist(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		try{
			List categorylist=new CategoryDao().getCategoryList();					//获取所有类别
			if(categorylist!=null&&categorylist.size()!=0){							//接下来获取每个类别下的所有版块
				request.setAttribute("categorylist",categorylist);
				Map allboardmap=new HashMap();
				for(int i=0;i<categorylist.size();i++){
					int categoryId=((CategoryBean)categorylist.get(i)).getId();
					List oneboardlist=new BoardDao().getBoardList(categoryId);
					allboardmap.put(categoryId,oneboardlist);						//通过Map对象以当前类别ID作为关键字key保存当前类别下的所有版块；				
				}
				request.setAttribute("allboards",allboardmap);						//保存所有类别的所有版块
			}	
		}catch(SQLException e){
			System.out.println("获取论坛类别失败！");
			e.printStackTrace();
		}		
		
		request.setAttribute("mainPage",getInitParameter("alllistPage"));
		RequestDispatcher rd=request.getRequestDispatcher(INDEXTEMP);
		rd.forward(request,response);
	}
	private void onelist(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try{
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				message="<li>类别ID值错误！</li>";
				forward=RUNFAIL;
			}
			else{
				CategoryBean single=new CategoryDao().getCategorySingle(categoryId);							//获取某个类别
				if(single==null){
					message="<li>要访问的类别不存在后已经被删除！</li>";
					forward=RUNFAIL;
				}
				else{																	//获取该类别下的所有版面
					forward=INDEXTEMP;
					request.setAttribute("mainPage",getInitParameter("onelistPage"));
					request.setAttribute("category",single);
					
					List oneboardlist=new BoardDao().getBoardList(categoryId);
					request.setAttribute("oneboardlist",oneboardlist);								//保存当前类别下的所有版面				
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}			
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void forummanager(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		try{
			List categorylist=new CategoryDao().getCategoryList();					//获取所有类别
			if(categorylist!=null&&categorylist.size()!=0){							//接下来获取每个类别下的所有版块
				request.setAttribute("categorylist",categorylist);
				Map allboardmap=new HashMap();
				for(int i=0;i<categorylist.size();i++){
					int categoryId=((CategoryBean)categorylist.get(i)).getId();
					List oneboardlist=new BoardDao().getAdminBoardList(categoryId);
					allboardmap.put(categoryId,oneboardlist);						//通过Map对象以当前类别ID作为关键字key保存当前类别下的所有版块；				
				}
				request.setAttribute("allboards",allboardmap);						//保存所有类别的所有版块
			}
		}catch(SQLException e){
			System.out.println("后台获取论坛类别失败！");
			e.printStackTrace();
		}		
		
		request.setAttribute("mainPage",getInitParameter("managerindex"));
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}
	
	private void updateorder(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		try {
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				request.setAttribute("message","<li>类别ID值错误！</li>");
				RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
				rd.forward(request,response);
			}
			else{
				CategoryDao categoryDao=new CategoryDao();
				CategoryBean single = categoryDao.getCategorySingle(categoryId);
				if(single==null){
					String message="<li>要重新排序的类别不存在后已经被删除！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					request.setAttribute("message",message);
					RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
					rd.forward(request,response);
				}
				else{
					int maxorder=Integer.parseInt(getServletContext().getInitParameter("maxorder"));
					int order=single.getCategoryOrder();
					
					String direction=request.getParameter("direction");
					if("up".equals(direction)){
						if(order>0)
							categoryDao.updateOrderToUp(categoryId);
					}
					else if("down".equals(direction)){
						if(order<maxorder)
							categoryDao.updateOrderToDown(categoryId);
					}	
					response.sendRedirect(request.getContextPath()+"/admin/category/c/forummanager");
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	private void addcategoryview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		request.setAttribute("mainPage",getInitParameter("addcategoryview"));
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}
	private void addcategoryrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String categoryName=request.getParameter("categoryName");
		CategoryDao categoryDao=new CategoryDao();
		try {
			if(categoryDao.isexistbyname(categoryName)){
				message="<li>类别 '"+categoryName+"' 已经存在！请重新输入！</li>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				String categoryInfo=request.getParameter("categoryInfo");
				int categoryOrder=0;
				String categoryFoundTime=StringHandler.timeTostr(new Date());
				Object[] params={categoryName,categoryInfo,categoryOrder,categoryFoundTime};
				int i=categoryDao.insert(params);
				if(i<=0){
					message="<li>添加新类别失败！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("mainPage",RUNSUCCESS);
					forward=ADMINTEMP;			
					
					message="<li>添加新类别成功！3秒后将自动跳转到 <b>论坛管理</b> 模块主页！</li>";
					String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
					request.setAttribute("autoforward",autoforward);
				}			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addboardview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				request.setAttribute("message","<li>类别ID值错误！</li>");
				forward=RUNFAIL;
			}
			else{
				CategoryBean single = new CategoryDao().getCategorySingle(categoryId);
				if(single==null){
					message="<li>要添加新版块的类别不存在后已经被删除！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("categorysingle",single);
					request.setAttribute("mainPage",getInitParameter("addboardview"));
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
	private void addboardrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String boardName=request.getParameter("boardname");
		BoardDao boardDao=new BoardDao();
		try {
			if(boardDao.isexistbyname(boardName)){
				message="<li>版块 '"+boardName+"' 已经存在！请重新输入！</li>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
				String boardInfo=request.getParameter("boardinfo");
				String boardStatus=request.getParameter("boardstatus");
				int boardOrder=0;
				int boardAllTopicNum=0;
				int boardBestTopicNum=0;
				String boardFoundTime=StringHandler.timeTostr(new Date());
				Object[] params={categoryId,boardName,boardInfo,boardStatus,boardOrder,boardAllTopicNum,boardBestTopicNum,boardFoundTime};
				int i=boardDao.insert(params);
				if(i<=0){
					message="<li>添加新版块失败！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("mainPage",RUNSUCCESS);
					forward=ADMINTEMP;			
					
					message="<li>添加新版块成功！3秒后将自动跳转到 <b>论坛管理</b> 模块主页！</li>";
					String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
					request.setAttribute("autoforward",autoforward);
				}			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addcategorymasterview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				request.setAttribute("message","<li>类别ID值错误！</li>");
				forward=RUNFAIL;
			}
			else{
				CategoryBean single = new CategoryDao().getCategorySingle(categoryId);
				if(single==null){
					message="<li>要添加新类主的类别不存在或已经被删除！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("categorysingle",single);
					request.setAttribute("mainPage",getInitParameter("addcategorymasterview"));
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
	private void addcategorymasterrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			String newmaster=request.getParameter("newmaster");

			try {
				UserDao userDao=new UserDao();
				UserBean single = userDao.getUserSingle(newmaster);
				if(single==null){									//如果不存在该用户名
					message+="用户 <font color='red'>"+newmaster+"</font> 不存在！";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else if(single.getGroupId()!=2){
					message+="用户 <font color='red'>"+newmaster+"</font> 不属于类别管理组成员，不能为其分配类别！";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else if(StringHandler.isexist(categoryId,single.getAssignCategoryId())){
					message="<li>已经为用户 "+newmaster+" 分配了当前类别！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{
					int i=new GroupCategoryDao().addnewmaster(single.getId(), categoryId);
					if(i<=0){
						message="<li>添加新类主失败！</li>";
						message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
						forward=RUNFAIL;
					}
					else{
						request.setAttribute("mainPage",RUNSUCCESS);
						forward=ADMINTEMP;
						
						message="<li>添加新类主成功！3秒后将自动跳转到 <b>论坛管理</b> 模块主页！</li>";
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
	private void modifycategoryview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				request.setAttribute("message","<li>类别ID值错误！</li>");
				forward=RUNFAIL;
			}
			else{
				CategoryBean single = new CategoryDao().getCategorySingle(categoryId);
				if(single==null){
					message="<li>要修改信息的类别不存在后已经被删除！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("categorysingle",single);
					request.setAttribute("mainPage",getInitParameter("modifycategoryview"));
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
	private void modifycategoryrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
		String categoryname=request.getParameter("categoryname");
		String categoryinfo=request.getParameter("categoryinfo");
		Integer categoryorder=StringHandler.strToint(request.getParameter("categoryorder"));
		Object[] params={categoryname,categoryinfo,categoryorder,categoryId};
		
		int i=new CategoryDao().update(params);
		if(i<=0){
			message="<li>修改类别信息失败！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			request.setAttribute("mainPage",RUNSUCCESS);
			forward=ADMINTEMP;	
			
			message="<li>修改类别信息成功！3秒后将自动跳转到 <b>论坛管理</b> 模块主页！</li>";
			String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
			request.setAttribute("autoforward",autoforward);
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deletecategoryview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			CategoryDao categoryDao=new CategoryDao();
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				request.setAttribute("message","<li>类别ID值错误！</li>");
				forward=RUNFAIL;
			}
			else{
				CategoryBean single =categoryDao.getCategorySingle(categoryId);
				if(single==null){
					message="<li>要删除的类别不存在后已经被删除！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else{
					//查询该类别下是否有版块
					boolean mark=new BoardDao().isexistincategory(categoryId);
					if(mark){				//存在版块
						message="<li>该类别下存在版块，不能进行删除操作！</li>";
						message+="<a href='javascript:window.history.go(-1)'>返回</a>";
						forward=RUNFAIL;
					}
					else{					//不存在
						request.setAttribute("categorysingle",single);
						request.setAttribute("mainPage",getInitParameter("deleteycategoryview"));
						forward=ADMINTEMP;
					}				
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deletecategoryrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			int i=new CategoryDao().delete(categoryId);
			if(i<=0){
				message="<li>删除类别失败！</li>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{				
				request.setAttribute("mainPage",RUNSUCCESS);
				forward=ADMINTEMP;
				
				message="<li>删除类别成功！3秒后将自动跳转到 <b>论坛管理</b> 模块主页！</li>";
				String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
				request.setAttribute("autoforward",autoforward);
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
}