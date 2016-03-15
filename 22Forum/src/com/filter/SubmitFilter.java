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

public class SubmitFilter implements Filter {
	private FilterConfig fc;
	private Map needposturl=null;

	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)sRequest;
		String[] subPaths=(String[])request.getAttribute("subPaths");			
		String operate=subPaths[3];	
		String method=request.getMethod();
		
		if(needposturl.containsValue(operate)){
			if(method.equalsIgnoreCase("POST"))
				chain.doFilter(sRequest,sResponse);	
			else{
				String message="<li>不是以POST方式进行的请求！</li>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
				request.setAttribute("message",message);				
				RequestDispatcher rd=request.getRequestDispatcher("/fail.jsp");
				rd.forward(request,sResponse);
			}				
		}	
		else
			chain.doFilter(sRequest,sResponse);
	}
	
	public void init(FilterConfig fc) throws ServletException {
		this.fc=fc;
		needposturl=new HashMap();
		needposturl.put("1","postrun");
		needposturl.put("2","replyrun");
		needposturl.put("3","modifyrun");
		needposturl.put("4","changetyperun");
		needposturl.put("5","changestatusrun");
		needposturl.put("6","moverun");
		needposturl.put("7","deleterun");
		needposturl.put("8","modifyrun");
		needposturl.put("9","deleterun");
		needposturl.put("10","editmyinforun");
		needposturl.put("11","editmyiconrun");
		needposturl.put("12","editmysignrun");
		needposturl.put("13","editmypswdrun");
		needposturl.put("14","sendmessagerun");
		needposturl.put("15","deleteselectmessages");
		needposturl.put("16","deleterun");
		needposturl.put("17","postplacardrun");
		needposturl.put("18","deleteplacardrun");
		needposturl.put("19","regrun");
		needposturl.put("20","checkmember");
		needposturl.put("21","addboardrun");
		needposturl.put("22","addcategorymasterrun");
		needposturl.put("23","modifycategoryrun");
		needposturl.put("24","deletecategoryrun");
		needposturl.put("25","addboardmasterrun");
		needposturl.put("26","modifyboardrun");
		needposturl.put("27","deleteboardrun");
		needposturl.put("28","addcategoryrun");
		needposturl.put("29","addnewuserrun");
		needposturl.put("30","checkmember");
		needposturl.put("31","searchuserrun");
		needposturl.put("32","deleteuserrun");
		needposturl.put("33","admineditmemberinforun");
		needposturl.put("34","admineditmemberpswdrun");
		needposturl.put("35","assignboardrun");
		needposturl.put("36","assigncategoryrun");
		needposturl.put("37","cancelboardmasterrun");
		needposturl.put("38","modifygrouprun");
		needposturl.put("39","addmembertogrouprun");
	}	
	public void destroy() {
		this.fc=null;
		needposturl=null;
	}
}