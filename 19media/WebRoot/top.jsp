<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="com.wy.dao.MediaDao"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

 <script language="javascript" src="js/validate.JS" type="text/javascript"></script>
  <table width="1004" height="40" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="48"><img src="image/f_top1.gif" width="1004" height="79"></td>
    </tr>
  </table>
 
  <table width="1004" height="65" border="0" align="center" cellpadding="0" cellspacing="0" background="image/f_top2.gif">
    <tr>
      <td height="40" align="center">
	  <br>
	  <a href="index.jsp" class="a3">��ҳ</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=ԭ��" class="a3">ԭ��</a>	  
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=Ӱ��" class="a3">Ӱ��</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=��Ϸ" class="a3">��Ϸ</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=����" class="a3">����</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=����" class="a3">����</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=Ů��" class="a3">Ů��</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=��Ц" class="a3">��Ц</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=��Ʒ" class="a3">��Ʒ</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=����" class="a3">����</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=����" class="a3">����</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=�Ƽ�" class="a3">�Ƽ�</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=����" class="a3">����</a></td>
    </tr>
  </table>
  <table width="1004" height="69" border="0" align="center" cellpadding="0" cellspacing="0" background="image/f_top3.gif">
    <tr>
      <td width="580" height="30">

	  <form action="media_querySearch.jsp" method="post"  name="search" onsubmit="return checkEmpty(search)">
	  <table width="437" height="50" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="250" valign="bottom"><input name="key" type="text" size="45" title="�����������Ĺؼ���"></td>
          <td width="99" valign="bottom"><input type="image" name="Submit" src="image/sure.gif"></td>
        </tr>
      </table>
	  </form>	  </td>
      <td width="424" valign="top"><table width="356" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="58" valign="bottom">
          <%
          MediaDao mediaDao = new MediaDao();
          String type=null;
          %>
          ����<%=mediaDao.media_queryNumber(type)%>����Դ��
          
          </td>
        </tr>
      </table></td>
    </tr>
  </table>
