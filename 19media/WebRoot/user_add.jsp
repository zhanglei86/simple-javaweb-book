<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>钟毅播客-用户注册</title>
	<link href="css/style.css" type="text/css" rel="stylesheet">
  <body>
<jsp:include page="top.jsp" flush="true"/>
  <table width="1004" height="437" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="701" height="437" valign="top">
	  <br>
	  <table width="685" height="74" border="0" align="center" cellpadding="0" cellspacing="0" background="image/f_line3.gif">
        <tr>
          <td width="72" height="40">&nbsp;</td>
          <td width="613" class="wordBlack"><b>用户注册</b></td>
        </tr>
      </table>	  
      <form name="userInfo" method="post" action="dealwith.jsp?sign=2" onSubmit="return checkEmpty(userInfo)">
      <table width="423" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="86" height="40">用户名:</td>
          <td width="356"><input name="user_name" type="text" title="请输入用户名！" size="40"></td>
        </tr>
        <tr>
          <td height="40">密码:</td>
          <td><input name="user_pswd" type="password" title="请输入用户登录密码！" size="40"></td>
        </tr>   
		  <tr>
          <td height="40">性别:</td>
          <td>
		  <input type="radio" name="user_sex" value="男" checked class="button">男&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="user_sex" value="女" class="button">女
		  
		  </td>
        </tr>
        <tr>
          <td height="40">QQ号码:</td>
          <td><input name="user_oicq" type="text" title="请输入QQ号码！" size="40"></td>
        </tr>
        <tr>
          <td height="40">Email地址:</td>
          <td><input name="user_email" type="text" title="请输入Email地址！" size="40"></td>
        </tr>
        <tr>
          <td height="40">来自:</td>
          <td><input name="user_from" type="text" title="请输入用户来自地方！" size="40"></td>
        </tr>
		 <tr>
          <td height="40">&nbsp;</td>
          <td>
            <input type="submit" name="Submit" value=" 注册 ">
            <input type="reset" name="Submit2" value=" 重置 ">
          </td>
        </tr>
      </table>	
	  </form>	  </td>
      <td width="303" valign="top">
	 
	  <jsp:include page="right.jsp" flush="true"/>      </td>
    </tr>
  </table>
  <jsp:include page="down.jsp" flush="true"/>
  

  </body>
</html>
