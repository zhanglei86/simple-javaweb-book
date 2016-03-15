package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.AttachmentDao;
import com.dao.ReplyDao;
import com.dao.TopicDao;
import com.toolsbean.MyFileHandler;
import com.toolsbean.StringHandler;
import com.valuebean.ReplyBean;

public class ReplyServlet extends SuperServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
		if(replyId==null){
			request.setAttribute("message","<li>回复帖ID值错误！</li>");
			RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
			rd.forward(request,response);	
		}
		else{
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
			else
				invalidate(request,response);			
		}
	}	
	/** @功能：处理进入编辑回复帖页面请求的Servlet */
	private void modifyview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			forward=INDEXTEMP;
			request.setAttribute("mainPage",getInitParameter("modifyview"));
			
			Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
			ReplyBean single=new ReplyDao().getReplySingle(replyId);
			if(single==null){
				message="<li>要编辑的回复帖不存在或已经被删除！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
				forward=RUNFAIL;
			}
			else
				request.setAttribute("replysingle",single);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	/** @功能：处理编辑回复帖请求的Servlet */
	private void modifyrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
		Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
		
		String title=request.getParameter("title");
		String content=StringHandler.saveQuoteText(request.getParameter("content"));
		String emotion=request.getParameter("emotion");
		String attachment=request.getParameter("attachment");
		Object[] params={title,content,emotion,replyId};

		int i=new ReplyDao().updateReply(params);
		if(i<=0){
			message="<li>编辑失败！</li>";
			message+="<a href='javascript:window.history.go(-1)'>返回</a>";
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			if("1".equals(attachment)){								//添加附件
				String autoforward=request.getContextPath()+"/visit/attachment/c/uploadview?replyId="+replyId;			//自动转发到添加附件页面的路径
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
	private void deleteview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward=INDEXTEMP;		
		request.setAttribute("mainPage",getInitParameter("deleteview"));
		
		try {
			Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
			ReplyBean replysingle=new ReplyDao().getReplySingle(replyId);
			if(replysingle==null){
				message="<li>要删除的回复帖不存在或已经被删除！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
				forward=RUNFAIL;
			}
			else
				request.setAttribute("replysingle",replysingle);
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
			Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
			
			TopicDao topicDao=new TopicDao();
			ReplyDao replyDao=new ReplyDao();
			AttachmentDao attachmentDao=new AttachmentDao();
			
			replyDao.deleteReply(replyId);			// 删除该回复帖
			topicDao.reduceReplyNum(topicId);		// 将该回复帖所属主题的回复数减１
			
			/** 删除该回复帖具有的附件 */
			List savenames=attachmentDao.getAttachmentSaveNameByReply(replyId);		//获取该回复帖的所有保存在磁盘中的附件名称
			deleteFiles(savenames);													//删除磁盘文件
			attachmentDao.deleteAttachmentByReplyId(replyId);						//然后删除数据库中信息
			
			String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;			//自动转发的路径
			request.setAttribute("autoforward",autoforward);
			message="√ 删除回复帖成功！3秒后将自动转发到当前主题！";
		}			

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	/** 
	 * @功能：调用MyFileHandler类的deleteFile()方法批量删除文件
	 * @参数：List集合savenames：表示要删除的文件名称 
	 */
	private void deleteFiles(List savenames){
		String filedir=getServletContext().getInitParameter("filedir");					//获取在当前Web应用下存放所有附件的目录
		String[] destfilepathname=null;													//用来存储要删除附件的物理路径
		if(savenames!=null&&savenames.size()!=0){
			destfilepathname=new String[savenames.size()];
			for(int k=0;k<savenames.size();k++){
				String savename=(String)savenames.get(k);								//获取附件保存在磁盘中使用的文件名
				String deletefilename=filedir+savename;									//当前Web应用下附件的存放路径
				destfilepathname[k]=getServletContext().getRealPath(deletefilename);	//获取附件的物理路径（磁盘目录下的路径）
			}
			MyFileHandler.deleteFile(destfilepathname);
		}
	}
}
