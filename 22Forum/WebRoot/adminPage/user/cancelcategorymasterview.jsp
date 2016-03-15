<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>类主卸职</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminuser.js"></script>

<body>
<center>
<!-- 实现卸职界面 -->
<form action="<%=contextPath%>/admin/user/e/cancelcategorymasterrun" method="post" name="adminform">
<input type="hidden" name="memberName" value="${param.memberName}">
<input type="hidden" name="memberId" value="${requestScope.memberid}">
<input type="hidden" name="categoryId" value="${requestScope.categoryid}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">类主卸职</td></tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">会员名称：</td>
		<td nowrap>${param.memberName}</td>
	</tr>	
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">免去该类别的类主：</td>
		<td nowrap>${requestScope.categoryName}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td style="color:red" nowrap>*请输入管理员密码：</td>
		<td nowrap><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''">&nbsp;<b><span id="checkmessage" style="color:red"></span></b></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="卸职" onclick="mycancelmastersubmit()">
			<input type="reset" value="放弃" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</center>
</body>