package com.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
	private FilterConfig fc;
	private Map notloginlist=null;

	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)sRequest;
		HttpSession session=request.getSession();	

		String[] subPaths=(String[])request.getAttribute("subPaths");		
		String way=subPaths[0];													//获取前、后台标识
		if(way.equals("visit")){													//如果请求前台操作
			String operate=subPaths[3];													//获取请求的操作
			if(!notloginlist.containsValue(operate)){									//如果请求的是"需要登录"的操作：如发表帖子，回复帖子等
				Object loginer=session.getAttribute("loginer");
				if(loginer==null&&!(loginer instanceof com.valuebean.UserBean)){		//如果没有登录
					RequestDispatcher rd=request.getRequestDispatcher(fc.getInitParameter("visitlogin"));
					rd.forward(request,sResponse);
				}
				else
					chain.doFilter(sRequest,sResponse);	
			}
			else																		//如果请求的是"不需要登录"的操作，继续执行
				chain.doFilter(sRequest,sResponse);
		}
		else if(way.equals("admin")){													//如果请求后台，对每个请求都要验证用户是否登录，并且验证是否有权限进入后台
			Object loginer=session.getAttribute("loginer");
			if(loginer==null&&!(loginer instanceof com.valuebean.UserBean)){		//如果没有登录
				RequestDispatcher rd=request.getRequestDispatcher(fc.getInitParameter("adminlogin"));
				rd.forward(request,sResponse);
			}
			else
				chain.doFilter(sRequest,sResponse);	
		}
	}
	
	public void init(FilterConfig fc) throws ServletException {
		this.fc=fc;
		notloginlist=new HashMap();
		notloginlist.put("1", "listspecialtopic");
		notloginlist.put("2", "listcommontopic");
		notloginlist.put("3", "view");
		notloginlist.put("4", "newreplylist");
		notloginlist.put("5", "morereplylist");
		notloginlist.put("6", "viewmember");
		notloginlist.put("7", "newlist");
		notloginlist.put("8", "morelist");
		notloginlist.put("9", "alllist");
		notloginlist.put("10", "onelist");
		notloginlist.put("11", "listmemberposttopic");
	}	
	public void destroy() {
		this.fc=null;
		notloginlist=null;
	}
}