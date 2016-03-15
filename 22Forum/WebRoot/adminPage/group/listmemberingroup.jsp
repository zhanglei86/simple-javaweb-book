<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">

<body>
	<center>
	<c:set var="members" value="${requestScope.memberlist}"/>
	<c:if test="${empty members}"><b>û�л�Ա��</b></c:if>
	
	<c:if test="${!empty members}">		
	<table border="0" width="100%" style="word-break:break-all" cellpadding="4" cellspacing="1" bgcolor="#8A8989">
		<tr height="25" align="center" class="listhead">
  			<td>��Ա����</td>
  			<td width="10%">��Ա״̬</td>
  			<td width="16%">ע������</td>
  			<td width="10%">���</td>
  			<td width="16%" colspan="2">����</td>
  		</tr>
		<!-- ��ҳ�б���ʾ��Ա -->
  		<c:forEach var="single" varStatus="tvs" items="${members}">
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
				<c:if test="${single.groupId ne '0'}">
				<a href="<%=contextPath%>/admin/group/e/removefromgroupview?groupId=${single.groupId}&removememberName=${single.memberName}">�Ӹ����Ƴ�</a>
				</c:if>
			</td>
			<td align="center" width="8%">
				<c:if test="${single.memberStatus eq '1'}">
				<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${single.id}&action=disable&currentP=${param.currentP}">�����û�</a>
				</c:if>
				<c:if test="${single.memberStatus eq '0'}">
				<a href="<%=contextPath%>/admin/user/c/changeuserstatusrun?memberId=${single.id}&action=enable&currentP=${param.currentP}">�����û�</a>
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>
	
	<!-- ��ʾ��ҳ���� -->
	<div align="left"><jsp:include page="/pageBar.jsp"/></div>
	</c:if>
	</center>
</body>