<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>���㲥��-�û�ע��</title>
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
          <td width="613" class="wordBlack"><b>�û�ע��</b></td>
        </tr>
      </table>	  
      <form name="userInfo" method="post" action="dealwith.jsp?sign=2" onSubmit="return checkEmpty(userInfo)">
      <table width="423" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="86" height="40">�û���:</td>
          <td width="356"><input name="user_name" type="text" title="�������û�����" size="40"></td>
        </tr>
        <tr>
          <td height="40">����:</td>
          <td><input name="user_pswd" type="password" title="�������û���¼���룡" size="40"></td>
        </tr>   
		  <tr>
          <td height="40">�Ա�:</td>
          <td>
		  <input type="radio" name="user_sex" value="��" checked class="button">��&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="user_sex" value="Ů" class="button">Ů
		  
		  </td>
        </tr>
        <tr>
          <td height="40">QQ����:</td>
          <td><input name="user_oicq" type="text" title="������QQ���룡" size="40"></td>
        </tr>
        <tr>
          <td height="40">Email��ַ:</td>
          <td><input name="user_email" type="text" title="������Email��ַ��" size="40"></td>
        </tr>
        <tr>
          <td height="40">����:</td>
          <td><input name="user_from" type="text" title="�������û����Եط���" size="40"></td>
        </tr>
		 <tr>
          <td height="40">&nbsp;</td>
          <td>
            <input type="submit" name="Submit" value=" ע�� ">
            <input type="reset" name="Submit2" value=" ���� ">
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
