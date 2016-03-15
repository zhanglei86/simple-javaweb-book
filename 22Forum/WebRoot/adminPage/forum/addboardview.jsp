<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>添加新版块</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminforum.js"></script>

<body>
<center>

<c:set var="single" value="${requestScope.categorysingle}"/>
<c:if test="${!empty single}">
<!-- 实现添加版块界面 -->
<form action="<%=contextPath%>/admin/category/d/addboardrun" method="post" name="adminform">
<input type="hidden" name="categoryId" value="${single.id}">
<b><span id="checkmessage" style="color:red"></span></b>
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">添加新版块</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">版块所属类别：</td>
		<td nowrap>${single.categoryName}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap>版块名称：</td>
		<td><input type="text" name="boardname" size="51" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="white">
		<td nowrap valign="top">
			版块描述：<br>
			<font color="#7F7F7F">
           		最多：<b><span id="ContentAll" style="width:40">50</span></b><br>
               	已用：<b><span id="ContentUse" style="width:40">0</span></b><br>
               	剩余：<b><span id="ContentRem" style="width:40">50</span></b>
           	</font>
		</td>
		<td nowrap><textarea name="boardinfo" rows="5" cols="50" onPropertyChange="checkLen(boardinfo)"></textarea></td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>版块状态：</td>
		<td nowrap>
			<select name="boardstatus">
				<option value="2" selected>开放</option>
				<option value="1">锁定</option>
				<option value="0">关闭</option>				
			</select>
		</td>
	</tr>	
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="添加版块" onclick="myaddboardsubmit()">
			<input type="reset" value="重新设置">
			<input type="reset" value="放弃添加" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>