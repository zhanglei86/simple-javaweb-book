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
				Method md=getClass().getDeclaredMethod(operate,new Class[]{HttpServletRequest.class,HttpServletResponse.class});		//根据opName变量值获取名称为opName变量值的方法。Class数组指定该方法中各参数的类型
				md.setAccessible(true);													//设为true后将能调用私有方法
				md.invoke(this,new Object[]{request,response});							//调用方法，Object数组指定方法需要的参数
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
			//获取为用户分配的类别名称
			int[] assigncategorysid=loginer.getAssignCategoryId();
			if(assigncategorysid!=null&&assigncategorysid.length!=0){
				List assigncategorysname=new CategoryDao().getAssignCategorysName(assigncategorysid);
				request.setAttribute("assigncategorysname", assigncategorysname);
			}
		}
		else if(loginer.getGroupId()==1){
			//获取为用户分配的版块名称
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
			message="<li>修改个人资料失败！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
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
			message="√ 修改个人资料成功！3秒后将自动转发到个人资料主页！";
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
			message="<li>更换头像失败！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
			loginer.setMemberIcon(icon);
			
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/visit/myself/a/index";
			request.setAttribute("autoforward",autoforward);
			message="√ 更换头像成功！3秒后将自动转发到个人资料主页！";
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
			message="<li>修改个性签名失败！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
			loginer.setMemberSign(sign);
			
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/visit/myself/a/index";
			request.setAttribute("autoforward",autoforward);
			message="√ 修改个性签名成功！3秒后将自动转发到个人资料主页！";
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
			message="<li>输入的旧密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			UserDao userDao=new UserDao();
			String memberName=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
			String newpswd=request.getParameter("newpswd");
			
			Object[] params={newpswd,memberName};
			int i=userDao.updateMemberPswd(params);
			if(i<=0){
				message="<li>修改密码失败！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
				loginer.setMemberPswd(newpswd);
				
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/visit/myself/a/index";
				request.setAttribute("autoforward",autoforward);
				message="√ 修改密码成功！3秒后将自动转发到个人资料主页！";
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}
	private void cancelautologin(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		System.out.println("取消自动登录：自动转发-"+request.getHeader("referer"));
		
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

		request.setAttribute("message","√ 取消自动登录成功！3秒后将自动转发到个人资料主页！");
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void mycollect(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		int loginerid=((UserBean)request.getSession().getAttribute("loginer")).getId();
		try {
			List collectlist=new CollectDao().getCollects(loginerid);
			request.setAttribute("collectlist",collectlist);

			int num=collectlist.size();
			int max=Integer.parseInt(getServletContext().getInitParameter("collectmax"));			//获取允许收藏的数量值
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
			message="<li>主题ID值错误！</li>";
			forward=RUNFAIL;
		}
		else{
			int loginerid=((UserBean)request.getSession().getAttribute("loginer")).getId();
			int i=new CollectDao().deleteOne(topicId,loginerid);
			if(i<=0){
				message="<li>删除失败！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
				forward=RUNFAIL;
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/visit/myself/a/mycollect";						//自动转发到列表显示我的信息请求
				request.setAttribute("autoforward",autoforward);
				message="√ 删除成功！3秒后将自动转发到您的收藏夹！";
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
			message="<li>清空收藏夹失败！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>";
			forward=RUNFAIL;
		}
		else{			
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/visit/myself/a/mycollect";						//自动转发到列表显示我的信息请求
			request.setAttribute("autoforward",autoforward);
			message="√ 清空收藏夹成功！3秒后将自动转发到您的收藏夹！";
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
					message="<li>信息发送失败！</li>";
					message+="<br><a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{
					forward=INDEXTEMP;
					request.setAttribute("mainPage",RUNSUCCESS);
					
					String autoforward=request.getContextPath()+"/visit/myself/a/mymessagebox";						//自动转发到列表显示我的信息请求
					request.setAttribute("autoforward",autoforward);
					message="√ 发送信息成功！3秒后将自动转发到您的信息列表！";
				}
			}
			else{
				message="<li>不存在 <b>"+getter+"</b> 会员！</li>";
				message+="<br><a href='javascript:window.history.go(-1)'>返回重试</a>";
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
			message="<li>信息ID值错误！</li>";
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",getInitParameter("viewmymessage"));
			
			try {
				//获取查看的消息的详细内容
				MessageDao messageDao=new MessageDao();
				MessageBean single= messageDao.getMessageSingle(messageId);
				if(single==null){
					message="<li>要查看的消息不存在或已经被删除！</li>";
					forward=RUNFAIL;
				}
				else{
					if(single.getMessageReadmark().equals("1")){					//如果查看的是未读消息
						messageDao.updateReadMarkForInceptMessage(messageId);		//设置为已读
						//将用户未读的消息数减１
						UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
						int unread=loginer.getNewmessages();
						if(unread>0)
							loginer.setNewmessages(unread-1);
					}
					messageDao.closed();
					request.setAttribute("messagesingle",single);
					
					//获取消息的发送者或接收者信息
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
			message="<li>信息ID值错误！</li>";
			forward=RUNFAIL;
		}
		else{
			String from=request.getParameter("from");
			if(!"incept".equals(from)&&!"send".equals(from)){
				message="<li>信箱选择未知！</li>";
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
					
					String autoforward=request.getContextPath()+"/visit/myself/a/mymessagebox?from="+from;						//自动转发到列表显示我的信息请求
					request.setAttribute("autoforward",autoforward);
					message="√ 删除信息成功！3秒后将自动转发到您的信箱！";
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
			message="<li>信箱选择未知！</li>";
			forward=RUNFAIL;
		}
		else{
			String[] selectmessages=request.getParameterValues("selectmessages");
			int[] deletemessages=StringHandler.changeToIntArray(selectmessages);
			MessageDao messageDao=new MessageDao();

			message=deleterun(deletemessages,messageDao,request);
			messageDao.closed();
			if(!message.equals("")){
				message+="<br><a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/visit/myself/a/mymessagebox?from="+from;						//自动转发到列表显示我的信息请求
				request.setAttribute("autoforward",autoforward);
				message="√ 删除信息成功！3秒后将自动转发到您的信箱！";
			}						
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private String deleterun(int[] deletemessages,MessageDao messageDao,HttpServletRequest request){
		String message="";
		if(deletemessages==null||deletemessages.length==0)
			message="<li>请选择要删除的信息！</li>";
		else{
			try {
				int len=deletemessages.length;
				for(int i=0;i<len;i++){
					MessageBean single=messageDao.getDeleteSingle((Integer)deletemessages[i]);
					if(single==null)
						message+="<li>您要删除的'(id="+deletemessages[i]+")'信息不存在或已经被删除！</li>";
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
							message+="<li>删除'(id="+deletemessages[i]+")'信息时失败！</li>";
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
			System.out.println("获取我发表的帖子失败！");
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
			System.out.println("获取我回复的帖子失败！");
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
			message="<li>会员名称错误！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserBean member=new UserDao().getUserSingle(memberName);
				if(member==null){
					message="<li>会员(name="+memberName+")不存在或已经被删除！</li><br>";
					forward=RUNFAIL;
				}
				else{
					if(member.getGroupId()==2){
						//获取为用户分配的类别名称
						int[] assigncategorysid=member.getAssignCategoryId();
						if(assigncategorysid!=null&&assigncategorysid.length!=0){
							List assigncategorysname=new CategoryDao().getAssignCategorysName(assigncategorysid);
							request.setAttribute("assigncategorysname", assigncategorysname);
						}
					}
					else if(member.getGroupId()==1){
						//获取为用户分配的版块名称
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
			request.setAttribute("message","<li>会员名称错误！</li>");
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
				System.out.println("获取会员发表的帖子失败！");
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
			message="<li>会员ID值错误！</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserBean usersingle=new UserDao().getUserForAssignById(memberid);
				if(usersingle==null){
					message="<li>会员(id="+memberid+")不存在或已经被删除！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else if(usersingle.getGroupId()>=3){
					message="<li>该会员的身份可以管理所有类别及版块，不需要分配版块！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else if(usersingle.getGroupId()!=1){
					message="<li>不能为非版块管理组成员分配版块！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
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
					
					//获取所有类型的ID和名称
					Map categorys = new CategoryDao().getCategoryMap();
					request.setAttribute("categorys",categorys);
					
					//依次获取某类别下所有版块的ID和名称
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
				message="<li>选择的版块(id="+assignboardid+")不存在或已经被删除！</li>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				Integer memberid=StringHandler.strToint(request.getParameter("memberid"));
				int i=new GroupBoardDao().assignBoardToMember(memberid, assignboardid);
				if(i<=0){
					message="<li>为会员分配版块失败！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{
					forward=ADMINTEMP;
					request.setAttribute("mainPage",RUNSUCCESS);
					
					String autoforward=request.getParameter("autoforward");
					request.setAttribute("autoforward",autoforward);
					message="√ 分配版块成功！3秒后将自动转发到之前的请求！";
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
			message="<li>会员ID值错误！</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserBean usersingle=new UserDao().getUserForAssignById(memberid);
				if(usersingle==null){
					message="<li>会员(id="+memberid+")不存在或已经被删除！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else if(usersingle.getGroupId()>=3){
					message="<li>该会员的身份可以管理所有类别及版块，不需要分配类别！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else if(usersingle.getGroupId()!=2){
					message="<li>不能为非类别管理组成员分配类别！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
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
					//获取所有类型的ID和名称
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
				message="<li>选择的类别(id="+assigncategoryid+")不存在或已经被删除！</li>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				Integer memberid=StringHandler.strToint(request.getParameter("memberid"));
				int i=new GroupCategoryDao().assignCategoryToMember(memberid, assigncategoryid);
				if(i<=0){
					message="<li>为会员分类别块失败！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{
					forward=ADMINTEMP;
					request.setAttribute("mainPage",RUNSUCCESS);
					
					String autoforward=request.getParameter("autoforward");
					request.setAttribute("autoforward",autoforward);
					message="√ 分配类别成功！3秒后将自动转发到之前的请求！";
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
			message="<li>会员未知或类别名称未知！</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserDao userDao=new UserDao();
				int memberid= userDao.validatename(memberName);
				if(memberid<=0){
					message="<li>会员(name="+memberName+")不存在！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{					
					categoryName=new String(categoryName.getBytes("ISO8859_1"),"gb2312");
					int categoryid=new CategoryDao().validatename(categoryName);
					if(categoryid<=0){
						message="<li>类别(name="+categoryName+")不存在！</li>";
						message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
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
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			Integer memberId=StringHandler.strToint(request.getParameter("memberId"));
			Integer categoryId=StringHandler.strToint(request.getParameter("categoryId"));
			int i=new GroupCategoryDao().deleteassign(memberId,categoryId);
			if(i<=0){
				message="<li>卸职失败！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				forward=ADMINTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/admin/user/c/adminviewmember?memberName="+request.getParameter("memberName");
				request.setAttribute("autoforward",autoforward);
				message="<li>√ 卸职成功！3秒后将自动转发到查看会员资料页面！</li>";
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
			message="<li>会员未知或版块名称未知！</li>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserDao userDao=new UserDao();
				int memberid= userDao.validatename(memberName);
				if(memberid<=0){
					message="<li>会员(name="+memberName+")不存在！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{					
					boardName=new String(boardName.getBytes("ISO8859_1"),"gb2312");
					int boardid=new BoardDao().validatename(boardName);
					if(boardid<=0){
						message="<li>版块(name="+boardName+")不存在！</li>";
						message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
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
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			Integer memberId=StringHandler.strToint(request.getParameter("memberId"));
			Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
			int i=new GroupBoardDao().deleteassign(memberId,boardId);
			if(i<=0){
				message="<li>卸职失败！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				forward=ADMINTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/admin/user/c/adminviewmember?memberName="+request.getParameter("memberName");
				request.setAttribute("autoforward",autoforward);
				message="<li>√ 卸职成功！3秒后将自动转发到查看会员资料页面！</li>";
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
				message="<li>会员(id="+memberid+")不存在或已经被删除！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				String action=request.getParameter("action");
				if(action==null||action.equals("")||(!"disable".equals(action)&&!"enable".equals(action))){
					message="<li>会员状态(action="+action+")无效!</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
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
						message="<li>修改会员状态失败！</li><br>";
						message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
						forward=RUNFAIL;
					}
					else{
						forward=ADMINTEMP;
						request.setAttribute("mainPage",RUNSUCCESS);
						
						String autoforward=request.getHeader("referer");
						request.setAttribute("autoforward",autoforward);
						message="√ 修改会员状态成功！3秒后将自动转发到之前的请求！";
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
			message="<li>会员ID值错误！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserDao userDao=new UserDao();
				UserBean usersingle=userDao.getUserSingle(deletememberId);
				if(usersingle==null){
					message="<li>会员(id="+deletememberId+")不存在或已经被删除！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
					forward=RUNFAIL;
				}
				else{
					UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
					int loginerId=loginer.getId();
					if(loginerId==deletememberId){
						message="<li>不能删除自己！</li><br>";
						message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
						forward=RUNFAIL;
					}
					else if(loginer.getGroupId()<usersingle.getGroupId()){
						message="<li>您没有权限删除具有更高级别身份的会员！</li><br>";
						message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
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
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			Integer deletememberId=StringHandler.strToint(request.getParameter("deletememberId"));
			int i=new UserDao().delete(deletememberId);
			if(i<=0){
				message="<li>删除会员(id="+deletememberId+")失败！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{
				forward=ADMINTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getParameter("autoforward");
				request.setAttribute("autoforward",autoforward);
				message="√ 删除会员成功！3秒后将自动转发到之前的请求！";
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
			//查询name指定的用户是否存在
			int memberid = new UserDao().validatename(name);
			if(memberid>0)										//存在该会员名称
				disabled=true;
			else												//不存在
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
			oicq="无";
		if(sign==null||sign.equals(""))
			sign="留下一片空白！";
		
		Object[] params={0,name,pswd,sex,age,oicq,"default.gif",sign,"1",time,time,0,0,0};
		int i=new UserDao().insert(params);
		if(i<=0){
			message="<li>添加新会员失败！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			forward=ADMINTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/admin/user/c/membermanager";
			request.setAttribute("autoforward",autoforward);
			message="<li>添加新会员成功！3秒后将自动转发到 <b>会员管理</b> 模块主页！";
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
			message="<li>会员名称错误！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			try {
				UserBean member=new UserDao().getUserSingle(memberName);
				if(member==null){
					message="<li>会员(name="+memberName+")不存在或已经被删除！</li><br>";
					forward=RUNFAIL;
				}
				else{
					if(member.getGroupId()==2){
						//获取为用户分配的类别名称
						int[] assigncategorysid=member.getAssignCategoryId();
						if(assigncategorysid!=null&&assigncategorysid.length!=0){
							List assigncategorysname=new CategoryDao().getAssignCategorysName(assigncategorysid);
							request.setAttribute("assigncategorysname", assigncategorysname);
						}
					}
					else if(member.getGroupId()==1){
						//获取为用户分配的版块名称
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
				message="<li>要修改的会员不存在或已经被删除！</li>";
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
			message="<li>修改个人资料失败！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			forward=ADMINTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/admin/user/c/adminviewmember?memberName="+memberName;
			request.setAttribute("autoforward",autoforward);
			message="√ 修改会员资料成功！3秒后将自动转发到会员资料主页！";
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
			message="<li>会员名称错误！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'></a>";
			forward=RUNFAIL;			
		}
		else{
			try {
				int memberId = new UserDao().validatename(memberName);
				if(memberId<=0){
					message="<li>会员(name="+memberName+")不存在或已经被删除！</li><br>";
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
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			UserDao userDao=new UserDao();
			String memberName=request.getParameter("memberName");
			String newpswd=request.getParameter("newpswd");
			
			Object[] params={newpswd,memberName};
			int i=userDao.updateMemberPswd(params);
			if(i<=0){
				message="<li>修改密码失败！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
				forward=RUNFAIL;
			}
			else{		
				forward=ADMINTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/admin/user/c/adminviewmember?memberName="+memberName;
				request.setAttribute("autoforward",autoforward);
				message="√ 修改密码成功！3秒后将自动转发到个人资料主页！";
			}			
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);	
	}	

}