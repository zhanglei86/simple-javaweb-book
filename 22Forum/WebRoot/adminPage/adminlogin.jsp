<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
	session.setAttribute("activation",true);
	String contextPath=request.getContextPath();
%>

<html>
	<title>��̳-��̨��¼</title>
	<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
	<script type="text/javascript" src="<%=contextPath%>/js/login.js"></script>
	<body onload="loginform.membername.focus()">
	<center>
	<table border="0" width="95%" cellpadding="0" cellspacing="6">
    	<tr><td align="center"><%@ include file="top.jsp" %></td></tr>
        <tr><td><b><a href="<%=contextPath%>/index">ǰ̨��ҳ</a></b></td></tr>
         <tr bgcolor="#E0E0E0" height="100">
        	<td style="border:1 solid;border-Color:#999999;padding-left:5">
        		��ֻ��ע��Ϊ��Ա���ܵ�¼����̳����ȷ�����Ѿ�ע�ᣡ<br>
        		������û��ע�ᣬ�뵥���������<a href="<%=contextPath%>/regview">ע��</a>��<br>
        		��ֻ�а������������������ϼ���Ļ�Ա���ܵ�¼��̨��<br>
        		���¼�ɹ��󣬻���ݻ�Ա����ṩ��Ӧ�Ĺ��ܣ�
        	</td>
        </tr>
        <tr><td><font color="red"><b><span id="checkmessage"></span></b></font></td></tr>
        <tr>
       		<form action="<%=contextPath%>/loginrun" method="post" name="loginform">
       		<input type="hidden" name="autoforward" value="${requestScope.autoforward}"> 
        	<td align="center">
				<table bgcolor="#999999" border="0" width="100%" style="word-break:break-all;margin-bottom:10" cellpadding="3" cellspacing="1">
					<tr height="25" class="listhead"><td colspan="2">�������Ա���ơ���Ա�������֤����е�¼</td></tr>
			<tr height="25" bgcolor="white">
				<td width="40%" align="right">��Ա���ƣ�</td>
				<td><input type="text" name="membername" size="25" class="login"></td>
			</tr>
			<tr height="25" bgcolor="#F6F6F6">
				<td align="right">��Ա���룺</td>
				<td><input type="password" name="memberpswd" size="25" class="login"></td>
			</tr>
			<tr height="25" bgcolor="white">
				<td align="right">��֤�룺</td>
				<td><input type="text" name="inverifycode" size="25" class="login"></td>
			</tr>
			<tr bgcolor="#F6F6F6">
				<td align="right"><a href="javascript:regetCode();">���»�ȡ��֤��</a></td>
				<td valign="middle">
					<script language="javascript" type="text/javascript">
						function regetCode(){var tag = document.getElementById("code"); tag.src = tag.src;}
					</script>
					<img id="code" src="<%=contextPath%>/makeverifycode">
				</td>
			</tr>
			<tr bgcolor="lightgrey">
				<td colspan="2" align="center">
					<input type="button" name="submitb"  value="��¼" class="login" onclick="myloginsubmit()">
					<input type="reset" value="����" class="login">
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