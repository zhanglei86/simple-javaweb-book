<%@ page contentType="text/html;charset=gb2312"%>
<% String contextPath=request.getContextPath(); %>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body onload="changesubsizeshow('addattachmentview')">
<center>
<table border="0" width="100%" cellspacing="1" cellpadding="5" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td>�ļ��ϴ�</td></tr>
	<tr bgcolor="#F5F5F5"><td>${requestScope.message}</td></tr>
</table>
</center>
</body>