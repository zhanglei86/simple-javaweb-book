<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>�鿴��Ա����</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">

<body>
<c:set var="member" value="${requestScope.viewmember}"/>
�������
<table border="0" width="100%" style="word-break:break-all" cellpadding="5" cellspacing="1" bgcolor="#8A8989">
	<tr bgcolor="#e9e9e9">
		<td align="center" width="15%"><img src="<%=contextPath%>/images/user/${member.memberIcon}" border="0"></td>
		<td width="35%">
			<a href="<%=contextPath%>/admin/user/c/admineditmemberinfoview?memberName=${member.memberName}">�༭��Ա����</a><br>
			<a href="<%=contextPath%>/admin/user/c/admineditmemberpswdview?memberName=${member.memberName}">��������</a><br>
			<a href="<%=contextPath%>/admin/user/e/deleteuserview?deletememberId=${member.id}">ɾ����Ա</a><br>
			��Ա״̬:
			<c:if test="${member.memberStatus eq '1'}">
			<b><font color="blue">�</font></b>
			(<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${member.id}&action=disable">������û�</a>)
			</c:if>
			<c:if test="${member.memberStatus eq '0'}">
			<b><font color="red">����</font></b>
			(<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${member.id}&action=enable">������û�</a>)
			</c:if>
		</td>
		<td>
			<a href="<%=contextPath%>/admin/user/e/assigncategoryview?memberid=${member.id}">�������</a><br>
			<a href="<%=contextPath%>/admin/user/d/assignboardview?memberid=${member.id}">������</a><br>
			
		</td>		
	</tr>
</table>
��������
<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead" height="25"><td colspan="2">��������</td></tr>
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
	<c:if test="${member.groupId eq '2'}">
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center" valign="top">��������</td>
		<td>
			<c:if test="${empty requestScope.assigncategorysname}">
			û�з���</c:if>
			<c:if test="${!empty requestScope.assigncategorysname}">
			<table border="0" width="40%">
				<c:forEach var="assignsingle" items="${requestScope.assigncategorysname}">
				<tr>
					<td>${assignsingle}</td>
					<td><a href="<%=contextPath%>/admin/user/e/cancelcategorymasterview?memberName=${member.memberName}&categoryName=${assignsingle}">жְ</a></td>
				</tr>
				</c:forEach>		
			</table>
			</c:if>
		</td>
	</tr>
	</c:if>
	<c:if test="${member.groupId eq '1'}">
	<tr bgcolor="white" height="20">
		<td align="center" valign="top">����İ��</td>
		<td>
			<c:if test="${empty requestScope.assignboardsname}">
			û�з���</c:if>
			<c:if test="${!empty requestScope.assignboardsname}">
			<table border="0" cellpadding="0" cellspacing="0" width="40%">
				<c:forEach var="assignsingle" items="${requestScope.assignboardsname}">
				<tr height="25">
					<td>${assignsingle}</td>
					<td><a href="<%=contextPath%>/admin/user/d/cancelboardmasterview?memberName=${member.memberName}&boardName=${assignsingle}">жְ</a></td>
				</tr>
				</c:forEach>		
			</table>
			</c:if>
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
</body>