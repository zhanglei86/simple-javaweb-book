<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<logic:iterate id="scrip" name="scrollScrip" type="com.model.ScripForm" scope="request" indexId="ind">
	[<span style="color:#990000"><bean:write name="scrip" property="wellWisher" filter="true"/></span>]×£¸£[<span style="color:#990000"><bean:write name="scrip" property="wishMan" filter="true"/></span>]£º<bean:write name="scrip" property="content" filter="true"/> <span style="color:#666666"><bean:write name="scrip" property="sendTime" filter="true"/></span>
</logic:iterate>