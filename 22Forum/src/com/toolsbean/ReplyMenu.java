package com.toolsbean;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.valuebean.UserBean;

public class ReplyMenu extends TagSupport {
	private int topicId;
	private int replyId;
	public void setTopicId(int topicId){
		this.topicId=topicId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public int doStartTag() throws JspException {
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession();
		
		String boardstatus=(String)request.getAttribute("visitboardstatus");
		if("2".equals(boardstatus)){			//如果当前访问的版块为开放状态
			Object object=session.getAttribute("loginer");
			if((object!=null)&&(object instanceof UserBean))			//如果用户已经登录
				getshowmenu(true);
			else
				getshowmenu(false);
		}		
		return SKIP_BODY;
	}
	public void getshowmenu(boolean islogin) {
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession();
		JspWriter jspwriter=pageContext.getOut();
		String contextPath=request.getContextPath();
		
		String menubar="";
		String space="&nbsp;&nbsp;";
	
		String menu1="<a href='"+contextPath+"/visit/attachment/c/uploadview?replyId="+replyId+"' target='_parent'>添加附件</a>";
		String menu2="<a href='"+contextPath+"/visit/reply/c/modifyview?replyId="+replyId+"' target='_parent'>编辑回复</a>";
		String menu3="<a href='javascript:void(0)' onclick='parent.showreplyface();parent.location.hash=\"replytop\"'>简单模式回复</a>";
		String menu4="<a href='"+contextPath+"/visit/topic/a/replyview?topicId="+topicId+"' target='_parent'>在线编辑回复</a>";
		String menu5="<a href='"+contextPath+"/visit/topic/a/replyview?topicId="+topicId+"&replyId="+replyId+"&fromquote=reply' target='_parent'>引用回复</a>";
		String menu6="<a href='"+contextPath+"/visit/reply/c/deleteview?topicId="+topicId+"&replyId="+replyId+"' target='_parent'>删除回复</a>";
		String showmenu="";

		String topicstatus=(String)request.getAttribute("visittopicstatus");
		Object object=session.getAttribute("loginer");
		if((object!=null)&&(object instanceof UserBean)){
			UserBean loginer=(UserBean)object;
			boolean mark=false;
			int groupId=loginer.getGroupId();					//获取登录用户所属组的ID
			Integer visitboardId=(Integer)session.getAttribute("visitboard");
			Integer visitcategoryId=(Integer)session.getAttribute("visitcategory");
			
			if(groupId>=3)										//如果用户是论坛管理组或系统管理组的成员
				mark=true;
			else if(groupId==2){								//如果用户是类管理组的成员，判断当前访问的类是否被分配给了该用户
				int[] assignCategoryId=loginer.getAssignCategoryId();
				mark=isMyAble(visitcategoryId,assignCategoryId);
			}
			else if(groupId==1){								//如果用户是版块管理组的成员，判断当前访问的版块是否被分配给了该用户
				int[] assignBoardId=loginer.getAssignBoardId();
				mark=isMyAble(visitboardId,assignBoardId);
			}
			if(mark){											//如果具有管理权限
				if("2".equals(topicstatus))						//如果当前主题为开发状态，显示所有按钮
					showmenu=menu1+space+menu2+space+menu3+space+menu4+space+menu5+space+menu6;
				else if("1".equals(topicstatus))				//如果当前主题为锁定状态。对于管理员只显示删除回复按钮
					showmenu=menu6;
				else if("0".equals(topicstatus))				//如果当前主题为关闭状态。对于管理员只显示添加附件、编辑回复和删除回复按钮
					showmenu=menu1+space+menu2+space+menu6;
			}
			else{												//如果不具有管理权限
				if("2".equals(topicstatus))						//如果当前主题为开发状态，只显示回复按钮
					showmenu=menu3+space+menu4+space+menu5;
			}
		} 
		else													//没有登录												
			if("2".equals(topicstatus))							//如果当前主题为开发状态，只显示编辑回复、引用回复按钮
				showmenu=menu4+space+menu5;			
		
		menubar+="<table border='0' width='100%' cellpadding='0' cellspacing='0' rules='rows'>";
		menubar+="<tr><td>"+showmenu+"</td></tr>";
		menubar+="</table>";
		
		try {
			jspwriter.write(menubar);
		} catch (IOException e) {
			e.printStackTrace();
		}		
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
