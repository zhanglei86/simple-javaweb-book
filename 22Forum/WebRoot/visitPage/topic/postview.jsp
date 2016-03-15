<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.mingribook.com/tages/xmenu" prefix="xmenu" %>
<%String contextPath=request.getContextPath();%>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/post.js"></script>

<body>
<center>
<!-- 实现在线编辑发表帖子界面 -->
<form name="postform" action="<%=contextPath%>/visit/board/a/postrun" method="post">
<input type="hidden" name="boardId" value="${param.boardId}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">发表帖子</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap>标题：</td>
		<td nowrap><input type="text" name="title" size="90"></td>
	</tr>
	<tr bgcolor="lightgrey" height="8" align="center"><td colspan="2"><b><span id="checkmessage" style="color:red"></span></b></td></tr>
	<tr bgcolor="#F5F5F5">
		<td width=23%" valign="top">表情：<br><jsp:include page="../emotion.jsp"/></td>
		<td align="center" nowrap rowspan="4">
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center" valign="top">
						<textarea name="content"></textarea>
						<script type="text/javascript">
							var oFCKeditor = new FCKeditor("content") ;
							oFCKeditor.BasePath = "<%=contextPath%>/fckeditor/" ;
							oFCKeditor.Height = 300;
							oFCKeditor.ToolbarSet = "myself" ; 
							oFCKeditor.ReplaceTextarea();
						</script>
						<input type="button" name="postsubmitb" value="发表帖子" onclick="myoleditsubmit()">
						<input type="reset" value="重新填写" onclick="myReset(content.value)">						
					</td>
				</tr>	
			</table>
		</td>
	</tr>
	<tr bgcolor="white"><td><xmenu:attachmentMenu operate="add"/></td></tr>
	<tr bgcolor="#F5F5F5">
		<td valign="top">		
           	<img src="<%=contextPath%>/images/icon/post.gif" width="50" height="50"><br>
           	■ 请不要发表危害祖国的非法信息！<br>
           	■ 请不要发表侵犯个人名誉的信息！<br>
           	■ 请不要发表不文明内容！<br>
           	■ 违反以上规则，后果自负！<br><br>
           <font color="#7F7F7F">内容最多允许输入：<b><span id="ContentAll" style="width:40;text-align:center">2000</span></b>个字符！</font >
		</td>
	</tr>
</table>
</form>
</center>
</body>