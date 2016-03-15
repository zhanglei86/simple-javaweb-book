<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();System.out.println("ddddddddddd"); %>

<title>我的发信箱</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visituser.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body onload="changesubsizeshow('listmessages')">
	<center>
		<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
			<tr class="listhead" height="25">
				<td colspan="2">接收者</td>
				<td align="center">信息标题</td>
				<td align="center">发送日期</td>
			</tr>
			<tr bgcolor="lightgrey">
				<td colspan="4"><b>发信箱</b> <input type="checkbox" name="selectall" onclick="selectall()">全选</td>
			</tr>
			<c:if test="${empty requestScope.sendmessages}">
			<tr height="30" bgcolor="white"><td colspan="4" align="center">没有信息</td>
			</c:if>
			
			<c:if test="${!empty requestScope.sendmessages}">
			<form name="messageform" action="<%=contextPath%>/visit/myself/a/deleteselectmessages" method="post" target="_parent">
			<input type="hidden" name="from" value="send">
			<c:forEach varStatus="svs" var="single" items="${requestScope.sendmessages}">
			<c:if test="${svs.index%2==0}">
			<tr height="30" bgcolor="white"></c:if>
			<c:if test="${svs.index%2!=0}">
			<tr height="30" bgcolor="#F5F5F5"></c:if>
				<td align="center" width="4%"><input type="checkbox" id="select${svs.index}" name="selectmessages" value="${single.id}" onclick="oneclick('select${svs.index}')"></td>
				<td align="center" width="23%">${single.messageGetter}</td>
				<td width="55%">
					<img src="<%=contextPath%>/images/icon/unread.gif">
					<a href="<%=contextPath%>/visit/myself/a/viewmymessage?messageId=${single.id}&from=send" target="_parent">${single.messageTitle}</a></b>
				</td>
				<td align="center">${single.messageSendTime}</td>
			</tr>
			</c:forEach>
			<tr bgcolor="lightgrey"><td colspan="4"><input type="button" name="deletesubmitb" value="删除选定消息" onclick="mydeletemessagesubmit()"></td></tr>
			</form>
			</c:if>
		</table>
	</center>
</body>