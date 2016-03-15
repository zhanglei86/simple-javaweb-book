<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.FileInputStream"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%!	Properties ptxt=new Properties(); %>
<%
	int experience=0;				//会员经验值
	int charm=0;					//会员魅力值
	int prestige=0;					//会员威望值
	if(ptxt==null||ptxt.size()==0){
		System.out.println("执行了synchronized()块");		
		String gradefilepath=getServletContext().getRealPath("/WEB-INF/classes/grade.properties");
		FileInputStream fin=new FileInputStream(gradefilepath);
		ptxt.load(fin);
		fin.close();
	}

	int order=0;							//等级序号
	int minpoint=0;							//等级点数范围中的最小值
	String gradeName="陌生人";				//等级名称
	
	try{
		String strexperience=request.getParameter("experience");		//获取会员经验值
		String strcharm=request.getParameter("charm");					//获取会员魅力值
		experience=Integer.parseInt(strexperience);
		charm=Integer.parseInt(strcharm);
		prestige=experience+charm;										//计算会员威望值	
	
		int len=ptxt.size();				//获取等级数，也是最高等级序号
		while(len>0){						//从最高等级开始循环
			String value=(String)ptxt.get(String.valueOf(len));			//获取等级序号对应的内容
			String[] str=value.split(":");								//按＂:＂分隔字符串
			minpoint=Integer.parseInt(str[1]);							//获取当前等级范围中的最小威望值
			if(prestige>=minpoint){										//如果会员的威望值大于等于当前当前等级范围中的最小威望值
				order=len;												//获取等级序号，在后面的代码中将通过该值确定输出几颗＂★＂
				gradeName=str[0];										//获取等级名称
				break;
			}
			--len;
		}			
	} catch (Exception e) {
		e.printStackTrace();
	}		
%>
<%=gradeName%><br>
<font color='red'>
<%
	for(int i=0;i<order;i++)
		out.print("★");
%>
</font>
