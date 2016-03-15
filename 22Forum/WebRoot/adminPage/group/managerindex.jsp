<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<body>
	<center>
	<c:set var="groups" value="${requestScope.grouplist}"/>
	<table border="0" width="100%" style="word-break:break-all" cellpadding="4" cellspacing="1" bgcolor="#8A8989">
		<tr height="25" align="center" class="listhead">
  			<td>用户组名称/描述</td>
  			<td width="20%">会员数</td>
  			<td width="20%">编辑</td>
  			<td width="20%">添加成员</td>
  		</tr>
		<c:if test="${empty groups}">
		<tr height="50" bgcolor="#F0F0F0" align="center"><td colspan="7"><b>没有用户组！</b></td></tr>
		</c:if>
		
		<c:if test="${!empty groups}">
  		<c:forEach var="single" varStatus="tvs" items="${groups}">
 	 	<c:if test="${tvs.index%2==0}">
		<tr bgcolor="#F0F0F0" height="35"></c:if>
		<c:if test="${tvs.index%2!=0}">
		<tr bgcolor="white" height="35"></c:if>
			<td>
				<b><font color="blue">${single.groupName}</font></b><br>
				${single.groupInfo}
			</td>
			<td align="center"><b><a href="<%=contextPath%>/admin/group/e/listmemberingroup?groupId=${single.groupId}" target="_blank">${single.memberNum}</a></b> 人</td>
			<td align="center"><a href="<%=contextPath%>/admin/group/e/modifygroupview?groupId=${single.groupId}">编辑</a></td>
			<td align="center"><c:if test="${single.groupId gt 0}"><a href="<%=contextPath%>/admin/group/e/addmembertogroupview?groupId=${single.groupId}">添加成员</a></c:if></td>
		</tr>
		</c:forEach>
		</c:if>
	</table>	
	</center>
</body>