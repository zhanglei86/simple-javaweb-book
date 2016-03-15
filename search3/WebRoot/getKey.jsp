<%@ page contentType="text/html; charset=GBK" errorPage="" import="java.sql.*"%>
<jsp:directive.page import="com.wy.dao.Dao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.wy.form.Word"/>
<%
String name = request.getParameter("key");
name = new String(name.getBytes("ISO8859_1"), "GBK");
Dao dao = new Dao();
List list = dao.soWordList(name);
response.setContentType("text/xml");
response.setHeader("Cache-Control", "no-cache");
response.getWriter().write("<?xml version='1.0' encoding='GBK' ?>");
response.getWriter().write("<myKeys>");
String word_name="";
for(int i = 0 ;i<list.size();i++){
    Word word =(Word)list.get(i);
    word_name =word.getWord_name();
    response.getWriter().write("<mykey>" + word_name + "</mykey>");

}
response.getWriter().write("</myKeys>");	




%>




