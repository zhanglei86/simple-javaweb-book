<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>��̳-���߱༭�ظ�����</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/post.js"></script>

<body>
<center>
<c:set var="topic" value="${requestScope.topicsingle}"/>
<!-- ʵ�����߱༭�ظ�������� -->
<form name="postform" action="<%=contextPath%>/visit/topic/a/replyrun" method="post">
<input type="hidden" name="posttype" value="oledit">
<input type="hidden" name="topicId" value="${param.topicId}">
<input type="hidden" name="author" value="${topic.topicAuthorName}">
<input type="hidden" name="attachment" value="0">
<table border="0" width="100%" cellspacing="1" cellpadding="0" bgcolor="#999999">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">�ظ������ӣ�${topic.topicTitle}</td></tr>
	<tr bgcolor="white">
		<td style="text-indent:5">���⣺</td>
		<td>
			<input type="text" name="title" size="80" value="Re:${topic.topicTitle}">&nbsp;
			<a href="#viewtopictop">�鿴�ظ�������</a>
		</td>
	</tr>
	<tr bgcolor="lightgrey" height="8" align="center"><td colspan="2"><b><span id="checkmessage" style="color:red"></span></b></td></tr>
	<tr>
		<td valign="top" width=25%" bgcolor="#F5F5F5" style="padding-left:5">
           	���飺<br>
           	<%@ include file="../emotion.jsp" %>
           	<img src="<%=contextPath%>/images/icon/post.gif" width="50" height="50" border="0"><br>
           	�� �벻Ҫ����Σ������ķǷ���Ϣ��<br>
           	�� �벻Ҫ�����ַ�������������Ϣ��<br>
           	�� �벻Ҫ�����������ݣ�<br>
           	�� Υ�����Ϲ��򣬺���Ը���<br><br>
           	<font color="#7F7F7F">��������������룺<b><span id="ContentAll" style="width:40;text-align:center">2000</span></b>���ַ���</font >
		</td>
		<td align="center" bgcolor="#F5F5F5">
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<textarea id="content" name="content" rows="19" cols="97">${requestScope.quotetext}</textarea>
						<script type="text/javascript">
							var oFCKeditor = new FCKeditor("content") ;
							oFCKeditor.BasePath = "<%=contextPath%>/fckeditor/" ;
							oFCKeditor.Height = 300;
							oFCKeditor.ToolbarSet = "myself" ; 
							oFCKeditor.ReplaceTextarea();
						</script>
					</td>
				</tr>
				<tr height="30" align="center">
					<td>						
						<input type="button" name="postsubmitb" value="�ύ�ظ�" onclick="myoleditsubmit()">
						<input type="reset" value="������д" onclick="myReset(content.value)">
					</td>
				</tr>	
			</table>
		</td>
	</tr>
</table>
</form>
<a name="viewtopictop"></a>
<table bgcolor="#999999" border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="1">
	<tr class="listhead" height="25"><td colspan="2">�ظ�������${topic.topicTitle}</td></tr>
	<tr bgcolor="#FFCC99">
		<td width="19%" valign="top" style="padding-top:5;padding-bottom:10;padding-left:7;line-height:18px" nowrap>
			<c:if test="${empty topic.topicAuthor}"><font color="red">�û�Ա�Ѿ���ɾ��</font></c:if>
			<c:if test="${!empty topic.topicAuthor}">
			<c:if test="${topic.topicAuthor.memberSex eq '��'}">
			<img src="<%=contextPath%>/images/icon/man.gif"></c:if>
			<c:if test="${topic.topicAuthor.memberSex eq 'Ů'}">
			<img src="<%=contextPath%>/images/icon/women.gif"></c:if>
			<a href="<%=contextPath%>/visit/user/a/viewmember?memberName=${topic.topicAuthorName}" class="title" target="_blank">${topic.topicAuthorName}</a><br>
			<a href="<%=contextPath%>/visit/myself/a/sendmessageview?getter=${topic.topicAuthorName}" target="_blank">������Ϣ</a><br><br>
			<img src="<%=contextPath%>/images/user/${topic.topicAuthor.memberIcon}" style="border:1 solid">&nbsp;&nbsp;
			<br><br>
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
		<td height="100" valign="top">
			<b>Re:${topic.topicTitle}</b>
			<hr style="color:#808080">
			${topic.topicContent}
		</td>
	</tr>	
</table>
</center>
</body>