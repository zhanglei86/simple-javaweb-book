<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="com.wy.dao.*"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.wy.model.*"/>

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
MediaDao mediaDao = new MediaDao();
Integer id=Integer.valueOf(request.getParameter("id"));
MediaInfo mediaInfo = mediaDao.media_query(id);
%>
<title>视频观看</title>
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
          <td width="615"><p class="wordBlack"><b>正在播放视频：<%=mediaInfo.getMediaTitle()%></b></p></td>
        </tr>
    </table>
	<br>
	
	
	
	
	<table width="625" border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
		
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
				观看：<%=mediaInfo.getLookCount()%> 次<br><br>			
				上传时间：<br><br><%=mediaInfo.getMediaUptime()%>		  </td>
		</tr>
		<tr><td height="30" style="padding-left:30">视频介绍：</td>
		  <td height="30" style="padding-left:30"><a href="dealwith.jsp?sign=6&id=<%=mediaInfo.getId()%>">删除</a></td>
		</tr>
		<tr valign="middle" bgcolor="#FFFFFF"><td height="30" colspan="2" ><%=mediaInfo.getMediaInfo()%></td>
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
          <td width="176"><%=mediaRInfo.getMediaR_author() %></td>
          <td width="315"><%=mediaRInfo.getMediaR_time()%></td>
         
          <td width="118"><a href="dealwith.jsp?sign=7&id=<%=mediaRInfo.getId()%>&media_id=<%=id%>">删除</a></td>
        </tr>
        <tr>
          <td colspan="3" bgcolor="#FFFFFF"><%=mediaRInfo.getMediaR_content() %></td>
        </tr>
<%} %>         
      </table>
      
      <table width="627" height="30" border="0" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
        <tr>
          <td  bgcolor="#FFFFFF" align="right">   <%=pagination.printCtrl(Page,request.getRequestURI(),id)%></td>
        </tr>
      </table>
	
	
	
	
	
	
	<br>
	
	
	
    </td>
  </tr>
</table>


<jsp:include page="down.jsp" flush="true"/>
</body>
</html>
