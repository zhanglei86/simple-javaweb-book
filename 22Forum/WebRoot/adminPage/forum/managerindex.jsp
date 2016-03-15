<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%String contextPath=request.getContextPath();%>

<title>论坛后台-论坛管理</title>
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<table border="0" width="100%" style="margin-top:5;word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr bgcolor="lightgrey">
		<td>
			您可以进行以下操作：<br>
			>> <a href="<%=contextPath%>/admin/category/e/addcategoryview">添加新类别</a>
		</td>
	</tr>
</table>

<c:set var="categorys" value="${requestScope.categorylist}"/>
<c:if test="${!empty categorys}">
	<table border="0" width="100%" style="margin-top:5;word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
		<tr height="30" class="listhead">
			<td colspan="2">版块名称/描述</td>
			<td align="center" width="10%">排序</td>
			<td align="center">创建日期</td>
			<td align="center" width="9%">添加新版块</td>
			<td align="center" width="9%">分配管理员</td>
			<td align="center" width="7%">编辑</td>
			<td align="center" width="7%">删除</td>
		</tr>
		
		<c:forEach var="categorySingle" varStatus="cvs" items="${categorys}">
		<tr height="40" class="listsubhead">
			<td colspan="2" style="padding-left:3">
				<img id="img${cvs.index}" src="<%=contextPath%>/images/icon/close.gif" style="border:0;cursor:hand" onclick="showorhiden('trs${cvs.index}','img${cvs.index}','<%=contextPath%>')">
				<b>${categorySingle.categoryName}</b><br>
				<font color="gray">${categorySingle.categoryInfo}</font><br>
				<c:set var="masters" value="${categorySingle.masters}"/>
				类主:
				<c:if test="${empty masters}">没有类主</c:if>
				<c:if test="${!empty masters}">
				<c:forEach var="master" items="${masters}">
				<a href="<%=contextPath%>/admin/user/c/adminviewmember?memberName=${master}" target="_blank">${master}</a>&nbsp;&nbsp;
				</c:forEach>
				</c:if>
			</td>
			<td>
				<c:if test="${categorySingle.categoryOrder gt 0}">
				<a href="<%=contextPath%>/admin/category/e/updateorder?categoryId=${categorySingle.id}&direction=up"><img src="<%=contextPath%>/images/icon/up.gif" style="border:0"></a>
				</c:if>
				<c:if test="${categorySingle.categoryOrder lt 1000}">
				<b>${categorySingle.categoryOrder}</b>
				</c:if>
				<a href="<%=contextPath%>/admin/category/e/updateorder?categoryId=${categorySingle.id}&direction=down"><img src="<%=contextPath%>/images/icon/down.gif" style="border:0"></a>
			</td>
			<td align="center">${categorySingle.categoryFoundTime}</td>
			<td align="center"><a href="<%=contextPath%>/admin/category/d/addboardview?categoryId=${categorySingle.id}">新版块</a></td>
			<td><a href="<%=contextPath%>/admin/category/e/addcategorymasterview?categoryId=${categorySingle.id}">新类主</a></td>
			<td><a href="<%=contextPath%>/admin/category/d/modifycategoryview?categoryId=${categorySingle.id}">编辑</a></td>
			<td><a href="<%=contextPath%>/admin/category/e/deletecategoryview?categoryId=${categorySingle.id}">删除</a></td>
		</tr>
		
		<tbody id="trs${cvs.index}">
		<c:set var="oneboards" value="${requestScope.allboards[categorySingle.id]}"/>
		<c:if test="${empty oneboards}">
		<tr bgcolor="white"><td align="center" colspan="8" style="color:red">该类别下没有版块．请单击“新版块”进行添加．</td></tr>
		</c:if>

		<c:if test="${!empty oneboards}">
		<c:forEach var="boardSingle" varStatus="bvs" items="${oneboards}">
		<c:if test="${bvs.index%2==0}">
		<tr bgcolor="white" class="listbody"></c:if>
		<c:if test="${bvs.index%2!=0}">
		<tr bgcolor="#F0F0F0" class="listbody"></c:if>
			<td align="center" width="4%"><img src="<%=contextPath%>/images/icon/board${boardSingle.boardStatus}.gif"></td>
			<td width="39%" style="padding-left:5">
				<b><font color="blue">${boardSingle.boardName}</font></b><br>
				<font color="gray">${boardSingle.boardInfo}</font><br>
				版主:
				<c:set var="masters" value="${boardSingle.masters}"/>
				<c:if test="${empty masters}">没有版主</c:if>
				<c:if test="${!empty masters}">
				<c:forEach var="master" items="${masters}">
				<a href="<%=contextPath%>/admin/user/c/adminviewmember?memberName=${master}" target="_blank">${master}</a>&nbsp;&nbsp;
				</c:forEach>
				</c:if>
			</td>
			<td align="right">
				<c:if test="${boardSingle.boardOrder gt 0}">
				<a href="<%=contextPath%>/admin/board/e/updateorder?boardId=${boardSingle.id}&direction=up"><img src="<%=contextPath%>/images/icon/up.gif" style="border:0"></a>
				</c:if>
				<b>${boardSingle.boardOrder}</b>
				<c:if test="${boardSingle.boardOrder lt 1000}">
				<a href="<%=contextPath%>/admin/board/e/updateorder?boardId=${boardSingle.id}&direction=down"><img src="<%=contextPath%>/images/icon/down.gif" style="border:0"></a>
				</c:if>
			</td>
			<td align="center">${boardSingle.boardFoundTime}</td>
			<td></td>
			
			<td align="right"><a href="<%=contextPath%>/admin/board/d/addboardmasterview?boardId=${boardSingle.id}&categoryId=${boardSingle.categoryId}">新版主</a></td>
			<td align="right"><a href="<%=contextPath%>/admin/board/c/modifyboardview?boardId=${boardSingle.id}&categoryId=${boardSingle.categoryId}">编辑</a></td>
			<td align="right"><a href="<%=contextPath%>/admin/board/d/deleteboardview?boardId=${boardSingle.id}&categoryId=${boardSingle.categoryId}">删除</a></td>
		
		</tr>
		</c:forEach>
		</c:if>
		</tbody>
		
		</c:forEach>
	</table>	
</c:if>