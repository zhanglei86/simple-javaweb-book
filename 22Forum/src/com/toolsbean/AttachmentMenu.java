package com.toolsbean;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.valuebean.UserBean;

public class AttachmentMenu extends TagSupport {
	private int attachmentId;
	private String operate;
	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}	
	public void setOperate(String operate) {
		this.operate = operate;
	}

	public int doStartTag() throws JspException {		
		JspWriter jspwriter=pageContext.getOut();		
		String menubar="";
		
		boolean mark=validateAble();
		if(operate.equals("delete"))
			menubar=delete(mark);
		else if(operate.equals("add"))
			menubar=add(mark);
		
		try{
			jspwriter.write(menubar);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    return SKIP_BODY;
	}
	private boolean validateAble(){
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession();
		boolean mark=false;

		Object object=session.getAttribute("loginer");
		if((object!=null)&&(object instanceof UserBean)){
			UserBean loginer=(UserBean)object;
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
		}
		return mark;
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
	private String delete(boolean mark){
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		String contextPath=request.getContextPath();
		String showmenu="";
		if(mark)
			showmenu="<a href='"+contextPath+"/visit/attachment/c/deleteview?attachmentid="+attachmentId+"' target='_parent'>[删除]</a>";
		return showmenu;
	}
	private String add(boolean mark){
		String showmenu="";
		if(mark){
			showmenu="<input type='radio' name='attachment' value='0' checked>不添加附件";
			showmenu+="&nbsp;&nbsp;";
			showmenu+="<input type='radio' name='attachment' value='1'>添加附件";			
		}
		else 
			showmenu="<li>您没有权限添加附件！</li>";
		return showmenu;
	}
}
