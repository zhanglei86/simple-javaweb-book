<%@ page contentType="text/html;charset=gb2312"%>
<% String contextPath=request.getContextPath();%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<title>Ϊ�ظ�����Ӹ���</title>

<body>
	<center>
	<c:set var="attreply" value="${requestScope.attachreply}"/>
	<c:if test="${!empty attreply}">
	<table border="0" width="100%" cellspacing="1" cellpadding="3" bgcolor="#999999" style="word-break:break-all">
		<tr class="listhead"><td colspan="2">Ϊ�ûظ�����Ӹ�����</td></tr>
		<tr bgcolor="white">
			<td width="25%">���⣺</td>
			<td>${attreply.replyTitle}</td>
		</tr>
		<tr bgcolor="#F5F5F5">
			<td valign="top">���ݣ�</td>
			<td>${attreply.replyContentForJsp}</td>
		</tr>
		<tr bgcolor="white">
			<td>���ߣ�</td>
			<td>${attreply.replyAuthorName}</td>
		</tr>
		<tr bgcolor="#F5F5F5">
			<td>�ظ�ʱ�䣺</td>
			<td>${attreply.replyReplyTime}</td>
		</tr>
		<tr bgcolor="white">
			<td valign="top">���и�����</td>
			<td>
				<c:if test="${empty attreply.replyAttachment}">��</c:if>					
				<c:if test="${!empty attreply.replyAttachment}">
				<c:forEach var="attsingle" varStatus="avs" items="${attreply.replyAttachment}">
				����${avs.count}:<img src="<%=contextPath%>/images/icon/attach.gif">
				<a href="">${attsingle.attachmentFileName}</a>
				(��С��${attsingle.attachmentFileSize} �ֽ�)<br>
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