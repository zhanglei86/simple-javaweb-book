package com.toolsbean;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.valuebean.UserBean;

public class AdminTopMenu1 extends TagSupport {
	public int doStartTag() throws JspException {
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession();
		JspWriter jspwriter=pageContext.getOut();
		String contextPath=request.getContextPath();
		
		String menubar="";		
		String space="&nbsp;&nbsp;|&nbsp;&nbsp;";

		String showhello1="";
		String showhello2="";
		
		String menu1="<a href='"+contextPath+"/adminlogout'>注销</a>";
		String menu2="<a href='"+contextPath+"/visit/myself/a/index' target='_blank'>个人资料</a>";
		String menu3="<a href='"+contextPath+"/adminlogin'>登录</a>";

		Object object=session.getAttribute("loginer");
		if((object!=null)&&(object instanceof UserBean)){
			showhello1="<b>欢迎: (<font color='red'>"+((UserBean)object).getMemberName()+"</font>)访问</b>";
			showhello2="上次登录日期: "+((UserBean)object).getMemberLogonTime();
			menubar=showhello1+space+menu1+"<br>"+showhello2+space+menu2;
		}
		else{
			showhello1="<b>欢迎: (<font color='red'>游客</font>)访问</b>";
			menubar=showhello1+space+menu3;
		}

		try {
			jspwriter.write(menubar);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	    return SKIP_BODY;
	}
}
