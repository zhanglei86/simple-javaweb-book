<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="org.apache.lucene.search.Hits"/>
<jsp:directive.page import="org.apache.lucene.document.Document"/>
<jsp:directive.page import="com.wy.dao.Dao"/>
<jsp:directive.page import="org.apache.lucene.document.DateTools"/>
<link href="css/style.css" type="text/css" rel="stylesheet">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<title>钟毅搜索---显示搜索结果</title>
</head>
<%
Hits hits=null;
if(null!=request.getAttribute("hits")){
	hits=(Hits)request.getAttribute("hits");
}else{
	hits=(Hits)session.getAttribute("hits");
}
int number=hits.length();
//以下这段代码是计算多少页数
int pageNumber=number;
if(pageNumber%3==0){
pageNumber=pageNumber/3;
}else{
pageNumber=pageNumber/3+1;
}
//以下是计算当前页的页数
int count;
if(request.getParameter("count")==null){
count=0;
}else{
count=Integer.valueOf(request.getParameter("count"));
}
//以下显示多少条纪录
int start=count*3;//开始条数
int over=(count+1)*3;//结束条数
int lastCount=number-over;//还剩多少条记录
 if(lastCount<=0){
  over=number;
  }
session.setAttribute("hits",hits);
%>
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
    <td width="42%" class="wordspace">搜索内容：${sessionScope.seach}   </td>
    <td width="51%" class="wordspace">搜索时间：${sessionScope.overTime/1000}秒</td>
  </tr>
</table>



<br>
<br>
<table width="1003" height="182" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="95" height="182">&nbsp;</td>
    <td width="908" valign="top">
	  <%if(number==0){
	out.print("<font color=0001CB size=3 style=text-decoration:underline>没有你所要的结果！</font>");
	
}else{ 
for(int i=start;i<over;i++){
	Document document=hits.doc(i);

%>

<table width="800" border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#D7EAF8">
  <tr>
    <td height="30" bgcolor="#FFFFFF"><%=document.get("net_title")%></td>
  </tr>
  <tr>
    <td height="34" valign="top" bgcolor="#FFFFFF">&nbsp;&nbsp;&nbsp;&nbsp;<%=document.get("net_content")%></td>
  </tr>
  <tr>
    <td width="682" height="30" align="right" bgcolor="#FFFFFF"> &nbsp;</td>   
  </tr>
</table>
<br>

<%} }%>


<table  border="0" cellpadding="0" cellspacing="0">
      <tr align="center">
        <td width="800" height="37" >
          <%for(int i=0;i<pageNumber;i++){%>
          <%if(count==i){%>
      [<font color="#FF0000"><b><%=i+1%></b></font>]
      <%}else{%>
      [<a href="showInformation.jsp?count=<%=i%>"><b><%=i+1%></b></a>]
      <%}}%></td>
      </tr>
</table>	</td>
  </tr>
</table>




<br>
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


<table width="991" border="0" align="left" cellpadding="0" cellspacing="0">
  <tr align="center">
    <td width="173" class="wordspace">&nbsp;      </td>
    <td width="818" class="wordspace2"><font color="023795">技术服务热线：0431-84978981 传真：0431-84978982 企业邮箱：mingrisoft@mingrisoft.com <br>
        <br>
        <br>
公司地址：吉林省长春市亚泰广场C座 吉ICP备 ******** <br>
<br>
<br>
Copyright &copy;www.mrbook.com All Rights Reserved! </font></td>
  </tr>
</table>



</body>
</html>
