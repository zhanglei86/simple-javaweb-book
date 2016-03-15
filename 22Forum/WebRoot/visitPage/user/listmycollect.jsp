<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>

<title>�ҵ��ղؼ�</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<center>
	<c:set var="collects" value="${requestScope.collectlist}"/>
<table border="0" width="100%" style="margin-bottom:0" cellpadding="3" cellspacing="0" >
	<tr>
		<td align="right" valign="bottom">
			<c:if test="${!empty collects}">
			<a href="<%=contextPath%>/visit/myself/a/clearmycollect">����ղؼ�</a>
			</c:if>
			�ղ�:��<b>${requestScope.num}</b>�� ��
			����:��<b>${requestScope.max}</b>�� ��
			&nbsp;
		</td>
		<td bgcolor="#717171" width="400">
			<table width="100%" cellspacing="1" cellpadding="0">
				<tr><td bgcolor="#F0F0F0"><img src="<%=contextPath%>/images/icon/collect.gif" width="${(requestScope.num/requestScope.max)*400}" height="20" style="border:1 solid;border-color:lightgrey"></td></tr>
				<tr height="5"><td></td></tr>
			</table>			
		</td>
	</tr>	
</table>
<table border="0" width="100%" style="word-break:break-all" cellpadding="0" cellspacing="1" bgcolor="#8A8989">
	<tr height="25" class="listhead">
		<td colspan="2" style="text-indent:5;border:0">������Ϣ</td>
 		<td align="center" width="14%" >����</td>
 		<td align="center"width="10%">���</td>
 		<td align="center"width="10%">���</td>
 		<td align="center" width="5%">�ظ�</td>
  		<td align="center" width="5%">���</td>
  		<td align="center" width="14%">���ظ�</td>
  		<td align="center" width="5%">ɾ��</td>
  	</tr>

	<c:if test="${empty collects}">
	<tr height="50" bgcolor="#F0F0F0" align="center"><td colspan="9"><b>û���ղص����ӣ�</b></td></tr></c:if>
	
	<c:if test="${!empty collects}">
	<!-- �б���ʾ�ղص����� -->
  	<c:forEach var="single" varStatus="clvs" items="${collects}">
 	 	<c:if test="${clvs.index%2==0}">
		<tr bgcolor="#F0F0F0" height="35" align="center"></c:if>
		<c:if test="${clvs.index%2!=0}">
		<tr bgcolor="white" height="35" align="center"></c:if>
			<td width="4%"><img src="<%=contextPath%>/images/icon/topic${single.collectTopic.topicStatus}.gif" style="border:0"></td>
			<td align="left" style="text-indent:5;border:0">
				<img src="<%=contextPath%>/images/emotion/${single.collectTopic.topicEmotion}" style="border:0">
				<c:if test="${single.collectTopic.topicType eq '1'}"><img src="<%=contextPath%>/images/icon/best.gif" style="border:0"/></c:if>
				<c:if test="${single.collectTopic.topicAttachmentSign eq '1'}"><img src="<%=contextPath%>/images/icon/attach.gif" style="border:0"/></c:if>
				<a href="<%=contextPath %>/visit/topic/a/view?topicId=${single.collectTopic.id}" target="_blank">${single.collectTopic.topicTitle}</a>
			</td>
			<td>	
				<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${single.collectTopic.topicAuthorName}" class="title" target="_blank">${single.collectTopic.topicAuthorName}</a><br>
				${single.collectTopic.topicPostTime}
			</td>
			<td><a href="<%=contextPath%>/visit/category/a/onelist?categoryId=${single.category['id']}" target="_blank">${single.category["name"]}</a></td>
			<td><a href="<%=contextPath%>/visit/board/a/listspecialtopic?boardId=${single.board['id']}" target="_blank">${single.board["name"]}</a></td>
			<td><b>${single.collectTopic.topicReplyNum}</b></td>
			<td><b>${single.collectTopic.topicHits}</b></td>
			<td>
				<c:if test="${!empty single.collectTopic.lastReply}">
				<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${single.collectTopic.lastReply.replyAuthorName}" class="title" target="_blank">${single.collectTopic.lastReply.replyAuthorName}</a><br>
				${single.collectTopic.lastReply.replyReplyTime}				
				</c:if>
			</td>
			<td><a href="<%=contextPath%>/visit/myself/a/deletemycollect?topicId=${single.collectTopic.id}">ɾ��</a></td>
		</tr>
		</c:forEach>
	</c:if>
	</table>
	</center>
</body>