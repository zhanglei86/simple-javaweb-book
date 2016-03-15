<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath(); %>
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>
<title>��̳-�鿴�������</title>

<c:set var="categorys" value="${requestScope.categorylist}"/>
<c:if test="${!empty categorys}">
<table border="0" width="100%" style="margin-top:5;word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr height="30" class="listhead">
		<td colspan="2">�������/����</td>
		<td align="center" width="7%">������</td>
		<td align="center" width="7%">������</td>
		<td align="center">���·���</td>
	</tr>
	
	<c:forEach var="categorySingle" varStatus="cvs" items="${categorys}">
	<tr height="40" class="listsubhead">
		<td colspan="5" style="padding-left:3">
			<img id="img${cvs.index}" src="<%=contextPath%>/images/icon/close.gif" style="border:0;cursor:hand" onclick="showorhiden('trs${cvs.index}','img${cvs.index}','<%=contextPath%>')">
			<b>${categorySingle.categoryName}</b><br>
			${categorySingle.categoryInfo}<br>
			����:
			<c:set var="masters" value="${categorySingle.masters}"/>
			<c:if test="${empty masters}">û������</c:if>
			<c:if test="${!empty masters}">
			<c:forEach var="master" items="${masters}">
			<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${master}" target="_blank">${master}</a>&nbsp;&nbsp;
			</c:forEach>
			</c:if>
		</td>
	</tr>
	
	<tbody id="trs${cvs.index}">
	<c:forEach var="boardSingle" varStatus="bvs" items="${requestScope.allboards[categorySingle.id]}">
	<c:if test="${bvs.index%2==0}">
	<tr bgcolor="white" class="listbody"></c:if>
	<c:if test="${bvs.index%2!=0}">
	<tr bgcolor="#F0F0F0" class="listbody"></c:if>
		<td align="center" width="4%"><img src="<%=contextPath%>/images/icon/board${boardSingle.boardStatus}.gif"></td>
		<td width="53%" style="padding-left:5">
			<a href="<%=contextPath%>/visit/board/a/listspecialtopic?boardId=${boardSingle.id}" target="_blank">${boardSingle.boardName}</a><br>
			<font color="gray">${boardSingle.boardInfo}</font>
		</td>
		<td align="center"><b>${boardSingle.boardAllTopicNum}</b></td>
		<td align="center"><b>${boardSingle.boardBestTopicNum}</b></td>
		<td style="padding-left:5">			
			<img src="<%=contextPath%>/images/icon/title.gif" title="���ӱ���">&nbsp;&nbsp;<a href="<%=contextPath%>/visit/topic/a/view?topicId=${boardSingle.lastTopic.id}" class="title" target="_blank">${boardSingle.lastTopic.topicTitle}</a><br>
			<img src="<%=contextPath%>/images/icon/author.gif" title="��������">&nbsp;&nbsp;<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${boardSingle.lastTopic.topicAuthorName}" class="title" target="_blank">${boardSingle.lastTopic.topicAuthorName}</a><br>
			<img src="<%=contextPath%>/images/icon/time.gif" title="����ʱ��">&nbsp;&nbsp;${boardSingle.lastTopic.topicPostTime}
		</td>
	</tr>
	</c:forEach>
	</tbody>
		
	</c:forEach>
</table>

<table border="0" width="100%" style="margin-top:5">
	<tr>
		<td width="30"><img src="<%=contextPath%>/images/icon/board2.gif" alt="���Ű��"></td>
		<td>���л�Ա���ڸð����������ӡ��������⡢�ظ������Լ����Լ������������в�����</td>
	</tr>
	<tr>
		<td width="20"><img src="<%=contextPath%>/images/icon/board1.gif" alt="�������"></td>
		<td>���л�Աֻ������ð���е����ӣ�</td>	
	</tr>
</table>	
</c:if>