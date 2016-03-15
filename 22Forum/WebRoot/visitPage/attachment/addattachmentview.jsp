<%@ page contentType="text/html;charset=gb2312"%>
<%String contextPath=request.getContextPath();%>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body onload="changesubsizeshow('addattachmentview');gotop('uploadtop');">
	<center>
		<form action="<%=contextPath%>/visit/attachment/c/uploadrun?topicId=${param.topicId}&replyId=${param.replyId}" name="attachmentform" method="post" enctype="multipart/form-data">
		<table border="0" width="100%" cellspacing="1" cellpadding="3" bgcolor="#999999" style="word-break:break-all">
			<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">添加附件</td></tr>
			<tr bgcolor="white">
				<td width="25%" valign="top">选择文件：</td>
				<td>
      				文件1>> <input id="file1" type="file" name="attachment1" size="80" class="login" style="background:#F5F5F5"/><br/>
      				文件2>> <input id="file2" type="file" name="attachment2" size="80" class="login" style="background:#F5F5F5"/><br/>
			         文件3>> <input id="file3" type="file" name="attachment3" size="80" class="login" style="background:#F5F5F5"/><br/>
      				文件4>> <input id="file4" type="file" name="attachment4" size="80" class="login" style="background:#F5F5F5"/><br/>
				</td>
			</tr>
			<tr bgcolor="lightgrey" align="center">
				<td colspan="2">
					<input type="button" value="附加文件" name="submitb" onclick="myupattachmentsubmit()">
					<input type="reset" value="重新选择">
				</td>
			</tr>
		</table>
		</form>
	</center>
</body>