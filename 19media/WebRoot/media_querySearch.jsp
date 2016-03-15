<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>

<jsp:directive.page import="com.wy.dao.MediaDao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.wy.model.MediaInfo"/>
<jsp:useBean id="pagination" class="com.wy.tool.MyPagination" scope="session"></jsp:useBean>	
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String key=request.getParameter("key");

MediaDao mediaDao =new MediaDao();
String str=request.getParameter("Page");
int Page=1;
List list =null ;
int num=0;
if(str==null){
	list= mediaDao.media_querySearch(key);
	num=list.size();
	int pagesize=4;      //指定每页显示的记录数
	list=pagination.getInitPage(list,Page,pagesize);     //初始化分页信息
}else{
	Page=pagination.getPage(str);
	list=pagination.getAppointPage(Page);     //获取指定页的数据
}



%>
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>钟毅播客-视频搜索<%=key %></title>
	<link href="css/style.css" type="text/css" rel="stylesheet">
  <body>
<jsp:include page="top.jsp" flush="true"/>
  <table width="1004" height="437" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="701" height="437" valign="top">
	  <br>
	  <table width="685" height="74" border="0" align="center" cellpadding="0" cellspacing="0" background="image/f_line3.gif">
        <tr>
          <td width="72" height="40">&nbsp;</td>
          <td width="613" class="wordBlack"><b>视频搜索(<%=num %>)个结果</b></td>
        </tr>
      </table>	     
<%
  for(int i = 0; i<list.size();i++){ 
   MediaInfo mediaInfo = (MediaInfo)list.get(i);
%>   
<table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="175" height="125" rowspan="4"><a href="media_show.jsp?id=<%=mediaInfo.getId()%>"  class="cannleLine"><img src="<%=mediaInfo.getMediaPic()%>" width="175" height="125"></a></td>
          <td width="291" height="30" align="center"><%=mediaInfo.getMediaTitle() %></td>
        </tr>
        <tr>
          <td height="30" align="center"><%=mediaInfo.getMediaUptime() %></td>
        </tr>
        <tr>
          <td height="30" align="center"><%=mediaInfo.getMediaInfo() %></td>
        </tr>   
</table>
<table width="685" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><hr></td>
  </tr>
</table><br>	
 <%}%> 
	   <%=pagination.printCtrl(Page,request.getRequestURI())%>	 
	 <br>
	   </td>
      <td width="303" valign="top">
	 
	  <jsp:include page="right.jsp" flush="true"/>      </td>
    </tr>
  </table>
  <jsp:include page="down.jsp" flush="true"/>
  

  </body>
</html>
