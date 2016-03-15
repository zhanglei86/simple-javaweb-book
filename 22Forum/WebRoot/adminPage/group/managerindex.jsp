<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<body>
	<center>
	<c:set var="groups" value="${requestScope.grouplist}"/>
	<table border="0" width="100%" style="word-break:break-all" cellpadding="4" cellspacing="1" bgcolor="#8A8989">
		<tr height="25" align="center" class="listhead">
  			<td>�û�������/����</td>
  			<td width="20%">��Ա��</td>
  			<td width="20%">�༭</td>
  			<td width="20%">��ӳ�Ա</td>
  		</tr>
		<c:if test="${empty groups}">
		<tr height="50" bgcolor="#F0F0F0" align="center"><td colspan="7"><b>û���û��飡</b></td></tr>
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
			<td align="center"><b><a href="<%=contextPath%>/admin/group/e/listmemberingroup?groupId=${single.groupId}" target="_blank">${single.memberNum}</a></b> ��</td>
			<td align="center"><a href="<%=contextPath%>/admin/group/e/modifygroupview?groupId=${single.groupId}">�༭</a></td>
			<td align="center"><c:if test="${single.groupId gt 0}"><a href="<%=contextPath%>/admin/group/e/addmembertogroupview?groupId=${single.groupId}">��ӳ�Ա</a></c:if></td>
		</tr>
		</c:forEach>
		</c:if>
	</table>	
	</center>
</body>