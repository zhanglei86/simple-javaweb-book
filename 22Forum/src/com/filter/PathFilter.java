package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.toolsbean.PathHandler;

public class PathFilter implements Filter {
	private FilterConfig fc;
	
	public void init(FilterConfig fc) throws ServletException {
		this.fc=fc;
	}
	public void destroy() {
		this.fc=null;
	}

	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)sRequest;
		String servletPath=request.getServletPath();
		String pathInfo=request.getPathInfo();
		
		String[] subPaths=new PathHandler().dividePath(servletPath, pathInfo);
		if(!validatePath(subPaths)){
			RequestDispatcher rd=request.getRequestDispatcher("/error.html");
			rd.forward(request,sResponse);
		}
		else{			
			request.setAttribute("subPaths",subPaths);			
			chain.doFilter(sRequest,sResponse);					//将执行下一个过滤器来判断用户是否登录
		}
	}
	
	private boolean validatePath(String[] subPaths){
		boolean mark=true;
		if(subPaths==null||subPaths.length==0)
			mark=false;
		else{
			String able=subPaths[2];
			if(!able.equals("a")&&!able.equals("b")&&!able.equals("c")&&!able.equals("d")&&!able.equals("e")&&!able.equals("f"))
				mark=false;
		}
		return mark;		
	}
}