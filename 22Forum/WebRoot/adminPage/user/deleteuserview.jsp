<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>ɾ����Ա</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminuser.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.usersingle}"/>

<c:if test="${!empty single}">
<!-- ʵ��ɾ����Ա���� -->
<form action="<%=contextPath%>/admin/user/e/deleteuserrun" method="post" name="adminform">
<input type="hidden" name="autoforward" value="<%=request.getHeader("referer") %>">
<input type="hidden" name="deletememberId" value="${param.deletememberId}">
<input type="hidden" name="currentP" value="${param.currentP}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">ɾ����Ա</td></tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">��Ա���ƣ�</td>
		<td nowrap>${single.memberName}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">��Ա��ݣ�</td>
		<td nowrap>
			<c:if test="${single.groupId eq '4'}">ϵͳ�������Ա</c:if>
			<c:if test="${single.groupId eq '3'}">��̳�������Ա</c:if>
			<c:if test="${single.groupId eq '2'}">���������Ա</c:if>
			<c:if test="${single.groupId eq '1'}">���������Ա</c:if>
			<c:if test="${single.groupId eq '0'}">��ͨ��Ա���Ա</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">ע�����ڣ�</td>
		<td nowrap>${single.memberRegTime}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td style="color:red" nowrap>*���������Ա���룺</td>
		<td nowrap><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''">&nbsp;<b><span id="checkmessage" style="color:red"></span></b></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="ɾ����Ա" onclick="mydeleteusersubmit()">
			<input type="reset" value="����ɾ��" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>