<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath();%>
<title>更换头像</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript">
<!--
function editmyiconsubmit(){
	editmyiconform.editiconsubmitb.disabled=true;
	editmyiconform.submit();
}
function showHead(icon,webname){
	document.images['head'].src=webname+"/images/user/"+icon;
}
//-->
</script>
<body>
<c:set var="my" value="${sessionScope.loginer}"/>

<form action="<%=contextPath%>/visit/myself/a/editmyiconrun" name="editmyiconform" method="post">
<input type="hidden" name="memberId" value="${my.id}">
<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr class="listhead"><td colspan="2">从下列图片中选择你的新头像：</td>	</tr>
	<tr bgcolor="#F5F5F5">
		<td width="15%" align="center"><img src="<%=contextPath%>/images/user/${my.memberIcon}" id="head"></td>
		<td>
			<jsp:include page="usericon.jsp?oldicon=${my.memberIcon}"/>
			<input type="button" value="更换头像" name="editiconsubmitb" onclick="editmyiconsubmit()">
			<input type="reset" value="放弃更换" onclick="javascript:window.history.go(-1)">
		</td>		
	</tr>	
</table>
</form>
</body>