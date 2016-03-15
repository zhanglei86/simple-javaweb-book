<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.wy.form.Word"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/style.css" type="text/css" rel="stylesheet">
<title>钟毅搜索---站内关键搜索结果</title>

</head>

<body>
<table width="876" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="256" height="99" rowspan="2"><img src="images/ico.jpg" width="256" height="99"></td>
    <td width="754" height="102" valign="bottom">
	<form name="form1" method="post" action="operationServlet?info=searchResult">
	<table width="595" height="33" border="0" align="left" cellpadding="0" cellspacing="0">
      <tr>
        <td width="437"><input name="queryString" type="text" size="65" height="20"></td>
        <td width="90"> <input type="submit" name="Submit" value="  搜一下  "></td>
      </tr>
    </table>
	</form>
	
	</td>
  </tr>
  <tr>
    <td height="13">&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="D7EAF8">
  <tr>
    <td width="7%" height="26">&nbsp;</td>
    <td width="93%" class="wordspace">站内关键字搜索结果</td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td width="7%" height="75">&nbsp;</td>
    <td class="wordspace">
	<font color="0001CB" size="3">
	钟毅中文搜索风云榜每天对上亿次进行分析、权威、全面、准确、精彩！<br><br>
      <br>
凸现热点，纵横风云，挖掘在我们身边的新奇和惊喜，通过搜索，把握世界 。
</font>
</td>
  </tr>
</table>



<table width="1190" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="88" height="61">&nbsp;</td>
    <td width="454" class="wordspace">
	<font color="0001CB" size="3" style="text-decoration:underline ">
	热门搜索排行</font>
	
	</td>
    <td width="648" class="wordspace">
	<font color="0001CB" size="3" style="text-decoration:underline ">
	新关键字排行	</font>	</td>
  </tr>
</table>
<table width="1190" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="88" height="65">&nbsp;</td>
    <td width="454" valign="top">
	  <table border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
  <tr align="center" bgcolor="#FFFFFF" class="wordspace">
    <td width="44" height="30"><font color="#FF0000" style="font-weight:bold ">排行</font></td>
    <td width="186"><font color="#FF0000" style="font-weight:bold ">关键字</font></td>
    <td width="70"><font color="#FF0000" style="font-weight:bold ">次数</font></td>
	<td width="70"><font color="#FF0000" style="font-weight:bold ">搜索结果</font></td>
  </tr>
<%
List list1=(List)request.getAttribute("list1");
int length=list1.size();
if(length>10){
	length = 10;
}
for(int i = 0 ; i < length ;i++){
	Word word = (Word)list1.get(i);
%>
  
    <tr align="center" bgcolor="#FFFFFF" class="wordspace">
    <td width="44" height="30"><%=i+1%></td>
    <td width="186"><%=word.getWord_name() %></td>
    <td width="70"><%=word.getWord_number() %></td>
	 <td width="70"><%=word.getWord_result() %></td>
  </tr>
<%} %>
</table>
	
	
	
    </td>
    <td width="648" valign="top">
	 <table border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
  <tr align="center" bgcolor="#FFFFFF" class="wordspace">
    <td width="44" height="30"><font color="#FF0000" style="font-weight:bold ">排行</font></td>
    <td width="186"><font color="#FF0000" style="font-weight:bold ">关键字</font></td>
    <td width="70"><font color="#FF0000" style="font-weight:bold ">次数</font></td>
	<td width="70"><font color="#FF0000" style="font-weight:bold ">搜索结果</font></td>
  </tr>
<%
List list2=(List)request.getAttribute("list2");
int length2=list2.size();
if(length2>10){
	length2 = 10;
}
for(int i = 0 ; i < length2 ;i++){
	Word word = (Word)list2.get(i);
%>
  
    <tr align="center" bgcolor="#FFFFFF" class="wordspace">
    <td width="44" height="30"><%=i+1%></td>
    <td width="186"><%=word.getWord_name() %></td>
    <td width="70"><%=word.getWord_number() %></td>
	 <td width="70"><%=word.getWord_result() %></td>
  </tr>
<%} %>
</table>
	
	
	
	 </td>
  </tr>
</table>
<br><br>
<form name="form1" method="post" action="operationServlet?info=searchResult">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="D7EAF8">
  <tr>
    <td width="256" height="40">&nbsp;</td>
    <td width="1007">
		<table width="595" height="33" border="0" align="left" cellpadding="0" cellspacing="0">
      <tr>
        <td width="437"><input name="queryString" type="text" size="65" height="20"></td>
        <td width="90"> <input type="submit" name="Submit" value="  搜一下  "></td>
      </tr>
    </table>
	</td>
  </tr>
</table>
</form>


</body>
</html>
