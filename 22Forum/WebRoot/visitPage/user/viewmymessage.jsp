<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>�鿴��Ϣ</title>

<body>
<c:set var="message" value="${requestScope.messagesingle}"/>
<!-- ��Ϣ���� -->
<c:if test="${!empty message}">
<c:set var="author" value="${requestScope.messageauthor}"/>
<table border="0" width="98%" cellpadding="0" cellspacing="0">
	<tr valign="bottom">
		<c:if test="${param.from eq 'incept'}">
		<td width="75%">�鿴��������Ϣ</td></c:if>
		<c:if test="${param.from eq 'send'}">
		<td width="75%">�鿴��������Ϣ</td></c:if>
		<td align="center"><a href="<%=contextPath%>/visit/myself/a/mymessagebox?from=${param.from}">��������</a></td>
		<td align="center"><a href="<%=contextPath%>/visit/myself/a/sendmessageview"><img src="<%=contextPath%>/images/icon/newmessage.gif" style="border:0"></a><br>����Ϣ</td>
		<td align="center">
			<c:if test="${param.from eq 'incept'}">
			<a href="<%=contextPath%>/visit/myself/a/sendmessageview?getter=${author.memberName}"><img src="<%=contextPath%>/images/icon/replymessage.gif" style="border:0"></a><br>�ظ�
			</c:if>			
		</td>
		<td align="center"><a href="<%=contextPath%>/visit/myself/a/deleteonemessage?messageId=${message.id}&from=${param.from}"><img src="<%=contextPath%>/images/icon/deletemessage.gif" style="border:0"></a><br>ɾ��</td>
	</tr>
</table>
<table border="0" width="100%" style="word-break:break-all" cellpadding="0" cellspacing="1" bgcolor="#8A8989">
	<tr bgcolor="white">
		<!-- ����鿴�����������е���Ϣ����ʾ��������Ϣ -->
		<td width="19%" rowspan="2" valign="top" style="padding-top:5;padding-bottom:10;padding-left:7;line-height:18px" nowrap>
			<c:if test="${param.from eq 'incept'}">
			������<br></c:if>
			<c:if test="${param.from eq 'send'}">
			������<br></c:if>
			<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${author.memberName}" class="title" target="_blank">${author.memberName}</a>
			<c:if test="${author.memberSex eq '��'}">
			<img src="<%=contextPath%>/images/icon/man.gif"></c:if>
			<c:if test="${author.memberSex eq 'Ů'}">
			<img src="<%=contextPath%>/images/icon/women.gif"></c:if>
			<br><br>
			<img src="<%=contextPath%>/images/user/${author.memberIcon}"><br><br>
			��������${author.memberPostNum} ��<br>
			����ֵ��${author.memberExperience} ��<br>
			����ֵ��${author.memberCharm} ��<br>
			�ȡ�����
			<jsp:include page="/gradeHandler.jsp">
				<jsp:param name="experience" value="${author.memberExperience}"/>
				<jsp:param name="charm" value="${author.memberCharm}"/>
            </jsp:include><br>
			ע���᣺${author.memberRegTime}<br>
			״��̬������
		</td>
		<!-- ��ʾ��Ϣ��ϸ���� -->
		<td nowrap align="center" valign="top">
			<table border="0" width="100%" cellpadding="2" cellspacing="0">
				<tr height="20">
					<td align="right" width="10%"><b>����:</b></td>
					<td style="padding-left:10"><img src="<%=contextPath%>/images/emotion/${message.messageEmotion}"> ${message.messageTitle}</td>
				</tr>
				<tr height="20">
					<td align="right"><b>������:</b></td>
					<td style="padding-left:10">${message.messageSender}</td>
				</tr>
				<tr height="20">
					<td align="right"><b>��������:</b></td>
					<td style="padding-left:10">${message.messageSendTime}</td>
				</tr>
				<tr height="20">
					<td align="right"><b>������:</b></td>
					<td style="padding-left:10">${message.messageGetter}</td>
				</tr>
				<tr><td colspan="2"><hr width="99%" color="black"></td></tr>
				<tr><td colspan="2" style="padding-left:10">${message.messageContent}</td></tr>				
			</table>
		</td>		
	</tr>
	<tr bgcolor="#F5F5F5">
		<td style="padding-left:5">
			[${message.messageSendTime}]&nbsp;
			<a href="<%=contextPath%>/visit/myself/a/sendmessageview">����Ϣ</a>
			<a href="<%=contextPath%>/visit/myself/a/deleteonemessage?messageId=${message.id}&from=${param.from}">ɾ��</a>
		</td>
	</tr>

</table>
</c:if>
</body>