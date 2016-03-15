<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>�鿴��Ա����</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">

<body>
<center>

<c:set var="member" value="${requestScope.viewmember}"/>
<img src="<%=contextPath%>/images/user/${member.memberIcon}"><br>
<a href="<%=contextPath%>/visit/user/a/listmemberposttopic?memberName=${member.memberName}" target="_blank">�鿴�û�Ա���������</a><br><br>

<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead" height="25"><td colspan="2">��Ա����</td></tr>
	<tr bgcolor="white" height="20">
		<td width="25%" align="center">��Ա����</td>
		<td><b>${member.memberName}</b></td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">���</td>
		<td>
			<c:if test="${member.groupId eq '4'}">ϵͳ�������Ա</c:if>
			<c:if test="${member.groupId eq '3'}">��̳�������Ա</c:if>
			<c:if test="${member.groupId eq '2'}">���������Ա</c:if>
			<c:if test="${member.groupId eq '1'}">���������Ա</c:if>
			<c:if test="${member.groupId eq '0'}">��ͨ��Ա���Ա</c:if>
		</td>
	</tr>
	<c:if test="${member.groupId eq '2'}">
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">��������</td>
		<td>
			<c:if test="${empty requestScope.assigncategorysname}">
			û�з���</c:if>
			<c:if test="${!empty requestScope.assigncategorysname}">
			<c:forEach var="assignsingle" items="${requestScope.assigncategorysname}">
			${assignsingle}��</c:forEach></c:if>		
		</td>
	</tr>
	</c:if>
	<c:if test="${member.groupId eq '1'}">
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">����İ��</td>
		<td>
			<c:if test="${empty requestScope.assignboardsname}">
			û�з���</c:if>
			<c:if test="${!empty requestScope.assignboardsname}">
			<c:forEach var="assignsingle" items="${requestScope.assignboardsname}">
			${assignsingle}��</c:forEach></c:if>		
		</td>
	</tr>	
	</c:if>
	<tr bgcolor="white" height="20">
		<td align="center">�Ա�</td>
		<td>${member.memberSex}</td>
	</tr>	
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">����ֵ</td>
		<td>${member.memberExperience} ��</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">����ֵ</td>
		<td>${member.memberCharm} ��</td>
	</tr>
	<tr  bgcolor="#F5F5F5" height="20">
		<td align="center">��Ա�ȼ�</td>
		<td>
			<jsp:include page="/gradeHandler.jsp">
				<jsp:param name="experience" value="${member.memberExperience}"/>
				<jsp:param name="charm" value="${member.memberCharm}"/>
			</jsp:include>
		</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">��������</td>
		<td><b>${member.memberPostNum}</b> ��</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">����</td>
		<td>${member.memberAge}</td>
	</tr>	
	<tr bgcolor="white">
		<td align="center">QQ����</td>
		<td>${member.memberOICQ}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">ע������</td>
		<td>${member.memberRegTime}</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">�ϴε�¼</td>
		<td>${member.memberLogonTime}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">����ǩ��</td>
		<td>${member.memberSign}</td>
	</tr>
</table>
</center>
</body>