<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>���㲥��----��̨��¼</title>
</head>
<link href="css/style.css" type="text/css" rel="stylesheet">
<script language="javascript" language="javascript" src="js/validate.JS"></script>
<body>
<center>


<table width="760" height="570" border="0" cellpadding="0" cellspacing="0" background="image/admin.gif">
  <tr>
    <td valign="top"><table width="442" height="235" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="235">&nbsp;</td>
      </tr>
    </table>
	
	
	
<form name="form" method="post" action="dealwith.jsp?sign=0"  onSubmit="return checkEmpty(form)">
<table width="341" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="80" height="30" align="right">��&nbsp;&nbsp;�ţ�</td>
    <td colspan="2"><input type="text" name="account" title="�������˺ţ�"></td>
  </tr>
  <tr>
    <td height="30" align="right">��&nbsp;&nbsp;�룺</td>
    <td colspan="2"><input type="password" name="password" title="���������룡"></td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td height="30" colspan="2">
      <input type="image" name="Submit" value="��¼" src="image/land.gif">
&nbsp;&nbsp;
<a href="#"><img src="image/back.gif" class="cannleLine"></a></td>
  </tr>
</table>
  </form>
	
	
	
	
	
	
	
	
	</td>
  </tr>
</table>
</center>
</body>
</html>
