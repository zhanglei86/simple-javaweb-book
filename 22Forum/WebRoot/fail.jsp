<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
	<head>
    	<title>”—«ÈÃ· æ£°</title>
    	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
  	</head>  
  	<body bgcolor="#FBFCFE">
  		<center>
  			<table border="0" background="<%=request.getContextPath()%>/images/fail.gif" width="392" height="260" style="word-break:break-all">
  				<tr>
  					<td width="30%"></td>
  					<td align="center">${requestScope.message}</td>
  				</tr>
  			</table>
  		</center>
  	</body>
</html>
