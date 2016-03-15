<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="com.wy.dao.MediaRDao"/>
<jsp:directive.page import="com.wy.model.MediaRInfo"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:directive.page import="com.wy.dao.MediaDao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.wy.model.MediaInfo"/>
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>钟毅播客-视频播放</title>
	<link href="css/style.css" type="text/css" rel="stylesheet">
	<jsp:useBean id="pagination" class="com.wy.tool.MyPagination" scope="session"></jsp:useBean>	
<%

if(null==session.getAttribute("username")){
out.print("<script language=javascript>alert('您还没有登录，请您先登录！');window.location.href='index.jsp';</script>");
}


MediaDao mediaDao = new MediaDao();
Integer id=Integer.valueOf(request.getParameter("id"));
mediaDao.media_addNumber(id);
MediaInfo mediaInfo = mediaDao.media_query(id);



%>
  <body>
<jsp:include page="top.jsp" flush="true"/>
  <table width="1004" height="437" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="701" height="437" valign="top">
	  <br>
	  <table width="685" height="74" border="0" align="center" cellpadding="0" cellspacing="0" background="image/f_line3.gif">
        <tr>
          <td width="72" height="40">&nbsp;</td>
          <td width="613" class="wordBlack"><b>正在播放：<%=mediaInfo.getMediaTitle()%></b></td>
        </tr>
      </table>	  
   
   
   
   
   
      <table width="625" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
        <tr>
          <!-- 嵌入Flash播放器 -->
          <td rowspan="2" width="455" align="center">
            <object width="452" height="339" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000">
              <param name="movie" value="<%=basePath%>/videos/player.swf?fileName=<%=basePath%>/<%=mediaInfo.getMediaSrc()%>"/>
              <embed src="<%=basePath%>/videos/player.swf?fileName=<%=basePath%>/<%=mediaInfo.getMediaSrc()%>" width="40%" height="30%"></embed>
            </object>
          </td>
          <td height="30" style="padding-left:15">【视频信息】</td>
        </tr>
        <tr valign="top">
          <!-- 输出视频基本信息 -->
          <td valign="top" bgcolor="#FFFFFF"><br>
      观看：<%=mediaInfo.getLookCount()%> 次<br>
      <br>
   
  
      上传时间：<br>
      <br>
      <%=mediaInfo.getMediaUptime()%> </td>
        </tr>
        <tr>
          <td height="30" colspan="2" style="padding-left:30">视频介绍：</td>
        </tr>
        <tr valign="middle" bgcolor="#FFFFFF">
          <td height="30" colspan="2" ><%=mediaInfo.getMediaInfo()%></td>
        </tr>
      </table>
      <br>
	  
	  <table width="627" height="61" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
      <%
      
MediaRDao mediaRDao =new MediaRDao();  
String str=request.getParameter("Page");
int Page=1;
List list_mediaR =null ;
if(str==null){
	list_mediaR= mediaRDao.mediaR_query(mediaInfo.getId());	
	int pagesize=3;      //指定每页显示的记录数
	list_mediaR=pagination.getInitPage(list_mediaR,Page,pagesize);     //初始化分页信息
}else{
	Page=pagination.getPage(str);
	list_mediaR=pagination.getAppointPage(Page);     //获取指定页的数据
}

for(int i = 0;i<list_mediaR.size();i++){
MediaRInfo mediaRInfo = (MediaRInfo)list_mediaR.get(i);
%>      
        <tr>
          <td width="165"><%=mediaRInfo.getMediaR_author() %></td>
          <td width="241"><%=mediaRInfo.getMediaR_time()%></td>
         
        </tr>
        <tr>
          <td colspan="2" bgcolor="#FFFFFF"><%=mediaRInfo.getMediaR_content() %></td>
        </tr>
<%} %>         
      </table>
      
      <table width="627" height="30" border="0" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
        <tr>
          <td  bgcolor="#FFFFFF" align="right">   <%=pagination.printCtrl(Page,request.getRequestURI(),id)%></td>
        </tr>
      </table>
      
      
      
	   <br>
	   <form name="mediaRInfo" method="post" action="dealwith.jsp?sign=4" onsubmit="return checkEmpty(mediaRInfo)">
      <table width="374" height="123" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
        <tr>
          <td width="207" height="27">视频留言</td>
          <td width="154">留言人：<%=session.getAttribute("username")%><input type="hidden" value="<%=session.getAttribute("username")%>" name="mediaR_author"> <input type="hidden" value="<%=mediaInfo.getId()%>" name="mediaR_rootId"></td>
        </tr>
        <tr>
          <td colspan="2" bgcolor="#FFFFFF">
            <textarea name="mediaR_content" cols="60" rows="6" title="请输入留言信息"></textarea>
         </td>
        </tr>
        <tr>
          <td height="27" colspan="2" bgcolor="#FFFFFF"><div align="center">
            <input type="submit" name="Submit" value=" 留言 ">
          </div></td>
        </tr>
      </table>
	   </form>
      <br></td>
      <td width="303" valign="top">
	 
	  <jsp:include page="right.jsp" flush="true"/>      </td>
    </tr>
  </table>
  <jsp:include page="down.jsp" flush="true"/>
  

  </body>
</html>
