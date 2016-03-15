<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>修改帖子状态</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.topicsingle}"/>
<c:if test="${empty single}"><li>您要编辑的帖子不存在或已经被删除！</li></c:if>
<c:if test="${!empty single}">
<!-- 实现修改帖子类型界面 -->
<form action="<%=contextPath%>/visit/topic/c/changestatusrun" method="post" name="changeform">
<input type="hidden" name="topicId" value="${param.topicId}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">编辑帖子：${single.topicTitle}</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">标题：</td>
		<td nowrap>${single.topicTitle}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap>内容：</td>
		<td>${single.topicContent}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>作者：</td>
		<td nowrap>${single.topicAuthorName}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>发表时间：</td>
		<td nowrap>${single.topicPostTime}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>目前帖子状态：</td>
		<td nowrap>
		<c:if test="${single.topicStatus eq '2'}">开放帖子</c:if>
		<c:if test="${single.topicStatus eq '1'}">锁定帖子</c:if>
		<c:if test="${single.topicStatus eq '0'}">关闭帖子</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>更改状态：</td>
		<td nowrap>
			<select name="status">
				<option value="2">开放帖子</option>
				<option value="1">锁定帖子</option>
				<option value="0">关闭帖子</option>
			</select>
		</td>
	</tr>
	<tr bgcolor="white">
		<td style="color:red">*请输入管理员密码：</td>
		<td><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''">&nbsp;<b><span id="checkmessage" style="color:red"></span></b></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="editsubmitb" value="修改状态" onclick="myvalidateadminsubmit()">
			<input type="reset" value="重新设置">
			<input type="reset" value="放弃修改" onclick="javascript:history.back(1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>