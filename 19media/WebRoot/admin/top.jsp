<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" type="text/css" rel="stylesheet">
<script language="javascript" language="javascript" src="js/validate.JS"></script>



<%
if(null==session.getAttribute("info")){
out.print("<script language=javascript>alert('����������Ѿ��Ͽ���');window.location.href='index.jsp';</script>");
}
%>


<table width="989" height="132" border="0" align="center" cellpadding="0" cellspacing="0" background="image/b_top.gif">
  <tr>
    <td valign="top">
	
	<table width="823" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="116" valign="bottom">
	<font color="#FFFFFF"><b>
   ��ǰ��¼����Ա�� ${sessionScope.info.account}&nbsp;&nbsp;&nbsp;&nbsp;  
                               ���ʴ�����${sessionScope.info.visit}&nbsp;&nbsp;&nbsp;&nbsp; 
    </b></font>
                           <a href="admin/media_add.jsp" class="a1">�ϴ���Ƶ�ļ�</a>&nbsp;&nbsp;&nbsp;&nbsp; 
						  <a href="admin/mainPage.jsp" class="a1">������ҳ</a>

    </td>
  </tr>
</table>	</td>
  </tr>
</table>
