<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="createRandomUri" prefix="wghRandom" %>
<jsp:useBean id="getRSCount" class="com.dao.ScripDAO" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��֮����Ըǽ</title>
<script language="javascript">
var iLayerMaxNum=<%=getRSCount.getRSCount()+100%>;
</script>
<script language="javascript" src="JS/AjaxRequest.js"></script>
<script language="javascript" src="JS/index.js"></script>
<script language="javascript" src="JS/add.js"></script>
<script language="javascript">
function createNewScrip(id){	//ʵʱ��ʾ�ո���ӵ�����
	var newScrip="<div id='scrip"+id+"' class='Style"+scripForm.color.value+"' style='left:<wghRandom:createRandom n="920" base="5"/>px;top:<wghRandom:createRandom n="376" base="184"/>px;z-index:"+iLayerMaxNum+"' onmousedown='Move(this,event)' ondblclick=\"Show("+id+",'shadeDiv')\"><p class='Num'>������ţ�"+id+"&nbsp;&nbsp;������<span id='hitsValue"+id+"'>0</span><img src='images/close.gif' alt='�ر�' onclick='myClose("+id+")'></p><br /><p class='Detail'><img src='images/face/face_"+scripForm.face.value+".gif'><span class='wishMan'>"+scripForm.wishMan.value+"</span>	<br />"+scripForm.content.value+"</p><p class='wellWisher'>"+scripForm.wellWisher.value+"</p><p class='comment'><a href='#' onclick='holdout("+id+")'>[֧��]</a></p><p class='Date'>"+getTime()+"</p></div>";
	document.getElementById("main").innerHTML=document.getElementById("main").innerHTML+newScrip;
}
</script>
<link href="CSS/index.css" rel="stylesheet"/>
<link href="CSS/scrip.css" rel="stylesheet"/>
</head>

<body onBlur="showCheckCodeClear()">
<div style="display:none;" id="shadeDiv" onClick="Hide();"></div>
<jsp:include page="top.jsp"/>
<jsp:include page="scrollScrip.jsp"/>
<!--��ʼ��ʾ������Ϣ-->
<div id="main">
	<logic:iterate id="scrip" name="scripList" type="com.model.ScripForm" scope="request" indexId="ind">
	<div id="scrip<bean:write name="scrip" property="id" filter="true"/>" class="Style<bean:write name="scrip" property="color" filter="true"/>" style="left:<wghRandom:createRandom n="920" base="5"/>px;top:<wghRandom:createRandom n="376" base="184"/>px; z-index:${ind}" onmousedown="Move(this,event)" ondblclick="Show(<bean:write name="scrip" property="id" filter="true"/>,'shadeDiv')">
		<p class='Num'>������ţ�<bean:write name="scrip" property="id" filter="true"/>&nbsp;&nbsp;������<span id="hitsValue<bean:write name="scrip" property="id" filter="true"/>"><bean:write name="scrip" property="hits" filter="true"/></span><img src='images/close.gif' alt='�ر�' onClick="myClose(<bean:write name="scrip" property="id" filter="true"/>)"></p>
		<br /> 
		<p class="Detail">
		<img src="images/face/face_<bean:write name="scrip" property="face" filter="true"/>.gif">
		<span class='wishMan'><bean:write name="scrip" property="wishMan" filter="true"/></span>
		<br />
		<bean:write name="scrip" property="content" filter="true"/></p>
		<p class='wellWisher'><bean:write name="scrip" property="wellWisher" filter="true"/></p>
		<p class="comment"><a href="#" onClick="holdout(<bean:write name="scrip" property="id" filter="true"/>)">[֧��]</a></p>
		<p class="Date"><bean:write name="scrip" property="sendTime" filter="true"/></p>
</div>
	</logic:iterate>
</div>
<!--��ʾ������Ϣ����-->
<!--�������-->
<div id="notClickDiv"></div>
<div id="scrip_add">
  <table width="100%" height="300" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="15" height="14" background="images/scrip_add_leftTop.gif"></td>
      <td height="14" background="images/scrip_add_Top.gif"></td>
      <td width="15" background="images/scrip_add_rightTop.gif"></td>
    </tr>
    <tr>
      <td height="272" background="images/scrip_add_Left.gif"></td>
      <td valign="top" bgcolor="#FFFFFF">
	  <form action="" method="post" name="scripForm">
	  <table width="100%" height="188" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="16%" align="center">ף������</td>
          <td width="84%" align="left" colspan="3">
             <input type="text" name="wishMan" size="30"  onkeyup="javascript:InputInfo(this,'pWishMan');"/>
            *          </td>
        </tr>
        <tr>
          <td align="center">ף �� �ߣ�</td>
          <td align="left" colspan="3">
            <input type="text" name="wellWisher" onKeyUp="javascript:InputInfo(this,'pWellWisher');" size="30" value="����" onClick="if(this.value =='����') this.value=''; "/>
            * </td>
        </tr>
        <tr>
          <td align="center">������ɫ��</td>
          <td height="50px" align="left" colspan="3"><span class="color0" onClick="ColorChoose('0')"></span> <span class="color1" onClick="ColorChoose('1')"></span> <span class="color2" onClick="ColorChoose('2')"></span> <span class="color3" onClick="ColorChoose('3')"></span> <span class="color4" onClick="ColorChoose('4')"></span><span class="color5" onClick="ColorChoose('5')"></span><span class="color6" onClick="ColorChoose('6')"></span><span class="color7" onClick="ColorChoose('7')"></span><input type="hidden" id="color" name="color" value="0"/></td>
        </tr>
        <tr>
          <td height="65px" rowspan="2" align="center">����ͼ����</td>
          <td height="24" colspan="3" align="left">��ѡ������ͼ����� <a href="#" onClick="face_1.style.display='block';face_2.style.display='none';">��֮��</a> <a href="#" onClick="face_1.style.display='none';face_2.style.display='block';">��֮��</a> </td>
        </tr>
        <tr>
          <td align="left" colspan="3">
		  <div id="face_1" style="display:block">
		  <c:forEach begin="0" end="5" step="1" var="num" varStatus="id">
		  <img src="images/face/face_${num}.gif" width="56" height="56" onClick="javascript:faceChoose('${num}');" />		  </c:forEach>
		  </div>
		  <div id="face_2" style="display:none">
		  <c:forEach begin="6" end="11" step="1" var="num" varStatus="id">
		  <img src="images/face/face_${num}.gif" width="56" height="56" onClick="javascript:faceChoose('${num}');" />		  </c:forEach>
		  </div>		   
		  <input type="hidden" value="0" name="face" id="face"/></td>
        </tr>
        <tr>
          <td align="center">�������ݣ�</td>
          <td align="left" colspan="3"> <textarea name="content" id="content" rows="6" cols="49" class="wenbenkuang" onKeyDown="CountStrByte(this,this.form.total,this.form.used,this.form.remain);"
					    onkeyup="CountStrByte(this,this.form.total,this.form.used,this.form.remain);"></textarea> * </td>
        </tr>
		<tr>
                      <td height="33" align="center" style="padding-left:10px">�ֽڣ�</td>
                      <td style="padding-left:10px" colspan="3">������� 
                        <input name="total" type="text" disabled class="noborder" id="total" value="200" size="4"> 
                        ���ֽ� �����ֽڣ�&nbsp;
                        <input name="used" type="text" disabled class="noborder" id="used"  value="0" size="4">                        
                        ʣ���ֽڣ�
                        <input name="remain" type="text" disabled class="noborder" id="remain" value="200" size="4">            </td>
          </tr>
        <tr>
          <td align="center" valign="bottom">�� ֤ �룺</td>
          <td width="50px" align="left"><div style="position:absolute"><div id="showCheckCode" style="display:none; padding:3px" align="center" ><img src="PictureCheckCode?" id="createCheckCode" width="160" height="45"><a href="#" style="color:#EEEEEE" onClick="getCheckCode1(showCheckCode,checkCode)">������?��һ��</a></div>
<input name="checkCode" type="text" id="checkCode" size="6" value="" title="������֤������򣬻�ȡ��֤��" onClick="getCheckCodeFun(showCheckCode,checkCode)" onBlur="checkCheckCode(this.value)">
			  </div></td>
            <td width="40px" valign="bottom" id="resultCheckCode" onClick="showCheckCodeClear()">&nbsp;&nbsp;&nbsp;<img id="messageImg" src='images/tishi2.gif' width='16' height='16'>
			<input type='hidden' id='hResult'  value='0'></td>
            <td width="450px" valign="bottom" id="resultCheckCode" onClick="showCheckCodeClear()">&nbsp;
              <div id="resultMessage" style="text-align:left">��ܰ��ʾ��������֤������򣬻�ȡ��֤��</div></td>
        </tr>
        <tr>
          <td align="center" onClick="showCheckCodeClear()">&nbsp;</td>
          <td colspan="3" onClick="showCheckCodeClear()">
		<input type="button" id="btn_Submit" name="btn_Submit" class="btn_grey" value="����" onClick="scripSubmit()" disabled="disabled"/>
		&nbsp;
        <input type="button" class="btn_grey" value="�ر�" onClick="close_window()"/>		  </td>
        </tr>
      </table>
	  </form>
	  </td>
      <td background="images/scrip_add_Right.gif">&nbsp;</td>
    </tr>
    <tr>
      <td height="14" background="images/scrip_add_leftBottom.gif"></td>
      <td height="14" background="images/scrip_add_Bottom.gif"></td>
      <td background="images/scrip_add_rightBottom.gif"></td>
    </tr>
  </table>
</div>
  <input type="hidden" value="<%=getRSCount.getRSCount()%>"  id="hRsCount"/>		<!-- ��¼�������� -->
  <!--����Ԥ��-->
<div id="preview" class="preview">
		<p class='Num'>����Ԥ����</p>
		<br /> 
		<p class="Detail">
		<img src="images/face/face_0.gif" id="pFace">
		<span class='wishMan'><span class="wishMan" id="pWishMan"></span></span>
		<br />
		<span id="pContext"></span></p>
		<p class='wellWisher'><span class="wellWisher" id="pWellWisher">����</span></p>
 </div>
<jsp:include page="copyright.jsp"/>
</body>
</html>