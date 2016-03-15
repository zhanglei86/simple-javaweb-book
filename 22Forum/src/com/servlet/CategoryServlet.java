package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDao;
import com.dao.CategoryDao;
import com.dao.GroupCategoryDao;
import com.dao.UserDao;
import com.toolsbean.StringHandler;
import com.valuebean.CategoryBean;
import com.valuebean.UserBean;

public class CategoryServlet extends SuperServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String[] subPaths=(String[])request.getAttribute("subPaths");
		if(subPaths!=null&&subPaths.length==4){
			String operate=subPaths[3];
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
	private void alllist(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		try{
			List categorylist=new CategoryDao().getCategoryList();					//��ȡ�������
			if(categorylist!=null&&categorylist.size()!=0){							//��������ȡÿ������µ����а��
				request.setAttribute("categorylist",categorylist);
				Map allboardmap=new HashMap();
				for(int i=0;i<categorylist.size();i++){
					int categoryId=((CategoryBean)categorylist.get(i)).getId();
					List oneboardlist=new BoardDao().getBoardList(categoryId);
					allboardmap.put(categoryId,oneboardlist);						//ͨ��Map�����Ե�ǰ���ID��Ϊ�ؼ���key���浱ǰ����µ����а�飻				
				}
				request.setAttribute("allboards",allboardmap);						//���������������а��
			}	
		}catch(SQLException e){
			System.out.println("��ȡ��̳���ʧ�ܣ�");
			e.printStackTrace();
		}		
		
		request.setAttribute("mainPage",getInitParameter("alllistPage"));
		RequestDispatcher rd=request.getRequestDispatcher(INDEXTEMP);
		rd.forward(request,response);
	}
	private void onelist(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try{
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				message="<li>���IDֵ����</li>";
				forward=RUNFAIL;
			}
			else{
				CategoryBean single=new CategoryDao().getCategorySingle(categoryId);							//��ȡĳ�����
				if(single==null){
					message="<li>Ҫ���ʵ���𲻴��ں��Ѿ���ɾ����</li>";
					forward=RUNFAIL;
				}
				else{																	//��ȡ������µ����а���
					forward=INDEXTEMP;
					request.setAttribute("mainPage",getInitParameter("onelistPage"));
					request.setAttribute("category",single);
					
					List oneboardlist=new BoardDao().getBoardList(categoryId);
					request.setAttribute("oneboardlist",oneboardlist);								//���浱ǰ����µ����а���				
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}			
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void forummanager(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		try{
			List categorylist=new CategoryDao().getCategoryList();					//��ȡ�������
			if(categorylist!=null&&categorylist.size()!=0){							//��������ȡÿ������µ����а��
				request.setAttribute("categorylist",categorylist);
				Map allboardmap=new HashMap();
				for(int i=0;i<categorylist.size();i++){
					int categoryId=((CategoryBean)categorylist.get(i)).getId();
					List oneboardlist=new BoardDao().getAdminBoardList(categoryId);
					allboardmap.put(categoryId,oneboardlist);						//ͨ��Map�����Ե�ǰ���ID��Ϊ�ؼ���key���浱ǰ����µ����а�飻				
				}
				request.setAttribute("allboards",allboardmap);						//���������������а��
			}
		}catch(SQLException e){
			System.out.println("��̨��ȡ��̳���ʧ�ܣ�");
			e.printStackTrace();
		}		
		
		request.setAttribute("mainPage",getInitParameter("managerindex"));
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}
	
	private void updateorder(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		try {
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				request.setAttribute("message","<li>���IDֵ����</li>");
				RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
				rd.forward(request,response);
			}
			else{
				CategoryDao categoryDao=new CategoryDao();
				CategoryBean single = categoryDao.getCategorySingle(categoryId);
				if(single==null){
					String message="<li>Ҫ�����������𲻴��ں��Ѿ���ɾ����</li>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					request.setAttribute("message",message);
					RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
					rd.forward(request,response);
				}
				else{
					int maxorder=Integer.parseInt(getServletContext().getInitParameter("maxorder"));
					int order=single.getCategoryOrder();
					
					String direction=request.getParameter("direction");
					if("up".equals(direction)){
						if(order>0)
							categoryDao.updateOrderToUp(categoryId);
					}
					else if("down".equals(direction)){
						if(order<maxorder)
							categoryDao.updateOrderToDown(categoryId);
					}	
					response.sendRedirect(request.getContextPath()+"/admin/category/c/forummanager");
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	private void addcategoryview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		request.setAttribute("mainPage",getInitParameter("addcategoryview"));
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}
	private void addcategoryrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String categoryName=request.getParameter("categoryName");
		CategoryDao categoryDao=new CategoryDao();
		try {
			if(categoryDao.isexistbyname(categoryName)){
				message="<li>��� '"+categoryName+"' �Ѿ����ڣ����������룡</li>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{
				String categoryInfo=request.getParameter("categoryInfo");
				int categoryOrder=0;
				String categoryFoundTime=StringHandler.timeTostr(new Date());
				Object[] params={categoryName,categoryInfo,categoryOrder,categoryFoundTime};
				int i=categoryDao.insert(params);
				if(i<=0){
					message="<li>��������ʧ�ܣ�</li>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("mainPage",RUNSUCCESS);
					forward=ADMINTEMP;			
					
					message="<li>��������ɹ���3����Զ���ת�� <b>��̳����</b> ģ����ҳ��</li>";
					String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
					request.setAttribute("autoforward",autoforward);
				}			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addboardview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				request.setAttribute("message","<li>���IDֵ����</li>");
				forward=RUNFAIL;
			}
			else{
				CategoryBean single = new CategoryDao().getCategorySingle(categoryId);
				if(single==null){
					message="<li>Ҫ����°�����𲻴��ں��Ѿ���ɾ����</li>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("categorysingle",single);
					request.setAttribute("mainPage",getInitParameter("addboardview"));
					forward=ADMINTEMP;				
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addboardrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String boardName=request.getParameter("boardname");
		BoardDao boardDao=new BoardDao();
		try {
			if(boardDao.isexistbyname(boardName)){
				message="<li>��� '"+boardName+"' �Ѿ����ڣ����������룡</li>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{
				Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
				String boardInfo=request.getParameter("boardinfo");
				String boardStatus=request.getParameter("boardstatus");
				int boardOrder=0;
				int boardAllTopicNum=0;
				int boardBestTopicNum=0;
				String boardFoundTime=StringHandler.timeTostr(new Date());
				Object[] params={categoryId,boardName,boardInfo,boardStatus,boardOrder,boardAllTopicNum,boardBestTopicNum,boardFoundTime};
				int i=boardDao.insert(params);
				if(i<=0){
					message="<li>����°��ʧ�ܣ�</li>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("mainPage",RUNSUCCESS);
					forward=ADMINTEMP;			
					
					message="<li>����°��ɹ���3����Զ���ת�� <b>��̳����</b> ģ����ҳ��</li>";
					String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
					request.setAttribute("autoforward",autoforward);
				}			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addcategorymasterview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				request.setAttribute("message","<li>���IDֵ����</li>");
				forward=RUNFAIL;
			}
			else{
				CategoryBean single = new CategoryDao().getCategorySingle(categoryId);
				if(single==null){
					message="<li>Ҫ�������������𲻴��ڻ��Ѿ���ɾ����</li>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("categorysingle",single);
					request.setAttribute("mainPage",getInitParameter("addcategorymasterview"));
					forward=ADMINTEMP;
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addcategorymasterrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			String newmaster=request.getParameter("newmaster");

			try {
				UserDao userDao=new UserDao();
				UserBean single = userDao.getUserSingle(newmaster);
				if(single==null){									//��������ڸ��û���
					message+="�û� <font color='red'>"+newmaster+"</font> �����ڣ�";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else if(single.getGroupId()!=2){
					message+="�û� <font color='red'>"+newmaster+"</font> ���������������Ա������Ϊ��������";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else if(StringHandler.isexist(categoryId,single.getAssignCategoryId())){
					message="<li>�Ѿ�Ϊ�û� "+newmaster+" �����˵�ǰ���</li>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{
					int i=new GroupCategoryDao().addnewmaster(single.getId(), categoryId);
					if(i<=0){
						message="<li>���������ʧ�ܣ�</li>";
						message+="<a href='javascript:window.history.go(-1)'>��������</a>";
						forward=RUNFAIL;
					}
					else{
						request.setAttribute("mainPage",RUNSUCCESS);
						forward=ADMINTEMP;
						
						message="<li>����������ɹ���3����Զ���ת�� <b>��̳����</b> ģ����ҳ��</li>";
						String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
						request.setAttribute("autoforward",autoforward);
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
	private void modifycategoryview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				request.setAttribute("message","<li>���IDֵ����</li>");
				forward=RUNFAIL;
			}
			else{
				CategoryBean single = new CategoryDao().getCategorySingle(categoryId);
				if(single==null){
					message="<li>Ҫ�޸���Ϣ����𲻴��ں��Ѿ���ɾ����</li>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("categorysingle",single);
					request.setAttribute("mainPage",getInitParameter("modifycategoryview"));
					forward=ADMINTEMP;
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void modifycategoryrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
		String categoryname=request.getParameter("categoryname");
		String categoryinfo=request.getParameter("categoryinfo");
		Integer categoryorder=StringHandler.strToint(request.getParameter("categoryorder"));
		Object[] params={categoryname,categoryinfo,categoryorder,categoryId};
		
		int i=new CategoryDao().update(params);
		if(i<=0){
			message="<li>�޸������Ϣʧ�ܣ�</li>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			request.setAttribute("mainPage",RUNSUCCESS);
			forward=ADMINTEMP;	
			
			message="<li>�޸������Ϣ�ɹ���3����Զ���ת�� <b>��̳����</b> ģ����ҳ��</li>";
			String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
			request.setAttribute("autoforward",autoforward);
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deletecategoryview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			CategoryDao categoryDao=new CategoryDao();
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			if(categoryId==null){
				request.setAttribute("message","<li>���IDֵ����</li>");
				forward=RUNFAIL;
			}
			else{
				CategoryBean single =categoryDao.getCategorySingle(categoryId);
				if(single==null){
					message="<li>Ҫɾ������𲻴��ں��Ѿ���ɾ����</li>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else{
					//��ѯ��������Ƿ��а��
					boolean mark=new BoardDao().isexistincategory(categoryId);
					if(mark){				//���ڰ��
						message="<li>������´��ڰ�飬���ܽ���ɾ��������</li>";
						message+="<a href='javascript:window.history.go(-1)'>����</a>";
						forward=RUNFAIL;
					}
					else{					//������
						request.setAttribute("categorysingle",single);
						request.setAttribute("mainPage",getInitParameter("deleteycategoryview"));
						forward=ADMINTEMP;
					}				
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deletecategoryrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			int i=new CategoryDao().delete(categoryId);
			if(i<=0){
				message="<li>ɾ�����ʧ�ܣ�</li>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{				
				request.setAttribute("mainPage",RUNSUCCESS);
				forward=ADMINTEMP;
				
				message="<li>ɾ�����ɹ���3����Զ���ת�� <b>��̳����</b> ģ����ҳ��</li>";
				String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
				request.setAttribute("autoforward",autoforward);
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
}