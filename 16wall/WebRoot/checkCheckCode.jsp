<%@ page contentType="text/html; charset=GBK" language="java"  %>
<%
String inCheckCode=request.getParameter("inCheckCode");
	if(session.getAttribute("randCheckCode").equals(inCheckCode)){
		out.println("1");
	}else{
		out.println("0");
	}
%>