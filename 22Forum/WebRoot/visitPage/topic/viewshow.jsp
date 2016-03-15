<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.mingribook.com/tages/xmenu" prefix="xmenu" %>
<% String contextPath=request.getContextPath();%>

<title>��̳-�鿴����</title>
<script type="text/javascript" src="<%=contextPath%>/js/post.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body>
<c:set var="topic" value="${requestScope.topicsingle}"/>

<div align="left">
	<c:if test="${requestScope.visitboardstatus eq '2'}">
	<a href="<%=contextPath%>/visit/board/a/postview?boardId=${topic.boardId}" target="_blank" title="��������"><img src="<%=contextPath%>/images/icon/topicadd.gif" style="border:0"></a>
	</c:if>
	<c:if test="${requestScope.visitboardstatus ne '2'}">
	��<font color="red">��ǰ��鱻������رգ����ܷ������ӣ�</font>��
	</c:if>
	
	<!-- ʵ���������ⳬ���� -->
	<c:set var="prevTopicId" value="${requestScope.prevId}"/>
	<c:set var="nextTopicId" value="${requestScope.nextId}"/>
	<c:if test="${!empty prevTopicId}">
	<a href="<%=contextPath %>/visit/topic/a/view?topicId=${prevTopicId}">��һ����</a></c:if>
	<c:if test="${empty prevTopicId}">��һ����</c:if>
	|
	<c:if test="${!empty nextTopicId}">
	<a href="<%=contextPath %>/visit/topic/a/view?topicId=${nextTopicId}">��һ����</a></c:if>
	<c:if test="${empty nextTopicId}">��һ����</c:if>
</div>

<!-- ������� -->
<c:if test="${!empty topic}">
<a name="viewtop"></a>
<table bgcolor="white" border="1" width="100%" style="word-break:break-all" cellpadding="0" cellspacing="1" bordercolor="#8A8989">
 	<tr height="30" bgcolor="#747474"  align="center" class="listhead">
  		<td nowrap width="15%">����</td>
  		<td nowrap width="85%">�����ѹۿ� ${topic.topicHits} �� | �� <span id="replyNum">${topic.topicReplyNum}</span> ���ظ�</td>
  	</tr>
	<tr bgcolor="#FFCC99">
		<!-- ��ʾ����������Ϣ -->
		<td width="19%" rowspan="2" valign="top" style="padding-top:5;padding-bottom:10;padding-left:7;line-height:18px" nowrap>
			<c:if test="${empty topic.topicAuthor}"><font color="red">�û�Ա�Ѿ���ɾ��</font></c:if>
			<c:if test="${!empty topic.topicAuthor}">
			<c:if test="${topic.topicAuthor.memberSex eq '��'}">
			<img src="<%=contextPath%>/images/icon/man.gif" title="��"></c:if>
			<c:if test="${topic.topicAuthor.memberSex eq 'Ů'}">
			<img src="<%=contextPath%>/images/icon/women.gif" title="Ů"></c:if>
			<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${topic.topicAuthorName}" class="title" target="_blank">${topic.topicAuthorName}</a><br>
			<a href="<%=contextPath%>/visit/myself/a/sendmessageview?getter=${topic.topicAuthorName}" target="_blank">������Ϣ</a><br><br>
			<img src="<%=contextPath%>/images/user/${topic.topicAuthor.memberIcon}" style="border:1 solid;border-color:gray">&nbsp;&nbsp;
			¥��<br><br>
			��������${topic.topicAuthor.memberPostNum} ��<br>
			����ֵ��${topic.topicAuthor.memberExperience} ��<br>
			����ֵ��${topic.topicAuthor.memberCharm} ��<br>
			�ȡ�����
			<jsp:include page="/gradeHandler.jsp">
				<jsp:param name="experience" value="${topic.topicAuthor.memberExperience}"/>
				<jsp:param name="charm" value="${topic.topicAuthor.memberCharm}"/>
			</jsp:include><br>
			ע���᣺${topic.topicAuthor.memberRegTime}<br>
			</c:if>
		</td>
		<!-- ��ʾ������Ϣ -->
		<td nowrap align="center" valign="top">
			<table border="0" width="100%" cellpadding="3" cellspacing="0">
				<!-- ��ʾ������ť -->
				<tr><td><xmenu:topicMenu topicId="${topic.id}" authorName="${topic.topicAuthorName}"/></td></tr>
				<!-- ��ʾ���⡢���ݼ����� -->
				<tr height="120">
					<td valign="top">
						<!-- ���� -->
						<img src="<%=contextPath%>/images/emotion/${topic.topicEmotion}">
						<c:if test="${topic.topicType eq '1'}"><img src="<%=contextPath%>/images/icon/best.gif" style="border:0"/></c:if>
						<img src="<%=contextPath%>/images/icon/topic${topic.topicStatus}.gif" style="border:0"/>
						<b>${topic.topicTitle}</b>
						<hr style="color:#808080">
						<!-- ���� -->
						${topic.topicContent}
						<br><br>					
					</td>
				</tr>
				<tr>
					<td>
						<!-- ���� -->						
						<c:if test="${!empty topic.topicAttachment}">
						<b>����:</b><br>
						<c:forEach var="attsingle" items="${topic.topicAttachment}">
						<xmenu:attachmentMenu attachmentId="${attsingle.id}" operate="delete"/>
						<img src="<%=contextPath%>/images/icon/attach.gif">
						<a href="<%=contextPath%>/visit/attachment/a/downloadrun?attachmentid=${attsingle.id}">${attsingle.attachmentFileName}</a>
						(��С��${attsingle.attachmentFileSize} �ֽ�)<br>
						</c:forEach>
						</c:if>
					</td>
				</tr>
			</table>
		</td>		
	</tr>
	<c:if test="${requestScope.visitboardstatus eq '2'}">
	<!-- ��ʾ���������� -->
	<tr bgcolor="#FFCC99">
		<td nowrap align="right" style="padding-right:5">
			${topic.topicPostTime} |
			<a href="#viewtop">���ض���</a> |
			<a href="<%=contextPath%>/visit/topic/a/morereplylist?topicId=${topic.id}" target="listreply">����ظ�</a>
		</td>
	</tr>
	</c:if>
</table>
</c:if>

<!-- ͨ��iframe��ܰ���������ʾ�ظ�����JSPҳ -->
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
