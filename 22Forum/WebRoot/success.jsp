<%@ page contentType="text/html;charset=gb2312"%>
<title>ำัว้ฬแสพฃก</title>
<meta http-equiv="refresh" content="3;url=${requestScope.autoforward}">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">

<body bgcolor="#FBFCFE">
	<center>
		<table border="0" background="<%=request.getContextPath()%>/images/success.gif" width="392" height="260" style="word-break:break-all;" cellspacing="0" cellpadding="2">
			<tr>
				<td width="30%"></td>
				<td align="center">${requestScope.message}</td>
			</tr>
		</table>
	</center>
</body>