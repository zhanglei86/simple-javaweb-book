<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="com.wy.dao.MediaDao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.wy.model.MediaInfo"/>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<jsp:useBean id="pagination" class="com.wy.tool.MyPagination" scope="session"></jsp:useBean>	
<%
request.setCharacterEncoding("gb2312");


String media_type=request.getParameter("type");
media_type=new String(media_type.getBytes("ISO8859_1"), "GBK");



MediaDao mediaDao =new MediaDao();
String str=request.getParameter("Page");
int Page=1;
List list =null ;
if(str==null){
	list= mediaDao.media_query(media_type);
	int pagesize=3;      //指定每页显示的记录数
	list=pagination.getInitPage(list,Page,pagesize);     //初始化分页信息
}else{
	Page=pagination.getPage(str);
	list=pagination.getAppointPage(Page);     //获取指定页的数据
}
%>		
<title>钟毅播客----<%=media_type%>类型视频浏览</title>
<link href="css/style.css" type="text/css" rel="stylesheet">
</head>

<body>
<jsp:include page="top.jsp" flush="true"/>


<table height="371" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="194" align="center" valign="top" background="image/b_back.gif">
	
	<jsp:include page="left.jsp" flush="true"/>	</td>
    <td width="797" align="center" valign="top">
    
<br>
    
      <table width="700" height="54" border="0" cellpadding="0" cellspacing="0" background="image/b_line.gif">
        <tr>
          <td width="85" height="28">&nbsp;</td>
          <td width="615"><p class="wordBlack"><b><%=media_type%>类型视频浏览</b></p></td>
        </tr>
      </table>
<br>
      <%
  for(int i = 0; i<list.size();i++){ 
   MediaInfo mediaInfo = (MediaInfo)list.get(i);
%>   

<table border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
        <tr>
          <td width="175" height="125" rowspan="4"><a href="admin/media_queryOne.jsp?id=<%=mediaInfo.getId()%>"><img src="<%=mediaInfo.getMediaPic()%>" width="175" height="125"></a></td>
          <td width="291" height="30" align="center" bgcolor="#FFFFFF"><%=mediaInfo.getMediaTitle() %></td>
        </tr>
        <tr>
          <td height="30" align="center" bgcolor="#FFFFFF"><%=mediaInfo.getMediaUptime() %></td>
        </tr>
        <tr>
          <td height="30" align="center" bgcolor="#FFFFFF"><%=mediaInfo.getMediaInfo() %></td>
        </tr>
        <tr>
          <td height="30" align="center" bgcolor="#FFFFFF">&nbsp;</td>
        </tr>
</table>
<table width="700" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><hr></td>
  </tr>
</table>	
 <%}%>   
    <%=pagination.printCtrl(Page,request.getRequestURI(),media_type)%>   <br> </td>
  </tr>
</table>


<jsp:include page="down.jsp" flush="true"/>
</body>
</html>
