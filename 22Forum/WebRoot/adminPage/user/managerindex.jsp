<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<body>
	<center>	
	<table border="0" width="100%" style="margin-bottom:20;word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
		<tr bgcolor="#E9E9E9">
			<td>
				您可以进行以下操作：<br>
				>> <a href="<%=contextPath%>/admin/user/e/addnewuserview">添加新会员</a><br>
				>> <a href="<%=contextPath%>/admin/user/c/searchuserview">查找会员</a>
			</td>
		</tr>
	</table>
	
	<a name="listtop"></a>
	<c:set var="users" value="${requestScope.userList}"/>
	<table border="0" width="100%" style="word-break:break-all" cellpadding="4" cellspacing="1" bgcolor="#8A8989">
		<tr height="25" align="center" class="listhead">
  			<td>会员名称</td>
  			<td width="10%">会员状态</td>
  			<td width="16%">注册日期</td>
  			<td width="10%">身份</td>
  			<td width="16%" colspan="2">分配管理</td>
  			<td width="15%" colspan="2">操作</td>
  		</tr>
		<c:if test="${empty users}">
		<tr height="50" bgcolor="#F0F0F0" align="center"><td colspan="7"><b>没有会员！</b></td></tr></c:if>
		<c:if test="${!empty users}">
		<!-- 分页列表显示会员 -->
  		<c:forEach var="single" varStatus="tvs" items="${users}">
 	 	<c:if test="${tvs.index%2==0}">
		<tr bgcolor="#F0F0F0" height="35"></c:if>
		<c:if test="${tvs.index%2!=0}">
		<tr bgcolor="white" height="35"></c:if>
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
		</c:forEach>
	</table>
	
	<!-- 显示分页导航 -->
	<div align="left"><jsp:include page="/pageBar.jsp"/></div>
	</c:if>
	</center>
</body>