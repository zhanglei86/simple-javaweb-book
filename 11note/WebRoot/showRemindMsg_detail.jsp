<%@ page contentType="text/html; charset=gb2312" language="java" import="java.util.*" errorPage="" %>
<%@ page import="com.model.MemoForm"%>
<%List<MemoForm> list=(List<MemoForm>)request.getAttribute("remindMsgList");
String temp="";
%>
<html>
  <head>
    <title>����¼����ϸ��Ϣ</title>
	<link href="CSS/style.css" rel="stylesheet"/>
  </head>
	<script language="javascript">
	function del(para){
		if(confirm("���Ҫɾ���ñ�����Ϣ��")){
			window.location.href="MemoServlet?action=del&id="+para+"&url=<%=request.getAttribute("url").toString()%>";
		}
	}
	</script>  
  <body style="background-color:#FFFFFF">

<table width="98%" height="60" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
<%
	for(int i=0;i<list.size();i++){ 
		switch (list.get(i).getRemindMode()) {
			case 0:temp="����";break;
			case 1:temp="ÿ��";break;
			case 2:temp="ÿ��";break;
			case 3:temp="ÿ��";break;
			case 4:temp="ÿ��";break;
		}
	%>
	  <tr>
		<td height="27" bgcolor="#FFFFFF" class="word_red">&nbsp;<%=list.get(i).getTitle()%></td></tr>
		<tr>
		<td height="90" bgcolor="#FFFFFF">&nbsp;<%=list.get(i).getContent()%></td></tr>
		<tr>
	    <td bgcolor="#FFFFFF">&nbsp;<%=temp%>���� ����ʱ�䣺<%=list.get(i).getRemindTime()%>&nbsp;&nbsp;[<a href="#" onClick="del(<%=list.get(i).getId()%>)">ɾ��</a>]</td>
	  </tr>
	  <tr><td><hr size="1"></td></tr>     
	<%
}%> 
</table>
  </body>
</html>
