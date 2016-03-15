<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<% String contextPath=request.getContextPath(); %>
<title>论坛-显示主题列表</title>

<c:set var="board" value="${requestScope.boardsingle}"/>
<table border="0" width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="bottom">
			版主:
			<c:set var="masters" value="${board.masters}"/>
			<c:if test="${empty masters}">没有版主</c:if>
			<c:if test="${!empty masters}">
			<c:forEach var="master" items="${masters}">
			<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${master}" target="_blank">${master}</a>&nbsp;&nbsp;
			</c:forEach>
			</c:if>
		</td>
		<td align="right" valign="bottom">
			<a href="<%=contextPath%>/visit/placard/c/postplacardview?boardId=${param.boardId}" target="_blank">发布公告</a> |&nbsp;
			版块状态：
			<font color="red"><b>
			<c:if test="${board.boardStatus eq '2'}">开放</c:if>
			<c:if test="${board.boardStatus eq '1'}">锁定</c:if>
			<c:if test="${board.boardStatus eq '0'}">关闭</c:if>
			</b></font>|&nbsp;
			该版块共有（<b><font color="red">${board.boardAllTopicNum}</font></b>）帖 |&nbsp;
			精华帖（<b><font color="red">${board.boardBestTopicNum}</font></b>）帖
			<c:if test="${requestScope.visitboardstatus eq '2'}">
			<a href="<%=contextPath%>/visit/board/a/postview?boardId=${param.boardId}" target="_blank" title="发表帖子"><img src="<%=contextPath%>/images/icon/topicadd.gif" style="border:0"></a>
			</c:if>
			<c:if test="${requestScope.visitboardstatus ne '2'}">
			【<font color="red">当前版块被锁定或关闭，不能发表帖子！</font>】
			</c:if>
		</td>
	</tr>
</table>

<!-- 显示公告及置顶帖子 -->
<c:set var="placards" value="${requestScope.placardlist}"/>
<c:set var="tops" value="${requestScope.topTopicList}"/>

<c:if test="${(!empty placards)||(!empty tops)}">
<table border="0" width="100%" style="margin-top:5;word-break:break-all" cellpadding="0" cellspacing="1" bgcolor="#8A8989">
  	<!-- 显示公告 -->
  	<c:if test="${!empty placards}">
  	<tr height="25" class="listhead">
  		<td style="text-indent:5" colspan="5">公告信息</td>
  	</tr>
  	<c:forEach var="single" varStatus="svs" items="${placards}">
	<c:if test="${svs.index%2==0}">
	<tr bgcolor="#F0F0F0" height="30"></c:if>
	<c:if test="${svs.index%2!=0}">
	<tr bgcolor="white" height="30"></c:if>
		<td nowrap colspan="5">
			<b><font color="red">【公告】</font></b>
			<a href="<%=contextPath%>/visit/placard/a/view?placardId=${single.id}&boardId=${param.boardId}" target="_blank">${single.placardTitle}</a>
			<font color="gray">[${single.placardPostTime}]</font>
		</td>
	</tr>
	</c:forEach>
	</c:if>
	<!-- 显示置顶 -->
  	<c:if test="${!empty tops}">
  	<tr height="25" class="listhead">
  		<td style="text-indent:5;border:0">置顶主题</td>
  		<td align="center" width="17%" style="border:0">楼主</td>
  		<td align="center" width="7%" style="border:0">回复</td>
  		<td align="center" width="7%" style="border:0">浏览</td>
  		<td align="center" width="17%" style="border:0">最后回复</td>
  	</tr>
  	<c:forEach var="single" varStatus="tvs" items="${tops}">
  	<c:if test="${tvs.index%2==0}">
	<tr bgcolor="#F0F0F0" height="35" align="center"></c:if>
	<c:if test="${tvs.index%2!=0}">
	<tr bgcolor="white" height="35" align="center"></c:if>
		<td align="left">
			<b><font color="red">【置顶】</font></b>
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
<!-- 通过iframe框架显示主题列表 -->
<iframe id="tolistcommontopic" src="<%=contextPath%>/visit/board/a/listcommontopic?boardId=${param.boardId}" width="100%" frameborder="0" scrolling="no"></iframe>