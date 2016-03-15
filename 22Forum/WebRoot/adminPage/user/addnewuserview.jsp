<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>添加新会员</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adduser.js"></script>
<script type="text/javascript">
<!--
function mysubmit(){
	editmysignform.editmysignsubmitb.disabled=true;
	editmysignform.submit();
}
function checkLen(){
	var fieldName=document.all.sign;
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
<body onload="checkLen();getcheckexistresult('${requestScope.disabled}')">
<form action="<%=contextPath%>/admin/user/c/addnewuserrun" name="regform" method="post">
<b><span id="checkmessage" style="color:red"></span></b>
<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead" height="25"><td colspan="2">请输入下列注册信息 带 "*＂ 为必填内容！</td></tr>
	<tr bgcolor="white" height="20">
		<td width="25%" align="center">会员名称*</td>
		<td>
			<input type="text" name="name" value="${param.name}" class="login" onkeypress="checkmessage.innerHTML=''">
			<input type="button" name="checknamesubmit" value="检查该会员名称"  onclick="mycheckexistsubmit('<%=contextPath%>/admin/user/e/checkmember')">
			<b><span id="checknamemessage" style="color:red"></span></b>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">会员密码*</td>
		<td><input type="password" name="pswd" class="login" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">确认员密码*</td>
		<td><input type="password" name="aginpswd" class="login" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="white" height="20">
		<td align="center">性别</td>
		<td>
			<c:if test="${param.sex ne '女'}">
			<input type="radio" name="sex" value="男" checked>男
			<input type="radio" name="sex" value="女">女
			</c:if>
			<c:if test="${param.sex eq '女'}">
			<input type="radio" name="sex" value="男">男
			<input type="radio" name="sex" value="女" checked>女
			</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">年龄</td>
		<td><input type="text" name="age" value="${param.age}" class="login" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="white">
		<td align="center">QQ号码</td>
		<td><input type="text" name="oicq" value="${param.oicq}" class="login" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="white">
		<td align="center">
			个性签名：<br>
			<font color="#7F7F7F">
           		最多：<b><span id="ContentAll" style="width:20">50</span></b>
               	已用：<b><span id="ContentUse" style="width:20">0</span></b>
               	剩余：<b><span id="ContentRem" style="width:20">50</span></b>
           	</font><br>
           	<span id="editmessage"></span>
		</td>
		<td><textarea rows="4" cols="50" name="sign" onPropertyChange="checkLen()" onkeypress="checkmessage.innerHTML=''">${param.sign}</textarea></td>
	</tr>	
	<tr bgcolor="lightgrey">		
		<td colspan="2" align="center">
			<input type="button" name="regsubmitb" value="添加会员" onclick="myaddusersubmit()">
			<input type="reset" value="重新填写">
		</td>
	</tr>
</table>
</form>
</body>