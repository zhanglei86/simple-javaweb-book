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
		if("2".equals(boardstatus))			//如果当前访问的版块为开放状态，获取要显示的按钮
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
		String menu1="<a href='"+contextPath+"/visit/attachment/c/uploadview?topicId="+topicId+"'>添加附件</a>";
		String menu2="<a href='"+contextPath+"/visit/topic/c/modifyview?topicId="+topicId+"'>编辑帖子</a>";
		String menu3="<a href='#replytop' onclick='showreplyface()'>简单模式回复</a>";
		String menu4="<a href='"+contextPath+"/visit/topic/a/replyview?topicId="+topicId+"'>在线编辑回复</a>";
		String menu5="<a href='"+contextPath+"/visit/topic/a/replyview?topicId="+topicId+"&fromquote=topic'>引用回复</a>";
		String menu6="<a href='"+contextPath+"/visit/topic/c/deleteview?topicId="+topicId+"'>删除帖子</a>";
		String menu7="<a href='"+contextPath+"/visit/topic/c/changestatusview?topicId="+topicId+"'>改变状态</a>";
		String menu8="<a href='"+contextPath+"/visit/topic/c/changetypeview?topicId="+topicId+"'>改变类型</a>";
		String menu9="<a href='"+contextPath+"/visit/topic/b/movefirst?topicId="+topicId+"&authorName="+authorName+"'>提前帖子</a>";
		String menu10="<a href='"+contextPath+"/visit/topic/c/moveview?topicId="+topicId+"'>搬移帖子</a>";
		String menu11="<a href='"+contextPath+"/visit/topic/a/collecttopic?topicId="+topicId+"' target='_blank'>收藏帖子</a>";
		String showmenu1="";
		String showmenu2="";

		String topicstatus=(String)request.getAttribute("visittopicstatus");
		
		boolean islogin=false;
		Object object=session.getAttribute("loginer");
		if((object!=null)&&(object instanceof UserBean))			//如果用户已经登录
			islogin=true;
		else
			islogin=false;
		
		if(islogin){					//如果用户已经登录，根据用户身份来显示相应按钮
			UserBean loginer=(UserBean)object;
			int groupId=loginer.getGroupId();					//获取登录用户所属组的ID，根据该值选择要显示的按钮
			Integer visitboardId=(Integer)session.getAttribute("visitboard");			//获取当前访问的版块ID值
			Integer visitcategoryId=(Integer)session.getAttribute("visitcategory");		//获取当前访问的类别ID值
			boolean mark=false;									//true表示具有管理当前帖子的权限；false表示普通会员和不具有管理当前帖子的权限
			
			if(groupId>=3)													//如果用户是论坛管理组或系统管理组的成员
				mark=true;
			else if(groupId==2){											//如果用户是类管理组的成员，判断当前访问的类是否被分配给了该用户
				int[] assignCategoryId=loginer.getAssignCategoryId();					//获取分配给该类主的类别(ID值)
				mark=isMyAble(visitcategoryId,assignCategoryId);						//判断当前访问的类别是否分配给了该类主
			}
			else if(groupId==1){											//如果用户是版块管理组的成员，判断当前访问的版块是否被分配给了该用户
				int[] assignBoardId=loginer.getAssignBoardId();
				mark=isMyAble(visitboardId,assignBoardId);
			}
			
			if(mark){							//如果具有管理当前帖子的权限
				if("2".equals(topicstatus)){					//如果当前主题为开发状态，显示所有按钮
					showmenu1=menu1+space+menu2+space+menu3+space+menu4+space+menu5+space+menu6;
					showmenu2=menu7+space+menu8+space+menu9+space+menu10+space+menu11;
				}
				else if("1".equals(topicstatus)){				//如果当前主题为锁定状态。处于锁定状态的帖子，对于管理员不显示回复、添加附件、编辑按钮
					showmenu1=menu6;
					showmenu2=menu7+space+menu8+space+menu9+space+menu10+space+menu11;
				}
				else if("0".equals(topicstatus)){				//如果当前主题为关闭状态。 处于关闭状态的帖子普通会员浏览不到，对于管理员只显示添加附件、编辑帖子、删除帖子、改变帖子状态、收藏帖子按钮
					showmenu1=menu1+space+menu2+space+menu6;
					showmenu2=menu7+space+menu10+space+menu11;
				}
			}
			else{								//不具有管理当前帖子的权限
				if("2".equals(topicstatus)){					//如果当前主题为开发状态
					showmenu1=menu3+space+menu4+space+menu5;
					showmenu2=menu9+space+menu11;
				}				
			}
		} 
		else{							//没有登录，则默认显示"在线编辑回复"和"引用回复"两个按钮
			if("2".equals(topicstatus))					//如果当前主题为开放状态
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
