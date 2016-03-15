<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>个人资料</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">

<body>
<c:set var="my" value="${sessionScope.loginer}"/>
控制面版
<table border="0" width="100%" style="word-break:break-all" cellpadding="5" cellspacing="1" bgcolor="#8A8989">
	<tr bgcolor="#e9e9e9">
		<td align="center" width="15%"><img src="<%=contextPath%>/images/user/${my.memberIcon}" border="0"></td>
		<td width="35%">
			<a href="<%=contextPath%>/visit/myself/a/editmyinfoview">编辑个人资料</a><br>
			<a href="<%=contextPath%>/visit/myself/a/editmyiconview">更换头像</a><br>
			<a href="<%=contextPath%>/visit/myself/a/editmysignview">更改个性签名</a><br>
			<a href="<%=contextPath%>/visit/myself/a/editmypswdview">更改密码</a><br>
			<a href="<%=contextPath%>/visit/myself/a/cancelautologin">取消自动登录(删除Cookie)</a>
		</td>
		<td>
			<a href="<%=contextPath%>/visit/myself/a/mymessagebox" target="_blank">我的信箱</a><br>
			<a href="<%=contextPath%>/visit/myself/a/mycollect" target="_blank">我的收藏夹</a><br>
			<a href="<%=contextPath%>/visit/myself/a/listmyposttopic" target="_blank">我发表的帖子</a><br>
			<a href="<%=contextPath%>/visit/myself/a/listmyreplytopic" target="_blank">我回复的帖子</a>
		</td>		
	</tr>
</table>
我的个人资料
<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead" height="25"><td colspan="2">个人资料</td></tr>
	<tr bgcolor="white" height="20">
		<td width="25%" align="center">会员名称</td>
		<td><b>${my.memberName}</b></td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">身份</td>
		<td>
			<c:if test="${my.groupId eq '4'}">系统管理员</c:if>
			<c:if test="${my.groupId eq '3'}">论坛管理员</c:if>
			<c:if test="${my.groupId eq '2'}">类别管理员</c:if>
			<c:if test="${my.groupId eq '1'}">版块管理员</c:if>
			<c:if test="${my.groupId eq '0'}">普通会员</c:if>
		</td>
	</tr>
	<c:if test="${my.groupId eq '2'}">
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
	<c:if test="${my.groupId eq '1'}">
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
		<td>${my.memberSex}</td>
	</tr>	
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">经验值</td>
		<td>${my.memberExperience} 点</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">魅力值</td>
		<td>${my.memberCharm} 点</td>
	</tr>
	<tr  bgcolor="#F5F5F5" height="20">
		<td align="center">会员等级</td>
		<td>
			<jsp:include page="/gradeHandler.jsp">
				<jsp:param name="experience" value="${my.memberExperience}"/>
				<jsp:param name="charm" value="${my.memberCharm}"/>
			</jsp:include>
		</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">发表帖子</td>
		<td><b>${my.memberPostNum}</b> 帖</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">年龄</td>
		<td>${my.memberAge}</td>
	</tr>	
	<tr bgcolor="white">
		<td align="center">QQ号码</td>
		<td>${my.memberOICQ}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">注册日期</td>
		<td>${my.memberRegTime}</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">上次登录</td>
		<td>${my.memberLogonTime}</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">个性签名</td>
		<td>${my.memberSign}</td>
	</tr>
</table>
</body>