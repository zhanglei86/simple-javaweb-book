package com.toolsbean;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.valuebean.UserBean;

public class VisitTopMenu extends TagSupport {
	public int doStartTag() throws JspException {
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession();
		JspWriter jspwriter=pageContext.getOut();
		String contextPath=request.getContextPath();
		
		String showhello="";		
		String space="&nbsp;&nbsp;|&nbsp;&nbsp;";
		String menubar="";		

		String menu1="<a href='"+contextPath+"/index'>首页</a>";
		String menu2="<a href='"+contextPath+"/regview'>注册</a>";
		String menu3="<a href='"+contextPath+"/visitlogin'>登录</a>";
		String menu4="<a href='"+contextPath+"/visitlogout'>注销</a>";
		String menu5="<a href='"+contextPath+"/visit/myself/a/index' target='_blank'>个人资料</a>";
		String menu6="<a href='"+contextPath+"/visit/topic/a/listuncheck?topicId=-1'>待审核帖子</a>";
		
		Object object=session.getAttribute("loginer");
		if((object!=null)&&(object instanceof UserBean)){
			showhello="欢迎: (<font color='red'>"+((UserBean)object).getMemberName()+"</font>)访问 - ";
			showhello+="上次登录: "+((UserBean)object).getMemberLogonTime();
			
			UserBean loginer=(UserBean)object;
			int groupId=loginer.getGroupId();					//获取登录用户所属组的ID
			if(groupId>=1)										//如果用户是版块管理组（或更高级别）的成员
				menubar=showhello+space+menu1+space+menu4+space+menu5+space+menu6;
			else
				menubar=showhello+space+menu1+space+menu4+space+menu5;
		} 
		else{
			showhello="欢迎: (游客)访问";
			menubar=showhello+space+menu1+space+menu2+space+menu3;
		}
		
		try {
			jspwriter.write(menubar);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	    return SKIP_BODY;
	}
	private boolean isMyAble(Integer current,int[]assign){
		boolean mark=false;
		if(current!=null&&assign!=null&&assign.length!=0){
			for(int i=0;i<assign.length;i++){
				if(current.intValue()==assign[i]){
					mark=true;
					break;
				}
			}			
		}		
		return mark;
	}
}

