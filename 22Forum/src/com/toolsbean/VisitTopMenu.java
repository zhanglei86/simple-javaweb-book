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

		String menu1="<a href='"+contextPath+"/index'>��ҳ</a>";
		String menu2="<a href='"+contextPath+"/regview'>ע��</a>";
		String menu3="<a href='"+contextPath+"/visitlogin'>��¼</a>";
		String menu4="<a href='"+contextPath+"/visitlogout'>ע��</a>";
		String menu5="<a href='"+contextPath+"/visit/myself/a/index' target='_blank'>��������</a>";
		String menu6="<a href='"+contextPath+"/visit/topic/a/listuncheck?topicId=-1'>���������</a>";
		
		Object object=session.getAttribute("loginer");
		if((object!=null)&&(object instanceof UserBean)){
			showhello="��ӭ: (<font color='red'>"+((UserBean)object).getMemberName()+"</font>)���� - ";
			showhello+="�ϴε�¼: "+((UserBean)object).getMemberLogonTime();
			
			UserBean loginer=(UserBean)object;
			int groupId=loginer.getGroupId();					//��ȡ��¼�û��������ID
			if(groupId>=1)										//����û��ǰ������飨����߼��𣩵ĳ�Ա
				menubar=showhello+space+menu1+space+menu4+space+menu5+space+menu6;
			else
				menubar=showhello+space+menu1+space+menu4+space+menu5;
		} 
		else{
			showhello="��ӭ: (�ο�)����";
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

