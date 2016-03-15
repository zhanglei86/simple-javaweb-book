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
	  <a href="index.jsp" class="a3">首页</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=原创" class="a3">原创</a>	  
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=影视" class="a3">影视</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=游戏" class="a3">游戏</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=娱乐" class="a3">娱乐</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=动画" class="a3">动画</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=女性" class="a3">女性</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=搞笑" class="a3">搞笑</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=商品" class="a3">商品</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=体育" class="a3">体育</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=生活" class="a3">生活</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=科技" class="a3">科技</a>
	  &nbsp;&nbsp;<font color="#FFFFFF">|</font>&nbsp;&nbsp;<a href="media_query.jsp?type=音乐" class="a3">音乐</a></td>
    </tr>
  </table>
  <table width="1004" height="69" border="0" align="center" cellpadding="0" cellspacing="0" background="image/f_top3.gif">
    <tr>
      <td width="580" height="30">

	  <form action="media_querySearch.jsp" method="post"  name="search" onsubmit="return checkEmpty(search)">
	  <table width="437" height="50" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="250" valign="bottom"><input name="key" type="text" size="45" title="请输入搜索的关键字"></td>
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
          共有<%=mediaDao.media_queryNumber(type)%>个资源！
          
          </td>
        </tr>
      </table></td>
    </tr>
  </table>
