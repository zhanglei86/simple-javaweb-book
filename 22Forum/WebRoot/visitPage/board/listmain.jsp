<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath(); %>
<title>��̳-��ʾ�����б�</title>

<c:set var="board" value="${requestScope.boardsingle}"/>
<table border="0" width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="bottom">
			����:
			<c:set var="masters" value="${board.masters}"/>
			<c:if test="${empty masters}">û�а���</c:if>
			<c:if test="${!empty masters}">
			<c:forEach var="master" items="${masters}">
			<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${master}" target="_blank">${master}</a>&nbsp;&nbsp;
			</c:forEach>
			</c:if>
		</td>
		<td align="right" valign="bottom">
			<a href="<%=contextPath%>/visit/placard/c/postplacardview?boardId=${param.boardId}" target="_blank">��������</a> |&nbsp;
			���״̬��
			<font color="red"><b>
			<c:if test="${board.boardStatus eq '2'}">����</c:if>
			<c:if test="${board.boardStatus eq '1'}">����</c:if>
			<c:if test="${board.boardStatus eq '0'}">�ر�</c:if>
			</b></font>|&nbsp;
			�ð�鹲�У�<b><font color="red">${board.boardAllTopicNum}</font></b>���� |&nbsp;
			��������<b><font color="red">${board.boardBestTopicNum}</font></b>����
			<c:if test="${requestScope.visitboardstatus eq '2'}">
			<a href="<%=contextPath%>/visit/board/a/postview?boardId=${param.boardId}" target="_blank" title="��������"><img src="<%=contextPath%>/images/icon/topicadd.gif" style="border:0"></a>
			</c:if>
			<c:if test="${requestScope.visitboardstatus ne '2'}">
			��<font color="red">��ǰ��鱻������رգ����ܷ������ӣ�</font>��
			</c:if>
		</td>
	</tr>
</table>

<!-- ��ʾ���漰�ö����� -->
<c:set var="placards" value="${requestScope.placardlist}"/>
<c:set var="tops" value="${requestScope.topTopicList}"/>

<c:if test="${(!empty placards)||(!empty tops)}">
<table border="0" width="100%" style="margin-top:5;word-break:break-all" cellpadding="0" cellspacing="1" bgcolor="#8A8989">
  	<!-- ��ʾ���� -->
  	<c:if test="${!empty placards}">
  	<tr height="25" class="listhead">
  		<td style="text-indent:5" colspan="5">������Ϣ</td>
  	</tr>
  	<c:forEach var="single" varStatus="svs" items="${placards}">
	<c:if test="${svs.index%2==0}">
	<tr bgcolor="#F0F0F0" height="30"></c:if>
	<c:if test="${svs.index%2!=0}">
	<tr bgcolor="white" height="30"></c:if>
		<td nowrap colspan="5">
			<b><font color="red">�����桿</font></b>
			<a href="<%=contextPath%>/visit/placard/a/view?placardId=${single.id}&boardId=${param.boardId}" target="_blank">${single.placardTitle}</a>
			<font color="gray">[${single.placardPostTime}]</font>
		</td>
	</tr>
	</c:forEach>
	</c:if>
	<!-- ��ʾ�ö� -->
  	<c:if test="${!empty tops}">
  	<tr height="25" class="listhead">
  		<td style="text-indent:5;border:0">�ö�����</td>
  		<td align="center" width="17%" style="border:0">¥��</td>
  		<td align="center" width="7%" style="border:0">�ظ�</td>
  		<td align="center" width="7%" style="border:0">���</td>
  		<td align="center" width="17%" style="border:0">���ظ�</td>
  	</tr>
  	<c:forEach var="single" varStatus="tvs" items="${tops}">
  	<c:if test="${tvs.index%2==0}">
	<tr bgcolor="#F0F0F0" height="35" align="center"></c:if>
	<c:if test="${tvs.index%2!=0}">
	<tr bgcolor="white" height="35" align="center"></c:if>
		<td align="left">
			<b><font color="red">���ö���</font></b>
			<img src="<%=contextPath%>/images/emotion/${single.topicEmotion}" style="border:0">
			<a href="<%=contextPath %>/visit/topic/a/view?topicId=${single.id}" target="_blank">${single.topicTitle}</a>
		</td>
		<td>
			<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${single.topicAuthorName}" class="title" target="_blank">${single.topicAuthorName}</a><br>
			${single.topicPostTime}
		</td>
		<td><b>${single.topicReplyNum}</b></td>
		<td><b>${single.topicHits}</b></td>
		<td>
			<c:if test="${!empty single.lastReply}">
			<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${single.lastReply.replyAuthorName}" class="title" target="_blank">${single.lastReply.replyAuthorName}</a><br>
			${single.lastReply.replyReplyTime}
			</c:if>
		</td>
	</tr>
	</c:forEach>
  	</c:if>
</table>  	
</c:if>
<!-- ͨ��iframe�����ʾ�����б� -->
<iframe id="tolistcommontopic" src="<%=contextPath%>/visit/board/a/listcommontopic?boardId=${param.boardId}" width="100%" frameborder="0" scrolling="no"></iframe>