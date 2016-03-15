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

import com.dao.AttachmentDao;
import com.dao.BoardDao;
import com.dao.CollectDao;
import com.dao.GradeDao;
import com.dao.ReplyDao;
import com.dao.TopicDao;
import com.toolsbean.MyFileHandler;
import com.toolsbean.StringHandler;
import com.valuebean.TopicBean;
import com.valuebean.UserBean;

public class TopicServlet extends SuperServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
		if(topicId==null){
			request.setAttribute("message","<li>����IDֵ����</li>");
			RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
			rd.forward(request,response);	
		}
		else{
			int boardId=new TopicDao().getBoardId(topicId);
			int categoryId=new BoardDao().getBelongToCategoryId(boardId);
			request.getSession().setAttribute("visittopic",topicId);
			request.getSession().setAttribute("visitboard",boardId);
			request.getSession().setAttribute("visitcategory",categoryId);
			request.setAttribute("visitboardstatus",new BoardDao().getBoardStatus(boardId));
			request.setAttribute("visittopicstatus",new TopicDao().getTopicStatus(topicId));
			
			String[] subPaths=(String[])request.getAttribute("subPaths");
			if(subPaths!=null&&subPaths.length==4){
				String operate=subPaths[3];
				try {
					Method md=getClass().getDeclaredMethod(operate,new Class[]{HttpServletRequest.class,HttpServletResponse.class});		//����opName����ֵ��ȡ����ΪopName����ֵ�ķ�����Class����ָ���÷����и�����������
					md.setAccessible(true);													//��Ϊtrue���ܵ���˽�з���
					md.invoke(this,new Object[]{request,response});							//���÷�����Object����ָ��������Ҫ�Ĳ���
					
					System.out.println("���÷�������"+md);
				}catch (Exception e) {
					invalidate(request,response);	
				}			
			}
			else
				invalidate(request,response);			
		}
	}	
	/** @���ܣ�����ۿ���������ķ��� */
	private void view(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";		
		
		try {
			forward=INDEXTEMP;			
			request.setAttribute("mainPage",getInitParameter("viewPage"));
			
			TopicDao topicDao=new TopicDao();
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			
			TopicBean single=topicDao.getTopicSingle(topicId);												//��ȡ������ϸ��Ϣ
			if(single!=null){
				single.setTopicHits(single.getTopicHits()+1);												//���Ѳ�ѯ��������ķ��ʴ����ӣ�
				topicDao.addTopicHits(topicId);																//�����ݿ�������ķ��ʴ����ӣ�
				
				UserBean author=single.getTopicAuthor();													//��ȡ���ӵ�����
				if(author!=null){
					single.getTopicAuthor().setMemberCharm(author.getMemberCharm()+1);						//���Ѳ�ѯ��������������ֵ�ӣ�
					new GradeDao().charmLook(single.getTopicAuthorName());									//�����ݿ�������������ֵ�ӣ�
				}
				request.setAttribute("topicsingle",single);
				
				//���´����ȡ��һ���⡢��һ����
				Map bothsides=topicDao.getBothSidesTopic(single.getTopicOperateTime(),(Integer)request.getSession().getAttribute("visitboard"));
				if(bothsides.size()!=0){
					request.setAttribute("prevId",bothsides.get("prevId"));
					request.setAttribute("nextId",bothsides.get("nextId"));
				}
			}
			else{
				message="<li>Ҫ�鿴�����Ӳ����ڻ��Ѿ���ɾ����</li>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;	
			}
		} catch (Exception e) {
			message="<li>�鿴����ʧ�ܣ�</li>";
			message+="<a href='javascript:window.history.go(-1)'>����</a>";
			forward=RUNFAIL;				
			e.printStackTrace();
		}

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	
	private void newreplylist(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		try{
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			ReplyDao replyDao=new ReplyDao();				
			List newreplylist=replyDao.getNewReplyList(topicId);
			request.setAttribute("newreplylist",newreplylist);					
		}catch(Exception e){
			e.printStackTrace();
		}				

		RequestDispatcher rd=request.getRequestDispatcher(getInitParameter("newreplylist"));
		rd.forward(request,response);
	}
	private void morereplylist(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		try{
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			ReplyDao replyDao=new ReplyDao();				
			String currentP=request.getParameter("currentP");
			String currentG=request.getParameter("currentG");
			String goWhich=request.getContextPath()+"/visit/topic/a/morereplylist?topicId="+topicId;
			
			List morereplylist=replyDao.getMoreReplyList(topicId,currentP,currentG,goWhich);
			request.setAttribute("morereplylist",morereplylist);
			request.setAttribute("pageBar",replyDao.getDaoPage());
		}catch(Exception e){
			e.printStackTrace();
		}				

		RequestDispatcher rd=request.getRequestDispatcher(getInitParameter("morereplylist"));
		rd.forward(request,response);
	}
	
	private void replyview(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String forward="";
		String message="";

		Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
		try {
			TopicBean single = new TopicDao().getTopicSingle(topicId);
			if(single==null){
				message="<li>Ҫ�ظ������Ӳ����ڻ��Ѿ���ɾ����</li>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",getInitParameter("replyoledit"));
				request.setAttribute("topicsingle",single);

				String fromquote=request.getParameter("fromquote");
				if("topic".equals(fromquote)){
					String quoteauthor=single.getTopicAuthorName();
					String quotecontent=single.getTopicContent();
					
					String quotetext=StringHandler.getQuoteText(quoteauthor,quotecontent);
					request.setAttribute("quotetext",StringHandler.changeQuoteSign(quotetext));
				}
				else if("reply".equals(fromquote)){
					Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
					Map qoutereply=new ReplyDao().getQuoteReply(replyId);
					String quoteauthor=(String)qoutereply.get("quoteauthor");
					String quotecontent=(String)qoutereply.get("quotecontent");
					
					String quotetext=StringHandler.getQuoteText(quoteauthor,quotecontent);
					request.setAttribute("quotetext",StringHandler.changeQuoteSign(quotetext));
				}
			}					
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
	private void replyrun(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";

		String posttype=request.getParameter("posttype");		
		Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
		String title=request.getParameter("title");		
		String content=StringHandler.saveQuoteText(request.getParameter("content"));
		if(!"oledit".equals(posttype))
			content=StringHandler.changehtml(content);		
		String author=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		String emotion=request.getParameter("emotion");
		String attachment=request.getParameter("attachment");
		String time=StringHandler.timeTostr(new Date());
		
		Object[] params={topicId,title,content,author,emotion,attachment,time};
		
		ReplyDao replyDao=new ReplyDao();
		int i=replyDao.reply(params);
		if(i<=0){													//�ظ�ʧ��
			message="<li>�ظ�ʧ�ܣ�<br></li>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{														//�ظ��ɹ�
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId+"#topic";						//�Զ�ת�����ۿ����������·��
			request.setAttribute("autoforward",autoforward);
			message="�� �ظ����ӳɹ���3����Զ�ת�������ظ������⣡";
			
			TopicDao topicDao=new TopicDao();
			GradeDao gradeDao=new GradeDao();			
			topicDao.addTopicReplyNum(topicId);																	//�ۼ�����Ļظ���	
			topicDao.updateOperateTime(topicId,time);															//��������ı�����ʱ��Ϊ��ǰʱ��
			gradeDao.experReply(((UserBean)request.getSession().getAttribute("loginer")).getId());				//���ӻ����˵ľ���ֵ
			gradeDao.charmReply(request.getParameter("author"));										//��������(��������)������ֵ
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
	
	/** @���ܣ��������༭����ҳ�������Servlet */
	private void modifyview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			TopicBean single=new TopicDao().getEditViewSingle(topicId);
			if(single==null){
				message="<li>Ҫ�༭�����Ӳ����ڻ��Ѿ���ɾ����</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;
			}
			else{
				if(!"2".equals(single.getTopicStatus())){
					message="<li>Ҫ�༭�����ӱ�������رգ����ܽ��б༭��</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else{
					forward=INDEXTEMP;
					request.setAttribute("mainPage",getInitParameter("modifyview"));				
					request.setAttribute("topicsingle",single);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	/** @���ܣ�����༭���������Servlet */
	private void modifyrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String emotion=request.getParameter("emotion");
		String attachment=request.getParameter("attachment");
		Object[] params={title,content,emotion,topicId};

		int i=new TopicDao().updateTopic(params);
		if(i<=0){
			message="<li>�༭ʧ�ܣ�</li>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			if("1".equals(attachment)){								//��Ӹ���
				String autoforward=request.getContextPath()+"/visit/attachment/c/uploadview?topicId="+topicId;			//�Զ�ת������Ӹ���ҳ���·��
				request.setAttribute("autoforward",autoforward);
				message="�� �༭�ɹ������潫ת������Ӹ���ҳ�棡";
			}
			else{													//û�и���
				String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//�Զ�ת�����ۿ����������·��
				request.setAttribute("autoforward",autoforward);
				message="�� �༭�ɹ���3����Զ�ת�������޸ĵ����ӣ�";
			}
		}			

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void changetypeview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward="";
		
		try {			
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			TopicBean single=new TopicDao().getEditViewSingle(topicId);
			if(single==null){
				request.setAttribute("message","<li>��Ҫ�޸ĵ����Ӳ����ڻ��Ѿ���ɾ����</li>");
				forward=RUNFAIL;	
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",getInitParameter("changetypeview"));
				request.setAttribute("topicsingle",single);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			

		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void changetyperun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			String oldtype=request.getParameter("oldtype");
			String newtype=request.getParameter("newtype");
			
			if(!oldtype.equals(newtype)){
				String time=StringHandler.timeTostr(new Date());
				Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
				Object[] params={newtype,time,topicId};
				
				int i=new TopicDao().updateType(params);
				if(i<=0){
					message="<li>�޸���������ʧ�ܣ�</li>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else{
					forward=INDEXTEMP;
					request.setAttribute("mainPage",RUNSUCCESS);
					
					Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
					BoardDao boardDao=new BoardDao();
					if(oldtype.equals("1"))						//���ԭ���������Ǿ�����
						boardDao.updateBestTopicNum("reduce",boardId);				//�������������ľ�����������
					else if(newtype.equals("1"))				//����µ������Ǿ�����
						boardDao.updateBestTopicNum("add",boardId);					//�������������ľ��������ӣ�
					
					String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//�Զ�ת�����ۿ����������·��
					request.setAttribute("autoforward",autoforward);
					message="�� �޸��������ͳɹ���3����Զ�ת�������޸ĵ����ӣ�";
				}				
			}
		}			

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void changestatusview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward="";
	
		try {
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			TopicBean single=new TopicDao().getEditViewSingle(topicId);
			if(single==null){
				request.setAttribute("message","<li>��Ҫ�޸ĵ����Ӳ����ڻ��Ѿ���ɾ����</li>");
				forward=RUNFAIL;	
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",getInitParameter("changestatusview"));
				request.setAttribute("topicsingle",single);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			

		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void changestatusrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			String time=StringHandler.timeTostr(new Date());
			String status=request.getParameter("status");
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			Object[] params={status,time,topicId};
			
			int i=new TopicDao().updateStatus(params);
			if(i<=0){
				message="<li>�޸�����״̬ʧ�ܣ�</li>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//�Զ�ת�����ۿ����������·��
				request.setAttribute("autoforward",autoforward);
				message="�� �޸�����״̬�ɹ���3����Զ�ת�������޸ĵ����ӣ�";
			}
		}			

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void moveview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			TopicBean single=new TopicDao().getEditViewSingle(topicId);
			if(single==null){
				message="<li>��Ҫ���Ƶ����Ӳ����ڻ��Ѿ���ɾ����</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;	
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",getInitParameter("moveview"));
				request.setAttribute("topicsingle",single);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void moverun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			String time=StringHandler.timeTostr(new Date());
			Integer newboardId =StringHandler.strToint(request.getParameter("newboardId"));
			try {
				if(new BoardDao().isexistbyid(newboardId)){
					Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
					Object[] params={newboardId,time,topicId};
					
					int i=new TopicDao().updateBoard(params);
					if(i<=0){
						message="<li>�ƶ�����ʧ�ܣ�</li><br>";
						message+="<a href='javascript:window.history.go(-1)>��������</a>'";
						forward=RUNFAIL;
					}
					else{
						forward=INDEXTEMP;
						request.setAttribute("mainPage",RUNSUCCESS);
						
						BoardDao boardDao=new BoardDao();
						String topicType=request.getParameter("topicType");
						Integer oldboardId=StringHandler.strToint(request.getParameter("oldboardId"));
						
						boardDao.reduceTopicNum(topicType, oldboardId);				//�����������ɰ�������������
						boardDao.addTopicNum(topicType, newboardId);				//�����������°����������ӣ�
						
						String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//�Զ�ת�����ۿ����������·��
						request.setAttribute("autoforward",autoforward);
						message="�� �ƶ����ӳɹ���3����Զ�ת�������ƶ������ӣ�";
					}				
				}
				else{
					message="<li>Ŀ�İ��(id="+newboardId+")�Ѿ���ɾ����</li>";
					message+="<a href='javascript:window.history.go(-1)>��������</a>'";
					forward=RUNFAIL;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	/** @���ܣ���ǰ���� */
	private void movefirst(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			TopicDao topicDao=new TopicDao();
			
			TopicBean topicBean = topicDao.getEditViewSingle(topicId);
			if(topicBean==null){
				message="<li>Ҫ��ǰ�����Ӳ����ڻ��Ѿ���ɾ����</li>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>'";
				forward=RUNFAIL;
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				topicDao.updateOperateTime(topicId,StringHandler.timeTostr(new Date()));
				String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//�Զ�ת�����ۿ����������·��
				request.setAttribute("autoforward",autoforward);
				message="�� ��ǰ���ӳɹ���3����Զ�ת��������ǰ�����ӣ�";
			}
		} catch (SQLException e) {
			message="<li>��ǰ����ʧ�ܣ�</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>����</a>";
			forward=RUNFAIL;
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void collecttopic(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		CollectDao collectDao=new CollectDao();
		int memberid=((UserBean)request.getSession().getAttribute("loginer")).getId();
		Integer topicid=StringHandler.strToint(request.getParameter("topicId"));
		
		boolean isexist=collectDao.isExist(memberid, topicid);						//��ѯ�û��Ƿ��Ѿ��ղ���ָ��������
		if(isexist){									//����Ѿ��ղ��˸���
			message="<li>���Ѿ��ղ��˸�����</li>";
			message+="<a href='javascript:window.history.go(-1)'>����</a>";
			forward=RUNFAIL;
		}
		else{											//û���ղظ�������Ҫ������ղص������Ƿ��Ѿ��ﵽ�����ֵ
			try {
				int num=collectDao.getCollectNum(memberid);							//��ȡ�ղص�����
				int max=Integer.parseInt(getServletContext().getInitParameter("collectmax"));			//��ȡ�����ղص�����ֵ
				if(num<max){
					String time=StringHandler.timeTostr(new Date());
					Object[] params={memberid,topicid,time};					
					int i=new TopicDao().addCoolect(params);
					if(i<=0){
						message="<li>�ղ�����ʧ�ܣ�</li>";
						message+="<a href='javascript:window.history.go(-1)'>����</a>";
						forward=RUNFAIL;
					}
					else{
						forward=INDEXTEMP;
						request.setAttribute("mainPage",RUNSUCCESS);						
						String autoforward=request.getContextPath()+"/visit/myself/a/mycollect";						//�Զ�ת�����ۿ��ҵ��ղ������·��
						request.setAttribute("autoforward",autoforward);
						message="�� �ղ����ӳɹ���3����Զ�ת���������ղؼУ�";
					}
				}
				else{
					message="<li>�����ղؼ�������</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>����</a><br>";
					message+="<a href='"+request.getContextPath()+"/visit/user/a/listmycollect'>�����ղؼ�</a>";			
					forward=RUNFAIL;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	
	private void deleteview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward="";
		
		try {
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			TopicBean topicsingle=new TopicDao().getEditViewSingle(topicId);
			if(topicsingle==null){
				request.setAttribute("message","<li>��Ҫɾ�������Ӳ����ڻ��Ѿ���ɾ����</li>");
				forward=RUNFAIL;	
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",getInitParameter("deleteview"));
				request.setAttribute("topicsingle",topicsingle);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deleterun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));			

			BoardDao boardDao=new BoardDao();
			TopicDao topicDao=new TopicDao();
			ReplyDao replyDao=new ReplyDao();
			AttachmentDao attachmentDao=new AttachmentDao();
			
			/** ɾ�������� */
			topicDao.deleteTopic(topicId);			
			
			/** ����������������������� */
			String topicType=request.getParameter("topicType");
			Integer boardId =StringHandler.strToint(request.getParameter("boardId"));
			boardDao.reduceTopicNum(topicType,boardId);												//���°����е�������			
			
			/** ɾ�������������ĸ��� */
			try {					
				//����ɾ�������ļ�
				List savenames=attachmentDao.getAttachmentSaveNameByTopic(topicId);
				deleteFiles(savenames);
				
				//Ȼ��ɾ�����ݿ�����Ϣ
				attachmentDao.deleteAttachmentByTopicId(topicId);
			} catch (SQLException e) {					
				e.printStackTrace();
			}
			
			/** ɾ������������лظ��� */
			replyDao.deleteReplyByTopicId(topicId);
			
			/** ɾ�������������лظ������еĸ��� */
			try {					
				//����ɾ�������ļ�
				List savenames=attachmentDao.getAttachmentSaveNameByTopicAndReply(topicId);
				deleteFiles(savenames);
				
				//Ȼ��ɾ�����ݿ�����Ϣ
				attachmentDao.deleteAttachmentByTopicAndReply(topicId);
			} catch (SQLException e) {					
				e.printStackTrace();
			}
			
			String autoforward=request.getContextPath()+"/visit/board/a/listspecialtopic?boardId="+boardId;		//�Զ�ת������ʾ�����б������·��
			request.setAttribute("autoforward",autoforward);
			message="�� ɾ�����ӳɹ���3����Զ�ת���������б�";

		}			

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	/** @���ܣ�����MyFileHandler���deleteFile()��������ɾ���ļ� */
	private void deleteFiles(List savenames){
		String filedir=getServletContext().getInitParameter("filedir");						//�ڵ�ǰWebӦ���´�Ÿ�����Ŀ¼
		String[] destfilepathname=null;									//�����洢Ҫɾ������������·��
		if(savenames!=null&&savenames.size()!=0){
			destfilepathname=new String[savenames.size()];
			for(int k=0;k<savenames.size();k++){
				String savename=(String)savenames.get(k);										//��ȡ���������ڴ�����ʹ�õ��ļ���
				String deletefilename=filedir+savename;											//����ڵ�ǰWebӦ���¸����Ĵ��·��
				destfilepathname[k]=getServletContext().getRealPath(deletefilename);			//��ȡ����������·��
			}
			MyFileHandler.deleteFile(destfilepathname);
		}
	}
	/** @���ܣ���ʾ���д���˵����� */
	private void listuncheck(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward="";
		
		List unchecktopics=null;
		UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
		
		if(loginer.getGroupId()==0){
			String message="<li>��û��Ȩ�޲鿴��������ӣ�</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>����</a>'";
			request.setAttribute("message",message);
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",getInitParameter("listunchecktopics"));

			if(loginer.getGroupId()>=3)					//��ȡ���д��������
				unchecktopics=new TopicDao().getAllUnCheckTopics();
			else if(loginer.getGroupId()==2)				//��ȡ�������ǰ�û���������еĴ��������
				unchecktopics=new TopicDao().getMyCategoryUnCheckTopics(loginer.getAssignCategoryId());
			else if(loginer.getGroupId()==1)				//��ȡ�������ǰ�û����а���еĴ��������
				unchecktopics=new TopicDao().getMyBoardUnCheckTopics(loginer.getAssignBoardId());
			request.setAttribute("unchecktopics",unchecktopics);
		}
		
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
}
