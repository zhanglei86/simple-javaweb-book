<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>�༭��������</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visituser.js"></script>
<body>
<c:set var="my" value="${sessionScope.loginer}"/>
�ҵĸ�������
<form action="<%=contextPath%>/visit/myself/a/editmyinforun" name="editmyinfoform" method="post">
<b><span id="checkmessage" style="color:red"></span></b>
<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead" height="25"><td colspan="2">���������и�������</td></tr>
	<tr bgcolor="white" height="20">
		<td width="25%" align="center">��Ա����</td>
		<td><b>${my.memberName}</b></td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">���</td>
		<td>
			<c:if test="${my.groupId eq '4'}">ϵͳ����Ա</c:if>
			<c:if test="${my.groupId eq '3'}">��̳����Ա</c:if>
			<c:if test="${my.groupId eq '2'}">������Ա</c:if>
			<c:if test="${my.groupId eq '1'}">������Ա</c:if>
			<c:if test="${my.groupId eq '0'}">��ͨ��Ա</c:if>
		</td>
	</tr>
	<tr bgcolor="white" height="20">
		<td align="center">�Ա�</td>
		<td>
			<select name="sex">
				<c:if test="${my.memberSex eq '��'}">
				<option value="��" selected>��</option>
				<option value="Ů">Ů</option>
				</c:if>
				<c:if test="${my.memberSex eq 'Ů'}">
				<option value="��">��</option>
				<option value="Ů" selected>Ů</option>
				</c:if>
			</select>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">����</td>
		<td><input type="text" name="age" value="${my.memberAge}"></td>
	</tr>
	<tr bgcolor="white">
		<td align="center">QQ����</td>
		<td><input type="text" name="oicq" value="${my.memberOICQ}"></td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">��������</td>
		<td><b>${my.memberPostNum}</b> ��</td>
	</tr>
	<tr bgcolor="white" height="20">
		<td align="center">����ֵ</td>
		<td>${my.memberExperience} ��</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">����ֵ</td>
		<td>${my.memberCharm} ��</td>
	</tr>
	<tr  bgcolor="white" height="20">
		<td align="center">��Ա�ȼ�</td>
		<td>
			<jsp:include page="/gradeHandler.jsp">
				<jsp:param name="experience" value="${my.memberExperience}"/>
				<jsp:param name="charm" value="${my.memberCharm}"/>
			</jsp:include>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">ע������</td>
		<td>${my.memberRegTime}</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">�ϴε�¼</td>
		<td>${my.memberLogonTime}</td>
	</tr>
	<tr bgcolor="lightgrey">
		<td colspan="2" align="center">
			<input type="button" name="editmyinfosubmitb" value="ȷ���޸�" onclick="mymodifymyinfosubmit()">
			<input type="reset" value="������д">
			<input type="reset" value="�����༭" onclick="javascript:window.history.go(-1)">
		</td>
	</tr>
</table>
</form>
</body>