<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:set var="old" value="${param.oldicon}"/>
<select name="icon" onchange="showHead(this.options[this.selectedIndex].value,'<%=request.getContextPath()%>')" size="10" style="width:120">
   	<c:forEach varStatus="vs" begin="0" end="17">
  	
  	<c:set var="oneicon" value="user${vs.index}.gif"/>
  	<c:if test="${old eq oneicon}">
  	<option value="user${vs.index}.gif" selected>user${vs.index}</option>
  	</c:if>  	
  	<c:if test="${old ne oneicon}">
  	<option value="user${vs.index}.gif">user${vs.index}</option>
  	</c:if>
  		        	
   	</c:forEach>
</select>
