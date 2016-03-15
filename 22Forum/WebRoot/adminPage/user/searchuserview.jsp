<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>查询会员</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminuser.js"></script>

<body>
<center>

<!-- 实现查询会员界面 -->
<form action="<%=contextPath%>/admin/user/c/searchuserrun" method="post" name="adminform">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">删除会员</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap>会员ID值：</td>
		<td nowrap><input type="text" name="searchid" class="login" size="50" value="${param.searchid}"></td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td colspan="2">
			<b>Or</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<b><span id="checkmessage" style="color:red"></span></b>
		</td>
	</tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="25%">会员名称：</td>
		<td nowrap><input type="text" name="searchname" class="login" size="50" value="${param.searchname}"></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="查询" onclick="mysearchsubmit()">
			<input type="reset" value="重置">						
		</td>
	</tr>
</table>
</form>

<c:set var="single" value="${requestScope.searchsingle}"/>
<c:if test="${!(single eq null)}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr height="25" align="center" class="listhead">
  			<td width="5%">会员ID</td>
  			<td>会员名称</td>
  			<td width="10%">会员状态</td>
  			<td width="16%">注册日期</td>
  			<td width="10%">身份</td>
  			<td width="16%" colspan="2">分配管理</td>
  			<td width="15%" colspan="2">操作</td>
  	</tr>
  	<c:if test="${single.id eq 0}">
	<tr height="50" bgcolor="#F0F0F0" align="center"><td colspan="9"><b>没有查询到会员！</b></td></tr></c:if>
	<c:if test="${!(single.id eq 0)}">
	<tr bgcolor="white" height="35">
		<td align="center">${single.id}</td>
		<td>
			<c:if test="${single.memberSex eq '男'}">
			<img src="<%=contextPath%>/images/icon/man.gif"></c:if>
			<c:if test="${single.memberSex eq '女'}">
			<img src="<%=contextPath%>/images/icon/women.gif"></c:if>
			<a href="<%=contextPath%>/admin/user/c/adminviewmember?memberName=${single.memberName}" target="_blank">${single.memberName}</a>				
		</td>
		<td align="center">
			<c:if test="${single.memberStatus eq '1'}"><b><font color="blue">活动</font></b></c:if>
			<c:if test="${single.memberStatus eq '0'}"><b><font color="red">冻结</font></b></c:if>
		</td>
		<td align="center">${single.memberRegTime}</td>
		<td align="center">
			<c:if test="${single.groupId eq '4'}">系统管理员</c:if>
			<c:if test="${single.groupId eq '3'}">论坛管理员</c:if>
			<c:if test="${single.groupId eq '2'}">类别管理员</c:if>
			<c:if test="${single.groupId eq '1'}">版块管理员</c:if>
			<c:if test="${single.groupId eq '0'}">普通会员</c:if>
		</td>
		<td align="center" width="8%">
			<c:if test="${single.groupId eq '2'}">
			<a href="<%=contextPath%>/admin/user/e/assigncategoryview?memberid=${single.id}">分配类别</a>
			</c:if>
		</td>
		<td align="center" width="8%">
			<c:if test="${single.groupId eq '1'}">
			<a href="<%=contextPath%>/admin/user/d/assignboardview?memberid=${single.id}">分配版块</a>
			</c:if>
		</td>
		<td align="center">
			<c:if test="${single.memberStatus eq '1'}">
			<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${single.id}&action=disable&currentP=${param.currentP}">冻结该用户</a>
			</c:if>
			<c:if test="${single.memberStatus eq '0'}">
			<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${single.id}&action=enable&currentP=${param.currentP}">激活该用户</a>
			</c:if>
		</td>
		<td align="center"><a href="<%=contextPath%>/admin/user/e/deleteuserview?deletememberId=${single.id}&currentP=${param.currentP}">删除</a></td>
	</tr>
	</c:if>
</table>
</c:if>
</center>
</body>