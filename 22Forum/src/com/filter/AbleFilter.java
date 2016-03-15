package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.toolsbean.StringHandler;
import com.valuebean.UserBean;

public class AbleFilter implements Filter {
	private FilterConfig fc;
	
	public void destroy() {
		this.fc=null;
	}

	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)sRequest;
		boolean mark=validateAble(request);
		if(mark)
			chain.doFilter(sRequest,sResponse);
		else{
			String message="<li>您没有权限进行该操作！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>";
			request.setAttribute("message",message);
			RequestDispatcher rd=request.getRequestDispatcher("/fail.jsp");
			rd.forward(request,sResponse);
		}
	}
	private boolean validateAble(HttpServletRequest request){
		HttpSession session=request.getSession();
		UserBean loginer=(UserBean)session.getAttribute("loginer");		
		boolean mark=false;
		String subPaths[]=(String[])request.getAttribute("subPaths");		
		String way=subPaths[0];
		String able=subPaths[2];						//获取请求路径中的权限标识
		String operate=subPaths[3];
		
		if("visit".equals(way)){						//如果请求前台操作
			if(able.equals("a"))						//请求所有身份的用户都可进行的操作
				mark=true;
			else{
				int groupId=loginer.getGroupId();				//获取登录用户所属组的ID
				Integer visitboardId=(Integer)session.getAttribute("visitboard");
				Integer visitcategoryId=(Integer)session.getAttribute("visitcategory");
				int[] assignBoardId=loginer.getAssignBoardId();
				int[] assignCategoryId=loginer.getAssignCategoryId();
				
				if(groupId==4||groupId==3)
					mark=true;
				else if(groupId==2)									//如果用户是类管理组的成员，判断当前访问的类是否被分配给了用户
					mark=isMyAble(visitcategoryId,assignCategoryId);
				else if(groupId==1)									//如果用户是版块管理组的成员，判断当前访问的版块是否被分配给了用户
					mark=isMyAble(visitboardId,assignBoardId);
				else if(groupId==0){
					if(able.equals("b")){
						String authorName=request.getParameter("authorName");
						String loginerName=loginer.getMemberName();
						if(loginerName.equals(authorName))
							mark=true;
					}
				}			
			}
		}
		else if("admin".equals(way)){						//如果请求后台操作
			int groupId=loginer.getGroupId();				//获取登录用户所属组的ID
			String module=subPaths[1];						//获取操作的模块	
			String opname=subPaths[3];						//获取请求的操作名称
			if(opname.equals("forummanager")||opname.equals("membermanager"))
				mark=true;
			else{
				if("category".equals(module)||"board".equals(module)){
					Integer visitcategoryId=StringHandler.strToint(request.getParameter("categoryId"));
					Integer visitboardId=StringHandler.strToint(request.getParameter("boardId"));
					int[] assignBoardId=loginer.getAssignBoardId();
					int[] assignCategoryId=loginer.getAssignCategoryId();
					
					if(groupId==4)
						mark=true;
					else if(groupId==3)									//如果用户是论坛管理组或系统管理组的成员
						mark=true;
					else if(groupId==2)									//如果用户是类管理组的成员，判断当前访问的类是否被分配给了用户
						mark=isMyAble(visitcategoryId,assignCategoryId);
					else if(groupId==1)									//如果用户是版块管理组的成员，判断当前访问的版块是否被分配给了用户
						mark=isMyAble(visitboardId,assignBoardId);
				}
				else if("user".equals(module)){
					if("changeuserstatusrun".equals(operate)||"adminviewmember".equals(operate)||"searchuserview".equals(operate)||"searchuserrun".equals(operate)){
						if(groupId>=1)
							mark=true;
					}
					else{
						if(groupId>=3)
							mark=true;
					}
				}
				else if("group".equals(module)&&groupId>=3)
					mark=true;				
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

	public void init(FilterConfig fc) throws ServletException {
		this.fc=fc;
	}
}