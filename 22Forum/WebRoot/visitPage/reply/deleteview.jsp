<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>

<title>ɾ���ظ�����</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.replysingle}"/>
<c:if test="${empty single}"><li>��Ҫɾ���Ļظ��������ڻ��Ѿ���ɾ����</li></c:if>
<c:if test="${!empty single}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr height="25" bgcolor="#F5F5F5"><td><font color="red"><b>��ʾ��</b>�㽫ͬʱ��ɾ�������С����С����������ٴ�ȷ�ϣ�ɾ��ʱ��Ҫ�������룡</font></td></tr>
</table>
<!-- ʵ��ɾ�����ӽ��� -->
<form action="<%=contextPath%>/visit/reply/c/deleterun" method="post" name="changeform">
<input type="hidden" name="topicId" value="${single.topicId}">
<input type="hidden" name="replyId" value="${single.id}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">ɾ���ظ���</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="20%">���⣺</td>
		<td nowrap>${single.replyTitle}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap valign="top">���ݣ�</td>
		<td>${single.replyContentForJsp}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>���ߣ�</td>
		<td nowrap>${single.replyAuthorName}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>�ظ�ʱ�䣺</td>
		<td nowrap>${single.replyReplyTime}</td>
	</tr>
	<tr bgcolor="white">
		<td style="color:red">*���������Ա���룺</td>
		<td><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''">&nbsp;<b><span id="checkmessage" style="color:red"></span></b></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="editsubmitb" value="ɾ���ظ�" onclick="myvalidateadminsubmit()">
			<input type="button" value="����ɾ��" onclick="javascript:window.history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>