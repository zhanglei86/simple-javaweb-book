<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String f="";
String key="";
if(request.getAttribute("f")!=null){
	f=request.getAttribute("f").toString();
	key=request.getAttribute("key").toString();
}
%>
<jsp:useBean id="pagination" class="com.tools.MyPagination" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��֮����Ըǽ_�����б�</title>
<link href="CSS/index.css" rel="stylesheet"/>
<script language="javascript" src="JS/AjaxRequest.js"></script>
</head>

<body>
<div id="header"><img src="images/banner.jpg" width="932" height="112" /></div>
 <form id="form1" name="form1" method="post" action="scrip.do?action=scripList">
<div  id="navigation"> 
 <ul>
 <li><img src="images/home_ico.gif" width="15" height="17" /></li>
 <li><a href="scrip.do?action=scripQuery">������ҳ</a>&nbsp;&nbsp;&nbsp;&nbsp;</li>
 <li>   ��ѡ���ѯ������</li><li><select name="f" class="wenbenkuang" id="f">
        <option value="all" selected="selected">ȫ��</option>
        <option value="wishMan">ף������</option>
        <option value="wellWisher">ף����</option>
        <option value="content">��������</option>
    </select></li>
   <li> �ؼ��֣�</li><li><input name="key" type="text" id="key" size="40" class="navigation_input"/>&nbsp;</li>
    <li>
    <input type="image" name="imageField" src="images/btn_search.gif" class="noborder"/>
	</li>
 </ul>
</div>
</form>
<!--��ʼ��ʾ������Ϣ-->
<jsp:include page="scrollScrip.jsp"/>
<div id="main" style="padding-top:5px;">
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#3F873B">
      <tr>
        <td height="27" align="center" bgcolor="#D9EE9F">�������</td>
        <td align="center" bgcolor="#D9EE9F">ף������</td>
        <td align="center" bgcolor="#D9EE9F">ף����</td>
        <td align="center" bgcolor="#D9EE9F">��������</td>
        <td align="center" bgcolor="#D9EE9F">����ʱ��</td>
        <td align="center" bgcolor="#D9EE9F">����</td>
      </tr>
	<logic:iterate id="scrip" name="scripList1" type="com.model.ScripForm" scope="request" indexId="ind">
      <tr>
        <td height="27" bgcolor="#E8F3D1">&nbsp;
        <bean:write name="scrip" property="id" filter="true"/></td>
        <td bgcolor="#E8F3D1">&nbsp;
        <bean:write name="scrip" property="wishMan" filter="true"/></td>
        <td bgcolor="#E8F3D1">&nbsp;
        <bean:write name="scrip" property="wellWisher" filter="true"/></td>
        <td bgcolor="#E8F3D1">&nbsp;
        <bean:write name="scrip" property="content" filter="true"/></td>
        <td bgcolor="#E8F3D1">&nbsp;
        <bean:write name="scrip" property="sendTime" filter="true"/></td>
        <td bgcolor="#E8F3D1">&nbsp;
        <bean:write name="scrip" property="hits" filter="true"/></td>
      </tr>
	</logic:iterate>
  </table>
  <%=pagination.printCtrl(Integer.parseInt(request.getAttribute("Page").toString()),"scrip.do?action=scripList","&f="+f+"&key="+key)%> 
</div>
<!--��ʾ������Ϣ����-->
<jsp:include page="copyright.jsp"/>
</body>
</html>