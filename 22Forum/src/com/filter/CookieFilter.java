package com.filter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dao.UserDao;

public class CookieFilter implements Filter {
	private FilterConfig fc;

	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)sRequest;
		HttpSession session=request.getSession();
		
		Object loginer=session.getAttribute("loginer");
		if(loginer==null&&!(loginer instanceof com.valuebean.UserBean)){		//如果没有登录
			Map userMap=seecookie(request);										//查找客户端Cookie中是否记录了用户名和密码
			if(userMap!=null){													//如果Cookie中存在登录论坛的用户名和密码
				String name=(String)userMap.get("name");
				String pswd=(String)userMap.get("pswd");
				try {
					UserDao userDao=new UserDao();
					loginer=userDao.login(name, pswd);									//查询数据库获取用户信息
					session.setAttribute("loginer",loginer);						//保存用户到session中，用来表明用户已经登录
				} catch (SQLException e) {
					System.out.println("使用Cookie自动登录失败！");
					e.printStackTrace();
				}			
			}
		}
		chain.doFilter(sRequest,sResponse);
	}	
	private Map seecookie(HttpServletRequest request)throws ServletException, IOException {
		String webname=request.getContextPath();
		webname=webname.substring(1);
		Map userMap=new HashMap();
		Cookie[] coks=request.getCookies();
		int i=0;
		for(i=0;i<coks.length;i++){
			Cookie icok=coks[i];
			if(icok.getName().equals(webname+".loginname"))
				userMap.put("name",icok.getValue());
			else if(icok.getName().equals(webname+".loginpswd"))
				userMap.put("pswd",icok.getValue());
			if(userMap.size()==2)
				break;			
		}
		if(i==coks.length)
			return null;
		else
			return userMap;	
	}	
	public void init(FilterConfig fc) throws ServletException {
		this.fc=fc;
	}	
	public void destroy() {
		this.fc=null;
	}
}