<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="com.wy.dao.UserDao"/>
<jsp:directive.page import="com.wy.model.UserInfo"/>
<jsp:directive.page import="java.util.List"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>钟毅播客----浏览用户</title>
</head>
<jsp:useBean id="pagination" class="com.wy.tool.MyPagination" scope="session"></jsp:useBean>
<%
UserDao userDao=new UserDao();
String str=request.getParameter("Page");
int Page=1;
List list =null ;
if(str==null){
	list=userDao.user_query();
	int pagesize=4;      //指定每页显示的记录数
	list=pagination.getInitPage(list,Page,pagesize);     //初始化分页信息
}else{
	Page=pagination.getPage(str);
	list=pagination.getAppointPage(Page);     //获取指定页的数据
}
%>


<body>
<jsp:include page="top.jsp" flush="true"/>
<table height="371" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="194" align="center" valign="top" background="image/b_back.gif">	
	<jsp:include page="left.jsp" flush="true"/>	</td>
    <td width="797" align="left" valign="top">
	<br>
	<table width="700" height="54" border="0" align="center" cellpadding="0" cellspacing="0" background="image/b_line.gif">
      <tr>
        <td width="85" height="28">&nbsp;</td>
        <td width="615"><p class="wordBlack"><b>用户浏览</b></p></td>
      </tr>
    </table>
    <br>
	<%
    for(int i=0;i<list.size();i++){ 
    UserInfo userInfo=(UserInfo)list.get(i);
    %>
	<table width="600" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
      <tr align="center">
        <td width="69" height="30">用户名</td>
        <td width="72">性别</td>
        <td width="103">QQ联系</td>
        <td width="130">来自</td>
        <td width="121">访问次数</td>
      
        </tr>
      <tr align="center" bgcolor="#FFFFFF">
        <td height="30"><%=userInfo.getUser_name()%></td>
        <td><%=userInfo.getUser_sex()%></td>
        <td><%=userInfo.getUser_oicq()%></td>
        <td><%=userInfo.getUser_from()%></td>
        <td><%=userInfo.getUser_hitNum()%>次</td>
     
        </tr>
    </table>	
	<table width="434" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
      <tr align="center">
        <td width="166" height="30">Email地址</td>
        <td width="177">注册时间</td>
        <td width="83">操作</td>
      </tr>
      <tr align="center" bgcolor="#FFFFFF">
        <td height="30"><%=userInfo.getUser_email()%></td>
        <td><%=userInfo.getUser_ctTime()%></td>
        <td><a href=dealwith.jsp?sign=5&id=<%=userInfo.getId()%>&username=<%=userInfo.getUser_name()%>>删除</a></td>
      </tr>
    </table>
	<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><hr></td>
      </tr>
    </table>
	<%} %>
	 <%=pagination.printCtrl(Page,request.getRequestURI())%>	
     <br>	 </td>
  </tr>
</table>
<jsp:include page="down.jsp" flush="true"/>
</body>
</html>
