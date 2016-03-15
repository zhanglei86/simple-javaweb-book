<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
	session.setAttribute("activation",true);
	String contextPath=request.getContextPath();
%>

<html>
	<title>论坛-后台登录</title>
	<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
	<script type="text/javascript" src="<%=contextPath%>/js/login.js"></script>
	<body onload="loginform.membername.focus()">
	<center>
	<table border="0" width="95%" cellpadding="0" cellspacing="6">
    	<tr><td align="center"><%@ include file="top.jsp" %></td></tr>
        <tr><td><b><a href="<%=contextPath%>/index">前台首页</a></b></td></tr>
         <tr bgcolor="#E0E0E0" height="100">
        	<td style="border:1 solid;border-Color:#999999;padding-left:5">
        		●只有注册为会员才能登录本论坛，请确认您已经注册！<br>
        		●若您没有注册，请单击这里进行<a href="<%=contextPath%>/regview">注册</a>！<br>
        		●只有版主（包括版主）以上级别的会员才能登录后台！<br>
        		●登录成功后，会根据会员身份提供相应的功能！
        	</td>
        </tr>
        <tr><td><font color="red"><b><span id="checkmessage"></span></b></font></td></tr>
        <tr>
       		<form action="<%=contextPath%>/loginrun" method="post" name="loginform">
       		<input type="hidden" name="autoforward" value="${requestScope.autoforward}"> 
        	<td align="center">
				<table bgcolor="#999999" border="0" width="100%" style="word-break:break-all;margin-bottom:10" cellpadding="3" cellspacing="1">
					<tr height="25" class="listhead"><td colspan="2">请输入会员名称、会员密码和验证码进行登录</td></tr>
			<tr height="25" bgcolor="white">
				<td width="40%" align="right">会员名称：</td>
				<td><input type="text" name="membername" size="25" class="login"></td>
			</tr>
			<tr height="25" bgcolor="#F6F6F6">
				<td align="right">会员密码：</td>
				<td><input type="password" name="memberpswd" size="25" class="login"></td>
			</tr>
			<tr height="25" bgcolor="white">
				<td align="right">验证码：</td>
				<td><input type="text" name="inverifycode" size="25" class="login"></td>
			</tr>
			<tr bgcolor="#F6F6F6">
				<td align="right"><a href="javascript:regetCode();">重新获取验证码</a></td>
				<td valign="middle">
					<script language="javascript" type="text/javascript">
						function regetCode(){var tag = document.getElementById("code"); tag.src = tag.src;}
					</script>
					<img id="code" src="<%=contextPath%>/makeverifycode">
				</td>
			</tr>
			<tr bgcolor="lightgrey">
				<td colspan="2" align="center">
					<input type="button" name="submitb"  value="登录" class="login" onclick="myloginsubmit()">
					<input type="reset" value="重置" class="login">
				</td>
			</tr>
		</table>
       	</td>
		</form>
        </tr>
    	<tr><td align="center"><%@ include file="end.jsp" %></td></tr>
   	</table>
	</center>
</body>
</html>