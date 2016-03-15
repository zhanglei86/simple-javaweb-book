<%@ page contentType="text/html;charset=gb2312"%>
<% String contextPath=request.getContextPath();%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<title>为主题添加附件</title>

<body>
	<center>
	<c:set var="atttopic" value="${requestScope.attachtopic}"/>
	
	<c:if test="${!empty atttopic}">
	<table border="0" width="100%" cellspacing="1" cellpadding="3" bgcolor="#999999" style="word-break:break-all">
		<tr class="listhead"><td colspan="2">为该主题添加附件！</td></tr>
		<tr bgcolor="white">
			<td width="25%">标题：</td>
			<td>${atttopic.topicTitle}</td>
		</tr>
		<tr bgcolor="#F5F5F5">
			<td valign="top">内容：</td>
			<td>${atttopic.topicContent}</td>
		</tr>
		<tr bgcolor="white">
			<td>作者：</td>
			<td>${atttopic.topicAuthorName}</td>
		</tr>
		<tr bgcolor="#F5F5F5">
			<td>发表时间：</td>
			<td>${atttopic.topicPostTime}</td>
		</tr>
		<tr bgcolor="white">
			<td valign="top">已有附件：</td>
			<td>
				<c:if test="${empty atttopic.topicAttachment}">
				没有附件！
				</c:if>					
				<c:if test="${!empty atttopic.topicAttachment}">
				<c:forEach var="attsingle" varStatus="avs" items="${atttopic.topicAttachment}">
				附件${avs.count}:
				<img src="<%=contextPath%>/images/icon/attach.gif">
				<a href="">${attsingle.attachmentFileName}</a>
				(大小：${attsingle.attachmentFileSize} 字节)<br>
				</c:forEach>
				</c:if>
			</td>
		</tr>
	</table>	
	</c:if>

	<a name="uploadtop"></a>
	<b><span id="message" style="color:red"></span></b>
	<iframe id="addattachmentview" src="<%=contextPath%>/visitPage/attachment/addattachmentview.jsp?topicId=${atttopic.id}" width="100%" height="0" frameborder="0" scrolling="no"></iframe>
	</center>
</body>