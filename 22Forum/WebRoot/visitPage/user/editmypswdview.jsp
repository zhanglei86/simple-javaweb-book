<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>

<html>
	<title>��������</title>
	<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
	<script type="text/javascript" src="<%=contextPath%>/js/visituser.js"></script>
	<body onload="editmypswdform.adminpswd.focus()">
	<center>
	<form action="<%=contextPath%>/visit/myself/a/editmypswdrun" method="post" name="updatepswdform" onsubmit="return false;">
    <b><span id="checkmessage" style="color:red"></span></b>
	<table bgcolor="#999999" border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1">
 		<tr height="25" class="listhead"><td colspan="2">������������������</td></tr>
		<tr height="25" bgcolor="white">
			<td width="40%" align="right">�����룺</td>
			<td><input type="password" name="adminpswd" size="25" class="login" onkeypress="checkmessage.innerHTML=''"></td>
		</tr>
		<tr height="25" bgcolor="#F6F6F6">
			<td align="right">�����룺</td>
			<td><input type="password" name="newpswd" size="25" class="login" onkeypress="checkmessage.innerHTML=''"></td>
		</tr>
		<tr height="25" bgcolor="white">
			<td align="right">ȷ�������룺</td>
			<td><input type="password" name="aginnewpswd" size="25" class="login" onkeypress="checkmessage.innerHTML=''"></td>
		</tr>
		<tr>			
			<td colspan="2" align="center">
				<input type="button" name="updateswdsubmitb" value="�޸�����" onclick="myupdatepswdsubmit()">
				<input type="reset" value="��������">
				<input type="reset" value="�����޸�" onclick="window.history.go(-1)">
			</td>
		</tr>							
   	</table>
	</form>
	</center>
</body>