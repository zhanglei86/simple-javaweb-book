<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>��������</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminforum.js"></script>

<body>
<center>

<!-- ʵ����������� -->
<form action="<%=contextPath%>/admin/category/d/addcategoryrun" method="post" name="adminform">
<input type="hidden" name="categoryId" value="-1">
<b><span id="checkmessage" style="color:red"></span></b>
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">��������</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">������ƣ�</td>
		<td nowrap><input type="text" name="categoryName" size="50" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap>
			���������<br>
			<font color="#7F7F7F">
           		��ࣺ<b><span id="ContentAll" style="width:40">50</span></b><br>
               	���ã�<b><span id="ContentUse" style="width:40">0</span></b><br>
               	ʣ�ࣺ<b><span id="ContentRem" style="width:40">50</span></b>
           	</font>
		</td>
		<td><textarea name="categoryInfo" rows="5" cols="50" onpropertychange="checkLen(categoryInfo)"></textarea></td>
	</tr>	
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="������" onclick="myaddcategorysubmit()">
			<input type="reset" value="��������">
			<input type="reset" value="�������" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</center>
</body>