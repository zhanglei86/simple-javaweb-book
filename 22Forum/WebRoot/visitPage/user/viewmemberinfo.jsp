<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>查看会员资料</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">

<body>
<center>

<c:set var="member" value="${requestScope.viewmember}"/>
<img src="<%=contextPath%>/images/user/${member.memberIcon}"><br>
<a href="<%=contextPath%>/visit/user/a/listmemberposttopic?memberName=${member.memberName}" target="_blank">查看该会员发表的帖子</a><br><br>

<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead" height="25"><td colspan="2">会员资料</td></tr>
	<tr bgcolor="white" height="20">
		<td width="25%" align="center">会员名称</td>
		<td><b>${member.memberName}</b></td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">身份</td>
		<td>
			<c:if test="${member.groupId eq '4'}">系统管理组成员</c:if>
			<c:if test="${member.groupId eq '3'}">论坛管理组成员</c:if>
			<c:if test="${member.groupId eq '2'}">类别管理组成员</c:if>
			<c:if test="${member.groupId eq '1'}">版块管理组成员</c:if>
			<c:if test="${member.groupId eq '0'}">普通会员组成员</c:if>
		</td>
	</tr>
	<c:if test="${member.groupId eq '2'}">
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">分配的类别</td>
		<td>
			<c:if test="${empty requestScope.assigncategorysname}">
			没有分配</c:if>
			<c:if test="${!empty requestScope.assigncategorysname}">
			<c:forEach var="assignsingle" items="${requestScope.assigncategorysname}">
			${assignsingle}，</c:forEach></c:if>		
		</td>
	</tr>
	</c:if>
	<c:if test="${member.groupId eq '1'}">
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">分配的版块</td>
		<td>
			<c:if test="${empty requestScope.assignboardsname}">
			没有分配</c:if>
			<c:if test="${!empty requestScope.assignboardsname}">
			<c:forEach var="assignsingle" items="${requestScope.assignboardsname}">
			${assignsingle}，</c:forEach></c:if>		
		</td>
	</tr>	
	</c:if>
	<tr bgcolor="white" height="20">
		<td align="center">性别</td>
		<td>${member.memberSex}</td>
	</tr>	
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">经验值</td>
		<td>${member.memberExperience} 点</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">魅力值</td>
		<td>${member.memberCharm} 点</td>
	</tr>
	<tr  bgcolor="#F5F5F5" height="20">
		<td align="center">会员等级</td>
		<td>
			<jsp:include page="/gradeHandler.jsp">
				<jsp:param name="experience" value="${member.memberExperience}"/>
				<jsp:param name="charm" value="${member.memberCharm}"/>
			</jsp:include>
		</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">发表帖子</td>
		<td><b>${member.memberPostNum}</b> 帖</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">年龄</td>
		<td>${member.memberAge}</td>
	</tr>	
	<tr bgcolor="white">
		<td align="center">QQ号码</td>
		<td>${member.memberOICQ}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">注册日期</td>
		<td>${member.memberRegTime}</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">上次登录</td>
		<td>${member.memberLogonTime}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">个性签名</td>
		<td>${member.memberSign}</td>
	</tr>
</table>
</center>
</body>