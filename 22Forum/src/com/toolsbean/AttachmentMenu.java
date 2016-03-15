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
			int groupId=loginer.getGroupId();					//��ȡ��¼�û��������ID
			Integer visitboardId=(Integer)session.getAttribute("visitboard");
			Integer visitcategoryId=(Integer)session.getAttribute("visitcategory");
			
			if(groupId>=3)										//����û�����̳�������ϵͳ������ĳ�Ա
				mark=true;
			else if(groupId==2){								//����û����������ĳ�Ա���жϵ�ǰ���ʵ����Ƿ񱻷�����˸��û�
				int[] assignCategoryId=loginer.getAssignCategoryId();
				mark=isMyAble(visitcategoryId,assignCategoryId);
			}
			else if(groupId==1){								//����û��ǰ�������ĳ�Ա���жϵ�ǰ���ʵİ���Ƿ񱻷�����˸��û�
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
			showmenu="<a href='"+contextPath+"/visit/attachment/c/deleteview?attachmentid="+attachmentId+"' target='_parent'>[ɾ��]</a>";
		return showmenu;
	}
	private String add(boolean mark){
		String showmenu="";
		if(mark){
			showmenu="<input type='radio' name='attachment' value='0' checked>����Ӹ���";
			showmenu+="&nbsp;&nbsp;";
			showmenu+="<input type='radio' name='attachment' value='1'>��Ӹ���";			
		}
		else 
			showmenu="<li>��û��Ȩ����Ӹ�����</li>";
		return showmenu;
	}
}
