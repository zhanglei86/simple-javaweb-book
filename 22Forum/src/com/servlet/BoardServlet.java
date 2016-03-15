package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDao;
import com.dao.CategoryDao;
import com.dao.GradeDao;
import com.dao.GroupBoardDao;
import com.dao.PlacardDao;
import com.dao.TopicDao;
import com.dao.UserDao;
import com.toolsbean.StringHandler;
import com.valuebean.BoardBean;
import com.valuebean.UserBean;

public class BoardServlet extends SuperServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
		if(boardId==null){
			request.setAttribute("message","<li>���IDֵ����</li>");
			RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
			rd.forward(request,response);	
		}
		else{
			request.getSession().setAttribute("visitboard",boardId);													//���浱ǰ���ID��session�У��������䵱ǰ���ʵİ��
			request.getSession().setAttribute("visitcategory",new BoardDao().getBelongToCategoryId(boardId));					//���浱ǰ�����������ID��session�У��������䵱ǰ���ʵ����
			request.setAttribute("visitboardstatus",new BoardDao().getBoardStatus(boardId));
			
			//���´�������ʵ�֣�����������������Ӧ�ķ����Ĺ���
			String[] subPaths=(String[])request.getAttribute("subPaths");
			if(subPaths!=null&&subPaths.length==4){
				String operate=subPaths[3];													//��ȡ����Ĳ���(��������)
				try {
					Method md=getClass().getDeclaredMethod(operate,new Class[]{HttpServletRequest.class,HttpServletResponse.class});		//����opName����ֵ��ȡ����ΪopName����ֵ�ķ�����Class����ָ���÷����и�����������
					md.setAccessible(true);													//��Ϊtrue���ܵ���˽�з���
					md.invoke(this,new Object[]{request,response});							//���÷�����Object����ָ��������Ҫ�Ĳ���
				}catch (Exception e) {
					e.printStackTrace();
					invalidate(request,response);	
				}			
			}
			else
				invalidate(request,response);
		}		
	}

	/** @���ܣ���ȡ���漰�ö����� */
	private void listspecialtopic(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("listmain"));			
		
		try {
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));								//��ȡ��ǰ���ID
			BoardDao boardDao=new BoardDao();				
			
			BoardBean boardsingle=boardDao.getBoardSingle(boardId);													//��ȡ�����Ϣ
			List placardlist=new PlacardDao().getPlacardList((Integer)boardsingle.getCategoryId(),boardId);			//��ȡ����
			List topTopicList=new TopicDao().getTopTopicList(boardId);												//��ȡ�ö�����
			
			request.setAttribute("boardsingle",boardsingle);
			request.setAttribute("placardlist",placardlist);
			request.setAttribute("topTopicList",topTopicList);				
		} catch (SQLException e) {			
			System.out.println("��ȡ���漰�ö�����ʧ�ܣ�");
			e.printStackTrace();
		}

		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
	/** @���ܣ���ȡ���ö����� */
	private void listcommontopic(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		try {
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));						//��ȡ��ǰ���ID
			String currentP=request.getParameter("currentP");
			String currentG=request.getParameter("currentG");
			String goWhich=request.getContextPath()+"/visit/board/a/listcommontopic?boardId="+boardId;
			
			TopicDao topicDao=new TopicDao();			
			List topicList=topicDao.getTopicList(boardId,currentP,currentG,goWhich);
			
			request.setAttribute("topicList",topicList);
			request.setAttribute("pageBar",topicDao.getDaoPage());
		} catch (SQLException e) {
			System.out.println("��ȡ���ö�����ʧ�ܣ�");
			e.printStackTrace();
		}
		
		RequestDispatcher rd=request.getRequestDispatcher(getInitParameter("listcommontopic"));
		rd.forward(request,response);
	}
	/** @���ܣ�������ķ�������ҳ�� */
	private void postview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		try {
			BoardDao boardDao=new BoardDao();
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			BoardBean boardsingle = boardDao.getBoardSingle(boardId);
			if(boardsingle==null){
				message="<li>��鲻���ڻ��Ѿ���ɾ����</li>";
				message+="<a href='javascript:window.history.go(-1)>����</a>";
				forward=RUNFAIL;
			}
			else{
				if(!"2".equals(boardsingle.getBoardStatus())){								//������رհ��
					message="<li>��ǰ��鱻������رգ����ܷ������ӣ�</li>";
					message+="<a href='javascript:window.history.go(-1)>����</a>";
					forward=RUNFAIL;
				}
				else{																		//���Ű��
					forward=INDEXTEMP;
					request.setAttribute("mainPage",getInitParameter("postview"));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	/** @���ܣ��������� */
	private void postrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String author=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		String emotion=request.getParameter("emotion");
		String hits="0";
		String type="0";
		String status="2";
		String replyNum="0";
		String attachment=request.getParameter("attachment");
		String posttime=StringHandler.timeTostr(new Date());
		String operatetime=posttime;
		Object[] params={boardId,title,content,author,emotion,hits,type,status,replyNum,"0",posttime,operatetime};
		
		TopicDao topicDao=new TopicDao();
		int i=topicDao.postTopic(params);
		if(i<=0){					//����ʧ��
			message="<li>��������ʧ�ܣ�</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</>";
			forward=RUNFAIL;
		}						
		else{																	//����ɹ�
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			new UserDao().updatePostNum(author);								//�ۼӻ�Ա��������			
			new BoardDao().addTopicNum(boardId);								//�ۼӵ�ǰ����������
			new GradeDao().experPost(((UserBean)request.getSession().getAttribute("loginer")).getId());		//���ӷ����˵ľ���ֵ
	
			int topicId=topicDao.getJustTopicId(author, operatetime);
			if("1".equals(attachment)){											//��Ӹ���
				String autoforward=request.getContextPath()+"/visit/attachment/c/uploadview?topicId="+topicId;			//�Զ�ת������Ӹ���ҳ���·��
				request.setAttribute("autoforward",autoforward);
				request.setAttribute("message","�� ����ɹ������潫ת������Ӹ���ҳ�棡");
			}
			else{																//û�и���
				String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//�Զ�ת�����鿴������Ϣ�����·��
				request.setAttribute("autoforward",autoforward);
				request.setAttribute("message","�� ����ɹ���3����Զ�ת��������������ӣ�");
			}
		}
		
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void updateorder(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
		if(boardId==null){
			String message="<li>���IDֵ����</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>����</a>";
			request.setAttribute("message",message);
			RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
			rd.forward(request,response);
		}
		else{
			BoardDao boardDao=new BoardDao();
			try {
				BoardBean single = boardDao.getBoardSingle(boardId);
				if(single==null){
					String message="Ҫ��������İ�鲻���ں��Ѿ���ɾ����</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					request.setAttribute("message",message);
					RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
					rd.forward(request,response);
				}
				else{
					int maxorder=Integer.parseInt(getServletContext().getInitParameter("maxorder"));
					int order=single.getBoardOrder();
					
					String direction=request.getParameter("direction");
					if("up".equals(direction)){
						if(order>0)
							boardDao.updateOrderToUp(boardId);
					}
					else if("down".equals(direction)){
						if(order<maxorder)
							boardDao.updateOrderToDown(boardId);
					}	
					response.sendRedirect(request.getContextPath()+"/admin/category/c/forummanager");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private void addboardmasterview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			BoardBean single = new BoardDao().getBoardSingle(boardId);
			if(single==null){
				message="<li>Ҫ����°����İ�鲻���ں��Ѿ���ɾ����</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;
			}
			else{
				request.setAttribute("boardsingle",single);
				request.setAttribute("mainPage",getInitParameter("addboardmasterview"));
				forward=ADMINTEMP;
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addboardmasterrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			String newmaster=request.getParameter("newmaster");
			
			try {
				UserDao userDao=new UserDao();
				UserBean single = userDao.getUserSingle(newmaster);
				if(single==null){									//����û����Ʋ�������̳��
					message+="�û� <font color='red'>"+newmaster+"</font> �����ڣ�<br>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else if(single.getGroupId()!=1){
					message+="�û� <font color='red'>"+newmaster+"</font> �����ڰ��������Ա������Ϊ������飡<br>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else if(StringHandler.isexist(boardId,single.getAssignBoardId())){
					message="<li>�Ѿ�Ϊ�û� "+newmaster+" �����˵�ǰ��飡</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{
					int i=new GroupBoardDao().addnewmaster(single.getId(), boardId);
					if(i<=0){
						message="<li>����°���ʧ�ܣ�</li>";
						message+="<a href='javascript:window.history.go(-1)'>��������</a>";
						forward=RUNFAIL;
					}
					else{
						request.setAttribute("mainPage",RUNSUCCESS);
						forward=ADMINTEMP;
						
						message="<li>����°����ɹ���3����Զ���ת�� <b>��̳����</b> ģ����ҳ��</li>";
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
	private void modifyboardview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			BoardBean single = new BoardDao().getBoardSingle(boardId);
			if(single==null){
				message="<li>Ҫ�޸���Ϣ�İ�鲻���ں��Ѿ���ɾ����</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;
			}
			else{
				request.setAttribute("mainPage",getInitParameter("modifyboardview"));
				forward=ADMINTEMP;
				
				//��ȡ�������͵�ID������
				Map categorys=new CategoryDao().getCategoryMap();
				request.setAttribute("categorys",categorys);
				request.setAttribute("boardsingle",single);
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void modifyboardrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
		int categoryId=StringHandler.strToint(request.getParameter("categoryid"));
		String boardname=request.getParameter("boardname");
		String boardinfo=request.getParameter("boardinfo");
		String boardstatus=request.getParameter("boardstatus");
		int boardorder=StringHandler.strToint(request.getParameter("boardorder"));
		Object[] params={categoryId,boardname,boardinfo,boardstatus,boardorder,boardId};
		
		int i=new BoardDao().update(params);
		if(i<=0){
			message="<li>�޸İ����Ϣʧ�ܣ�</li>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			request.setAttribute("mainPage",RUNSUCCESS);
			forward=ADMINTEMP;	
			
			message="<li>�޸İ����Ϣ�ɹ���3����Զ���ת�� <b>��̳����</b> ģ����ҳ��</li>";
			String autoforward=request.getContextPath()+"/admin/category/c/forummanager";
			request.setAttribute("autoforward",autoforward);
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deleteboardview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			BoardDao boardDao=new BoardDao();
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));

			BoardBean single =boardDao.getBoardSingle(boardId);
			if(single==null){
				message="<li>Ҫɾ���İ�鲻���ڻ��Ѿ���ɾ����</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;
			}
			else{				
				if(single.getBoardAllTopicNum()>0){
					message="<li>�ð���¾������ӣ����ܽ���ɾ����</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("boardsingle",single);
					request.setAttribute("mainPage",getInitParameter("deleteboardview"));
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
	private void deleteboardrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			int i=new BoardDao().delete(boardId);
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
