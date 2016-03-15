<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>更新个性签名</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
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
<body onload="checkLen()">
<c:set var="my" value="${sessionScope.loginer}"/>

<form action="<%=contextPath%>/visit/myself/a/editmysignrun" name="editmysignform" method="post">
<input type="hidden" name="memberId" value="${my.id}">
<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead"><td colspan="2">请输入新的个性签名：</td>	</tr>
	<tr bgcolor="#F5F5F5">
		<td width="30%" valign="top">
			新的签名：<br>
			<font color="#7F7F7F">
           		最多：<b><span id="ContentAll" style="width:40">50</span></b><br>
               	已用：<b><span id="ContentUse" style="width:40">0</span></b><br>
               	剩余：<b><span id="ContentRem" style="width:40">50</span></b>
           	</font><br>
           	<span id="editmessage"></span>
		</td>
		<td>
			<textarea rows="6" cols="50" name="sign" onPropertyChange="checkLen()">${my.memberSignForEdit}</textarea>
			<input type="button" name="editmysignsubmitb" value="更新签名" onclick="mysubmit()">
			<input type="reset" value="放弃修改" onclick="javascript:window.history.go(-1)">
		</td>		
	</tr>	
</table>
</form>
</body>