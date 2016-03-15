<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<center>
<table border="0" cellspacing="2" cellpadding="2">
	<tr>
   		<c:forEach varStatus="vs" begin="1" end="16">
   		<td>
   		<c:if test="${vs.count==1}">
		<input type="radio" name="emotion" value="face${vs.count}.gif" checked><img src="<%=request.getContextPath()%>/images/emotion/face${vs.count}.gif"></c:if>
   		
   		<c:if test="${vs.count!=1}">
		<input type="radio" name="emotion" value="face${vs.count}.gif"><img src="<%=request.getContextPath()%>/images/emotion/face${vs.count}.gif"></c:if>
   		</td>
   		
   		<c:if test="${vs.count%4==0}"></tr></c:if>
   		</c:forEach>
</table>
</center>