<%@ page contentType="text/html;charset=gb2312"%>
<%
	response.addHeader("Pragma","no-cache");
	response.addHeader("Cache-Control","no-cache");
	response.addDateHeader("Expires",0);
	String mainPage=(String)request.getAttribute("mainPage");
	if(mainPage==null||mainPage.equals(""))
		mainPage="default.jsp";
%>
<html>
	<head>	
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	</head>
	<body>
    	<center>
	        <table width="95%" border="0" cellpadding="0" cellspacing="0">
    	        <tr><td align="center"><%@ include file="top.jsp" %></td></tr>
        	    <tr><td align="center"><jsp:include page="menu.jsp"/></td></tr>
	       	   	<tr><td align="center" valign="top"><jsp:include page="tree.jsp"/></td></tr>
        	    <tr><td align="center"><jsp:include page="<%=mainPage%>"/></td></tr>
    	        <tr><td align="center"><%@ include file="end.jsp" %></td></tr>
        	</table>        
	    </center>
	</body>
</html>
