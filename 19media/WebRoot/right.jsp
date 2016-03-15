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
             <td width="55" height="30">用户名：</td>
             <td colspan="2"><input type="text" name="account" title="请输入用户名！"></td>
           </tr>
           <tr>
             <td height="30">密&nbsp;&nbsp;码：</td>
             <td colspan="2"><input type="password" name="password" title="请输入密码！"></td>
           </tr>
           <tr>
             <td height="30">校验码：</td>
             <td width="84"><input name="code" type="text" size="12"></td>
             <td width="82"><img border=0 src="image.jsp" name="checkCode" title="请输入校验码！"></td>
           </tr>
           <tr>
             <td height="30">&nbsp;</td>
             <td colspan="2">
               <input type="image" name="Submit" src="image/land.gif" class="cannleLine">
&nbsp;
              <a href="user_add.jsp" class="cannleLine">用户注册</a>
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
          <td width="225" height="225"> 用户名：<%=userInfo.getUser_name()%> <br>
              <br>
      性别：<%=userInfo.getUser_sex()%> <br>
      <br>
      QQ号码：<%=userInfo.getUser_oicq()%> <br>
      <br>
      email地址：<%=userInfo.getUser_email()%> <br>
      <br>
      来自：<%=userInfo.getUser_from()%> <br>
      <br>
      访问次数：<%=userInfo.getUser_hitNum()%> <br>
      <br>
      <a href="dealwith.jsp?sign=1">安全退出</a> </td>
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
 <a href="media_show.jsp?id=<%=mediaInfo.getId()%>"><%=mediaInfo.getMediaTitle()%> (<%=mediaInfo.getLookCount()%>次)</a>
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
