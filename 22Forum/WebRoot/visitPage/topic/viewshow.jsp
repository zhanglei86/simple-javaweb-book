<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.mingribook.com/tages/xmenu" prefix="xmenu" %>
<% String contextPath=request.getContextPath();%>

<title>论坛-查看主题</title>
<script type="text/javascript" src="<%=contextPath%>/js/post.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body>
<c:set var="topic" value="${requestScope.topicsingle}"/>

<div align="left">
	<c:if test="${requestScope.visitboardstatus eq '2'}">
	<a href="<%=contextPath%>/visit/board/a/postview?boardId=${topic.boardId}" target="_blank" title="发表帖子"><img src="<%=contextPath%>/images/icon/topicadd.gif" style="border:0"></a>
	</c:if>
	<c:if test="${requestScope.visitboardstatus ne '2'}">
	【<font color="red">当前版块被锁定或关闭，不能发表帖子！</font>】
	</c:if>
	
	<!-- 实现上下主题超链接 -->
	<c:set var="prevTopicId" value="${requestScope.prevId}"/>
	<c:set var="nextTopicId" value="${requestScope.nextId}"/>
	<c:if test="${!empty prevTopicId}">
	<a href="<%=contextPath %>/visit/topic/a/view?topicId=${prevTopicId}">上一主题</a></c:if>
	<c:if test="${empty prevTopicId}">上一主题</c:if>
	|
	<c:if test="${!empty nextTopicId}">
	<a href="<%=contextPath %>/visit/topic/a/view?topicId=${nextTopicId}">下一主题</a></c:if>
	<c:if test="${empty nextTopicId}">下一主题</c:if>
</div>

<!-- 主题存在 -->
<c:if test="${!empty topic}">
<a name="viewtop"></a>
<table bgcolor="white" border="1" width="100%" style="word-break:break-all" cellpadding="0" cellspacing="1" bordercolor="#8A8989">
 	<tr height="30" bgcolor="#747474"  align="center" class="listhead">
  		<td nowrap width="15%">作者</td>
  		<td nowrap width="85%">此帖已观看 ${topic.topicHits} 次 | 有 <span id="replyNum">${topic.topicReplyNum}</span> 帖回复</td>
  	</tr>
	<tr bgcolor="#FFCC99">
		<!-- 显示主题作者信息 -->
		<td width="19%" rowspan="2" valign="top" style="padding-top:5;padding-bottom:10;padding-left:7;line-height:18px" nowrap>
			<c:if test="${empty topic.topicAuthor}"><font color="red">该会员已经被删除</font></c:if>
			<c:if test="${!empty topic.topicAuthor}">
			<c:if test="${topic.topicAuthor.memberSex eq '男'}">
			<img src="<%=contextPath%>/images/icon/man.gif" title="男"></c:if>
			<c:if test="${topic.topicAuthor.memberSex eq '女'}">
			<img src="<%=contextPath%>/images/icon/women.gif" title="女"></c:if>
			<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${topic.topicAuthorName}" class="title" target="_blank">${topic.topicAuthorName}</a><br>
			<a href="<%=contextPath%>/visit/myself/a/sendmessageview?getter=${topic.topicAuthorName}" target="_blank">发送信息</a><br><br>
			<img src="<%=contextPath%>/images/user/${topic.topicAuthor.memberIcon}" style="border:1 solid;border-color:gray">&nbsp;&nbsp;
			楼主<br><br>
			发　帖：${topic.topicAuthor.memberPostNum} 帖<br>
			经验值：${topic.topicAuthor.memberExperience} 点<br>
			魅力值：${topic.topicAuthor.memberCharm} 点<br>
			等　级：
			<jsp:include page="/gradeHandler.jsp">
				<jsp:param name="experience" value="${topic.topicAuthor.memberExperience}"/>
				<jsp:param name="charm" value="${topic.topicAuthor.memberCharm}"/>
			</jsp:include><br>
			注　册：${topic.topicAuthor.memberRegTime}<br>
			</c:if>
		</td>
		<!-- 显示主题信息 -->
		<td nowrap align="center" valign="top">
			<table border="0" width="100%" cellpadding="3" cellspacing="0">
				<!-- 显示操作按钮 -->
				<tr><td><xmenu:topicMenu topicId="${topic.id}" authorName="${topic.topicAuthorName}"/></td></tr>
				<!-- 显示标题、内容及附件 -->
				<tr height="120">
					<td valign="top">
						<!-- 标题 -->
						<img src="<%=contextPath%>/images/emotion/${topic.topicEmotion}">
						<c:if test="${topic.topicType eq '1'}"><img src="<%=contextPath%>/images/icon/best.gif" style="border:0"/></c:if>
						<img src="<%=contextPath%>/images/icon/topic${topic.topicStatus}.gif" style="border:0"/>
						<b>${topic.topicTitle}</b>
						<hr style="color:#808080">
						<!-- 内容 -->
						${topic.topicContent}
						<br><br>					
					</td>
				</tr>
				<tr>
					<td>
						<!-- 附件 -->						
						<c:if test="${!empty topic.topicAttachment}">
						<b>附件:</b><br>
						<c:forEach var="attsingle" items="${topic.topicAttachment}">
						<xmenu:attachmentMenu attachmentId="${attsingle.id}" operate="delete"/>
						<img src="<%=contextPath%>/images/icon/attach.gif">
						<a href="<%=contextPath%>/visit/attachment/a/downloadrun?attachmentid=${attsingle.id}">${attsingle.attachmentFileName}</a>
						(大小：${attsingle.attachmentFileSize} 字节)<br>
						</c:forEach>
						</c:if>
					</td>
				</tr>
			</table>
		</td>		
	</tr>
	<c:if test="${requestScope.visitboardstatus eq '2'}">
	<!-- 显示操作超链接 -->
	<tr bgcolor="#FFCC99">
		<td nowrap align="right" style="padding-right:5">
			${topic.topicPostTime} |
			<a href="#viewtop">返回顶部</a> |
			<a href="<%=contextPath%>/visit/topic/a/morereplylist?topicId=${topic.id}" target="listreply">更多回复</a>
		</td>
	</tr>
	</c:if>
</table>
</c:if>

<!-- 通过iframe框架包含最新显示回复帖的JSP页 -->
<iframe id="replylistshow" name="listreply" src="<%=contextPath%>/visit/topic/a/newreplylist?topicId=${topic.id}" width="100%" height="0" frameborder="0" scrolling="no"></iframe>

<hr style="color:gray">
<a name="replytop"></a>
<div id="replyface" style="display:none">
<jsp:include page="../reply/replysimple.jsp">
	<jsp:param name="author" value="${topic.topicAuthorName}"/>
	<jsp:param name="title" value="${topic.topicTitle}"/>
</jsp:include>
</div>
</body>
