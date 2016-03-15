<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>

<title>删除帖子！</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.topicsingle}"/>
<c:if test="${empty single}"><li>您要删除的帖子不存在或已经被删除！</li></c:if>
<c:if test="${!empty single}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr height="25" bgcolor="#F5F5F5"><td><font color="red"><b>提示：</b>你将同时会删除该帖中【所有】回复帖和附件！请再次确认！删除话题时需要输入密码！</font></td></tr>
</table>
<!-- 实现删除帖子界面 -->
<form action="<%=contextPath%>/visit/topic/c/deleterun" method="post" name="changeform">
<input type="hidden" name="boardId" value="${single.boardId}">
<input type="hidden" name="topicId" value="${param.topicId}">
<input type="hidden" name="topicType" value="${single.topicType}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">删除帖子</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">标题：</td>
		<td nowrap>${single.topicTitle}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap valign="top">内容：</td>
		<td>${single.topicContent}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>类型：</td>
		<td nowrap>
			<c:if test="${single.topicType eq '2'}">置顶帖子</c:if>
			<c:if test="${single.topicType eq '1'}">精华帖子</c:if>
			<c:if test="${single.topicType eq '0'}">普通帖子</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>作者：</td>
		<td nowrap>${single.topicAuthorName}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>发表时间：</td>
		<td nowrap>${single.topicPostTime}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>回复：</td>
		<td nowrap>${single.topicReplyNum} 帖</td>
	</tr>
	<tr bgcolor="white">
		<td style="color:red">*请输入管理员密码：</td>
		<td><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''">&nbsp;<b><span id="checkmessage" style="color:red"></span></b></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="editsubmitb" value="删除帖子" onclick="myvalidateadminsubmit()">
			<input type="button" value="放弃删除" onclick="javascript:window.history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>