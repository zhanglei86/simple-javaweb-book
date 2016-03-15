<%@ page language="java" pageEncoding="gb2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>»¶Ó­</title>
  	</head>
	<body bgcolor="#DDEAF3">
		<center>
			<span style="background:url(<%=path%>/images/index.jpg);width:508;height:327;margin-top:100">
			<a href="index"><img src="<%=path%>/images/enter.jpg" style="border:0;margin-top:200;margin-left:-30"></a>
			</span>
		</center>
 	</body>
</html>
