<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.mingribook.com/tages/xmenu" prefix="xmenu" %>
<% String contextPath=request.getContextPath();%>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body onload="changesubsizeshow('replylistshow')">
	<center>
	<c:set var="newreplylist" value="${requestScope.newreplylist}"/>
	
	<!-- �����ڻظ� -->
	<c:if test="${empty newreplylist}">
	<table border="0" width="100%" style="word-break:break-all" cellpadding="0" cellspacing="0">
		<tr height="50" bgcolor="white" align="center"><td>û�лظ���</td></tr>
	</table>
	</c:if>	
	
	<!-- ���ڻظ� -->	
	<c:if test="${!empty newreplylist}">
	<c:forEach var="reply" varStatus="rvs" items="${newreplylist}">
	<a name="reply${rvs.index}"></a>
	<table bgcolor="#999999" border="0" width="100%" style="word-break:break-all" cellpadding="0" cellspacing="1">
		<c:if test="${rvs.index%2==0}"><c:set var="bgc" value="white"/></c:if>
		<c:if test="${rvs.index%2!=0}"><c:set var="bgc" value="#F5F5F5"/></c:if>
		<tr bgcolor="${bgc}">
			<!-- ��ʾ�ظ���������Ϣ -->
			<td width="19%" rowspan="2" valign="top" style="padding-top:5;padding-bottom:10;padding-left:7;border:0;line-height:18px" nowrap>
				<c:if test="${empty reply.replyAuthor}"><font color="red">�û�Ա�Ѿ���ɾ��</font></c:if>
				<c:if test="${!empty reply.replyAuthor}">
				<c:if test="${reply.replyAuthor.memberSex eq '��'}"><c:set var="sexicon" value="man.gif"/></c:if>
				<c:if test="${reply.replyAuthor.memberSex eq 'Ů'}"><c:set var="sexicon" value="women.gif"/></c:if>
				<img src="<%=contextPath%>/images/icon/${sexicon}">
				<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${reply.replyAuthorName}" class="title" target="_blank">${reply.replyAuthorName}</a><br>
				<a href="<%=contextPath%>/visit/myself/a/sendmessageview?getter=${reply.replyAuthorName}" target="_blank">������Ϣ</a><br><br>
				<img src="<%=contextPath%>/images/user/${reply.replyAuthor.memberIcon}" style="border:1 solid;border-color:gray">&nbsp;&nbsp;
				<script type="text/javascript">
				document.write(parent.document.all.replyNum.innerText-${rvs.index}+" ¥");
				</script><br><br>
				��������${reply.replyAuthor.memberPostNum} ��<br>
				����ֵ��${reply.replyAuthor.memberExperience} ��<br>
				����ֵ��${reply.replyAuthor.memberCharm} ��<br>
				�ȡ�����
				<jsp:include page="/gradeHandler.jsp">
					<jsp:param name="experience" value="${reply.replyAuthor.memberExperience}"/>
					<jsp:param name="charm" value="${reply.replyAuthor.memberCharm}"/>
				</jsp:include><br>
				ע���᣺${reply.replyAuthor.memberRegTime}<br>
				</c:if>
			</td>
			<!-- ��ʾ����ظ���Ϣ -->
			<td nowrap align="center" valign="top">
				<table border="0" width="100%" cellpadding="3">
					<!-- ��ʾ������ť -->
					<tr><td><xmenu:replyMenu replyId="${reply.id}" topicId="${param.topicId}"/></td></tr>
					<!-- ��ʾ���⼰���� -->
					<tr height="120">
						<td valign="top">
							<!-- ���� -->
							<img src="<%=contextPath%>/images/emotion/${reply.replyEmotion}">						
							<b>${reply.replyTitle}</b>						
							<hr style="color:#808080">
							<!-- ���� -->
							${reply.replyContentForJsp}
							<br><br>
						</td>
					</tr>
					<!-- ���� -->						
					<tr>
						<td>
							<c:if test="${!empty reply.replyAttachment}">
							<b>����:</b><br>
							<c:forEach var="attsingle" items="${reply.replyAttachment}">
							<a href="<%=contextPath%>/visit/attachment/c/deleteview?attachmentid=${attsingle.id}" target="_parent">[ɾ��]</a>
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
		<tr bgcolor="${bgc}">
			<td nowrap align="right" style="padding-right:5" style="border:0">
				${reply.replyReplyTime} |
				<a href="#reply${rvs.index}">���ر�������</a> |
				<a href="javascript:gotop('viewtop')">���ض���</a> |
				<a href="<%=contextPath%>/visit/topic/a/morereplylist?topicId=${param.topicId}#listtop">����ظ�</a>
			</td>
		</tr>
		</c:if>
	</table>
	</c:forEach>
	</c:if>
	</center>
</body>