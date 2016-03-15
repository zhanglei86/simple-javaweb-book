<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>查看公告</title>

<body>
<c:set var="placard" value="${requestScope.placardsingle}"/>

<!-- 信息存在 -->
<c:if test="${!empty placard}">
<c:set var="author" value="${placard.placardAuthor}"/>
<table border="0" width="98%" cellpadding="0" cellspacing="0">
	<tr valign="bottom">
		<td width="85%"></td>
		<td align="center"><a href="<%=contextPath%>/visit/placard/c/postplacardview?boardId=${param.boardId}"><img src="<%=contextPath%>/images/icon/newmessage.gif" style="border:0"></a><br>发布新公告</td>
		<td align="center"><a href="<%=contextPath%>/visit/placard/c/deleteplacardview?placardId=${placard.id}&boardId=${param.boardId}"><img src="<%=contextPath%>/images/icon/deletemessage.gif" style="border:0"></a><br>删除公告</td>
	</tr>
</table>

<table border="0" width="100%" style="word-break:break-all" cellpadding="0" cellspacing="1" bgcolor="#8A8989">
	<tr bgcolor="white">
		<td width="19%" rowspan="2" valign="top" style="padding-top:5;padding-bottom:10;padding-left:7;line-height:18px" nowrap>
			发布者<br>
			<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${author.memberName}" class="title" target="_blank">${author.memberName}</a>
			<c:if test="${author.memberSex eq '男'}">
			<img src="<%=contextPath%>/images/icon/man.gif" title="男"></c:if>
			<c:if test="${author.memberSex eq '女'}">
			<img src="<%=contextPath%>/images/icon/women.gif" title="女"></c:if>
			<br><br>
			<img src="<%=contextPath%>/images/user/${author.memberIcon}"><br><br>
			发　帖：${author.memberPostNum} 帖<br>
			经验值：${author.memberExperience} 点<br>
			魅力值：${author.memberCharm} 点<br>
			等　级：
			<jsp:include page="/gradeHandler.jsp">
				<jsp:param name="experience" value="${author.memberExperience}"/>
				<jsp:param name="charm" value="${author.memberCharm}"/>
            </jsp:include><br>
			注　册：${author.memberRegTime}<br>
			状　态：在线
		</td>
		<!-- 显示公告详细内容 -->
		<td nowrap align="center" valign="top">
			<table border="0" width="100%" cellpadding="2" cellspacing="0">
				<tr height="20">
					<td align="right" width="10%"><b>标题:</b></td>
					<td style="padding-left:10"><img src="<%=contextPath%>/images/icon/placard_title.gif"> ${placard.placardTitle}</td>
				</tr>
				<tr height="20">
					<td align="right"><b>发布日期:</b></td>
					<td style="padding-left:10">${placard.placardPostTime}</td>
				</tr>
				<tr><td colspan="2"><hr width="99%" color="black"></td></tr>
				<tr><td colspan="2" style="padding-left:10">${placard.placardContent}</td></tr>				
			</table>
		</td>		
	</tr>
	<tr bgcolor="#F5F5F5" height="25">
		<td style="padding-left:5">
			[${placard.placardPostTime}]&nbsp;
			<a href="<%=contextPath%>/visit/placard/c/postplacardview?boardId=${param.boardId}">发布新公告</a>
			<a href="<%=contextPath%>/visit/placard/c/deleteplacardview?placardId=${placard.id}&boardId=${param.boardId}">删除公告</a>
		</td>
	</tr>

</table>
</c:if>
</body>