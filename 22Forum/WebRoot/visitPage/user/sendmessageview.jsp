<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visituser.js"></script>
<script type="text/javascript" src="<%=contextPath%>/fckeditor/fckeditor.js"></script>

<body>
<center>

<form name="messageform" action="<%=contextPath%>/visit/myself/a/sendmessagerun" method="post">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">������Ϣ</td></tr>
	<tr bgcolor="white" align="center"><td colspan="2"><b><span id="checkmessage" style="color:red"></span></td></tr>
	<tr bgcolor="#F5F5F5" height="25">
		<td nowrap>�� Ѷ �ˣ�[����]</td>
		<td nowrap><input type="text" name="getter" size="90" class="login" value="${param.getter}"></td>
	</tr>
	<tr bgcolor="white"bgcolor="white" height="25">
		<td nowrap>��Ϣ���⣺[����]</td>
		<td nowrap><input type="text" name="title" size="90" class="login"></td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td width=23%" nowrap valign="top">
           	��Ϣ���飺<jsp:include page="../emotion.jsp"/><br>
           	<font color="#7F7F7F">��������������룺<b><span id="ContentAll" style="width:40;text-align:center">500</span></b>���ַ���</font >
		</td>
		<td align="center" nowrap rowspan="4">
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<textarea id="content" name="content"></textarea>
						<script type="text/javascript">
							var oFCKeditor = new FCKeditor("content") ;
							oFCKeditor.BasePath = "<%=contextPath%>/fckeditor/" ;
							oFCKeditor.Height = 300;
							oFCKeditor.ToolbarSet = "myself" ; 
							oFCKeditor.ReplaceTextarea();
						</script>
						<input type="button" name="sendsubmitb" value="������Ϣ" onclick="mysendmessagesubmit()">
						<input type="reset" value="���±�д">						
						<input type="checkbox" name="savetosend" value="yes">���ݸ���Ϣ����������
					</td>
				</tr>	
			</table>
		</td>
	</tr>
</table>
</form>
</center>
</body>