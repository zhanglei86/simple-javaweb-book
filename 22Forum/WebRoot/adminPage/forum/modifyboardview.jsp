<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>修改版块信息</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminforum.js"></script>

<body onload="checkLen(adminform.boardinfo)">
<center>
<c:set var="single" value="${requestScope.boardsingle}"/>

<c:if test="${!empty single}">
<!-- 实现修改版块信息界面 -->
<form action="<%=contextPath%>/admin/board/c/modifyboardrun" method="post" name="adminform">
<input type="hidden" name="boardId" value="${single.id}">
<input type="hidden" name="categoryId" value="${single.categoryId}">
<b><span id="checkmessage" style="color:red"></span></b>
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">修改版块信息</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">版块所属类别：</td>
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
		<td nowrap>版块名称：</td>
		<td><input type="text" name="boardname" size="67" value="${single.boardNameForEdit}" class="login" onkeydown="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="white">
		<td valign="top">版块描述：<br>
		<font color="#7F7F7F">
       		最多：<b><span id="ContentAll" style="width:40">50</span></b>
          	已用：<b><span id="ContentUse" style="width:40">0</span></b>
           	剩余：<b><span id="ContentRem" style="width:40">50</span></b>
       	</font>
       	</td>
		<td nowrap><textarea name="boardinfo" rows="4" cols="50" onPropertyChange="checkLen(boardinfo)" style="border:1 solid">${single.boardInfo}</textarea></td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>目前版块状态：</td>
		<td nowrap>
			<c:if test="${single.boardStatus eq '2'}">开放</c:if>
			<c:if test="${single.boardStatus eq '1'}">锁定</c:if>
			<c:if test="${single.boardStatus eq '0'}">关闭</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>版块状态：</td>
		<td nowrap>
			<select name="boardstatus">
				<option value="2">开放</option>
				<option value="1">锁定</option>
				<option value="0">关闭</option>				
			</select>
		</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>版块序号：</td>
		<td nowrap><input type="text" name="boardorder" value="${single.boardOrder}" size="10" class="login"></td>
	</tr>	
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="修改版块" onclick="mymodifyboardsubmit()">
			<input type="reset" value="重新设置">
			<input type="reset" value="放弃修改" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>