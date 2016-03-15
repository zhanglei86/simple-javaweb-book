package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDao;
import com.dao.CategoryDao;
import com.dao.CollectDao;
import com.dao.GroupBoardDao;
import com.dao.GroupCategoryDao;
import com.dao.MessageDao;
import com.dao.TopicDao;
import com.dao.UserDao;
import com.toolsbean.StringHandler;
import com.valuebean.MessageBean;
import com.valuebean.UserBean;

public class UserServlet extends SuperServlet {

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
	
	private void index(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("myinfoindex"));
		
		UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
		if(loginer.getGroupId()==2){
			//��ȡΪ�û�������������
			int[] assigncategorysid=loginer.getAssignCategoryId();
			if(assigncategorysid!=null&&assigncategorysid.length!=0){
				List assigncategorysname=new CategoryDao().getAssignCategorysName(assigncategorysid);
				request.setAttribute("assigncategorysname", assigncategorysname);
			}
		}
		else if(loginer.getGroupId()==1){
			//��ȡΪ�û�����İ������
			int[] assignboardsid=loginer.getAssignBoardId();
			if(assignboardsid!=null&&assignboardsid.length!=0){
				List assignboardsname=new BoardDao().getAssignBoardsName(assignboardsid);
				request.setAttribute("assignboardsname", assignboardsname);
			}
		}
		
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void editmyinfoview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("editmyinfoview"));
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void editmyinforun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String memberName=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		String sex=request.getParameter("sex");
		Integer age=StringHandler.strToint(request.getParameter("age"));
		String oicq=request.getParameter("oicq");
		Object[] params={sex,age,oicq,memberName};
		int i=new UserDao().updateMemberInfo(params);
		if(i<=0){
			message="<li>�޸ĸ�������ʧ�ܣ�</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
			loginer.setMemberSex(sex);
			loginer.setMemberAge(age);
			loginer.setMemberOICQ(oicq);
			
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/visit/myself/a/index";
			request.setAttribute("autoforward",autoforward);
			message="�� �޸ĸ������ϳɹ���3����Զ�ת��������������ҳ��";
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void editmyiconview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("editmyiconview"));
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void editmyiconrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer memberId=StringHandler.strToint(request.getParameter("memberId"));
		String icon=request.getParameter("icon");

		Object[] params={icon,memberId};
		int i=new UserDao().updateMyIcon(params);
		if(i<=0){
			message="<li>����ͷ��ʧ�ܣ�</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
			loginer.setMemberIcon(icon);
			
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/visit/myself/a/index";
			request.setAttribute("autoforward",autoforward);
			message="�� ����ͷ��ɹ���3����Զ�ת��������������ҳ��";
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void editmysignview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("editmysignview"));
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void editmysignrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer memberId=StringHandler.strToint(request.getParameter("memberId"));
		String sign=request.getParameter("sign");

		Object[] params={sign,memberId};
		int i=new UserDao().updateMemberSign(params);
		if(i<=0){
			message="<li>�޸ĸ���ǩ��ʧ�ܣ�</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
			loginer.setMemberSign(sign);
			
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/visit/myself/a/index";
			request.setAttribute("autoforward",autoforward);
			message="�� �޸ĸ���ǩ���ɹ���3����Զ�ת��������������ҳ��";
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void editmypswdview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("editmypswdview"));
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void editmypswdrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����ľ�������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			UserDao userDao=new UserDao();
			String memberName=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
			String newpswd=request.getParameter("newpswd");
			
			Object[] params={newpswd,memberName};
			int i=userDao.updateMemberPswd(params);
			if(i<=0){
				message="<li>�޸�����ʧ�ܣ�</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{
				UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
				loginer.setMemberPswd(newpswd);
				
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/visit/myself/a/index";
				request.setAttribute("autoforward",autoforward);
				message="�� �޸�����ɹ���3����Զ�ת��������������ҳ��";
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void cancelautologin(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		System.out.println("ȡ���Զ���¼���Զ�ת��-"+request.getHeader("referer"));
		
		String webname=request.getContextPath();
		webname=webname.substring(1);
		
		Cookie delloginname=new Cookie(webname+".loginname",null);
		Cookie delloginpswd=new Cookie(webname+".loginpswd",null);
		delloginname.setPath("/");
		delloginpswd.setPath("/");		
		delloginname.setMaxAge(0);
		delloginpswd.setMaxAge(0);	
		response.addCookie(delloginname);
		response.addCookie(delloginpswd);
		
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",RUNSUCCESS);
		
		String autoforward=request.getContextPath()+"/visit/myself/a/index";
		request.setAttribute("autoforward",autoforward);

		request.setAttribute("message","�� ȡ���Զ���¼�ɹ���3����Զ�ת��������������ҳ��");
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void mycollect(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		int loginerid=((UserBean)request.getSession().getAttribute("loginer")).getId();
		try {
			List collectlist=new CollectDao().getCollects(loginerid);
			request.setAttribute("collectlist",collectlist);

			int num=collectlist.size();
			int max=Integer.parseInt(getServletContext().getInitParameter("collectmax"));			//��ȡ�����ղص�����ֵ
			request.setAttribute("num",num);
			request.setAttribute("max",max);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("listmycollect"));
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deletemycollect(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
		if(topicId==null){
			message="<li>����IDֵ����</li>";
			forward=RUNFAIL;
		}
		else{
			int loginerid=((UserBean)request.getSession().getAttribute("loginer")).getId();
			int i=new CollectDao().deleteOne(topicId,loginerid);
			if(i<=0){
				message="<li>ɾ��ʧ�ܣ�</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/visit/myself/a/mycollect";						//�Զ�ת�����б���ʾ�ҵ���Ϣ����
				request.setAttribute("autoforward",autoforward);
				message="�� ɾ���ɹ���3����Զ�ת���������ղؼУ�";
			}
		}
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	
	private void clearmycollect(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		int loginerid=((UserBean)request.getSession().getAttribute("loginer")).getId();
		int i=new CollectDao().deleteAll(loginerid);
		if(i<=0){
			message="<li>����ղؼ�ʧ�ܣ�</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>����</a>";
			forward=RUNFAIL;
		}
		else{			
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/visit/myself/a/mycollect";						//�Զ�ת�����б���ʾ�ҵ���Ϣ����
			request.setAttribute("autoforward",autoforward);
			message="�� ����ղؼгɹ���3����Զ�ת���������ղؼУ�";
		}
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void sendmessageview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("sendmessageview"));
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
	private void sendmessagerun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";		
		
		UserDao userDao=new UserDao();
		String getter=request.getParameter("getter");
		try {
			int memberid = userDao.validatename(getter);
			if(memberid>0){
				String savetosend=request.getParameter("savetosend");
				
				String sender=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
				String title=request.getParameter("title");
				String emotion=request.getParameter("emotion");
				String content=request.getParameter("content");
				String time=StringHandler.timeTostr(new Date());
				String mark="1";
				String deletesender="";
				if("yes".equals(savetosend))
					deletesender="0";
				else
					deletesender="1";
				String deletegetter="0";
				Object[] params={title,content,time,emotion,mark,sender,getter,deletesender,deletegetter};
				MessageDao messageDao=new MessageDao();
				int i=messageDao.insert(params);
				messageDao.closed();
				if(i<=0){
					message="<li>��Ϣ����ʧ�ܣ�</li>";
					message+="<br><a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{
					forward=INDEXTEMP;
					request.setAttribute("mainPage",RUNSUCCESS);
					
					String autoforward=request.getContextPath()+"/visit/myself/a/mymessagebox";						//�Զ�ת�����б���ʾ�ҵ���Ϣ����
					request.setAttribute("autoforward",autoforward);
					message="�� ������Ϣ�ɹ���3����Զ�ת����������Ϣ�б�";
				}
			}
			else{
				message="<li>������ <b>"+getter+"</b> ��Ա��</li>";
				message+="<br><a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
	private void mymessagebox(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String loginername=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		MessageDao messageDao=new MessageDao();
		
		int inceptunreadnum=messageDao.getInceptUnReadNum(loginername);
		int inceptallnum=messageDao.getInceptAllNum(loginername);
		int sendallnum=messageDao.getSendAllNum(loginername);
		int messagemax=Integer.parseInt(getServletContext().getInitParameter("messagemax"));
		
		messageDao.closed();
		request.setAttribute("inceptallnum",inceptallnum);
		request.setAttribute("inceptunreadnum",inceptunreadnum);
		request.setAttribute("sendallnum",sendallnum);
		request.setAttribute("messagemax",messagemax);
		
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("listmessagemain"));
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void viewmymessage(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer messageId=StringHandler.strToint(request.getParameter("messageId"));
		if(messageId==null){
			message="<li>��ϢIDֵ����</li>";
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",getInitParameter("viewmymessage"));
			
			try {
				//��ȡ�鿴����Ϣ����ϸ����
				MessageDao messageDao=new MessageDao();
				MessageBean single= messageDao.getMessageSingle(messageId);
				if(single==null){
					message="<li>Ҫ�鿴����Ϣ�����ڻ��Ѿ���ɾ����</li>";
					forward=RUNFAIL;
				}
				else{
					if(single.getMessageReadmark().equals("1")){					//����鿴����δ����Ϣ
						messageDao.updateReadMarkForInceptMessage(messageId);		//����Ϊ�Ѷ�
						//���û�δ������Ϣ������
						UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
						int unread=loginer.getNewmessages();
						if(unread>0)
							loginer.setNewmessages(unread-1);
					}
					messageDao.closed();
					request.setAttribute("messagesingle",single);
					
					//��ȡ��Ϣ�ķ����߻��������Ϣ
					UserDao userDao=new UserDao();
					UserBean author=null;
					String from=request.getParameter("from");
					if("send".equals(from))
						author=userDao.getUserSingle(single.getMessageGetter());
					else if("incept".equals(from))
						author=userDao.getUserSingle(single.getMessageSender());
					request.setAttribute("messageauthor",author);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void listmyinceptmessages(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String loginername=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		try {
			int messagemax=Integer.parseInt(getServletContext().getInitParameter("messagemax"));
			List inceptmessages=new MessageDao().getInceptMessages(loginername,messagemax);
			request.setAttribute("inceptmessages",inceptmessages);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		RequestDispatcher rd=request.getRequestDispatcher(getInitParameter("listmyinceptmessages"));
		rd.forward(request,response);
	}
	private void listmysendmessages(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String loginername=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		try {
			List sendmessages=new MessageDao().getSendMessages(loginername);
			request.setAttribute("sendmessages",sendmessages);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		RequestDispatcher rd=request.getRequestDispatcher(getInitParameter("listmysendmessages"));
		rd.forward(request,response);
	}
	private void deleteonemessage(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer messageId=StringHandler.strToint(request.getParameter("messageId"));
		if(messageId==null){
			message="<li>��ϢIDֵ����</li>";
			forward=RUNFAIL;
		}
		else{
			String from=request.getParameter("from");
			if(!"incept".equals(from)&&!"send".equals(from)){
				message="<li>����ѡ��δ֪��</li>";
				forward=RUNFAIL;
			}
			else{
				MessageDao messageDao=new MessageDao();
				int[] deletemessages={messageId};
				message=deleterun(deletemessages,messageDao,request);
				messageDao.closed();
				if(!message.equals(""))
					forward=RUNFAIL;
				else{
					forward=INDEXTEMP;
					request.setAttribute("mainPage",RUNSUCCESS);
					
					String autoforward=request.getContextPath()+"/visit/myself/a/mymessagebox?from="+from;						//�Զ�ת�����б���ʾ�ҵ���Ϣ����
					request.setAttribute("autoforward",autoforward);
					message="�� ɾ����Ϣ�ɹ���3����Զ�ת�����������䣡";
				}						
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deleteselectmessages(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String from=request.getParameter("from");
		if(!"incept".equals(from)&&!"send".equals(from)){
			message="<li>����ѡ��δ֪��</li>";
			forward=RUNFAIL;
		}
		else{
			String[] selectmessages=request.getParameterValues("selectmessages");
			int[] deletemessages=StringHandler.changeToIntArray(selectmessages);
			MessageDao messageDao=new MessageDao();

			message=deleterun(deletemessages,messageDao,request);
			messageDao.closed();
			if(!message.equals("")){
				message+="<br><a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/visit/myself/a/mymessagebox?from="+from;						//�Զ�ת�����б���ʾ�ҵ���Ϣ����
				request.setAttribute("autoforward",autoforward);
				message="�� ɾ����Ϣ�ɹ���3����Զ�ת�����������䣡";
			}						
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private String deleterun(int[] deletemessages,MessageDao messageDao,HttpServletRequest request){
		String message="";
		if(deletemessages==null||deletemessages.length==0)
			message="<li>��ѡ��Ҫɾ������Ϣ��</li>";
		else{
			try {
				int len=deletemessages.length;
				for(int i=0;i<len;i++){
					MessageBean single=messageDao.getDeleteSingle((Integer)deletemessages[i]);
					if(single==null)
						message+="<li>��Ҫɾ����'(id="+deletemessages[i]+")'��Ϣ�����ڻ��Ѿ���ɾ����</li>";
					else{
						int k=0;
						String from=request.getParameter("from");
						if("incept".equals(from)){
							String deletesender=single.getDeleteSender();
							if(deletesender.equals("1"))
								k=messageDao.deleteMessage(deletemessages[i]);
							else if(deletesender.equals("0"))
								k=messageDao.updateDeleteForInceptMessage(deletemessages[i]);
						} 
						else if("send".equals(from)){
							String deletegetter=single.getDeleteGetter();
							if(deletegetter.equals("1"))
								k=messageDao.deleteMessage(deletemessages[i]);
							else if(deletegetter.equals("0"))
								k=messageDao.updateDeleteForSendMessage(deletemessages[i]);
						}
						if(k<=0)
							message+="<li>ɾ��'(id="+deletemessages[i]+")'��Ϣʱʧ�ܣ�</li>";
					}				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			messageDao.closed();
		}
		return message;
	}
	private void listmyposttopic(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		try {
			String memberName=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
			String currentP=request.getParameter("currentP");
			String currentG=request.getParameter("currentG");
			String goWhich=request.getContextPath()+"/visit/myself/a/listmyposttopic?";
			
			TopicDao topicDao=new TopicDao();			
			List myposttopics=topicDao.getMemberPostTopicList(memberName,currentP,currentG,goWhich);
			
			request.setAttribute("memberposttopics",myposttopics);
			request.setAttribute("pageBar",topicDao.getDaoPage());
		} catch (SQLException e) {
			System.out.println("��ȡ�ҷ��������ʧ�ܣ�");
			e.printStackTrace();
		}
		
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("listmyposttopic"));
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void listmyreplytopic(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		try {
			String memberName=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
			String currentP=request.getParameter("currentP");
			String currentG=request.getParameter("currentG");
			String goWhich=request.getContextPath()+"/visit/myself/a/listmyreplytopic?";
			
			TopicDao topicDao=new TopicDao();			
			List myreplytopics=topicDao.getMyReplyTopicList(memberName,currentP,currentG,goWhich);
			
			request.setAttribute("myreplytopics",myreplytopics);
			request.setAttribute("pageBar",topicDao.getDaoPage());
		} catch (SQLException e) {
			System.out.println("��ȡ�һظ�������ʧ�ܣ�");
			e.printStackTrace();
		}
		
		String forward=INDEXTEMP;
		request.setAttribute("mainPage",getInitParameter("listmyreplytopic"));
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void viewmember(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String memberName=request.getParameter("memberName");
		if(memberName==null||memberName.equals("")){
			message="<li>��Ա���ƴ���</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserBean member=new UserDao().getUserSingle(memberName);
				if(member==null){
					message="<li>��Ա(name="+memberName+")�����ڻ��Ѿ���ɾ����</li><br>";
					forward=RUNFAIL;
				}
				else{
					if(member.getGroupId()==2){
						//��ȡΪ�û�������������
						int[] assigncategorysid=member.getAssignCategoryId();
						if(assigncategorysid!=null&&assigncategorysid.length!=0){
							List assigncategorysname=new CategoryDao().getAssignCategorysName(assigncategorysid);
							request.setAttribute("assigncategorysname", assigncategorysname);
						}
					}
					else if(member.getGroupId()==1){
						//��ȡΪ�û�����İ������
						int[] assignboardsid=member.getAssignBoardId();
						if(assignboardsid!=null&&assignboardsid.length!=0){
							List assignboardsname=new BoardDao().getAssignBoardsName(assignboardsid);
							request.setAttribute("assignboardsname", assignboardsname);
						}
					}
					
					request.setAttribute("viewmember",member);
					request.setAttribute("mainPage",getInitParameter("viewmember"));
					forward=INDEXTEMP;
				}
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void listmemberposttopic(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward="";
		
		String memberName=request.getParameter("memberName");
		if(memberName==null||memberName.equals("")){
			request.setAttribute("message","<li>��Ա���ƴ���</li>");
			forward=RUNFAIL;
		}
		else{
			try {
				forward=INDEXTEMP;
				request.setAttribute("mainPage",getInitParameter("listmemberposttopic"));
				
				String currentP=request.getParameter("currentP");
				String currentG=request.getParameter("currentG");
				String goWhich=request.getContextPath()+"/visit/user/a/listmemberposttopic?memberName="+memberName;
				
				TopicDao topicDao=new TopicDao();			
				List memberposttopics=topicDao.getMemberPostTopicList(memberName,currentP,currentG,goWhich);
				
				request.setAttribute("memberposttopics",memberposttopics);
				request.setAttribute("pageBar",topicDao.getDaoPage());
				
			} catch (SQLException e) {
				System.out.println("��ȡ��Ա���������ʧ�ܣ�");
				e.printStackTrace();
			}
		}		
		
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void membermanager(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String currentP=request.getParameter("currentP");
		String currentG=request.getParameter("currentG");
		String goWhich=request.getContextPath()+"/admin/user/c/membermanager";
		
		try {
			UserDao userDao=new UserDao();			
			List userList = userDao.getUserList(currentP,currentG,goWhich);

			request.setAttribute("userList",userList);
			request.setAttribute("pageBar",userDao.getDaoPage());
		} catch (SQLException e) {
			e.printStackTrace();
		}		
			
		request.setAttribute("mainPage",getInitParameter("managerindex"));
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}
	private void assignboardview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer memberid=StringHandler.strToint(request.getParameter("memberid"));
		if(memberid==null){
			message="<li>��ԱIDֵ����</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserBean usersingle=new UserDao().getUserForAssignById(memberid);
				if(usersingle==null){
					message="<li>��Ա(id="+memberid+")�����ڻ��Ѿ���ɾ����</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else if(usersingle.getGroupId()>=3){
					message="<li>�û�Ա����ݿ��Թ���������𼰰�飬����Ҫ�����飡</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else if(usersingle.getGroupId()!=1){
					message="<li>����Ϊ�ǰ��������Ա�����飡</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("mainPage",getInitParameter("assignboardview"));
					forward=ADMINTEMP;
					
					request.setAttribute("usersingle",usersingle);
					int[] assignboardsid=usersingle.getAssignBoardId();
					if(assignboardsid!=null&&assignboardsid.length!=0){
						request.setAttribute("assignboardsid",StringHandler.ArrayToString(assignboardsid));
						List assignboardsname=new BoardDao().getAssignBoardsName(assignboardsid);
						request.setAttribute("assignboardsname", assignboardsname);
					}
					
					//��ȡ�������͵�ID������
					Map categorys = new CategoryDao().getCategoryMap();
					request.setAttribute("categorys",categorys);
					
					//���λ�ȡĳ��������а���ID������
					if(categorys!=null&&categorys.size()!=0){
						Map boards=new HashMap();
						Iterator itc=categorys.keySet().iterator();
						while(itc.hasNext()){
							int categoryId=(Integer)itc.next();
							Map board=new BoardDao().getBoardMap(categoryId);
							boards.put(categoryId,board);
						}
						request.setAttribute("boards",boards);
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
	private void assignboardrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer assignboardid=StringHandler.strToint(request.getParameter("assignboardid"));
		try {
			boolean mark = new BoardDao().isexistbyid(assignboardid);
			if(!mark){
				message="<li>ѡ��İ��(id="+assignboardid+")�����ڻ��Ѿ���ɾ����</li>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{
				Integer memberid=StringHandler.strToint(request.getParameter("memberid"));
				int i=new GroupBoardDao().assignBoardToMember(memberid, assignboardid);
				if(i<=0){
					message="<li>Ϊ��Ա������ʧ�ܣ�</li>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{
					forward=ADMINTEMP;
					request.setAttribute("mainPage",RUNSUCCESS);
					
					String autoforward=request.getParameter("autoforward");
					request.setAttribute("autoforward",autoforward);
					message="�� ������ɹ���3����Զ�ת����֮ǰ������";
				}		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void assigncategoryview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer memberid=StringHandler.strToint(request.getParameter("memberid"));
		if(memberid==null){
			message="<li>��ԱIDֵ����</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserBean usersingle=new UserDao().getUserForAssignById(memberid);
				if(usersingle==null){
					message="<li>��Ա(id="+memberid+")�����ڻ��Ѿ���ɾ����</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else if(usersingle.getGroupId()>=3){
					message="<li>�û�Ա����ݿ��Թ���������𼰰�飬����Ҫ�������</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>����</a>";
					forward=RUNFAIL;
				}
				else if(usersingle.getGroupId()!=2){
					message="<li>����Ϊ�����������Ա�������</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("mainPage",getInitParameter("assigncategoryview"));
					forward=ADMINTEMP;
					
					request.setAttribute("usersingle",usersingle);
					int[] assigncategorysid=usersingle.getAssignCategoryId();
					if(assigncategorysid!=null&&assigncategorysid.length!=0){
						request.setAttribute("assigncategorysid",StringHandler.ArrayToString(assigncategorysid));
						List assigncategorysname=new CategoryDao().getAssignCategorysName(assigncategorysid);
						request.setAttribute("assigncategorysname", assigncategorysname);
					}					
					//��ȡ�������͵�ID������
					Map categorys = new CategoryDao().getCategoryMap();
					request.setAttribute("categorys",categorys);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void assigncategoryrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer assigncategoryid=StringHandler.strToint(request.getParameter("assigncategoryid"));
		try {
			boolean mark = new CategoryDao().isexistbyid(assigncategoryid);
			if(!mark){
				message="<li>ѡ������(id="+assigncategoryid+")�����ڻ��Ѿ���ɾ����</li>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{
				Integer memberid=StringHandler.strToint(request.getParameter("memberid"));
				int i=new GroupCategoryDao().assignCategoryToMember(memberid, assigncategoryid);
				if(i<=0){
					message="<li>Ϊ��Ա������ʧ�ܣ�</li>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{
					forward=ADMINTEMP;
					request.setAttribute("mainPage",RUNSUCCESS);
					
					String autoforward=request.getParameter("autoforward");
					request.setAttribute("autoforward",autoforward);
					message="�� �������ɹ���3����Զ�ת����֮ǰ������";
				}		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void cancelcategorymasterview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String memberName=request.getParameter("memberName");
		String categoryName=request.getParameter("categoryName");

		if(memberName==null||memberName.equals("")||categoryName==null||categoryName.equals("")){
			message="<li>��Աδ֪���������δ֪��</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserDao userDao=new UserDao();
				int memberid= userDao.validatename(memberName);
				if(memberid<=0){
					message="<li>��Ա(name="+memberName+")�����ڣ�</li>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{					
					categoryName=new String(categoryName.getBytes("ISO8859_1"),"gb2312");
					int categoryid=new CategoryDao().validatename(categoryName);
					if(categoryid<=0){
						message="<li>���(name="+categoryName+")�����ڣ�</li>";
						message+="<a href='javascript:window.history.go(-1)'>��������</a>";
						forward=RUNFAIL;
					}
					else{						
						request.setAttribute("memberid",memberid);
						request.setAttribute("categoryid",categoryid);
						request.setAttribute("categoryName",categoryName);
						request.setAttribute("mainPage",getInitParameter("cancelcategorymasterview"));
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
	private void cancelcategorymasterrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			Integer memberId=StringHandler.strToint(request.getParameter("memberId"));
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			int i=new GroupCategoryDao().deleteassign(memberId,categoryId);
			if(i<=0){
				message="<li>жְʧ�ܣ�</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{
				forward=ADMINTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/admin/user/c/adminviewmember?memberName="+request.getParameter("memberName");
				request.setAttribute("autoforward",autoforward);
				message="<li>�� жְ�ɹ���3����Զ�ת�����鿴��Ա����ҳ�棡</li>";
			}				
		}	
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void cancelboardmasterview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String memberName=request.getParameter("memberName");
		String boardName=request.getParameter("boardName");

		if(memberName==null||memberName.equals("")||boardName==null||boardName.equals("")){
			message="<li>��Աδ֪��������δ֪��</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserDao userDao=new UserDao();
				int memberid= userDao.validatename(memberName);
				if(memberid<=0){
					message="<li>��Ա(name="+memberName+")�����ڣ�</li>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{					
					boardName=new String(boardName.getBytes("ISO8859_1"),"gb2312");
					int boardid=new BoardDao().validatename(boardName);
					if(boardid<=0){
						message="<li>���(name="+boardName+")�����ڣ�</li>";
						message+="<a href='javascript:window.history.go(-1)'>��������</a>";
						forward=RUNFAIL;
					}
					else{						
						request.setAttribute("memberid",memberid);
						request.setAttribute("boardid",boardid);
						request.setAttribute("boardName",boardName);
						request.setAttribute("mainPage",getInitParameter("cancelboardmasterview"));
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
	private void cancelboardmasterrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			Integer memberId=StringHandler.strToint(request.getParameter("memberId"));
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			int i=new GroupBoardDao().deleteassign(memberId,boardId);
			if(i<=0){
				message="<li>жְʧ�ܣ�</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{
				forward=ADMINTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/admin/user/c/adminviewmember?memberName="+request.getParameter("memberName");
				request.setAttribute("autoforward",autoforward);
				message="<li>�� жְ�ɹ���3����Զ�ת�����鿴��Ա����ҳ�棡</li>";
			}				
		}	
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void changeuserstatusrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer memberid=StringHandler.strToint(request.getParameter("memberId"));
		try {
			UserDao userDao=new UserDao();
			boolean mark=userDao.isexistbyid(memberid);
			if(!mark){
				message="<li>��Ա(id="+memberid+")�����ڻ��Ѿ���ɾ����</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{
				String action=request.getParameter("action");
				if(action==null||action.equals("")||(!"disable".equals(action)&&!"enable".equals(action))){
					message="<li>��Ա״̬(action="+action+")��Ч!</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;					
				}
				else{
					Object[] params=null;
					if("disable".equals(action))
						params=new Object[]{"0",memberid};	
					if("enable".equals(action))
						params=new Object[]{"1",memberid};	
					int i=userDao.updateUserStatus(params);
					if(i<=0){
						message="<li>�޸Ļ�Ա״̬ʧ�ܣ�</li><br>";
						message+="<a href='javascript:window.history.go(-1)'>��������</a>";
						forward=RUNFAIL;
					}
					else{
						forward=ADMINTEMP;
						request.setAttribute("mainPage",RUNSUCCESS);
						
						String autoforward=request.getHeader("referer");
						request.setAttribute("autoforward",autoforward);
						message="�� �޸Ļ�Ա״̬�ɹ���3����Զ�ת����֮ǰ������";
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
	private void deleteuserview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer deletememberId=StringHandler.strToint(request.getParameter("deletememberId"));
		if(deletememberId==null){
			message="<li>��ԱIDֵ����</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserDao userDao=new UserDao();
				UserBean usersingle=userDao.getUserSingle(deletememberId);
				if(usersingle==null){
					message="<li>��Ա(id="+deletememberId+")�����ڻ��Ѿ���ɾ����</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>��������</a>";
					forward=RUNFAIL;
				}
				else{
					UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
					int loginerId=loginer.getId();
					if(loginerId==deletememberId){
						message="<li>����ɾ���Լ���</li><br>";
						message+="<a href='javascript:window.history.go(-1)'>��������</a>";
						forward=RUNFAIL;
					}
					else if(loginer.getGroupId()<usersingle.getGroupId()){
						message="<li>��û��Ȩ��ɾ�����и��߼�����ݵĻ�Ա��</li><br>";
						message+="<a href='javascript:window.history.go(-1)'>��������</a>";
						forward=RUNFAIL;
					}
					else{
						request.setAttribute("usersingle",usersingle);
						request.setAttribute("mainPage",getInitParameter("deleteuserview"));
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
	private void deleteuserrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			Integer deletememberId=StringHandler.strToint(request.getParameter("deletememberId"));
			int i=new UserDao().delete(deletememberId);
			if(i<=0){
				message="<li>ɾ����Ա(id="+deletememberId+")ʧ�ܣ�</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{
				forward=ADMINTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getParameter("autoforward");
				request.setAttribute("autoforward",autoforward);
				message="�� ɾ����Ա�ɹ���3����Զ�ת����֮ǰ������";
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void addnewuserview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		request.setAttribute("mainPage",getInitParameter("addnewuserview"));		
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}
	private void checkmember(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String name=request.getParameter("name");
		boolean disabled=true;
		try {
			//��ѯnameָ�����û��Ƿ����
			int memberid = new UserDao().validatename(name);
			if(memberid>0)										//���ڸû�Ա����
				disabled=true;
			else												//������
				disabled=false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("disabled",disabled);		
		request.setAttribute("mainPage",getInitParameter("addnewuserview"));
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);		
	}	
	private void addnewuserrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String name=request.getParameter("name");
		String pswd=request.getParameter("pswd");
		String sex=request.getParameter("sex");
		String age=request.getParameter("age");
		String oicq=request.getParameter("oicq");
		String sign=request.getParameter("sign");		
		String time=StringHandler.timeTostr(new Date());
		if(oicq==null||oicq.equals(""))
			oicq="��";
		if(sign==null||sign.equals(""))
			sign="����һƬ�հף�";
		
		Object[] params={0,name,pswd,sex,age,oicq,"default.gif",sign,"1",time,time,0,0,0};
		int i=new UserDao().insert(params);
		if(i<=0){
			message="<li>����»�Աʧ�ܣ�</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			forward=ADMINTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/admin/user/c/membermanager";
			request.setAttribute("autoforward",autoforward);
			message="<li>����»�Ա�ɹ���3����Զ�ת���� <b>��Ա����</b> ģ����ҳ��";
		}
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void searchuserview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		request.setAttribute("mainPage",getInitParameter("searchuserview"));		
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}
	private void searchuserrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String searchname=request.getParameter("searchname");
		String searchid=request.getParameter("searchid");
		if(searchname==null)
			searchname="";
		if(searchid==null)
			searchid="";
		Object[] params={searchid,searchname};
		try {
			UserBean searchsingle = new UserDao().search(params);
			request.setAttribute("searchsingle",searchsingle);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("mainPage",getInitParameter("searchuserview"));
		RequestDispatcher rd=request.getRequestDispatcher(ADMINTEMP);
		rd.forward(request,response);
	}
	private void adminviewmember(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String memberName=request.getParameter("memberName");
		if(memberName==null||memberName.equals("")){
			message="<li>��Ա���ƴ���</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserBean member=new UserDao().getUserSingle(memberName);
				if(member==null){
					message="<li>��Ա(name="+memberName+")�����ڻ��Ѿ���ɾ����</li><br>";
					forward=RUNFAIL;
				}
				else{
					if(member.getGroupId()==2){
						//��ȡΪ�û�������������
						int[] assigncategorysid=member.getAssignCategoryId();
						if(assigncategorysid!=null&&assigncategorysid.length!=0){
							List assigncategorysname=new CategoryDao().getAssignCategorysName(assigncategorysid);
							request.setAttribute("assigncategorysname", assigncategorysname);
						}
					}
					else if(member.getGroupId()==1){
						//��ȡΪ�û�����İ������
						int[] assignboardsid=member.getAssignBoardId();
						if(assignboardsid!=null&&assignboardsid.length!=0){
							List assignboardsname=new BoardDao().getAssignBoardsName(assignboardsid);
							request.setAttribute("assignboardsname", assignboardsname);
						}
					}
					
					request.setAttribute("viewmember",member);
					request.setAttribute("mainPage",getInitParameter("adminviewmember"));
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
	private void admineditmemberinfoview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String memberName=request.getParameter("memberName");
		try {
			UserBean edituser = new UserDao().getUserSingle(memberName);
			if(edituser==null){
				message="<li>Ҫ�޸ĵĻ�Ա�����ڻ��Ѿ���ɾ����</li>";
				forward=RUNFAIL;
			}
			else{
				request.setAttribute("edituser",edituser);
				request.setAttribute("mainPage",getInitParameter("admineditmemberinfoview"));
				forward=ADMINTEMP;			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void admineditmemberinforun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String memberName=request.getParameter("memberName");
		String sex=request.getParameter("sex");
		Integer age=StringHandler.strToint(request.getParameter("age"));
		String oicq=request.getParameter("oicq");
		if(age==null)age=0;
		if(oicq==null)oicq="";
		Object[] params={sex,age,oicq,memberName};
		int i=new UserDao().updateMemberInfo(params);
		if(i<=0){
			message="<li>�޸ĸ�������ʧ�ܣ�</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			forward=ADMINTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/admin/user/c/adminviewmember?memberName="+memberName;
			request.setAttribute("autoforward",autoforward);
			message="�� �޸Ļ�Ա���ϳɹ���3����Զ�ת������Ա������ҳ��";
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void admineditmemberpswdview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		String memberName=request.getParameter("memberName");
		if(memberName==null||memberName.equals("")){
			message="<li>��Ա���ƴ���</li><br>";
			message+="<a href='javascript:window.history.go(-1)'></a>";
			forward=RUNFAIL;			
		}
		else{
			try {
				int memberId = new UserDao().validatename(memberName);
				if(memberId<=0){
					message="<li>��Ա(name="+memberName+")�����ڻ��Ѿ���ɾ����</li><br>";
					message+="<a href='javascript:window.history.go(-1)'></a>";
					forward=RUNFAIL;
				}
				else{
					request.setAttribute("mainPage",getInitParameter("admineditmemberpswdview"));
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
	private void admineditmemberpswdrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			UserDao userDao=new UserDao();
			String memberName=request.getParameter("memberName");
			String newpswd=request.getParameter("newpswd");
			
			Object[] params={newpswd,memberName};
			int i=userDao.updateMemberPswd(params);
			if(i<=0){
				message="<li>�޸�����ʧ�ܣ�</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>��������</a>";
				forward=RUNFAIL;
			}
			else{		
				forward=ADMINTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/admin/user/c/adminviewmember?memberName="+memberName;
				request.setAttribute("autoforward",autoforward);
				message="�� �޸�����ɹ���3����Զ�ת��������������ҳ��";
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}	

}