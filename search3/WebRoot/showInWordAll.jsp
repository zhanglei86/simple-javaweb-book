<%@ page contentType="text/html; charset=gb2312" language="java"
	import="java.sql.*" errorPage=""%>
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="com.wy.form.Word" />
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<link href="css/style.css" type="text/css" rel="stylesheet">
		<title>钟毅搜索---站内关键字全部搜索结果</title>

	</head>

	<body>
		<table width="876" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="256" height="99" rowspan="2">
					<img src="images/ico.jpg" width="256" height="99">
				</td>
				<td width="754" height="102" valign="bottom">
					<form name="form1" method="post"
						action="operationServlet?info=searchResult">
						<table width="595" height="33" border="0" align="left"
							cellpadding="0" cellspacing="0">
							<tr>
								<td width="437">
									<input name="queryString" type="text" size="65" height="20">
								</td>
								<td width="90">
									<input type="submit" name="Submit" value="  搜一下  ">
								</td>
							</tr>
						</table>
					</form>

				</td>
			</tr>
			<tr>
				<td height="13">
					&nbsp;
				</td>
			</tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="D7EAF8">
			<tr>
				<td width="7%" height="26">
					&nbsp;
				</td>
				<td width="93%" class="wordspace">
					站内关键字搜索结果
				</td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td width="7%" height="75">
					&nbsp;
				</td>
				<td class="wordspace">
					<font color="0001CB" size="3">
						钟毅中文搜索风云榜每天对上亿次进行分析、权威、全面、准确、精彩！<br>
						<br> <br> 凸现热点，纵横风云，挖掘在我们身边的新奇和惊喜，通过搜索，把握世界 。 </font>
				</td>
			</tr>
		</table>



		<table width="1190" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="88" height="61">
					&nbsp;
				</td>
				<td class="wordspace">
					<font color="0001CB" size="3" style="text-decoration:underline ">关键字全部查询</font>
				</td>
			</tr>
		</table>

		<table width="1190" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="88" height="121">
					&nbsp;
				</td>
				<td valign="top" class="wordspace">
					<table border="1" cellpadding="1" cellspacing="1"
						bordercolor="#FFFFFF" bgcolor="#CCCCCC">
						<tr bgcolor="#FFFFFF" align="center">
							<td width="190" height="30">
								<font color="#FF0000" style="font-weight:bold ">关键字</font>
							</td>
							<td width="100">
								<font color="#FF0000" style="font-weight:bold ">搜索结果</font>
							</td>
							<td width="100">
								<font color="#FF0000" style="font-weight:bold ">次数</font>
							</td>
							<td width="190">
								<font color="#FF0000" style="font-weight:bold ">关键字</font>
							</td>
							<td width="100">
								<font color="#FF0000" style="font-weight:bold ">搜索结果</font>
							</td>
							<td width="100">
								<font color="#FF0000" style="font-weight:bold ">次数</font>
							</td>
						</tr>
						<%
						    out.print("<tr bgcolor=#FFFFFF align=center height=30>");
							List list = (List) request.getAttribute("list");
						    int lineCount=2;
						    int size=list.size();
						    int rowCount=size/lineCount;
						    if(size%lineCount!=0){
						    	rowCount++;
						    }
						    for(int i=0;i<size;i++){
						    	Word word=(Word)list.get(i);
						    	out.print("<td>"+word.getWord_name()+"</td>");
						    	out.print("<td>"+word.getWord_result()+"</td>");
						    	out.print("<td>"+word.getWord_number()+"</td>");
						    	 if(i%lineCount==lineCount-1){					             	  
						             out.print("</tr><tr bgcolor=#FFFFFF align=center height=30>");             	  
						           }						    	
						    }
						    if(rowCount*lineCount-size>0){
						    	 out.print("<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>");
						    }
						    out.print("</tr>");
						%>
					</table>

				</td>
			</tr>
		</table>
		<br>
		<br>
		<form name="form1" method="post"
			action="operationServlet?info=searchResult">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="D7EAF8">
				<tr>
					<td width="256" height="40">
						&nbsp;
					</td>
					<td width="1007">
						<table width="595" height="33" border="0" align="left"
							cellpadding="0" cellspacing="0">
							<tr>
								<td width="437">
									<input name="queryString" type="text" size="65" height="20">
								</td>
								<td width="90">
									<input type="submit" name="Submit" value="  搜一下  ">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>


	</body>
</html>
