package com.toolsbean;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.valuebean.UserBean;

public class AdminTopMenu2 extends TagSupport {
	public int doStartTag() throws JspException {
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession();
		JspWriter jspwriter=pageContext.getOut();
		String contextPath=request.getContextPath();
		
		String menubar="";		
		String space="&nbsp;&nbsp;|&nbsp;&nbsp;";

		String menu1="<a href='"+contextPath+"/index'>��̳��ҳ</a>";
		String menu2="<a href='"+contextPath+"/adminindex'>��̨��ҳ</a>";
		String menu3="<a href='"+contextPath+"/admin/category/c/forummanager'>��̳����</a>";
		String menu4="<a href='"+contextPath+"/admin/user/c/membermanager'>��Ա����</a>";
		String menu5="<a href='"+contextPath+"/admin/group/e/groupmanager'>�û������</a>";

		UserBean loginer=(UserBean)session.getAttribute("loginer");
		int groupId=loginer.getGroupId();				//��ȡ��¼�û��������ID
		if(groupId>=3)							//����û�������̳����Ա���ϵͳ����Ա���Ա
			menubar=menu1+space+menu2+space+menu3+space+menu4+space+menu5;
		else if(groupId>=1)
			menubar=menu1+space+menu2+space+menu3+space+menu4;
	
		try {
			jspwriter.write(menubar);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	    return SKIP_BODY;
	}
}
