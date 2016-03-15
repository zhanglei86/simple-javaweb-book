<%@ page contentType="text/html;charset=gb2312"%>
<% String contextPath=request.getContextPath();%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<title>为回复帖添加附件</title>

<body>
	<center>
	<c:set var="attreply" value="${requestScope.attachreply}"/>
	<c:if test="${!empty attreply}">
	<table border="0" width="100%" cellspacing="1" cellpadding="3" bgcolor="#999999" style="word-break:break-all">
		<tr class="listhead"><td colspan="2">为该回复帖添加附件！</td></tr>
		<tr bgcolor="white">
			<td width="25%">标题：</td>
			<td>${attreply.replyTitle}</td>
		</tr>
		<tr bgcolor="#F5F5F5">
			<td valign="top">内容：</td>
			<td>${attreply.replyContentForJsp}</td>
		</tr>
		<tr bgcolor="white">
			<td>作者：</td>
			<td>${attreply.replyAuthorName}</td>
		</tr>
		<tr bgcolor="#F5F5F5">
			<td>回复时间：</td>
			<td>${attreply.replyReplyTime}</td>
		</tr>
		<tr bgcolor="white">
			<td valign="top">已有附件：</td>
			<td>
				<c:if test="${empty attreply.replyAttachment}">无</c:if>					
				<c:if test="${!empty attreply.replyAttachment}">
				<c:forEach var="attsingle" varStatus="avs" items="${attreply.replyAttachment}">
				附件${avs.count}:<img src="<%=contextPath%>/images/icon/attach.gif">
				<a href="">${attsingle.attachmentFileName}</a>
				(大小：${attsingle.attachmentFileSize} 字节)<br>
				</c:forEach>
				</c:if>
			</td>
		</tr>
	</table>	
	</c:if>
	<a name="uploadtop"></a>
	<span id="message"></span>
	<iframe id="addattachmentview" src="<%=contextPath%>/visitPage/attachment/addattachmentview.jsp?topicId=${attreply.topicId}&replyId=${attreply.id}" width="100%" height="0" frameborder="0" scrolling="no"></iframe>
	</center>
</body>