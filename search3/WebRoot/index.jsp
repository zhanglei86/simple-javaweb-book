<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>钟毅搜索</title>
<link href="css/style.css" type="text/css" rel="stylesheet">
<script language="javascript" type="text/javascript" src="js/js.js"></script>
</head>
<body>

<table width="210" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="images/top_index.jpg" width="308" height="163"></td>
  </tr>
</table>
<form name="form1" method="post" action="operationServlet?info=searchResult">
<table width="801" height="56" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="F7F7F7">
  <tr>
    <td height="56">
	<table width="595" height="33" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="437">
		<input name="queryString" type="text" size="65" height="20" onKeyUp="createRequest()">

			<div style="position:absolute;" id="pop">
        <table id="key_table" bgcolor="#FAFAFF" border="0" cellspacing="0" cellpadding="0"/>            
            <tbody id="key_table_body"></tbody>
        </table>
    	</div>

		
		
		</td>
        <td width="90"> <input type="submit" name="Submit" value="  搜一下  "></td>
      </tr>
 </table>

	</td>
  </tr>
</table>
</form>
<table width="940" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr align="center">
    <td height="35" class="wordspace">
	 <a href="#">编程词典网</a> | <a href="#">明日图书网</a>  | <a href="operationServlet?info=searchWordResult">站内关键字风云榜</a> |  <a href="operationServlet?info=searchWordAll">站内关键字全部查询</a> |
	<a href="operationServlet?info=createSearch">生成新的搜索引擎</a>
	</td>
  </tr>
  <tr align="center">
    <td height="129" valign="bottom" class="wordspace2"><font color="023795">技术服务热线：0431-84978981 传真：0431-84978982 企业邮箱：mingrisoft@mingrisoft.com <br>
      <br> <br>
公司地址：吉林省长春市亚泰广场C座 吉ICP备 ******** <br>
<br> <br>
Copyright &copy;www.mrbook.com All Rights Reserved! </font></td>
  </tr>
</table>
<table width="244" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr align="center">
    <td valign="bottom" class="wordspace"><font color="023795">${requestScope.result}</font></td>
  </tr>
</table>
</body>
</html>
