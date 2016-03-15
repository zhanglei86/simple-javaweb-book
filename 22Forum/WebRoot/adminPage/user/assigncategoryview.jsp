<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>Ϊ��Ա�������</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminuser.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.usersingle}"/>

<c:if test="${!empty single}">
<!-- ʵ��Ϊ��Ա���������� -->
<form action="<%=contextPath%>/admin/user/d/assigncategoryrun" method="post" name="adminform">
<input type="hidden" name="autoforward" value="<%=request.getHeader("referer") %>">
<input type="hidden" name="memberid" value="${param.memberid}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">Ϊ��Ա�������</td></tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">��Ա���ƣ�</td>
		<td nowrap>${single.memberName}</td>
	</tr>
	<tr bgcolor="white" height="30">
		<td nowrap>�ѷ������</td>
		<td nowrap>			
			<c:if test="${empty requestScope.assigncategorysname}">û�з���</c:if>
			<c:if test="${!empty requestScope.assigncategorysname}">
			<c:forEach var="assignsingle" items="${requestScope.assigncategorysname}">
			${assignsingle}��
			</c:forEach>
			</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap valign="top">ѡ�����</td>
		<td nowrap>
			<select name="assigncategoryid" onchange="checkassigncategory(this.options[this.selectedIndex].value,'${requestScope.assigncategorysid}')">
				<option value="">--��ѡ��һ�����--</option>
				<c:if test="${(!empty requestScope.categorys)}">
				<c:forEach var="categorysingle" items="${requestScope.categorys}">
				<c:set var="categoryid" value="${categorysingle.key}"/>
				<c:set var="categoryname" value="${categorys[categoryid]}"/>
				<option value="${categoryid}">${categoryname}</option>							<!-- �����������б��� -->
				</c:forEach>
				</c:if>
			</select>&nbsp;
			<b><span id="checkmessage" style="color:red"></span></b>
		</td>
	</tr>	
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="�������" onclick="myassignsubmit()" disabled>
			<input type="reset" value="��������">
			<input type="reset" value="��������" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>