<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>

<title>ɾ�����ӣ�</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.topicsingle}"/>
<c:if test="${empty single}"><li>��Ҫɾ�������Ӳ����ڻ��Ѿ���ɾ����</li></c:if>
<c:if test="${!empty single}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr height="25" bgcolor="#F5F5F5"><td><font color="red"><b>��ʾ��</b>�㽫ͬʱ��ɾ�������С����С��ظ����͸��������ٴ�ȷ�ϣ�ɾ������ʱ��Ҫ�������룡</font></td></tr>
</table>
<!-- ʵ��ɾ�����ӽ��� -->
<form action="<%=contextPath%>/visit/topic/c/deleterun" method="post" name="changeform">
<input type="hidden" name="boardId" value="${single.boardId}">
<input type="hidden" name="topicId" value="${param.topicId}">
<input type="hidden" name="topicType" value="${single.topicType}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">ɾ������</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">���⣺</td>
		<td nowrap>${single.topicTitle}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap valign="top">���ݣ�</td>
		<td>${single.topicContent}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>���ͣ�</td>
		<td nowrap>
			<c:if test="${single.topicType eq '2'}">�ö�����</c:if>
			<c:if test="${single.topicType eq '1'}">��������</c:if>
			<c:if test="${single.topicType eq '0'}">��ͨ����</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>���ߣ�</td>
		<td nowrap>${single.topicAuthorName}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>����ʱ�䣺</td>
		<td nowrap>${single.topicPostTime}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>�ظ���</td>
		<td nowrap>${single.topicReplyNum} ��</td>
	</tr>
	<tr bgcolor="white">
		<td style="color:red">*���������Ա���룺</td>
		<td><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''">&nbsp;<b><span id="checkmessage" style="color:red"></span></b></td>
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