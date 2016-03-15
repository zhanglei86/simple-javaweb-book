<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>

<jsp:directive.page import="com.wy.dao.MediaDao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.wy.model.MediaInfo"/>
<jsp:useBean id="pagination" class="com.wy.tool.MyPagination" scope="session"></jsp:useBean>	
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String type=request.getParameter("type");
String info="��Ƶȫ�����";
if(null!=type){
type=new String(type.getBytes("ISO8859_1"), "GBK");
info=type+"-��Ƶ���";
}

MediaDao mediaDao =new MediaDao();
String str=request.getParameter("Page");
int Page=1;
List list =null ;
if(str==null){
	list= mediaDao.media_query(type);
	int pagesize=4;      //ָ��ÿҳ��ʾ�ļ�¼��
	list=pagination.getInitPage(list,Page,pagesize);     //��ʼ����ҳ��Ϣ
}else{
	Page=pagination.getPage(str);
	list=pagination.getAppointPage(Page);     //��ȡָ��ҳ������
}



%>
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>���㲥��-<%=info%></title>
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
          <td width="613" class="wordBlack"><b><%=info%></b></td>
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
