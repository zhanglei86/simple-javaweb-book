<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>���㲥��----��̨�ϴ���Ƶ</title>

</head>

<body>
<jsp:include page="top.jsp" flush="true"/>
<table height="371" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="194" align="center" valign="top" background="image/b_back.gif">	
	<jsp:include page="left.jsp" flush="true"/>	</td>
    <td width="797" align="center" valign="top">
	
	<br>
	<table width="700" height="54" border="0" align="center" cellpadding="0" cellspacing="0" background="image/b_line.gif">
      <tr>
        <td width="85" height="28">&nbsp;</td>
        <td width="615"><p class="wordBlack"><b>��ѡ��Ҫ�ϴ�����Ƶ</b></p></td>
      </tr>
    </table>
	<br>
	<form action="mediaServlet?sign=0" method="post" enctype="multipart/form-data" name="form" onsubmit="return checkEmpty(form)">
	<table width="560" border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="E7EAF9">
      <tr>
        <td width="73" height="40">ѡ����Ƶ��</td>
        <td width="442" bgcolor="#FFFFFF"><input name="src" type="file" id="src" title="��ѡ����Ƶ�ļ�!"></td>
      </tr>
      <tr>
        <td height="80">��Ƶ���ͣ�</td>
        <td bgcolor="#FFFFFF">
		<%
		String types[]={"ԭ��","Ӱ��","��Ϸ","����","����","Ů��","��Ц","��Ʒ","����","����","�Ƽ�","����"};
		for(int i=0;i<types.length;i++){
		%>		
		<input type="radio" name="type" value="<%=types[i]%>" class="button"  <%if(i==0) out.print("checked");%> >&nbsp;  <%=types[i]%>&nbsp;&nbsp;
		<%
		if(i==5){
		out.print("<br><br>");
		}
		}%>		</td>
      </tr>
      <tr>
        <td height="40">��Ƶ���⣺</td>
        <td bgcolor="#FFFFFF"><input name="title" type="text" id="title"  title="��������Ƶ��������!"></td>
      </tr>
      <tr>
        <td height="40">��Ƶ������</td>
        <td bgcolor="#FFFFFF"><input name="info" type="text" id="info" size="60" title="��������Ƶ��������!"></td>
      </tr>
      <tr>
        <td height="40" bgcolor="#FFFFFF">&nbsp;</td>
        <td bgcolor="#FFFFFF">
<input type="submit" name="Submit" value=" �ϴ���Ƶ ">
&nbsp;&nbsp;
<input type="reset" name="Submit2" value=" ����ѡ�� "></td>
      </tr>
    </table>
	</form>
	${requestScope.message }	</td>
  </tr>
</table>
<jsp:include page="down.jsp" flush="true"/>
</body>
</html>
