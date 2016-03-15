package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.GroupBoardDao;
import com.dao.GroupCategoryDao;
import com.dao.GroupDao;
import com.dao.UserDao;
import com.toolsbean.StringHandler;
import com.valuebean.GroupBean;
import com.valuebean.UserBean;

public class GroupServlet extends SuperServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String[] subPaths=(String[])request.getAttribute("subPaths");
		if(subPaths!=null&&subPaths.length==4){
			String operate=subPaths[3];
			System.out.println("方法名称："+operate);
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
	private void groupmanager(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		List grouplist=new GroupDao().getGroupList();					//获取所有用户组
		request.setAttribute("grouplist",grouplist);
		request.setAttribute("mainPage",getInitParameter("managerindex"));
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}	
	private void listmemberingroup(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
		if(groupId==null){
			message="<li>用户组ID错误！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>";
			forward=RUNFAIL;
		}
		else{
			String currentP=request.getParameter("currentP");
			String currentG=request.getParameter("currentG");
			String goWhich=request.getContextPath()+"/admin/group/e/listmemberingroup?groupId="+groupId;
			
			try {
				UserDao userDao=new UserDao();			
				List memberlist=userDao.getGroupUserList(groupId,currentP, currentG, goWhich);
				request.setAttribute("memberlist",memberlist);
				request.setAttribute("pageBar",userDao.getDaoPage());
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}
		
		request.setAttribute("mainPage",getInitParameter("listmemberingroup"));
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}	
	private void modifygroupview(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
		if(groupId==null){
			message="<li>用户组ID错误！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				GroupBean single = new GroupDao().getGroupSingle(groupId);
				if(single==null){
					message="<li>要修改的用户组不存在或已经被删除！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("groupsingle",single);
					request.setAttribute("mainPage",getInitParameter("modifygroupview"));
					forward=ADMINTEMP;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			request.setAttribute("message",message);
			RequestDispatcher rd=request.getRequestDispatcher(forward);
			rd.forward(request,response);
		}
	}
	private void modifygrouprun(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
		String groupInfo=request.getParameter("groupInfo");
		Object[] params={groupInfo,groupId};
		int i=new GroupDao().update(params);
		if(i<=0){
			message="<li>修改失败！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>";
			forward=RUNFAIL;
		}
		else{
			request.setAttribute("mainPage",RUNSUCCESS);
			forward=ADMINTEMP;			
			
			message="<li>修改用户组成功！3秒后将自动跳转到 <b>用户组管理</b> 模块主页！</li>";
			String autoforward=request.getContextPath()+"/admin/group/e/groupmanager";
			request.setAttribute("autoforward",autoforward);
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addmembertogroupview(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
		if(groupId==null){
			message="<li>用户组ID错误！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				GroupBean single = new GroupDao().getGroupSingle(groupId);
				if(single==null){
					message="<li>要添会员的用户组不存在或已经被删除！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("groupsingle",single);
					request.setAttribute("mainPage",getInitParameter("addmembertogroupview"));
					forward=ADMINTEMP;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addmembertogrouprun(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{			
			Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
			List inputmembernames=StringHandler.StringToList(request.getParameter("inputmembernames")+"\r\n");
			
			if(inputmembernames!=null&&inputmembernames.size()!=0){
				request.setAttribute("mainPage",getInitParameter("addmembertogroupresult"));
				forward=ADMINTEMP;
				
				try {
					UserDao userDao=new UserDao();
					List newmembernames=new ArrayList();
					
					//检查输入的每个用户名称是否存在，并且是否为普通会员(group_id字段值为0)
					for(int i=0;i<inputmembernames.size();i++){
						String name=(String)inputmembernames.get(i);
						UserBean single=userDao.getUserSingleForAddGroupMember(name);
						if(single==null)									//如果用户名称不存在论坛中
							message+="× 会员 <font color='red'>"+name+"</font> 不存在！<br>";
						else if(single.getGroupId()==0){						//如果存在该用户名，检查是否为普通会员
							newmembernames.add(name);							//如果是普通会员，保存该用户名
							message+="√ 会员 <font color='red'>"+name+"</font> 添加成功！<br>";
						}
						else
							message+="× 会员 <font color='red'>"+name+"</font> 已经具有其他职务，不能兼职！<br>";
					}
					
					if(newmembernames.size()!=0){
						int i=userDao.updateUserGroupId(groupId,newmembernames);
						if(i<=0){
							message="<li>添加成员失败！</li><br>";
							message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
							forward=RUNFAIL;
						}
						else{
							message+=">><a href='"+request.getHeader("referer")+"'>继续添加</a><br>";
							message+=">><a href='"+request.getContextPath()+"/admin/group/e/groupmanager'>返回用户组管理模块主页</a>";
						}
					}
					else
						message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else{
				message="<li>获取输入的成员名称失败！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void removefromgroupview(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
		String removememberName=request.getParameter("removememberName");
		if(groupId==null||removememberName==null||removememberName.equals("")){
			message="<li>用户组ID值或成员名称错误！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserDao userDao=new UserDao();
				int removememberId = userDao.validatename(removememberName);
				if(removememberId<=0){
					message="<li>成员(name="+removememberName+")不存在！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{
					UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
					int loginerId=loginer.getId();
					if(loginerId==removememberId){
						message="<li>不能移除自己！</li><br>";
						message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
						forward=RUNFAIL;
					}
					else if(loginer.getGroupId()<groupId){
						message="<li>您没有权限移除具有更高级别身份的会员！</li><br>";
						message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
						forward=RUNFAIL;
					}
					else{
						request.setAttribute("removememberId",removememberId);
						request.setAttribute("mainPage",getInitParameter("removefromgroupview"));
						forward=ADMINTEMP;
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
	private void removefromgrouprun(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{	
			Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
			Integer removememberId=StringHandler.strToint(request.getParameter("removememberId"));
			String removememberName=request.getParameter("removememberName");
			
			List members=new ArrayList();
			members.add(removememberName);
			int i=new UserDao().updateUserGroupId(0,members);							//将会员的用户组ID(group_id字段)修改为0
			if(i<=0){
				message="<li>移除成员失败！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				if(groupId==2)						//如果从类别管理组移除成员
					new GroupCategoryDao().deleteallassignformember(removememberId);
				else if(groupId==1)					//如果从版块管理组移除成员
					new GroupBoardDao().deleteallassignformember(removememberId);
				
				request.setAttribute("mainPage",RUNSUCCESS);
				forward=ADMINTEMP;			
				
				message="<li>移除成员成功！3秒后将自动跳转到查看组成员页面！</li>";
				String autoforward=request.getContextPath()+"/admin/group/e/listmemberingroup?groupId="+groupId;
				request.setAttribute("autoforward",autoforward);
			}			
		}		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
}
