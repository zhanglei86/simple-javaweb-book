<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>����»�Ա</title>
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
        document.all.editmessage.innerHTML="<font color='red'>����������� "+maxlen+" ���ַ���</font>";
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
	<tr class="listhead" height="25"><td colspan="2">����������ע����Ϣ �� "*�� Ϊ�������ݣ�</td></tr>
	<tr bgcolor="white" height="20">
		<td width="25%" align="center">��Ա����*</td>
		<td>
			<input type="text" name="name" value="${param.name}" class="login" onkeypress="checkmessage.innerHTML=''">
			<input type="button" name="checknamesubmit" value="���û�Ա����"  onclick="mycheckexistsubmit('<%=contextPath%>/admin/user/e/checkmember')">
			<b><span id="checknamemessage" style="color:red"></span></b>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">��Ա����*</td>
		<td><input type="password" name="pswd" class="login" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="#F5F5F5" height="20">
		<td align="center">ȷ��Ա����*</td>
		<td><input type="password" name="aginpswd" class="login" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="white" height="20">
		<td align="center">�Ա�</td>
		<td>
			<c:if test="${param.sex ne 'Ů'}">
			<input type="radio" name="sex" value="��" checked>��
			<input type="radio" name="sex" value="Ů">Ů
			</c:if>
			<c:if test="${param.sex eq 'Ů'}">
			<input type="radio" name="sex" value="��">��
			<input type="radio" name="sex" value="Ů" checked>Ů
			</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td align="center">����</td>
		<td><input type="text" name="age" value="${param.age}" class="login" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="white">
		<td align="center">QQ����</td>
		<td><input type="text" name="oicq" value="${param.oicq}" class="login" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="white">
		<td align="center">
			����ǩ����<br>
			<font color="#7F7F7F">
           		��ࣺ<b><span id="ContentAll" style="width:20">50</span></b>
               	���ã�<b><span id="ContentUse" style="width:20">0</span></b>
               	ʣ�ࣺ<b><span id="ContentRem" style="width:20">50</span></b>
           	</font><br>
           	<span id="editmessage"></span>
		</td>
		<td><textarea rows="4" cols="50" name="sign" onPropertyChange="checkLen()" onkeypress="checkmessage.innerHTML=''">${param.sign}</textarea></td>
	</tr>	
	<tr bgcolor="lightgrey">		
		<td colspan="2" align="center">
			<input type="button" name="regsubmitb" value="��ӻ�Ա" onclick="myaddusersubmit()">
			<input type="reset" value="������д">
		</td>
	</tr>
</table>
</form>
</body>