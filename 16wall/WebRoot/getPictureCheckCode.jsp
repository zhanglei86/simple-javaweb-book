<%@ page contentType="text/html; charset=GBK" language="java"  import="java.util.Random"%>
<%
Random random = new Random();
%>
<img src="<% out.println("PictureCheckCode?rand="+random.nextInt(10000));%>" id="createCheckCode" width="160" height="45">
<a href="#" style="color:#EEEEEE" onclick="getCheckCode1(showCheckCode,checkCode)">看不清?换一个</a>
