<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>编辑个人资料</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visituser.js"></script>
<body>
<c:set var="my" value="${sessionScope.loginer}"/>
我的个人资料
<form action="<%=contextPath%>/visit/myself/a/editmyinforun" name="editmyinfoform" method="post">
<b><span id="checkmessage" style="color:red"></span></b>
<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead" height="25"><td colspan="2">请输入下列个人资料</td></tr>
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
	<tr bgcolor="white" height="20">
		<td align="center">性别</td>
		<td>
			<select name="sex">
				<c:if test="${my.memberSex eq '男'}">
				<option value="男" selected>男</option>
				<option value="女">女</option>
				</c:if>
				<c:if test="${my.memberSex eq '女'}">
				<option value="男">男</option>
				<option value="女" selected>女</option>
				</c:if>
			</select>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">年龄</td>
		<td><input type="text" name="age" value="${my.memberAge}"></td>
	</tr>
	<tr bgcolor="white">
		<td align="center">QQ号码</td>
		<td><input type="text" name="oicq" value="${my.memberOICQ}"></td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">发表帖子</td>
		<td><b>${my.memberPostNum}</b> 帖</td>
	</tr>
	<tr bgcolor="white" height="20">
		<td align="center">经验值</td>
		<td>${my.memberExperience} 点</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">魅力值</td>
		<td>${my.memberCharm} 点</td>
	</tr>
	<tr  bgcolor="white" height="20">
		<td align="center">会员等级</td>
		<td>
			<jsp:include page="/gradeHandler.jsp">
				<jsp:param name="experience" value="${my.memberExperience}"/>
				<jsp:param name="charm" value="${my.memberCharm}"/>
			</jsp:include>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">注册日期</td>
		<td>${my.memberRegTime}</td>
	</tr>
	<tr bgcolor="white">
		<td align="center">上次登录</td>
		<td>${my.memberLogonTime}</td>
	</tr>
	<tr bgcolor="lightgrey">
		<td colspan="2" align="center">
			<input type="button" name="editmyinfosubmitb" value="确认修改" onclick="mymodifymyinfosubmit()">
			<input type="reset" value="重新填写">
			<input type="reset" value="放弃编辑" onclick="javascript:window.history.go(-1)">
		</td>
	</tr>
</table>
</form>
</body>