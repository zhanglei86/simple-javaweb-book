<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>�޸�����״̬</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.topicsingle}"/>
<c:if test="${empty single}"><li>��Ҫ�༭�����Ӳ����ڻ��Ѿ���ɾ����</li></c:if>
<c:if test="${!empty single}">
<!-- ʵ���޸��������ͽ��� -->
<form action="<%=contextPath%>/visit/topic/c/changestatusrun" method="post" name="changeform">
<input type="hidden" name="topicId" value="${param.topicId}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">�༭���ӣ�${single.topicTitle}</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">���⣺</td>
		<td nowrap>${single.topicTitle}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap>���ݣ�</td>
		<td>${single.topicContent}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>���ߣ�</td>
		<td nowrap>${single.topicAuthorName}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>����ʱ�䣺</td>
		<td nowrap>${single.topicPostTime}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>Ŀǰ����״̬��</td>
		<td nowrap>
		<c:if test="${single.topicStatus eq '2'}">��������</c:if>
		<c:if test="${single.topicStatus eq '1'}">��������</c:if>
		<c:if test="${single.topicStatus eq '0'}">�ر�����</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>����״̬��</td>
		<td nowrap>
			<select name="status">
				<option value="2">��������</option>
				<option value="1">��������</option>
				<option value="0">�ر�����</option>
			</select>
		</td>
	</tr>
	<tr bgcolor="white">
		<td style="color:red">*���������Ա���룺</td>
		<td><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''">&nbsp;<b><span id="checkmessage" style="color:red"></span></b></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="editsubmitb" value="�޸�״̬" onclick="myvalidateadminsubmit()">
			<input type="reset" value="��������">
			<input type="reset" value="�����޸�" onclick="javascript:history.back(1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>