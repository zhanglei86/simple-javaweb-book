<%@ page contentType="text/html;charset=gb2312"%>
<%
	
	String webname=request.getContextPath();

	//���ɵ�ǰλ����״�˵�
	String forumicon="<img src='"+webname+"/images/icon/forum.gif'>&nbsp;&nbsp;";
	String categoryicon="<img src='"+webname+"/images/icon/link.gif'><img src='"+webname+"/images/icon/category.gif'>&nbsp;&nbsp;";
	String boardicon="<img src='"+webname+"/images/icon/link.gif'><img src='"+webname+"/images/icon/board.gif'>&nbsp;&nbsp;";
	String tree=forumicon+"<a href='"+webname+"/index'>������̳</a><br>";				//λ����״�˵���Ĭ��ֵ(���ڵ�)
	
	String[] subPaths=(String[])request.getAttribute("subPaths");
	if(subPaths!=null&&subPaths.length!=0){
		String way=subPaths[0];						//����ǰ��̨�ı�ʶ
		String module=subPaths[1];					//����ģ��ı�ʶ
		String operate=subPaths[3];					//���ֲ����ı�ʶ��
	
		if("admin".equals(way)){
			if("category".equals(module)||"board".equals(module)){
				tree+=categoryicon+"<a href='"+webname+"/admin/category/c/forummanager'>��̳����</a><br>";
				tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if("addcategoryview".equals(operate))
					tree+=boardicon+"<font color='red'>��������</font>";
				else if("addcategoryrun".equals(operate))
					tree+=boardicon+"<font color='red'>��������ɹ���</font>";
				else if("modifycategoryview".equals(operate))
					tree+=boardicon+"<font color='red'>�޸������Ϣ��</font>";
				else if("modifycategoryrun".equals(operate))
					tree+=boardicon+"<font color='red'>�޸������Ϣ�ɹ���</font>";
				else if("deletecategoryview".equals(operate))
					tree+=boardicon+"<font color='red'>ɾ�������Ϣ��</font>";
				else if("deletecategoryrun".equals(operate))
					tree+=boardicon+"<font color='red'>ɾ�������Ϣ�ɹ���</font>";
				else if("addboardview".equals(operate))
					tree+=boardicon+"<font color='red'>����°�飡</font>";
				else if("addboardrun".equals(operate))
					tree+=boardicon+"<font color='red'>����°��ɹ���</font>";
				else if("addboardmasterview".equals(operate))
					tree+=boardicon+"<font color='red'>����°�����</font>";
				else if("addboardmasterrun".equals(operate))
					tree+=boardicon+"<font color='red'>����°����ɹ���</font>";
				else if("modifyboardview".equals(operate))
					tree+=boardicon+"<font color='red'>�޸İ����Ϣ��</font>";
				else if("modifyboardrun".equals(operate))
					tree+=boardicon+"<font color='red'>�޸İ����Ϣ�ɹ���</font>";
				else if("deleteboardview".equals(operate))
					tree+=boardicon+"<font color='red'>ɾ�������Ϣ��</font>";
				else if("deleteboardrun".equals(operate))
					tree+=boardicon+"<font color='red'>ɾ�������Ϣ�ɹ���</font>";
			}
			else if("user".equals(module)){
				tree+=categoryicon+"<a href='"+webname+"/admin/user/c/membermanager'>��Ա����</a><br>";
				tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if("addnewuserview".equals(operate)||"checkmember".equals(operate))
					tree+=boardicon+"<font color='red'>����»�Ա��</font>";
				else if("addnewuserrun".equals(operate))
					tree+=boardicon+"<font color='red'>����»�Ա�ɹ���</font>";
				else if("searchuserview".equals(operate)||"checkmember".equals(operate))
					tree+=boardicon+"<font color='red'>���һ�Ա��</font>";
				else if("searchuserrun".equals(operate))
					tree+=boardicon+"<font color='red'>���ҽ����</font>";
				else if("changeuserstatusrun".equals(operate))
					tree+=boardicon+"<font color='red'>�޸Ļ�Ա״̬�ɹ���</font>";
				else if("deleteuserview".equals(operate))
					tree+=boardicon+"<font color='red'>ɾ����Ա��</font>";
				else if("deleteuserrun".equals(operate))
					tree+=boardicon+"<font color='red'>ɾ����Ա�ɹ���</font>";
				else if("adminviewmember".equals(operate))
					tree+=boardicon+"<font color='red'>�鿴��Ա���ϣ�</font>";
				else if("admineditmemberinfoview".equals(operate))
					tree+=boardicon+"<font color='red'>�޸Ļ�Ա���ϣ�</font>";
				else if("admineditmemberinforun".equals(operate))
					tree+=boardicon+"<font color='red'>�޸Ļ�Ա���ϳɹ���</font>";
				else if("admineditmemberpswdview".equals(operate))
					tree+=boardicon+"<font color='red'>�޸Ļ�Ա���룡</font>";
				else if("admineditmemberpswdrun".equals(operate))
					tree+=boardicon+"<font color='red'>�޸Ļ�Ա����ɹ���</font>";
				else if("assigncategoryview".equals(operate))
					tree+=boardicon+"<font color='red'>Ϊ��Ա�������</font>";
				else if("assigncategoryrun".equals(operate))
					tree+=boardicon+"<font color='red'>�������ɹ���</font>";
				else if("assignboardview".equals(operate))
					tree+=boardicon+"<font color='red'>Ϊ��Ա�����飡</font>";
				else if("assignboardrun".equals(operate))
					tree+=boardicon+"<font color='red'>������ɹ���</font>";
				else if("cancelcategorymasterview".equals(operate))
					tree+=boardicon+"<font color='red'>жְ������ݣ�</font>";
				else if("cancelcategorymasterrun".equals(operate))
					tree+=boardicon+"<font color='red'>жְ������ݳɹ���</font>";
				else if("cancelboardmasterview".equals(operate))
					tree+=boardicon+"<font color='red'>жְ������ݣ�</font>";
				else if("cancelboardmasterrun".equals(operate))
					tree+=boardicon+"<font color='red'>жְ������ݳɹ���</font>";					
			}
			else if("group".equals(module)){
				tree+=categoryicon+"<a href='"+webname+"/admin/group/e/groupmanager'>�û������</a><br>";
				tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if("modifygroupview".equals(operate))
					tree+=boardicon+"<font color='red'>�޸��û�����Ϣ��</font>";
				else if("modifygrouprun".equals(operate))
					tree+=boardicon+"<font color='red'>�޸��û�����Ϣ�ɹ���</font>";
				else if("addmembertogroupview".equals(operate))
					tree+=boardicon+"<font color='red'>����³�Ա����ǰ�飡</font>";
				else if("addmembertogrouprun".equals(operate))
					tree+=boardicon+"<font color='red'>����³�Ա�����</font>";
				else if("listmemberingroup".equals(operate))
					tree+=boardicon+"<font color='red'>�鿴��ǰ�������г�Ա��</font>";
				else if("removefromgroupview".equals(operate))
					tree+=boardicon+"<font color='red'>�ӵ�ǰ���Ƴ���Ա��</font>";
				else if("removefromgrouprun".equals(operate))
					tree+=boardicon+"<font color='red'>�Ƴ���Ա�ɹ���</font>";				
			}
		}		
	}
%>
<table width="99%" border="0" cellpadding="8" cellspacing="0">
	<tr><td><%=tree%></td></tr>
</table>
