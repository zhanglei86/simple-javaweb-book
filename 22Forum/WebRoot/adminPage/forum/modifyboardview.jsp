<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>�޸İ����Ϣ</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminforum.js"></script>

<body onload="checkLen(adminform.boardinfo)">
<center>
<c:set var="single" value="${requestScope.boardsingle}"/>

<c:if test="${!empty single}">
<!-- ʵ���޸İ����Ϣ���� -->
<form action="<%=contextPath%>/admin/board/c/modifyboardrun" method="post" name="adminform">
<input type="hidden" name="boardId" value="${single.id}">
<input type="hidden" name="categoryId" value="${single.categoryId}">
<b><span id="checkmessage" style="color:red"></span></b>
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">�޸İ����Ϣ</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">����������</td>
		<td nowrap>
			<select name="categoryid">
				<c:set var="categorys" value="${requestScope.categorys}"/>
				<c:forEach var="categorysingle" items="${categorys}">
				
				<c:set var="categoryid" value="${categorysingle.key}"/>
				<c:set var="categoryname" value="${categorys[categoryid]}"/>
				
				<c:if test="${single.categoryId eq categoryid}">
				<option value="${categoryid}" selected>${categoryname}</option></c:if>
				
				<c:if test="${single.categoryId ne categoryid}">
				<option value="${categoryid}">${categoryname}</option></c:if>
				
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap>������ƣ�</td>
		<td><input type="text" name="boardname" size="67" value="${single.boardNameForEdit}" class="login" onkeydown="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="white">
		<td valign="top">���������<br>
		<font color="#7F7F7F">
       		��ࣺ<b><span id="ContentAll" style="width:40">50</span></b>
          	���ã�<b><span id="ContentUse" style="width:40">0</span></b>
           	ʣ�ࣺ<b><span id="ContentRem" style="width:40">50</span></b>
       	</font>
       	</td>
		<td nowrap><textarea name="boardinfo" rows="4" cols="50" onPropertyChange="checkLen(boardinfo)" style="border:1 solid">${single.boardInfo}</textarea></td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>Ŀǰ���״̬��</td>
		<td nowrap>
			<c:if test="${single.boardStatus eq '2'}">����</c:if>
			<c:if test="${single.boardStatus eq '1'}">����</c:if>
			<c:if test="${single.boardStatus eq '0'}">�ر�</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>���״̬��</td>
		<td nowrap>
			<select name="boardstatus">
				<option value="2">����</option>
				<option value="1">����</option>
				<option value="0">�ر�</option>				
			</select>
		</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>�����ţ�</td>
		<td nowrap><input type="text" name="boardorder" value="${single.boardOrder}" size="10" class="login"></td>
	</tr>	
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="�޸İ��" onclick="mymodifyboardsubmit()">
			<input type="reset" value="��������">
			<input type="reset" value="�����޸�" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>