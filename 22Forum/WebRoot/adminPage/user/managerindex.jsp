<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<body>
	<center>	
	<table border="0" width="100%" style="margin-bottom:20;word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
		<tr bgcolor="#E9E9E9">
			<td>
				�����Խ������²�����<br>
				>> <a href="<%=contextPath%>/admin/user/e/addnewuserview">����»�Ա</a><br>
				>> <a href="<%=contextPath%>/admin/user/c/searchuserview">���һ�Ա</a>
			</td>
		</tr>
	</table>
	
	<a name="listtop"></a>
	<c:set var="users" value="${requestScope.userList}"/>
	<table border="0" width="100%" style="word-break:break-all" cellpadding="4" cellspacing="1" bgcolor="#8A8989">
		<tr height="25" align="center" class="listhead">
  			<td>��Ա����</td>
  			<td width="10%">��Ա״̬</td>
  			<td width="16%">ע������</td>
  			<td width="10%">���</td>
  			<td width="16%" colspan="2">�������</td>
  			<td width="15%" colspan="2">����</td>
  		</tr>
		<c:if test="${empty users}">
		<tr height="50" bgcolor="#F0F0F0" align="center"><td colspan="7"><b>û�л�Ա��</b></td></tr></c:if>
		<c:if test="${!empty users}">
		<!-- ��ҳ�б���ʾ��Ա -->
  		<c:forEach var="single" varStatus="tvs" items="${users}">
 	 	<c:if test="${tvs.index%2==0}">
		<tr bgcolor="#F0F0F0" height="35"></c:if>
		<c:if test="${tvs.index%2!=0}">
		<tr bgcolor="white" height="35"></c:if>
			<td>
				<c:if test="${single.memberSex eq '��'}">
				<img src="<%=contextPath%>/images/icon/man.gif"></c:if>
				<c:if test="${single.memberSex eq 'Ů'}">
				<img src="<%=contextPath%>/images/icon/women.gif"></c:if>
				<a href="<%=contextPath%>/admin/user/c/adminviewmember?memberName=${single.memberName}" target="_blank">${single.memberName}</a>				
			</td>
			<td align="center">
				<c:if test="${single.memberStatus eq '1'}"><b><font color="blue">�</font></b></c:if>
				<c:if test="${single.memberStatus eq '0'}"><b><font color="red">����</font></b></c:if>
			</td>
			<td align="center">${single.memberRegTime}</td>
			<td align="center">
				<c:if test="${single.groupId eq '4'}">ϵͳ����Ա</c:if>
				<c:if test="${single.groupId eq '3'}">��̳����Ա</c:if>
				<c:if test="${single.groupId eq '2'}">������Ա</c:if>
				<c:if test="${single.groupId eq '1'}">������Ա</c:if>
				<c:if test="${single.groupId eq '0'}">��ͨ��Ա</c:if>
			</td>
			<td align="center" width="8%">
				<c:if test="${single.groupId eq '2'}">
				<a href="<%=contextPath%>/admin/user/e/assigncategoryview?memberid=${single.id}">�������</a>
				</c:if>
			</td>
			<td align="center" width="8%">
				<c:if test="${single.groupId eq '1'}">
				<a href="<%=contextPath%>/admin/user/d/assignboardview?memberid=${single.id}">������</a>
				</c:if>
			</td>
			<td align="center">
				<c:if test="${single.memberStatus eq '1'}">
				<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${single.id}&action=disable&currentP=${param.currentP}">������û�</a>
				</c:if>
				<c:if test="${single.memberStatus eq '0'}">
				<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${single.id}&action=enable&currentP=${param.currentP}">������û�</a>
				</c:if>
			</td>
			<td align="center"><a href="<%=contextPath%>/admin/user/e/deleteuserview?deletememberId=${single.id}&currentP=${param.currentP}">ɾ��</a></td>
		</tr>
		</c:forEach>
	</table>
	
	<!-- ��ʾ��ҳ���� -->
	<div align="left"><jsp:include page="/pageBar.jsp"/></div>
	</c:if>
	</center>
</body>