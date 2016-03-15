package com.toolsbean;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.valuebean.UserBean;

public class TopicMenu extends TagSupport {
	private int topicId;
	private String authorName;
	public void setTopicId(int topicId){
		this.topicId=topicId;
	}
	public void setAuthorName(String authorName){
		this.authorName=authorName;
	}
	public int doStartTag() throws JspException {
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		String boardstatus=(String)request.getAttribute("visitboardstatus");
		if("2".equals(boardstatus))			//�����ǰ���ʵİ��Ϊ����״̬����ȡҪ��ʾ�İ�ť
			getshowmenu();
		return SKIP_BODY;
	}
	private void getshowmenu(){
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession();
		JspWriter jspwriter=pageContext.getOut();
		String contextPath=request.getContextPath();
		
		String menubar="";
		String space="&nbsp;&nbsp;";
		String menu1="<a href='"+contextPath+"/visit/attachment/c/uploadview?topicId="+topicId+"'>��Ӹ���</a>";
		String menu2="<a href='"+contextPath+"/visit/topic/c/modifyview?topicId="+topicId+"'>�༭����</a>";
		String menu3="<a href='#replytop' onclick='showreplyface()'>��ģʽ�ظ�</a>";
		String menu4="<a href='"+contextPath+"/visit/topic/a/replyview?topicId="+topicId+"'>���߱༭�ظ�</a>";
		String menu5="<a href='"+contextPath+"/visit/topic/a/replyview?topicId="+topicId+"&fromquote=topic'>���ûظ�</a>";
		String menu6="<a href='"+contextPath+"/visit/topic/c/deleteview?topicId="+topicId+"'>ɾ������</a>";
		String menu7="<a href='"+contextPath+"/visit/topic/c/changestatusview?topicId="+topicId+"'>�ı�״̬</a>";
		String menu8="<a href='"+contextPath+"/visit/topic/c/changetypeview?topicId="+topicId+"'>�ı�����</a>";
		String menu9="<a href='"+contextPath+"/visit/topic/b/movefirst?topicId="+topicId+"&authorName="+authorName+"'>��ǰ����</a>";
		String menu10="<a href='"+contextPath+"/visit/topic/c/moveview?topicId="+topicId+"'>��������</a>";
		String menu11="<a href='"+contextPath+"/visit/topic/a/collecttopic?topicId="+topicId+"' target='_blank'>�ղ�����</a>";
		String showmenu1="";
		String showmenu2="";

		String topicstatus=(String)request.getAttribute("visittopicstatus");
		
		boolean islogin=false;
		Object object=session.getAttribute("loginer");
		if((object!=null)&&(object instanceof UserBean))			//����û��Ѿ���¼
			islogin=true;
		else
			islogin=false;
		
		if(islogin){					//����û��Ѿ���¼�������û��������ʾ��Ӧ��ť
			UserBean loginer=(UserBean)object;
			int groupId=loginer.getGroupId();					//��ȡ��¼�û��������ID�����ݸ�ֵѡ��Ҫ��ʾ�İ�ť
			Integer visitboardId=(Integer)session.getAttribute("visitboard");			//��ȡ��ǰ���ʵİ��IDֵ
			Integer visitcategoryId=(Integer)session.getAttribute("visitcategory");		//��ȡ��ǰ���ʵ����IDֵ
			boolean mark=false;									//true��ʾ���й���ǰ���ӵ�Ȩ�ޣ�false��ʾ��ͨ��Ա�Ͳ����й���ǰ���ӵ�Ȩ��
			
			if(groupId>=3)													//����û�����̳�������ϵͳ������ĳ�Ա
				mark=true;
			else if(groupId==2){											//����û����������ĳ�Ա���жϵ�ǰ���ʵ����Ƿ񱻷�����˸��û�
				int[] assignCategoryId=loginer.getAssignCategoryId();					//��ȡ����������������(IDֵ)
				mark=isMyAble(visitcategoryId,assignCategoryId);						//�жϵ�ǰ���ʵ�����Ƿ������˸�����
			}
			else if(groupId==1){											//����û��ǰ�������ĳ�Ա���жϵ�ǰ���ʵİ���Ƿ񱻷�����˸��û�
				int[] assignBoardId=loginer.getAssignBoardId();
				mark=isMyAble(visitboardId,assignBoardId);
			}
			
			if(mark){							//������й���ǰ���ӵ�Ȩ��
				if("2".equals(topicstatus)){					//�����ǰ����Ϊ����״̬����ʾ���а�ť
					showmenu1=menu1+space+menu2+space+menu3+space+menu4+space+menu5+space+menu6;
					showmenu2=menu7+space+menu8+space+menu9+space+menu10+space+menu11;
				}
				else if("1".equals(topicstatus)){				//�����ǰ����Ϊ����״̬����������״̬�����ӣ����ڹ���Ա����ʾ�ظ�����Ӹ������༭��ť
					showmenu1=menu6;
					showmenu2=menu7+space+menu8+space+menu9+space+menu10+space+menu11;
				}
				else if("0".equals(topicstatus)){				//�����ǰ����Ϊ�ر�״̬�� ���ڹر�״̬��������ͨ��Ա������������ڹ���Աֻ��ʾ��Ӹ������༭���ӡ�ɾ�����ӡ��ı�����״̬���ղ����Ӱ�ť
					showmenu1=menu1+space+menu2+space+menu6;
					showmenu2=menu7+space+menu10+space+menu11;
				}
			}
			else{								//�����й���ǰ���ӵ�Ȩ��
				if("2".equals(topicstatus)){					//�����ǰ����Ϊ����״̬
					showmenu1=menu3+space+menu4+space+menu5;
					showmenu2=menu9+space+menu11;
				}				
			}
		} 
		else{							//û�е�¼����Ĭ����ʾ"���߱༭�ظ�"��"���ûظ�"������ť
			if("2".equals(topicstatus))					//�����ǰ����Ϊ����״̬
				showmenu1=menu4+space+menu5;
		}
		
		menubar+="<table border='0' width='100%' cellpadding='0' cellspacing='0' rules='rows'>";
		menubar+="<tr>";
		menubar+="<td width='50%'>"+showmenu1+"</td><td align='right'>"+showmenu2+"</td>";
		menubar+="</tr>";
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
