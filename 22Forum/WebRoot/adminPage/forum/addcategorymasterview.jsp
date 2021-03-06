<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>添加新类主</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminforum.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.categorysingle}"/>

<c:if test="${!empty single}">
<!-- 实现添加新类主界面 -->
<form action="<%=contextPath%>/admin/category/e/addcategorymasterrun" method="post" name="adminform">
<input type="hidden" name="categoryId" value="${single.id}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">添加新类主</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">类别名称：</td>
		<td nowrap>${single.categoryName}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap>已有类主：</td>
		<td>
			<c:set var="masters" value="${single.masters}"/>
			<c:if test="${empty masters}">没有类主</c:if>
			<c:if test="${!empty masters}">
			<c:forEach var="master" items="${masters}">
			<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${master}" target="_blank">${master}</a>&nbsp;&nbsp;
			</c:forEach>
			</c:if>
		</td>
	</tr>
	<tr bgcolor="white">		
		<td nowrap>新类主：</td>
		<td><input type="text" name="newmaster" size="30" class="login"></td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td style="color:red" nowrap>*请输入管理员密码：</td>
		<td nowrap><input type="password" name="adminpswd" size="30" class="login"></td>
	</tr>
	<tr bgcolor="white"><td colspan="2" align="center"><b><span id="checkmessage" style="color:red"></span></b></td></tr>	
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="添加类主" onclick="myaddmastersubmit();">
			<input type="reset" value="重新设置">
			<input type="reset" value="放弃添加" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>