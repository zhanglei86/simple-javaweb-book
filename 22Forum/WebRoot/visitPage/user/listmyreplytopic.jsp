<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>

<title>�һظ�������</title>
<body>
	<center>
	<c:set var="topics" value="${requestScope.myreplytopics}"/>
	<table border="0" width="100%" style="word-break:break-all" cellpadding="0" cellspacing="1" bgcolor="#8A8989">
		<tr height="25" class="listhead">
  			<td colspan="3" style="text-indent:5;border:0">������Ϣ</td>
  			<td align="center" width="15%" style="border:0">¥��</td>
  			<td align="center" width="7%" style="border:0">�ظ�</td>
  			<td align="center" width="7%" style="border:0">���</td>
  			<td align="center" width="17%" style="border:0">���ظ�</td>
  		</tr>
		<c:if test="${empty topics}">
		<tr height="50" bgcolor="#F0F0F0" align="center"><td colspan="7"><b>û�����ӣ�</b></td></tr>
		</c:if>
		<c:if test="${!empty topics}">
		<!-- �б���ʾ���� -->
  		<c:forEach var="single" varStatus="tvs" items="${topics}">
 	 	<c:if test="${tvs.index%2==0}">
		<tr bgcolor="#F0F0F0" height="35" align="center"></c:if>
		<c:if test="${tvs.index%2!=0}">
		<tr bgcolor="white" height="35" align="center"></c:if>
			<td width="4%"><img src="<%=contextPath%>/images/icon/topic${single.topicStatus}.gif" style="border:0"></td>
			<td width="3%"><img src="<%=contextPath%>/images/emotion/${single.topicEmotion}" style="border:0"></td>
			<td align="left" style="text-indent:5;border:0">
				<c:if test="${single.topicType eq '1'}"><img src="<%=contextPath%>/images/icon/best.gif" style="border:0"/></c:if>
				<c:if test="${single.topicAttachmentSign eq '1'}"><img src="<%=contextPath%>/images/icon/attach.gif" style="border:0"/></c:if>
				<a href="<%=contextPath %>/visit/topic/a/view?topicId=${single.id}" target="_blank">${single.topicTitle}</a>
			</td>
			<td>	
	
<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${single.topicAuthorName}" target="_blank" class="title">${single.topicAuthorName}</a><br>
				${single.topicPostTime}me}
			</td>
			<td><b>${single.topicReplyNum}</b></td>
			<td><b>${single.topicHits}</b></td>
			<td>
				<c:if test="${!empty single.lastReply}">
				<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${single.lastReply.replyAuthorName}" target="_blank" class="title">${single.lastReply.replyAuthorName}</a><br>
				${single.topicPostTime}
				</c:if>
			</td>
		</tr>
		</c:forEach>
		</c:if>
	</table>
	
	<c:if test="${!empty topics}">
	<!-- ��ʾ��ҳ���� -->
	<table border="0" width="100%" cellspacing="0" cellpadding="0" style="margin-top:1;margin-bottom:1">
		<tr><td><jsp:include page="/pageBar.jsp"/></td></tr>
	</table>
	</c:if>	
	</center>
</body>