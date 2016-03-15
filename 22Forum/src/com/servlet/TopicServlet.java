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
			request.setAttribute("message","<li>主题ID值错误！</li>");
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
					Method md=getClass().getDeclaredMethod(operate,new Class[]{HttpServletRequest.class,HttpServletResponse.class});		//根据opName变量值获取名称为opName变量值的方法。Class数组指定该方法中各参数的类型
					md.setAccessible(true);													//设为true后将能调用私有方法
					md.invoke(this,new Object[]{request,response});							//调用方法，Object数组指定方法需要的参数
					
					System.out.println("调用方法：："+md);
				}catch (Exception e) {
					invalidate(request,response);	
				}			
			}
			else
				invalidate(request,response);			
		}
	}	
	/** @功能：处理观看帖子请求的方法 */
	private void view(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";		
		
		try {
			forward=INDEXTEMP;			
			request.setAttribute("mainPage",getInitParameter("viewPage"));
			
			TopicDao topicDao=new TopicDao();
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			
			TopicBean single=topicDao.getTopicSingle(topicId);												//获取主题详细信息
			if(single!=null){
				single.setTopicHits(single.getTopicHits()+1);												//将已查询出的主题的访问次数加１
				topicDao.addTopicHits(topicId);																//将数据库中主题的访问次数加１
				
				UserBean author=single.getTopicAuthor();													//获取帖子的作者
				if(author!=null){
					single.getTopicAuthor().setMemberCharm(author.getMemberCharm()+1);						//将已查询出的帖主的魅力值加１
					new GradeDao().charmLook(single.getTopicAuthorName());									//将数据库中帖主的魅力值加１
				}
				request.setAttribute("topicsingle",single);
				
				//以下代码获取上一主题、下一主题
				Map bothsides=topicDao.getBothSidesTopic(single.getTopicOperateTime(),(Integer)request.getSession().getAttribute("visitboard"));
				if(bothsides.size()!=0){
					request.setAttribute("prevId",bothsides.get("prevId"));
					request.setAttribute("nextId",bothsides.get("nextId"));
				}
			}
			else{
				message="<li>要查看的帖子不存在或已经被删除！</li>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
				forward=RUNFAIL;	
			}
		} catch (Exception e) {
			message="<li>查看主题失败！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>";
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
				message="<li>要回复的帖子不存在或已经被删除！</li>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
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
		if(i<=0){													//回复失败
			message="<li>回复失败！<br></li>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{														//回复成功
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId+"#topic";						//自动转发到观看主题请求的路径
			request.setAttribute("autoforward",autoforward);
			message="√ 回复帖子成功！3秒后将自动转发到您回复的主题！";
			
			TopicDao topicDao=new TopicDao();
			GradeDao gradeDao=new GradeDao();			
			topicDao.addTopicReplyNum(topicId);																	//累加主题的回复数	
			topicDao.updateOperateTime(topicId,time);															//更新主题的被操作时间为当前时间
			gradeDao.experReply(((UserBean)request.getSession().getAttribute("loginer")).getId());				//增加回帖人的经验值
			gradeDao.charmReply(request.getParameter("author"));										//增加帖主(主题作者)的魅力值
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
	
	/** @功能：处理进入编辑主题页面请求的Servlet */
	private void modifyview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			TopicBean single=new TopicDao().getEditViewSingle(topicId);
			if(single==null){
				message="<li>要编辑的帖子不存在或已经被删除！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
				forward=RUNFAIL;
			}
			else{
				if(!"2".equals(single.getTopicStatus())){
					message="<li>要编辑的帖子被锁定或关闭，不能进行编辑！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
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
	/** @功能：处理编辑主题请求的Servlet */
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
			message="<li>编辑失败！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			if("1".equals(attachment)){								//添加附件
				String autoforward=request.getContextPath()+"/visit/attachment/c/uploadview?topicId="+topicId;			//自动转发到添加附件页面的路径
				request.setAttribute("autoforward",autoforward);
				message="√ 编辑成功！下面将转发到添加附件页面！";
			}
			else{													//没有附件
				String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//自动转发到观看主题请求的路径
				request.setAttribute("autoforward",autoforward);
				message="√ 编辑成功！3秒后将自动转发到您修改的帖子！";
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
				request.setAttribute("message","<li>您要修改的帖子不存在或已经被删除！</li>");
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
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
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
					message="<li>修改帖子类型失败！</li>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a>";
					forward=RUNFAIL;
				}
				else{
					forward=INDEXTEMP;
					request.setAttribute("mainPage",RUNSUCCESS);
					
					Integer boardId=StringHandler.strToint(request.getParameter("boardId"));
					BoardDao boardDao=new BoardDao();
					if(oldtype.equals("1"))						//如果原来的类型是精华帖
						boardDao.updateBestTopicNum("reduce",boardId);				//将帖子所属版块的精华帖数减１
					else if(newtype.equals("1"))				//如果新的类型是精华帖
						boardDao.updateBestTopicNum("add",boardId);					//将帖子所属版块的精华帖数加１
					
					String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//自动转发到观看主题请求的路径
					request.setAttribute("autoforward",autoforward);
					message="√ 修改帖子类型成功！3秒后将自动转发到您修改的帖子！";
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
				request.setAttribute("message","<li>您要修改的帖子不存在或已经被删除！</li>");
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
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			String time=StringHandler.timeTostr(new Date());
			String status=request.getParameter("status");
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			Object[] params={status,time,topicId};
			
			int i=new TopicDao().updateStatus(params);
			if(i<=0){
				message="<li>修改帖子状态失败！</li>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
				forward=RUNFAIL;
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//自动转发到观看主题请求的路径
				request.setAttribute("autoforward",autoforward);
				message="√ 修改帖子状态成功！3秒后将自动转发到您修改的帖子！";
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
				message="<li>您要搬移的帖子不存在或已经被删除！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
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
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
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
						message="<li>移动帖子失败！</li><br>";
						message+="<a href='javascript:window.history.go(-1)>返回重试</a>'";
						forward=RUNFAIL;
					}
					else{
						forward=INDEXTEMP;
						request.setAttribute("mainPage",RUNSUCCESS);
						
						BoardDao boardDao=new BoardDao();
						String topicType=request.getParameter("topicType");
						Integer oldboardId=StringHandler.strToint(request.getParameter("oldboardId"));
						
						boardDao.reduceTopicNum(topicType, oldboardId);				//将帖子所属旧版块的主题数减１
						boardDao.addTopicNum(topicType, newboardId);				//将帖子所属新版块的主题数加１
						
						String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//自动转发到观看主题请求的路径
						request.setAttribute("autoforward",autoforward);
						message="√ 移动帖子成功！3秒后将自动转发到您移动的帖子！";
					}				
				}
				else{
					message="<li>目的版块(id="+newboardId+")已经被删除！</li>";
					message+="<a href='javascript:window.history.go(-1)>返回重试</a>'";
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
	/** @功能：提前帖子 */
	private void movefirst(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			TopicDao topicDao=new TopicDao();
			
			TopicBean topicBean = topicDao.getEditViewSingle(topicId);
			if(topicBean==null){
				message="<li>要提前的帖子不存在或已经被删除！</li>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>'";
				forward=RUNFAIL;
			}
			else{
				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				topicDao.updateOperateTime(topicId,StringHandler.timeTostr(new Date()));
				String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//自动转发到观看主题请求的路径
				request.setAttribute("autoforward",autoforward);
				message="√ 提前帖子成功！3秒后将自动转发到您提前的帖子！";
			}
		} catch (SQLException e) {
			message="<li>提前帖子失败！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>";
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
		
		boolean isexist=collectDao.isExist(memberid, topicid);						//查询用户是否已经收藏了指定的帖子
		if(isexist){									//如果已经收藏了该帖
			message="<li>您已经收藏了该帖！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>";
			forward=RUNFAIL;
		}
		else{											//没有收藏该帖，先要计算出收藏的数量是否已经达到允许的值
			try {
				int num=collectDao.getCollectNum(memberid);							//获取收藏的数量
				int max=Integer.parseInt(getServletContext().getInitParameter("collectmax"));			//获取允许收藏的数量值
				if(num<max){
					String time=StringHandler.timeTostr(new Date());
					Object[] params={memberid,topicid,time};					
					int i=new TopicDao().addCoolect(params);
					if(i<=0){
						message="<li>收藏帖子失败！</li>";
						message+="<a href='javascript:window.history.go(-1)'>返回</a>";
						forward=RUNFAIL;
					}
					else{
						forward=INDEXTEMP;
						request.setAttribute("mainPage",RUNSUCCESS);						
						String autoforward=request.getContextPath()+"/visit/myself/a/mycollect";						//自动转发到观看我的收藏请求的路径
						request.setAttribute("autoforward",autoforward);
						message="√ 收藏帖子成功！3秒后将自动转发到您的收藏夹！";
					}
				}
				else{
					message="<li>您的收藏夹已满！</li><br>";
					message+="<a href='javascript:window.history.go(-1)'>返回</a><br>";
					message+="<a href='"+request.getContextPath()+"/visit/user/a/listmycollect'>进入收藏夹</a>";			
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
				request.setAttribute("message","<li>您要删除的帖子不存在或已经被删除！</li>");
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
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
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
			
			/** 删除该主题 */
			topicDao.deleteTopic(topicId);			
			
			/** 更新主题所属版块中主题数 */
			String topicType=request.getParameter("topicType");
			Integer boardId =StringHandler.strToint(request.getParameter("boardId"));
			boardDao.reduceTopicNum(topicType,boardId);												//更新版块具有的帖子数			
			
			/** 删除该主题所带的附件 */
			try {					
				//首先删除磁盘文件
				List savenames=attachmentDao.getAttachmentSaveNameByTopic(topicId);
				deleteFiles(savenames);
				
				//然后删除数据库中信息
				attachmentDao.deleteAttachmentByTopicId(topicId);
			} catch (SQLException e) {					
				e.printStackTrace();
			}
			
			/** 删除该主题的所有回复贴 */
			replyDao.deleteReplyByTopicId(topicId);
			
			/** 删除该主题下所有回复帖具有的附件 */
			try {					
				//首先删除磁盘文件
				List savenames=attachmentDao.getAttachmentSaveNameByTopicAndReply(topicId);
				deleteFiles(savenames);
				
				//然后删除数据库中信息
				attachmentDao.deleteAttachmentByTopicAndReply(topicId);
			} catch (SQLException e) {					
				e.printStackTrace();
			}
			
			String autoforward=request.getContextPath()+"/visit/board/a/listspecialtopic?boardId="+boardId;		//自动转发到显示帖子列表请求的路径
			request.setAttribute("autoforward",autoforward);
			message="√ 删除帖子成功！3秒后将自动转发到帖子列表！";

		}			

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	/** @功能：调用MyFileHandler类的deleteFile()方法批量删除文件 */
	private void deleteFiles(List savenames){
		String filedir=getServletContext().getInitParameter("filedir");						//在当前Web应用下存放附件的目录
		String[] destfilepathname=null;									//用来存储要删除附件的物理路径
		if(savenames!=null&&savenames.size()!=0){
			destfilepathname=new String[savenames.size()];
			for(int k=0;k<savenames.size();k++){
				String savename=(String)savenames.get(k);										//获取附件保存在磁盘中使用的文件名
				String deletefilename=filedir+savename;											//相对于当前Web应用下附件的存放路径
				destfilepathname[k]=getServletContext().getRealPath(deletefilename);			//获取附件的物理路径
			}
			MyFileHandler.deleteFile(destfilepathname);
		}
	}
	/** @功能：显示所有待审核的帖子 */
	private void listuncheck(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String forward="";
		
		List unchecktopics=null;
		UserBean loginer=(UserBean)request.getSession().getAttribute("loginer");
		
		if(loginer.getGroupId()==0){
			String message="<li>您没有权限查看待审核帖子！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>'";
			request.setAttribute("message",message);
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",getInitParameter("listunchecktopics"));

			if(loginer.getGroupId()>=3)					//获取所有待审核帖子
				unchecktopics=new TopicDao().getAllUnCheckTopics();
			else if(loginer.getGroupId()==2)				//获取分配给当前用户所有类别中的待审核帖子
				unchecktopics=new TopicDao().getMyCategoryUnCheckTopics(loginer.getAssignCategoryId());
			else if(loginer.getGroupId()==1)				//获取分配给当前用户所有版块中的待审核帖子
				unchecktopics=new TopicDao().getMyBoardUnCheckTopics(loginer.getAssignBoardId());
			request.setAttribute("unchecktopics",unchecktopics);
		}
		
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
}
