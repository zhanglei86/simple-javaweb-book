<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:directive.page import="com.wy.dao.UserDao"/>
<jsp:directive.page import="com.wy.model.UserInfo"/>
<jsp:directive.page import="com.wy.dao.MediaDao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.wy.model.MediaInfo"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">


<br>


<%
if(null==session.getAttribute("username")){
%>
 <form name="form" method="post" action="dealwith.jsp?sign=3" onsubmit="return checkEmpty(form)">
   <table width="249" height="195" border="0" align="center" cellpadding="0" cellspacing="0" background="image/f_land.gif">
     <tr>
       <td valign="top"><table width="203" height="44" border="0" align="center" cellpadding="0" cellspacing="0">
         <tr>
           <td height="44">&nbsp;</td>
         </tr>
       </table>
         <table width="230" border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td width="55" height="30">�û�����</td>
             <td colspan="2"><input type="text" name="account" title="�������û�����"></td>
           </tr>
           <tr>
             <td height="30">��&nbsp;&nbsp;�룺</td>
             <td colspan="2"><input type="password" name="password" title="���������룡"></td>
           </tr>
           <tr>
             <td height="30">У���룺</td>
             <td width="84"><input name="code" type="text" size="12"></td>
             <td width="82"><img border=0 src="image.jsp" name="checkCode" title="������У���룡"></td>
           </tr>
           <tr>
             <td height="30">&nbsp;</td>
             <td colspan="2">
               <input type="image" name="Submit" src="image/land.gif" class="cannleLine">
&nbsp;
              <a href="user_add.jsp" class="cannleLine">�û�ע��</a>
             </td>
           </tr>
         </table></td>
     </tr>
   </table>
</form>	 
<%}else{
String username=(String)session.getAttribute("username");
UserDao userDao=new UserDao();
UserInfo userInfo=userDao.user_queryOne(username);
%>	  
<br>

<table width="249" height="290" border="0" align="center" cellpadding="0" cellspacing="0" background="image/f_show.gif">
  <tr>
    <td height="291" valign="top"><table width="203" height="44" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="44">&nbsp;</td>
      </tr>
    </table>
	
	  <table width="232" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="225" height="225"> �û�����<%=userInfo.getUser_name()%> <br>
              <br>
      �Ա�<%=userInfo.getUser_sex()%> <br>
      <br>
      QQ���룺<%=userInfo.getUser_oicq()%> <br>
      <br>
      email��ַ��<%=userInfo.getUser_email()%> <br>
      <br>
      ���ԣ�<%=userInfo.getUser_from()%> <br>
      <br>
      ���ʴ�����<%=userInfo.getUser_hitNum()%> <br>
      <br>
      <a href="dealwith.jsp?sign=1">��ȫ�˳�</a> </td>
        </tr>
      </table></td>
  </tr>
</table>
<%}%>
<br>
<table width="251" height="338" border="0" align="center" cellpadding="0" cellspacing="0" background="image/f_hot.gif">
  <tr>
    <td valign="top" align="center"><table width="210" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="42">&nbsp;		
		</td>
      </tr>
    </table>
	
	
			<%
String sql= "select * from tb_media order by media_lookCount desc";
MediaDao MediaDao = new MediaDao();
List list = MediaDao.media_queryAuto(sql);
int size=list.size();
if(size>10){
size=10;
}
for(int i = 0; i<size;i++){
MediaInfo mediaInfo = (MediaInfo)list.get(i);
%>        
 <a href="media_show.jsp?id=<%=mediaInfo.getId()%>"><%=mediaInfo.getMediaTitle()%> (<%=mediaInfo.getLookCount()%>��)</a>
  <br> <br>
<%}%>
	
	</td>
  </tr>
</table>
<br>
<table width="210" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="image/03.jpg" width="250" height="68"></td>
  </tr>
  <tr>
    <td><img src="image/05.jpg" width="250" height="68"></td>
  </tr>
</table>
<br>
