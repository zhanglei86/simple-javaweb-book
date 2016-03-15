<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>修改用户组信息</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/admingroup.js"></script>
<script type="text/javascript">
<!--
function checkLen(){
	var fieldName=document.all.groupInfo;
	var maxlen=document.all.ContentAll.innerText;
	var useName=document.all.ContentUse;
	var remName=document.all.ContentRem;
	var inlen=fieldName.value.length;
    
    if(inlen>maxlen){
        fieldName.value=(fieldName.value).substring(0,maxlen);
        document.all.editmessage.innerHTML="<font color='red'>最多允许输入 "+maxlen+" 个字符！</font>";
        return false;
    }
    else{
        useName.innerText=eval(fieldName.value.length);
        remName.innerText=maxlen-useName.innerText;
        return true;
    }
}
//-->
</script>

<body onload="checkLen()">
<center>
<c:set var="single" value="${requestScope.groupsingle}"/>

<c:if test="${!empty single}">
<!-- 实现修改用户组信息界面 -->
<form action="<%=contextPath%>/admin/group/e/modifygrouprun" method="post" name="adminform">
<input type="hidden" name="groupId" value="${single.groupId}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">修改用户组信息</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">用户组名称：</td>
		<td nowrap>${single.groupName}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap valign="top">
			用户组描述：<br>
			<font color="#7F7F7F">
           		最多：<b><span id="ContentAll" style="width:20">50</span></b>
               	已用：<b><span id="ContentUse" style="width:20">0</span></b>
               	剩余：<b><span id="ContentRem" style="width:20">50</span></b>
           	</font><br>
           	<span id="editmessage"></span>
		</td>
		<td>
			<textarea name="groupInfo" rows="4" cols="50" onPropertyChange="checkLen()">${single.groupInfo}</textarea>
			<b><font color="red"><span id="checkmessage"></span></font></b>
		</td>
	</tr>	
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="修改" onclick="mymodifygroupinfosubmit()">
			<input type="reset" value="重置">
			<input type="reset" value="放弃" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>