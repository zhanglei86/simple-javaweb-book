<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>��ѯ��Ա</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminuser.js"></script>

<body>
<center>

<!-- ʵ�ֲ�ѯ��Ա���� -->
<form action="<%=contextPath%>/admin/user/c/searchuserrun" method="post" name="adminform">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">ɾ����Ա</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap>��ԱIDֵ��</td>
		<td nowrap><input type="text" name="searchid" class="login" size="50" value="${param.searchid}"></td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td colspan="2">
			<b>Or</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<b><span id="checkmessage" style="color:red"></span></b>
		</td>
	</tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="25%">��Ա���ƣ�</td>
		<td nowrap><input type="text" name="searchname" class="login" size="50" value="${param.searchname}"></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="��ѯ" onclick="mysearchsubmit()">
			<input type="reset" value="����">						
		</td>
	</tr>
</table>
</form>

<c:set var="single" value="${requestScope.searchsingle}"/>
<c:if test="${!(single eq null)}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr height="25" align="center" class="listhead">
  			<td width="5%">��ԱID</td>
  			<td>��Ա����</td>
  			<td width="10%">��Ա״̬</td>
  			<td width="16%">ע������</td>
  			<td width="10%">���</td>
  			<td width="16%" colspan="2">�������</td>
  			<td width="15%" colspan="2">����</td>
  	</tr>
  	<c:if test="${single.id eq 0}">
	<tr height="50" bgcolor="#F0F0F0" align="center"><td colspan="9"><b>û�в�ѯ����Ա��</b></td></tr></c:if>
	<c:if test="${!(single.id eq 0)}">
	<tr bgcolor="white" height="35">
		<td align="center">${single.id}</td>
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
	</c:if>
</table>
</c:if>
</center>
</body>