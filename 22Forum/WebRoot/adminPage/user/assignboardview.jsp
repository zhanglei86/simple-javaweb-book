<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<% String contextPath=request.getContextPath();%>

<title>为会员分配版块</title>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/style.css">
<script type="text/javascript" src="<%=contextPath%>/js/adminuser.js"></script>

<body>
<center>
<c:set var="single" value="${requestScope.usersingle}"/>

<c:if test="${!empty single}">
<!-- 实现为会员分配版块界面 -->
<form action="<%=contextPath%>/admin/user/d/assignboardrun" method="post" name="adminform">
<input type="hidden" name="autoforward" value="<%=request.getHeader("referer") %>">
<input type="hidden" name="memberid" value="${param.memberid}">
<table border="0" width="100%" cellspacing="1" cellpadding="2" bgcolor="#999999" style="word-break:break-all">
	<tr class="listhead" height="25"><td colspan="2" style="text-indent:5">为会员分配版块</td></tr>
	<tr bgcolor="#F5F5F5" height="30">
		<td nowrap width="30%">会员名称：</td>
		<td nowrap>${single.memberName}</td>
	</tr>
	<tr bgcolor="white" height="30">
		<td nowrap>已分配版块：</td>
		<td nowrap>			
			<c:if test="${empty requestScope.assignboardsname}">没有分配</c:if>
			<c:if test="${!empty requestScope.assignboardsname}">
			<c:forEach var="assignsingle" items="${requestScope.assignboardsname}">
			${assignsingle}，
			</c:forEach>
			</c:if>
		</td>
	</tr>
	<tr bgcolor="#F5F5F5">
		<td nowrap valign="top">选择版块：</td>
		<td nowrap>
			<select name="assignboardid" size="15" onchange="checkassignboard(this.options[this.selectedIndex].value,'${requestScope.assignboardsid}')">
				<c:if test="${(!empty requestScope.categorys)&&(!empty requestScope.boards)}">
				<!-- 外层forEach用来循环输出类别 -->
				<c:forEach var="categorysingle" items="${requestScope.categorys}">
				<c:set var="categoryid" value="${categorysingle.key}"/>
				<c:set var="categoryname" value="${categorys[categoryid]}"/>
				<option value="">□${categoryname}</option>							<!-- 输出类别下拉列表项 -->
				
				<c:set var="oneboards" value="${requestScope.boards[categoryid]}"/>
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
	<tr bgcolor="lightgrey" align="center">
		<td colspan="2">
			<input type="button" name="adminsubmitb" value="分配版块" onclick="myassignsubmit()" disabled>
			<input type="reset" value="重新设置">
			<input type="reset" value="放弃分配" onclick="javascript:history.go(-1)">						
		</td>
	</tr>
</table>
</form>
</c:if>
</center>
</body>