<%@ page contentType="text/html;charset=gb2312"%>
<%
	Object loginer=session.getAttribute("loginer");
	if(loginer==null&&!(loginer instanceof com.valuebean.UserBean)){		//如果没有登录
		RequestDispatcher rd=request.getRequestDispatcher("adminlogin.jsp");
		rd.forward(request,response);
	}
	else{
		request.setAttribute("mainPage","default.jsp");
		RequestDispatcher rd=request.getRequestDispatcher("adminTemp.jsp");
		rd.forward(request,response);
	}
%>
