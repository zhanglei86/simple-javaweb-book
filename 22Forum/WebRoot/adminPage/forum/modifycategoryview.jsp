<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>修改类别信息</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminforum.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.categorysingle}"/>

<c:if test="${!empty single}">
<!-- 实现修改类别信息界面 -->
<form action="<%=contextPath%>/admin/category/d/modifycategoryrun" method="post" name="adminform">
<input type="hidden" name="categoryId" value="${single.id}">
<b><span id="checkmessage" style="color:red"></span></b>
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">修改类别信息</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">类别名称：</td>
		<td nowrap><input type="text" size="67" name="categoryname" value="${single.categoryNameForEdit}" class="login"></td>
	</tr>		
	<tr bgcolor="white">
		<td nowrap valign="top">
			类别描述：<br>
			<font color="#7F7F7F">
       		最多：<b><span id="ContentAll" style="width:40">50</span></b>
          	已用：<b><span id="ContentUse" style="width:40">0</span></b>
           	剩余：<b><span id="ContentRem" style="width:40">50</span>
	       	</font>
		</td>
		<td nowrap><textarea name="categoryinfo" rows="4" cols="50" onpropertychange="checkLen(categoryinfo)">${single.categoryInfoForEdit}</textarea></td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>类别序号：</td>
		<td nowrap><input type="text" name="categoryorder" value="${single.categoryOrder}" class="login"></td>
	</tr>	
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="修改类别" onclick="mymodifycategorysubmit()">
			<input type="reset" value="重新设置">
			<input type="reset" value="放弃修改" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>