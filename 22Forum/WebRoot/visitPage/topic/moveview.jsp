<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>移动帖子</title>
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
<!-- 实现移动帖子界面 -->
<form action="<%=contextPath%>/visit/topic/c/moverun" method="post" name="changeform">
<input type="hidden" name="topicType" value="${single.topicType}">
<input type="hidden" name="topicId" value="${single.id}">
<input type="hidden" name="oldboardId" value="${single.boardId}"> 
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">编辑帖子：${single.topicTitle}</td></tr>
	<tr bgcolor="white" height="25">
		<td nowrap width="30%">标题：</td>
		<td nowrap>${single.topicTitle}</td>
	</tr>
	<tr bgcolor="#F5F5F5">		
		<td nowrap>内容：</td>
		<td>${single.topicContent}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>作者：</td>
		<td nowrap>${single.topicAuthorName}</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>发表时间：</td>
		<td nowrap>${single.topicPostTime}</td>
	</tr>
	<tr bgcolor="white">
		<td nowrap>目前帖子所在位置：</td>
		<td nowrap>
			<c:if test="${(!empty categorys)&&(!empty allboards)}">
			${categorys[visitcategoryid]}>>${(allboards[visitcategoryid])[visitboardid]}
			</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap>移动帖子到：</td>
		<td nowrap>
			<select name="newboardId" size="10" onchange="checkmovetopicselect(this.options[this.selectedIndex].value,'${visitboardid}')">
				<c:if test="${(!empty categorys)&&(!empty allboards)}">
				<!-- 外层forEach用来循环输出类别 -->
				<c:forEach var="categorysingle" items="${categorys}">
				<c:set var="categoryid" value="${categorysingle.key}"/>
				<c:set var="categoryname" value="${categorys[categoryid]}"/>
				<option value="">□${categoryname}</option>							<!-- 输出类别下拉列表项 -->
				
				<c:set var="oneboards" value="${allboards[categoryid]}"/>
				<c:if test="${!empty oneboards}">
				<!-- 内层forEach用来循环输出当前类别下的版块 -->
				<c:forEach var="boardsingle" items="${oneboards}">
				<c:set var="boardid" value="${boardsingle.key}"/>
				<c:set var="boardname" value="${oneboards[boardid]}"/>
				<option value="${boardid}">&nbsp;&nbsp;├ ${boardname}</option>		<!-- 输出版块下拉列表项 -->
				</c:forEach>
				<!-- 内层forEach结束 -->				
				</c:if>
				<c:if test="${empty oneboards}">
				<option value="">&nbsp;&nbsp;├ 没有版块</option>
				</c:if>
				
				<option value="">--------------------</option>						<!-- 输出分隔线 -->
				</c:forEach>
				<!-- 外层forEach结束 -->	
				</c:if>
			</select>&nbsp;
			<b><span id="checkmessage" style="color:red"></span></b>
		</td>
	</tr>
	<tr bgcolor="white">
		<td style="color:red">*请输入管理员密码：</td>
		<td><input type="password" name="adminpswd" size="30" class="login" onkeypress="checkmessage.innerHTML=''"></td>
	</tr>
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="editsubmitb" value="移动帖子" onclick="myvalidateadminsubmit()">
			<input type="reset" value="重新设置">
			<input type="reset" value="放弃移动" onclick="javascript:history.back(1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>