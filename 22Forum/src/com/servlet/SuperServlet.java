package com.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDao;
import com.valuebean.UserBean;

public class SuperServlet extends HttpServlet {
	protected static String INDEXTEMP;
	protected static String ADMINTEMP;
	protected static String RUNSUCCESS;
	protected static String RUNFAIL;

	public void init() throws ServletException {
		INDEXTEMP=getServletContext().getInitParameter("indexTemp");
		ADMINTEMP=getServletContext().getInitParameter("adminTemp");
		RUNSUCCESS=getServletContext().getInitParameter("success");
		RUNFAIL=getServletContext().getInitParameter("fail");
	}
	/** @���ܣ���֤����Ա���� */
	protected boolean validatepswd(HttpServletRequest request){
		boolean mark=false;
		String name=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		String pswd=request.getParameter("adminpswd");
		mark=new UserDao().validatepswd(name,pswd);
		return mark;		
	}
	protected void invalidate(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="���Բ�������������Ч������<br>";
		message+="<a href='javascript:window.history.go(-1)'>����</a>";
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
		rd.forward(request,response);
	}	
}
