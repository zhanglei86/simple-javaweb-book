<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath(); %>

<title>�ҵ�����</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript">
	function autoshowface(from,webname){
		var tag=document.getElementById("listmessages");
		if("send"==from){
			tag.src=webname+"/visit/myself/a/listmysendmessages";
		}
		else{
			tag.src=webname+"/visit/myself/a/listmyinceptmessages";
		}			
	}
	function showface(newurl){
		var tag=document.getElementById("listmessages");
		tag.src=newurl;
	}
</script>
<body onload="autoshowface('${param.from}','<%=contextPath%>')">
<center>
<c:set var="iunreadnum" value="${requestScope.inceptunreadnum}"/>
<c:set var="iallnum" value="${requestScope.inceptallnum}"/>
<c:set var="sallnum" value="${requestScope.sendallnum}"/>
<c:set var="maxnum" value="${requestScope.messagemax}"/>
<c:if test="${iallnum>maxnum}">
<c:set var="iallnum" value="${maxnum}"/>
</c:if>

<table border="0" width="100%" style="word-break:break-all" cellpadding="3" cellspacing="0">
	<tr align="right"><td colspan="3" style="color:red">�뼰ʱɾ���ռ�����������Ϣ�����������Ϣ������ʾ��</td></tr>
	<tr>
		<td><b><span id="checkmessage" style="color:red"></span></b></td>
		<td align="right" valign="bottom">
			<a href="<%=contextPath%>/visit/myself/a/sendmessageview">������Ϣ</a>
			<img src="<%=contextPath%>/images/icon/unread.gif"> δ����Ϣ |&nbsp;
			<img src="<%=contextPath%>/images/icon/read.gif"> �Ѷ���Ϣ |&nbsp;
			��������:��<b>${maxnum}</b>����			
		</td>
		<td bgcolor="#717171" width="300">
			<table width="100%" cellspacing="1" cellpadding="0">
				<tr><td bgcolor="#F0F0F0"><img src="<%=contextPath%>/images/icon/collect.gif" width="${iallnum/maxnum*300}" height="20" style="border:1 solid;border-color:lightgrey"></td></tr>
				<tr height="2"><td></td></tr>
			</table>			
		</td>
	</tr>
</table>
<table border="0" width="100%" style="word-break:break-all" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top" width="20%">
			<table border="0" width="90%" style="word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
				<tr class="listhead" height="25">
					<td width="70%">����</td>
					<td align="center">״̬</td>
				</tr>
				<tr bgcolor="white" height="30">
					<td><img src="<%=contextPath%>/images/icon/inbox.gif"> <a href="javascript:void(0)" onclick="showface('<%=contextPath%>/visit/myself/a/listmyinceptmessages')">�ռ���</a></td>
					<td align="center"><b>${iunreadnum}</b>(��)/${iallnum}</td>
				</tr>
				<tr bgcolor="#F5F5F5" height="30">
					<td><img src="<%=contextPath%>/images/icon/send.gif"> <a href="javascript:void(0)" onclick="showface('<%=contextPath%>/visit/myself/a/listmysendmessages')">������</a></td>
					<td align="center">${sallnum}</td>
				</tr>
				<tr bgcolor="lightgrey" height="25" align="right"><td colspan="3">������${iallnum+sallnum}</td></tr>
			</table>
		</td>
		<td align="right" valign="top"><iframe id="listmessages" src="" width="100%" frameborder="0" scrolling="no"></iframe></td>
	</tr>
</table>
	</center>
</body>