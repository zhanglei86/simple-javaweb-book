<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>

<title>显示待审核帖子</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<body>
<center>
	<c:set var="unchecks" value="${requestScope.unchecktopics}"/>
<table border="0" width="100%" style="word-break:break-all" cellpadding="0" cellspacing="1" bgcolor="#8A8989">
	<tr height="25" class="listhead">
		<td colspan="2" style="text-indent:5;border:0">主题信息</td>
 		<td align="center" width="14%" >作者</td>
 		<td align="center"width="10%">类别</td>
 		<td align="center"width="10%">版块</td>
 		<td align="center" width="5%">回复</td>
  		<td align="center" width="5%">浏览</td>
  		<td align="center" width="14%">最后回复</td>
  	</tr>

	<c:if test="${empty unchecks}">
	<tr height="50" bgcolor="#F0F0F0" align="center"><td colspan="8"><b>没有待审核的帖子！</b></td></tr></c:if>
	
	<c:if test="${!empty unchecks}">
	<!-- 列表显示待审核的帖子 -->
  	<c:forEach var="single" varStatus="ucvs" items="${unchecks}">
 	 	<c:if test="${ucvs.index%2==0}">
		<tr bgcolor="#F0F0F0" height="35" align="center"></c:if>
		<c:if test="${ucvs.index%2!=0}">
		<tr bgcolor="white" height="35" align="center"></c:if>
			<td width="4%"><img src="<%=contextPath%>/images/icon/topic0.gif" style="border:0"></td>
			<td align="left" style="text-indent:5;border:0">
				<img src="<%=contextPath%>/images/emotion/${single.topic.topicEmotion}" style="border:0">
				<c:if test="${single.topic.topicType eq '1'}"><img src="<%=contextPath%>/images/icon/best.gif" style="border:0"/></c:if>
				<c:if test="${single.topic.topicAttachmentSign eq '1'}"><img src="<%=contextPath%>/images/icon/attach.gif" style="border:0"/></c:if>
				<a href="<%=contextPath %>/visit/topic/a/view?topicId=${single.topic.id}" target="_blank">${single.topic.topicTitle}</a>
			</td>
			<td>	
				<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${single.topic.topicAuthorName}" class="title" target="_blank">${single.topic.topicAuthorName}</a><br>
				${single.topic.topicPostTime}
			</td>
			<td><a href="<%=contextPath%>/visit/category/a/onelist?categoryId=${single.category['id']}" target="_blank">${single.category["name"]}</a></td>
			<td><a href="<%=contextPath%>/visit/board/a/listspecialtopic?boardId=${single.board['id']}" target="_blank">${single.board["name"]}</a></td>
			<td><b>${single.topic.topicReplyNum}</b></td>
			<td><b>${single.topic.topicHits}</b></td>
			<td>
				<c:if test="${!empty single.topic.lastReply}">
				<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${single.topic.lastReply.replyAuthorName}" class="title" target="_blank">${single.topic.lastReply.replyAuthorName}</a><br>
				${single.topic.lastReply.replyReplyTime}				
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</c:if>
	</table>
	</center>
</body>