<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>

<html>
	<title>更改密码</title>
	<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
	<script type="text/javascript" src="<%=contextPath%>/js/visituser.js"></script>
	<body onload="editmypswdform.adminpswd.focus()">
	<center>
	<form action="<%=contextPath%>/visit/myself/a/editmypswdrun" method="post" name="updatepswdform" onsubmit="return false;">
    <b><span id="checkmessage" style="color:red"></span></b>
	<table bgcolor="#999999" border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1">
 		<tr height="25" class="listhead"><td colspan="2">请输入旧密码和新密码</td></tr>
		<tr height="25" bgcolor="white">
			<td width="40%" align="right">旧密码：</td>
			<td><input type="password" name="adminpswd" size="25" class="login" onkeypress="checkmessage.innerHTML=''"></td>
		</tr>
		<tr height="25" bgcolor="#F6F6F6">
			<td align="right">新密码：</td>
			<td><input type="password" name="newpswd" size="25" class="login" onkeypress="checkmessage.innerHTML=''"></td>
		</tr>
		<tr height="25" bgcolor="white">
			<td align="right">确认新密码：</td>
			<td><input type="password" name="aginnewpswd" size="25" class="login" onkeypress="checkmessage.innerHTML=''"></td>
		</tr>
		<tr>			
			<td colspan="2" align="center">
				<input type="button" name="updateswdsubmitb" value="修改密码" onclick="myupdatepswdsubmit()">
				<input type="reset" value="重新设置">
				<input type="reset" value="放弃修改" onclick="window.history.go(-1)">
			</td>
		</tr>							
   	</table>
	</form>
	</center>
</body>