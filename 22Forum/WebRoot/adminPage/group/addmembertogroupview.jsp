<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>添加新成员</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/admingroup.js"></script>

<body>
<center>

<c:set var="single" value="${requestScope.groupsingle}"/>
<c:if test="${!empty single}">
<!-- 实现添加新成员界面 -->
<form action="<%=contextPath%>/admin/group/e/addmembertogrouprun" method="post" name="adminform">
<input type="hidden" name="groupId" value="${single.groupId}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">添加新会员</td></tr>
	<tr bgcolor="white"><td colspan="2" align="center"><b><span id="checkmessage" style="color:red"></span></b></td></tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">当前用户组：</td>
		<td nowrap>${single.groupName}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%" valign="top">新成员：</td>
		<td nowrap><textarea name="inputmembernames" rows="8" cols="30"></textarea> 添加多个成员请用"回车"分隔！</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td style="color:red" nowrap>*请输入管理员密码：</td>
		<td nowrap><input type="password" name="adminpswd" size="40" class="login"> <span id="message"></span></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="添加成员" onclick="myaddnewmembertogroupsubmit()">
			<input type="reset" value="重新填写">
			<input type="reset" value="放弃添加" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>