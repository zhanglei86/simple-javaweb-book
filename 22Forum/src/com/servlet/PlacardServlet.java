package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDao;
import com.dao.PlacardDao;
import com.dao.UserDao;
import com.toolsbean.StringHandler;
import com.valuebean.PlacardBean;
import com.valuebean.UserBean;

public class PlacardServlet extends SuperServlet {

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
			request.getSession().setAttribute("visitcategory",new BoardDao().getBelongToCategoryId(boardId));				//���浱ǰ�����������ID��session�У��������䵱ǰ���ʵ����
			request.getSession().setAttribute("visitboardstatus",new BoardDao().getBoardStatus(boardId));
			
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
	
	private void view(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer placardId=StringHandler.strToint(request.getParameter("placardId"));
		if(placardId==null){
			message="<li>����IDֵ����</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				PlacardBean single = new PlacardDao().getPlacardSingle(placardId);
				if(single==null){
					message="<li>Ҫ�鿴�Ĺ��治���ڻ��Ѿ���ɾ����</li>";
					forward=RUNFAIL;
				}
				else{
					forward=INDEXTEMP;
					request.setAttribute("mainPage",getInitParameter("viewplacard"));
					request.setAttribute("placardsingle",single);
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	/** @���ܣ�����������뷢������ҳ��ķ��� */
	private void postplacardview(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("postplacardview"));			
		
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
	/** @���ܣ��������󷢲�����ķ��� */
	private void postplacardrun(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String forward="";
		String message="";
		request.setAttribute("mainPage",getInitParameter("postplacardview"));			
		
		int currentboardId=StringHandler.strToint(request.getParameter("boardId"));
		
		int categoryId=new BoardDao().getBelongToCategoryId(currentboardId);
		int boardId=currentboardId;
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String time=StringHandler.timeTostr(new Date());
		String author=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		String type=request.getParameter("type");
		
		if("2".equals(type)){
			categoryId=-1;
			boardId=-1;
		}
		else if("1".equals(type))
			boardId=-1;
		
		PlacardDao placardDao=new PlacardDao();
		Object[] params={categoryId,boardId,title,content,time,type,author};
		int i=placardDao.add(params);
		if(i<=0){
			message="<li>�ֲ�����ʧ�ܣ�</li>";
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			int placardId=placardDao.getId(time,author);			
			String autoforward=request.getContextPath()+"/visit/placard/a/view?placardId="+placardId+"&boardId="+currentboardId;
			request.setAttribute("autoforward",autoforward);
			message="�� ��������ɹ���3����Զ�ת���������Ĺ��棡";
		}		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
	private void deleteplacardview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer placardId=StringHandler.strToint(request.getParameter("placardId"));
		if(placardId==null){
			message="<li>����IDֵ����</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				PlacardBean single=new PlacardDao().getPlacardSingle(placardId);
				if(single==null){
					message="<li>Ҫɾ���Ĺ��治���ڻ��Ѿ���ɾ����</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else{
					forward=INDEXTEMP;
					request.setAttribute("mainPage",getInitParameter("deleteplacardview"));
					request.setAttribute("deletesingle",single);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void deleteplacardrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			Integer placardId=StringHandler.strToint(request.getParameter("placardId"));
			int i=new PlacardDao().deletePlacard(placardId);
			if(i<=0){
				message="<li>ɾ������ʧ�ܣ�</li><br>";
				message="<a href='javascript:window.history.go(-1)>��������</a>";
				forward=RUNFAIL;
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
				String autoforward=request.getContextPath()+"/visit/board/a/listspecialtopic?boardId="+boardId;	
				request.setAttribute("autoforward",autoforward);
				message="�� ɾ������ɹ���3����Զ�ת������ǰ��飡";
			}			
		}		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
}
