<%@ page contentType="text/html;charset=gb2312"%>
<% String contextPath=request.getContextPath();%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<title>Ϊ������Ӹ���</title>

<body>
	<center>
	<c:set var="atttopic" value="${requestScope.attachtopic}"/>
	
	<c:if test="${!empty atttopic}">
	<table border="0" width="100%" cellspacing="1" cellpadding="3" bgcolor="#999999" style="word-break:break-all">
		<tr class="listhead"><td colspan="2">Ϊ��������Ӹ�����</td></tr>
		<tr bgcolor="white">
			<td width="25%">���⣺</td>
			<td>${atttopic.topicTitle}</td>
		</tr>
		<tr bgcolor="#F5F5F5">
			<td valign="top">���ݣ�</td>
			<td>${atttopic.topicContent}</td>
		</tr>
		<tr bgcolor="white">
			<td>���ߣ�</td>
			<td>${atttopic.topicAuthorName}</td>
		</tr>
		<tr bgcolor="#F5F5F5">
			<td>����ʱ�䣺</td>
			<td>${atttopic.topicPostTime}</td>
		</tr>
		<tr bgcolor="white">
			<td valign="top">���и�����</td>
			<td>
				<c:if test="${empty atttopic.topicAttachment}">
				û�и�����
				</c:if>					
				<c:if test="${!empty atttopic.topicAttachment}">
				<c:forEach var="attsingle" varStatus="avs" items="${atttopic.topicAttachment}">
				����${avs.count}:
				<img src="<%=contextPath%>/images/icon/attach.gif">
				<a href="">${attsingle.attachmentFileName}</a>
				(��С��${attsingle.attachmentFileSize} �ֽ�)<br>
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