<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.FileInputStream"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%!	Properties ptxt=new Properties(); %>
<%
	int experience=0;				//��Ա����ֵ
	int charm=0;					//��Ա����ֵ
	int prestige=0;					//��Ա����ֵ
	if(ptxt==null||ptxt.size()==0){
		System.out.println("ִ����synchronized()��");		
		String gradefilepath=getServletContext().getRealPath("/WEB-INF/classes/grade.properties");
		FileInputStream fin=new FileInputStream(gradefilepath);
		ptxt.load(fin);
		fin.close();
	}

	int order=0;							//�ȼ����
	int minpoint=0;							//�ȼ�������Χ�е���Сֵ
	String gradeName="İ����";				//�ȼ�����
	
	try{
		String strexperience=request.getParameter("experience");		//��ȡ��Ա����ֵ
		String strcharm=request.getParameter("charm");					//��ȡ��Ա����ֵ
		experience=Integer.parseInt(strexperience);
		charm=Integer.parseInt(strcharm);
		prestige=experience+charm;										//�����Ա����ֵ	
	
		int len=ptxt.size();				//��ȡ�ȼ�����Ҳ����ߵȼ����
		while(len>0){						//����ߵȼ���ʼѭ��
			String value=(String)ptxt.get(String.valueOf(len));			//��ȡ�ȼ���Ŷ�Ӧ������
			String[] str=value.split(":");								//����:���ָ��ַ���
			minpoint=Integer.parseInt(str[1]);							//��ȡ��ǰ�ȼ���Χ�е���С����ֵ
			if(prestige>=minpoint){										//�����Ա������ֵ���ڵ��ڵ�ǰ��ǰ�ȼ���Χ�е���С����ֵ
				order=len;												//��ȡ�ȼ���ţ��ں���Ĵ����н�ͨ����ֵȷ��������ţ��
				gradeName=str[0];										//��ȡ�ȼ�����
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
		out.print("��");
%>
</font>
