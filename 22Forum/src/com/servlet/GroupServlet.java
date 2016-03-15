package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.GroupBoardDao;
import com.dao.GroupCategoryDao;
import com.dao.GroupDao;
import com.dao.UserDao;
import com.toolsbean.StringHandler;
import com.valuebean.GroupBean;
import com.valuebean.UserBean;

public class GroupServlet extends SuperServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String[] subPaths=(String[])request.getAttribute("subPaths");
		if(subPaths!=null&&subPaths.length==4){
			String operate=subPaths[3];
			System.out.println("�������ƣ�"+operate);
			try {
				Method md=getClass().getDeclaredMethod(operate,new Class[]{HttpServletRequest.class,HttpServletResponse.class});		//����opName����ֵ��ȡ����ΪopName����ֵ�ķ�����Class����ָ���÷����и�����������
				md.setAccessible(true);													//��Ϊtrue���ܵ���˽�з���
				md.invoke(this,new Object[]{request,response});							//���÷�����Object����ָ��������Ҫ�Ĳ���
			}catch (Exception e) {
				e.printStackTrace();
				invalidate(request,response);	
			}			
		}
		else{
			invalidate(request,response);
		}
	}
	private void groupmanager(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		List grouplist=new GroupDao().getGroupList();					//��ȡ�����û���
		request.setAttribute("grouplist",grouplist);
		request.setAttribute("mainPage",getInitParameter("managerindex"));
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}	
	private void listmemberingroup(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
		if(groupId==null){
			message="<li>�û���ID����</li>";
			message+="<a href='javascript:window.history.go(-1)'>����</a>";
			forward=RUNFAIL;
		}
		else{
			String currentP=request.getParameter("currentP");
			String currentG=request.getParameter("currentG");
			String goWhich=request.getContextPath()+"/admin/group/e/listmemberingroup?groupId="+groupId;
			
			try {
				UserDao userDao=new UserDao();			
				List memberlist=userDao.getGroupUserList(groupId,currentP, currentG, goWhich);
				request.setAttribute("memberlist",memberlist);
				request.setAttribute("pageBar",userDao.getDaoPage());
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}
		
		request.setAttribute("mainPage",getInitParameter("listmemberingroup"));
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}	
	private void modifygroupview(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
		if(groupId==null){
			message="<li>�û���ID����</li>";
			message+="<a href='javascript:window.history.go(-1)'>����</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				GroupBean single = new GroupDao().getGroupSingle(groupId);
				if(single==null){
					message="<li>Ҫ�޸ĵ��û��鲻���ڻ��Ѿ���ɾ����</li>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("groupsingle",single);
					request.setAttribute("mainPage",getInitParameter("modifygroupview"));
					forward=ADMINTEMP;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			request.setAttribute("message",message);
			RequestDispatcher rd=request.getRequestDispatcher(forward);
			rd.forward(request,response);
		}
	}
	private void modifygrouprun(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
		String groupInfo=request.getParameter("groupInfo");
		Object[] params={groupInfo,groupId};
		int i=new GroupDao().update(params);
		if(i<=0){
			message="<li>�޸�ʧ�ܣ�</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>����</a>";
			forward=RUNFAIL;
		}
		else{
			request.setAttribute("mainPage",RUNSUCCESS);
			forward=ADMINTEMP;			
			
			message="<li>�޸��û���ɹ���3����Զ���ת�� <b>�û������</b> ģ����ҳ��</li>";
			String autoforward=request.getContextPath()+"/admin/group/e/groupmanager";
			request.setAttribute("autoforward",autoforward);
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addmembertogroupview(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
		if(groupId==null){
			message="<li>�û���ID����</li>";
			message+="<a href='javascript:window.history.go(-1)'>����</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				GroupBean single = new GroupDao().getGroupSingle(groupId);
				if(single==null){
					message="<li>Ҫ���Ա���û��鲻���ڻ��Ѿ���ɾ����</li>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("groupsingle",single);
					request.setAttribute("mainPage",getInitParameter("addmembertogroupview"));
					forward=ADMINTEMP;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addmembertogrouprun(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{			
			Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
			List inputmembernames=StringHandler.StringToList(request.getParameter("inputmembernames")+"\r\n");
			
			if(inputmembernames!=null&&inputmembernames.size()!=0){
				request.setAttribute("mainPage",getInitParameter("addmembertogroupresult"));
				forward=ADMINTEMP;
				
				try {
					UserDao userDao=new UserDao();
					List newmembernames=new ArrayList();
					
					//��������ÿ���û������Ƿ���ڣ������Ƿ�Ϊ��ͨ��Ա(group_id�ֶ�ֵΪ0)
					for(int i=0;i<inputmembernames.size();i++){
						String name=(String)inputmembernames.get(i);
						UserBean single=userDao.getUserSingleForAddGroupMember(name);
						if(single==null)									//����û����Ʋ�������̳��
							message+="�� ��Ա <font color='red'>"+name+"</font> �����ڣ�<br>";
						else if(single.getGroupId()==0){						//������ڸ��û���������Ƿ�Ϊ��ͨ��Ա
							newmembernames.add(name);							//�������ͨ��Ա��������û���
							message+="�� ��Ա <font color='red'>"+name+"</font> ��ӳɹ���<br>";
						}
						else
							message+="�� ��Ա <font color='red'>"+name+"</font> �Ѿ���������ְ�񣬲��ܼ�ְ��<br>";
					}
					
					if(newmembernames.size()!=0){
						int i=userDao.updateUserGroupId(groupId,newmembernames);
						if(i<=0){
							message="<li>��ӳ�Աʧ�ܣ�</li><br>";
							message+="<a href='javascript:window.history.go(-1)'>��������</a>";
							forward=RUNFAIL;
						}
						else{
							message+=">><a href='"+request.getHeader("referer")+"'>�������</a><br>";
							message+=">><a href='"+request.getContextPath()+"/admin/group/e/groupmanager'>�����û������ģ����ҳ</a>";
						}
					}
					else
						message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else{
				message="<li>��ȡ����ĳ�Ա����ʧ�ܣ�</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void removefromgroupview(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
		String removememberName=request.getParameter("removememberName");
		if(groupId==null||removememberName==null||removememberName.equals("")){
			message="<li>�û���IDֵ���Ա���ƴ���</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserDao userDao=new UserDao();
				int removememberId = userDao.validatename(removememberName);
				if(removememberId<=0){
					message="<li>��Ա(name="+removememberName+")�����ڣ�</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{
					UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
					int loginerId=loginer.getId();
					if(loginerId==removememberId){
						message="<li>�����Ƴ��Լ���</li><br>";
						message+="<a href='javascript:window.history.go(-1)'>��������</a>";
						forward=RUNFAIL;
					}
					else if(loginer.getGroupId()<groupId){
						message="<li>��û��Ȩ���Ƴ����и��߼�����ݵĻ�Ա��</li><br>";
						message+="<a href='javascript:window.history.go(-1)'>��������</a>";
						forward=RUNFAIL;
					}
					else{
						request.setAttribute("removememberId",removememberId);
						request.setAttribute("mainPage",getInitParameter("removefromgroupview"));
						forward=ADMINTEMP;
					}					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void removefromgrouprun(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{	
			Integer groupId=StringHandler.strToint(request.getParameter("groupId"));
			Integer removememberId=StringHandler.strToint(request.getParameter("removememberId"));
			String removememberName=request.getParameter("removememberName");
			
			List members=new ArrayList();
			members.add(removememberName);
			int i=new UserDao().updateUserGroupId(0,members);							//����Ա���û���ID(group_id�ֶ�)�޸�Ϊ0
			if(i<=0){
				message="<li>�Ƴ���Աʧ�ܣ�</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{
				if(groupId==2)						//��������������Ƴ���Ա
					new GroupCategoryDao().deleteallassignformember(removememberId);
				else if(groupId==1)					//����Ӱ��������Ƴ���Ա
					new GroupBoardDao().deleteallassignformember(removememberId);
				
				request.setAttribute("mainPage",RUNSUCCESS);
				forward=ADMINTEMP;			
				
				message="<li>�Ƴ���Ա�ɹ���3����Զ���ת���鿴���Աҳ�棡</li>";
				String autoforward=request.getContextPath()+"/admin/group/e/listmemberingroup?groupId="+groupId;
				request.setAttribute("autoforward",autoforward);
			}			
		}		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
}
