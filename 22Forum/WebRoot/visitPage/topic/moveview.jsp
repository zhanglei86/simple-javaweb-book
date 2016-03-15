<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>�ƶ�����</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<body onload="checkmovetopicselect(changeform.newboardId.value)">
<center>

<c:set var="categorys" value="${sessionScope.categorys}"/>
<c:set var="allboards" value="${sessionScope.boards}"/>
<c:set var="visitcategoryid" value="${sessionScope.visitcategory}"/>
<c:set var="visitboardid" value="${sessionScope.visitboard}"/>
<c:set var="single" value="${requestScope.topicsingle}"/>

<c:if test="${!empty single}">
<!-- ʵ���ƶ����ӽ��� -->
<form action="<%=contextPath%>/visit/topic/c/moverun" method="post" name="changeform">
<input type="hidden" name="topicType" value="${single.topicType}">
<input type="hidden" name="topicId" value="${single.id}">
<input type="hidden" name="oldboardId" value="${single.boardId}"> 
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">�༭���ӣ�${single.topicTitle}</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">���⣺</td>
		<td nowrap>${single.topicTitle}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap>���ݣ�</td>
		<td>${single.topicContent}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>���ߣ�</td>
		<td nowrap>${single.topicAuthorName}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>����ʱ�䣺</td>
		<td nowrap>${single.topicPostTime}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>Ŀǰ��������λ�ã�</td>
		<td nowrap>
			<c:if test="${(!empty categorys)&&(!empty allboards)}">
			${categorys[visitcategoryid]}>>${(allboards[visitcategoryid])[visitboardid]}
			</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>�ƶ����ӵ���</td>
		<td nowrap>
			<select name="newboardId" size="10" onchange="checkmovetopicselect(this.options[this.selectedIndex].value,'${visitboardid}')">
				<c:if test="${(!empty categorys)&&(!empty allboards)}">
				<!-- ���forEach����ѭ�������� -->
				<c:forEach var="categorysingle" items="${categorys}">
				<c:set var="categoryid" value="${categorysingle.key}"/>
				<c:set var="categoryname" value="${categorys[categoryid]}"/>
				<option value="">��${categoryname}</option>							<!-- �����������б��� -->
				
				<c:set var="oneboards" value="${allboards[categoryid]}"/>
				<c:if test="${!empty oneboards}">
				<!-- �ڲ�forEach����ѭ�������ǰ����µİ�� -->
				<c:forEach var="boardsingle" items="${oneboards}">
				<c:set var="boardid" value="${boardsingle.key}"/>
				<c:set var="boardname" value="${oneboards[boardid]}"/>
				<option value="${boardid}">&nbsp;&nbsp;�� ${boardname}</option>		<!-- �����������б��� -->
				</c:forEach>
				<!-- �ڲ�forEach���� -->				
				</c:if>
				<c:if test="${empty oneboards}">
				<option value="">&nbsp;&nbsp;�� û�а��</option>
				</c:if>
				
				<option value="">--------------------</option>						<!-- ����ָ��� -->
				</c:forEach>
				<!-- ���forEach���� -->	
				</c:if>
			</select>&nbsp;
			<b><span id="checkmessage" style="color:red"></span></b>
		</td>
	</tr>
	<tr bgcolor="white">
		<td style="color:red">*���������Ա���룺</td>
		<td><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="editsubmitb" value="�ƶ�����" onclick="myvalidateadminsubmit()">
			<input type="reset" value="��������">
			<input type="reset" value="�����ƶ�" onclick="javascript:history.back(1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>