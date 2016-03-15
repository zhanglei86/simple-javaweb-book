<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>查看会员资料</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">

<body>
<c:set var="member" value="${requestScope.viewmember}"/>
控制面版
<table border="0" width="100%" style="word-break:break-all" cellpadding="5" cellspacing="1" bgcolor="#8A8989">
	<tr bgcolor="#e9e9e9">
		<td align="center" width="15%"><img src="<%=contextPath%>/images/user/${member.memberIcon}" border="0"></td>
		<td width="35%">
			<a href="<%=contextPath%>/admin/user/c/admineditmemberinfoview?memberName=${member.memberName}">编辑会员资料</a><br>
			<a href="<%=contextPath%>/admin/user/c/admineditmemberpswdview?memberName=${member.memberName}">更改密码</a><br>
			<a href="<%=contextPath%>/admin/user/e/deleteuserview?deletememberId=${member.id}">删除会员</a><br>
			会员状态:
			<c:if test="${member.memberStatus eq '1'}">
			<b><font color="blue">活动</font></b>
			(<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${member.id}&action=disable">冻结该用户</a>)
			</c:if>
			<c:if test="${member.memberStatus eq '0'}">
			<b><font color="red">冻结</font></b>
			(<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${member.id}&action=enable">激活该用户</a>)
			</c:if>
		</td>
		<td>
			<a href="<%=contextPath%>/admin/user/e/assigncategoryview?memberid=${member.id}">分配类别</a><br>
			<a href="<%=contextPath%>/admin/user/d/assignboardview?memberid=${member.id}">分配版块</a><br>
			
		</td>		
	</tr>
</table>
个人资料
<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead" height="25"><td colspan="2">个人资料</td></tr>
	<tr bgcolor="white" height="20">
		<td width="25%" align="center">会员名称</td>
		<td><b>${member.memberName}</b></td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">身份</td>
		<td>
			<c:if test="${member.groupId eq '4'}">系统管理员</c:if>
			<c:if test="${member.groupId eq '3'}">论坛管理员</c:if>
			<c:if test="${member.groupId eq '2'}">类别管理员</c:if>
			<c:if test="${member.groupId eq '1'}">版块管理员</c:if>
			<c:if test="${member.groupId eq '0'}">普通会员</c:if>
		</td>
	</tr>
	<c:if test="${member.groupId eq '2'}">
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center" valign="top">分配的类别</td>
		<td>
			<c:if test="${empty requestScope.assigncategorysname}">
			没有分配</c:if>
			<c:if test="${!empty requestScope.assigncategorysname}">
			<table border="0" width="40%">
				<c:forEach var="assignsingle" items="${requestScope.assigncategorysname}">
				<tr>
					<td>${assignsingle}</td>
					<td><a href="<%=contextPath%>/admin/user/e/cancelcategorymasterview?memberName=${member.memberName}&categoryName=${assignsingle}">卸职</a></td>
				</tr>
				</c:forEach>		
			</table>
			</c:if>
		</td>
	</tr>
	</c:if>
	<c:if test="${member.groupId eq '1'}">
	<tr bgcolor="white" height="20">
		<td align="center" valign="top">分配的版块</td>
		<td>
			<c:if test="${empty requestScope.assignboardsname}">
			没有分配</c:if>
			<c:if test="${!empty requestScope.assignboardsname}">
			<table border="0" cellpadding="0" cellspacing="0" width="40%">
				<c:forEach var="assignsingle" items="${requestScope.assignboardsname}">
				<tr height="25">
					<td>${assignsingle}</td>
					<td><a href="<%=contextPath%>/admin/user/d/cancelboardmasterview?memberName=${member.memberName}&boardName=${assignsingle}">卸职</a></td>
				</tr>
				</c:forEach>		
			</table>
			</c:if>
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
</body>