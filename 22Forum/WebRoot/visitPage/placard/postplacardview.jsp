<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>发布公告</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/post.js"></script>
<script type="text/javascript" src="<%=contextPath%>/fckeditor/fckeditor.js"></script>

<center>
<!-- 实现在线编辑发布公告界面 -->
<form action="<%=contextPath%>/visit/placard/c/postplacardrun" name="postform" method="post">
<input type="hidden" name="boardId" value="${param.boardId}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">发布公告</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap>标题：</td>
		<td nowrap><input type="text" name="title" size="90"></td>
	</tr>
	<tr bgcolor="lightgrey" height="8" align="center"><td colspan="2"><b><span id="checkmessage" style="color:red"></span></b></td></tr>
	<tr bgcolor="#F5F5F5">
		<td width=23%" valign="top" nowrap>
           	公告类型：<br>
           	<input type="radio" name="type" value="2">论坛公告<br>
           	<input type="radio" name="type" value="1">类别公告【当前类别】<br>
           	<input type="radio" name="type" value="0" checked>版块公告【当前版块】<br>
           	<font color="#7F7F7F">内容最多允许输入：<b><span id="ContentAll" style="width:40;text-align:center">300</span></b>个字符！</font >
		</td>
		<td align="center" nowrap rowspan="4">
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<textarea id="content" name="content"></textarea>
						<script type="text/javascript">
							var oFCKeditor = new FCKeditor("content") ;
							oFCKeditor.BasePath = "<%=contextPath%>/fckeditor/" ;
							oFCKeditor.Height = 200;
							oFCKeditor.ToolbarSet = "myself" ; 
							oFCKeditor.ReplaceTextarea();
						</script>
						<input type="button" name="postsubmitb" value="分布公告" onclick="myoleditsubmit()">
						<input type="reset" value="重新填写">						
					</td>
				</tr>	
			</table>
		</td>
	</tr>
</table>
</form>
</center>
</body>