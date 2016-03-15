<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>删除附件</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body>
<center>

<c:set var="single" value="${requestScope.attachmentsingle}"/>
<c:if test="${empty single}"><li>您要删除的附件不存在或已经被删除！</li></c:if>
<c:if test="${!empty single}">
<!-- 实现删除附件界面 -->
<form action="<%=contextPath%>/visit/attachment/c/deleterun" method="post" name="changeform">
<input type="hidden" name="autoforward" value="<%=request.getHeader("referer")%>">
<input type="hidden" name="topicId" value="${single.topicId}">
<input type="hidden" name="replyId" value="${single.replyId}">
<input type="hidden" name="attachmentid" value="${single.id}">
<input type="hidden" name="savename" value="${single.attachmentSaveName}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">删除附件：${single.attachmentFileName}</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">附件名称：</td>
		<td nowrap>${single.attachmentFileName}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap valign="top">附件格式：</td>
		<td nowrap>${single.attachmentFileType}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>附件大小：</td>
		<td nowrap>${single.attachmentFileSize} 字节</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>上传时间：</td>
		<td nowrap>${single.attachmentUpTime}</td>
	</tr>
	<tr bgcolor="white">
		<td style="color:red">*请输入管理员密码：</td>
		<td><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''"><b><span id="checkmessage" style="color:red"></span></b></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="editsubmitb" value="删除附件" onclick="myvalidateadminsubmit()">
			<input type="button" value="放弃删除" onclick="javascript:window.history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>