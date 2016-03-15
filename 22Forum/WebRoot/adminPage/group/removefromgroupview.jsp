<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>移除成员</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/admingroup.js"></script>

<body>
<center>

<!-- 实现移除会员界面 -->
<form action="<%=contextPath%>/admin/group/e/removefromgrouprun" method="post" name="adminform">
<input type="hidden" name="groupId" value="${param.groupId}">
<input type="hidden" name="removememberId" value="${requestScope.removememberId}">
<input type="hidden" name="removememberName" value="${param.removememberName}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">删除会员</td></tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">会员名称：</td>
		<td nowrap>${param.removememberName}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">所属用户组：</td>
		<td nowrap>
			<c:if test="${param.groupId eq '4'}">系统管理组</c:if>
			<c:if test="${param.groupId eq '3'}">论坛管理组</c:if>
			<c:if test="${param.groupId eq '2'}">类别管理组</c:if>
			<c:if test="${param.groupId eq '1'}">版块管理组</c:if>
			<c:if test="${param.groupId eq '0'}">普通会员组</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td style="color:red" nowrap>*请输入管理员密码：</td>
		<td nowrap><input type="password" name="adminpswd" size="30" class="login"> <b><span id="checkmessage" style="color:red"></span></b></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="移除会员" onclick="myremovememberfromgroupsubmit()">
			<input type="reset" value="放弃移除" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>

</center>
</body>