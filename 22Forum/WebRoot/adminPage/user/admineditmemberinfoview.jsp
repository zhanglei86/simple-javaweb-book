<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>�༭��Ա����</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminuser.js"></script>

<body>
<c:set var="member" value="${requestScope.edituser}"/>
��Ա����
<form action="<%=contextPath%>/admin/user/c/admineditmemberinforun" name="adminform" method="post">
<input type="hidden" name="memberName" value="${member.memberName}">
<b><span id="checkmessage" style="color:red"></span></b>
<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead" height="25"><td colspan="2">���������л�Ա����</td></tr>
	<tr bgcolor="white" height="20">
		<td width="25%" align="center">��Ա����</td>
		<td><b>${member.memberName}</b></td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">���</td>
		<td>
			<c:if test="${member.groupId eq '4'}">ϵͳ����Ա</c:if>
			<c:if test="${member.groupId eq '3'}">��̳����Ա</c:if>
			<c:if test="${member.groupId eq '2'}">������Ա</c:if>
			<c:if test="${member.groupId eq '1'}">������Ա</c:if>
			<c:if test="${member.groupId eq '0'}">��ͨ��Ա</c:if>
		</td>
	</tr>
	<tr bgcolor="white" height="20">
		<td align="center">�Ա�</td>
		<td>
			<select name="sex">
				<c:if test="${member.memberSex eq '��'}">
				<option value="��" selected>��</option>
				<option value="Ů">Ů</option>
				</c:if>
				<c:if test="${member.memberSex eq 'Ů'}">
				<option value="��">��</option>
				<option value="Ů" selected>Ů</option>
				</c:if>
			</select>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">����</td>
		<td><input type="text" name="age" value="${member.memberAge}" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="white">
		<td align="center">QQ����</td>
		<td><input type="text" name="oicq" value="${member.memberOICQ}" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">��������</td>
		<td><b>${member.memberPostNum}</b> ��</td>
	</tr>
	<tr bgcolor="white" height="20">
		<td align="center">����ֵ</td>
		<td>${member.memberExperience} ��</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">����ֵ</td>
		<td>${member.memberCharm} ��</td>
	</tr>
	<tr  bgcolor="white" height="20">
		<td align="center">��Ա�ȼ�</td>
		<td>
			<jsp:include page="/gradeHandler.jsp">
				<jsp:param name="experience" value="${member.memberExperience}"/>
				<jsp:param name="charm" value="${member.memberCharm}"/>
			</jsp:include>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">ע������</td>
		<td>${member.memberRegTime}</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">�ϴε�¼</td>
		<td>${member.memberLogonTime}</td>
	</tr>
	<tr bgcolor="lightgrey">
		<td colspan="2" align="center">
			<input type="button" name="adminsubmitb" value="ȷ���޸�" onclick="mymodifyuserinfosubmit()">
			<input type="reset" value="������д">
			<input type="reset" value="�����༭" onclick="javascript:window.history.go(-1)">
		</td>
	</tr>
</table>
</form>
</body>