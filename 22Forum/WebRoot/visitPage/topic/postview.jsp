<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.mingribook.com/tages/xmenu" prefix="xmenu" %>
<%String contextPath=request.getContextPath();%>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/post.js"></script>

<body>
<center>
<!-- ʵ�����߱༭�������ӽ��� -->
<form name="postform" action="<%=contextPath%>/visit/board/a/postrun" method="post">
<input type="hidden" name="boardId" value="${param.boardId}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">��������</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap>���⣺</td>
		<td nowrap><input type="text" name="title" size="90"></td>
	</tr>
	<tr bgcolor="lightgrey" height="8" align="center"><td colspan="2"><b><span id="checkmessage" style="color:red"></span></b></td></tr>
	<tr bgcolor="#F5F5F5">
		<td width=23%" valign="top">���飺<br><jsp:include page="../emotion.jsp"/></td>
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
						<input type="button" name="postsubmitb" value="��������" onclick="myoleditsubmit()">
						<input type="reset" value="������д" onclick="myReset(content.value)">						
					</td>
				</tr>	
			</table>
		</td>
	</tr>
	<tr bgcolor="white"><td><xmenu:attachmentMenu operate="add"/></td></tr>
	<tr bgcolor="#F5F5F5">
		<td valign="top">		
           	<img src="<%=contextPath%>/images/icon/post.gif" width="50" height="50"><br>
           	�� �벻Ҫ����Σ������ķǷ���Ϣ��<br>
           	�� �벻Ҫ�����ַ�������������Ϣ��<br>
           	�� �벻Ҫ�����������ݣ�<br>
           	�� Υ�����Ϲ��򣬺���Ը���<br><br>
           <font color="#7F7F7F">��������������룺<b><span id="ContentAll" style="width:40;text-align:center">2000</span></b>���ַ���</font >
		</td>
	</tr>
</table>
</form>
</center>
</body>