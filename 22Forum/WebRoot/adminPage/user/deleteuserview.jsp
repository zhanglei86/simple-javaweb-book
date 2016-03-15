<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>删除会员</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminuser.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.usersingle}"/>

<c:if test="${!empty single}">
<!-- 实现删除会员界面 -->
<form action="<%=contextPath%>/admin/user/e/deleteuserrun" method="post" name="adminform">
<input type="hidden" name="autoforward" value="<%=request.getHeader("referer") %>">
<input type="hidden" name="deletememberId" value="${param.deletememberId}">
<input type="hidden" name="currentP" value="${param.currentP}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">删除会员</td></tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">会员名称：</td>
		<td nowrap>${single.memberName}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">会员身份：</td>
		<td nowrap>
			<c:if test="${single.groupId eq '4'}">系统管理组成员</c:if>
			<c:if test="${single.groupId eq '3'}">论坛管理组成员</c:if>
			<c:if test="${single.groupId eq '2'}">类别管理组成员</c:if>
			<c:if test="${single.groupId eq '1'}">版块管理组成员</c:if>
			<c:if test="${single.groupId eq '0'}">普通会员组成员</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">注册日期：</td>
		<td nowrap>${single.memberRegTime}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td style="color:red" nowrap>*请输入管理员密码：</td>
		<td nowrap><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''">&nbsp;<b><span id="checkmessage" style="color:red"></span></b></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="删除会员" onclick="mydeleteusersubmit()">
			<input type="reset" value="放弃删除" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>