<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>ɾ������</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body>
<center>

<c:set var="single" value="${requestScope.attachmentsingle}"/>
<c:if test="${empty single}"><li>��Ҫɾ���ĸ��������ڻ��Ѿ���ɾ����</li></c:if>
<c:if test="${!empty single}">
<!-- ʵ��ɾ���������� -->
<form action="<%=contextPath%>/visit/attachment/c/deleterun" method="post" name="changeform">
<input type="hidden" name="autoforward" value="<%=request.getHeader("referer")%>">
<input type="hidden" name="topicId" value="${single.topicId}">
<input type="hidden" name="replyId" value="${single.replyId}">
<input type="hidden" name="attachmentid" value="${single.id}">
<input type="hidden" name="savename" value="${single.attachmentSaveName}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">ɾ��������${single.attachmentFileName}</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">�������ƣ�</td>
		<td nowrap>${single.attachmentFileName}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap valign="top">������ʽ��</td>
		<td nowrap>${single.attachmentFileType}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>������С��</td>
		<td nowrap>${single.attachmentFileSize} �ֽ�</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>�ϴ�ʱ�䣺</td>
		<td nowrap>${single.attachmentUpTime}</td>
	</tr>
	<tr bgcolor="white">
		<td style="color:red">*���������Ա���룺</td>
		<td><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''"><b><span id="checkmessage" style="color:red"></span></b></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="editsubmitb" value="ɾ������" onclick="myvalidateadminsubmit()">
			<input type="button" value="����ɾ��" onclick="javascript:window.history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>