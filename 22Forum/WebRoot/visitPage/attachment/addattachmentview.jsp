<%@ page contentType="text/html;charset=gb2312"%>
<%String contextPath=request.getContextPath();%>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body onload="changesubsizeshow('addattachmentview');gotop('uploadtop');">
	<center>
		<form action="<%=contextPath%>/visit/attachment/c/uploadrun?topicId=${param.topicId}&replyId=${param.replyId}" name="attachmentform" method="post" enctype="multipart/form-data">
		<table border="0" width="100%" cellspacing="1" cellpadding="3" bgcolor="#999999" style="word-break:break-all">
			<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">��Ӹ���</td></tr>
			<tr bgcolor="white">
				<td width="25%" valign="top">ѡ���ļ���</td>
				<td>
      				�ļ�1>> <input id="file1" type="file" name="attachment1" size="80" class="login" style="background:#F5F5F5"/><br/>
      				�ļ�2>> <input id="file2" type="file" name="attachment2" size="80" class="login" style="background:#F5F5F5"/><br/>
			         �ļ�3>> <input id="file3" type="file" name="attachment3" size="80" class="login" style="background:#F5F5F5"/><br/>
      				�ļ�4>> <input id="file4" type="file" name="attachment4" size="80" class="login" style="background:#F5F5F5"/><br/>
				</td>
			</tr>
			<tr bgcolor="lightgrey" align="center">
				<td colspan="2">
					<input type="button" value="�����ļ�" name="submitb" onclick="myupattachmentsubmit()">
					<input type="reset" value="����ѡ��">
				</td>
			</tr>
		</table>
		</form>
	</center>
</body>