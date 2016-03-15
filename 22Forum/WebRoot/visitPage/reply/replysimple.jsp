<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
	String contextPath=request.getContextPath();
	response.addHeader("Pragma","no-cache");
	response.addHeader("Cache-Control","no-cache");
	response.addDateHeader("Expires",0);
%>

<title>论坛-简单模式回复主题</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/post.js"></script>

<center>
<!-- 实现简单模式回复主题界面 -->
<form name="postform" action="<%=contextPath%>/visit/topic/a/replyrun?topicId=${param.topicId}" method="post">
<input type="hidden" name="title" value="${param.title}">
<input type="hidden" name="author" value="${param.author}">
<input type="hidden" name="emotion" value="face1.gif">
<input type="hidden" name="attachment" value="0">
<b><span id="checkmessage" style="color:red"></span></b>
<table border="0" width="100%" cellspacing="1" cellpadding="0" bgcolor="#999999">
	<tr class="listhead" height="25"><td colspan="3" style="text-indent:5">简单模式回复：${param.title}</td></tr>
	<tr>
		<td valign="top" width=25%" bgcolor="#F5F5F5" style="padding-left:5">
           	<br>
           	<img src="<%=contextPath%>/images/icon/post.gif" width="50" height="50" border="0"><br>
           	■ 请不要发表危害祖国的非法信息！<br>
           	■ 请不要发表侵犯个人名誉的信息！<br>
           	■ 请不要发表不文明内容！<br>
           	■ 违反以上规则，后果自负！<br><br>
           	<a href="<%=contextPath%>/visit/topic/a/replyview?topicId=${param.topicId}">编辑模式回复</a>
           	<br><br>
		</td>
		<td align="center" bgcolor="white"><textarea rows="20" cols="80" name="content" onPropertyChange="checkLen()"></textarea></td>
		<td align="center" bgcolor="#F6F6F6" width="13%">
           	<font color="#7F7F7F">
           		最多：<b><span id="ContentAll" style="width:40">2000</span></b><br>
               	已用：<b><span id="ContentUse" style="width:40">0</span></b><br>
               	剩余：<b><span id="ContentRem" style="width:40">2000</span></b>
           	</font >
           	<hr style="color:gray"><br> 
			<input type="button" name="postsubmitb" value="提交回复" onclick="mysimplesubmit()"><br>
			<input type="reset" value="重新填写"><br>
		</td>
	</tr>
</table>
</form>
</center>