<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>

<title>ɾ�����棡</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.deletesingle}"/>

<c:if test="${!empty single}">
<!-- ʵ��ɾ��������� -->
<form action="<%=contextPath%>/visit/placard/c/deleteplacardrun" method="post" name="changeform">
<input type="hidden" name="boardId" value="${param.boardId}">
<input type="hidden" name="placardId" value="${single.id}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">ɾ������</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">���⣺</td>
		<td nowrap>${single.placardTitle}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td valign="top">���ݣ�</td>
		<td>${single.placardContent}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>���ͣ�</td>
		<td nowrap>
			<c:if test="${single.placardType eq '2'}">��̳����</c:if>
			<c:if test="${single.placardType eq '1'}">��𹫸�</c:if>
			<c:if test="${single.placardType eq '0'}">��鹫��</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>���ߣ�</td>
		<td nowrap>${single.placardAuthorName}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>����ʱ�䣺</td>
		<td nowrap>${single.placardPostTime}</td>
	</tr>
	<tr bgcolor="white">
		<td style="color:red">*���������Ա���룺</td>
		<td><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''">&nbsp;<b><span id="checkmessage" style="color:red"></span></b></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" value="ɾ������" name="editsubmitb" onclick="myvalidateadminsubmit()">
			<input type="button" value="����ɾ��" onclick="javascript:window.history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>