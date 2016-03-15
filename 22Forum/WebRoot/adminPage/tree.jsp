<%@ page contentType="text/html;charset=gb2312"%>
<%
	
	String webname=request.getContextPath();

	//生成当前位置树状菜单
	String forumicon="<img src='"+webname+"/images/icon/forum.gif'>&nbsp;&nbsp;";
	String categoryicon="<img src='"+webname+"/images/icon/link.gif'><img src='"+webname+"/images/icon/category.gif'>&nbsp;&nbsp;";
	String boardicon="<img src='"+webname+"/images/icon/link.gif'><img src='"+webname+"/images/icon/board.gif'>&nbsp;&nbsp;";
	String tree=forumicon+"<a href='"+webname+"/index'>随意论坛</a><br>";				//位置树状菜单的默认值(根节点)
	
	String[] subPaths=(String[])request.getAttribute("subPaths");
	if(subPaths!=null&&subPaths.length!=0){
		String way=subPaths[0];						//区分前后台的标识
		String module=subPaths[1];					//区分模块的标识
		String operate=subPaths[3];					//区分操作的标识　
	
		if("admin".equals(way)){
			if("category".equals(module)||"board".equals(module)){
				tree+=categoryicon+"<a href='"+webname+"/admin/category/c/forummanager'>论坛管理</a><br>";
				tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if("addcategoryview".equals(operate))
					tree+=boardicon+"<font color='red'>添加新类别！</font>";
				else if("addcategoryrun".equals(operate))
					tree+=boardicon+"<font color='red'>添加新类别成功！</font>";
				else if("modifycategoryview".equals(operate))
					tree+=boardicon+"<font color='red'>修改类别信息！</font>";
				else if("modifycategoryrun".equals(operate))
					tree+=boardicon+"<font color='red'>修改类别信息成功！</font>";
				else if("deletecategoryview".equals(operate))
					tree+=boardicon+"<font color='red'>删除类别信息！</font>";
				else if("deletecategoryrun".equals(operate))
					tree+=boardicon+"<font color='red'>删除类别信息成功！</font>";
				else if("addboardview".equals(operate))
					tree+=boardicon+"<font color='red'>添加新版块！</font>";
				else if("addboardrun".equals(operate))
					tree+=boardicon+"<font color='red'>添加新版块成功！</font>";
				else if("addboardmasterview".equals(operate))
					tree+=boardicon+"<font color='red'>添加新版主！</font>";
				else if("addboardmasterrun".equals(operate))
					tree+=boardicon+"<font color='red'>添加新版主成功！</font>";
				else if("modifyboardview".equals(operate))
					tree+=boardicon+"<font color='red'>修改版块信息！</font>";
				else if("modifyboardrun".equals(operate))
					tree+=boardicon+"<font color='red'>修改版块信息成功！</font>";
				else if("deleteboardview".equals(operate))
					tree+=boardicon+"<font color='red'>删除版块信息！</font>";
				else if("deleteboardrun".equals(operate))
					tree+=boardicon+"<font color='red'>删除版块信息成功！</font>";
			}
			else if("user".equals(module)){
				tree+=categoryicon+"<a href='"+webname+"/admin/user/c/membermanager'>会员管理</a><br>";
				tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if("addnewuserview".equals(operate)||"checkmember".equals(operate))
					tree+=boardicon+"<font color='red'>添加新会员！</font>";
				else if("addnewuserrun".equals(operate))
					tree+=boardicon+"<font color='red'>添加新会员成功！</font>";
				else if("searchuserview".equals(operate)||"checkmember".equals(operate))
					tree+=boardicon+"<font color='red'>查找会员！</font>";
				else if("searchuserrun".equals(operate))
					tree+=boardicon+"<font color='red'>查找结果！</font>";
				else if("changeuserstatusrun".equals(operate))
					tree+=boardicon+"<font color='red'>修改会员状态成功！</font>";
				else if("deleteuserview".equals(operate))
					tree+=boardicon+"<font color='red'>删除会员！</font>";
				else if("deleteuserrun".equals(operate))
					tree+=boardicon+"<font color='red'>删除会员成功！</font>";
				else if("adminviewmember".equals(operate))
					tree+=boardicon+"<font color='red'>查看会员资料！</font>";
				else if("admineditmemberinfoview".equals(operate))
					tree+=boardicon+"<font color='red'>修改会员资料！</font>";
				else if("admineditmemberinforun".equals(operate))
					tree+=boardicon+"<font color='red'>修改会员资料成功！</font>";
				else if("admineditmemberpswdview".equals(operate))
					tree+=boardicon+"<font color='red'>修改会员密码！</font>";
				else if("admineditmemberpswdrun".equals(operate))
					tree+=boardicon+"<font color='red'>修改会员密码成功！</font>";
				else if("assigncategoryview".equals(operate))
					tree+=boardicon+"<font color='red'>为会员分配类别！</font>";
				else if("assigncategoryrun".equals(operate))
					tree+=boardicon+"<font color='red'>分配类别成功！</font>";
				else if("assignboardview".equals(operate))
					tree+=boardicon+"<font color='red'>为会员分配版块！</font>";
				else if("assignboardrun".equals(operate))
					tree+=boardicon+"<font color='red'>分配版块成功！</font>";
				else if("cancelcategorymasterview".equals(operate))
					tree+=boardicon+"<font color='red'>卸职类主身份！</font>";
				else if("cancelcategorymasterrun".equals(operate))
					tree+=boardicon+"<font color='red'>卸职类主身份成功！</font>";
				else if("cancelboardmasterview".equals(operate))
					tree+=boardicon+"<font color='red'>卸职版主身份！</font>";
				else if("cancelboardmasterrun".equals(operate))
					tree+=boardicon+"<font color='red'>卸职版主身份成功！</font>";					
			}
			else if("group".equals(module)){
				tree+=categoryicon+"<a href='"+webname+"/admin/group/e/groupmanager'>用户组管理</a><br>";
				tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if("modifygroupview".equals(operate))
					tree+=boardicon+"<font color='red'>修改用户组信息！</font>";
				else if("modifygrouprun".equals(operate))
					tree+=boardicon+"<font color='red'>修改用户组信息成功！</font>";
				else if("addmembertogroupview".equals(operate))
					tree+=boardicon+"<font color='red'>添加新成员到当前组！</font>";
				else if("addmembertogrouprun".equals(operate))
					tree+=boardicon+"<font color='red'>添加新成员结果！</font>";
				else if("listmemberingroup".equals(operate))
					tree+=boardicon+"<font color='red'>查看当前组下所有成员！</font>";
				else if("removefromgroupview".equals(operate))
					tree+=boardicon+"<font color='red'>从当前组移除成员！</font>";
				else if("removefromgrouprun".equals(operate))
					tree+=boardicon+"<font color='red'>移除成员成功！</font>";				
			}
		}		
	}
%>
<table width="99%" border="0" cellpadding="8" cellspacing="0">
	<tr><td><%=tree%></td></tr>
</table>
