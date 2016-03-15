<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%String contextPath=request.getContextPath();%>

<title>��̳��̨-��̳����</title>
<script type="text/javascript" src="<%=contextPath%>/js/visitforum.js"></script>

<table border="0" width="100%" style="margin-top:5;word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
	<tr bgcolor="lightgrey">
		<td>
			�����Խ������²�����<br>
			>> <a href="<%=contextPath%>/admin/category/e/addcategoryview">��������</a>
		</td>
	</tr>
</table>

<c:set var="categorys" value="${requestScope.categorylist}"/>
<c:if test="${!empty categorys}">
	<table border="0" width="100%" style="margin-top:5;word-break:break-all" cellpadding="3" cellspacing="1" bgcolor="#8A8989">
		<tr height="30" class="listhead">
			<td colspan="2">�������/����</td>
			<td align="center" width="10%">����</td>
			<td align="center">��������</td>
			<td align="center" width="9%">����°��</td>
			<td align="center" width="9%">�������Ա</td>
			<td align="center" width="7%">�༭</td>
			<td align="center" width="7%">ɾ��</td>
		</tr>
		
		<c:forEach var="categorySingle" varStatus="cvs" items="${categorys}">
		<tr height="40" class="listsubhead">
			<td colspan="2" style="padding-left:3">
				<img id="img${cvs.index}" src="<%=contextPath%>/images/icon/close.gif" style="border:0;cursor:hand" onclick="showorhiden('trs${cvs.index}','img${cvs.index}','<%=contextPath%>')">
				<b>${categorySingle.categoryName}</b><br>
				<font color="gray">${categorySingle.categoryInfo}</font><br>
				<c:set var="masters" value="${categorySingle.masters}"/>
				����:
				<c:if test="${empty masters}">û������</c:if>
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
			<td align="center"><a href="<%=contextPath%>/admin/category/d/addboardview?categoryId=${categorySingle.id}">�°��</a></td>
			<td><a href="<%=contextPath%>/admin/category/e/addcategorymasterview?categoryId=${categorySingle.id}">������</a></td>
			<td><a href="<%=contextPath%>/admin/category/d/modifycategoryview?categoryId=${categorySingle.id}">�༭</a></td>
			<td><a href="<%=contextPath%>/admin/category/e/deletecategoryview?categoryId=${categorySingle.id}">ɾ��</a></td>
		</tr>
		
		<tbody id="trs${cvs.index}">
		<c:set var="oneboards" value="${requestScope.allboards[categorySingle.id]}"/>
		<c:if test="${empty oneboards}">
		<tr bgcolor="white"><td align="center" colspan="8" style="color:red">�������û�а�飮�뵥�����°�顱������ӣ�</td></tr>
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
				����:
				<c:set var="masters" value="${boardSingle.masters}"/>
				<c:if test="${empty masters}">û�а���</c:if>
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
			
			<td align="right"><a href="<%=contextPath%>/admin/board/d/addboardmasterview?boardId=${boardSingle.id}&categoryId=${boardSingle.categoryId}">�°���</a></td>
			<td align="right"><a href="<%=contextPath%>/admin/board/c/modifyboardview?boardId=${boardSingle.id}&categoryId=${boardSingle.categoryId}">�༭</a></td>
			<td align="right"><a href="<%=contextPath%>/admin/board/d/deleteboardview?boardId=${boardSingle.id}&categoryId=${boardSingle.categoryId}">ɾ��</a></td>
		
		</tr>
		</c:forEach>
		</c:if>
		</tbody>
		
		</c:forEach>
	</table>	
</c:if>