package com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDao;
import com.toolsbean.StringHandler;
import com.valuebean.UserBean;

public class LogXServlet extends SuperServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String operate=request.getServletPath();
		if("/visitlogin".equals(operate)||"/adminlogin".equals(operate))
			login(request,response);
		else if("/loginrun".equals(operate))
			loginrun(request,response);
		else if("/visitlogout".equals(operate)||("/adminlogout".equals(operate)))
			logout(request,response);			
		else if("/regview".equals(operate))
			regview(request,response);
		else if("/checkmember".equals(operate))
			checkmember(request,response);
		else if("/regrun".equals(operate))
			regrun(request,response);
		else
			invalidate(request,response);
	}
	private void login(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward="";
		String operate=request.getServletPath();
		
		String autoforward=request.getHeader("referer");
		if("/adminlogin".equals(operate)){
			forward=getInitParameter("adminloginview");
			if(autoforward==null||autoforward.equals(""))
				autoforward=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/adminindex";
			request.setAttribute("autoforward",autoforward);			
		}
		else{
			forward=getInitParameter("visitloginview");
			if(autoforward==null||autoforward.equals(""))
				autoforward=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/index";
			request.setAttribute("autoforward",autoforward);
			
		}			
		
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void loginrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		HttpSession session=request.getSession();		
		String message="";
		String forward="";
		
		String name=request.getParameter("membername");
		String pswd=request.getParameter("memberpswd");

		try {
			String autoforward=(String)request.getParameter("autoforward");
			if(autoforward==null||autoforward.equals(""))
				autoforward=request.getHeader("referer");
			if(autoforward==null||autoforward.equals("")||autoforward.equals(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/regrun"))
				autoforward=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/index";
			request.setAttribute("autoforward",autoforward);
			
			UserDao userDao=new UserDao();
			UserBean loginer = userDao.login(name, pswd);
			if(loginer!=null){
				String incode=request.getParameter("inverifycode");
				String getcode=(String)session.getAttribute("getverifycode");
				if(!incode.equalsIgnoreCase(getcode)){
					message="<li>输入的验证码不正确！<br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{
					if("1".equals(loginer.getMemberStatus())){
						message="登录成功！3秒后将自动转发到该请求：<br><a href='"+autoforward+"'>"+autoforward+"</a>！";
						forward=RUNSUCCESS;
						
						session.setAttribute("loginer",loginer);		
						addcookie(request,response);
						userDao.updateLoginTime(StringHandler.timeTostr(new Date()),loginer.getId());
					}
					else{
						message="<li>用户名'<font color='red'>"+name+"</fotn>'已经被冻结，不能进行登录！</li><br>";
						message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
						forward=RUNFAIL;
					}					
				}
			}
			else{
				message="<li>用户名或密码错误！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}

	private void logout(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward="";
		String autoforward="";
		
		String servletPath=request.getServletPath();
		if("/adminlogout".equals(servletPath)){
			forward=getInitParameter("adminloginview");
			autoforward=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/adminindex";
		}
		else{
			forward=getInitParameter("visitloginview");
			autoforward=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/index";
		}

		request.setAttribute("autoforward",autoforward);
		request.getSession().invalidate();
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	
	private void addcookie(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String webname=request.getContextPath();
		webname=webname.substring(1);
		String auto=request.getParameter("autologin");
		if("on".equals(auto)){
			Cookie name=new Cookie(webname+".loginname",request.getParameter("membername"));
			Cookie pswd=new Cookie(webname+".loginpswd",request.getParameter("memberpswd"));
			name.setPath("/");
			pswd.setPath("/");
			int maxage=0;			
			String limit=request.getParameter("limit");
			if("oneYear".equals(limit))
				maxage=60*60*24*365;
			else if("oneMonth".equals(limit))
				maxage=60*60*24*31;
			else if("oneWeek".equals(limit))
				maxage=60*60*24*7;
			else
				maxage=60*60*24;
			
			name.setMaxAge(maxage);
			pswd.setMaxAge(maxage);	
			response.addCookie(name);
			response.addCookie(pswd);			
		}
	}
	private void regview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		request.setAttribute("operate",request.getServletPath());
		request.setAttribute("mainPage",getInitParameter("regview"));		
		RequestDispatcher rd=request.getRequestDispatcher(INDEXTEMP);
		rd.forward(request,response);
	}
	private void checkmember(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		request.setAttribute("operate",request.getServletPath());
		String name=request.getParameter("name");
		boolean disabled=true;
		try {
			//查询name指定的用户是否存在
			int memberid = new UserDao().validatename(name);
			if(memberid>0)										//存在该会员名称
				disabled=true;
			else												//不存在
				disabled=false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("disabled",disabled);		
		request.setAttribute("mainPage",getInitParameter("regview"));
		RequestDispatcher rd=request.getRequestDispatcher(INDEXTEMP);
		rd.forward(request,response);		
	}	
	private void regrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		try {
			String name=request.getParameter("name");
			boolean disabled=true;
			//查询name指定的用户是否存在
			int memberid = new UserDao().validatename(name);
			if(memberid>0)										//存在该会员名称
				disabled=true;
			else												//不存在
				disabled=false;
			request.setAttribute("disabled",disabled);
			if(disabled){
				request.setAttribute("mainPage",getInitParameter("regview"));
				forward=INDEXTEMP;
			}
			else{
				String pswd=request.getParameter("pswd");
				String sex=request.getParameter("sex");
				String age=request.getParameter("age");
				String oicq=request.getParameter("oicq");
				String sign=request.getParameter("sign");		
				String time=StringHandler.timeTostr(new Date());
				if(oicq==null||oicq.equals(""))
					oicq="无";
				if(sign==null||sign.equals(""))
					sign="留下一片空白！";
				
				Object[] params={0,name,pswd,sex,age,oicq,"user0.gif",sign,"1",time,time,0,0,0};
				int i=new UserDao().insert(params);
				if(i<=0){
					message="<li>注册失败！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{
					message="<li>注册成功！3秒后将自动转发到登录页面！</li>";
					forward=RUNSUCCESS;
					request.setAttribute("autoforward",request.getContextPath()+"/visitlogin");
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
}
