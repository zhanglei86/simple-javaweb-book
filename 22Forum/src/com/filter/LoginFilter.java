package com.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
	private FilterConfig fc;
	private Map notloginlist=null;

	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)sRequest;
		HttpSession session=request.getSession();	

		String[] subPaths=(String[])request.getAttribute("subPaths");		
		String way=subPaths[0];													//��ȡǰ����̨��ʶ
		if(way.equals("visit")){													//�������ǰ̨����
			String operate=subPaths[3];													//��ȡ����Ĳ���
			if(!notloginlist.containsValue(operate)){									//����������"��Ҫ��¼"�Ĳ������緢�����ӣ��ظ����ӵ�
				Object loginer=session.getAttribute("loginer");
				if(loginer==null&&!(loginer instanceof com.valuebean.UserBean)){		//���û�е�¼
					RequestDispatcher rd=request.getRequestDispatcher(fc.getInitParameter("visitlogin"));
					rd.forward(request,sResponse);
				}
				else
					chain.doFilter(sRequest,sResponse);	
			}
			else																		//����������"����Ҫ��¼"�Ĳ���������ִ��
				chain.doFilter(sRequest,sResponse);
		}
		else if(way.equals("admin")){													//��������̨����ÿ������Ҫ��֤�û��Ƿ��¼��������֤�Ƿ���Ȩ�޽����̨
			Object loginer=session.getAttribute("loginer");
			if(loginer==null&&!(loginer instanceof com.valuebean.UserBean)){		//���û�е�¼
				RequestDispatcher rd=request.getRequestDispatcher(fc.getInitParameter("adminlogin"));
				rd.forward(request,sResponse);
			}
			else
				chain.doFilter(sRequest,sResponse);	
		}
	}
	
	public void init(FilterConfig fc) throws ServletException {
		this.fc=fc;
		notloginlist=new HashMap();
		notloginlist.put("1", "listspecialtopic");
		notloginlist.put("2", "listcommontopic");
		notloginlist.put("3", "view");
		notloginlist.put("4", "newreplylist");
		notloginlist.put("5", "morereplylist");
		notloginlist.put("6", "viewmember");
		notloginlist.put("7", "newlist");
		notloginlist.put("8", "morelist");
		notloginlist.put("9", "alllist");
		notloginlist.put("10", "onelist");
		notloginlist.put("11", "listmemberposttopic");
	}	
	public void destroy() {
		this.fc=null;
		notloginlist=null;
	}
}