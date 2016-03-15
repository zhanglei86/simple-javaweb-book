<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>��������</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">

<body>
<c:set var="my" value="${sessionScope.loginer}"/>
�������
<table border="0" width="100%" style="word-break:break-all" cellpadding="5" cellspacing="1" bgcolor="#8A8989">
	<tr bgcolor="#e9e9e9">
		<td align="center" width="15%"><img src="<%=contextPath%>/images/user/${my.memberIcon}" border="0"></td>
		<td width="35%">
			<a href="<%=contextPath%>/visit/myself/a/editmyinfoview">�༭��������</a><br>
			<a href="<%=contextPath%>/visit/myself/a/editmyiconview">����ͷ��</a><br>
			<a href="<%=contextPath%>/visit/myself/a/editmysignview">���ĸ���ǩ��</a><br>
			<a href="<%=contextPath%>/visit/myself/a/editmypswdview">��������</a><br>
			<a href="<%=contextPath%>/visit/myself/a/cancelautologin">ȡ���Զ���¼(ɾ��Cookie)</a>
		</td>
		<td>
			<a href="<%=contextPath%>/visit/myself/a/mymessagebox" target="_blank">�ҵ�����</a><br>
			<a href="<%=contextPath%>/visit/myself/a/mycollect" target="_blank">�ҵ��ղؼ�</a><br>
			<a href="<%=contextPath%>/visit/myself/a/listmyposttopic" target="_blank">�ҷ��������</a><br>
			<a href="<%=contextPath%>/visit/myself/a/listmyreplytopic" target="_blank">�һظ�������</a>
		</td>		
	</tr>
</table>
�ҵĸ�������
<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead" height="25"><td colspan="2">��������</td></tr>
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
	<c:if test="${my.groupId eq '2'}">
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
	<c:if test="${my.groupId eq '1'}">
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
		<td>${my.memberSex}</td>
	</tr>	
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">����ֵ</td>
		<td>${my.memberExperience} ��</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">����ֵ</td>
		<td>${my.memberCharm} ��</td>
	</tr>
	<tr  bgcolor="#F5F5F5" height="20">
		<td align="center">��Ա�ȼ�</td>
		<td>
			<jsp:include page="/gradeHandler.jsp">
				<jsp:param name="experience" value="${my.memberExperience}"/>
				<jsp:param name="charm" value="${my.memberCharm}"/>
			</jsp:include>
		</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">��������</td>
		<td><b>${my.memberPostNum}</b> ��</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">����</td>
		<td>${my.memberAge}</td>
	</tr>	
	<tr bgcolor="white">
		<td align="center">QQ����</td>
		<td>${my.memberOICQ}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">ע������</td>
		<td>${my.memberRegTime}</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">�ϴε�¼</td>
		<td>${my.memberLogonTime}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">����ǩ��</td>
		<td>${my.memberSign}</td>
	</tr>
</table>
</body>