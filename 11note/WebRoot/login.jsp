<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<html>
  <head>
    <title>�û���¼</title>
	<link href="CSS/style.css" rel="stylesheet"/>
<script language="javascript"> 
function check(){
   if(form1.userName.value==""){
     alert("�������û�����");form1.userName.focus();return false;
   }
   if(form1.pwd.value==""){
     alert("���������룡");form1.pwd.focus();return false;
   }
}
</script> 

  </head>
  
  <body>
  <form name="form1" method="post" action="UserServlet?action=login" onSubmit="return check()">
    <table width="583" height="281" border="0" cellpadding="0" cellspacing="0" background="images/bg_login.jpg">
      <tr>
        <td height="173" colspan="2" align="center">&nbsp;</td>
      </tr>
      <tr>
        <td width="200" height="27" align="right">��&nbsp;��&nbsp;����&nbsp;</td>
        <td width="383"><input name="userName" type="text" id="userName"></td>
      </tr>
      <tr>
        <td height="27" align="right">��&nbsp;&nbsp;&nbsp;&nbsp;�룺&nbsp;</td>
        <td><input name="pwd" type="password" id="pwd"></td>
      </tr>
      <tr>
        <td height="25" colspan="2" align="center"><input name="Submit2" type="submit" class="btn_bg" value="ȷ��">
        &nbsp;
        <input name="Submit3" type="reset" class="btn_bg" value="����">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      </tr>
      <tr>
        <td height="25" colspan="2" align="center">&nbsp;</td>
      </tr>
    </table>
  
  </form>
  </body>
</html>
