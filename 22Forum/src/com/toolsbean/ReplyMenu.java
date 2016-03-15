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
		if("2".equals(boardstatus)){			//�����ǰ���ʵİ��Ϊ����״̬
			Object object=session.getAttribute("loginer");
			if((object!=null)&&(object instanceof UserBean))			//����û��Ѿ���¼
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
	
		String menu1="<a href='"+contextPath+"/visit/attachment/c/uploadview?replyId="+replyId+"' target='_parent'>��Ӹ���</a>";
		String menu2="<a href='"+contextPath+"/visit/reply/c/modifyview?replyId="+replyId+"' target='_parent'>�༭�ظ�</a>";
		String menu3="<a href='javascript:void(0)' onclick='parent.showreplyface();parent.location.hash=\"replytop\"'>��ģʽ�ظ�</a>";
		String menu4="<a href='"+contextPath+"/visit/topic/a/replyview?topicId="+topicId+"' target='_parent'>���߱༭�ظ�</a>";
		String menu5="<a href='"+contextPath+"/visit/topic/a/replyview?topicId="+topicId+"&replyId="+replyId+"&fromquote=reply' target='_parent'>���ûظ�</a>";
		String menu6="<a href='"+contextPath+"/visit/reply/c/deleteview?topicId="+topicId+"&replyId="+replyId+"' target='_parent'>ɾ���ظ�</a>";
		String showmenu="";

		String topicstatus=(String)request.getAttribute("visittopicstatus");
		Object object=session.getAttribute("loginer");
		if((object!=null)&&(object instanceof UserBean)){
			UserBean loginer=(UserBean)object;
			boolean mark=false;
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
			if(mark){											//������й���Ȩ��
				if("2".equals(topicstatus))						//�����ǰ����Ϊ����״̬����ʾ���а�ť
					showmenu=menu1+space+menu2+space+menu3+space+menu4+space+menu5+space+menu6;
				else if("1".equals(topicstatus))				//�����ǰ����Ϊ����״̬�����ڹ���Աֻ��ʾɾ���ظ���ť
					showmenu=menu6;
				else if("0".equals(topicstatus))				//�����ǰ����Ϊ�ر�״̬�����ڹ���Աֻ��ʾ��Ӹ������༭�ظ���ɾ���ظ���ť
					showmenu=menu1+space+menu2+space+menu6;
			}
			else{												//��������й���Ȩ��
				if("2".equals(topicstatus))						//�����ǰ����Ϊ����״̬��ֻ��ʾ�ظ���ť
					showmenu=menu3+space+menu4+space+menu5;
			}
		} 
		else													//û�е�¼												
			if("2".equals(topicstatus))							//�����ǰ����Ϊ����״̬��ֻ��ʾ�༭�ظ������ûظ���ť
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
