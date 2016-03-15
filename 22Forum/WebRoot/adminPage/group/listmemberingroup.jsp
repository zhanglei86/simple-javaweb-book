<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">

<body>
	<center>
	<c:set var="members" value="${requestScope.memberlist}"/>
	<c:if test="${empty members}"><b>没有会员！</b></c:if>
	
	<c:if test="${!empty members}">		
	<table border="0" width="100%" style="word-break:break-all" cellpadding="4" cellspacing="1" bgcolor="#8A8989">
		<tr height="25" align="center" class="listhead">
  			<td>会员名称</td>
  			<td width="10%">会员状态</td>
  			<td width="16%">注册日期</td>
  			<td width="10%">身份</td>
  			<td width="16%" colspan="2">操作</td>
  		</tr>
		<!-- 分页列表显示会员 -->
  		<c:forEach var="single" varStatus="tvs" items="${members}">
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
				<c:if test="${single.groupId ne '0'}">
				<a href="<%=contextPath%>/admin/group/e/removefromgroupview?groupId=${single.groupId}&removememberName=${single.memberName}">从该组移除</a>
				</c:if>
			</td>
			<td align="center" width="8%">
				<c:if test="${single.memberStatus eq '1'}">
				<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${single.id}&action=disable&currentP=${param.currentP}">冻结用户</a>
				</c:if>
				<c:if test="${single.memberStatus eq '0'}">
				<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${single.id}&action=enable&currentP=${param.currentP}">激活用户</a>
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>
	
	<!-- 显示分页导航 -->
	<div align="left"><jsp:include page="/pageBar.jsp"/></div>
	</c:if>
	</center>
</body>