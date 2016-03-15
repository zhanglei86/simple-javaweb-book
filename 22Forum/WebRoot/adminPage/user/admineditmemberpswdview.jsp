<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>

<html>
	<title>更改会员密码</title>
	<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
	<script type="text/javascript" src="<%=contextPath%>/js/adminuser.js"></script>
	
	<body onload="adminform.newpswd.focus()">
	<center>
	<form action="<%=contextPath%>/admin/user/c/admineditmemberpswdrun" method="post" name="adminform" onsubmit="return false;">
	<input type="hidden" name="memberName" value="${param.memberName}">
	<table bgcolor="#999999" border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1">
 		<tr height="25" class="listhead"><td colspan="2">请输入新密码、确认密码和管理员密码</td></tr>
		<tr height="25" bgcolor="#F6F6F6">
			<td>新密码：</td>
			<td><input type="password" name="newpswd" size="30" class="login"></td>
		</tr>
		<tr height="25" bgcolor="white">
			<td>确认新密码：</td>
			<td><input type="password" name="aginpswd" size="30" class="login"></td>
		</tr>
		<tr bgcolor="#F5F5F5">
			<td style="color:red" nowrap>*请输入管理员密码：</td>
			<td nowrap><input type="password" name="adminpswd" size="30" class="login"></td>
		</tr>
		<tr bgcolor="white" align="center"><td colspan="2"><b><span id="checkmessage" style="color:red"></span></b></td></tr>
		<tr>			
			<td colspan="2" align="center">
				<input type="button" name="adminsubmitb" value="修改密码" onclick="return myupdateuserpswdsubmit();">
				<input type="reset" value="重新设置">
			</td>
		</tr>							
   	</table>
	</form>
	</center>
</body>