<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.mingribook.com/tages/xmenu" prefix="xmenu" %>
<% String contextPath=request.getContextPath(); %>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body onload="changesubsizeshow('replylistshow')">
	<center>
		<c:set var="morereplylist" value="${requestScope.morereplylist}"/>
		
		<!-- 不存在回复 -->
		<c:if test="${empty morereplylist}">没有回复！</c:if>
		
		<a name="listtop"></a>
		<!-- 存在回复 -->	
		<c:if test="${!empty morereplylist}">
		<!-- 显示分页导航 -->
		<table border="0" width="100%" cellspacing="0" cellpadding="0" style="margin-top:1;margin-bottom:1">
			<tr><td><jsp:include page="/pageBar.jsp"/></td></tr>
		</table>
		
		<c:forEach var="reply" varStatus="rvs" items="${morereplylist}">
		<a name="reply${rvs.index}"></a>
		<table bgcolor="#999999" border="0" width="100%" style="word-break:break-all" cellpadding="0" cellspacing="1" bordercolor="#B9B9B9">
			<c:if test="${rvs.index%2==0}"><c:set var="bgc" value="white"/></c:if>
			<c:if test="${rvs.index%2!=0}"><c:set var="bgc" value="#F5F5F5"/></c:if>
			<tr bgcolor="${bgc}">
				<td width="19%" rowspan="2" valign="top" style="padding-top:5;padding-bottom:10;padding-left:7;border:0;line-height:18px" nowrap>
					<c:if test="${empty reply.replyAuthor}"><font color="red">该会员已经被删除</font></c:if>
					<c:if test="${!empty reply.replyAuthor}">
					<c:if test="${reply.replyAuthor.memberSex eq '男'}"><c:set var="sexicon" value="man.gif"/></c:if>
					<c:if test="${reply.replyAuthor.memberSex eq '女'}"><c:set var="sexicon" value="women.gif"/></c:if>
					<img src="<%=contextPath%>/images/icon/${sexicon}">
					<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${reply.replyAuthorName}" class="title" target="_blank">${reply.replyAuthorName}</a><br>
					<a href="<%=contextPath%>/visit/myself/a/sendmessageview?getter=${reply.replyAuthorName}" target="_blank">发送信息</a><br><br>
					<img src="<%=contextPath%>/images/user/${reply.replyAuthor.memberIcon}" style="border:1 solid;border-color:gray">&nbsp;&nbsp;
					${(requestScope.pageBar.currentP-1)*requestScope.pageBar.perR+(rvs.index+1)} 楼<br><br>
					发　帖：${reply.replyAuthor.memberPostNum} 帖<br>
					经验值：${reply.replyAuthor.memberExperience} 点<br>
					魅力值：${reply.replyAuthor.memberCharm} 点<br>
					等　级：
					<jsp:include page="/gradeHandler.jsp">
						<jsp:param name="experience" value="${reply.replyAuthor.memberExperience}"/>
						<jsp:param name="charm" value="${reply.replyAuthor.memberCharm}"/>
					</jsp:include><br>
					注　册：${reply.replyAuthor.memberRegTime}<br>
					</c:if>
				</td>
				<!-- 显示主题回复信息 -->
				<td nowrap align="center" valign="top">
					<table border="0" width="100%" cellpadding="3">
					<!-- 显示操作按钮 -->
					<tr><td><xmenu:replyMenu replyId="${reply.id}" topicId="${param.topicId}"/></td></tr>
						<!-- 显示标题及内容 -->
						<tr height="120">
							<td valign="top">
								<!-- 标题 -->
								<img src="<%=contextPath%>/images/emotion/${reply.replyEmotion}">						
								<b>${reply.replyTitle}</b>							
								<hr style="color:#808080">
								<!-- 内容 -->
								${reply.replyContentForJsp}
								<br><br>
							</td>
						</tr>
						<!-- 附件 -->						
						<tr>
							<td>
								<c:if test="${!empty reply.replyAttachment}">
								<b>附件:</b><br>
								<c:forEach var="attsingle" items="${reply.replyAttachment}">
								<a href="<%=contextPath%>/visit/attachment/c/deleteview?attachmentid=${attsingle.id}" target="_parent">[删除]</a>
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
			<tr bgcolor="${bgc}">
				<td align="right" style="padding-right:5" style="border:0" nowrap>
					${reply.replyReplyTime} |
					<a href="#reply${rvs.index}">返回本帖顶部</a> |
					<a href="javascript:gotop('viewtop')">返回顶部</a> |
				</td>	
			</tr>
			</c:if>
		</table>
		</c:forEach>

		<!-- 显示分页导航 -->
		<table border="0" width="100%" cellspacing="0" cellpadding="0" style="margin-top:1;margin-bottom:1">
			<tr><td><jsp:include page="/pageBar.jsp"/></td></tr>
		</table>
		</c:if>
	</center>
</body>