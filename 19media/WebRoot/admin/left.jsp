<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">


	<table width="194" height="428" border="0" cellpadding="0" cellspacing="0" background="image/b_left.gif">
      <tr>
        <td valign="top"><table width="173" height="62" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="59">&nbsp;</td>
          </tr>
        </table>
          <table width="90" border="0" align="center" cellpadding="0" cellspacing="0">
		  
            <tr>
              <td width="98" height="25"><a href="admin/user_query.jsp">用户管理</a></td>
            </tr>
			<%
	String types[]={"原创","影视","游戏","娱乐","动画","女性","搞笑","商品","体育","生活","科技","音乐"};
	for(String type:types){%>
            <tr>
              <td height="25">
			  <%
     out.print("<a href=admin/media_query.jsp?type="+type+">");
	 out.print(type);
	 out.print("</a>");
			  %>
			  
			  </td>
            </tr>
			<%}%>
            <tr>
              <td height="25"><a href="dealwith.jsp?sign=1" class="a2">安全退出</a></td>
            </tr>
          </table></td>
      </tr>
    </table>
	