<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="com.wy.dao.MediaDao"/>
<jsp:directive.page import="com.wy.model.MediaInfo"/>
<jsp:directive.page import="java.util.List"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>钟毅播客-主界面</title>
	<link href="css/style.css" type="text/css" rel="stylesheet">
	
	
<% 
MediaDao mediaDao = new MediaDao();

String type=null;
List list= mediaDao.media_query(type);
int size1=list.size();
if(size1>3){
size1=3;
}



String sql ="select * from tb_media";
List list1= mediaDao.media_queryAuto(sql);
int size2=list1.size();
if(size2>3){
size2=3;
}

%>
	
	
	
  <body>
<jsp:include page="top.jsp" flush="true"/>
  <table width="1004" height="437" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="701" height="437" valign="top">
	  <br>	  <table width="210" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="image/f_new.gif" width="681" height="83"></td>
        </tr>
      </table>
      <table width="681" height="158" border="0" align="center" cellpadding="0" cellspacing="0" background="image/f_line.gif">
        		
        <tr align="center">
       <%
       for(int i= 0;i<size1;i++){
       MediaInfo mediaInfo = (MediaInfo)list.get(i);
        %>
          <td height="158">
         <a href="media_show.jsp?id=<%=mediaInfo.getId()%>"  class="cannleLine"> <img src="<%=mediaInfo.getMediaPic()%>" width="125" height="125"></a><br>
          <br>
          <%=mediaInfo.getMediaTitle()%>          </td>
        <%}%>
        </tr>
      </table>
      <table width="210" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="image/f_line1.gif" width="681" height="12"></td>
        </tr>
      </table>        
      <br>	  <table width="685" height="74" border="0" align="center" cellpadding="0" cellspacing="0" background="image/f_line3.gif">
          <tr>
            <td width="72" height="40">&nbsp;</td>
            <td width="613" class="wordBlack"><b>视频浏览</b></td>
          </tr>
        </table>
		
		
		
		<%
  for(int i = 0; i<size2;i++){ 
   MediaInfo mediaInfo = (MediaInfo)list1.get(i);
%>   
<table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="175" height="125" rowspan="4"> <a href="media_show.jsp?id=<%=mediaInfo.getId()%>"  class="cannleLine"><img src="<%=mediaInfo.getMediaPic()%>" width="175" height="125"></a></td>
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
		    <table width="520" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="515" height="20" align="right"><a href="media_query.jsp">更多...</a></td>
          </tr>
        </table>
		<br>
		
		
		
		
		
		
		
		
</td>
      <td width="303" valign="top">
	 
	  <jsp:include page="right.jsp" flush="true"/>      </td>
    </tr>
  </table>
  <jsp:include page="down.jsp" flush="true"/>
  

  </body>
</html>
