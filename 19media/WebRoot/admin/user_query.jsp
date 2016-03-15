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
<title>���㲥��----����û�</title>
</head>
<jsp:useBean id="pagination" class="com.wy.tool.MyPagination" scope="session"></jsp:useBean>
<%
UserDao userDao=new UserDao();
String str=request.getParameter("Page");
int Page=1;
List list =null ;
if(str==null){
	list=userDao.user_query();
	int pagesize=4;      //ָ��ÿҳ��ʾ�ļ�¼��
	list=pagination.getInitPage(list,Page,pagesize);     //��ʼ����ҳ��Ϣ
}else{
	Page=pagination.getPage(str);
	list=pagination.getAppointPage(Page);     //��ȡָ��ҳ������
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
        <td width="615"><p class="wordBlack"><b>�û����</b></p></td>
      </tr>
    </table>
    <br>
	<%
    for(int i=0;i<list.size();i++){ 
    UserInfo userInfo=(UserInfo)list.get(i);
    %>
	<table width="600" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
      <tr align="center">
        <td width="69" height="30">�û���</td>
        <td width="72">�Ա�</td>
        <td width="103">QQ��ϵ</td>
        <td width="130">����</td>
        <td width="121">���ʴ���</td>
      
        </tr>
      <tr align="center" bgcolor="#FFFFFF">
        <td height="30"><%=userInfo.getUser_name()%></td>
        <td><%=userInfo.getUser_sex()%></td>
        <td><%=userInfo.getUser_oicq()%></td>
        <td><%=userInfo.getUser_from()%></td>
        <td><%=userInfo.getUser_hitNum()%>��</td>
     
        </tr>
    </table>	
	<table width="434" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
      <tr align="center">
        <td width="166" height="30">Email��ַ</td>
        <td width="177">ע��ʱ��</td>
        <td width="83">����</td>
      </tr>
      <tr align="center" bgcolor="#FFFFFF">
        <td height="30"><%=userInfo.getUser_email()%></td>
        <td><%=userInfo.getUser_ctTime()%></td>
        <td><a href=dealwith.jsp?sign=5&id=<%=userInfo.getId()%>&username=<%=userInfo.getUser_name()%>>ɾ��</a></td>
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
